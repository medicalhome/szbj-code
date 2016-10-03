/**
 * $Author : wu_jianfeng
 * $Date : 2012/09/17 14:10$
 * [BUG]0009752 ADD BEGIN
 * [FUN]V03-001患者列表
 * @version 1.0, 2012/03/02  
 * @author wu_jianfeng
 * @since 1.0
 * 在院患者列表结果(按入院日期降序排列)
 */
select p.patient_sn,
       p.patient_eid,
       p.patient_name,
       p.birth_date,
       p.gender_Name,
       mv.visit_dept_name,
       mv.visit_date,
       mv.discharge_dept_name,
       mv.discharge_ward_name,
       mv.discharge_bed_no,
       mv.org_code
  from patient p,
       (select patient_sn,visit_dept_name,visit_date,discharge_dept_name,discharge_ward_name,discharge_bed_no,org_code
          from (select row_number() OVER(partition by patient_sn order by visit_date desc) ACOL,
                       patient_sn,visit_dept_name,visit_date,discharge_dept_name,discharge_ward_name,discharge_bed_no,org_code
                  from medical_visit
				  where 
				  	/*IF acl.patientScopeAuth05 == false && (acl.patientScopeAuth01 || acl.patientScopeAuth03) && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
					  	visit_sn in (
					 	/*IF acl.patientScopeAuth01 && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
						  	select visit_sn from medical_visit
						  	where delete_flag = '0'
		                        and visit_type_code in /*patientListSearchPra.visitTypes*/(10, 20, 30)
						        and visit_status_code in /*patientListSearchPra.visitStatus*/(10, 20, 30)
								and patient_sn in (select patient_sn 
		 	     				   from patient_doctor 
		 	     				   where doctor_code = /*patientListSearchPra.doctorId*/
		 	     				   and exists 
			 	     				   (
			 	     				   		select 1
			 	     				   		  from medical_visit mvt
			 	     				   		 where mvt.visit_sn = visit_sn
			 	     				   		   and mvt.patient_domain in /*patientListSearchPra.patientDomains*/(10,20,30)
			 	     				   		   and mvt.delete_flag = 0
			 	     				   )
		 	     				   and delete_flag = '0')
		 	     				/*IF patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
		 	     				and discharge_dept_id in /*patientListSearchPra.deptIds*/(10,20,30)
		 	     				/*END*/
				     	/*END*/
		 	     		/*IF acl.patientScopeAuth01 && acl.patientScopeAuth03 && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
		 	     			union all
		 	     		/*END*/
					 	/*IF acl.patientScopeAuth03 && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
						  	select visit_sn from medical_visit
						  	where delete_flag = '0'
		                        and visit_type_code in /*patientListSearchPra.visitTypes*/(10, 20, 30)
						        and visit_status_code in /*patientListSearchPra.visitStatus*/(10, 20, 30)
						        and discharge_dept_id in /*patientListSearchPra.deptIds*/(10,20,30)
						/*END*/
						)
		     	  --ELSE
		     	  		delete_flag = '0'
                        and visit_type_code in /*patientListSearchPra.visitTypes*/(10, 20, 30)
				        and visit_status_code in /*patientListSearchPra.visitStatus*/(10, 20, 30)
		     	  /*END*/
				  /*IF patientListSearchPra.dischargeBedNo != NULL && patientListSearchPra.dischargeBedNo.length() != 0*/
				        and discharge_bed_no = /*patientListSearchPra.dischargeBedNo*/
				  /*END*/
				  /*IF patientListSearchPra.deptId != NULL && patientListSearchPra.deptId.length() != 0*/
				     	and discharge_dept_id = /*patientListSearchPra.deptId*/
				  /*END*/
				  /*IF patientListSearchPra.dischargeWardId != NULL && patientListSearchPra.dischargeWardId.length() != 0*/
				     	and discharge_ward_id = /*patientListSearchPra.dischargeWardId*/
				  /*END*/
				  /*IF patientListSearchPra.orgCode != NULL && patientListSearchPra.orgCode.length() != 0*/
				     	and org_code = /*patientListSearchPra.orgCode*/
				  /*END*/
                  --order by visit_date desc
                  ) mv_temp
          where ACOL = 1) mv
  where mv.patient_sn = p.patient_sn
    /*IF patientListSearchPra.patientName != NULL && patientListSearchPra.patientName.length() != 0*/
    and p.patient_name like /*patientListSearchPra.patientName*/''
    /*END*/
    /*IF patientListSearchPra.patientEId != NULL && patientListSearchPra.patientEId.length() != 0*/
      	and p.patient_eid = /*patientListSearchPra.patientEId*/''
    /*END*/
  	/*IF patientListSearchPra.inpatientNo != NULL && patientListSearchPra.inpatientNo.length() != 0*/
    and exists (select 'X' from patient_cross_index pci 
        where pci.patient_eid = p.patient_eid
          and pci.delete_flag = '0'
          and pci.patient_domain in /*patientListSearchPra.patientDomains*/(10,20,30)
          and pci.inpatient_no = /*patientListSearchPra.inpatientNo*/
      )
    /*END*/
    and p.delete_flag = '0'
  order by mv.visit_date desc
/**[BUG]0009752 ADD END*/
  