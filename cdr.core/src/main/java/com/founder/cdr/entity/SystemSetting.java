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
@Table(name = "SYSTEM_SETTING")
public class SystemSetting implements Serializable
{

    private String userSn;

    private String displayMenus;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String outpatientViews;

    private String inpatientViews;

    private String normalViews;

    private String timerViews;

    private String visitIndexViews;

    // Author:jia_yanqing
    // Date : 2013/1/10 18:30
    // [BUG]0012699 ADD BEGIN
    private BigDecimal rowsPerPage;

    // [BUG]0012699 ADD END

    // $Author :jin_pen
    // $Date : 2013/12/19 09:51$
    // [BUG]0040598 ADD BEGIN
    private BigDecimal rowsPerPageLab;

    // [BUG]0040598 ADD END

    @Id
    public String getUserSn()
    {
        return this.userSn;
    }

    public void setUserSn(String userSn)
    {
        this.userSn = userSn;
    }

    @Column(name = "DISPLAY_MENUS", length = 200)
    public String getDisplayMenus()
    {
        return this.displayMenus;
    }

    public void setDisplayMenus(String displayMenus)
    {
        this.displayMenus = displayMenus;
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

    @Column(name = "UPDATE_COUNT", precision = 22, scale = 0)
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

    @Column(name = "OUTPATIENT_VIEWS", length = 200)
    public String getOutpatientViews()
    {
        return this.outpatientViews;
    }

    public void setOutpatientViews(String outpatientViews)
    {
        this.outpatientViews = outpatientViews;
    }

    @Column(name = "INPATIENT_VIEWS", length = 200)
    public String getInpatientViews()
    {
        return this.inpatientViews;
    }

    public void setInpatientViews(String inpatientViews)
    {
        this.inpatientViews = inpatientViews;
    }

    @Column(name = "NORMAL_VIEWS", length = 200)
    public String getNormalViews()
    {
        return this.normalViews;
    }

    public void setNormalViews(String normalViews)
    {
        this.normalViews = normalViews;
    }

    @Column(name = "TIMER_VIEWS", length = 200)
    public String getTimerViews()
    {
        return this.timerViews;
    }

    public void setTimerViews(String timerViews)
    {
        this.timerViews = timerViews;
    }

    @Column(name = "VISIT_INDEX_VIEWS", length = 200)
    public String getVisitIndexViews()
    {
        return this.visitIndexViews;
    }

    public void setVisitIndexViews(String visitIndexViews)
    {
        this.visitIndexViews = visitIndexViews;
    }

    // Author:jia_yanqing
    // Date : 2013/1/10 18:30
    // [BUG]0012699 ADD BEGIN
    @Column(name = "ROWS_PER_PAGE", nullable = false, precision = 22, scale = 0)
    public BigDecimal getRowsPerPage()
    {
        return rowsPerPage;
    }

    public void setRowsPerPage(BigDecimal rowsPerPage)
    {
        this.rowsPerPage = rowsPerPage;
    }

    // [BUG]0012699 ADD END

    @Column(name = "ROWS_PER_PAGE_LAB", nullable = false, precision = 22, scale = 0)
    public BigDecimal getRowsPerPageLab()
    {
        return rowsPerPageLab;
    }

    public void setRowsPerPageLab(BigDecimal rowsPerPageLab)
    {
        this.rowsPerPageLab = rowsPerPageLab;
    }

}
