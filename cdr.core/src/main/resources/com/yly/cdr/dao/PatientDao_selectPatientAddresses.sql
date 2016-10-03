/**
 * 根据患者内部序列号获取患者地址列表
 * @param patientSn 患者内部序列号
 * @return 患者地址列表
 */
SELECT pa.*
  FROM patient_address pa
 WHERE pa.patient_sn = /*patientSn*/
  /*IF addressType != NULL && addressType.length() != 0*/
   AND pa.address_type = /*addressType*/
  /*END*/
   AND pa.delete_flag = 0
 ORDER BY pa.create_time DESC
