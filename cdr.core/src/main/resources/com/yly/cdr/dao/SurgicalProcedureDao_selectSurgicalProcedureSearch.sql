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
       sp.start_Time,
       sp.end_Time,
       sp.workload, 
       sp.healing_Grade,
       pr.procedure_Level,
       pp.zhudao,
       pp.zhuy,
       pp.zhue,
       pp.mazui 
  from Surgical_Procedure sp,procedure_order pr,
       (select pp.procedure_Sn,max(case when pp.participant_identity = 1 then pp.participant_name end) zhudao, max(case when pp.participant_identity = 2 then pp.participant_name end) zhuy,max(case when pp.participant_identity = 3 then pp.participant_name end) zhue, max(case when pp.participant_identity = 7 then pp.participant_name end) mazui
          from procedure_participants pp, surgical_procedure sp 
         where pp.procedure_sn = sp.procedure_sn 
           and pp.delete_flag='0'
           and sp.delete_flag='0'
           and sp.patient_sn = /*patientSn*/   
         group by pp.procedure_Sn) pp
 where sp.patient_sn=/*patientSn*/ 
   and pr.order_sn=sp.order_sn 
   and sp.delete_flag='0'
   and pr.delete_flag='0'
   /*IF operationName != null && operationName.length() != 0*/
   and sp.operation_Name like /*operationName*/ 
   /*END*/
   /*IF operationDate1 != null && operationDate1.length() != 0*/
   and sp.operation_Date >= to_date(/*operationDate1*/,'YYYY-MM-DD')
   /*END*/
   /*IF operationDate2 != null && operationDate2.length() != 0*/
   and sp.operation_Date <= to_date(/*operationDate2*/,'YYYY-MM-DD')
   /*END*/
   /*IF surgicalDept != null && surgicalDept.length() != 0*/
   and sp.surgical_Dept = /*surgicalDept*/
   /*END*/
   /*IF participantName1 != NULL && participantName1.length() != 0*/
   and pp.zhudao = /*participantName1*/ 
   /*END*/
   /*IF participantName2 != NULL && participantName2.length() != 0*/
   and pp.mazui = /*participantName2*/ 
   /*END*/
   /*IF procedureLevel != NULL && procedureLevel.length() != 0*/
   and pr.procedure_Level = /*procedureLevel*/
   /*END*/
   /*IF workload != NULL && workload.length() != 0*/
   and sp.workload = /*workload*/
   /*END*/
   /*IF healingGrade != NULL && healingGrade.length() != 0*/
   and sp.healing_Grade = /*healingGrade*/
   /*END*/
   /*IF execdept != NULL && execdept.length() != 0*/
   and pr.exec_dept = /*execdept*/
   /*END*/
   
  