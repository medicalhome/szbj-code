package com.yly.cdr.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Id;

import java.io.Serializable;

@Entity
@Table(name = "MEDICATION_VERDICT")
public class MedicationVerdict implements Serializable
{

    private BigDecimal verdictSn;

    private BigDecimal visitSn;
    
    private BigDecimal patientSn;
    
    private String patientLid;
    
    private String patientDomain;
    
    private String verdictLid;
    
    private String verdictGroup;
    
    private String orgCode;
    
    private String orgName;
    
    private String drugCode1;
    
    private String drugName1;
    
    private String drugCode2;
    
    private String drugName2;
    
    private String resultTypeCode;
    
    private String resultTypeName;
    
    private String resultText;
    
    private Date resultDate;
    
    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String createby;

    private String updateby;

    private String deleteby;
    
    @Id
    public BigDecimal getVerdictSn()
    {
        return verdictSn;
    }

    public void setVerdictSn(BigDecimal verdictSn)
    {
        this.verdictSn = verdictSn;
    }

    @Column(name = "VISIT_SN", precision = 22, scale = 0)
    public BigDecimal getVisitSn()
    {
        return visitSn;
    }

    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
    }

    @Column(name = "PATIENT_SN", precision = 22, scale = 0)
    public BigDecimal getPatientSn()
    {
        return patientSn;
    }

    public void setPatientSn(BigDecimal patientSn)
    {
        this.patientSn = patientSn;
    }

    @Column(name = "PATIENT_LID", nullable = false, length = 20)
    public String getPatientLid()
    {
        return patientLid;
    }

    public void setPatientLid(String patientLid)
    {
        this.patientLid = patientLid;
    }

    @Column(name = "PATIENT_DOMAIN", nullable = false, length = 6)
    public String getPatientDomain()
    {
        return patientDomain;
    }

    public void setPatientDomain(String patientDomain)
    {
        this.patientDomain = patientDomain;
    }

    @Column(name = "VERDICT_LID", nullable = false, length = 32)
    public String getVerdictLid()
    {
        return verdictLid;
    }

    public void setVerdictLid(String verdictLid)
    {
        this.verdictLid = verdictLid;
    }

    @Column(name = "VERDICT_GROUP", nullable = false, length = 32)
    public String getVerdictGroup()
    {
        return verdictGroup;
    }

    public void setVerdictGroup(String verdictGroup)
    {
        this.verdictGroup = verdictGroup;
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

    @Column(name = "ORG_CODE", length = 50)
    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

    @Column(name = "DRUG_CODE1", length = 32)
    public String getDrugCode1()
    {
        return drugCode1;
    }

    public void setDrugCode1(String drugCode1)
    {
        this.drugCode1 = drugCode1;
    }

    @Column(name = "DRUG_NAME1", length = 300)
    public String getDrugName1()
    {
        return drugName1;
    }

    public void setDrugName1(String drugName1)
    {
        this.drugName1 = drugName1;
    }

    @Column(name = "DRUG_CODE2", length = 32)
    public String getDrugCode2()
    {
        return drugCode2;
    }

    public void setDrugCode2(String drugCode2)
    {
        this.drugCode2 = drugCode2;
    }

    @Column(name = "DRUG_NAME2", length = 300)
    public String getDrugName2()
    {
        return drugName2;
    }

    public void setDrugName2(String drugName2)
    {
        this.drugName2 = drugName2;
    }

    @Column(name = "RESULT_TYPE_CODE", nullable = false, length = 12)
    public String getResultTypeCode()
    {
        return resultTypeCode;
    }

    public void setResultTypeCode(String resultTypeCode)
    {
        this.resultTypeCode = resultTypeCode;
    }

    @Column(name = "RESULT_TYPE_NAME", length = 192)
    public String getResultTypeName()
    {
        return resultTypeName;
    }

    public void setResultTypeName(String resultTypeName)
    {
        this.resultTypeName = resultTypeName;
    }

    @Column(name = "RESULT_TEXT")
    public String getResultText()
    {
        return resultText;
    }

    public void setResultText(String resultText)
    {
        this.resultText = resultText;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "RESULT_DATE", nullable = false, length = 7)
    public Date getResultDate()
    {
        return resultDate;
    }

    public void setResultDate(Date resultDate)
    {
        this.resultDate = resultDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_TIME", length = 7)
    public Date getCreateTime()
    {
        return this.createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATE_TIME", length = 7)
    public Date getUpdateTime()
    {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    @Column(name = "UPDATE_COUNT", precision = 22, scale = 0)
    public BigDecimal getUpdateCount()
    {
        return this.updateCount;
    }

    public void setUpdateCount(BigDecimal updateCount)
    {
        this.updateCount = updateCount;
    }

    @Column(name = "DELETE_FLAG", length = 1)
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

}
