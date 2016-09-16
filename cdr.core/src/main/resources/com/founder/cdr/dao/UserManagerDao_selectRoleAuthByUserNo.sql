
select distinct *
  from (select t.system_auth_id, t.memo
          from system_user_role u, system_role_auth sa, system_auth t
         where u.system_role_id = sa.system_role_id
           and sa.system_auth_id = t.system_auth_id
           and u.user_id = /*userNo*/
           and u.delete_flag='0'
           and sa.delete_flag='0'
           and t.delete_flag='0'
        union all
        select t1.system_auth_id, t2.memo
          from system_default_auth t1, system_auth t2
         where t1.system_auth_id = t2.system_auth_id
            and t1.delete_flag='0'
             and t2.delete_flag='0'
           and (t2.occupation_type = (select case
                                                when (a.job_category = '医') then
                                                 '0'
                                                when (a.job_category = '护') then
                                                 '1'
                                              end
                                         from system_account a
                                        where a.user_id = /*userNo*/)
                or t2.occupation_type='2'        ))