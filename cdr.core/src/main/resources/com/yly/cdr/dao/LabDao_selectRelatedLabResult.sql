SELECT lri.item_name_cn,
	   lri.qualitative_results,
	   lri.item_value,
	   lri.low_value,
	   lri.high_value,
	   lr.report_date as test_date
  FROM lab_result lr,lab_result_composite_item lrci,lab_result_item lri 
 WHERE lr.delete_flag = '0' 
   AND lrci.delete_flag = '0' 
   AND lri.delete_flag = '0' 
   AND lr.lab_report_sn = lrci.lab_report_sn
   AND lrci.composite_item_sn = lri.lab_result_composite_item_sn
   AND lri.item_code = /*itemCode*/
   AND lr.patient_sn = /*patientSn*/
   AND lri.item_name_cn = /*itemName*/
 ORDER BY lr.report_date DESC