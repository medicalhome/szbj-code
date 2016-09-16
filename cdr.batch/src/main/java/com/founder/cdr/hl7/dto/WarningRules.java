package com.founder.cdr.hl7.dto;

import java.util.List;

public class WarningRules extends BaseDto{
	private String rowKey;
	
	private String factType;
	
	private List<WarningItem> item;
	
	public String getRowKey() {
		return rowKey;
	}
	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}
	public String getFactType() {
		return factType;
	}
	public void setFactType(String factType) {
		this.factType = factType;
	}
	public List<WarningItem> getItem() {
		return item;
	}
	public void setItem(List<WarningItem> item) {
		this.item = item;
	}

	
}
