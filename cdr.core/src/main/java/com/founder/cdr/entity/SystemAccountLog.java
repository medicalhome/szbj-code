package com.founder.cdr.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Id;

import java.io.Serializable;

@Entity
@Table(name = "SYSTEM_ACCOUNT_LOG")
public class SystemAccountLog implements Serializable
{

    private BigDecimal accountLogSn;

    private String userId;

    private String userName;

    private String deptCode;

    private Date useBeginDate;

    private Date useEndDate;

    private String linkKindsCode;

    private String linkKindsName;

    private String clientIp;
    
    private String clientComputerName;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    @Id
    public BigDecimal getAccountLogSn()
    {
        return this.accountLogSn;
    }

    public void setAccountLogSn(BigDecimal accountLogSn)
    {
        this.accountLogSn = accountLogSn;
    }

    @Column(name = "USER_ID", nullable = false, length = 12)
    public String getUserId()
    {
        return this.userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    @Column(name = "USER_NAME", length = 56)
    public String getUserName()
    {
        return this.userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    @Column(name = "DEPT_CODE", length = 50)
    public String getDeptCode()
    {
        return this.deptCode;
    }

    public void setDeptCode(String deptCode)
    {
        this.deptCode = deptCode;
    }

    @Column(name = "LINK_KINDS_CODE", length = 12)
    public String getLinkKindsCode()
    {
        return this.linkKindsCode;
    }

    public void setLinkKindsCode(String linkKindsCode)
    {
        this.linkKindsCode = linkKindsCode;
    }

    @Column(name = "LINK_KINDS_NAME", length = 100)
    public String getLinkKindsName()
    {
        return this.linkKindsName;
    }

    public void setLinkKindsName(String linkKindsName)
    {
        this.linkKindsName = linkKindsName;
    }

    @Column(name = "CLIENT_IP", length = 56)
    public String getClientIp()
    {
        return this.clientIp;
    }

    public void setClientIp(String clientIp)
    {
        this.clientIp = clientIp;
    }
    
    @Column(name = "CLIENT_COMPUTER_NAME", length = 200)
    public String getClientComputerName()
    {
        return this.clientComputerName;
    }

    public void setClientComputerName(String clientComputerName)
    {
        this.clientComputerName = clientComputerName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "USE_BEGIN_DATE", length = 7)
    public Date getUseBeginDate()
    {
        return this.useBeginDate;
    }

    public void setUseBeginDate(Date useBeginDate)
    {
        this.useBeginDate = useBeginDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "USE_END_DATE", length = 7)
    public Date getUseEndDate()
    {
        return this.useEndDate;
    }

    public void setUseEndDate(Date useEndDate)
    {
        this.useEndDate = useEndDate;
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
