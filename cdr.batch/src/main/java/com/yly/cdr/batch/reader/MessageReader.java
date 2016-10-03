package com.yly.cdr.batch.reader;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.yly.cdr.batch.exception.database.DatabaseException;
import com.yly.cdr.batch.exception.mail.MailException;
import com.yly.cdr.batch.exception.message.MessageStructureException;
import com.yly.cdr.batch.exception.mq.MQException;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.Message;
import com.yly.cdr.entity.MessageFailure;
import com.yly.cdr.service.MessageService;
import com.yly.cdr.util.PropertiesUtils;
import com.yly.cdr.util.StringUtils;
import com.yly.cdr.util.mail.MailUtils;
import com.yly.cdr.util.mq.MQClient;
import com.yly.cdr.util.mq.MQSession;

/**
 * Batch中方法执行顺序（已测）：
 * 正常：open()->update()->read()->process()->write()->update()->read()->update()->执行完指定消息->close()
 * 
 * reader中read()方法返回null：open()->update()->read()->update()->close()
 * reader中read()方法抛出{@link com.yly.cdr.batch.exception.UnexpectedException}异常：open()->update()->read()->close()
 * reader中read()方法抛出{@link com.yly.cdr.batch.exception.ExpectedException}异常：open()->update()->read()->...read()->执行完指定消息->update()->close()
 * 
 * processor中process()方法抛出{@link com.yly.cdr.batch.exception.UnexpectedException}异常：open()->update()->read()->process()->close()
 * 
 * writer中write()方法抛出{@link com.yly.cdr.batch.exception.UnexpectedException}异常：open()->update()->read()->process()->write()->close()
 * 
 * @author wen_ruichao
 * @version 1.0
 */
public class MessageReader implements ItemReader<Message>, ItemStream
{

    private static final Logger logger = LoggerFactory.getLogger(MessageReader.class);

    @Autowired
    private MessageService messageService;

    private MQClient mqClient;

    /**
     * 队列名称
     */
    private String qName;

    /**
     * 当前队列的长度。默认0。
     */
    private int qLength = 0;

    /**
     * 备份消息是否存在，默认存在。防止读队列消息时候，每次都连接数据库。
     */
    private boolean isExist = true;

    /**
     * 消息ID（根据消息ID，获取队列名称）
     */
    private String messageId;

    public void setMessageId(String messageId)
    {
        this.messageId = messageId;
    }

    /**
     * 初始化当前job
     * 只能在step open时初始化备份消息状态。其它任何阶段都不可避免消息丢失。
     * @exception
     */
    public void open(ExecutionContext executionContext)
            throws ItemStreamException
    {
        logger.trace("open()");
        try
        {
            // 只能在step open时初始化备份消息状态。其它任何阶段都不可避免消息丢失。
            initMessageState(messageId);
        }
        catch (Exception e)
        {
            logger.error("数据库异常:{}", e.getMessage());
            throw new DatabaseException(messageId, e);
        }

        try
        {
            // 初始化MQ服务
            MQSession mqSession = new MQSession();
            mqSession.setHostname(PropertiesUtils.getValue("mq.hostname"));
            mqSession.setChannel(PropertiesUtils.getValue("mq.channel"));
            mqSession.setCcsid(Integer.parseInt(PropertiesUtils.getValue("mq.ccsid")));
            mqSession.setPort(Integer.parseInt(PropertiesUtils.getValue("mq.port")));
            mqClient = new MQClient(mqSession,
                    PropertiesUtils.getValue("mq.qmName"));

            qName = PropertiesUtils.getValue("mq.qName." + messageId);
            // 根据qName获取对应队列长度
            qLength = mqClient.getQueueLength(qName);
        }
        catch (Exception e)
        {
            logger.error("MQ服务器异常:{}", e.getMessage());
            throw new MQException(messageId, e);
        }
    }

    /**
     * 中间业务操作接口
     * 
     * @exception
     */
    public void update(ExecutionContext executionContext)
            throws ItemStreamException
    {
        logger.trace("update()");
    }

    /**
     * 结束当前job。（设置的消息执行完，正常完成或异常终止）
     * 
     * @exception
     */
    public void close() throws ItemStreamException
    {
        logger.trace("close()");
        try
        {
            // 关闭队列
            mqClient.closeQueue(qName);

            // 断开本地连接
            mqClient.disconnectQueueManager(qName);
        }
        catch (Exception e)
        {
            logger.error("MQ服务器异常:{}", e.getMessage());
            throw new MQException(messageId, e);
        }

        // 重置队列长度
        qLength = 0;
    }

    /**
     * 
     * @return message:消息对象； null:结束当前step。
     * @exception
     */
    public Message read() throws Exception, UnexpectedInputException,
            ParseException, NonTransientResourceException
    {
        logger.trace("read()");
        // 备份表操作
        if (isExist)
        {
            Message dbMessage = null;
            try
            {
                dbMessage = getMessageFromDB(messageId);
            }
            catch (Exception e)
            {
                logger.error("数据库异常:{}", e.getMessage());
                throw new DatabaseException(messageId, e);
            }
            if (dbMessage != null)
            {
                //Author:jia_yanqing
                //Date:2013/2/26 11:09
                //[BUG]0014124 MODIFY BEGIN
                //logger.debug("read message from db: \n{}",
                //dbMessage.getContent());
                //[BUG]0014124 MODIFY END
                try
                {
                    dbMessage.setState(Constants.STATE_RUNNING);
                    updateMessageStateFromDB(dbMessage);
                }
                catch (Exception e)
                {
                    logger.error("数据库异常:{}", e.getMessage());
                    throw new DatabaseException(messageId, e);
                }
                return dbMessage;
            }
            else
            {
                isExist = false;
            }
        }

        if (qLength == 0)
        {
            return null; // end the step here
        }
        else
        {
            qLength--;
        }

        // 读MQ消息
        String content = "";
        try
        {
            content = getMessageFromMQ();
        }
        catch (Exception e)
        {
            logger.error("MQ服务器异常:{}", e.getMessage());
            throw new MQException(messageId, e);
        }
        if (!StringUtils.isEmpty(content))
        {
            //Author:jia_yanqing
            //Date:2013/2/26 11:09
            //[BUG]0014124 MODIFY BEGIN
            //logger.debug("read message from MQ: \n{}", content);
            //[BUG]0014124 MODIFY END
            if (validateMessage(content))
            { // 校验通过
                Message mqMessage = new Message();
                try
                {
                    mqMessage.setContent(content);
                    saveMessage(mqMessage);
                }
                catch (Exception e)
                {
                    logger.error("数据库异常:{}", e.getMessage());
                    throw new DatabaseException(messageId, e);
                }
                try
                {
                    removeMessageFromMQ();
                }
                catch (Exception e)
                {
                    logger.error("MQ服务器异常:{}", e.getMessage());
                    throw new MQException(messageId, e);
                }
                return mqMessage;
            }
            else
            { // 校验未通过
                MessageFailure messageFailure = new MessageFailure();
                try
                {
                    messageFailure.setContent(content);
                    saveMessageFailure(messageFailure); // 保存结构错误消息
                }
                catch (Exception e)
                {
                    logger.error("数据库异常:{}", e.getMessage());
                    throw new DatabaseException(messageId, e);
                }
                try
                {
                    MailUtils.sendMail(messageFailure); // 发送结构错误消息
                }
                catch (Exception e)
                {
                    logger.error("邮件服务器异常:{}", e.getMessage());
                    throw new MailException(messageId, e);
                }
                try
                {
                    removeMessageFromMQ();
                }
                catch (Exception e)
                {
                    logger.error("MQ服务器异常:{}", e.getMessage());
                    throw new MQException(messageId, e);
                }
                throw new MessageStructureException(messageId); // 跳过当前消息，继续执行下一个消息。
            }
        }
        return null;
    }

    /**
     * 执行完当前队列后，重置正在运行的消息，下次batch启动时运行。
     * 
     * @param messageId
     * @return
     */
    private void initMessageState(String messageId)
    {
        messageService.updateMessageStateByVid(messageId);
    }

    /**
     * 更改消息状态
     * 
     * @param id
     */
    private boolean updateMessageStateFromDB(Message message)
    {
        if (messageService.updateMessageState(message.getId(),message.getState(),message.getCreateTime().toString(),message.getServiceId()) == 1)
        {
            return true;
        }
        return false;
    }

    /**
     * 查询失败的消息，每次读一条。
     * @param id 当前消息ID
     * @return 备份消息对象
     */
    private Message getMessageFromDB(String id)
    {
        return messageService.getMessage(id);
    }

    /**
     * 从消息队列获取消息后，先保存消息，防止batch服务器down掉后丢失消息。
     * 
     * @param message
     * @return
     */
    private boolean saveMessage(Message message)
    {
        message.setVid(messageId);
        message.setDatetime(new Date());
        message.setState(Constants.STATE_UNRUN);
        if (messageService.saveMessage(message) == 1)
        {
            return true;
        }
        return false;
    }

    /**
     * 校验未通过，保存消息到错误消息表。
     * 
     * @param messageFailure
     */
    private boolean saveMessageFailure(MessageFailure messageFailure)
    {
        messageFailure.setVid(messageId);
        messageFailure.setDatetime(new Date());
        messageFailure.setReason("V3消息" + messageId + "结构校验未通过");
        messageFailure.setFlag(Constants.FLAG_DATA_STRUCTURE);
        if (messageService.saveMessageFailure(messageFailure) == 1)
        {
            return true;
        }
        return false;
    }

    /**
     * 校验消息结构
     * 
     * @param content
     * @return
     */
    private boolean validateMessage(String content)
    {
        // InputStream xmlStream = null;
        // InputStream xsdStream = null;
        // try {
        // xmlStream = new ByteArrayInputStream(content.getBytes());
        // xsdStream =
        // Thread.currentThread().getContextClassLoader().getResourceAsStream(PropertiesUtils.getProperty("validation."
        // + messageId));
        // return SchemaValidator.validate(xmlStream, xsdStream);
        // } finally {
        // if (xmlStream != null) {
        // try {
        // xmlStream.close();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // if (xsdStream != null) {
        // try {
        // xsdStream.close();
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        // }
        return true;
    }

    /**
     * 根据messageId从MQ中获取当前消息对应的队列中的消息
     * @param id 当前消息ID
     * @return 队列中某一条消息
     */
    private String getMessageFromMQ() throws Exception
    {
        return mqClient.receive(qName);
    }

    /**
     * 根据messageId从MQ中提交当前消息对应的队列的事务，即可从对应队列中删除当前消息。
     * @param id 当前消息ID
     * @return
     */
    private void removeMessageFromMQ() throws Exception
    {
        mqClient.commitQueueManager(qName);
    }
}
