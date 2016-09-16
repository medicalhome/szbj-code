/**
 * 
 * 根据visit_sn，得到药物医嘱信息
 * @version 1.0, 2012/09/27  
 * @author zhangbin
 * @since 1.0
 */
SELECT mo.order_sn,
       mo.approved_Drug_Name,
       mo.drug_Name,
       mo.medicine_Type_Name,
       mo.medicine_Form,
       mo.route_Name,
       mo.dosage,
       mo.dosage_unit,
       mo.total_dosage,
       mo.total_dosage_unit,
       mo.medicine_Frenquency,
       mo.medicine_freq_name,
       mo.temporary_Flag,
       mo.order_Person,
       mo.order_person_name,
       mo.order_Time,
       mo.exec_Dept,
       mo.order_Dept_name,
       mo.prescription_sn,
       mo.order_type,
       mo.english_name,
       mo.days
       --mo.dispensing_sn
  FROM medication_order mo
 WHERE mo.delete_flag = 0 and mo.visit_sn = /*visitSn*/ 
   and not exists
      (select 1
         from hide_drug hd
        where mo.drug_code = hd.drug_code
          and hd.delete_flag = '0'
          and hd.user_name = /*userSn*/)
   order by mo.order_time desc, mo.order_sn desc


  