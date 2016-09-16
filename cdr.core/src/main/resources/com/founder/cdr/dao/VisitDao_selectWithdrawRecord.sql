/**
 * 查询召回信息
 */
select visit_sn,
       withdraw_person_name,
       withdraw_time,
       withdraw_reason
  from withdraw_record
 WHERE delete_flag='0'
   and visit_sn=/*visitSn*/