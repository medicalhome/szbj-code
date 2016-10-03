package com.yly.cdr.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.founder.fasf.core.util.daohelper.annotation.Generator;

import java.io.Serializable;

@Entity
@Table(name = "CLINICAL_EVENT")
public class ClinicalEvent implements Serializable
{

    private BigDecimal clinicalEventSn;

    private BigDecimal documentSn;

    private String eventName;

    private String eventCategory;

    private Date startTime;

    private Date endTime;

    private String occuredIn;

    private String ocurredPlace;

    private String eventParticipants;

    private String eventReason;

    private String eventResult;

    private String eventCategoryName;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getClinicalEventSn()
    {
        return this.clinicalEventSn;
    }

    public void setClinicalEventSn(BigDecimal clinicalEventSn)
    {
        this.clinicalEventSn = clinicalEventSn;
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

    @Column(name = "EVENT_NAME", length = 100)
    public String getEventName()
    {
        return this.eventName;
    }

    public void setEventName(String eventName)
    {
        this.eventName = eventName;
    }

    @Column(name = "EVENT_CATEGORY", length = 12)
    public String getEventCategory()
    {
        return this.eventCategory;
    }

    public void setEventCategory(String eventCategory)
    {
        this.eventCategory = eventCategory;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "START_TIME", length = 7)
    public Date getStartTime()
    {
        return this.startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "END_TIME", length = 7)
    public Date getEndTime()
    {
        return this.endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    @Column(name = "OCCURED_IN", length = 100)
    public String getOccuredIn()
    {
        return this.occuredIn;
    }

    public void setOccuredIn(String occuredIn)
    {
        this.occuredIn = occuredIn;
    }

    @Column(name = "OCURRED_PLACE", length = 100)
    public String getOcurredPlace()
    {
        return this.ocurredPlace;
    }

    public void setOcurredPlace(String ocurredPlace)
    {
        this.ocurredPlace = ocurredPlace;
    }

    @Column(name = "EVENT_PARTICIPANTS", length = 100)
    public String getEventParticipants()
    {
        return this.eventParticipants;
    }

    public void setEventParticipants(String eventParticipants)
    {
        this.eventParticipants = eventParticipants;
    }

    @Column(name = "EVENT_REASON")
    public String getEventReason()
    {
        return this.eventReason;
    }

    public void setEventReason(String eventReason)
    {
        this.eventReason = eventReason;
    }

    @Column(name = "EVENT_RESULT")
    public String getEventResult()
    {
        return this.eventResult;
    }

    public void setEventResult(String eventResult)
    {
        this.eventResult = eventResult;
    }

    @Column(name = "EVENT_CATEGORY_NAME", length = 40)
    public String getEventCategoryName()
    {
        return this.eventCategoryName;
    }

    public void setEventCategoryName(String eventCategoryName)
    {
        this.eventCategoryName = eventCategoryName;
    }

}
