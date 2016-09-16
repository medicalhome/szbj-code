package com.founder.cdr.hl7.dto;
import org.hibernate.validator.constraints.NotEmpty;

public class LabOrderDto extends BaseDto
{
    /**
     * 医嘱号
     */
	@NotEmpty(message = "{LabOrderDto.orderLid}")
    private String orderLid;

    /**
     * 检验项目ID
     */
//    @NotEmpty(message = "{LabOrderDto.itemCode}")
    private String itemCode;
    /**
     * 检验项目名称
     */
//    @NotEmpty(message = "{LabOrderDto.itemName}")
    private String itemName;
    
    /*
     * $Author: yu_yzh
     * $Date: 2015/7/31 13:58
     * 添加检验项目类型，医嘱执行小分类
     * ADD BEGIN
     * */
    /**
     * 检验项目类型
     * */
    private String itemClass;


	/**
     * 医嘱执行小分类
     * */
    private String orderExecId;
    // ADD END
    
    // $Author :tong_meng
    // $Date : 2013/7/31 15:50$
    // [BUG]0035541 ADD BEGIN
    /**
     * 医嘱开始时间
     */
    private String orderStartTime;
    
    /**
     * 医嘱结束时间
     */
    private String orderEndTime;
    
    /**
     * 医嘱执行频率编码
     */
    private String orderExecFreqCode;
    
    /**
     * 医嘱执行频率名称
     */
    private String orderExecFreqName;
    
    /**
     * 是否皮试
     */
    private String skinTestFlag;

    /**
     * 是否药观
     */
    private String medViewFlag;
    
    /**
     * 是否加急
     */
    private String urgentFlag;
    // [BUG]0035541 ADD END
    /**
     * 标本号
     */
    private String sampleId;

    /**
     * 执行科室编码
     */
    private String deliveryId;

    /**
     * 执行科室名称
     */
    private String deliveryName;

    /**
     * 测试项目价格
     */
    private String itemPrice;

    /**
     * 测试项目价格单位
     */
    private String itemPriceUtil;

    /**
     * 标本要求
     */
    private String sampleRequirement;
    
    /**
     * 检验描述编码
     */
    private String labDescriptionCode;
    
    /**
     * 检验描述名称
     */
    private String labDescriptionName;
    
   //BUGID 54396 begin du_xiaolan
    /**
     * 婴儿编号
     */
    private String childNo;
    //BUGID 54396 end

    public String getOrderLid()
    {
        return orderLid;
    }

    public String getItemCode()
    {
        return itemCode;
    }

    public String getItemName()
    {
        return itemName;
    }

    public String getItemClass() {
		return itemClass;
	}

	public String getOrderExecId() {
		return orderExecId;
	}
    
    public String getOrderStartTime()
    {
        return orderStartTime;
    }

    public String getOrderEndTime()
    {
        return orderEndTime;
    }

    public String getOrderExecFreqCode()
    {
        return orderExecFreqCode;
    }

    public String getOrderExecFreqName()
    {
        return orderExecFreqName;
    }

    public String getSkinTestFlag()
    {
        return skinTestFlag;
    }

    public String getMedViewFlag()
    {
        return medViewFlag;
    }

    public String getUrgentFlag()
    {
        return urgentFlag;
    }

    public String getSampleId()
    {
        return sampleId;
    }

    public String getDeliveryId()
    {
        return deliveryId;
    }

    public String getDeliveryName()
    {
        return deliveryName;
    }

    public String getItemPrice()
    {
        return itemPrice;
    }

    public String getItemPriceUtil()
    {
        return itemPriceUtil;
    }

    public String getSampleRequirement()
    {
        return sampleRequirement;
    }

    public String getLabDescriptionCode()
    {
        return labDescriptionCode;
    }

    public String getLabDescriptionName()
    {
        return labDescriptionName;
    }
    
    public String getChildNo() {
		return childNo;
	}

	@Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("LabOrderDto [orderLid=");
        builder.append(orderLid);
        builder.append(", itemCode=");
        builder.append(itemCode);
        builder.append(", itemName=");
        builder.append(itemName);
        builder.append(", sampleId=");
        builder.append(sampleId);
        builder.append(", deliveryId=");
        builder.append(deliveryId);
        builder.append(", deliveryName=");
        builder.append(deliveryName);
        builder.append(", itemPrice=");
        builder.append(itemPrice);
        builder.append(", itemPriceUtil=");
        builder.append(itemPriceUtil);
        builder.append(", sampleRequirement=");
        builder.append(sampleRequirement);
        builder.append(", labDescriptionCode=");
        builder.append(labDescriptionCode);
        builder.append(", labDescriptionName=");
        builder.append(labDescriptionName);
        builder.append("]");
        return builder.toString();
    }
    
}
