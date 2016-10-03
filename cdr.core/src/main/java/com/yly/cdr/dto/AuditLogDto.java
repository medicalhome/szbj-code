package com.yly.cdr.dto;

import java.math.BigDecimal;
import java.util.Date;

public class AuditLogDto
{
    private String userId;

    private String userName;

    private String deptCode;

    private Date useBeginDate;

    private Date operationTime;

    private String patientLid;

    private String patientDomain;

    private String dataTypeName;

    private String businessDataNo;

    private String itemName;

    private String clientIp;

    private String clientComputerName;
    
    private BigDecimal auditLogSn;
    
    private String orgCode;
    
    private String orgName;

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getDeptCode()
    {
        return deptCode;
    }

    public void setDeptCode(String deptCode)
    {
        this.deptCode = deptCode;
    }

    public Date getOperationTime()
    {
        return operationTime;
    }

    public void setOperationTime(Date operationTime)
    {
        this.operationTime = operationTime;
    }

    public String getPatientLid()
    {
        return patientLid;
    }

    public void setPatientLid(String patientLid)
    {
        this.patientLid = patientLid;
    }

    public String getPatientDomain()
    {
        return patientDomain;
    }

    public void setPatientDomain(String patientDomain)
    {
        this.patientDomain = patientDomain;
    }

    public String getDataTypeName()
    {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName)
    {
        this.dataTypeName = dataTypeName;
    }

    public String getBusinessDataNo()
    {
        return businessDataNo;
    }

    public void setBusinessDataNo(String businessDataNo)
    {
        this.businessDataNo = businessDataNo;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public Date getUseBeginDate()
    {
        return useBeginDate;
    }

    public void setUseBeginDate(Date useBeginDate)
    {
        this.useBeginDate = useBeginDate;
    }

    public String getClientIp()
    {
        return clientIp;
    }

    public void setClientIp(String clientIp)
    {
        this.clientIp = clientIp;
    }

    public String getClientComputerName()
    {
        return clientComputerName;
    }

    public void setClientComputerName(String clientComputerName)
    {
        this.clientComputerName = clientComputerName;
    }

    public BigDecimal getAuditLogSn()
    {
        return auditLogSn;
    }

    public void setAuditLogSn(BigDecimal auditLogSn)
    {
        this.auditLogSn = auditLogSn;
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

}
