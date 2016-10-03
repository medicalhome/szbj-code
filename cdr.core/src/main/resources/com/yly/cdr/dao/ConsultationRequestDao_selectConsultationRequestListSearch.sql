/**
*
*[FUN]會診列表
*@version 1.0, 2014/07/14
*@author li_shenggen
*@since 1.0
*
*會診申請检索结果
*/
select cr.request_no,
		cr.patient_Sn,
		cr.visit_Sn,
		cr.patient_lid,
		cr.patient_domain,
       cr.consultation_type,
       cr.consultation_type_name,
       cr.urgency_code,
       cr.urgency_name,
       cr.request_time,
       cr.order_dept_id,
       cr.order_dept_name,
       cr.request_doctor_name,
       cr.consultation_person_name,
       cr.consultation_dept_name,
       cr.consultation_org_code,
       cr.consultation_org_name,
       cr.org_code,
       cr.org_name
   from consultation_request cr
  where cr.patient_sn = /*patientSn*/
   and cr.delete_flag = '0'
   /*IF consultationListSearchPra.consultationStartDate != null && consultationListSearchPra.consultationStartDate.length() != 0*/
   and cr.request_time >= to_date(/*consultationListSearchPra.consultationStartDate*/''||'00-00-00', 'YYYY-MM-DD hh24:mi:ss')
   /*END*/
    /*IF consultationListSearchPra.consultationStopDate != null && consultationListSearchPra.consultationStopDate.length() != 0*/
   and cr.request_time <= to_date(/*consultationListSearchPra.consultationStopDate*/''||'23-59-59', 'YYYY-MM-DD hh24:mi:ss')
   /*END*/
    /*IF consultationListSearchPra.consultationRequestDept != null && consultationListSearchPra.consultationRequestDept.length() != 0*/
   and cr.order_dept_id = /*consultationListSearchPra.consultationRequestDept*/
   /*END*/   
   /*IF consultationListSearchPra.orgCode != NULL && consultationListSearchPra.orgCode.length() != 0*/
   and cr.org_code = /*consultationListSearchPra.orgCode*/
   /*END*/
   order by cr.request_time desc,cr.request_no desc