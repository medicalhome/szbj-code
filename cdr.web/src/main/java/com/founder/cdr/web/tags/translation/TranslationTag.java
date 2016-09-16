package com.founder.cdr.web.tags.translation;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.founder.cdr.cache.DictionaryCache;

/**
 * 翻译标签
 * @author wen_ruichao
 * @version 1.0
 * @see
 * @since
 */
public class TranslationTag extends TagSupport
{

    private static final long serialVersionUID = -4779856402566801968L;

    private static final Logger logger = LoggerFactory.getLogger(TranslationTag.class);

    /**
     * 域
     */
    private String domain;

    /**
     * 值
     */
    private String code;

    public void setDomain(String domain)
    {
        this.domain = domain;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    /**
     * 处理开始标签
     */
    public int doStartTag() throws JspException
    {
        String target = translate();
        logger.trace("Translate domain {}, code {} to {}", new Object[] {
                domain, code, target });
        try
        {
            pageContext.getOut().write(target);
        }
        catch (IOException e)
        {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    /**
     * 翻译
     */
    private String translate()
    {
        Map<String, String> map = DictionaryCache.getDictionary(domain);
        if (map == null)
            return null;
        else
            return map.get(code);
    }
}
