package com.founder.cdr.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapToBean {
	
    private static final String[] DATE_PATTERN = new String[] {
        "yyyyMMddHHmmss", "yyyyMMdd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" };
    private static final Logger logger = LoggerFactory.getLogger(MapToBean.class);
/** 
     * 将map转换成Javabean 
     * 
     * @param javabean javaBean 
     * @param data map数据 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
     */ 
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object toJavaBean(Object javabean, Map<String, Object> data) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException 
    {
    	Field[] fields = javabean.getClass().getDeclaredFields();
    	for(Field field : fields){// 遍历判断是否存在List类型变量
    		boolean isList = List.class.isAssignableFrom(field.getType());
    		String attri = field.getName();
    		boolean a = field.isAccessible();
			field.setAccessible(true);
    		if(isList){
    			List<Object> itemList = new ArrayList<Object>();
    			Type fc = field.getGenericType();
    			if(fc == null) continue;  
    			if(fc instanceof ParameterizedType){// 判断泛型参数
    				ParameterizedType pt = (ParameterizedType) fc;
                	Class genericClazz = (Class)pt.getActualTypeArguments()[0];
                	List<Map> list = (List<Map>) data.get(attri);
                	if(null != list && list.size() > 0) {
                		if(String.class.isAssignableFrom(genericClazz)) {
                        	for(Map map:list){
                        		Iterator i=map.entrySet().iterator();
                        		while(i.hasNext()){
                        			Map.Entry e=(Map.Entry)i.next();
                        			itemList.add(e.getValue());
                        			//System.out.println(e.getKey()+"="+e.getValue());
                        		}
                    		}                 			
                		}else if(Date.class.isAssignableFrom(genericClazz)) {
                        	for(Map map:list){
                        		Iterator i=map.entrySet().iterator();
                        		while(i.hasNext()){
                        			Map.Entry e=(Map.Entry)i.next();
                        			if(null != e.getValue()) {
                            			itemList.add(DateUtils.parseDate(e.getValue().toString(), DATE_PATTERN));                        				
                        			}
                        		}
                    		}                			
                		}else if(Integer.class.isAssignableFrom(genericClazz)) {
                        	for(Map map:list){
                        		Iterator i=map.entrySet().iterator();
                        		while(i.hasNext()){
                        			Map.Entry e=(Map.Entry)i.next();
                        			if(null != e.getValue()) {
										itemList.add(Integer.valueOf(e.getValue().toString()));
                        			}
                        		}
                    		}                			
                		}else {
                        	for(Map map:list){
                    			Object obj = genericClazz.newInstance();// List的泛型实例化，如：List<Test>,Test类实例化
                        		itemList.add(toJavaBean(obj,map));
                    		}                			
                		}
                	}
    			}
    			field.set(javabean, itemList);
    		}else{
    			if(Integer.class.isAssignableFrom(field.getType())) {
    				if(null != data.get(attri)) {
            			field.set(javabean, Integer.valueOf(data.get(attri).toString()));     					
    				}
    			}else if(Date.class.isAssignableFrom(field.getType())) {
    				if(null != data.get(attri)) {
            			field.set(javabean, DateUtils.parseDate(data.get(attri).toString(), DATE_PATTERN));     					
    				}    				
    			}else {
    				field.set(javabean, data.get(attri));   
    			}

    		}
    		field.setAccessible(a);
    	}
    	return javabean;
    } 
}
