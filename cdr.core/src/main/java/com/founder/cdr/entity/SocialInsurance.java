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
@Table(name = "SOCIAL_INSURANCE")
public class SocialInsurance implements Serializable
{

    private BigDecimal patientSn;

    private String cardNo;

    private String icNo;

    private String idNo;

    private String personname;

    private String genderCode;

    private Date birthday;

    private String fromhosp;

    private Date fromhospdate;

    private String fundType;

    private BigDecimal isyt;

    private BigDecimal jclevel;

    private BigDecimal hospflag;

    private String persontype;

    private String isinredlist;

    private String isspecifiedhosp;

    private String ischronichosp;

    private BigDecimal personcount;

    private String chronicCode;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String genderName;

    private String fundTypeName;

    private String chronicName;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getPatientSn()
    {
        return this.patientSn;
    }

    public void setPatientSn(BigDecimal patientSn)
    {
        this.patientSn = patientSn;
    }

    @Column(name = "CARD_NO", nullable = false, length = 20)
    public String getCardNo()
    {
        return this.cardNo;
    }

    public void setCardNo(String cardNo)
    {
        this.cardNo = cardNo;
    }

    @Column(name = "IC_NO", nullable = false, length = 20)
    public String getIcNo()
    {
        return this.icNo;
    }

    public void setIcNo(String icNo)
    {
        this.icNo = icNo;
    }

    @Column(name = "ID_NO", length = 18)
    public String getIdNo()
    {
        return this.idNo;
    }

    public void setIdNo(String idNo)
    {
        this.idNo = idNo;
    }

    @Column(name = "PERSONNAME", length = 20)
    public String getPersonname()
    {
        return this.personname;
    }

    public void setPersonname(String personname)
    {
        this.personname = personname;
    }

    @Column(name = "GENDER_CODE", length = 6)
    public String getGenderCode()
    {
        return this.genderCode;
    }

    public void setGenderCode(String genderCode)
    {
        this.genderCode = genderCode;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTHDAY", length = 7)
    public Date getBirthday()
    {
        return this.birthday;
    }

    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    @Column(name = "FROMHOSP", length = 8)
    public String getFromhosp()
    {
        return this.fromhosp;
    }

    public void setFromhosp(String fromhosp)
    {
        this.fromhosp = fromhosp;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "FROMHOSPDATE", length = 7)
    public Date getFromhospdate()
    {
        return this.fromhospdate;
    }

    public void setFromhospdate(Date fromhospdate)
    {
        this.fromhospdate = fromhospdate;
    }

    @Column(name = "FUND_TYPE", length = 4)
    public String getFundType()
    {
        return this.fundType;
    }

    public void setFundType(String fundType)
    {
        this.fundType = fundType;
    }

    @Column(name = "ISYT", precision = 22, scale = 0)
    public BigDecimal getIsyt()
    {
        return this.isyt;
    }

    public void setIsyt(BigDecimal isyt)
    {
        this.isyt = isyt;
    }

    @Column(name = "JCLEVEL", precision = 22, scale = 0)
    public BigDecimal getJclevel()
    {
        return this.jclevel;
    }

    public void setJclevel(BigDecimal jclevel)
    {
        this.jclevel = jclevel;
    }

    @Column(name = "HOSPFLAG", precision = 22, scale = 0)
    public BigDecimal getHospflag()
    {
        return this.hospflag;
    }

    public void setHospflag(BigDecimal hospflag)
    {
        this.hospflag = hospflag;
    }

    @Column(name = "PERSONTYPE", length = 3)
    public String getPersontype()
    {
        return this.persontype;
    }

    public void setPersontype(String persontype)
    {
        this.persontype = persontype;
    }

    @Column(name = "ISINREDLIST", length = 5)
    public String getIsinredlist()
    {
        return this.isinredlist;
    }

    public void setIsinredlist(String isinredlist)
    {
        this.isinredlist = isinredlist;
    }

    @Column(name = "ISSPECIFIEDHOSP", length = 1)
    public String getIsspecifiedhosp()
    {
        return this.isspecifiedhosp;
    }

    public void setIsspecifiedhosp(String isspecifiedhosp)
    {
        this.isspecifiedhosp = isspecifiedhosp;
    }

    @Column(name = "ISCHRONICHOSP", length = 5)
    public String getIschronichosp()
    {
        return this.ischronichosp;
    }

    public void setIschronichosp(String ischronichosp)
    {
        this.ischronichosp = ischronichosp;
    }

    @Column(name = "PERSONCOUNT", precision = 10)
    public BigDecimal getPersoncount()
    {
        return this.personcount;
    }

    public void setPersoncount(BigDecimal personcount)
    {
        this.personcount = personcount;
    }

    @Column(name = "CHRONIC_CODE", length = 50)
    public String getChronicCode()
    {
        return this.chronicCode;
    }

    public void setChronicCode(String chronicCode)
    {
        this.chronicCode = chronicCode;
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

    @Column(name = "GENDER_NAME", length = 40)
    public String getGenderName()
    {
        return this.genderName;
    }

    public void setGenderName(String genderName)
    {
        this.genderName = genderName;
    }

    @Column(name = "FUND_TYPE_NAME", length = 40)
    public String getFundTypeName()
    {
        return this.fundTypeName;
    }

    public void setFundTypeName(String fundTypeName)
    {
        this.fundTypeName = fundTypeName;
    }

    @Column(name = "CHRONIC_NAME", length = 40)
    public String getChronicName()
    {
        return this.chronicName;
    }

    public void setChronicName(String chronicName)
    {
        this.chronicName = chronicName;
    }

}
