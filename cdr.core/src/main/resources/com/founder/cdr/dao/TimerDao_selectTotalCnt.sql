/**
 * $Author :jin_peng
 * $Date : 2012/11/05 10:20$
 * [BUG]0011016 MODIFY BEGIN
 * [FUN]V05-101时间轴列表
 * @version 1.0, 2012/07/31  
 * @author 魏鹏
 * @since 1.0
 * 时间轴列表检索结果
 */
SELECT COUNT(1)
  FROM (
		SELECT MV.VISIT_SN,MV.VISIT_DATE,MV.VISIT_TYPE_code,MV.VISIT_TYPE_Name,MV.VISIT_DEPT_id, MV.VISIT_DEPT_Name,MV.visit_doctor_id, --住院
		 	   MV.VISIT_DOCTOR_NAME,MV.DISCHARGE_DATE,MV.VISIT_TIMES,MV.VISIT_STATUS_CODE
		  FROM MEDICAL_VISIT MV
		 WHERE MV.PATIENT_SN = /*patientSn*/
		       AND MV.DELETE_FLAG = '0'
		       AND MV.VISIT_TYPE_CODE = /*visitTypeCodeInpatient*/
		       /*IF (visitDate != null && visitDate.length() != 0) && !isValidation */
		   	    	AND MV.VISIT_DATE < to_date(/*visitDate*/,'YYYY-MM-DD HH24:MI:SS')
		 	   /*END*/
		   	   /*IF (visitDate != null && visitDate.length() != 0) && isValidation */
		   	    	AND MV.VISIT_DATE >= to_date(/*visitDate*/,'YYYY-MM-DD HH24:MI:SS')
		 	   /*END*/
		UNION 
		
		SELECT MV.VISIT_SN,MV.VISIT_DATE,MV.VISIT_TYPE_code,MV.VISIT_TYPE_Name,MV.VISIT_DEPT_id, MV.VISIT_DEPT_Name,MV.visit_doctor_id, --门诊
		 	   MV.VISIT_DOCTOR_NAME,MV.DISCHARGE_DATE,MV.VISIT_TIMES,MV.VISIT_STATUS_CODE
		  FROM MEDICAL_VISIT MV
		 WHERE MV.PATIENT_SN = /*patientSn*/
		       AND MV.DELETE_FLAG = '0'
		       AND MV.VISIT_TYPE_CODE != /*visitTypeCodeInpatient*/
		       AND (EXISTS (SELECT 1 FROM DIAGNOSIS DS WHERE DS.DELETE_FLAG = '0' AND DS.VISIT_SN = MV.VISIT_SN)
		        OR EXISTS (SELECT 1 FROM EXAMINATION_ORDER EO WHERE EO.DELETE_FLAG = '0' AND EO.VISIT_SN = MV.VISIT_SN)
		        OR EXISTS (SELECT 1 FROM LAB_ORDER LO WHERE LO.DELETE_FLAG = '0' AND LO.VISIT_SN = MV.VISIT_SN))
		       /*IF (visitDate != null && visitDate.length() != 0) && !isValidation */
		   	    	AND MV.VISIT_DATE < to_date(/*visitDate*/,'YYYY-MM-DD HH24:MI:SS')
		 	   /*END*/
		   	   /*IF (visitDate != null && visitDate.length() != 0) && isValidation */
		   	    	AND MV.VISIT_DATE >= to_date(/*visitDate*/,'YYYY-MM-DD HH24:MI:SS')
		 	   /*END*/
		)
/**[BUG]0011016 MODIFY END*/