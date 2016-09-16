package com.founder.cdr.hl7.dto.poorin200901uv02;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.founder.cdr.hl7.dto.OrderDto;

public class ExaminationOrderDto extends OrderDto
{
    /**
     * 医嘱号
     */
	@NotEmpty(message="{ExaminationOrderDto.orderLid}")
    private String orderLid;

	/*
	 * $Author: yu_yzh
	 * $Date: 2015/7/29 10:22
	 * 添加检查类型
	 * ADD BEGIN
	 * */
	/**
	 * 检查类型代码
	 * */
	private String itemClass;
	/**
	 * 检查类型名称
	 * */
	private String itemClassName;
	//ADD END

	// $Author :jin_peng
    // $Date : 2012/9/18 16:18$
    // [BUG]0009718 DELETE BEGIN
	
    /*
     * Author: yu_yzh
     * Date: 2015/7/31 10:00
     * */
    /**
     * 医嘱执行小分类，与检查类型代码值相同
     * */
    private String orderExecId;

	/**
     * 检查项目代码
     */
    // @NotEmpty(message="{ExaminationOrderDto.itemCode}")
    private String itemCode;

    /**
     * 检查项目名称
     */
    // @NotEmpty(message="{ExaminationOrderDto.itemName}")
    private String itemName;

    // [BUG]0009718 DELETE END
    /**
     * 检查部位代码
     */
    private String regionCode;

    /**
     * 检查部位名称
     */
    private String regionName;

	// $Author :jin_peng
    // $Date : 2012/9/18 16:18$
    // [BUG]0009718 DELETE BEGIN
    /**
     * 检查方法代码
     */
    // @NotEmpty(message="{ExaminationOrderDto.examMethodCode}")
    private String examMethodCode;

    /**
     * 检查方法名称
     */
    // @NotEmpty(message="{ExaminationOrderDto.examMethodName}")
    private String examMethodName;

    // [BUG]0009718 DELETE END
    
    // $Author :chang_xuewen
    // $Date : 2013/8/12 14:00$
    // [BUG]0035982 ADD BEGIN
    /**
     * 医嘱执行频率编码
     */
    private String executionFrequency;
    
    /**
     * 医嘱执行频率名称
     */
    private String execFreqName;
    
    /**
     * 医嘱开始时间
     */
    private String orderStartTime;
    
    /**
     * 医嘱结束时间
     */
    private String orderEndTime;
    
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
    // [BUG]0035982 ADD END
    
    /**
     * 医嘱描述
     */
    private String orderDescribe;
    
    /*
     * Author: yu_yzh
     * Date: 2015/7/27 15:51
     * 添加费用状态信息
     * Add Begin
     * */
    /**
     * 
     * */
//    private String 
    
    // Add End
    
    public String getOrderLid()
    {
        return orderLid;
    }

    public String getExecutionFrequency() {
		return executionFrequency;
	}

	public String getExecFreqName() {
		return execFreqName;
	}
	

	public String getOrderStartTime() {
		return orderStartTime;
	}

	public String getOrderEndTime() {
		return orderEndTime;
	}

	public String getSkinTestFlag() {
		return skinTestFlag;
	}

	public String getUrgentFlag() {
		return urgentFlag;
	}

	public String getMedViewFlag() {
		return medViewFlag;
	}
	
    public String getItemClass() {
		return itemClass;
	}

	public String getItemClassName() {
		return itemClassName;
	}
	
    public String getOrderExecId() {
		return orderExecId;
	}

	public String getItemCode()
    {
        return itemCode;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

    public String getRegionCode()
    {
        return regionCode;
    }

    public String getRegionName()
    {
        return regionName;
    }

    public String getExamMethodCode()
    {
        return examMethodCode;
    }

    public String getExamMethodName()
    {
        return examMethodName;
    }
    
	public String getOrderDescribe() {
		return orderDescribe;
	}

	public void setOrderDescibe(String orderDescribe) {
		this.orderDescribe = orderDescribe;
	}


    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ExaminationOrderDto [orderLid=");
        builder.append(orderLid);
        builder.append(", itemCode=");
        builder.append(itemCode);
        builder.append(", itemName=");
        builder.append(itemName);
        builder.append(", regionCode=");
        builder.append(regionCode);
        builder.append(", regionName=");
        builder.append(regionName);
        builder.append(", examMethodCode=");
        builder.append(examMethodCode);
        builder.append(", examMethodName=");
        builder.append(examMethodName);
        builder.append(", orderDescibe=");
        builder.append(orderDescribe);
        builder.append("]");
        return builder.toString();
    }



}
