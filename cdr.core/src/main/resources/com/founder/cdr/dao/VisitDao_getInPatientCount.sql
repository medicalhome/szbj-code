/** 获取有效住院条数 */
select count(*)  from medical_visit mv
 where mv.patient_sn = /*patientSn*/
   and mv.delete_flag = '0'
   and mv.visit_type_code = /*visitTypeCode*/'03'