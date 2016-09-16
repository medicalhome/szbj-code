package com.founder.cdr.batch.exception.message;

import com.founder.cdr.batch.exception.ExpectedException;
//$Author :jin_peng
//$Date : 2014/02/17 
//对外应用服务 ADD BEGIN
/**
 * 消息业务异常。 
 * @author jin_peng
 */
public class MessageExternalSendNoWarningException extends ExpectedException
{

    private static String description = "消息调用对外应用服务检验报告无预警内容";

    public MessageExternalSendNoWarningException(String msg)
    {
        super(description + msg);
    }

    public MessageExternalSendNoWarningException(String msg, Throwable cause)
    {
        super(description + msg, cause);
    }
}
//对外应用服务 ADD END
