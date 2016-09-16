package com.founder.cdr.hl7.dto;

import java.util.List;

public class LabReport extends BaseDto
{
    /**
     * 检验项目
     */
    private String labItemCode;
    /**
     * 检验项目名称
     */
    private String labItemName;
    /**
     * 报告结果
     */
    private List<LabReportResult> reportResult;
    /**
     * 危险标识代码
     */
    private String warnFlag;
    /**
     * 危险标识名称
     */
    private String warnFlagName;
    /**
     * 涂片信息
     */
    private List<SmearMessage> smearMessage;
    /**
     * 免疫报告结果
     */
    private List<ImmunityResult> immunityResult;

    public String getLabItemCode()
    {
        return labItemCode;
    }

    public String getLabItemName()
    {
        return labItemName;
    }

    public List<LabReportResult> getReportResult()
    {
        return reportResult;
    }

    public String getWarnFlag()
    {
        return warnFlag;
    }

    public String getWarnFlagName()
    {
        return warnFlagName;
    }

    public List<SmearMessage> getSmearMessage()
    {
        return smearMessage;
    }

    public List<ImmunityResult> getImmunityResult()
    {
        return immunityResult;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("LabReport [labItemCode=");
        builder.append(labItemCode);
        builder.append(", reportResult=");
        builder.append(reportResult);
        builder.append(", smearMessage=");
        builder.append(smearMessage);
        builder.append(", immunityResult=");
        builder.append(immunityResult);
        builder.append("]");
        return builder.toString();
    }
    
}
