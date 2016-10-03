package com.yly.cdr.dto.patient;

import java.util.List;

public class PatientListAdvanceSearchPra
{
    private String patientSql;

    private String visitSql;

    private String diagnosisSql;

    private String empiSql;

    private String labItemSql; 

    private List deptIds;

    private String doctorId;

    private String textTip;

    // 就诊状态出院Y67
    private String visitStatusDischarge;

    // 就诊类型门诊
    private String visitTypeOutpatient;

    // 就诊类型住院
    private String visitTypeInpatient;

    public String getTextTip()
    {
        return textTip;
    }

    public void setTextTip(String textTip)
    {
        this.textTip = textTip;
    }

    public List getDeptIds()
    {
        return deptIds;
    }

    public void setDeptIds(List deptIds)
    {
        this.deptIds = deptIds;
    }

    public String getDoctorId()
    {
        return doctorId;
    }

    public void setDoctorId(String doctorId)
    {
        this.doctorId = doctorId;
    }

    public String getPatientSql()
    {
        return patientSql;
    }

    public void setPatientSql(String patientSql)
    {
        this.patientSql = patientSql;
    }

    public String getVisitSql()
    {
        return visitSql;
    }

    public void setVisitSql(String visitSql)
    {
        this.visitSql = visitSql;
    }

    public String getDiagnosisSql()
    {
        return diagnosisSql;
    }

    public void setDiagnosisSql(String diagnosisSql)
    {
        this.diagnosisSql = diagnosisSql;
    }

    public String getEmpiSql()
    {
        return empiSql;
    }

    public void setEmpiSql(String empiSql)
    {
        this.empiSql = empiSql;
    }

    public String getVisitStatusDischarge()
    {
        return visitStatusDischarge;
    }

    public void setVisitStatusDischarge(String visitStatusDischarge)
    {
        this.visitStatusDischarge = visitStatusDischarge;
    }

    public String getVisitTypeOutpatient()
    {
        return visitTypeOutpatient;
    }

    public void setVisitTypeOutpatient(String visitTypeOutpatient)
    {
        this.visitTypeOutpatient = visitTypeOutpatient;
    }

    public String getVisitTypeInpatient()
    {
        return visitTypeInpatient;
    }

    public void setVisitTypeInpatient(String visitTypeInpatient)
    {
        this.visitTypeInpatient = visitTypeInpatient;
    }

    public String getLabItemSql()
    {
        return labItemSql;
    }

    public void setLabItemSql(String labItemSql)
    {
        this.labItemSql = labItemSql;
    }

}
