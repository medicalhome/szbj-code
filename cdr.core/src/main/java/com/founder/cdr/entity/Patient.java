package com.founder.cdr.entity;

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

import java.io.Serializable;

@Entity
@Table(name = "PATIENT")
public class Patient implements Serializable
{

    private BigDecimal patientSn;

    private String patientEid;

    private String patientName;

    private String genderCode;

    private Date birthDate;

    private String marriageStatus;

    private String nationCode;

    private String birthPlace;

    private String nationalityCode;

    private String occupationCode;

    private String educationDegree;

    private String workPlace;

    private String telephone;

    private String mobile;

    private String email;

    private String bloodAbo;

    private String bloodRh;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String versionNo;

    private String genderName;

    private String marriageStatusName;

    private String nationName;

    private String nationalityName;

    private String occupationName;

    private String educationDegreeName;

    private String bloodAboName;

    private String bloodRhName;

    private String birthplaceDistCode;

    private String createby;
    
    private String updateby;
    
    private String deleteby;

    @Id
    // @GeneratedValue(generator = "guid-generator")
    // @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getPatientSn()
    {
        return this.patientSn;
    }

    public void setPatientSn(BigDecimal patientSn)
    {
        this.patientSn = patientSn;
    }

    @Column(name = "PATIENT_EID", length = 32)
    public String getPatientEid()
    {
        return this.patientEid;
    }

    public void setPatientEid(String patientEid)
    {
        this.patientEid = patientEid;
    }

    @Column(name = "PATIENT_NAME", length = 200)
    public String getPatientName()
    {
        return this.patientName;
    }

    public void setPatientName(String patientName)
    {
        this.patientName = patientName;
    }

    @Column(name = "GENDER_CODE", length = 12)
    public String getGenderCode()
    {
        return this.genderCode;
    }

    public void setGenderCode(String genderCode)
    {
        this.genderCode = genderCode;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTH_DATE", length = 7)
    public Date getBirthDate()
    {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    @Column(name = "MARRIAGE_STATUS", length = 10)
    public String getMarriageStatus()
    {
        return this.marriageStatus;
    }

    public void setMarriageStatus(String marriageStatus)
    {
        this.marriageStatus = marriageStatus;
    }

    @Column(name = "NATION_CODE", length = 12)
    public String getNationCode()
    {
        return this.nationCode;
    }

    public void setNationCode(String nationCode)
    {
        this.nationCode = nationCode;
    }

    @Column(name = "BIRTH_PLACE", length = 256)
    public String getBirthPlace()
    {
        return this.birthPlace;
    }

    public void setBirthPlace(String birthPlace)
    {
        this.birthPlace = birthPlace;
    }

    @Column(name = "NATIONALITY_CODE", length = 12)
    public String getNationalityCode()
    {
        return this.nationalityCode;
    }

    public void setNationalityCode(String nationalityCode)
    {
        this.nationalityCode = nationalityCode;
    }

    @Column(name = "OCCUPATION_CODE", length = 12)
    public String getOccupationCode()
    {
        return this.occupationCode;
    }

    public void setOccupationCode(String occupationCode)
    {
        this.occupationCode = occupationCode;
    }

    @Column(name = "EDUCATION_DEGREE", length = 12)
    public String getEducationDegree()
    {
        return this.educationDegree;
    }

    public void setEducationDegree(String educationDegree)
    {
        this.educationDegree = educationDegree;
    }

    @Column(name = "WORK_PLACE")
    public String getWorkPlace()
    {
        return this.workPlace;
    }

    public void setWorkPlace(String workPlace)
    {
        this.workPlace = workPlace;
    }

    @Column(name = "TELEPHONE", length = 32)
    public String getTelephone()
    {
        return this.telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    @Column(name = "MOBILE", length = 16)
    public String getMobile()
    {
        return this.mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    @Column(name = "EMAIL", length = 64)
    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Column(name = "BLOOD_ABO", length = 12)
    public String getBloodAbo()
    {
        return this.bloodAbo;
    }

    public void setBloodAbo(String bloodAbo)
    {
        this.bloodAbo = bloodAbo;
    }

    @Column(name = "BLOOD_RH", length = 12)
    public String getBloodRh()
    {
        return this.bloodRh;
    }

    public void setBloodRh(String bloodRh)
    {
        this.bloodRh = bloodRh;
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

    @Column(name = "VERSION_NO", length = 64)
    public String getVersionNo()
    {
        return this.versionNo;
    }

    public void setVersionNo(String versionNo)
    {
        this.versionNo = versionNo;
    }

    @Column(name = "GENDER_NAME", length = 40)
    public String getGenderName()
    {
        return this.genderName;
    }

    public void setGenderName(String genderName)
    {
        this.genderName = genderName;
    }

    @Column(name = "MARRIAGE_STATUS_NAME", length = 40)
    public String getMarriageStatusName()
    {
        return this.marriageStatusName;
    }

    public void setMarriageStatusName(String marriageStatusName)
    {
        this.marriageStatusName = marriageStatusName;
    }

    @Column(name = "NATION_NAME", length = 40)
    public String getNationName()
    {
        return this.nationName;
    }

    public void setNationName(String nationName)
    {
        this.nationName = nationName;
    }

    @Column(name = "NATIONALITY_NAME")
    public String getNationalityName()
    {
        return this.nationalityName;
    }

    public void setNationalityName(String nationalityName)
    {
        this.nationalityName = nationalityName;
    }

    @Column(name = "OCCUPATION_NAME")
    public String getOccupationName()
    {
        return this.occupationName;
    }

    public void setOccupationName(String occupationName)
    {
        this.occupationName = occupationName;
    }

    @Column(name = "EDUCATION_DEGREE_NAME")
    public String getEducationDegreeName()
    {
        return this.educationDegreeName;
    }

    public void setEducationDegreeName(String educationDegreeName)
    {
        this.educationDegreeName = educationDegreeName;
    }

    @Column(name = "BLOOD_ABO_NAME", length = 40)
    public String getBloodAboName()
    {
        return this.bloodAboName;
    }

    public void setBloodAboName(String bloodAboName)
    {
        this.bloodAboName = bloodAboName;
    }

    @Column(name = "BLOOD_RH_NAME", length = 40)
    public String getBloodRhName()
    {
        return this.bloodRhName;
    }

    public void setBloodRhName(String bloodRhName)
    {
        this.bloodRhName = bloodRhName;
    }

    @Column(name = "BIRTHPLACE_DIST_CODE", length = 32)
    public String getBirthplaceDistCode()
    {
        return birthplaceDistCode;
    }

    public void setBirthplaceDistCode(String birthplaceDistCode)
    {
        this.birthplaceDistCode = birthplaceDistCode;
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
