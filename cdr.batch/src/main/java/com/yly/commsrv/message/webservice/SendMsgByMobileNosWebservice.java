package com.yly.commsrv.message.webservice;

import javax.jws.WebService;

@WebService
public interface SendMsgByMobileNosWebservice
{

    /***
     *  发送短信接口
     * @param mobile 手机号
     * @param content 短信内容
     * @param sysId 系统ID
     * @param businessId 场景ID
     * @return
     */
    public int sendMessageByMobileNos(String mobile, String content,
            String sysId, String businessId);
}
