/**
 * occu为2时为系统管理员角色，不过滤权限类型
 * */
select sa.system_auth_id, sa.memo, sa.occupation_type, sa.auth_type
from system_auth sa
where sa.delete_flag = 0
/*IF occu != "2"*/
and (sa.occupation_type = 2 
	/*IF occu != null && occu != ""*/
	or sa.occupation_type = /*occu*/	
	/*END*/
)
/*END*/

order by sa.system_auth_id
