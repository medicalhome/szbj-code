/**
 * 
 * 根据visit_sn，得到病历文书信息
 * @version 1.0, 2012/09/27  
 * @author zhangbin
 * @since 1.0
 * 病历文书列表结果
 */
SELECT cd.document_sn, 
	   cd.visit_sn,
	   cd.patient_sn,
	   cd.document_type, 
	   cd.document_type_name, 
	   cd.document_name, 
	   cd.document_author_name,
	   cd.document_modifier_name,
	   cd.submit_time,
	   cd.review_person_name,
	   cd.document_url,
	   cd.signature_id
  FROM clinical_document cd 
 WHERE cd.visit_sn = /*visitSn*/
   AND (cd.document_type != '04' OR cd.service_id = 'BS337_S006')
   AND cd.delete_flag='0'
 ORDER BY cd.submit_time desc,cd.document_sn desc

     