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
@Table(name = "HERBAL_FORMULA")
public class HerbalFormula implements Serializable
{

    private BigDecimal formulaSn;

    private BigDecimal orderSn;

    private String herbCode;

    private String herbName;

    private String approvedHerbName;

    private BigDecimal quantity;

    private String unit;

    private String numUnconcernedFlag;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String serialNo;

    private String decoctionMethodCode;

    private String decoctionMethodName;
    
    private String createby;
    
    private String updateby;
    
    private String deleteby;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_HERBAL_FORMULA") })
    public BigDecimal getFormulaSn()
    {
        return this.formulaSn;
    }

    public void setFormulaSn(BigDecimal formulaSn)
    {
        this.formulaSn = formulaSn;
    }

    @Column(name = "ORDER_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getOrderSn()
    {
        return this.orderSn;
    }

    public void setOrderSn(BigDecimal orderSn)
    {
        this.orderSn = orderSn;
    }

    @Column(name = "HERB_CODE", length = 12)
    public String getHerbCode()
    {
        return this.herbCode;
    }

    public void setHerbCode(String herbCode)
    {
        this.herbCode = herbCode;
    }

    @Column(name = "HERB_NAME", nullable = false, length = 300)
    public String getHerbName()
    {
        return this.herbName;
    }

    public void setHerbName(String herbName)
    {
        this.herbName = herbName;
    }

    @Column(name = "APPROVED_HERB_NAME", length = 180)
    public String getApprovedHerbName()
    {
        return approvedHerbName;
    }

    public void setApprovedHerbName(String approvedHerbName)
    {
        this.approvedHerbName = approvedHerbName;
    }

    @Column(name = "QUANTITY", nullable = false, precision = 9, scale = 4)
    public BigDecimal getQuantity()
    {
        return this.quantity;
    }

    public void setQuantity(BigDecimal quantity)
    {
        this.quantity = quantity;
    }

    @Column(name = "UNIT", length = 192)
    public String getUnit()
    {
        return this.unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    @Column(name = "NUM_UNCONCERNED_FLAG", length = 1)
    public String getNumUnconcernedFlag()
    {
        return this.numUnconcernedFlag;
    }

    public void setNumUnconcernedFlag(String numUnconcernedFlag)
    {
        this.numUnconcernedFlag = numUnconcernedFlag;
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

    @Column(name = "SERIAL_NO", length = 20)
    public String getSerialNo()
    {
        return this.serialNo;
    }

    public void setSerialNo(String serialNo)
    {
        this.serialNo = serialNo;
    }

    @Column(name = "DECOCTION_METHOD_CODE", length = 12)
    public String getDecoctionMethodCode()
    {
        return this.decoctionMethodCode;
    }

    public void setDecoctionMethodCode(String decoctionMethodCode)
    {
        this.decoctionMethodCode = decoctionMethodCode;
    }

    @Column(name = "DECOCTION_METHOD_NAME", length = 768)
    public String getDecoctionMethodName()
    {
        return this.decoctionMethodName;
    }

    public void setDecoctionMethodName(String decoctionMethodName)
    {
        this.decoctionMethodName = decoctionMethodName;
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
