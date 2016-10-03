/**
 * [FUN]V03-001诊断DAO
 * 
* @version 1.0, 2011/10/28  10:22:00

 * @author  liujingyang * @since 1.0
*/
package com.yly.cdr.dao;

import java.util.Date;
import java.util.List;

import com.founder.fasf.core.util.daohelper.annotation.Arguments;
import com.founder.fasf.web.paging.PagingContext;
import com.yly.cdr.dto.DiagnosisDto;
import com.yly.cdr.dto.diagnosis.DiagnosisListPra;
import com.yly.cdr.dto.diagnosis.DiagnosisListSearchPra;
import com.yly.cdr.dto.visit.VisitDiagnosisDto;
import com.yly.cdr.entity.Diagnosis;

public interface DiagnosisDao
{

    @Arguments({ "name1", "name2", "name3", "name4", "name5", "name6", "name7" })
    public List<Diagnosis> selectAllDiagnosisByConditions(String diagnosisSn,
            Date diagnosisDate, Date diagnosisDate1, String diagnosisType,
            String diagnosticDeptName, String diagnosisDoctor,
            String diseaseName, PagingContext pagingContext);

    @Arguments({ "patientSn", "diagnosisType", "diseaseName",
            "diagnosticDeptName", "diagnosisDoctor", "diagnosisDate",
            "diagnosisDate1", "check1", "check2", "mainDiagnosisFlag" })
    public List<DiagnosisDto> selectAllDiagnosis(String patientSn,
            String diagnosisType, String diseaseName,
            String diagnosticDeptName, String diagnosisDoctor,
            Date diagnosisDate, Date diagnosisDate1, String check1,
            String check2, String mainDiagnosisFlag, PagingContext pagingContext);

    @Arguments({ "diagnosisSn" })
    public DiagnosisDto selectOneDiagnosis(String diagnosisSn);

    @Arguments({ "patientSn", "diagnosisListSearchPra" })
    public DiagnosisDto[] selectDiagnosisListSearchOb(String patientSn,
            DiagnosisListSearchPra diagnosisListSearchPra,
            PagingContext pagingContext);

    @Arguments({ "visitSn", "diagnosisTypeCode" })
    public List<DiagnosisDto> selectCiDiagnosis(String visitSn,
            String diagnosisTypeCode);

    @Arguments({ "visitSn", "diagnosisTypeCode" })
    public void deleteDiagnosis(String visitSn, String[] diagnosisTypeCode);

    @Arguments({ "diagnosisListPra" })
    public List<DiagnosisDto> selectDiagnosises(
            DiagnosisListPra diagnosisListPra);
    
    // Author: yu_yzh
    // Date: 2015/11/18 15:44
    // ADD BEGIN
    @Arguments({ "patientSn"})
    public List<VisitDiagnosisDto> selectVisitMainDiagnosisDisease(String patientSn);
    // ADD END
}
