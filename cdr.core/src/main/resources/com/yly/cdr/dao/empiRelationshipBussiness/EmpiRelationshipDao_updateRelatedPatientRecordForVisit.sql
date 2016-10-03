/**
 * [FUN]V05-101 empi动态更新相应业务表 根据就诊sn
 * @version 1.0, 2014/04/18  
 * @author 金鹏
 * @since 1.0 
 * empi更新相应业务表
 */
begin
	
	execute immediate 'update (select ah.*
          from ' || /*tableName*/ || ' ah, medical_visit mv
         where mv.visit_sn = ah.visit_sn
           and mv.patient_lid = :1
           and mv.patient_domain = :2
           and mv.org_code = :3
           and ah.delete_flag = 0
           and mv.delete_flag = 0) res
   		set res.patient_sn   = :4,
       		res.update_time  = to_date(:5,''yyyy-mm-dd hh24:mi:ss''),
      	 	res.update_count = res.update_count + 1'
	    /*IF serviceId != null && serviceId.length() != 0*/
	       || ', res.updateby = ' || '''' || /*serviceId*/ || ''''
	    /*END*/
	using /*patientLid*/, /*patientDomain*/, /*orgCode*/, /*patientSn*/, '' || /*updateTime*/ || '';

end;;