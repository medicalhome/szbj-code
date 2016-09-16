SELECT cd.document_cda.extract('*').getClobVal() document_cda,document_type,
service_id,cd.document_lid,cd.in_record_type,cd.visit_sn
  FROM clinical_document cd
 WHERE document_sn=/*documentSn*/
   AND delete_flag = '0'