/**
 * 
 * 根据visit_sn，得到检验信息
 * @version 1.0, 2013/01/28  
 * @author 张彬
 * @modifier wei_peng
 * @since 1.0
 * 门诊检验列表
 */
SELECT labs.lab_dept,
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
	   labs.item_code
  FROM (
		SELECT laborder.* FROM 
		   (SELECT row_number() OVER(partition by lo.order_sn order by lr.report_date desc) ACOL,
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
				   NULL as composite_item_sn,
				   lr.lab_report_sn,
				   cd.signature_id,
				   NULL as item_code
			  FROM lab_order lo,
			       lab_result lr,
			       clinical_document cd,
			       lab_order_lab_result lolr
			 WHERE lo.order_sn=lolr.lab_order_sn(+)
		  	   AND lolr.lab_report_sn=lr.lab_report_sn(+)
			   AND lr.document_sn=cd.document_sn(+)
			   AND lo.delete_flag = '0'
			   AND lolr.delete_flag(+) = '0'
			   AND lr.delete_flag(+) = '0'
			   AND cd.delete_flag(+) = '0'
			   AND lo.visit_sn = /*visitSn*/
		     ORDER BY lr.report_date DESC) laborder 
	    WHERE ACOL = 1

        UNION ALL

		SELECT NULL AS ACOL, 
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
			   lrci.item_code
		  FROM lab_result lr,
		       clinical_document cd,
		       lab_result_composite_item lrci
		 WHERE NOT EXISTS (SELECT * FROM lab_order_lab_result WHERE lab_report_sn = lr.lab_report_sn) 
		 	   AND lr.document_sn=cd.document_sn(+)
		       AND lr.lab_report_sn=lrci.lab_report_sn(+)
		       AND lr.delete_flag = '0'
		       AND lrci.delete_flag(+) = '0'
		       AND cd.delete_flag(+) = '0'
			   AND lr.visit_sn = /*visitSn*/
        ) labs
ORDER BY labs.order_time DESC,labs.order_sn DESC,labs.test_date DESC, labs.lab_report_sn DESC,labs.composite_item_sn DESC 
