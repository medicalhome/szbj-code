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
@Table(name = "SYSTEM_ACCOUNT")
public class SystemAccount implements Serializable
{

    private BigDecimal accountSn;

    private String userId;

    private String userName;

    private String passwd;

    private BigDecimal sex;

    private String email;

    private String mobile;

    private String deptCode;

    private String groupCd;

    private String memo;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String status;

    private String employmentStatusCd;

    private String employeeTypeCd;

    private String jobCategory;

    private Date serviceStartDate;
    
    private String initPwd;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_SYSTEM_ACCOUNT") })
    public BigDecimal getAccountSn()
    {
        return this.accountSn;
    }

    public void setAccountSn(BigDecimal accountSn)
    {
        this.accountSn = accountSn;
    }

    @Column(name = "USER_ID", nullable = false, length = 12)
    public String getUserId()
    {
        return this.userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    @Column(name = "USER_NAME", length = 56)
    public String getUserName()
    {
        return this.userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    @Column(name = "PASSWD", nullable = false, length = 50)
    public String getPasswd()
    {
        return this.passwd;
    }

    public void setPasswd(String passwd)
    {
        this.passwd = passwd;
    }

    @Column(name = "SEX", precision = 22, scale = 0)
    public BigDecimal getSex()
    {
        return this.sex;
    }

    public void setSex(BigDecimal sex)
    {
        this.sex = sex;
    }

    @Column(name = "EMAIL", length = 100)
    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Column(name = "MOBILE", length = 32)
    public String getMobile()
    {
        return this.mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    @Column(name = "DEPT_CODE", length = 50)
    public String getDeptCode()
    {
        return this.deptCode;
    }

    public void setDeptCode(String deptCode)
    {
        this.deptCode = deptCode;
    }

    @Column(name = "GROUP_CD", length = 32)
    public String getGroupCd()
    {
        return this.groupCd;
    }

    public void setGroupCd(String groupCd)
    {
        this.groupCd = groupCd;
    }

    @Column(name = "MEMO")
    public String getMemo()
    {
        return this.memo;
    }

    public void setMemo(String memo)
    {
        this.memo = memo;
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

    @Column(name = "STATUS", length = 2)
    public String getStatus()
    {
        return this.status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Column(name = "EMPLOYMENT_STATUS_CD", length = 20)
    public String getEmploymentStatusCd()
    {
        return this.employmentStatusCd;
    }

    public void setEmploymentStatusCd(String employmentStatusCd)
    {
        this.employmentStatusCd = employmentStatusCd;
    }

    @Column(name = "EMPLOYEE_TYPE_CD", length = 20)
    public String getEmployeeTypeCd()
    {
        return this.employeeTypeCd;
    }

    public void setEmployeeTypeCd(String employeeTypeCd)
    {
        this.employeeTypeCd = employeeTypeCd;
    }

    @Column(name = "JOB_CATEGORY", length = 20)
    public String getJobCategory()
    {
        return this.jobCategory;
    }

    public void setJobCategory(String jobCategory)
    {
        this.jobCategory = jobCategory;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "SERVICE_START_DATE", length = 7)
    public Date getServiceStartDate()
    {
        return this.serviceStartDate;
    }

    public void setServiceStartDate(Date serviceStartDate)
    {
        this.serviceStartDate = serviceStartDate;
    }
    
    @Column(name = "INIT_PWD", length = 1)
    public String getInitPwd()
    {
        return this.initPwd;
    }

    public void setInitPwd(String initPwd)
    {
        this.initPwd = initPwd;
    }

}
