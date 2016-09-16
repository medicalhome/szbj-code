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
@Table(name = "PRESENT_ILLNESS")
public class PresentIllness implements Serializable
{

    private BigDecimal presentIllnessSn;

    private BigDecimal documentSn;

    private String onsetTime;

    private String onsetSeason;

    private String description;

    private String reasonIncentive;

    private String characteristics;

    private String associatedSymptoms;

    private String treatmentHistory;

    private String general;

    private String primaryDiseasesTreatment;

    private String infectionRoute;

    private String infectionRouteCode;

    private String onsetSeasonName;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getPresentIllnessSn()
    {
        return this.presentIllnessSn;
    }

    public void setPresentIllnessSn(BigDecimal presentIllnessSn)
    {
        this.presentIllnessSn = presentIllnessSn;
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

    @Column(name = "ONSET_TIME", length = 20)
    public String getOnsetTime()
    {
        return this.onsetTime;
    }

    public void setOnsetTime(String onsetTime)
    {
        this.onsetTime = onsetTime;
    }

    @Column(name = "ONSET_SEASON", length = 20)
    public String getOnsetSeason()
    {
        return this.onsetSeason;
    }

    public void setOnsetSeason(String onsetSeason)
    {
        this.onsetSeason = onsetSeason;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Column(name = "REASON_INCENTIVE")
    public String getReasonIncentive()
    {
        return this.reasonIncentive;
    }

    public void setReasonIncentive(String reasonIncentive)
    {
        this.reasonIncentive = reasonIncentive;
    }

    @Column(name = "CHARACTERISTICS")
    public String getCharacteristics()
    {
        return this.characteristics;
    }

    public void setCharacteristics(String characteristics)
    {
        this.characteristics = characteristics;
    }

    @Column(name = "ASSOCIATED_SYMPTOMS")
    public String getAssociatedSymptoms()
    {
        return this.associatedSymptoms;
    }

    public void setAssociatedSymptoms(String associatedSymptoms)
    {
        this.associatedSymptoms = associatedSymptoms;
    }

    @Column(name = "TREATMENT_HISTORY")
    public String getTreatmentHistory()
    {
        return this.treatmentHistory;
    }

    public void setTreatmentHistory(String treatmentHistory)
    {
        this.treatmentHistory = treatmentHistory;
    }

    @Column(name = "GENERAL")
    public String getGeneral()
    {
        return this.general;
    }

    public void setGeneral(String general)
    {
        this.general = general;
    }

    @Column(name = "PRIMARY_DISEASES_TREATMENT")
    public String getPrimaryDiseasesTreatment()
    {
        return this.primaryDiseasesTreatment;
    }

    public void setPrimaryDiseasesTreatment(String primaryDiseasesTreatment)
    {
        this.primaryDiseasesTreatment = primaryDiseasesTreatment;
    }

    @Column(name = "INFECTION_ROUTE", length = 50)
    public String getInfectionRoute()
    {
        return this.infectionRoute;
    }

    public void setInfectionRoute(String infectionRoute)
    {
        this.infectionRoute = infectionRoute;
    }

    @Column(name = "INFECTION_ROUTE_CODE", length = 20)
    public String getInfectionRouteCode()
    {
        return this.infectionRouteCode;
    }

    public void setInfectionRouteCode(String infectionRouteCode)
    {
        this.infectionRouteCode = infectionRouteCode;
    }

    @Column(name = "ONSET_SEASON_NAME", length = 40)
    public String getOnsetSeasonName()
    {
        return this.onsetSeasonName;
    }

    public void setOnsetSeasonName(String onsetSeasonName)
    {
        this.onsetSeasonName = onsetSeasonName;
    }

}
