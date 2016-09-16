package com.founder.cdr.hl7.dto;

public class WarningLabReportSpecificResult {
	/**
	 * 项目代码
	 */
	private String itemCode;

	/**
	 * 检验数值
	 */
	private String itemNumValue;

	/**
	 * 检验字符值
	 */
	private String itemStrValue;

	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 检验结果状态
	 */
	private boolean checkStatus;

	/**
	 * 检验结果信息
	 */
	private String checkMessage;

	/**
	 * 处理建议
	 */
	private String suggestion;

	/**
	 * 科室
	 */
	private String checkOffice;

	/**
	 * 诊断疾病代码
	 */
	private String diagnosisCode;

	public String getItemCode() {
		return itemCode;
	}

	public String getItemNumValue() {
		return itemNumValue;
	}

	public void setItemNumValue(String itemNumValue) {
		this.itemNumValue = itemNumValue;
	}

	public String getItemStrValue() {
		return itemStrValue;
	}

	public void setItemStrValue(String itemStrValue) {
		this.itemStrValue = itemStrValue;
	}

	public boolean isCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(boolean checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCheckMessage() {
		return checkMessage;
	}

	public void setCheckMessage(String checkMessage) {
		this.checkMessage = checkMessage;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCheckOffice() {
		return checkOffice;
	}

	public void setCheckOffice(String checkOffice) {
		this.checkOffice = checkOffice;
	}

	public String getDiagnosisCode() {
		return diagnosisCode;
	}

	public void setDiagnosisCode(String diagnosisCode) {
		this.diagnosisCode = diagnosisCode;
	}

}
