/**
 * 根据患者内部序列号获取患者证件列表
 * @param patientSn 患者内部序列号
 * @return 患者证件列表
 */
SELECT a.*
  FROM patient_credential a
 WHERE a.patient_sn = /*patientSn*/
   and a.delete_flag = 0