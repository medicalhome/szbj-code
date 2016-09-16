package com.founder.cdr.web.idm.webservice.impl;

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
import com.founder.cdr.web.idm.webservice.DeleteAcountWebservice;

public class DeleteAcountWebserviceImpl implements DeleteAcountWebservice {

	private static final Logger logger = LoggerFactory.getLogger(DeleteAcountWebserviceImpl.class);
	
	@Autowired
	CommonService commonService;

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

}
