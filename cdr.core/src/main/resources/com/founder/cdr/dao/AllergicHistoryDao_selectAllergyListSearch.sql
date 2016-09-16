/**
*
*[FUN]过敏/不良反应列表
*@version 1.0, 2012/03/12
*@author 黄洁玉
*@since 1.0
*
*过敏/不良反应检索结果
*/
select ah.allergic_sn,
       ah.allergic_source,
       ah.allergic_symptom,
       ah.allergen_code,
       ah.allergic_condition,
       ah.allergic_condition_name,
       ah.allergic_start_date,
	   ah.allergic_stop_date,
	   ah.seriousness_name,
	   ah.seriousness,
	   ah.allergic_type_name
   from allergic_history ah
  where ah.patient_sn = /*patientSn*/
   and ah.delete_flag = '0'
   /*IF riskListSearchPra.allergicStartDate != null && riskListSearchPra.allergicStartDate.length() != 0*/
   and ah.allergic_start_date >= to_date(/*riskListSearchPra.allergicStartDate*/''||'00-00-00', 'YYYY-MM-DD hh24:mi:ss')
   /*END*/
    /*IF riskListSearchPra.allergicStopDate != null && riskListSearchPra.allergicStopDate.length() != 0*/
   and ah.allergic_start_date <= to_date(/*riskListSearchPra.allergicStopDate*/''||'23-59-59', 'YYYY-MM-DD hh24:mi:ss')
   /*END*/
   /*IF riskListSearchPra.seriousness != NULL && riskListSearchPra.seriousness.length() != 0*/
   and ah.seriousness = /*riskListSearchPra.seriousness*/
   /*END*/
   order by ah.allergic_start_date desc,ah.allergic_sn desc