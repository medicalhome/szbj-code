select patient_eid, patient_domain, outpatient_no, inpatient_no
  from (select row_number() OVER(partition by patient_eid, patient_domain order by update_time desc) ACOL,
  			   row_number() OVER(partition by patient_eid,outpatient_no order by update_time desc) outcount,
               row_number() OVER(partition by patient_eid,inpatient_no order by update_time desc) incount,
               patient_eid,
               patient_domain,
      		   outpatient_no,
               inpatient_no
          from patient_cross_index
         where delete_flag = '0' 
			/*IF patientEids != NULL && patientEids.size() != 0*/
			  and patient_eid in /*patientEids*/(10,20,30)
			/*END*/
			/*IF patientDomainTag == '01'*/
			  and outpatient_no is not null
			/*END*/
			/*IF patientDomainTag == '02'*/
			  and inpatient_no is not null
			/*END*/
			/*IF orgCode != NULL && orgCode.length() != 0*/
		      and org_code = /*orgCode*/
		    /*END*/
		    
         ) pci
where pci.ACOL <6
	 /*IF patientDomainTag == '01'*/
		 and  outcount = 1
	 /*END*/
	 /*IF patientDomainTag == '02'*/
		and  incount = 1
	 /*END*/
