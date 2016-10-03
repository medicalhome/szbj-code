package com.yly.cdr.batch.exception.message;

import com.yly.cdr.batch.exception.ExpectedException;
//$Author :jin_peng
// $Date : 2014/02/17 
// 对外应用服务 ADD BEGIN
/**
 * 消息业务异常。 
 * @author jin_peng
 */
public class MessageExternalDuplicateValidationException extends ExpectedException
{

    private static String description = "消息调用对外应用服务唯一性约束验证";

    public MessageExternalDuplicateValidationException(String msg)
    {
        super(description + msg);
    }

    public MessageExternalDuplicateValidationException(String msg, Throwable cause)
    {
        super(description + msg, cause);
    }
}
//对外应用服务 ADD END
