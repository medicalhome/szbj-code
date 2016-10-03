package com.yly.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;


public class Drugs extends BaseDto
{
    /**
     * 医嘱号
     */
    @NotEmpty(message = "{Drugs.orderId}")
    private String orderId;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 单位
     */
    private String unit;
    /**
     * 药量
     */
    private String quantity;
    /**
     * 药品编码
     */
    @NotEmpty(message = "{Drugs.drugCode}")
    private String drugCode;
    /**
     * 药品名称
     */
//    @NotEmpty(message = "{Drugs.drugName}")
    private String drugName;

    // Author: yu_yzh
    // Date: 2016/3/2 14:57
    // [BUG]0064315
    // ADD BEGIN
    /**
     * 发药单号
     * */
    private String recordNo;
    // [BUG]0064315
    // ADD END
    public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getOrderId()
    {
        return orderId;
    }

    public String getBatchNo()
    {
        return batchNo;
    }

    public String getUnit()
    {
        return unit;
    }

    public String getQuantity()
    {
        return quantity;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Drugs [orderId=");
        builder.append(orderId);
        builder.append(", batchNo=");
        builder.append(batchNo);
        builder.append("]");
        return builder.toString();
    }

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}
    
}
