select visit_sn,wm_concat(doctor_name) visit_doctor_name 
  from patient_doctor
 where delete_flag = '0'
   and visit_sn in /*visitSns*/(10, 20, 30)
 group by visit_sn