package com.yly.cdr.batch.exception.mq;

import com.yly.cdr.batch.exception.UnexpectedException;

/**
 * MQ服务异常
 * @author wen_ruichao
 */
public class MQException extends UnexpectedException
{

    private static String description = "MQ服务异常";

    public MQException(String msg)
    {
        super(description + msg);
    }

    public MQException(String msg, Throwable cause)
    {
        super(description + msg, cause);
    }
}
