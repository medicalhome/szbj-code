/**
 * 
 * [FUN]V05-801麻醉记录列表/护理记录列表
 *
 * @since 1.0
 * 麻醉记录列表/护理记录列表
 */
 
SELECT * FROM (
SELECT cd.document_sn, 
	   cd.visit_sn,
	   cd.patient_sn,
	   cd.document_type, 
	   cd.document_lid, 
	   cd.document_type_name, 
	   cd.document_name, 
	   cd.document_author_name,
	   cd.document_modifier_name,
	   cd.submit_time,
	   cd.review_person_name,
	   cd.document_url,
	   cd.signature_id,
	   cd.org_code,
	   cd.org_name
  FROM clinical_document cd,
       medical_visit mv
 WHERE cd.patient_sn = /*patientSn*/
   AND cd.delete_flag='0'
   AND mv.visit_sn = cd.visit_sn
   AND mv.delete_flag(+) ='0'
   /*IF docListSearchParameters.visitTimes != null && docListSearchParameters.visitTimes.length() != 0*/
   and mv.visit_times = /*docListSearchParameters.visitTimes*/
   /*END*/
    /*IF docListSearchParameters.portalViewId == 'V004'*/
   AND cd.service_id in ('BS327', 'BS329', 'BS379', 'BS401')
    /*END*/
     /*IF docListSearchParameters.portalViewId == 'V005' && docListSearchParameters.documentType != NULL && docListSearchParameters.documentType != '-1' */
   AND cd.document_type in /*docListSearchParameters.portalDocumentTypes*/('10','12')
    /*END*/
   /*IF docListSearchParameters.documentName != NULL && docListSearchParameters.documentName.length() != 0*/
   AND cd.document_name like '%' || /*docListSearchParameters.documentName*/ || '%'
   /*END*/
   /*IF docListSearchParameters.submitTimeFrom != NULL && docListSearchParameters.submitTimeFrom.length() != 0*/
   AND cd.submit_time >= to_date(/*docListSearchParameters.submitTimeFrom*/, 'YYYY-MM-DD')
   /*END*/
   /*IF docListSearchParameters.submitTimeTo != NULL && docListSearchParameters.submitTimeTo.length() != 0*/
   AND cd.submit_time <= to_date(/*docListSearchParameters.submitTimeTo*/, 'YYYY-MM-DD')
   /*END*/
   AND cd.document_type != '04'
   ) CL
ORDER BY CL.submit_time desc,CL.document_sn desc
     