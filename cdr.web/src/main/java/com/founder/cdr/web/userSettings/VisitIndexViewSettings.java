package com.founder.cdr.web.userSettings;

import java.util.Arrays;
import java.util.List;

import com.founder.cdr.core.Constants;

public class VisitIndexViewSettings
{
    private boolean showView;
    
    public VisitIndexViewSettings(String views)
    {
/*
        if(views == null) views = "";
        List<String> viewList = Arrays.asList(views.split(","));
        for(String view : viewList)
        {
            if(view == Constants.VIEW_SETTING_VISITINDEXVIEW)
                this.showView = true;
        }
*/
        if(views == null)
            this.showView = true;
        else
        {
            List<String> viewList = Arrays.asList(views.split(","));
            for (String view : viewList)
            {
                if (view.equals(Constants.VIEW_SETTING_VISITINDEXVIEW))
                    this.showView = true;
            }
        }
    }

    public boolean getShowView()
    {
        return showView;
    }

    public void setShowView(boolean showView)
    {
        this.showView = showView;
    }
}
