/**
 * $Author :wang.meng
 * $Date : 2012/11/07 11:07$
 * [BUG]0011177 MODIFY BEGIN
 * [FUN]V05-101检验列表 
 * @version 1.0, 2012/03/14  
 * @author 王盟
 * @since 1.0
 * 检验列表检索结果
 */
SELECT labs.patient_domain,
	   labs.lab_dept,
       labs.lab_dept_name,
	   labs.tester_name,
	   labs.reporter_name,
	   labs.reviewer_id,
	   labs.request_time,
	   labs.item_name,
	   labs.order_sn,
	   labs.order_status_name,
	   labs.order_time,
	   labs.order_dept_name,
	   labs.request_date,
	   labs.test_date,
	   labs.report_date,
	   labs.withdraw_flag,
	   labs.source_system_id,
	   labs.composite_item_sn,
	   labs.lab_report_sn,
	   labs.signature_id,
	   labs.item_code,
	   labs.org_code
  FROM (
 	SELECT laborder.* FROM 
	   (SELECT row_number() OVER(partition by lo.order_sn order by nvl(lr.report_date,lo.request_date) desc) ACOL,
	   	       lr.patient_domain,
	   		   lr.lab_dept,
		       lr.lab_dept_name,
			   lr.tester_name,
			   lr.reporter_name,
			   lr.reviewer_id,
			   lr.request_time,
			   lo.item_name,
			   lo.order_sn,
			   lo.order_status_name,
			   lo.order_time,
			   lo.order_dept_name,
			   lo.request_date,
			   lr.test_date,
			   lr.report_date,
			   lr.withdraw_flag,
			   lr.source_system_id,
			   NULL AS composite_item_sn,
			   lr.lab_report_sn,
			   cd.signature_id,
			   NULL AS item_code,
			   lo.org_code
	 	  FROM lab_order lo,
		       lab_order_lab_result lolr,
		       lab_result lr,
		       clinical_document cd,
		       medical_visit mv
		 WHERE lo.order_sn=lolr.lab_order_sn(+)
		   AND lolr.lab_report_sn=lr.lab_report_sn(+)
		   AND lr.document_sn=cd.document_sn(+)
		   AND mv.visit_sn = lr.visit_sn
		   AND lo.order_status_name ='检验报告已审核'
		   AND lo.delete_flag = '0'
		   AND lolr.delete_flag(+) = '0'
		   AND lr.delete_flag(+) = '0'
		   AND cd.delete_flag(+) = '0'
		   AND mv.delete_flag(+) ='0'
		   AND lo.patient_sn = /*patientSn*/
		   /*IF labListSearchParameters.visitTimes != null && labListSearchParameters.visitTimes.length() != 0*/
           AND mv.visit_times = /*labListSearchParameters.visitTimes*/
          /*END*/
	   	   /*IF labListSearchParameters.itemName != null && labListSearchParameters.itemName.length() != 0*/
	   	   AND lo.item_name  LIKE '%' || /*labListSearchParameters.itemName*/ || '%'
	   	   /*END*/
	   	   /*IF labListSearchParameters.requestDateFrom != null && labListSearchParameters.requestDateFrom.length() != 0*/
	   	   AND lo.request_date >= to_date(/*labListSearchParameters.requestDateFrom*/''||'00-00-00','yyyy-MM-dd hh24:mi:ss')
	   	   /*END*/
	   	   /*IF labListSearchParameters.requestDateTo != null && labListSearchParameters.requestDateTo.length() != 0*/
	   	   AND lo.request_date <= to_date(/*labListSearchParameters.requestDateTo*/''||'23-59-59','yyyy-MM-dd hh24:mi:ss')
	   	   /*END*/
	   	   /*IF labListSearchParameters.sourceSystemId != null && labListSearchParameters.sourceSystemId.length() != 0*/
	   	   AND lr.source_system_id = /*labListSearchParameters.sourceSystemId*/''
	   	   /*END*/
	 	 ORDER BY lo.request_date DESC) laborder 
	WHERE ACOL = 1

	/*IF labListSearchParameters.visitSn == null || labListSearchParameters.visitSn.length() == 0*/
	 UNION ALL

	SELECT NULL AS ACOL,
		   lr.patient_domain,
		   lr.lab_dept,
	       lr.lab_dept_name,
		   lr.tester_name,
		   lr.reporter_name,
		   lr.reviewer_id,
		   lr.request_time,
		   lrci.item_name,
		   NULL AS order_sn,
		   NULL AS order_status_name,
		   NULL AS order_time,
		   NULL AS order_dept_name,
		   NULL AS request_date,
		   lr.test_date,
		   lr.report_date,
		   lr.withdraw_flag,
		   lr.source_system_id,
		   lrci.composite_item_sn,
		   lr.lab_report_sn,
		   cd.signature_id,
		   lrci.item_code,
		   lr.org_code
	  FROM lab_result lr,
	       clinical_document cd,
	       lab_result_composite_item lrci,
	       medical_visit mv
	 WHERE NOT EXISTS (SELECT * FROM lab_order_lab_result WHERE lab_report_sn = lr.lab_report_sn) 
 	   AND lr.document_sn=cd.document_sn(+)
       AND lr.lab_report_sn=lrci.lab_report_sn(+)
       AND mv.visit_sn = lr.visit_sn
       AND mv.delete_flag='0'
       AND lr.delete_flag = '0'
       AND lrci.delete_flag(+) = '0'
       AND cd.delete_flag(+) = '0'
 	   AND lr.patient_sn = /*patientSn*/ 
 	   /*IF labListSearchParameters.visitTimes != null && labListSearchParameters.visitTimes.length() != 0*/
       AND mv.visit_times = /*labListSearchParameters.visitTimes*/
       /*END*/
   	   /*IF labListSearchParameters.itemName != null && labListSearchParameters.itemName.length() != 0*/
   	   AND lrci.item_name  LIKE '%' || /*labListSearchParameters.itemName*/ || '%'
   	   /*END*/
   	   /*IF labListSearchParameters.sourceSystemId != null && labListSearchParameters.sourceSystemId.length() != 0*/
   	   AND lr.source_system_id = /*labListSearchParameters.sourceSystemId*/''
   	   /*END*/
/*END*/
) labs
 ORDER BY nvl(labs.report_date,labs.request_date) DESC,labs.order_time DESC,labs.order_sn DESC,labs.test_date DESC, labs.lab_report_sn DESC,labs.composite_item_sn DESC 
/**[BUG]0011177 MODIFY END*/