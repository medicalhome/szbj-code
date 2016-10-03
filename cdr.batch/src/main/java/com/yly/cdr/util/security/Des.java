package com.yly.cdr.util.security;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author wen_ruichao
 */
public class Des
{

    private static final Logger logger = LoggerFactory.getLogger(Des.class);

    public static int _DES = 1;

    public static int _DESede = 2;

    public static int _Blowfish = 3;

    private Cipher p_Cipher;

    private SecretKey p_Key;

    private String p_Algorithm;

    /**
     * 初始化
     * @param algorithm
     * @throws Exception
     */
    public Des(int algorithm, String key)
    {
        selectAlgorithm(algorithm);
        try
        {
            this.p_Key = new SecretKeySpec(hex2byte(key), p_Algorithm);
            this.p_Cipher = Cipher.getInstance(p_Algorithm);
        }
        catch (NoSuchAlgorithmException e)
        {
            logger.error("算法初始化失败：{}", e.getMessage());
        }
        catch (NoSuchPaddingException e)
        {
            logger.error("算法初始化失败：{}", e.getMessage());
        }
    }

    /**
     * 选择加密算法
     * @param algorithm
     */
    private void selectAlgorithm(int algorithm)
    {
        switch (algorithm)
        {
        default:
        case 1:
            this.p_Algorithm = "DES";
            break;
        case 2:
            this.p_Algorithm = "DESede";
            break;
        case 3:
            this.p_Algorithm = "Blowfish";
            break;
        }
    }

    /**
     * 对data数进行加密，如果没有密钥，则新生成一个密钥.
     * 
     * @param plaintext
     * @return
     * @throws Exception
     */
    public String encode(byte[] plaintext) throws Exception
    {
        p_Cipher.init(Cipher.ENCRYPT_MODE, p_Key);
        return byte2hex(p_Cipher.doFinal(plaintext));
    }

    /**
     * 用enckey密钥对encdate进行解密
     * 
     * @param ciphertext
     * @param enckey
     * @return
     * @throws Exception
     */
    public byte[] decode(String ciphertext) throws Exception
    {
        p_Cipher.init(Cipher.DECRYPT_MODE, p_Key);
        return p_Cipher.doFinal(hex2byte(ciphertext));
    }

    /**
     * 生成字节数组的16进表示字符串
     *
     * @param b
     * @return
     */
    private String byte2hex(byte[] b)
    {
        String hs = "";
        String stmp = "";
        for (int i = 0; i < b.length; i++)
        {
            stmp = Integer.toHexString(b[i] & 0xFF);
            if (stmp.length() == 1)
                hs += "0" + stmp;
            else
                hs += stmp;
        }
        return hs.toLowerCase();
    }

    /**
     * 从16进制的表示字符串得到二字节数组
     * 
     * @param hex
     * @return
     * @throws IllegalArgumentException
     */
    private byte[] hex2byte(String hex) throws IllegalArgumentException
    {
        if (hex.length() % 2 != 0)
        {
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++)
        {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }
}
