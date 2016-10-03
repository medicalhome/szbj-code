package com.yly.cdr.wmq;

import org.springframework.transaction.TransactionException;

public class WMQTransactionException extends TransactionException
{
    private static final long serialVersionUID = 6846295277696203502L;

    public WMQTransactionException(String msg)
    {
        super(msg);
    }

    public WMQTransactionException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
