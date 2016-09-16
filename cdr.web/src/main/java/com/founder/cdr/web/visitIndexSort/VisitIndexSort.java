package com.founder.cdr.web.visitIndexSort;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ch.qos.logback.core.joran.conditional.IfAction;

import com.founder.cdr.core.Constants;
import com.founder.cdr.dto.VisitIndexListDto;
import com.founder.cdr.entity.SystemAccount;
import com.founder.cdr.service.CommonService;
import com.founder.cdr.web.loginValidation.LoginUserDetails;

public class VisitIndexSort
{
    private List<VisitIndexListDto> visitIndexs;

    private Comparator<VisitIndexListDto> comparatorStrategry;

    public static String DEPT_STRATETEGY = "DeptComparator";

    @SuppressWarnings("unchecked")
    public VisitIndexSort(List<VisitIndexListDto> visitIndexs,
            String sortStrategy)
    {
        this.visitIndexs = visitIndexs;
        // 利用反射动态加载策略类
        String pack = this.getClass().getPackage().getName();
        pack += ".";
        try
        {
            Class<?> cl = Class.forName(pack + sortStrategy);
            comparatorStrategry = (Comparator<VisitIndexListDto>) cl.newInstance();

            if (sortStrategy == DEPT_STRATETEGY)
            {
                // 获取登陆用户名
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();
                String loginUserDept = userDetails.getDeptCode();
                if (loginUserDept == null)
                    loginUserDept = "";

                Method loginUserDeptMethod = cl.getDeclaredMethod(
                        "setLoginUserDept", loginUserDept.getClass());
                loginUserDeptMethod.invoke(comparatorStrategry, loginUserDept);
            }
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public List<VisitIndexListDto> sort()
    {
        Collections.sort(visitIndexs, comparatorStrategry);
        return visitIndexs;
    }

    public static void main(String[] args)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        VisitIndexListDto visit1 = new VisitIndexListDto();
        VisitIndexListDto visit2 = new VisitIndexListDto();
        VisitIndexListDto visit3 = new VisitIndexListDto();

        try
        {
            visit1.setVisitSn("1");
            visit1.setVisitDeptId("dept1");
            visit1.setVisitDate(sdf.parse("2008-01-01"));

            visit2.setVisitSn("2");
            visit2.setVisitDeptId("dept2");
            visit2.setVisitDate(sdf.parse("2008-01-01"));

            visit3.setVisitSn("3");
            visit3.setVisitDeptId("dept3");
            visit3.setVisitDate(sdf.parse("2008-02-01"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        List<VisitIndexListDto> visitIndexs = new ArrayList<VisitIndexListDto>();
        visitIndexs.add(visit1);
        visitIndexs.add(visit2);
        visitIndexs.add(visit3);

        VisitIndexSort viSort = new VisitIndexSort(visitIndexs,
                "DeptComparator");
        List<VisitIndexListDto> sortVisitIndexs = viSort.sort();
        for (VisitIndexListDto visitIndex : sortVisitIndexs)
        {
            System.out.println(visitIndex.getVisitSn());
        }

    }
}
