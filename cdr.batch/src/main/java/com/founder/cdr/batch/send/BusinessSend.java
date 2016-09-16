package com.founder.cdr.batch.send;
//$Author :wu_biao
//$Date : 2013/03/13 
//警告通知框架 ADD BEGIN
/**
 * 业务消息Send口
 * 
 * @author wu_biao
 */
public interface BusinessSend<T>
{
    /**
     * send
     * 
     * @param t
     * @return true:是；false:否。
     * @throws Exception 
     */
    public void send(T t) throws Exception;
}
//警告通知框架 ADD END
