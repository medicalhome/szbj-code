package com.yly.cdr.hl7.dto;

public class AnesthesiaDoctor extends BaseDto {
	private String anesthesiaDoctorCode;
	private String anesthesiaDoctorName;

	public String getAnesthesiaDoctorCode() {
		return anesthesiaDoctorCode;
	}

	public void setAnesthesiaDoctorCode(String anesthesiaDoctorCode) {
		this.anesthesiaDoctorCode = anesthesiaDoctorCode;
	}

	public String getAnesthesiaDoctorName() {
		return anesthesiaDoctorName;
	}

	public void setAnesthesiaDoctorName(String anesthesiaDoctorName) {
		this.anesthesiaDoctorName = anesthesiaDoctorName;
	}

}
