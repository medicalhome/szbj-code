package com.founder.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class LabSubitem extends CodeValueDto
{
    /**
     * 版本号
     */
    private String versionNo;

    /**
     * 操作类型
     */
    @NotEmpty(message = "{LabSubitem.newUpFlag}")
    private String newUpFlag;

    /**
     * 项目编码
     */
    @NotEmpty(message = "{LabSubitem.code}")
    private String code;

    /**
     * 项目名称
     */
    @NotEmpty(message = "{LabSubitem.name}")
    private String name;

    /**
     * 拼音码
     */
    private String pyCode;

    /**
     * 检验项目全称
     */
    private String fullName;

    /**
     * 检验项目中文名
     */
    private String chineseName;

    /**
     * 小数位
     */
    private String decimals;

    /**
     * 单位
     */
    private String units;

    /**
     * 男性特殊参考范围
     */
    private String maleRefer;

    /**
     * 女性特殊参考范围
     */
    private String femaleRefer;

    /**
     * 临床意义
     */
    private String clinicalMeaning;

    /**
     * 操作备注
     */
    private String operateRemark;

    /**
     * 应用范围
     */
    private String appRange;

    public String getVersionNo()
    {
        return versionNo;
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

    public String getPyCode()
    {
        return pyCode;
    }

    public String getFullName()
    {
        return fullName;
    }

    public String getChineseName()
    {
        return chineseName;
    }

    public String getDecimals()
    {
        return decimals;
    }

    public String getUnits()
    {
        return units;
    }

    public String getMaleRefer()
    {
        return maleRefer;
    }
    
    public String getFemaleRefer()
    {
        return femaleRefer;
    }

    public String getClinicalMeaning()
    {
        return clinicalMeaning;
    }

    public String getOperateRemark()
    {
        return operateRemark;
    }

    public String getAppRange()
    {
        return appRange;
    }
    
    public String getUniqueId()
    {
        return code;
    }

    public String getOptType()
    {
        return newUpFlag;
    }

}
