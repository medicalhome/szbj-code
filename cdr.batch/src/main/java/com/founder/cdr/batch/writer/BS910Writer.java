package com.founder.cdr.batch.writer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.core.Constants;
import com.founder.cdr.entity.SystemRole;
import com.founder.cdr.entity.SystemRoleAuth;
import com.founder.cdr.entity.SystemUserRole;
import com.founder.cdr.hl7.dto.Role;
import com.founder.cdr.hl7.dto.bs910.BS910;
import com.founder.cdr.service.RoleService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;

/**
 * 角色更新通知
 * @author tong_meng
 *
 */
// $Author :tong_meng
// $Date : 2012/9/28 16:20$
// [BUG]0010193 MODIFY BEGIN
@Component(value = "bs910Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BS910Writer implements BusinessWriter<BS910>
{
    private Logger logger = LoggerFactory.getLogger(BS910Writer.class);

    // 根据操作类型--insert类型消息集合,角色用户表集合
    private List<Object> userRoleInsertList;

    // 根据操作类型--insert类型消息集合,角色信息表集合
    private List<Object> roleInsertList;

    // 要更新的用户角色集合
    private List<Object> userRoleUpdateList;

    // 要更新的角色信息集合
    private List<Object> roleUpdateList;

    // $Author :tong_meng
    // $Date : 2012/10/17 14:00$
    // [BUG]0010503 ADD BEGIN
    // 角色权限集合
    private List<Object> roleAuthList;

    // [BUG]0010503 ADD END

    @Autowired
    private RoleService roleService;

    @Override
    public boolean validate(BS910 t)
    {
        return true;
    }

    /**
     * 判断关联消息是否存在
     * 如果是新增消息，关联角色信息表
     * 如果是删除消息，关联要删除的消息是否存在
     */
    @Override
    public boolean checkDependency(BS910 t,boolean flag)
    {
        userRoleInsertList = new ArrayList<Object>();
        roleInsertList = new ArrayList<Object>();
        userRoleUpdateList = new ArrayList<Object>();
        roleUpdateList = new ArrayList<Object>();
        roleAuthList = new ArrayList<Object>();
        return checkRoles(t);
    }

    /**
     * 
     * @param t
     * @return
     */
    private boolean checkRoles(BS910 t)
    {
        List<Role> roles = t.getRole();
        for (Role role : roles)
        {
            logger.debug("消息{}场合", role.getNewUpFlag());
            // 检查角色信息表中的数据
            this.checkSystemRole(role);
            // 检查角色用户表中的数据
            this.checkSystemUserRole(role);
        }
        return true;
    }

    /**
     * 查询角色信息表是否存在关联的数据
     * @param role  带有新增标志的dto
     * @return  返回的是，是否找到关联消息
     */
    private void checkSystemRole(Role role)
    {
        List<SystemRole> systemRoles = roleService.selectSystemRole(role.getRoleNo());
        logger.debug("关联查询的角色ID为    {}  的角色信息表是否存在！  {}  ", role.getRoleNo(),
                systemRoles != null && systemRoles.size() != 0);
        List<SystemRoleAuth> systemRoleAuths = roleService.selectSystemRoleAuth(role.getRoleNo());
        if (systemRoles != null && !systemRoles.isEmpty())
            roleUpdateList.add(systemRoles);
        // $Author :tong_meng
        // $Date : 2012/10/17 14:00$
        // [BUG]0010503 ADD BEGIN
        if (systemRoleAuths != null && !systemRoleAuths.isEmpty())
            roleAuthList.add(systemRoleAuths);
        // [BUG]0010503 ADD END
        if (!isDeleteMessage(role))
            roleInsertList.add(role);
    }

    /**
     * 查询用户角色表的关联消息是否存在
     * @param role 带有删除标志的dto
     * @return  返回的是，是否找到关联消息
     */
    private void checkSystemUserRole(Role role)
    {
        List<SystemUserRole> systemUserRoles = null;
        systemUserRoles = roleService.selectSystemUserRole(role.getRoleNo());
        logger.debug("关联查询的角色信息表是否存在数据！  {}  ", systemUserRoles != null
            && systemUserRoles.size() != 0);
        if (systemUserRoles != null && !systemUserRoles.isEmpty())
            userRoleUpdateList.add(systemUserRoles);
        if (!isDeleteMessage(role))
            userRoleInsertList.add(role);
    }

    @Override
    @Transactional
    public void saveOrUpdate(BS910 t)
    {
        Date systemDate = DateUtils.getSystemTime();
        List<Object> systemUserRoleInsertList = new ArrayList<Object>();
        List<Object> systemRoleInsertList = new ArrayList<Object>();
        
        // $Author :tong_meng
        // $Date : 2013/3/6 15:32$
        // [BUG]0014301 ADD BEGIN
        /*if (!isDeleteMessage(t.getRole().get(0)))
        {*/
        systemUserRoleInsertList = this.getSystemUserRoleInsertList(systemDate);
        systemRoleInsertList = this.getSystemRoleInsertList(systemDate);
        /*}*/
        logger.debug("需要先删除用户角色表{}条数据，再做{}操作。", userRoleUpdateList.size(), "插入");
        logger.debug("需要插入用户角色表{}条数据", systemUserRoleInsertList.size());
        logger.debug("需要先删除角色信息表{}条数据，再做{}操作。", roleUpdateList.size(), "插入");
        logger.debug("需要插入角色信息表{}条数据", systemRoleInsertList.size());
        // [BUG]0014301 ADD END
        
        roleService.saveSystemUserRole(systemRoleInsertList,
                systemUserRoleInsertList, roleUpdateList, userRoleUpdateList,
                roleAuthList, t.getRole().get(0).getNewUpFlag());
    }

    /**
     * 得到要新增的角色信息表
     * @param systemDate
     * @return
     */
    private List<Object> getSystemRoleInsertList(Date systemDate)
    {
        List<Object> systemRoleInsertList = new ArrayList<Object>();
        SystemRole systemRole ;
        for (Object object : roleInsertList)
        {
            systemRole = new SystemRole();
            Role roleInsert = (Role) object;
            systemRole.setSystemRoleId(roleInsert.getRoleNo());
            systemRole.setRoleName(roleInsert.getRoleName());
            systemRole.setOccupationType(StringUtils.strToBigDecimal(
                    roleInsert.getOccupationalType(), "pe"));
            systemRole.setCreateTime(systemDate);
            systemRole.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            systemRole.setUpdateTime(systemDate);
            systemRole.setDeleteFlag(Constants.NO_DELETE_FLAG);
            systemRoleInsertList.add(systemRole);
        }
        return systemRoleInsertList;
    }

    /**
     * 得到要新增的用户角色表
     * @param systemDate
     * @return
     */
    private List<Object> getSystemUserRoleInsertList(Date systemDate)
    {
        List<Object> systemUserRoleInsertList = new ArrayList<Object>();
        for (Object object : userRoleInsertList)
        {
            Role userRoleInsert = (Role) object;
            if(!StringUtils.isEmpty(userRoleInsert.getRoleUser()))
            {
                String[] str = userRoleInsert.getRoleUser().split(",");
                SystemUserRole systemUserRole = null;
                for (String userNo : str)
                {
                    // $Author :tong_meng
                    // $Date : 2012/10/10 15:27$
                    // [BUG]0010229 MODIFY BEGIN 增加判断userNo为空的情况
                    if (!StringUtils.isEmpty(userNo.trim()))
                    {
                        systemUserRole = new SystemUserRole();
                        systemUserRole.setUserId(userNo);
                        systemUserRole.setSystemRoleId(userRoleInsert.getRoleNo());
                        systemUserRole.setCreateTime(systemDate);
                        systemUserRole.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
                        systemUserRole.setUpdateTime(systemDate);
                        systemUserRole.setDeleteFlag(Constants.NO_DELETE_FLAG);
                        systemUserRoleInsertList.add(systemUserRole);
                    }
                    // [BUG]0010229 MODIFY END
                }
            }
        }
        return systemUserRoleInsertList;
    }

    /**
     * 是否是删除消息
     * @param c
     * @return
     */
    private boolean isDeleteMessage(Role role)
    {
        return Constants.DELETE_MESSAGE_FLAG.equals(role.getNewUpFlag());
    }
}
// [BUG]0010193 MODIFY BEGIN
