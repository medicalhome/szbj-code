/**
 * [FUN]CVIS 检查报告
 * @version 1.0, 2013/5/31  
 * @author tong_meng
 * @since 1.0
 * CVIS 检查报告（包括超声，冠脉造影等）
 */
select create_time,
	   item_class,
       item_name,
       item_sn,
       exam_result_procedure_sn,
       update_time,
       item_value,
       PARENT_EXAM_RESULT_DETAIL_SN,
       update_count,
       delete_flag,
       examination_result_detail_sn,
       delete_time,
       item_code 
  from examination_result_detail
 where item_class = /*itemClass*/ 
/*IF itemClass.equals('EC')*/
   and item_sn = /*itemSn*/
/*END*/
/*IF itemClass.equals('DSA')*/
   and exam_result_procedure_sn = /*examResultProcedureSn*/
/*END*/
   and delete_flag = '0'
  order by examination_result_detail_sn   