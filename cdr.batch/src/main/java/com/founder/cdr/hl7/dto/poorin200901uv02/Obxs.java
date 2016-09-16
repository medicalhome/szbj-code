package com.founder.cdr.hl7.dto.poorin200901uv02;
/**
 * v2消息Obx段
 * */
public class Obxs {
	/**
	 * obx段内容标识符
	 */
	private String identifier;
	
	/**
	 * obx段内容
	 * */
	private String value;
	
	public String getIdentifier(){
		return this.identifier;
	}
	
	public String getValue(){
		return this.value;
	}
}
