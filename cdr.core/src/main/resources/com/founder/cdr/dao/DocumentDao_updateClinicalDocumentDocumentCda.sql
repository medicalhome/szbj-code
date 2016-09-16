/**
 * 更新病历文档表中文档CDA
 * jia_yanqing
 */
update clinical_document cd
   set cd.document_cda = (
   select updateXMl(xmlType(m.content), '/ClinicalDocument/component/structuredBody/component/section/entry/observationMedia/value/text()', '' , 'xmlns="urn:hl7-org:v3"') from message m where id=/*message.id*/ and org_code=/*message.orgCode*/)
  
 where cd.patient_lid = /*clinicalDocument.patientLid*/
   and cd.patient_domain = /*clinicalDocument.patientDomain*/
   and cd.document_lid = /*clinicalDocument.documentLid*/
   and cd.service_id = /*clinicalDocument.serviceId*/
   and cd.org_code = /*clinicalDocument.orgCode*/
   and cd.delete_flag = 0