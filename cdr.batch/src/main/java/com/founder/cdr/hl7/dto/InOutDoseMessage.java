package com.founder.cdr.hl7.dto;

public class InOutDoseMessage extends BaseDto {
	private String inOutDoseCode;
	private String inOutDoseName;
	private String inOutDoseValue;
	private String inOutDoseUnit;

	public String getInOutDoseCode() {
		return inOutDoseCode;
	}

	public void setInOutDoseCode(String inOutDoseCode) {
		this.inOutDoseCode = inOutDoseCode;
	}

	public String getInOutDoseName() {
		return inOutDoseName;
	}

	public void setInOutDoseName(String inOutDoseName) {
		this.inOutDoseName = inOutDoseName;
	}

	public String getInOutDoseValue() {
		return inOutDoseValue;
	}

	public void setInOutDoseValue(String inOutDoseValue) {
		this.inOutDoseValue = inOutDoseValue;
	}

	public String getInOutDoseUnit() {
		return inOutDoseUnit;
	}

	public void setInOutDoseUnit(String inOutDoseUnit) {
		this.inOutDoseUnit = inOutDoseUnit;
	}

}
