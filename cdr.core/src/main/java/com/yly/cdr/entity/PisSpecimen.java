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
@Table(name = "PIS_SPECIMEN")
public class PisSpecimen implements Serializable
{

    private BigDecimal specimenSn;

    private BigDecimal totalCutBlocks;

    private BigDecimal butiedBlocks;

    private BigDecimal frozenPieces;

    private BigDecimal HEPieces;

    private BigDecimal immunityPieces;

    private String color;

    private String organization;

    private BigDecimal sendBlocks;

    private String size;

    private String section;

    private String getPerson;

    private String getPersonName;

    private String recordPerson;

    private String recordPersonName;

    private Date frozenCheckTime;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getSpecimenSn()
    {
        return this.specimenSn;
    }

    public void setSpecimenSn(BigDecimal specimenSn)
    {
        this.specimenSn = specimenSn;
    }

    @Column(name = "TOTAL_CUT_BLOCKS", precision = 22, scale = 0)
    public BigDecimal getTotalCutBlocks()
    {
        return this.totalCutBlocks;
    }

    public void setTotalCutBlocks(BigDecimal totalCutBlocks)
    {
        this.totalCutBlocks = totalCutBlocks;
    }

    @Column(name = "BUTIED_BLOCKS", precision = 22, scale = 0)
    public BigDecimal getButiedBlocks()
    {
        return this.butiedBlocks;
    }

    public void setButiedBlocks(BigDecimal butiedBlocks)
    {
        this.butiedBlocks = butiedBlocks;
    }

    @Column(name = "FROZEN_PIECES", precision = 22, scale = 0)
    public BigDecimal getFrozenPieces()
    {
        return this.frozenPieces;
    }

    public void setFrozenPieces(BigDecimal frozenPieces)
    {
        this.frozenPieces = frozenPieces;
    }

    @Column(name = "H_E_PIECES", precision = 22, scale = 0)
    public BigDecimal getHEPieces()
    {
        return this.HEPieces;
    }

    public void setHEPieces(BigDecimal HEPieces)
    {
        this.HEPieces = HEPieces;
    }

    @Column(name = "IMMUNITY_PIECES", precision = 22, scale = 0)
    public BigDecimal getImmunityPieces()
    {
        return this.immunityPieces;
    }

    public void setImmunityPieces(BigDecimal immunityPieces)
    {
        this.immunityPieces = immunityPieces;
    }

    @Column(name = "COLOR", length = 20)
    public String getColor()
    {
        return this.color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    @Column(name = "ORGANIZATION", length = 50)
    public String getOrganization()
    {
        return this.organization;
    }

    public void setOrganization(String organization)
    {
        this.organization = organization;
    }

    @Column(name = "SEND_BLOCKS", precision = 22, scale = 0)
    public BigDecimal getSendBlocks()
    {
        return this.sendBlocks;
    }

    public void setSendBlocks(BigDecimal sendBlocks)
    {
        this.sendBlocks = sendBlocks;
    }

    @Column(name = "size", length = 56)
    public String getSize()
    {
        return this.size;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    @Column(name = "SECTION", length = 512)
    public String getSection()
    {
        return this.section;
    }

    public void setSection(String section)
    {
        this.section = section;
    }

    @Column(name = "GET_PERSON", length = 20)
    public String getGetPerson()
    {
        return this.getPerson;
    }

    public void setGetPerson(String getPerson)
    {
        this.getPerson = getPerson;
    }

    @Column(name = "GET_PERSON_NAME", length = 56)
    public String getGetPersonName()
    {
        return this.getPersonName;
    }

    public void setGetPersonName(String getPersonName)
    {
        this.getPersonName = getPersonName;
    }

    @Column(name = "RECORD_PERSON", length = 20)
    public String getRecordPerson()
    {
        return this.recordPerson;
    }

    public void setRecordPerson(String recordPerson)
    {
        this.recordPerson = recordPerson;
    }

    @Column(name = "RECORD_PERSON_NAME", length = 56)
    public String getRecordPersonName()
    {
        return this.recordPersonName;
    }

    public void setRecordPersonName(String recordPersonName)
    {
        this.recordPersonName = recordPersonName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "FROZEN_CHECK_TIME", length = 7)
    public Date getFrozenCheckTime()
    {
        return this.frozenCheckTime;
    }

    public void setFrozenCheckTime(Date frozenCheckTime)
    {
        this.frozenCheckTime = frozenCheckTime;
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

}
