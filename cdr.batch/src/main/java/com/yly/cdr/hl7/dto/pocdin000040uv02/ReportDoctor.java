package com.yly.cdr.hl7.dto.pocdin000040uv02;

public class ReportDoctor
{
    /**
     * 报告日期
     */
    // $Author:tong_meng
    // $Date:2012/11/29 16:00
    // $[BUG]0012042 DELETE BEGIN
    // @NotEmpty(message = "{POCDIN000040UV02.reportDate}")
    // $[BUG]0012042 DELETE BEGIN
    private String reportDate;

    /**
     * 报告医生代码
     */
    private String reportDoctor;

    /**
     * 报告医生姓名
     */
    // $Author:tong_meng
    // $Date:2012/10/16 12:57
    // $[BUG]0010453 DELETE BEGIN
    /* @NotEmpty(message = "{POCDIN000040UV02.reportDoctorName}") */
    // $[BUG]0010453 DELETE END
    private String reportDoctorName;

    public String getReportDate()
    {
        return reportDate;
    }

    public String getReportDoctor()
    {
        return reportDoctor;
    }

    public String getReportDoctorName()
    {
        return reportDoctorName;
    }
}
