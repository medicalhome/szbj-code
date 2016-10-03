package com.yly.cdr.batch.writer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.SystemAccountAuth;
import com.yly.cdr.entity.SystemAuth;
import com.yly.cdr.entity.SystemRole;
import com.yly.cdr.entity.SystemRoleAuth;
import com.yly.cdr.hl7.dto.AccessControl;
import com.yly.cdr.hl7.dto.bs908.BS908;
import com.yly.cdr.service.AccessControlService;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.util.DateUtils;

/**
 * 访问权限控制
 * @author tong_meng
 *
 */
@Component(value = "bs908Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BS908Writer implements BusinessWriter<BS908>
{
    private Logger logger = LoggerFactory.getLogger(BS908Writer.class);

    // 要新增的账户(个人)权限表集合
    private List<Object> systemAccountAuthInsertList;

    // 删除的账户(个人)权限表集合(处理过的deleteFlag = 1 的数据)
    private List<Object> systemAccountAuthDeleteList;

    // 要新增的角色权限表集合
    private List<Object> systemRoleAuthInsertList;

    // 删除的角色权限表集合(处理过的deleteFlag = 1 的数据)
    private List<Object> systemRoleAuthDeleteList;

    // 根据操作类型--insert类型消息集合
    private List<AccessControl> accessControlInsertList;
    
    // $Author :tong_meng
    // $Date : 2013/3/7 11:20$
    // [BUG]0014352 ADD BEGIN
    // 如果是用户信息，并且用户信息不存在
    private boolean isUserAndUserExists = true;

    // 如果是用户信息，并且用户信息不存在
    private boolean isRoleAndRoleExists = true;
    
    // 账户权限不存在
    private boolean isAccountAuthExists ;
    
    // 角色权限不存在
    private boolean isRoleAuthExists;
    // [BUG]0014352 ADD END
    
    @Autowired
    private AccessControlService accessControlService;

    @Autowired
    private CommonService commonService;

    // $Author :tong_meng
    // $Date : 2013/3/7 11:20$
    // [BUG]0014352 MODIFY BEGIN
    @Override
    public boolean validate(BS908 t)
    {
        accessControlInsertList = new ArrayList<AccessControl>();
        systemAccountAuthDeleteList = new ArrayList<Object>();
        systemRoleAuthDeleteList = new ArrayList<Object>();
        List<AccessControl> accessControlList = t.getAccessControl();
        for (AccessControl accessControl : accessControlList)
        {
            if (isUser(accessControl))
            {
                if(!checkUser(accessControl))
                {
                    isUserAndUserExists = false;
                    break;
                }
            }
            else if (isRole(accessControl))
            {
                if(!checkRole(accessControl))
                {
                    isRoleAndRoleExists = false;
                    break;
                }
            }
            else
                return false;
        }
        return true;
    }

    /**
     * 如果更新或者删除找不到关联数据，那么消息就是错误的消息
     */
    @Override
    public boolean checkDependency(BS908 t,boolean flag)
    {
        if (!isUserAndUserExists)
        {
            logger.error("信息时，不存在该权限！");
            return false;
        }
        
        if (!isRoleAndRoleExists)
        {
            logger.error("角色信息时，不存在该权限！");
            return false;
        }
        logger.info("可以对角色或者用户表进行操作！");
        logger.info("需要插入的数据为  {}  条！", accessControlInsertList == null ? 0
                : accessControlInsertList.size());
        logger.info("需要删除用户信息表的数据为    {}  条！",
                systemAccountAuthDeleteList == null ? 0
                        : systemAccountAuthDeleteList.size());
        logger.info(
                "需要删除角色信息表的数据为    {}  条！",
                systemRoleAuthDeleteList == null ? 0
                        : systemRoleAuthDeleteList.size());
        return true;
    }
    // [BUG]0014352 MODIFY END
    
    private boolean checkRole(AccessControl accessControl)
    {
        logger.debug("消息是角色信息时：");
        // 查询要删除的角色信息表是否存在
        boolean systemRoleAuthExists = isSystemRoleAuthExsits(accessControl);
        if (isNewMessage(accessControl))
        {
            logger.debug("新增时...");
            boolean systemAuthExists = isSystemAuthExsits(accessControl);
            boolean systemRoleExists = isRoleExists(accessControl);
            if (systemAuthExists && systemRoleExists && !systemRoleAuthExists)
            {
                logger.debug("关联的权限信息存在：权限ID为{}", accessControl.getAuthId());
                logger.debug("要新增的数据不存在：角色ID{}，权限ID{}",
                        accessControl.getUserOrRoleNo(),
                        accessControl.getAuthId());
                accessControlInsertList.add(accessControl);
            }
            else if (!systemAuthExists)
            {
                logger.error("关联的权限信息不存在：权限ID为{}", accessControl.getAuthId());

                return false;
            }
            else if (!systemRoleExists)
            {
                logger.error("关联的角色信息不存在：角色ID为{}",
                        accessControl.getUserOrRoleNo());
                return false;
            }
            else if (systemRoleAuthExists)
            {
                logger.debug("要新增的数据存在：角色ID{}，权限ID{}",
                        accessControl.getUserOrRoleNo(),
                        accessControl.getAuthId());
                // $Author :tong_meng
                // $Date : 2013/3/7 11:20$
                // [BUG]0014352 ADD BEGIN
                isRoleAuthExists = true;
                // [BUG]0014352 ADD END
                return true;
            }
        }
        else if (isDeleteMessage(accessControl))
        {
            logger.debug("删除时...");
            if (!systemRoleAuthExists)
            {
                logger.error("未找到要删除的用户权限信息！角色ID{}",
                        accessControl.getUserOrRoleNo());
                return false;
            }
        }
        else
            return false;
        return true;
    }

    private boolean checkUser(AccessControl accessControl)
    {
        logger.debug("消息是账户信息：");
        // 查询要删除的用户权限表是否存在
        boolean systemAccountAuthExists = isSystemAccountAuthExsits(accessControl);
        if (isNewMessage(accessControl))
        {
            logger.debug("新增时...");
            // 查询是否有重复记录
            boolean systemAuthExsits = isSystemAuthExsits(accessControl);
            if (systemAuthExsits && !systemAccountAuthExists)
            {
                logger.debug("关联的权限信息存在：权限ID为{}", accessControl.getAuthId());
                logger.debug("要新增的数据不存在：用户ID{}，权限ID{}",
                        accessControl.getUserOrRoleNo(),
                        accessControl.getAuthId());
                accessControlInsertList.add(accessControl);
            }
            else if (!systemAuthExsits)
            {
                logger.error("关联的权限信息不存在：权限ID为{}",
                        accessControl.getAuthId());
                return false;
            }
            else if (systemAccountAuthExists)
            {
                logger.debug("要新增的数据存在：用户ID{}，权限ID{}",
                        accessControl.getUserOrRoleNo(),
                        accessControl.getAuthId());
                // $Author :tong_meng
                // $Date : 2013/3/7 11:20$
                // [BUG]0014352 ADD BEGIN
                isAccountAuthExists = true;
                // [BUG]0014352 ADD END
                return true;
            }
        }
        else if (isDeleteMessage(accessControl))
        {
            logger.debug("删除时...");
            if (!systemAccountAuthExists)
            {
                logger.error("要删除的账户信息数据不存在：用户ID{}，权限ID{}",
                        accessControl.getUserOrRoleNo(),
                        accessControl.getAuthId());
                return false;
            }
        }

        return true;
    }

    /**
     * 如果消息传来的是角色权限信息，
     * 关联查询角色信息表
     * @param accessControl
     * @return 返回关联的结果是否存在
     */
    private boolean isRoleExists(AccessControl accessControl)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("systemRoleId", accessControl.getUserOrRoleNo());
        List<Object> systemRole = commonService.selectByCondition(
                SystemRole.class, map);
        if (systemRole == null || systemRole.size() == 0)
            return false;
        else
            return true;
    }

    /**
     * 查询要删除的用户权限表是否存在
     * @param accessControl
     * @return  是否存在的标识
     */
    private boolean isSystemAccountAuthExsits(AccessControl accessControl)
    {
        // 查询要删除的用户权限表
        List<SystemAccountAuth> deleteSystemAccountAuthList = accessControlService.selectSystemAccountAuth(
                accessControl.getAuthId(), accessControl.getUserOrRoleNo());
        // 如果没有，消息打回
        if (deleteSystemAccountAuthList == null
            || deleteSystemAccountAuthList.size() == 0)
            return false;
        // 有，如果是删除消息，增加删除信息
        else if (isDeleteMessage(accessControl))
            systemAccountAuthDeleteList.add(deleteSystemAccountAuthList.get(0));
        return true;
    }

    /**
     * 查询要删除的角色信息表是否存在
     * @param accessControl
     * @return  是否存在的标识
     */
    private boolean isSystemRoleAuthExsits(AccessControl accessControl)
    {
        // 查询要删除的角色权限表
        List<SystemRoleAuth> deleteSystemRoleAuthList = accessControlService.selectSystemRoleAuth(
                accessControl.getAuthId(), accessControl.getUserOrRoleNo());
        // 如果没有，消息打回
        if (deleteSystemRoleAuthList == null
            || deleteSystemRoleAuthList.size() == 0)
            return false;
        // 有，如果是删除消息，增加删除信息
        else if (isDeleteMessage(accessControl))
            systemRoleAuthDeleteList.add(deleteSystemRoleAuthList.get(0));
        return true;
    }

    /**
     * 需要找到关联数据，找不到就认为是错误的消息
     * @param accessControl
     * @return 是否找到关联数据
     */
    private boolean isSystemAuthExsits(AccessControl accessControl)
    {
        List<SystemAuth> systemAuths = accessControlService.selectSystemAuthByAuthId(accessControl.getAuthId());
        if (systemAuths == null || systemAuths.size() == 0)
            return false;
        return true;
    }

    @Override
    @Transactional
    public void saveOrUpdate(BS908 t)
    {
        // $Author :tong_meng
        // $Date : 2013/3/7 11:20$
        // [BUG]0014352 ADD BEGIN
        if(isAccountAuthExists)
            return;
        if(isRoleAuthExists)
            return;
        // [BUG]0014352 ADD END
        
        Date systemDate = DateUtils.getSystemTime();
        this.setList(systemDate);
        accessControlService.updateRoleOrUser(systemAccountAuthInsertList,
                systemRoleAuthInsertList, systemAccountAuthDeleteList,
                systemRoleAuthDeleteList, systemDate);
    }

    /**
     * 向集合中添加要插入数据库的实体
     */
    public void setList(Date systemDate)
    {
        systemRoleAuthInsertList = new ArrayList<Object>();
        systemAccountAuthInsertList = new ArrayList<Object>();
        for (AccessControl accessControl : accessControlInsertList)
        {
            if (isUser(accessControl))
                setSystemAccountAuth(accessControl, systemDate);
            if (isRole(accessControl))
                setSystemRoleAuth(accessControl, systemDate);
        }
    }

    /**
     * 得到要新增的账户权限表
     * @param accessControl
     */
    private void setSystemAccountAuth(AccessControl accessControl,
            Date systemDate)
    {
        // 账户(个人)权限表
        SystemAccountAuth systemAccountAuth = new SystemAccountAuth();
        systemAccountAuth.setUserId(accessControl.getUserOrRoleNo()); // 用户ID
        systemAccountAuth.setSystemAuthId(accessControl.getAuthId()); // 权限ID
        systemAccountAuth.setCreateTime(systemDate);
        systemAccountAuth.setUpdateTime(systemDate);
        systemAccountAuth.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        systemAccountAuth.setDeleteFlag(Constants.NO_DELETE_FLAG);
        systemAccountAuthInsertList.add(systemAccountAuth);
    }

    /**
     * 得到要新增的角色权限表
     * @param accessControl
     */
    private void setSystemRoleAuth(AccessControl accessControl, Date systemDate)
    {
        // 角色权限表
        SystemRoleAuth systemRoleAuth = new SystemRoleAuth();
        systemRoleAuth.setSystemRoleId(accessControl.getUserOrRoleNo()); // 角色ID
        systemRoleAuth.setSystemAuthId(accessControl.getAuthId()); // 权限ID
        systemRoleAuth.setCreateTime(systemDate);
        systemRoleAuth.setUpdateTime(systemDate);
        systemRoleAuth.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
        systemRoleAuth.setDeleteFlag(Constants.NO_DELETE_FLAG);
        systemRoleAuthInsertList.add(systemRoleAuth);
    }

    /**
     * 判断是否是用户信息
     * @param accessControl
     * @return
     */
    public boolean isUser(AccessControl accessControl)
    {
        return Constants.USER.equals(accessControl.getUserOrRole());
    }

    /**
     * 判断是否是角色信息
     * @param accessControl
     * @return
     */
    public boolean isRole(AccessControl accessControl)
    {
        return Constants.ROLE.equals(accessControl.getUserOrRole());
    }

    /**
     * 是否是删除消息
     * @param c
     * @return
     */
    private boolean isDeleteMessage(AccessControl accessControl)
    {
        return Constants.DELETE_MESSAGE_FLAG.equals(accessControl.getNewUpFlag());
    }

    /**
     * 是否是新增消息
     * @param c
     * @return
     */
    private boolean isNewMessage(AccessControl accessControl)
    {
        return Constants.INSERT_MESSAGE_FLAG.equals(accessControl.getNewUpFlag());
    }

}
