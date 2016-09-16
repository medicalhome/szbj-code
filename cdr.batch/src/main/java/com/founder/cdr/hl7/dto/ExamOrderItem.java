package com.founder.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;


public class ExamOrderItem extends BaseDto
{
    /**
     * 医嘱号
     */
    @NotEmpty(message="{ExamOrderItem.orderLid}")
    private String orderLid;
    /**
     * 检查项目代码
     */
    private String itemCode;
    /**
     * 检查项目名称
     */
    @NotEmpty(message="{ExamOrderItem.itemName}")
    private String itemName;
    /**
     * 检查方法代码
     */
    private String examMethodCode;
    /**
     * 检查方法名称
     */
    private String examMethodName;
    /**
     * 检查部位名称
     */
    @NotEmpty(message="{ExamOrderItem.regionName}")
    private String regionName;
    /**
     * 检查部位代码
     */
    private String regionCode;

    public String getOrderLid()
    {
        return orderLid;
    }
    
    
    public String getExamMethodName()
    {
        return examMethodName;
    }


    public String getItemCode()
    {
        return itemCode;
    }

    public String getItemName()
    {
        return itemName;
    }

    public String getExamMethodCode()
    {
        return examMethodCode;
    }

    public String getRegionName()
    {
        return regionName;
    }

    public String getRegionCode()
    {
        return regionCode;
    }
}
