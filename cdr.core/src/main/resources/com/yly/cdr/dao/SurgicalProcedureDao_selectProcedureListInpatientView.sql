/**
* 
* [FUN]V05-101手术列表 -- 住院视图
* @version 1.0, 2013/10/25 
* @author 常学文
* @since 1.0
* 检索结果
* [BUG]0038443 ADD BEGIN
*/
select pr.order_sn,
       pr.visit_sn,
       pr.operation_name,
       pr.order_name,
       pr.order_time,
       pr.exec_dept,
       pr.exec_dept_name,
       pr.order_status_name,
       pr.charge_status_name,
       pr.order_person_name,
       pr.procedure_level,
       pr.procedure_level_name
  from procedure_order pr
 where pr.patient_sn=/*patientSn*/ 
   and pr.delete_flag=0
      /*IF procedureListSearchPra.operationName != null && procedureListSearchPra.operationName.length() != 0*/
   and pr.operation_name like '%' || /*procedureListSearchPra.operationName*/'' || '%'
      /*END*/
      /*IF procedureListSearchPra.operationDateFromStr != null && procedureListSearchPra.operationDateFromStr.length() != 0*/
   and pr.order_time >= to_date( /*procedureListSearchPra.operationDateFromStr*/''||'00-00-00', 'YYYY-MM-DD hh24:mi:ss')
      /*END*/
      /*IF procedureListSearchPra.operationDateToStr != null && procedureListSearchPra.operationDateToStr.length() != 0*/
   and pr.order_time <= to_date( /*procedureListSearchPra.operationDateToStr*/''||'23-59-59', 'YYYY-MM-DD hh24:mi:ss')
      /*END*/
      /*IF procedureListSearchPra.surgicalDept != null && procedureListSearchPra.surgicalDept.length() != 0*/
   and pr.exec_dept = /*procedureListSearchPra.surgicalDept*/''
      /*END*/
      /*IF procedureListSearchPra.procedureLevel != NULL && procedureListSearchPra.procedureLevel.length() != 0*/
   and pr.procedure_Level = /*procedureListSearchPra.procedureLevel*/''
      /*END*/
      /*IF procedureListSearchPra.visitSn != null && procedureListSearchPra.visitSn.length() != 0*/
   and pr.visit_sn = /*procedureListSearchPra.visitSn*/
      /*END*/
      /*IF procedureListSearchPra.inpatientDate != null && procedureListSearchPra.inpatientDate.length() != 0*/
   and pr.order_time between TO_DATE(/*procedureListSearchPra.inpatientDate*/,'YYYY-MM-DD') and TO_DATE(/*procedureListSearchPra.inpatientDate*/ || '23:59:59', 'YYYY-MM-DD HH24:MI:SS')
      /*END*/
 order by pr.order_sn desc
   
   
   
  