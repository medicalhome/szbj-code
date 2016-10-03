package com.yly.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class Person extends CodeValueDto
{
    /**
     * 版本号
     */
    private String versionNo;

    /**
     * 操作类型
     */
    @NotEmpty(message = "{Person.newUpFlag}")
    private String newUpFlag;

    /**
     * 人员编码
     */
    @NotEmpty(message = "{Person.code}")
    private String code;

    /**
     * 人员名称
     */
    @NotEmpty(message = "{Person.name}")
    private String name;

    /**
     * 拼音码
     */
    private String pyCode;

    /**
     * 性别码
     */
    private String sexCode;

    /**
     * 岗位类别
     */
    private String employmentStatusCd;
    
    /**
     * 在岗状态
     */
    private String jobCategory;

    /**
     * 科室编码
     */
    private String departCd;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 人员类型码
     */
    private String employeeTypeCd;

    /**
     * 邮箱
     */
    private String mailAddress;

    /**
     * 入职日期
     */
    private String serviceStartDate;
    /**
     * 科室名称
     */
    private String departName;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 身份证号
     */
    private String nationalIdentifier;
    /**
     * 财务科室编码
     */
    private String financialDepartCd;
    /**
     * 财务科室名称
     */
    private String financialDepartName;

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

    public String getSexCode()
    {
        return sexCode;
    }

    public String getEmploymentStatusCd()
    {
        return employmentStatusCd;
    }

    public String getDepartCd()
    {
        return departCd;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getEmployeeTypeCd()
    {
        return employeeTypeCd;
    }

    public String getMailAddress()
    {
        return mailAddress;
    }

    public String getServiceStartDate()
    {
        return serviceStartDate;
    }

    public String getJobCategory()
    {
        return jobCategory;
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

    public String getDepartName()
    {
        return departName;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public String getNationalIdentifier()
    {
        return nationalIdentifier;
    }

    public String getFinancialDepartCd()
    {
        return financialDepartCd;
    }

    public String getFinancialDepartName()
    {
        return financialDepartName;
    }
    
    
}
