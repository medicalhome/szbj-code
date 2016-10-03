select distinct sa.user_id userNo,
       sa.user_name,
       sa.sex,
       vs.cv_value sexName,
       sa.dept_code,
       cd.name deptName,
       sa.mobile,
       to_char(sa.service_start_date,'yyyy-mm-dd') as entryDate,
       sa.employment_status_cd,
       ves.cv_value as inPostStatus,
       sa.employee_type_cd,
       vet.cv_value as personType,
       sa.group_cd,   
       sa.status,
       sa.memo,
       sa.job_category posiClass
  from system_account sa, code_department cd,
  (select cv.cs_id, cv.cv_id,cv.cv_code,cv.cv_value from code_system cy, code_value cv where cy.cs_code = 'MS005' and cv.cs_id=cy.cs_id(+))  vs, 
  (select cv.cs_id, cv.cv_id,cv.cv_code,cv.cv_value from code_system cy, code_value cv where cy.cs_code = 'MS029' and cv.cs_id=cy.cs_id(+)) ves, 
  (select cv.cs_id, cv.cv_id,cv.cv_code,cv.cv_value from code_system cy, code_value cv where cy.cs_code = 'MS030' and cv.cs_id=cy.cs_id(+)) vet 
  where sa.dept_code = cd.code(+)
   and to_char(sa.sex) = vs.cv_code(+)
   and sa.employment_status_cd = ves.cv_code(+)
   and sa.employee_type_cd = vet.cv_code(+)
   and sa.delete_flag = 0
   /*IF userNo!=null and userNo != '' */
   and sa.user_id =/*userNo*/
   /*END*/
  
   