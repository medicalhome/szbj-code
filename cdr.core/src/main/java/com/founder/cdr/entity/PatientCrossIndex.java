package com.founder.cdr.entity;

import java.math.BigDecimal;
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
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

import java.io.Serializable;

@Entity
@Table(name = "PATIENT_CROSS_INDEX")
public class PatientCrossIndex implements Serializable
{

    private BigDecimal crossIndexSn;

    private String patientDomain;

    private String patientLid;

    private String patientEid;

    private String medicalCardNo;

    private String outpatientNo;

    private String inpatientNo;

    private String imagingNo;

    private String insuranceCardNo;

    private String versionNo;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;
    
    private String createby;

    private String updateby;

    private String deleteby;
    
    private String orgCode;
    
    private String orgName;

    @Id
    // $Author: jin_peng
    // $Date: 2012/07/03 17:56
    // [BUG]0007698 ADD BEGIN
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_CROSS_INDEX") })
    // [BUG]0007698 ADD END
    public BigDecimal getCrossIndexSn()
    {
        return this.crossIndexSn;
    }

    public void setCrossIndexSn(BigDecimal crossIndexSn)
    {
        this.crossIndexSn = crossIndexSn;
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

    @Column(name = "PATIENT_EID", nullable = false, length = 30)
    public String getPatientEid()
    {
        return this.patientEid;
    }

    public void setPatientEid(String patientEid)
    {
        this.patientEid = patientEid;
    }

    @Column(name = "MEDICAL_CARD_NO", length = 30)
    public String getMedicalCardNo()
    {
        return this.medicalCardNo;
    }

    public void setMedicalCardNo(String medicalCardNo)
    {
        this.medicalCardNo = medicalCardNo;
    }

    @Column(name = "OUTPATIENT_NO", length = 18)
    public String getOutpatientNo()
    {
        return this.outpatientNo;
    }

    public void setOutpatientNo(String outpatientNo)
    {
        this.outpatientNo = outpatientNo;
    }

    @Column(name = "INPATIENT_NO", length = 18)
    public String getInpatientNo()
    {
        return this.inpatientNo;
    }

    public void setInpatientNo(String inpatientNo)
    {
        this.inpatientNo = inpatientNo;
    }

    @Column(name = "IMAGING_NO", length = 20)
    public String getImagingNo()
    {
        return this.imagingNo;
    }

    public void setImagingNo(String imagingNo)
    {
        this.imagingNo = imagingNo;
    }

    @Column(name = "INSURANCE_CARD_NO", length = 20)
    public String getInsuranceCardNo()
    {
        return this.insuranceCardNo;
    }

    public void setInsuranceCardNo(String insuranceCardNo)
    {
        this.insuranceCardNo = insuranceCardNo;
    }

    @Column(name = "VERSION_NO", length = 64)
    public String getVersionNo()
    {
        return this.versionNo;
    }

    public void setVersionNo(String versionNo)
    {
        this.versionNo = versionNo;
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

}
