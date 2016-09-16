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
@Table(name = "IMMUNIZATION_HISTORY")
public class ImmunizationHistory implements Serializable
{

    private BigDecimal immunizationSn;

    private BigDecimal documentSn;

    private String vaccineCode;

    private String vaccineName;

    private String vaccineLot;

    private String vaccineType;

    private String inoculationDate;

    private String inoculationMethod;

    private String inoculationStatus;

    private String vaccineTypeName;

    private String inoculationMethodName;

    private String inoculationStatusName;

    @Id
    @GeneratedValue(generator = "guid-generator")
    @Generator(name = "guid-generator", strategy = "guid")
    public BigDecimal getImmunizationSn()
    {
        return this.immunizationSn;
    }

    public void setImmunizationSn(BigDecimal immunizationSn)
    {
        this.immunizationSn = immunizationSn;
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

    @Column(name = "VACCINE_CODE", length = 20)
    public String getVaccineCode()
    {
        return this.vaccineCode;
    }

    public void setVaccineCode(String vaccineCode)
    {
        this.vaccineCode = vaccineCode;
    }

    @Column(name = "VACCINE_NAME")
    public String getVaccineName()
    {
        return this.vaccineName;
    }

    public void setVaccineName(String vaccineName)
    {
        this.vaccineName = vaccineName;
    }

    @Column(name = "VACCINE_LOT", length = 20)
    public String getVaccineLot()
    {
        return this.vaccineLot;
    }

    public void setVaccineLot(String vaccineLot)
    {
        this.vaccineLot = vaccineLot;
    }

    @Column(name = "VACCINE_TYPE", length = 20)
    public String getVaccineType()
    {
        return this.vaccineType;
    }

    public void setVaccineType(String vaccineType)
    {
        this.vaccineType = vaccineType;
    }

    @Column(name = "INOCULATION_DATE", length = 12)
    public String getInoculationDate()
    {
        return this.inoculationDate;
    }

    public void setInoculationDate(String inoculationDate)
    {
        this.inoculationDate = inoculationDate;
    }

    @Column(name = "INOCULATION_METHOD", length = 20)
    public String getInoculationMethod()
    {
        return this.inoculationMethod;
    }

    public void setInoculationMethod(String inoculationMethod)
    {
        this.inoculationMethod = inoculationMethod;
    }

    @Column(name = "INOCULATION_STATUS", length = 20)
    public String getInoculationStatus()
    {
        return this.inoculationStatus;
    }

    public void setInoculationStatus(String inoculationStatus)
    {
        this.inoculationStatus = inoculationStatus;
    }

    @Column(name = "VACCINE_TYPE_NAME", length = 40)
    public String getVaccineTypeName()
    {
        return this.vaccineTypeName;
    }

    public void setVaccineTypeName(String vaccineTypeName)
    {
        this.vaccineTypeName = vaccineTypeName;
    }

    @Column(name = "INOCULATION_METHOD_NAME", length = 40)
    public String getInoculationMethodName()
    {
        return this.inoculationMethodName;
    }

    public void setInoculationMethodName(String inoculationMethodName)
    {
        this.inoculationMethodName = inoculationMethodName;
    }

    @Column(name = "INOCULATION_STATUS_NAME", length = 40)
    public String getInoculationStatusName()
    {
        return this.inoculationStatusName;
    }

    public void setInoculationStatusName(String inoculationStatusName)
    {
        this.inoculationStatusName = inoculationStatusName;
    }

}
