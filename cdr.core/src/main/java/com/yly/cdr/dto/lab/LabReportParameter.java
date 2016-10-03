package com.yly.cdr.dto.lab;

public class LabReportParameter
{
    // 检验报告参数
    private String labReportParam;
    // 报告日期
    private String reportDate;
    // 召回标识
    private String withdrawFlag;
    
    public String getLabReportParam()
    {
        return labReportParam;
    }
    public void setLabReportParam(String labReportParam)
    {
        this.labReportParam = labReportParam;
    }
    public String getReportDate()
    {
        return reportDate;
    }
    public void setReportDate(String reportDate)
    {
        this.reportDate = reportDate;
    }
    public String getWithdrawFlag()
    {
        return withdrawFlag;
    }
    public void setWithdrawFlag(String withdrawFlag)
    {
        this.withdrawFlag = withdrawFlag;
    }
    
    private boolean LISLAB;

	public boolean getLISLAB() {
		return LISLAB;
	}
	public void setLISLAB(boolean lISLAB) {
		LISLAB = lISLAB;
	}
  
}
