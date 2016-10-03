/**
 * 
 * [FUN]V05-101就诊列表
 * @version 1.0, 2012/02/29  
 * @author 郭红燕
 * @since 1.0
 * 就诊检索结果
 */
select * from (
select mv.visit_date,
       mv.discharge_date,
       mv.visit_type_name,
       mv.visit_type_code,
       mv.visit_sn,
       mv.visit_dept_name,
       mv.visit_doctor_name,
       mv.visit_times
  from medical_visit mv
 where mv.delete_flag = '0' 
   and mv.patient_sn = /*visitListSearchPra.patientSn*/
   and mv.visit_type_code in /*visitListSearchPra.visitTypeInpatientCode*/(10,20,30)
union all
select mv.visit_date,
       mv.discharge_date,
       mv.visit_type_name,
       mv.visit_type_code,
       mv.visit_sn,
       mv.visit_dept_name,
       mv.visit_doctor_name,
       mv.visit_times
  from medical_visit mv
 where mv.delete_flag = '0' 
   and mv.patient_sn = /*visitListSearchPra.patientSn*/
   and mv.visit_type_code not in /*visitListSearchPra.visitTypeInpatientCode*/(10,20,30)
   and (exists (select 'X' from diagnosis where diagnosis.delete_flag = 0 and diagnosis.visit_sn  = mv.visit_sn)
    or exists (select 'X' from examination_order eo where eo.delete_flag = 0 and eo.visit_sn  = mv.visit_sn)
    or exists (select 'X' from lab_order lo where lo.delete_flag = 0 and lo.visit_sn  = mv.visit_sn)
   or mv.visit_date >= to_date(/*visitListSearchPra.sysDate*/''||'00-00-00', 'YYYY-MM-DD hh24:mi:ss'))
) mv1 
 order by mv1.visit_date desc, mv1.visit_sn desc