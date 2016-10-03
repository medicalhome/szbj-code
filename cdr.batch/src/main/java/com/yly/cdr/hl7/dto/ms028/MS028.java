package com.yly.cdr.hl7.dto.ms028;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.Drug;
import com.yly.cdr.hl7.dto.MSBaseDto;

public class MS028 extends MSBaseDto<Drug>
{
    /**
     * 消息类别代码
     */
    @NotEmpty(message = "{MS028.msgId}")
    private String msgId;

    /**
     * 版本号
     */
//    @NotEmpty(message = "{MS028.version}")
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
     * 药品字典
     */
    @NotEmpty(message = "{MS028.Drug}")
    private List<Drug> Drug;

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

    public List<Drug> getDrug()
    {
        return Drug;
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

    public List<Drug> getMesageRecords()
    {
        return Drug;
    }

    public String getMessageVersionNo()
    {
        return version;
    }
}
