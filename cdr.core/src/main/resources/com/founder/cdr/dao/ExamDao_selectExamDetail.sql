/**
 * @version 1.0, 2012/12/21 
 * @author 魏鹏
 * @since 1.0
 * 检查报告信息
 */
select ei.examination_method_name,
       ei.examining_doctor_name,
       ei.imaging_finding as ei_imaging_finding,
       ei.imaging_conclusion as ei_imaging_conclusion,
       ei.image_index_no,
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
       er.document_sn,
       er.data_source_type,
       er.item_Class,
       er.report_memo,
       er.withdraw_flag,
       er.reagent_code,
       er.reagent_name,
       er.reagent_dosage,
       er.reagent_dosage_unit,
       er.org_code
  from examination_result er , examination_item ei 
 WHERE er.delete_flag = '0' and ei.delete_flag(+) = '0' 
   and ei.exam_report_sn(+) = er.exam_report_sn
   /*IF reportSn != null && reportSn.length() != 0*/
   and er.exam_report_sn = /*reportSn*/
   /*END*/
   /*IF itemSn != null && itemSn.length() != 0*/
   and ei.item_sn = /*itemSn*/
   /*END*/
 order by ei.item_sn desc   