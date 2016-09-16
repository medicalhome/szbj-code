package com.founder.cdr.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.founder.cdr.dao.LoginDao;
import com.founder.cdr.dto.LoginDto;
import com.founder.cdr.service.LoginService;

/**
 * 用于查询用户信息
 * @author jin_peng
 * @version 1.0, 2012/07/17
 */
@Component
public class LoginServiceImpl implements LoginService
{
    @Autowired
    private LoginDao loginDao;

    /**
     * 根据用户id得到相应用户信息
     * @param userId 用户id
     * @return 用户信息对象集合
     */
    @Transactional
    public List<LoginDto> selectUserMessages(String userId)
    {
        return loginDao.selectUserMessages(userId);
    }

    // $Author :jin_peng
    // $Date : 2012/09/21 14:01$
    // [BUG]0009561 ADD BEGIN
    /**
     * 根据患者本地ID，域ID查询相应患者的内部序列号
     * @param patientId 患者本地ID
     * @param patientDomain 域ID
     * @return 患者内部序列号
     */
    @Transactional
    public LoginDto selectPatient(String patientId, String patientDomain)
    {
        return loginDao.selectPatient(patientId, patientDomain);
    }

    // [BUG]0009561 ADD END

    // $Author :jin_peng
    // $Date : 2012/10/30 17:38$
    // [BUG]0010836 ADD BEGIN
    /**
     * 记录系统退出日志
     * @param exitKindsName 退出方式名称
     * @param systemTime 系统当前时间
     * @param accountLogSn 用户日志内部序列号
     */
    @Transactional
    public void recordSystemExitLog(String exitKindsName, String systemTime,
            BigDecimal accountLogSn)
    {
        loginDao.recordSystemExitLog(exitKindsName, systemTime, accountLogSn);
    }
    // [BUG]0010836 ADD END

}
