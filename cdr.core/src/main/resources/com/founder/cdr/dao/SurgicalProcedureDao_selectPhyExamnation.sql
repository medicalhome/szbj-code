/**
 * 
 * [FUN]V05-101手术列表
 * @version 1.0, 2012/02/29  
 * @author 张彬
 * @since 1.0
 * 检索结果
 */
select nop.operator_name,ane.anesthesia_dept,ane.anesthesia_dept_name,pe.item_name,pe.item_result,
pe.item_result_unit,pe.exam_time,pe.exam_description 
  from physical_examination pe,nursing_operation nop,anesthesia ane 
 where ane.anesthesia_sn=pe.anesthesia_sn 
   and pe.operation_sn=nop.operation_sn 
   and ane.anesthesia_Sn=/*anesthesiaSn*/