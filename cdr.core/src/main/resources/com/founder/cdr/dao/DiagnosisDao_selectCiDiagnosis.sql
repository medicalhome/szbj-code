/*
*	DiagnosisDao_selectCiDiagnosis.sql
*/
select diagnosis_sn,visit_sn,patient_sn,diagnostic_dept_name,diagnosis_doctor_name,diagnosis_type_name,diagnosis_type_code,disease_code,disease_name,diagnosis_date,diagnosis_sequence,diagnoise_basis_name,uncertain_flag,main_diagnosis_flag,contagious_flag 
  from diagnosis
 where visit_sn = /*visitSn*/ 
   and diagnosis_type_code=/*diagnosisTypeCode*/ 
   and main_diagnosis_flag='0'  
   and delete_flag = '0'
