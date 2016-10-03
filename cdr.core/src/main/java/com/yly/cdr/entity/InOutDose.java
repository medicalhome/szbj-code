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

import com.founder.fasf.core.util.daohelper.annotation.Generator;

import java.io.Serializable;

@Entity
@Table(name = "IN_OUT_DOSE")
public class InOutDose implements Serializable {

	private BigDecimal inOutDoseSn;

	private BigDecimal anesthesiaSn;

	private String inOutType;

	private String inOutDoseType;

	private String inOutItemCode;

	private String inOutItemName;

	private String dose;

	private String doseUnit;

	private Date createTime;

	private Date updateTime;

	private BigDecimal updateCount;

	private String deleteFlag;

	private Date deleteTime;
	
	private String createby;

	private String updateby;

	private String deleteby;

	@Id
	@GeneratedValue(generator = "guid-generator")
	@Generator(name = "guid-generator", strategy = "guid")
	public BigDecimal getInOutDoseSn() {
		return this.inOutDoseSn;
	}

	public void setInOutDoseSn(BigDecimal inOutDoseSn) {
		this.inOutDoseSn = inOutDoseSn;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME", nullable = false, length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_TIME", nullable = false, length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "UPDATE_COUNT", nullable = false, precision = 22, scale = 0)
	public BigDecimal getUpdateCount() {
		return this.updateCount;
	}

	public void setUpdateCount(BigDecimal updateCount) {
		this.updateCount = updateCount;
	}

	@Column(name = "DELETE_FLAG", nullable = false, length = 1)
	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DELETE_TIME", length = 7)
	public Date getDeleteTime() {
		return this.deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}
    @Column(name = "ANESTHESIA_SN", precision = 22, scale = 0)
	public BigDecimal getAnesthesiaSn() {
		return anesthesiaSn;
	}

	public void setAnesthesiaSn(BigDecimal anesthesiaSn) {
		this.anesthesiaSn = anesthesiaSn;
	}
	
    @Column(name = "IN_OUT_TYPE", length = 12)
	public String getInOutType() {
		return inOutType;
	}

	public void setInOutType(String inOutType) {
		this.inOutType = inOutType;
	}
    @Column(name = "IN_OUT_DOSE_TYPE", length = 12)
	public String getInOutDoseType() {
		return inOutDoseType;
	}

	public void setInOutDoseType(String inOutDoseType) {
		this.inOutDoseType = inOutDoseType;
	}
    @Column(name = "IN_OUT_ITEM_CODE", length = 12)
	public String getInOutItemCode() {
		return inOutItemCode;
	}

	public void setInOutItemCode(String inOutItemCode) {
		this.inOutItemCode = inOutItemCode;
	}
    @Column(name = "IN_OUT_ITEM_NAME", length = 50)
	public String getInOutItemName() {
		return inOutItemName;
	}

	public void setInOutItemName(String inOutItemName) {
		this.inOutItemName = inOutItemName;
	}
    @Column(name = "DOSE", length = 20)
	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}
    @Column(name = "DOSE_UNIT", length = 20)
	public String getDoseUnit() {
		return doseUnit;
	}

	public void setDoseUnit(String doseUnit) {
		this.doseUnit = doseUnit;
	}
    @Column(name = "CREATEBY", length = 12)
	public String getCreateby() {
		return createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}
    @Column(name = "UPDATEBY", length = 12)
	public String getUpdateby() {
		return updateby;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}
    @Column(name = "DELETEBY", length = 12)
	public String getDeleteby() {
		return deleteby;
	}

	public void setDeleteby(String deleteby) {
		this.deleteby = deleteby;
	}
}
