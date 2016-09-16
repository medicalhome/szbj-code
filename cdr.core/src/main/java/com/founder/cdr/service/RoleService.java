package com.founder.cdr.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.entity.SystemRole;
import com.founder.cdr.entity.SystemRoleAuth;
import com.founder.cdr.entity.SystemUserRole;

public interface RoleService
{
    /**
     * 根据角色ID和用户ID查询用户角色表是否存在相关数据
     * @param roleNo    角色ID
     * @return
     */
    @Transactional
    public List<SystemUserRole> selectSystemUserRole(String roleNo);

    /**
     * 根据角色ID角色信息表中，是否存在数据
     * @param roleNo
     * @return
     */
    @Transactional
    public List<SystemRole> selectSystemRole(String roleNo);

    // $Author :tong_meng
    // $Date : 2012/10/17 14:00$
    // [BUG]0010503 MODIFY BEGIN
    /**
     * 删除更新集合中的数据，插入要插入集合的数据
     * @param systemUserRoleInsertList  用户角色信息表插入集合
     * @param systemRoleInsertList  角色信息表插入集合
     * @param roleUpdateList    角色信息更细哪集合
     * @param userRoleUpdateList    用户角色更新表集合
     */
    @Transactional
    public void saveSystemUserRole(List<Object> systemRoleInsertList,
            List<Object> systemUserRoleInsertList, List<Object> roleUpdateList,
            List<Object> userRoleUpdateList, List<Object> roleAuthList,
            String newUpFlag);

    // [BUG]0010503 MODIFY END

    // $Author :tong_meng
    // $Date : 2012/10/17 14:00$
    // [BUG]0010503 ADD BEGIN
    /**
     * 根据角色ID关联查询角色权限表中的权限信息
     * @param roleNo
     * @return
     */
    @Transactional
    public List<SystemRoleAuth> selectSystemRoleAuth(String roleNo);
    // [BUG]0010503 ADD END

    /**
     * 根据用户ID查询对应的角色No
     */
    @Transactional
    public List<SystemUserRole> selectSystemRoleNo(String userId);
    
    
    /**
     * 根据角色NO和权限ID查询是否存在对应的记录
     * @param roleNo
     * @param authId
     * @return
     */
    @Transactional
    public List<SystemRoleAuth> selectSystemRoleAuth(String roleNo,String authId);
}
