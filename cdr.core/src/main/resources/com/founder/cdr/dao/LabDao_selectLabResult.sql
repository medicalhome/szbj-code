/**
 * 
 * [FUN]V05-101检验报告结果列表
 * @version 1.0, 2012/03/16  
 * @author 金鹏
 * @since 1.0
 * 检验报告结果列表
 */
SELECT item_name_cn,
       item_name_en,
       item_unit,
       item_value,
       low_value,
       high_value,
       warn_low_value,
       warn_high_value,
       normal_flag,
       qualitative_results,
       display_order,
       report_memo,
       item_code,
       normal_ref_value_text,
       lab_result_composite_item_sn
  FROM lab_result_item
 WHERE delete_flag = '0'
   AND lab_result_composite_item_sn = /*compositeItemSn*/
 ORDER BY display_order