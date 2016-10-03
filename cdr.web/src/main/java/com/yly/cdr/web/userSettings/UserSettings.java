package com.yly.cdr.web.userSettings;

import com.yly.cdr.core.AppSettings;
import com.yly.cdr.core.Constants;
import com.yly.cdr.entity.SystemSetting;
import com.yly.cdr.util.StringUtils;

public class UserSettings
{
    private String displayMenus;

    private VisitIndexViewSettings visitIndexViewSettings;

    private OutpatientViewSettings outpatientViewSettings;

    private InpatientViewSettings inpatientViewSettings;

    private NormalViewSettings normalViewSettings;

    private TimerViewSettings timerViewSettings;

    // Author:jia_yanqing
    // Date : 2013/1/11 15:30
    // [BUG]0012699 ADD BEGIN
    private String rowsPerPage;

    // [BUG]0012699 ADD END

    // $Author :jin_peng
    // $Date : 2013/12/19 09:51$
    // [BUG]0040598 ADD BEGIN
    private String rowsPerPageLab;

    // [BUG]0040598 ADD END

    public UserSettings(SystemSetting systemSetting)
    {
        init(systemSetting);
    }

    public void init(SystemSetting systemSetting)
    {
        displayMenus = systemSetting.getDisplayMenus();

        visitIndexViewSettings = new VisitIndexViewSettings(
                systemSetting.getVisitIndexViews());
        outpatientViewSettings = new OutpatientViewSettings(
                systemSetting.getOutpatientViews());
        inpatientViewSettings = new InpatientViewSettings(
                systemSetting.getInpatientViews());
        normalViewSettings = new NormalViewSettings(
                systemSetting.getNormalViews());
        timerViewSettings = new TimerViewSettings(systemSetting.getTimerViews());
        // Author:jia_yanqing
        // Date : 2013/1/21 15:00
        // [BUG]0012699 MODIFY BEGIN
        if (systemSetting.getRowsPerPage() == null
            || "".equals(systemSetting.getRowsPerPage()))
        {
            rowsPerPage = AppSettings.getConfig(Constants.CFG_ROWS_PER_PAGE);
        }
        else
        {
            rowsPerPage = StringUtils.BigDecimalToStr(systemSetting.getRowsPerPage());
        }
        // [BUG]0012699 MODIFY END

        // $Author :jin_peng
        // $Date : 2013/12/19 09:51$
        // [BUG]0040598 ADD BEGIN
        if (systemSetting.getRowsPerPageLab() == null
            || "".equals(systemSetting.getRowsPerPageLab()))
        {
            rowsPerPageLab = Constants.ROWS_PER_PAGE_DEFAULT_LAB;
        }
        else
        {
            rowsPerPageLab = StringUtils.BigDecimalToStr(systemSetting.getRowsPerPageLab());
        }

        // [BUG]0040598 ADD END
    }

    public String getDisplayMenus()
    {
        return displayMenus;
    }

    public void setDisplayMenus(String displayMenus)
    {
        this.displayMenus = displayMenus;
    }

    public VisitIndexViewSettings getVisitIndexViewSettings()
    {
        return visitIndexViewSettings;
    }

    public void setVisitIndexViewSettings(
            VisitIndexViewSettings visitIndexViewSettings)
    {
        this.visitIndexViewSettings = visitIndexViewSettings;
    }

    public OutpatientViewSettings getOutpatientViewSettings()
    {
        return outpatientViewSettings;
    }

    public void setOutpatientViewSettings(
            OutpatientViewSettings outpatientViewSettings)
    {
        this.outpatientViewSettings = outpatientViewSettings;
    }

    public InpatientViewSettings getInpatientViewSettings()
    {
        return inpatientViewSettings;
    }

    public void setInpatientViewSettings(
            InpatientViewSettings inpatientViewSettings)
    {
        this.inpatientViewSettings = inpatientViewSettings;
    }

    public NormalViewSettings getNormalViewSettings()
    {
        return normalViewSettings;
    }

    public void setNormalViewSettings(NormalViewSettings normalViewSettings)
    {
        this.normalViewSettings = normalViewSettings;
    }

    public TimerViewSettings getTimerViewSettings()
    {
        return timerViewSettings;
    }

    public void setTimerViewSettings(TimerViewSettings timerViewSettings)
    {
        this.timerViewSettings = timerViewSettings;
    }

    // Author:jia_yanqing
    // Date : 2013/1/11 15:30
    // [BUG]0012699 ADD BEGIN
    public String getRowsPerPage()
    {
        return rowsPerPage;
    }

    public void setRowsPerPage(String rowsPerPage)
    {
        this.rowsPerPage = rowsPerPage;
    }

    // [BUG]0012699 ADD END

    public String getRowsPerPageLab()
    {
        return rowsPerPageLab;
    }

    public void setRowsPerPageLab(String rowsPerPageLab)
    {
        this.rowsPerPageLab = rowsPerPageLab;
    }

}
