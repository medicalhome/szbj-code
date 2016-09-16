/**
 * $Author :jin_peng
 * $Date : 2012/11/23 13:59$
 * [BUG]0011864 MODIFY BEGIN
 * $Author :jin_peng
 * $Date : 2012/09/20 14:47$
 * [BUG]0009691 MODIFY BEGIN
 * 
 * 更新检查医嘱表中检查报告内部序列号
 * guo_hongyan
 */
select * 
  from examination_order
 where 
 	   order_lid in /*orderLidList*/(10, 20, 30)	
       and patient_domain = /*patientDomain*/'01'
       and patient_lid = /*patientLid*/'4' 
       and org_code = /*orgCode*/
       and delete_flag = 0
       and visit_sn = /*visitSn*/
/**[BUG]0009691 MODIFY END*/
/**[BUG]0011864 MODIFY END*/