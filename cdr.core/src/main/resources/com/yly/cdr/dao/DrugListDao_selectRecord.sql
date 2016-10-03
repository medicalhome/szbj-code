/**
 * 
 * [FUN]V05-101住院医嘱
 * @version 1.0, 2012/02/29  
 * @author 张彬
 * @since 1.0
 */
SELECT ar.Administration_Sn,
       ar.Operation_Sn,
       ar.patient_Sn,
       ar.order_Sn,
       ar.medicine_Name,
       ar.medicine_Form,
       ar.dosage,
       ar.dosage_Unit,
       nop.operator_Name,
       nop.operator_Id,
       ar.medicine_Time,
       ar.bed_No,
       ar.dept,
       ar.dept_name
  FROM Administration_Record ar,nursing_operation nop
 WHERE ar.order_Sn= /*orderSn*/ 
   AND ar.order_sn=nop.order_sn
   AND ar.delete_flag=0
   AND nop.delete_flag=0
 
  
  