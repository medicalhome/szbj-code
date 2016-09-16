package com.founder.cdr.web.idm.webservice;

import javax.jws.WebService;

@WebService
public interface EnableAcountWebservice {

	/**
	 * 启用账号
	 * @param account
	 * @return
	 */
	public String enableAcount(String account);
}
