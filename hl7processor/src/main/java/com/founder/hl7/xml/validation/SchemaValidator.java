package com.founder.hl7.xml.validation;

import java.io.File;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息结构校验类
 * @author wen_ruichao
 */
public class SchemaValidator
{

    private static final Logger logger = LoggerFactory.getLogger(SchemaValidator.class);

    public static boolean validate(String xmlName, String xsdName)
    {
        return validate(xmlName, new File(xsdName));
    }

    public static boolean validate(String xmlName, File xsdFileName)
    {
        try
        {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(xsdFileName);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlName));
            return true;
        }
        catch (Exception e)
        {
            logger.error("validate error: {}", e.getMessage());
            return false;
        }
    }

    public static boolean validate(InputStream xmlStream, InputStream xsdStream)
    {
        try
        {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new StreamSource(xsdStream));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlStream));
            return true;
        }
        catch (Exception e)
        {
            logger.error("validate error: {}", e.getMessage());
            return false;
        }
    }
}
