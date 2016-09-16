package com.founder.cdr.core;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.io.support.PropertiesLoaderSupport;

public class AppSettings extends PropertiesLoaderSupport implements
        BeanFactoryPostProcessor, Ordered
{
    private int order = Ordered.LOWEST_PRECEDENCE; // default: same as
                                                   // non-Ordered

    private static Properties props = null;

    public void setOrder(int order)
    {
        this.order = order;
    }

    @Override
    public int getOrder()
    {
        return order;
    }

    @Override
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) throws BeansException
    {
        try
        {
            props = mergeProperties();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new FatalBeanException("加载设定文件失败！");
        }
    }

    public static String getConfig(String key)
    {
        if (props != null)
            return props.getProperty(key).trim();
        else
            return null;
    }

    public Integer getConfigNumber(String key)
    {
        if (props == null || !props.containsKey(key))
            return null;

        String value = props.getProperty(key).trim();
        return Integer.valueOf(value);
    }
}
