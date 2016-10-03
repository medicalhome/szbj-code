package com.yly.cdr.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置属性文件帮助类，支持动态修改属性值。
 * 
 */
// $Author :wu_biao
// $Date : 2012/10/10 14:44$
// [BUG]9807 ADD BEGIN
public class PropertiesUtils
{

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    private static final String BATCH_DYNIMIC_CONFIG_FILE = "setting.properties";

    private static PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();

    // 毫秒计时，默认一分钟
    private static long refreshDelay = 60000;

    static
    {
        try
        {
            init();
            logger.debug("{}加载成功。", BATCH_DYNIMIC_CONFIG_FILE);
        }
        catch (ConfigurationException e)
        {
            logger.error("初始化配置文件：{}失败。 ", BATCH_DYNIMIC_CONFIG_FILE);
            e.printStackTrace();
        }
    }

    private PropertiesUtils()
    {
    }

    private static void init() throws ConfigurationException
    {
        propertiesConfiguration.load(Thread.currentThread().getContextClassLoader().getResource(
                BATCH_DYNIMIC_CONFIG_FILE).getFile());
        FileChangedReloadingStrategy fileChangedReloadingStrategy = new FileChangedReloadingStrategy();
        fileChangedReloadingStrategy.setRefreshDelay(refreshDelay);
        propertiesConfiguration.setReloadingStrategy(fileChangedReloadingStrategy);
    }

    /**
     * 从属性文件中获取键对应的值
     * 
     * @param key
     * @return value
     */
    public static String getValue(String key)
    {
        String value = propertiesConfiguration.getString(key);
        if (value == null)
        {
            logger.error("{}中{}对应的值不存在!", BATCH_DYNIMIC_CONFIG_FILE, key);
        }
        return value;
    }
}
// [BUG]9807 ADD END
