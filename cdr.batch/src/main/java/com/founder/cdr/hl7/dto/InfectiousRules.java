package com.founder.cdr.hl7.dto;

import java.util.List;

public class InfectiousRules extends BaseDto{
	private String rowKey;
	
	private String factType;
	
	private List<InfectiousItem> item;
	
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
	public List<InfectiousItem> getItem() {
		return item;
	}
	public void setItem(List<InfectiousItem> item) {
		this.item = item;
	}

	
}
