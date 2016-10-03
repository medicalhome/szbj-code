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
import com.yly.cdr.web.idm.webservice.ModifyAcountWebservice;

public class ModifyAcountWebserviceImpl implements ModifyAcountWebservice {

	private static final Logger logger = LoggerFactory.getLogger(ModifyAcountWebserviceImpl.class);
	
	@Autowired
	CommonService commonService;
	
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
