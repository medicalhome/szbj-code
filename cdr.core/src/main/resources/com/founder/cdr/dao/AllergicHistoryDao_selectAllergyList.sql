/**
*
*[FUN]过敏/不良反应列表
*@version 1.0, 2012/03/12
*@author 黄洁玉
*@since 1.0
*
*过敏/不良反应检索结果
*/
select  ah.allergic_source,
        ah.allergic_symptom,
        ah.allergen_code,
        ah.allergic_condition,
        ah.allergic_start_date,
	    ah.allergic_stop_date,
	    ah.seriousness,
	    ah.allergic_type_name
   from allergic_history ah  
   where ah.patient_sn = /*patientSn*/
     and ah.delete_flag = '0'
   order by ah.allergic_start_date desc