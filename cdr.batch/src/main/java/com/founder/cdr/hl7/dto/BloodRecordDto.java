package com.founder.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;
/**
 * @author yang_jianbo
 * @date 2014-3-18
 */
public class BloodRecordDto {
	/**
	 * 血型
	 */
    @NotEmpty(message = "{BS360.bloodType}")
	private String bloodType;
    
    /**
	 * 血袋条码号
	 */
    @NotEmpty(message = "{BS360.bloodBagBarCode}")
	private String bloodBagBarCode;
	    
    /**
     * 血液区别码
     */
    @NotEmpty(message = "{BS360.differenceCode}")
   	private String differenceCode;
    
    /**
     * 血袋ID
     */
   	private String bloodBagLid;
    
	/**
	 * 单位（规格）
	 */
    @NotEmpty(message = "{BS360.unit}")
    private String unit;
    
    /**
	 * 量（规格）
	 */
    @NotEmpty(message = "{BS360.quality}")
	private String quality;

	/**
	 * 供血种类编码
	 */
    @NotEmpty(message = "{BS360.supplyBloodTypeCode}")
    private String supplyBloodTypeCode;
   
	/**
	 * 供血种类名称
	 */
    @NotEmpty(message = "{BS360.supplyBloodTypeName}")
	private String supplyBloodTypeName;
    
	/**
	 * 发血者编码
	 */
    @NotEmpty(message = "{BS360.assignedPersonCode}")
	private String assignedPersonCode;
    
	/**
	 * 发血者姓名
	 */
    @NotEmpty(message = "{BS360.assignedPersonName}")
    private String assignedPersonName;
    
    /**
	 * 发血日期
	 */
    @NotEmpty(message = "{BS360.sendBloodDate}")
	private String sendBloodDate;
    
    /**
	 * 取血者编码
	 */
	private String playingEntityCode;
	
	/**
	 * 取血者姓名
	 */
    private String playingEntityName;
    
    /**
     * 取血日期
     */
    private String playingDate;
    
	/**
	 * 配血者编码
	 */
    private String matchBloodPersonCode;
    
    /**
	 * 配血者姓名
	 */
	private String matchBloodPersonName;
	
	/**
	 * 配血日期
	 */   
	private String matchBloodDate;
	
	/**
	 * 交叉配血总结果
	 */
	private String crossMatchResult;
	
	/**
	 * 主侧交叉配血结果
	 */
	private String majorCrossMatchResult;
    
	/**
	 * 次侧交叉配血结果
	 */
    private String minorCrossMatchResult;

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getBloodBagBarCode() {
		return bloodBagBarCode;
	}

	public void setBloodBagBarCode(String bloodBagBarCode) {
		this.bloodBagBarCode = bloodBagBarCode;
	}

	public String getDifferenceCode() {
		return differenceCode;
	}

	public void setDifferenceCode(String differenceCode) {
		this.differenceCode = differenceCode;
	}

	public String getBloodBagLid() {
		return bloodBagLid;
	}

	public void setBloodBagLid(String bloodBagLid) {
		this.bloodBagLid = bloodBagLid;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getSupplyBloodTypeCode() {
		return supplyBloodTypeCode;
	}

	public void setSupplyBloodTypeCode(String supplyBloodTypeCode) {
		this.supplyBloodTypeCode = supplyBloodTypeCode;
	}

	public String getSupplyBloodTypeName() {
		return supplyBloodTypeName;
	}

	public void setSupplyBloodTypeName(String supplyBloodTypeName) {
		this.supplyBloodTypeName = supplyBloodTypeName;
	}

	public String getAssignedPersonCode() {
		return assignedPersonCode;
	}

	public void setAssignedPersonCode(String assignedPersonCode) {
		this.assignedPersonCode = assignedPersonCode;
	}

	public String getAssignedPersonName() {
		return assignedPersonName;
	}

	public void setAssignedPersonName(String assignedPersonName) {
		this.assignedPersonName = assignedPersonName;
	}

	public String getSendBloodDate() {
		return sendBloodDate;
	}

	public void setSendBloodDate(String sendBloodDate) {
		this.sendBloodDate = sendBloodDate;
	}

	public String getPlayingEntityCode() {
		return playingEntityCode;
	}

	public void setPlayingEntityCode(String playingEntityCode) {
		this.playingEntityCode = playingEntityCode;
	}

	public String getPlayingEntityName() {
		return playingEntityName;
	}

	public void setPlayingEntityName(String playingEntityName) {
		this.playingEntityName = playingEntityName;
	}
	
	public String getPlayingDate() {
		return playingDate;
	}

	public void setPlayingDate(String playingDate) {
		this.playingDate = playingDate;
	}

	public String getMatchBloodPersonCode() {
		return matchBloodPersonCode;
	}

	public void setMatchBloodPersonCode(String matchBloodPersonCode) {
		this.matchBloodPersonCode = matchBloodPersonCode;
	}

	public String getMatchBloodPersonName() {
		return matchBloodPersonName;
	}

	public void setMatchBloodPersonName(String matchBloodPersonName) {
		this.matchBloodPersonName = matchBloodPersonName;
	}

	public String getMatchBloodDate() {
		return matchBloodDate;
	}

	public void setMatchBloodDate(String matchBloodDate) {
		this.matchBloodDate = matchBloodDate;
	}

	public String getCrossMatchResult() {
		return crossMatchResult;
	}

	public void setCrossMatchResult(String crossMatchResult) {
		this.crossMatchResult = crossMatchResult;
	}

	public String getMajorCrossMatchResult() {
		return majorCrossMatchResult;
	}

	public void setMajorCrossMatchResult(String majorCrossMatchResult) {
		this.majorCrossMatchResult = majorCrossMatchResult;
	}

	public String getMinorCrossMatchResult() {
		return minorCrossMatchResult;
	}

	public void setMinorCrossMatchResult(String minorCrossMatchResult) {
		this.minorCrossMatchResult = minorCrossMatchResult;
	}
	
}
