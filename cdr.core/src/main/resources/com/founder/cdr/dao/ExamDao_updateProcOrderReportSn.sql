/**
 * $Author :jin_peng
 * $Date : 2012/11/23 13:59$
 * [BUG]0011864 MODIFY BEGIN
 * $Author :jin_peng
 * $Date : 2012/10/19 17:47$
 * [BUG]0010594 ADD BEGIN
 * 更新手术医嘱表中检查报告内部序列号
 */
update procedure_order po
   set po.exam_report_sn = /*reportSn*/'01',
       po.update_time    = sysdate,
       po.update_count   = po.update_count + 1
 where /*IF outpatientDomain.equals(patientDomain)*/
 		 po.request_no in /*orderLidList*/(10, 20, 30)
	   --ELSE
		 po.order_lid in /*orderLidList*/(10, 20, 30)
	   /*END*/
   and po.patient_domain = /*patientDomain*/'01'
   and po.patient_lid = /*patientLid*/'4' 
   and po.delete_flag = 0
/**[BUG]0009691 ADD END*/
/**[BUG]0011864 MODIFY END*/