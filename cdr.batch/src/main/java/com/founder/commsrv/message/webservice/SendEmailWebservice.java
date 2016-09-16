package com.founder.commsrv.message.webservice;

import javax.jws.WebService;

@WebService
public interface SendEmailWebservice
{

    /***
     * 
     * @param toAddress 多个邮箱以逗号分隔符连接，应用系统保证邮箱地址规范有效
     * @param content
     * @param title
     * @param ccAddress 多个邮箱以逗号分隔符连接，应用系统保证邮箱地址规范有效
     * @param bccAdress 多个邮箱以逗号分隔符连接，应用系统保证邮箱地址规范有效
     * @param fileFlow 发送单个附件，以byte[]流的方式传入
     * @param file6f4Str 发送多个附件，以64位加密字符串形式的方式传入
     * @param attachFileName 有附件情况下，附件名称不允许为空
     * @param screen 使用场景描述
     */
    public void sendEmail(String toAddress, String content, String title,
            String ccAddress, String bccAddress, Byte[] fileFlow,
            String[] file64Str, String[] attachFileName, String screen);
}
