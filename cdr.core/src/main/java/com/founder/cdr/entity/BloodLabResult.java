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
@Table(name = "BLOOD_LAB_RESULT")
public class BloodLabResult implements Serializable
{

    private BigDecimal bloodLabResultSn;

    private BigDecimal requestSn;

    private String itemCode;

    private String itemNameCn;

    private String itemNameEn;

    private String itemValue;

    private String itemUnit;

    private String itemResult;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String createby;

    private String updateby;

    private String deleteby;
    // add by li_shenggen begin 2014/11/12
    private Date itemResultDate;
    // end
    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_BLOOD_LAB_RESULT") })
    public BigDecimal getBloodLabResultSn()
    {
        return this.bloodLabResultSn;
    }

    public void setBloodLabResultSn(BigDecimal bloodLabResultSn)
    {
        this.bloodLabResultSn = bloodLabResultSn;
    }

    @Column(name = "REQUEST_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getRequestSn()
    {
        return this.requestSn;
    }

    public void setRequestSn(BigDecimal requestSn)
    {
        this.requestSn = requestSn;
    }

    @Column(name = "ITEM_CODE", length = 32)
    public String getItemCode()
    {
        return this.itemCode;
    }

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    @Column(name = "ITEM_NAME_CN", length = 300)
    public String getItemNameCn()
    {
        return this.itemNameCn;
    }

    public void setItemNameCn(String itemNameCn)
    {
        this.itemNameCn = itemNameCn;
    }

    @Column(name = "ITEM_NAME_EN", length = 150)
    public String getItemNameEn()
    {
        return this.itemNameEn;
    }

    public void setItemNameEn(String itemNameEn)
    {
        this.itemNameEn = itemNameEn;
    }

    @Column(name = "ITEM_VALUE", length = 300)
    public String getItemValue()
    {
        return this.itemValue;
    }

    public void setItemValue(String itemValue)
    {
        this.itemValue = itemValue;
    }

    @Column(name = "ITEM_UNIT", length = 56)
    public String getItemUnit()
    {
        return this.itemUnit;
    }

    public void setItemUnit(String itemUnit)
    {
        this.itemUnit = itemUnit;
    }

    @Column(name = "ITEM_RESULT", length = 300)
    public String getItemResult()
    {
        return this.itemResult;
    }

    public void setItemResult(String itemResult)
    {
        this.itemResult = itemResult;
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
    
    @Temporal(TemporalType.DATE)
    @Column(name = "ITEM_RESULT_DATE")
	public Date getItemResultDate() {
		return itemResultDate;
	}

	public void setItemResultDate(Date itemResultDate) {
		this.itemResultDate = itemResultDate;
	}
}
