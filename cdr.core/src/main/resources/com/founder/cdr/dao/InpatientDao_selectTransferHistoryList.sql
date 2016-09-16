/**
 * $Date : 2015/09/22 13:36$
 * [BUG]0011026 MODIFY BEGIN
 * 根据病人LID，就诊号查询入科转科表中，最近一条记录
 */
SELECT *
  FROM transfer_history th
 WHERE th.patient_lid = /*patientlid*/''
   AND th.visit_sn = /*visitSn*/''
   AND th.delete_flag = '0'
 ORDER BY th.update_time desc