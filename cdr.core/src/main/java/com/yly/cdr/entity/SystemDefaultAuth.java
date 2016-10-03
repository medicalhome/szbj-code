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
@Table(name = "SYSTEM_DEFAULT_AUTH")
public class SystemDefaultAuth implements Serializable
{

    private BigDecimal systemDefaultAuthSn;

    private String occupationType;

    private String systemAuthId;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getSystemDefaultAuthSn()
    {
        return this.systemDefaultAuthSn;
    }

    public void setSystemDefaultAuthSn(BigDecimal systemDefaultAuthSn)
    {
        this.systemDefaultAuthSn = systemDefaultAuthSn;
    }

    @Column(name = "OCCUPATION_TYPE", nullable = false, length = 50)
    public String getOccupationType()
    {
        return this.occupationType;
    }

    public void setOccupationType(String occupationType)
    {
        this.occupationType = occupationType;
    }

    @Column(name = "SYSTEM_AUTH_ID", nullable = false, length = 50)
    public String getSystemAuthId()
    {
        return this.systemAuthId;
    }

    public void setSystemAuthId(String systemAuthId)
    {
        this.systemAuthId = systemAuthId;
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
