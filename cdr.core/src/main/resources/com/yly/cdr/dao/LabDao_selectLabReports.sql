/**
 * 通过检验医嘱查找检验报告集合
 */
select lr.*
  from lab_order lo,lab_order_lab_result lolr,lab_result lr
 where lo.order_sn = lolr.lab_order_sn
   and lolr.lab_report_sn = lr.lab_report_sn
   and lo.delete_flag = '0'
   and lolr.delete_flag = '0'
   and lr.delete_flag = '0'
   and lo.order_sn = /*orderSn*/