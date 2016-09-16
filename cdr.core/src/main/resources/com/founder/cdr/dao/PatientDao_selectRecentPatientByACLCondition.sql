/**
 * 
 * [FUN]V03-001患者列表
 * @version 1.0, 2012/03/02  
 * @author wu_jianfeng
 * @since 1.0
 * 最近患者列表结果(按就诊日期降序排列)
 */
select res.patient_sn,
       res.patient_eid,
       res.patient_name,
       res.birth_date,
       res.gender_Name,
       res.patient_domain,
       res.visit_dept_name,
       res.visit_date,
       res.discharge_dept_name,
       res.discharge_ward_name,
       res.discharge_bed_no,
       res.org_code,
       res.visit_type_code
  from (
select p.patient_sn,
       p.patient_eid,
       p.patient_name,
       p.birth_date,
       p.gender_Name,
       mv.patient_domain,
       mv.visit_dept_name,
       mv.visit_date,
       mv.discharge_dept_name,
       mv.discharge_ward_name,
       mv.discharge_bed_no,
       mv.org_code,
       mv.visit_type_code,
       row_number() OVER(partition by p.patient_sn order by mv.visit_date desc) ACOL
  from patient p,
       	(
	       select patient_sn,visit_dept_name,visit_date,discharge_dept_name,discharge_ward_name,discharge_bed_no,patient_domain,org_code,visit_type_code
	         from (
	       /*IF acl.patientScopeAuth05 || acl.patientScopeAuth01 || acl.patientScopeAuth02*/
	        select /*+ INDEX(MEDICAL_VISIT,MEDICAL_VISIT_IDX06) */ patient_sn,visit_dept_name,visit_date,discharge_dept_name,discharge_ward_name,discharge_bed_no,patient_domain,org_code,visit_type_code
		      from medical_visit
		      where delete_flag = '0'
		            and patient_domain in /*patientListSearchPra.outpatientDomains*/(10, 20, 30)
		            and visit_date >=
		                  to_date(/*patientListSearchPra.beginDateText*/'' || '00-00-00',
		                     'YYYY-MM-DD hh24:mi:ss')
		            and visit_date <=
		                  to_date(/*patientListSearchPra.endDateText*/'' || '23-59-59',
		                   'YYYY-MM-DD hh24:mi:ss')
		            /*IF acl.patientScopeAuth05 == false && (acl.patientScopeAuth01 || (acl.patientScopeAuth02 && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0))*/
				    and ( 
					 	/*BEGIN*/
						 	/*IF acl.patientScopeAuth01*/
					             visit_doctor_id = /*patientListSearchPra.doctorId*/
					     	/*END*/
					        /*IF acl.patientScopeAuth01 && acl.patientScopeAuth02 && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
		 	     				   or
						     	/*END*/
						 	/*IF acl.patientScopeAuth02 && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
								 visit_dept_id in /*patientListSearchPra.deptIds*/(10,20,30)
					     	/*END*/
				     	/*END*/
			        )
			        /*END*/
		        /*IF patientListSearchPra.deptId != NULL && patientListSearchPra.deptId.length() != 0*/
			     	and visit_dept_id = /*patientListSearchPra.deptId*/
			    /*END*/
			    /*IF patientListSearchPra.wardId != NULL && patientListSearchPra.wardId.length() != 0*/
			     	and discharge_ward_id = /*patientListSearchPra.wardId*/
			    /*END*/
		      --order by visit_date desc
		   	/*END*/
	/*IF acl.patientScopeAuth05 || acl.patientScopeAuth01 || acl.patientScopeAuth06*/
		         union all
	/*END*/     	
	       /*IF acl.patientScopeAuth05 || acl.patientScopeAuth01 || acl.patientScopeAuth06*/
		    select /*+ INDEX(MEDICAL_VISIT,MEDICAL_VISIT_IDX06) */ patient_sn,visit_dept_name,visit_date,discharge_dept_name,discharge_ward_name,discharge_bed_no,patient_domain,org_code,visit_type_code
	          from medical_visit
	          where 
	          		/*IF (acl.patientScopeAuth01 || acl.patientScopeAuth06) && acl.patientScopeAuth05 == false && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0 */
	      				visit_sn in (
	      				    	  /*IF acl.patientScopeAuth01 && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0 */
		              					select visit_sn from medical_visit
		              					 where delete_flag = '0'
						                       and patient_domain in /*patientListSearchPra.inpatientDomains*/(10, 20, 30)
						                       and visit_status_code = /*patientListSearchPra.visitStatusCodeDischarge*/
						                       and discharge_date >=
						                              to_date(/*patientListSearchPra.beginDateText*/'' || '00-00-00',
						                                 'YYYY-MM-DD hh24:mi:ss')
						                       and discharge_date <=
						                              to_date(/*patientListSearchPra.endDateText*/'' || '23-59-59',
						                               'YYYY-MM-DD hh24:mi:ss')
										       		and patient_sn in (select patient_sn 
								 	     				   from patient_doctor 
								 	     				   where doctor_code = /*patientListSearchPra.doctorId*/
								 	     				   and exists 
									 	     				   (
									 	     				   		select 1
									 	     				   		  from medical_visit mvt
									 	     				   		 where mvt.visit_sn = visit_sn
									 	     				   		   and mvt.patient_domain in /*patientListSearchPra.inpatientDomains*/(10, 20, 30)
									 	     				   		   and mvt.delete_flag = 0
									 	     				   )
								 	     				   and delete_flag = '0')
											   /*IF patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
											 		and discharge_dept_id in /*patientListSearchPra.deptIds*/(10,20,30)
											   /*END*/
							       /*END*/
								   /*IF acl.patientScopeAuth01 && acl.patientScopeAuth06 && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
					 	     			union all
					 	           /*END*/
								   /*IF acl.patientScopeAuth06 && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
				 	    				select visit_sn from medical_visit
									  	 where delete_flag = '0'
									  	   and patient_domain in /*patientListSearchPra.inpatientDomains*/(10, 20, 30)
					                       and visit_status_code = /*patientListSearchPra.visitStatusCodeDischarge*/
					                       and discharge_date >=
					                              to_date(/*patientListSearchPra.beginDateText*/'' || '00-00-00',
					                                 'YYYY-MM-DD hh24:mi:ss')
					                       and discharge_date <=
					                              to_date(/*patientListSearchPra.endDateText*/'' || '23-59-59',
					                               'YYYY-MM-DD hh24:mi:ss')
									       and discharge_dept_id in /*patientListSearchPra.deptIds*/(10,20,30)
								   /*END*/
								)
					--ELSE
						delete_flag = '0'
	                    and patient_domain in /*patientListSearchPra.inpatientDomains*/(10, 20, 30)
	                    and visit_status_code = /*patientListSearchPra.visitStatusCodeDischarge*/
	                    and discharge_date >=
	                          to_date(/*patientListSearchPra.beginDateText*/'' || '00-00-00',
	                             'YYYY-MM-DD hh24:mi:ss')
	                    and discharge_date <=
	                          to_date(/*patientListSearchPra.endDateText*/'' || '23-59-59',
	                           'YYYY-MM-DD hh24:mi:ss')
					/*END*/
				 	/*IF patientListSearchPra.deptId != NULL && patientListSearchPra.deptId.length() != 0*/
				     	and discharge_dept_id = /*patientListSearchPra.deptId*/
				    /*END*/
				    /*IF patientListSearchPra.wardId != NULL && patientListSearchPra.wardId.length() != 0*/
			     		and discharge_ward_id = /*patientListSearchPra.wardId*/
			    	/*END*/
	          		--order by visit_date desc
		    	/*END*/
	          )mv_all
	      --where mv_all.ACOL = 1
	      /*IF patientListSearchPra.orgCode != NULL && patientListSearchPra.orgCode.length() != 0*/
		    where org_code = /*patientListSearchPra.orgCode*/
		  /*END*/
	  ) mv
  where mv.patient_sn = p.patient_sn
    /*IF patientListSearchPra.patientName != NULL && patientListSearchPra.patientName.length() != 0*/
    and p.patient_name like /*patientListSearchPra.patientName*/''
    /*END*/
    /*IF patientListSearchPra.patientEId != NULL && patientListSearchPra.patientEId.length() != 0*/
    and p.patient_eid = /*patientListSearchPra.patientEId*/''
    /*END*/
    /*IF !"-1".equals(patientListSearchPra.visitNoFlag) && patientListSearchPra.visitNoFlag != NULL && patientListSearchPra.visitNoFlag.length() != 0*/
    and exists (select 'X' from patient_cross_index pci 
        where pci.patient_eid = p.patient_eid
          and pci.patient_domain = mv.patient_domain
          and pci.delete_flag = '0'
          /*IF patientListSearchPra.outpatientNo != NULL && patientListSearchPra.outpatientNo.length() != 0*/
          and pci.patient_domain in /*patientListSearchPra.outpatientDomains*/(10,20,30)
          and pci.outpatient_no = /*patientListSearchPra.outpatientNo*/
          /*END*/
          /*IF patientListSearchPra.inpatientNo != NULL && patientListSearchPra.inpatientNo.length() != 0*/
          and pci.patient_domain in /*patientListSearchPra.inpatientDomains*/(10,20,30)
          and pci.inpatient_no = /*patientListSearchPra.inpatientNo*/
          /*END*/
      )
    /*END*/
    and p.delete_flag = '0'
  order by mv.visit_date desc) res
  where res.ACOL = 1