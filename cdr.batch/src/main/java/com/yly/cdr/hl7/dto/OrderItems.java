package com.yly.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;


public class OrderItems extends BaseDto
{
    /**
     * 医嘱号
     */
    @NotEmpty(message = "{OrderItems.orderLid}")
    private String orderLid;
    /**
     * 执行医嘱的类别代码
     */
//    @NotEmpty(message = "{OrderItems.orderTypeId}")
    private String orderTypeId;
    /**
     * 执行医嘱的类别名称
     */
    private String orderTypeName;
    /**
     * 医嘱描述
     */
    private String orderRemark;
    /**
     * 开嘱时间
     */
    @NotEmpty(message = "{OrderItems.orderTime}")
    private String orderTime;
    /**
     * 开嘱人标识
     */
    @NotEmpty(message = "{OrderItems.orderPersonId}")
    private String orderPersonId;
    /**
     * 开嘱人姓名
     */
//    @NotEmpty(message = "{OrderItems.orderPersonName}")
    private String orderPersonName;
    // $Author :tong_meng
    // $Date : 2012/9/17 11:30$
    // [BUG]0009718 ADD BEGIN
    /**
     * 确认人标识(医生)
     */
 //   @NotEmpty(message = "{OrderItems.doctorConfirmPerson}")
    private String doctorConfirmPerson;
    /**
     * 确认人姓名（医生）
     */
 //   @NotEmpty(message = "{OrderItems.doctorConfirmPersonName}")
    private String doctorConfirmPersonName;
    /**
     * 确认时间（医生）
     */
//    @NotEmpty(message = "{OrderItems.doctorConfirmTime}")
    private String doctorConfirmTime;
    // [BUG]0009718 ADD END
    /**
     * 开嘱科室标识
     */
//    @NotEmpty(message = "{OrderItems.orderDeptNo}")
    private String orderDeptNo;
    /**
     * 开嘱科室姓名
     */
//    @NotEmpty(message = "{OrderItems.orderDeptName}")
    private String orderDeptName;
    /**
     * 执行医嘱科室标识
     */
//    @NotEmpty(message = "{OrderItems.excuOrderDeptId}")
    private String excuOrderDeptId;
    /**
     * 执行医嘱科室名称
     */
//    @NotEmpty(message = "{OrderItems.excuOrderDeptName}")
    private String excuOrderDeptName;
    /**
     * 费用标记标识
     */
    private String costId;
    /**
     * 费用标记名称
     */
    private String costName;
    /**
     * 医嘱编码 
     */
//    @NotEmpty(message = "{OrderItems.orderCode}")
    private String orderCode;
    /**
     * 医嘱名称
     */
    private String orderName;
    // $Author :tong_meng
    // $Date : 2012/9/17 11:30$
    // [BUG]0009718 ADD BEGIN
    /**
     * 病区编号
     */
  //  @NotEmpty(message = "{OrderItems.wardsId}")
  //  private String wardsId;
    /**
     * 病区名称 
     */
 //   @NotEmpty(message = "{OrderItems.wardsName}")
 //   private String wardsName;
    // [BUG]0009718 ADD END
    /**
     * 医护医嘱类型编码
     */
    private String mutexesOrderTypeCode;
    /**
     * 互斥医嘱类型名称
     */
    private String mutexesOrderTypeName;
    /**
     * 父医嘱号
     */
    private String parentOrderLid;
    
    /**
     * 是否加急
     */
    private String urgentFlag;
    
    // $Author :jin_peng
    // $Date : 2013/08/08 13:52$
    // [BUG]0035952 ADD BEGIN
    /**
     * 医嘱开始时间(下医嘱时填写的预计开始时间)
     */
    private String orderStartTime;
    
    /**
     * 医嘱停止时间(下医嘱时填写的预计停止时间)
     */
    private String orderEndTime;
    /**
     * 嘱托内容
     */
    private String comments;
    
    /**
     * 执行频率
     * */
    private String execFreqCode;
    
    /**
     * 数量
     */
    private String quantity;

    /**
     * 单位
     */
    private String unit;
    
    public String getOrderStartTime()
    {
        return orderStartTime;
    }

    public String getOrderEndTime()
    {
        return orderEndTime;
    }
    
    // [BUG]0035952 ADD END
    
    // $Author :jin_peng
    // $Date : 2013/08/09 11:02$
    // [BUG]0035952 ADD BEGIN
    /**
     * 是否适应
     */
    private String adaptiveFlag;
    
    /**
     * 是否药观
     */
    private String medViewFlag;
    
    /**
     * 是否皮试
     */
    private String skinTestFlag;
    
    /**
     * 医嘱序号
     */
//    @NotEmpty(message="{OrderItems.orderSeqnum}")
    private String orderSeqnum;
    
    /**
     * 长期/临时
     * */
    private String usage;
    
    public String getAdaptiveFlag()
    {
        return adaptiveFlag;
    }

    public String getMedViewFlag()
    {
        return medViewFlag;
    }

    public String getSkinTestFlag()
    {
        return skinTestFlag;
    }
    
    // [BUG]0035952 ADD END
    
//    public String getWardsId()
//    {
//        return wardsId;
//    }

    public String getOrderLid()
    {
        return orderLid;
    }

    public String getOrderTypeId()
    {
        return orderTypeId;
    }

    public String getOrderTypeName()
    {
        return orderTypeName;
    }

    public String getOrderRemark()
    {
        return orderRemark;
    }

    public String getOrderTime()
    {
        return orderTime;
    }

    public String getOrderPersonId()
    {
        return orderPersonId;
    }

    public String getOrderPersonName()
    {
        return orderPersonName;
    }

    public String getOrderDeptNo()
    {
        return orderDeptNo;
    }

    public String getDoctorConfirmPerson()
    {
        return doctorConfirmPerson;
    }

    public String getDoctorConfirmPersonName()
    {
        return doctorConfirmPersonName;
    }

    public String getDoctorConfirmTime()
    {
        return doctorConfirmTime;
    }

    public String getOrderDeptName()
    {
        return orderDeptName;
    }

    public String getExcuOrderDeptId()
    {
        return excuOrderDeptId;
    }

    public String getExcuOrderDeptName()
    {
        return excuOrderDeptName;
    }

    public String getCostId()
    {
        return costId;
    }

    public String getCostName()
    {
        return costName;
    }

    public String getOrderCode()
    {
        return orderCode;
    }

    public String getOrderName()
    {
        return orderName;
    }

//    public String getWardsName()
//    {
//        return wardsName;
//    }

    public String getMutexesOrderTypeCode()
    {
        return mutexesOrderTypeCode;
    }

    public String getMutexesOrderTypeName()
    {
        return mutexesOrderTypeName;
    }

    public String getParentOrderLid()
    {
        return parentOrderLid;
    }
    
    public String getUrgentFlag()
    {
        return urgentFlag;
    }
    
    public String getComments()
    {
        return comments;
    }
    
    public String getOrderSeqnum() {
		return orderSeqnum;
	}

	@Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("OrderItems [orderLid=");
        builder.append(orderLid);
        builder.append("OrderItems [orderSeqnum=");
        builder.append(orderSeqnum);
        builder.append("]");
        return builder.toString();
    }

	public String getExecFreqCode() {
		return execFreqCode;
	}

	public String getUsage() {
		return usage;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getUnit() {
		return unit;
	}

}
