package com.yly.cdr.hl7.dto.ms053;

import java.util.List;

import com.yly.cdr.hl7.dto.CheckItem;
import com.yly.cdr.hl7.dto.DrugName;
import com.yly.cdr.hl7.dto.MSBaseDto;

public class MS053 extends MSBaseDto<DrugName>
{
    /**
     * 消息类别代码
     */
    private String msgId;

    /**
     * 版本号
     */
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
     * 药品名称
     */
    private List<DrugName> drugName;

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

    public List<DrugName> getDrugName()
    {
        return drugName;
    }

    public String getMessageId()
    {
        return msgId;
    }

    public List<DrugName> getMesageRecords()
    {
        return drugName;
    }

    public String getMessageVersionNo()
    {
        return version;
    }
}
