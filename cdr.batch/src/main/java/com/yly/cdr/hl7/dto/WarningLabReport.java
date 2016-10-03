package com.yly.cdr.hl7.dto;

import java.util.List;

public class WarningLabReport extends BaseDto
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
    private List<WarningLabReportResult> reportResult;

    public String getLabItemCode()
    {
        return labItemCode;
    }

    public void setLabItemCode(String labItemCode)
    {
        this.labItemCode = labItemCode;
    }

    public String getLabItemName() {
		return labItemName;
	}

	public void setLabItemName(String labItemName) {
		this.labItemName = labItemName;
	}

	public List<WarningLabReportResult> getReportResult()
    {
        return reportResult;
    }

    public void setReportResult(List<WarningLabReportResult> reportResult)
    {
        this.reportResult = reportResult;
    }

}
