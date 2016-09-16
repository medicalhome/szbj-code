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

import java.io.Serializable;

@Entity
@Table(name = "ANESTHESIA")
public class Anesthesia implements Serializable {

	private BigDecimal anesthesiaSn;

	private BigDecimal visitSn;

	private BigDecimal patientSn;

	private String patientDomain;

	private String patientLid;

	private String chineseAnesthesia;

	private String anesthesiaMethod;

	private String anesthesiaDept;

	private Date prepareTime;

	private Date startTime;

	private Date endTime;

	private String workload;

	private Date createTime;

	private Date updateTime;

	private BigDecimal updateCount;

	private String deleteFlag;

	private Date deleteTime;

	private String anesthesiaMethodName;

	private String anesthesiaDeptName;

	private String orderLid;

	private String requestNo;
	private String anesthesiaGradeCode;

	private String anesthesiaGradeName;
	private String operateCode;

	private String operateName;
	private String operateOverGone;
	private String operateAnalgesia;
    private String orgCode;
    private String orgName;

	@Id
	@GeneratedValue(generator = "guid-generator")
	@Generator(name = "guid-generator", strategy = "guid")
	public BigDecimal getAnesthesiaSn() {
		return this.anesthesiaSn;
	}

	public void setAnesthesiaSn(BigDecimal anesthesiaSn) {
		this.anesthesiaSn = anesthesiaSn;
	}

	@Column(name = "VISIT_SN", nullable = false, precision = 22, scale = 0)
	public BigDecimal getVisitSn() {
		return this.visitSn;
	}

	public void setVisitSn(BigDecimal visitSn) {
		this.visitSn = visitSn;
	}

	@Column(name = "PATIENT_SN", nullable = false, precision = 22, scale = 0)
	public BigDecimal getPatientSn() {
		return this.patientSn;
	}

	public void setPatientSn(BigDecimal patientSn) {
		this.patientSn = patientSn;
	}

	@Column(name = "PATIENT_DOMAIN", nullable = false, length = 6)
	public String getPatientDomain() {
		return this.patientDomain;
	}

	public void setPatientDomain(String patientDomain) {
		this.patientDomain = patientDomain;
	}

	@Column(name = "PATIENT_LID", nullable = false, length = 12)
	public String getPatientLid() {
		return this.patientLid;
	}

	public void setPatientLid(String patientLid) {
		this.patientLid = patientLid;
	}

	@Column(name = "CHINESE_ANESTHESIA", nullable = false, length = 1)
	public String getChineseAnesthesia() {
		return this.chineseAnesthesia;
	}

	public void setChineseAnesthesia(String chineseAnesthesia) {
		this.chineseAnesthesia = chineseAnesthesia;
	}

	@Column(name = "ANESTHESIA_METHOD", length = 20)
	public String getAnesthesiaMethod() {
		return this.anesthesiaMethod;
	}

	public void setAnesthesiaMethod(String anesthesiaMethod) {
		this.anesthesiaMethod = anesthesiaMethod;
	}

	@Column(name = "ANESTHESIA_DEPT", nullable = false, length = 20)
	public String getAnesthesiaDept() {
		return this.anesthesiaDept;
	}

	public void setAnesthesiaDept(String anesthesiaDept) {
		this.anesthesiaDept = anesthesiaDept;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PREPARE_TIME", length = 7)
	public Date getPrepareTime() {
		return this.prepareTime;
	}

	public void setPrepareTime(Date prepareTime) {
		this.prepareTime = prepareTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_TIME", nullable = false, length = 7)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_TIME", nullable = false, length = 7)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "WORKLOAD", length = 20)
	public String getWorkload() {
		return this.workload;
	}

	public void setWorkload(String workload) {
		this.workload = workload;
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

	@Column(name = "ANESTHESIA_METHOD_NAME", length = 40)
	public String getAnesthesiaMethodName() {
		return this.anesthesiaMethodName;
	}

	public void setAnesthesiaMethodName(String anesthesiaMethodName) {
		this.anesthesiaMethodName = anesthesiaMethodName;
	}

	@Column(name = "ANESTHESIA_DEPT_NAME", length = 40)
	public String getAnesthesiaDeptName() {
		return this.anesthesiaDeptName;
	}

	public void setAnesthesiaDeptName(String anesthesiaDeptName) {
		this.anesthesiaDeptName = anesthesiaDeptName;
	}
	@Column(name = "ORDER_LID", length = 50)
	public String getOrderLid() {
		return orderLid;
	}

	public void setOrderLid(String orderLid) {
		this.orderLid = orderLid;
	}
	@Column(name = "REQUEST_NO", length = 50)
	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	@Column(name = "ANESTHESIA_GRADE_CODE", length = 12)
	public String getAnesthesiaGradeCode() {
		return anesthesiaGradeCode;
	}

	public void setAnesthesiaGradeCode(String anesthesiaGradeCode) {
		this.anesthesiaGradeCode = anesthesiaGradeCode;
	}
	@Column(name = "ANESTHESIA_GRADE_NAME", length = 50)
	public String getAnesthesiaGradeName() {
		return anesthesiaGradeName;
	}

	public void setAnesthesiaGradeName(String anesthesiaGradeName) {
		this.anesthesiaGradeName = anesthesiaGradeName;
	}
	@Column(name = "OPERATE_CODE", length = 12)
	public String getOperateCode() {
		return operateCode;
	}

	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
	}
	@Column(name = "OPERATE_NAME", length = 50)
	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	@Column(name = "OPERATE_OVER_GONE", length = 50)
	public String getOperateOverGone() {
		return operateOverGone;
	}

	public void setOperateOverGone(String operateOverGone) {
		this.operateOverGone = operateOverGone;
	}
	@Column(name = "OPERATE_ANALGESIA", length = 50)
	public String getOperateAnalgesia() {
		return operateAnalgesia;
	}

	public void setOperateAnalgesia(String operateAnalgesia) {
		this.operateAnalgesia = operateAnalgesia;
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

}
