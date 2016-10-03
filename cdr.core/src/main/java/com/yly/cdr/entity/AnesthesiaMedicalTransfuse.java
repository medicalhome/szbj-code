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
@Table(name = "ANESTHESIA_MEDICAL_TRANSFUSE")
public class AnesthesiaMedicalTransfuse implements Serializable {

	private BigDecimal medicationSn;

	private BigDecimal anesthesiaSn;

	private String medicationType;

	private String medicationCode;

	private String medicationName;

	private String dosageUnit;

	private String dosage;
	private String drugsPartCode;
	private String drugsPartName;
	private String timeUpper;
	private String timeFloor;
	private String medicationEvent;

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
	public BigDecimal getMedicationSn() {
		return this.medicationSn;
	}

	public void setMedicationSn(BigDecimal medicationSn) {
		this.medicationSn = medicationSn;
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
	
    @Column(name = "CREATE_BY", length = 12)
	public String getCreateby() {
		return createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}
    @Column(name = "UPDATE_BY", length = 12)
	public String getUpdateby() {
		return updateby;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}
    @Column(name = "DELETE_BY", length = 12)
	public String getDeleteby() {
		return deleteby;
	}

	public void setDeleteby(String deleteby) {
		this.deleteby = deleteby;
	}
    @Column(name = "MEDICATION_TYPE", length = 12)
	public String getMedicationType() {
		return medicationType;
	}

	public void setMedicationType(String medicationType) {
		this.medicationType = medicationType;
	}
    @Column(name = "MEDICATION_CODE", length = 12)
	public String getMedicationCode() {
		return medicationCode;
	}

	public void setMedicationCode(String medicationCode) {
		this.medicationCode = medicationCode;
	}
    @Column(name = "MEDICATION_NAME", length = 50)
	public String getMedicationName() {
		return medicationName;
	}

	public void setMedicationName(String medicationName) {
		this.medicationName = medicationName;
	}
    @Column(name = "DOSAGE_UNIT", length = 12)
	public String getDosageUnit() {
		return dosageUnit;
	}

	public void setDosageUnit(String dosageUnit) {
		this.dosageUnit = dosageUnit;
	}
    @Column(name = "DOSAGE", length = 12)
	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
    @Column(name = "DRUGS_PART_CODE", length = 12)
	public String getDrugsPartCode() {
		return drugsPartCode;
	}

	public void setDrugsPartCode(String drugsPartCode) {
		this.drugsPartCode = drugsPartCode;
	}
    @Column(name = "DRUGS_PART_NAME", length = 50)
	public String getDrugsPartName() {
		return drugsPartName;
	}

	public void setDrugsPartName(String drugsPartName) {
		this.drugsPartName = drugsPartName;
	}
    @Column(name = "TIME_UPPER", length = 12)
	public String getTimeUpper() {
		return timeUpper;
	}

	public void setTimeUpper(String timeUpper) {
		this.timeUpper = timeUpper;
	}
    @Column(name = "TIME_FLOOR", length = 12)
	public String getTimeFloor() {
		return timeFloor;
	}

	public void setTimeFloor(String timeFloor) {
		this.timeFloor = timeFloor;
	}
    @Column(name = "MEDICATION_EVENT", length = 256)
	public String getMedicationEvent() {
		return medicationEvent;
	}

	public void setMedicationEvent(String medicationEvent) {
		this.medicationEvent = medicationEvent;
	}
}
