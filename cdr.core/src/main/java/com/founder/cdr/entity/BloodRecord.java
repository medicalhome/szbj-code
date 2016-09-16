package com.founder.cdr.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.founder.fasf.core.util.daohelper.annotation.Generator;
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

@Entity
@Table(name = "blood_record")
public class BloodRecord implements Serializable
{
	private static final long serialVersionUID = 1L;

	private BigDecimal recordSn;

    private BigDecimal menuSn;
    
    private BigDecimal requestSn;
    
    private String differenceCode;
    
    private String bloodType;
    
    private String bloodBagBarCode;

    private Date sendBloodDate;

    private String unit;
    
    private String quality;
    
    private String supplyBloodClassCode;

    private String supplyBloodClassName;
    
    private String assignedPersonCode;
    
    private String assignedPersonName;
    
    private String playingEntityCode;
    
    private String playingEntityName;
    
    private Date playingDate;
    
    private Date matchBloodDate;
    
    private String matchBloodPersonCode;
    
    private String matchBloodPersonName;
    
    private String crossMatchResult;
    
    private String majorCrossMatchResult;
    
    private String minorCrossMatchResult;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String createby;

    private String updateby;

    private String deleteby;
    
    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "seq_blood_record") })
	public BigDecimal getRecordSn() {
		return recordSn;
	}

	public void setRecordSn(BigDecimal recordSn) {
		this.recordSn = recordSn;
	}

	@Column(name = "menu_sn", length = 12)
	public BigDecimal getMenuSn() {
		return menuSn;
	}

	public void setMenuSn(BigDecimal menuSn) {
		this.menuSn = menuSn;
	}
	
	@Column(name = "request_sn", length = 12)
	public BigDecimal getRequestSn() {
		return requestSn;
	}

	public void setRequestSn(BigDecimal requestSn) {
		this.requestSn = requestSn;
	}

	@Column(name = "different_code", length = 20)
	public String getDifferenceCode() {
		return differenceCode;
	}

	public void setDifferenceCode(String differenceCode) {
		this.differenceCode = differenceCode;
	}

	@Column(name = "blood_type", length = 12)
	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}
	
	@Temporal(TemporalType.DATE)
    @Column(name = "send_blood_date", length = 7)
	public Date getSendBloodDate() {
		return sendBloodDate;
	}

	public void setSendBloodDate(Date sendBloodDate) {
		this.sendBloodDate = sendBloodDate;
	}

	@Column(name = "unit", length = 12)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "quality", length = 12)
	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	@Column(name = "blood_bag_bar_code", length = 20)
	public String getBloodBagBarCode() {
		return bloodBagBarCode;
	}

	public void setBloodBagBarCode(String bloodBagBarCode) {
		this.bloodBagBarCode = bloodBagBarCode;
	}

	@Column(name = "supply_blood_class_code", length = 20)
	public String getSupplyBloodClassCode() {
		return supplyBloodClassCode;
	}

	public void setSupplyBloodClassCode(String supplyBloodClassCode) {
		this.supplyBloodClassCode = supplyBloodClassCode;
	}

	@Column(name = "supply_blood_class_name", length = 50)
	public String getSupplyBloodClassName() {
		return supplyBloodClassName;
	}

	public void setSupplyBloodClassName(String supplyBloodClassName) {
		this.supplyBloodClassName = supplyBloodClassName;
	}

	@Column(name = "assigned_person_code", length = 20)
	public String getAssignedPersonCode() {
		return assignedPersonCode;
	}

	public void setAssignedPersonCode(String assignedPersonCode) {
		this.assignedPersonCode = assignedPersonCode;
	}

	@Column(name = "assigned_person_name", length = 50)
	public String getAssignedPersonName() {
		return assignedPersonName;
	}

	public void setAssignedPersonName(String assignedPersonName) {
		this.assignedPersonName = assignedPersonName;
	}

	@Column(name = "playing_entity_code", length = 20)
	public String getPlayingEntityCode() {
		return playingEntityCode;
	}

	public void setPlayingEntityCode(String playingEntityCode) {
		this.playingEntityCode = playingEntityCode;
	}

	@Column(name = "playing_entity_name", length = 50)
	public String getPlayingEntityName() {
		return playingEntityName;
	}

	public void setPlayingEntityName(String playingEntityName) {
		this.playingEntityName = playingEntityName;
	}

	@Temporal(TemporalType.DATE)
    @Column(name = "playing_date", length = 7)
	public Date getPlayingDate() {
		return playingDate;
	}

	public void setPlayingDate(Date playingDate) {
		this.playingDate = playingDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Temporal(TemporalType.DATE)
    @Column(name = "match_blood_date", length = 7)
	public Date getMatchBloodDate() {
		return matchBloodDate;
	}

	public void setMatchBloodDate(Date matchBloodDate) {
		this.matchBloodDate = matchBloodDate;
	}

	@Column(name = "match_blood_person_code", length = 20)
	public String getMatchBloodPersonCode() {
		return matchBloodPersonCode;
	}

	public void setMatchBloodPersonCode(String matchBloodPersonCode) {
		this.matchBloodPersonCode = matchBloodPersonCode;
	}

	@Column(name = "match_blood_person_name", length = 50)
	public String getMatchBloodPersonName() {
		return matchBloodPersonName;
	}

	public void setMatchBloodPersonName(String matchBloodPersonName) {
		this.matchBloodPersonName = matchBloodPersonName;
	}

	@Column(name = "cross_match_result", length = 12)
	public String getCrossMatchResult() {
		return crossMatchResult;
	}

	public void setCrossMatchResult(String crossMatchResult) {
		this.crossMatchResult = crossMatchResult;
	}

	@Column(name = "major_cross_match_result", length = 12)
	public String getMajorCrossMatchResult() {
		return majorCrossMatchResult;
	}

	public void setMajorCrossMatchResult(String majorCrossMatchResult) {
		this.majorCrossMatchResult = majorCrossMatchResult;
	}

	@Column(name = "minor_cross_match_result", length = 12)
	public String getMinorCrossMatchResult() {
		return minorCrossMatchResult;
	}

	public void setMinorCrossMatchResult(String minorCrossMatchResult) {
		this.minorCrossMatchResult = minorCrossMatchResult;
	}

	@Column(name = "createby", length = 12)
    public String getCreateby()
    {
        return this.createby;
    }

    public void setCreateby(String createby)
    {
        this.createby = createby;
    }
    
	@Temporal(TemporalType.DATE)
    @Column(name = "create_time", nullable = false, length = 7)
    public Date getCreateTime()
    {
        return this.createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Column(name = "updateby", length = 12)
    public String getUpdateby()
    {
        return this.updateby;
    }

    public void setUpdateby(String updateby)
    {
        this.updateby = updateby;
    }
    
    @Temporal(TemporalType.DATE)
    @Column(name = "update_time", nullable = false, length = 7)
    public Date getUpdateTime()
    {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    @Column(name = "update_count", nullable = false, precision = 22, scale = 0)
    public BigDecimal getUpdateCount()
    {
        return this.updateCount;
    }

    public void setUpdateCount(BigDecimal updateCount)
    {
        this.updateCount = updateCount;
    }

    @Column(name = "deleteby", length = 12)
    public String getDeleteby()
    {
        return this.deleteby;
    }

    public void setDeleteby(String deleteby)
    {
        this.deleteby = deleteby;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "delete_time", length = 7)
    public Date getDeleteTime()
    {
        return this.deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    @Column(name = "delete_flag", nullable = false, length = 1)
    public String getDeleteFlag()
    {
        return this.deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }
}
