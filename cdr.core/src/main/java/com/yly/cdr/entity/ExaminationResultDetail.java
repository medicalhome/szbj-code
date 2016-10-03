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
@Table(name = "EXAMINATION_RESULT_DETAIL")
public class ExaminationResultDetail
{
    private BigDecimal examinationResultDetailSn;
    private BigDecimal itemSn;
    private BigDecimal examResultProcedureSn;
    private String parentExamResultDetailSn;
    private String itemClass;
    private String itemCode;
    private String itemName;
    private String itemValue;
    private Date createTime;
    private Date updateTime;
    private BigDecimal updateCount;
    private String deleteFlag;
    private Date deleteTime;
    private String createby;
    private String updateby;
    private String deleteby;
    private String text;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_EXAM_RESULT_ITEM") })
    public BigDecimal getExaminationResultDetailSn()
    {
        return examinationResultDetailSn;
    }

    public void setExaminationResultDetailSn(BigDecimal examinationResultDetailSn)
    {
        this.examinationResultDetailSn = examinationResultDetailSn;
    }
    
    @Column(name = "ITEM_SN", nullable = false)
    public BigDecimal getItemSn()
    {
        return itemSn;
    }

    public void setItemSn(BigDecimal itemSn)
    {
        this.itemSn = itemSn;
    }
    
    @Column(name = "EXAM_RESULT_PROCEDURE_SN", nullable = false)
    public BigDecimal getExamResultProcedureSn()
    {
        return examResultProcedureSn;
    }

    public void setExamResultProcedureSn(BigDecimal examResultProcedureSn)
    {
        this.examResultProcedureSn = examResultProcedureSn;
    }

    @Column(name = "PARENT_EXAM_RESULT_DETAIL_SN", length = 12)
    public String getParentExamResultDetailSn()
    {
        return parentExamResultDetailSn;
    }

    public void setParentExamResultDetailSn(String parentExamResultDetailSn)
    {
        this.parentExamResultDetailSn = parentExamResultDetailSn;
    }

    @Column(name = "ITEM_CLASS", length = 32)
    public String getItemClass()
    {
        return itemClass;
    }
    
    public void setItemClass(String itemClass)
    {
        this.itemClass = itemClass;
    }
    
    @Column(name = "ITEM_CODE", length = 50)
    public String getItemCode()
    {
        return itemCode;
    }

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    @Column(name = "ITEM_NAME", length = 50)
    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    @Column(name = "ITEM_VALUE", columnDefinition = "CLOB")
    public String getItemValue()
    {
        return itemValue;
    }

    public void setItemValue(String itemValue)
    {
        this.itemValue = itemValue;
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

    @Column(name = "TEXT", length = 32)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
