/**
 * $Author : wu_jianfeng
 * $Date : 2012/09/17 14:10$
 * [BUG]0009752 ADD BEGIN
 * [FUN]V03-001患者列表
 * @version 1.0, 2012/03/02  
 * @author wu_jianfeng
 * @since 1.0
 * 患者列表结果
 */
select res.patient_sn, 
	   res.patient_eid, 
       res.patient_name, 
	   res.birth_date, 
       res.gender_Name,
       res.patient_domain,
	   res.visit_date,
	   res.visit_dept_name,
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
       view1.patient_domain,
	   view1.visit_date,
	   view1.visit_dept_name,
	   view1.discharge_dept_name,
       view1.discharge_ward_name,
       view1.discharge_bed_no,
       view1.org_code,
       view1.visit_type_code,
       row_number()over(partition by p.patient_sn order by view1.visit_date desc) rn
    	 /** 
		  * Author：yu_yzh
		  * Date：2016/3/2 11:19
		  * 增加身份证号检索条件
		  * [BUG]0063885
		  * MODIFY BEGIN
		  */
		  from (
		  		/*IF patientListSearchPra.idNumber != NULL && patientListSearchPra.idNumber.length() != 0*/
		  		select distinct p.patient_sn,
					   p.patient_eid,
					   p.patient_name,
					   p.birth_date,
					   p.gender_Name,
					   p.delete_flag
				from patient p left join patient_credential pc on p.patient_sn = pc.patient_sn
				  
				  and pc.credential_no = /*patientListSearchPra.idNumber*/''
				 /*END*/
				 /*IF patientListSearchPra.idNumber == NULL || patientListSearchPra.idNumber.length() == 0*/
				  patient
				 /*END*/
				) p
		  
		  inner join
  (
		   /*IF patientListSearchPra.visitTypeStr == NULL || patientListSearchPra.visitTypeStr.length() ==0 || patientListSearchPra.visitTypeStr.equals(patientListSearchPra.visitTypeInpatient)*/
		   select mv.visit_date, mv.patient_sn,mv.visit_dept_name, mv.discharge_dept_name,mv.discharge_ward_name, mv.discharge_bed_no, mv.patient_domain, mv.org_code , mv.visit_type_code
		     from medical_visit mv    
		     /*IF patientListSearchPra.patientName != NULL && patientListSearchPra.patientName.length() != 0*/
		     ,patient p
		     /*END*/
		     	  --,diagnosis d
		    where mv.delete_flag='0'
		      --and d.delete_flag(+)='0'
		      --and mv.visit_sn=d.visit_sn(+)
		      
		    
		      and mv.patient_domain in /*patientListSearchPra.inpatientDomains*/(10, 20, 30)     
		      /*IF patientListSearchPra.patientEId != NULL && patientListSearchPra.patientEId.length() != 0*/
     			and mv.patient_sn in (select patient_sn from patient where patient_eid = /*patientListSearchPra.patientEId*/'')       
     		  /*END*/
		      
		      /*IF patientListSearchPra.patientName != NULL && patientListSearchPra.patientName.length() != 0*/
		        and p.patient_sn=mv.patient_sn
			    and p.patient_name like /*patientListSearchPra.patientName*/''  
			  /*END*/	    
		      /*IF patientListSearchPra.visitStartDate != NULL && patientListSearchPra.visitStartDate.length() != 0*/
		      	and mv.visit_date >= to_date(/*patientListSearchPra.visitStartDate*/'2012-10-10'||'00-00-00', 'YYYY-MM-DD hh24:mi:ss')
		      /*END*/
		      /*IF patientListSearchPra.visitEndDate != NULL && patientListSearchPra.visitEndDate.length() != 0*/
		        and mv.visit_date <= to_date(/*patientListSearchPra.visitEndDate*/'2012-10-10'||'23-59-59', 'YYYY-MM-DD hh24:mi:ss')
		      /*END*/
		      
		        and mv.visit_type_code = /*patientListSearchPra.visitTypeInpatient*/'I'
		      
		      /*IF patientListSearchPra.visitStatus != NULL && patientListSearchPra.visitStatus.length() != 0*/
		        and mv.visit_status_code = /*patientListSearchPra.visitStatus*/''
		      /*END*/
		      /*IF patientListSearchPra.staff != NULL && patientListSearchPra.staff.length() != 0*/
	            and exists (select 1 from patient_doctor pd where pd.doctor_code = /*patientListSearchPra.staff*/'000' and pd.visit_sn = mv.visit_sn and pd.delete_flag = '0')
			  /*END*/  
		      
		      /*IF acl.patientScopeAuth05 == false*/
		        /*IF (acl.patientScopeAuth01 || acl.patientScopeAuth03 || acl.patientScopeAuth06) && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
		          and (
		   			/*IF acl.patientScopeAuth01 && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
		             exists (select 1 from patient_doctor pd where pd.doctor_code = /*patientListSearchPra.doctorId*/'000' and pd.visit_sn = mv.visit_sn and pd.delete_flag = '0')
			 	     		/*IF patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
			 	     		and mv.discharge_dept_id in /*patientListSearchPra.deptIds*/(10,20,30)
			 	     		/*END*/
			     	/*END*/
			 	    /*IF acl.patientScopeAuth01 && (acl.patientScopeAuth03 || acl.patientScopeAuth06) && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
			 	    	or
			 	    /*END*/
		            /*IF acl.patientScopeAuth03 && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
		              (mv.visit_status_code = /*patientListSearchPra.visitStatusInHospital*/''
		              and mv.discharge_dept_id in /*patientListSearchPra.deptIds*/(10,20,30))
		            /*END*/
		            /*IF acl.patientScopeAuth03 && acl.patientScopeAuth06 && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
			 	    	or
			 	    /*END*/
		            /*IF acl.patientScopeAuth06 && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
		              (mv.visit_status_code = /*patientListSearchPra.visitStatusDischarge*/''
		              and mv.discharge_dept_id in /*patientListSearchPra.deptIds*/(10,20,30))
		            /*END*/
			          )
			      /*END*/
		       /*END*/      
		       /*IF patientListSearchPra.dischargeBedNo != NULL && patientListSearchPra.dischargeBedNo.length() != 0*/
		        and mv.discharge_bed_no like /*patientListSearchPra.dischargeBedNo*/ || '%'
		       /*END*/
		       /*IF patientListSearchPra.dischargeWardId != NULL && patientListSearchPra.dischargeWardId.length() != 0*/
		        and mv.discharge_ward_Id = /*patientListSearchPra.dischargeWardId*/
		       /*END*/
		       /*IF patientListSearchPra.deptId != NULL && patientListSearchPra.deptId.length() != 0*/
		        and mv.discharge_dept_id = /*patientListSearchPra.deptId*/
		       /*END*/
		       /*IF patientListSearchPra.orgCode != NULL && patientListSearchPra.orgCode.length() != 0*/
				where org_code = /*patientListSearchPra.orgCode*/
			   /*END*/   
		    /*END*/   
		        
		    
		    /*IF patientListSearchPra.visitTypeStr == NULL || patientListSearchPra.visitTypeStr.length() ==0*/
		    	union all
		    /*END*/
		        
		    /*IF patientListSearchPra.visitTypeStr == NULL || patientListSearchPra.visitTypeStr.length() ==0 || !patientListSearchPra.visitTypeStr.equals(patientListSearchPra.visitTypeInpatient)*/
		        select mv.visit_date, mv.patient_sn,mv.visit_dept_name, mv.discharge_dept_name, mv.discharge_ward_name, mv.discharge_bed_no, mv.patient_domain, mv.org_code , mv.visit_type_code
			      from medical_visit mv	      
			 /*IF patientListSearchPra.patientName != NULL && patientListSearchPra.patientName.length() != 0*/
		     ,patient p
		     /*END*/
			     	   --,diagnosis d   
			     where mv.delete_flag='0'
			       --and d.delete_flag(+)='0'
			       --and mv.visit_sn=d.visit_sn(+)
			       and mv.patient_domain  in /*patientListSearchPra.inpatientDomains*/(10, 20, 30)	       
			  /*IF patientListSearchPra.patientEId != NULL && patientListSearchPra.patientEId.length() != 0*/
     			and mv.patient_sn in (select patient_sn from patient where patient_eid = /*patientListSearchPra.patientEId*/'')       
     		  /*END*/
			       
			  /*IF patientListSearchPra.patientName != NULL && patientListSearchPra.patientName.length() != 0*/
		        and p.patient_sn=mv.patient_sn
			    and p.patient_name like /*patientListSearchPra.patientName*/''  
			  /*END*/	    
			      /*IF patientListSearchPra.visitStartDate != NULL && patientListSearchPra.visitStartDate.length() != 0*/
			      	and mv.visit_date >= to_date(/*patientListSearchPra.visitStartDate*/'2012-10-10'||'00-00-00', 'YYYY-MM-DD hh24:mi:ss')
			      /*END*/
			      /*IF patientListSearchPra.visitEndDate != NULL && patientListSearchPra.visitEndDate.length() != 0*/
			        and mv.visit_date <= to_date(/*patientListSearchPra.visitEndDate*/'2012-10-10'||'23-59-59', 'YYYY-MM-DD hh24:mi:ss')
			      /*END*/
			      
			        and mv.visit_type_code != /*patientListSearchPra.visitTypeInpatient*/'I'
			      
			      /*IF patientListSearchPra.visitStatus != NULL && patientListSearchPra.visitStatus.length() != 0*/
			        and mv.visit_status_code = /*patientListSearchPra.visitStatus*/''
			      /*END*/ 
			      /*IF patientListSearchPra.staff != NULL && patientListSearchPra.staff.length() != 0*/
		            and exists (select 1 from patient_doctor pd where pd.doctor_code = /*patientListSearchPra.staff*/'000' and pd.visit_sn = mv.visit_sn and pd.delete_flag = '0')
				  /*END*/ 
			      /*IF acl.patientScopeAuth05 == false*/	
			   			/*IF acl.patientScopeAuth01 || (acl.patientScopeAuth02 && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0)*/
							and ( 
				  					/*BEGIN*/
									 	/*IF acl.patientScopeAuth01*/
				 	     				   mv.visit_doctor_id = /*patientListSearchPra.doctorId*/
								     	/*END*/
				 	     				/*IF acl.patientScopeAuth01 && acl.patientScopeAuth02 && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
				 	     				   or
								     	/*END*/
									 	/*IF acl.patientScopeAuth02 && patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
										   mv.visit_dept_id in /*patientListSearchPra.deptIds*/(10,20,30)
								     	/*END*/
							     	/*END*/
				     		    )
				     	/*END*/
			       /*END*/      
			       /*IF patientListSearchPra.deptId != NULL && patientListSearchPra.deptId.length() != 0*/
				       and mv.visit_dept_id = /*patientListSearchPra.deptId*/
				   /*END*/
				   /*IF patientListSearchPra.dischargeBedNo != NULL && patientListSearchPra.dischargeBedNo.length() != 0*/
				       and mv.discharge_bed_no like /*patientListSearchPra.dischargeBedNo*/ || '%'
				   /*END*/
				   /*IF patientListSearchPra.dischargeWardId != NULL && patientListSearchPra.dischargeWardId.length() != 0*/
				       and mv.discharge_ward_id = /*patientListSearchPra.dischargeWardId*/
				   /*END*/
				   /*IF patientListSearchPra.orgCode != NULL && patientListSearchPra.orgCode.length() != 0*/
				     where org_code = /*patientListSearchPra.orgCode*/
				   /*END*/    
   		/*END*/ 
   	
   )view1
   on p.patient_sn = view1.patient_sn
		  
where p.delete_flag = '0'
	 /** 增加身份证号检索条件 [BUG]0063885 MODIFY END*/
	 /*IF patientListSearchPra.patientName != NULL && patientListSearchPra.patientName.length() != 0*/
	    and p.patient_name like /*patientListSearchPra.patientName*/''
	 /*END*/
	 /*IF patientListSearchPra.genderCode != NULL && patientListSearchPra.genderCode.length() != 0*/
	    and p.gender_code = /*patientListSearchPra.genderCode*/''
	 /*END*/
     /*IF patientListSearchPra.patientEId != NULL && patientListSearchPra.patientEId.length() != 0*/
     	and p.patient_eid = /*patientListSearchPra.patientEId*/''
     /*END*/
     /*IF patientListSearchPra.baseAge != NULL && patientListSearchPra.baseAge.length() != 0 && !patientListSearchPra.baseAge.equals('20')*/
        and (view1.visit_date-p.birth_date)/365 between to_number(/*patientListSearchPra.baseAge*/)*5 and (to_number(/*patientListSearchPra.baseAge*/)*5+5)
     /*END*/
     /*IF patientListSearchPra.baseAge != NULL && patientListSearchPra.baseAge.length() != 0 && patientListSearchPra.baseAge.equals('20')*/
        and (view1.visit_date-p.birth_date)/365 >= 100
     /*END*/
     /*IF empiInfoIsEmpty*/
     --ELSE
        and exists 
	      (select 'X' from patient_cross_index pci 
	        where pci.patient_eid = p.patient_eid
	          and pci.patient_domain = view1.patient_domain
	          and pci.delete_flag = '0'
	      /*IF patientListSearchPra.patientDomains != NULL && patientListSearchPra.patientDomains.size() != 0*/
	          and pci.patient_domain in /*patientListSearchPra.patientDomains*/(10,20,30)
	      /*END*/
	      /*IF patientListSearchPra.outpatientNo != NULL && patientListSearchPra.outpatientNo.length() != 0*/
	          and pci.outpatient_no = /*patientListSearchPra.outpatientNo*/
	      /*END*/
	      /*IF patientListSearchPra.inpatientNo != NULL && patientListSearchPra.inpatientNo.length() != 0*/
	          and pci.inpatient_no = /*patientListSearchPra.inpatientNo*/
	      /*END*/
	      /*IF patientListSearchPra.imagingNo != NULL && patientListSearchPra.imagingNo.length() != 0*/
	          and pci.imaging_no = /*patientListSearchPra.imagingNo*/
	      /*END*/)
     /*END*/  
order by view1.visit_date desc) res
where res.rn = 1
