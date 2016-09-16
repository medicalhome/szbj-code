package com.founder.cdr.wmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

import com.founder.cdr.util.ExceptionUtils;
import com.founder.cdr.wmq.WMQQueueFactory;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;

@Component
public class WMQTransactionManager extends AbstractPlatformTransactionManager
{
    private static final long serialVersionUID = -7542332819205971775L;

    private static Logger logger = LoggerFactory.getLogger(WMQTransactionManager.class);

    @Autowired
    private WMQQueueFactory queueFactory;

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition)
            throws TransactionException
    {
        // WMQTransactionObject obj = (WMQTransactionObject) transaction;
        // MQQueueManager queueManager = obj.getQueueManager();
        // int timeout = determineTimeout(definition);
        // try
        // {
        // queueManager.begin();
        // }
        // catch (MQException e)
        // {
        // e.printStackTrace();
        // throw new WMQTransactionException("WebSphere Message Queue事务启动失败。",
        // e);
        // }
    }

    @Override
    protected void doCommit(DefaultTransactionStatus status)
            throws TransactionException
    {
        WMQTransactionObject txObject = (WMQTransactionObject) status.getTransaction();
        MQQueueManager queueManager = txObject.getQueueManager();
        try
        {
            queueManager.commit();
        }
        catch (MQException e)
        {
            // e.printStackTrace();
            logger.error("提交MQ事务异常：\r\n{}", ExceptionUtils.getStackTrace(e));
            throw new WMQTransactionException("WebSphere Message Queue事务提交失败。",
                    e);
        }
    }

    @Override
    protected Object doGetTransaction() throws TransactionException
    {
        WMQTransactionObject txObject = new WMQTransactionObject();
        try
        {   
            txObject.setQueueManager(queueFactory.getQueueManager());
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            logger.error("开始MQ事务异常：\r\n{}", ExceptionUtils.getStackTrace(e));
        }
        return txObject;
    }

    @Override
    protected void doRollback(DefaultTransactionStatus status)
            throws TransactionException
    {
        WMQTransactionObject txObject = (WMQTransactionObject) status.getTransaction();
        MQQueueManager queueManager = txObject.getQueueManager();
        try
        {
            queueManager.backout();
        }
        catch (MQException e)
        {
            // e.printStackTrace();
            logger.error("MQ事务回滚异常：\r\n{}", ExceptionUtils.getStackTrace(e));
            throw new WMQTransactionException("WebSphere Message Queue事务回滚失败。",
                    e);
        }
    }

}
