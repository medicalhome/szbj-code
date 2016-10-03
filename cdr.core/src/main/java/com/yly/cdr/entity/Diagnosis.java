package com.yly.cdr.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.founder.fasf.core.util.daohelper.annotation.Generator;
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

@Entity
@Table(name = "DIAGNOSIS")
public class Diagnosis implements Serializable
{

    private BigDecimal diagnosisSn;

    private BigDecimal visitSn;

    private BigDecimal documentSn;

    private BigDecimal patientSn;

    private String patientDomain;

    private String patientLid;

    private String diagnosisDoctorName;

    private Date diagnosisDate;

    private String diagnosisSequence;

    private String diagnosisTypeName;

    private String diseaseCode;

    private String diseaseName;

    private String diagnoiseBasisName;

    private String uncertainFlag;

    private String contagiousFlag;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String mainDiagnosisFlag;

    private String diagnosticDeptId;

    private String diagnosticDeptName;

    private String diagnosisDoctorId;

    private String diagnosisTypeCode;

    private String diagnoiseBasisCode;
    
    private String createby;

    private String updateby;

    private String deleteby;
    
    // $Author:tong_meng
    // $Date:2013/12/03 11:00
    // [BUG]0040270 ADD BEGIN
    // 医疗机构代码
    private String orgCode;
    // 医疗机构名称
    private String orgName;
    // [BUG]0040270 ADD END

    private String serialnumber;
    
    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_DIAGNOSIS") })
    public BigDecimal getDiagnosisSn()
    {
        return this.diagnosisSn;
    }

    public void setDiagnosisSn(BigDecimal diagnosisSn)
    {
        this.diagnosisSn = diagnosisSn;
    }

    @Column(name = "VISIT_SN", nullable = false, precision = 22, scale = 0)
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

    @Column(name = "PATIENT_LID", nullable = false, length = 12)
    public String getPatientLid()
    {
        return this.patientLid;
    }

    public void setPatientLid(String patientLid)
    {
        this.patientLid = patientLid;
    }

    @Column(name = "DIAGNOSIS_DOCTOR_NAME", length = 192)
    public String getDiagnosisDoctorName()
    {
        return this.diagnosisDoctorName;
    }

    public void setDiagnosisDoctorName(String diagnosisDoctorName)
    {
        this.diagnosisDoctorName = diagnosisDoctorName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DIAGNOSIS_DATE", nullable = false, length = 7)
    public Date getDiagnosisDate()
    {
        return this.diagnosisDate;
    }

    public void setDiagnosisDate(Date diagnosisDate)
    {
        this.diagnosisDate = diagnosisDate;
    }

    @Column(name = "DIAGNOSIS_SEQUENCE", length = 2)
    public String getDiagnosisSequence()
    {
        return this.diagnosisSequence;
    }

    public void setDiagnosisSequence(String diagnosisSequence)
    {
        this.diagnosisSequence = diagnosisSequence;
    }

    @Column(name = "DIAGNOSIS_TYPE_NAME", length = 32)
    public String getDiagnosisTypeName()
    {
        return this.diagnosisTypeName;
    }

    public void setDiagnosisTypeName(String diagnosisTypeName)
    {
        this.diagnosisTypeName = diagnosisTypeName;
    }

    @Column(name = "DISEASE_CODE", length = 128)
    public String getDiseaseCode()
    {
        return this.diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode)
    {
        this.diseaseCode = diseaseCode;
    }

    @Column(name = "DISEASE_NAME", nullable = false, length = 600)
    public String getDiseaseName()
    {
        return this.diseaseName;
    }

    public void setDiseaseName(String diseaseName)
    {
        this.diseaseName = diseaseName;
    }

    @Column(name = "DIAGNOISE_BASIS_NAME", length = 256)
    public String getDiagnoiseBasisName()
    {
        return this.diagnoiseBasisName;
    }

    public void setDiagnoiseBasisName(String diagnoiseBasisName)
    {
        this.diagnoiseBasisName = diagnoiseBasisName;
    }

    @Column(name = "UNCERTAIN_FLAG", length = 1)
    public String getUncertainFlag()
    {
        return this.uncertainFlag;
    }

    public void setUncertainFlag(String uncertainFlag)
    {
        this.uncertainFlag = uncertainFlag;
    }

    @Column(name = "CONTAGIOUS_FLAG", length = 1)
    public String getContagiousFlag()
    {
        return this.contagiousFlag;
    }

    public void setContagiousFlag(String contagiousFlag)
    {
        this.contagiousFlag = contagiousFlag;
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

    @Column(name = "MAIN_DIAGNOSIS_FLAG", length = 1)
    public String getMainDiagnosisFlag()
    {
        return this.mainDiagnosisFlag;
    }

    public void setMainDiagnosisFlag(String mainDiagnosisFlag)
    {
        this.mainDiagnosisFlag = mainDiagnosisFlag;
    }

    @Column(name = "DIAGNOSTIC_DEPT_ID", length = 12)
    public String getDiagnosticDeptId()
    {
        return this.diagnosticDeptId;
    }

    public void setDiagnosticDeptId(String diagnosticDeptId)
    {
        this.diagnosticDeptId = diagnosticDeptId;
    }

    @Column(name = "DIAGNOSTIC_DEPT_NAME", length = 192)
    public String getDiagnosticDeptName()
    {
        return this.diagnosticDeptName;
    }

    public void setDiagnosticDeptName(String diagnosticDeptName)
    {
        this.diagnosticDeptName = diagnosticDeptName;
    }

    @Column(name = "DIAGNOSIS_DOCTOR_ID", length = 12)
    public String getDiagnosisDoctorId()
    {
        return this.diagnosisDoctorId;
    }

    public void setDiagnosisDoctorId(String diagnosisDoctorId)
    {
        this.diagnosisDoctorId = diagnosisDoctorId;
    }

    @Column(name = "DIAGNOSIS_TYPE_CODE", length = 16)
    public String getDiagnosisTypeCode()
    {
        return this.diagnosisTypeCode;
    }

    public void setDiagnosisTypeCode(String diagnosisTypeCode)
    {
        this.diagnosisTypeCode = diagnosisTypeCode;
    }

    @Column(name = "DIAGNOISE_BASIS_CODE", length = 16)
    public String getDiagnoiseBasisCode()
    {
        return this.diagnoiseBasisCode;
    }

    public void setDiagnoiseBasisCode(String diagnoiseBasisCode)
    {
        this.diagnoiseBasisCode = diagnoiseBasisCode;
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

    @Column(name = "SERIALNUMBER", length = 64)
	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
}
