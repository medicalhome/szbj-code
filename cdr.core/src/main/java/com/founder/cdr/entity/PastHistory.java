package com.founder.cdr.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.founder.fasf.core.util.daohelper.annotation.Generator;
import com.founder.fasf.core.util.daohelper.annotation.Parameter;

import java.io.Serializable;

@Entity
@Table(name = "PAST_HISTORY")
public class PastHistory implements Serializable
{

    private BigDecimal historySn;

    private BigDecimal documentSn;

    private BigDecimal examOrderSn;

    private String medicalHistory;

    private String mentalDiseasesDiagnosis;

    private String diseasesName;

    private String diseasesCode;

    private String diagnosticFacilities;

    private String facilitiesLevel;

    private String diagnosticTime;

    private String diseaseStatus;

    private String facilitiesLevelName;

    private String diseaseStatusName;
    
    private String createby;

    private String updateby;

    private String deleteby;

    @Id
    @GeneratedValue(generator = "native-generator")
    @Generator(name = "native-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SEQ_PAST_HISTORY") })
    public BigDecimal getHistorySn()
    {
        return this.historySn;
    }

    public void setHistorySn(BigDecimal historySn)
    {
        this.historySn = historySn;
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

    @Column(name = "EXAM_ORDER_SN", precision = 22, scale = 0)
    public BigDecimal getExamOrderSn()
    {
        return examOrderSn;
    }

    public void setExamOrderSn(BigDecimal examOrderSn)
    {
        this.examOrderSn = examOrderSn;
    }

    @Column(name = "MEDICAL_HISTORY")
    public String getMedicalHistory()
    {
        return this.medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory)
    {
        this.medicalHistory = medicalHistory;
    }

    @Column(name = "MENTAL_DISEASES_DIAGNOSIS")
    public String getMentalDiseasesDiagnosis()
    {
        return this.mentalDiseasesDiagnosis;
    }

    public void setMentalDiseasesDiagnosis(String mentalDiseasesDiagnosis)
    {
        this.mentalDiseasesDiagnosis = mentalDiseasesDiagnosis;
    }

    @Column(name = "DISEASES_NAME")
    public String getDiseasesName()
    {
        return this.diseasesName;
    }

    public void setDiseasesName(String diseasesName)
    {
        this.diseasesName = diseasesName;
    }

    @Column(name = "DISEASES_CODE")
    public String getDiseasesCode()
    {
        return this.diseasesCode;
    }

    public void setDiseasesCode(String diseasesCode)
    {
        this.diseasesCode = diseasesCode;
    }

    @Column(name = "DIAGNOSTIC_FACILITIES")
    public String getDiagnosticFacilities()
    {
        return this.diagnosticFacilities;
    }

    public void setDiagnosticFacilities(String diagnosticFacilities)
    {
        this.diagnosticFacilities = diagnosticFacilities;
    }

    @Column(name = "FACILITIES_LEVEL", length = 12)
    public String getFacilitiesLevel()
    {
        return this.facilitiesLevel;
    }

    public void setFacilitiesLevel(String facilitiesLevel)
    {
        this.facilitiesLevel = facilitiesLevel;
    }

    @Column(name = "DIAGNOSTIC_TIME", length = 20)
    public String getDiagnosticTime()
    {
        return this.diagnosticTime;
    }

    public void setDiagnosticTime(String diagnosticTime)
    {
        this.diagnosticTime = diagnosticTime;
    }

    @Column(name = "DISEASE_STATUS", length = 20)
    public String getDiseaseStatus()
    {
        return this.diseaseStatus;
    }

    public void setDiseaseStatus(String diseaseStatus)
    {
        this.diseaseStatus = diseaseStatus;
    }

    @Column(name = "FACILITIES_LEVEL_NAME", length = 40)
    public String getFacilitiesLevelName()
    {
        return this.facilitiesLevelName;
    }

    public void setFacilitiesLevelName(String facilitiesLevelName)
    {
        this.facilitiesLevelName = facilitiesLevelName;
    }

    @Column(name = "DISEASE_STATUS_NAME", length = 40)
    public String getDiseaseStatusName()
    {
        return this.diseaseStatusName;
    }

    public void setDiseaseStatusName(String diseaseStatusName)
    {
        this.diseaseStatusName = diseaseStatusName;
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
