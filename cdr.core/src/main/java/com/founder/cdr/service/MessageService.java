package com.founder.cdr.service;

import java.math.BigDecimal;
import java.util.List;

import com.founder.cdr.entity.Message;
import com.founder.cdr.entity.MessageFailure;

public interface MessageService
{

    /**
     * 保存从MQ获取的消息。
     * @param message
     * @return 返回影响的条数
     */
    public int saveMessage(Message message);

    /**
     * 保存校验未通过的消息。
     * @param messageFailure
     * @return 返回影响的条数
     */
    public int saveMessageFailure(MessageFailure messageFailure);

    /**
     * 查询失败的消息，每次读一条。
     * @return
     */
    public Message getMessage(String messageId);

    /**
     * 从本地删除执行完成的消息
     * @param message
     * @return 返回影响的条数
     */
    public int deleteMessage(BigDecimal id,String createTime);

    /**
     * 更新消息状态
     * @param message
     */
    public int updateMessageState(BigDecimal id,String state,String createTime, String serviceId);
    
    
    /**
     * 更新消息VID,SERVICE_ID,BATCH_ID
     * @param message
     */
    public int updateMessage(BigDecimal id,String serviceId,String batchId);
    
    /**
     * 更新消息对外应用服务状态
     * @param message
     */
    public int updateMessageExternalState(BigDecimal id,String externalState,String createTime);

    /**
     * 执行完当前队列后，重置正在运行的消息，下次batch启动时运行。
     * @param messageId
     * @return
     */
    public int updateMessageStateByVid(String messageId);

    //$Author :wu_biao
    //$Date : 2013/03/13 
    //警告通知框架 ADD BEGIN
    public List<Message> getMessageList(String batchId,String state,String date);
    //警告通知框架 ADD END
    
    public Message selectMessageById(BigDecimal id,String createTime);
    
    public boolean isExternalMessage(String serviceId);
}
