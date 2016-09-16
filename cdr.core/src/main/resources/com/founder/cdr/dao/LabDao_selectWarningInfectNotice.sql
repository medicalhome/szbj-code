/**
 * tong_meng
 */
SELECT wn_d.warning_notice_sn,
       wn_d.user_id,
       wn_d.rulegroup_id,
       wn_d.user_type,
       wn_d.unit_id,
       wn_d.ward_no
  FROM warning_notice wn_d
 WHERE wn_d.delete_flag = '0'
   AND wn_d.rulegroup_id = /*ruleGroup*/
   AND wn_d.org_code = /*orgCode*/
   /*IF wardNo != NULL*/
       AND wn_d.ward_no = /*wardNo*/
   /*END*/