/*
* DiagnosisDao_selectOneDiagnosis.sql
*/
select d.diagnosis_sn,
       d.visit_sn,
       d.patient_sn,
       d.diagnostic_dept_name,
       d.diagnosis_doctor_name,
       d.diagnosis_type_name,
       d.diagnosis_type_code,
       d.main_diagnosis_flag,
       d.disease_code,
       d.disease_name,
       d.diagnosis_date,
       d.diagnosis_sequence,
       d.diagnoise_basis_name,
       d.uncertain_flag,
       d.main_diagnosis_flag,
       d.contagious_flag,
       m.visit_type_name,
       d.org_code,
       d.org_name
  from diagnosis d, Medical_Visit m
 where d.visit_sn = m.visit_sn
   and d.delete_flag = '0'
   and d.diagnosis_sn = /*diagnosisSn*/
