select count(*)
  from external_send_business a, external_send_report b
 where a.business_sn = b.business_sn
   and a.patient_lid = /*patientLid*/
   and b.infectious_name  = /*disearseName*/
   and a.report_date >= trunc(to_date(/*reportDate*/,'yyyy-mm-dd hh24:mi:ss'),'dd') - /*dayCount*/