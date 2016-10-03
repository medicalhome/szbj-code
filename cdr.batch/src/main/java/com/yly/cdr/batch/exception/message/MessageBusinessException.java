package com.yly.cdr.batch.exception.message;

import com.yly.cdr.batch.exception.ExpectedException;

/**
 * 消息业务异常。 
 * @author wen_ruichao
 */
public class MessageBusinessException extends ExpectedException
{

    private static String description = "消息业务异常";

    public MessageBusinessException(String msg)
    {
        super(description + msg);
    }

    public MessageBusinessException(String msg, Throwable cause)
    {
        super(description + msg, cause);
    }
}
