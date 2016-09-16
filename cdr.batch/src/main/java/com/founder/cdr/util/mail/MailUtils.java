package com.founder.cdr.util.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.founder.cdr.entity.MessageFailure;
import com.founder.cdr.util.PropertiesUtils;
import com.founder.cdr.util.security.DesUtils;

/**
 * 邮件帮助类
 * 
 * @author wen_ruichao
 */
public class MailUtils
{

    private static final Logger logger = LoggerFactory.getLogger(MailUtils.class);

    private static final String MAIL_ENCODE = "GB2312";

    // 邮件密码前缀
    private static final String PASSWORD_PREFIX = "PS:";

    /**
     * 发送邮件
     * 
     * @param messageFailure
     * @throws MessagingException 
     * @throws AddressException 
     */
    public static void sendMail(MessageFailure messageFailure)
            throws AddressException, MessagingException
    {
        String host = PropertiesUtils.getValue("mail.host");
        String connectiontimeout = PropertiesUtils.getValue("mail.connectiontimeout");
        String from = PropertiesUtils.getValue("mail.from");
        String username = PropertiesUtils.getValue("mail.username");
        String password = PropertiesUtils.getValue("mail.password");
        String to = PropertiesUtils.getValue("mail.to");

        if (password.startsWith(PASSWORD_PREFIX))
        {
            password = DesUtils.decode(password.substring(PASSWORD_PREFIX.length()));
        }

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.connectiontimeout", connectiontimeout);
        // smtp服务器需要身份验证
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties,
                new SimpleAuthenticator(username, password));
        // 为保证邮件服务器对tls连接支持正常，先做一个连接测试
        try
        {
            Transport transport = session.getTransport();
            transport.connect();
            transport.close();
        }
        catch (Exception e)
        {
            logger.error("邮件服务器连接测试失败：{}", e.getMessage());
            properties.put("mail.smtp.starttls.enable", "false");
            session = Session.getInstance(properties, new SimpleAuthenticator(
                    username, password));
        }
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
//        message.setSubject(getBase64String(messageFailure.getReason(),
//                MAIL_ENCODE));
        message.setContent(messageFailure.getContent(), "text/html; charset="
            + MAIL_ENCODE);
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(
                to));
        // message.setRecipients(Message.RecipientType.CC, new Address[0]);
        message.setSentDate(new Date());
        Transport.send(message);
    }

    /**
     * base64转码
     * 
     * @param content
     * @return
     * @throws UnsupportedEncodingException
     */
//    private static String getBase64String(String content, String encode)
//    {
//        if (content == null || "".endsWith(content))
//        {
//            return "";
//        }
//        sun.misc.BASE64Encoder base64Encoder = new sun.misc.BASE64Encoder();
//        String newContent = "";
//        try
//        {
//            newContent = base64Encoder.encode(content.getBytes(encode));
//            // 去除换行，解决过长的邮件标题造成的乱码问题（Base64规范要求每行最多72个字符）。
//            newContent = newContent.replaceAll("\n", "");
//        }
//        catch (UnsupportedEncodingException e)
//        {
//            logger.error("base64转码失败：{}", e.getMessage());
//        }
//        return "=?" + encode + "?B?" + newContent + "?=";
//    }
}
