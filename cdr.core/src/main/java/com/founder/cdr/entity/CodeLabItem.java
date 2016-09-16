package com.founder.cdr.entity;

import java.io.Serializable;
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
@Table(name = "CODE_LAB_ITEM")
public class CodeLabItem implements Serializable
{

    private BigDecimal codeLabItemId;

    private BigDecimal csId;

    private String code;

    private String name;

    private String itemClass;

    private String performUnit;

    private String vessel;

    private String dosage;

    private String sampleCode;

    private String pyCode;

    private String emergencyFlag;

    private String wbCode;

    private String yzOrderCode;

    private String seqNo;

    private String versionNo;

    private String execUnit;

    private String groupSn;

    private String srFlag;
    
    private String orderTypeMinCode;
    
    private String orderTypeMinName;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_CODE_LAB_ITEM") })
    public BigDecimal getCodeLabItemId()
    {
        return this.codeLabItemId;
    }

    public void setCodeLabItemId(BigDecimal codeLabItemId)
    {
        this.codeLabItemId = codeLabItemId;
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

    @Column(name = "ITEM_CLASS", length = 38)
    public String getItemClass()
    {
        return this.itemClass;
    }

    public void setItemClass(String itemClass)
    {
        this.itemClass = itemClass;
    }

    @Column(name = "PERFORM_UNIT", length = 38)
    public String getPerformUnit()
    {
        return this.performUnit;
    }

    public void setPerformUnit(String performUnit)
    {
        this.performUnit = performUnit;
    }

    @Column(name = "VESSEL", length = 38)
    public String getVessel()
    {
        return this.vessel;
    }

    public void setVessel(String vessel)
    {
        this.vessel = vessel;
    }

    @Column(name = "DOSAGE", length = 38)
    public String getDosage()
    {
        return this.dosage;
    }

    public void setDosage(String dosage)
    {
        this.dosage = dosage;
    }

    @Column(name = "SAMPLE_CODE", length = 38)
    public String getSampleCode()
    {
        return this.sampleCode;
    }

    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
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

    @Column(name = "EMERGENCY_FLAG", length = 38)
    public String getEmergencyFlag()
    {
        return this.emergencyFlag;
    }

    public void setEmergencyFlag(String emergencyFlag)
    {
        this.emergencyFlag = emergencyFlag;
    }

    @Column(name = "WB_CODE", length = 38)
    public String getWbCode()
    {
        return this.wbCode;
    }

    public void setWbCode(String wbCode)
    {
        this.wbCode = wbCode;
    }

    @Column(name = "YZ_ORDER_CODE", length = 38)
    public String getYzOrderCode()
    {
        return this.yzOrderCode;
    }

    public void setYzOrderCode(String yzOrderCode)
    {
        this.yzOrderCode = yzOrderCode;
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

    @Column(name = "EXEC_UNIT", length = 38)
    public String getExecUnit()
    {
        return this.execUnit;
    }

    public void setExecUnit(String execUnit)
    {
        this.execUnit = execUnit;
    }

    @Column(name = "GROUP_SN", length = 38)
    public String getGroupSn()
    {
        return this.groupSn;
    }

    public void setGroupSn(String groupSn)
    {
        this.groupSn = groupSn;
    }

    @Column(name = "SR_FLAG", length = 38)
    public String getSrFlag()
    {
        return this.srFlag;
    }

    public void setSrFlag(String srFlag)
    {
        this.srFlag = srFlag;
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
    
    @Column(name = "ORDER_TYPE_MIN_CODE", length = 12)
    public String getOrderTypeMinCode()
    {
        return orderTypeMinCode;
    }

    public void setOrderTypeMinCode(String orderTypeMinCode)
    {
        this.orderTypeMinCode = orderTypeMinCode;
    }

    @Column(name = "ORDER_TYPE_MIN_NAME", length = 192)
    public String getOrderTypeMinName()
    {
        return orderTypeMinName;
    }

    public void setOrderTypeMinName(String orderTypeMinName)
    {
        this.orderTypeMinName = orderTypeMinName;
    }

}
