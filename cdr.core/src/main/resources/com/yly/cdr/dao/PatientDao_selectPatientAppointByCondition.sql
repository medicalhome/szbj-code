select p.patient_sn,
               p.gender_name,
               p.birth_date,
               pc.outpatient_no,
               pc.unit_sn,
               pc.doctor_sn,
               pc.name,
               p.patient_name,
               pc.appointment_sn,
               pc.clinic_type,
               pc.req_type,
               pc.input_date,
               pc.request_date
          from patient p,
               (select 
                       a.appointment_sn,
                       c.name,
                       a.doctor_sn,
                       a.unit_sn,
                       a.clinic_type,
                       a.req_type,
                       a.input_date,
                       a.request_date,
                       p. patient_eid,
                       p. outpatient_no,
                       p. patient_lid
                  from patient_cross_index p, appointment a, code_person c
                 where p.delete_flag = 0
                   and a.delete_flag = 0
                   and a.cancel_date is null
                   and p.patient_lid = a.patient_lid
                   and a.doctor_sn = /*patientListSearchPra.doctorId*/'3'  
                   and p.patient_domain='01'
                   and a.doctor_sn = c.code
                /*IF patientListSearchPra.requestStartDate != NULL && patientListSearchPra.requestStartDate.length() != 0*/
                 and a.request_date >=
                     to_date( /*patientListSearchPra.requestStartDate*/'2012-1-12' || '00-00-00',
                           'YYYY-MM-DD hh24:mi:ss')
                /*END*/
                /*IF patientListSearchPra.requestEndDate != NULL && patientListSearchPra.requestEndDate.length() != 0*/
                  and a.request_date <=
                      to_date( /*patientListSearchPra.requestEndDate*/'2012-1-12' || '23-59-59',
                             'YYYY-MM-DD hh24:mi:ss')
                /*END*/
                  /*IF patientListSearchPra.normalPatient == 1*/
                 UNION ALL
                 select 
                       a.appointment_sn,
                       c.name,
                       a.doctor_sn,
                       a.unit_sn,
                       a.clinic_type,
                       a.req_type,
                       a.input_date,
                       a.request_date,
                       p. patient_eid,
                       p. outpatient_no,
                       p. patient_lid
                  from patient_cross_index p, appointment a,code_person c
                 where p.delete_flag = 0
                   and a.delete_flag = 0
                   and a.cancel_date is null
                   and p.patient_lid = a.patient_lid
                   and a.doctor_sn = c.code(+)
                   and p.patient_domain='01'
                    and (a.doctor_sn is null or a.doctor_sn in(select t1.doctor_sn
                     from appointment t1
                    where t1.doctor_sn is not null
                      and t1.doctor_sn <> /*patientListSearchPra.doctorId*/'3'))
                   and a.unit_sn = (select t.unit_sn  from appointment t where t.doctor_sn=/*patientListSearchPra.doctorId*/'3' and rownum =1)
                    /*IF patientListSearchPra.requestStartDate != NULL && patientListSearchPra.requestStartDate.length() != 0*/
                 and a.request_date >=
                     to_date( /*patientListSearchPra.requestStartDate*/'2012-1-12' || '00-00-00',
                           'YYYY-MM-DD hh24:mi:ss')
                /*END*/
                /*IF patientListSearchPra.requestEndDate != NULL && patientListSearchPra.requestEndDate.length() != 0*/
                  and a.request_date <=
                      to_date( /*patientListSearchPra.requestEndDate*/'2012-1-12' || '23-59-59',
                             'YYYY-MM-DD hh24:mi:ss')
                /*END*/
                  /*END*/
                ) pc
         where  p.patient_eid = pc.patient_eid
         and p.delete_flag=0
          /*IF patientListSearchPra.patientName != NULL && patientListSearchPra.patientName.length() != 0*/
           and p.patient_name like '%' || /*patientListSearchPra.patientName*/'uuu' || '%' 
        /*END*/

 order by pc.request_date desc,pc.appointment_sn desc

