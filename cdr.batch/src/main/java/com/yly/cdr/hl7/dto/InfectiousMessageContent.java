package com.yly.cdr.hl7.dto;

import java.util.List;


public class InfectiousMessageContent extends BaseDto {
	private String reportContentType;
	private String reportContentTypeName;
	private List<InfectiousItemsforMessage> items;
	public List<InfectiousItemsforMessage> getItems() {
		return items;
	}
	public void setItems(List<InfectiousItemsforMessage> items) {
		this.items = items;
	}
	public String getReportContentTypeName() {
		return reportContentTypeName;
	}
	public void setReportContentTypeName(String reportContentTypeName) {
		this.reportContentTypeName = reportContentTypeName;
	}
	public String getReportContentType() {
		return reportContentType;
	}
	public void setReportContentType(String reportContentType) {
		this.reportContentType = reportContentType;
	}

}
