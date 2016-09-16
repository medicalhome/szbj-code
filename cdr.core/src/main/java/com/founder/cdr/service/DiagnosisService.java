package com.founder.cdr.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.dto.DiagnosisDto;
import com.founder.cdr.dto.VisitIndexListDto;
import com.founder.cdr.dto.diagnosis.DiagnosisListPra;
import com.founder.cdr.dto.diagnosis.DiagnosisListSearchPra;
import com.founder.cdr.dto.visit.VisitDiagnosisDto;
import com.founder.cdr.entity.Diagnosis;
import com.founder.cdr.entity.MedicalVisit;
import com.founder.fasf.web.paging.PagingContext;

public interface DiagnosisService
{

    @Transactional
    public List<Diagnosis> selectAllDiagnosisByConditions(String diagnosisSn,
            Date diagnosisDate, Date diagnosisDate1, String diagnosisType,
            String diagnosticDeptName, String diagnosisDoctor,
            String diseaseName, PagingContext pagingContext);

    @Transactional
    public List<DiagnosisDto> selectAllDiagnosis(String patientSn,
            String diagnosisType, String diseaseName,
            String diagnosticDeptName, String diagnosisDoctor,
            Date diagnosisDate, Date diagnosisDate1, String check1,
            String check2, String mainDiagnosisFlag, PagingContext pagingContext);

    @Transactional
    DiagnosisDto[] selectDiagnosisList(String patientSn,
            DiagnosisListSearchPra diagnosisListSearchPra,
            PagingContext pagingContext);

    @Transactional
    public DiagnosisDto selectOneDiagnosis(String diagnosisSn);

    /*
     * @Transactional public void updateDiagnosis(List<Object> diagnosisList,
     * List<Object> diagnosisResultList, MedicalVisit medicalVisit);
     */

    @Transactional
    public void saveList(List<Object> diagnosisList, MedicalVisit medicalVisit);

    @Transactional
    public List<DiagnosisDto> selectCiDiagnosis(String visitSn,
            String diagnosisTypeCode);

    @Transactional
    public List<Diagnosis> selectDiagnosisByVisitSn(String visitSn);

    @Transactional
    public void deleteDiagnosis(String visitSn, String[] diagnosisTypeCode);

    @Transactional
    public Map<String, String> selectVisitDiagnosis(List<String> visitSns);

    @Transactional
    public List<VisitDiagnosisDto> selectMainDiagnosisByPatientSn(String patientSn);
}
