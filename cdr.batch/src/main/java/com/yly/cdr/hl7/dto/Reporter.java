package com.yly.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;


public class Reporter extends BaseDto
{
    /**
     * 报告时间
     */
    @NotEmpty(message="{Reporter.reportDate}")
    private String reportDate;
    /**
     * 报告人编码
     */
    private String reporterId;
    /**
     * 报告人姓名
     */
    private String reporterName;

    public String getReportDate()
    {
        return reportDate;
    }

    public String getReporterId()
    {
        return reporterId;
    }

    public String getReporterName()
    {
        return reporterName;
    }
}
