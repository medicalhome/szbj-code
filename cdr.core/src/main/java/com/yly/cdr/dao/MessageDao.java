package com.yly.cdr.dao;

import java.math.BigDecimal;
import java.util.List;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.yly.cdr.entity.Message;

public interface MessageDao
{

    /**
     * 查询失败的消息，每次读一条。
     * @return
     */
    @Arguments({ "messageId" })
    public Message getMessage(String messageId);

    /**
     * 执行完当前队列后，重置正在运行的消息，下次batch启动时运行。
     * @param messageId
     * @return
     */
    @Arguments({ "messageId" })
    public int updateMessageStateByVid(String messageId);

    // $Author :wu_biao
    // $Date : 2013/03/13
    // 警告通知框架 ADD BEGIN
    @Arguments({ "batchId", "state", "dates" })
    public List<Message> getAllMessages(String batchId, String state,
            String dates);

    // 警告通知框架 ADD END

    @Arguments({ "id", "state", "createTime", "serviceId" })
    public int updateMessage(BigDecimal id, String state, String createTime, String serviceId);
    
    @Arguments({ "id", "serviceId", "batchId" })
    public int updateV2Message(BigDecimal id, String serviceId, String batchId);

    @Arguments({ "id", "externalState", "createTime" })
    public int updateExternalMessage(BigDecimal id, String externalState,
            String createTime);

    @Arguments({ "id", "createTime" })
    public Message selectMessageById(BigDecimal id, String createTime);

    @Arguments({ "id", "createTime" })
    public int deleteMessage(BigDecimal id, String createTime);
}
