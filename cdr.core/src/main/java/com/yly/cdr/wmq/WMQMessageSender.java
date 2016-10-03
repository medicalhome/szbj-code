package com.yly.cdr.wmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPropertyDescriptor;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.MQConstants;
import com.yly.cdr.util.ExceptionUtils;
import com.yly.cdr.wmq.Hl7MessageWmq;

@Component
public class WMQMessageSender
{
    protected static Logger logger = LoggerFactory.getLogger(WMQMessageSender.class);

    private int openOptions;

    private MQPutMessageOptions pmo;

    @Autowired
    private WMQQueueFactory queueFactory;

    public WMQMessageSender()
    {
        initQueueOption();
    }

    private void initQueueOption()
    {
        // Set the put message options.（设置放置消息选项）
        pmo = new MQPutMessageOptions();
        // Put messages under sync point control
        // 在同步点控制下发送消息
        pmo.options = pmo.options + MQConstants.MQGMO_SYNCPOINT;

        // Fail if QueueManager Quiescing
        // 如果队列管理器停顿则失败
        pmo.options = pmo.options + MQConstants.MQGMO_FAIL_IF_QUIESCING;

        openOptions = MQConstants.MQOO_BIND_AS_Q_DEF;
        openOptions = openOptions + MQConstants.MQOO_OUTPUT;
        openOptions = openOptions + MQConstants.MQPMO_PASS_ALL_CONTEXT;
    }

    public void sendMessage(Hl7MessageWmq hl7message, String queueName)
            throws Exception
    {
        MQQueue queue = null;
        MQMessage msg = new MQMessage();
        msg.format = MQConstants.MQFMT_STRING;
        MQPropertyDescriptor descriptor = new MQPropertyDescriptor();
        descriptor.context = CMQC.MQPD_USER_CONTEXT;
        descriptor.support = CMQC.MQPD_SUPPORT_REQUIRED;
        msg.setStringProperty(Hl7MessageWmq.HEADER_SERVICE_ID, descriptor,
                hl7message.getServiceId());
        msg.setStringProperty(Hl7MessageWmq.HEADER_DOMAIN_ID, descriptor,
                hl7message.getDomainId());
        msg.setStringProperty(Hl7MessageWmq.HEADER_EXEC_UNIT, descriptor,
                hl7message.getExecUnitId());
        msg.setStringProperty(Hl7MessageWmq.HEADER_ORDER_DISPATCH_TYPE,
                descriptor, hl7message.getOrderDispatchType());
        msg.setStringProperty(Hl7MessageWmq.HEADER_HOSPITAL_CD,
                descriptor, hl7message.getHospitalCd());
        msg.setStringProperty(Hl7MessageWmq.HEADER_SOURCE_SYS_CD,
                descriptor, "S015");
        msg.setStringProperty(Hl7MessageWmq.ORDER_EXEC_ID,descriptor,
        		hl7message.getOrderExecId().toString());
        msg.setStringProperty(Hl7MessageWmq.EXTEND_SUB_ID,descriptor,
        		hl7message.getExtendSubId().toString());
        msg.setStringProperty(Hl7MessageWmq.APPLY_UNIT_ID,descriptor,
        		hl7message.getApplyUnitId().toString());
        msg.setStringProperty(Hl7MessageWmq.PROPERTY_ID, descriptor,
                hl7message.getDatabaseId().toString());
        msg.replyToQueueManagerName = hl7message.getQueueName();

        msg.write(hl7message.getContent().getBytes("UTF-8"));
        try
        {
            queue = queueFactory.getMQQueue(queueName, openOptions);
            queue.put(msg, pmo);
            MQQueueManager manager = queue.getConnectionReference();
            manager.commit();
            logger.debug("消息发送成功！");
        }
        catch (MQException e)
        {
            if (e.reasonCode == MQConstants.MQRC_CHANNEL_NOT_AVAILABLE
                || e.reasonCode == MQConstants.MQRC_CONNECTION_BROKEN
                || e.reasonCode == MQConstants.MQRC_CONNECTION_ERROR
                || e.reasonCode == MQConstants.MQRC_CONNECTION_STOPPED
                || e.reasonCode == MQConstants.MQRC_Q_MGR_QUIESCING)
            {
                queueFactory.setReconnect();
                logger.error("队列管理器不可用，需要重新创建队列管理器!");
            }
            else
                throw e;
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            logger.error("发送消息{}异常：\r\n{}", hl7message.getDatabaseId().toString(),ExceptionUtils.getStackTrace(e));
        }
        finally
        {
            queue.close();
        }
    }

    public void sendMessageTo(Hl7MessageWmq hl7message, String queueName)
            throws Exception
    {
        MQQueue queue = null;
        MQMessage msg = new MQMessage();
        msg.format = MQConstants.MQFMT_STRING;
        MQPropertyDescriptor descriptor = new MQPropertyDescriptor();
        descriptor.context = CMQC.MQPD_USER_CONTEXT;
        descriptor.support = CMQC.MQPD_SUPPORT_REQUIRED;
        msg.setStringProperty(Hl7MessageWmq.HEADER_SERVICE_ID, descriptor,
                hl7message.getServiceId());
        msg.setStringProperty(Hl7MessageWmq.HEADER_DOMAIN_ID, descriptor,
                hl7message.getDomainId());
        msg.setStringProperty(Hl7MessageWmq.HEADER_EXEC_UNIT, descriptor,
                hl7message.getExecUnitId());
        msg.setStringProperty(Hl7MessageWmq.HEADER_ORDER_DISPATCH_TYPE,
                descriptor, hl7message.getOrderDispatchType());
        msg.setStringProperty(Hl7MessageWmq.HEADER_HOSPITAL_CD,
                descriptor, hl7message.getHospitalCd());
        msg.setStringProperty(Hl7MessageWmq.HEADER_SOURCE_SYS_CD,
                descriptor, hl7message.getSourceSysCd());
        /*
         * msg.setStringProperty(Hl7Message.PROPERTY_ID, descriptor,
         * hl7message.getDatabaseId().toString()); msg.replyToQueueManagerName =
         * hl7message.getQueueName();
         */
        
        msg.replyToQueueManagerName = hl7message.getQueueName();

        msg.setStringProperty(Hl7MessageWmq.TARGET_SYSTEM_CODE, descriptor,
                hl7message.getTargetSystemCode());

        msg.write(hl7message.getContent().getBytes("UTF-8"));

        try
        {
            queue = queueFactory.getMQQueue(queueName, openOptions);
            queue.put(msg, pmo);
            MQQueueManager manager = queue.getConnectionReference();
            manager.commit();
            logger.debug("消息发送成功！");
        }
        catch (MQException e)
        {
            if (e.reasonCode == MQConstants.MQRC_CHANNEL_NOT_AVAILABLE
                || e.reasonCode == MQConstants.MQRC_CONNECTION_BROKEN
                || e.reasonCode == MQConstants.MQRC_CONNECTION_ERROR
                || e.reasonCode == MQConstants.MQRC_CONNECTION_STOPPED
                || e.reasonCode == MQConstants.MQRC_Q_MGR_QUIESCING)
            {
                queueFactory.setReconnect();
                logger.error("队列管理器不可用，需要重新创建队列管理器!");
            }
            else
                throw e;
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            logger.error("发送消息异常：\r\n{}", ExceptionUtils.getStackTrace(e));
        }
        finally
        {
            queue.close();
        }
    }
    
    public void sendMessageAuditTo(Hl7MessageWmq hl7message, String queueName)
            throws Exception
    {
        MQQueue queue = null;
        MQMessage msg = new MQMessage();
        msg.format = MQConstants.MQFMT_STRING;
        MQPropertyDescriptor descriptor = new MQPropertyDescriptor();
        descriptor.context = CMQC.MQPD_USER_CONTEXT;
        descriptor.support = CMQC.MQPD_SUPPORT_REQUIRED;
        msg.setStringProperty(Hl7MessageWmq.HEADER_SERVICE_ID, descriptor,
                hl7message.getServiceId());
        msg.setStringProperty(Hl7MessageWmq.HEADER_DOMAIN_ID, descriptor,
                hl7message.getDomainId());
        msg.setStringProperty(Hl7MessageWmq.HEADER_EXEC_UNIT, descriptor,
                hl7message.getExecUnitId());
        msg.setStringProperty(Hl7MessageWmq.HEADER_ORDER_DISPATCH_TYPE,
                descriptor, hl7message.getOrderDispatchType());
        msg.setStringProperty(Hl7MessageWmq.HEADER_HOSPITAL_CD,
                descriptor, hl7message.getHospitalCd());
        msg.setStringProperty(Hl7MessageWmq.HEADER_SOURCE_SYS_CD,
                descriptor, hl7message.getSourceSysCd());
        
        msg.replyToQueueManagerName = hl7message.getQueueName();

        msg.write(hl7message.getContent().getBytes("UTF-8"));

        try
        {
            queue = queueFactory.getMQQueue(queueName, openOptions);
            queue.put(msg, pmo);
            MQQueueManager manager = queue.getConnectionReference();
            manager.commit();
            logger.debug("消息发送成功！");
        }
        catch (MQException e)
        {
            if (e.reasonCode == MQConstants.MQRC_CHANNEL_NOT_AVAILABLE
                || e.reasonCode == MQConstants.MQRC_CONNECTION_BROKEN
                || e.reasonCode == MQConstants.MQRC_CONNECTION_ERROR
                || e.reasonCode == MQConstants.MQRC_CONNECTION_STOPPED
                || e.reasonCode == MQConstants.MQRC_Q_MGR_QUIESCING)
            {
                queueFactory.setReconnect();
                logger.error("队列管理器不可用，需要重新创建队列管理器!");
            }
            else
                throw e;
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            logger.error("发送消息异常：\r\n{}", ExceptionUtils.getStackTrace(e));
        }
        finally
        {
            queue.close();
        }
    }

    public WMQQueueFactory getQueueFactory()
    {
        return queueFactory;
    }

    public void setQueueFactory(WMQQueueFactory queueFactory)
    {
        this.queueFactory = queueFactory;
    }
}
