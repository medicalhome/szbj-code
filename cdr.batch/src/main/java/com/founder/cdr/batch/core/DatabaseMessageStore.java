package com.founder.cdr.batch.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.Message;
import com.founder.cdr.entity.MessageFailure;
import com.founder.cdr.service.MessageService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;

public class DatabaseMessageStore implements MessageStore
{
    @Autowired
    private MessageService messageService;

    protected static Logger logger = LoggerFactory.getLogger(DatabaseMessageStore.class);

    // $Author :wu_biao
    // $Date : 2013/03/13
    // 警告通知框架 ADD BEGIN
    @Override
    public List<Hl7Message> retrieveMessages(String batchId, String state,
            String date)
    {
        List<Message> list = messageService.getMessageList(batchId, state, date);
        // 警告通知框架 ADD END
        if (list == null || list.size() == 0)
            return null;

        List<Hl7Message> result = new ArrayList<Hl7Message>();
        for (Message msg : list)
        {
            Hl7Message message = new Hl7Message();
            // message.setServiceId(null);
            message.setMessageType(msg.getVid());
            message.setContent(msg.getContent());
            message.setUuid(msg.getId().toString());
            message.setDatabaseId(msg.getId());
            message.setCreateTime(DateUtils.dateToString(msg.getCreateTime(),
                    DateUtils.PATTERN_MINUS_DATETIME));
            message.setQueueName(msg.getBatchId());
            // $Author :wu_biao
            // $Date : 2013/03/04 14:44$
            // [BUG]14248 ADD BEGIN
            message.setServiceId(msg.getServiceId());
            message.setDomainId(msg.getDomainId());
            message.setOrderDispatchType(msg.getOrderDispatchTypeCode());
            message.setExecUnitId(msg.getExecUnitId());
            message.setHospitalCd(StringUtils.isEmpty(msg.getOrgCode()) ? Constants.HOSPITAL_CODE
                    : msg.getOrgCode());
            message.setSourceSysCd(msg.getSourceSysCd());
            message.setMsgMode(msg.getMsgMode());
            // [BUG]14248 ADD END
            Date temp = msg.getDatetime();
            try
            {
                message.setJMSTimestamp(temp.getTime());
            }
            catch (JMSException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            result.add(message);
        }

        return result;
    }

    @Transactional
    @Override
    public void saveError(Hl7Message message, String reason)
    {
        // $Author :wu_biao
        // $Date : 2013/02/25 14:44$
        // [BUG]13879 ADD BEGIN
        MessageFailure messageFailure = new MessageFailure();
        messageFailure.setId(message.getDatabaseId());
        messageFailure.setVid(message.getMessageType());
        messageFailure.setContent(message.getContent());
        try
        {
            messageFailure.setDatetime(new Date(message.getJMSTimestamp()));
        }
        catch (JMSException e)
        {
            e.printStackTrace();
        }
        messageFailure.setCreateTime(new Date());
        messageFailure.setReason(reason);
        messageFailure.setFlag(Constants.FLAG_DATA_INTEGRITY);
        // $Author :wu_biao
        // $Date : 2013/03/06 14:44$
        // [BUG]14248 ADD BEGIN
        messageFailure.setServiceId(message.getServiceId());
        messageFailure.setDomainId(message.getDomainId());
        messageFailure.setExecUnitId(message.getExecUnitId());
        messageFailure.setOrderDispatchTypeCode(message.getOrderDispatchType());
        messageFailure.setOrgCode(StringUtils.isEmpty(message.getHospitalCd()) ? Constants.HOSPITAL_CODE
                : message.getHospitalCd());
        messageFailure.setSourceSysCd(message.getSourceSysCd());
        // [BUG]14248 ADD END
        // $Author :wu_biao
        // $Date : 2013/09/26 14:44$
        // [BUG]37645 ADD BEGIN
        messageFailure.setBatchId(message.getQueueName());
        // [BUG]37645 ADD END
        messageFailure.setMsgId(message.getMsgId());
        // $Author :jin_peng
        // $Date : 2014/02/11
        // 对外应用服务 ADD BEGIN
        if (messageService.isExternalMessage(message.getServiceId()))
        {
            Message messageState = messageService.selectMessageById(
                    message.getDatabaseId(), message.getCreateTime());

            if (messageState != null)
            {
                messageFailure.setExternalState(messageState.getExternalState());
            }
        }
        // 对外应用服务 ADD END

        messageService.saveMessageFailure(messageFailure);
        // Message
        // msgtemp=messageService.selectMessageById(message.getDatabaseId());
        // if(msgtemp==null)
        // {
        // sleep();
        // logger.debug("message is null,id={}",message.getDatabaseId());
        // }
        // Message msg = new Message();
        messageService.deleteMessage(message.getDatabaseId(),
                message.getCreateTime());
        // [BUG]13879 ADD END
    }

    @Override
    public void saveMessage(Hl7Message message) throws Exception
    {
        Message msg = new Message();
        msg.setContent(message.getContent());
        msg.setState("0");
        msg.setVid(message.getMessageType());
        try
        {
            msg.setDatetime(new Date(message.getJMSTimestamp()));
            msg.setBatchId(message.getQueueName());
            msg.setMsgMode(message.getMsgMode());
            msg.setCreateTime(new Date());
            // $Author :wu_biao
            // $Date : 2013/03/04 14:44$
            // [BUG]14248 ADD BEGIN
            msg.setServiceId(message.getServiceId());
            msg.setDomainId(message.getDomainId());
//            msg.setOrderDispatchTypeCode(message.getOrderDispatchType());
            msg.setOrderDispatchTypeCode(message.getOrderExecId());
            msg.setExecUnitId(message.getExecUnitId());
            msg.setOrgCode(StringUtils.isEmpty(message.getHospitalCd()) ? Constants.HOSPITAL_CODE
                    : message.getHospitalCd());
            msg.setSourceSysCd(message.getSourceSysCd());
            msg.setMsgId(message.getMsgId());
            // [BUG]14248 ADD END
            // $Author :jin_peng
            // $Date : 2014/02/11
            // 对外应用服务 ADD BEGIN
            if (messageService.isExternalMessage(message.getServiceId()))
            {
                msg.setExternalState("0");
            }
            msg.setMsgMode(message.getMsgMode());
            // 对外应用服务 ADD END
            this.messageService.saveMessage(msg);
            message.setDatabaseId(msg.getId());
            message.setUuid(msg.getId().toString());
            message.setCreateTime(DateUtils.dateToString(msg.getCreateTime(),
                    DateUtils.PATTERN_MINUS_DATETIME));
        }
        catch (JMSException e)
        {
            e.printStackTrace();
            // wubiao BUG14649 test
            logger.error("insert message error!原因：{}", e.getMessage());
            // wubiao BUG14649 test
        }
    }

    @Override
    public void removeMessage(Hl7Message message, String flag)
    {
        Message msg = new Message();
        BigDecimal id = message.getDatabaseId();
        messageService.updateMessageState(id, flag,
                message.getCreateTime().toString(),message.getServiceId());
        // this.messageService.deleteMessage(msg);
    }

    @Override
    public void removeMessageExternal(Hl7Message message, String flag)
    {
        BigDecimal id = message.getDatabaseId();
        messageService.updateMessageExternalState(id, flag,
                message.getCreateTime().toString());
    }

    private void sleep()
    {
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException ie)
        {
            ie.printStackTrace();
        }
    }

}
