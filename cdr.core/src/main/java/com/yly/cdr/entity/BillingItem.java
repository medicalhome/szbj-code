package com.yly.cdr.entity;


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
@Table(name="BILLING_ITEM")
public class BillingItem implements Serializable
{

	private BigDecimal billingItemSn;
	private BigDecimal visitSn;
	private BigDecimal receiptSn;
	private BigDecimal registrationSn;
	private BigDecimal patientSn;
	private BigDecimal requestSn;
	private BigDecimal orderSn;
	private String patientDomain;
	private String patientLid;
	private String chargeNo;
	private String chargeCode;
	private String itemName;
	private String itemNo;
	private String ledgerCount;
	private String chargeStatus;
	private BigDecimal chargePrice;
	private BigDecimal amount;
	private BigDecimal backAmount;
	private String chargeGroup;
	private BigDecimal insurancePaid;
	private BigDecimal ownExpense;
	private BigDecimal herbsAmount;
	private Date occurrenceTime;
	private Date inputTime;
	private String inputPerson;
	private BigDecimal inputWindow;
	private Date confirmTime;
	private String confirmPerson;
	private BigDecimal confirmWindow;
	private String confirmFlag;
	private String infantFlag;
	private String separateFlag;
	private String costlyFlag;
	private String poisionFlag;
	private String rescueFlag;
	private String procedureFlag;
	private String emergencyFlag;
	private String additionalFlag;
	private String prepaidFlag;
	private String transFlag;
	private BigDecimal transCount;
	private Date transTime;
	private Date createTime;
	private Date updateTime;
	private BigDecimal updateCount;
	private String deleteFlag;
	private Date deleteTime;
	private String inputPersonName;
	private String confirmPersonName;
	private String chargeStatusName;
	private String chargeGroupName;
	private String chargeType;
	private String billCode;
	private BigDecimal zyDetailSn;
	private String billingItemLid;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_BILLING_ITEM") })
	public BigDecimal getBillingItemSn() 
	{
		return this.billingItemSn;
	}
	
	public void setBillingItemSn(BigDecimal billingItemSn) 
	{
		this.billingItemSn = billingItemSn;
	}

	    
    @Column(name="VISIT_SN", nullable=false, precision=22, scale=0)
	public BigDecimal getVisitSn() 
	{
		return this.visitSn;
	}
	
	public void setVisitSn(BigDecimal visitSn) 
	{
		this.visitSn = visitSn;
	}

	    
    @Column(name="RECEIPT_SN", precision=22, scale=0)
	public BigDecimal getReceiptSn() 
	{
		return this.receiptSn;
	}
	
	public void setReceiptSn(BigDecimal receiptSn) 
	{
		this.receiptSn = receiptSn;
	}

	    
    @Column(name="REGISTRATION_SN", precision=22, scale=0)
	public BigDecimal getRegistrationSn() 
	{
		return this.registrationSn;
	}
	
	public void setRegistrationSn(BigDecimal registrationSn) 
	{
		this.registrationSn = registrationSn;
	}

	    
    @Column(name="PATIENT_SN", nullable=false, precision=22, scale=0)
	public BigDecimal getPatientSn() 
	{
		return this.patientSn;
	}
	
	public void setPatientSn(BigDecimal patientSn) 
	{
		this.patientSn = patientSn;
	}

	    
    @Column(name="REQUEST_SN", precision=22, scale=0)
	public BigDecimal getRequestSn() 
	{
		return this.requestSn;
	}
	
	public void setRequestSn(BigDecimal requestSn) 
	{
		this.requestSn = requestSn;
	}

	    
    @Column(name="ORDER_SN", precision=22, scale=0)
	public BigDecimal getOrderSn() 
	{
		return this.orderSn;
	}
	
	public void setOrderSn(BigDecimal orderSn) 
	{
		this.orderSn = orderSn;
	}

	    
    @Column(name="PATIENT_DOMAIN", nullable=false, length=6)
	public String getPatientDomain() 
	{
		return this.patientDomain;
	}
	
	public void setPatientDomain(String patientDomain) 
	{
		this.patientDomain = patientDomain;
	}

	    
    @Column(name="PATIENT_LID", nullable=false, length=12)
	public String getPatientLid() 
	{
		return this.patientLid;
	}
	
	public void setPatientLid(String patientLid) 
	{
		this.patientLid = patientLid;
	}

	    
    @Column(name="CHARGE_NO", length=64)
	public String getChargeNo() 
	{
		return this.chargeNo;
	}
	
	public void setChargeNo(String chargeNo) 
	{
		this.chargeNo = chargeNo;
	}

	    
    @Column(name="CHARGE_CODE", length=6)
	public String getChargeCode() 
	{
		return this.chargeCode;
	}
	
	public void setChargeCode(String chargeCode) 
	{
		this.chargeCode = chargeCode;
	}

	    
    @Column(name="ITEM_NAME", length=240)
	public String getItemName() 
	{
		return this.itemName;
	}
	
	public void setItemName(String itemName) 
	{
		this.itemName = itemName;
	}

	    
    @Column(name="ITEM_NO", length=20)
	public String getItemNo() 
	{
		return this.itemNo;
	}
	
	public void setItemNo(String itemNo) 
	{
		this.itemNo = itemNo;
	}

	    
    @Column(name="LEDGER_COUNT", length=20)
	public String getLedgerCount() 
	{
		return this.ledgerCount;
	}
	
	public void setLedgerCount(String ledgerCount) 
	{
		this.ledgerCount = ledgerCount;
	}

	    
    @Column(name="CHARGE_STATUS", length=1)
	public String getChargeStatus() 
	{
		return this.chargeStatus;
	}
	
	public void setChargeStatus(String chargeStatus) 
	{
		this.chargeStatus = chargeStatus;
	}

	    
    @Column(name="CHARGE_PRICE", precision=10, scale=4)
	public BigDecimal getChargePrice() 
	{
		return this.chargePrice;
	}
	
	public void setChargePrice(BigDecimal chargePrice) 
	{
		this.chargePrice = chargePrice;
	}

	    
    @Column(name="AMOUNT", precision=22, scale=0)
	public BigDecimal getAmount() 
	{
		return this.amount;
	}
	
	public void setAmount(BigDecimal amount) 
	{
		this.amount = amount;
	}

	    
    @Column(name="BACK_AMOUNT", precision=22, scale=0)
	public BigDecimal getBackAmount() 
	{
		return this.backAmount;
	}
	
	public void setBackAmount(BigDecimal backAmount) 
	{
		this.backAmount = backAmount;
	}

	    
    @Column(name="CHARGE_GROUP", length=2)
	public String getChargeGroup() 
	{
		return this.chargeGroup;
	}
	
	public void setChargeGroup(String chargeGroup) 
	{
		this.chargeGroup = chargeGroup;
	}

	    
    @Column(name="INSURANCE_PAID", precision=22, scale=0)
	public BigDecimal getInsurancePaid() 
	{
		return this.insurancePaid;
	}
	
	public void setInsurancePaid(BigDecimal insurancePaid) 
	{
		this.insurancePaid = insurancePaid;
	}

	    
    @Column(name="OWN_EXPENSE", precision=22, scale=0)
	public BigDecimal getOwnExpense() 
	{
		return this.ownExpense;
	}
	
	public void setOwnExpense(BigDecimal ownExpense) 
	{
		this.ownExpense = ownExpense;
	}

	    
    @Column(name="HERBS_AMOUNT", precision=22, scale=0)
	public BigDecimal getHerbsAmount() 
	{
		return this.herbsAmount;
	}
	
	public void setHerbsAmount(BigDecimal herbsAmount) 
	{
		this.herbsAmount = herbsAmount;
	}

	    @Temporal(TemporalType.DATE)
    @Column(name="OCCURRENCE_TIME", length=7)
	public Date getOccurrenceTime() 
	{
		return this.occurrenceTime;
	}
	
	public void setOccurrenceTime(Date occurrenceTime) 
	{
		this.occurrenceTime = occurrenceTime;
	}

	    @Temporal(TemporalType.DATE)
    @Column(name="INPUT_TIME", length=7)
	public Date getInputTime() 
	{
		return this.inputTime;
	}
	
	public void setInputTime(Date inputTime) 
	{
		this.inputTime = inputTime;
	}

	    
    @Column(name="INPUT_PERSON", length=12)
	public String getInputPerson() 
	{
		return this.inputPerson;
	}
	
	public void setInputPerson(String inputPerson) 
	{
		this.inputPerson = inputPerson;
	}

	    
    @Column(name="INPUT_WINDOW", precision=22, scale=0)
	public BigDecimal getInputWindow() 
	{
		return this.inputWindow;
	}
	
	public void setInputWindow(BigDecimal inputWindow) 
	{
		this.inputWindow = inputWindow;
	}

	    @Temporal(TemporalType.DATE)
    @Column(name="CONFIRM_TIME", length=7)
	public Date getConfirmTime() 
	{
		return this.confirmTime;
	}
	
	public void setConfirmTime(Date confirmTime) 
	{
		this.confirmTime = confirmTime;
	}

	    
    @Column(name="CONFIRM_PERSON", length=6)
	public String getConfirmPerson() 
	{
		return this.confirmPerson;
	}
	
	public void setConfirmPerson(String confirmPerson) 
	{
		this.confirmPerson = confirmPerson;
	}

	    
    @Column(name="CONFIRM_WINDOW", precision=22, scale=0)
	public BigDecimal getConfirmWindow() 
	{
		return this.confirmWindow;
	}
	
	public void setConfirmWindow(BigDecimal confirmWindow) 
	{
		this.confirmWindow = confirmWindow;
	}

	    
    @Column(name="CONFIRM_FLAG", length=1)
	public String getConfirmFlag() 
	{
		return this.confirmFlag;
	}
	
	public void setConfirmFlag(String confirmFlag) 
	{
		this.confirmFlag = confirmFlag;
	}

	    
    @Column(name="INFANT_FLAG", length=1)
	public String getInfantFlag() 
	{
		return this.infantFlag;
	}
	
	public void setInfantFlag(String infantFlag) 
	{
		this.infantFlag = infantFlag;
	}

	    
    @Column(name="SEPARATE_FLAG", length=1)
	public String getSeparateFlag() 
	{
		return this.separateFlag;
	}
	
	public void setSeparateFlag(String separateFlag) 
	{
		this.separateFlag = separateFlag;
	}

	    
    @Column(name="COSTLY_FLAG", length=1)
	public String getCostlyFlag() 
	{
		return this.costlyFlag;
	}
	
	public void setCostlyFlag(String costlyFlag) 
	{
		this.costlyFlag = costlyFlag;
	}

	    
    @Column(name="POISION_FLAG", length=1)
	public String getPoisionFlag() 
	{
		return this.poisionFlag;
	}
	
	public void setPoisionFlag(String poisionFlag) 
	{
		this.poisionFlag = poisionFlag;
	}

	    
    @Column(name="RESCUE_FLAG", length=1)
	public String getRescueFlag() 
	{
		return this.rescueFlag;
	}
	
	public void setRescueFlag(String rescueFlag) 
	{
		this.rescueFlag = rescueFlag;
	}

	    
    @Column(name="PROCEDURE_FLAG", length=1)
	public String getProcedureFlag() 
	{
		return this.procedureFlag;
	}
	
	public void setProcedureFlag(String procedureFlag) 
	{
		this.procedureFlag = procedureFlag;
	}

	    
    @Column(name="EMERGENCY_FLAG", length=1)
	public String getEmergencyFlag() 
	{
		return this.emergencyFlag;
	}
	
	public void setEmergencyFlag(String emergencyFlag) 
	{
		this.emergencyFlag = emergencyFlag;
	}

	    
    @Column(name="ADDITIONAL_FLAG", length=1)
	public String getAdditionalFlag() 
	{
		return this.additionalFlag;
	}
	
	public void setAdditionalFlag(String additionalFlag) 
	{
		this.additionalFlag = additionalFlag;
	}

	    
    @Column(name="PREPAID_FLAG", length=1)
	public String getPrepaidFlag() 
	{
		return this.prepaidFlag;
	}
	
	public void setPrepaidFlag(String prepaidFlag) 
	{
		this.prepaidFlag = prepaidFlag;
	}

	    
    @Column(name="TRANS_FLAG", length=1)
	public String getTransFlag() 
	{
		return this.transFlag;
	}
	
	public void setTransFlag(String transFlag) 
	{
		this.transFlag = transFlag;
	}

	    
    @Column(name="TRANS_COUNT", precision=22, scale=0)
	public BigDecimal getTransCount() 
	{
		return this.transCount;
	}
	
	public void setTransCount(BigDecimal transCount) 
	{
		this.transCount = transCount;
	}

	    @Temporal(TemporalType.DATE)
    @Column(name="TRANS_TIME", length=7)
	public Date getTransTime() 
	{
		return this.transTime;
	}
	
	public void setTransTime(Date transTime) 
	{
		this.transTime = transTime;
	}

	    @Temporal(TemporalType.DATE)
    @Column(name="CREATE_TIME", nullable=false, length=7)
	public Date getCreateTime() 
	{
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) 
	{
		this.createTime = createTime;
	}

	    @Temporal(TemporalType.DATE)
    @Column(name="UPDATE_TIME", nullable=false, length=7)
	public Date getUpdateTime() 
	{
		return this.updateTime;
	}
	
	public void setUpdateTime(Date updateTime) 
	{
		this.updateTime = updateTime;
	}

	    
    @Column(name="UPDATE_COUNT", nullable=false, precision=22, scale=0)
	public BigDecimal getUpdateCount() 
	{
		return this.updateCount;
	}
	
	public void setUpdateCount(BigDecimal updateCount) 
	{
		this.updateCount = updateCount;
	}

	    
    @Column(name="DELETE_FLAG", nullable=false, length=1)
	public String getDeleteFlag() 
	{
		return this.deleteFlag;
	}
	
	public void setDeleteFlag(String deleteFlag) 
	{
		this.deleteFlag = deleteFlag;
	}

	    @Temporal(TemporalType.DATE)
    @Column(name="DELETE_TIME", length=7)
	public Date getDeleteTime() 
	{
		return this.deleteTime;
	}
	
	public void setDeleteTime(Date deleteTime) 
	{
		this.deleteTime = deleteTime;
	}

	    
    @Column(name="INPUT_PERSON_NAME", length=40)
	public String getInputPersonName() 
	{
		return this.inputPersonName;
	}
	
	public void setInputPersonName(String inputPersonName) 
	{
		this.inputPersonName = inputPersonName;
	}

	    
    @Column(name="CONFIRM_PERSON_NAME", length=40)
	public String getConfirmPersonName() 
	{
		return this.confirmPersonName;
	}
	
	public void setConfirmPersonName(String confirmPersonName) 
	{
		this.confirmPersonName = confirmPersonName;
	}

	    
    @Column(name="CHARGE_STATUS_NAME", length=40)
	public String getChargeStatusName() 
	{
		return this.chargeStatusName;
	}
	
	public void setChargeStatusName(String chargeStatusName) 
	{
		this.chargeStatusName = chargeStatusName;
	}

	    
    @Column(name="CHARGE_GROUP_NAME", length=40)
	public String getChargeGroupName() 
	{
		return this.chargeGroupName;
	}
	
	public void setChargeGroupName(String chargeGroupName) 
	{
		this.chargeGroupName = chargeGroupName;
	}

	    
    @Column(name="CHARGE_TYPE", length=6)
	public String getChargeType() 
	{
		return this.chargeType;
	}
	
	public void setChargeType(String chargeType) 
	{
		this.chargeType = chargeType;
	}

	    
    @Column(name="BILL_CODE", length=6)
	public String getBillCode() 
	{
		return this.billCode;
	}
	
	public void setBillCode(String billCode) 
	{
		this.billCode = billCode;
	}

	    
    @Column(name="ZY_DETAIL_SN", precision=22, scale=0)
	public BigDecimal getZyDetailSn() 
	{
		return this.zyDetailSn;
	}
	
	public void setZyDetailSn(BigDecimal zyDetailSn) 
	{
		this.zyDetailSn = zyDetailSn;
	}

	    
    @Column(name="BILLING_ITEM_LID", length=64)
	public String getBillingItemLid() 
	{
		return this.billingItemLid;
	}
	
	public void setBillingItemLid(String billingItemLid) 
	{
		this.billingItemLid = billingItemLid;
	}

}
