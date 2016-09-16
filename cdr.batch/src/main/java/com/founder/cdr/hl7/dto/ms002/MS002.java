package com.founder.cdr.hl7.dto.ms002;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.EmergencyTreatment;
import com.founder.cdr.hl7.dto.MSBaseDto;

public class MS002 extends MSBaseDto<EmergencyTreatment>
{
    /**
     * 消息类别代码
     */
    @NotEmpty(message = "{MS002.msgId}")
    private String msgId;

    /**
     * 版本号
     */
//    @NotEmpty(message = "{MS002.version}")
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
     * 国际疾病-门急诊
     */
    @NotEmpty(message = "{MS002.EmergencyTreatment}")
    private List<EmergencyTreatment> EmergencyTreatment;

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

    public List<EmergencyTreatment> getEmergencyTreatment()
    {
        return EmergencyTreatment;
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

    public List<EmergencyTreatment> getMesageRecords()
    {
        return EmergencyTreatment;
    }

    public String getMessageVersionNo()
    {
        return version;
    }
}
