package com.founder.cdr.batch.core;

import java.util.Properties;

public class Config {
	private static Properties prop = new Properties();
	
	public static void setConfig(String key, String value)
	{
		prop.setProperty(key, value);
	}
	
	public static String getConfig(String key)
	{
		return prop.getProperty(key);
	}
}
