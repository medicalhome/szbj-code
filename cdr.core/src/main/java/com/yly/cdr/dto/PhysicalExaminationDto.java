package com.yly.cdr.dto;

import java.math.BigDecimal;
import java.util.Date;

public class PhysicalExaminationDto
{

    private BigDecimal examinationSn;

    private BigDecimal operationSn;

    private BigDecimal anesthesiaSn;

    private BigDecimal documentSn;

    private String itemCode;

    private String itemName;

    private String itemResult;

    private String itemResultUnit;

    private Date examTime;

    private String examDescription;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private char deleteFlag;

    private Date deleteTime;

    private String operatorId;

    private String operatorName;

    private String anesthesiaDept;

    private String anesthesiaDeptName;

    public String getOperatorId()
    {
        return operatorId;
    }

    public void setOperatorId(String operatorId)
    {
        this.operatorId = operatorId;
    }

    public String getOperatorName()
    {
        return operatorName;
    }

    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }

    public String getAnesthesiaDept()
    {
        return anesthesiaDept;
    }

    public void setAnesthesiaDept(String anesthesiaDept)
    {
        this.anesthesiaDept = anesthesiaDept;
    }

    public BigDecimal getExaminationSn()
    {
        return this.examinationSn;
    }

    public void setExaminationSn(BigDecimal examinationSn)
    {
        this.examinationSn = examinationSn;
    }

    public BigDecimal getOperationSn()
    {
        return this.operationSn;
    }

    public void setOperationSn(BigDecimal operationSn)
    {
        this.operationSn = operationSn;
    }

    public BigDecimal getAnesthesiaSn()
    {
        return this.anesthesiaSn;
    }

    public void setAnesthesiaSn(BigDecimal anesthesiaSn)
    {
        this.anesthesiaSn = anesthesiaSn;
    }

    public BigDecimal getDocumentSn()
    {
        return this.documentSn;
    }

    public void setDocumentSn(BigDecimal documentSn)
    {
        this.documentSn = documentSn;
    }

    public String getItemCode()
    {
        return this.itemCode;
    }

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    public String getItemName()
    {
        return this.itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public String getItemResultUnit()
    {
        return this.itemResultUnit;
    }

    public String getItemResult()
    {
        return itemResult;
    }

    public void setItemResult(String itemResult)
    {
        this.itemResult = itemResult;
    }

    public void setItemResultUnit(String itemResultUnit)
    {
        this.itemResultUnit = itemResultUnit;
    }

    public Date getExamTime()
    {
        return this.examTime;
    }

    public void setExamTime(Date examTime)
    {
        this.examTime = examTime;
    }

    public String getExamDescription()
    {
        return this.examDescription;
    }

    public void setExamDescription(String examDescription)
    {
        this.examDescription = examDescription;
    }

    public Date getCreateTime()
    {
        return this.createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public BigDecimal getUpdateCount()
    {
        return this.updateCount;
    }

    public void setUpdateCount(BigDecimal updateCount)
    {
        this.updateCount = updateCount;
    }

    public char getDeleteFlag()
    {
        return this.deleteFlag;
    }

    public void setDeleteFlag(char deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

    public Date getDeleteTime()
    {
        return this.deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    public String getAnesthesiaDeptName()
    {
        return anesthesiaDeptName;
    }

    public void setAnesthesiaDeptName(String anesthesiaDeptName)
    {
        this.anesthesiaDeptName = anesthesiaDeptName;
    }

}
