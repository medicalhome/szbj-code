package com.yly.cdr.batch.core;

import java.util.List;

public interface MessageStore
{

    //$Author :wu_biao
    // $Date : 2013/03/13 
    // 警告通知框架 ADD BEGIN
    List<Hl7Message> retrieveMessages(String queueName, String state,String date);
    // 警告通知框架 ADD END

    void saveError(Hl7Message message, String reason);
    //wubiao BUG14649 test
    void saveMessage(Hl7Message message) throws Exception;
    //wubiao BUG14649 test
    void removeMessage(Hl7Message message,String flag);
    //$Author :jin_peng
    // $Date : 2014/02/12 
    // 对外应用服务 ADD BEGIN
    void removeMessageExternal(Hl7Message message,String flag);
    // 对外应用服务 ADD END
}
