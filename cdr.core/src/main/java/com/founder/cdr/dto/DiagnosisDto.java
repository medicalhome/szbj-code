package com.founder.cdr.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.founder.cdr.entity.Diagnosis;
import com.founder.fasf.core.util.daohelper.annotation.Generator;
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

public class DiagnosisDto implements Serializable
{

    private BigDecimal diagnosisSn;

    private BigDecimal visitSn;

    private BigDecimal documentSn;

    private BigDecimal patientSn;

    private BigDecimal reportSn;

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

    private String ci;

    private String visitTypeCode;

    private String visitTypeName;
    // $Author :tong_meng
    // $Date : 2013/12/23 10:50$
    // [BUG]0041102 ADD BEGIN
    private String orgCode;
    private String orgName;
    // [BUG]0041102 ADD END
    public BigDecimal getDiagnosisSn()
    {
        return diagnosisSn;
    }

    public void setDiagnosisSn(BigDecimal diagnosisSn)
    {
        this.diagnosisSn = diagnosisSn;
    }

    public BigDecimal getVisitSn()
    {
        return visitSn;
    }

    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
    }

    public BigDecimal getDocumentSn()
    {
        return documentSn;
    }

    public void setDocumentSn(BigDecimal documentSn)
    {
        this.documentSn = documentSn;
    }

    public BigDecimal getPatientSn()
    {
        return patientSn;
    }

    public void setPatientSn(BigDecimal patientSn)
    {
        this.patientSn = patientSn;
    }

    public BigDecimal getReportSn()
    {
        return reportSn;
    }

    public void setReportSn(BigDecimal reportSn)
    {
        this.reportSn = reportSn;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public void setPatientDomain(String patientDomain)
    {
        this.patientDomain = patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public void setPatientLid(String patientLid)
    {
        this.patientLid = patientLid;
    }

    public String getDiagnosisDoctorName()
    {
        return diagnosisDoctorName;
    }

    public void setDiagnosisDoctorName(String diagnosisDoctorName)
    {
        this.diagnosisDoctorName = diagnosisDoctorName;
    }

    public Date getDiagnosisDate()
    {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Date diagnosisDate)
    {
        this.diagnosisDate = diagnosisDate;
    }

    public String getDiagnosisSequence()
    {
        return diagnosisSequence;
    }

    public void setDiagnosisSequence(String diagnosisSequence)
    {
        this.diagnosisSequence = diagnosisSequence;
    }

    public String getDiagnosisTypeName()
    {
        return diagnosisTypeName;
    }

    public void setDiagnosisTypeName(String diagnosisTypeName)
    {
        this.diagnosisTypeName = diagnosisTypeName;
    }

    public String getDiseaseCode()
    {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode)
    {
        this.diseaseCode = diseaseCode;
    }

    public String getDiseaseName()
    {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName)
    {
        this.diseaseName = diseaseName;
    }

    public String getDiagnoiseBasisName()
    {
        return diagnoiseBasisName;
    }

    public void setDiagnoiseBasisName(String diagnoiseBasisName)
    {
        this.diagnoiseBasisName = diagnoiseBasisName;
    }

    public String getUncertainFlag()
    {
        return uncertainFlag;
    }

    public void setUncertainFlag(String uncertainFlag)
    {
        this.uncertainFlag = uncertainFlag;
    }

    public String getContagiousFlag()
    {
        return contagiousFlag;
    }

    public void setContagiousFlag(String contagiousFlag)
    {
        this.contagiousFlag = contagiousFlag;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public BigDecimal getUpdateCount()
    {
        return updateCount;
    }

    public void setUpdateCount(BigDecimal updateCount)
    {
        this.updateCount = updateCount;
    }

    public String getDeleteFlag()
    {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

    public Date getDeleteTime()
    {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    public String getMainDiagnosisFlag()
    {
        return mainDiagnosisFlag;
    }

    public void setMainDiagnosisFlag(String mainDiagnosisFlag)
    {
        this.mainDiagnosisFlag = mainDiagnosisFlag;
    }

    public String getDiagnosticDeptId()
    {
        return diagnosticDeptId;
    }

    public void setDiagnosticDeptId(String diagnosticDeptId)
    {
        this.diagnosticDeptId = diagnosticDeptId;
    }

    public String getDiagnosticDeptName()
    {
        return diagnosticDeptName;
    }

    public void setDiagnosticDeptName(String diagnosticDeptName)
    {
        this.diagnosticDeptName = diagnosticDeptName;
    }

    public String getDiagnosisDoctorId()
    {
        return diagnosisDoctorId;
    }

    public void setDiagnosisDoctorId(String diagnosisDoctorId)
    {
        this.diagnosisDoctorId = diagnosisDoctorId;
    }

    public String getDiagnosisTypeCode()
    {
        return diagnosisTypeCode;
    }

    public void setDiagnosisTypeCode(String diagnosisTypeCode)
    {
        this.diagnosisTypeCode = diagnosisTypeCode;
    }

    public String getDiagnoiseBasisCode()
    {
        return diagnoiseBasisCode;
    }

    public void setDiagnoiseBasisCode(String diagnoiseBasisCode)
    {
        this.diagnoiseBasisCode = diagnoiseBasisCode;
    }

    public String getCi()
    {
        return ci;
    }

    public void setCi(String ci)
    {
        this.ci = ci;
    }

    public String getVisitTypeCode()
    {
        return visitTypeCode;
    }

    public void setVisitTypeCode(String visitTypeCode)
    {
        this.visitTypeCode = visitTypeCode;
    }

    public String getVisitTypeName()
    {
        return visitTypeName;
    }

    public void setVisitTypeName(String visitTypeName)
    {
        this.visitTypeName = visitTypeName;
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }
}
