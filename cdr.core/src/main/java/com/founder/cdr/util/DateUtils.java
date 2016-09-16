package com.founder.cdr.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import com.founder.fasf.core.exception.SystemException;

public class DateUtils
{
    /**
     * Number of milliseconds in a standard day.
     */
    public static final long MILLIS_PER_DAY = 86400000L;

    /**
     * Number of milliseconds in a standard hour.
     */
    public static final long MILLIS_PER_HOUR = 3600000L;

    /**
     * Number of milliseconds in a standard minute.
     */
    public static final long MILLIS_PER_MINUTE = 60000L;

    /**
     * Number of milliseconds in a standard second.
     */
    public static final long MILLIS_PER_SECOND = 1000L;

    /**
     * A month range, the week starting on Monday.
     */
    public static final int RANGE_MONTH_MONDAY = 6;

    /**
     * A month range, the week starting on Sunday.
     */
    public static final int RANGE_MONTH_SUNDAY = 5;

    /**
     * A week range, centered around the day focused.
     */
    public static final int RANGE_WEEK_CENTER = 4;

    /**
     * A week range, starting on Monday.
     */
    public static final int RANGE_WEEK_MONDAY = 2;

    /**
     * A week range, starting on the day focused.
     */
    public static final int RANGE_WEEK_RELATIVE = 3;

    /**
     * A week range, starting on Sunday.
     */
    public static final int RANGE_WEEK_SUNDAY = 1;

    /**
     * This is half a month, so this represents whether a date is in the top or bottom half of the month.
     */
    public static final int SEMI_MONTH = 1001;

    /**
     * The UTC time zone (often referred to as GMT).
     */
    public static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("GMT");

    public static final String PATTERN_COMPACT_DATE = "yyyyMMdd";

    public static final String PATTERN_COMPACT_DATETIME_TOHOUR = "yyyyMMddHH";

    public static final String PATTERN_COMPACT_DATETIME_TOMINUTE = "yyyyMMddHHmm";

    public static final String PATTERN_COMPACT_DATETIME = "yyyyMMddHHmmss";

    public static final String PATTERN_COMPACT_FULL = "yyyyMMddHHmmssSSS";

    public static final String PATTERN_SLASH_DATE = "yyyy/MM/dd";

    public static final String PATTERN_SLASH_DATETIME = "yyyy/MM/dd HH:mm:ss";

    public static final String PATTERN_SLASH_FULL = "yyyy/MM/dd HH:mm:ss.SSS";

    public static final String PATTERN_MINUS_DATE = "yyyy-MM-dd";

    public static final String PATTERN_MINUS_DATETIME = "yyyy-MM-dd HH:mm:ss";

    public static final String PATTERN_MINUS_FULL = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * Adds a number of days to a date returning a new object.
     * @param date
     * @param amount
     * @return
     */
    public static Date addDays(Date date, int amount)
    {
        return null;
    }

    /**
     * Adds a number of hours to a date returning a new object.
     * @param date
     * @param amount
     * @return
     */
    public static Date addHours(Date date, int amount)
    {
        return null;
    }

    /**
     * Adds a number of milliseconds to a date returning a new object.
     * @param date
     * @param amount
     * @return
     */
    public static Date addMilliseconds(Date date, int amount)
    {
        return null;
    }

    /**
     * Adds a number of minutes to a date returning a new object.
     * @param date
     * @param amount
     * @return
     */
    public static Date addMinutes(Date date, int amount)
    {
        return null;
    }

    /**
     * Adds a number of months to a date returning a new object.
     * @param date
     * @param amount
     * @return
     */
    public static Date addMonths(Date date, int amount)
    {
        return null;
    }

    /**
     * Adds a number of seconds to a date returning a new object.
     * @param date
     * @param amount
     * @return
     */
    public static Date addSeconds(Date date, int amount)
    {
        return null;
    }

    /**
     * Adds a number of weeks to a date returning a new object.
     * @param date
     * @param amount
     * @return
     */
    public static Date addWeeks(Date date, int amount)
    {
        return null;
    }

    /**
     * Adds a number of years to a date returning a new object.
     * @param date
     * @param amount
     * @return
     */
    public static Date addYears(Date date, int amount)
    {
        return null;
    }

    /**
     * Returns the number of days within the fragment.
     * @param calendar
     * @param fragment
     * @return
     */
    public static long getFragmentInDays(Calendar calendar, int fragment)
    {
        return -1;
    }

    /**
     * Returns the number of days within the fragment.
     * @param date
     * @param fragment
     * @return
     */
    public static long getFragmentInDays(Date date, int fragment)
    {
        return -1;
    }

    /**
     * Returns the number of hours within the fragment.
     * @param calendar
     * @param fragment
     * @return
     */
    public static long getFragmentInHours(Calendar calendar, int fragment)
    {
        return -1;
    }

    /**
     * Returns the number of hours within the fragment.
     * @param date
     * @param fragment
     * @return
     */
    public static long getFragmentInHours(Date date, int fragment)
    {
        return -1;
    }

    /**
     * Returns the number of milliseconds within the fragment.
     * @param calendar
     * @param fragment
     * @return
     */
    public static long getFragmentInMilliseconds(Calendar calendar, int fragment)
    {
        return -1;
    }

    /**
     * Returns the number of milliseconds within the fragment.
     * @param date
     * @param fragment
     * @return
     */
    public static long getFragmentInMilliseconds(Date date, int fragment)
    {
        return -1;
    }

    /**
     * Returns the number of minutes within the fragment.
     * @param calendar
     * @param fragment
     * @return
     */
    public static long getFragmentInMinutes(Calendar calendar, int fragment)
    {
        return -1;
    }

    /**
     * Returns the number of minutes within the fragment.
     * @param date
     * @param fragment
     * @return
     */
    public static long getFragmentInMinutes(Date date, int fragment)
    {
        return -1;
    }

    /**
     * Returns the number of seconds within the fragment.
     * @param calendar
     * @param fragment
     * @return
     */
    public static long getFragmentInSeconds(Calendar calendar, int fragment)
    {
        return -1;
    }

    /**
     * Returns the number of seconds within the fragment.
     * @param date
     * @param fragment
     * @return
     */
    public static long getFragmentInSeconds(Date date, int fragment)
    {
        return -1;
    }

    /**
     * Checks if two calendar objects are on the same day ignoring time.
     * @param cal1
     * @param cal2
     * @return
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2)
    {
        return false;
    }

    /**
     * Checks if two date objects are on the same day ignoring time.
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2)
    {
        String dateStr1 = dateToString(date1, PATTERN_COMPACT_DATE);
        String dateStr2 = dateToString(date2, PATTERN_COMPACT_DATE);
        return dateStr1.equals(dateStr2);
    }

    /**
     * Checks if two calendar objects represent the same instant in time.
     * @param cal1
     * @param cal2
     * @return
     */
    public static boolean isSameInstant(Calendar cal1, Calendar cal2)
    {
        return false;
    }

    /**
     * Checks if two date objects represent the same instant in time.
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameInstant(Date date1, Date date2)
    {
        return false;
    }

    /**
     * Checks if two calendar objects represent the same local time.
     * @param cal1
     * @param cal2
     * @return
     */
    public static boolean isSameLocalTime(Calendar cal1, Calendar cal2)
    {
        return false;
    }

    /**
     * This constructs an Iterator over each day in a date range defined by a focus date and range style.
     * @param focus
     * @param rangeStyle
     * @return
     */
    public static Iterator<Integer> iterator(Calendar focus, int rangeStyle)
    {
        return null;
    }

    /**
     * This constructs an Iterator over each day in a date range defined by a focus date and range style.
     * @param focus
     * @param rangeStyle
     * @return
     */
    public static Iterator<Integer> iterator(Date focus, int rangeStyle)
    {
        return null;
    }

    /**
     * This constructs an Iterator over each day in a date range defined by a focus date and range style.
     * @param focus
     * @param rangeStyle
     * @return
     */
    public static Iterator<Integer> iterator(java.lang.Object focus,
            int rangeStyle)
    {
        return null;
    }

    /**
     * Parses a string representing a date by trying a variety of different parsers.
     * @param str
     * @param parsePatterns
     * @return
     */
    public static Date parseDate(java.lang.String str,
            java.lang.String[] parsePatterns)
    {
        return null;
    }

    /**
     * Round this date, leaving the field specified as the most significant field.
     * @param date
     * @param field
     * @return
     */
    public static Calendar round(Calendar date, int field)
    {
        return null;
    }

    /**
     * Round this date, leaving the field specified as the most significant field.
     * @param date
     * @param field
     * @return
     */
    public static Date round(Date date, int field)
    {
        return null;
    }

    /**
     * Round this date, leaving the field specified as the most significant field.
     * @param date
     * @param field
     * @return
     */
    public static Date round(java.lang.Object date, int field)
    {
        return null;
    }

    /**
     * Sets the day of month field to a date returning a new object.
     * @param date
     * @param amount
     * @return
     */
    public static Date setDays(Date date, int amount)
    {
        return null;
    }

    /**
     * Sets the hours field to a date returning a new object.
     * @param date
     * @param amount
     * @return
     */
    public static Date setHours(Date date, int amount)
    {
        return null;
    }

    /**
     * Sets the miliseconds field to a date returning a new object.
     * @param date
     * @param amount
     * @return
     */
    public static Date setMilliseconds(Date date, int amount)
    {
        return null;
    }

    /**
     * Sets the minute field to a date returning a new object.
     * @param date
     * @param amount
     * @return
     */
    public static Date setMinutes(Date date, int amount)
    {
        return null;
    }

    /**
     * Sets the months field to a date returning a new object.
     * @param date
     * @param amount
     * @return
     */
    public static Date setMonths(Date date, int amount)
    {
        return null;
    }

    /**
     * Sets the seconds field to a date returning a new object.
     * @param date
     * @param amount
     * @return
     */
    public static Date setSeconds(Date date, int amount)
    {
        return null;
    }

    /**
     * Sets the years field to a date returning a new object.
     * @param date
     * @param amount
     * @return
     */
    public static Date setYears(Date date, int amount)
    {
        return null;
    }

    /**
     * Truncate this date, leaving the field specified as the most significant field.
     * @param date
     * @param field
     * @return
     */
    public static Calendar truncate(Calendar date, int field)
    {
        return null;
    }

    /**
     * Truncate this date, leaving the field specified as the most significant field.
     * @param date
     * @param field
     * @return
     */
    public static Date truncate(Date date, int field)
    {
        return null;
    }

    /**
     * Truncate this date, leaving the field specified as the most significant field.
     * @param date
     * @param field
     * @return
     */
    public static Date truncate(java.lang.Object date, int field)
    {
        return null;
    }

    /**
     * calculate the age based on birthdate of patient
     * @param date
     * @return age
     */
    public static int caluAgeYears(Date birthDate)
    {
        int age = 0;

        if (birthDate != null)
        {

            Calendar birthDay = Calendar.getInstance();
            birthDay.setTime(birthDate);
            Calendar today = Calendar.getInstance();

            if (today.get(Calendar.YEAR) > birthDay.get(Calendar.YEAR))
            {
                age = today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR)
                    - 1;
                if (today.get(Calendar.MONTH) + 1 > birthDay.get(Calendar.MONTH))
                {
                    age++;
                }
                else if (today.get(Calendar.MONTH) + 1 == birthDay.get(Calendar.MONTH))
                {
                    if (today.get(Calendar.DAY_OF_MONTH) > birthDay.get(Calendar.DAY_OF_MONTH))
                    {
                        age++;
                    }
                }
            }
        }
        return age;
    }

    /**
     * calculate the age based on birthdate of patient
     * @param birthDate 出生日期
     * @return age
     * 指患者的实足年龄，为患者出生后按照日历计算的历法年龄。年龄满1周岁的，以实足年龄的相应整数填写；
     * 年龄不足1周岁的，按照实足年龄的月龄填写，以分数形式表示：分数的整数部分代表实足月龄，分数部分分母为30，
     * 分子为不足1个月的天数，如“2 15/30月”代表患儿实足年龄为2个月又15天。
     */
    public static String caluAge(Date birthDate, Date visitDate)
    {
        if (birthDate == null)
        {
            return "";
        }
        else if (visitDate == null)
        {
            visitDate = new Date();
        }

        int years = getYearsBetween(birthDate, visitDate);
        // 如果已满岁，按实足年龄计算
        if (years >= 1)
            return years + "岁";
        // 否则，按实足月龄计算
        else
        {
            // 计算出生日期到现在的日期天数
            int days = getDaysBetween(birthDate, visitDate);
            int months = days / 30;
            days = days % 30;
            String s = months > 0 ? (months + "月" + days + "天") : (days + "天"); 
            return s;
        }

    }

    /**
     * 计算两个相隔的天数
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer getDaysBetween(Date startDate, Date endDate)
    {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        long days = (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime())
            / (1000 * 60 * 60 * 24);
        if (days < 0)
            return 0;
        else
            return (int) days;
    }

    /**
     * 根据月份，年，得到该月份的天数
     * @param month
     * @param year
     * @return
     */
    public static int getDays(int month, int year)
    {
        int days = 0;

        switch (month)
        {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            days = 31;
            break;
        case 2:
            // 闰年天，平年天
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                days = 29;
            else
                days = 28;
            break;
        case 4:
        case 6:
        case 9:
        case 11:
            days = 30;
            break;
        }

        return days;
    }

    /**
     * 计算两个相隔的年数
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer getYearsBetween(Date startDate, Date endDate)
    {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        long years = (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime())
            / (1000 * 60 * 60 * 24) / 365;
        if (years < 0)
            return 0;
        else
            return (int) years;
    }

    /**
         * 类型转换(字符串转换日期型)
         * @param 需要转换的字符串
         * @param formatStr 需要格式的目标字符串  举例 yyyyMMdd
         * @return 转换完成的日期
         */
    public static Date stringToDate(String str, String formatStyle)
    {
        Date date = null;
        SimpleDateFormat sdf = null;
        try
        {
            if (!StringUtils.isEmpty(str))
            {
                sdf = new SimpleDateFormat(formatStyle);
                date = sdf.parse(str);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return date;
    }

    /**
     * 类型转换(日期型转换字符型)
     * @param targetDate 需要转换的日期
     * @param formatStyle 需要格式的目标字符串  举例 yyyyMMdd
     * @return 转换完成的日期字符串
     */
    public static String dateToString(Date targetDate, String formatStyle)
    {
        String targetStr = null;
        SimpleDateFormat sdf = null;
        try
        {
            if (targetDate != null)
            {
                if (!StringUtils.isEmpty(formatStyle))
                {
                    sdf = new SimpleDateFormat(formatStyle);
                }
                else 
                {
                    sdf = new SimpleDateFormat(PATTERN_MINUS_DATETIME);
                }
                targetStr = sdf.format(targetDate);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return targetStr;
    }

    /**
     * 字符串转换到时间格式
     * @param dateStr 需要转换的字符串
     * @return Date 返回转换后的时间
     * @throws ParseException 转换异常
     */
    public static Date stringToDate(String dateStr)
    {
        if (dateStr == null)
        {
            return null;
        }
        Date date = null;
        String formatStr = "";
        try
        {
            if (!StringUtils.isEmpty(dateStr.trim()))
            {
                // 参数的字符长度
                int dateLen = dateStr.trim().length();

                // 根据参数的日期格式来判断日期的精度
                if (dateLen == PATTERN_COMPACT_DATE.length())
                {
                    formatStr = PATTERN_COMPACT_DATE;
                }
                else if (dateLen == PATTERN_COMPACT_DATETIME_TOHOUR.length())
                {
                    formatStr = PATTERN_COMPACT_DATETIME_TOHOUR;
                }
                else if (dateLen == PATTERN_COMPACT_DATETIME_TOMINUTE.length())
                {
                    formatStr = PATTERN_COMPACT_DATETIME_TOMINUTE;
                }
                else if (dateLen == PATTERN_COMPACT_DATETIME.length())
                {
                    formatStr = PATTERN_COMPACT_DATETIME;
                }
                else if (dateLen == PATTERN_COMPACT_FULL.length())
                {
                    formatStr = PATTERN_COMPACT_FULL;
                }

                DateFormat sdf = new SimpleDateFormat(formatStr);
                date = sdf.parse(dateStr);
            }
        }
        catch (ParseException pe)
        {
            throw new SystemException("日期格式输入不正确" + formatStr, pe);
        }
        return date;
    }

    /**
     * 获取系统当前时间
     * @return Date 返回系统当前时间
     */
    public static Date getSystemTime()
    {
        return new Date();
    }

    public static void main(String[] args)
    {
        // System.out.println(stringToDate("201212"));
        /*
         * System.out.println(getDaysBetween(stringToDate("20120524122201"),
         * stringToDate("20120528081401")));
         * System.out.println(getDaysBetween(stringToDate
         * ("20120524122201"),stringToDate("20120601081401")));
         * System.out.println
         * (getDaysBetween(stringToDate("20111224122201"),stringToDate
         * ("20120101081401")));
         * System.out.println(getDaysBetween(stringToDate("20120524122201"
         * ),stringToDate("20120522081401")));
         * 
         * System.out.println(getYearsBetween(stringToDate("20120524122201"),
         * stringToDate("20120528081401")));
         * System.out.println(getYearsBetween(stringToDate
         * ("20110524122201"),stringToDate("20120101081401")));
         * System.out.println
         * (getYearsBetween(stringToDate("20110524122201"),stringToDate
         * ("20120524081401")));
         * System.out.println(getYearsBetween(stringToDate(
         * "20110524122201"),stringToDate("20120624081401")));
         * System.out.println
         * (getYearsBetween(stringToDate("20120524122201"),stringToDate
         * ("20120522081401")));
         */
        /*
         * System.out.println(caluAge(stringToDate("20120623122201")));
         * System.out.println(caluAge(stringToDate("20120624122201")));
         * System.out.println(caluAge(stringToDate("20120622122201")));
         * System.out.println(caluAge(stringToDate("20120528122201")));
         * System.out.println(caluAge(stringToDate("20110724122201")));
         * System.out.println(caluAge(stringToDate("20110723122201")));
         * System.out.println(caluAge(stringToDate("20110722122201")));
         * System.out.println(caluAge(stringToDate("20110622122201")));
         */
    }

    public static int compareDate(Date date1, Date date2)
    {
        try
        {
            if (date1.getTime() > date2.getTime())
                return -1;
            else if (date1.getTime() < date2.getTime())
                return 1;
            else
                return 0;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return 0;
    }

    // $Author :jin_peng
    // $Date : 2013/03/27 10:55$
    // [BUG]0014747 ADD BEGIN
    /**
     * 获取指定日期为一周中的第几天 
     * @param date 待处理日期
     * @return 星期几
     */
    public static int getDayOfWeek(Date date)
    {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;

        if (w == 0)
        {
            w = 7;
        }

        return w;
    }

    // [BUG]0014747 ADD END
}
