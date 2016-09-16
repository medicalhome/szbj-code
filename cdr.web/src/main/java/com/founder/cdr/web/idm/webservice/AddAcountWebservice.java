package com.founder.cdr.web.idm.webservice;

import javax.jws.WebService;

@WebService
public interface AddAcountWebservice {

	/**
	 * IDM添加账户接口
	 * @param account
	 * @param username
	 * @param password
	 * @param organization
	 * @return
	 */
	public String addAcount(String acount, String username, String password, String organization);
}
