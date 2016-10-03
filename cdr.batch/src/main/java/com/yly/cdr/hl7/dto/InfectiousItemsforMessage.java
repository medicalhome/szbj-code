package com.yly.cdr.hl7.dto;

public class InfectiousItemsforMessage extends BaseDto {

	private String name;
	
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
