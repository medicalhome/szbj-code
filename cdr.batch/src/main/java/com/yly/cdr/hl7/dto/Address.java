package com.yly.cdr.hl7.dto;


public class Address extends BaseDto
{
    /**
     * 地址类型
     */
    private String addressType;
    /**
     * 地址区县码
     */
    private String districtCode;
    /**
     * 地址区名称
     */
    private String districtName;
    /**
     * 省市地址
     */
    private String proCityAddress;
    /**
     * 乡镇街道地址或完整地址
     */
    private String subDisNameOrFull;
    /**
     * 地址邮政编码
     */
    private String zipCode;

    public String getAddressType()
    {
        return addressType;
    }

    public String getDistrictCode()
    {
        return districtCode;
    }

    public String getDistrictName()
    {
        return districtName;
    }

    public String getProCityAddress()
    {
        return proCityAddress;
    }

    public String getSubDisNameOrFull()
    {
        return subDisNameOrFull;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setAddressType(String addressType)
    {
        this.addressType = addressType;
    }

    public void setDistrictCode(String districtCode)
    {
        this.districtCode = districtCode;
    }

    public void setDistrictName(String districtName)
    {
        this.districtName = districtName;
    }

    public void setProCityAddress(String proCityAddress)
    {
        this.proCityAddress = proCityAddress;
    }

    public void setSubDisNameOrFull(String subDisNameOrFull)
    {
        this.subDisNameOrFull = subDisNameOrFull;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }
    
}
