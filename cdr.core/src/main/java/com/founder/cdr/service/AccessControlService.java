package com.founder.cdr.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.dto.UserAuthDto;
import com.founder.cdr.entity.SystemAccountAuth;
import com.founder.cdr.entity.SystemAuth;
import com.founder.cdr.entity.SystemRoleAuth;

/**
 * 权限管理服务接口
 * @author tong_meng
 *
 */
public interface AccessControlService
{

    /**
     * 找到要更新或者删除的权限信息表数据
     * @param code
     * @return
     */
    @Transactional
    public List<SystemAuth> selectSystemAuthByAuthId(String authId);

    /**
     * 通过用户ID权限ID找到要删除的账户权限表
     * @param code
     * @return
     */
    @Transactional
    public List<SystemAccountAuth> selectSystemAccountAuth(String authId,
            String userOrRoleNo);

    /**
     * 通过角色ID权限ID找到要删除的角色权限表
     * @param code
     * @return
     */
    @Transactional
    public List<SystemRoleAuth> selectSystemRoleAuth(String authId,
            String userOrRoleNo);

    /**
     * 保存要插入的数据，更新要删除的数据！
     * @param systemAccountAuthInsertList   要保存的用户权限表
     * @param systemRoleAuthInsertList      要保存的角色权限表
     * @param deleteSystemAccountAuthList   要删除的用户权限表
     * @param deleteSystemRoleAuthList      要删除的角色权限表
     */
    public void updateRoleOrUser(List<Object> systemAccountAuthInsertList,
            List<Object> systemRoleAuthInsertList,
            List<Object> deleteSystemAccountAuthList,
            List<Object> deleteSystemRoleAuthList, Date systemDate);

    // $Author:wu_jianfeng
    // $Date : 2012/09/12 14:10
    // $[BUG]0009663 ADD BEGIN
    // 访问控制实现
    /**
     * 根据用户ID，得到该用户的最大权限
     * @param userId
     * @return
     */
    public List<String> getUserAuths(String userId);
    // $[BUG]0009663 ADD END
}
