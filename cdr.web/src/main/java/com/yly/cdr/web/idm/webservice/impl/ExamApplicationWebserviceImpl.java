package com.yly.cdr.web.idm.webservice.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yly.cdr.service.CommonService;
import com.yly.cdr.web.idm.webservice.ExamApplicationWebservice;

public class ExamApplicationWebserviceImpl implements ExamApplicationWebservice {
	
	private static final Logger logger = LoggerFactory.getLogger(ExamApplicationWebserviceImpl.class);

	@Autowired
	CommonService commonService;

	@Override
	public String examApplicationQuery(String conditionXml) {
		try{
			
		}catch(Exception e){
			
		}
		return null;
	}
	
}
