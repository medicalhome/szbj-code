package com.yly.cdr.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Id;

import com.founder.fasf.core.util.daohelper.annotation.Generator;
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

import java.io.Serializable;

@Entity
@Table(name = "SYSTEM_IMPRESSION_LOG")
public class SystemImpressionLog implements Serializable
{

    private BigDecimal impressionLogSn;

    private BigDecimal accountLogSn;

    private String requestUrl;

    private String requestParams;

    private BigDecimal responseTimeMillis;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_SYSTEM_IMPRESSION_LOG") })
    public BigDecimal getImpressionLogSn()
    {
        return this.impressionLogSn;
    }

    public void setImpressionLogSn(BigDecimal impressionLogSn)
    {
        this.impressionLogSn = impressionLogSn;
    }

    @Column(name = "ACCOUNT_LOG_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getAccountLogSn()
    {
        return this.accountLogSn;
    }

    public void setAccountLogSn(BigDecimal accountLogSn)
    {
        this.accountLogSn = accountLogSn;
    }

    @Column(name = "REQUEST_URL", nullable = false, length = 1000)
    public String getRequestUrl()
    {
        return this.requestUrl;
    }

    public void setRequestUrl(String requestUrl)
    {
        this.requestUrl = requestUrl;
    }

    @Column(name = "REQUEST_PARAMS", length = 1500)
    public String getRequestParams()
    {
        return this.requestParams;
    }

    public void setRequestParams(String requestParams)
    {
        this.requestParams = requestParams;
    }

    @Column(name = "RESPONSE_TIME_MILLIS", nullable = false, precision = 22, scale = 0)
    public BigDecimal getResponseTimeMillis()
    {
        return this.responseTimeMillis;
    }

    public void setResponseTimeMillis(BigDecimal responseTimeMillis)
    {
        this.responseTimeMillis = responseTimeMillis;
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
