package com.founder.cdr.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.founder.fasf.core.util.daohelper.annotation.Generator;

import java.io.Serializable;

@Entity
@Table(name = "TREATMENT_PLAN")
public class TreatmentPlan implements Serializable
{

    private BigDecimal treatmentPlanSn;

    private BigDecimal documentSn;

    private String examinationPlan;

    private String labTestPlan;

    private String clinicPlan;

    private String followFlag;

    private String followInterval;

    private String orderContent;

    private String specialNotice;

    private String classification;

    private String diagnosisTreatmentBasis;

    private String therapeuticMethod;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getTreatmentPlanSn()
    {
        return this.treatmentPlanSn;
    }

    public void setTreatmentPlanSn(BigDecimal treatmentPlanSn)
    {
        this.treatmentPlanSn = treatmentPlanSn;
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

    @Column(name = "EXAMINATION_PLAN")
    public String getExaminationPlan()
    {
        return this.examinationPlan;
    }

    public void setExaminationPlan(String examinationPlan)
    {
        this.examinationPlan = examinationPlan;
    }

    @Column(name = "LAB_TEST_PLAN")
    public String getLabTestPlan()
    {
        return this.labTestPlan;
    }

    public void setLabTestPlan(String labTestPlan)
    {
        this.labTestPlan = labTestPlan;
    }

    @Column(name = "CLINIC_PLAN")
    public String getClinicPlan()
    {
        return this.clinicPlan;
    }

    public void setClinicPlan(String clinicPlan)
    {
        this.clinicPlan = clinicPlan;
    }

    @Column(name = "FOLLOW_FLAG", length = 1)
    public String getFollowFlag()
    {
        return this.followFlag;
    }

    public void setFollowFlag(String followFlag)
    {
        this.followFlag = followFlag;
    }

    @Column(name = "FOLLOW_INTERVAL", length = 20)
    public String getFollowInterval()
    {
        return this.followInterval;
    }

    public void setFollowInterval(String followInterval)
    {
        this.followInterval = followInterval;
    }

    @Column(name = "ORDER_CONTENT")
    public String getOrderContent()
    {
        return this.orderContent;
    }

    public void setOrderContent(String orderContent)
    {
        this.orderContent = orderContent;
    }

    @Column(name = "SPECIAL_NOTICE", length = 200)
    public String getSpecialNotice()
    {
        return this.specialNotice;
    }

    public void setSpecialNotice(String specialNotice)
    {
        this.specialNotice = specialNotice;
    }

    @Column(name = "CLASSIFICATION")
    public String getClassification()
    {
        return this.classification;
    }

    public void setClassification(String classification)
    {
        this.classification = classification;
    }

    @Column(name = "DIAGNOSIS_TREATMENT_BASIS")
    public String getDiagnosisTreatmentBasis()
    {
        return this.diagnosisTreatmentBasis;
    }

    public void setDiagnosisTreatmentBasis(String diagnosisTreatmentBasis)
    {
        this.diagnosisTreatmentBasis = diagnosisTreatmentBasis;
    }

    @Column(name = "THERAPEUTIC_METHOD")
    public String getTherapeuticMethod()
    {
        return this.therapeuticMethod;
    }

    public void setTherapeuticMethod(String therapeuticMethod)
    {
        this.therapeuticMethod = therapeuticMethod;
    }

}
