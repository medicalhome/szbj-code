package com.yly.cdr.web.idm.webservice.impl;

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
import com.yly.cdr.web.idm.webservice.EnableAcountWebservice;

public class EnableAcountWebserviceImpl implements EnableAcountWebservice {

	private static final Logger logger = LoggerFactory.getLogger(EnableAcountWebserviceImpl.class);
	
	@Autowired
	CommonService commonService;
	
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

}
