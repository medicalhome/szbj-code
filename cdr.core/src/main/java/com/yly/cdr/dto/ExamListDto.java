package com.yly.cdr.dto;

import java.io.InputStream;
import java.sql.Blob;
import java.util.Date;

public class ExamListDto
{
    //患者域
	private String patientDomain;
	
	// 检查医嘱内部序列号
    private String orderSn;

    // 检查项目内部序列号
    private String itemSn;
    
    // 检查文档内部序列号
    private String documentSn;
    
    // 检查项目代码
    private String examinationItem;

    // 检查部位代码
    private String examinationRegion;

    // 检查项目名称
    private String examinationItemName;

    // 检查科室
    private String examinationDept;

    // 检查科室
    private String examDeptName;

    // 检查结果
    private String examinationResult;

    // 检查部位名称
    private String examinationRegionName;

    // 检查方法
    private String examinationMethodName;

    // 检查医师
    private String examiningDoctorName;

    // 检查日期
    private Date examinationDate;

    // 报告医师
    private String reportDoctorName;

    // 报告日期
    private Date reportDate;

    // 审核医师
    private String reviewDoctorName;

    // 审核日期
    private Date reviewDate;

    // 影像学表现（检查报告中）
    private String eiImagingFinding;

    // 影像学结论（检查报告中）
    private String eiImagingConclusion;

    // 影像学表现（检查项目中）
    private String erImagingFinding;

    // 影像学结论（检查项目中）
    private String erImagingConclusion;

    // 报告内部序列号
    private String examReportSn;

    // 患者内部序列号
    private Integer patientSn;

    // 检查报告备注
    private String reportMemo;

    // 召回状态
    private String withdrawFlag;

    // 试剂代码
    private String reagentCode;

    // 试剂名称
    private String reagentName;

    // 试剂用量
    private String reagentDosage;

    // 试剂用量单位
    private String reagentDosageUnit;

    // 申请单号
    private String requestNo;

    // 申请日期
    private Date requestDate;

    // 诊断
    private String diagnosis;

    // 申请原因
    private String requestReason;

    // 检查条件
    private String examCondition;

    // 注意事项
    private String examNotice;

    // 影像内容
    private Blob imageContent;

    // 图片类型
    private String imageFormat;

    // 影像流数据
    private InputStream imageStream;

    // 检查项目编码
    private String itemClass;
    
    // 源系统ID
    private String dataSourceType;
    
    // 检查类型名称
    private String itemClassName;

    // 图像索引号
    private String imageIndexNo;

    // 电子签章号
    private String signatureId;

    // 医嘱状态
    private String orderStatusName;

    // 下嘱科室
    private String orderDeptName;

    // 第二审核人姓名
    private String secondReviewDoctorName;

    // 二次审核时间
    private Date secondReviewDate;

    // 第三审核人姓名
    private String thirdReviewDoctorName;

    // 三次审核时间
    private Date thirdReviewDate;
    
    // 医疗机构编码
    private String orgCode;

    
    public String getDocumentSn() {
		return documentSn;
	}

	public void setDocumentSn(String documentSn) {
		this.documentSn = documentSn;
	}

	public String getPatientDomain() {
		return patientDomain;
	}

	public void setPatientDomain(String patientDomain) {
		this.patientDomain = patientDomain;
	}

	public String getSignatureId()
    {
        return signatureId;
    }

    public void setSignatureId(String signatureId)
    {
        this.signatureId = signatureId;
    }

    public String getItemClassName()
    {
        return itemClassName;
    }

    public void setItemClassName(String itemClassName)
    {
        this.itemClassName = itemClassName;
    }
    
    public String getItemClass()
    {
        return itemClass;
    }

    public void setItemClass(String itemClass)
    {
        this.itemClass = itemClass;
    }

    public String getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(String dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

	public String getExaminationItem()
    {
        return examinationItem;
    }

    public void setExaminationItem(String examinationItem)
    {
        this.examinationItem = examinationItem;
    }

    public String getExaminationRegion()
    {
        return examinationRegion;
    }

    public void setExaminationRegion(String examinationRegion)
    {
        this.examinationRegion = examinationRegion;
    }

    public String getItemSn()
    {
        return itemSn;
    }

    public void setItemSn(String itemSn)
    {
        this.itemSn = itemSn;
    }

    public String getWithdrawFlag()
    {
        return withdrawFlag;
    }

    public void setWithdrawFlag(String withdrawFlag)
    {
        this.withdrawFlag = withdrawFlag;
    }

    public String getReagentCode()
    {
        return reagentCode;
    }

    public void setReagentCode(String reagentCode)
    {
        this.reagentCode = reagentCode;
    }

    public String getReagentDosage()
    {
        return reagentDosage;
    }

    public void setReagentDosage(String reagentDosage)
    {
        this.reagentDosage = reagentDosage;
    }

    public String getReagentDosageUnit()
    {
        return reagentDosageUnit;
    }

    public void setReagentDosageUnit(String reagentDosageUnit)
    {
        this.reagentDosageUnit = reagentDosageUnit;
    }

    public String getExaminationItemName()
    {
        return examinationItemName;
    }

    public void setExaminationItemName(String examinationItemName)
    {
        this.examinationItemName = examinationItemName;
    }

    public String getExaminationDept()
    {
        return examinationDept;
    }

    public void setExaminationDept(String examinationDept)
    {
        this.examinationDept = examinationDept;
    }

    public String getExaminationResult()
    {
        return examinationResult;
    }

    public void setExaminationResult(String examinationResult)
    {
        this.examinationResult = examinationResult;
    }

    public String getExaminationRegionName()
    {
        return examinationRegionName;
    }

    public void setExaminationRegionName(String examinationRegionName)
    {
        this.examinationRegionName = examinationRegionName;
    }

    public String getExaminationMethodName()
    {
        return examinationMethodName;
    }

    public void setExaminationMethodName(String examinationMethodName)
    {
        this.examinationMethodName = examinationMethodName;
    }

    public String getExaminingDoctorName()
    {
        return examiningDoctorName;
    }

    public void setExaminingDoctorName(String examiningDoctorName)
    {
        this.examiningDoctorName = examiningDoctorName;
    }

    public Date getExaminationDate()
    {
        return examinationDate;
    }

    public void setExaminationDate(Date examinationDate)
    {
        this.examinationDate = examinationDate;
    }

    public String getReportDoctorName()
    {
        return reportDoctorName;
    }

    public void setReportDoctorName(String reportDoctorName)
    {
        this.reportDoctorName = reportDoctorName;
    }

    public Date getReportDate()
    {
        return reportDate;
    }

    public void setReportDate(Date reportDate)
    {
        this.reportDate = reportDate;
    }

    public String getReviewDoctorName()
    {
        return reviewDoctorName;
    }

    public void setReviewDoctorName(String reviewDoctorName)
    {
        this.reviewDoctorName = reviewDoctorName;
    }

    public Date getReviewDate()
    {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate)
    {
        this.reviewDate = reviewDate;
    }

    public String getEiImagingFinding()
    {
        return eiImagingFinding;
    }

    public void setEiImagingFinding(String eiImagingFinding)
    {
        this.eiImagingFinding = eiImagingFinding;
    }

    public String getEiImagingConclusion()
    {
        return eiImagingConclusion;
    }

    public void setEiImagingConclusion(String eiImagingConclusion)
    {
        this.eiImagingConclusion = eiImagingConclusion;
    }

    public String getErImagingFinding()
    {
        return erImagingFinding;
    }

    public void setErImagingFinding(String erImagingFinding)
    {
        this.erImagingFinding = erImagingFinding;
    }

    public String getErImagingConclusion()
    {
        return erImagingConclusion;
    }

    public void setErImagingConclusion(String erImagingConclusion)
    {
        this.erImagingConclusion = erImagingConclusion;
    }

    public String getExamReportSn()
    {
        return examReportSn;
    }

    public void setExamReportSn(String examReportSn)
    {
        this.examReportSn = examReportSn;
    }

    public Integer getPatientSn()
    {
        return patientSn;
    }

    public void setPatientSn(Integer patientSn)
    {
        this.patientSn = patientSn;
    }

    public String getReportMemo()
    {
        return reportMemo;
    }

    public void setReportMemo(String reportMemo)
    {
        this.reportMemo = reportMemo;
    }

    public String getRequestNo()
    {
        return requestNo;
    }

    public void setRequestNo(String requestNo)
    {
        this.requestNo = requestNo;
    }

    public Date getRequestDate()
    {
        return requestDate;
    }

    public void setRequestDate(Date requestDate)
    {
        this.requestDate = requestDate;
    }

    public String getDiagnosis()
    {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis)
    {
        this.diagnosis = diagnosis;
    }

    public String getRequestReason()
    {
        return requestReason;
    }

    public void setRequestReason(String requestReason)
    {
        this.requestReason = requestReason;
    }

    public String getExamCondition()
    {
        return examCondition;
    }

    public void setExamCondition(String examCondition)
    {
        this.examCondition = examCondition;
    }

    public String getExamNotice()
    {
        return examNotice;
    }

    public void setExamNotice(String examNotice)
    {
        this.examNotice = examNotice;
    }

    public Blob getImageContent()
    {
        return imageContent;
    }

    public void setImageContent(Blob imageContent)
    {
        this.imageContent = imageContent;
    }

    public String getImageFormat()
    {
        return imageFormat;
    }

    public void setImageFormat(String imageFormat)
    {
        this.imageFormat = imageFormat;
    }

    public InputStream getImageStream()
    {
        return imageStream;
    }

    public void setImageStream(InputStream imageStream)
    {
        this.imageStream = imageStream;
    }

    public String getExamDeptName()
    {
        return examDeptName;
    }

    public void setExamDeptName(String examDeptName)
    {
        this.examDeptName = examDeptName;
    }

    public String getReagentName()
    {
        return reagentName;
    }

    public void setReagentName(String reagentName)
    {
        this.reagentName = reagentName;
    }

    public String getImageIndexNo()
    {
        return imageIndexNo;
    }

    public void setImageIndexNo(String imageIndexNo)
    {
        this.imageIndexNo = imageIndexNo;
    }

    public String getOrderSn()
    {
        return orderSn;
    }

    public void setOrderSn(String orderSn)
    {
        this.orderSn = orderSn;
    }

    public String getOrderStatusName()
    {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName)
    {
        this.orderStatusName = orderStatusName;
    }

    public String getOrderDeptName()
    {
        return orderDeptName;
    }

    public void setOrderDeptName(String orderDeptName)
    {
        this.orderDeptName = orderDeptName;
    }

    public String getSecondReviewDoctorName()
    {
        return secondReviewDoctorName;
    }

    public void setSecondReviewDoctorName(String secondReviewDoctorName)
    {
        this.secondReviewDoctorName = secondReviewDoctorName;
    }

    public Date getSecondReviewDate()
    {
        return secondReviewDate;
    }

    public void setSecondReviewDate(Date secondReviewDate)
    {
        this.secondReviewDate = secondReviewDate;
    }

    public String getThirdReviewDoctorName()
    {
        return thirdReviewDoctorName;
    }

    public void setThirdReviewDoctorName(String thirdReviewDoctorName)
    {
        this.thirdReviewDoctorName = thirdReviewDoctorName;
    }

    public Date getThirdReviewDate()
    {
        return thirdReviewDate;
    }

    public void setThirdReviewDate(Date thirdReviewDate)
    {
        this.thirdReviewDate = thirdReviewDate;
    }

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

}
