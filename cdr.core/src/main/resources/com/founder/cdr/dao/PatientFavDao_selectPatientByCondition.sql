
select p.patient_sn, 
       p.patient_eid,
	   pf.patient_fav_sn,
	   pf.memo,
       p.patient_name, 
       p.birth_date, 
       p.gender_Name,
       mv.visit_date,
       mv.discharge_ward_name,
       mv.discharge_bed_no
  from patient p left outer join (
	     select visit_date, patient_sn, discharge_ward_name, discharge_bed_no from(
	     select visit_date, patient_sn, discharge_ward_name, discharge_bed_no, row_number()over(partition by patient_sn order by visit_date desc) rn 
	       from medical_visit 
	      where delete_flag='0'
	     ) where rn=1 ) mv on p.patient_sn = mv.patient_sn , patient_fav pf
where p.delete_flag = '0' and pf.delete_flag = '0' and 
      pf.patient_sn = p.patient_sn  and 
      pf.folder_sn=/*patientFavListSearchPra.folderSn*/
     /*IF patientFavListSearchPra.patientName != NULL && patientFavListSearchPra.patientName.length() != 0*/
        and p.patient_name like /*patientFavListSearchPra.patientName*/
  	 /*END*/
     /*IF patientFavListSearchPra.genderCode != NULL && patientFavListSearchPra.genderCode.length() != 0*/
        and p.gender_code = /*patientFavListSearchPra.genderCode*/
     /*END*/
   