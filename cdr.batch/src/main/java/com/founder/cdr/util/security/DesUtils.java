package com.founder.cdr.util.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

/**
 * 
 * @author wen_ruichao
 */
public class DesUtils
{

    private static final Logger logger = LoggerFactory.getLogger(DesUtils.class);

    private static Des des = new Des(Des._DES, "4649898545621457");

    /**
     * 加密
     * @param plaintext
     * @return
     * @throws DataAccessException
     */
    public static String encode(String plaintext)
    {
        String ciphertext = null;
        try
        {
            ciphertext = des.encode(plaintext.getBytes());
        }
        catch (Exception e)
        {
            logger.error("加密失败：{}", e.getMessage());
            return plaintext;
        }
        return ciphertext;
    }

    /**
     * 解密
     * @param ciphertext
     * @return
     */
    public static String decode(String ciphertext)
    {
        byte[] plaintext = null;
        try
        {
            plaintext = des.decode(ciphertext);
        }
        catch (Exception e)
        {
            logger.error("解密失败：{}", e.getMessage());
            return ciphertext;
        }
        return new String(plaintext);
    }

    public static void main(String[] args)
    {
        System.out.println(encode("cdrbatch"));
        System.out.println(decode(encode("cdrbatch")));
    }
}
