/*
*	DiagnosisDao_deleteDiagnosis.sql
*/

DELETE FROM diagnosis WHERE visit_sn = /*visitSn*/ 
/*IF diagnosisTypeCode != NULL */
and diagnosis_type_code in /*diagnosisTypeCode*/(10, 20, 30)
/*END*/