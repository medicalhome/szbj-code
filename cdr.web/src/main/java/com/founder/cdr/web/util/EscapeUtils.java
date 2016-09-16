package com.founder.cdr.web.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringEscapeUtils;

public class EscapeUtils {
	
	private static final String[] escapeStrings = {"select ", "update ", "delete ",
		"exec ", "count ", "or ", "and ", "union ", "'", "\"", "=", ";", ">", "<",
		"%", "--", "#", "&", "/", 
		/*"select|update|delete|exec|count|or |an |union |'|\"|=|;|>|<|%|--|#|&|/"*/};
	public static String escapeSql(String before){
		String after = before;
		try {
			after = java.net.URLDecoder.decode(before, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String s : escapeStrings){
			after = after.replaceAll(s, "");
		}
		return after;
	}
	
	public static String escapeHtml(String before){
		return StringEscapeUtils.escapeHtml4(before);
	}
	
	public static String escapeJS(String before){
		return StringEscapeUtils.escapeEcmaScript(before);
	}
}
