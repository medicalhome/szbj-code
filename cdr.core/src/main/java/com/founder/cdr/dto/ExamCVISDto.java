package com.founder.cdr.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ExamCVISDto
{
    private String examResultProcedureSn;
    private String examReportSn;
    private String source;
    private String patientName; //患者名称
    private String patientAge; // 患者年龄
    private String genderName; // 患者性别
    private String inpatientNo; // 住院号
    private Date examinationDate;
    private String itemClass; // 检查类型
    private String wardName; // 病区 
    private String bedNo; // 床号
    private String imageIndexNo; // 影像号
    private String imagingConclusion; // 结论
    private String imagingFinding; // 表现
    private String reportDoctorName; // 报告医生名称
    private String reviewDoctorName; // 审核医生姓名
    private Date reportDate; // 报告日期
    private Date operationDate; // 手术日期
    private String operationRoomId; // 手术间ID
    private String operationRoomName; // 手术间名称
    private String operatorDoctorId; // 主刀医师Id
    private String operatorDoctorName; // 主刀医师名称
    private String operatorAssistantId; // 助手ID
    private String operatorAssistantName; // 助手名称
    private String reportTypeCode; // 报告类型
    private String reportTypeName; // 报告类型名称
    private String dataSourceType; // 数据源
    private String orgCode; // 医疗机构编码
    public String getSource()
    {
        return source;
    }
    public void setSource(String source)
    {
        this.source = source;
    }
    public String getExamReportSn()
    {
        return examReportSn;
    }
    public void setExamReportSn(String examReportSn)
    {
        this.examReportSn = examReportSn;
    }
    public String getExamResultProcedureSn()
    {
        return examResultProcedureSn;
    }
    public void setExamResultProcedureSn(String examResultProcedureSn)
    {
        this.examResultProcedureSn = examResultProcedureSn;
    }
    public String getPatientName()
    {
        return patientName;
    }
    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }
    public String getPatientAge()
    {
        return patientAge;
    }
    public void setPatientAge(String patientAge)
    {
        this.patientAge = patientAge;
    }
    public String getInpatientNo()
    {
        return inpatientNo;
    }
    public void setInpatientNo(String inpatientNo)
    {
        this.inpatientNo = inpatientNo;
    }
    public Date getExaminationDate()
    {
        return examinationDate;
    }
    public void setExaminationDate(Date examinationDate)
    {
        this.examinationDate = examinationDate;
    }
    public String getItemClass()
    {
        return itemClass;
    }
    public void setItemClass(String itemClass)
    {
        this.itemClass = itemClass;
    }
    public String getGenderName()
    {
        return genderName;
    }
    public void setGenderName(String genderName)
    {
        this.genderName = genderName;
    }
    public String getWardName()
    {
        return wardName;
    }
    public void setWardName(String wardName)
    {
        this.wardName = wardName;
    }
    public String getBedNo()
    {
        return bedNo;
    }
    public void setBedNo(String bedNo)
    {
        this.bedNo = bedNo;
    }
    public String getImageIndexNo()
    {
        return imageIndexNo;
    }
    public void setImageIndexNo(String imageIndexNo)
    {
        this.imageIndexNo = imageIndexNo;
    }
    public void setOperationDate(Date operationDate)
    {
        this.operationDate = operationDate;
    }
    public Date getOperationDate()
    {
        return operationDate;
    }
    public String getImagingConclusion()
    {
        return imagingConclusion;
    }
    public void setImagingConclusion(String imagingConclusion)
    {
        this.imagingConclusion = imagingConclusion;
    }
    public String getImagingFinding()
    {
        return imagingFinding;
    }
    public void setImagingFinding(String imagingFinding)
    {
        this.imagingFinding = imagingFinding;
    }
    public String getReportDoctorName()
    {
        return reportDoctorName;
    }
    public void setReportDoctorName(String reportDoctorName)
    {
        this.reportDoctorName = reportDoctorName;
    }
    public String getReviewDoctorName()
    {
        return reviewDoctorName;
    }
    public void setReviewDoctorName(String reviewDoctorName)
    {
        this.reviewDoctorName = reviewDoctorName;
    }
    public Date getReportDate()
    {
        return reportDate;
    }
    public void setReportDate(Date reportDate)
    {
        this.reportDate = reportDate;
    }
    public String getOperationRoomId()
    {
        return operationRoomId;
    }
    public void setOperationRoomId(String operationRoomId)
    {
        this.operationRoomId = operationRoomId;
    }
    public String getOperationRoomName()
    {
        return operationRoomName;
    }
    public void setOperationRoomName(String operationRoomName)
    {
        this.operationRoomName = operationRoomName;
    }
    public String getOperatorDoctorId()
    {
        return operatorDoctorId;
    }
    public void setOperatorDoctorId(String operatorDoctorId)
    {
        this.operatorDoctorId = operatorDoctorId;
    }
    public String getOperatorDoctorName()
    {
        return operatorDoctorName;
    }
    public void setOperatorDoctorName(String operatorDoctorName)
    {
        this.operatorDoctorName = operatorDoctorName;
    }
    public String getOperatorAssistantId()
    {
        return operatorAssistantId;
    }
    public void setOperatorAssistantId(String operatorAssistantId)
    {
        this.operatorAssistantId = operatorAssistantId;
    }
    public String getOperatorAssistantName()
    {
        return operatorAssistantName;
    }
    public void setOperatorAssistantName(String operatorAssistantName)
    {
        this.operatorAssistantName = operatorAssistantName;
    }
    public String getReportTypeCode()
    {
        return reportTypeCode;
    }
    public void setReportTypeCode(String reportTypeCode)
    {
        this.reportTypeCode = reportTypeCode;
    }
    public String getReportTypeName()
    {
        return reportTypeName;
    }
    public void setReportTypeName(String reportTypeName)
    {
        this.reportTypeName = reportTypeName;
    }
    public String getDataSourceType()
    {
        return dataSourceType;
    }
    public void setDataSourceType(String dataSourceType)
    {
        this.dataSourceType = dataSourceType;
    }
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
}
