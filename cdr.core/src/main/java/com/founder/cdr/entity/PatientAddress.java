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
@Table(name = "PATIENT_ADDRESS")
public class PatientAddress implements Serializable
{

    private BigDecimal addressSn;

    private BigDecimal patientSn;

    private String addressType;

    private String districtCode;

    private String subDistrictName;

    private String zipCode;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String fullAddress;

    private String addressTypeName;

    private String districtName;
    
    private String createby;

    private String updateby;

    private String deleteby;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_ADDRESS") })
    public BigDecimal getAddressSn()
    {
        return this.addressSn;
    }

    public void setAddressSn(BigDecimal addressSn)
    {
        this.addressSn = addressSn;
    }

    @Column(name = "PATIENT_SN", nullable = false, precision = 22, scale = 0)
    public BigDecimal getPatientSn()
    {
        return this.patientSn;
    }

    public void setPatientSn(BigDecimal patientSn)
    {
        this.patientSn = patientSn;
    }

    @Column(name = "ADDRESS_TYPE", length = 12)
    public String getAddressType()
    {
        return this.addressType;
    }

    public void setAddressType(String addressType)
    {
        this.addressType = addressType;
    }

    @Column(name = "DISTRICT_CODE", length = 12)
    public String getDistrictCode()
    {
        return this.districtCode;
    }

    public void setDistrictCode(String districtCode)
    {
        this.districtCode = districtCode;
    }

    @Column(name = "SUB_DISTRICT_NAME", length = 200)
    public String getSubDistrictName()
    {
        return this.subDistrictName;
    }

    public void setSubDistrictName(String subDistrictName)
    {
        this.subDistrictName = subDistrictName;
    }

    @Column(name = "ZIP_CODE", length = 12)
    public String getZipCode()
    {
        return this.zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
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

    @Column(name = "FULL_ADDRESS", length = 200)
    public String getFullAddress()
    {
        return this.fullAddress;
    }

    public void setFullAddress(String fullAddress)
    {
        this.fullAddress = fullAddress;
    }

    @Column(name = "ADDRESS_TYPE_NAME", length = 40)
    public String getAddressTypeName()
    {
        return this.addressTypeName;
    }

    public void setAddressTypeName(String addressTypeName)
    {
        this.addressTypeName = addressTypeName;
    }

    @Column(name = "DISTRICT_NAME", length = 100)
    public String getDistrictName()
    {
        return this.districtName;
    }

    public void setDistrictName(String districtName)
    {
        this.districtName = districtName;
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
