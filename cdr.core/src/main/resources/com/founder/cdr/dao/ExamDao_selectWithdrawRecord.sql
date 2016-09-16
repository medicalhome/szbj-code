/**
 * 查询召回信息
 */
select withdraw_person_name,
       withdraw_time,
       withdraw_reason
  from withdraw_record
 where delete_flag='0'
   and report_sn=/*reportSn*/