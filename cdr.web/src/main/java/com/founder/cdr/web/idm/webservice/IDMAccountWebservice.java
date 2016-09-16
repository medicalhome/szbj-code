package com.founder.cdr.web.idm.webservice;

public interface IDMAccountWebservice {

	/**
	 * IDM添加账户接口
	 * @param account
	 * @param username
	 * @param password
	 * @param organization
	 * @return
	 */
	public String addAcount(String acount, String username, String password, String organization);
	
	/**
	 * IDM删除账户接口
	 * @param username
	 * @return
	 */
	public String deleteAcount(String username);
	
	/**
	 * 禁用账号
	 * @param account
	 * @return
	 */
	public String disableAcount(String account);
	
	/**
	 * 启用账号
	 * @param account
	 * @return
	 */
	public String enableAcount(String account);
	
	/**
	 * IDM修改账户
	 * @param acounts
	 * @param requestID
	 * @return
	 */
	public String modifyAccounts(String account, String username, String password, String organization);
}
