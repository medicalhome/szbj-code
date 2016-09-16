select * from (
select mv.visit_sn, 
       mv.visit_type_name,
       mv.visit_type_code,
       mv.visit_times,
       mv.visit_doctor_name,
       mv.visit_date,
       mv.patient_domain,
       mv.discharge_dept_name,
       mv.discharge_ward_name,
       mv.discharge_bed_no,
       mv.visit_dept_name,
       mv.visit_status_code,
       mv.visit_status_name,
       mv.registration_Class_Name,
       mv.discharge_date,
       mv.org_code
  from medical_visit mv
  /*IF visitListSearchPra.diagnosisName != NULL && visitListSearchPra.diagnosisName.length() != 0*/
   , diagnosis d
   /*END*/
  
 where mv.patient_sn = /*patientSn*/
   and mv.delete_flag = '0'   
   /*IF visitListSearchPra.diagnosisName != NULL && visitListSearchPra.diagnosisName.length() != 0*/
   and d.delete_flag = '0'   
   and d.disease_name = /*visitListSearchPra.diagnosisName*/ 
   and d.main_diagnosis_flag = '1'
   and d.visit_sn = mv.visit_sn
   /*END*/
   and mv.visit_type_code in /*visitListSearchPra.visitTypeInpatientCode*/(10,20,30)
   /*IF visitListSearchPra.visitDateFromStr != null && visitListSearchPra.visitDateFromStr.length() != 0*/
   and mv.visit_date >= to_date(/*visitListSearchPra.visitDateFromStr*/''||'00-00-00', 'YYYY-MM-DD hh24:mi:ss')
   /*END*/
   /*IF visitListSearchPra.visitDateToStr != null && visitListSearchPra.visitDateToStr.length() != 0*/
   and mv.visit_date <= to_date(/*visitListSearchPra.visitDateToStr*/''||'23-59-59', 'YYYY-MM-DD hh24:mi:ss')
   /*END*/
   /*IF visitListSearchPra.visitTypeCode != null && visitListSearchPra.visitTypeCode.length() != 0*/
   and mv.visit_type_code = /*visitListSearchPra.visitTypeCode*/
   /*END*/
   /*IF visitListSearchPra.visitDepts != NULL && visitListSearchPra.visitDepts.size() != 0*/
   and mv.visit_dept_Id in /*visitListSearchPra.visitDepts*/(10,20,30)
   /*END*/
   /*IF visitListSearchPra.dischargeDateFromStr != NULL && visitListSearchPra.dischargeDateFromStr.length() != 0*/
   and mv.discharge_date >= to_date(/*visitListSearchPra.dischargeDateFromStr*/''||'00-00-00', 'YYYY-MM-DD hh24:mi:ss')
   /*END*/
   /*IF visitListSearchPra.dischargeDateToStr != NULL && visitListSearchPra.dischargeDateToStr.length() != 0*/
   and mv.discharge_date <= to_date(/*visitListSearchPra.dischargeDateToStr*/''||'23-59-59', 'YYYY-MM-DD hh24:mi:ss')
   /*END*/
   /*IF visitListSearchPra.visitDoctorName != NULL && visitListSearchPra.visitDoctorName.length() != 0*/
   and mv.visit_doctor_name like '%' || /*visitListSearchPra.visitDoctorName*/'' || '%'
   /*END*/
   /*IF visitListSearchPra.registrationClassCode != NULL && visitListSearchPra.registrationClassCode.length() != 0*/
   and mv.registration_class_Code = /*visitListSearchPra.registrationClassCode*/
   /*END*/
   /*IF visitListSearchPra.orgCode != NULL && visitListSearchPra.orgCode.length() != 0*/
   and mv.org_code = /*visitListSearchPra.orgCode*/
   /*END*/

union all
select mv.visit_sn, 
       mv.visit_type_name,
       mv.visit_type_code,
       mv.visit_times,
       mv.visit_doctor_name,
       mv.visit_date,
       mv.patient_domain,
       mv.discharge_dept_name,
       mv.discharge_ward_name,
       mv.discharge_bed_no,
       mv.visit_dept_name,
       mv.visit_status_code,
       mv.visit_status_name,
       mv.registration_Class_Name,
       mv.discharge_date,
       mv.org_code
  from medical_visit mv
  /*IF visitListSearchPra.diagnosisName != NULL && visitListSearchPra.diagnosisName.length() != 0*/
   , diagnosis d
   /*END*/
  
 where mv.patient_sn = /*patientSn*/
   and mv.delete_flag = '0'   
   /*IF visitListSearchPra.diagnosisName != NULL && visitListSearchPra.diagnosisName.length() != 0*/
   and d.delete_flag = '0'   
   and d.disease_name = /*visitListSearchPra.diagnosisName*/ 
   and d.main_diagnosis_flag = '1'
   and d.visit_sn = mv.visit_sn
   /*END*/
   and mv.visit_type_code not in /*visitListSearchPra.visitTypeInpatientCode*/(10,20,30)
   and (exists (select 'X' from diagnosis where diagnosis.delete_flag = 0 and diagnosis.visit_sn  = mv.visit_sn)
    or exists (select 'X' from examination_order eo where eo.delete_flag = 0 and eo.visit_sn  = mv.visit_sn)
    or exists (select 'X' from lab_order lo where lo.delete_flag = 0 and lo.visit_sn  = mv.visit_sn) 
   or mv.visit_date >= to_date(/*visitListSearchPra.sysDate*/''||'00-00-00', 'YYYY-MM-DD hh24:mi:ss'))
   
   /*IF visitListSearchPra.visitDateFromStr != null && visitListSearchPra.visitDateFromStr.length() != 0*/
   and mv.visit_date >= to_date(/*visitListSearchPra.visitDateFromStr*/''||'00-00-00', 'YYYY-MM-DD hh24:mi:ss')
   /*END*/
   /*IF visitListSearchPra.visitDateToStr != null && visitListSearchPra.visitDateToStr.length() != 0*/
   and mv.visit_date <= to_date(/*visitListSearchPra.visitDateToStr*/''||'23-59-59', 'YYYY-MM-DD hh24:mi:ss')
   /*END*/
   /*IF visitListSearchPra.visitTypeCode != null && visitListSearchPra.visitTypeCode.length() != 0*/
   and mv.visit_type_code = /*visitListSearchPra.visitTypeCode*/
   /*END*/
   /*IF visitListSearchPra.visitDepts != NULL && visitListSearchPra.visitDepts.size() != 0*/
   and mv.visit_dept_Id in /*visitListSearchPra.visitDepts*/(10,20,30)
   /*END*/
   /*IF visitListSearchPra.dischargeDateFromStr != NULL && visitListSearchPra.dischargeDateFromStr.length() != 0*/
   and mv.discharge_date >= to_date(/*visitListSearchPra.dischargeDateFromStr*/''||'00-00-00', 'YYYY-MM-DD hh24:mi:ss')
   /*END*/
   /*IF visitListSearchPra.dischargeDateToStr != NULL && visitListSearchPra.dischargeDateToStr.length() != 0*/
   and mv.discharge_date <= to_date(/*visitListSearchPra.dischargeDateToStr*/''||'23-59-59', 'YYYY-MM-DD hh24:mi:ss')
   /*END*/
   /*IF visitListSearchPra.visitDoctorName != NULL && visitListSearchPra.visitDoctorName.length() != 0*/
   and mv.visit_doctor_name like '%' || /*visitListSearchPra.visitDoctorName*/'' || '%'
   /*END*/
   /*IF visitListSearchPra.registrationClassCode != NULL && visitListSearchPra.registrationClassCode.length() != 0*/
   and mv.registration_class_Code = /*visitListSearchPra.registrationClassCode*/
   /*END*/ 
   /*IF visitListSearchPra.orgCode != NULL && visitListSearchPra.orgCode.length() != 0*/
   and mv.org_code = /*visitListSearchPra.orgCode*/
   /*END*/
) vi order by vi.visit_date desc, vi.visit_sn desc