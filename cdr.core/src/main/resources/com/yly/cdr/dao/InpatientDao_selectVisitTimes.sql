/**
 * $Author :jin_peng
 * $Date : 2012/11/06 13:36$
 * [BUG]0011026 MODIFY BEGIN
 * $Author :jin_peng
 * $Date : 2012/09/27 10:31$
 * [BUG]0010132 ADD BEGIN
 * [FUN]V05-101住院视图住院日期及次数等内容查询
 * @version 1.0, 2012/09/28  
 * @author 金鹏
 * @since 1.0 
 * 住院视图住院次数
 */
SELECT MV.VISIT_DATE,MV.DISCHARGE_DATE,MV.VISIT_SN,MV.VISIT_TIMES,MV.ORG_CODE, MV.DISCHARGE_WARD_NAME, MV.DISCHARGE_BED_NO, MV.ENTRANCE_TIME
  FROM MEDICAL_VISIT MV
 WHERE MV.PATIENT_SN = /*patientSn*/
   AND MV.VISIT_TYPE_CODE = /*inpatientType*/
   AND MV.VISIT_STATUS_CODE != /*exitInpatient*/
   /*IF orgCode != null && orgCode.length() != 0*/
   AND MV.ORG_CODE = /*orgCode*/
   /*END*/
   AND MV.DELETE_FLAG = 0
 ORDER BY MV.VISIT_DATE DESC
/**[BUG]0010132 ADD END*/
/**[BUG]0011026 MODIFY END*/