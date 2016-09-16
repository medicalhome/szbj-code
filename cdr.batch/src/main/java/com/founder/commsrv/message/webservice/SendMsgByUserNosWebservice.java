package com.founder.commsrv.message.webservice;

import javax.jws.WebService;

@WebService
public interface SendMsgByUserNosWebservice
{

    /***
     *  发送短信接口
     * @param employeeId 员工编号
     * @param content 短信内容
     * @param sysId 系统ID
     * @param businessId 场景ID
     * @return
     */
    public int sendMessageByUserNos(String employeeId, String content,
            String sysId, String businessId);
}
