package com.founder.cdr.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.founder.cdr.web.util.EscapeUtils;

public class EscapeHttpServletRequestWrapper extends HttpServletRequestWrapper {

	public EscapeHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String[] getParameterValues(String name){
		String[] values = super.getParameterValues(name);
		if(values == null) return null;
		
		String[] afterEscape = new String[values.length];
		for(int i = 0; i < values.length; i++){
			afterEscape[i] = escape(values[i]);
		}
		return afterEscape;
	}
	
	@Override
	public String getParameter(String name){
		String value = super.getParameter(name);
		if(value == null) return null;
		else {
			return escape(value);
		}
	}
	
	@Override
	public String getHeader(String name){
		String value = super.getHeader(name);
		if(value == null) return null;
		else {
			return escape(value);
		}
	}
	
	private String escape(String in){
		if(in == null) return null;
		String out = in;
		out = EscapeUtils.escapeHtml(out);
		out = EscapeUtils.escapeJS(out);
		out = EscapeUtils.escapeSql(out);
		return out;
	}
}
