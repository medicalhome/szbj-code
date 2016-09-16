package com.founder.cdr.hl7.dto;

import java.util.List;

public class MedicationMessage extends BaseDto {
	private String medicationCode;
	private String medicationName;
	private List<TimeQuantum> timeQuantumList;

	public String getMedicationCode() {
		return medicationCode;
	}

	public void setMedicationCode(String medicationCode) {
		this.medicationCode = medicationCode;
	}

	public String getMedicationName() {
		return medicationName;
	}

	public void setMedicationName(String medicationName) {
		this.medicationName = medicationName;
	}

	public List<TimeQuantum> getTimeQuantumList() {
		return timeQuantumList;
	}

	public void setTimeQuantumList(List<TimeQuantum> timeQuantumList) {
		this.timeQuantumList = timeQuantumList;
	}

}
