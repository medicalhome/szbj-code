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

import com.founder.fasf.core.util.daohelper.entity.EntityDao;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.SystemAccount;
import com.yly.cdr.hl7.dto.msh0001.MSH0001;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.StringUtils;

/**
 * 账户信息服务结果数据
 * @author tong_meng
 *
 */
@Component(value = "msh0001Writer")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MSH0001Writer implements BusinessWriter<MSH0001>
{
    private Logger logger = LoggerFactory.getLogger(MSH0001Writer.class);

    @Autowired
    private CommonService commonService;

    @Autowired
    private EntityDao entityDao;

    // 操作類型
    private String newUpFlag;

    // 要更新的账户信息
    private SystemAccount sysAccount;

    /**
     * 检查操作类型格式
     * @return
     */
    private boolean isFormCorrect()
    {
        if (Constants.INSERT_MESSAGE_FLAG.equals(newUpFlag)
            || Constants.UPDATE_MESSAGE_FLAG.equals(newUpFlag)
            || Constants.DELETE_MESSAGE_FLAG.equals(newUpFlag))
            return true;
        logger.error("操作类型格式不正确！");
        return false;
    }

    @Override
    public boolean validate(MSH0001 t)
    {
        newUpFlag = t.getNewUpFlag();
        logger.debug(" {} 状态", newUpFlag);
        return isFormCorrect();
    }

    /**
     * 更新时，检查要更新的消息是否存在
     */
    @Override
    public boolean checkDependency(MSH0001 t,boolean flag)
    {
        if (isUpdateMessage(t) || isDeleteMessage(t))
        {
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("userId", t.getUserId());
            condition.put("deleteFlag", Constants.NO_DELETE_FLAG);
            List<Object> list = new ArrayList<Object>();
            list = entityDao.selectByCondition(SystemAccount.class, condition);
            if (list == null || list.size() == 0)
            {
                logger.debug("未找到要更新或者删除的数据！");
                return false;
            }
            sysAccount = (SystemAccount) list.get(0);
            logger.debug("要更新的账户信息的用户ID为    {} ", sysAccount.getUserId());
        }
        return true;
    }

    @Override
    @Transactional
    public void saveOrUpdate(MSH0001 t)
    {
        SystemAccount systemAccount = getSystemAccount(t);
        if (isNewMessage(t))
            commonService.save(systemAccount);
        // Author :jia_yanqing
        // Date : 2013/4/12 10:00
        // [BUG]0015042 MODIFY BEGIN
        if (isUpdateMessage(t))
            commonService.update(systemAccount);
        if (isDeleteMessage(t))
            commonService.delete(systemAccount);
        // [BUG]0015042 MODIFY BEGIN
    }

    private SystemAccount getSystemAccount(MSH0001 t)
    {
        SystemAccount systemAccount = null;
        // 取系统时间
        Date systemDate = DateUtils.getSystemTime();
        if (isNewMessage(t))
        {
            systemAccount = new SystemAccount();

            // $Author :tong_meng
            // $Date : 2012/5/24 17:45$
            // [BUG]0008109 MODIFY BEGIN
            // 创建时间
            systemAccount.setCreateTime(systemDate);
            // [BUG]0008109 MODIFY END
            // 更新回数
            systemAccount.setUpdateCount(Constants.INSERT_UPDATE_COUNT);
            // 删除标志
            systemAccount.setDeleteFlag(Constants.NO_DELETE_FLAG);
        }
        else
        {
            systemAccount = sysAccount;
            
            if (isDeleteMessage(t))
            {
                // Author :jia_yanqing
                // Date : 2013/4/12 10:00
                // [BUG]0015042 DELETE BEGIN
                // 设置删除标志
                /**
                systemAccount.setDeleteFlag(Constants.DELETE_FLAG);

                // $Author :tong_meng
                // $Date : 2012/5/24 17:45$
                // [BUG]0008109 MODIFY BEGIN
                // 删除时间
                systemAccount.setDeleteTime(systemDate);
                // 更新时间
                systemAccount.setUpdateTime(systemDate);
                // [BUG]0008109 MODIFY END
                */
                // [BUG]0015042 DELETE BEGIN

                return systemAccount;
            }
            
        }
        // 密码变化设定状态
        systemAccount.setInitPwd(t.getResetPasswdFlag());
        // 用户ID
        systemAccount.setUserId(t.getUserId());
        // 密码
        systemAccount.setPasswd(t.getPassword());
        // 用户名
        systemAccount.setUserName(t.getUserName());
        // 性别
        systemAccount.setSex(StringUtils.strToBigDecimal(t.getSex(), "性别"));

        // $Author :tong_meng
        // $Date : 2012/8/13 15:00$
        // [BUG]0008886 MODIFY BEGIN
        // email
        systemAccount.setEmail(t.getMobile());
        // 电话
        systemAccount.setMobile(t.getPhone());
        // [BUG]0008886 MODIFY END

        // 部门编码
        systemAccount.setDeptCode(t.getDeptCode());

        // $Author :tong_meng
        // $Date : 2012/8/13 15:00$
        // [BUG]0008886 MODIFY BEGIN
        // $Author:wei_peng
        // $Date:2012/12/12 10:22
        // $[BUG]0012376 DELETE BEGIN
        // 职务编码
        // systemAccount.setGroupCd(t.getGroupCd());
        // $[BUG]0012376 DELETE END
        // 账户状态
        systemAccount.setStatus(t.getStatus());
        // [BUG]0008886 MODIFY END

        // $Author :tong_meng
        // $Date : 2012/8/13 15:00$
        // [BUG]0008886 DELETE BEGIN
        // // 有效起始日期
        // systemAccount.setExptBeginDate(DateUtils.stringToDate(t.getExptBeginDate()));
        // // 有效截至日期
        // systemAccount.setExptEndDate(DateUtils.stringToDate(t.getExptEndDate()));
        // [BUG]0008886 DELETE END

        // $Author :tong_meng
        // $Date : 2012/8/13 15:00$
        // [BUG]0008886 ADD BEGIN
        // 人员类型
        systemAccount.setEmployeeTypeCd(t.getEmployeeTypeCd());
        // 在岗状态
        systemAccount.setEmploymentStatusCd(t.getEmploymentStatusCd());
        // 人员所属
        systemAccount.setJobCategory(t.getJobCategory());
        // 入职日期
        systemAccount.setServiceStartDate(DateUtils.stringToDate(t.getServiceStartDate()));

        // [BUG]0008886 ADD END

        // 备注
        systemAccount.setMemo(t.getMemo());

        // $Author :tong_meng
        // $Date : 2012/5/24 17:45$
        // [BUG]0008109 MODIFY BEGIN
        // 更新时间
        systemAccount.setUpdateTime(systemDate);
        // [BUG]0008109 MODIFY END

        return systemAccount;
    }

    private boolean isDeleteMessage(MSH0001 t)
    {
        return Constants.DELETE_MESSAGE_FLAG.equals(newUpFlag);
    }

    private boolean isUpdateMessage(MSH0001 t)
    {
        return Constants.UPDATE_MESSAGE_FLAG.equals(newUpFlag);
    }

    // $Author :tong_meng
    // $Date : 2012/5/24 17:45$
    // [BUG]0008009 MODIFY BEGIN
    private boolean isNewMessage(MSH0001 t)
    {
        return Constants.INSERT_MESSAGE_FLAG.equals(newUpFlag);
    }
    // [BUG]0008009 MODIFY END

}
