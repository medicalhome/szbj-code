package com.founder.cdr.util.mq;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPropertyDescriptor;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.MQConstants;
import com.ibm.mq.headers.MQDataException;
import com.ibm.mq.headers.MQHeaderIterator;
import com.ibm.mq.headers.MQHeaderList;

/**
 * MQ帮助类。
 * 
 * @author wen_ruichao
 */
public class MQClient
{

    private static final Logger logger = LoggerFactory.getLogger(MQClient.class);

    private MQQueueManager queueManager;

    private MQQueue queue;

    public MQClient(MQSession mqSession, String qmName) throws MQException
    {
        MQEnvironment.hostname = mqSession.getHostname();
        MQEnvironment.channel = mqSession.getChannel();
        MQEnvironment.port = mqSession.getPort();
        MQEnvironment.CCSID = mqSession.getCcsid();
        queueManager = new MQQueueManager(qmName);
    }

    public int getQueueLength(String qName) throws MQException
    {
        logger.trace("getQueueLength()");
        int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF
            | MQConstants.MQOO_INQUIRE;
        queue = queueManager.accessQueue(qName, openOptions, null, null, null);
        return queue.getCurrentDepth();
    }

    /**
     * 发送消息
     * 
     * @param qName 队列名称
     * @param message 消息
     * @return
     * @throws MQException 
     * @throws IOException 
     */
    public void send(String qName, String message) throws MQException,
            IOException
    {
        logger.trace("send()");
        int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF
            | MQConstants.MQOO_OUTPUT | MQConstants.MQPMO_PASS_ALL_CONTEXT;
        queue = queueManager.accessQueue(qName, openOptions, null, null, null);
        MQMessage msg = new MQMessage();
        msg.format = MQConstants.MQFMT_STRING;
        MQPropertyDescriptor descriptor = new MQPropertyDescriptor();
        descriptor.context = CMQC.MQPD_USER_CONTEXT;
        descriptor.support = CMQC.MQPD_SUPPORT_REQUIRED;
        msg.setStringProperty("JMSType", descriptor, "POCDIN000040UV02");
        msg.setStringProperty("Root.MQMD.PutApplName", descriptor, "01");
        msg.setStringProperty("founder", descriptor, "01");
        msg.writeUTF(message);
        MQPutMessageOptions pmo = new MQPutMessageOptions();
        pmo.options = MQConstants.MQPMO_SYNCPOINT;

        queue.put(msg, pmo);
    }

    /**
     * 接受消息
     * @param qName 队列名称
     * @return 消息
     * @throws MQException
     * @throws IOException
     * @throws Exception 
     */
    public String receive(String qName) throws MQException, IOException,
            Exception
    {
        int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF
            | MQConstants.MQOO_INQUIRE;
        queue = queueManager.accessQueue(qName, openOptions, null, null, null);
        MQMessage msg = new MQMessage();
        MQGetMessageOptions gmo = new MQGetMessageOptions();
        gmo.options = MQConstants.MQGMO_SYNCPOINT_IF_PERSISTENT;
        queue.get(msg, gmo);

        // MQHeaderList headerList = new MQHeaderList (msg, false);
        // logger.trace("{}", headerList);

        String msgText = new MQHeaderIterator(msg).getBodyAsText();

        return msgText;
    }

    /**
     * 提交队列管理器，移除当前消息。
     * 
     * @param qName 队列名称
     * @throws MQException
     */
    public void commitQueueManager(String qName) throws MQException
    {
        logger.trace("commitQueueManager()");
        queueManager.commit();
    }

    /**
     * 关闭队列
     * 
     * @param qName 队列名称
     * @throws MQException
     */
    public void closeQueue(String qName) throws MQException
    {
        logger.trace("closeQueue()");
        queue.close();
    }

    /**
     * 先关闭队列，再断开队列管理器
     * 
     * @param qName 队列名称
     * @throws MQException
     */
    public void disconnectQueueManager(String qName) throws MQException
    {
        logger.trace("disconnectQueueManager()");
        queueManager.disconnect();
    }
}
