package com.founder.cdr.entity;

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
@Table(name = "PROCEDURE_PARTICIPANTS")
public class ProcedureParticipants implements Serializable
{

    private BigDecimal participantSn;

    private BigDecimal procedureSn;

    private String participantIdentity;

    private String participantId;

    private String participantName;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String participantIdentityName;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getParticipantSn()
    {
        return this.participantSn;
    }

    public void setParticipantSn(BigDecimal participantSn)
    {
        this.participantSn = participantSn;
    }

    @Column(name = "PROCEDURE_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getProcedureSn()
    {
        return this.procedureSn;
    }

    public void setProcedureSn(BigDecimal procedureSn)
    {
        this.procedureSn = procedureSn;
    }

    @Column(name = "PARTICIPANT_IDENTITY", nullable = false, length = 12)
    public String getParticipantIdentity()
    {
        return this.participantIdentity;
    }

    public void setParticipantIdentity(String participantIdentity)
    {
        this.participantIdentity = participantIdentity;
    }

    @Column(name = "PARTICIPANT_ID", length = 12)
    public String getParticipantId()
    {
        return this.participantId;
    }

    public void setParticipantId(String participantId)
    {
        this.participantId = participantId;
    }

    @Column(name = "PARTICIPANT_NAME", nullable = false, length = 50)
    public String getParticipantName()
    {
        return this.participantName;
    }

    public void setParticipantName(String participantName)
    {
        this.participantName = participantName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_TIME", nullable = false, length = 7)
    public Date getCreateTime()
    {
        return this.createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATE_TIME", nullable = false, length = 7)
    public Date getUpdateTime()
    {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    @Column(name = "UPDATE_COUNT", nullable = false, precision = 22, scale = 0)
    public BigDecimal getUpdateCount()
    {
        return this.updateCount;
    }

    public void setUpdateCount(BigDecimal updateCount)
    {
        this.updateCount = updateCount;
    }

    @Column(name = "DELETE_FLAG", nullable = false, length = 1)
    public String getDeleteFlag()
    {
        return this.deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DELETE_TIME", length = 7)
    public Date getDeleteTime()
    {
        return this.deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    @Column(name = "PARTICIPANT_IDENTITY_NAME", length = 40)
    public String getParticipantIdentityName()
    {
        return this.participantIdentityName;
    }

    public void setParticipantIdentityName(String participantIdentityName)
    {
        this.participantIdentityName = participantIdentityName;
    }

}
