package com.yly.cdr.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dao.AccessControlDao;
import com.yly.cdr.dto.UserAuthDto;
import com.yly.cdr.entity.SystemAccountAuth;
import com.yly.cdr.entity.SystemAuth;
import com.yly.cdr.entity.SystemRoleAuth;
import com.yly.cdr.service.AccessControlService;
import com.yly.cdr.service.CommonService;

@Component
public class AccessControlServiceImpl implements AccessControlService
{

    @Autowired
    private CommonService commonService;

    @Autowired
    private AccessControlDao accessControlDao;

    @Autowired
    private EntityDao entityDao;

    @Override
    @Transactional
    public List<SystemAuth> selectSystemAuthByAuthId(String authId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("systemAuthId", authId);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List result = new ArrayList<SystemAuth>();
        result = entityDao.selectByCondition(SystemAuth.class, map);
        return result;
    }

    @Override
    @Transactional
    public List<SystemAccountAuth> selectSystemAccountAuth(String authId,
            String userOrRoleNo)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("systemAuthId", authId);
        map.put("userId", userOrRoleNo);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List result = new ArrayList<SystemAccountAuth>();
        result = entityDao.selectByCondition(SystemAccountAuth.class, map);
        return result;
    }

    @Override
    @Transactional
    public List<SystemRoleAuth> selectSystemRoleAuth(String authId,
            String userOrRoleNo)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("systemAuthId", authId);
        map.put("systemRoleId", userOrRoleNo);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List result = new ArrayList<SystemRoleAuth>();
        result = entityDao.selectByCondition(SystemRoleAuth.class, map);
        return result;
    }

    @Override
    @Transactional
    public void updateRoleOrUser(List<Object> systemAccountAuthInsertList,
            List<Object> systemRoleAuthInsertList,
            List<Object> deleteSystemAccountAuthList,
            List<Object> deleteSystemRoleAuthList, Date systemDate)
    {
        saveList(systemAccountAuthInsertList, systemRoleAuthInsertList);

        setDeleteList(deleteSystemAccountAuthList, deleteSystemRoleAuthList,
                systemDate);
    }

    /**
     * 向用户信息表和角色权限表中保存数据
     * @param systemAccountAuthInsertList
     * @param systemRoleAuthInsertList
     */
    @Transactional
    private void saveList(List<Object> systemAccountAuthInsertList,
            List<Object> systemRoleAuthInsertList)
    {
        if (systemAccountAuthInsertList != null
            & systemAccountAuthInsertList.size() != 0)
            for (Object systemAccountAuth : systemAccountAuthInsertList)
                commonService.save(systemAccountAuth);
        if (systemRoleAuthInsertList != null
            & systemRoleAuthInsertList.size() != 0)
            for (Object systemRoleAuth : systemRoleAuthInsertList)
                commonService.save(systemRoleAuth);
    }

    /**
     * 删除指定的用户权限表和角色信息表
     * @param deleteSystemAccountAuthList
     * @param deleteSystemRoleAuthList
     */
    @Transactional
    private void setDeleteList(List<Object> deleteSystemAccountAuthList,
            List<Object> deleteSystemRoleAuthList, Date systemDate)
    {
        if (deleteSystemAccountAuthList != null
            & deleteSystemAccountAuthList.size() != 0)
        {
            for (Object systemAccountAuth : deleteSystemAccountAuthList)
            {
                SystemAccountAuth acount = (SystemAccountAuth) systemAccountAuth;
                acount.setDeleteFlag(Constants.DELETE_FLAG);
                acount.setDeleteTime(systemDate);
                commonService.update(acount);
            }
        }
        if (deleteSystemRoleAuthList != null
            & deleteSystemRoleAuthList.size() != 0)
        {
            for (Object systemRoleAuth : deleteSystemRoleAuthList)
            {
                SystemRoleAuth role = (SystemRoleAuth) systemRoleAuth;
                // Author :tong_meng
                // Date : 2013/4/12 15:20
                // [BUG]0015054 MODIFY BEGIN
                //role.setDeleteFlag(Constants.DELETE_FLAG);
                //role.setDeleteTime(systemDate);
                commonService.delete(role);
                // [BUG]0015054 MODIFY BEGIN
            }
        }
    }

    // $Author:wu_jianfeng
    // $Date : 2012/09/12 14:10
    // $[BUG]0009663 ADD BEGIN
    // 访问控制实现
    /**
     * 根据用户ID，得到该用户的权限
     */
    @Override
    @Transactional
    public List<String> getUserAuths(String userId)
    {
        // 取最大权限
        List<String> auths = new ArrayList<String>();
        List<UserAuthDto> userAuths = accessControlDao.selectUserAuths(userId);
        for (UserAuthDto userAuth : userAuths)
        {
            String systemAuthId = userAuth.getSystemAuthId();
            if (!auths.contains(systemAuthId))
                auths.add(systemAuthId);
        }
        return auths;
        // List<UserAuthDto> userAuths =
        // accessControlDao.selectUserAuths(userId);
        // return userAuths;
    }
    // $[BUG]0009663 ADD END
}
