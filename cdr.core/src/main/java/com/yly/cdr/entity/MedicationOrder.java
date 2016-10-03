package com.yly.cdr.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.founder.fasf.core.util.daohelper.annotation.Generator;

import java.io.Serializable;

@Entity
@Table(name = "MEDICATION_ORDER")
public class MedicationOrder implements Serializable
{
    private BigDecimal orderSn;
    private BigDecimal visitSn;
    private BigDecimal prescriptionSn;
    private BigDecimal documentSn;
    private BigDecimal parentOrderSn;
    private BigDecimal patientSn;
    private BigDecimal orderSeqnum;
    private String orderSetCode;
    private String orderDescribe;
    private String patientDomain;
    private String patientLid;
    private String orderLid;
    private String orderType;
    private String orderTypeName;
    private String drugCode;
    private String drugName;
    private String approvedDrugName;
    private String medicineClass;
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
    private String mutexesOrderType;
    private BigDecimal mutexesOrderSn;
    private Date createTime;
    private Date updateTime;
    private BigDecimal updateCount;
    private String deleteFlag;
    private Date deleteTime;
    private String orderStatus;
    private String doctorConfirmPerson;
    private String doctorConfirmPersonName;
    private Date doctorConfirmTime;
    private Date orderStartTime;
    private Date orderEndTime;
    private String serialNo;
    private String medicineClassName;
    private String medicineFreqName;
    private String decoctionMethodName;
    private String orderDeptName;
    private String orderStatusName;
    private String execDeptName;
    private String wardName;
    private String mutexesOrderTypeName;
    private String urgentFlag;
    private String chargeStatusCode;
    private String chargeStatusName;
    private BigDecimal days;
    private String dispensingQuantity;
    private String dispensingUnit;
    private String mutexesOrderNoType;
    private String adaptiveFlag;
    private String medViewFlag;
    private String createby;
    private String updateby;
    private String deleteby;
    private String orgCode;
    private String orgName;
    private String englishName;
    @Id
    public BigDecimal getOrderSn()
    {
        return this.orderSn;
    }
    public void setOrderSn(BigDecimal orderSn)
    {
        this.orderSn = orderSn;
    }
    @Column(name = "VISIT_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getVisitSn()
    {
        return this.visitSn;
    }
    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
    }
    @Column(name = "PRESCRIPTION_SN", precision = 22, scale = 0)
    public BigDecimal getPrescriptionSn()
    {
        return this.prescriptionSn;
    }
    public void setPrescriptionSn(BigDecimal prescriptionSn)
    {
        this.prescriptionSn = prescriptionSn;
    }
    @Column(name = "DOCUMENT_SN", precision = 22, scale = 0)
    public BigDecimal getDocumentSn()
    {
        return this.documentSn;
    }
    public void setDocumentSn(BigDecimal documentSn)
    {
        this.documentSn = documentSn;
    }
    @Column(name = "PARENT_ORDER_SN", precision = 22, scale = 0)
    public BigDecimal getParentOrderSn()
    {
        return this.parentOrderSn;
    }
    public void setParentOrderSn(BigDecimal parentOrderSn)
    {
        this.parentOrderSn = parentOrderSn;
    }
    @Column(name = "PATIENT_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getPatientSn()
    {
        return this.patientSn;
    }
    public void setPatientSn(BigDecimal patientSn)
    {
        this.patientSn = patientSn;
    }
    @Column(name = "ORDER_SEQNUM", nullable = false, precision = 22, scale = 0)
    public BigDecimal getOrderSeqnum()
    {
        return this.orderSeqnum;
    }
    public void setOrderSeqnum(BigDecimal orderSeqnum)
    {
        this.orderSeqnum = orderSeqnum;
    }
    @Column(name = "ORDER_SET_CODE", length = 12)
    public String getOrderSetCode() {
		return orderSetCode;
	}
	public void setOrderSetCode(String orderSetCode) {
		this.orderSetCode = orderSetCode;
	}
	@Column(name = "ORDER_DESCRIBE",length = 512)
	public String getOrderDescribe() {
		return orderDescribe;
	}
	public void setOrderDescribe(String orderDescribe) {
		this.orderDescribe = orderDescribe;
	}
	@Column(name = "PATIENT_DOMAIN", nullable = false, length = 6)
    public String getPatientDomain()
    {
        return this.patientDomain;
    }
    public void setPatientDomain(String patientDomain)
    {
        this.patientDomain = patientDomain;
    }
    @Column(name = "PATIENT_LID", nullable = false, length = 12)
    public String getPatientLid()
    {
        return this.patientLid;
    }
    public void setPatientLid(String patientLid)
    {
        this.patientLid = patientLid;
    }
    @Column(name = "ORDER_LID", nullable = false, length = 64)
    public String getOrderLid()
    {
        return this.orderLid;
    }
    public void setOrderLid(String orderLid)
    {
        this.orderLid = orderLid;
    }
    @Column(name = "ORDER_TYPE", length = 12)
    public String getOrderType()
    {
        return this.orderType;
    }
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    @Column(name = "ORDER_TYPE_NAME", length = 72)
    public String getOrderTypeName()
    {
        return this.orderTypeName;
    }
    public void setOrderTypeName(String orderTypeName)
    {
        this.orderTypeName = orderTypeName;
    }
    @Column(name = "DRUG_CODE", length = 32)
    public String getDrugCode()
    {
        return this.drugCode;
    }
    public void setDrugCode(String drugCode)
    {
        this.drugCode = drugCode;
    }
    @Column(name = "DRUG_NAME", length = 300)
    public String getDrugName()
    {
        return this.drugName;
    }
    public void setDrugName(String drugName)
    {
        this.drugName = drugName;
    }
    @Column(name = "APPROVED_DRUG_NAME", length = 180)
    public String getApprovedDrugName()
    {
        return this.approvedDrugName;
    }
    public void setApprovedDrugName(String approvedDrugName)
    {
        this.approvedDrugName = approvedDrugName;
    }
    @Column(name = "MEDICINE_CLASS", length = 12)
    public String getMedicineClass()
    {
        return this.medicineClass;
    }
    public void setMedicineClass(String medicineClass)
    {
        this.medicineClass = medicineClass;
    }
    @Column(name = "MEDICINE_TYPE", length = 128)
    public String getMedicineType()
    {
        return this.medicineType;
    }
    public void setMedicineType(String medicineType)
    {
        this.medicineType = medicineType;
    }
    @Column(name = "MEDICINE_TYPE_NAME", length = 240)
    public String getMedicineTypeName()
    {
        return this.medicineTypeName;
    }
    public void setMedicineTypeName(String medicineTypeName)
    {
        this.medicineTypeName = medicineTypeName;
    }
    @Column(name = "DOSAGE", length = 32)
    public String getDosage()
    {
        return this.dosage;
    }
    public void setDosage(String dosage)
    {
        this.dosage = dosage;
    }
    @Column(name = "DOSAGE_UNIT", length = 32)
    public String getDosageUnit()
    {
        return this.dosageUnit;
    }
    public void setDosageUnit(String dosageUnit)
    {
        this.dosageUnit = dosageUnit;
    }
    @Column(name = "TOTAL_DOSAGE", length = 32)
    public String getTotalDosage()
    {
        return this.totalDosage;
    }
    public void setTotalDosage(String totalDosage)
    {
        this.totalDosage = totalDosage;
    }
    @Column(name = "TOTAL_DOSAGE_UNIT", length = 32)
    public String getTotalDosageUnit()
    {
        return this.totalDosageUnit;
    }
    public void setTotalDosageUnit(String totalDosageUnit)
    {
        this.totalDosageUnit = totalDosageUnit;
    }
    @Column(name = "ROUTE_CODE", length = 12)
    public String getRouteCode()
    {
        return this.routeCode;
    }
    public void setRouteCode(String routeCode)
    {
        this.routeCode = routeCode;
    }
    @Column(name = "ROUTE_NAME", length = 192)
    public String getRouteName()
    {
        return this.routeName;
    }
    public void setRouteName(String routeName)
    {
        this.routeName = routeName;
    }
    @Column(name = "MEDICINE_FRENQUENCY", length = 64)
    public String getMedicineFrenquency()
    {
        return this.medicineFrenquency;
    }
    public void setMedicineFrenquency(String medicineFrenquency)
    {
        this.medicineFrenquency = medicineFrenquency;
    }
    @Column(name = "MEDICINE_FORM_CODE", length = 64)
    public String getMedicineFormCode()
    {
        return this.medicineFormCode;
    }
    public void setMedicineFormCode(String medicineFormCode)
    {
        this.medicineFormCode = medicineFormCode;
    }
    @Column(name = "MEDICINE_FORM", length = 96)
    public String getMedicineForm()
    {
        return this.medicineForm;
    }
    public void setMedicineForm(String medicineForm)
    {
        this.medicineForm = medicineForm;
    }
    @Column(name = "SKIN_TEST_FLAG", length = 1)
    public String getSkinTestFlag()
    {
        return this.skinTestFlag;
    }
    public void setSkinTestFlag(String skinTestFlag)
    {
        this.skinTestFlag = skinTestFlag;
    }
    @Column(name = "SKIN_TEST_RESULT", length = 10)
    public String getSkinTestResult()
    {
        return this.skinTestResult;
    }
    public void setSkinTestResult(String skinTestResult)
    {
        this.skinTestResult = skinTestResult;
    }
    @Column(name = "COMMENTS", length = 384)
    public String getComments()
    {
        return this.comments;
    }
    public void setComments(String comments)
    {
        this.comments = comments;
    }
    @Column(name = "DECOCTION_METHOD", length = 12)
    public String getDecoctionMethod()
    {
        return this.decoctionMethod;
    }
    public void setDecoctionMethod(String decoctionMethod)
    {
        this.decoctionMethod = decoctionMethod;
    }
    @Column(name = "TEMPORARY_FLAG", length = 1)
    public String getTemporaryFlag()
    {
        return this.temporaryFlag;
    }
    public void setTemporaryFlag(String temporaryFlag)
    {
        this.temporaryFlag = temporaryFlag;
    }
    @Column(name = "ORDER_DEPT", length = 12)
    public String getOrderDept()
    {
        return this.orderDept;
    }
    public void setOrderDept(String orderDept)
    {
        this.orderDept = orderDept;
    }
    @Column(name = "ORDER_PERSON", length = 12)
    public String getOrderPerson()
    {
        return this.orderPerson;
    }
    public void setOrderPerson(String orderPerson)
    {
        this.orderPerson = orderPerson;
    }
    @Column(name = "ORDER_PERSON_NAME", length = 255)
    public String getOrderPersonName()
    {
        return this.orderPersonName;
    }
    public void setOrderPersonName(String orderPersonName)
    {
        this.orderPersonName = orderPersonName;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "ORDER_TIME", length = 7)
    public Date getOrderTime()
    {
        return this.orderTime;
    }
    public void setOrderTime(Date orderTime)
    {
        this.orderTime = orderTime;
    }
    @Column(name = "STOP_PERSON", length = 12)
    public String getStopPerson()
    {
        return this.stopPerson;
    }
    public void setStopPerson(String stopPerson)
    {
        this.stopPerson = stopPerson;
    }
    @Column(name = "STOP_PERSON_NAME", length = 56)
    public String getStopPersonName()
    {
        return this.stopPersonName;
    }
    public void setStopPersonName(String stopPersonName)
    {
        this.stopPersonName = stopPersonName;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "STOP_TIME", length = 7)
    public Date getStopTime()
    {
        return this.stopTime;
    }
    public void setStopTime(Date stopTime)
    {
        this.stopTime = stopTime;
    }
    @Column(name = "CANCEL_PERSON", length = 12)
    public String getCancelPerson()
    {
        return this.cancelPerson;
    }
    public void setCancelPerson(String cancelPerson)
    {
        this.cancelPerson = cancelPerson;
    }
    @Column(name = "CANCEL_PERSON_NAME", length = 56)
    public String getCancelPersonName()
    {
        return this.cancelPersonName;
    }
    public void setCancelPersonName(String cancelPersonName)
    {
        this.cancelPersonName = cancelPersonName;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "CANCEL_TIME", length = 7)
    public Date getCancelTime()
    {
        return this.cancelTime;
    }
    public void setCancelTime(Date cancelTime)
    {
        this.cancelTime = cancelTime;
    }
    @Column(name = "NURSE_CONFIRM_PERSON", length = 12)
    public String getNurseConfirmPerson()
    {
        return this.nurseConfirmPerson;
    }
    public void setNurseConfirmPerson(String nurseConfirmPerson)
    {
        this.nurseConfirmPerson = nurseConfirmPerson;
    }
    @Column(name = "NURSE_CONFIRM_PERSON_NAME")
    public String getNurseConfirmPersonName()
    {
        return this.nurseConfirmPersonName;
    }
    public void setNurseConfirmPersonName(String nurseConfirmPersonName)
    {
        this.nurseConfirmPersonName = nurseConfirmPersonName;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "NURSE_CONFIRM_TIME", length = 7)
    public Date getNurseConfirmTime()
    {
        return this.nurseConfirmTime;
    }
    public void setNurseConfirmTime(Date nurseConfirmTime)
    {
        this.nurseConfirmTime = nurseConfirmTime;
    }
    @Column(name = "EXEC_DEPT", length = 12)
    public String getExecDept()
    {
        return this.execDept;
    }
    public void setExecDept(String execDept)
    {
        this.execDept = execDept;
    }
    @Column(name = "WARDS_ID", length = 12)
    public String getWardsId()
    {
        return this.wardsId;
    }
    public void setWardsId(String wardsId)
    {
        this.wardsId = wardsId;
    }
    @Column(name = "BED_NO", length = 80)
    public String getBedNo()
    {
        return this.bedNo;
    }
    public void setBedNo(String bedNo)
    {
        this.bedNo = bedNo;
    }
    @Column(name = "MUTEXES_ORDER_TYPE", length = 32)
    public String getMutexesOrderType()
    {
        return this.mutexesOrderType;
    }
    public void setMutexesOrderType(String mutexesOrderType)
    {
        this.mutexesOrderType = mutexesOrderType;
    }
    @Column(name = "MUTEXES_ORDER_SN", precision = 22, scale = 0)
    public BigDecimal getMutexesOrderSn()
    {
        return this.mutexesOrderSn;
    }
    public void setMutexesOrderSn(BigDecimal mutexesOrderSn)
    {
        this.mutexesOrderSn = mutexesOrderSn;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_TIME", nullable = false, length = 7)
    public Date getCreateTime()
    {
        return this.createTime;
    }
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATE_TIME", nullable = false, length = 7)
    public Date getUpdateTime()
    {
        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    @Column(name = "UPDATE_COUNT", nullable = false, precision = 22, scale = 0)
    public BigDecimal getUpdateCount()
    {
        return this.updateCount;
    }
    public void setUpdateCount(BigDecimal updateCount)
    {
        this.updateCount = updateCount;
    }
    @Column(name = "DELETE_FLAG", nullable = false, length = 1)
    public String getDeleteFlag()
    {
        return this.deleteFlag;
    }
    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "DELETE_TIME", length = 7)
    public Date getDeleteTime()
    {
        return this.deleteTime;
    }
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    @Column(name = "ORDER_STATUS", length = 12)
    public String getOrderStatus()
    {
        return this.orderStatus;
    }
    public void setOrderStatus(String orderStatus)
    {
        this.orderStatus = orderStatus;
    }
    @Column(name = "DOCTOR_CONFIRM_PERSON", length = 12)
    public String getDoctorConfirmPerson()
    {
        return this.doctorConfirmPerson;
    }
    public void setDoctorConfirmPerson(String doctorConfirmPerson)
    {
        this.doctorConfirmPerson = doctorConfirmPerson;
    }
    @Column(name = "DOCTOR_CONFIRM_PERSON_NAME")
    public String getDoctorConfirmPersonName()
    {
        return this.doctorConfirmPersonName;
    }
    public void setDoctorConfirmPersonName(String doctorConfirmPersonName)
    {
        this.doctorConfirmPersonName = doctorConfirmPersonName;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "DOCTOR_CONFIRM_TIME", length = 7)
    public Date getDoctorConfirmTime()
    {
        return this.doctorConfirmTime;
    }
    public void setDoctorConfirmTime(Date doctorConfirmTime)
    {
        this.doctorConfirmTime = doctorConfirmTime;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "ORDER_START_TIME", length = 7)
    public Date getOrderStartTime()
    {
        return this.orderStartTime;
    }
    public void setOrderStartTime(Date orderStartTime)
    {
        this.orderStartTime = orderStartTime;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "ORDER_END_TIME", length = 7)
    public Date getOrderEndTime()
    {
        return this.orderEndTime;
    }
    public void setOrderEndTime(Date orderEndTime)
    {
        this.orderEndTime = orderEndTime;
    }
    @Column(name = "SERIAL_NO", length = 20)
    public String getSerialNo()
    {
        return this.serialNo;
    }
    public void setSerialNo(String serialNo)
    {
        this.serialNo = serialNo;
    }
    @Column(name = "MEDICINE_CLASS_NAME", length = 96)
    public String getMedicineClassName()
    {
        return this.medicineClassName;
    }
    public void setMedicineClassName(String medicineClassName)
    {
        this.medicineClassName = medicineClassName;
    }
    @Column(name = "MEDICINE_FREQ_NAME", length = 192)
    public String getMedicineFreqName()
    {
        return this.medicineFreqName;
    }
    public void setMedicineFreqName(String medicineFreqName)
    {
        this.medicineFreqName = medicineFreqName;
    }
    @Column(name = "DECOCTION_METHOD_NAME", length = 40)
    public String getDecoctionMethodName()
    {
        return this.decoctionMethodName;
    }
    public void setDecoctionMethodName(String decoctionMethodName)
    {
        this.decoctionMethodName = decoctionMethodName;
    }
    @Column(name = "ORDER_DEPT_NAME", length = 192)
    public String getOrderDeptName()
    {
        return this.orderDeptName;
    }
    public void setOrderDeptName(String orderDeptName)
    {
        this.orderDeptName = orderDeptName;
    }
    @Column(name = "ORDER_STATUS_NAME", length = 40)
    public String getOrderStatusName()
    {
        return this.orderStatusName;
    }
    public void setOrderStatusName(String orderStatusName)
    {
        this.orderStatusName = orderStatusName;
    }
    @Column(name = "EXEC_DEPT_NAME", length = 192)
    public String getExecDeptName()
    {
        return this.execDeptName;
    }
    public void setExecDeptName(String execDeptName)
    {
        this.execDeptName = execDeptName;
    }
    @Column(name = "WARD_NAME", length = 256)
    public String getWardName()
    {
        return this.wardName;
    }
    public void setWardName(String wardName)
    {
        this.wardName = wardName;
    }
    @Column(name = "MUTEXES_ORDER_TYPE_NAME", length = 40)
    public String getMutexesOrderTypeName()
    {
        return this.mutexesOrderTypeName;
    }
    public void setMutexesOrderTypeName(String mutexesOrderTypeName)
    {
        this.mutexesOrderTypeName = mutexesOrderTypeName;
    }
    @Column(name = "URGENT_FLAG", length = 1)
    public String getUrgentFlag()
    {
        return this.urgentFlag;
    }
    public void setUrgentFlag(String urgentFlag)
    {
        this.urgentFlag = urgentFlag;
    }
    @Column(name = "CHARGE_STATUS_CODE", length = 2)
    public String getChargeStatusCode()
    {
        return chargeStatusCode;
    }
    public void setChargeStatusCode(String chargeStatusCode)
    {
        this.chargeStatusCode = chargeStatusCode;
    }
    @Column(name = "CHARGE_STATUS_NAME", length = 40)
    public String getChargeStatusName()
    {
        return chargeStatusName;
    }
    public void setChargeStatusName(String chargeStatusName)
    {
        this.chargeStatusName = chargeStatusName;
    }
    @Column(name = "DAYS")
    public BigDecimal getDays()
    {
        return days;
    }
    public void setDays(BigDecimal days)
    {
        this.days = days;
    }
    @Column(name = "DISPENSING_QUANTITY")
    public String getDispensingQuantity()
    {
        return dispensingQuantity;
    }
    public void setDispensingQuantity(String dispensingQuantity)
    {
        this.dispensingQuantity = dispensingQuantity;
    }
    @Column(name = "DISPENSING_UNIT")
    public String getDispensingUnit()
    {
        return dispensingUnit;
    }
    public void setDispensingUnit(String dispensingUnit)
    {
        this.dispensingUnit = dispensingUnit;
    }
    @Column(name = "MUTEXES_ORDER_NO_TYPE", length = 12)
    public String getMutexesOrderNoType()
    {
        return this.mutexesOrderNoType;
    }
    public void setMutexesOrderNoType(String mutexesOrderNoType)
    {
        this.mutexesOrderNoType = mutexesOrderNoType;
    }
    @Column(name = "ADAPTIVE_FLAG", length = 1)
    public String getAdaptiveFlag()
    {
        return adaptiveFlag;
    }
    public void setAdaptiveFlag(String adaptiveFlag)
    {
        this.adaptiveFlag = adaptiveFlag;
    }
    @Column(name = "MED_VIEW_FLAG", length = 1)
    public String getMedViewFlag()
    {
        return medViewFlag;
    }
    public void setMedViewFlag(String medViewFlag)
    {
        this.medViewFlag = medViewFlag;
    }
    @Column(name = "CREATEBY", length = 12)
    public String getCreateby()
    {
        return this.createby;
    }

    public void setCreateby(String createby)
    {
        this.createby = createby;
    }
    
    @Column(name = "UPDATEBY", length = 12)
    public String getUpdateby()
    {
        return this.updateby;
    }

    public void setUpdateby(String updateby)
    {
        this.updateby = updateby;
    }
    
    @Column(name = "DELETEBY", length = 12)
    public String getDeleteby()
    {
        return this.deleteby;
    }

    public void setDeleteby(String deleteby)
    {
        this.deleteby = deleteby;
    }

    @Column(name = "ORG_CODE", length = 12)
    public String getOrgCode()
    {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }
    
    @Column(name = "ORG_NAME", length = 12)
    public String getOrgName()
    {
        return this.orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }
    
    @Column(name = "ENGLISH_NAME", length = 200)
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
}
