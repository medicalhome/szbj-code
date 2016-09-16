package com.founder.cdr.batch;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.PropertyDefiner;
import ch.qos.logback.core.status.Status;

public class QueueNamePropertyDefiner implements PropertyDefiner
{
    private Context context;

    private static String queueName;

    public void setContext(Context context)
    {
        this.context = context;
    }

    public Context getContext()
    {
        return this.context;
    }

    public void addStatus(Status status)
    {

    }

    public void addInfo(String msg)
    {

    }

    public void addInfo(String msg, Throwable ex)
    {

    }

    public void addWarn(String msg)
    {

    }

    public void addWarn(String msg, Throwable ex)
    {

    }

    public void addError(String msg)
    {

    }

    public void addError(String msg, Throwable ex)
    {

    }

    @Override
    public String getPropertyValue()
    {
        return QueueNamePropertyDefiner.queueName;
    }

    public static void setQueueName(String queueName)
    {
        QueueNamePropertyDefiner.queueName = queueName;
    }
}
