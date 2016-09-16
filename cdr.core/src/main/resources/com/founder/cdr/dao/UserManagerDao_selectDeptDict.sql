/*
*	UserManagerDao.selectDeptDict
*/
 select distinct depart_cd,
	   depart_name
  from code_person
   order by depart_cd
   
   