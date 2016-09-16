package com.founder.cdr.entity;

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
@Table(name = "REGISTRATION_FEE")
public class RegistrationFee implements Serializable
{

    private BigDecimal registrationSn;

    private BigDecimal visitSn;

    private BigDecimal patientSn;

    private String patientDomain;

    private String patientLid;

    private BigDecimal ledgerCount;

    private BigDecimal amount;

    private String paymentMethod;

    private String chargeStatus;

    private BigDecimal quantity;

    private String insuranceFlag;

    private BigDecimal receivedAmount;

    private String payee;

    private Date paidTime;

    private String confirmPerson;

    private Date confirmTime;

    private BigDecimal confirmWindow;

    private BigDecimal inputWindow;

    private BigDecimal receiptNo;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String paymentMethodName;

    private String payeeName;

    private String confirmPersonName;

    private String chargeStatusName;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getRegistrationSn()
    {
        return this.registrationSn;
    }

    public void setRegistrationSn(BigDecimal registrationSn)
    {
        this.registrationSn = registrationSn;
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

    @Column(name = "LEDGER_COUNT", nullable = false, precision = 22, scale = 0)
    public BigDecimal getLedgerCount()
    {
        return this.ledgerCount;
    }

    public void setLedgerCount(BigDecimal ledgerCount)
    {
        this.ledgerCount = ledgerCount;
    }

    @Column(name = "AMOUNT", precision = 8)
    public BigDecimal getAmount()
    {
        return this.amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    @Column(name = "PAYMENT_METHOD", length = 8)
    public String getPaymentMethod()
    {
        return this.paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    @Column(name = "CHARGE_STATUS", length = 4)
    public String getChargeStatus()
    {
        return this.chargeStatus;
    }

    public void setChargeStatus(String chargeStatus)
    {
        this.chargeStatus = chargeStatus;
    }

    @Column(name = "QUANTITY", precision = 22, scale = 0)
    public BigDecimal getQuantity()
    {
        return this.quantity;
    }

    public void setQuantity(BigDecimal quantity)
    {
        this.quantity = quantity;
    }

    @Column(name = "INSURANCE_FLAG", length = 1)
    public String getInsuranceFlag()
    {
        return this.insuranceFlag;
    }

    public void setInsuranceFlag(String insuranceFlag)
    {
        this.insuranceFlag = insuranceFlag;
    }

    @Column(name = "RECEIVED_AMOUNT", precision = 8, scale = 4)
    public BigDecimal getReceivedAmount()
    {
        return this.receivedAmount;
    }

    public void setReceivedAmount(BigDecimal receivedAmount)
    {
        this.receivedAmount = receivedAmount;
    }

    @Column(name = "PAYEE", length = 20)
    public String getPayee()
    {
        return this.payee;
    }

    public void setPayee(String payee)
    {
        this.payee = payee;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "PAID_TIME", length = 7)
    public Date getPaidTime()
    {
        return this.paidTime;
    }

    public void setPaidTime(Date paidTime)
    {
        this.paidTime = paidTime;
    }

    @Column(name = "CONFIRM_PERSON", length = 6)
    public String getConfirmPerson()
    {
        return this.confirmPerson;
    }

    public void setConfirmPerson(String confirmPerson)
    {
        this.confirmPerson = confirmPerson;
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

    @Column(name = "CONFIRM_WINDOW", precision = 22, scale = 0)
    public BigDecimal getConfirmWindow()
    {
        return this.confirmWindow;
    }

    public void setConfirmWindow(BigDecimal confirmWindow)
    {
        this.confirmWindow = confirmWindow;
    }

    @Column(name = "INPUT_WINDOW", precision = 22, scale = 0)
    public BigDecimal getInputWindow()
    {
        return this.inputWindow;
    }

    public void setInputWindow(BigDecimal inputWindow)
    {
        this.inputWindow = inputWindow;
    }

    @Column(name = "RECEIPT_NO", precision = 22, scale = 0)
    public BigDecimal getReceiptNo()
    {
        return this.receiptNo;
    }

    public void setReceiptNo(BigDecimal receiptNo)
    {
        this.receiptNo = receiptNo;
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

    @Column(name = "PAYMENT_METHOD_NAME", length = 40)
    public String getPaymentMethodName()
    {
        return this.paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName)
    {
        this.paymentMethodName = paymentMethodName;
    }

    @Column(name = "PAYEE_NAME", length = 40)
    public String getPayeeName()
    {
        return this.payeeName;
    }

    public void setPayeeName(String payeeName)
    {
        this.payeeName = payeeName;
    }

    @Column(name = "CONFIRM_PERSON_NAME", length = 40)
    public String getConfirmPersonName()
    {
        return this.confirmPersonName;
    }

    public void setConfirmPersonName(String confirmPersonName)
    {
        this.confirmPersonName = confirmPersonName;
    }

    @Column(name = "CHARGE_STATUS_NAME", length = 56)
    public String getChargeStatusName()
    {
        return this.chargeStatusName;
    }

    public void setChargeStatusName(String chargeStatusName)
    {
        this.chargeStatusName = chargeStatusName;
    }

}
