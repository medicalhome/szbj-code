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
@Table(name = "PROCEDURE_ORDER")
public class ProcedureOrder implements Serializable
{
    private BigDecimal orderSn;
    private BigDecimal visitSn;
    private BigDecimal parentOrderSn;
    private BigDecimal patientSn;
    private BigDecimal examReportSn;
    private String patientDomain;
    private String patientLid;
    private String orderLid;
    private String orderType;
    private String orderDept;
    private String orderName;
    private String executionFrequency;
    private Date planExecTime;
    private String quantity;
    private String description;
    private String orderStatus;
    private String orderPerson;
    private String orderPersonName;
    private Date orderTime;
    private String confirmPerson;
    private String confirmPersonName;
    private Date confirmTime;
    private String stopPerson;
    private String stopPersonName;
    private Date stopTime;
    private String cancelPerson;
    private String cancelPersonName;
    private Date cancelTime;
    private Date createTime;
    private Date updateTime;
    private BigDecimal updateCount;
    private String deleteFlag;
    private Date deleteTime;
    private String wardsId;
    private String bedNo;
    private String requestNo;
    private String operationCode;
    private String operationName;
    private String procedureLevel;
    private Date proposalExecTime;
    private String precautions;
    private String execDept;
    private Date requestDate;
    private String requestReason;
    private String temporaryFlag;
    private String orderTypeName;
    private String orderDeptName;
    private String orderCode;
    private String execFreqName;
    private String wardsName;
    private String procedureLevelName;
    private String execDeptName;
    private String orderStatusName;
    private String urgentFlag;
    private String chargeStatusCode;
    private String chargeStatusName;
    private BigDecimal mutexesOrderSn;
    private String mutexesOrderNoType;
    private String medViewFlag;
    private String skinTestFlag;
    private String createby;
    private String updateby;
    private String deleteby;
    private String anesthesiaCode;
    private String anesthesiaName;
    private String orgCode;
    private String orgName;
    private String operationPropertyCode;
    private String operationPropertyName;
    private String roomNo;
    private String planOperationUseTime;
    private String directorCode;
    private String directorName;
    private String assistantfirCode;
    private String assistantfirName;
    private String assistantsecCode;
    private String assistantsecName;
    private String assistantthiCode;
    private String assistantthiName;
    private String scrubNurseCode;
    private String scrubNurseName;
    private String circulatingNurseCode;
    private String circulatingNurseName;
    private String operationTable;
    private String tableTimes;
    private String planFlag;
    private String noPlanReason;
    private String operatorCode;
    private String operatorName;
    private Date operationConfigDate;
    private BigDecimal charge;

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
    @Column(name = "EXAM_REPORT_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getExamReportSn()
    {
        return this.examReportSn;
    }
    public void setExamReportSn(BigDecimal examReportSn)
    {
        this.examReportSn = examReportSn;
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
    @Column(name = "ORDER_TYPE", length = 80)
    public String getOrderType()
    {
        return this.orderType;
    }
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    @Column(name = "ORDER_DEPT", length = 20)
    public String getOrderDept()
    {
        return this.orderDept;
    }
    public void setOrderDept(String orderDept)
    {
        this.orderDept = orderDept;
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
    @Column(name = "EXECUTION_FREQUENCY", length = 20)
    public String getExecutionFrequency()
    {
        return this.executionFrequency;
    }
    public void setExecutionFrequency(String executionFrequency)
    {
        this.executionFrequency = executionFrequency;
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
    @Column(name = "QUANTITY", length = 20)
    public String getQuantity()
    {
        return this.quantity;
    }
    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }
    @Column(name = "DESCRIPTION", length = 512)
    public String getDescription()
    {
        return this.description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    @Column(name = "ORDER_STATUS", length = 20)
    public String getOrderStatus()
    {
        return this.orderStatus;
    }
    public void setOrderStatus(String orderStatus)
    {
        this.orderStatus = orderStatus;
    }
    @Column(name = "ORDER_PERSON", length = 20)
    public String getOrderPerson()
    {
        return this.orderPerson;
    }
    public void setOrderPerson(String orderPerson)
    {
        this.orderPerson = orderPerson;
    }
    @Column(name = "ORDER_PERSON_NAME", length = 56)
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
    @Column(name = "CONFIRM_PERSON", length = 20)
    public String getConfirmPerson()
    {
        return this.confirmPerson;
    }
    public void setConfirmPerson(String confirmPerson)
    {
        this.confirmPerson = confirmPerson;
    }
    @Column(name = "CONFIRM_PERSON_NAME", length = 56)
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
    @Column(name = "STOP_PERSON", length = 20)
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
    @Column(name = "REQUEST_NO", length = 20)
    public String getRequestNo()
    {
        return this.requestNo;
    }
    public void setRequestNo(String requestNo)
    {
        this.requestNo = requestNo;
    }
    @Column(name = "OPERATION_CODE", length = 20)
    public String getOperationCode()
    {
        return this.operationCode;
    }
    public void setOperationCode(String operationCode)
    {
        this.operationCode = operationCode;
    }
    @Column(name = "OPERATION_NAME", length = 192)
    public String getOperationName()
    {
        return this.operationName;
    }
    public void setOperationName(String operationName)
    {
        this.operationName = operationName;
    }
    @Column(name = "PROCEDURE_LEVEL", length = 20)
    public String getProcedureLevel()
    {
        return this.procedureLevel;
    }
    public void setProcedureLevel(String procedureLevel)
    {
        this.procedureLevel = procedureLevel;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "PROPOSAL_EXEC_TIME", length = 7)
    public Date getProposalExecTime()
    {
        return this.proposalExecTime;
    }
    public void setProposalExecTime(Date proposalExecTime)
    {
        this.proposalExecTime = proposalExecTime;
    }
    @Column(name = "PRECAUTIONS", length = 200)
    public String getPrecautions()
    {
        return this.precautions;
    }
    public void setPrecautions(String precautions)
    {
        this.precautions = precautions;
    }
    @Column(name = "EXEC_DEPT", length = 20)
    public String getExecDept()
    {
        return this.execDept;
    }
    public void setExecDept(String execDept)
    {
        this.execDept = execDept;
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
    @Column(name = "REQUEST_REASON", length = 20)
    public String getRequestReason()
    {
        return this.requestReason;
    }
    public void setRequestReason(String requestReason)
    {
        this.requestReason = requestReason;
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
    @Column(name = "ORDER_TYPE_NAME", length = 192)
    public String getOrderTypeName()
    {
        return this.orderTypeName;
    }
    public void setOrderTypeName(String orderTypeName)
    {
        this.orderTypeName = orderTypeName;
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
    @Column(name = "ORDER_CODE", length = 120)
    public String getOrderCode()
    {
        return this.orderCode;
    }
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
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
    @Column(name = "PROCEDURE_LEVEL_NAME", length = 192)
    public String getProcedureLevelName()
    {
        return this.procedureLevelName;
    }
    public void setProcedureLevelName(String procedureLevelName)
    {
        this.procedureLevelName = procedureLevelName;
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
    @Column(name = "ORDER_STATUS_NAME", length = 192)
    public String getOrderStatusName()
    {
        return this.orderStatusName;
    }
    public void setOrderStatusName(String orderStatusName)
    {
        this.orderStatusName = orderStatusName;
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
    @Column(name = "MED_VIEW_FLAG", length = 1)
    public String getMedViewFlag()
    {
        return medViewFlag;
    }
    public void setMedViewFlag(String medViewFlag)
    {
        this.medViewFlag = medViewFlag;
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

    @Column(name = "ANESTHESIA_CODE", length = 30)
    public String getAnesthesiaCode()
    {
        return anesthesiaCode;
    }
    
    public void setAnesthesiaCode(String anesthesiaCode)
    {
        this.anesthesiaCode = anesthesiaCode;
    }
    
    @Column(name = "ANESTHESIA_NAME", length = 192)
    public String getAnesthesiaName()
    {
        return anesthesiaName;
    }
    
    public void setAnesthesiaName(String anesthesiaName)
    {
        this.anesthesiaName = anesthesiaName;
    }
    
    @Column(name = "ORG_CODE", length = 50)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	@Column(name = "ORG_NAME", length = 50)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	@Column(name = "OPERATION_PROPERTY_CODE", length = 20)
	public String getOperationPropertyCode() {
		return operationPropertyCode;
	}
	
	public void setOperationPropertyCode(String operationPropertyCode) {
		this.operationPropertyCode = operationPropertyCode;
	}
	
	@Column(name = "OPERATION_PROPERTY_NAME", length = 192)
	public String getOperationPropertyName() {
		return operationPropertyName;
	}
	
	public void setOperationPropertyName(String operationPropertyName) {
		this.operationPropertyName = operationPropertyName;
	}
	
	@Column(name = "ROOM_NO", length = 20)
	public String getRoomNo() {
		return roomNo;
	}
	
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	
	@Column(name = "PLAN_OPERATION_USE_TIME", precision = 10, scale = 4)
	public String getPlanOperationUseTime() {
		return planOperationUseTime;
	}
	
	public void setPlanOperationUseTime(String planOperationUseTime) {
		this.planOperationUseTime = planOperationUseTime;
	}
	
	@Column(name = "DIRECTOR_CODE", length = 12)
	public String getDirectorCode() {
		return directorCode;
	}
	
	public void setDirectorCode(String directorCode) {
		this.directorCode = directorCode;
	}
	
	@Column(name = "DIRECTOR_NAME", length = 20)
	public String getDirectorName() {
		return directorName;
	}
	
	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}
	
	@Column(name = "ASSISTANTFIR_CODE", length = 12)
	public String getAssistantfirCode() {
		return assistantfirCode;
	}
	
	public void setAssistantfirCode(String assistant1Code) {
		this.assistantfirCode = assistant1Code;
	}
	
	@Column(name = "ASSISTANTFIR_NAME", length = 20)
	public String getAssistantfirName() {
		return assistantfirName;
	}
	
	public void setAssistantfirName(String assistant1Name) {
		this.assistantfirName = assistant1Name;
	}
	
	@Column(name = "ASSISTANTSEC_CODE", length = 12)
	public String getAssistantsecCode() {
		return assistantsecCode;
	}
	
	public void setAssistantsecCode(String assistant2Code) {
		this.assistantsecCode = assistant2Code;
	}
	
	@Column(name = "ASSISTANTSEC_NAME", length = 20)
	public String getAssistantsecName() {
		return assistantsecName;
	}
	
	public void setAssistantsecName(String assistant2Name) {
		this.assistantsecName = assistant2Name;
	}
	
	@Column(name = "ASSISTANTTHI_CODE", length = 12)
	public String getAssistantthiCode() {
		return assistantthiCode;
	}
	
	public void setAssistantthiCode(String assistant3Code) {
		this.assistantthiCode = assistant3Code;
	}
	
	@Column(name = "ASSISTANT3_NAME", length = 20)
	public String getAssistantthiName() {
		return assistantthiName;
	}
	
	public void setAssistantthiName(String assistant3Name) {
		this.assistantthiName = assistant3Name;
	}
	
	@Column(name = "SCRUB_NURSE_CODE", length = 12)
	public String getScrubNurseCode() {
		return scrubNurseCode;
	}
	
	public void setScrubNurseCode(String scrubNurseCode) {
		this.scrubNurseCode = scrubNurseCode;
	}
	
	@Column(name = "SCRUB_NURSE_NAME", length = 20)
	public String getScrubNurseName() {
		return scrubNurseName;
	}
	
	public void setScrubNurseName(String scrubNurseName) {
		this.scrubNurseName = scrubNurseName;
	}
	
	@Column(name = "CIRCULATING_NURSE_CODE", length = 12)
	public String getCirculatingNurseCode() {
		return circulatingNurseCode;
	}
	
	public void setCirculatingNurseCode(String circulatingNurseCode) {
		this.circulatingNurseCode = circulatingNurseCode;
	}
	
	@Column(name = "CIRCULATING_NURSE_NAME", length = 20)
	public String getCirculatingNurseName() {
		return circulatingNurseName;
	}
	
	public void setCirculatingNurseName(String circulatingNurseName) {
		this.circulatingNurseName = circulatingNurseName;
	}
	
	@Column(name = "OPERATION_TABLE", length = 50)
	public String getOperationTable() {
		return operationTable;
	}
	
	public void setOperationTable(String operationTable) {
		this.operationTable = operationTable;
	}
	
	@Column(name = "TABLE_TIMES", length = 5)
	public String getTableTimes() {
		return tableTimes;
	}
	
	public void setTableTimes(String tableTimes) {
		this.tableTimes = tableTimes;
	}
	
	@Column(name = "PLAN_FLAG", length = 1)
	public String getPlanFlag() {
		return planFlag;
	}
	
	public void setPlanFlag(String planFlag) {
		this.planFlag = planFlag;
	}
	
	@Column(name = "OPERATOR_CODE", length = 12)
	public String getOperatorCode() {
		return operatorCode;
	}
	
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	
	@Column(name = "OPERATOR_NAME", length = 20)
	public String getOperatorName() {
		return operatorName;
	}
	
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	 @Temporal(TemporalType.DATE)
	 @Column(name = "OPERATION_CONFIG_DATE", length = 7)
	public Date getOperationConfigDate() {
		return operationConfigDate;
	}
	 
	public void setOperationConfigDate(Date operationConfigDate) {
		this.operationConfigDate = operationConfigDate;
	}
	
	@Column(name = "NO_PLAN_REASON", length = 256)
	public String getNoPlanReason() {
		return noPlanReason;
	}
	public void setNoPlanReason(String noPlanReason) {
		this.noPlanReason = noPlanReason;
	}
	@Column(name = "CHARGE", precision=10, scale=4)
	public BigDecimal getCharge() {
		return charge;
	}

	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}
    
}
