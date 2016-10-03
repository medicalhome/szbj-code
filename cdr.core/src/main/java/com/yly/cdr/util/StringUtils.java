package com.yly.cdr.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.founder.fasf.core.exception.SystemException;
import com.yly.cdr.core.Constants;
import com.yly.cdr.dto.OrderStepDto;

public class StringUtils
{
    private static final Object EMPTY_STRING = "";

    // 声明值域转换map
    private static final Map<Object, String> conversionMap;

    /**
     * 构造值域转换map
     */
    static
    {
        conversionMap = new HashMap<Object, String>();

        // 消息或CDA中true对应CDR中相应字段值1
        conversionMap.put(Constants.TRUE_FLAG, Constants.T_FLAG_VALUE);

        // 消息或CDA中false对应CDR中相应字段值0
        conversionMap.put(Constants.FALSE_FLAG, Constants.F_FLAG_VALUE);
    }

    /**
     * 通过消息中传来的值转换成CDR中需要的值
     * @param valueOfMessage 消息中传来的需转换值
     * @return 转换后的值
     */
    public static String getConversionValue(Object valueOfMessage)
    {
        return conversionMap.get(valueOfMessage);
    }

    /**
     * 判断一个字符串是否是空
     * 
     * @param value
     * @return
     */
    public static boolean isEmpty(String value)
    {
        if (value == null || EMPTY_STRING.equals(value))
            return true;
        else
            return false;
    }

    /**
     * 将给定字符串中的${}包围的变量使用给定的映射表替换
     * 
     * @param template 需要替换的原始字符串
     * @param prop 用于替换的映射列表
     * @return 替换后的字符串
     */
    public static String evaluate(String template, Properties prop)
    {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
        Matcher matcher = pattern.matcher(template);

        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (matcher.find())
        {
            String replacement = (String) prop.get(matcher.group(1));
            builder.append(template.substring(i, matcher.start()));
            if (replacement == null)
                builder.append(matcher.group(0));
            else
                builder.append(replacement);
            i = matcher.end();
        }
        builder.append(template.substring(i, template.length()));
        return builder.toString();
    }

    /**
     * 类型转换(字符串转换小数型)
     * @param 需要转换的字符串
     * @return 转换完成的小数
     */
    public static BigDecimal strToBigDecimal(String str, String pe)
    {
        BigDecimal bigDecimal = null;
        try
        {
            if (!StringUtils.isEmpty(str))
            {
                bigDecimal = new BigDecimal(str);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new SystemException(pe + "=" + str + "字符串转换小数型error");
        }
        return bigDecimal;
    }

    /**
     * 类型转换(字符串转换长整型)
     * @param 需要转换的字符串
     * @return 转换完成的整数
     */
    public static long strToLong(String str)
    {
        long l = 0;

        if (!StringUtils.isEmpty(str))
        {
            l = Long.parseLong(str);
        }

        return l;
    }

    /**
     * 验证多个字符串是否都不为空
     * @param strings 字符串集合
     * @return 是否都不为空标识
     */
    public static boolean isNotNullAll(String... strings)
    {
        boolean isNotNullAll = true;

        if (strings == null)
        {
            return false;
        }

        for (int i = 0; i < strings.length; i++)
        {
            if (StringUtils.isEmpty(strings[i]))
            {
                isNotNullAll = false;
                break;
            }
        }

        return isNotNullAll;
    }

    /**
     * 验证多个字符串是否都为空
     * @param strings 字符串集合
     * @return 是否都为空标识
     */
    public static boolean isNullAll(String... strings)
    {
        boolean isNullAll = true;

        if (strings == null)
        {
            return true;
        }

        for (int i = 0; i < strings.length; i++)
        {
            if (!StringUtils.isEmpty(strings[i]))
            {
                isNullAll = false;
                break;
            }
        }

        return isNullAll;
    }

    // $Author :jin_peng
    // $Date : 2012/9/25 14:46$
    // [BUG]0009863 MODIFY BEGIN
    /**
     * 将BigDecimal转换成String
     * @param csId
     * @return
     */
    public static String BigDecimalToStr(BigDecimal csId)
    {
        String str = null;
        try
        {
            if (csId != null)
            {
                str = csId.toString();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return str;

    }

    // [BUG]0009863 MODIFY END

    /**
     * 将对象为null并转换成字符串形式情况时，返回空
     * @param str 待转换字符串
     * @return 返回的空值
     */
    public static String nullToEmpty(String str)
    {
        if ("null".equals(str))
        {
            str = "";
        }

        return str;
    }

    /**
     * 将对象不为空的字符串转化成double
     * @param version 带转化的字符串
     * @return
     */
    public static Double strToDouble(String version)
    {
        Double str = null;
        try
        {
            if (version != null)
            {
                str = new Double(version);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return str;
    }

    /**
     * 判断字符串是否为数字
     * @param number
     * @return
     */
    public static boolean isNumber(String number)
    {
        if (number.matches("\\d+.?\\d*"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // $Author :tong_meng
    // $Date : 2013/1/7 11:06$
    // [BUG]0012698 ADD BEGIN
    public static String compareItemValue(String highValue, String value,
            String lowValue)
    {
        /* String resultHighLowFlagStr = "error非数值"; */
        double highValueDouble = 0;
        double valueDouble = 0;
        double lowValueDouble = 0;
        try
        {
            // 检验值为空或者上限值和下限值都为空时显示检验值
            if (StringUtils.isEmpty(value)
                || (StringUtils.isEmpty(highValue) && StringUtils.isEmpty(lowValue)))
            {
                return "normal";
            }
            // 检验值上限为空下限不为空时只用检验值比较下限值
            if (StringUtils.isEmpty(highValue)
                && !StringUtils.isEmpty(lowValue))
            {
                valueDouble = Double.parseDouble(value);
                lowValueDouble = Double.parseDouble(lowValue);
                if (valueDouble < lowValueDouble)
                {
                    return "low";
                }
                else
                {
                    return "normal";
                }
            }
            // 检验值上限不为空下限为空时只用检验值比较上限值
            if (!StringUtils.isEmpty(highValue)
                && StringUtils.isEmpty(lowValue))
            {
                valueDouble = Double.parseDouble(value);
                highValueDouble = Double.parseDouble(highValue);
                if (valueDouble > highValueDouble)
                {
                    return "high";
                }
                else
                {
                    return "normal";
                }
            }
            // 检验值上限和下限值都不为空时用检验值比较上限下限值
            if (!StringUtils.isEmpty(highValue)
                && !StringUtils.isEmpty(lowValue))
            {
                valueDouble = Double.parseDouble(value);
                lowValueDouble = Double.parseDouble(lowValue);
                highValueDouble = Double.parseDouble(highValue);
                if (valueDouble > highValueDouble)
                {
                    return "high";
                }
                else if (valueDouble < lowValueDouble)
                {
                    return "low";
                }
                else
                {
                    return "normal";
                }
            }
            else
            {
                return "normal";
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return "normal";
        }
        /* return resultHighLowFlagStr; */
    }

    // [BUG]0012698 ADD END

    // Author:wei_peng
    // Date:2013/02/28 14:00
    // [BUG]0012739 ADD BEGIN
    // Author:jia_yanqing
    // Date:2012/12/21 16:10
    // [BUG]0012739 ADD BEGIN
    /**
     * 将部分字段中的\x000d\替换为</br>
     * @param replaceContent
     * @return
     */
    public static String replaceStr(String replaceContent)
    {
        if (replaceContent != null && !"".equals(replaceContent))
        {
            replaceContent = replaceContent.replace("\\x000d\\", "</br>");
        }
        return replaceContent;
    }

    // [BUG]0012739 ADD END
    // [BUG]0012739 ADD END

    public static List<String> getJsonStrings(List<OrderStepDto> orderStepDtos)
    {
        List<String> jsonStrings = new ArrayList<String>();
        for (OrderStepDto orderStepDto : orderStepDtos)
        {
            StringBuilder jsonString = new StringBuilder();
            jsonString.append("{\"code\":\""
                + orderStepDto.getOrderStatusCode()
                + "\",\"name\":\""
                + orderStepDto.getOrderStatusName()
                + "\",\"time\":\""
                + DateUtils.dateToString(orderStepDto.getExecuteTime(),
                        "yyyy-MM-dd HH:mm") + "\",\"person\":\""
                + orderStepDto.getExecutePersonName() + "\",\"dept\":\""
                + orderStepDto.getExecuteDeptName() + "\"}");
            if (orderStepDto.getStatusContent() != null)
                jsonString = jsonString.insert(
                        jsonString.length() - 1,
                        ",\"statusContent\":\""
                            + orderStepDto.getStatusContent() + "\"");
            jsonStrings.add(jsonString.toString());
        }
        return jsonStrings;
    }

    // $Author:tong_meng
    // $Date : 2013/11/07 10:10
    // $[BUG]0039034 ADD BEGIN
    /**
     * 
     * @param objects 要格式化的带有【searchString】的字符串的集合
     * @param text 要格式化的带有【searchString】的DB字段
     * @param searchString 要替换的字符串 
     * @param replacement 替换的字符串
     * @return
     * @throws NoSuchFieldException 
     * @throws SecurityException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public static List<?> cutString(List<?> objects, String text,
            String searchString, String replacement) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException
    {
        // 操作实体集合不能为空

        if (objects == null || objects.isEmpty())
        {
            return null;
        }
        // 要操作的实体字段不能为空，要替换的字符串不能为空
        if (StringUtils.isEmpty(text) || StringUtils.isEmpty(searchString))
        {
            return null;
        }
        if (StringUtils.isEmpty(replacement))
        {
            replacement = "";
        }
        for (Object object : objects)
        {
            if (object == null)
            {
                break;
            }

            Field field = object.getClass().getDeclaredField(text);
            if (field == null)
            {
                break;
            }
            field.setAccessible(true);
            String str = (String) field.get(object);
            if (!StringUtils.isEmpty(str))
            {
                String replaceString = str.replaceAll(searchString, replacement);
                field.set(object, replaceString);
            }
        }
        return objects;
    }

    // $[BUG]0039034 ADD END

    public static String getChs(String str)
    {
        if (isEmpty(str))
        {
            return str;
        }

        Pattern pattern = Pattern.compile("[\u4E00-\u9FA5]*");
        Matcher matc = pattern.matcher(str);
        StringBuffer stb = new StringBuffer();
        while (matc.find())
        {
            stb.append(matc.group());
        }

        return stb.toString();
    }

    public static boolean contains(List<String> targetList, String targetStr)
    {
        boolean isExisted = false;

        if (targetList != null && !targetList.isEmpty())
        {
            isExisted = targetList.contains(targetStr);
        }

        return isExisted;
    }
}
