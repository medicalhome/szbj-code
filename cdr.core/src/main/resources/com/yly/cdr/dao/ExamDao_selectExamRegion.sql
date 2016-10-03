/**
 * 
 * [FUN]V05-101就诊列表
 * @version 1.0, 2012/03/16  
 * @author 郭红燕1
 * @since 1.0
 * 检查-关联查询
 */
select eo.item_name, 
	   er.examination_dept, 
	   eo.region_name, 
	   eo.exam_method_name, 
	   eo.order_person,
	   er.examination_date, 
	   er.report_doctor, 
	   er.report_date, 
	   er.review_doctor, 
	   er.review_date, 
	   eo.diagnosis, 
	   er.report_memo, 
	   er.exam_report_sn, 
	   eo.region_code, 
       er.patient_sn 
  from examination_result er,
  	   examination_order eo,
  	   exam_order_exam_result eoer
 where eo.order_sn = eoer.exam_order_sn
   and eoer.exam_result_sn = er.exam_report_sn
   and er.visit_sn = eo.visit_sn
   and er.delete_flag = '0'
   and eoer.delete_flag = '0'
   and eo.delete_flag = '0'   
   and er.patient_sn = /*patientSn*/
   and er.examination_region = /*examinationRegion*/
 order by eo.order_sn desc
 
