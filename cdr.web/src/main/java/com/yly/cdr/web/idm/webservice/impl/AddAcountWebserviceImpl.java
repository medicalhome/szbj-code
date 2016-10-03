package com.yly.cdr.web.idm.webservice.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yly.cdr.entity.SystemAccount;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.web.idm.IDMConstant;
import com.yly.cdr.web.idm.webservice.AddAcountWebservice;

public class AddAcountWebserviceImpl implements AddAcountWebservice {
	
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
	
}
