package com.yly.cdr.web.userSettings;

import java.util.Arrays;
import java.util.List;

import com.yly.cdr.core.Constants;

public class NormalViewSettings
{
    private boolean showView;

    private boolean showVisit;

    private boolean showDiagnosis;

    private boolean showDrug;

    private boolean showLongDrug;

    private boolean showShortDrug;

    private boolean showExam;

    private boolean showLab;

    private boolean showProcedure;

    private boolean showDoc;

    private boolean showOther;

    public NormalViewSettings(String views)
    {
        if (views == null)
        {
            this.showView = true;
            this.showVisit = true;
            this.showDiagnosis = true;
            this.showDrug = true;
            this.showLongDrug = true;
            this.showShortDrug = true;
            this.showExam = true;
            this.showLab = true;
            this.showProcedure = true;
            this.showDoc = true;
            this.showOther = true;
        }
        else
        {
            List<String> viewList = Arrays.asList(views.split(","));
            for (String view : viewList)
            {
                if (view.equals(Constants.VIEW_SETTING_NORMALVIEW))
                    this.showView = true;
                else if (view.equals(Constants.VIEW_SETTING_NVIEW_VISIT))
                    this.showVisit = true;
                else if (view.equals(Constants.VIEW_SETTING_NVIEW_DIAGNOSIS))
                    this.showDiagnosis = true;
                else if (view.equals(Constants.VIEW_SETTING_NVIEW_DRUG))
                    this.showDrug = true;
                else if (view.equals(Constants.VIEW_SETTING_NVIEW_LONGDRUG))
                    this.showLongDrug = true;
                else if (view.equals(Constants.VIEW_SETTING_NVIEW_SHORTDRUG))
                    this.showShortDrug = true;
                else if (view.equals(Constants.VIEW_SETTING_NVIEW_EXAM))
                    this.showExam = true;
                else if (view.equals(Constants.VIEW_SETTING_NVIEW_LAB))
                    this.showLab = true;
                else if (view.equals(Constants.VIEW_SETTING_NVIEW_PROCEDURE))
                    this.showProcedure = true;
                else if (view.equals(Constants.VIEW_SETTING_NVIEW_DOC))
                    this.showDoc = true;
                else if (view.equals(Constants.VIEW_SETTING_NVIEW_OTHER))
                    this.showOther = true;
            }
        }
    }

    public boolean getShowVisit()
    {
        return showVisit;
    }

    public void setShowVisit(boolean showVisit)
    {
        this.showVisit = showVisit;
    }

    public boolean getShowDiagnosis()
    {
        return showDiagnosis;
    }

    public void setShowDiagnosis(boolean showDiagnosis)
    {
        this.showDiagnosis = showDiagnosis;
    }

    public boolean getShowLongDrug()
    {
        return showLongDrug;
    }

    public void setShowLongDrug(boolean showLongDrug)
    {
        this.showLongDrug = showLongDrug;
    }

    public boolean getShowShortDrug()
    {
        return showShortDrug;
    }

    public void setShowShortDrug(boolean showShortDrug)
    {
        this.showShortDrug = showShortDrug;
    }

    public boolean getShowExam()
    {
        return showExam;
    }

    public void setShowExam(boolean showExam)
    {
        this.showExam = showExam;
    }

    public boolean getShowLab()
    {
        return showLab;
    }

    public void setShowLab(boolean showLab)
    {
        this.showLab = showLab;
    }

    public boolean getShowView()
    {
        return showView;
    }

    public void setShowView(boolean showView)
    {
        this.showView = showView;
    }

    public boolean getShowDrug()
    {
        return showDrug;
    }

    public void setShowDrug(boolean showDrug)
    {
        this.showDrug = showDrug;
    }

    public boolean getShowProcedure()
    {
        return showProcedure;
    }

    public void setShowProcedure(boolean showProcedure)
    {
        this.showProcedure = showProcedure;
    }

    public boolean getShowDoc()
    {
        return showDoc;
    }

    public void setShowDoc(boolean showDoc)
    {
        this.showDoc = showDoc;
    }

    public boolean getShowOther()
    {
        return showOther;
    }

    public void setShowOther(boolean showOther)
    {
        this.showOther = showOther;
    }

}
