package com.founder.cdr.hl7.dto.ms004;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.MSBaseDto;
import com.founder.cdr.hl7.dto.Operation;

public class MS004 extends MSBaseDto<Operation>
{
    /**
     * 消息类别代码
     */
    @NotEmpty(message = "{MS004.msgId}")
    private String msgId;

    /**
     * 版本号
     */
//    @NotEmpty(message = "{MS004.version}")
    private String version;

    /**
     * 消息名称 
     */
    private String msgName;

    /**
     * 消息源系统编码
     */
    private String sourceSysCode;

    /**
     * 消息目标系统编码
     */
    private String targetSysCode;

    /**
     * 消息创建时间
     */
    private String createTime;

    /**
     * 字典表值
     */
    @NotEmpty(message = "{MS004.operation}")
    private List<Operation> operation;

    public String getMsgId()
    {
        return msgId;
    }

    public String getVersion()
    {
        return version;
    }

    public String getMsgName()
    {
        return msgName;
    }

    public String getSourceSysCode()
    {
        return sourceSysCode;
    }

    public String getTargetSysCode()
    {
        return targetSysCode;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public List<Operation> getOperation()
    {
        return operation;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder("{\n");
        sb.append("msgId:").append(getMsgId()).append("\n");
        sb.append(":").append(getMsgId()).append("\n");
        sb.append("}");

        return sb.toString();
    }

    public String getMessageId()
    {
        return msgId;
    }
    
    public List<Operation> getMesageRecords()
    {
        return operation;
    }

    public String getMessageVersionNo()
    {
        return version;
    }

}
