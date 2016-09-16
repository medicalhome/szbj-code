package com.founder.cdr.batch.exception.message;

import com.founder.cdr.batch.exception.ExpectedException;

/**
 * 消息解析异常
 * @author wen_ruichao
 */
public class MessageProcessException extends ExpectedException
{

    private static String description = "消息解析异常";

    public MessageProcessException(String msg)
    {
        super(description + msg);
    }

    public MessageProcessException(String msg, Throwable cause)
    {
        super(description + msg, cause);
    }
}
