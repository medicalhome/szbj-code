/**
 * 根据患者内部序列号获取患者危险性信息列表
 * @param patientSn 患者内部序列号
 * @return 患者危险性信息列表
 */
SELECT a.*
  FROM risk_information a
 WHERE a.patient_sn = /*patientSn*/
   AND a.delete_flag = '0'