package com.founder.cdr.hl7.dto;

public class Contact extends BaseDto
{
    /**
     * 与患者关系
     */
    private String relationship;
    /**
     * 联系人地址类型
     */
    private String contactAddressType;
    /**
     * 联系人地址区县码
     */
    private String contactDistrictCode;
    /**
     * 联系人地址区名称
     */
    private String contactDistrictValue;
    /**
     * 联系人省市地址
     */
    private String contactProCityAddress;
    /**
     * 联系人乡镇街道地址或完整地址
     */
    private String contactSubDisNameOrFull;
    /**
     * 联系人地址邮政编码
     */
    private String contactZipCode;
    /**
     * 联系人电话
     */
    private String contactPhone;
    /**
     * 联系人电话
     */
    private String contactMobelPhone;
    /**
     * 联系人电子邮件
     */
    private String contactEmail;
    /**
     * 联系人姓名
     */
    private String contactName;

    public String getContactAddressType()
    {
        return contactAddressType;
    }

    public String getContactDistrictCode()
    {
        return contactDistrictCode;
    }

    public String getContactDistrictValue()
    {
        return contactDistrictValue;
    }

    public String getContactProCityAddress()
    {
        return contactProCityAddress;
    }

    public String getContactSubDisNameOrFull()
    {
        return contactSubDisNameOrFull;
    }

    public String getContactZipCode()
    {
        return contactZipCode;
    }

    public String getContactPhone()
    {
        return contactPhone;
    }

    public String getContactEmail()
    {
        return contactEmail;
    }

    public String getContactName()
    {
        return contactName;
    }

    public String getRelationship()
    {
        return relationship;
    }

    public String getContactMobelPhone()
    {
        return contactMobelPhone;
    }
    
}
