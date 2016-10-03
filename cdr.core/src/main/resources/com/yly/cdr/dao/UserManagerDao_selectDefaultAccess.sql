/***
 *  查询医生和护士的缺省权限
 */

 select sa.system_auth_id, sa.memo, sa.occupation_type, sa.auth_type
  from system_auth sa
 where sa.delete_flag = 0
   and (sa.occupation_type = 2 
   /*IF type!=null && type == 0*/
   or sa.occupation_type = 0
   /*END*/
   /*IF type!=null && type == 1*/
   or sa.occupation_type = 1
   /*END*/
   )
   /*IF area!=null && area == 0*/
   and sa.auth_type = 0
   /*END*/
    /*IF area!=null && area == 1*/
   and sa.auth_type = 1
   /*END*/
    /*IF area!=null && area == 2*/
   and sa.auth_type = 2
   /*END*/
 order by sa.auth_type