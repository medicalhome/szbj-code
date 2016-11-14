package com.yly.cdr.web.idm.webservice.impl;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yly.cdr.web.idm.webservice.SZBJGetPushXmlResultWebservice;

public class SZBJGetPushXmlResultWebserviceImpl implements SZBJGetPushXmlResultWebservice {
	
	private static final Logger logger = LoggerFactory.getLogger(SZBJGetPushXmlResultWebserviceImpl.class);
	
	@Override
	/**
	 * 读取指定路径的V2XML
	 */
	public String getXmlResult(String xmlContent) {
		ClassLoader classLoader = getClass().getClassLoader();
		String xmlJson = "szbjMessages/omg_o19_p/omg_o19_p.xml";
		String result = "";
		try {
			result = loadFile(classLoader.getResource(xmlJson));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private static String loadFile(URL url) throws Exception {
        if (url == null)
            return null;
        InputStream is = url.openStream();
        if (is == null) {
            return null;
        } else {
            String result = loadStream(is);
            is.close();
            return result;
        }
    }
	
	private static String loadStream(InputStream is) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(is);
        InputStreamReader isr = new InputStreamReader(bis, "UTF-8");
        char buffer[] = new char[4096];
        StringBuilder sb = new StringBuilder();
        int size = 0;
        do {
            size = isr.read(buffer);
            if (size >= 0) {
                sb.append(buffer, 0, size);
            } else {
                isr.close();
                bis.close();
                return sb.toString();
            }
        } while (true);
    }
	
}
