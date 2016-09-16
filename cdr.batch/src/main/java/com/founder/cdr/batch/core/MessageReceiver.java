package com.founder.cdr.batch.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.founder.cdr.batch.wmq.WMQMessageReader;
import com.founder.cdr.util.ExceptionUtils;
import com.founder.cdr.wmq.WMQTransactionManager;
import com.founder.cdr.wmq.WMQTransactionStatus;

@Component
public class MessageReceiver extends Thread implements InitializingBean
{
    private final static Logger logger = LoggerFactory.getLogger(MessageReceiver.class);

    private boolean quit = false;

    @Autowired
    private MessagePool pool;

    @Autowired
    private WMQTransactionManager transactionManager;

    @Autowired
    private WMQMessageReader messageReader;

    private String queueName;

    private int maxErrorCount = 100;
    
    private @Value("${batch.receive.sleeptime}")
    long sleeptime = 100;

    @Override
    public void run()
    {
        logger.debug("start MessageReceiver");

        long lock = 0;
        int errorCount = 0;
        TransactionStatus status = null;
        while (!quit)
        {
            logger.debug("start MessageReceiver logic");
            try
            {
                lock = pool.purchaseResource();
                DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
                definition.setName("WQSeries-CDR");
                definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                status = transactionManager.getTransaction(definition);

                Hl7Message msg = messageReader.receive();
                if (quit)
                {
                    status.setRollbackOnly();
                }
                else
                {
                    if (msg != null)
                    {
                        if (msg.isShutdownCommand())
                            this.quit = true;
                        msg.setQueueName(queueName);
                        pool.addMessage(msg);
                    }

                    errorCount = 0;
                    if (status instanceof WMQTransactionStatus)
                        ((WMQTransactionStatus) status).setCompleted(true);
                }
            }
            catch (Exception e1)
            {
                // e1.printStackTrace();
                logger.error("接收消息异常：\r\n{}", ExceptionUtils.getStackTrace(e1));
                status.setRollbackOnly();
                errorCount++;
                if (maxErrorCount > 0 && errorCount > maxErrorCount)
                {
                    // $Author :wu_biao
                    // $Date : 2012/12/13 14:44$
                    // [BUG]12367 ADD BEGIN
                    //this.quit = true;
                    try
                    {
                        logger.error("连续错误次数超过{}次，暂停接受MQ消息，线程睡眠5分钟", maxErrorCount);
                        Thread.sleep(300000);
                        errorCount=0;
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    //logger.error("连续错误次数超过{}次，停止接受MQ消息。", maxErrorCount);
                    // [BUG]12367 ADD END
                }
            }
            attempCommit(status);
            if (lock > 0)
                pool.releaseResource(lock);
            try
            {
                Thread.sleep(sleeptime);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        logger.info("MessageReceiver停止。");
    }

    private void attempCommit(TransactionStatus status)
    {
        try
        {
            transactionManager.commit(status);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getQueueName()
    {
        return queueName;
    }

    public void setQueueName(String queueName)
    {
        this.queueName = queueName;
        this.messageReader.setQueueName(this.queueName);
    }

    public int getMaxErrorCount()
    {
        return maxErrorCount;
    }

    public void setMaxErrorCount(int maxErrorCount)
    {
        this.maxErrorCount = maxErrorCount;
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        this.messageReader.setQueueName(this.queueName);
    }

    public void terminate()
    {
        this.quit = true;
    }

    public long getSleeptime()
    {
        return sleeptime;
    }

    public void setSleeptime(long sleeptime)
    {
        this.sleeptime = sleeptime;
    }
    
    
}
