package com.founder.cdr.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.founder.cdr.batch.core.ServiceMapper;

/**
 * dto约束校验类
 * 
 * @author wen_ruichao
 */
public class ValidatorUtils
{
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    // Add by jin_peng 2013.04.09
    private static ServiceMapper serviceMapper;

    static
    {
        serviceMapper = new ServiceMapper();
    }

    // End by jin_peng 2013.04.09

    private ValidatorUtils()
    {
    }

    /**
     * dto校验
     * @param <T>
     * 
     * @param t
     * @return 成功：true；失败：false。
     */
    public static <T> boolean validate(T t, Class<?> group, String messageId)
    {
        StringBuilder builder = new StringBuilder();

          if (!recursionValidate(t, group, messageId, builder))
          {
              // Add by jin_peng 2013.04.09
              String logNameValue = messageId.toUpperCase();//serviceMapper.getMessageId(messageId.toUpperCase());
    
              Logger validateLogger = null;
    
              if (!StringUtils.isEmpty(logNameValue))
              {
                  validateLogger = LoggerFactory.getLogger(logNameValue);
              }
              validateLogger.error("Message:{},validate:{}", t,builder.toString());
    
              // End by jin_peng 2013.04.09 
              return false;
          }
          else
          {
              return true;
          }
            
    }

    /**
     * 递归校验
     * @param t
     * @return
     */
    private static <T> boolean recursionValidate(T t, Class<?> group,
            String messageId, StringBuilder builder)
    {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(
                t, Default.class, group);
        if (!constraintViolations.isEmpty())
        {
            for (ConstraintViolation<T> constraintViolation : constraintViolations)
            {
                builder.append(constraintViolation.getMessage());
            }
            return false;
        }

        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            Class<?> fieldType = field.getType();
            if (List.class.isAssignableFrom(fieldType))
            { // 如果属性是List类型
              // 得到泛型类型
                Type fieldGenericType = field.getGenericType();
                if (ParameterizedType.class.isInstance(fieldGenericType))
                { // 如果是参数类型
                    ParameterizedType parameterizedType = (ParameterizedType) fieldGenericType;
                    // 泛型参数类型
                    Class<?> genericClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                    if (!String.class.isAssignableFrom(genericClass))
                    { // 泛型参数非String
                        field.setAccessible(true);
                        List<? extends T> children = null;
                        try
                        {
                            children = (List<? extends T>) field.get(t);
                        }
                        catch (IllegalArgumentException e)
                        {
                            e.printStackTrace();
                        }
                        catch (IllegalAccessException e)
                        {
                            e.printStackTrace();
                        }
                        if (children != null)
                        {
                            // 循环校验List中实例
                            for (T child : children)
                            {
                                if (!recursionValidate(child, group, messageId,  builder))
                                {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
