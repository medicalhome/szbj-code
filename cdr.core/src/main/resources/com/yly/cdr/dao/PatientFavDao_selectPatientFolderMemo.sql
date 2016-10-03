select pff.folder_name, pff.create_time, pf.memo
  from patient_fav_folder pff, patient_fav pf
 where pf.folder_sn = pff.folder_sn
   and pf.delete_flag = '0'
   and pff.delete_flag = '0'
   and pf.patient_sn = /*patientSn*/
   and pff.create_user_id =  /*userId*/
   and pf.memo <> ' '
 order by pff.create_time desc

   