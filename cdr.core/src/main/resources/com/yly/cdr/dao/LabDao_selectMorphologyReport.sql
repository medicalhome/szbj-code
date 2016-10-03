/**
 * @version 1.0, 2012/09/11  
 * @author 魏鹏
 * @since 1.0
 * 形态学报告详细信息
 */
SELECT lr.sampling_part,
	   lr.sampling_time,
	   lr.sample_type_name,
	   lr.patient_domain,
	   lr.patient_lid,
	   lr.ward_name,
	   lr.bed_no,
	   lr.receive_time,
	   lr.report_no,
	   lr.diagnosis_text,
	   lrci.item_name,
	   lrci.item_code,
	   lrci.test_method,
	   lrci.test_method_desc,
	   lrci.preparation_method,
	   lr.test_results,
	   lr.reporter_name,
	   lr.reviewer_name,
	   lr.report_date,
	   lr.review_date,
	   lr.tester_name,
	   lr.phenomenon_performance,
	   lr.submitting_person_name,
	   lr.lab_dept,
	   lr.sample_no,
	   lr.laboratory_no,
	   lr.report_memo,
	   lr.memo,
	   lr.supplier_name,
	   lr.supplier_gender,
	   lr.supplier_age,
	   lr.test_date,
	   lr.last_menstrual_time,
	   lr.menopause,
	   lr.test_results_detail,
	   lr.expected_age,
	   lr.fetal_number,
	   lr.gestational_week_calc_method,
	   lr.report_type_code
  FROM lab_result lr,lab_result_composite_item lrci
 WHERE lr.delete_flag = '0'
   AND lrci.delete_flag = '0'
   AND lr.lab_report_sn = lrci.lab_report_sn
   AND lr.lab_report_sn = /*labReportSn*/
 ORDER BY lr.report_date desc, lr.lab_report_sn desc