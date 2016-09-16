/**
 * 
 * [FUN]V03-001患者列表
 * @version 1.0, 2012/03/02  
 * @author wu_jianfeng
 * @since 1.0
 * 患者列表结果
 */
select p.patient_sn, 
	   p.patient_eid, 
	   p.patient_name, 
	   p.birth_date, 
	   p.gender_code, 
	   p.gender_name,
	   p.nation_code, 
	   p.marriage_status,
	   p.birth_place, 
	   p.occupation_code, 
	   p.education_degree, 
	   mv.visit_dept_id,
	   mv.visit_type_code,
	   mv.visit_date,
	   mv.discharge_date,
	   mv.admission_bed_no
  from patient p left join 
     (select * from medical_visit where delete_flag = '0') mv on p.patient_sn = mv.patient_sn 
 where not exists (select 1 from medical_visit mv1 where mv1.delete_flag = '0' and mv.patient_sn = mv1.patient_sn and mv.visit_date < mv1.visit_date)
   and p.delete_flag = '0'
 /*IF patientName != NULL && patientName.length() != 0*/
   and p.patient_name like /*patientName*/
  	 /*END*/
 /*IF genderCode != NULL && genderCode.length() != 0*/
   and p.gender_code = /*genderCode*/
 /*END*/
 /*IF visitStartDate != NULL && visitStartDate.length() != 0*/
   and mv.visit_date >= to_date(/*visitStartDate*/, 'YYYY-MM-DD')
 /*END*/
 /*IF visitEndDate != NULL && visitEndDate.length() != 0*/
   and mv.discharge_date <= to_date(/*visitEndDate*/, 'YYYY-MM-DD')
 /*END*/
 order by mv.visit_date desc
     