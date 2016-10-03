package com.yly.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;


public class DrugName extends CodeValueDto
{
    /**
     * 版本号
     */
    private String version;
    
    /**
     * 操作类型
     */
    private String newUpFlag;
    /**
     * 药品编码
     */
    @NotEmpty(message = "{DrugName.code}")
    private String code;
    /**
     * 药品名称
     */
    @NotEmpty(message = "{DrugName.name}")
    private String name;
    /**
     * 常用标记
     */
    private String flag;
    /**
     * 打印名
     */
    private String printName;
    /**
     * 名称类型
     */
    private String nameType;

    /**
     * 拼音码
     */
    private String pyCode;

    public String getVersionNo()
    {
        return version;
    }

    public String getNewUpFlag()
    {
        return newUpFlag;
    }

    public String getCode()
    {
        return code;
    }

    public String getName()
    {
        return name;
    }

    public String getFlag()
    {
        return flag;
    }

    public String getPrintName()
    {
        return printName;
    }

    public String getNameType()
    {
        return nameType;
    }

    public String getPyCode()
    {
        return pyCode;
    }
   
    public String getUniqueId()
    {
        return code;
    }

    public String getOptType()
    {
        return newUpFlag;
    }
    
    public String getForeignKey()
    {
        return name;
    }

}
