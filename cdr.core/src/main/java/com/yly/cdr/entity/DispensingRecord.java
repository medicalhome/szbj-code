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
@Table(name = "DISPENSING_RECORD")
public class DispensingRecord implements Serializable
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

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String recordNo;

    private String execDeptName;
    
    private String createby;

    private String updateby;

    private String deleteby;
    // $Author:tong_meng
    // $Date:2013/12/03 11:00
    // [BUG]0040270 ADD BEGIN
    private String orgCode;
    private String orgName;
    // [BUG]0040270 ADD END

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_DISPENDING") })
    public BigDecimal getDispensingSn()
    {
        return this.dispensingSn;
    }

    public void setDispensingSn(BigDecimal dispensingSn)
    {
        this.dispensingSn = dispensingSn;
    }

    @Column(name = "VISIT_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getVisitSn()
    {
        return this.visitSn;
    }

    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
    }

    @Column(name = "DISPENSER", length = 12)
    public String getDispenser()
    {
        return this.dispenser;
    }

    public void setDispenser(String dispenser)
    {
        this.dispenser = dispenser;
    }

    @Column(name = "DISPENSER_NAME", length = 40)
    public String getDispenserName()
    {
        return this.dispenserName;
    }

    public void setDispenserName(String dispenserName)
    {
        this.dispenserName = dispenserName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DISPENSE_TIME", length = 7)
    public Date getDispenseTime()
    {
        return this.dispenseTime;
    }

    public void setDispenseTime(Date dispenseTime)
    {
        this.dispenseTime = dispenseTime;
    }

    @Column(name = "SUPPLY_PERSON", length = 12)
    public String getSupplyPerson()
    {
        return this.supplyPerson;
    }

    public void setSupplyPerson(String supplyPerson)
    {
        this.supplyPerson = supplyPerson;
    }

    @Column(name = "SUPPLY_PERSON_NAME", length = 192)
    public String getSupplyPersonName()
    {
        return this.supplyPersonName;
    }

    public void setSupplyPersonName(String supplyPersonName)
    {
        this.supplyPersonName = supplyPersonName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "SUPPLY_TIME", length = 7)
    public Date getSupplyTime()
    {
        return this.supplyTime;
    }

    public void setSupplyTime(Date supplyTime)
    {
        this.supplyTime = supplyTime;
    }

    @Column(name = "EXEC_DEPT", length = 42)
    public String getExecDept()
    {
        return this.execDept;
    }

    public void setExecDept(String execDept)
    {
        this.execDept = execDept;
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

    @Column(name = "RECORD_NO", length = 64)
    public String getRecordNo()
    {
        return this.recordNo;
    }

    public void setRecordNo(String recordNo)
    {
        this.recordNo = recordNo;
    }

    @Column(name = "EXEC_DEPT_NAME", length = 40)
    public String getExecDeptName()
    {
        return this.execDeptName;
    }

    public void setExecDeptName(String execDeptName)
    {
        this.execDeptName = execDeptName;
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
