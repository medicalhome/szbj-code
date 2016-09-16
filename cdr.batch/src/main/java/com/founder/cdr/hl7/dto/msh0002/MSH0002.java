package com.founder.cdr.hl7.dto.msh0002;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.CodeValues;
import com.founder.cdr.hl7.dto.MSBaseDto;

public class MSH0002 extends MSBaseDto<CodeValues>
{
    /**
     * 消息类别代码
     */
    @NotEmpty(message = "{MSH0002.msgId}")
    private String msgId;

    /**
     * 版本号
     */
//    @NotEmpty(message = "{MSH0002.version}")
    private String versionNo;

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
    @NotEmpty(message = "{MSH0002.codeValues}")
    private List<CodeValues> codeValues;

    public String getMsgId()
    {
        return msgId;
    }

    public String getVersionNo()
    {
        return versionNo;
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

    public List<CodeValues> getCodeValues()
    {
        return codeValues;
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

    public List<CodeValues> getMesageRecords()
    {
        return codeValues;
    }

    public String getMessageVersionNo()
    {
        return versionNo;
    }

}
