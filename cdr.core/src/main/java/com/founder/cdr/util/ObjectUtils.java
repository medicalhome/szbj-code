package com.founder.cdr.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class ObjectUtils
{
    /**
     * 给传入对象私有属性赋值(多个)
     * @param object 待操作属性所在对象
     * @param map 属性名和该属性值集合
     */
    public static void setObjectPrivateFields(Object object,
            Map<String, Object> map)
    {
        Field field = null;

        if (object != null && map != null && !map.isEmpty())
        {
            try
            {
                Set<Entry<String, Object>> entrySet = map.entrySet();
                Iterator<Entry<String, Object>> entryIterator = entrySet.iterator();

                while (entryIterator.hasNext())
                {
                    Entry<String, Object> entry = entryIterator.next();

                    // 通过map中的属性名给该所在对象中的对应属性赋值
                    field = object.getClass().getDeclaredField(entry.getKey());
                    field.setAccessible(true);
                    field.set(object, entry.getValue());
                }
            }
            catch (IllegalArgumentException e)
            {
                throw new RuntimeException(e);
            }
            catch (SecurityException e)
            {
                throw new RuntimeException(e);
            }
            catch (IllegalAccessException e)
            {
                throw new RuntimeException(e);
            }
            catch (NoSuchFieldException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 给传入对象私有属性赋值(多个)
     * @param objectList 待操作属性所在对象集合
     * @param map 属性名和该属性值集合
     */
    public static void setObjectListPrivateFields(List<Object> objectList,
            Map<String, Object> map)
    {
        Field field = null;

        if (objectList != null && !objectList.isEmpty() && map != null
            && !map.isEmpty())
        {
            try
            {
                Set<Entry<String, Object>> entrySet = map.entrySet();
                Iterator<Entry<String, Object>> entryIterator = entrySet.iterator();

                for (Object object : objectList)
                {
                    while (entryIterator.hasNext())
                    {
                        Entry<String, Object> entry = entryIterator.next();

                        // 通过map中的属性名给该所在对象中的对应属性赋值
                        field = object.getClass().getDeclaredField(
                                entry.getKey());
                        field.setAccessible(true);
                        field.set(object, entry.getValue());
                    }
                }
            }
            catch (IllegalArgumentException e)
            {
                throw new RuntimeException(e);
            }
            catch (SecurityException e)
            {
                throw new RuntimeException(e);
            }
            catch (IllegalAccessException e)
            {
                throw new RuntimeException(e);
            }
            catch (NoSuchFieldException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 获取传入对象私有属性值(多个)
     * @param obj 待获取属性所在对象
     * @param strings 待获取值的属性名集合
     * @param 已获取到的key和属性值
     */
    public static Map<String, Object> getObjectPrivateFields(Object object,
            String... strings)
    {
        Map<String, Object> resMap = null;
        Field field = null;

        if (object != null && strings != null && strings.length != 0)
        {
            try
            {
                resMap = new HashMap<String, Object>();

                for (int i = 0; i < strings.length; i++)
                {
                    // 通过strings中的值当属性名获取该所在对象中的对应属性值
                    field = object.getClass().getDeclaredField(strings[i]);
                    field.setAccessible(true);

                    // 将传入参数中的值做返回结果resMap的key，获取的属性值做value添加到返回结果resMap中
                    resMap.put(strings[i], field.get(object));
                }
            }
            catch (IllegalArgumentException e)
            {
                throw new RuntimeException(e);
            }
            catch (SecurityException e)
            {
                throw new RuntimeException(e);
            }
            catch (IllegalAccessException e)
            {
                throw new RuntimeException(e);
            }
            catch (NoSuchFieldException e)
            {
                throw new RuntimeException(e);
            }
        }

        return resMap;
    }

    /**
     * 判断两个数值的大小
     * @param str1 待比较的数值1(字符串形式)
     * @param str2 待比较的数值2(字符串形式)
     * @return 比较结果标识 1为前者比后者大；-1为后者比前者大；0为两者相等
     */
    public static int compare(String str1, String str2)
    {
        long l1 = StringUtils.strToLong(str1);
        long l2 = StringUtils.strToLong(str2);

        if (l1 > l2)
        {
            return 1;
        }
        else if (l1 < l2)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
    
    // $Author :jin_peng
    // $Date : 2012/12/20 16:52$
    // [BUG]0012702 ADD BEGIN
    /**
     * 判断传入的list是否为空
     * @param argsList 待验证的集合
     * @return 是否为空
     */
    public static boolean isNullList(List<?> argsList)
    {
        boolean isNull = true;
        
        if(argsList != null && !argsList.isEmpty())
        {
            isNull = false;
        }
        
        return isNull;
    }
    
    // [BUG]0012702 ADD END
}
