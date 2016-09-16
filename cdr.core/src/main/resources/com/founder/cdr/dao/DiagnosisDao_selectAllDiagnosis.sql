/*
*	DiagnosisDao_selectAllDiagnosis.sql
*/

/*
select diagnosis_sn,visit_sn,patient_sn,diagnostic_dept_name,diagnosis_doctor_name,diagnosis_type_name,diagnosis_type_code,disease_code,disease_name,diagnosis_date,diagnosis_sequence,diagnoise_basis_name,uncertain_flag, contagious_flag from diagnosis

*/
select a.*, b.ci
  from (select *
          from diagnosis
         where delete_flag = '0'
           and main_diagnosis_flag = 1
           and patient_sn = /*patientSn*/ ) a
  left outer join (select diagnosis_type_code, visit_sn, count(*) as ci
                     from diagnosis
                    where delete_flag = '0'
                      and main_diagnosis_flag = 0
                      and patient_sn = /*patientSn*/
                    group by diagnosis_type_code, visit_sn) b
    on a.visit_sn = b.visit_sn
   and a.diagnosis_type_code = b.diagnosis_type_code

   /*IF diagnosisTypeName != null && diagnosisTypeName.length() != 0*/
   and a.diagnosis_type_Name = /*diagnosisTypeName*/
   /*END*/
   /*IF diseaseName != null && diseaseName.length() != 0*/
   and a.disease_name like '%' || /*diseaseName*/ || '%'
   /*END*/
	 /*IF diagnosticDept != null && diagnosticDept.length() != 0*/
   and a.diagnostic_dept_name = /*diagnosticDeptName*/
   /*END*/
   /*IF diagnosisDoctor != null && diagnosisDoctor.length() != 0*/
   and a.diagnosis_doctor_name like '%' ||  /*diagnosisDoctor*/ || '%'
   /*END*/
   /*IF diagnosisDate != null*/
   and a.diagnosis_date > to_date(/*diagnosisDate*/,'yyyy-MM-dd hh24:mi:ss')
   /*END*/
   /*IF diagnosisDate1 != null*/
   and a.diagnosis_date < to_date(/*diagnosisDate1*/,'yyyy-MM-dd hh24:mi:ss')
   /*END*/
   /*IF check1 != null && check1.length() != 0*/
   and a.uncertain_flag = /*check1*/
   /*END*/
   /*IF check2 != null && check2.length() != 0*/
   and a.contagious_flag = /*check2*/
   /*END*/
   /*IF mainDiagnosisFlag != null && mainDiagnosisFlag.length() != 0*/
   and a.main_diagnosis_flag = /*mainDiagnosisFlag*/
   /*END*/
   order by a.diagnosis_date desc, a.diagnosis_sn desc