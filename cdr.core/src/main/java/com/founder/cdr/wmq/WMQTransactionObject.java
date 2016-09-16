package com.founder.cdr.wmq;

import com.ibm.mq.MQQueueManager;

public class WMQTransactionObject
{
    private MQQueueManager queueManager;

    public MQQueueManager getQueueManager()
    {
        return queueManager;
    }

    public void setQueueManager(MQQueueManager queueManager)
    {
        this.queueManager = queueManager;
    }
}
