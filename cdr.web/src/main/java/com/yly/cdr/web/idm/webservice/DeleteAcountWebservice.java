package com.yly.cdr.web.idm.webservice;

import javax.jws.WebService;


@WebService
public interface DeleteAcountWebservice {

	/**
	 * IDM删除账户接口
	 * @param username
	 * @return
	 */
	public String deleteAcount(String username);
}
