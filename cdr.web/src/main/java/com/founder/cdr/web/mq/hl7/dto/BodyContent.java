package com.founder.cdr.web.mq.hl7.dto;

import java.util.List;

public class BodyContent
{
    private String operDateTime;
    
    private String operUserId;
    
    private String operUnitId;
    
    private String hospitalCode;
    
    private String hospitalName;
    
    private String auditEvent;
    
    private String auditSystemId;
    
    private List<Content> content;
    
    private String computerName;
    
    private String ipAddress;
    
    private String Field1;
    
    private String Field2;
    
    private String Field3;

    public String getOperDateTime()
    {
        return operDateTime;
    }

    public void setOperDateTime(String operDateTime)
    {
        this.operDateTime = operDateTime;
    }

    public String getOperUserId()
    {
        return operUserId;
    }

    public void setOperUserId(String operUserId)
    {
        this.operUserId = operUserId;
    }

    public String getOperUnitId()
    {
        return operUnitId;
    }

    public void setOperUnitId(String operUnitId)
    {
        this.operUnitId = operUnitId;
    }

    public String getHospitalCode()
    {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode)
    {
        this.hospitalCode = hospitalCode;
    }

    public String getHospitalName()
    {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName)
    {
        this.hospitalName = hospitalName;
    }

    public String getAuditEvent()
    {
        return auditEvent;
    }

    public void setAuditEvent(String auditEvent)
    {
        this.auditEvent = auditEvent;
    }

    public String getAuditSystemId()
    {
        return auditSystemId;
    }

    public void setAuditSystemId(String auditSystemId)
    {
        this.auditSystemId = auditSystemId;
    }

    public List<Content> getContent()
    {
        return content;
    }

    public void setContent(List<Content> content)
    {
        this.content = content;
    }

    public String getComputerName()
    {
        return computerName;
    }

    public void setComputerName(String computerName)
    {
        this.computerName = computerName;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    public String getField1()
    {
        return Field1;
    }

    public void setField1(String field1)
    {
        Field1 = field1;
    }

    public String getField2()
    {
        return Field2;
    }

    public void setField2(String field2)
    {
        Field2 = field2;
    }

    public String getField3()
    {
        return Field3;
    }

    public void setField3(String field3)
    {
        Field3 = field3;
    }
    
}
