/**
 * 
 * [FUN]V05-101就诊列表
 * @version 1.0, 2012/03/12  
 * @author 郭红燕
 * @since 1.0
 * 就诊详细
 */
select mv.patient_sn, 
       mv.visit_type_code,
       mv.visit_type_name,
       mv.visit_times,
       mv.admission_doctor_name,
       mv.resident_doctor_name,
       mv.visit_date,
       mv.visit_dept_name,
       mv.admission_ward_id,
       mv.admission_bed_no,
       mv.attending_doctor_name,
       mv.dept_director_name,
       mv.discharge_date,
       mv.discharge_dept_name,
       mv.discharge_ward_Id,
       mv.discharge_status_name,
       mv.visit_status_name,
       mv.admission_ward_Name,
       mv.discharge_ward_name,
       mv.registration_Class_Name,
       mv.visit_status_code,
       mv.urgent_level_name,
       mv.visit_doctor_name,
       mv.visit_sn,
       mv.charge_class_name,
       mv.discharge_bed_no,
       mv.org_code,
       mv.org_name
  from medical_visit mv
 where mv.delete_flag = '0'
   and mv.visit_sn = /*visitSn*/1
 order by mv.visit_date desc