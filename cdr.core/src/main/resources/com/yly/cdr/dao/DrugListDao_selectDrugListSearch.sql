/**
 * 
 * [FUN]V05-101就诊列表
 * @version 1.0, 2012/02/29  
 * @author 郭红燕
 * @since 1.0
 * 就诊检索结果
 */
SELECT mo.order_sn,
       mo.approved_Drug_Name,
       mo.medicine_Type_Name,
       mo.medicine_Form,
       mo.route_Name,
       mo.dosage,
       mo.total_Dosage,
       mo.medicine_Frenquency,
       mo.medicine_freq_name,
       mo.temporary_Flag,
       mo.order_Person,
       mo.order_person_name,
       mo.order_Time,
       mo.exec_Dept,
       mo.prescription_sn--,
       --mo.dispensing_sn
  FROM medication_order mo
 WHERE mo.delete_flag=0 and mo.patient_sn = /*patientSn*/ and  mo.temporary_flag=/*temporaryFlag*/
   and not exists
   (
       select 1
         from hide_drug hd
        where mo.drug_code = hd.drug_code
          and hd.delete_flag = '0'
          and hd.user_name = /*userSn*/
   )
   /*IF approvedDrugName != null && approvedDrugName.length() != 0*/
   and mo.approved_Drug_Name like '%' ||  /*approvedDrugName*/'' || '%'
   /*END*/
   /*IF orderTime1 != null && orderTime1.length() != 0*/
   and mo.order_time >= to_date(/*orderTime1*/,'YYYY-MM-DD')
   /*END*/
   /*IF orderTime2 != null && orderTime2.length() != 0*/
   and mo.order_time <= to_date(/*orderTime2*/,'YYYY-MM-DD')
   /*END*/
   /*IF medicineTypeName != null && medicineTypeName.length() != 0*/
   and mo.medicine_Type_Name like '%' || /*medicineTypeName*/'' || '%'
   /*END*/
   /*IF stopTime1 != NULL && stopTime1.length() != 0*/
   and mo.stop_time >= to_date(/*stopTime1*/,'YYYY-MM-DD')
   /*END*/
   /*IF stopTime2 != NULL && stopTime2.length() != 0*/
   and mo.stop_time <= to_date(/*stopTime2*/,'YYYY-MM-DD')
   /*END*/
   /*IF orderPersonName != NULL && orderPersonName.length() != 0*/
   and mo.order_Person_Name like '%' || /*orderPersonName*/'' || '%'
   /*END*/
   /*IF execDept != NULL && execDept.length() != 0*/
   and mo.exec_Dept = /*execDept*/
   /*END*/
   order by mo.order_time desc,mo.order_sn desc


  