package com.yly.cdr.entity;

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

import java.io.Serializable;

@Entity
@Table(name = "PRESCRIPTION")
public class Prescription implements Serializable
{

    private BigDecimal prescriptionSn;

    private BigDecimal visitSn;

    private BigDecimal patientSn;

    private String patientDomain;

    private String patientLid;

    private String prescriptionNo;

    private BigDecimal price;

    private Date orderTime;

    private String reviewPersonName;

    private Date reviewTime;

    private String diagnosis;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String medViewCode;

    private String medViewName;

    private String medViewFlag;

    private String patientAge;

    private String prescriptionClassCode;

    private String prescriptionClassName;

    private String prescriptionTypeCode;

    private String prescriptionTypeName;

    private String reviewPersonId;

    private String createby;

    private String updateby;

    private String deleteby;
    
    private String orgCode;
    
    private String orgName;
    
    @Id
    // @GeneratedValue(generator = "guid-generator")
    // @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getPrescriptionSn()
    {
        return this.prescriptionSn;
    }

    public void setPrescriptionSn(BigDecimal prescriptionSn)
    {
        this.prescriptionSn = prescriptionSn;
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

    @Column(name = "PRESCRIPTION_NO", nullable = false, length = 100)
    public String getPrescriptionNo()
    {
        return this.prescriptionNo;
    }

    public void setPrescriptionNo(String prescriptionNo)
    {
        this.prescriptionNo = prescriptionNo;
    }

    @Column(name = "PRICE", precision = 8)
    public BigDecimal getPrice()
    {
        return this.price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "ORDER_TIME", length = 7)
    public Date getOrderTime()
    {
        return this.orderTime;
    }

    public void setOrderTime(Date orderTime)
    {
        this.orderTime = orderTime;
    }

    @Column(name = "REVIEW_PERSON_NAME", length = 192)
    public String getReviewPersonName()
    {
        return this.reviewPersonName;
    }

    public void setReviewPersonName(String reviewPersonName)
    {
        this.reviewPersonName = reviewPersonName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "REVIEW_TIME", length = 7)
    public Date getReviewTime()
    {
        return this.reviewTime;
    }

    public void setReviewTime(Date reviewTime)
    {
        this.reviewTime = reviewTime;
    }

    @Column(name = "DIAGNOSIS")
    public String getDiagnosis()
    {
        return this.diagnosis;
    }

    public void setDiagnosis(String diagnosis)
    {
        this.diagnosis = diagnosis;
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

    @Column(name = "MED_VIEW_CODE", length = 32)
    public String getMedViewCode()
    {
        return this.medViewCode;
    }

    public void setMedViewCode(String medViewCode)
    {
        this.medViewCode = medViewCode;
    }

    @Column(name = "MED_VIEW_NAME", length = 32)
    public String getMedViewName()
    {
        return this.medViewName;
    }

    public void setMedViewName(String medViewName)
    {
        this.medViewName = medViewName;
    }

    @Column(name = "MED_VIEW_FLAG", length = 1)
    public String getMedViewFlag()
    {
        return this.medViewFlag;
    }

    public void setMedViewFlag(String medViewFlag)
    {
        this.medViewFlag = medViewFlag;
    }

    @Column(name = "PATIENT_AGE", length = 100)
    public String getPatientAge()
    {
        return this.patientAge;
    }

    public void setPatientAge(String patientAge)
    {
        this.patientAge = patientAge;
    }

    @Column(name = "PRESCRIPTION_CLASS_CODE", length = 12)
    public String getPrescriptionClassCode()
    {
        return this.prescriptionClassCode;
    }

    public void setPrescriptionClassCode(String prescriptionClassCode)
    {
        this.prescriptionClassCode = prescriptionClassCode;
    }

    @Column(name = "PRESCRIPTION_CLASS_NAME", length = 192)
    public String getPrescriptionClassName()
    {
        return this.prescriptionClassName;
    }

    public void setPrescriptionClassName(String prescriptionClassName)
    {
        this.prescriptionClassName = prescriptionClassName;
    }

    @Column(name = "PRESCRIPTION_TYPE_CODE", length = 12)
    public String getPrescriptionTypeCode()
    {
        return this.prescriptionTypeCode;
    }

    public void setPrescriptionTypeCode(String prescriptionTypeCode)
    {
        this.prescriptionTypeCode = prescriptionTypeCode;
    }

    @Column(name = "PRESCRIPTION_TYPE_NAME", length = 100)
    public String getPrescriptionTypeName()
    {
        return this.prescriptionTypeName;
    }

    public void setPrescriptionTypeName(String prescriptionTypeName)
    {
        this.prescriptionTypeName = prescriptionTypeName;
    }

    @Column(name = "REVIEW_PERSON_ID", length = 12)
    public String getReviewPersonId()
    {
        return this.reviewPersonId;
    }

    public void setReviewPersonId(String reviewPersonId)
    {
        this.reviewPersonId = reviewPersonId;
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
