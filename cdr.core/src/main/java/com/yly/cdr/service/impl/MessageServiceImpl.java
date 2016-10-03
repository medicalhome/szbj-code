package com.yly.cdr.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dao.MessageDao;
import com.yly.cdr.entity.Message;
import com.yly.cdr.entity.MessageFailure;
import com.yly.cdr.service.MessageService;

@Component
public class MessageServiceImpl implements MessageService
{

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private EntityDao entiryDao;

    /**
     * 保存从MQ获取的消息。
     * @param message
     * @return 返回影响的条数
     */
    @Transactional
    public int saveMessage(Message message)
    {
        return entiryDao.insert(message);
    }

    /**
     * 保存校验未通过的消息。
     * @param messageFailure
     * @return 返回影响的条数
     */
    @Transactional
    public int saveMessageFailure(MessageFailure messageFailure)
    {
        return entiryDao.insert(messageFailure);
    }

    /**
     * 查询失败的消息，每次读一条。
     * @return 
     */
    @Transactional
    public Message getMessage(String messageId)
    {
        return messageDao.getMessage(messageId);
    }

    @Transactional
    public Message selectMessageById(BigDecimal id, String createTime)
    {
        return messageDao.selectMessageById(id, createTime);
    }

    /**
     * 从本地删除执行完成的消息
     * @param message
     * @return 返回影响的条数
     */
    @Transactional
    public int deleteMessage(BigDecimal id, String createTime)
    {
        return messageDao.deleteMessage(id, createTime);
    }

    /**
     * 更新消息状态
     * @param message
     */
    @Transactional
    public int updateMessageState(BigDecimal id, String state, String createTime , String serviceId)
    {
        return messageDao.updateMessage(id, state, createTime, serviceId);
    }
    
    /**
     * 更新消息SERVICE_ID,BATCH_ID
     * @param message
     */
    @Transactional
    public int updateMessage(BigDecimal id,String serviceId,String batchId){
    	return messageDao.updateV2Message(id, serviceId, batchId);
    }

    /**
     * 更新消息对外应用服务状态
     * @param message
     */
    public int updateMessageExternalState(BigDecimal id, String externalState,
            String createTime)
    {
        return messageDao.updateExternalMessage(id, externalState, createTime);
    }

    /**
     * 执行完当前队列后，重置正在运行的消息，下次batch启动时运行。
     * @param messageId
     * @return
     */
    @Transactional
    public int updateMessageStateByVid(String messageId)
    {
        return messageDao.updateMessageStateByVid(messageId);
    }

    // $Author :wu_biao
    // $Date : 2013/03/13
    // 警告通知框架 ADD BEGIN
    @Override
    @Transactional
    public List<Message> getMessageList(String batchId, String state,
            String date)
    {
        return messageDao.getAllMessages(batchId, state, date);
    }

    // 警告通知框架 ADD END

    @Override
    public boolean isExternalMessage(String serviceId)
    {
        boolean isExternal = false;
        if(serviceId == null) return isExternal;
        //将需要开启对外应用服务的消息修改在配置文件中
        String externalMessageServiceId = Constants.EXTERNAL_MESSAGE_SERVICE_ID;
        
        if (externalMessageServiceId.contains(serviceId))
        {
            isExternal = true;
        }

        return isExternal;
    }
}
