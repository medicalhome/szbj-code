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
@Table(name = "SYSTEM_AUTH")
public class SystemAuth implements Serializable
{

    private String systemAuthId;

    private String memo;

    private BigDecimal occupationType;

    private BigDecimal authType;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public String getSystemAuthId()
    {
        return this.systemAuthId;
    }

    public void setSystemAuthId(String systemAuthId)
    {
        this.systemAuthId = systemAuthId;
    }

    @Column(name = "MEMO", nullable = false, length = 100)
    public String getMemo()
    {
        return this.memo;
    }

    public void setMemo(String memo)
    {
        this.memo = memo;
    }

    @Column(name = "OCCUPATION_TYPE", precision = 22, scale = 0)
    public BigDecimal getOccupationType()
    {
        return this.occupationType;
    }

    public void setOccupationType(BigDecimal occupationType)
    {
        this.occupationType = occupationType;
    }

    @Column(name = "AUTH_TYPE", precision = 22, scale = 0)
    public BigDecimal getAuthType()
    {
        return this.authType;
    }

    public void setAuthType(BigDecimal authType)
    {
        this.authType = authType;
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
