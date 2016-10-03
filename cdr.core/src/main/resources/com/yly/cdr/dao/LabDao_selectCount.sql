select count(1)
  from external_send_business esb , external_send_report esr 
 WHERE esb.business_sn = esr.business_sn
   and esb.patient_domain = /*patientDomain*/'01'
   and esb.patient_lid = /*patientLid*/'00'
   and esb.org_code = /*orgCode*/'01'
   and esb.visit_times = /*visitTimes*/1
   and esr.infectious_name = /*infectiousName*/'00'
   and esb.delete_flag = '0'
   and esb.business_type in ('02','03','04')