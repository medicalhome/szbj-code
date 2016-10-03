package com.yly.cdr.batch.exception.database;

import com.yly.cdr.batch.exception.UnexpectedException;

/**
 * 数据库异常
 * @author wen_ruichao
 */
public class DatabaseException extends UnexpectedException
{

    private static String description = "数据库异常";

    public DatabaseException(String msg)
    {
        super(description + msg);
    }

    public DatabaseException(String msg, Throwable cause)
    {
        super(description + msg, cause);
    }
}
