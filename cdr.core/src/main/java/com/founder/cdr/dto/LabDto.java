package com.founder.cdr.dto;

import java.util.Date;

/**
 * 
 * [FUN]V05-101检验列表
 * @version 1.0, 2012/03/14  
 * @author 金鹏
 * @since 1.0
 * 
 */
public class LabDto
{
    private String labDept;

    private String labDeptName;

    private String testerName;

    private String reporterName;

    private Date testDate;

    private Date reportDate;

    private String itemName;

    private String withdrawFlag;

    private String compositeItemSn;

    private String labReportSn;

    private Date requestTime;

    private String sampleType;

    private String sampleTypeName;

    private String reviewerName;

    private String reviewerId;

    private Date reviewDate;

    private String phenomenonPerformance;

    private String technicalNote;

    private String reportMemo;

    private String testMethod;

    private String itemCode;

    private String samplingSite;

    private Date samplingTime;

    private String submittingPersonName;

    private Date receiveTime;

    private String memo;

    private String sourceSystemId;

    private String signatureId;

    private String orderSn;

    private String orderStatusName;

    // 下嘱科室
    private String orderDeptName;

    // 申请时间
    private Date requestDate;

    // $Author :jin_peng
    // $Date : 2013/04/22 09:40$
    // [BUG]0014747 ADD BEGIN
    // 检验具体结果编码
    private String subitemCode;

    // 检验具体结果名称
    private String subitemName;
    
    // 检验具体结果
    private String itemValue;

    // 检验具体结果单位
    private String itemUnit;

    // 是否异常标识
    private String uncommonFlag;

    // 是否危急标识
    private String crisisFlag;

    // 检验描述性结果
    private String qualitativeResults;
    
    private String orgCode;

    // [BUG]0014747 ADD END

    // private BigDecimal reportSn;
    // private BigDecimal visitSn;
    // private BigDecimal requestSn;
    // private BigDecimal documentSn;
    // private BigDecimal patientSn;
     private String patientDomain;
    // private String patientLid;
    // private String sampleType;
    // private String itemCode;
    // private String testMethod;
    // private String testerId;
    // private String reporterId;
    // private String reviewerName;
    // private Date reviewDate;
    // private String reportMemo;
    //
    //
    // private BigDecimal orderSn;
    // private String requestNo;
    // private String requestDept;
    // private String requestDoctor;
    // private String requestDoctorName;
    // private String diagnosis;
    // private String sampleRequirement;
    // private String requestMemo;

    public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getLabDept()
    {
        return labDept;
    }

    public String getSourceSystemId()
    {
        return sourceSystemId;
    }

    public void setSourceSystemId(String sourceSystemId)
    {
        this.sourceSystemId = sourceSystemId;
    }

    public String getSamplingSite()
    {
        return samplingSite;
    }

    public void setSamplingSite(String samplingSite)
    {
        this.samplingSite = samplingSite;
    }

    public Date getSamplingTime()
    {
        return samplingTime;
    }

    public void setSamplingTime(Date samplingTime)
    {
        this.samplingTime = samplingTime;
    }

    public String getSubmittingPersonName()
    {
        return submittingPersonName;
    }

    public void setSubmittingPersonName(String submittingPersonName)
    {
        this.submittingPersonName = submittingPersonName;
    }

    public Date getReceiveTime()
    {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime)
    {
        this.receiveTime = receiveTime;
    }

    public String getMemo()
    {
        return memo;
    }

    public void setMemo(String memo)
    {
        this.memo = memo;
    }

    public Date getRequestTime()
    {
        return requestTime;
    }

    public void setRequestTime(Date requestTime)
    {
        this.requestTime = requestTime;
    }

    public String getItemCode()
    {
        return itemCode;
    }

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    public void setLabDept(String labDept)
    {
        this.labDept = labDept;
    }

    public String getTesterName()
    {
        return testerName;
    }

    public void setTesterName(String testerName)
    {
        this.testerName = testerName;
    }

    public String getReporterName()
    {
        return reporterName;
    }

    public void setReporterName(String reporterName)
    {
        this.reporterName = reporterName;
    }

    public Date getTestDate()
    {
        return testDate;
    }

    public void setTestDate(Date testDate)
    {
        this.testDate = testDate;
    }

    public Date getReportDate()
    {
        return reportDate;
    }

    public void setReportDate(Date reportDate)
    {
        this.reportDate = reportDate;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public String getWithdrawFlag()
    {
        return withdrawFlag;
    }

    public void setWithdrawFlag(String withdrawFlag)
    {
        this.withdrawFlag = withdrawFlag;
    }

    public String getCompositeItemSn()
    {
        return compositeItemSn;
    }

    public void setCompositeItemSn(String compositeItemSn)
    {
        this.compositeItemSn = compositeItemSn;
    }

    public String getLabReportSn()
    {
        return labReportSn;
    }

    public void setLabReportSn(String labReportSn)
    {
        this.labReportSn = labReportSn;
    }

    public String getSampleType()
    {
        return sampleType;
    }

    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }

    public String getReviewerName()
    {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName)
    {
        this.reviewerName = reviewerName;
    }

    public Date getReviewDate()
    {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate)
    {
        this.reviewDate = reviewDate;
    }

    public String getPhenomenonPerformance()
    {
        return phenomenonPerformance;
    }

    public void setPhenomenonPerformance(String phenomenonPerformance)
    {
        this.phenomenonPerformance = phenomenonPerformance;
    }

    public String getTechnicalNote()
    {
        return technicalNote;
    }

    public void setTechnicalNote(String technicalNote)
    {
        this.technicalNote = technicalNote;
    }

    public String getReportMemo()
    {
        return reportMemo;
    }

    public void setReportMemo(String reportMemo)
    {
        this.reportMemo = reportMemo;
    }

    public String getTestMethod()
    {
        return testMethod;
    }

    public void setTestMethod(String testMethod)
    {
        this.testMethod = testMethod;
    }

    public String getLabDeptName()
    {
        return labDeptName;
    }

    public void setLabDeptName(String labDeptName)
    {
        this.labDeptName = labDeptName;
    }

    public String getSampleTypeName()
    {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
    }

    public String getSignatureId()
    {
        return signatureId;
    }

    public void setSignatureId(String signatureId)
    {
        this.signatureId = signatureId;
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

    public Date getRequestDate()
    {
        return requestDate;
    }

    public void setRequestDate(Date requestDate)
    {
        this.requestDate = requestDate;
    }

    public String getReviewerId()
    {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId)
    {
        this.reviewerId = reviewerId;
    }

    public String getSubitemCode()
    {
        return subitemCode;
    }

    public void setSubitemCode(String subitemCode)
    {
        this.subitemCode = subitemCode;
    }

    public String getSubitemName()
    {
        return subitemName;
    }

    public void setSubitemName(String subitemName)
    {
        this.subitemName = subitemName;
    }

    public String getItemUnit()
    {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit)
    {
        this.itemUnit = itemUnit;
    }

    public String getUncommonFlag()
    {
        return uncommonFlag;
    }

    public void setUncommonFlag(String uncommonFlag)
    {
        this.uncommonFlag = uncommonFlag;
    }

    public String getCrisisFlag()
    {
        return crisisFlag;
    }

    public void setCrisisFlag(String crisisFlag)
    {
        this.crisisFlag = crisisFlag;
    }

    public String getQualitativeResults()
    {
        return qualitativeResults;
    }

    public void setQualitativeResults(String qualitativeResults)
    {
        this.qualitativeResults = qualitativeResults;
    }

    public String getItemValue()
    {
        return itemValue;
    }

    public void setItemValue(String itemValue)
    {
        this.itemValue = itemValue;
    }

	public String getPatientDomain() {
		return patientDomain;
	}

	public void setPatientDomain(String patientDomain) {
		this.patientDomain = patientDomain;
	}

}
