package com.yly.cdr.batch.core;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper
{
    private Properties mapper;

    public ServiceMapper()
    {
        try
        {
            Resource resource = new ClassPathResource(
                    "service_mapping.properties");
            mapper = PropertiesLoaderUtils.loadProperties(resource);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getMessageId(String serviceType)
    {
        if (mapper == null || serviceType == null)
            return null;
        return mapper.getProperty(serviceType);
    }
}
