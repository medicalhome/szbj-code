package com.yly.cdr.entity;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.founder.fasf.core.util.daohelper.annotation.Generator;

import java.io.Serializable;

@Entity
@Table(name = "EXAMINATION_RESULT")
public class ExaminationResult implements Serializable
{

    private BigDecimal examReportSn;

    private BigDecimal visitSn;

    private BigDecimal documentSn;

    private BigDecimal patientSn;

    private String patientDomain;

    private String patientLid;
    
    private String patientAge;

    private Date examinationDate;

    private String reportDoctor;

    private String reportDoctorName;

    private Date reportDate;

    private String reviewDoctor;

    private String reviewDoctorName;

    private Date reviewDate;

    private String examinationDept;

    private String reportMemo;

    private BigDecimal withdrawFlag;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String examReportLid;

    private String clinicalInformation;

    private String reagentCode;

    private String reagentDosage;

    private String reagentDosageUnit;

    private String examDeptName;

    private String reagentName;

    private String itemClass;

    private String itemClassName;

    private String wardId;

    private String wardName;

    private String bedNo;

    private String imagingFinding;

    private String imagingConclusion;

    private String secondReviewDoctor;

    private String secondReviewDoctorName;

    private Date secondReviewDate;

    private String thirdReviewDoctor;

    private String thirdReviewDoctorName;

    private Date thirdReviewDate;

    private String reportTypeCode;
    private String reportTypeName;
    private String dataSourceType;
    private String createby;

    private String updateby;

    private String deleteby;
    
    private String orgCode;
    private String orgName;
    
    private String pathlogyNo;
    
    private String hpCode;
    private String hpValue;

    @Id
    public BigDecimal getExamReportSn()
    {
        return this.examReportSn;
    }

    public void setExamReportSn(BigDecimal examReportSn)
    {
        this.examReportSn = examReportSn;
    }

    @Column(name = "VISIT_SN", precision = 22, scale = 0)
    public BigDecimal getVisitSn()
    {
        return this.visitSn;
    }

    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
    }

    @Column(name = "DOCUMENT_SN", precision = 22, scale = 0)
    public BigDecimal getDocumentSn()
    {
        return this.documentSn;
    }

    public void setDocumentSn(BigDecimal documentSn)
    {
        this.documentSn = documentSn;
    }

    @Column(name = "PATIENT_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getPatientSn()
    {
        return this.patientSn;
    }

    public void setPatientSn(BigDecimal patientSn)
    {
        this.patientSn = patientSn;
    }

    @Column(name = "PATIENT_DOMAIN", nullable = false, length = 6)
    public String getPatientDomain()
    {
        return this.patientDomain;
    }

    public void setPatientDomain(String patientDomain)
    {
        this.patientDomain = patientDomain;
    }

    @Column(name = "PATIENT_LID", nullable = false, length = 48)
    public String getPatientLid()
    {
        return this.patientLid;
    }

    public void setPatientLid(String patientLid)
    {
        this.patientLid = patientLid;
    }
    
    @Column(name = "PATIENT_AGE", length = 50)
    public String getPatientAge()
    {
        return patientAge;
    }

    public void setPatientAge(String patientAge)
    {
        this.patientAge = patientAge;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "EXAMINATION_DATE", length = 7)
    public Date getExaminationDate()
    {
        return this.examinationDate;
    }

    public void setExaminationDate(Date examinationDate)
    {
        this.examinationDate = examinationDate;
    }

    @Column(name = "REPORT_DOCTOR", length = 56)
    public String getReportDoctor()
    {
        return this.reportDoctor;
    }

    public void setReportDoctor(String reportDoctor)
    {
        this.reportDoctor = reportDoctor;
    }

    @Column(name = "REPORT_DOCTOR_NAME", length = 180)
    public String getReportDoctorName()
    {
        return this.reportDoctorName;
    }

    public void setReportDoctorName(String reportDoctorName)
    {
        this.reportDoctorName = reportDoctorName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "REPORT_DATE", length = 7)
    public Date getReportDate()
    {
        return this.reportDate;
    }

    public void setReportDate(Date reportDate)
    {
        this.reportDate = reportDate;
    }

    @Column(name = "REVIEW_DOCTOR", length = 56)
    public String getReviewDoctor()
    {
        return this.reviewDoctor;
    }

    public void setReviewDoctor(String reviewDoctor)
    {
        this.reviewDoctor = reviewDoctor;
    }

    @Column(name = "REVIEW_DOCTOR_NAME", length = 180)
    public String getReviewDoctorName()
    {
        return this.reviewDoctorName;
    }

    public void setReviewDoctorName(String reviewDoctorName)
    {
        this.reviewDoctorName = reviewDoctorName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "REVIEW_DATE", length = 7)
    public Date getReviewDate()
    {
        return this.reviewDate;
    }

    public void setReviewDate(Date reviewDate)
    {
        this.reviewDate = reviewDate;
    }

    @Column(name = "EXAMINATION_DEPT", length = 56)
    public String getExaminationDept()
    {
        return this.examinationDept;
    }

    public void setExaminationDept(String examinationDept)
    {
        this.examinationDept = examinationDept;
    }

    @Column(name = "REPORT_MEMO", length = 256)
    public String getReportMemo()
    {
        return this.reportMemo;
    }

    public void setReportMemo(String reportMemo)
    {
        this.reportMemo = reportMemo;
    }

    @Column(name = "WITHDRAW_FLAG", precision = 22, scale = 0)
    public BigDecimal getWithdrawFlag()
    {
        return this.withdrawFlag;
    }

    public void setWithdrawFlag(BigDecimal withdrawFlag)
    {
        this.withdrawFlag = withdrawFlag;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_TIME", nullable = false, length = 7)
    public Date getCreateTime()
    {
        return this.createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATE_TIME", nullable = false, length = 7)
    public Date getUpdateTime()
    {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    @Column(name = "UPDATE_COUNT", nullable = false, precision = 22, scale = 0)
    public BigDecimal getUpdateCount()
    {
        return this.updateCount;
    }

    public void setUpdateCount(BigDecimal updateCount)
    {
        this.updateCount = updateCount;
    }

    @Column(name = "DELETE_FLAG", nullable = false, length = 1)
    public String getDeleteFlag()
    {
        return this.deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DELETE_TIME", length = 7)
    public Date getDeleteTime()
    {
        return this.deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    @Column(name = "EXAM_REPORT_LID", nullable = false, length = 48)
    public String getExamReportLid()
    {
        return this.examReportLid;
    }

    public void setExamReportLid(String examReportLid)
    {
        this.examReportLid = examReportLid;
    }

    @Column(name = "CLINICAL_INFORMATION")
    public String getClinicalInformation()
    {
        return this.clinicalInformation;
    }

    public void setClinicalInformation(String clinicalInformation)
    {
        this.clinicalInformation = clinicalInformation;
    }

    @Column(name = "REAGENT_CODE", length = 12)
    public String getReagentCode()
    {
        return this.reagentCode;
    }

    public void setReagentCode(String reagentCode)
    {
        this.reagentCode = reagentCode;
    }

    @Column(name = "REAGENT_DOSAGE", length = 32)
    public String getReagentDosage()
    {
        return this.reagentDosage;
    }

    public void setReagentDosage(String reagentDosage)
    {
        this.reagentDosage = reagentDosage;
    }

    @Column(name = "REAGENT_DOSAGE_UNIT", length = 32)
    public String getReagentDosageUnit()
    {
        return this.reagentDosageUnit;
    }

    public void setReagentDosageUnit(String reagentDosageUnit)
    {
        this.reagentDosageUnit = reagentDosageUnit;
    }

    @Column(name = "EXAM_DEPT_NAME", length = 192)
    public String getExamDeptName()
    {
        return this.examDeptName;
    }

    public void setExamDeptName(String examDeptName)
    {
        this.examDeptName = examDeptName;
    }

    @Column(name = "REAGENT_NAME", length = 40)
    public String getReagentName()
    {
        return this.reagentName;
    }

    public void setReagentName(String reagentName)
    {
        this.reagentName = reagentName;
    }

    @Column(name = "ITEM_CLASS", length = 32)
    public String getItemClass()
    {
        return this.itemClass;
    }

    public void setItemClass(String itemClass)
    {
        this.itemClass = itemClass;
    }

    @Column(name = "ITEM_CLASS_NAME", length = 192)
    public String getItemClassName()
    {
        return this.itemClassName;
    }

    public void setItemClassName(String itemClassName)
    {
        this.itemClassName = itemClassName;
    }

    @Column(name = "WARD_ID", length = 12)
    public String getWardId()
    {
        return this.wardId;
    }

    public void setWardId(String wardId)
    {
        this.wardId = wardId;
    }

    @Column(name = "WARD_NAME", length = 192)
    public String getWardName()
    {
        return this.wardName;
    }

    public void setWardName(String wardName)
    {
        this.wardName = wardName;
    }

    @Column(name = "BED_NO", length = 30)
    public String getBedNo()
    {
        return this.bedNo;
    }

    public void setBedNo(String bedNo)
    {
        this.bedNo = bedNo;
    }

    @Column(name = "IMAGING_FINDING")
    public String getImagingFinding()
    {
        return this.imagingFinding;
    }

    public void setImagingFinding(String imagingFinding)
    {
        this.imagingFinding = imagingFinding;
    }

    @Column(name = "IMAGING_CONCLUSION")
    public String getImagingConclusion()
    {
        return this.imagingConclusion;
    }

    public void setImagingConclusion(String imagingConclusion)
    {
        this.imagingConclusion = imagingConclusion;
    }

    @Column(name = "SECOND_REVIEW_DOCTOR", length = 56)
    public String getSecondReviewDoctor()
    {
        return secondReviewDoctor;
    }

    public void setSecondReviewDoctor(String secondReviewDoctor)
    {
        this.secondReviewDoctor = secondReviewDoctor;
    }

    @Column(name = "SECOND_REVIEW_DOCTOR_NAME", length = 180)
    public String getSecondReviewDoctorName()
    {
        return secondReviewDoctorName;
    }

    public void setSecondReviewDoctorName(String secondReviewDoctorName)
    {
        this.secondReviewDoctorName = secondReviewDoctorName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "SECOND_REVIEW_DATE", length = 7)
    public Date getSecondReviewDate()
    {
        return secondReviewDate;
    }

    public void setSecondReviewDate(Date secondReviewDate)
    {
        this.secondReviewDate = secondReviewDate;
    }

    @Column(name = "THIRD_REVIEW_DOCTOR", length = 56)
    public String getThirdReviewDoctor()
    {
        return thirdReviewDoctor;
    }

    public void setThirdReviewDoctor(String thirdReviewDoctor)
    {
        this.thirdReviewDoctor = thirdReviewDoctor;
    }

    @Column(name = "THIRD_REVIEW_DOCTOR_NAME", length = 180)
    public String getThirdReviewDoctorName()
    {
        return thirdReviewDoctorName;
    }

    public void setThirdReviewDoctorName(String thirdReviewDoctorName)
    {
        this.thirdReviewDoctorName = thirdReviewDoctorName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "THIRD_REVIEW_DATE", length = 7)
    public Date getThirdReviewDate()
    {
        return thirdReviewDate;
    }

    public void setThirdReviewDate(Date thirdReviewDate)
    {
        this.thirdReviewDate = thirdReviewDate;
    }

    @Column(name = "REPORT_TYPE_CODE", length = 6)
    public String getReportTypeCode()
    {
        return reportTypeCode;
    }

    public void setReportTypeCode(String reportTypeCode)
    {
        this.reportTypeCode = reportTypeCode;
    }

    @Column(name = "REPORT_TYPE_NAME", length = 192)
    public String getReportTypeName()
    {
        return reportTypeName;
    }

    public void setReportTypeName(String reportTypeName)
    {
        this.reportTypeName = reportTypeName;
    }

    @Column(name = "DATA_SOURCE_TYPE", length = 10)
    public String getDataSourceType()
    {
        return dataSourceType;
    }

    public void setDataSourceType(String dataSourceType)
    {
        this.dataSourceType = dataSourceType;
    }
    @Column(name = "CREATEBY", length = 12)
    public String getCreateby()
    {
        return this.createby;
    }

    public void setCreateby(String createby)
    {
        this.createby = createby;
    }
    
    @Column(name = "UPDATEBY", length = 12)
    public String getUpdateby()
    {
        return this.updateby;
    }

    public void setUpdateby(String updateby)
    {
        this.updateby = updateby;
    }
    
    @Column(name = "DELETEBY", length = 12)
    public String getDeleteby()
    {
        return this.deleteby;
    }

    public void setDeleteby(String deleteby)
    {
        this.deleteby = deleteby;
    }

    @Column(name = "ORG_CODE", length = 50)
    public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

    @Column(name = "ORG_NAME", length = 50)
    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

    @Column(name = "PATHLOGY_NO", length = 12)
	public String getPathlogyNo() {
		return pathlogyNo;
	}

	public void setPathlogyNo(String pathlogyNo) {
		this.pathlogyNo = pathlogyNo;
	}
	
	@Column(name = "HP_CODE", length = 12)
	public String getHpCode() {
		return hpCode;
	}

	public void setHpCode(String hpCode) {
		this.hpCode = hpCode;
	}

	@Column(name = "HP_VALUE", length = 128)
	public String getHpValue() {
		return hpValue;
	}

	public void setHpValue(String hpValue) {
		this.hpValue = hpValue;
	}
	


}
