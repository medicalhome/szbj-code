/**
 * 根据患者内部序列号获取患者医保信息
 * @param patientSn 患者内部序列号
 * @return 患者医保信息
 */
SELECT *
  FROM social_insurance
 WHERE patient_sn = /*patientSn*/
   AND delete_flag = '0'