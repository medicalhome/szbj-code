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
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

import java.io.Serializable;

@Entity
@Table(name = "CODE_DEPARTMENT")
public class CodeDepartment implements Serializable
{

    private BigDecimal codeDeptId;

    private BigDecimal csId;

    private String code;

    private String name;

    private String pyCode;

    private String seqNo;

    private String versionNo;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String otherName;

    private String openFlag;

    private String mzFlag;

    private String erpCode;

    private String address;

    private String incomeType;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_CODE_DEPARTMENT") })
    public BigDecimal getCodeDeptId()
    {
        return this.codeDeptId;
    }

    public void setCodeDeptId(BigDecimal codeDeptId)
    {
        this.codeDeptId = codeDeptId;
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

    @Column(name = "NAME", length = 60)
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "PY_CODE", length = 38)
    public String getPyCode()
    {
        return this.pyCode;
    }

    public void setPyCode(String pyCode)
    {
        this.pyCode = pyCode;
    }

    @Column(name = "SEQ_NO", length = 32)
    public String getSeqNo()
    {
        return this.seqNo;
    }

    public void setSeqNo(String seqNo)
    {
        this.seqNo = seqNo;
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

    @Column(name = "OTHER_NAME", length = 60)
    public String getOtherName()
    {
        return otherName;
    }

    public void setOtherName(String otherName)
    {
        this.otherName = otherName;
    }

    @Column(name = "OPEN_FLAG", length = 10)
    public String getOpenFlag()
    {
        return openFlag;
    }

    public void setOpenFlag(String openFlag)
    {
        this.openFlag = openFlag;
    }

    @Column(name = "MZ_FLAG", length = 10)
    public String getMzFlag()
    {
        return mzFlag;
    }

    public void setMzFlag(String mzFlag)
    {
        this.mzFlag = mzFlag;
    }

    @Column(name = "ERP_CODE", length = 20)
    public String getErpCode()
    {
        return erpCode;
    }

    public void setErpCode(String erpCode)
    {
        this.erpCode = erpCode;
    }

    @Column(name = "ADDRESS", length = 100)
    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    @Column(name = "INCOME_TYPE", length = 50)
    public String getIncomeType()
    {
        return incomeType;
    }

    public void setIncomeType(String incomeType)
    {
        this.incomeType = incomeType;
    }

}
