/**
 * $Author :jin_peng
 * $Date : 2012/11/22 13:26$
 * [BUG]0011026 MODIFY BEGIN
 */
SELECT B.Patient_Domain,B.VISIT_TYPE_CODE,
	   A.ORDER_FLAG,A.ORDER_TITLE,A.ORDER_SN,A.ORDER_TYPE,A.ORDER_NAME,A.ORDER_TYPE_NAME,A.ORDER_TIME,
       A.CANCEL_TIME,A.ORDER_DEPT,A.ORDER_DEPT_name,A.Confirm_Person_Name,
       A.EXEC_DEPT,A.EXEC_DEPT_name,A.PATIENT_SN,A.WARDS_ID,A.ward_name,A.TEMPORARY_FLAG,A.Confirm_Time,
       A.CANCEL_PERSON_NAME ,A.CANCEL_PERSON,A.order_person_name,A.org_code
  FROM(
  --项目名称，医嘱类型，开嘱时间，取消时间，下医嘱科室，下医嘱人，医嘱确认人，执行科室，病人编码，病区，长期或临时，确认时间，取消人
    SELECT '1' ORDER_FLAG,'诊疗医嘱详细' ORDER_TITLE,TOD.ORDER_SN,TOD.ORDER_TYPE,TOD.ITEM_NAME ORDER_NAME,
         TOD.ORDER_TYPE_NAME ORDER_TYPE_NAME,TOD.ORDER_TIME,
         TOD.CANCEL_TIME,TOD.ORDER_DEPT,
         TOD.ORDER_DEPT_name,
         TOD.ORDER_PERSON,TOD.ORDER_PERSON_NAME,
         TOD.Nurse_Confirm_Person,TOD.Nurse_Confirm_Person_Name as Confirm_Person_Name,TOD.EXECUTIVE_DEPT EXEC_DEPT,
         TOD.EXEC_DEPT_name,
         TOD.PATIENT_SN,
         TOD.WARDS_ID,
         TOD.ward_name,
         TOD.TEMPORARY_FLAG,
         TOD.Nurse_Confirm_Time as Confirm_Time,
         TOD.CANCEL_PERSON_NAME,TOD.CANCEL_PERSON,TOD.VISIT_SN,
         TOD.ORG_CODE
      FROM TREATMENT_ORDER TOD--诊疗医嘱
     WHERE TOD.delete_flag=0
     UNION all
    
    SELECT '2' ORDER_FLAG,'护理医嘱详细' ORDER_TITLE,CO.ORDER_SN,CO.ORDER_TYPE,CO.ORDER_NAME,--医嘱类型名称
         CO.ORDER_TYPE_NAME,CO.INPUT_TIME ORDER_TIME,
         CO.CANCEL_TIME,CO.ORDER_DEPT,
         CO.ORDER_DEPT_name,
         CO.ORDER_PERSON, 
         Co.ORDER_PERSON_NAME,
         CO.Nurse_Confirm_Person,
         CO.Nurse_Confirm_Person_Name as Confirm_Person_Name,
         CO.EXEC_DEPT,
         CO.EXEC_DEPT_name,
         CO.PATIENT_SN,
         CO.WARDS_ID,
         CO.wards_name as ward_name,
         CO.USAGE TEMPORARY_FLAG,
         CO.Nurse_Confirm_Time as Confirm_Time,
         CO.CANCEL_PERSON_NAME,CO.CANCEL_PERSON,CO.VISIT_SN,
         CO.ORG_CODE
      FROM CARE_ORDER CO--护理医嘱
     WHERE CO.delete_flag=0
     UNION all
--    /*IF "inp".equals(flag)*/
--    SELECT '3' ORDER_FLAG,'手术医嘱详细' ORDER_TITLE,PO.ORDER_SN,PO.ORDER_TYPE,PO.ORDER_NAME,
--         PO.ORDER_TYPE_NAME,PO.ORDER_TIME,
--         PO.CANCEL_TIME,PO.ORDER_DEPT,
--        PO.ORDER_DEPT_name,
--         PO.ORDER_PERSON,PO.ORDER_PERSON_NAME,
--         PO.Confirm_Person,PO.Confirm_Person_Name,PO.exec_dept,
--        PO.EXEC_DEPT_name,
--         PO.PATIENT_SN,
--         po.WARDS_ID,
--         po.wards_name as ward_name,
--         po.TEMPORARY_FLAG,PO.Confirm_Time,
--         PO.CANCEL_PERSON_NAME,PO.CANCEL_PERSON,PO.VISIT_SN,
--         PO.ORG_CODE
--      FROM PROCEDURE_ORDER PO--手术医嘱
--    WHERE PO.delete_flag=0
--     UNION all
--     /*END*/
--    SELECT '4' ORDER_FLAG,'检查医嘱详细' ORDER_TITLE,EO.ORDER_SN,EO.ORDER_TYPE,EO.ITEM_NAME ORDER_NAME,
--         EO.ORDER_TYPE_NAME ORDER_TYPE_NAME,EO.ORDER_TIME,
--         EO.CANCEL_TIME,EO.ORDER_DEPT,
--         EO.ORDER_DEPT_name,
--         EO.ORDER_PERSON,EO.ORDER_PERSON_NAME,
--         EO.Confirm_Person,EO.Confirm_Person_Name,EO.EXECUTIVE_DEPT EXEC_DEPT,
--         EO.EXEC_DEPT_name,
--         EO.PATIENT_SN,
--         EO.WARDS_ID,
--         EO.wards_name as ward_name,
--         EO.TEMPORARY_FLAG,EO.Confirm_Time,
--         EO.CANCEL_PERSON_NAME,EO.CANCEL_PERSON,EO.VISIT_SN
--      FROM EXAMINATION_ORDER EO--检查医嘱
--     WHERE EO.delete_flag=0
--     UNION all
--    SELECT '5' ORDER_FLAG,'检验医嘱详细' ORDER_TITLE,LO.ORDER_SN,LO.ORDER_TYPE,LO.ITEM_NAME ORDER_NAME,
--         LO.ORDER_TYPE_NAME ORDER_TYPE_NAME,LO.ORDER_TIME,
--         LO.CANCEL_TIME,LO.ORDER_DEPT,
--         LO.ORDER_DEPT_name,
--         LO.ORDER_PERSON,LO.ORDER_PERSON_NAME,
--         LO.Confirm_Person,LO.Confirm_Person_Name,LO.EXECUTIVE_DEPT EXEC_DEPT,
--         LO.executive_dept_name as EXEC_DEPT_name ,
--         LO.PATIENT_SN,
--         LO.WARDS_ID,
--         LO.wards_name as ward_name,
--         LO.TEMPORARY_FLAG,LO.Confirm_Time,
--         LO.CANCEL_PERSON_NAME,LO.CANCEL_PERSON,LO.VISIT_SN
--      FROM LAB_ORDER LO--检验医嘱
--     WHERE LO.delete_flag=0
--     UNION all
    SELECT '6' ORDER_FLAG,'其他医嘱详细' ORDER_TITLE,go.ORDER_SN,go.order_type_code as ORDER_TYPE,go.ORDER_NAME as ORDER_NAME,
         go.ORDER_TYPE_NAME ORDER_TYPE_NAME,
         go.INPUT_TIME ORDER_TIME,
         go.CANCEL_TIME,go.order_dept_id as ORDER_DEPT,
         go.ORDER_DEPT_name,
         go.order_person_id as ORDER_PERSON,go.ORDER_PERSON_NAME,
         go.nurse_confirm_person_id as nurse_Confirm_Person,go.nurse_Confirm_Person_Name as Confirm_Person_Name,
         go.exec_dept_id as exec_dept,
         go.EXEC_DEPT_name,
         go.PATIENT_SN,
         go.ward_id as WARDS_ID,
         go.ward_name,
         go.usage TEMPORARY_FLAG,
         go.nurse_Confirm_Time as Confirm_Time,
         go.CANCEL_PERSON_NAME,go.cancel_person_id as CANCEL_PERSON,go.VISIT_SN,
         go.ORG_CODE
      FROM general_order go--其他医嘱
     WHERE go.delete_flag=0
     /*IF "inp".equals(flag)*/
     UNION all
     SELECT '7' ORDER_FLAG,'会诊医嘱详细' ORDER_TITLE,co.ORDER_SN,co.order_type_code as order_type,co.ORDER_NAME,
         co.ORDER_TYPE_NAME ORDER_TYPE_NAME,co.ORDER_TIME,
         co.CANCEL_TIME,co.order_dept_id as ORDER_DEPT,
         co.ORDER_DEPT_name,
         co.order_person_id as order_person,co.ORDER_PERSON_NAME,
         co.confirm_person_id as Confirm_Person,co.Confirm_Person_Name,
         --co.EXECUTIVE_DEPT EXEC_DEPT,
         '' EXEC_DEPT,
         --co.EXEC_DEPT_name,
         '' EXEC_DEPT_name,
         co.PATIENT_SN,
         --12 PATIENT_SN,
         --co.WARDS_ID,
         '' WARDS_ID,
         ''ward_name,
         --co.TEMPORARY_FLAG,
          '1' TEMPORARY_FLAG,
         co.Confirm_Time,
         co.CANCEL_PERSON_NAME,co.cancel_person_id as CANCEL_PERSON,co.VISIT_SN,
         co.ORG_CODE
      FROM consultation_order co  --会诊医嘱
     WHERE co.delete_flag=0
     /*END*/
   ) A , medical_visit B
 WHERE A.PATIENT_SN = /*patientSn*/'' and B.VISIT_SN = A.VISIT_SN and B.delete_flag = 0
      /*IF nonDrugOrderPra.orderStrStartTime != null && nonDrugOrderPra.orderStrStartTime.length() != 0*/
         AND A.ORDER_TIME >= to_date(/*nonDrugOrderPra.orderStrStartTime*/''||'00-00-00','YYYY-MM-DD HH24:MI:SS')
        /*END*/
        /*IF nonDrugOrderPra.orderStrEndTime != null && nonDrugOrderPra.orderStrEndTime.length() != 0*/
         AND A.ORDER_TIME <= to_date(/*nonDrugOrderPra.orderStrEndTime*/''||'23-59-59','YYYY-MM-DD HH24:MI:SS')
        /*END*/ 
     /*IF nonDrugOrderPra.orderType != null && nonDrugOrderPra.orderType.length() != 0 && !"-1".equals(nonDrugOrderPra.orderType)*/
         AND A.ORDER_TYPE = /*nonDrugOrderPra.orderType*/
        /*END*/
        /*IF nonDrugOrderPra.orderDept != null && nonDrugOrderPra.orderDept.length() != 0 && !"-1".equals(nonDrugOrderPra.orderDept)*/
         AND A.ORDER_DEPT = /*nonDrugOrderPra.orderDept*/
        /*END*/
     /*IF nonDrugOrderPra.orderPerson != null && nonDrugOrderPra.orderPerson.length() != 0*/
         AND A.ORDER_PERSON_NAME like '%' || /*nonDrugOrderPra.orderPerson*/'' || '%'
        /*END*/
       /*IF nonDrugOrderPra.execDept != null && nonDrugOrderPra.execDept.length() != 0 && !"-1".equals(nonDrugOrderPra.execDept)*/
         AND A.EXEC_DEPT = /*nonDrugOrderPra.execDept*/
        /*END*/
        /*IF nonDrugOrderPra.orderName != null && nonDrugOrderPra.orderName.length() != 0*/
         AND A.ORDER_NAME like '%' || /*nonDrugOrderPra.orderName*/'' || '%'
        /*END*/
		/*IF nonDrugOrderPra.visitSn != null && nonDrugOrderPra.visitSn.length() != 0*/
		     and A.VISIT_SN = /*nonDrugOrderPra.visitSn*/
		/*END*/
		 /*IF nonDrugOrderPra.visitTimes != null && nonDrugOrderPra.visitTimes.length() != 0*/
         and B.visit_times = /*nonDrugOrderPra.visitTimes*/
        /*END*/
		/*IF nonDrugOrderPra.inpatientDate != null && nonDrugOrderPra.inpatientDate.length() != 0*/
		     and A.ORDER_TIME between TO_DATE(/*nonDrugOrderPra.inpatientDate*/,'YYYY-MM-DD') and TO_DATE(/*nonDrugOrderPra.inpatientDate*/ || '23:59:59', 'YYYY-MM-DD HH24:MI:SS')
		/*END*/
		/*IF nonDrugOrderPra.visitDateSn != null && nonDrugOrderPra.visitDateSn.length() != 0*/
   			and A.VISIT_SN = /*nonDrugOrderPra.visitDateSn*/
   		/*END*/
   		/*IF nonDrugOrderPra.orgCode != null && nonDrugOrderPra.orgCode.length() != 0*/
   			and A.ORG_CODE = /*nonDrugOrderPra.orgCode*/
   		/*END*/
       ORDER BY A.ORDER_TIME DESC, A.ORDER_SN DESC
/**[BUG]0011026 MODIFY END*/