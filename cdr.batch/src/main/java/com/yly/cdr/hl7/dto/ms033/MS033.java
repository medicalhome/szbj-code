package com.yly.cdr.hl7.dto.ms033;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.LabItem;
import com.yly.cdr.hl7.dto.MSBaseDto;

public class MS033 extends MSBaseDto<LabItem>
{
    /**
     * 消息类别代码
     */
    @NotEmpty(message = "{MS033.msgId}")
    private String msgId;

    /**
     * 版本号
     */
//    @NotEmpty(message = "{MS033.version}")
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
     * 检验项目
     */
    @NotEmpty(message = "{MS033.labItems}")
    private List<LabItem> labItems;

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

    public List<LabItem> getLabItems()
    {
        return labItems;
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

    public List<LabItem> getMesageRecords()
    {
        return labItems;
    }

    public String getMessageVersionNo()
    {
        return version;
    }
}
