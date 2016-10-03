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
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

import java.io.Serializable;

@Entity
@Table(name = "CODE_PERSON")
public class CodePerson implements Serializable
{

    private BigDecimal codePersonId;

    private BigDecimal csId;

    private String code;

    private String name;

    private String pyCode;

    private String sexCode;

    private String employmentStatusCd;

    private String jobCategory;

    private String departCd;

    private String employeeTypeCd;

    private BigDecimal phoneNumber;

    private String mailAddress;

    private Date serviceStartDate;

    private String seqNo;

    private String versionNo;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String departName;

    private String birthday;

    private String nationalIdentifier;

    private String financialDepartCd;

    private String financialDepartName;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_CODE_PERSON") })
    public BigDecimal getCodePersonId()
    {
        return this.codePersonId;
    }

    public void setCodePersonId(BigDecimal codePersonId)
    {
        this.codePersonId = codePersonId;
    }

    @Column(name = "CS_ID", nullable = false, precision = 22, scale = 0)
    public BigDecimal getCsId()
    {
        return this.csId;
    }

    public void setCsId(BigDecimal csId)
    {
        this.csId = csId;
    }

    @Column(name = "CODE", length = 20)
    public String getCode()
    {
        return this.code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    @Column(name = "NAME", length = 50)
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "PY_CODE", length = 38)
    public String getPyCode()
    {
        return this.pyCode;
    }

    public void setPyCode(String pyCode)
    {
        this.pyCode = pyCode;
    }

    @Column(name = "SEX_CODE", length = 12)
    public String getSexCode()
    {
        return this.sexCode;
    }

    public void setSexCode(String sexCode)
    {
        this.sexCode = sexCode;
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

    @Column(name = "JOB_CATEGORY", length = 50)
    public String getJobCategory()
    {
        return this.jobCategory;
    }

    public void setJobCategory(String jobCategory)
    {
        this.jobCategory = jobCategory;
    }

    @Column(name = "DEPART_CD", length = 64)
    public String getDepartCd()
    {
        return this.departCd;
    }

    public void setDepartCd(String departCd)
    {
        this.departCd = departCd;
    }

    @Column(name = "EMPLOYEE_TYPE_CD", precision = 32, scale = 0)
    public String getEmployeeTypeCd()
    {
        return this.employeeTypeCd;
    }

    public void setEmployeeTypeCd(String employeeTypeCd)
    {
        this.employeeTypeCd = employeeTypeCd;
    }

    @Column(name = "PHONE_NUMBER", precision = 32, scale = 0)
    public BigDecimal getPhoneNumber()
    {
        return this.phoneNumber;
    }

    public void setPhoneNumber(BigDecimal phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "MAIL_ADDRESS", length = 20)
    public String getMailAddress()
    {
        return this.mailAddress;
    }

    public void setMailAddress(String mailAddress)
    {
        this.mailAddress = mailAddress;
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

    @Column(name = "SEQ_NO", length = 32)
    public String getSeqNo()
    {
        return this.seqNo;
    }

    public void setSeqNo(String seqNo)
    {
        this.seqNo = seqNo;
    }

    @Column(name = "VERSION_NO", length = 32)
    public String getVersionNo()
    {
        return versionNo;
    }

    public void setVersionNo(String versionNo)
    {
        this.versionNo = versionNo;
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

    @Column(name = "DEPART_NAME", length = 100)
    public String getDepartName()
    {
        return departName;
    }

    public void setDepartName(String departName)
    {
        this.departName = departName;
    }

    @Column(name = "BIRTHDAY", length = 20)
    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    @Column(name = "NATIONAL_IDENTIFIER", length = 50)
    public String getNationalIdentifier()
    {
        return nationalIdentifier;
    }

    public void setNationalIdentifier(String nationalIdentifier)
    {
        this.nationalIdentifier = nationalIdentifier;
    }

    @Column(name = "FINANCIAL_DEPART_CD", length = 15)
    public String getFinancialDepartCd()
    {
        return financialDepartCd;
    }

    public void setFinancialDepartCd(String financialDepartCd)
    {
        this.financialDepartCd = financialDepartCd;
    }

    @Column(name = "FINANCIAL_DEPART_NAME", length = 100)
    public String getFinancialDepartName()
    {
        return financialDepartName;
    }

    public void setFinancialDepartName(String financialDepartName)
    {
        this.financialDepartName = financialDepartName;
    }

}
