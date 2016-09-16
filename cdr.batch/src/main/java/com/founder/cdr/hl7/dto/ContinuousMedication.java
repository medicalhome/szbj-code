package com.founder.cdr.hl7.dto;

import java.util.List;

public class ContinuousMedication extends BaseDto {
	private String medicationCode;
	private String medicationName;
	private String dosage;
	private String dosageUnit;
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

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getDosageUnit() {
		return dosageUnit;
	}

	public void setDosageUnit(String dosageUnit) {
		this.dosageUnit = dosageUnit;
	}

	public List<TimeQuantum> getTimeQuantumList() {
		return timeQuantumList;
	}

	public void setTimeQuantumList(List<TimeQuantum> timeQuantumList) {
		this.timeQuantumList = timeQuantumList;
	}
}
