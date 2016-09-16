package com.founder.cdr.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils
{
    /**
     * 获得异常的详细信息
     * 
     * @param t
     * @return
     */
    public static String getStackTrace(Throwable t)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        pw.flush();
        pw.close();

        return sw.getBuffer().toString();
    }
}
