package com.founder.hl7.maphandler;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于读取json配置。
 * 
 * @author wgy
 *
 */
public class FileJsonMapHandler
{
    private static final String FILE_SUFFIX = ".json";

    private String messageId;

    private static Map<String, String> cache = new HashMap<String,String>();

    public String getJsonContent()
    {
        if (cache.containsKey(messageId.toLowerCase()))
        {
            return cache.get(messageId.toLowerCase());
        }

        String map = null;
        try
        {
            map = loadMapping();
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
    private synchronized String loadMapping()
            throws Exception
    {
        String result = null;
        ClassLoader classLoader = getClass().getClassLoader();
        String fileName = messageId.toLowerCase() + FILE_SUFFIX;
        String cacheKey = messageId.toLowerCase();
        /** End BUG#9223 */

        if (cache.containsKey(cacheKey))
            return cache.get(cacheKey);

        result = loadFile(classLoader.getResource("V2Message/"+messageId.toLowerCase() + "/" + fileName));
        
        cache.put(cacheKey, result);

        return result;
    }

    public void setMessageId(String messageId)
    {
        this.messageId = messageId;
    }
    
	private static String loadFile(URL url)
			throws Exception
		{
		if (url == null)
			return null;
		InputStream is = url.openStream();
		if (is == null)
		{
			return null;
		} else
		{
			String result = loadStream(is);
			is.close();
			return result;
		}
		}
	
	private static String loadStream(InputStream is)
			throws Exception
		{
			BufferedInputStream bis = new BufferedInputStream(is);
			InputStreamReader isr = new InputStreamReader(bis, "UTF-8");
			char buffer[] = new char[4096];
			StringBuilder sb = new StringBuilder();
			int size = 0;
			do
			{
				size = isr.read(buffer);
				if (size >= 0)
				{
					sb.append(buffer, 0, size);
				} else
				{
					isr.close();
					bis.close();
					return sb.toString();
				}
			} while (true);
		}
}
