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
@Table(name = "EXAMINATION_ORDER")
public class ExaminationOrder implements Serializable
{

    private BigDecimal orderSn;

    private BigDecimal visitSn;

    private BigDecimal patientSn;

    private String patientDomain;

    private String patientLid;

    private String orderLid;

    private String itemClass;

    private String itemCode;

    private String itemName;

    private String regionCode;

    private String regionName;

    private String examMethodCode;

    private String examMethodName;

    private String orderType;

    private String orderTypeName;

    private String orderDept;

    private String orderPerson;

    private String orderPersonName;

    private Date orderTime;

    private String confirmPerson;

    private String confirmPersonName;

    private Date confirmTime;

    private String cancelPerson;

    private String cancelPersonName;

    private Date cancelTime;

    private String executiveDept;

    private String executionFrequency;

    private String wardsId;

    private String temporaryFlag;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String bedNo;

    private String requestReason;

    private String examCondition;

    private String examNotice;

    private String requestNo;

    private Date requestDate;

    private String diagnosis;

    private String sampleType;

    private String sampleRequirement;

    private String orderStatus;

    private String itemClassName;

    private String orderDeptName;

    private String execDeptName;

    private String execFreqName;

    private String wardsName;

    private String sampleTypeName;

    private String orderStatusName;

    private String urgentFlag;

    private String stopPersonId;

    private String stopPersonName;

    private Date stopTime;

    private String chargeStatusCode;

    private String chargeStatusName;
    
    private BigDecimal mutexesOrderSn;

    private String mutexesOrderNoType;
    
    private String requestDetails;
    
    private String sampleNo;
    
    private Date orderStartTime;

    private Date orderEndTime;
    
    private String skinTestFlag;
    
    private String adaptiveFlag;    
    
    private String medViewFlag;

    private String createby;

    private String updateby;

    private String deleteby;
    
    private String orgCode;
    
    private String orgName;
    
    private String orderDescribe;
    
    private String orderExecId;
    
    /*
	 * $Author: yu_yzh
	 * $Date: 2015/7/29 : 11:20
	 * 检查费用 ADD BEGIN
	 * */
    private BigDecimal charge;
    // ADD END
    
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
    
    @Column(name = "SKIN_TEST_FLAG", length = 1)
    public String getSkinTestFlag() {
		return skinTestFlag;
	}

	public void setSkinTestFlag(String skinTestFlag) {
		this.skinTestFlag = skinTestFlag;
	}

	@Column(name = "ADAPTIVE_FLAG", length = 1)
	public String getAdaptiveFlag() {
		return adaptiveFlag;
	}

	public void setAdaptiveFlag(String adaptiveFlag) {
		this.adaptiveFlag = adaptiveFlag;
	}

	@Column(name = "MED_VIEW_FLAG", length = 1)
	public String getMedViewFlag() {
		return medViewFlag;
	}

	public void setMedViewFlag(String medViewFlag) {
		this.medViewFlag = medViewFlag;
	}
	
	@Temporal(TemporalType.DATE)
    @Column(name = "ORDER_START_TIME", length = 7)
	public Date getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(Date orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	@Temporal(TemporalType.DATE)
    @Column(name = "ORDER_END_TIME", length = 7)
	public Date getOrderEndTime() {
		return orderEndTime;
	}

	public void setOrderEndTime(Date orderEndTime) {
		this.orderEndTime = orderEndTime;
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

    @Column(name = "ITEM_CLASS", length = 32)
    public String getItemClass()
    {
        return this.itemClass;
    }

    public void setItemClass(String itemClass)
    {
        this.itemClass = itemClass;
    }

    @Column(name = "ITEM_CODE", length = 12)
    public String getItemCode()
    {
        return this.itemCode;
    }

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    @Column(name = "ITEM_NAME", nullable = false, length = 192)
    public String getItemName()
    {
        return this.itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    @Column(name = "REGION_CODE", length = 12)
    public String getRegionCode()
    {
        return this.regionCode;
    }

    public void setRegionCode(String regionCode)
    {
        this.regionCode = regionCode;
    }

    @Column(name = "REGION_NAME", length = 192)
    public String getRegionName()
    {
        return this.regionName;
    }

    public void setRegionName(String regionName)
    {
        this.regionName = regionName;
    }

    @Column(name = "EXAM_METHOD_CODE", length = 12)
    public String getExamMethodCode()
    {
        return this.examMethodCode;
    }

    public void setExamMethodCode(String examMethodCode)
    {
        this.examMethodCode = examMethodCode;
    }

    @Column(name = "EXAM_METHOD_NAME", length = 192)
    public String getExamMethodName()
    {
        return this.examMethodName;
    }

    public void setExamMethodName(String examMethodName)
    {
        this.examMethodName = examMethodName;
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

    @Column(name = "ORDER_TYPE_NAME", length = 192)
    public String getOrderTypeName()
    {
        return this.orderTypeName;
    }

    public void setOrderTypeName(String orderTypeName)
    {
        this.orderTypeName = orderTypeName;
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
    @Column(name = "ORDER_TIME", length = 7)
    public Date getOrderTime()
    {
        return this.orderTime;
    }

    public void setOrderTime(Date orderTime)
    {
        this.orderTime = orderTime;
    }

    @Column(name = "CONFIRM_PERSON", length = 12)
    public String getConfirmPerson()
    {
        return this.confirmPerson;
    }

    public void setConfirmPerson(String confirmPerson)
    {
        this.confirmPerson = confirmPerson;
    }

    @Column(name = "CONFIRM_PERSON_NAME", length = 192)
    public String getConfirmPersonName()
    {
        return this.confirmPersonName;
    }

    public void setConfirmPersonName(String confirmPersonName)
    {
        this.confirmPersonName = confirmPersonName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CONFIRM_TIME", length = 7)
    public Date getConfirmTime()
    {
        return this.confirmTime;
    }

    public void setConfirmTime(Date confirmTime)
    {
        this.confirmTime = confirmTime;
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

    @Column(name = "EXECUTIVE_DEPT", length = 12)
    public String getExecutiveDept()
    {
        return this.executiveDept;
    }

    public void setExecutiveDept(String executiveDept)
    {
        this.executiveDept = executiveDept;
    }

    @Column(name = "EXECUTION_FREQUENCY", length = 12)
    public String getExecutionFrequency()
    {
        return this.executionFrequency;
    }

    public void setExecutionFrequency(String executionFrequency)
    {
        this.executionFrequency = executionFrequency;
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

    @Column(name = "TEMPORARY_FLAG", length = 1)
    public String getTemporaryFlag()
    {
        return this.temporaryFlag;
    }

    public void setTemporaryFlag(String temporaryFlag)
    {
        this.temporaryFlag = temporaryFlag;
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

    @Column(name = "BED_NO", length = 80)
    public String getBedNo()
    {
        return this.bedNo;
    }

    public void setBedNo(String bedNo)
    {
        this.bedNo = bedNo;
    }

    @Column(name = "REQUEST_REASON", length = 256)
    public String getRequestReason()
    {
        return this.requestReason;
    }

    public void setRequestReason(String requestReason)
    {
        this.requestReason = requestReason;
    }

    @Column(name = "EXAM_CONDITION", length = 100)
    public String getExamCondition()
    {
        return this.examCondition;
    }

    public void setExamCondition(String examCondition)
    {
        this.examCondition = examCondition;
    }

    @Column(name = "EXAM_NOTICE", length = 100)
    public String getExamNotice()
    {
        return this.examNotice;
    }

    public void setExamNotice(String examNotice)
    {
        this.examNotice = examNotice;
    }

    @Column(name = "REQUEST_NO", length = 20)
    public String getRequestNo()
    {
        return this.requestNo;
    }

    public void setRequestNo(String requestNo)
    {
        this.requestNo = requestNo;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "REQUEST_DATE", length = 7)
    public Date getRequestDate()
    {
        return this.requestDate;
    }

    public void setRequestDate(Date requestDate)
    {
        this.requestDate = requestDate;
    }

    @Column(name = "DIAGNOSIS", length = 100)
    public String getDiagnosis()
    {
        return this.diagnosis;
    }

    public void setDiagnosis(String diagnosis)
    {
        this.diagnosis = diagnosis;
    }

    @Column(name = "SAMPLE_TYPE", length = 12)
    public String getSampleType()
    {
        return this.sampleType;
    }

    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }

    @Column(name = "SAMPLE_REQUIREMENT", length = 100)
    public String getSampleRequirement()
    {
        return this.sampleRequirement;
    }

    public void setSampleRequirement(String sampleRequirement)
    {
        this.sampleRequirement = sampleRequirement;
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

    @Column(name = "ITEM_CLASS_NAME", length = 192)
    public String getItemClassName()
    {
        return this.itemClassName;
    }

    public void setItemClassName(String itemClassName)
    {
        this.itemClassName = itemClassName;
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

    @Column(name = "EXEC_FREQ_NAME", length = 192)
    public String getExecFreqName()
    {
        return this.execFreqName;
    }

    public void setExecFreqName(String execFreqName)
    {
        this.execFreqName = execFreqName;
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

    @Column(name = "SAMPLE_TYPE_NAME", length = 192)
    public String getSampleTypeName()
    {
        return this.sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
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

    @Column(name = "URGENT_FLAG", length = 6)
    public String getUrgentFlag()
    {
        return this.urgentFlag;
    }

    public void setUrgentFlag(String urgentFlag)
    {
        this.urgentFlag = urgentFlag;
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
    
    @Column(name = "MUTEXES_ORDER_SN", precision = 22, scale = 0)
    public BigDecimal getMutexesOrderSn()
    {
        return this.mutexesOrderSn;
    }

    public void setMutexesOrderSn(BigDecimal mutexesOrderSn)
    {
        this.mutexesOrderSn = mutexesOrderSn;
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

    @Column(name = "REQUEST_DETAILS")
    public String getRequestDetails()
    {
        return requestDetails;
    }

    public void setRequestDetails(String requestDetails)
    {
        this.requestDetails = requestDetails;
    }
    @Column(name = "SAMPLE_NO", length = 20)
    public String getSampleNo()
    {
        return sampleNo;
    }
    public void setSampleNo(String sampleNo)
    {
        this.sampleNo = sampleNo;
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
    
    @Column(name = "UPDATEBY", length = 24)
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

    @Column(name = "ORG_CODE", length = 50)
    public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

    @Column(name = "ORG_NAME", length = 50)
    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }
    
    @Column(name = "ORDER_DESCRIBE", length = 256)
	public String getOrderDescribe() {
		return orderDescribe;
	}

	public void setOrderDescribe(String orderDescribe) {
		this.orderDescribe = orderDescribe;
	}

	@Column(name = "ORDER_EXEC_ID", length = 12)
	public String getOrderExecId() {
		return orderExecId;
	}

	public void setOrderExecId(String orderExecId) {
		this.orderExecId = orderExecId;
	}
	
	@Column(name = "CHARGE", precision=10, scale=4)
	public BigDecimal getCharge() {
		return charge;
	}

	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}

}
