package com.founder.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;


public class BillingItem extends BaseDto
{
    /**
     * 细目号
     */
    private String itemNo;
    /**
     * 结算次数
     */
    private String ledgerCount;
    /**
     * 费用状态编码
     */
    @NotEmpty(message = "{BillingItem.chargeStatus}")
    private String chargeStatus;
    /**
     * 费用状态名称
     */
    private String chargeStatusName;
    /**
     * 项目收费时间
     */
    private String occurrenceTime;
    /**
     * 数量
     */
    private String amount;
    /**
     * 单位
     */
    private String unitPrice;
    /**
     * 单价
     */
    @NotEmpty(message = "{BillingItem.chargePrice}")
    private String chargePrice;
    /**
     * 单价单位
     */
    private String chargePriceUnit;
    /**
     * 处方号
     */
    private String prescription;
    /**
     * 处方类别编码
     */
    private String prescriptionType;
    /**
     * 处方类别名称
     */
    private String prescriptionTypeName;
    /**
     * 医嘱号
     */
    @NotEmpty(message = "{BillingItem.orderLid}")
    private String orderLid;
    /**
     * 医嘱类别编码
     */
    @NotEmpty(message = "{BillingItem.orderType}")
    private String orderType;
    /**
     * 医嘱类别名称
     */
    private String orderTypeName;
    /**
     * 包装序号
     */
    @NotEmpty(message = "{BillingItem.serialId}")
    private String serialId;
    /**
     * 费用编码
     */
    @NotEmpty(message = "{BillingItem.charge}")
    private String charge;
    /**
     * 费用名称
     */
    private String chargeName;
    /**
     * 医保类别编码
     */
    private String insuranceType;
    /**
     * 医保类别名称
     */
    private String insuranceTypeName;
    /**
     * 项目分组编码
     */
    private String chargeGroup;
    /**
     * 项目分组名称
     */
    private String chargeGroupName;
    /**
     * 费别编码
     */
    private String chargeClass;
    /**
     * 费别名称
     */
    private String chargeClassName;
    /**
     * 婴儿标记信息
     */
    private String infantFlag;
    /**
     * 费用执行科室编码
     */
    private String execDept;
    /**
     * 费用执行科室名称
     */
    private String execDeptName;
    /**
     * 费用执行病区编码
     */
    private String execWard;
    /**
     * 费用执行病区名称
     */
    private String execWardName;
    /**
     * 费用确认人编码
     */
    @NotEmpty(message = "{BillingItem.confirmPerson}")
    private String confirmPerson;
    /**
     * 费用确认人名称
     */
    @NotEmpty(message = "{BillingItem.confirmPersonName}")
    private String confirmPersonName;
    /**
     * 申请科室编码
     */
    @NotEmpty(message = "{BillingItem.orderDept}")
    private String orderDept;
    /**
     * 申请科室名称
     */
    @NotEmpty(message = "{BillingItem.orderDeptName}")
    private String orderDeptName;
    /**
     * 费用申请人编码
     */
    @NotEmpty(message = "{BillingItem.orderPerson}")
    private String orderPerson;
    /**
     * 费用申请人名称
     */
    @NotEmpty(message = "{BillingItem.orderPersonName}")
    private String orderPersonName;
    /**
     * 费用号
     */
    @NotEmpty(message = "{BillingItem.chargeNo}")
    private String chargeNo;
    /**
     * 就诊次数
     */
    private String visitTimes;

    public String getItemNo()
    {
        return itemNo;
    }

    public String getLedgerCount()
    {
        return ledgerCount;
    }

    public String getChargeStatus()
    {
        return chargeStatus;
    }

    public String getChargeStatusName()
    {
        return chargeStatusName;
    }

    public String getOccurrenceTime()
    {
        return occurrenceTime;
    }

    public String getAmount()
    {
        return amount;
    }

    public String getUnitPrice()
    {
        return unitPrice;
    }

    public String getChargePrice()
    {
        return chargePrice;
    }

    public String getChargePriceUnit()
    {
        return chargePriceUnit;
    }

    public String getPrescription()
    {
        return prescription;
    }

    public String getPrescriptionType()
    {
        return prescriptionType;
    }

    public String getPrescriptionTypeName()
    {
        return prescriptionTypeName;
    }

    public String getOrderLid()
    {
        return orderLid;
    }

    public String getOrderType()
    {
        return orderType;
    }

    public String getOrderTypeName()
    {
        return orderTypeName;
    }

    public String getSerialId()
    {
        return serialId;
    }

    public String getCharge()
    {
        return charge;
    }

    public String getChargeName()
    {
        return chargeName;
    }

    public String getInsuranceType()
    {
        return insuranceType;
    }

    public String getInsuranceTypeName()
    {
        return insuranceTypeName;
    }

    public String getChargeGroup()
    {
        return chargeGroup;
    }

    public String getChargeGroupName()
    {
        return chargeGroupName;
    }

    public String getChargeClass()
    {
        return chargeClass;
    }

    public String getChargeClassName()
    {
        return chargeClassName;
    }

    public String getInfantFlag()
    {
        return infantFlag;
    }

    public String getExecDept()
    {
        return execDept;
    }

    public String getExecDeptName()
    {
        return execDeptName;
    }

    public String getExecWard()
    {
        return execWard;
    }

    public String getExecWardName()
    {
        return execWardName;
    }

    public String getConfirmPerson()
    {
        return confirmPerson;
    }

    public String getConfirmPersonName()
    {
        return confirmPersonName;
    }

    public String getOrderDept()
    {
        return orderDept;
    }

    public String getOrderDeptName()
    {
        return orderDeptName;
    }

    public String getOrderPerson()
    {
        return orderPerson;
    }

    public String getOrderPersonName()
    {
        return orderPersonName;
    }

    public String getChargeNo()
    {
        return chargeNo;
    }

    public String getVisitTimes()
    {
        return visitTimes;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("BillingItem [itemNo=");
        builder.append(itemNo);
        builder.append(", orderLid=");
        builder.append(orderLid);
        builder.append(", orderType=");
        builder.append(orderType);
        builder.append(", prescription=");
        builder.append(prescription);
        builder.append(", serialId=");
        builder.append(serialId);
        builder.append(", chargeNo=");
        builder.append(chargeNo);
        builder.append(", visitTimes=");
        builder.append(visitTimes);
        builder.append("]");
        return builder.toString();
    }
}
