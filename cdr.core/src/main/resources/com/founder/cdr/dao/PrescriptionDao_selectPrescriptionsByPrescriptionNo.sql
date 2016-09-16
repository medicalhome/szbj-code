SELECT * 
	FROM PRESCRIPTION P
	WHERE P.DELETE_FLAG='0'
	/*IF prescriptionNo != null && !"".equals(prescriptionNo) */
	AND P.PRESCRIPTION_NO = /*prescriptionNo*/
	/*END*/
	/*IF visitSn != null && !"".equals(visitSn) */
	AND P.VISIT_SN = /*visitSn*/
	/*END*/
	/*IF patientSn != null && !"".equals(patientSn) */
	AND P.PATIENT_SN = /*patientSn*/
	/*END*/