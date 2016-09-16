package com.founder.cdr.web.idm.webservice.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.founder.cdr.entity.SystemAccount;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.web.idm.IDMConstant;
import com.founder.cdr.web.idm.webservice.IDMAccountWebservice;

public class IDMAccountWebserviceImpl implements IDMAccountWebservice {

	private static final Logger logger = LoggerFactory.getLogger(AddAcountWebserviceImpl.class);

	@Autowired
	CommonService commonService;
	
	@Override
	public String addAcount(String account, String username, String password,
			String organization) {
		try{
			Map<String ,Object> condition = new HashMap<String ,Object>();
			condition.put("userId", account);
			condition.put("deleteFlag", "0");
			List<Object> list = commonService.selectByCondition(SystemAccount.class, condition);
			if(list.size()>0){
				logger.debug("not find the account:",account);
				return IDMConstant.ADD_RESPONSE_001;
			}
			SystemAccount systemAccount = new SystemAccount();
			systemAccount.setAccountSn(BigDecimal.valueOf(new Date().getTime()));
			systemAccount.setUserId(account);
			systemAccount.setUserName(username);
			systemAccount.setPasswd(password);
			systemAccount.setDeptCode(organization);
			systemAccount.setCreateTime(new Date());
			systemAccount.setStatus("1");
			systemAccount.setUpdateCount(BigDecimal.valueOf(0));
			systemAccount.setDeleteFlag("0");
			systemAccount.setMemo("idm");
			commonService.save(systemAccount);
		}catch(Exception e){
			logger.debug("add account Exception:",e);
			return IDMConstant.ADD_RESPONSE_002;
		}
		logger.debug("add acount \"" + account + "\" success!!");
		return IDMConstant.ADD_RESPONSE_000;
	}

	@Override
	public String deleteAcount(String account) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("userId", account);
			condition.put("deleteFlag", "0");
			condition.put("status", "1");
			List<Object> list = commonService.selectByCondition(
					SystemAccount.class, condition);
			if(list.size()<1){
				logger.debug("not find the account:",account);
				return IDMConstant.DELETE_RESPONSE_401;
			}
			SystemAccount systemAccount = (SystemAccount) list.get(0);
			systemAccount.setDeleteFlag("1");
			systemAccount.setDeleteTime(new Date());
			commonService.update(systemAccount);
		} catch (Exception e) {
			logger.debug("delete account Exception",e);
			return IDMConstant.DELETE_RESPONSE_402;
		}
		logger.debug("enalble account \"" + account + "\" success!!");
		return IDMConstant.DELETE_RESPONSE_400;
	}

	@Override
	public String disableAcount(String account) {
		try{
			Map<String ,Object> condition = new HashMap<String ,Object>();
			condition.put("userId", account);
			condition.put("status", "1");
			condition.put("deleteFlag", "0");
			List<Object> list = commonService.selectByCondition(SystemAccount.class, condition);
			if(list.size()<1){
				logger.debug("not find the account:",account);
				return IDMConstant.DISABLE_RESPONSE_201;
			}
			SystemAccount systemAccount = (SystemAccount)list.get(0);
			systemAccount.setStatus("0");
			systemAccount.setUpdateTime(new Date());
			commonService.update(systemAccount);
		}catch(Exception e){
			logger.debug("disable account Exception",e);
			return IDMConstant.DISABLE_RESPONSE_202;
		}
		logger.debug("disable account \"" + account + "\" success!!");
		return IDMConstant.DISABLE_RESPONSE_200;
	}
	
	@Override
	public String enableAcount(String account) {
		try{
			Map<String ,Object> condition = new HashMap<String ,Object>();
			condition.put("userId", account);
			condition.put("status", "0");
			condition.put("deleteFlag", "0");
			List<Object> list = commonService.selectByCondition(SystemAccount.class, condition);
			if(list.size()<1){
				logger.debug("not find the account:",account);
				return IDMConstant.ENABLE_RESPONSE_301;
			}
			SystemAccount systemAccount = (SystemAccount)list.get(0);
			systemAccount.setStatus("1");
			systemAccount.setUpdateTime(new Date());
			commonService.update(systemAccount);
		}catch(Exception e){
			logger.debug("enable account Exception",e);
			return IDMConstant.ENABLE_RESPONSE_302;
		}
		logger.debug("enalble account \"" + account + "\" success!!");
		return IDMConstant.ENABLE_RESPONSE_300;
	}

	@Override
	public String modifyAccounts(String account, String username, String password, String organization) {
		try{
			Map<String ,Object> condition = new HashMap<String ,Object>();
			condition.put("userId", account);
			condition.put("deleteFlag", "0");
			List<Object> list = commonService.selectByCondition(SystemAccount.class, condition);
			if(list.size()<1){
				logger.debug("not find the account:",account);
				return IDMConstant.UPDATE_RESPONSE_101;
			}
			SystemAccount systemAccount = (SystemAccount)list.get(0);
			systemAccount.setUserName(username);
			systemAccount.setPasswd(password);
			systemAccount.setDeptCode(organization);
			systemAccount.setUpdateTime(new Date());
			commonService.update(systemAccount);
		}catch(Exception e){
			logger.debug("modify account Exception:",e);
			return IDMConstant.UPDATE_RESPONSE_102;
		}
		logger.debug("modify account \"" + account + "\" success!!");
		return IDMConstant.UPDATE_RESPONSE_100;
	}

}
