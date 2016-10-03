/** 获取患者最小住院次数 */
select min(mv.visit_times)-1  
  from medical_visit mv
 where mv.patient_sn = /*patientSn*/
   and mv.org_code = /*orgCode*/