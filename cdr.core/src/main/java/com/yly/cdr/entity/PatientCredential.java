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
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

import java.io.Serializable;

@Entity
@Table(name = "PATIENT_CREDENTIAL")
public class PatientCredential implements Serializable
{

    private BigDecimal credentialSn;

    private BigDecimal patientSn;

    private String credentialType;

    private String credentialNo;

    private Date effectiveDate;

    private Date expirationDate;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String credentialTypeName;
    
    private String createby;

    private String updateby;

    private String deleteby;

    @Id
    // $Author: jin_peng
    // $Date: 2012/07/03 17:56
    // [BUG]0007698 ADD BEGIN
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_CREDENTIAL") })
    // [BUG]0007698 ADD END
    public BigDecimal getCredentialSn()
    {
        return this.credentialSn;
    }

    public void setCredentialSn(BigDecimal credentialSn)
    {
        this.credentialSn = credentialSn;
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

    @Column(name = "CREDENTIAL_TYPE", length = 6)
    public String getCredentialType()
    {
        return this.credentialType;
    }

    public void setCredentialType(String credentialType)
    {
        this.credentialType = credentialType;
    }

    @Column(name = "CREDENTIAL_NO", nullable = false, length = 30)
    public String getCredentialNo()
    {
        return this.credentialNo;
    }

    public void setCredentialNo(String credentialNo)
    {
        this.credentialNo = credentialNo;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "EFFECTIVE_DATE", length = 7)
    public Date getEffectiveDate()
    {
        return this.effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "EXPIRATION_DATE", length = 7)
    public Date getExpirationDate()
    {
        return this.expirationDate;
    }

    public void setExpirationDate(Date expirationDate)
    {
        this.expirationDate = expirationDate;
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

    @Column(name = "CREDENTIAL_TYPE_NAME", length = 40)
    public String getCredentialTypeName()
    {
        return this.credentialTypeName;
    }

    public void setCredentialTypeName(String credentialTypeName)
    {
        this.credentialTypeName = credentialTypeName;
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
