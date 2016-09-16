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
@Table(name = "CODE_SYSTEM")
public class CodeSystem implements Serializable
{

    private BigDecimal csId;

    private String csCode;

    private String csOid;

    private String csName;

    private String versionNo;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getCsId()
    {
        return this.csId;
    }

    public void setCsId(BigDecimal csId)
    {
        this.csId = csId;
    }

    @Column(name = "CS_CODE", nullable = false, length = 32)
    public String getCsCode()
    {
        return this.csCode;
    }

    public void setCsCode(String csCode)
    {
        this.csCode = csCode;
    }

    @Column(name = "CS_OID")
    public String getCsOid()
    {
        return this.csOid;
    }

    public void setCsOid(String csOid)
    {
        this.csOid = csOid;
    }

    @Column(name = "CS_NAME")
    public String getCsName()
    {
        return this.csName;
    }

    public void setCsName(String csName)
    {
        this.csName = csName;
    }

    @Column(name = "VERSION_NO", nullable = false, length = 32)
    public String getVersionNo()
    {
        return this.versionNo;
    }

    public void setVersionNo(String versionNo)
    {
        this.versionNo = versionNo;
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
