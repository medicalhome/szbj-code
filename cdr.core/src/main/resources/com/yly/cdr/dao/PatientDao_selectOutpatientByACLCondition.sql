/**
 * $Author : wu_jianfeng
 * $Date : 2012/09/17 14:10$
 * [BUG]0009752 ADD BEGIN
 * [FUN]V03-001患者列表
 * @version 1.0, 2012/03/02  
 * @author wu_jianfeng
 * @since 1.0
 * 门诊患者列表结果(按就诊日期降序排列)
 */
select p.patient_sn,
       p.patient_eid,
       p.patient_name,
       p.birth_date,
       p.gender_Name,
       mv.visit_dept_name,
       mv.visit_date,
       mv.admission_ward_name,
       mv.org_code
  from patient p,
       (select patient_sn,visit_dept_name,visit_date,admission_ward_name,org_code
          from (select /*+ INDEX(MEDICAL_VISIT,MEDICAL_VISIT_IDX06) */ row_number() OVER(partition by patient_sn order by visit_date desc) ACOL,
                       patient_sn,visit_dept_name,visit_date,admission_ward_name,org_code
                  from medical_visit
                  where delete_flag = '0'
                        and visit_type_code in /*patientListSearchPra.visitTypes*/(10, 20, 30)
                        and visit_date >=
                              to_date(/*patientListSearchPra.visitStartDate*/'' || '00-00-00',
                                 'YYYY-MM-DD hh24:mi:ss')
                        and visit_date <=
                              to_date(/*patientListSearchPra.visitEndDate*/'' || '23-59-59',
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
				     	/*IF patientListSearchPra.deptName != NULL && patientListSearchPra.deptName.length() != 0*/
				     		and visit_dept_name like /*patientListSearchPra.deptName*/ || '%'
				     	/*END*/
				     	/*IF patientListSearchPra.deptId != NULL && patientListSearchPra.deptId.length() != 0*/
					 		and visit_dept_id = /*patientListSearchPra.deptId*/
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
  	/*IF patientListSearchPra.outpatientNo != NULL && patientListSearchPra.outpatientNo.length() != 0*/
    and exists (select 'X' from patient_cross_index pci 
        where pci.patient_eid = p.patient_eid
          and pci.delete_flag = '0'
          and pci.patient_domain in /*patientListSearchPra.patientDomains*/(10,20,30)
          and pci.outpatient_no = /*patientListSearchPra.outpatientNo*/
      )
    /*END*/
    and p.delete_flag = '0'
  order by mv.visit_date desc
/**[BUG]0009752 ADD END*/     