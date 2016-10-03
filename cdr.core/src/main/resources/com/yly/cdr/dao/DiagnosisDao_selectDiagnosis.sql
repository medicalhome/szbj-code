/*
*	DiagnosisDao_selectDiagnosis.sql
*/
select diagnosis_sn,visit_sn,patient_sn,diagnostic_dept_name,diagnosis_doctor_name,diagnosis_type_name,disease_code,disease_name,diagnosis_date,diagnosis_sequence,diagnoise_basis_name,uncertain_flag,main_diagnosis_flag,contagious_flag 
  from diagnosis
 where diagnosis_sn = /*diagnosisSn*/ 
   and delete_flag = '0'
