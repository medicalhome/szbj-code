package com.yly.cdr.hl7.dto;

public class ExamResultDto {
	/**
	 * 项目编码
	 */
	private String itemCode;
	
	/**
	 * 项目名称
	 */
	private String itemName;
	
	/**
	 * 项目结果
	 */
	private String itemResult;

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemResult() {
		return itemResult;
	}

	public void setItemResult(String itemResult) {
		this.itemResult = itemResult;
	}
}
