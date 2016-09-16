package com.founder.cdr.dto;

import java.math.BigDecimal;
import java.util.Date;

public class MedicationOrderDto
{

    private BigDecimal orderSn;

    private BigDecimal visitSn;

    private BigDecimal prescriptionSn;

    private BigDecimal documentSn;

    private BigDecimal dispensingSn;

    private BigDecimal parentOrderSn;

    private BigDecimal patientSn;

    private String patientDomain;

    private String patientLid;

    private String orderLid;
    
    private String orderSeqnum;
    
    private String orderSetCode;
    
    private String orderDescribe;

    private String orderType;

    private String orderTypeName;

    private String drugCode;

    private String drugName;

    private String approvedDrugName;

    private String medicineClass;

    private String medicineClassName;

    private String medicineType;

    private String medicineTypeName;

    private String dosage;

    private String dosageUnit;

    private String totalDosage;

    private String totalDosageUnit;

    private String routeCode;

    private String routeName;

    private String medicineFrenquency;

    private String medicineFormCode;

    private String medicineForm;

    private String skinTestFlag;

    private String skinTestResult;

    private String comments;

    private String decoctionMethod;

    private String temporaryFlag;

    private String orderDept;

    private String orderPerson;

    private String orderPersonName;

    private Date orderTime;
    
    private Date orderStartTime;
    
    private Date orderEndTime;
    
    private String stopPerson;

    private String stopPersonName;

    private Date stopTime;

    private String cancelPerson;

    private String cancelPersonName;

    private Date cancelTime;

    private String nurseConfirmPerson;

    private String nurseConfirmPersonName;

    private Date nurseConfirmTime;

    private String execDept;

    private String wardsId;

    private String bedNo;

    private String urgentFlag;
    private String medViewFlag;
    private String adaptiveFlag;

    private String mutexesOrderType;

    private BigDecimal mutexesOrderSn;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private char deleteFlag;

    private Date deleteTime;

    private String visitTypeCode;

    private String visitTypeName;

    private String isHerbal;

    private String prescriptionTypeCode;

    private String prescriptionTypeName;
    
    private String prescriptionNo;

    private String doctorConfirmPersonName;

    private Date doctorConfirmTime;

    private String medicineFreqName;

    private String decoctionMethodName;

    private String orderDeptName;

    private String orderStatusName;

    private String execDeptName;

    private String wardName;

    private String mutexesOrderTypeName;

    private String diseaseName;

    // $Author:tong_meng
    // $Date:2012/9/28 10:42
    // $[BUG]0010919 ADD BEGIN
    private Date visitDate;

    // $[BUG]0010919 ADD END
    private String chargeStatusName;
    
    // $Author:tong_meng
    // $Date:2013/1/30 11:20
    // $[BUG]0013740 ADD BEGIN
    private String chargeStatusCode;
    
    private String days;
    
    private String dispensingQuantity;
    
    private String dispensingUnit;
    // $[BUG]0013740 ADD END
    
    // $Author:wu_jianfeng
    // $Date:2013/3/13 10:42
    // $[BUG]0014531 ADD BEGIN
    private String mutexesOrderNoType;

    // $[BUG]0014531 ADD END
    
    private String orgCode;
    
    private String orgName;
    
    private String englishName;

    public String getChargeStatusName()
    {
        return chargeStatusName;
    }

    public void setChargeStatusName(String chargeStatusName)
    {
        this.chargeStatusName = chargeStatusName;
    }

    public String getChargeStatusCode()
    {
        return chargeStatusCode;
    }

    public void setChargeStatusCode(String chargeStatusCode)
    {
        this.chargeStatusCode = chargeStatusCode;
    }

    public BigDecimal getOrderSn()
    {
        return orderSn;
    }

    public void setOrderSn(BigDecimal orderSn)
    {
        this.orderSn = orderSn;
    }

    public BigDecimal getVisitSn()
    {
        return visitSn;
    }

    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
    }

    public BigDecimal getPrescriptionSn()
    {
        return prescriptionSn;
    }

    public void setPrescriptionSn(BigDecimal prescriptionSn)
    {
        this.prescriptionSn = prescriptionSn;
    }

    public BigDecimal getDocumentSn()
    {
        return documentSn;
    }

    public void setDocumentSn(BigDecimal documentSn)
    {
        this.documentSn = documentSn;
    }

    public BigDecimal getDispensingSn()
    {
        return dispensingSn;
    }

    public void setDispensingSn(BigDecimal dispensingSn)
    {
        this.dispensingSn = dispensingSn;
    }

    public BigDecimal getParentOrderSn()
    {
        return parentOrderSn;
    }

    public void setParentOrderSn(BigDecimal parentOrderSn)
    {
        this.parentOrderSn = parentOrderSn;
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

    public String getOrderLid()
    {
        return orderLid;
    }

    public void setOrderLid(String orderLid)
    {
        this.orderLid = orderLid;
    }

    public String getOrderType()
    {
        return orderType;
    }

    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }

    public String getOrderTypeName()
    {
        return orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName)
    {
        this.orderTypeName = orderTypeName;
    }

    public String getDrugCode()
    {
        return drugCode;
    }

    public void setDrugCode(String drugCode)
    {
        this.drugCode = drugCode;
    }

    public String getDrugName()
    {
        return drugName;
    }

    public void setDrugName(String drugName)
    {
        this.drugName = drugName;
    }

    public String getApprovedDrugName()
    {
        return approvedDrugName;
    }

    public void setApprovedDrugName(String approvedDrugName)
    {
        this.approvedDrugName = approvedDrugName;
    }

    public String getMedicineClass()
    {
        return medicineClass;
    }

    public void setMedicineClass(String medicineClass)
    {
        this.medicineClass = medicineClass;
    }

    public String getMedicineClassName()
    {
        return medicineClassName;
    }

    public void setMedicineClassName(String medicineClassName)
    {
        this.medicineClassName = medicineClassName;
    }

    public String getMedicineType()
    {
        return medicineType;
    }

    public void setMedicineType(String medicineType)
    {
        this.medicineType = medicineType;
    }

    public String getMedicineTypeName()
    {
        return medicineTypeName;
    }

    public void setMedicineTypeName(String medicineTypeName)
    {
        this.medicineTypeName = medicineTypeName;
    }

    public String getDosage()
    {
        return dosage;
    }

    public void setDosage(String dosage)
    {
        this.dosage = dosage;
    }

    public String getDosageUnit()
    {
        return dosageUnit;
    }

    public void setDosageUnit(String dosageUnit)
    {
        this.dosageUnit = dosageUnit;
    }

    public String getTotalDosage()
    {
        return totalDosage;
    }

    public void setTotalDosage(String totalDosage)
    {
        this.totalDosage = totalDosage;
    }

    public String getTotalDosageUnit()
    {
        return totalDosageUnit;
    }

    public void setTotalDosageUnit(String totalDosageUnit)
    {
        this.totalDosageUnit = totalDosageUnit;
    }

    public String getRouteCode()
    {
        return routeCode;
    }

    public void setRouteCode(String routeCode)
    {
        this.routeCode = routeCode;
    }

    public String getRouteName()
    {
        return routeName;
    }

    public void setRouteName(String routeName)
    {
        this.routeName = routeName;
    }

    public String getMedicineFrenquency()
    {
        return medicineFrenquency;
    }

    public void setMedicineFrenquency(String medicineFrenquency)
    {
        this.medicineFrenquency = medicineFrenquency;
    }

    public String getMedicineFormCode()
    {
        return medicineFormCode;
    }

    public void setMedicineFormCode(String medicineFormCode)
    {
        this.medicineFormCode = medicineFormCode;
    }

    public String getMedicineForm()
    {
        return medicineForm;
    }

    public void setMedicineForm(String medicineForm)
    {
        this.medicineForm = medicineForm;
    }

    public String getSkinTestFlag()
    {
        return skinTestFlag;
    }

    public void setSkinTestFlag(String skinTestFlag)
    {
        this.skinTestFlag = skinTestFlag;
    }

    public String getSkinTestResult()
    {
        return skinTestResult;
    }

    public void setSkinTestResult(String skinTestResult)
    {
        this.skinTestResult = skinTestResult;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public String getDecoctionMethod()
    {
        return decoctionMethod;
    }

    public void setDecoctionMethod(String decoctionMethod)
    {
        this.decoctionMethod = decoctionMethod;
    }

    public String getTemporaryFlag()
    {
        return temporaryFlag;
    }

    public void setTemporaryFlag(String temporaryFlag)
    {
        this.temporaryFlag = temporaryFlag;
    }

    public String getOrderDept()
    {
        return orderDept;
    }

    public void setOrderDept(String orderDept)
    {
        this.orderDept = orderDept;
    }

    public String getOrderPerson()
    {
        return orderPerson;
    }

    public void setOrderPerson(String orderPerson)
    {
        this.orderPerson = orderPerson;
    }

    public String getOrderPersonName()
    {
        return orderPersonName;
    }

    public void setOrderPersonName(String orderPersonName)
    {
        this.orderPersonName = orderPersonName;
    }

    public Date getOrderTime()
    {
        return orderTime;
    }

    public void setOrderTime(Date orderTime)
    {
        this.orderTime = orderTime;
    }

    public String getStopPerson()
    {
        return stopPerson;
    }

    public void setStopPerson(String stopPerson)
    {
        this.stopPerson = stopPerson;
    }

    public String getStopPersonName()
    {
        return stopPersonName;
    }

    public void setStopPersonName(String stopPersonName)
    {
        this.stopPersonName = stopPersonName;
    }

    public Date getStopTime()
    {
        return stopTime;
    }

    public void setStopTime(Date stopTime)
    {
        this.stopTime = stopTime;
    }

    public String getCancelPerson()
    {
        return cancelPerson;
    }

    public void setCancelPerson(String cancelPerson)
    {
        this.cancelPerson = cancelPerson;
    }

    public String getCancelPersonName()
    {
        return cancelPersonName;
    }

    public void setCancelPersonName(String cancelPersonName)
    {
        this.cancelPersonName = cancelPersonName;
    }

    public Date getNurseConfirmTime()
    {
        return nurseConfirmTime;
    }

    public void setNurseConfirmTime(Date nurseConfirmTime)
    {
        this.nurseConfirmTime = nurseConfirmTime;
    }

    public String getExecDept()
    {
        return execDept;
    }

    public void setExecDept(String execDept)
    {
        this.execDept = execDept;
    }

    public String getWardsId()
    {
        return wardsId;
    }

    public void setWardsId(String wardsId)
    {
        this.wardsId = wardsId;
    }

    public String getBedNo()
    {
        return bedNo;
    }

    public void setBedNo(String bedNo)
    {
        this.bedNo = bedNo;
    }

    public String getMutexesOrderType()
    {
        return mutexesOrderType;
    }

    public void setMutexesOrderType(String mutexesOrderType)
    {
        this.mutexesOrderType = mutexesOrderType;
    }

    public BigDecimal getMutexesOrderSn()
    {
        return mutexesOrderSn;
    }

    public void setMutexesOrderSn(BigDecimal mutexesOrderSn)
    {
        this.mutexesOrderSn = mutexesOrderSn;
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

    public char getDeleteFlag()
    {
        return deleteFlag;
    }

    public void setDeleteFlag(char deleteFlag)
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

    public String getVisitTypeName()
    {
        return visitTypeName;
    }

    public void setVisitTypeName(String visitTypeName)
    {
        this.visitTypeName = visitTypeName;
    }

    public String getIsHerbal()
    {
        return isHerbal;
    }

    public void setIsHerbal(String isHerbal)
    {
        this.isHerbal = isHerbal;
    }

    public Date getCancelTime()
    {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime)
    {
        this.cancelTime = cancelTime;
    }

    public String getNurseConfirmPerson()
    {
        return nurseConfirmPerson;
    }

    public void setNurseConfirmPerson(String nurseConfirmPerson)
    {
        this.nurseConfirmPerson = nurseConfirmPerson;
    }

    public String getNurseConfirmPersonName()
    {
        return nurseConfirmPersonName;
    }

    public void setNurseConfirmPersonName(String nurseConfirmPersonName)
    {
        this.nurseConfirmPersonName = nurseConfirmPersonName;
    }

    public String getDoctorConfirmPersonName()
    {
        return doctorConfirmPersonName;
    }

    public void setDoctorConfirmPersonName(String doctorConfirmPersonName)
    {
        this.doctorConfirmPersonName = doctorConfirmPersonName;
    }

    public Date getDoctorConfirmTime()
    {
        return doctorConfirmTime;
    }

    public void setDoctorConfirmTime(Date doctorConfirmTime)
    {
        this.doctorConfirmTime = doctorConfirmTime;
    }

    public String getPrescriptionTypeName()
    {
        return prescriptionTypeName;
    }

    public void setPrescriptionTypeName(String prescriptionTypeName)
    {
        this.prescriptionTypeName = prescriptionTypeName;
    }

    public String getVisitTypeCode()
    {
        return visitTypeCode;
    }

    public void setVisitTypeCode(String visitTypeCode)
    {
        this.visitTypeCode = visitTypeCode;
    }

    public String getPrescriptionTypeCode()
    {
        return prescriptionTypeCode;
    }

    public void setPrescriptionTypeCode(String prescriptionTypeCode)
    {
        this.prescriptionTypeCode = prescriptionTypeCode;
    }
    
    public String getPrescriptionNo() {
		return prescriptionNo;
	}

	public void setPrescriptionNo(String prescriptionNo) {
		this.prescriptionNo = prescriptionNo;
	}

	public String getMedicineFreqName()
    {
        return medicineFreqName;
    }

    public void setMedicineFreqName(String medicineFreqName)
    {
        this.medicineFreqName = medicineFreqName;
    }

    public String getDecoctionMethodName()
    {
        return decoctionMethodName;
    }

    public void setDecoctionMethodName(String decoctionMethodName)
    {
        this.decoctionMethodName = decoctionMethodName;
    }

    public String getOrderDeptName()
    {
        return orderDeptName;
    }

    public void setOrderDeptName(String orderDeptName)
    {
        this.orderDeptName = orderDeptName;
    }

    public String getOrderStatusName()
    {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName)
    {
        this.orderStatusName = orderStatusName;
    }

    public String getExecDeptName()
    {
        return execDeptName;
    }

    public void setExecDeptName(String execDeptName)
    {
        this.execDeptName = execDeptName;
    }

    public String getWardName()
    {
        return wardName;
    }

    public void setWardName(String wardName)
    {
        this.wardName = wardName;
    }

    public String getMutexesOrderTypeName()
    {
        return mutexesOrderTypeName;
    }

    public void setMutexesOrderTypeName(String mutexesOrderTypeName)
    {
        this.mutexesOrderTypeName = mutexesOrderTypeName;
    }

    public String getUrgentFlag()
    {
        return urgentFlag;
    }

    public void setUrgentFlag(String urgentFlag)
    {
        this.urgentFlag = urgentFlag;
    }

    public String getMedViewFlag()
    {
        return medViewFlag;
    }

    public void setMedViewFlag(String medViewFlag)
    {
        this.medViewFlag = medViewFlag;
    }

    public String getAdaptiveFlag()
    {
        return adaptiveFlag;
    }

    public void setAdaptiveFlag(String adaptiveFlag)
    {
        this.adaptiveFlag = adaptiveFlag;
    }

    public String getDiseaseName()
    {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName)
    {
        this.diseaseName = diseaseName;
    }

    public Date getVisitDate()
    {
        return visitDate;
    }

    public void setVisitDate(Date visitDate)
    {
        this.visitDate = visitDate;
    }

    public String getDays()
    {
        return days;
    }

    public void setDays(String days)
    {
        this.days = days;
    }

    public String getDispensingQuantity()
    {
        return dispensingQuantity;
    }

    public void setDispensingQuantity(String dispensingQuantity)
    {
        this.dispensingQuantity = dispensingQuantity;
    }

    public String getDispensingUnit()
    {
        return dispensingUnit;
    }

    public void setDispensingUnit(String dispensingUnit)
    {
        this.dispensingUnit = dispensingUnit;
    }

    public Date getOrderStartTime()
    {
        return orderStartTime;
    }

    public void setOrderStartTime(Date orderStartTime)
    {
        this.orderStartTime = orderStartTime;
    }

    public Date getOrderEndTime()
    {
        return orderEndTime;
    }

    public void setOrderEndTime(Date orderEndTime)
    {
        this.orderEndTime = orderEndTime;
    }

    public String getMutexesOrderNoType()
    {
        return mutexesOrderNoType;
    }

    public void setMutexesOrderNoType(String mutexesOrderNoType)
    {
        this.mutexesOrderNoType = mutexesOrderNoType;
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

	public String getOrderSeqnum() {
		return orderSeqnum;
	}

	public void setOrderSeqnum(String orderSeqnum) {
		this.orderSeqnum = orderSeqnum;
	}

	public String getOrderSetCode() {
		return orderSetCode;
	}

	public void setOrderSetCode(String orderSetCode) {
		this.orderSetCode = orderSetCode;
	}

	public String getOrderDescribe() {
		return orderDescribe;
	}

	public void setOrderDescribe(String orderDescribe) {
		this.orderDescribe = orderDescribe;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
    
}
