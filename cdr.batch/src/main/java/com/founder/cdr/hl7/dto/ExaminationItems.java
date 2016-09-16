package com.founder.cdr.hl7.dto;

import java.util.List;

import com.founder.cdr.hl7.dto.pocdin000040uv02.ExaminingDoctor;

public class ExaminationItems extends BaseDto
{
    /**
     * 检查报告单编号
     */
    private String reportOrderLid;

    //$Author:wei_peng
    //$Date:2012/9/25 13:45
    //$[BUG]0010017 DELETE BEGIN
    /**
     * 检查项目代码
     */
    //@NotEmpty(message = "{ExaminationItems.examinationItemId}")
    //private String examinationItemId;
    //$[BUG]0010017 DELETE END

    //$Author:wei_peng
    //$Date:2012/9/25 13:45
    //$[BUG]0010017 DELETE BEGIN
    /**
     * 检查项目名称
     */
    //@NotEmpty(message = "{ExaminationItems.examinationItem}")
    //private String examinationItem;
    //$[BUG]0010017 DELETE END

    /**
     * 检查报告备注
     */
    private String reportMemo;

    /**
     * 报告日期
     */
    private String reportDate;

    /**
     * 检查报告结果-影像学表现
     */
    private String imagingFinding;

    /**
     * 检查报告结果-影像学结论
     */
    private String imagingConclusion;

    /**
     * 检查方法代码
     */
    private String examinationMethod;

    /**
     * 检查方法名称
     */
    private String examinationMethodName;

    //$Author:wei_peng
    //$Date:2012/9/25 13:45
    //$[BUG]0010017 DELETE BEGIN
    /**
     * 检查部位代码
     */
    //private String examinationRegion;
    //$[BUG]0010017 DELETE END
    
    //$Author:wei_peng
    //$Date:2012/9/25 13:45
    //$[BUG]0010017 DELETE BEGIN
    /**
     * 检查部位名称
     */
    //@NotEmpty(message = "{ExaminationItems.examinationRegionName}")
    //private String examinationRegionName;
    //$[BUG]0010017 DELETE END

    /**
     * 检查申请单编号
     */
    private String requestNo;

    /**
     * 影像内容List
     */
    private List<LabImageContent> imageText;
    
    /**
     * 检查医生信息List
     */
    private List<ExaminingDoctor> examiningDoctors;
    
    /**
     * 具体项目信息
     */
    private List<ExamItemMessage> itemMessages;
    
    public String getReportOrderLid()
    {
        return reportOrderLid;
    }

    public String getReportMemo()
    {
        return reportMemo;
    }

    public String getReportDate()
    {
        return reportDate;
    }

    public String getImagingFinding()
    {
        return imagingFinding;
    }

    public List<ExamItemMessage> getItemMessages()
    {
        return itemMessages;
    }

    public String getImagingConclusion()
    {
        return imagingConclusion;
    }

    public String getExaminationMethod()
    {
        return examinationMethod;
    }

    public String getExaminationMethodName()
    {
        return examinationMethodName;
    }

    public String getRequestNo()
    {
        return requestNo;
    }

    public List<LabImageContent> getImageText()
    {
        return imageText;
    }

    public List<ExaminingDoctor> getExaminingDoctors()
    {
        return examiningDoctors;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ExaminationItems [");
        builder.append("reportOrderLid=");
        builder.append(reportOrderLid).append(", ");
        builder.append("reportDate=");
        builder.append(reportDate).append(", ");
        builder.append("examinationMethod=");
        builder.append(examinationMethod).append(", ");
        builder.append("examinationMethodName=");
        builder.append(examinationMethodName).append(", ");
        builder.append("requestNo=");
        builder.append(requestNo).append(", ");
        builder.append("itemMessages=");
        builder.append(itemMessages).append(", ");
        builder.append("]");
        return builder.toString();
    }
    
}
