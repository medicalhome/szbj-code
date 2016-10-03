/**
 * 检验申请单列表
 * [FUN]V05-101检验申请单列表
 * @version 1.0, 2012/09/17  
 * @author 佟萌
 * @since 1.0
 * 检验申请单列表检索结果
 */
SELECT * 
  FROM lab_order 
 WHERE delete_flag = '0' 
   AND request_no IN /*requestNoList*/(10,20,30)
   AND patient_domain = /*patientDomain*/
   AND patient_lid = /*patientLid*/
   AND org_code = /*orgCode*/