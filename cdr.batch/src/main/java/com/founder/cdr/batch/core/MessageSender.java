package com.founder.cdr.batch.core;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.founder.cdr.batch.exception.message.MessageExternalDuplicateValidationException;
import com.founder.cdr.batch.exception.message.MessageExternalSendException;
import com.founder.cdr.batch.exception.message.MessageExternalSendNoWarningException;
import com.founder.cdr.batch.exception.message.MessageWsSendException;
import com.founder.cdr.batch.send.BusinessExSend;
import com.founder.cdr.batch.send.BusinessSend;
import com.founder.cdr.entity.Message;
import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.service.MessageService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.ExceptionUtils;
import com.founder.cdr.util.PropertiesUtils;
import com.founder.cdr.wmq.Hl7MessageWmq;
import com.founder.cdr.wmq.WMQMessageSender;

@Component
public class MessageSender
{
    @Autowired
    private WMQMessageSender sender;

    @Autowired
    private MessageService messageService;

    protected static Logger logger = LoggerFactory.getLogger(WMQMessageSender.class);

    public void run(Hl7Message message)
    {
        // $Author :wu_biao
        // $Date : 2013/03/13
        // 警告通知框架 ADD BEGIN
        String queueName = PropertiesUtils.getValue("sendQueueName");
        // 警告通知框架 ADD END
        try
        {
            Hl7MessageWmq hl7MessageWmq = new Hl7MessageWmq();
            hl7MessageWmq.setServiceId(message.getServiceId());
            hl7MessageWmq.setDomainId(message.getDomainId());
            hl7MessageWmq.setDatabaseId(message.getDatabaseId());
            hl7MessageWmq.setExecUnitId(message.getExecUnitId());
            hl7MessageWmq.setOrderDispatchType(message.getOrderDispatchType());
            hl7MessageWmq.setHospitalCd(message.getHospitalCd());
            hl7MessageWmq.setSourceSysCd(message.getSourceSysCd());
            hl7MessageWmq.setOrderExecId(message.getOrderExecId());
            hl7MessageWmq.setApplyUnitId(message.getApplyUnitId());
            hl7MessageWmq.setExtendSubId(message.getExtendSubId());
            hl7MessageWmq.setQueueName(message.getQueueName());
            hl7MessageWmq.setContent(message.getContent());
            sender.sendMessage(hl7MessageWmq, queueName);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    // $Author :jin_peng
    // $Date : 2014/02/11
    // 对外应用服务 ADD BEGIN
    private static final String EXTERNAL_SEND_SUFFIX = "ExSend";

    // 对外应用服务 ADD END

    // $Author :wu_biao
    // $Date : 2013/03/13
    // 警告通知框架 ADD BEGIN
    private static final String DEFAULT_WRITER_SUFFIX = "Send";

    @Autowired
    private BeanFactory beanFactory;

    /**
     * 消息ID（根据消息ID，调用对应的业务Writer）
     */
    // private String messageId;
    //
    // public void setMessageId(String messageId)
    // {
    // this.messageId = messageId;
    // }

    public void send(List<? extends BaseDto> baseDtos) throws Exception
    {
        logger.trace("send()");
        for (BaseDto baseDto : baseDtos)
        {
            doSend(baseDto);
        }
    }

    // $Author :jin_peng
    // $Date : 2014/02/11
    // 对外应用服务 ADD BEGIN
    public void sendExternal(List<? extends BaseDto> baseDtos,
            String paramsExternal) throws Exception
    {
        logger.trace("sendExternal()");
        for (BaseDto baseDto : baseDtos)
        {
            doSendExternal(baseDto, paramsExternal);
        }
    }

    /**
     * 根据消息ID调用不同Writer
     * 
     * @param baseDto
     * @exception 抛出RuntimeException异常
     */
    @SuppressWarnings("unchecked")
    private void doSendExternal(BaseDto baseDto, String paramsExternal)
            throws Exception
    {
        String serviceId = baseDto.getMessage().getServiceId();

        if (messageService.isExternalMessage(serviceId))
        {
            Message msg = null;
            try
            {
                String beanId = baseDto.getMessage().getVid().toLowerCase()
                    + EXTERNAL_SEND_SUFFIX;
                BusinessExSend<BaseDto> businessMessageSend = (BusinessExSend<BaseDto>) beanFactory.getBean(beanId);

                msg = baseDto.getMessage();

                try
                {
                    if (!"1".equals(paramsExternal))
                    {
                        businessMessageSend.sendValication(baseDto);
                    }
                }
                catch (DuplicateKeyException ce)
                {
                    logger.error("对外应用服务消息{}唯一性约束异常", msg != null ? msg.getId()
                            : "");

                    if (msg != null)
                    {
                        try
                        {
                            messageService.updateMessageExternalState(
                                    msg.getId(), "7", DateUtils.dateToString(
                                            msg.getCreateTime(),
                                            DateUtils.PATTERN_MINUS_DATETIME));
                        }
                        catch (Exception e)
                        {
                            logger.error("对外应用服务调用Send消息唯一性约束存入DB状态异常:\r\n{}",
                                    ExceptionUtils.getStackTrace(e));
                            throw new MessageExternalDuplicateValidationException(
                                    serviceId, e);
                        }
                    }

                    return;
                }
                catch (Exception e)
                {
                    logger.error("对外应用服务调用SendValidation消息异常:\r\n{}",
                            ExceptionUtils.getStackTrace(e));

                    throw new MessageExternalDuplicateValidationException(
                            serviceId, e);
                }

                businessMessageSend.send(baseDto);
                // msg.setState("3");
                // msg.setUpdateTime(new Date());
                messageService.updateMessageExternalState(msg.getId(), "3",
                        DateUtils.dateToString(msg.getCreateTime(),
                                DateUtils.PATTERN_MINUS_DATETIME));
            }
            catch (MessageExternalDuplicateValidationException e)
            {
                throw new MessageExternalDuplicateValidationException(
                        serviceId, e);
            }
            catch (MessageExternalSendNoWarningException e)
            {
                try
                {
                    messageService.updateMessageExternalState(msg.getId(), "6",
                            DateUtils.dateToString(msg.getCreateTime(),
                                    DateUtils.PATTERN_MINUS_DATETIME));
                }
                catch (Exception ex)
                {
                    logger.error("对外应用服务调用Send消息异常:\r\n{}",
                            ExceptionUtils.getStackTrace(ex));
                    throw new MessageExternalSendException(serviceId, ex);
                }
            }
            catch (Exception e)
            {
            	//Author: yu_yzh
            	//Date: 2015/5/6
            	//[BUG]: 0055085 BEGIN
                try
                {
                	logger.error("对外应用服务调用Send消息异常:\r\n{}",
                            ExceptionUtils.getStackTrace(e));
                    messageService.updateMessageExternalState(msg.getId(), "5",
                            DateUtils.dateToString(msg.getCreateTime(),
                                    DateUtils.PATTERN_MINUS_DATETIME));
                } catch (Exception excep){
                	throw new MessageExternalSendException(serviceId, e);
                }
                //[BUG]: 0055085 END
            }
        }
    }

    // 对外应用服务 ADD END

    /**
     * 根据消息ID调用不同Writer
     * 
     * @param baseDto
     * @exception 抛出RuntimeException异常
     */
    @SuppressWarnings("unchecked")
    private void doSend(BaseDto baseDto) throws Exception
    {

        if ("BS319".equals(baseDto.getMessage().getServiceId()))
        {
            String beanId = baseDto.getMessage().getVid().toLowerCase()
                + DEFAULT_WRITER_SUFFIX;
            BusinessSend<BaseDto> businessMessageSend = (BusinessSend<BaseDto>) beanFactory.getBean(beanId);
            try
            {
                businessMessageSend.send(baseDto);
                Message msg = baseDto.getMessage();
                msg.setState("6");
                msg.setUpdateTime(new Date());
                messageService.updateMessageState(msg.getId(), "6",
                        DateUtils.dateToString(msg.getCreateTime(),
                                DateUtils.PATTERN_MINUS_DATETIME),msg.getServiceId());
            }
            catch (Exception e)
            {
                logger.error("Ws调用Send消息异常:\r\n{}",
                        ExceptionUtils.getStackTrace(e));
                throw new MessageWsSendException("BS319", e);
            }
            ;
        }
    }
    // 警告通知框架 ADD END
}
