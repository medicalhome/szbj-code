package com.yly.cdr.hl7.dto.ms031;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.ChargeItem;
import com.yly.cdr.hl7.dto.MSBaseDto;

public class MS031 extends MSBaseDto<ChargeItem>
{
    /**
     * 消息类别代码
     */
    @NotEmpty(message = "{MS031.msgId}")
    private String msgId;
    /**
     * 版本号
     */
//    @NotEmpty(message = "{MS031.version}")
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
     * 收费项目
     */
    @NotEmpty(message = "{MS031.ChargeItems}")
    private List<ChargeItem> ChargeItems;

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

    public List<ChargeItem> getChargeItems()
    {
        return ChargeItems;
    }
    
    public String getMessageId()
    {
        return msgId;
    }

    public List<ChargeItem> getMesageRecords()
    {
        return ChargeItems;
    }

    public String getMessageVersionNo()
    {
        return version;
    }
}
