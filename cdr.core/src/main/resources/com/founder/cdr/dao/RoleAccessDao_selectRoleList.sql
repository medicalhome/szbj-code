/*
 * 角色列表
 */

select sr.system_role_id roleId, sr.role_name, sr.occupation_type occupationType, sr.memo 
	from system_role sr 
	where sr.delete_flag = 0
	/*IF occuType!=null and occuType!=-1 */
	and sr.occupation_type = /*occuType*/
	/*END*/
	/*IF roleName!=null and roleName!='' */
	and sr.role_name like concat('%', concat(/*roleName*/, '%'))
	/*END*/
	order by sr.system_role_id