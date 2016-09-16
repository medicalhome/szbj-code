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
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

import java.io.Serializable;

@Entity
@Table(name = "CODE_ICD")
public class CodeIcd implements Serializable
{

    private BigDecimal codeIcdId;

    private BigDecimal csId;

    private String code;

    private String name;

    private String diseaseReason;

    private String deathReason;

    private String pyCode;

    private BigDecimal flag;

    private String addCode;

    private String extraFlag;

    private String icdVer;

    private String icdCode;

    private String deptNo;

    private String source;

    private String seqNo;

    private String versionNo;
    
    // Author :jia_yanqing
    // Date : 2013/10/11 17:30
    // [BUG]0037993 ADD BEGIN
    private String standardFlg;
    // [BUG]0037993 ADD END

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_CODE_ICD") })
    public BigDecimal getCodeIcdId()
    {
        return this.codeIcdId;
    }

    public void setCodeIcdId(BigDecimal codeIcdId)
    {
        this.codeIcdId = codeIcdId;
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

    @Column(name = "NAME", length = 150)
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "DISEASE_REASON", length = 49)
    public String getDiseaseReason()
    {
        return this.diseaseReason;
    }

    public void setDiseaseReason(String diseaseReason)
    {
        this.diseaseReason = diseaseReason;
    }

    @Column(name = "DEATH_REASON", length = 39)
    public String getDeathReason()
    {
        return this.deathReason;
    }

    public void setDeathReason(String deathReason)
    {
        this.deathReason = deathReason;
    }

    @Column(name = "PY_CODE", length = 300)
    public String getPyCode()
    {
        return this.pyCode;
    }

    public void setPyCode(String pyCode)
    {
        this.pyCode = pyCode;
    }

    @Column(name = "FLAG", precision = 22, scale = 0)
    public BigDecimal getFlag()
    {
        return this.flag;
    }

    public void setFlag(BigDecimal flag)
    {
        this.flag = flag;
    }

    @Column(name = "ADD_CODE", length = 35)
    public String getAddCode()
    {
        return this.addCode;
    }

    public void setAddCode(String addCode)
    {
        this.addCode = addCode;
    }

    @Column(name = "EXTRA_FLAG", length = 36)
    public String getExtraFlag()
    {
        return this.extraFlag;
    }

    public void setExtraFlag(String extraFlag)
    {
        this.extraFlag = extraFlag;
    }

    @Column(name = "ICD_VER", length = 64)
    public String getIcdVer()
    {
        return this.icdVer;
    }

    public void setIcdVer(String icdVer)
    {
        this.icdVer = icdVer;
    }

    @Column(name = "ICD_CODE", length = 35)
    public String getIcdCode()
    {
        return this.icdCode;
    }

    public void setIcdCode(String icdCode)
    {
        this.icdCode = icdCode;
    }

    @Column(name = "DEPT_NO", length = 38)
    public String getDeptNo()
    {
        return this.deptNo;
    }

    public void setDeptNo(String deptNo)
    {
        this.deptNo = deptNo;
    }

    @Column(name = "SOURCE", length = 38)
    public String getSource()
    {
        return this.source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    @Column(name = "SEQ_NO", length = 38)
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
    
    // Author :jia_yanqing
    // Date : 2013/10/11 17:30
    // [BUG]0037993 ADD BEGIN
    @Column(name = "STANDARD_FLG", length = 2)
    public String getStandardFlg()
    {
        return standardFlg;
    }

    public void setStandardFlg(String standardFlg)
    {
        this.standardFlg = standardFlg;
    }
    // [BUG]0037993 ADD END

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
