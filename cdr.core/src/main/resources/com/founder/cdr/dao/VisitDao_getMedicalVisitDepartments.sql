/**
 * 
 * [FUN]V05-001:临床信息集成视图
 * @version 1.0, 2012/03/02  
 * @author wu_jianfeng
 * @since 1.0
 * 患者的就诊科室记录
 */
select mv.visit_dept　
from medical_visit mv
where mv.patient_sn = /*patientSn*/
	and mv.delete_flag = '0'
group by mv.visit_dept
