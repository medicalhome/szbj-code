/**
 * 
 * [FUN]V05-101就诊列表
 * @version 1.0, 2012/03/02  
 * @author 郭红燕
 * @since 1.0
 * 就诊检索结果
 */
select * from (select mv.visit_sn, 
       mv.visit_type_name,
       mv.visit_type_code,
       mv.visit_times,
       trim(mv.visit_doctor_name) visit_doctor_name,
       mv.visit_date,
       mv.visit_dept_name,
       mv.visit_status_name,
       mv.registration_class_name,
       mv.discharge_date,
       mv.org_code,
       mv.org_name
  from medical_visit mv
 where mv.patient_sn = /*patientSn*/
   and mv.delete_flag = '0'
   and mv.visit_type_code in/*visitListSearchPra.visitTypeInpatientCode*/(10,20,30)
    /*IF visitListSearchPra.visitDoctorName != NULL && visitListSearchPra.visitDoctorName.length() != 0*/
   and  (exists (select 'X' from patient_doctor pd where pd.visit_sn  = mv.visit_sn and pd.delete_flag = '0'
   				 and pd.doctor_name like '%' || /*visitListSearchPra.visitDoctorName*/'' || '%') 
   		 or mv.visit_doctor_name like '%' || /*visitListSearchPra.visitDoctorName*/'' || '%' ) 
   /*END*/ 
    /*IF visitListSearchPra.visitTypeCode != null && visitListSearchPra.visitTypeCode.length() != 0*/
   and mv.visit_type_code=/*visitListSearchPra.visitTypeCode*/
   /*END*/
   /*IF visitListSearchPra.visitDateFromStr != null && visitListSearchPra.visitDateFromStr.length() != 0*/
   and mv.visit_date >= to_date(/*visitListSearchPra.visitDateFromStr*/''||'00-00-00', 'YYYY-MM-DD hh24:mi:ss')
   /*END*/
   /*IF visitListSearchPra.visitDateToStr != null && visitListSearchPra.visitDateToStr.length() != 0*/
   and mv.visit_date <= to_date(/*visitListSearchPra.visitDateToStr*/''||'23-59-59', 'YYYY-MM-DD hh24:mi:ss')
   /*END*/
   /*IF visitListSearchPra.visitDeptId != null && visitListSearchPra.visitDeptId.length() != 0*/
   and mv.visit_dept_Id = /*visitListSearchPra.visitDeptId*/
   /*END*/
   /*IF visitListSearchPra.dischargeDateFromStr != NULL && visitListSearchPra.dischargeDateFromStr.length() != 0*/
   and mv.discharge_date >= to_date(/*visitListSearchPra.dischargeDateFromStr*/''||'00-00-00', 'YYYY-MM-DD hh24:mi:ss')
   /*END*/
   /*IF visitListSearchPra.dischargeDateToStr != NULL && visitListSearchPra.dischargeDateToStr.length() != 0*/
   and mv.discharge_date <= to_date(/*visitListSearchPra.dischargeDateToStr*/''||'23-59-59', 'YYYY-MM-DD hh24:mi:ss')
   /*END*/
   /*IF visitListSearchPra.registrationClassCode != NULL && visitListSearchPra.registrationClassCode.length() != 0*/
   and mv.registration_class_Code = /*visitListSearchPra.registrationClassCode*/
   /*END*/
   /*IF visitListSearchPra.orgCode != NULL && visitListSearchPra.orgCode.length() != 0*/
   and mv.org_Code = /*visitListSearchPra.orgCode*/
   /*END*/
union all
select mv.visit_sn, 
       mv.visit_type_name,
       mv.visit_type_code,
       mv.visit_times,
       trim(mv.visit_doctor_name) visit_doctor_name,
       mv.visit_date,
       mv.visit_dept_name,
       mv.visit_status_name,
       mv.registration_class_name,
       mv.discharge_date,
       mv.org_code,
       mv.org_name
  from medical_visit mv
 where mv.patient_sn = /*patientSn*/
   and mv.delete_flag = '0'
   and mv.visit_type_code not in/*visitListSearchPra.visitTypeInpatientCode*/(10,20,30)
   /*IF visitListSearchPra.visitDateFromStr != null && visitListSearchPra.visitDateFromStr.length() != 0*/
   and mv.visit_date >= to_date(/*visitListSearchPra.visitDateFromStr*/''||'00-00-00', 'YYYY-MM-DD hh24:mi:ss')
   /*END*/
   /*IF visitListSearchPra.visitDateToStr != null && visitListSearchPra.visitDateToStr.length() != 0*/
   and mv.visit_date <= to_date(/*visitListSearchPra.visitDateToStr*/''||'23-59-59', 'YYYY-MM-DD hh24:mi:ss')
   /*END*/
   /*IF visitListSearchPra.visitTypeCode != null && visitListSearchPra.visitTypeCode.length() != 0 */
   and mv.visit_type_code =/*visitListSearchPra.visitTypeCode*/
   /*END*/
   /*IF visitListSearchPra.visitDeptId != null && visitListSearchPra.visitDeptId.length() != 0*/
   and mv.visit_dept_Id = /*visitListSearchPra.visitDeptId*/
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
   and mv.org_Code = /*visitListSearchPra.orgCode*/
   /*END*/
   and (exists (select 'X' from diagnosis where diagnosis.delete_flag = 0 and diagnosis.visit_sn  = mv.visit_sn) 
    or exists (select 'X' from examination_order eo where eo.delete_flag = 0 and eo.visit_sn  = mv.visit_sn)
    or exists (select 'X' from lab_order lo where lo.delete_flag = 0 and lo.visit_sn  = mv.visit_sn)
    or mv.visit_date >= to_date(/*visitListSearchPra.sysDate*/''||'00-00-00', 'YYYY-MM-DD hh24:mi:ss') )
   ) mvn
   
 order by mvn.visit_date desc, mvn.visit_sn desc
