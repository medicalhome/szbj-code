package com.yly.cdr.hl7.dto.infectiousbuild;

import java.util.Date;
import java.util.List;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.hl7.dto.InfectiousRules;

public class INFECTIOUSBUILD extends BaseDto {
	private String msgId;

	private String msgName;

	private String sourceSysCode;

	private String targetSysCode;

	private String createTime;

	private String serviceId;

	private String visitDept;

	private String roleGroupType;

	private String hospitalCode;

	private String hospitalName;
	
	private String patientName;
	
	private String wardNo;
	
	private Date reportDate;
	
	private String chiefPsychiatrist;
	
	private Date sendDate;

	private List<InfectiousRules> row;
	
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getWardNo() {
		return wardNo;
	}

	public void setWardNo(String wardNo) {
		this.wardNo = wardNo;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getChiefPsychiatrist() {
		return chiefPsychiatrist;
	}

	public void setChiefPsychiatrist(String chiefPsychiatrist) {
		this.chiefPsychiatrist = chiefPsychiatrist;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public List<InfectiousRules> getRow() {
		return row;
	}
	public void setRow(List<InfectiousRules> row) {
		this.row = row;
	}
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

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getVisitDept() {
		return visitDept;
	}

	public void setVisitDept(String visitDept) {
		this.visitDept = visitDept;
	}

	public String getRoleGroupType() {
		return roleGroupType;
	}

	public void setRoleGroupType(String roleGroupType) {
		this.roleGroupType = roleGroupType;
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

}
