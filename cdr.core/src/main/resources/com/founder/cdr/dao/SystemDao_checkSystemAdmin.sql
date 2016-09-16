select sa.* from system_account sa, 
       (select distinct sur.user_id from system_user_role sur, system_role sr 
        where sur.system_role_id = sr.system_role_id 
        and sur.delete_flag = 0 
        and sr.delete_flag = 0
        and sr.occupation_type = '2') admin
  where sa.delete_flag = 0
        and sa.user_id = admin.user_id
        and sa.user_id = /*userId*/;  