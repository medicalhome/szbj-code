/**
 * 
 * [FUN]V05-801病历文书列表
 * @version 1.0, 2012/03/02  
 * @author wu_jianfeng
 * @since 1.0
 * 病历文书列表结果
 */
 /* $Author :chang_xuewen
  * $Date : 2013/10/24 11:00$
  * [BUG]0038443 MODIFY BEGIN
  */
SELECT * FROM (
SELECT cd.document_sn, 
	   cd.visit_sn,
	   cd.patient_sn,
	   cd.patient_domain,
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
	   cd.service_id,
	   cd.org_code,
	   cd.org_name
  FROM clinical_document cd 
 WHERE cd.patient_sn = /*patientSn*/
   AND cd.delete_flag='0'
   AND cd.document_type != '04'
   /*IF docListSearchParameters.orgCode != NULL && docListSearchParameters.orgCode.length() != 0*/
   AND cd.org_code = /*docListSearchParameters.orgCode*/
   /*END*/
    /*IF docListSearchParameters.documentType != NULL && docListSearchParameters.documentType.length() != 0*/
   AND cd.document_type = /*docListSearchParameters.documentType*/
   /*END*/
   /*IF docListSearchParameters.documentName != NULL && docListSearchParameters.documentName.length() != 0*/
   AND cd.document_name like '%' || /*docListSearchParameters.documentName*/ || '%'
   /*END*/
   /*IF docListSearchParameters.documentAuthor != NULL && docListSearchParameters.documentAuthor.length() != 0*/
   AND cd.document_author_name like '%' || /*docListSearchParameters.documentAuthor*/ || '%'
   /*END*/
   /*IF docListSearchParameters.submitTimeFrom != NULL && docListSearchParameters.submitTimeFrom.length() != 0*/
   AND cd.submit_time >= to_date(/*docListSearchParameters.submitTimeFrom*/, 'YYYY-MM-DD')
   /*END*/
   /*IF docListSearchParameters.submitTimeTo != NULL && docListSearchParameters.submitTimeTo.length() != 0*/
   AND cd.submit_time <= to_date(/*docListSearchParameters.submitTimeTo*/, 'YYYY-MM-DD')
   /*END*/
   /*IF docListSearchParameters.inDocumentTypes != NULL && docListSearchParameters.inDocumentTypes.size != 0*/
   AND cd.document_type in /*docListSearchParameters.inDocumentTypes*/(10,20,30)
   /*END*/
   /*IF docListSearchParameters.visitSn != null && docListSearchParameters.visitSn.length() != 0*/
   AND cd.visit_sn = /*docListSearchParameters.visitSn*/
   /*END*/
   /*IF docListSearchParameters.inpatientDate != null && docListSearchParameters.inpatientDate.length() != 0*/
   AND cd.WRITE_TIME between TO_DATE(/*docListSearchParameters.inpatientDate*/,'YYYY-MM-DD') AND TO_DATE(/*docListSearchParameters.inpatientDate*/ || '23:59:59', 'YYYY-MM-DD HH24:MI:SS')
   /*END*/
   /*IF "inpatientView".equals(viewFlag) */
   AND NOT EXISTS (SELECT 1 FROM DUAL WHERE (cd.service_id = 'BS329' or cd.service_id = 'BS337' or cd.service_id = 'BS327' or cd.service_id = 'BS370' or cd.service_id = 'BS375' or cd.service_id = 'BS379'))
   AND NOT EXISTS (SELECT 1 FROM DUAL WHERE (cd.service_id = 'BS336' AND (cd.document_name LIKE '%手术前讨论记录%' OR cd.document_name  LIKE '%手术前小结%' OR cd.document_name  LIKE '%手术记录%')))
   /*END*/   
/*IF "list".equals(viewFlag) */
   AND (cd.service_id = 'BS329' or cd.service_id = 'BS337' or cd.service_id = 'BS327' or cd.service_id = 'BS370' or cd.service_id = 'BS375' or cd.service_id = 'BS379'
   or (cd.service_id = 'BS336' AND (cd.document_name LIKE '%手术前讨论记录%' OR cd.document_name  LIKE '%手术前小结%' OR cd.document_name  LIKE '%手术记录%')))
/*END*/

   ) CL
ORDER BY CL.submit_time desc,CL.document_sn desc
/* [BUG]0038443 MODIFY END  */
     