package com.yly.cdr.batch.exception;

/**
 * CDR-Batch异常基类。包括期望的异常、非期望的异常
 * @author wen_ruichao
 * @see com.yly.cdr.batch.exception.ExpectedException
 * @see com.yly.cdr.batch.exception.UnexpectedException
 */
public abstract class CDRBatchException extends RuntimeException
{

    public CDRBatchException(String msg)
    {
        super(msg);
    }

    public CDRBatchException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
