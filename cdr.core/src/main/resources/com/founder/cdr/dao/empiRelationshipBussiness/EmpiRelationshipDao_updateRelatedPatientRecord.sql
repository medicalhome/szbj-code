/**
 * [FUN]V05-101 empi动态更新相应业务表
 * @version 1.0, 2014/04/17  
 * @author 金鹏
 * @since 1.0 
 * empi更新相应业务表
 */
begin
	
	execute immediate 'update ' || /*tableName*/ || ' ah
	   set ah.patient_sn = :1,
	       ah.update_time = to_date(:2,''yyyy-mm-dd hh24:mi:ss''),
	       ah.update_count = ah.update_count + 1' ||
	 /*IF serviceId != null && serviceId.length() != 0*/
	      ', ah.updateby = ' || '''' || /*serviceId*/ || '''' ||
	 /*END*/
	 'where ah.patient_lid = :3
	   and ah.patient_domain = :4
	   and ah.org_code = :5
	   and ah.delete_flag = 0'
	using /*patientSn*/, /*updateTime*/, /*patientLid*/, /*patientDomain*/, '' || /*orgCode*/ || '';

end;;