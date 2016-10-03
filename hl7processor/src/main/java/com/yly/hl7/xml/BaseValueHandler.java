package com.yly.hl7.xml;

import com.yly.hl7.maphandler.XPathMapHandler;

public interface BaseValueHandler extends ValueHandler
{

    public void setXpathMapHandler(XPathMapHandler mapHandler);
}
