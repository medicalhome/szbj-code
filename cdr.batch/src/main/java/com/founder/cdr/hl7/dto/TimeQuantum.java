package com.founder.cdr.hl7.dto;

public class TimeQuantum extends BaseDto {
	private String low;
	private String high;
	private String text;

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
