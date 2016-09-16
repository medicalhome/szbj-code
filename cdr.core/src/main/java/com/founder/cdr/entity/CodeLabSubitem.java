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

import com.founder.fasf.core.util.daohelper.annotation.Generator;
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

import java.io.Serializable;

@Entity
@Table(name = "CODE_LAB_SUBITEM")
public class CodeLabSubitem implements Serializable
{

    private BigDecimal codeLabSubitemId;

    private BigDecimal csId;

    private String code;

    private String name;

    private String pyCode;

    private String fullName;

    private String chineseName;

    private String decimals;

    private String units;

    private String maleRefer;

    private String femaleRefer;

    private String clinicalMeaning;

    private String operateRemark;

    private String appRange;

    private String versionNo;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String seqNo;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_CODE_LAB_SUBITEM") })
    public BigDecimal getCodeLabSubitemId()
    {
        return codeLabSubitemId;
    }

    public void setCodeLabSubitemId(BigDecimal codeLabSubitemId)
    {
        this.codeLabSubitemId = codeLabSubitemId;
    }

    @Column(name = "CS_ID", nullable = false, precision = 22, scale = 0)
    public BigDecimal getCsId()
    {
        return this.csId;
    }

    public void setCsId(BigDecimal csId)
    {
        this.csId = csId;
    }

    @Column(name = "CODE", length = 38)
    public String getCode()
    {
        return this.code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    @Column(name = "NAME", length = 100)
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "PY_CODE", length = 80)
    public String getPyCode()
    {
        return this.pyCode;
    }

    public void setPyCode(String pyCode)
    {
        this.pyCode = pyCode;
    }

    @Column(name = "FULL_NAME", length = 160)
    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    @Column(name = "CHINESE_NAME", length = 160)
    public String getChineseName()
    {
        return chineseName;
    }

    public void setChineseName(String chineseName)
    {
        this.chineseName = chineseName;
    }

    @Column(name = "DECIMALS", length = 10)
    public String getDecimals()
    {
        return decimals;
    }

    public void setDecimals(String decimals)
    {
        this.decimals = decimals;
    }

    @Column(name = "UNITS", length = 80)
    public String getUnits()
    {
        return units;
    }

    public void setUnits(String units)
    {
        this.units = units;
    }

    @Column(name = "MALE_REFER", length = 2000)
    public String getMaleRefer()
    {
        return maleRefer;
    }

    public void setMaleRefer(String maleRefer)
    {
        this.maleRefer = maleRefer;
    }

    @Column(name = "FEMALE_REFER", length = 2000)
    public String getFemaleRefer()
    {
        return femaleRefer;
    }

    public void setFemaleRefer(String femaleRefer)
    {
        this.femaleRefer = femaleRefer;
    }

    @Column(name = "CLINICAL_MEANING")
    public String getClinicalMeaning()
    {
        return clinicalMeaning;
    }

    public void setClinicalMeaning(String clinicalMeaning)
    {
        this.clinicalMeaning = clinicalMeaning;
    }

    @Column(name = "OPERATE_REMARK")
    public String getOperateRemark()
    {
        return operateRemark;
    }

    public void setOperateRemark(String operateRemark)
    {
        this.operateRemark = operateRemark;
    }

    @Column(name = "APP_RANGE", length = 10)
    public String getAppRange()
    {
        return appRange;
    }

    public void setAppRange(String appRange)
    {
        this.appRange = appRange;
    }

    @Column(name = "VERSION_NO", length = 32)
    public String getVersionNo()
    {
        return versionNo;
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

    @Column(name = "SEQ_NO", length = 38)
    public String getSeqNo()
    {
        return seqNo;
    }

    public void setSeqNo(String seqNo)
    {
        this.seqNo = seqNo;
    }

}
