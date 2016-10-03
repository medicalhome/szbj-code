package com.yly.cdr.web.tags.options;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yly.cdr.cache.DictionaryCache;
import com.yly.cdr.core.Constants;

/**
 * 下拉列表标签
 * @author wen_ruichao
 * @version 1.0
 */
public class OptionsTag extends TagSupport
{

    private static final long serialVersionUID = 3656766185796017503L;

    private static final Logger logger = LoggerFactory.getLogger(OptionsTag.class);

    private static final String USE_TITLE = "true";

    /**
     * 域
     */
    private String domain;

    /**
     * 设置选中的option
     */
    private String value;

    /**
     * 设置title 
     */
    private String title;

    public void setDomain(String domain)
    {
        this.domain = domain;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * 处理开始标签
     */
    public int doStartTag() throws JspException
    {
        String result = options();
        logger.debug("select domain {}", domain);
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
    private String options()
    {
        StringBuffer sb = new StringBuffer();
        Map<String, String> data = DictionaryCache.getDictionary(domain);
        if (data == null)
        {
            logger.warn("下列框数据源对象为空。");
            return "";
        }
        // $Author:wei_peng
        // $Date:2012/10/23 17:19
        // $[BUG]0010674 ADD BEGIN
        if (Constants.AGE_GROUP.equals(domain))
        {
            TreeMap<String, String> ageMap = new TreeMap<String, String>(
                    new Comparator()
                    {

                        @Override
                        public int compare(Object o1, Object o2)
                        {
                            return Integer.parseInt((String) o1)
                                - Integer.parseInt((String) o2);
                        }

                    });
            ageMap.putAll(data);
            data = ageMap;
        }
        // $[BUG]0010674 ADD END

        Iterator<Entry<String, String>> it = data.entrySet().iterator();
        for (; it.hasNext();)
        {
            Entry<String, String> entry = it.next();
            String key = entry.getKey();
            sb.append("<option value=\"").append(key).append("\"");
            if (title != null && USE_TITLE.equals(title))
            {
                sb.append(" title=\"").append(entry.getValue()).append("\"");
            }
            if (value != null && value.equals(key))
            {
                sb.append(" selected=\"selected\"");
            }
            sb.append(">").append(entry.getValue()).append("</option>\r\n");
        }
        return sb.toString();
    }
}
