/**
 * $Author :jin_peng
 * $Date : 2012/11/07 11:07$
 * [BUG]0011177 MODIFY BEGIN
 * [FUN]V05-101检验列表 
 * @version 1.0, 2012/03/14  
 * @author 金鹏
 * @since 1.0
 * 检验列表检索结果
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
	   labs.createby as source_system_id,
	   labs.composite_item_sn,
	   labs.lab_report_sn,
	   labs.signature_id,
	   labs.item_code,
	   labs.org_code,
	   /*$Author :yang_mingjie
         $Date : 2014/07/15 15:05$
         [BUG]0045623 MODIFY BEGIN */
	   labs.sample_type_name
	   /*[BUG]0045623 MODIFY END*/
  FROM (
 	SELECT laborder.* FROM 
	   (SELECT row_number() OVER(partition by lo.order_sn order by nvl(lr.report_date,lo.request_date) desc) ACOL,
	   /*$Author :yang_mingjie
         $Date : 2014/07/15 15:05$
         [BUG]0045623 MODIFY BEGIN */
	    	   lr.sample_type_name,
	    /*[BUG]0045623 MODIFY END*/
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
			   lr.createby,
			   NULL AS composite_item_sn,
			   lr.lab_report_sn,
			   cd.signature_id,
			   NULL AS item_code,
			   lo.org_code
	 	  FROM lab_order lo,
		       lab_order_lab_result lolr,
		       lab_result lr,
		       clinical_document cd
		 WHERE lo.order_sn=lolr.lab_order_sn(+)
		   AND lolr.lab_report_sn=lr.lab_report_sn(+)
		   AND lr.document_sn=cd.document_sn(+)
		   AND lo.delete_flag = '0'
		   AND lolr.delete_flag(+) = '0'
		   AND lr.delete_flag(+) = '0'
		   AND cd.delete_flag(+) = '0'
		   AND lo.patient_sn = /*patientSn*/ 
		   /*IF labListSearchParameters.orderExecId != null && labListSearchParameters.orderExecId.length() != 0 */
	   	   AND lo.order_exec_id = /*labListSearchParameters.orderExecId*/
	   	   /*END*/
	   	   /*IF labListSearchParameters.orderExecList != null && labListSearchParameters.orderExecList.size() > 0 */
	   	   AND lo.order_exec_id in /*labListSearchParameters.orderExecList*/(10,20,30)
	   	   /*END*/
		   /*IF labListSearchParameters.labDept != null && labListSearchParameters.labDept.length() != 0 */
	   	   AND lr.lab_dept = /*labListSearchParameters.labDept*/
	   	   /*END*/
	   	   /*IF labListSearchParameters.itemName != null && labListSearchParameters.itemName.length() != 0*/
	   	   AND lo.item_name  LIKE '%' || /*labListSearchParameters.itemName*/ || '%'
	   	   /*END*/
	   	   /*IF labListSearchParameters.testDateFrom != null && labListSearchParameters.testDateFrom.length() != 0*/
	   	   AND lr.test_date >= to_date(/*labListSearchParameters.testDateFrom*/''||'00-00-00','yyyy-MM-dd hh24:mi:ss')
	   	   /*END*/
	   	   /*IF labListSearchParameters.testDateTo != null && labListSearchParameters.testDateTo.length() != 0*/
	   	   AND lr.test_date <= to_date(/*labListSearchParameters.testDateTo*/''||'23-59-59','yyyy-MM-dd hh24:mi:ss')
	   	   /*END*/
	   	   /*IF labListSearchParameters.testerName != null && labListSearchParameters.testerName.length() != 0*/
	   	   AND lr.tester_name LIKE '%' || /*labListSearchParameters.testerName*/ || '%'
	   	   /*END*/
	   	   /*IF labListSearchParameters.reporterName != null && labListSearchParameters.reporterName.length() != 0*/
	   	   AND lr.reporter_name LIKE '%' || /*labListSearchParameters.reporterName*/ || '%'
	   	   /*END*/
	   	   /*IF labListSearchParameters.reportDateFrom != null && labListSearchParameters.reportDateFrom.length() != 0*/
	   	   AND lr.report_date >= to_date(/*labListSearchParameters.reportDateFrom*/''||'00-00-00','yyyy-MM-dd hh24:mi:ss')
	   	   /*END*/
	   	   /*IF labListSearchParameters.reportDateTo != null && labListSearchParameters.reportDateTo.length() != 0*/
	   	   AND lr.report_date <= to_date(/*labListSearchParameters.reportDateTo*/''||'23-59-59','yyyy-MM-dd hh24:mi:ss')
	   	   /*END*/
	   	   /*IF labListSearchParameters.notInLabDepts != null && labListSearchParameters.notInLabDepts.size != 0*/
	   	   AND (lr.lab_dept NOT IN /*labListSearchParameters.notInLabDepts*/(10,20,30)  OR  lr.lab_dept IS NULL)
	   	   /*END*/
	   	   /*IF labListSearchParameters.sourceSystemId != null && labListSearchParameters.sourceSystemId.length() != 0*/
	   	   AND lr.source_system_id = /*labListSearchParameters.sourceSystemId*/
	   	   /*END*/
	   	   /*IF labListSearchParameters.orderPersonName != NULL && labListSearchParameters.orderPersonName.length() != 0*/
		   AND lo.order_Person_Name like '%' || /*labListSearchParameters.orderPersonName*/'' || '%'
		   /*END*/
		   /*IF labListSearchParameters.orderDept != NULL && labListSearchParameters.orderDept.length() != 0*/
		   AND lo.order_Dept = /*labListSearchParameters.orderDept*/
		   /*END*/
	   	   
	   	   /*IF labListSearchParameters.visitSn != null && labListSearchParameters.visitSn.length() != 0*/
		   AND lo.visit_sn = /*labListSearchParameters.visitSn*/
		   /*END*/
		   /*IF labListSearchParameters.orgCode != null && labListSearchParameters.orgCode.length() != 0*/
		   AND lo.org_code = /*labListSearchParameters.orgCode*/
		   /*END*/
		   /*IF labListSearchParameters.inpatientDate != null && labListSearchParameters.inpatientDate.length() != 0*/
		   AND lo.order_time BETWEEN TO_DATE(/*labListSearchParameters.inpatientDate*/,'YYYY-MM-DD') AND TO_DATE(/*labListSearchParameters.inpatientDate*/ || '23:59:59', 'YYYY-MM-DD HH24:MI:SS')
		   /*END*/
	 	 ORDER BY lr.report_date DESC) laborder 
	WHERE ACOL = 1

	/*IF labListSearchParameters.orderExecId == null || labListSearchParameters.orderExecId.length() == 0*/
	/*IF labListSearchParameters.visitSn == null || labListSearchParameters.visitSn.length() == 0*/
	 UNION ALL

	SELECT NULL AS ACOL,
	/*$Author :yang_mingjie
         $Date : 2014/07/15 15:05$
         [BUG]0045623 MODIFY BEGIN */
		   lr.sample_type_name,
    /*[BUG]0045623 MODIFY END*/
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
		   lr.createby,
		   lrci.composite_item_sn,
		   lr.lab_report_sn,
		   cd.signature_id,
		   lrci.item_code,
		   lr.org_code
	  FROM lab_result lr,
	       clinical_document cd,
	       lab_result_composite_item lrci
	 WHERE NOT EXISTS (SELECT * FROM lab_order_lab_result WHERE lab_report_sn = lr.lab_report_sn) 
 	   AND lr.document_sn=cd.document_sn(+)
       AND lr.lab_report_sn=lrci.lab_report_sn(+)
       AND lr.delete_flag = '0'
       AND lrci.delete_flag(+) = '0'
       AND cd.delete_flag(+) = '0'
 	   AND lr.patient_sn = /*patientSn*/ 
 	   /*IF labListSearchParameters.labDept != null && labListSearchParameters.labDept.length() != 0 */
   	   AND lr.lab_dept = /*labListSearchParameters.labDept*/
   	   /*END*/
   	   /*IF labListSearchParameters.itemName != null && labListSearchParameters.itemName.length() != 0*/
   	   AND lrci.item_name  LIKE '%' || /*labListSearchParameters.itemName*/ || '%'
   	   /*END*/
   	   /*IF labListSearchParameters.testDateFrom != null && labListSearchParameters.testDateFrom.length() != 0*/
   	   AND lr.test_date >= to_date(/*labListSearchParameters.testDateFrom*/''||'00-00-00','yyyy-MM-dd hh24:mi:ss')
   	   /*END*/
   	   /*IF labListSearchParameters.testDateTo != null && labListSearchParameters.testDateTo.length() != 0*/
   	   AND lr.test_date <= to_date(/*labListSearchParameters.testDateTo*/''||'23-59-59','yyyy-MM-dd hh24:mi:ss')
   	   /*END*/
   	   /*IF labListSearchParameters.testerName != null && labListSearchParameters.testerName.length() != 0*/
   	   AND lr.tester_name LIKE '%' || /*labListSearchParameters.testerName*/ || '%'
   	   /*END*/
   	   /*IF labListSearchParameters.reporterName != null && labListSearchParameters.reporterName.length() != 0*/
   	   AND lr.reporter_name LIKE '%' || /*labListSearchParameters.reporterName*/ || '%'
   	   /*END*/
   	   /*IF labListSearchParameters.reportDateFrom != null && labListSearchParameters.reportDateFrom.length() != 0*/
   	   AND lr.report_date >= to_date(/*labListSearchParameters.reportDateFrom*/''||'00-00-00','yyyy-MM-dd hh24:mi:ss')
   	   /*END*/
   	   /*IF labListSearchParameters.reportDateTo != null && labListSearchParameters.reportDateTo.length() != 0*/
   	   AND lr.report_date <= to_date(/*labListSearchParameters.reportDateTo*/''||'23-59-59','yyyy-MM-dd hh24:mi:ss')
   	   /*END*/
   	   /*IF labListSearchParameters.notInLabDepts != null && labListSearchParameters.notInLabDepts.size != 0*/
   	   AND (lr.lab_dept NOT IN /*labListSearchParameters.notInLabDepts*/(10,20,30)  OR  lr.lab_dept IS NULL)
   	   /*END*/
   	   /*IF labListSearchParameters.sourceSystemId != null && labListSearchParameters.sourceSystemId.length() != 0*/
   	   AND lr.source_system_id = /*labListSearchParameters.sourceSystemId*/
   	   /*END*/
   	   /*IF labListSearchParameters.visitSn != null && labListSearchParameters.visitSn.length() != 0*/
	   AND lr.visit_sn = /*labListSearchParameters.visitSn*/
	   /*END*/
	   /*IF labListSearchParameters.orgCode != null && labListSearchParameters.orgCode.length() != 0*/
	   AND lr.org_code = /*labListSearchParameters.orgCode*/
	   /*END*/
	   /*IF labListSearchParameters.inpatientDate != null && labListSearchParameters.inpatientDate.length() != 0*/
	   AND lr.report_date BETWEEN TO_DATE(/*labListSearchParameters.inpatientDate*/,'YYYY-MM-DD') AND TO_DATE(/*labListSearchParameters.inpatientDate*/ || '23:59:59', 'YYYY-MM-DD HH24:MI:SS')
	   /*END*/
 /*END*/
/*END*/) labs
 ORDER BY nvl(labs.report_date,labs.request_date) DESC,labs.order_time DESC,labs.order_sn DESC,labs.test_date DESC, labs.lab_report_sn DESC,labs.composite_item_sn DESC 
/**[BUG]0011177 MODIFY END*/