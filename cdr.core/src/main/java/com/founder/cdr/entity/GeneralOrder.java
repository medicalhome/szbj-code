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
@Table(name = "GENERAL_ORDER")
public class GeneralOrder implements Serializable
{

    private BigDecimal orderSn;

    private BigDecimal visitSn;

    private BigDecimal patientSn;

    private String patientDomain;

    private String patientLid;

    private String orderLid;

    private String orderName;

    private String orderPersonName;

    private Date inputTime;

    private String stopPersonName;

    private Date stopTime;

    private String cancelPersonName;

    private Date cancelTime;

    private Date planExecTime;

    private BigDecimal quantity;

    private String urgentFlag;

    private String description;

    private BigDecimal parentOrderSn;

    private BigDecimal mutexesOrderSn;

    private String usage;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String quantityUnit;

    private Date doctorConfirmTime;

    private String doctorConfirmPersonName;

    private Date nurseConfirmTime;

    private String nurseConfirmPersonName;

    private String orderTypeCode;

    private String orderTypeName;

    private String execFreqCode;

    private String execFreqName;

    private String orderDeptId;

    private String orderDeptName;

    private String execDeptId;

    private String execDeptName;

    private String wardId;

    private String wardName;

    private String mutexesOrderTypeCode;

    private String mutexesOrderTypeName;

    private String orderStatusCode;

    private String orderStatusName;

    private String orderCode;

    private String cancelPersonId;

    private String stopPersonId;

    private String nurseConfirmPersonId;

    private String doctorConfirmPersonId;

    private String orderPersonId;

    private String chargeStatusCode;

    private String chargeStatusName;

    private String mutexesOrderNoType;
    
    private Date orderEndTime;
    
    private String adaptiveFlag;
    
    private String medViewFlag;
    
    private String skinTestFlag;
    
    private String bedNo;
    
    private String comments;
    
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

    @Column(name = "VISIT_SN", precision = 22, scale = 0)
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

    @Column(name = "ORDER_NAME", length = 192)
    public String getOrderName()
    {
        return this.orderName;
    }

    public void setOrderName(String orderName)
    {
        this.orderName = orderName;
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

    @Temporal(TemporalType.DATE)
    @Column(name = "PLAN_EXEC_TIME", length = 7)
    public Date getPlanExecTime()
    {
        return this.planExecTime;
    }

    public void setPlanExecTime(Date planExecTime)
    {
        this.planExecTime = planExecTime;
    }

    @Column(name = "QUANTITY", precision = 14)
    public BigDecimal getQuantity()
    {
        return this.quantity;
    }

    public void setQuantity(BigDecimal quantity)
    {
        this.quantity = quantity;
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

    @Column(name = "DESCRIPTION", length = 1530)
    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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

    @Column(name = "MUTEXES_ORDER_SN", precision = 22, scale = 0)
    public BigDecimal getMutexesOrderSn()
    {
        return this.mutexesOrderSn;
    }

    public void setMutexesOrderSn(BigDecimal mutexesOrderSn)
    {
        this.mutexesOrderSn = mutexesOrderSn;
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

    @Column(name = "QUANTITY_UNIT", length = 32)
    public String getQuantityUnit()
    {
        return this.quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit)
    {
        this.quantityUnit = quantityUnit;
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
    @Column(name = "NURSE_CONFIRM_TIME", length = 7)
    public Date getNurseConfirmTime()
    {
        return this.nurseConfirmTime;
    }

    public void setNurseConfirmTime(Date nurseConfirmTime)
    {
        this.nurseConfirmTime = nurseConfirmTime;
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

    @Column(name = "ORDER_TYPE_CODE", length = 12)
    public String getOrderTypeCode()
    {
        return this.orderTypeCode;
    }

    public void setOrderTypeCode(String orderTypeCode)
    {
        this.orderTypeCode = orderTypeCode;
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

    @Column(name = "EXEC_FREQ_CODE", length = 12)
    public String getExecFreqCode()
    {
        return this.execFreqCode;
    }

    public void setExecFreqCode(String execFreqCode)
    {
        this.execFreqCode = execFreqCode;
    }

    @Column(name = "EXEC_FREQ_NAME", length = 192)
    public String getExecFreqName()
    {
        return this.execFreqName;
    }

    public void setExecFreqName(String execFreqName)
    {
        this.execFreqName = execFreqName;
    }

    @Column(name = "ORDER_DEPT_ID", length = 12)
    public String getOrderDeptId()
    {
        return this.orderDeptId;
    }

    public void setOrderDeptId(String orderDeptId)
    {
        this.orderDeptId = orderDeptId;
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

    @Column(name = "EXEC_DEPT_ID", length = 12)
    public String getExecDeptId()
    {
        return this.execDeptId;
    }

    public void setExecDeptId(String execDeptId)
    {
        this.execDeptId = execDeptId;
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

    @Column(name = "WARD_ID", length = 12)
    public String getWardId()
    {
        return this.wardId;
    }

    public void setWardId(String wardId)
    {
        this.wardId = wardId;
    }

    @Column(name = "WARD_NAME", length = 192)
    public String getWardName()
    {
        return this.wardName;
    }

    public void setWardName(String wardName)
    {
        this.wardName = wardName;
    }

    @Column(name = "MUTEXES_ORDER_TYPE_CODE", length = 12)
    public String getMutexesOrderTypeCode()
    {
        return this.mutexesOrderTypeCode;
    }

    public void setMutexesOrderTypeCode(String mutexesOrderTypeCode)
    {
        this.mutexesOrderTypeCode = mutexesOrderTypeCode;
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

    @Column(name = "ORDER_STATUS_CODE", length = 12)
    public String getOrderStatusCode()
    {
        return this.orderStatusCode;
    }

    public void setOrderStatusCode(String orderStatusCode)
    {
        this.orderStatusCode = orderStatusCode;
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

    @Column(name = "ORDER_CODE", length = 12)
    public String getOrderCode()
    {
        return this.orderCode;
    }

    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }

    @Column(name = "CANCEL_PERSON_ID", length = 12)
    public String getCancelPersonId()
    {
        return this.cancelPersonId;
    }

    public void setCancelPersonId(String cancelPersonId)
    {
        this.cancelPersonId = cancelPersonId;
    }

    @Column(name = "STOP_PERSON_ID", length = 12)
    public String getStopPersonId()
    {
        return this.stopPersonId;
    }

    public void setStopPersonId(String stopPersonId)
    {
        this.stopPersonId = stopPersonId;
    }

    @Column(name = "NURSE_CONFIRM_PERSON_ID", length = 12)
    public String getNurseConfirmPersonId()
    {
        return this.nurseConfirmPersonId;
    }

    public void setNurseConfirmPersonId(String nurseConfirmPersonId)
    {
        this.nurseConfirmPersonId = nurseConfirmPersonId;
    }

    @Column(name = "DOCTOR_CONFIRM_PERSON_ID", length = 12)
    public String getDoctorConfirmPersonId()
    {
        return this.doctorConfirmPersonId;
    }

    public void setDoctorConfirmPersonId(String doctorConfirmPersonId)
    {
        this.doctorConfirmPersonId = doctorConfirmPersonId;
    }

    @Column(name = "ORDER_PERSON_ID", length = 12)
    public String getOrderPersonId()
    {
        return this.orderPersonId;
    }

    public void setOrderPersonId(String orderPersonId)
    {
        this.orderPersonId = orderPersonId;
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
    
    @Column(name = "ADAPTIVE_FLAG", length = 1)
    public String getAdaptiveFlag()
    {
        return this.adaptiveFlag;
    }

    public void setAdaptiveFlag(String adaptiveFlag)
    {
        this.adaptiveFlag = adaptiveFlag;
    }
    
    @Column(name = "MEDVIEW_FLAG", length = 1)
    public String getMedViewFlag()
    {
        return this.medViewFlag;
    }

    public void setMedViewFlag(String medViewFlag)
    {
        this.medViewFlag = medViewFlag;
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

    @Column(name = "BED_NO", length = 80)
    public String getBedNo()
    {
        return bedNo;
    }

    public void setBedNo(String bedNo)
    {
        this.bedNo = bedNo;
    }

    @Column(name = "COMMENTS", length = 384)
    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
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
