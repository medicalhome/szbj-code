package com.yly.cdr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.SystemRole;
import com.yly.cdr.entity.SystemRoleAuth;
import com.yly.cdr.entity.SystemUserRole;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.service.RoleService;

@Component
public class RoleServiceImpl implements RoleService
{
    @Autowired
    private CommonService commonService;

    @Autowired
    private EntityDao entityDao;

    @Override
    @Transactional
    public List<SystemUserRole> selectSystemUserRole(String roleNo)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("systemRoleId", roleNo);
        List result = new ArrayList<SystemUserRole>();
        result = entityDao.selectByCondition(SystemUserRole.class, map);
        return result;
    }

    @Override
    @Transactional
    public List<SystemRole> selectSystemRole(String roleNo)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("systemRoleId", roleNo);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List result = new ArrayList<SystemRole>();
        result = entityDao.selectByCondition(SystemRole.class, map);
        return result;
    }

    // $Author :tong_meng
    // $Date : 2012/10/17 14:00$
    // [BUG]0010503 ADD BEGIN
    @Override
    @Transactional
    public List<SystemRoleAuth> selectSystemRoleAuth(String roleNo)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("systemRoleId", roleNo);
        map.put("deleteFlag", Constants.NO_DELETE_FLAG);
        List result = new ArrayList<SystemRole>();
        result = entityDao.selectByCondition(SystemRoleAuth.class, map);
        return result;
    }

    // [BUG]0010503 ADD END

    // $Author :tong_meng
    // $Date : 2012/10/17 14:00$
    // [BUG]0010503 MODIFY BEGIN
    // $Author :tong_meng
    // $Date : 2012/9/28 16:20$
    // [BUG]0010193 MODIFY BEGIN
    @Override
    @Transactional
    public void saveSystemUserRole(List<Object> systemRoleInsertList,
            List<Object> systemUserRoleInsertList, List<Object> roleUpdateList,
            List<Object> userRoleUpdateList, List<Object> roleAuthList,
            String newUpFlag)
    {
        deleteList(roleUpdateList, userRoleUpdateList, roleAuthList);

        saveList(systemRoleInsertList, systemUserRoleInsertList, roleAuthList,
                newUpFlag);
    }

    private void deleteList(List<Object> roleUpdateList,
            List<Object> userRoleUpdateList, List<Object> roleAuthList)
    {
        for (Object object : userRoleUpdateList)
        {
            for (Object object1 : (List<Object>) object)
            {
                commonService.delete(object1);
            }
        }
        for (Object object : roleAuthList)
        {
            for (Object object1 : (List<Object>) object)
            {
                commonService.delete(object1);
            }
        }
        for (Object object : roleUpdateList)
        {
            for (Object object1 : (List<Object>) object)
            {
                commonService.delete(object1);
            }
        }
    }

    private void saveList(List<Object> systemRoleInsertList,
            List<Object> systemUserRoleInsertList, List<Object> roleAuthList,
            String newUpFlag)
    {
        for (Object object : systemRoleInsertList)
        {
            commonService.save(object);
        }
        for (Object object : systemUserRoleInsertList)
        {
            commonService.save(object);
        }
        if (!Constants.DELETE_MESSAGE_FLAG.equals(newUpFlag))
        {
            for (Object object : roleAuthList)
            {
                for (Object object1 : (List<Object>) object)
                {
                    commonService.save(object1);
                }
            }
        }
    }
    // [BUG]0010193 MODIFY BEGIN
    // [BUG]0010503 MODIFY END

    /**
     * 打印权限控制
     * Added by yang_jianbo@2014-4-21
     */
	@Override
	@Transactional
	public List<SystemUserRole> selectSystemRoleNo(String userId) {
		  Map<String, Object> map = new HashMap<String, Object>();
	      map.put("userId", userId);
	      map.put("deleteFlag", Constants.NO_DELETE_FLAG);
	      List result = new ArrayList<SystemUserRole>();
	      result = entityDao.selectByCondition(SystemUserRole.class, map);
	      return result;
	}

	/**
	 * 根据角色NO和权限ID查询是否存在对应的记录
	 */
	@Override
	@Transactional
	public List<SystemRoleAuth> selectSystemRoleAuth(String roleNo,
			String authId) {
		  Map<String, Object> map = new HashMap<String, Object>();
	      map.put("systemRoleId", roleNo);
	      map.put("systemAuthId", authId);
	      map.put("deleteFlag", Constants.NO_DELETE_FLAG);
	      List result = new ArrayList<SystemRoleAuth>();
	      result = entityDao.selectByCondition(SystemRoleAuth.class, map);
	      return result;
	}

}
