package com.yly.cdr.hl7.dto;


public class SpecialRequest extends BaseDto
{
    /**
     * 血液特殊要求代码
     */
    private String specialRequestCode;
    /**
     * 血液特殊要求名称
     */
    private String specialRequestName;

    public String getSpecialRequestCode()
    {
        return specialRequestCode;
    }

    public String getSpecialRequestName()
    {
        return specialRequestName;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("SpecialRequest [specialRequestCode=");
        builder.append(specialRequestCode);
        builder.append(", specialRequestName=");
        builder.append(specialRequestName);
        builder.append("]");
        return builder.toString();
    }
    
    
}
