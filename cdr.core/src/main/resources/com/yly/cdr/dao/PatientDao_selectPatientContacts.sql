/**
 * 根据患者内部序列号获取患者联系人列表
 * @param patientSn 患者内部序列号
 * @return 患者联系人列表
 */
SELECT a.*
  FROM patient_contact a
 WHERE a.patient_sn = /*patientSn*/
   and a.delete_flag = 0