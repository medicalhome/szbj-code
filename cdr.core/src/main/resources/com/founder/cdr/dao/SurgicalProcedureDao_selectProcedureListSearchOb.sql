/**
* 
* [FUN]V05-101手术列表
* @version 1.0, 2012/02/29  
* @author 张彬
* @since 1.0
* 检索结果
*/
select sp.procedure_Sn,
       sp.anesthesia_Sn,
       sp.order_sn,
       sp.operation_Name,
       sp.operation_Date,
       sp.surgical_Dept,
       sp.surgical_Dept_Name,
       sp.start_Time,
       sp.end_Time,
       sp.workload, 
       sp.healing_Grade,
       sp.healing_Grade_name,
       pr.procedure_Level,
       pr.procedure_Level_name,
       pp.zhudao,
       pp.mazui,
       sp.org_Code 
  from Surgical_Procedure sp,procedure_order pr,
     (select pp.procedure_Sn,max(case when pp.participant_identity = 11 then pp.participant_name end) zhudao, max(case when pp.participant_identity = 31 then pp.participant_name end) mazui
        from (select procedure_sn,participant_identity,to_char(wm_concat(participant_name)) as participant_name  from procedure_participants a where a.participant_identity in(11,31) group by procedure_sn,participant_identity) pp, surgical_procedure sp 
        where pp.procedure_sn = sp.procedure_sn 
        and sp.patient_sn =/*patientSn*/  
      group by pp.procedure_Sn) pp
 where sp.patient_sn=/*patientSn*/ 
   and pr.order_sn=sp.order_sn 
   and pp.procedure_Sn=sp.procedure_sn
   and sp.delete_flag=0
   and pr.delete_flag(+)=0
      /*IF procedureListSearchPra.operationName != null && procedureListSearchPra.operationName.length() != 0*/
   and sp.operation_Name like '%' || /*procedureListSearchPra.operationName*/'' || '%'
      /*END*/
      /*IF procedureListSearchPra.operationDateFromStr != null && procedureListSearchPra.operationDateFromStr.length() != 0*/
   and sp.operation_Date >= to_date( /*procedureListSearchPra.operationDateFromStr*/''||'00-00-00', 'YYYY-MM-DD hh24:mi:ss')
      /*END*/
      /*IF procedureListSearchPra.operationDateToStr != null && procedureListSearchPra.operationDateToStr.length() != 0*/
   and sp.operation_Date <= to_date( /*procedureListSearchPra.operationDateToStr*/''||'23-59-59', 'YYYY-MM-DD hh24:mi:ss')
      /*END*/
      /*IF procedureListSearchPra.surgicalDept != null && procedureListSearchPra.surgicalDept.length() != 0*/
   and sp.surgical_Dept = /*procedureListSearchPra.surgicalDept*/''
      /*END*/
      /*IF procedureListSearchPra.zhudao != NULL && procedureListSearchPra.zhudao.length() != 0*/
   and pp.zhudao like '%' || /*procedureListSearchPra.zhudao*/'' || '%'
      /*END*/    
      /*IF procedureListSearchPra.mazui != NULL && procedureListSearchPra.mazui.length() != 0*/
   and pp.mazui like '%' || /*procedureListSearchPra.mazui*/'' || '%'
      /*END*/
      /*IF procedureListSearchPra.procedureLevel != NULL && procedureListSearchPra.procedureLevel.length() != 0*/
   and pr.procedure_Level = /*procedureListSearchPra.procedureLevel*/''
      /*END*/
      /*IF procedureListSearchPra.workload != NULL && procedureListSearchPra.workload.length() != 0*/
   and sp.workload = /*procedureListSearchPra.workload*/''
      /*END*/    
      /*IF procedureListSearchPra.healingGrade != NULL && procedureListSearchPra.healingGrade.length() != 0*/
   and sp.healing_Grade = /*procedureListSearchPra.healingGrade*/''
      /*END*/

      /*IF procedureListSearchPra.visitSn != null && procedureListSearchPra.visitSn.length() != 0*/
   and sp.visit_sn = /*procedureListSearchPra.visitSn*/
      /*END*/
      /*IF procedureListSearchPra.inpatientDate != null && procedureListSearchPra.inpatientDate.length() != 0*/
   and sp.operation_Date between TO_DATE(/*procedureListSearchPra.inpatientDate*/,'YYYY-MM-DD') and TO_DATE(/*procedureListSearchPra.inpatientDate*/ || '23:59:59', 'YYYY-MM-DD HH24:MI:SS')
      /*END*/
      /*IF procedureListSearchPra.orgCode != NULL && procedureListSearchPra.orgCode.length() != 0*/
   and sp.org_code = /*procedureListSearchPra.orgCode*/''
      /*END*/
 order by sp.start_time desc, sp.procedure_Sn desc
   
   
   
  