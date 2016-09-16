package com.founder.cdr.util;

import java.util.Map;

import com.founder.cdr.cache.DictionaryCache;
/**
 * @author yu_yzh
 * */
public class DictionaryUtils {
	/**
	 * @param domain 字典对应的CS_CODE
	 * @param id 编码值
	 * @param defaultName 消息中值或默认值，若不为空优先返回该值，否则返回字典中的值
	 * @return 优先返回消息中的值或默认值，若消息中无值则返回字典中的值
	 * */
	public static String getNameFromDictionary(String domain, String id, String defaultName){
		if(!StringUtils.isEmpty(defaultName) || id == null || domain == null) return defaultName;
		else {
			String name = null;
			Map<String, String> dic = DictionaryCache.getDictionary(domain);
			name = dic == null ? null : dic.get(id);
			name = name == null ? defaultName : name;
			return name;
		}
	}
	
	/**
	 * @param domain 字典对应的CS_CODE
	 * @param id 编码值	 
	 * @return 返回字典中取的值，若取不到值，则返回null
	 * */
	public static String getNameFromDictionary(String domain, String id){
		return getNameFromDictionary(domain, id, null);
	}
}
