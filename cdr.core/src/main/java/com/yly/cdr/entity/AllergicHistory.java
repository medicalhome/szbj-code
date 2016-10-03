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
@Table(name = "ALLERGIC_HISTORY")
public class AllergicHistory implements Serializable
{

    private BigDecimal allergicSn;

    private BigDecimal patientSn;

    private String allergicSymptom;

    private String symptomCode;

    private String allergenCode;

    private String allergicSource;

    private String allergicCondition;

    private String seriousness;

    private Date allergicStartDate;

    private Date allergicStopDate;

    private Date createTime;

    private Date updateTime;

    private BigDecimal updateCount;

    private String deleteFlag;

    private Date deleteTime;

    private String allergicType;

    private String familyHistoryFlag;

    private String memo;

    private String patientDomain;

    private String patientLid;

    private String allergicConditionName;

    private String seriousnessName;

    private String allergicTypeName;

    private String createby;

    private String updateby;

    private String deleteby;
    
    private String orgCode;
    
    private String orgName;
    
    public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_ALLERGIC_HISTORY") })
    public BigDecimal getAllergicSn()
    {
        return this.allergicSn;
    }

    public void setAllergicSn(BigDecimal allergicSn)
    {
        this.allergicSn = allergicSn;
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

    @Column(name = "ALLERGIC_SYMPTOM", length = 300)
    public String getAllergicSymptom()
    {
        return this.allergicSymptom;
    }

    public void setAllergicSymptom(String allergicSymptom)
    {
        this.allergicSymptom = allergicSymptom;
    }

    @Column(name = "SYMPTOM_CODE", length = 72)
    public String getSymptomCode()
    {
        return this.symptomCode;
    }

    public void setSymptomCode(String symptomCode)
    {
        this.symptomCode = symptomCode;
    }

    @Column(name = "ALLERGEN_CODE", length = 48)
    public String getAllergenCode()
    {
        return this.allergenCode;
    }

    public void setAllergenCode(String allergenCode)
    {
        this.allergenCode = allergenCode;
    }

    @Column(name = "ALLERGIC_SOURCE", length = 300)
    public String getAllergicSource()
    {
        return this.allergicSource;
    }

    public void setAllergicSource(String allergicSource)
    {
        this.allergicSource = allergicSource;
    }

    @Column(name = "ALLERGIC_CONDITION", length = 12)
    public String getAllergicCondition()
    {
        return this.allergicCondition;
    }

    public void setAllergicCondition(String allergicCondition)
    {
        this.allergicCondition = allergicCondition;
    }

    @Column(name = "SERIOUSNESS", length = 12)
    public String getSeriousness()
    {
        return this.seriousness;
    }

    public void setSeriousness(String seriousness)
    {
        this.seriousness = seriousness;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "ALLERGIC_START_DATE", length = 7)
    public Date getAllergicStartDate()
    {
        return this.allergicStartDate;
    }

    public void setAllergicStartDate(Date allergicStartDate)
    {
        this.allergicStartDate = allergicStartDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "ALLERGIC_STOP_DATE", length = 7)
    public Date getAllergicStopDate()
    {
        return this.allergicStopDate;
    }

    public void setAllergicStopDate(Date allergicStopDate)
    {
        this.allergicStopDate = allergicStopDate;
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

    @Column(name = "ALLERGIC_TYPE", length = 12)
    public String getAllergicType()
    {
        return this.allergicType;
    }

    public void setAllergicType(String allergicType)
    {
        this.allergicType = allergicType;
    }

    @Column(name = "FAMILY_HISTORY_FLAG", length = 1)
    public String getFamilyHistoryFlag()
    {
        return this.familyHistoryFlag;
    }

    public void setFamilyHistoryFlag(String familyHistoryFlag)
    {
        this.familyHistoryFlag = familyHistoryFlag;
    }

    @Column(name = "MEMO", length = 600)
    public String getMemo()
    {
        return this.memo;
    }

    public void setMemo(String memo)
    {
        this.memo = memo;
    }

    @Column(name = "PATIENT_DOMAIN", length = 12)
    public String getPatientDomain()
    {
        return this.patientDomain;
    }

    public void setPatientDomain(String patientDomain)
    {
        this.patientDomain = patientDomain;
    }

    @Column(name = "PATIENT_LID", length = 200)
    public String getPatientLid()
    {
        return this.patientLid;
    }

    public void setPatientLid(String patientLid)
    {
        this.patientLid = patientLid;
    }

    @Column(name = "ALLERGIC_CONDITION_NAME", length = 40)
    public String getAllergicConditionName()
    {
        return this.allergicConditionName;
    }

    public void setAllergicConditionName(String allergicConditionName)
    {
        this.allergicConditionName = allergicConditionName;
    }

    @Column(name = "SERIOUSNESS_NAME", length = 300)
    public String getSeriousnessName()
    {
        return this.seriousnessName;
    }

    public void setSeriousnessName(String seriousnessName)
    {
        this.seriousnessName = seriousnessName;
    }

    @Column(name = "ALLERGIC_TYPE_NAME", length = 192)
    public String getAllergicTypeName()
    {
        return this.allergicTypeName;
    }

    public void setAllergicTypeName(String allergicTypeName)
    {
        this.allergicTypeName = allergicTypeName;
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
