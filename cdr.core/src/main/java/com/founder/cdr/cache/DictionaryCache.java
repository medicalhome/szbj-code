package com.founder.cdr.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.founder.cdr.dao.DictionaryDao;
import com.founder.cdr.dto.DictionaryDto;

/**
 * 数据字典缓存。支持定时更新功能。
 */
public class DictionaryCache
{

    private static final Logger logger = LoggerFactory.getLogger(DictionaryCache.class);

    private static Map<String, TreeMap<String, String>> dictionaries = Collections.synchronizedMap(new HashMap<String, TreeMap<String, String>>());

    private static Map<String, TreeMap<String, DictionaryDto>> fullDictionaries = Collections.synchronizedMap(new HashMap<String, TreeMap<String, DictionaryDto>>());

    private DictionaryCache()
    {
    }

    @Autowired
    private DictionaryDao dictionaryDao;

    private String csId;

    /**
     * CDR Portal字典缓存初始化。
     */
    public void init()
    {
        try
        {
            logger.debug("CDR Portal字典缓存，正在初始化...");
            cache();
            logger.debug("CDR Portal字典缓存，初始化成功。");
        }
        catch (Exception e)
        {
            logger.error("CDR Portal字典缓存，初始化失败！");
            e.printStackTrace();
        }
    }

    /**
     * CDR Portal字典缓存更新。
     */
    public void update(String csId) throws Exception
    {
        this.csId = csId;
        cache();
    }

    /**
     * 初始化数据字典缓存
     */
    public void cache() throws Exception
    {
        logger.debug("正在查询字典...");
        List<DictionaryDto> dictionaryDtos = new ArrayList<DictionaryDto>();
        if (csId == null)
        {
            dictionaryDtos = dictionaryDao.selectDictionaries();
        }
        else if (!"MS026".equals(csId) && !"MS002".equals(csId)
            && !"MS003".equals(csId) && !"MS040".equals(csId)
            && !"MS039".equals(csId) && !"MS033".equals(csId)
            && !"MS025".equals(csId) && !"MS028".equals(csId)
            && !"MS024".equals(csId) && !"MS053".equals(csId)
            && !"MS064".equals(csId))
        {
            dictionaryDtos = dictionaryDao.selectDictionariesByCodeValuePara(csId);
        }
        else
        {
            dictionaryDtos = dictionaryDao.selectDictionariesBypara(csId);
        }

        logger.debug("正在缓存字典...");
        TreeMap<String, String> data = null;
        TreeMap<String, DictionaryDto> fullData = null;
        for (Iterator<DictionaryDto> iterator = dictionaryDtos.iterator(); iterator.hasNext();)
        {
            DictionaryDto dictionaryDto = iterator.next();
            String csCode = dictionaryDto.getCsCode();
            if (dictionaries.containsKey(csCode))
            {
                dictionaries.get(csCode).put(dictionaryDto.getCvCode(),
                        dictionaryDto.getCvValue());
                fullDictionaries.get(csCode).put(dictionaryDto.getCvCode(),
                        dictionaryDto);
            }
            else
            {
                data = new TreeMap<String, String>();
                data.put(dictionaryDto.getCvCode(), dictionaryDto.getCvValue());
                dictionaries.put(csCode, data);

                fullData = new TreeMap<String, DictionaryDto>();
                fullData.put(dictionaryDto.getCvCode(), dictionaryDto);
                fullDictionaries.put(csCode, fullData);
            }
        }
    }

    /**
     * 获取某个domian字典信息，内容不可修改！
     * 
     * @param domain
     * @return
     */
    public static Map<String, String> getDictionary(String domain)
    {
        TreeMap<String, String> map = dictionaries.get(domain);
        if (map == null)
        {
            return null;
        }
        else
        {
            return Collections.unmodifiableMap(map);
        }
    }

    /**
     * 获取某个domian字典信息，内容不可修改！
     * 
     * @param domain
     * @return
     */
    public static Map<String, DictionaryDto> getFullDictionary(String domain)
    {
        TreeMap<String, DictionaryDto> map = fullDictionaries.get(domain);
        if (map == null)
        {
            return null;
        }
        else
        {
            return Collections.unmodifiableMap(map);
        }
    }

    /**
     * 获取某个domian字典信息克隆
     * 
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> getDictionaryClone(String domain)
    {
        TreeMap<String, String> map = dictionaries.get(domain);
        if (map == null)
        {
            return null;
        }
        else
        {
            return (Map<String, String>) map.clone();
        }
    }

    public String getCsId()
    {
        return csId;
    }

    public void setCsId(String csId)
    {
        this.csId = csId;
    }

}
