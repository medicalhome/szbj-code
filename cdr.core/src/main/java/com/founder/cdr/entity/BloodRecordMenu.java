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
@Table(name = "blood_record_menu")
public class BloodRecordMenu implements Serializable
{
	private static final long serialVersionUID = 1L;

	private BigDecimal menuSn;

    private BigDecimal requestSn;
    
    private BigDecimal visitSn;
    
    private BigDecimal patientSn;
    
    private String patientLid;
    
    private String menuLid;
    
    private String orgCode;
    
    private String orgName;
    
    private String patientDomain;
    
    private String requestBloodAboCode;
    
    private String requestBloodAboName;
    
    private String requestBloodRhCode;
    
    private String requestBloodRhName;
    
    private String recheckBloodAboCode;
    
    private String recheckBloodAboName;
    
    private String recheckBloodRhCode;
    
    private String recheckBloodRhName;
    
    private String remark;
    
    private Date effectiveTime;
    
    private String createby;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String updateby;

    private String deleteby;
    
    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "seq_blood_record_menu") })
	public BigDecimal getMenuSn() {
		return menuSn;
	}

	public void setMenuSn(BigDecimal menuSn) {
		this.menuSn = menuSn;
	}

	@Column(name = "request_sn", length = 12)
	public BigDecimal getRequestSn() {
		return requestSn;
	}

	public void setRequestSn(BigDecimal requestSn) {
		this.requestSn = requestSn;
	}

	@Column(name = "visit_sn", length = 12)
	public BigDecimal getVisitSn() {
		return visitSn;
	}

	public void setVisitSn(BigDecimal visitSn) {
		this.visitSn = visitSn;
	}

	@Column(name = "patient_sn", length = 12)
	public BigDecimal getPatientSn() {
		return patientSn;
	}

	public void setPatientSn(BigDecimal patientSn) {
		this.patientSn = patientSn;
	}

	@Column(name = "patient_lid", length = 20)
	public String getPatientLid() {
		return patientLid;
	}

	public void setPatientLid(String patientLid) {
		this.patientLid = patientLid;
	}

	@Column(name = "menu_lid", length = 50)
	public String getMenuLid() {
		return menuLid;
	}

	public void setMenuLid(String menuLid) {
		this.menuLid = menuLid;
	}

	@Column(name = "org_code", length = 20)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "org_name", length = 50)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "patient_domain", length = 12)
	public String getPatientDomain() {
		return patientDomain;
	}

	public void setPatientDomain(String patientDomain) {
		this.patientDomain = patientDomain;
	}
	
	@Column(name = "request_blood_abo_code", length = 20)
	public String getRequestBloodAboCode() {
		return requestBloodAboCode;
	}

	public void setRequestBloodAboCode(String requestBloodAboCode) {
		this.requestBloodAboCode = requestBloodAboCode;
	}

	@Column(name = "request_blood_abo_name", length = 50)
	public String getRequestBloodAboName() {
		return requestBloodAboName;
	}

	public void setRequestBloodAboName(String requestBloodAboName) {
		this.requestBloodAboName = requestBloodAboName;
	}

	@Column(name = "request_blood_rh_code", length = 20)
	public String getRequestBloodRhCode() {
		return requestBloodRhCode;
	}

	public void setRequestBloodRhCode(String requestBloodRhCode) {
		this.requestBloodRhCode = requestBloodRhCode;
	}
	
	@Column(name = "request_blood_rh_name", length = 50)
	public String getRequestBloodRhName() {
		return requestBloodRhName;
	}

	public void setRequestBloodRhName(String requestBloodRhName) {
		this.requestBloodRhName = requestBloodRhName;
	}

	@Column(name = "recheck_blood_abo_code", length = 20)
	public String getRecheckBloodAboCode() {
		return recheckBloodAboCode;
	}

	public void setRecheckBloodAboCode(String recheckBloodAboCode) {
		this.recheckBloodAboCode = recheckBloodAboCode;
	}

	@Column(name = "recheck_blood_abo_name", length = 50)
	public String getRecheckBloodAboName() {
		return recheckBloodAboName;
	}

	public void setRecheckBloodAboName(String recheckBloodAboName) {
		this.recheckBloodAboName = recheckBloodAboName;
	}

	@Column(name = "recheck_blood_rh_code", length = 20)
	public String getRecheckBloodRhCode() {
		return recheckBloodRhCode;
	}

	public void setRecheckBloodRhCode(String recheckBloodRhCode) {
		this.recheckBloodRhCode = recheckBloodRhCode;
	}

	@Column(name = "recheck_blood_rh_name", length = 50)
	public String getRecheckBloodRhName() {
		return recheckBloodRhName;
	}

	public void setRecheckBloodRhName(String recheckBloodRhName) {
		this.recheckBloodRhName = recheckBloodRhName;
	}


	@Column(name = "remark", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.DATE)
    @Column(name = "effective_time", nullable = false, length = 7)
	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "createby", length = 12)
    public String getCreateby()
    {
        return this.createby;
    }

    public void setCreateby(String createby)
    {
        this.createby = createby;
    }
    
	@Temporal(TemporalType.DATE)
    @Column(name = "create_time", nullable = false, length = 7)
    public Date getCreateTime()
    {
        return this.createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Column(name = "updateby", length = 12)
    public String getUpdateby()
    {
        return this.updateby;
    }

    public void setUpdateby(String updateby)
    {
        this.updateby = updateby;
    }
    
    @Temporal(TemporalType.DATE)
    @Column(name = "update_time", nullable = false, length = 7)
    public Date getUpdateTime()
    {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    @Column(name = "update_count", nullable = false, precision = 22, scale = 0)
    public BigDecimal getUpdateCount()
    {
        return this.updateCount;
    }

    public void setUpdateCount(BigDecimal updateCount)
    {
        this.updateCount = updateCount;
    }

    @Column(name = "deleteby", length = 12)
    public String getDeleteby()
    {
        return this.deleteby;
    }

    public void setDeleteby(String deleteby)
    {
        this.deleteby = deleteby;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "delete_time", length = 7)
    public Date getDeleteTime()
    {
        return this.deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    @Column(name = "delete_flag", nullable = false, length = 1)
    public String getDeleteFlag()
    {
        return this.deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }
}
