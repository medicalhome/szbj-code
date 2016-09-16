/**
* 
* [FUN]V05-101手术列表
* @version 1.0, 2014/12/16  
* @author 张彬
* @since 1.0
* 检索结果
*/
select sp.order_sn,
	   sp.order_time,
	   sp.visit_sn,
       sp.operation_Name,
       sp.request_no,
       sp.plan_exec_time,
       sp.operation_property_name,
       sp.anesthesia_name,
       sp.operation_table,
       sp.operator_name,
       sp.exec_dept_name as exec_dept,
       sp.order_dept_name as order_dept,
       sp.org_Code
  from procedure_order sp
 where sp.patient_sn=/*patientSn*/ 
   and sp.delete_flag=0
      /*IF procedureListSearchPra.operationName != null && procedureListSearchPra.operationName.length() != 0*/
   and sp.operation_Name like '%' || /*procedureListSearchPra.operationName*/'' || '%'
      /*END*/
      /*IF procedureListSearchPra.operationDateFromStr != null && procedureListSearchPra.operationDateFromStr.length() != 0*/
   and sp.plan_exec_time >= to_date( /*procedureListSearchPra.operationDateFromStr*/''||'00-00-00', 'YYYY-MM-DD hh24:mi:ss')
      /*END*/
      /*IF procedureListSearchPra.operationDateToStr != null && procedureListSearchPra.operationDateToStr.length() != 0*/
   and sp.plan_exec_time <= to_date( /*procedureListSearchPra.operationDateToStr*/''||'23-59-59', 'YYYY-MM-DD hh24:mi:ss')
      /*END*/
      /*IF procedureListSearchPra.surgicalDept != null && procedureListSearchPra.surgicalDept.length() != 0*/
   and sp.exec_dept = /*procedureListSearchPra.surgicalDept*/''
      /*END*/
      /*IF procedureListSearchPra.zhudao != NULL && procedureListSearchPra.zhudao.length() != 0*/
   and pp.operator_name like '%' || /*procedureListSearchPra.zhudao*/'' || '%'
      /*END*/    
--      /*IF procedureListSearchPra.mazui != NULL && procedureListSearchPra.mazui.length() != 0*/
--   and pp.mazui like '%' || /*procedureListSearchPra.mazui*/'' || '%'
--      /*END*/
--      /*IF procedureListSearchPra.procedureLevel != NULL && procedureListSearchPra.procedureLevel.length() != 0*/
--   and pr.procedure_Level = /*procedureListSearchPra.procedureLevel*/''
--      /*END*/
      /*IF procedureListSearchPra.workload != NULL && procedureListSearchPra.workload.length() != 0*/
   	and sp.operation_table like '%' || /*procedureListSearchPra.workload*/'' || '%'
      /*END*/    
--      /*IF procedureListSearchPra.healingGrade != NULL && procedureListSearchPra.healingGrade.length() != 0*/
--   and sp.healing_Grade = /*procedureListSearchPra.healingGrade*/''
--      /*END*/

      /*IF procedureListSearchPra.visitSn != null && procedureListSearchPra.visitSn.length() != 0*/
   and sp.visit_sn = /*procedureListSearchPra.visitSn*/
      /*END*/
      /*IF procedureListSearchPra.inpatientDate != null && procedureListSearchPra.inpatientDate.length() != 0*/
   and sp.plan_exec_time between TO_DATE(/*procedureListSearchPra.inpatientDate*/,'YYYY-MM-DD') and TO_DATE(/*procedureListSearchPra.inpatientDate*/ || '23:59:59', 'YYYY-MM-DD HH24:MI:SS')
      /*END*/
      /*IF procedureListSearchPra.orgCode != NULL && procedureListSearchPra.orgCode.length() != 0*/
   and sp.org_code = /*procedureListSearchPra.orgCode*/''
      /*END*/
 order by sp.ORDER_TIME desc, sp.order_Sn desc
   
   
   
  