package com.yly.commsrv.rule.webservice;

import javax.jws.WebService;

@WebService
public interface RuleCheckWebservice
{

    /**
     * 警告通知规则校验接口
     * @param msg 检验源数据
     * @return 检验结果数据
     */
    public String ruleCheck(String msg);

}
