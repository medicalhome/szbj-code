/*IF pageNo == 1*/		 	 
select patient_sn, patient_eid, patient_name, birth_date,gender_name,visit_date from (
	select * from table(F_GET_PATIENTLIST(/*sql*/)) order by visit_date desc
) where rownum <= 10
 --ELSE
select patient_sn, patient_eid, patient_name, birth_date,gender_name,visit_date from (
	select patient_sn, patient_eid, patient_name, birth_date,gender_name,visit_date, rownum rownum_ from (
		select * from table(F_GET_PATIENTLIST(/*sql*/)) order by visit_date desc
	) row_ where rownum <= /*recordEndNum*/
) where rownum_ > /*recordStartNum*/
/*END*/
