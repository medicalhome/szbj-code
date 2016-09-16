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
@Table(name = "SYSTEM_MENU")
public class SystemMenu implements Serializable
{

    private BigDecimal menuSn;

    private BigDecimal parentMenuSn;

    private String menuCode;

    private BigDecimal menuLevel;

    private String menuName;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String auths;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getMenuSn()
    {
        return this.menuSn;
    }

    public void setMenuSn(BigDecimal menuSn)
    {
        this.menuSn = menuSn;
    }

    @Column(name = "PARENT_MENU_SN", precision = 22, scale = 0)
    public BigDecimal getParentMenuSn()
    {
        return this.parentMenuSn;
    }

    public void setParentMenuSn(BigDecimal parentMenuSn)
    {
        this.parentMenuSn = parentMenuSn;
    }

    @Column(name = "MENU_CODE", length = 20)
    public String getMenuCode()
    {
        return this.menuCode;
    }

    public void setMenuCode(String menuCode)
    {
        this.menuCode = menuCode;
    }

    @Column(name = "MENU_LEVEL", precision = 22, scale = 0)
    public BigDecimal getMenuLevel()
    {
        return this.menuLevel;
    }

    public void setMenuLevel(BigDecimal menuLevel)
    {
        this.menuLevel = menuLevel;
    }

    @Column(name = "MENU_NAME", length = 20)
    public String getMenuName()
    {
        return this.menuName;
    }

    public void setMenuName(String menuName)
    {
        this.menuName = menuName;
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

    @Column(name = "AUTHS", length = 200)
    public String getAuths()
    {
        return this.auths;
    }

    public void setAuths(String auths)
    {
        this.auths = auths;
    }

}
