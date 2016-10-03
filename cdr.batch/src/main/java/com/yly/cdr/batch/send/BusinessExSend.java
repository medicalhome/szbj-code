package com.yly.cdr.batch.send;

//$Author :jin_peng
//$Date : 2014/02/17 
//对外应用服务 ADD BEGIN
/**
 * 业务消息Send口
 * 
 * @author wu_biao
 */
public interface BusinessExSend<T>
{
    /**
     * 消息唯一性约束验证
     * @param t
     * @throws Exception 
     */
    public void sendValication(T t) throws Exception;

    /**
     * send
     * 
     * @param t
     * @throws Exception 
     */
    public void send(T t) throws Exception;
}
// 对外应用服务 ADD END
