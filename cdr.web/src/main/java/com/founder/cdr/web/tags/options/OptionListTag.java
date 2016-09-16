package com.founder.cdr.web.tags.options;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OptionListTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -329834029977375176L;
	
    private static final Logger logger = LoggerFactory.getLogger(OptionListTag.class);
    
    private List<String> values;
    
    private String selected;
	
    public int doStartTag() throws JspException{

    	String result = createOptionList();
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
    
    private String createOptionList(){
    	if(values == null){
    		logger.warn("下列框数据源对象为空");
    		return "";
    	}
    	StringBuffer sb = new StringBuffer();
    	Iterator<String> iterator = values.iterator();
    	while(iterator.hasNext()){
    		String entry = iterator.next();
    		sb.append("<option value=\"").append(entry).append("\"");
            if (selected != null && selected.equals(entry))
            {
                sb.append(" selected=\"selected\"");
            }
            sb.append(">").append(entry).append("</option>\r\n");
    	}
     	return sb.toString();
    }
    
    public void setValues(List<String> values){
    	this.values = values;
    }
    
    public void setSelected(String selected){
    	this.selected = selected;
    }
}
