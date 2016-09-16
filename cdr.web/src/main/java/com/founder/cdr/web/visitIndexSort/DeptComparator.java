package com.founder.cdr.web.visitIndexSort;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.founder.cdr.dto.VisitIndexListDto;
import com.founder.cdr.util.DateUtils;

public class DeptComparator implements Comparator<VisitIndexListDto>
{
    private String loginUserDept = "";

    public void setLoginUserDept(String loginUserDept)
    {
        this.loginUserDept = loginUserDept;
    }

    @Override
    public int compare(VisitIndexListDto visitIndex0,
            VisitIndexListDto visitIndex1)
    {
        String dept0 = visitIndex0.getVisitDeptId();
        if (dept0 == null)
            dept0 = "";
        String dept1 = visitIndex1.getVisitDeptId();
        if (dept1 == null)
            dept1 = "";

        // 如果是与登录科室相同，按就诊日期比较
        if (dept0.equals(loginUserDept) && dept1.equals(loginUserDept))
        {
            Date visitDate0 = visitIndex0.getVisitDate();
            Date visitDate1 = visitIndex1.getVisitDate();
            return DateUtils.compareDate(visitDate0, visitDate1);

        }
        else if (dept0.equals(loginUserDept) && !dept1.equals(loginUserDept))
        {
            return -1;
        }
        else if (!dept0.equals(loginUserDept) && dept1.equals(loginUserDept))
        {
            return 1;
        }
        // 如果比较的科室都不是登录科室，按照就诊日期比较
        else
        {
            Date visitDate0 = visitIndex0.getVisitDate();
            Date visitDate1 = visitIndex1.getVisitDate();
            return DateUtils.compareDate(visitDate0, visitDate1);
        }
    }
}
