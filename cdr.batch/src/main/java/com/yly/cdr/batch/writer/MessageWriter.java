package com.yly.cdr.batch.writer;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yly.cdr.batch.exception.database.DatabaseException;
import com.yly.cdr.batch.exception.mail.MailException;
import com.yly.cdr.batch.exception.message.MessageBusinessException;
import com.yly.cdr.batch.exception.message.MessageIntegrityException;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.Message;
import com.yly.cdr.entity.MessageFailure;
import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.service.MessageService;
import com.yly.cdr.util.PropertiesUtils;
import com.yly.cdr.util.ValidatorUtils;
import com.yly.cdr.util.mail.MailUtils;

/**
 * 消息Writer
 * 
 * @author wen_ruichao
 * @version 1.0
 */
public class MessageWriter implements ItemWriter<BaseDto>
{

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String DEFAULT_GROUP_PREFIX = "Group";

    private static final String DEFAULT_WRITER_SUFFIX = "Writer";

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private MessageService messageService;

    /**
     * 消息ID（根据消息ID，调用对应的业务Writer）
     */
    private String messageId;

    public void setMessageId(String messageId)
    {
        this.messageId = messageId;
    }

    /**
     * 
     * @param baseDtos 
     * @exception 在write方法中抛出RuntimeException，来调用reader中close()结束当前job。
     */
    public void write(List<? extends BaseDto> baseDtos) throws Exception
    {
        logger.trace("write()");
        for (BaseDto baseDto : baseDtos)
        {
            doWrite(baseDto);
        }
    }

    /**
     * 根据消息ID调用不同Writer
     * 
     * @param baseDto
     * @exception 抛出RuntimeException异常
     */
    private void doWrite(BaseDto baseDto) throws Exception
    {
        String groupName = PropertiesUtils.getValue("package.dto") + "."
            + messageId.toLowerCase() + "." + DEFAULT_GROUP_PREFIX + messageId;

        // Add by jin_peng 2013.04.09
        if (!ValidatorUtils.validate(baseDto, Class.forName(groupName),
                messageId))
        {
            integrate(baseDto);
        }

        // End by jin_peng 2013.04.09

        BusinessWriter<BaseDto> businessMessageWriter = (BusinessWriter<BaseDto>) beanFactory.getBean(messageId.toLowerCase()
            + DEFAULT_WRITER_SUFFIX);
        if (businessMessageWriter.validate(baseDto))
        { // 自定义的dto数据校验通过
            if (businessMessageWriter.checkDependency(baseDto,false))
            { // 依赖存在
                try
                {
                    businessMessageWriter.saveOrUpdate(baseDto);
                    deleteMessage(baseDto);
                }
                catch (Exception e)
                {
                    logger.error("数据库异常:{}", e.getMessage());
                    throw new DatabaseException(messageId, e);
                }
            }
            else
            { // 依赖不存在
                throw new MessageBusinessException(messageId); // 跳过当前消息，继续执行下一个消息。
            }
        }
        else
        { // 自定义的dto数据校验未通过
            integrate(baseDto);
        }
    }

    /**
     * 
     * @param baseDto
     * @throws MessageIntegrityException
     */
    private void integrate(BaseDto baseDto) throws Exception
    {
        // $Author :wu_biao
        // $Date : 2013/02/25 14:44$
        // [BUG]13879 ADD BEGIN
        MessageFailure messageFailure = new MessageFailure();
        messageFailure.setVid(baseDto.getMessage().getVid());
        messageFailure.setContent(baseDto.getMessage().getContent());
        messageFailure.setDatetime(baseDto.getMessage().getDatetime());
        messageFailure.setCreateTime(new Date());
        // [BUG]13879 ADD END
        try
        {
            if (saveMessageFailure(messageFailure))
            { // 保存错误消息
                deleteMessage(baseDto); // 删除备份消息
            }
        }
        catch (Exception e)
        {
            logger.error("数据库异常:{}", e.getMessage());
            throw new DatabaseException(messageId, e);
        }
        try
        {
            MailUtils.sendMail(messageFailure); // 发送错误消息
        }
        catch (Exception e)
        {
            logger.error("邮件服务器异常:{}", e.getMessage());
            throw new MailException(messageId, e);
        }
        throw new MessageIntegrityException(messageId); // 跳过当前消息，继续执行下一个消息。
    }

    /**
     * 消息中确实无需要的数据，造成消息内容保存失败，需要保存并发送消息内容，待后续沟通。
     * 
     * @param messageFailure
     */
    private boolean saveMessageFailure(MessageFailure messageFailure)
    {
        // messageFailure.setDatetime(new Date());
        messageFailure.setReason("V3消息" + messageId + "数据不完整");
        messageFailure.setFlag(Constants.FLAG_DATA_INTEGRITY);
        if (messageService.saveMessageFailure(messageFailure) == 1)
        {
            return true;
        }
        return false;
    }

    /**
     * 消息内容保存成功，删除备份消息。
     * 
     * @param baseDto
     */
    private boolean deleteMessage(BaseDto baseDto)
    {
        Message message = new Message();
        message.setId(baseDto.getMessage().getId());
        if (messageService.deleteMessage(baseDto.getMessage().getId(),baseDto.getMessage().getCreateTime().toString()) == 1)
        {
            return true;
        }
        return false;
    }
}
