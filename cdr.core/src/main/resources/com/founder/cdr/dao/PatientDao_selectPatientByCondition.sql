/**
 * $Author : wu_jianfeng
 * $Date : 2012/09/4 14:10$
 * [BUG]0009345 MODIFY BEGIN
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
res.gender_name,
res.visit_date,
res.discharge_ward_name, 
res.discharge_bed_no,
res.org_code
from (
select p.patient_sn, 
p.patient_eid, 
p.patient_name, 
p.birth_date, 
p.gender_name,
view1.visit_date,
view1.discharge_ward_name, 
view1.discharge_bed_no,
view1.org_code,
view1.visit_type_code,
row_number()over(partition by p.patient_sn order by view1.visit_date desc) rn
from patient p ,
(
  select mv_all.visit_date, mv_all.patient_sn, mv_all.discharge_ward_name, mv_all.discharge_bed_no, mv_all.patient_domain, mv_all.org_code,mv_all.visit_type_code
    from (  
   	/*IF patientListSearchPra.visitTypeStr == NULL || patientListSearchPra.visitTypeStr.length() ==0 || patientListSearchPra.visitTypeStr.equals(patientListSearchPra.visitTypeInpatient)*/
		   select mv.visit_date, mv.patient_sn, mv.discharge_ward_name, mv.discharge_bed_no, mv.patient_domain, mv.org_code,mv.visit_type_code
		     from medical_visit mv
		     	  --,diagnosis  ds 
		    where mv.delete_flag='0'
			  --and ds.delete_flag(+)='0'
			  --and mv.visit_sn=ds.visit_sn(+)
			  mv.patient_domain in /*patientListSearchPra.inpatientDomains*/(10, 20, 30)
		    /*IF patientListSearchPra.visitStartDate != NULL && patientListSearchPra.visitStartDate.length() != 0*/
		        and mv.visit_date >= to_date(/*patientListSearchPra.visitStartDate*/'2012-1-12'||'00-00-00', 'YYYY-MM-DD hh24:mi:ss')
		    /*END*/
		    /*IF patientListSearchPra.visitEndDate != NULL && patientListSearchPra.visitEndDate.length() != 0*/
		        and mv.visit_date <= to_date(/*patientListSearchPra.visitEndDate*/'2012-1-12'||'23-59-59', 'YYYY-MM-DD hh24:mi:ss')
		    /*END*/
		    /*IF patientListSearchPra.visitTypes != NULL && patientListSearchPra.visitTypes.size() != 0*/
		        and mv.visit_type_code in /*patientListSearchPra.visitTypes*/(10,20,30)
		    /*END*/
		    /*IF patientListSearchPra.visitStatus != NULL && patientListSearchPra.visitStatus.length() != 0*/
		        and mv.visit_status_code = /*patientListSearchPra.visitStatus*/''
		    /*END*/         
		    /*IF patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
		        and mv.discharge_dept_id in /*patientListSearchPra.deptIds*/(10,20,30)
		    /*END*/ 
		    /*IF patientListSearchPra.dischargeBedNo != NULL && patientListSearchPra.dischargeBedNo.length() != 0*/
		        and mv.discharge_bed_no like /*patientListSearchPra.dischargeBedNo*/ || '%'
		    /*END*/
		    /*IF patientListSearchPra.dischargeWardName != NULL && patientListSearchPra.dischargeWardName.length() != 0*/
		        and mv.discharge_ward_name like /*patientListSearchPra.dischargeWardName*/ || '%'
		    /*END*/
		    /*IF patientListSearchPra.deptName != NULL && patientListSearchPra.deptName.length() != 0*/
		        and mv.discharge_dept_name like /*patientListSearchPra.deptName*/ || '%'
		    /*END*/
		    /*IF patientListSearchPra.orgCode != NULL && patientListSearchPra.orgCode.length() != 0*/
		        and mv.org_code = /*patientListSearchPra.orgCode*/
		    /*END*/
	  /*END*/
	  
	/*IF patientListSearchPra.visitTypeStr == NULL || patientListSearchPra.visitTypeStr.length() ==0*/
    union all
    /*END*/
	    
	 /*IF patientListSearchPra.visitTypeStr == NULL || patientListSearchPra.visitTypeStr.length() ==0 || !patientListSearchPra.visitTypeStr.equals(patientListSearchPra.visitTypeInpatient)*/
		    select mv.visit_date, mv.patient_sn, mv.discharge_ward_name, mv.discharge_bed_no, mv.patient_domain, mv.org_code,mv.visit_type_code
		     from medical_visit mv
		     	  --,diagnosis  ds 
		    where mv.delete_flag='0'
			  --and ds.delete_flag(+)='0'
			  --and mv.visit_sn=ds.visit_sn(+)
			  and mv.patient_domain in /*patientListSearchPra.outpatientDomains*/(10, 20, 30)
		    /*IF patientListSearchPra.visitStartDate != NULL && patientListSearchPra.visitStartDate.length() != 0*/
		        and mv.visit_date >= to_date(/*patientListSearchPra.visitStartDate*/'2012-1-12'||'00-00-00', 'YYYY-MM-DD hh24:mi:ss')
		    /*END*/
		    /*IF patientListSearchPra.visitEndDate != NULL && patientListSearchPra.visitEndDate.length() != 0*/
		        and mv.visit_date <= to_date(/*patientListSearchPra.visitEndDate*/'2012-1-12'||'23-59-59', 'YYYY-MM-DD hh24:mi:ss')
		    /*END*/
		    /*IF patientListSearchPra.visitTypes != NULL && patientListSearchPra.visitTypes.size() != 0*/
		        and mv.visit_type_code in /*patientListSearchPra.visitTypes*/(10,20,30)
		    /*END*/
		    /*IF patientListSearchPra.visitStatus != NULL && patientListSearchPra.visitStatus.length() != 0*/
		        and mv.visit_status_code = /*patientListSearchPra.visitStatus*/''
		    /*END*/         
		    /*IF patientListSearchPra.deptIds != NULL && patientListSearchPra.deptIds.size() != 0*/
		        and mv.visit_dept_id in /*patientListSearchPra.deptIds*/(10,20,30)
		    /*END*/
		    /*IF patientListSearchPra.deptName != NULL && patientListSearchPra.deptName.length() != 0*/
		        and mv.visit_dept_name like /*patientListSearchPra.deptName*/ || '%'
		    /*END*/
		    /*IF patientListSearchPra.dischargeBedNo != NULL && patientListSearchPra.dischargeBedNo.length() != 0*/
		        and mv.discharge_bed_no like /*patientListSearchPra.dischargeBedNo*/ || '%'
		    /*END*/
		    /*IF patientListSearchPra.dischargeWardName != NULL && patientListSearchPra.dischargeWardName.length() != 0*/
		        and mv.discharge_ward_name like /*patientListSearchPra.dischargeWardName*/ || '%'
		    /*END*/
		    /*IF patientListSearchPra.orgCode != NULL && patientListSearchPra.orgCode.length() != 0*/
		        and mv.org_code = /*patientListSearchPra.orgCode*/
		    /*END*/
	   ) mv_all 
	   --where mv_all.rn = 1 
	   /*END*/
   ) view1
where p.delete_flag = '0'
      and p.patient_sn = view1.patient_sn
      /*IF patientListSearchPra.patientName != NULL && patientListSearchPra.patientName.length() != 0*/
      	and p.patient_name like /*patientListSearchPra.patientName*/''
      /*END*/
      /*IF patientListSearchPra.patientEId != NULL && patientListSearchPra.patientEId.length() != 0*/
      	and p.patient_eid = /*patientListSearchPra.patientEId*/''
      /*END*/
      /*IF patientListSearchPra.genderCode != NULL && patientListSearchPra.genderCode.length() != 0*/
      	and p.gender_code = /*patientListSearchPra.genderCode*/''
      /*END*/
  	  /*IF patientListSearchPra.baseAge != NULL && patientListSearchPra.baseAge.length() != 0 && !patientListSearchPra.baseAge.equals('20')*/
      	and (view1.visit_date-p.birth_date)/365 between to_number(/*patientListSearchPra.baseAge*/'1')*5 and (to_number(/*patientListSearchPra.baseAge*/'1')*5+5)
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
/**[BUG]0009345 MODIFY END*/     