package com.yly.cdr.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.founder.fasf.core.util.daohelper.annotation.Generator;
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

@Entity
@Table(name = "WARNING_NOTICE_DETAIL")
public class WarningNoticeDetail
{
    private BigDecimal warningNoticeDetailSn;
    private BigDecimal warningNoticeSn;
    private String noticeType;
    private String settingValue;
    private String monFlag;
    private String tusFlag;
    private String wedFlag;
    private String thuFlag;
    private String friFlag;
    private String satFlag;
    private String sunFlag;
    private Date createTime;
    private Date updateTime;
    private BigDecimal updateCount;
    private String deleteFlag;
    private Date deleteTime;
    
    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_WARNING_NOTICE_DETAIL") })
    public BigDecimal getWarningNoticeDetailSn()
    {
        return warningNoticeDetailSn;
    }
    
    public void setWarningNoticeDetailSn(BigDecimal warningNoticeDetailSn)
    {
        this.warningNoticeDetailSn = warningNoticeDetailSn;
    }

    @Column(name = "WARNING_NOTICE_SN", nullable = false, length = 50)
    public BigDecimal getWarningNoticeSn()
    {
        return warningNoticeSn;
    }
    
    public void setWarningNoticeSn(BigDecimal warningNoticeSn)
    {
        this.warningNoticeSn = warningNoticeSn;
    }
    
    @Column(name = "NOTICE_TYPE", nullable = false, length = 12)
    public String getNoticeType()
    {
        return noticeType;
    }
    
    public void setNoticeType(String noticeType)
    {
        this.noticeType = noticeType;
    }
    
    @Column(name = "SETTING_VALUE", nullable = false, length = 255)
    public String getSettingValue()
    {
        return settingValue;
    }

    public void setSettingValue(String settingValue)
    {
        this.settingValue = settingValue;
    }

    @Column(name = "MON_FLAG", length = 1)
    public String getMonFlag()
    {
        return monFlag;
    }

    public void setMonFlag(String monFlag)
    {
        this.monFlag = monFlag;
    }

    @Column(name = "TUS_FLAG", length = 1)
    public String getTusFlag()
    {
        return tusFlag;
    }

    public void setTusFlag(String tusFlag)
    {
        this.tusFlag = tusFlag;
    }

    @Column(name = "WED_FLAG", length = 1)
    public String getWedFlag()
    {
        return wedFlag;
    }

    public void setWedFlag(String wedFlag)
    {
        this.wedFlag = wedFlag;
    }

    @Column(name = "THU_FLAG", length = 1)
    public String getThuFlag()
    {
        return thuFlag;
    }

    public void setThuFlag(String thuFlag)
    {
        this.thuFlag = thuFlag;
    }

    @Column(name = "FRI_FLAG", length = 1)
    public String getFriFlag()
    {
        return friFlag;
    }

    public void setFriFlag(String friFlag)
    {
        this.friFlag = friFlag;
    }

    @Column(name = "SAT_FLAG", length = 1)
    public String getSatFlag()
    {
        return satFlag;
    }

    public void setSatFlag(String satFlag)
    {
        this.satFlag = satFlag;
    }

    @Column(name = "SUN_FLAG", length = 1)
    public String getSunFlag()
    {
        return sunFlag;
    }

    public void setSunFlag(String sunFlag)
    {
        this.sunFlag = sunFlag;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATE_TIME", nullable = false, length = 7)
    public Date getCreateTime()
    {
        return createTime;
    }


    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATE_TIME", nullable = false, length = 7)
    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    @Column(name = "UPDATE_COUNT", nullable = false, precision = 22, scale = 0)
    public BigDecimal getUpdateCount()
    {
        return updateCount;
    }

    public void setUpdateCount(BigDecimal updateCount)
    {
        this.updateCount = updateCount;
    }

    @Column(name = "DELETE_FLAG", nullable = false, length = 1)
    public String getDeleteFlag()
    {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DELETE_TIME", length = 7)
    public Date getDeleteTime()
    {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
}
