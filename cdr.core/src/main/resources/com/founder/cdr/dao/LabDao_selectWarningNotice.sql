/**
 * tong_meng
 */
SELECT wn_d.warning_notice_sn,
       wn_d.user_id,
       wn_d.rulegroup_id,
       wn_d.user_type,
       wn_d.unit_id
  FROM warning_notice wn_d
 WHERE wn_d.delete_flag = '0'
   AND wn_d.user_type = /*warnOccupationTypeDoctor*/
   AND wn_d.user_id = /*requestPerson*/
   AND wn_d.rulegroup_id = /*warnOrUnnormal*/
   AND wn_d.org_code = /*orgCode*/
   
union all

SELECT wn_n.warning_notice_sn,
       wn_n.user_id,
       wn_n.rulegroup_id,
       wn_n.user_type,
       wn_n.unit_id
  FROM warning_notice wn_n
 WHERE wn_n.delete_flag = '0'
   AND wn_n.user_type = /*warnOccupationTypeNurse*/
   /*IF warnOrUnnormal=='EmergencyGroup'*/
   AND wn_n.unit_id = '0000000'
   /*END*/
   /*IF warnOrUnnormal!='EmergencyGroup'*/
   AND wn_n.unit_id = /*deptId*/
   /*END*/
   AND wn_n.rulegroup_id = /*warnOrUnnormal*/
   AND wn_n.org_code = /*orgCode*/