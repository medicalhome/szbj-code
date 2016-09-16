package com.founder.cdr.dto.drug;

import java.math.BigDecimal;
import java.util.Date;

import com.founder.cdr.dto.CommonParameters;

/**
 * 
 * [FUN]V05-101药物医嘱列表
 * @version 1.0, 2012/03/12  
 * @author 张彬
 * @since 1.0
 * 
 */
public class DispensingRecordDto
{

    private BigDecimal dispensingSn;

    private BigDecimal visitSn;

    private String dispenser;

    private String dispenserName;

    private Date dispenseTime;

    private String supplyPerson;

    private String supplyPersonName;

    private Date supplyTime;

    private String execDept;

    private String execDeptName;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String recordNo;

    private String batchNo;

    private String dispensingQuantity;
    
    // $Author:chunlin jing
    // $Date:2013/6/26 15:00
    // $[BUG]0034107 ADD BEGIN
    // 添加领量单位, 并添加上对应的get/set方法
    private String dispensingUnit;
    // $[BUG]0034107 ADD END
    
    public BigDecimal getDispensingSn()
    {
        return dispensingSn;
    }

    public void setDispensingSn(BigDecimal dispensingSn)
    {
        this.dispensingSn = dispensingSn;
    }

    public BigDecimal getVisitSn()
    {
        return visitSn;
    }

    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
    }

    public String getDispenser()
    {
        return dispenser;
    }

    public void setDispenser(String dispenser)
    {
        this.dispenser = dispenser;
    }

    public String getDispenserName()
    {
        return dispenserName;
    }

    public void setDispenserName(String dispenserName)
    {
        this.dispenserName = dispenserName;
    }

    public Date getDispenseTime()
    {
        return dispenseTime;
    }

    public void setDispenseTime(Date dispenseTime)
    {
        this.dispenseTime = dispenseTime;
    }

    public String getSupplyPerson()
    {
        return supplyPerson;
    }

    public void setSupplyPerson(String supplyPerson)
    {
        this.supplyPerson = supplyPerson;
    }

    public String getSupplyPersonName()
    {
        return supplyPersonName;
    }

    public void setSupplyPersonName(String supplyPersonName)
    {
        this.supplyPersonName = supplyPersonName;
    }

    public Date getSupplyTime()
    {
        return supplyTime;
    }

    public void setSupplyTime(Date supplyTime)
    {
        this.supplyTime = supplyTime;
    }

    public String getExecDept()
    {
        return execDept;
    }

    public void setExecDept(String execDept)
    {
        this.execDept = execDept;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public BigDecimal getUpdateCount()
    {
        return updateCount;
    }

    public void setUpdateCount(BigDecimal updateCount)
    {
        this.updateCount = updateCount;
    }

    public String getDeleteFlag()
    {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

    public Date getDeleteTime()
    {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    public String getRecordNo()
    {
        return recordNo;
    }

    public void setRecordNo(String recordNo)
    {
        this.recordNo = recordNo;
    }

    public String getBatchNo()
    {
        return batchNo;
    }

    public void setBatchNo(String batchNo)
    {
        this.batchNo = batchNo;
    }

    public String getExecDeptName()
    {
        return execDeptName;
    }

    public void setExecDeptName(String execDeptName)
    {
        this.execDeptName = execDeptName;
    }

    public String getDispensingQuantity()
    {
        return dispensingQuantity;
    }

    public void setDispensingQuantity(String dispensingQuantity)
    {
        this.dispensingQuantity = dispensingQuantity;
    }

    // $Author:chunlin jing
    // $Date:2013/6/26 15:00
    // $[BUG]0034107 ADD BEGIN
	public String getDispensingUnit() {
		return dispensingUnit;
	}

	public void setDispensingUnit(String dispensingUnit) {
		this.dispensingUnit = dispensingUnit;
	}
	// $[BUG]0034107 ADD END
    
}
