package com.founder.cdr.hl7.dto;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class BillingReceipt extends BaseDto
{
    /**
     * 收据号
     */
    private String receiptNo;
    /**
     * 红冲账单流水号
     */
    private String writeBackNo;
    /**
     * 账单码编码
     */
    @NotEmpty(message = "{BillingReceipt.billCode}")
    private String billCode;
    /**
     * 账单码名称
     */
    private String billName;
    /**
     * 账单日期
     */
    private String receiptDate;
    /**
     * 账单金额
     */
    private String receiptTotal;
    /**
     * 金额单位
     */
    private String receiptTotalUnit;
    /**
     * 费用明细
     */
    @NotEmpty(message = "{BillingReceipt.billingItem}")
    private List<BillingItem> billingItem;

    public String getReceiptNo()
    {
        return receiptNo;
    }

    public String getWriteBackNo()
    {
        return writeBackNo;
    }

    public String getBillCode()
    {
        return billCode;
    }

    public String getBillName()
    {
        return billName;
    }

    public String getReceiptDate()
    {
        return receiptDate;
    }

    public String getReceiptTotal()
    {
        return receiptTotal;
    }

    public String getReceiptTotalUnit()
    {
        return receiptTotalUnit;
    }

    public List<BillingItem> getBillingItem()
    {
        return billingItem;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("BillingReceipt [receiptNo=");
        builder.append(receiptNo);
        builder.append(", billCode=");
        builder.append(billCode);
        builder.append(", billingItem=");
        builder.append(billingItem);
        builder.append("]");
        return builder.toString();
    }
    
}
