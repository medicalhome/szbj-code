package com.yly.cdr.hl7.dto;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.NotEmpty;



public class TreatmentItem extends BaseDto
{
    /**
     * 医嘱号
     */
    @NotEmpty(message="{TreatmentItem.orderLid}")
    private String orderLid;
    /**
     * 已收费/未收费处方区别标志
     */
    private String paidFlag;
    /**
     * 诊疗处方号
     */
    private String prescriptionNo;
    /**
     * 医嘱类别编码
     */
//    @NotEmpty(message="{TreatmentItem.orderType}")
    private String orderType;
    /**
     * 医嘱类别名称
     */
    private String orderTypeName;
    /**
     * 医嘱备注
     */
    private String memo;
    /**
     * 医嘱执行频率编码
     */
    private String executionFrequency;
    /**
     * 医嘱执行频率名称
     */
    private String execFreqName;
    /**
     * 病人原病区名称
     */
    private String formerWardName;
    /**
     * 病人原病区编码
     */
    private String formerWard;
    /**
     * 病人原科室编码
     */
    private String formerDept;
    /**
     * 病人原科室名称
     */
    private String formerDeptName;
    /**
     * 开嘱时间
     */
    private String orderTime;
    /**
     * 开嘱医生编码
     */
    private String orderPerson;
    /**
     * 开嘱医生姓名
     */
    private String orderPersonName;
    /**
     * 医生确认时间
     */
    private String doctorConfirmTime;
    /**
     * 医生确认人
     */
    private String doctorConfirmPerson;
    /**
     * 医生确认人姓名
     */
    private String doctorConfirmPersonName;
    /**
     * 执行科室
     */
    private String executiveDept;
    /**
     * 执行科室名称
     */
    private String executiveDeptName;
    /**
     * 父医嘱号
     */
    private String parentOrderLid;
    /**
     * 互斥医嘱类型编码
     */
    private String mutexesOrderType;
    /**
     * 互斥医嘱类型名称
     */
    private String mutexesOrderTypeName;
    /**
     * 费用标记编码
     */
    private String expenseSign;
    /**
     * 费用标记名称
     */
    private String expenseSignName;
    /**
     * 嘱托内容
     */
    private String comments;
    /**
     * 是否适应
     */
    private String adaptiveFlag;
    /**
     * 是否皮试
     */
    private String skinTestFlag;
	/**
     * 是否加急
     */
    private String urgentFlag;
    /**
     * 是否药观
     */
    private String medViewFlag;
    /**
     * 长期、临时标识
     */
    private String temporaryFlag;
    
    /**
     * 数量
     */
    private String quantity;
    /**
     * 单位
     */
    private String unit;
    /**
     * 开始时间
     */
    private String orderStartTime;
    /**
     * 结束时间
     */
    private String orderEndTime;
    /**
     * 包装序号
     */
    private String serialId;
    /**
     * 诊疗项目编码
     */
    private String itemCode;
    /**
     * 诊疗项目名称
     */
    private String itemName;
    /**
     * 药物医保类别编码
     */
    private String insuranceType;
    /**
     * 药物医保类别名称
     */
    private String insuranceTypeName;
    /**
     * 医嘱序号
     */
//    @NotEmpty(message="{TreatmentItem.orderSeqnum}")
    private String orderSeqnum;

    public String getOrderLid()
    {
        return orderLid;
    }

    public String getPaidFlag()
    {
        return paidFlag;
    }

    public String getPrescriptionNo()
    {
        return prescriptionNo;
    }

    public String getOrderType()
    {
        return orderType;
    }

    public String getOrderTypeName()
    {
        return orderTypeName;
    }

    public String getMemo()
    {
        return memo;
    }

    public String getExecutionFrequency()
    {
        return executionFrequency;
    }

    public String getExecFreqName()
    {
        return execFreqName;
    }

    public String getFormerWardName()
    {
        return formerWardName;
    }

    public String getFormerWard()
    {
        return formerWard;
    }

    public String getFormerDept()
    {
        return formerDept;
    }

    public String getFormerDeptName()
    {
        return formerDeptName;
    }

    public String getOrderTime()
    {
        return orderTime;
    }

    public String getOrderPerson()
    {
        return orderPerson;
    }

    public String getOrderPersonName()
    {
        return orderPersonName;
    }

    public String getDoctorConfirmTime()
    {
        return doctorConfirmTime;
    }

    public String getDoctorConfirmPerson()
    {
        return doctorConfirmPerson;
    }

    public String getDoctorConfirmPersonName()
    {
        return doctorConfirmPersonName;
    }

    public String getExecutiveDept()
    {
        return executiveDept;
    }

    public String getExecutiveDeptName()
    {
        return executiveDeptName;
    }

    public String getParentOrderLid()
    {
        return parentOrderLid;
    }

    public String getMutexesOrderType()
    {
        return mutexesOrderType;
    }

    public String getMutexesOrderTypeName()
    {
        return mutexesOrderTypeName;
    }

    public String getExpenseSign()
    {
        return expenseSign;
    }

    public String getExpenseSignName()
    {
        return expenseSignName;
    }

    public String getComments()
    {
        return comments;
    }

    public String getAdaptiveFlag()
    {
        return adaptiveFlag;
    }
    
    public String getSkinTestFlag() {
		return skinTestFlag;
	}
    
    public String getUrgentFlag()
    {
        return urgentFlag;
    }

    public String getMedViewFlag()
    {
        return medViewFlag;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public String getUnit()
    {
        return unit;
    }

    public String getOrderStartTime()
    {
        return orderStartTime;
    }

    public String getOrderEndTime()
    {
        return orderEndTime;
    }

    public String getSerialId()
    {
        return serialId;
    }

    public String getItemCode()
    {
        return itemCode;
    }

    public String getItemName()
    {
        return itemName;
    }

    public String getInsuranceType()
    {
        return insuranceType;
    }

    public String getInsuranceTypeName()
    {
        return insuranceTypeName;
    }


    public String getOrderSeqnum() {
		return orderSeqnum;
	}



	@Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("TreatmentItem [orderLid=");
        builder.append(orderLid);
        builder.append(", orderSeqnum=");
        builder.append(orderSeqnum);
        builder.append("]");
        return builder.toString();
    }

	public String getTemporaryFlag() {
		return temporaryFlag;
	}


    
    
}
