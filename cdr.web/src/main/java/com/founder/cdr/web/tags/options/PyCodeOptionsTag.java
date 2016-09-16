package com.founder.cdr.web.tags.options;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.founder.cdr.cache.DictionaryCache;
import com.founder.cdr.dto.DictionaryDto;
import com.founder.cdr.dto.KeyValueDto;
import com.founder.cdr.util.StringUtils;

/**
 * 下拉列表标签
 * @author wen_ruichao
 * @version 1.0
 */
@Component
public class PyCodeOptionsTag extends TagSupport
{

    private static final long serialVersionUID = 3656766185796017503L;

    private static final Logger logger = LoggerFactory.getLogger(PyCodeOptionsTag.class);

    /**
     * 域
     */
    private String domain;

    /**
     * 设置选中的option
     */
    private String value;

    private List<KeyValueDto> source;

    private List<String> more;
    
    private List<String> deptIds;

    public void setDeptIds(List<String> deptIds) {
		this.deptIds = deptIds;
	}

	public void setDomain(String domain)
    {
        this.domain = domain;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public void setSource(List<KeyValueDto> source)
    {
        this.source = source;
    }

    public void setMore(List<String> more)
    {
        this.more = more;
    }

    /**
     * 处理开始标签
     */
    public int doStartTag() throws JspException
    {
        String result = "";
        if (source == null)
            result = getResultFromDomain();
        else
            result = getResultFromSource();
        try
        {
            pageContext.getOut().write(result);
        }
        catch (IOException e)
        {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    /**
     * 下拉列表
     */
    private String getResultFromDomain()
    {
        StringBuffer sb = new StringBuffer();
        Map<String, DictionaryDto> map = DictionaryCache.getFullDictionary(domain);
        
        if(domain.equals("MS025_LIMIT")){
        	Map<String, DictionaryDto> deptMap = DictionaryCache.getFullDictionary("MS025");
        	Collection<DictionaryDto> list = deptMap.values();
        	for (DictionaryDto item : list)
            {
                String key = item.getCvCode();
                String pyCode = item.getPyCode();
                if (pyCode == null)
                    pyCode = "";               
                
                for(String deptid : deptIds){
                	if (key.equals(deptid))
                    {
                        sb.append("<option style=\"z-index:99999;\" value=\"").append(key).append("\"");
                        sb.append(" searchtext=\"").append(pyCode).append("\"");
                        if (value != null && value.equals(key))
                        {
                            sb.append(" selected=\"selected\"");
                        }
                        sb.append(">").append(item.getCvValue()).append("</option>\r\n");
                    }
                }
            }
        	logger.debug("select domain {}", domain);
        	return sb.toString();
        }
        
        // $Author:wu_jianfeng
        // $Date : 2013/3/15 14:10
        // $[BUG]0014593 ADD BEGIN
        if (map == null)
        {
            logger.warn("没有找到字典。");
            return "";
        }
        // $[BUG]0014593 ADD END

        Collection<DictionaryDto> list = map.values();

        if (list == null || list.size() == 0)
        {
            logger.warn("下列框数据源对象为空。");
            return "";
        }

        for (DictionaryDto item : list)
        {
            String key = item.getCvCode();

            String pyCode = item.getPyCode();
            if (pyCode == null)
                pyCode = "";
            // $Author: tong_meng
            // $Date : 2013/05/23 14:40
            // $[BUG]0014990 MODIFY BEGIN
            if (!domain.equals("MS028"))
            {
                sb.append("<option style=\"z-index:99999;\" value=\"").append(key).append("\"");
                sb.append(" searchtext=\"").append(pyCode).append("\"");
                if (value != null && value.equals(key))
                {
                    sb.append(" selected=\"selected\"");
                }
                sb.append(">").append(item.getCvValue()).append("</option>\r\n");
            }            
            else 
            {
                if (!split(key,sb))
                {   
                    sb.append("<option value=\"").append(key).append("\"");
                    sb.append(" searchtext=\"").append(pyCode).append("\"");
                    if (value != null && value.equals(key))
                    {
                        sb.append(" selected=\"selected\"");
                    }
                    /*CodeDrugDto codeDrugDto = (CodeDrugDto)item;*/
                    sb.append(">").append(item.getCvValue()).append("</option>\r\n");
                }
            }
            // $[BUG]0014990 MODIFY END
        }
        logger.debug("select domain {}", domain);
        return sb.toString();
    }

    
    // $Author: tong_meng
    // $Date : 2013/05/23 14:40
    // $[BUG]0014990 MODIFY BEGIN
    /**
     * 判断缓存中是否已经存在该药品名称
     * @param key
     * @param sb
     * @return
     */
    private boolean split(String key, StringBuffer sb)
    {
        String subKey = key.substring(0, key.length()-2);
        // 存在，返回true
        if (sb.indexOf(subKey) > -1)
        {
            return true;
        }
        return false;
    }
    // $[BUG]0014990 MODIFY END
    
    /**
     * 下拉列表
     */
    private String getResultFromSource()
    {
        StringBuffer sb = new StringBuffer();
        if (source == null || source.size() == 0)
        {
            logger.warn("下列框数据源对象为空。");
            return "";
        }

        Map<String, DictionaryDto> map = DictionaryCache.getFullDictionary(domain);
        Collection<DictionaryDto> list = map.values();

        for (KeyValueDto item : source)
        {
            String key = item.getValue();
            String text = item.getText();

            if (!StringUtils.isEmpty(key))
            {
                if (StringUtils.isEmpty(text))
                    text = "";

                String pyCode = "";
                DictionaryDto codeValue = map.get(key);
                if (codeValue != null)
                {
                    pyCode = codeValue.getPyCode();
                    if (StringUtils.isEmpty(pyCode))
                        pyCode = "";
                }

                sb.append("<option value=\"").append(key).append("\"");
                sb.append(" searchtext=\"").append(pyCode).append("\"");
                if (value != null && value.equals(key))
                {
                    sb.append(" selected=\"selected\"");
                }
                else
                {
                    if (more != null && more.size() != 0)
                    {
                        for (String value : more)
                        {
                            if (value.equals(key))
                            {
                                sb.append(" selected=\"selected\"");
                                break;
                            }
                        }
                    }
                }
                sb.append(">").append(text).append("</option>\r\n");
            }
        }

        return sb.toString();
    }

    /**
     * 拿到药品中的规格
     * @param key
     * @return
     *//*
    private String getSpecification(String key)
    {
        String drugCode = null;
        String serialNo = null;
        if (!StringUtils.isEmpty(key) && key.length() > 2)
        {
            // 查找隐藏药物表中的信息
            serialNo = key.substring(key.length() - 2, key.length());
            drugCode = key.substring(0, key.length() - 2);
        }
        CodeDrug cd = getCodeDrug(drugCode, serialNo);
        if (cd != null)
        {
            return cd.getSpecification();
        }
        return null;
    }

    *//**
     * 
     * @param drugCode
     * @param serialNo
     * @return
     *//*
    private CodeDrug getCodeDrug(String drugCode, String serialNo)
    {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("code", drugCode);
        condition.put("serialNo", serialNo);
        List<Object> result = commonService.selectByCondition(CodeDrug.class,
                condition);
        if (result != null && !result.isEmpty())
        {
            return (CodeDrug) result.get(0);
        }
        return null;
    }*/

}
