/**
 * V2消息中更新batchId、serviceId
 */
UPDATE MESSAGE a SET
     /*IF serviceId != NULL*/
     a.service_id=/*serviceId*/,
     /*END*/
     /*IF batchId != NULL*/
     a.batch_id=/*batchId*/,
     /*END*/
     a.update_time=sysdate
   WHERE a.id=/*id*/'1'
   