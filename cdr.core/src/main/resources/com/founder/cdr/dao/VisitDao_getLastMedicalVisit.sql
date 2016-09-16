/**
 * 
 * [FUN]V05-001:临床信息集成视图
 * @version 1.0, 2012/03/02  
 * @author wu_jianfeng
 * @since 1.0
 * 患者最后的就诊记录
 */
select mv.*　
from (select mv1.*, row_number()over(order by visit_date desc) rn from medical_visit mv1 where mv1.patient_sn = /*patientSn*/ and mv1.delete_flag = '0') mv
where rn = 1 
