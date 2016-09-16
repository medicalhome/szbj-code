package com.founder.cdr.dto.codeValue;

import java.util.Date;

public class HideDrugDto
{
    private String userName;
    private String drugCode;
    private String drugName;
    private String serialNo;
    private String specification;
    private Date createTime;
    public String getUserName()
    {
        return userName;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    public String getDrugCode()
    {
        return drugCode;
    }
    public void setDrugCode(String drugCode)
    {
        this.drugCode = drugCode;
    }
    public String getDrugName()
    {
        return drugName;
    }
    public void setDrugName(String drugName)
    {
        this.drugName = drugName;
    }
    public String getSerialNo()
    {
        return serialNo;
    }
    public void setSerialNo(String serialNo)
    {
        this.serialNo = serialNo;
    }
    public String getSpecification()
    {
        return specification;
    }
    public void setSpecification(String specification)
    {
        this.specification = specification;
    }
    public Date getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
}
