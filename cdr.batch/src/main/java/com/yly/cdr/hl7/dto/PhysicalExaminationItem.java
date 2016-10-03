package com.yly.cdr.hl7.dto;

public class PhysicalExaminationItem extends BaseDto {
	private String physicalExaminationItemCode;
	private String physicalExaminationItemName;
	private String physicalExaminationItemResult;

	public String getPhysicalExaminationItemCode() {
		return physicalExaminationItemCode;
	}

	public void setPhysicalExaminationItemCode(
			String physicalExaminationItemCode) {
		this.physicalExaminationItemCode = physicalExaminationItemCode;
	}

	public String getPhysicalExaminationItemName() {
		return physicalExaminationItemName;
	}

	public void setPhysicalExaminationItemName(
			String physicalExaminationItemName) {
		this.physicalExaminationItemName = physicalExaminationItemName;
	}

	public String getPhysicalExaminationItemResult() {
		return physicalExaminationItemResult;
	}

	public void setPhysicalExaminationItemResult(
			String physicalExaminationItemResult) {
		this.physicalExaminationItemResult = physicalExaminationItemResult;
	}

}
