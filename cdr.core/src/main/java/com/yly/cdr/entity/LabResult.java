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
@Table(name = "LAB_RESULT")
public class LabResult implements Serializable
{

    private BigDecimal labReportSn;

    private BigDecimal visitSn;

    private BigDecimal documentSn;

    private BigDecimal patientSn;

    private String patientDomain;

    private String patientLid;

    private String sampleType;

    private String labDept;

    private String testerId;

    private String testerName;

    private Date testDate;

    private String reporterId;

    private String reporterName;

    private Date reportDate;

    private String reviewerId;

    private String reviewerName;

    private Date reviewDate;

    private String reportMemo;

    private Date createTime;

    private BigDecimal withdrawFlag;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String labReportLid;

    private String technicalNote;

    private String sampleTypeName;

    private String labDeptName;

    private Date requestTime;

    private String samplingSite;

    private Date samplingTime;

    private Date receiveTime;

    private String submittingPersonId;

    private String submittingPersonName;

    private String memo;

    private String sourceSystemId;

    private String sendSamplePurposeCode;

    private String sendSamplePurposeName;

    private String sampleNo;

    private String samplingPart;

    private BigDecimal expectedAge;

    private BigDecimal fetalNumber;

    private Date lastMenstrualTime;

    private String gestationalWeekCalcMethod;

    private String menopause;

    private String reportNo;

    private String laboratoryNo;

    private String testResultsDetail;

    private String supplierId;

    private String supplierName;

    private String supplierGender;

    private String supplierAge;

    private String wardId;

    private String bedNo;

    private String phenomenonPerformance;

    private String testResults;

    private String wardName;

    private String diagnosisText;

    private String reportTypeCode;

    private String reportTypeName;

    private String dataSourceType;
    
    private String sendHospital; 

    private String gathererCode;

    private String gathererName;
    
    private String createby;

    private String updateby;

    private String deleteby;
    
    private String orgCode;
    
    private String orgName;
    
    private String roomNo;
    
    private String method;
    /*
     * 细胞分裂状态编码
     */
    private String cellDevisionStatus;
    /*
     * 细胞分裂状态名称
     */    
    private String cellDevisionName;
    /*
     * 细胞分裂值
     */
    private BigDecimal cellDevisionValue;
    /*
     * 基因编码
     */
    private String geneCode;
    /*
     * 基因名称
     */
    private String geneName;
    /*
     * 基因值
     */
    private String geneValue;

    @Id
    public BigDecimal getLabReportSn()
    {
        return this.labReportSn;
    }

    public void setLabReportSn(BigDecimal labReportSn)
    {
        this.labReportSn = labReportSn;
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

    @Column(name = "PATIENT_LID", nullable = false, length = 30)
    public String getPatientLid()
    {
        return this.patientLid;
    }

    public void setPatientLid(String patientLid)
    {
        this.patientLid = patientLid;
    }

    @Column(name = "SAMPLE_TYPE", length = 56)
    public String getSampleType()
    {
        return this.sampleType;
    }

    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }

    @Column(name = "LAB_DEPT", length = 60)
    public String getLabDept()
    {
        return this.labDept;
    }

    public void setLabDept(String labDept)
    {
        this.labDept = labDept;
    }

    @Column(name = "TESTER_ID", length = 15)
    public String getTesterId()
    {
        return this.testerId;
    }

    public void setTesterId(String testerId)
    {
        this.testerId = testerId;
    }

    @Column(name = "TESTER_NAME", length = 200)
    public String getTesterName()
    {
        return this.testerName;
    }

    public void setTesterName(String testerName)
    {
        this.testerName = testerName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "TEST_DATE", length = 7)
    public Date getTestDate()
    {
        return this.testDate;
    }

    public void setTestDate(Date testDate)
    {
        this.testDate = testDate;
    }

    @Column(name = "REPORTER_ID", length = 60)
    public String getReporterId()
    {
        return this.reporterId;
    }

    public void setReporterId(String reporterId)
    {
        this.reporterId = reporterId;
    }

    @Column(name = "REPORTER_NAME", length = 200)
    public String getReporterName()
    {
        return this.reporterName;
    }

    public void setReporterName(String reporterName)
    {
        this.reporterName = reporterName;
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

    @Column(name = "REVIEWER_ID", length = 56)
    public String getReviewerId()
    {
        return this.reviewerId;
    }

    public void setReviewerId(String reviewerId)
    {
        this.reviewerId = reviewerId;
    }

    @Column(name = "REVIEWER_NAME", length = 120)
    public String getReviewerName()
    {
        return this.reviewerName;
    }

    public void setReviewerName(String reviewerName)
    {
        this.reviewerName = reviewerName;
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

    @Column(name = "REPORT_MEMO", length = 2400)
    public String getReportMemo()
    {
        return this.reportMemo;
    }

    public void setReportMemo(String reportMemo)
    {
        this.reportMemo = reportMemo;
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

    @Column(name = "LAB_REPORT_LID", nullable = false, length = 80)
    public String getLabReportLid()
    {
        return this.labReportLid;
    }

    public void setLabReportLid(String labReportLid)
    {
        this.labReportLid = labReportLid;
    }

    @Column(name = "TECHNICAL_NOTE", length = 256)
    public String getTechnicalNote()
    {
        return this.technicalNote;
    }

    public void setTechnicalNote(String technicalNote)
    {
        this.technicalNote = technicalNote;
    }

    @Column(name = "SAMPLE_TYPE_NAME", length = 300)
    public String getSampleTypeName()
    {
        return this.sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
    }

    @Column(name = "LAB_DEPT_NAME", length = 300)
    public String getLabDeptName()
    {
        return this.labDeptName;
    }

    public void setLabDeptName(String labDeptName)
    {
        this.labDeptName = labDeptName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "REQUEST_TIME", length = 7)
    public Date getRequestTime()
    {
        return this.requestTime;
    }

    public void setRequestTime(Date requestTime)
    {
        this.requestTime = requestTime;
    }

    @Column(name = "SAMPLING_SITE", length = 180)
    public String getSamplingSite()
    {
        return this.samplingSite;
    }

    public void setSamplingSite(String samplingSite)
    {
        this.samplingSite = samplingSite;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "SAMPLING_TIME", length = 7)
    public Date getSamplingTime()
    {
        return this.samplingTime;
    }

    public void setSamplingTime(Date samplingTime)
    {
        this.samplingTime = samplingTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "RECEIVE_TIME", length = 7)
    public Date getReceiveTime()
    {
        return this.receiveTime;
    }

    public void setReceiveTime(Date receiveTime)
    {
        this.receiveTime = receiveTime;
    }

    @Column(name = "SUBMITTING_PERSON_ID", length = 60)
    public String getSubmittingPersonId()
    {
        return this.submittingPersonId;
    }

    public void setSubmittingPersonId(String submittingPersonId)
    {
        this.submittingPersonId = submittingPersonId;
    }

    @Column(name = "SUBMITTING_PERSON_NAME", length = 600)
    public String getSubmittingPersonName()
    {
        return this.submittingPersonName;
    }

    public void setSubmittingPersonName(String submittingPersonName)
    {
        this.submittingPersonName = submittingPersonName;
    }

    @Column(name = "MEMO", length = 300)
    public String getMemo()
    {
        return this.memo;
    }

    public void setMemo(String memo)
    {
        this.memo = memo;
    }

    @Column(name = "SOURCE_SYSTEM_ID", length = 12)
    public String getSourceSystemId()
    {
        return this.sourceSystemId;
    }

    public void setSourceSystemId(String sourceSystemId)
    {
        this.sourceSystemId = sourceSystemId;
    }

    @Column(name = "SEND_SAMPLE_PURPOSE_CODE", length = 56)
    public String getSendSamplePurposeCode()
    {
        return this.sendSamplePurposeCode;
    }

    public void setSendSamplePurposeCode(String sendSamplePurposeCode)
    {
        this.sendSamplePurposeCode = sendSamplePurposeCode;
    }

    @Column(name = "SEND_SAMPLE_PURPOSE_NAME", length = 120)
    public String getSendSamplePurposeName()
    {
        return this.sendSamplePurposeName;
    }

    public void setSendSamplePurposeName(String sendSamplePurposeName)
    {
        this.sendSamplePurposeName = sendSamplePurposeName;
    }

    @Column(name = "SAMPLE_NO", length = 30)
    public String getSampleNo()
    {
        return this.sampleNo;
    }

    public void setSampleNo(String sampleNo)
    {
        this.sampleNo = sampleNo;
    }

    @Column(name = "SAMPLING_PART", length = 600)
    public String getSamplingPart()
    {
        return this.samplingPart;
    }

    public void setSamplingPart(String samplingPart)
    {
        this.samplingPart = samplingPart;
    }

    @Column(name = "EXPECTED_AGE", precision = 4)
    public BigDecimal getExpectedAge()
    {
        return this.expectedAge;
    }

    public void setExpectedAge(BigDecimal expectedAge)
    {
        this.expectedAge = expectedAge;
    }

    @Column(name = "FETAL_NUMBER", precision = 22, scale = 0)
    public BigDecimal getFetalNumber()
    {
        return this.fetalNumber;
    }

    public void setFetalNumber(BigDecimal fetalNumber)
    {
        this.fetalNumber = fetalNumber;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "LAST_MENSTRUAL_TIME", length = 7)
    public Date getLastMenstrualTime()
    {
        return this.lastMenstrualTime;
    }

    public void setLastMenstrualTime(Date lastMenstrualTime)
    {
        this.lastMenstrualTime = lastMenstrualTime;
    }

    @Column(name = "GESTATIONAL_WEEK_CALC_METHOD", length = 120)
    public String getGestationalWeekCalcMethod()
    {
        return this.gestationalWeekCalcMethod;
    }

    public void setGestationalWeekCalcMethod(String gestationalWeekCalcMethod)
    {
        this.gestationalWeekCalcMethod = gestationalWeekCalcMethod;
    }

    @Column(name = "MENOPAUSE", length = 12)
    public String getMenopause()
    {
        return this.menopause;
    }

    public void setMenopause(String menopause)
    {
        this.menopause = menopause;
    }

    @Column(name = "REPORT_NO", length = 30)
    public String getReportNo()
    {
        return this.reportNo;
    }

    public void setReportNo(String reportNo)
    {
        this.reportNo = reportNo;
    }

    @Column(name = "LABORATORY_NO", length = 120)
    public String getLaboratoryNo()
    {
        return this.laboratoryNo;
    }

    public void setLaboratoryNo(String laboratoryNo)
    {
        this.laboratoryNo = laboratoryNo;
    }

    @Column(name = "TEST_RESULTS_DETAIL", length = 1200)
    public String getTestResultsDetail()
    {
        return this.testResultsDetail;
    }

    public void setTestResultsDetail(String testResultsDetail)
    {
        this.testResultsDetail = testResultsDetail;
    }

    @Column(name = "SUPPLIER_ID", length = 12)
    public String getSupplierId()
    {
        return this.supplierId;
    }

    public void setSupplierId(String supplierId)
    {
        this.supplierId = supplierId;
    }

    @Column(name = "SUPPLIER_NAME", length = 120)
    public String getSupplierName()
    {
        return this.supplierName;
    }

    public void setSupplierName(String supplierName)
    {
        this.supplierName = supplierName;
    }

    @Column(name = "SUPPLIER_GENDER", length = 12)
    public String getSupplierGender()
    {
        return this.supplierGender;
    }

    public void setSupplierGender(String supplierGender)
    {
        this.supplierGender = supplierGender;
    }

    @Column(name = "SUPPLIER_AGE", length = 12)
    public String getSupplierAge()
    {
        return this.supplierAge;
    }

    public void setSupplierAge(String supplierAge)
    {
        this.supplierAge = supplierAge;
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

    @Column(name = "BED_NO", length = 30)
    public String getBedNo()
    {
        return this.bedNo;
    }

    public void setBedNo(String bedNo)
    {
        this.bedNo = bedNo;
    }

    @Column(name = "PHENOMENON_PERFORMANCE")
    public String getPhenomenonPerformance()
    {
        return this.phenomenonPerformance;
    }

    public void setPhenomenonPerformance(String phenomenonPerformance)
    {
        this.phenomenonPerformance = phenomenonPerformance;
    }

    @Column(name = "TEST_RESULTS")
    public String getTestResults()
    {
        return this.testResults;
    }

    public void setTestResults(String testResults)
    {
        this.testResults = testResults;
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

    @Column(name = "DIAGNOSIS_TEXT", length = 1350)
    public String getDiagnosisText()
    {
        return this.diagnosisText;
    }

    public void setDiagnosisText(String diagnosisText)
    {
        this.diagnosisText = diagnosisText;
    }

    @Column(name = "REPORT_TYPE_CODE", length = 6)
    public String getReportTypeCode()
    {
        return this.reportTypeCode;
    }

    public void setReportTypeCode(String reportTypeCode)
    {
        this.reportTypeCode = reportTypeCode;
    }

    @Column(name = "REPORT_TYPE_NAME", length = 6)
    public String getReportTypeName()
    {
        return this.reportTypeName;
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

    @Column(name = "SEND_HOSPITAL", length = 256)
    public String getSendHospital()
    {
        return sendHospital;
    }

    public void setSendHospital(String sendHospital)
    {
        this.sendHospital = sendHospital;
    }

    @Column(name = "GATHERER_CODE", length = 12)
    public String getGathererCode()
    {
        return gathererCode;
    }

    public void setGathererCode(String gathererCode)
    {
        this.gathererCode = gathererCode;
    }

    @Column(name = "GATHERER_NAME", length = 196)
    public String getGathererName()
    {
        return gathererName;
    }

    public void setGathererName(String gathererName)
    {
        this.gathererName = gathererName;
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

    @Column(name = "ORG_CODE", length = 12)
    public String getOrgCode()
    {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }
    
    @Column(name = "ORG_NAME", length = 12)
    public String getOrgName()
    {
        return this.orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }
    
    @Column(name = "ROOM_NO", length = 20)
	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

    @Column(name = "METHOD", length = 180)
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
    @Column(name = "CELL_DEVISION_STATUS", length = 6)
	public String getCellDevisionStatus() {
		return cellDevisionStatus;
	}

	public void setCellDevisionStatus(String cellDevisionStatus) {
		this.cellDevisionStatus = cellDevisionStatus;
	}
    @Column(name = "CELL_DEVISION_NAME", length = 12)
	public String getCellDevisionName() {
		return cellDevisionName;
	}

	public void setCellDevisionName(String cellDevisionName) {
		this.cellDevisionName = cellDevisionName;
	}
    @Column(name = "CELL_DEVISION_VALUE", precision = 22, scale = 0)
    public BigDecimal getCellDevisionValue() {
		return cellDevisionValue;
	}

	public void setCellDevisionValue(BigDecimal cellDevisionValue) {
		this.cellDevisionValue = cellDevisionValue;
	}

	@Column(name = "GENE_CODE", length = 6)
	public String getGeneCode() {
		return geneCode;
	}

	public void setGeneCode(String geneCode) {
		this.geneCode = geneCode;
	}
    @Column(name = "GENE_NAME", length = 12)
	public String getGeneName() {
		return geneName;
	}

	public void setGeneName(String geneName) {
		this.geneName = geneName;
	}

	public String getGeneValue() {
		return geneValue;
	}

	public void setGeneValue(String geneValue) {
		this.geneValue = geneValue;
	}
    
}
