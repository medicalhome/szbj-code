/**
 * 
 * [FUN]V05-101药物医嘱图形展示
 * @version 1.0, 2013/01/07  
 * @author 金鹏
 * @since 1.0
 * 药物医嘱图形展示
 */
/*IF "drugTotal".equals(itemFlag)*/
SELECT COUNT(1) DISPLAY_PIE_TOTAL
  FROM MEDICATION_ORDER MO
 WHERE MO.PATIENT_SN = /*patientSn*/
   AND MO.DELETE_FLAG = 0
   AND NOT EXISTS 
     ( 
     	SELECT 1
          FROM HIDE_DRUG HD
         WHERE MO.DRUG_CODE = HD.DRUG_CODE
           AND HD.DELETE_FLAG = 0
           AND HD.USER_NAME = /*userSn*/
     )
/*END*/

/*IF "pieChart".equals(itemFlag)*/
SELECT RES.DISPLAY_PIE_LABEL, RES.PER_PIE_COUNT
  FROM 
     (  
        SELECT NVL(MO.DRUG_NAME,'草药') DISPLAY_PIE_LABEL, COUNT(1) PER_PIE_COUNT
          FROM MEDICATION_ORDER MO
         WHERE MO.PATIENT_SN = /*patientSn*/
           AND MO.DELETE_FLAG = 0
           AND NOT EXISTS 
             ( 
                SELECT 1
                  FROM HIDE_DRUG HD
                 WHERE MO.DRUG_CODE = HD.DRUG_CODE
                   AND HD.DELETE_FLAG = 0
                   AND HD.USER_NAME = /*userSn*/
             )
         GROUP BY MO.DRUG_NAME
         ORDER BY PER_PIE_COUNT DESC
     ) RES
 WHERE ROWNUM <= /*displayPieCount*/
/*END*/

/*IF "trendChart".equals(itemFlag)*/
SELECT MO.TOTAL_DOSAGE,MO.TOTAL_DOSAGE_UNIT,MO.DOSAGE,MO.DOSAGE_UNIT,TO_CHAR(MO.ORDER_TIME,'YYYY-MM-DD') ORDER_TIME
  FROM MEDICATION_ORDER MO
 WHERE MO.PATIENT_SN = /*patientSn*/
   AND NVL(MO.DRUG_NAME,'草药') = /*approvedDrugName*/
   AND MO.DELETE_FLAG = 0
   AND NOT EXISTS 
     ( 
        SELECT 1
          FROM HIDE_DRUG HD
         WHERE MO.DRUG_CODE = HD.DRUG_CODE
           AND HD.DELETE_FLAG = 0
           AND HD.USER_NAME = /*userSn*/
     )
 ORDER BY MO.ORDER_TIME DESC
/*END*/