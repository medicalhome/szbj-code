SELECT cd.document_cda.extract('*').getClobVal() document_cda,document_type,
service_id,cd.in_record_type,cd.visit_sn
  FROM clinical_document cd
 WHERE document_lid=/*documentLid*/
   AND service_id=/*serviceId*/
   AND delete_flag = '0'