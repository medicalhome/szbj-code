package com.yly.hl7.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtils
{

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    private static Properties SETTING_PROPERTIES = new Properties();

    static
    {
        InputStream is = null;
        try
        {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(
                    "setting.properties");
            SETTING_PROPERTIES.load(is);
            logger.debug("setting.properties配置文件加载成功");
        }
        catch (IOException e)
        {
            logger.error("初始化配置文件：setting.properties失败。 " + e);
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    logger.error("配置文件：setting.properties关闭失败。 " + e);
                }
            }
        }
    }

    private PropertiesUtils()
    {
    }

    /**
     * 从属性文件中获取键对应的值
     * 
     * @param key
     * @return value
     */
    public static String getProperty(String key)
    {
        String value = SETTING_PROPERTIES.getProperty(key);
        if (value == null)
        {
            logger.error("配置文件setting.properties中{}对应的值不存在！", key);
            throw new RuntimeException("请为配置文件setting.properties中的" + key
                + "设置对应的值");
        }
        return value;
    }
}
