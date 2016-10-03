select lo.*
  from lab_order lo,
	   lab_order_lab_result lolr
 where lo.order_sn = lolr.lab_order_sn
   and lo.delete_flag = '0'
   and lolr.delete_flag = '0'
   and lolr.lab_report_sn = /*labReportSn*/
   and lo.item_code= /*itemCode*/