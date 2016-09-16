select visit_sn, visit_date ||  '(' || ACOL || ')' visit_date, mv.org_code
from (
   select row_number() OVER(partition by to_char(visit_date, 'YYYY-MM-DD') order by visit_date desc) ACOL,
       to_char(visit_date, 'YYYY-MM-DD') visit_date,mv.org_code,
       visit_sn
   from medical_visit mv
   where delete_flag = '0'
	 and (exists (select 'X' from diagnosis where diagnosis.delete_flag = 0 and diagnosis.visit_sn  = mv.visit_sn)
	   or exists (select 'X' from examination_order eo where eo.delete_flag = 0 and eo.visit_sn  = mv.visit_sn)
       or exists (select 'X' from lab_order lo where lo.delete_flag = 0 and lo.visit_sn  = mv.visit_sn)
	   or mv.visit_date >= to_date(/*visitListSearchPra.sysDate*/''||'00-00-00', 'YYYY-MM-DD hh24:mi:ss'))
	   and mv.visit_type_code not in /*visitListSearchPra.visitTypeInpatientCode*/('03','05')
       and patient_sn = /*visitListSearchPra.patientSn*/
     /*IF visitListSearchPra.orgCode != null && visitListSearchPra.orgCode.length() != 0*/
     	and mv.org_code = /*visitListSearchPra.orgCode*/
     /*END*/
     order by visit_date desc
) mv
