/**
 *  检索个人权限
 */
select t1.system_auth_id, t2.memo as auth_desc
  from system_account_auth t1, system_auth t2
 where t1.system_auth_id = t2.system_auth_id
 and t1.user_id = /*userNo*/
 and t1.delete_flag='0'
 and t2.delete_flag='0'
 order by t2.system_auth_id