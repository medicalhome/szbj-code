/**
 * 
 * 根据visit_sn，得到检查信息
 * @version 1.0, 2013/01/28  
 * @author binzhang
 * @modifier wei_peng
 * @since 1.0
 */
select exams.examination_item,
       exams.examination_item_name,
       exams.examination_region,
       exams.examination_region_name,
       exams.order_sn,
       exams.order_status_name,
       exams.order_time,
       exams.order_dept_name,
       exams.request_date,
       exams.examining_doctor_name,
       exams.examination_method_name,
       exams.item_sn,
       exams.ei_imaging_finding,
       exams.ei_imaging_conclusion,
       exams.patient_sn,
       exams.withdraw_flag,
       exams.examination_dept,
       exams.exam_dept_name,
       exams.examination_date,
       exams.report_doctor_name,
       exams.report_date,
       exams.review_doctor_name,
       exams.review_date,
       exams.er_imaging_finding,
       exams.er_imaging_conclusion,
       exams.exam_report_sn,
       exams.item_class,
       exams.item_class_name,
       exams.image_index_no,
       exams.signature_id
  from
  (
	select eo.item_code as examination_item,
	       eo.item_name as examination_item_name,
	       eo.region_code as examination_region,
	       eo.region_name as examination_region_name,
	       eo.order_sn,
	       eo.order_status_name,
	       eo.order_time,
	       eo.order_dept_name,
	       eo.request_date,
	       ei.examining_doctor_name,
	       nvl(ei.examination_method_name,eo.exam_method_name) as examination_method_name,
	       ei.item_sn,
	       ei.imaging_finding as ei_imaging_finding,
	       ei.imaging_conclusion as ei_imaging_conclusion,
	       eo.patient_sn,
	       er.withdraw_flag,
	       er.examination_dept,
	       nvl(er.exam_dept_name,eo.exec_dept_name) as exam_dept_name,
	       er.examination_date,
	       er.report_doctor_name,
	       er.report_date,
	       er.review_doctor_name,
	       er.review_date,
	       er.imaging_finding as er_imaging_finding,
	       er.imaging_conclusion as er_imaging_conclusion,
	       er.exam_report_sn,
	       er.item_class,
	       er.item_class_name,
	       ei.image_index_no,
	       cd.signature_id
	  from examination_order eo,
	  	   exam_order_exam_result eoer,
	       examination_result er,
	       clinical_document cd,
	       examination_item ei       
	 where eo.order_sn=eoer.exam_order_sn(+)
	   and eoer.exam_result_sn=er.exam_report_sn(+)
	   and er.exam_report_sn=ei.exam_report_sn(+)
	   and er.document_sn = cd.document_sn(+)
	   and eo.delete_flag = '0' 
	   and eoer.delete_flag(+) = '0'
	   and er.delete_flag(+) = '0'
	   and ei.delete_flag(+) = '0'
	   and cd.delete_flag(+) = '0'
	   and eo.visit_sn = /*visitSn*/
   
    union all
 
	select null as examination_item,
	       null as examination_item_name,
	       null as examination_region,
	       null as examination_region_name,
	       null as order_sn,
	       null as order_status_name, 
	       null as order_time,
	       null as order_dept_name,
	       null as request_date,
		   ei.examining_doctor_name,
	       ei.examination_method_name,
	       ei.item_sn,
	       ei.imaging_finding as ei_imaging_finding,
	       ei.imaging_conclusion as ei_imaging_conclusion,
	       er.patient_sn,
	       er.withdraw_flag,
	       er.examination_dept,
	       er.exam_dept_name,
	       er.examination_date,
	       er.report_doctor_name,
	       er.report_date,
	       er.review_doctor_name,
	       er.review_date,
	       er.imaging_finding as er_imaging_finding,
	       er.imaging_conclusion as er_imaging_conclusion,
	       er.exam_report_sn,
	       er.item_class,
	       er.item_class_name,
	       ei.image_index_no,
	       cd.signature_id
	  from examination_result er,
	       clinical_document cd,
	       examination_item ei
	 where not exists (select * from exam_order_exam_result where exam_result_sn = er.exam_report_sn) 
	   and er.exam_report_sn = ei.exam_report_sn(+)
	   and er.document_sn = cd.document_sn(+)
	   and er.delete_flag = '0' 
	   and ei.delete_flag(+) = '0'
	   and cd.delete_flag(+) = '0'
	   and er.visit_sn = /*visitSn*/
  ) exams
order by exams.order_time desc,exams.order_sn desc,exams.examination_date desc,exams.exam_report_sn desc, exams.item_sn desc
    
   