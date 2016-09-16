package com.founder.cdr.dto;

import java.math.BigDecimal;

import com.founder.sqlparser.util.StringUtil;

public class SystemSettingDto
{
    private String userSn;

    // $Author:wu_jianfeng
    // $Date : 2012/12/21 14:10
    // $[BUG]0012967 ADD BEGIN
    private String outpatientViews;

    private String inpatientViews;

    private String normalViews;

    private String timerViews;

    private String visitIndexViews;

    // $[BUG]0012967 ADD END

    private String displayMenus;
    
    // Author:jia_yanqing
    // Date : 2013/1/10 18:30
    // [BUG]0012699 ADD BEGIN
    private BigDecimal rowsPerPage;
    // [BUG]0012699 ADD END
    
    // $Author :jin_peng
    // $Date : 2013/12/19 09:51$
    // [BUG]0040598 ADD BEGIN
    private BigDecimal rowsPerPageLab;
    
    // [BUG]0040598 ADD END

    public String getUserSn()
    {
        return userSn;
    }

    public void setUserSn(String userSn)
    {
        this.userSn = userSn;
    }

    public String getDisplayMenus()
    {
        return displayMenus;
    }

    public void setDisplayMenus(String displayMenus)
    {
        this.displayMenus = displayMenus;
    }

    // $Author:wu_jianfeng
    // $Date : 2012/12/21 14:10
    // $[BUG]0012967 ADD BEGIN
    public String getOutpatientViews()
    {
        return outpatientViews;
    }

    public void setOutpatientViews(String outpatientViews)
    {
        this.outpatientViews = outpatientViews;
    }

    public String getInpatientViews()
    {
        return inpatientViews;
    }

    public void setInpatientViews(String inpatientViews)
    {
        this.inpatientViews = inpatientViews;
    }

    public String getNormalViews()
    {
        return normalViews;
    }

    public void setNormalViews(String normalViews)
    {
        this.normalViews = normalViews;
    }

    public String getTimerViews()
    {
        return timerViews;
    }

    public void setTimerViews(String timerViews)
    {
        this.timerViews = timerViews;
    }

    public String getVisitIndexViews()
    {
        return visitIndexViews;
    }

    public void setVisitIndexViews(String visitIndexViews)
    {
        this.visitIndexViews = visitIndexViews;
    }
    // $[BUG]0012967 ADD END

    // Author:jia_yanqing
    // Date : 2013/1/10 18:30
    // [BUG]0012699 ADD BEGIN
    public BigDecimal getRowsPerPage()
    {
        return rowsPerPage;
    }

    public void setRowsPerPage(BigDecimal rowsPerPage)
    {
        this.rowsPerPage = rowsPerPage;
    }
    // [BUG]0012699 ADD END

    public Boolean isMenuDisplay(String menuCode)
    {
        if (displayMenus == null || StringUtil.isEmpty(displayMenus))
            return false;
        String[] displayMenuList = displayMenus.split(",");
        // 如果是二级菜单
        if (menuCode.length() == 4)
        {
            for (String displayMenu : displayMenuList)
            {
                if (displayMenu == menuCode)
                    return true;
            }
        }
        // 如果是一级菜单
        else if (menuCode.length() == 2)
        {
            for (String displayMenu : displayMenuList)
            {
                if (displayMenu.substring(0, 2) == menuCode)
                    return true;
            }
        }

        return false;
    }

    public BigDecimal getRowsPerPageLab()
    {
        return rowsPerPageLab;
    }

    public void setRowsPerPageLab(BigDecimal rowsPerPageLab)
    {
        this.rowsPerPageLab = rowsPerPageLab;
    }

}
