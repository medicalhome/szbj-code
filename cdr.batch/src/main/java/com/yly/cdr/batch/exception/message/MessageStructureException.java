package com.yly.cdr.batch.exception.message;

import com.yly.cdr.batch.exception.ExpectedException;

/**
 * 消息结构异常
 * @author wen_ruichao
 */
public class MessageStructureException extends ExpectedException
{

    private static String description = "消息结构异常";

    public MessageStructureException(String msg)
    {
        super(description + msg);
    }

    public MessageStructureException(String msg, Throwable cause)
    {
        super(description + msg, cause);
    }
}
