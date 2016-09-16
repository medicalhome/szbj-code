/**
 * Author:jin_peng
 * Date:2013/01/29 15:57
 * [BUG]0013729 MODIFY BEGIN
 * @version 1.0, 2012/12/21  
 * @author 魏鹏
 * @since 1.0
 * 检查-关联信息
 */
select eo.item_name as examination_item_name,
       eo.region_name as examination_region_name,
       ei.examination_method_name,
       ei.examining_doctor_name,
       ei.imaging_finding as ei_imaging_finding,
       ei.imaging_conclusion as ei_imaging_conclusion,
       er.examination_dept,
       er.exam_dept_name,
       er.examination_date,
       er.report_doctor_name,
       er.report_date,
       er.review_doctor_name,
       er.review_date,
       er.second_review_doctor_name,
       er.second_review_date,
       er.third_review_doctor_name,
       er.third_review_date,
       er.imaging_finding as er_imaging_finding,
       er.imaging_conclusion as er_imaging_conclusion, 
       er.exam_report_sn,
       er.report_memo,
       er.withdraw_flag,
       er.reagent_code,
       er.reagent_name,
       er.reagent_dosage,
       er.reagent_dosage_unit
  from examination_result er, 
  	   examination_item ei,
  	   examination_order eo,
  	   exam_order_exam_result eoer
 where eo.order_sn = eoer.exam_order_sn
   and eoer.exam_result_sn = er.exam_report_sn
   and er.exam_report_sn = ei.exam_report_sn(+)
   and eo.delete_flag = '0'
   and eoer.delete_flag = '0'
   and er.delete_flag = '0' 
   and ei.delete_flag(+) = '0' 
   and eo.order_sn != /*orderSn*/
   /*IF patientSn != null && patientSn.length() != 0*/
   and eo.patient_sn = /*patientSn*/
   /*END*/
   /*IF examinationItem != null && examinationItem.length() != 0*/
   and eo.item_code = /*examinationItem*/
   /*END*/
   /*IF examinationRegion != null && examinationRegion.length() != 0*/
   and eo.region_code = /*examinationRegion*/
   /*END*/
 order by er.report_date desc  
 /**[BUG]0013729 MODIFY END*/