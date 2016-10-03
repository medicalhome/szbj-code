package com.yly.cdr.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.founder.fasf.core.util.daohelper.annotation.Generator;

import java.io.Serializable;

@Entity
@Table(name = "CHIEF_COMPLAINT")
public class ChiefComplaint implements Serializable
{

    private BigDecimal complaintSn;

    private BigDecimal documentSn;

    private String complaintContent;

    private String symptomCode;

    private String startTime;

    private String stopTime;

    private String symptomDescription;

    private String acuteLevel;

    private String severity;

    private String firstTime;

    private String symptomName;

    private String acuteLevelName;

    private String severityName;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getComplaintSn()
    {
        return this.complaintSn;
    }

    public void setComplaintSn(BigDecimal complaintSn)
    {
        this.complaintSn = complaintSn;
    }

    @Column(name = "DOCUMENT_SN", precision = 22, scale = 0)
    public BigDecimal getDocumentSn()
    {
        return this.documentSn;
    }

    public void setDocumentSn(BigDecimal documentSn)
    {
        this.documentSn = documentSn;
    }

    @Column(name = "COMPLAINT_CONTENT")
    public String getComplaintContent()
    {
        return this.complaintContent;
    }

    public void setComplaintContent(String complaintContent)
    {
        this.complaintContent = complaintContent;
    }

    @Column(name = "SYMPTOM_CODE", length = 6)
    public String getSymptomCode()
    {
        return this.symptomCode;
    }

    public void setSymptomCode(String symptomCode)
    {
        this.symptomCode = symptomCode;
    }

    @Column(name = "START_TIME", length = 10)
    public String getStartTime()
    {
        return this.startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    @Column(name = "STOP_TIME", length = 10)
    public String getStopTime()
    {
        return this.stopTime;
    }

    public void setStopTime(String stopTime)
    {
        this.stopTime = stopTime;
    }

    @Column(name = "SYMPTOM_DESCRIPTION")
    public String getSymptomDescription()
    {
        return this.symptomDescription;
    }

    public void setSymptomDescription(String symptomDescription)
    {
        this.symptomDescription = symptomDescription;
    }

    @Column(name = "ACUTE_LEVEL", length = 6)
    public String getAcuteLevel()
    {
        return this.acuteLevel;
    }

    public void setAcuteLevel(String acuteLevel)
    {
        this.acuteLevel = acuteLevel;
    }

    @Column(name = "SEVERITY", length = 6)
    public String getSeverity()
    {
        return this.severity;
    }

    public void setSeverity(String severity)
    {
        this.severity = severity;
    }

    @Column(name = "FIRST_TIME", length = 1)
    public String getFirstTime()
    {
        return this.firstTime;
    }

    public void setFirstTime(String firstTime)
    {
        this.firstTime = firstTime;
    }

    @Column(name = "SYMPTOM_NAME", length = 40)
    public String getSymptomName()
    {
        return this.symptomName;
    }

    public void setSymptomName(String symptomName)
    {
        this.symptomName = symptomName;
    }

    @Column(name = "ACUTE_LEVEL_NAME", length = 40)
    public String getAcuteLevelName()
    {
        return this.acuteLevelName;
    }

    public void setAcuteLevelName(String acuteLevelName)
    {
        this.acuteLevelName = acuteLevelName;
    }

    @Column(name = "SEVERITY_NAME", length = 40)
    public String getSeverityName()
    {
        return this.severityName;
    }

    public void setSeverityName(String severityName)
    {
        this.severityName = severityName;
    }

}
