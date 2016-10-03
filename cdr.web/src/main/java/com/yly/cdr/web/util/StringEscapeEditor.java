package com.yly.cdr.web.util;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringEscapeUtils;


/**
 * @author yu_yzh
 * 过滤页面输入中的SQL注入，JS注入，XSS
 * */
public class StringEscapeEditor extends PropertyEditorSupport {
	private boolean escapeSQL;
	private boolean escapeHTML;
	private boolean escapeJS;
	
	public StringEscapeEditor(){
		super();
	}
	
	public StringEscapeEditor(boolean escapeSQL, boolean escapeHTML, boolean escapeJS){
		super();
		this.escapeSQL = escapeSQL;
		this.escapeHTML = escapeHTML;
		this.escapeJS = escapeJS;
	}
	
	@Override
	public void setAsText(String text){
		if(text == null){
			setValue(null);
		} else {
			String value = text.trim();
			if(escapeSQL){
				value = EscapeUtils.escapeSql(value);
			}
			if(escapeHTML){
				value = EscapeUtils.escapeHtml(value);
			}
			if(escapeJS){
				value = EscapeUtils.escapeJS(value);
			}
			setValue(value);
		}
	}
	
	@Override
	public String getAsText(){
		Object value = getValue();
		return value == null ? "" : value.toString();
	}
}
