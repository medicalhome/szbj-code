/**
 * $Author :jin_peng
 * $Date : 2012/11/06 13:36$
 * [BUG]0011026 MODIFY BEGIN
 * $Author :jin_peng
 * $Date : 2012/09/27 10:31$
 * [BUG]0010132 MODIFY BEGIN
 * [FUN]V05-101住院视图查询住院次数
 * @version 1.0, 2012/09/28  
 * @author 金鹏
 * @since 1.0 
 * 住院视图列表
 */
SELECT COUNT(*)
  FROM (
  			SELECT ROWNUM RN --树状查询出某住院日期开始需要循环增加的天数
              FROM (
              			SELECT MAX(TRUNC(NVL(MVDAY.DISCHARGE_DATE,SYSDATE)) - TRUNC(MVDAY.VISIT_DATE)) MAX_DIFF --获取住院天数最大值，以便后续进行树状查询
                          FROM MEDICAL_VISIT MVDAY
                         WHERE MVDAY.PATIENT_SN = /*patientSn*/
                         	   AND MVDAY.visit_type_code = /*inpatientType*/ --就诊类型为住院
                         	   AND MVDAY.VISIT_STATUS_CODE != /*exitInpatient*/
                               AND MVDAY.DELETE_FLAG = '0'
        		   ) DAYMAX
       	   CONNECT BY ROWNUM <= DAYMAX.MAX_DIFF + 1
       ) MVFOR,
       MEDICAL_VISIT MV
 WHERE MV.PATIENT_SN = /*patientSn*/
 	   AND MV.visit_type_code = /*inpatientType*/ 
 	   AND MV.VISIT_STATUS_CODE != /*exitInpatient*/
 	   /*IF orgCode != null && orgCode.length() != 0*/
 	   AND MV.ORG_CODE = /*orgCode*/
 	   /*END*/
       AND MV.DELETE_FLAG = '0'
       AND TRUNC(NVL(MV.DISCHARGE_DATE,SYSDATE)) - TRUNC(MV.VISIT_DATE) + 1 >= MVFOR.RN --保证住院天数要大于天数添加的增量，否则则超出该次住院的天数
       /*IF (inpatientStartDate != null && inpatientStartDate.length() != 0) && (inpatientEndDate != null && inpatientEndDate.length() != 0)*/
       	   AND MV.VISIT_DATE + MVFOR.RN - 1 < TO_DATE(/*inpatientEndDate*/,'YYYY-MM-DD HH24:MI:SS') + 1
	       /*IF dischargeDate != null && dischargeDate.length() != 0*/
	       AND MV.VISIT_DATE + MVFOR.RN - 1 < TO_DATE(/*dischargeDate*/,'YYYY-MM-DD HH24:MI:SS') + 1
	       /*END*/
       /*END*/
/**[BUG]0010132 MODIFY END*/
/**[BUG]0011026 MODIFY END*/