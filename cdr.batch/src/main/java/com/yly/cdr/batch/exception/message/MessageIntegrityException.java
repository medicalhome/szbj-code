package com.yly.cdr.batch.exception.message;

import com.yly.cdr.batch.exception.ExpectedException;

/**
 * 消息完整性异常。 
 * @author wen_ruichao
 */
public class MessageIntegrityException extends ExpectedException
{

    private static String description = "消息完整性异常";

    public MessageIntegrityException(String msg)
    {
        super(description + msg);
    }

    public MessageIntegrityException(String msg, Throwable cause)
    {
        super(description + msg, cause);
    }
}
