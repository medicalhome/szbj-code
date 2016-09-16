package com.founder.cdr.hl7.dto;

/**
 * @author yang_jianbo
 * @date 2014-3-27
 */
public class BloodRecordSpecialRequestDto {
	/**
	 * 血液特殊要求编码
	 */
	private String bloodSpecialRequestCode;
	
	/**
	 * 血液特殊要求名称
	 */
	private String bloodSpecialRequestName;

	public String getBloodSpecialRequestCode() {
		return bloodSpecialRequestCode;
	}

	public void setBloodSpecialRequestCode(String bloodSpecialRequestCode) {
		this.bloodSpecialRequestCode = bloodSpecialRequestCode;
	}

	public String getBloodSpecialRequestName() {
		return bloodSpecialRequestName;
	}

	public void setBloodSpecialRequestName(String bloodSpecialRequestName) {
		this.bloodSpecialRequestName = bloodSpecialRequestName;
	}
}
