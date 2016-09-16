package com.founder.cdr.hl7.dto;

import com.founder.cdr.entity.Message;

/**
 * Message和CDA的基类
 * @author wen_ruichao
 * @version 1.0
 */
public class BaseDto
{

    /**
     * 当前消息
     */
    private Message message;

    public Message getMessage()
    {
        return message;
    }

    public void setMessage(Message message)
    {
        this.message = message;
    }
}
