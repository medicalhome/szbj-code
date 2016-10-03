package com.yly.cdr.hl7.dto;

public class ApparatusDoctor extends BaseDto {
	private String apparatusDoctorCode;
	private String apparatusDoctorName;

	public String getApparatusDoctorCode() {
		return apparatusDoctorCode;
	}

	public void setApparatusDoctorCode(String apparatusDoctorCode) {
		this.apparatusDoctorCode = apparatusDoctorCode;
	}

	public String getApparatusDoctorName() {
		return apparatusDoctorName;
	}

	public void setApparatusDoctorName(String apparatusDoctorName) {
		this.apparatusDoctorName = apparatusDoctorName;
	}

}
