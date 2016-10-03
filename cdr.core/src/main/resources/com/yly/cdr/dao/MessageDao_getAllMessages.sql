SELECT *
  FROM MESSAGE
 WHERE batch_id = /*batchId*/'OUT.S015.HL7MSG.LQ'
   AND (state = /*state*/'0' OR external_state = '0')
   /*IF dates != NULL && dates.length() != 0*/
   AND create_time like  to_date(/*dates*/,'yyyy/mm/dd')
   /*END*/   
 ORDER BY datetime