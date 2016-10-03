package com.yly.cdr.hl7.dto;

public class GradedDetail extends BaseDto
{
    /**
     * 条目评分
     */
    private String itemsGraded;

    /**
     * 条目名称
     */
    private String itemsName;

    /**
     * 条目单位
     */
    private String unit;

    /**
     * 条目代码
     */
    private String code;

    public String getCode()
    {
        return code;
    }

    public String getItemsGraded()
    {
        return itemsGraded;
    }

    public String getItemsName()
    {
        return itemsName;
    }

    public String getUnit()
    {
        return unit;
    }
}
