select *
from patient_fav_folder pff
where pff.delete_flag = '0' and pff.create_user_id = /*userId*/
   /*IF patientSn != null && patientSn.length() != 0*/
   and exists (select 'X' from patient_fav pf where pf.folder_sn = pff.folder_sn and pf.delete_flag = '0' and pf.patient_sn = /*patientSn*/)
   /*END*/

order by create_time
   