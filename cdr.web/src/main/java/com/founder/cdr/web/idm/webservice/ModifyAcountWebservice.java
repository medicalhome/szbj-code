package com.founder.cdr.web.idm.webservice;

public interface ModifyAcountWebservice {

	/**
	 * IDM修改账户
	 * @param acounts
	 * @param requestID
	 * @return
	 */
	public String modifyAccounts(String account, String username, String password, String organization);
}
