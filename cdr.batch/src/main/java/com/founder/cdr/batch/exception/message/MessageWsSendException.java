package com.founder.cdr.batch.exception.message;

import com.founder.cdr.batch.exception.ExpectedException;
//$Author :wu_biao
// $Date : 2013/03/13 
// 警告通知框架 ADD BEGIN
/**
 * 消息业务异常。 
 * @author wu_biao
 */
public class MessageWsSendException extends ExpectedException
{

    private static String description = "消息调用WS再发送消息";

    public MessageWsSendException(String msg)
    {
        super(description + msg);
    }

    public MessageWsSendException(String msg, Throwable cause)
    {
        super(description + msg, cause);
    }
}
//警告通知框架 ADD END
