/**
 * $Author :jin_peng
 * $Date : 2012/11/22 13:26$
 * [BUG]0011026 MODIFY BEGIN
 * [FUN]V05-101就诊列表
 * @version 1.0, 2012/02/29  
 * @author 郭红燕
 * @since 1.0
 * 就诊检索结果
 */
SELECT distinct
	   mv.patient_domain,
	   mo.patient_sn,
	   mo.order_type,
       mo.order_sn,
       mo.order_seqnum,
       mo.approved_Drug_Name,
       mo.drug_Name,
       mo.medicine_Type_Name,
       mo.medicine_class,
       mo.medicine_Form,
       mo.route_Name,
       mo.dosage,
       mo.dosage_Unit,
       mo.total_Dosage,
       mo.medicine_Frenquency,
       mo.medicine_Freq_Name,
       mo.temporary_Flag,
       mo.order_Person,
       mo.order_person_name,
       mo.order_Time,
       mo.order_Dept,
       mo.order_Dept_name, 
       mo.prescription_sn,
       mo.total_Dosage_Unit,
       mo.order_start_time,
       mo.org_code,
       mo.english_name,
       mo.days
  FROM medication_order mo, medical_visit mv
  	/*IF drugListSearchPra.diseaseName != null &&  drugListSearchPra.diseaseName.length() != 0*/	
  	, diagnosis ds
  	/*END*/
 WHERE mo.delete_flag=0 
   and mv.delete_flag = 0 
   /*IF drugListSearchPra.diseaseName != null &&  drugListSearchPra.diseaseName.length() != 0*/	
   and ds.delete_flag(+) = 0 
   and mv.visit_sn = ds.visit_sn(+)
   /*END*/
   and mo.patient_sn = /*patientSn*/ 
   and mo.visit_sn = mv.visit_sn
   and not exists
      (select 1
         from hide_drug hd
        where mo.drug_code = hd.drug_code
          and hd.delete_flag = '0'
          and hd.user_name = /*userSn*/)
   /*IF drugListSearchPra.approvedDrugName != null && drugListSearchPra.approvedDrugName.length() != 0*/
   and mo.approved_Drug_Name like '%' ||  /*drugListSearchPra.approvedDrugName*/'' || '%'
   /*END*/
   /*IF drugListSearchPra.orderTimeFrom != null && drugListSearchPra.orderTimeFrom.length() != 0*/
   and mo.order_time >= to_date(/*drugListSearchPra.orderTimeFrom*/''||'00-00-00','yyyy-MM-dd hh24:mi:ss')
   /*END*/
   /*IF drugListSearchPra.orderTimeTo != null && drugListSearchPra.orderTimeTo.length() != 0*/
   and mo.order_time <= to_date(/*drugListSearchPra.orderTimeTo*/''||'23-59-59','yyyy-MM-dd hh24:mi:ss')
   /*END*/
   /*IF drugListSearchPra.medicineType != null && drugListSearchPra.medicineType.length() != 0*/
   and mo.medicine_Type = /*drugListSearchPra.medicineType*/
   /*END*/
   /*IF drugListSearchPra.stopTimeFrom != NULL && drugListSearchPra.stopTimeFrom.length() != 0*/
   and mo.stop_time >= to_date(/*drugListSearchPra.stopTimeFrom*/''||'00-00-00','yyyy-MM-dd hh24:mi:ss')
   /*END*/
   /*IF drugListSearchPra.stopTimeTo != NULL && drugListSearchPra.stopTimeTo.length() != 0*/
   and mo.stop_time <= to_date(/*drugListSearchPra.stopTimeTo*/''||'23-59-59','yyyy-MM-dd hh24:mi:ss')
   /*END*/
   /*IF drugListSearchPra.orderPersonName != NULL && drugListSearchPra.orderPersonName.length() != 0*/
   and mo.order_Person_Name like '%' || /*drugListSearchPra.orderPersonName*/'' || '%'
   /*END*/
   /*IF drugListSearchPra.orderDept != NULL && drugListSearchPra.orderDept.length() != 0*/
   and mo.order_Dept = /*drugListSearchPra.orderDept*/
   /*END*/
  
   /*IF drugListSearchPra.visitSn != null && drugListSearchPra.visitSn.length() != 0*/
   and mo.visit_sn = /*drugListSearchPra.visitSn*/
   /*END*/
    /*IF drugListSearchPra.visitTimes != null && drugListSearchPra.visitTimes.length() != 0*/
   and mv.visit_times = /*drugListSearchPra.visitTimes*/
   /*END*/
   /*IF drugListSearchPra.inpatientDate != null && drugListSearchPra.inpatientDate.length() != 0*/
   and nvl(mo.order_start_time,mo.order_time) between to_date(/*drugListSearchPra.inpatientDate*/,'YYYY-MM-DD') and to_date(/*drugListSearchPra.inpatientDate*/ || '23:59:59', 'YYYY-MM-DD HH24:MI:SS')
   /*END*/
   /*IF drugListSearchPra.inpatientLongTermDate != null && drugListSearchPra.inpatientLongTermDate.length() != 0*/
   and TRUNC(MO.ORDER_START_TIME) <= TO_DATE(/*drugListSearchPra.inpatientLongTermDate*/,'YYYY-MM-DD')
   and (MO.STOP_TIME >= TO_DATE(/*drugListSearchPra.inpatientLongTermDate*/, 'YYYY-MM-DD')
	or MO.STOP_TIME IS NULL)
   /*END*/
   /*IF drugListSearchPra.longTempFlag != null && drugListSearchPra.longTempFlag.length() != 0*/
   and mo.temporary_flag = /*drugListSearchPra.longTempFlag*/
   /*END*/
   /*IF drugListSearchPra.cancelOrderStatus != null && drugListSearchPra.cancelOrderStatus.length() != 0*/
   and mo.order_status != /*drugListSearchPra.cancelOrderStatus*/
   /*END*/
   
   /*IF drugListSearchPra.visitType != null &&  drugListSearchPra.visitType.length() != 0*/
   and mv.visit_type_code = /*drugListSearchPra.visitType*/	     
   /*END*/ 
   /*IF drugListSearchPra.diseaseName != null &&  drugListSearchPra.diseaseName.length() != 0*/
   and ds.disease_name like /*drugListSearchPra.diseaseName*/   
   /*END*/ 
   /*IF drugListSearchPra.visitDateSn != null && drugListSearchPra.visitDateSn.length() != 0*/
   and mo.visit_sn = /*drugListSearchPra.visitDateSn*/
   /*END*/
   /*IF drugListSearchPra.temporaryFlag != null && drugListSearchPra.temporaryFlag.length() != 0*/
   and mo.temporary_flag = /*drugListSearchPra.temporaryFlag*/
   /*END*/
   /*IF drugListSearchPra.orgCode != null && drugListSearchPra.orgCode.length() != 0*/
   and mo.org_code = /*drugListSearchPra.orgCode*/
   /*END*/
   
   /*IF drugListSearchPra.inpatientLongTermDate != null && drugListSearchPra.inpatientLongTermDate.length() != 0*/
   	ORDER BY MO.ORDER_START_TIME, MO.ORDER_SN DESC
   /*END*/
   	
   /*IF drugListSearchPra.inpatientDate != null && drugListSearchPra.inpatientDate.length() != 0*/
   	ORDER BY MO.ORDER_START_TIME DESC, MO.ORDER_SN DESC
   /*END*/
   	
   /*IF (drugListSearchPra.inpatientLongTermDate == null || drugListSearchPra.inpatientLongTermDate.length() == 0) && (drugListSearchPra.inpatientDate == null || drugListSearchPra.inpatientDate.length() == 0)*/
   	ORDER BY mo.order_time DESC,mo.order_sn DESC 
   /*END*/
/**[BUG]0011026 MODIFY END*/