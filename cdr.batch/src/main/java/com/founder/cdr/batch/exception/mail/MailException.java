package com.founder.cdr.batch.exception.mail;

import com.founder.cdr.batch.exception.UnexpectedException;

/**
 * 邮件服务异常
 * @author wen_ruichao
 */
public class MailException extends UnexpectedException
{

    private static String description = "邮件服务异常";

    public MailException(String msg)
    {
        super(description + msg);
    }

    public MailException(String msg, Throwable cause)
    {
        super(description + msg, cause);
    }
}
