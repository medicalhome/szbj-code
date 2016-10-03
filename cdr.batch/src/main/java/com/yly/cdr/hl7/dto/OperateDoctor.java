package com.yly.cdr.hl7.dto;

public class OperateDoctor extends BaseDto {
	private String operateDoctorCode;
	private String operateDoctorName;

	public String getOperateDoctorCode() {
		return operateDoctorCode;
	}

	public void setOperateDoctorCode(String operateDoctorCode) {
		this.operateDoctorCode = operateDoctorCode;
	}

	public String getOperateDoctorName() {
		return operateDoctorName;
	}

	public void setOperateDoctorName(String operateDoctorName) {
		this.operateDoctorName = operateDoctorName;
	}

}
