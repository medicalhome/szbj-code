/*
*	DiagnosisDao_selectDiagnosises.sql
*/
select visit_sn,disease_name 
  from diagnosis
 where visit_sn in /*diagnosisListPra.visitSns*/(10,20,30) 
   and main_diagnosis_flag=/*diagnosisListPra.mainDiagnosisFlag*/1 
   and delete_flag='0'


  