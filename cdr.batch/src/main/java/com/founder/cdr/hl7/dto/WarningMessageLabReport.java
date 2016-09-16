package com.founder.cdr.hl7.dto;

import java.util.List;

public class WarningMessageLabReport extends BaseDto
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
    private List<WarningMessageLabReportResult> reportResult;

    public String getLabItemCode()
    {
        return labItemCode;
    }

    public void setLabItemCode(String labItemCode)
    {
        this.labItemCode = labItemCode;
    }

    public List<WarningMessageLabReportResult> getReportResult()
    {
        return reportResult;
    }

    public void setReportResult(List<WarningMessageLabReportResult> reportResult)
    {
        this.reportResult = reportResult;
    }

    public String getLabItemName()
    {
        return labItemName;
    }

    public void setLabItemName(String labItemName)
    {
        this.labItemName = labItemName;
    }

}
