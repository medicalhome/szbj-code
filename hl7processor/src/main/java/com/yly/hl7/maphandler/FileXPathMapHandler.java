package com.yly.hl7.maphandler;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 用于处理从配置文件（Properties文件）中读取DTO与XML节点/属性的映射配置。
 * 
 * @author xu_dengfeng
 *
 */
public class FileXPathMapHandler implements XPathMapHandler
{
    private static final String CONFIG_SUFFIX_CHILDTYPE = ".childtype";

    private static final String CONFIG_SUFFIX_XPATH = ".xpath";

    private static final String FILE_SUFFIX = ".properties";

    private String messageId;

    private static Map<String, Map<String, Field>> cache = new HashMap<String, Map<String, Field>>();

    private static Map<String, Map<Field, Class<?>>> childtype = new HashMap<String, Map<Field, Class<?>>>();

    private ClassLoader classLoader = null;

    public ClassLoader getClassLoader()
    {
        return classLoader;
    }

    /**
     * 设定用来加载子类型的类加载器
     * 
     * @param classLoader
     */
    public void setClassLoader(ClassLoader classLoader)
    {
        this.classLoader = classLoader;
    }

    @Override
    public Map<String, Field> getXPathMap(Class<?> clazz)
    {
        /** Modified for BUG#9223 by XuDengfeng, 2012/08/30 */
        String dtoName = clazz.getSimpleName();
        if (cache.containsKey(messageId.toLowerCase() + "/" + dtoName))
        {
            return cache.get(messageId.toLowerCase() + "/" + dtoName);
        }
        /** End BUG#9223 */

        Map<String, Field> map = null;
        try
        {
            map = loadMapping(clazz);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 从与给定类同名的Properties文件中加载XPath的设定内容，并放到缓存中。
     * 
     * @param clazz 要加载设定内容的类
     * @return
     * @throws Exception
     */
    private synchronized Map<String, Field> loadMapping(Class<?> clazz)
            throws Exception
    {
        Map<String, Field> map = new HashMap<String, Field>();
        Map<Field, Class<?>> type = new HashMap<Field, Class<?>>();
        /** Modified for BUG#9223 by XuDengfeng, 2012/08/30 */
        String dtoName = clazz.getSimpleName();
        String fileName = dtoName + FILE_SUFFIX;
        String cacheKey = messageId.toLowerCase() + "/" + dtoName;
        /** End BUG#9223 */

        if (cache.containsKey(cacheKey))
            return cache.get(cacheKey);

        Properties properties = PropertiesLoaderUtils.loadAllProperties(messageId.toLowerCase()
            + File.separator + fileName);

        Enumeration<Object> en = properties.keys();
        String key = null;
        String value = null;
        Field field = null;
        while (en.hasMoreElements())
        {
            key = (String) en.nextElement();
            // 属性-节点映射的XPath
            if (key.endsWith(CONFIG_SUFFIX_XPATH))
            {
                value = properties.getProperty(key);
                key = key.substring(0, key.length() - 6);
                field = clazz.getDeclaredField(key);
                field.setAccessible(true);
                map.put(value, field);
            }
            // 嵌套类的子类型
            else if (key.endsWith(CONFIG_SUFFIX_CHILDTYPE))
            {
                value = properties.getProperty(key);
                key = key.substring(0, key.length() - 10);
                field = clazz.getDeclaredField(key);
                type.put(
                        field,
                        Thread.currentThread().getContextClassLoader().loadClass(
                                value));
            }
        }
        /** Modified for BUG#9223 by XuDengfeng, 2012/08/30 */
        cache.put(cacheKey, map);
        childtype.put(cacheKey, type);
        /** End BUG#9223 */

        return map;
    }

    @Override
    public Class<?> getXPathMapChildType(Field field)
    {
        /** Modified for BUG#9223 by XuDengfeng, 2012/08/30 */
        String typeCacheKey = messageId.toLowerCase() + "/"
            + field.getDeclaringClass().getSimpleName();
        Map<Field, Class<?>> map = childtype.get(typeCacheKey);
        /** End BUG#9223 */
        if (map == null)
            return null;
        else
            return map.get(field);
    }

    @Override
    public void setMessageId(String messageId)
    {
        this.messageId = messageId;
    }
}
