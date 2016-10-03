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
import com.yly.cdr.web.idm.webservice.DisableAcountWebservice;

public class DisableAcountWebserviceImpl implements DisableAcountWebservice {

	private static final Logger logger = LoggerFactory.getLogger(DisableAcountWebserviceImpl.class);
	
	@Autowired
	CommonService commonService;
	
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
}
