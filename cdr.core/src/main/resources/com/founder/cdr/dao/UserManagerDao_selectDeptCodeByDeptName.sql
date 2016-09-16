/*
*	UserManagerDao.selectDeptCodeByDeptName
*/
select distinct depart_cd
  from code_person
  /*IF name != null and name != '' */
  where depart_name = /*name*/
  /*END*/
   