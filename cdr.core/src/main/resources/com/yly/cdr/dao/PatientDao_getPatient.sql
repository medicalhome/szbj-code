/**
 * 根据患者内部序列号获取患者对象
 * @param patientSn 患者内部序列号
 * @return 患者对象
 */
SELECT a.*
  FROM patient a
 WHERE a.patient_sn = /*patientSn*/