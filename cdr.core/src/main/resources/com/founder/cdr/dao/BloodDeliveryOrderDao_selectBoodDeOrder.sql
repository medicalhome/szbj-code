/**
 * [FUN]V05-101就诊列表
 * @version 1.0, 2012/02/29  
 * @author 郭红燕
 * @since 1.0
 * 取血单
 */
select bdo.order_sn,
       bdo.request_sn,
       bdo.order_lid,
       bdo.patient_name,
       bdo.patient_gender_name,
       bdo.patient_age,
       bdo.order_dept_name,
       bdo.bed_no,
       bdo.transfusion_components,
       bdo.delivery_time,
       bdo.delivery_quality,
       bdo.delivery_unit,
       bdo.request_doctor_name,
       bdo.app_sender_name,
       bdo.app_receiver_name,
       bdo.create_time,
       bdo.update_time,
       bdo.update_count,
       bdo.delete_flag,
       bdo.delete_time,
       bdo.ward_name,
       bdo.blood_delivery_count,
       br.request_no,
       br.blood_class_name,
       br.blood_reason_name,
       br.quantity,
       br.quantity_unit,
       br.clinical_diagnosis,
       br.patient_domain
  from blood_delivery_order bdo, blood_request br
 where bdo.request_sn = br.request_sn
   and bdo.delete_flag = 0
   and br.delete_flag = 0
   and br.visit_sn = /*visitSn*/
   and br.request_sn = /*requestSn*/
 order by bdo.blood_delivery_count
