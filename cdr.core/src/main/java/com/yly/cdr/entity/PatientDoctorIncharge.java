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
@Table(name = "patient_doctor_incharge")
public class PatientDoctorIncharge implements Serializable
{
	private static final long serialVersionUID = 1L;

	private BigDecimal chargeSn;

    private BigDecimal visitSn;
    
    private BigDecimal patientSn;
    
    private String orgCode;
    
    private String orgName;
    
    private String patientDomain;

    private String doctorId;

    private String doctorName;

    private Date takeoverTime;

    private Date exitTime;

    private String chargeFlag;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String createby;

    private String updateby;

    private String deleteby;
    
    private String titleTypeName;
        
    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_PATIENT_DOCTOR_INCHARGE") })
    public BigDecimal getChargeSn() {
		return chargeSn;
	}

	public void setChargeSn(BigDecimal chargeSn) {
		this.chargeSn = chargeSn;
	}

	@Column(name = "VISIT_SN", length = 12)
    public BigDecimal getVisitSn()
    {
        return this.visitSn;
    }

    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
    }

    @Column(name = "PATIENT_SN", length = 12)
    public BigDecimal getPatientSn() {
		return patientSn;
	}

	public void setPatientSn(BigDecimal patientSn) {
		this.patientSn = patientSn;
	}

	@Column(name = "ORG_CODE", length = 50)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "ORG_NAME", length = 50)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "PATIENT_DOMAIN", length = 12)
	public String getPatientDomain() {
		return patientDomain;
	}

	public void setPatientDomain(String patientDomain) {
		this.patientDomain = patientDomain;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "DOCTOR_ID", nullable = false, length = 12)
	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	@Column(name = "DOCTOR_NAME", length = 192)
	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	@Temporal(TemporalType.DATE)
    @Column(name = "TAKEOVER_TIME", nullable = false, length = 7)
	public Date getTakeoverTime() {
		return takeoverTime;
	}

	public void setTakeoverTime(Date takeoverTime) {
		this.takeoverTime = takeoverTime;
	}

	@Temporal(TemporalType.DATE)
    @Column(name = "EXIT_TIME", nullable = false, length = 7)
	public Date getExitTime() {
		return exitTime;
	}

	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}

	@Column(name = "CHARGE_FLAG", nullable = false, length = 1)
	public String getChargeFlag() {
		return chargeFlag;
	}

	public void setChargeFlag(String chargeFlag) {
		this.chargeFlag = chargeFlag;
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

    @Column(name = "UPDATEBY", length = 12)
    public String getUpdateby()
    {
        return this.updateby;
    }

    public void setUpdateby(String updateby)
    {
        this.updateby = updateby;
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

    @Column(name = "DELETEBY", length = 12)
    public String getDeleteby()
    {
        return this.deleteby;
    }

    public void setDeleteby(String deleteby)
    {
        this.deleteby = deleteby;
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

    @Column(name = "DELETE_FLAG", nullable = false, length = 1)
    public String getDeleteFlag()
    {
        return this.deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }
    
    @Column(name = "TITLE_TYPE_NAME", nullable = false, length = 15)
	public String getTitleTypeName() {
		return titleTypeName;
	}

	public void setTitleTypeName(String titleTypeName) {
		this.titleTypeName = titleTypeName;
	}
}
