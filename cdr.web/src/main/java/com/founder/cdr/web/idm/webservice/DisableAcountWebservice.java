package com.founder.cdr.web.idm.webservice;

import javax.jws.WebService;

@WebService
public interface DisableAcountWebservice {

	/**
	 * 禁用账号
	 * @param account
	 * @return
	 */
	public String disableAcount(String account);
}
