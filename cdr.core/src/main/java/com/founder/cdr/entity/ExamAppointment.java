package com.founder.cdr.entity;

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
@Table(name="EXAM_APPOINTMENT")
public class ExamAppointment
{
    private BigDecimal ExamAppointmentSn;
    private BigDecimal visitSn;
    private BigDecimal orderSn;
    private BigDecimal patientSn;
    private String appointmentNo;
    private Date appointmentTime;
    private String patientDomain;
    private String patientLid;
    private String appDeviceCode; 
    private String appDeviceName; 
    private String appPerformerCode; 
    private String appPerformerName;
    private String appOrderNo;
    private String execDeptCode; 
    private String execDeptName;
    private Date createTime;
    private Date updateTime;
    private BigDecimal updateCount;
    private String deleteFlag;
    private Date deleteTime;
    private String createby;
    private String updateby;
    private String deleteby;
    private String orgCode;
    private String orgName;
    
    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_EXAM_APPOINTMENT") })
    public BigDecimal getExamAppointmentSn()
    {
        return ExamAppointmentSn;
    }
    public void setExamAppointmentSn(BigDecimal examAppointmentSn)
    {
        ExamAppointmentSn = examAppointmentSn;
    }
    @Column(name="VISIT_SN", nullable=false, precision=22, scale=0)
    public BigDecimal getVisitSn()
    {
        return visitSn;
    }
    public void setVisitSn(BigDecimal visitSn)
    {
        this.visitSn = visitSn;
    }
    @Column(name="ORDER_SN", nullable=false, precision=22, scale=0)
    public BigDecimal getOrderSn()
    {
        return orderSn;
    }
    public void setOrderSn(BigDecimal orderSn)
    {
        this.orderSn = orderSn;
    }
    @Column(name="PATIENT_SN", nullable=false, precision=22, scale=0)
    public BigDecimal getPatientSn()
    {
        return patientSn;
    }
    public void setPatientSn(BigDecimal patientSn)
    {
        this.patientSn = patientSn;
    }
    @Column(name="APPOINTMENT_NO", length=20)
    public String getAppointmentNo()
    {
        return appointmentNo;
    }
    public void setAppointmentNo(String appointmentNo)
    {
        this.appointmentNo = appointmentNo;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="APPOINTMENT_TIME", nullable=false, length=7)
    public Date getAppointmentTime()
    {
        return appointmentTime;
    }
    public void setAppointmentTime(Date appointmentTime)
    {
        this.appointmentTime = appointmentTime;
    }
    @Column(name="PATIENT_DOMAIN", nullable=false, length=2)
    public String getPatientDomain()
    {
        return patientDomain;
    }
    public void setPatientDomain(String patientDomain)
    {
        this.patientDomain = patientDomain;
    }
    @Column(name="PATIENT_LID", nullable=false, length=12)
    public String getPatientLid()
    {
        return patientLid;
    }
    public void setPatientLid(String patientLid)
    {
        this.patientLid = patientLid;
    }
    @Column(name="APP_DEVICE_CODE", length=12)
    public String getAppDeviceCode()
    {
        return appDeviceCode;
    }
    public void setAppDeviceCode(String appDeviceCode)
    {
        this.appDeviceCode = appDeviceCode;
    }
    @Column(name="APP_DEVICE_NAME", length=64)
    public String getAppDeviceName()
    {
        return appDeviceName;
    }
    public void setAppDeviceName(String appDeviceName)
    {
        this.appDeviceName = appDeviceName;
    }
    @Column(name="APP_PERFORMER_CODE", nullable=false, length=12)
    public String getAppPerformerCode()
    {
        return appPerformerCode;
    }
    public void setAppPerformerCode(String appPerformerCode)
    {
        this.appPerformerCode = appPerformerCode;
    }
    @Column(name="APP_PERFORMER_NAME", nullable=false, length=64)
    public String getAppPerformerName()
    {
        return appPerformerName;
    }
    public void setAppPerformerName(String appPerformerName)
    {
        this.appPerformerName = appPerformerName;
    }
    @Column(name="APP_ORDER_NO", length=20)
    public String getAppOrderNo()
    {
        return appOrderNo;
    }
    public void setAppOrderNo(String appOrderNo)
    {
        this.appOrderNo = appOrderNo;
    }
    @Column(name="EXEC_DEPT_CODE", nullable=false, length=12)
    public String getExecDeptCode()
    {
        return execDeptCode;
    }
    public void setExecDeptCode(String execDeptCode)
    {
        this.execDeptCode = execDeptCode;
    }
    @Column(name="EXEC_DEPT_NAME",length=192)
    public String getExecDeptName()
    {
        return execDeptName;
    }
    public void setExecDeptName(String execDeptName)
    {
        this.execDeptName = execDeptName;
    }
        @Temporal(TemporalType.DATE)
    @Column(name="CREATE_TIME", nullable=false, length=7)
    public Date getCreateTime() 
    {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) 
    {
        this.createTime = createTime;
    }
    
        @Temporal(TemporalType.DATE)
    @Column(name="UPDATE_TIME", nullable=false, length=7)
    public Date getUpdateTime() 
    {
        return this.updateTime;
    }
    
    public void setUpdateTime(Date updateTime) 
    {
        this.updateTime = updateTime;
    }
    
        
    @Column(name="UPDATE_COUNT", nullable=false, precision=22, scale=0)
    public BigDecimal getUpdateCount() 
    {
        return this.updateCount;
    }
    
    public void setUpdateCount(BigDecimal updateCount) 
    {
        this.updateCount = updateCount;
    }
    
        
    @Column(name="DELETE_FLAG", nullable=false, length=1)
    public String getDeleteFlag() 
    {
        return this.deleteFlag;
    }
    
    public void setDeleteFlag(String deleteFlag) 
    {
        this.deleteFlag = deleteFlag;
    }
    
        @Temporal(TemporalType.DATE)
    @Column(name="DELETE_TIME", length=7)
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
