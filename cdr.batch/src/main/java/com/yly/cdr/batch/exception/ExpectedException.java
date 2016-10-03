package com.yly.cdr.batch.exception;

/**
 * 下列异常，终止处理当前消息，继续处理下一个消息。
 * @author wen_ruichao
 * @see com.yly.cdr.batch.exception.message.MessageStructureException
 * @see com.yly.cdr.batch.exception.message.MessageProcessException
 * @see com.yly.cdr.batch.exception.message.MessageBusinessException
 * @see com.yly.cdr.batch.exception.message.MessageIntegrityException
 */
public abstract class ExpectedException extends CDRBatchException
{

    public ExpectedException(String msg)
    {
        super(msg);
    }

    public ExpectedException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
