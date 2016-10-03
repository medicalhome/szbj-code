package com.yly.cdr.entity;

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
@Table(name = "PHYSICAL_EXAMINATION")
public class PhysicalExamination implements Serializable
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

    private String deleteFlag;

    private Date deleteTime;

    private String itemTypeCode;

    private String itemTypeName;

    private String bloodPressureFlag;
    
    private String orgCode;
    
    private String orgName;

    private String createby;

    private String updateby;

    private String deleteby;
    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_PHYSICAL_EXAM") })
    public BigDecimal getExaminationSn()
    {
        return this.examinationSn;
    }

    public void setExaminationSn(BigDecimal examinationSn)
    {
        this.examinationSn = examinationSn;
    }

    @Column(name = "OPERATION_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getOperationSn()
    {
        return this.operationSn;
    }

    public void setOperationSn(BigDecimal operationSn)
    {
        this.operationSn = operationSn;
    }

    @Column(name = "ANESTHESIA_SN", precision = 22, scale = 0)
    public BigDecimal getAnesthesiaSn()
    {
        return this.anesthesiaSn;
    }

    public void setAnesthesiaSn(BigDecimal anesthesiaSn)
    {
        this.anesthesiaSn = anesthesiaSn;
    }

    @Column(name = "DOCUMENT_SN", precision = 22, scale = 0)
    public BigDecimal getDocumentSn()
    {
        return this.documentSn;
    }

    public void setDocumentSn(BigDecimal documentSn)
    {
        this.documentSn = documentSn;
    }

    @Column(name = "ITEM_CODE", length = 12)
    public String getItemCode()
    {
        return this.itemCode;
    }

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    @Column(name = "ITEM_NAME", length = 64)
    public String getItemName()
    {
        return this.itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    @Column(name = "ITEM_RESULT", length = 40)
    public String getItemResult()
    {
        return this.itemResult;
    }

    public void setItemResult(String itemResult)
    {
        this.itemResult = itemResult;
    }

    @Column(name = "ITEM_RESULT_UNIT", length = 32)
    public String getItemResultUnit()
    {
        return this.itemResultUnit;
    }

    public void setItemResultUnit(String itemResultUnit)
    {
        this.itemResultUnit = itemResultUnit;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "EXAM_TIME", length = 7)
    public Date getExamTime()
    {
        return this.examTime;
    }

    public void setExamTime(Date examTime)
    {
        this.examTime = examTime;
    }

    @Column(name = "EXAM_DESCRIPTION", length = 256)
    public String getExamDescription()
    {
        return this.examDescription;
    }

    public void setExamDescription(String examDescription)
    {
        this.examDescription = examDescription;
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

    @Column(name = "ITEM_TYPE_CODE", length = 12)
    public String getItemTypeCode()
    {
        return this.itemTypeCode;
    }

    public void setItemTypeCode(String itemTypeCode)
    {
        this.itemTypeCode = itemTypeCode;
    }

    @Column(name = "ITEM_TYPE_NAME", length = 56)
    public String getItemTypeName()
    {
        return this.itemTypeName;
    }

    public void setItemTypeName(String itemTypeName)
    {
        this.itemTypeName = itemTypeName;
    }

    @Column(name = "BLOOD_PRESSURE_FLAG", length = 1)
    public String getBloodPressureFlag()
    {
        return this.bloodPressureFlag;
    }

    public void setBloodPressureFlag(String bloodPressureFlag)
    {
        this.bloodPressureFlag = bloodPressureFlag;
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
    
    @Column(name = "ORG_CODE", length = 50)
    public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

    @Column(name = "ORG_NAME", length = 50)
    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

}
