/** 获取有效的门急诊条数  */
select count(*) from medical_visit mv
 where mv.patient_sn = /*patientSn*/
   and mv.delete_flag = '0'
   and mv.visit_type_code <> /*visitTypeCode*/'03'
   and (exists (select 'X' from diagnosis where diagnosis.delete_flag = 0 and diagnosis.visit_sn  = mv.visit_sn)
    or exists (select 'X' from examination_order eo where eo.delete_flag = 0 and eo.visit_sn  = mv.visit_sn)
    or exists (select 'X' from lab_order lo where lo.delete_flag = 0 and lo.visit_sn  = mv.visit_sn)
    or mv.visit_date >= to_date(/*sysDate*/''||'00-00-00', 'YYYY-MM-DD hh24:mi:ss'))
