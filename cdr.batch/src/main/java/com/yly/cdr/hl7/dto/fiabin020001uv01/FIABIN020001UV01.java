package com.yly.cdr.hl7.dto.fiabin020001uv01;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.BaseDto;
import com.yly.cdr.hl7.dto.BillingReceipt;
import com.yly.cdr.util.DateUtils;

public class FIABIN020001UV01 extends BaseDto
{
    /**
     * 新增标志
     */
    @NotEmpty(message = "{FIABIN020001UV01.newUpFlag}")
    private String newUpFlag;

    /**
     * 域ID
     */
    @NotEmpty(message = "{FIABIN020001UV01.patientDomain}")
    private String patientDomain;

    /**
     * 患者本地Lid
     */
    @NotEmpty(message = "{FIABIN020001UV01.patientLid}")
    private String patientLid;

    /**
     * 就诊号
     */
    private String outpatientNo;

    /**
     * 就诊次数
     */
    private String visitTimes;

    /**
     * 收款日期
     */
    @NotEmpty(message = "{FIABIN020001UV01.receiptTime}")
    private String receiptTime;

    /**
     * 收款人编码
     */
    @NotEmpty(message = "{FIABIN020001UV01.receiver}")
    private String receiver;

    /**
     * 收款人姓名
     */
    @NotEmpty(message = "{FIABIN020001UV01.receiverName}")
    private String receiverName;

    /**
     * 帐单信息
     */
    @NotEmpty(message = "{FIABIN020001UV01.billingReceipt}")
    private List<BillingReceipt> billingReceipt;

    /**
     * 医保内金额
     */
    private String insurancePaid;

    /**
     * 医保内金额单位
     */
    private String insurancePaidUnit;

    /**
     * 医保外金额
     */
    private String ownExpense;

    /**
     * 医保金额单位
     */
    private String ownExpenseUnit;

    /**
     * 交易类型
     */
    private String transactionType;

    public String getTransactionType()
    {
        return transactionType;
    }

    public String getNewUpFlag()
    {
        return newUpFlag;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public String getOutpatientNo()
    {
        return outpatientNo;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    public String getReceiptTime()
    {
        return receiptTime;
    }

    public String getReceiver()
    {
        return receiver;
    }

    public String getReceiverName()
    {
        return receiverName;
    }

    public List<BillingReceipt> getBillingReceipt()
    {
        return billingReceipt;
    }

    public String getInsurancePaid()
    {
        return insurancePaid;
    }

    public String getInsurancePaidUnit()
    {
        return insurancePaidUnit;
    }

    public String getOwnExpense()
    {
        return ownExpense;
    }

    public String getOwnExpenseUnit()
    {
        return ownExpenseUnit;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MessageId=");
        builder.append(getMessage().getId()+",");
        builder.append("Datetime=");
        builder.append(DateUtils.dateToString(getMessage().getDatetime(),null) + ",");
        builder.append("FIABIN020001UV01 [newUpFlag=");
        builder.append(newUpFlag);
        builder.append(", patientDomain=");
        builder.append(patientDomain);
        builder.append(", patientLid=");
        builder.append(patientLid);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append(", billingReceipt=");
        builder.append(billingReceipt);
        builder.append("]");
        return builder.toString();
    }
    
}
