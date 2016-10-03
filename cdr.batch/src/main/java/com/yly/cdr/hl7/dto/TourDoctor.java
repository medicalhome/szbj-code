package com.yly.cdr.hl7.dto;

public class TourDoctor extends BaseDto {
	private String tourDoctorCode;
	private String tourDoctorName;
	public String getTourDoctorCode() {
		return tourDoctorCode;
	}
	public void setTourDoctorCode(String tourDoctorCode) {
		this.tourDoctorCode = tourDoctorCode;
	}
	public String getTourDoctorName() {
		return tourDoctorName;
	}
	public void setTourDoctorName(String tourDoctorName) {
		this.tourDoctorName = tourDoctorName;
	}
}
