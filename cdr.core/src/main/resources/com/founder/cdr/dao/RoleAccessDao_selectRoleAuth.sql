select sra.system_auth_id, sa.occupation_type, sa.auth_type, sa.memo  
from system_role_auth sra, system_auth sa
where sra.system_auth_id = sa.system_auth_id
and sra.system_role_id = /*roleId*/
order by sa.system_auth_id