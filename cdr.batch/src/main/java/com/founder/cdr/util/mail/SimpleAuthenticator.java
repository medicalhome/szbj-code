package com.founder.cdr.util.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 继承javaMail的认证器，在构造mail的Session时使用。
 * @author wen_ruichao
 */
public class SimpleAuthenticator extends Authenticator
{
    String username;

    String password;

    public SimpleAuthenticator(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(username, password);
    }
}
