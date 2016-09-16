package com.founder.cdr.hl7.dto.prpain201302uv03;

import java.util.List;

import com.founder.cdr.hl7.dto.BaseDto;
import com.founder.cdr.hl7.dto.PatientDelCollection;

public class PRPAIN201302UV03 extends BaseDto
{
    /**
     * 患者EMPI标识集合
     */
    private List<PatientDelCollection> patientEidList;

    public List<PatientDelCollection> getPatientEidList()
    {
        return patientEidList;
    }
    
}
