package com.yly.cdr.batch.exception.message;

import com.yly.cdr.batch.exception.ExpectedException;
//$Author :jin_peng
//$Date : 2014/02/17 
//对外应用服务 ADD BEGIN
/**
 * 消息业务异常。 
 * @author jin_peng
 */
public class MessageExternalSendException extends ExpectedException
{

    private static String description = "消息调用对外应用服务再发送消息";

    public MessageExternalSendException(String msg)
    {
        super(description + msg);
    }

    public MessageExternalSendException(String msg, Throwable cause)
    {
        super(description + msg, cause);
    }
}
//对外应用服务 ADD END
