package com.yly.cdr.batch.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.core.exception.DBConcurrencyException;
import com.yly.cdr.batch.exception.message.MessageBusinessException;
import com.yly.cdr.batch.exception.message.MessageExternalDuplicateValidationException;
import com.yly.cdr.batch.exception.message.MessageExternalSendException;
import com.yly.cdr.batch.exception.message.MessageIntegrityException;
import com.yly.cdr.batch.exception.message.MessageWsSendException;
import com.yly.cdr.batch.processor.MessageProcessor;
import com.yly.cdr.batch.processor.V2MessageProcessor;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.Message;
import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.service.MessageService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.ExceptionUtils;
import com.yly.cdr.util.PropertiesUtils;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JobRunner implements Runnable
{
    private static final Logger logger = LoggerFactory.getLogger(JobRunner.class);

    @Autowired
    private MessagePool pool;

    private Hl7Message message;

    private String retryAccess;
    
    @Autowired
    private MessageWriter writer;

    @Autowired
    private MessageSender sender;

    @Autowired
    private MessageWriteLog writeLog;

    @Autowired
    private MessageService messageService;

    public void setMessage(Hl7Message message)
    {
        this.message = message;
    }

    public void setRetryAccess(String retryAccess)
    {
        this.retryAccess = retryAccess;
    }

    public void run()
    {
        logger.debug("start to process data {}.", message.getUuid());
        String sendDefault = PropertiesUtils.getValue("sendMessage.default");
        String sendExternal = PropertiesUtils.getValue("sendMessage.external");
        List<BaseDto> chrunk = new ArrayList<BaseDto>();
        try
        {
            // $Author :jin_peng
            // $Date : 2014/02/11
            // 对外应用服务 MODIFY BEGIN
            // $Author :wu_biao
            // $Date : 2013/03/13
            // 警告通知框架 ADD BEGIN
            // $Author :wu_biao
            // $Date : 2013/02/26 14:44$
            // [BUG]14004 ADD BEGIN
            chrunk = runInternal1();

            try
            {
                if ("1".equals(sendExternal))
                {
                    if (!Constants.ACCESS_WS.equals(retryAccess))
                    {
                        runInternalExternal(chrunk);
                    }
                }
            }
            catch (MessageExternalDuplicateValidationException ce)
            {
                logger.error("消息{}调用对外应用服务失败。", message.getUuid());
                message.setParamsExternal("0");
                pool.reExternalSendMessage(message);
            }
            catch (MessageExternalSendException ce)
            {
                logger.error("消息{}调用对外应用服务失败。", message.getUuid());
                message.setParamsExternal("1");
                pool.reExternalSendMessage(message);
            }

            if (!Constants.ACCESS_EXTERNAL.equals(retryAccess))
            {
                if (!"1".equals(message.getReWsSflag()))
                {
                    runInternal2(chrunk);
                }
                if ("1".equals(sendDefault))
                {
                    runInternal3(chrunk);
                }

                logger.debug("data {} process successed.", message.getUuid());
            }
            // 警告通知框架 ADD END
            // 对外应用服务 MODIFY END

            // [BUG]14004 ADD END
        }
        catch (MessageBusinessException mbe)
        {
            logger.warn("消息{}依赖的基础数据不存在，暂时跳过。", message.getUuid());
            pool.skipMessage(message);
        }
        /** Added by XuDengfeng, 2012/09/01 */
        catch (DBConcurrencyException ce)
        {
            logger.warn("消息{}乐观锁异常，暂时跳过。", message.getUuid());
            pool.skipMessage(message);
        }
        /** End, 2012/09/01 */
        // $Author :wu_biao
        // $Date : 2013/04/23
        // BUG31649 ADD BEGIN
        catch (DeadlockLoserDataAccessException ce)
        {
            logger.warn("消息{}等待资源时检测到死锁，暂时跳过。", message.getUuid());
            pool.skipMessage(message);
        }
        // BUG31649 ADD END
        // $Author :wu_biao
        // $Date : 2013/03/13
        // 警告通知框架 ADD BEGIN
        catch (MessageWsSendException ce)
        {
            if ("1".equals(sendDefault))
            {
                logger.error("消息{}调用WS再发送消息失败。", message.getUuid());
                pool.removeMessage(this.message, "5");
                pool.reWsSendMessage(message);
            }
        }
        // $Author :wu_biao
        // $Date : 2013/03/18
        // BUG14525 ADD BEGIN
        catch (DuplicateKeyException ce)
        {
            logger.error("消息{}唯一性约束异常", message.getUuid());
            String reason = "唯一性约束异常";
            backupErrorMessage(reason);
            writeLog.writeLog(chrunk, reason);
            sender.run(message);
        }
        catch (MessageIntegrityException e)
        {

            String reason = e.getMessage();
            backupErrorMessage(reason);
            sender.run(message);
        }

        // BUG14525 ADD END
        catch (Exception e)
        {
            logger.error("任务异常：\r\n{}", ExceptionUtils.getStackTrace(e));
            logger.error("data {} process failed!", message.getUuid());
            String reason = e.getMessage();
            if (reason == null || "".equals(reason))
                reason = "消息" + message.getMessageType() + "数据不完整";
            backupErrorMessage(reason);
            if (chrunk.size() == 0)
            {
                Message message1 = new Message();
                message1.setVid(message.getMessageType());
                message1.setId(message.getDatabaseId());
                message1.setServiceId(message.getServiceId());
                BaseDto dto1 = new BaseDto();
                dto1.setMessage(message1);
                chrunk.add(dto1);
            }
            writeLog.writeLog(chrunk, reason);
            sender.run(message);
        }
        catch (Throwable t)
        {
            logger.error("系统异常\r\n{}", ExceptionUtils.getStackTrace(t));
            logger.error("data {} process failed!", message.getUuid());
            backupErrorMessage("系统异常");
            if (chrunk.size() == 0)
            {
                Message message1 = new Message();
                message1.setVid(message.getMessageType());
                message1.setId(message.getDatabaseId());
                message1.setServiceId(message.getServiceId());
                BaseDto dto1 = new BaseDto();
                dto1.setMessage(message1);
                chrunk.add(dto1);
            }
            writeLog.writeLog(chrunk, "系统异常");
            sender.run(message);
        }
        logger.debug("data {} process finished.", message.getUuid());
    }

    // $Author :jin_peng
    // $Date : 2014/02/11
    // 对外应用服务 MODIFY BEGIN
    // $Author :wu_biao
    // $Date : 2013/03/13
    // 警告通知框架 ADD BEGIN
    @Transactional
    public void runInternal2(List<? extends BaseDto> baseDtos) throws Exception
    {
        String state = "0";

        if ("1".equals(message.getIsRestartRetry())
            && messageService.isExternalMessage(message.getServiceId()))
        {
            Message messageDto = messageService.selectMessageById(
                    message.getDatabaseId(), message.getCreateTime());

            state = messageDto.getState();
            
            if("3".equals(state))
            {
                message.setIsRestartRetry("0");
            }
        }

        if ("0".equals(state))
        {
            writer.write(baseDtos);
            pool.removeMessage(this.message, "3");
            String writeLogflg = PropertiesUtils.getValue("writeLog.default");
            if ("1".equals(writeLogflg))
            {
                writeLog.writeLog(baseDtos, "sucess");
            }
        }
    }

    // 对外应用服务 MODIFY END

    /**
     * 港大项目设置服务ID...
     * @return
     * @throws Exception
     */
    @Transactional
    public List<BaseDto> runInternal1() throws Exception
    {
        Message msg = null;
        BaseDto dto = null;
        String messageId = message.getMessageType();
        if (messageId == null || "unknow".equals(messageId))
        {
            logger.warn("消息ID不存在，跳过消息{}.", message.getUuid());
            throw new NullPointerException("消息ID不存在");
        }
        if(Constants.V2.equals(message.getMsgMode())){
        	V2MessageProcessor processor = new V2MessageProcessor();
        	 processor.setMessageId(messageId);
             msg = this.construct(message);
             dto = processor.process(msg);
             writer.setMessageId(msg.getVid());
        }
        else{
        	MessageProcessor processor = new MessageProcessor();
			 processor.setMessageId(messageId);
		     msg = this.construct(message);
		     dto = processor.process(msg);
        }
        // 重新给service_id赋值
        message.setServiceId(msg.getServiceId());
        messageService.updateMessage(msg.getId(), msg.getServiceId(), msg.getBatchId());
        List<BaseDto> chrunk = new ArrayList<BaseDto>();
        chrunk.add(dto);
        // writeLog.writeLog(chrunk);
        return chrunk;
    }

    // @Transactional
    public void runInternal3(List<? extends BaseDto> baseDtos) throws Exception
    {
        if("0".equals(message.getIsRestartRetry()))
        {
            return;
        }
        
        Message messageState = messageService.selectMessageById(
                message.getDatabaseId(), message.getCreateTime());
        if ("3".equals(messageState.getState()))
        {
            sender.send(baseDtos);
            // pool.removeMessage(this.message,"6");
        }
    }

    // 警告通知框架 ADD END

    // $Author :jin_peng
    // $Date : 2014/02/11
    // 对外应用服务 ADD BEGIN
    public void runInternalExternal(List<? extends BaseDto> baseDtos)
            throws Exception
    {
        if (!messageService.isExternalMessage(message.getServiceId()))
        {
            return;
        }

        Message messageState = messageService.selectMessageById(
                message.getDatabaseId(), message.getCreateTime());
        if ("0".equals(messageState.getExternalState()))
        {
            sender.sendExternal(baseDtos, message.getParamsExternal());

            logger.debug("data {} external service process successed.",
                    message.getUuid());
        }
    }

    // 对外应用服务 ADD END

    @Transactional
    public void backupErrorMessage(String reason)
    {
        try
        {
            pool.backupErrorMessage(message, reason);
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            logger.error("备份错误消息时异常：\r\n{}", ExceptionUtils.getStackTrace(e));
        }
    }

    private Message construct(Hl7Message message) throws Exception
    {
        String messageId = message.getMessageType();
        writer.setMessageId(messageId);
        writer.setTimes(message.getLongProperty(Hl7Message.HEADER_HANDLE_TIMES));
        writer.setTotaltime(message.getLongProperty(Hl7Message.HEADER_TOTAL_HANDLE_TIME));
        // $Author :wu_biao
        // $Date : 2013/03/13
        // 警告通知框架 ADD BEGIN
        // sender.setMessageId(messageId);
        // 警告通知框架 ADD END
        Message msg = new Message();
        msg.setId(message.getDatabaseId());
        msg.setVid(messageId);
        msg.setMsgMode(message.getMsgMode());
        msg.setBatchId(message.getQueueName());
        msg.setContent(message.getText());
        msg.setOrgCode(message.getHospitalCd());
        msg.setDatetime(new Date(message.getJMSTimestamp()));
        msg.setSourceSysCd(message.getSourceSysCd());
        // writeLog.setMessageId(messageId);
        // writeLog.setId(message.getDatabaseId().toString());

        // $Author:chunlin jing
        // $Date:2013/7/17
        // $[BUG]0034809 ADD BEGIN
        // 增加消息服务ID
        msg.setServiceId(message.getServiceId());
        msg.setCreateTime(DateUtils.stringToDate(message.getCreateTime(),
                DateUtils.PATTERN_MINUS_DATETIME));
        // $[BUG]0034809 ADD END
        msg.setOrderDispatchTypeCode(message.getOrderExecId());
        return msg;
    }
}
