

/**
 * 
 * [FUN]V05-101就诊列表
 * @version 1.0, 2012/02/29  
 * @author 郭红燕
 * @since 1.0
 * 就诊检索结果
 */
SELECT mo.order_sn,
       mo.visit_sn,
       mo.approved_Drug_Name,
       mo.medicine_Type_Name,
       mo.medicine_Form,
       mo.route_code,
       mo.route_Name,
       mo.dosage,
       mo.dosage_Unit,
       mo.total_Dosage,
       mo.total_Dosage_Unit,
       mo.medicine_Frenquency,
       mo.temporary_Flag,
       mo.order_Person,
       mo.order_person_name,
       mo.order_Time,
       mo.exec_Dept,
       mo.exec_Dept_name,
       mo.prescription_sn,
       mo.days,
       mo.dispensing_quantity,
       mo.dispensing_unit,
       mo.drug_Code, 
       mo.drug_Name,
       mv.visit_type_code,
       mv.visit_type_name,
       mo.medicine_Class,
       mo.medicine_Class_name,
       mo.skin_Test_Flag,
       mo.skin_Test_Result,
       mo.order_Dept,
       mo.order_Dept_name,
       mo.comments,
       mo.stop_Person_Name,
       mo.order_start_time,
       mo.order_end_time,
       mo.stop_Time,
       mo.nurse_Confirm_Person_Name,
       mo.nurse_Confirm_Time,
       mo.doctor_confirm_person_name,
       mo.doctor_confirm_time,
       mo.wards_Id,
       mo.ward_name,
       mo.urgent_flag,
       mo.med_view_flag,
       mo.adaptive_flag,
       mo.mutexes_order_type,
       mo.mutexes_order_type_name,
       mo.medicine_frenquency,
       mo.medicine_Freq_Name,
       mo.cancel_person_name,
       mo.cancel_time,
       mo.order_status_name,
       mo.order_type,
       mo.charge_status_name,
       mo.charge_status_code,
       p.prescription_type_name,
       p.prescription_type_code,
       p.prescription_no,
       mo.parent_order_sn,
       mo.mutexes_order_sn,
       mo.mutexes_order_no_type,
       mo.org_code,
       mo.org_name,
       mo.order_lid,
       mo.order_type,
       mo.order_type_name,
       mo.patient_domain,
       mv.visit_type_code
  FROM medication_order mo,medical_visit mv,
       prescription p
 WHERE mo.order_sn=/*orderSn*/'' 
   and mo.visit_sn=mv.visit_sn
   and p.prescription_sn(+)=mo.prescription_sn
   and mo.delete_flag=0
   and mv.delete_flag=0
   and p.delete_flag(+)=0
   and not exists
      (select 1
         from hide_drug hd
        where mo.drug_code = hd.drug_code
          and hd.delete_flag = '0'
          and hd.user_name = /*userSn*/)
