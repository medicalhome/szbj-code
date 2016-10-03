package com.yly.cdr.hl7.dto.ms025;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.Dept;
import com.yly.cdr.hl7.dto.MSBaseDto;

public class MS025 extends MSBaseDto<Dept>
{
    /**
     * 消息类别代码
     */
    @NotEmpty(message = "{MS025.msgId}")
    private String msgId;

    /**
     * 版本号
     */
//    @NotEmpty(message = "{MS025.version}")
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
     * 科室字典
     */
    @NotEmpty(message = "{MS025.Dept}")
    private List<Dept> Dept;

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

    public List<Dept> getDept()
    {
        return Dept;
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

    public List<Dept> getMesageRecords()
    {
        return Dept;
    }

    public String getMessageVersionNo()
    {
        return version;
    }
}
