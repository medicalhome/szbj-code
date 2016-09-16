/**
 * [FUN]V05-101用血申请单列表
 * @version 1.0, 2012/02/29  
 * @author 张彬
 * @since 1.0
 * 用血申请单
 */
select br.visit_Sn,
       br.request_sn,
       br.order_lid,
       br.patient_Sn,
       br.patient_Domain,
       br.patient_Lid,
       br.request_No,
       br.blood_Abo_name,
       br.blood_Rh_name,
       br.blood_Class_name,
       br.quantity,
       br.quantity_unit,
       br.blood_reason_name,
       br.transfusion_Date,
       br.request_Dept_name,
       br.request_Person_Name,
       br.request_Date,
       br.urgency_name,
       br.description,
       br.create_Time,
       br.update_Time,
       br.update_count,
       br.delete_flag,
       br.delete_time,
       br.request_no,
       br.order_Lid,
       br.order_Status_name,
       br.pregnancy_flag,
       br.pregnancy_count,
       br.childbirth_flag,
       br.childbirth_count,
       br.history_flag,
       br.adverse_history_flag,
       br.newborn_flag,
       br.other_disease_flag,
       br.other_disease_name,
       br.cross_match_blood_flag,
       br.cross_match_blood_name,
       br.reserve_flag,
       br.reserve_name,
       br.stem_cell_transplant_flag,
       br.stem_cell_transplant_name,       
       br.self_blood_flag,
       br.clinical_diagnosis,
       bsr.special_Request
       
  from(select * from blood_request where delete_flag = 0)  br left outer join 
    (select request_sn,wm_concat(special_Request) as special_Request  from blood_special_request where delete_flag = 0 group by request_sn) bsr on br.request_sn = bsr.request_sn
 where 
   br.visit_sn = /*visitSn*/ and br.patient_sn=/*patientSn*/