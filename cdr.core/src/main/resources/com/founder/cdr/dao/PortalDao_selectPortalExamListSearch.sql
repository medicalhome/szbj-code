/**
 * $Author :jin_peng
 * $Date : 2012/11/07 11:07$ 
 * [BUG]0011177 MODIFY BEGIN
 * [FUN]V05-101就诊列表
 * @version 1.0, 2012/03/15  
 * @author王盟
 * @since 1.0
 * 检查检索结果
 */
select exams.patient_domain,
	   exams.examination_item,
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
       exams.signature_id,
       exams.org_code
  from
(
select  er.patient_domain,
	   eo.item_code as examination_item,
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
       cd.signature_id,
       eo.org_code
  from examination_order eo,
       exam_order_exam_result eoer,
       examination_result er,
       clinical_document cd,
       examination_item ei,
       medical_visit mv
 where eo.order_sn = eoer.exam_order_sn(+)
   and eoer.exam_result_sn = er.exam_report_sn(+)
   and er.exam_report_sn = ei.exam_report_sn(+)
   and er.document_sn = cd.document_sn(+)
   and mv.visit_sn = er.visit_sn
   and eo.order_status_name ='检查报告已审核'
   and eo.delete_flag = '0' 
   and eoer.delete_flag(+) = '0'
   and er.delete_flag(+) = '0'
   and ei.delete_flag(+) = '0'
   and cd.delete_flag(+) = '0'
   and mv.delete_flag(+) ='0'
   and eo.patient_sn = /*patientSn*/
    /*IF examListSearchParameters.visitTimes != null && examListSearchParameters.visitTimes.length() != 0*/
   and mv.visit_times = /*examListSearchParameters.visitTimes*/
   /*END*/
   /*IF examListSearchParameters.examinationDept != null && examListSearchParameters.examinationDept.length() != 0*/
   and er.item_class = /*examListSearchParameters.examinationDept*/
   /*END*/
   /*IF examListSearchParameters.examinationTypes != null && examListSearchParameters.examinationTypes.size != 0 && "5".equals(examListSearchParameters.menuNum)*/
   and (er.item_class in /*examListSearchParameters.examinationTypes*/(10,20,30) or er.item_class is null)
   /*END*/
   /*IF examListSearchParameters.examinationTypes != null && examListSearchParameters.examinationTypes.size != 0 && !"5".equals(examListSearchParameters.menuNum)*/
   and eo.item_class in /*examListSearchParameters.examinationTypes*/(10,20,30) 
   /*END*/
   /*IF examListSearchParameters.examinationItem != null && examListSearchParameters.examinationItem.length() != 0*/
   and eo.item_name like  '%' || /*examListSearchParameters.examinationItem*/ || '%'
   /*END*/
   
   /*IF examListSearchParameters.requestDateFrom != null && examListSearchParameters.requestDateFrom.length() != 0*/
   and eo.request_date >= to_date(/*examListSearchParameters.requestDateFrom*/''||'00-00-00','yyyy-MM-dd hh24:mi:ss')
   /*END*/   
   /*IF examListSearchParameters.requestDateTo != null && examListSearchParameters.requestDateTo.length() != 0*/
   and eo.request_date < to_date(/*examListSearchParameters.requestDateTo*/''||'23-59-59','yyyy-MM-dd hh24:mi:ss')
   /*END*/ 
   
 /*IF examListSearchParameters.visitSn == null || examListSearchParameters.visitSn.length() == 0*/
 /*IF (examListSearchParameters.examinationRegion == null || examListSearchParameters.examinationRegion.length() == 0) && (examListSearchParameters.examinationItem == null || examListSearchParameters.examinationItem.length() == 0) */
 union all
 
 select er.patient_domain,
 		null   as examination_item,
        po.order_name   as examination_item_name,
        null   as examination_region_name,
        null  as examination_region,
        po.order_sn,
        po.order_status_name,
        po.order_time,
        po.order_dept_name,
        po.request_date,
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
       cd.signature_id,
       er.org_code
  from 
  	   /*增加手术遗嘱表*/
  	   procedure_order po,
  	   examination_result er,
       clinical_document cd,
       examination_item ei,
       medical_visit mv
 where not exists (select * from exam_order_exam_result where exam_result_sn = er.exam_report_sn) 
   and po.exam_report_sn = er.exam_report_sn(+)
   and er.exam_report_sn = ei.exam_report_sn(+)
   and er.document_sn = cd.document_sn(+)
   and mv.visit_sn = er.visit_sn
   and mv.delete_flag='0'
   and er.delete_flag = '0' 
   and ei.delete_flag(+) = '0'
   and cd.delete_flag(+) = '0'
   and er.patient_sn = /*patientSn*/
    /*IF examListSearchParameters.visitTimes != null && examListSearchParameters.visitTimes.length() != 0*/
   and mv.visit_times = /*examListSearchParameters.visitTimes*/
   /*END*/
   /*IF examListSearchParameters.examinationDept != null && examListSearchParameters.examinationDept.length() != 0*/
   and er.item_class = /*examListSearchParameters.examinationDept*/
   /*END*/
   /*IF examListSearchParameters.examinationTypes != null && examListSearchParameters.examinationTypes.size != 0 && "5".equals(examListSearchParameters.menuNum)*/
   and (er.item_class in /*examListSearchParameters.examinationTypes*/(10,20,30) or er.item_class is null)
   /*END*/
   /*IF examListSearchParameters.examinationTypes != null && examListSearchParameters.examinationTypes.size != 0 && !"5".equals(examListSearchParameters.menuNum)*/
   and er.item_class in /*examListSearchParameters.examinationTypes*/(10,20,30) 
   /*END*/
   /*END*/
   /*END*/
   ) exams
   order by nvl(exams.report_date,exams.request_date) desc,exams.order_time desc,exams.order_sn desc,exams.examination_date desc,exams.exam_report_sn desc, exams.item_sn desc
/**[BUG]0011177 MODIFY END*/