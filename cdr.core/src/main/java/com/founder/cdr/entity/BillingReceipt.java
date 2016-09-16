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
@Table(name="BILLING_RECEIPT")
public class BillingReceipt implements Serializable
{

	private BigDecimal receiptSn;
	private BigDecimal patientSn;
	private String patientDomain;
	private String patientLid;
	private String chargeClass;
	private String chargeClassName;
	private String payment;
	private BigDecimal receiptTotal;
	private Date receiptDate;
	private String ownExpenseFlag;
	private BigDecimal ownExpenseRate;
	private String amountReceivable;
	private String receiptNo;
	private String receiver;
	private Date receiptTime;
	private String returnFlag;
	private String returnPerson;
	private String refundFlag;
	private String refundPerson;
	private String writeBackNo;
	private Date createTime;
	private Date updateTime;
	private BigDecimal updateCount;
	private String deleteFlag;
	private Date deleteTime;
	private String receiverName;
	private String paymentName;
	private String returnPersonName;
	private String refundPersonName;
	private BigDecimal ledgerSn;
	private String billCode;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_BILLING_RECEIPT") })
	public BigDecimal getReceiptSn() 
	{
		return this.receiptSn;
	}
	
	public void setReceiptSn(BigDecimal receiptSn) 
	{
		this.receiptSn = receiptSn;
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

	    
    @Column(name="CHARGE_CLASS", length=6)
	public String getChargeClass() 
	{
		return this.chargeClass;
	}
	
	public void setChargeClass(String chargeClass) 
	{
		this.chargeClass = chargeClass;
	}

	    
    @Column(name="CHARGE_CLASS_NAME", length=64)
	public String getChargeClassName() 
	{
		return this.chargeClassName;
	}
	
	public void setChargeClassName(String chargeClassName) 
	{
		this.chargeClassName = chargeClassName;
	}

	    
    @Column(name="PAYMENT", length=6)
	public String getPayment() 
	{
		return this.payment;
	}
	
	public void setPayment(String payment) 
	{
		this.payment = payment;
	}

	    
    @Column(name="RECEIPT_TOTAL", precision=14, scale=4)
	public BigDecimal getReceiptTotal() 
	{
		return this.receiptTotal;
	}
	
	public void setReceiptTotal(BigDecimal receiptTotal) 
	{
		this.receiptTotal = receiptTotal;
	}

	    @Temporal(TemporalType.DATE)
    @Column(name="RECEIPT_DATE", length=7)
	public Date getReceiptDate() 
	{
		return this.receiptDate;
	}
	
	public void setReceiptDate(Date receiptDate) 
	{
		this.receiptDate = receiptDate;
	}

	    
    @Column(name="OWN_EXPENSE_FLAG", length=1)
	public String getOwnExpenseFlag() 
	{
		return this.ownExpenseFlag;
	}
	
	public void setOwnExpenseFlag(String ownExpenseFlag) 
	{
		this.ownExpenseFlag = ownExpenseFlag;
	}

	    
    @Column(name="OWN_EXPENSE_RATE", precision=3)
	public BigDecimal getOwnExpenseRate() 
	{
		return this.ownExpenseRate;
	}
	
	public void setOwnExpenseRate(BigDecimal ownExpenseRate) 
	{
		this.ownExpenseRate = ownExpenseRate;
	}

	    
    @Column(name="AMOUNT_RECEIVABLE", length=20)
	public String getAmountReceivable() 
	{
		return this.amountReceivable;
	}
	
	public void setAmountReceivable(String amountReceivable) 
	{
		this.amountReceivable = amountReceivable;
	}

	    
    @Column(name="RECEIPT_NO", length=20)
	public String getReceiptNo() 
	{
		return this.receiptNo;
	}
	
	public void setReceiptNo(String receiptNo) 
	{
		this.receiptNo = receiptNo;
	}

	    
    @Column(name="RECEIVER", length=8)
	public String getReceiver() 
	{
		return this.receiver;
	}
	
	public void setReceiver(String receiver) 
	{
		this.receiver = receiver;
	}

	    @Temporal(TemporalType.DATE)
    @Column(name="RECEIPT_TIME", length=7)
	public Date getReceiptTime() 
	{
		return this.receiptTime;
	}
	
	public void setReceiptTime(Date receiptTime) 
	{
		this.receiptTime = receiptTime;
	}

	    
    @Column(name="RETURN_FLAG", length=1)
	public String getReturnFlag() 
	{
		return this.returnFlag;
	}
	
	public void setReturnFlag(String returnFlag) 
	{
		this.returnFlag = returnFlag;
	}

	    
    @Column(name="RETURN_PERSON", length=8)
	public String getReturnPerson() 
	{
		return this.returnPerson;
	}
	
	public void setReturnPerson(String returnPerson) 
	{
		this.returnPerson = returnPerson;
	}

	    
    @Column(name="REFUND_FLAG", length=1)
	public String getRefundFlag() 
	{
		return this.refundFlag;
	}
	
	public void setRefundFlag(String refundFlag) 
	{
		this.refundFlag = refundFlag;
	}

	    
    @Column(name="REFUND_PERSON", length=8)
	public String getRefundPerson() 
	{
		return this.refundPerson;
	}
	
	public void setRefundPerson(String refundPerson) 
	{
		this.refundPerson = refundPerson;
	}

	    
    @Column(name="WRITE_BACK_NO", length=72)
	public String getWriteBackNo() 
	{
		return this.writeBackNo;
	}
	
	public void setWriteBackNo(String writeBackNo) 
	{
		this.writeBackNo = writeBackNo;
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

	    
    @Column(name="RECEIVER_NAME", length=192)
	public String getReceiverName() 
	{
		return this.receiverName;
	}
	
	public void setReceiverName(String receiverName) 
	{
		this.receiverName = receiverName;
	}

	    
    @Column(name="PAYMENT_NAME", length=64)
	public String getPaymentName() 
	{
		return this.paymentName;
	}
	
	public void setPaymentName(String paymentName) 
	{
		this.paymentName = paymentName;
	}

	    
    @Column(name="RETURN_PERSON_NAME", length=40)
	public String getReturnPersonName() 
	{
		return this.returnPersonName;
	}
	
	public void setReturnPersonName(String returnPersonName) 
	{
		this.returnPersonName = returnPersonName;
	}

	    
    @Column(name="REFUND_PERSON_NAME", length=40)
	public String getRefundPersonName() 
	{
		return this.refundPersonName;
	}
	
	public void setRefundPersonName(String refundPersonName) 
	{
		this.refundPersonName = refundPersonName;
	}

	    
    @Column(name="LEDGER_SN", precision=22, scale=0)
	public BigDecimal getLedgerSn() 
	{
		return this.ledgerSn;
	}
	
	public void setLedgerSn(BigDecimal ledgerSn) 
	{
		this.ledgerSn = ledgerSn;
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

}
