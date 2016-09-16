/*
 * VisitDao_selectVisitMainDiagnosisDisease.sql
 * Author: yu_yzh
 * Date: 2015/11/18 15:46
 * 检索该患者的所有主要诊断
 * */

select distinct d.disease_name from
diagnosis d
where 
	d.delete_flag = '0'
	and d.main_diagnosis_flag = '1'
	and d.patient_sn = /*patientSn*/