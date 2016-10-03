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
@Table(name = "CODE_CHARGE_ITEM")
public class CodeChargeItem
{
    private BigDecimal codeChargeItemId;
    private BigDecimal csId;
    private String itemCode;
    private String itemName;
    private String specification;
    private String seqNo;
    private String versionNo;
    private String pyCode;
    private Date createTime;
    private Date updateTime;
    private BigDecimal updateCount;
    private String deleteFlag;
    private Date deleteTime;
    
    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_CODE_CHARGE_ITEM") })
    public BigDecimal getCodeChargeItemId()
    {
        return codeChargeItemId;
    }
    public void setCodeChargeItemId(BigDecimal codeChargeItemId)
    {
        this.codeChargeItemId = codeChargeItemId;
    }
    
    @Column(name = "CS_ID", nullable = false, precision = 22, scale = 0)
    public BigDecimal getCsId()
    {
        return csId;
    }
    public void setCsId(BigDecimal csId)
    {
        this.csId = csId;
    }

    @Column(name = "ITEM_CODE", length = 12)
    public String getItemCode()
    {
        return itemCode;
    }
    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }
    
    @Column(name = "ITEM_NAME", length = 360)
    public String getItemName()
    {
        return itemName;
    }
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }
    
    @Column(name = "SPECIFICATION", length = 38)
    public String getSpecification()
    {
        return specification;
    }
    public void setSpecification(String specification)
    {
        this.specification = specification;
    }
    
    @Column(name = "SEQ_NO", length = 32)
    public String getSeqNo()
    {
        return seqNo;
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
    
    @Column(name = "PY_CODE", length = 38)
    public String getPyCode()
    {
        return pyCode;
    }
    public void setPyCode(String pyCode)
    {
        this.pyCode = pyCode;
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
