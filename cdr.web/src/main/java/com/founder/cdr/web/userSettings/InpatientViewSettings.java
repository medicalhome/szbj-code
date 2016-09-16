package com.founder.cdr.web.userSettings;

import java.util.Arrays;
import java.util.List;

import com.founder.cdr.core.Constants;

public class InpatientViewSettings
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
    
    private boolean showTC;

    public InpatientViewSettings(String views)
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
            this.showTC = true;
        }
        else
        {
            List<String> viewList = Arrays.asList(views.split(","));
            for (String view : viewList)
            {
                if (view.equals(Constants.VIEW_SETTING_INPATIENTVIEW))
                    this.showView = true;
                else if (view.equals(Constants.VIEW_SETTING_IVIEW_VISIT))
                    this.showVisit = true;
                else if (view.equals(Constants.VIEW_SETTING_IVIEW_DIAGNOSIS))
                    this.showDiagnosis = true;
                else if (view.equals(Constants.VIEW_SETTING_IVIEW_DRUG))
                    this.showDrug = true;
                else if (view.equals(Constants.VIEW_SETTING_IVIEW_LONGDRUG))
                    this.showLongDrug = true;
                else if (view.equals(Constants.VIEW_SETTING_IVIEW_SHORTDRUG))
                    this.showShortDrug = true;
                else if (view.equals(Constants.VIEW_SETTING_IVIEW_EXAM))
                    this.showExam = true;
                else if (view.equals(Constants.VIEW_SETTING_IVIEW_LAB))
                    this.showLab = true;
                else if (view.equals(Constants.VIEW_SETTING_IVIEW_PROCEDURE))
                    this.showProcedure = true;
                else if (view.equals(Constants.VIEW_SETTING_IVIEW_DOC))
                    this.showDoc = true;
                else if (view.equals(Constants.VIEW_SETTING_IVIEW_OTHER))
                    this.showOther = true;
                else if (view.equals(Constants.VIEW_SETTING_IVIEW_TC))
                    this.showTC = true;
            }
        }
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

    public boolean getShowView()
    {
        return showView;
    }

    public void setShowView(boolean showView)
    {
        this.showView = showView;
    }

    public boolean getShowVisit()
    {
        return showVisit;
    }

    public void setShowVisit(boolean showVisit)
    {
        this.showVisit = showVisit;
    }

    public boolean getShowDrug()
    {
        return showDrug;
    }

    public void setShowDrug(boolean showDrug)
    {
        this.showDrug = showDrug;
    }

    public boolean getShowTC()
    {
        return showTC;
    }

    public void setShowTC(boolean showTC)
    {
        this.showTC = showTC;
    }
}
