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
@Table(name = "CARE_ORDER")
public class CareOrder implements Serializable
{

    private BigDecimal orderSn;

    private BigDecimal visitSn;

    private BigDecimal patientSn;

    private String patientDomain;

    private String patientLid;

    private String orderLid;

    private String orderType;

    private String orderName;

    private String freq;

    private String orderDept;

    private String orderPerson;

    private String orderPersonName;

    private Date inputTime;

    private String nurseConfirmPerson;

    private String nurseConfirmPersonName;

    private Date nurseConfirmTime;

    private String stopPerson;

    private String stopPersonName;

    private Date stopTime;

    private String cancelPerson;

    private String cancelPersonName;

    private Date cancelTime;

    private String execDept;

    private BigDecimal quantity;

    private String description;

    private String comments;

    private BigDecimal parentOrderSn;

    private String mutexesOrderType;

    private BigDecimal mutexesOrderSn;

    private String orderStatus;

    private String usage;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String wardsId;

    private String bedNo;

    private String quantityUnit;

    private String orderCode;

    private String doctorConfirmPerson;

    private String doctorConfirmPersonName;

    private Date doctorConfirmTime;

    private String freqName;

    private String orderDeptName;

    private String execDeptName;

    private String mutexesOrderTypeName;

    private String wardsName;

    private String orderStatusName;

    private String orderTypeName;

    private String urgentFlag;

    private Date orderStartTime;

    private Date orderEndTime;

    private String chargeStatusCode;

    private String chargeStatusName;

    private String mutexesOrderNoType;
    
    private String skinTestFlag;
    
    private String medViewFlag;
    
    private String adaptiveFlag;

    private String createby;
    
    private String updateby;
    
    private String deleteby;
    
    private String orgCode;
    
    private String orgName;
    
    private Integer orderSeqnum;
    
    private String orderSetCode;
    
    private String orderDescribe;
    
    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_ORDER") })
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

    @Column(name = "PATIENT_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getPatientSn()
    {
        return this.patientSn;
    }

    public void setPatientSn(BigDecimal patientSn)
    {
        this.patientSn = patientSn;
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

    @Column(name = "ORDER_LID", length = 64)
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

    @Column(name = "ORDER_NAME", length = 768)
    public String getOrderName()
    {
        return this.orderName;
    }

    public void setOrderName(String orderName)
    {
        this.orderName = orderName;
    }

    @Column(name = "FREQ", length = 12)
    public String getFreq()
    {
        return this.freq;
    }

    public void setFreq(String freq)
    {
        this.freq = freq;
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

    @Column(name = "ORDER_PERSON_NAME", length = 192)
    public String getOrderPersonName()
    {
        return this.orderPersonName;
    }

    public void setOrderPersonName(String orderPersonName)
    {
        this.orderPersonName = orderPersonName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "INPUT_TIME", length = 7)
    public Date getInputTime()
    {
        return this.inputTime;
    }

    public void setInputTime(Date inputTime)
    {
        this.inputTime = inputTime;
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

    @Column(name = "NURSE_CONFIRM_PERSON_NAME", length = 192)
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

    @Column(name = "STOP_PERSON", length = 12)
    public String getStopPerson()
    {
        return this.stopPerson;
    }

    public void setStopPerson(String stopPerson)
    {
        this.stopPerson = stopPerson;
    }

    @Column(name = "STOP_PERSON_NAME", length = 192)
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

    @Column(name = "CANCEL_PERSON_NAME", length = 192)
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

    @Column(name = "EXEC_DEPT", length = 12)
    public String getExecDept()
    {
        return this.execDept;
    }

    public void setExecDept(String execDept)
    {
        this.execDept = execDept;
    }

    @Column(name = "QUANTITY", precision = 14, scale = 0)
    public BigDecimal getQuantity()
    {
        return this.quantity;
    }

    public void setQuantity(BigDecimal quantity)
    {
        this.quantity = quantity;
    }

    @Column(name = "DESCRIPTION", length = 768)
    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Column(name = "COMMENTS", length = 768)
    public String getComments()
    {
        return this.comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
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

    @Column(name = "MUTEXES_ORDER_TYPE", length = 12)
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

    @Column(name = "ORDER_STATUS", length = 12)
    public String getOrderStatus()
    {
        return this.orderStatus;
    }

    public void setOrderStatus(String orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    @Column(name = "USAGE", length = 1)
    public String getUsage()
    {
        return this.usage;
    }

    public void setUsage(String usage)
    {
        this.usage = usage;
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

    @Column(name = "QUANTITY_UNIT", length = 32)
    public String getQuantityUnit()
    {
        return this.quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit)
    {
        this.quantityUnit = quantityUnit;
    }

    @Column(name = "ORDER_CODE", length = 20)
    public String getOrderCode()
    {
        return this.orderCode;
    }

    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
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

    @Column(name = "DOCTOR_CONFIRM_PERSON_NAME", length = 192)
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

    @Column(name = "FREQ_NAME", length = 192)
    public String getFreqName()
    {
        return this.freqName;
    }

    public void setFreqName(String freqName)
    {
        this.freqName = freqName;
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

    @Column(name = "EXEC_DEPT_NAME", length = 192)
    public String getExecDeptName()
    {
        return this.execDeptName;
    }

    public void setExecDeptName(String execDeptName)
    {
        this.execDeptName = execDeptName;
    }

    @Column(name = "MUTEXES_ORDER_TYPE_NAME", length = 192)
    public String getMutexesOrderTypeName()
    {
        return this.mutexesOrderTypeName;
    }

    public void setMutexesOrderTypeName(String mutexesOrderTypeName)
    {
        this.mutexesOrderTypeName = mutexesOrderTypeName;
    }

    @Column(name = "WARDS_NAME", length = 192)
    public String getWardsName()
    {
        return this.wardsName;
    }

    public void setWardsName(String wardsName)
    {
        this.wardsName = wardsName;
    }

    @Column(name = "ORDER_STATUS_NAME", length = 192)
    public String getOrderStatusName()
    {
        return this.orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName)
    {
        this.orderStatusName = orderStatusName;
    }

    @Column(name = "ORDER_TYPE_NAME", length = 192)
    public String getOrderTypeName()
    {
        return this.orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName)
    {
        this.orderTypeName = orderTypeName;
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

    @Column(name = "MUTEXES_ORDER_NO_TYPE", length = 12)
    public String getMutexesOrderNoType()
    {
        return this.mutexesOrderNoType;
    }

    public void setMutexesOrderNoType(String mutexesOrderNoType)
    {
        this.mutexesOrderNoType = mutexesOrderNoType;
    }

    @Column(name = "SKIN_TEST_FLAG", length = 1)
    public String getSkinTestFlag()
    {
        return skinTestFlag;
    }

    public void setSkinTestFlag(String skinTestFlag)
    {
        this.skinTestFlag = skinTestFlag;
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

    @Column(name = "ADAPTIVE_FLAG", length = 1)
    public String getAdaptiveFlag()
    {
        return adaptiveFlag;
    }

    public void setAdaptiveFlag(String adaptiveFlag)
    {
        this.adaptiveFlag = adaptiveFlag;
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
    
	@Column(name = "ORDER_SEQNUM", precision = 22, scale = 0)
	public Integer getOrderSeqnum() {
		return orderSeqnum;
	}

	public void setOrderSeqnum(Integer orderSeqnum) {
		this.orderSeqnum = orderSeqnum;
	}
	@Column(name = "ORDER_SET_CODE",  length = 12)
	public String getOrderSetCode() {
		return orderSetCode;
	}

	public void setOrderSetCode(String orderSetCode) {
		this.orderSetCode = orderSetCode;
	}

	@Column(name = "ORDER_DESCRIBE", length = 512)
	public String getOrderDescribe() {
		return orderDescribe;
	}

	public void setOrderDescribe(String orderDescribe) {
		this.orderDescribe = orderDescribe;
	}
}
