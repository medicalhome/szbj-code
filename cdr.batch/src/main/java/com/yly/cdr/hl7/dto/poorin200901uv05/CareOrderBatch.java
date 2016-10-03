package com.yly.cdr.hl7.dto.poorin200901uv05;

import org.hibernate.validator.constraints.NotEmpty;

import com.yly.cdr.hl7.dto.OrderDto;

public class CareOrderBatch extends OrderDto
{
    /**
     * 医嘱号
     */
    @NotEmpty(message = "{CareOrderBatch.orderLid}")
    private String orderLid;

    /**
     * 医嘱类型
     */
    @NotEmpty(message = "{CareOrderBatch.orderType}")
    private String orderType;

    /**
     * 医嘱类型名称
     */
    private String orderTypeName;

    /**
     * 医嘱名称
     */
    private String orderName;

    /**
     * 医嘱编码（项目编码）
     */
    private String orderCode;

	/**
     * 医嘱执行频率
     */
    private String freq;

    /**
     * 医嘱执行频率名稱
     */
    private String freqName;

    /**
     * 紧急程度
     */
    private String urgency;

    /**
     * 数量
     */
    private String quantity;

    /**
     * 单位
     */
    private String unit;

    /**
     * 包装号
     */
    private String packNo;

    /**
     * 耗材类编号
     */
    private String suppliesNo;

    /**
     * 耗材类名称
     */
    private String suppliesName;

    /**
     * 医嘱编码
     */
    private String orderNo;

    /**
     * 确认时间
     */
    private String verifyTime;

    /**
     * 确认医生编号
     */
    private String verifyPerson;

    /**
     * 确认医生姓名
     */
    private String verifyPersonName;

    /**
     * 下医嘱机构(科室)
     */
//    @NotEmpty(message = "{CareOrderBatch.orderDept}")
    private String orderDept;

    /**
     * 下医嘱机构(科室)名稱
     */
//    @NotEmpty(message = "{CareOrderBatch.orderDeptName}")
    private String orderDeptName;

    /**
     * 开嘱人id
     */
    @NotEmpty(message = "{CareOrderBatch.orderPerson}")
    private String orderPerson;

    /**
     * 开嘱人姓名
     */
//    @NotEmpty(message = "{CareOrderBatch.orderPersonName}")
    private String orderPersonName;

    /**
     * 开嘱时间
     */
    @NotEmpty(message = "{CareOrderBatch.inputTime}")
    private String inputTime;

    /**
     * 医嘱执行科室
     */
//    @NotEmpty(message = "{CareOrderBatch.execDept}")
    private String execDept;

    /**
     * 医嘱执行科室名稱
     */
//    @NotEmpty(message = "{CareOrderBatch.execDeptName}")
    private String execDeptName;

    /**
     * 描述
     */
    private String description;

    /**
     * 父医嘱标识号码
     */
    private String parentOrderId;

    /**
     * 互斥医嘱类型
     */
    private String mutexesOrderType;

    /**
     * 互斥医嘱类型名稱
     */
    private String mutexesOrderTypeName;

    /**
     * 互斥医嘱标识号码
     */
    private String mutexesOrderId;

    /**
     * 嘱托
     */
    private String comments;

    /**
     * 医嘱开始时间
     */
    private String orderStartTime;

    /**
     * 医嘱停止时间
     */
    private String orderEndTime;
    
    /**
     * 是否皮试
     */
    private String skinTestFlag;
    
    /**
     * 是否适应
     */
    private String adaptiveFlag;
    /**
     * 是否药观
     */
    private String medViewFlag;
    /**
     * 医嘱序号
     */
    //@NotEmpty(message="{CareOrderBatch.orderSeqnum}")
    private String orderSeqnum;

    /**
     * 长期临时标识
     * */
    private String usage;
    
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

    public String getOrderName()
    {
        return orderName;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public String getFreq()
    {
        return freq;
    }

    public String getFreqName()
    {
        return freqName;
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

    public String getInputTime()
    {
        return inputTime;
    }

    public String getExecDept()
    {
        return execDept;
    }

    public String getExecDeptName()
    {
        return execDeptName;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public String getUnit()
    {
        return unit;
    }

    public String getPackNo()
    {
        return packNo;
    }

    public String getSuppliesNo()
    {
        return suppliesNo;
    }

    public String getSuppliesName()
    {
        return suppliesName;
    }

    public String getVerifyTime()
    {
        return verifyTime;
    }

    public String getVerifyPerson()
    {
        return verifyPerson;
    }

    public String getVerifyPersonName()
    {
        return verifyPersonName;
    }

    public String getUrgency()
    {
        return urgency;
    }

    public String getDescription()
    {
        return description;
    }

    public String getParentOrderId()
    {
        return parentOrderId;
    }

    public String getMutexesOrderType()
    {
        return mutexesOrderType;
    }

    public String getMutexesOrderTypeName()
    {
        return mutexesOrderTypeName;
    }

    public String getMutexesOrderId()
    {
        return mutexesOrderId;
    }

    public String getComments()
    {
        return comments;
    }

    public String getOrderStartTime()
    {
        return orderStartTime;
    }

    public String getOrderEndTime()
    {
        return orderEndTime;
    }
    
    public String getSkinTestFlag()
    {
        return skinTestFlag;
    }

    public String getAdaptiveFlag()
    {
        return adaptiveFlag;
    }

    public String getMedViewFlag()
    {
        return medViewFlag;
    }
    
    public String getOrderSeqnum() {
		return orderSeqnum;
	}
    
    public String getOrderCode() {
		return orderCode;
	}
    
	@Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CareOrderBatch [orderLid=");
        builder.append(orderLid);
        builder.append(", orderType=");
        builder.append(orderType);
        builder.append(", orderTypeName=");
        builder.append(orderTypeName);
        builder.append(", orderNo=");
        builder.append(orderNo);
        builder.append(", parentOrderId=");
        builder.append(parentOrderId);
        builder.append(", orderSeqnum=");
        builder.append(orderSeqnum);
        builder.append("]");
        return builder.toString();
    }

	public String getUsage() {
		return usage;
	}
}
