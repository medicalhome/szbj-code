package com.founder.cdr.util;

import java.util.Random;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class ResetPwUtil {

	public static String md5(String passwd) {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		// false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true
		// 表示：生成24位的Base64版
		md5.setEncodeHashAsBase64(false);
		String pwd = md5.encodePassword(passwd, null);
		return pwd;
	}

	public static String sha_256(String passwd) {
		ShaPasswordEncoder sha = new ShaPasswordEncoder(256);
		sha.setEncodeHashAsBase64(false);
		String pwd = sha.encodePassword(passwd, null);
		return pwd;
	}

	public static String sha_SHA_256(String passwd) {
		ShaPasswordEncoder sha = new ShaPasswordEncoder();
		sha.setEncodeHashAsBase64(false);
		String pwd = sha.encodePassword(passwd, null);
		return pwd;
	}

	public static String md5_SystemWideSaltSource(String userName,String passwd) {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		md5.setEncodeHashAsBase64(false);

		// 使用动态加密盐的只需要在注册用户的时候将第二个参数换成用户名即可
		String pwd = md5.encodePassword(userName, passwd);
		return pwd;
	}
	
	/**
	 * 生成随即密码
	 * 
	 * @param pwd_len
	 *            生成的密码的总长度
	 * @return 密码的字符串
	 */
	public static String genRandomNum() {
		int pwd_len = 6;
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String userName = "scott";
		String passwd = "123456";
		
		String md5 = md5(passwd);
		System.out.println("MD5: " + md5 + " len=" + md5.length());
		
		String sha_256 = sha_256(passwd);
		System.out.println("哈希算法 256: " + sha_256 + " len=" + sha_256.length());
		
		String sha_SHA_256 = sha_SHA_256(passwd);
		System.out.println("哈希算法 SHA-256: " + sha_SHA_256 + " len=" + sha_SHA_256.length());
		
		String md5_SystemWideSaltSource = md5_SystemWideSaltSource(userName,passwd);
		System.out.println("MD5 SystemWideSaltSource: " + md5_SystemWideSaltSource + " len="
				+ md5_SystemWideSaltSource.length());
		
		String randomPasswd = genRandomNum();
		System.out.println("随机密码："+randomPasswd);
	}

}
