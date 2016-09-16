package com.founder.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author tong_meng
 *
 */
public class Dept extends CodeValueDto
{
    /**
     * 版本号
     */
    private String versionNo;

    /**
     * 操作类型
     */
    @NotEmpty(message = "{Dept.newUpFlag}")
    private String newUpFlag;

    /**
     * 科室编码
     */
    @NotEmpty(message = "{Dept.code}")
    private String code;

    /**
     * 科室名称
     */
    @NotEmpty(message = "{Dept.name}")
    private String name;
    /**
     * 别名
     */
    private String otherName;
    /**
     * 开放标志
     */
    private String openFlag;
    /**
     * 门诊住院标志
     */
    private String mzFlag;
    /**
     * ERP对照码
     */
    private String erpCode;
    /**
     * 地点
     */
    private String address;
    /**
     * 收入类型
     */
    private String incomeType;
    /**
     * 拼音码
     */
    private String pyCode;

    /**
     * 排序码 
     */
    private String seqNo;

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

    public String getSeqNo()
    {
        return seqNo;
    }

    public String getVersionNo()
    {
        return versionNo;
    }

    public String getUniqueId()
    {
        return code;
    }

    public String getOptType()
    {
        return newUpFlag;
    }

    public String getOtherName()
    {
        return otherName;
    }

    public String getOpenFlag()
    {
        return openFlag;
    }

    public String getMzFlag()
    {
        return mzFlag;
    }

    public String getErpCode()
    {
        return erpCode;
    }

    public String getAddress()
    {
        return address;
    }

    public String getIncomeType()
    {
        return incomeType;
    }
    
}
