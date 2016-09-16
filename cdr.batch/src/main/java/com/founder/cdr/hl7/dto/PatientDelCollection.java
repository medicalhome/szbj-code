package com.founder.cdr.hl7.dto;

import org.hibernate.validator.constraints.NotEmpty;


public class PatientDelCollection extends BaseDto
{
    /**
     * 患者EMPI标识
     */
    @NotEmpty(message="{PatientDelCollection.patientEid}")
    private String patientEid;

    public String getPatientEid()
    {
        return patientEid;
    }
}
