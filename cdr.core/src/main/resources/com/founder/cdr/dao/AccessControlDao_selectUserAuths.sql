/**
 *$Author:wu_jianfeng
 *$Date : 2012/09/12 14:10
 *$[BUG]0009663 ADD BEGIN
 * 访问控制实现
 */
select saa.user_id,  
  saa.system_auth_id,
  'user' as auth_type
from system_account_auth saa  
  where saa.user_id = /*userId*/
    and saa.delete_flag = '0'
union all     
select /*userId*/ as user_id,  
  sra.system_auth_id,
  'role' as auth_type
from system_role_auth  sra
  where sra.delete_flag = '0'
    and exists (select 'X' from system_user_role sur 
        where sur.system_role_id = sra.system_role_id
          and sur.user_id = /*userId*/
          and sur.delete_flag = '0')
union all     
select /*userId*/ as user_id,  
  sda.system_auth_id,
  'default' as auth_type
from system_default_auth sda
  where sda.delete_flag = '0'
    and exists (select 'X' from system_account sa 
      where sa.job_category = sda.occupation_type
        and sa.user_id = /*userId*/
        and sa.delete_flag = '0')
/**$[BUG]0009663 ADD END*/