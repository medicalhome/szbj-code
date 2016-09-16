/**
 * [FUN]V05-101 empi动态更新相应业务表 根据就诊sn
 * @version 1.0, 2014/04/18  
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
		    'where exists (
		            select 1
		              from (
		                   select p.patient_sn
		                    from patient p, patient_cross_index pci
		                   where p.patient_eid = pci.patient_eid
		                     and pci.patient_lid = :3
		                     and pci.patient_domain = :4
		                     and pci.org_code = :5
		                     and p.delete_flag = 0
		                     and pci.delete_flag = 0
		              ) rec
		            where ah.patient_sn = rec.patient_sn
		          )
		      and ah.delete_flag = 0'
	using /*patientSn*/, /*updateTime*/, /*patientLid*/, /*patientDomain*/, '' || /*orgCode*/ || '';

end;;