  select	sa.user_id userId,
		sa.user_name userName, 
		sa.sex sex, 
		vs.cv_value sexName, 
		sa.dept_code deptCode, 
		cd.name deptName, 
		sa.mobile
	from system_account sa, code_department cd, system_user_role sur,
	(select cv.cs_id, cv.cv_id,cv.cv_code,cv.cv_value from code_system cy, code_value cv where cy.cs_code = 'MS005' and cv.cs_id=cy.cs_id(+))  vs
	where 
	sa.delete_flag = 0 and sur.delete_flag = 0
  	and sa.user_id = sur.user_id (+)
	and to_char(sa.sex) = vs.cv_code(+)
  	and cd.code = sa.dept_code
  	/*IF roleId != null and roleId != ""*/
  	and sur.system_role_id = /*roleId*/
  	/*END*/
  	/*IF userNo != null and userNo != ""*/
  	and sa.user_id like concat('%',concat(/*userNo*/,'%'))
  	/*END*/
  	/*IF userName != null and userName != ""*/
  	and sa.user_name like concat('%',concat(/*userName*/,'%'))
  	/*END*/
  	/*IF userDeptCode != null and userDeptCode != ""*/
  	and sa.dept_code = /*userDeptCode*/
  	/*END*/
  	/*IF hidden != null and hidden != ""*/
  	and sa.user_id not in (/*hidden*/)
  	/*END*/
  	order by sa.user_id