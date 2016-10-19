package com.founder.cdr.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * 类描述
 * Created by on 2016/10/3.
 */

public class JaxbXmlUtil {

    public static <T> T xmlToBean(String xml, Class<T> beanClass) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(beanClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        T bean = (T) unmarshaller.unmarshal(new StringReader(xml));
        return bean;
    }

    public static String beanToXml(Object obj) throws Exception {
        String result = null;
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        // 按标签自动换行，否则是一行的xml
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        // xml的编码方式
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        result = writer.toString();
        return result;
    }
}
