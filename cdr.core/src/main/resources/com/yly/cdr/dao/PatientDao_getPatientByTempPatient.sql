/**
 * [FUN]A01-002挂号信息 
 * @version 1.0, 2012/05/07  
 * @author 吴建峰
 * @since 1.0
 */
select p.*
  from patient p, patient_temp pt
 where p.patient_sn = pt.patient_sn
   and pt.patient_domain = /*patientDomain*/
   and pt.patient_lid = /*patientLid*/
   and pt.org_code = /*orgCode*/
   and p.delete_flag = 0
   and pt.delete_flag = 0