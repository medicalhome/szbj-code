/**
 * 
 * [FUN]V05-101就诊列表
 * @version 1.0, 2012/03/02  
 * @author 郭红燕
 * @since 1.0
 * 就诊检索结果
 */
select mv.visit_sn,
       mv.visit_type_name,
       mv.visit_times,
       mv.visit_doctor_name,
       mv.visit_date,
       mv.visit_dept,
       mv.visit_status_name
  from medical_visit mv
 where mv.patient_sn = /*patientSn*/
   and mv.delete_flag = '0'
   /*IF visitDateFromStr != null && visitDateFromStr.length() != 0*/
   and mv.visit_date >= to_date(/*visitDateFromStr*/,'YYYY/MM/DD')
   /*END*/
   /*IF visitDateToStr != null && visitDateToStr.length() != 0*/
   and mv.visit_date < to_date(/*visitDateToStr*/,'YYYY/MM/DD')
   /*END*/
   /*IF visitType != null && visitType.length() != 0*/
   and mv.visit_type = /*visitType*/
   /*END*/
   /*IF visitDept != null && visitDept.length() != 0*/
   and ((mv.visit_dept = /*visitDept*/) or (mv.registered_dept = /*visitDept*/))
   /*END*/
   /*IF dischargeDateFromStr != NULL && dischargeDateFromStr.length() != 0*/
   and mv.discharge_date >= to_date(/*dischargeDateFromStr*/,'YYYY/MM/DD')
   /*END*/
   /*IF dischargeDateToStr != NULL && dischargeDateToStr.length() != 0*/
   and mv.discharge_date < to_date(/*dischargeDateToStr*/,'YYYY/MM/DD')
   /*END*/
   /*IF visitDoctorName != NULL && visitDoctorName.length() != 0*/
   and mv.visit_doctor_name = /*visitDoctorName*/
   /*END*/
   /*IF registrationClass != NULL && registrationClass.length() != 0*/
   and mv.registration_class = /*registrationClass*/
   /*END*/
 order by mv.visit_date desc
