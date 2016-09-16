package com.founder.cdr.web.mq.hl7.dto;

public class HeadContent
{
    private String msgId;
    
    private String msgName;
    
    private String sourceSysCode;
    
    private String targetSysCode;
    
    private String createTime;

    public String getMsgId()
    {
        return msgId;
    }

    public void setMsgId(String msgId)
    {
        this.msgId = msgId;
    }

    public String getMsgName()
    {
        return msgName;
    }

    public void setMsgName(String msgName)
    {
        this.msgName = msgName;
    }

    public String getSourceSysCode()
    {
        return sourceSysCode;
    }

    public void setSourceSysCode(String sourceSysCode)
    {
        this.sourceSysCode = sourceSysCode;
    }

    public String getTargetSysCode()
    {
        return targetSysCode;
    }

    public void setTargetSysCode(String targetSysCode)
    {
        this.targetSysCode = targetSysCode;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
}
