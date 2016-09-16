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
@Table(name = "PATIENT_CONTACT")
public class PatientContact implements Serializable
{

    private BigDecimal contactSn;

    private BigDecimal patientSn;

    private String relationship;

    private String contactName;

    private String contactGender;

    private String contactTelephone;

    private String contactMobile;

    private String contactAddress;

    private String postCode;

    private String contactEmail;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String districtCode;

    private String districtName;

    private String subDistrictName;

    private String relationshipName;

    private String contactGenderName;
    
    private String createby;

    private String updateby;

    private String deleteby;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_CONTACT") })
    public BigDecimal getContactSn()
    {
        return this.contactSn;
    }

    public void setContactSn(BigDecimal contactSn)
    {
        this.contactSn = contactSn;
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

    @Column(name = "RELATIONSHIP", length = 6)
    public String getRelationship()
    {
        return this.relationship;
    }

    public void setRelationship(String relationship)
    {
        this.relationship = relationship;
    }

    @Column(name = "CONTACT_NAME", length = 200)
    public String getContactName()
    {
        return this.contactName;
    }

    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }

    @Column(name = "CONTACT_GENDER", length = 6)
    public String getContactGender()
    {
        return this.contactGender;
    }

    public void setContactGender(String contactGender)
    {
        this.contactGender = contactGender;
    }

    @Column(name = "CONTACT_TELEPHONE", length = 50)
    public String getContactTelephone()
    {
        return this.contactTelephone;
    }

    public void setContactTelephone(String contactTelephone)
    {
        this.contactTelephone = contactTelephone;
    }

    @Column(name = "CONTACT_MOBILE", length = 20)
    public String getContactMobile()
    {
        return this.contactMobile;
    }

    public void setContactMobile(String contactMobile)
    {
        this.contactMobile = contactMobile;
    }

    @Column(name = "CONTACT_ADDRESS", length = 200)
    public String getContactAddress()
    {
        return this.contactAddress;
    }

    public void setContactAddress(String contactAddress)
    {
        this.contactAddress = contactAddress;
    }

    @Column(name = "POST_CODE", length = 10)
    public String getPostCode()
    {
        return this.postCode;
    }

    public void setPostCode(String postCode)
    {
        this.postCode = postCode;
    }

    @Column(name = "CONTACT_EMAIL", length = 64)
    public String getContactEmail()
    {
        return this.contactEmail;
    }

    public void setContactEmail(String contactEmail)
    {
        this.contactEmail = contactEmail;
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

    @Column(name = "DISTRICT_CODE", length = 12)
    public String getDistrictCode()
    {
        return this.districtCode;
    }

    public void setDistrictCode(String districtCode)
    {
        this.districtCode = districtCode;
    }

    @Column(name = "DISTRICT_NAME", length = 255)
    public String getDistrictName()
    {
        return districtName;
    }

    public void setDistrictName(String districtName)
    {
        this.districtName = districtName;
    }

    @Column(name = "SUB_DISTRICT_NAME", length = 200)
    public String getSubDistrictName()
    {
        return this.subDistrictName;
    }

    public void setSubDistrictName(String subDistrictName)
    {
        this.subDistrictName = subDistrictName;
    }

    @Column(name = "RELATIONSHIP_NAME", length = 40)
    public String getRelationshipName()
    {
        return this.relationshipName;
    }

    public void setRelationshipName(String relationshipName)
    {
        this.relationshipName = relationshipName;
    }

    @Column(name = "CONTACT_GENDER_NAME", length = 40)
    public String getContactGenderName()
    {
        return this.contactGenderName;
    }

    public void setContactGenderName(String contactGenderName)
    {
        this.contactGenderName = contactGenderName;
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
