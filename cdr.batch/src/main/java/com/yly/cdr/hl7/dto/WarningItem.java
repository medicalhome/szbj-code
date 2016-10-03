package com.yly.cdr.hl7.dto;

public class WarningItem extends BaseDto {
	private String itemName;
	
	private String value;
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
