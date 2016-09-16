/*
*  DiagnosisDao_selectDiagnosisListSearchOb.sql
*/
select d.diagnosis_sn,
       d.visit_sn,
       d.patient_sn,
       d.diagnostic_dept_name,
       d.diagnosis_doctor_name,
       d.diagnosis_type_name,
       d.disease_code,
       d.disease_name,
       d.diagnosis_date,
       d.diagnosis_sequence,
       d.diagnoise_basis_name,
       d.main_diagnosis_flag,
       d.uncertain_flag,
       m.visit_type_name,
       d.contagious_flag,
       d.org_code,
       d.org_name
  from diagnosis d, medical_visit m
 where d.visit_sn = m.visit_sn
   and d.delete_flag=0 
   and m.delete_flag=0
   and d.patient_sn = /*patientSn*/''
      /*IF diagnosisListSearchPra.diagnosisTypeCode != null && diagnosisListSearchPra.diagnosisTypeCode.length() != 0*/
   and d.diagnosis_type_Code = /*diagnosisListSearchPra.diagnosisTypeCode*/''
      /*END*/
      /*IF diagnosisListSearchPra.diseaseName != null && diagnosisListSearchPra.diseaseName.length() != 0*/
   and d.disease_name like '%' || /*diagnosisListSearchPra.diseaseName*/''|| '%'
      /*END*/
      /*IF diagnosisListSearchPra.diagnosticDeptId != null && diagnosisListSearchPra.diagnosticDeptId.length() != 0*/
   and d.diagnostic_dept_id = /*diagnosisListSearchPra.diagnosticDeptId*/''
      /*END*/
      /*IF diagnosisListSearchPra.diagnosisDoctorName != null && diagnosisListSearchPra.diagnosisDoctorName.length() != 0*/
   and d.diagnosis_doctor_name like '%' || /*diagnosisListSearchPra.diagnosisDoctorName*/''|| '%'
      /*END*/
      /*IF diagnosisListSearchPra.diagnosisDateFromStr != null&& diagnosisListSearchPra.diagnosisDateFromStr.length() != 0*/
   and d.diagnosis_date >= to_date( /*diagnosisListSearchPra.diagnosisDateFromStr*/'' ||
                                 '00-00-00',
                                 'YYYY-MM-DD hh24:mi:ss')
      /*END*/
      /*IF diagnosisListSearchPra.diagnosisDateToStr != null&& diagnosisListSearchPra.diagnosisDateToStr.length() != 0*/
   and d.diagnosis_date <= to_date( /*diagnosisListSearchPra.diagnosisDateToStr*/'' ||
                                 '23-59-59',
                                 'YYYY-MM-DD hh24:mi:ss')
      /*END*/
      /*IF diagnosisListSearchPra.uncertainFlag != null && diagnosisListSearchPra.uncertainFlag.length() != 0*/
   and d.uncertain_flag = /*diagnosisListSearchPra.uncertainFlag*/''
      /*END*/
      /*IF diagnosisListSearchPra.contagiousFlag != null && diagnosisListSearchPra.contagiousFlag.length() != 0*/
   and d.contagious_flag = /*diagnosisListSearchPra.contagiousFlag*/''
      /*END*/
      /*IF diagnosisListSearchPra.mainDiagnosisFlag != null && diagnosisListSearchPra.mainDiagnosisFlag.length() != 0*/
   and d.main_diagnosis_flag = /*diagnosisListSearchPra.mainDiagnosisFlag*/''
      /*END*/
      /*IF diagnosisListSearchPra.visitSn != null && diagnosisListSearchPra.visitSn.length() != 0*/
   and d.visit_sn = /*diagnosisListSearchPra.visitSn*/''
      /*END*/
      /*IF diagnosisListSearchPra.inpatientDate != null && diagnosisListSearchPra.inpatientDate.length() != 0*/
   and d.diagnosis_date between
       TO_DATE( /*diagnosisListSearchPra.inpatientDate*/'', 'YYYY-MM-DD') and
       TO_DATE( /*diagnosisListSearchPra.inpatientDate*/''|| '23:59:59',
               'YYYY-MM-DD HH24:MI:SS')
	  /*END*/
      /*IF diagnosisListSearchPra.orgCode != null && diagnosisListSearchPra.orgCode.length() != 0*/
   and d.org_code = /*diagnosisListSearchPra.orgCode*/ 
	  /*END*/
 order by d.diagnosis_date desc, d.diagnosis_sn desc
