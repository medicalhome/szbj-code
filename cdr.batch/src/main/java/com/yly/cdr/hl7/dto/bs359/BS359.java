package com.yly.cdr.hl7.dto.bs359;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.hl7.dto.DoctorInCharge;

public class BS359 extends BaseDto {
	/**
	 * 消息类别代码
	 */
//	@NotEmpty(message = "{BS359.msgId}")
	private String msgId;

	/**
	 * 消息名称
	 */
//	@NotEmpty(message = "{BS359.msgName}")
	private String msgName;

	/**
	 * 消息源系统编码
	 */
	@NotEmpty(message = "{BS359.sourceSysCode}")
	private String sourceSysCode;

	/**
	 * 消息目标系统编码
	 */
	private String targetSysCode;

	/**
	 * 消息创建时间
	 */
	@NotEmpty(message = "{BS359.createTime}")
	private String createTime;

	/**
	 * 医疗结构代码
	 */
	private String hospitalCode;

	/**
	 * 医疗结构名称
	 */
	private String hospitalName;

	/**
	 * 域ID
	 */
	@NotEmpty(message = "{BS359.domainId}")
	private String domainId;

	/**
	 * 患者ID
	 */
	@NotEmpty(message = "{BS359.patientId}")
	private String patientId;

	/**
	 * 就诊号
	 */
	private String visitNo;

	/**
	 * 就诊次数
	 */
	// @NotEmpty(message = "{BS359.times}")
	private String times;

	/**
	 * 主管医生信息
	 */
	@NotEmpty(message = "{BS359.doctors}")
	private List<DoctorInCharge> doctors;

	/**
	 * 就诊流水号
	 */
	@NotEmpty(message = "{BS359.visitOrdNo}")
	private String visitOrdNo;

	/**
	 * 就诊类型
	 */
	@NotEmpty(message = "{BS359.visitType}")
	private String visitType;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsgName() {
		return msgName;
	}

	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}

	public String getSourceSysCode() {
		return sourceSysCode;
	}

	public void setSourceSysCode(String sourceSysCode) {
		this.sourceSysCode = sourceSysCode;
	}

	public String getTargetSysCode() {
		return targetSysCode;
	}

	public void setTargetSysCode(String targetSysCode) {
		this.targetSysCode = targetSysCode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getVisitNo() {
		return visitNo;
	}

	public void setVisitNo(String visitNo) {
		this.visitNo = visitNo;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public List<DoctorInCharge> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<DoctorInCharge> doctors) {
		this.doctors = doctors;
	}

	public String getVisitOrdNo() {
		return visitOrdNo;
	}

	public void setVisitOrdNo(String visitOrdNo) {
		this.visitOrdNo = visitOrdNo;
	}

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

}
