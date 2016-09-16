/**
 * [FUN]CVIS 检查报告
 * @version 1.0, 2013/5/31  
 * @author tong_meng
 * @since 1.0
 * CVIS 检查报告（包括超声，冠脉造影等）
 */
/*IF itemClass.equals('DSA')*/
select er.report_type_code,
       er.ward_name,
       er.bed_no,
       err.exam_result_procedure_sn,
       err.operation_room_name,
       err.operation_date,
       err.operator_doctor_name,
       err.operator_assistant_name,
       err.image_index_no,
	   er.data_source_type,
       er.exam_report_sn,
       er.report_doctor_name,
       er.review_doctor_name,
       er.item_class,
       er.examination_date,
       er.imaging_conclusion,
       er.imaging_finding,
       er.report_date,
       er.patient_age,
       er.org_code
  from examination_result er,
       examination_result_procedure err 
 where er.patient_sn = /*patientSn*/ 
   and er.item_class in /*itemClass*/(10,20,30)
   and err.exam_report_sn = /*reportSn*/
   and err.exam_report_sn = er.exam_report_sn
   and err.delete_flag = '0'
   and er.delete_flag = '0'
/*END*/

/*IF itemClass.equals('EC')*/
select er.data_source_type,
       er.exam_report_sn,
       er.report_doctor_name,
       er.review_doctor_name,
       er.item_class,
       er.bed_no,
       er.ward_name,
       er.examination_date,
       er.imaging_conclusion,
       er.imaging_finding,
       er.report_date,
       er.patient_age,
       er.org_code
  from examination_result er,
  	   examination_item ei
 where er.patient_sn = /*patientSn*/ 
   and er.item_class in /*itemClass*/(10,20,30)
   and ei.exam_report_sn(+) = /*reportSn*/
   and ei.exam_report_sn(+) = er.exam_report_sn
   and ei.item_sn = /*itemSn*/
   and ei.delete_flag = '0'
   and er.delete_flag = '0'
/*END*/