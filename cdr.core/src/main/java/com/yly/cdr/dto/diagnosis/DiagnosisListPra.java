package com.yly.cdr.dto.diagnosis;

import java.util.List;

import com.yly.cdr.dto.CommonParameters;

public class DiagnosisListPra extends CommonParameters
{
    // 就诊号
    private List visitSns;

    // 是否主要诊断
    private String mainDiagnosisFlag;

    public List getVisitSns()
    {
        return visitSns;
    }

    public void setVisitSns(List visitSns)
    {
        this.visitSns = visitSns;
    }

    public String getMainDiagnosisFlag()
    {
        return mainDiagnosisFlag;
    }

    public void setMainDiagnosisFlag(String mainDiagnosisFlag)
    {
        this.mainDiagnosisFlag = mainDiagnosisFlag;
    }

}
