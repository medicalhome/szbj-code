/**
 * [FUN]V05-101 empi更新相应业务表
 * @version 1.0, 2013/02/26  
 * @author 金鹏
 * @since 1.0 
 * empi更新相应业务表
 */
update care_order ah
   set ah.patient_sn = /*patientSn*/,
       ah.update_time = to_date(/*updateTime*/,'yyyy-mm-dd hh24:mi:ss'),
       ah.update_count = ah.update_count + 1,
       ah.updateby = /*serviceid*/
 where ah.patient_lid = /*patientLid*/
   and ah.patient_domain = /*patientDomain*/
   and ah.org_code = /*orgCode*/
   and ah.delete_flag = 0