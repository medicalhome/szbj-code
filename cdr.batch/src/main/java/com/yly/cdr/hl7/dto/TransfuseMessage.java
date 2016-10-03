package com.yly.cdr.hl7.dto;

import java.util.List;

public class TransfuseMessage extends BaseDto {
	private String dosage;
	private String dosageUnit;
	private String transfusePositionCode;
	private String transfusePositionName;
	private List<MedicationMessage> medicationMessageList;

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

	public String getTransfusePositionCode() {
		return transfusePositionCode;
	}

	public void setTransfusePositionCode(String transfusePositionCode) {
		this.transfusePositionCode = transfusePositionCode;
	}

	public String getTransfusePositionName() {
		return transfusePositionName;
	}

	public void setTransfusePositionName(String transfusePositionName) {
		this.transfusePositionName = transfusePositionName;
	}

	public List<MedicationMessage> getMedicationMessageList() {
		return medicationMessageList;
	}

	public void setMedicationMessageList(
			List<MedicationMessage> medicationMessageList) {
		this.medicationMessageList = medicationMessageList;
	}

}
