package com.founder.cdr.entity;

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
@Table(name = "RISK_INFORMATION")
public class RiskInformation implements Serializable
{

    private BigDecimal riskSn;

    private BigDecimal patientSn;

    private String riskCode;

    private String riskName;

    private Date expirationDate;

    private String infoSource;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String patientDomain;

    private String patientLid;

    private String createby;

    private String updateby;

    private String deleteby;
    
    private String orgCode;
    
    private String orgName;
    
    public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_RISK_INFORMATION") })
    public BigDecimal getRiskSn()
    {
        return this.riskSn;
    }

    public void setRiskSn(BigDecimal riskSn)
    {
        this.riskSn = riskSn;
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

    @Column(name = "RISK_CODE", length = 6)
    public String getRiskCode()
    {
        return this.riskCode;
    }

    public void setRiskCode(String riskCode)
    {
        this.riskCode = riskCode;
    }

    @Column(name = "RISK_NAME", length = 180)
    public String getRiskName()
    {
        return this.riskName;
    }

    public void setRiskName(String riskName)
    {
        this.riskName = riskName;
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

    @Column(name = "INFO_SOURCE", length = 6)
    public String getInfoSource()
    {
        return this.infoSource;
    }

    public void setInfoSource(String infoSource)
    {
        this.infoSource = infoSource;
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

    @Column(name = "PATIENT_DOMAIN", length = 12)
    public String getPatientDomain()
    {
        return this.patientDomain;
    }

    public void setPatientDomain(String patientDomain)
    {
        this.patientDomain = patientDomain;
    }

    @Column(name = "PATIENT_LID", length = 200)
    public String getPatientLid()
    {
        return this.patientLid;
    }

    public void setPatientLid(String patientLid)
    {
        this.patientLid = patientLid;
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
