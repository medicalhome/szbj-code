package com.founder.cdr.dto;

import java.math.BigDecimal;
import java.util.Date;

public class BloodRequestDto
{
    private BigDecimal requestSn;

    private BigDecimal visitSn;

    private BigDecimal patientSn;

    private String patientDomain;

    private String patientLid;

    private String requestNo;

    private String bloodAboName;

    private String bloodRhName;

    private String bloodClassName;

    private BigDecimal quantity;

    private String bloodReasonName;

    private Date transfusionDate;

    private String requestDeptName;

    private String requestPersonName;

    private Date requestDate;

    private String description;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String orderLid;

    private String orderStatusName;

    private String specialRequest;

    // $Author :bin_zhang
    // $Date : 2012/8/24 16:23$
    // [BUG]0008926 ADD BEGIN
    private String quantityUnit;

    private String pregnancyFlag;

    private String pregnancyCount;

    private String childbirthFlag;

    private String childbirthCount;

    private String historyFlag;

    private String adverseHistoryFlag;

    // [BUG]0008926 ADD END

    // $Author: tong_meng
    // $Date:2012/10/16 16:30
    // [BUG]0008311 ADD BEGIN
    private String newbornFlag;

    private String otherDiseaseFlag;

    private String otherDiseaseName;

    // [BUG]0008311 ADD END

    private String selfBloodFlag;
    
    private String clinicalDiagnosis;
    // add by li_shenggen begin 2014/11/13 11:13
    /**
     * 是否交叉配血
     */
    private String crossMatchBloodFlag;
    
    /**
     * 交叉配血名称
     */
    private String crossMatchBloodName;
    
    /**
     * 是否备用
     */
    private String reserveFlag;
    
    /**
     * 备用名称
     */
    private String reserveName;
    
    /**
     * 是否干细胞移植后循环
     */
    private String stemCellTransplantFlag;
    
    /**
     * 干细胞移植后循环名称
     */
    private String stemCellTransplantName;
    // end
    public BigDecimal getRequestSn()
    {
        return requestSn;
    }

    public void setRequestSn(BigDecimal requestSn)
    {
        this.requestSn = requestSn;
    }

    public BigDecimal getVisitSn()
    {
        return visitSn;
    }

    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
    }

    public BigDecimal getPatientSn()
    {
        return patientSn;
    }

    public void setPatientSn(BigDecimal patientSn)
    {
        this.patientSn = patientSn;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public void setPatientDomain(String patientDomain)
    {
        this.patientDomain = patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public void setPatientLid(String patientLid)
    {
        this.patientLid = patientLid;
    }

    public String getRequestNo()
    {
        return requestNo;
    }

    public void setRequestNo(String requestNo)
    {
        this.requestNo = requestNo;
    }

    public BigDecimal getQuantity()
    {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity)
    {
        this.quantity = quantity;
    }

    public Date getTransfusionDate()
    {
        return transfusionDate;
    }

    public void setTransfusionDate(Date transfusionDate)
    {
        this.transfusionDate = transfusionDate;
    }

    public String getRequestPersonName()
    {
        return requestPersonName;
    }

    public void setRequestPersonName(String requestPersonName)
    {
        this.requestPersonName = requestPersonName;
    }

    public Date getRequestDate()
    {
        return requestDate;
    }

    public void setRequestDate(Date requestDate)
    {
        this.requestDate = requestDate;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public BigDecimal getUpdateCount()
    {
        return updateCount;
    }

    public void setUpdateCount(BigDecimal updateCount)
    {
        this.updateCount = updateCount;
    }

    public String getDeleteFlag()
    {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

    public Date getDeleteTime()
    {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    public String getOrderLid()
    {
        return orderLid;
    }

    public void setOrderLid(String orderLid)
    {
        this.orderLid = orderLid;
    }

    public String getSpecialRequest()
    {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest)
    {
        this.specialRequest = specialRequest;
    }

    public String getBloodAboName()
    {
        return bloodAboName;
    }

    public void setBloodAboName(String bloodAboName)
    {
        this.bloodAboName = bloodAboName;
    }

    public String getBloodRhName()
    {
        return bloodRhName;
    }

    public void setBloodRhName(String bloodRhName)
    {
        this.bloodRhName = bloodRhName;
    }

    public String getBloodClassName()
    {
        return bloodClassName;
    }

    public void setBloodClassName(String bloodClassName)
    {
        this.bloodClassName = bloodClassName;
    }

    public String getBloodReasonName()
    {
        return bloodReasonName;
    }

    public void setBloodReasonName(String bloodReasonName)
    {
        this.bloodReasonName = bloodReasonName;
    }

    public String getRequestDeptName()
    {
        return requestDeptName;
    }

    public void setRequestDeptName(String requestDeptName)
    {
        this.requestDeptName = requestDeptName;
    }

    public String getOrderStatusName()
    {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName)
    {
        this.orderStatusName = orderStatusName;
    }

    // $Author :bin_zhang
    // $Date : 2012/8/24 16:23$
    // [BUG]0008926 ADD BEGIN
    public String getQuantityUnit()
    {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit)
    {
        this.quantityUnit = quantityUnit;
    }

    public String getPregnancyFlag()
    {
        return pregnancyFlag;
    }

    public void setPregnancyFlag(String pregnancyFlag)
    {
        this.pregnancyFlag = pregnancyFlag;
    }

    public String getChildbirthFlag()
    {
        return childbirthFlag;
    }

    public void setChildbirthFlag(String childbirthFlag)
    {
        this.childbirthFlag = childbirthFlag;
    }


    public String getPregnancyCount() {
		return pregnancyCount;
	}

	public void setPregnancyCount(String pregnancyCount) {
		this.pregnancyCount = pregnancyCount;
	}

	public String getChildbirthCount() {
		return childbirthCount;
	}

	public void setChildbirthCount(String childbirthCount) {
		this.childbirthCount = childbirthCount;
	}

	public String getHistoryFlag()
    {
        return historyFlag;
    }

    public void setHistoryFlag(String historyFlag)
    {
        this.historyFlag = historyFlag;
    }

    public String getAdverseHistoryFlag()
    {
        return adverseHistoryFlag;
    }

    public void setAdverseHistoryFlag(String adverseHistoryFlag)
    {
        this.adverseHistoryFlag = adverseHistoryFlag;
    }

    // [BUG]0008926 ADD END

    public String getNewbornFlag()
    {
        return newbornFlag;
    }

    public void setNewbornFlag(String newbornFlag)
    {
        this.newbornFlag = newbornFlag;
    }

    public String getOtherDiseaseFlag()
    {
        return otherDiseaseFlag;
    }

    public void setOtherDiseaseFlag(String otherDiseaseFlag)
    {
        this.otherDiseaseFlag = otherDiseaseFlag;
    }

    public String getOtherDiseaseName()
    {
        return otherDiseaseName;
    }

    public void setOtherDiseaseName(String otherDiseaseName)
    {
        this.otherDiseaseName = otherDiseaseName;
    }

    public String getSelfBloodFlag()
    {
        return selfBloodFlag;
    }

    public void setSelfBloodFlag(String selfBloodFlag)
    {
        this.selfBloodFlag = selfBloodFlag;
    }

    public String getClinicalDiagnosis()
    {
        return clinicalDiagnosis;
    }

    public void setClinicalDiagnosis(String clinicalDiagnosis)
    {
        this.clinicalDiagnosis = clinicalDiagnosis;
    }

	public String getCrossMatchBloodFlag() {
		return crossMatchBloodFlag;
	}

	public void setCrossMatchBloodFlag(String crossMatchBloodFlag) {
		this.crossMatchBloodFlag = crossMatchBloodFlag;
	}

	public String getCrossMatchBloodName() {
		return crossMatchBloodName;
	}

	public void setCrossMatchBloodName(String crossMatchBloodName) {
		this.crossMatchBloodName = crossMatchBloodName;
	}

	public String getReserveFlag() {
		return reserveFlag;
	}

	public void setReserveFlag(String reserveFlag) {
		this.reserveFlag = reserveFlag;
	}

	public String getReserveName() {
		return reserveName;
	}

	public void setReserveName(String reserveName) {
		this.reserveName = reserveName;
	}

	public String getStemCellTransplantFlag() {
		return stemCellTransplantFlag;
	}

	public void setStemCellTransplantFlag(String stemCellTransplantFlag) {
		this.stemCellTransplantFlag = stemCellTransplantFlag;
	}

	public String getStemCellTransplantName() {
		return stemCellTransplantName;
	}

	public void setStemCellTransplantName(String stemCellTransplantName) {
		this.stemCellTransplantName = stemCellTransplantName;
	}

}
