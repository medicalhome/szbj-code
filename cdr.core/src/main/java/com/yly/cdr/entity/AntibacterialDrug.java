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

import java.io.Serializable;

@Entity
@Table(name = "ANTIBACTERIAL_DRUG")
public class AntibacterialDrug implements Serializable
{

    private BigDecimal antibacterialDrugSn;

    private String itemCode;

    private BigDecimal labResultCompositeItemSn;

    private String drugNameCn;

    private String drugNameEn;

    private String sensitivity;

    private String mic;

    private String kb;

    private String breakpoint;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String drugCode;

    private String displayOrder;

    private String createby;

    private String updateby;

    private String deleteby;
    @Id
    public BigDecimal getAntibacterialDrugSn()
    {
        return this.antibacterialDrugSn;
    }

    public void setAntibacterialDrugSn(BigDecimal antibacterialDrugSn)
    {
        this.antibacterialDrugSn = antibacterialDrugSn;
    }

    @Column(name = "ITEM_CODE", nullable = false, length = 1000)
    public String getItemCode()
    {
        return this.itemCode;
    }

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    @Column(name = "LAB_RESULT_COMPOSITE_ITEM_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getLabResultCompositeItemSn()
    {
        return this.labResultCompositeItemSn;
    }

    public void setLabResultCompositeItemSn(BigDecimal labResultCompositeItemSn)
    {
        this.labResultCompositeItemSn = labResultCompositeItemSn;
    }

    @Column(name = "DRUG_NAME_CN", length = 300)
    public String getDrugNameCn()
    {
        return this.drugNameCn;
    }

    public void setDrugNameCn(String drugNameCn)
    {
        this.drugNameCn = drugNameCn;
    }

    @Column(name = "DRUG_NAME_EN", length = 300)
    public String getDrugNameEn()
    {
        return this.drugNameEn;
    }

    public void setDrugNameEn(String drugNameEn)
    {
        this.drugNameEn = drugNameEn;
    }

    @Column(name = "SENSITIVITY", length = 50)
    public String getSensitivity()
    {
        return this.sensitivity;
    }

    public void setSensitivity(String sensitivity)
    {
        this.sensitivity = sensitivity;
    }

    @Column(name = "MIC", length = 50)
    public String getMic()
    {
        return this.mic;
    }

    public void setMic(String mic)
    {
        this.mic = mic;
    }

    @Column(name = "KB", length = 50)
    public String getKb()
    {
        return this.kb;
    }

    public void setKb(String kb)
    {
        this.kb = kb;
    }

    @Column(name = "BREAKPOINT", length = 100)
    public String getBreakpoint()
    {
        return this.breakpoint;
    }

    public void setBreakpoint(String breakpoint)
    {
        this.breakpoint = breakpoint;
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

    @Column(name = "DRUG_CODE", length = 400)
    public String getDrugCode()
    {
        return this.drugCode;
    }

    public void setDrugCode(String drugCode)
    {
        this.drugCode = drugCode;
    }

    @Column(name = "DISPLAY_ORDER", nullable = false, precision = 22, scale = 0)
    public String getDisplayOrder()
    {
        return this.displayOrder;
    }

    public void setDisplayOrder(String displayOrder)
    {
        this.displayOrder = displayOrder;
    }
    @Column(name = "CREATEBY", length = 12)
    public String getCreateby()
    {
        return this.createby;
    }

    public void setCreateby(String createby)
    {
        this.createby = createby;
    }
    
    @Column(name = "UPDATEBY", length = 12)
    public String getUpdateby()
    {
        return this.updateby;
    }

    public void setUpdateby(String updateby)
    {
        this.updateby = updateby;
    }
    
    @Column(name = "DELETEBY", length = 12)
    public String getDeleteby()
    {
        return this.deleteby;
    }

    public void setDeleteby(String deleteby)
    {
        this.deleteby = deleteby;
    }

}
