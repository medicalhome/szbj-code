package com.founder.cdr.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @date 2014/03/20  
 * @author yang_jianbo
 */
public class BloodRequestAndRecordDto
{
	/**
	 * 申请单号
	 */
	private String requestNo;
	
	/**
	 * 血液种类名称
	 */
	private String bloodClassName;
	
	/**
	 * 输血目的名称
	 */
	private String bloodReasonName;
	
	/**
	 * 输血量
	 */
	private BigDecimal quantity;
	
	/**
	 * 单位
	 */
	private String quantityUnit;
	
	/**
	 * 临床诊断
	 */
	private String clinicalDiagnosis;
	
	/**
	 * 对应的输血记录单
	 */
	private List<BloodRecordMenuDto> recordMenu;

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getBloodClassName() {
		return bloodClassName;
	}

	public void setBloodClassName(String bloodClassName) {
		this.bloodClassName = bloodClassName;
	}

	public String getBloodReasonName() {
		return bloodReasonName;
	}

	public void setBloodReasonName(String bloodReasonName) {
		this.bloodReasonName = bloodReasonName;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getQuantityUnit() {
		return quantityUnit;
	}

	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}

	public String getClinicalDiagnosis() {
		return clinicalDiagnosis;
	}

	public void setClinicalDiagnosis(String clinicalDiagnosis) {
		this.clinicalDiagnosis = clinicalDiagnosis;
	}

	public List<BloodRecordMenuDto> getRecordMenu() {
		return recordMenu;
	}

	public void setRecordMenu(List<BloodRecordMenuDto> recordMenu) {
		this.recordMenu = recordMenu;
	}
	
}
