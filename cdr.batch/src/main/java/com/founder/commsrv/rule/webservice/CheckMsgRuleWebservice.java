package com.founder.commsrv.rule.webservice;

import javax.jws.WebService;

@WebService
public interface CheckMsgRuleWebservice
{

    /**
     * 警告通知规则校验接口
     * @param ruleType 检验类型（ExceptionGroup：异常值，EmergencyGroup：危急值）
     * @param msg 检验源数据
     * @return 检验结果数据
     */
    public String checkMsg(String ruleType, String msg);

}
