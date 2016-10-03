/**
 * Copyright (c) 2012—2012 Founder International Co.Ltd. All rights reserved.
 */
package com.yly.cdr.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用于医学观察的视图控制类
 * 
 * @author xu_dengfeng
 *
 */
@Controller
@RequestMapping("/observation")
public class ObservationController
{
    private static Logger logger = LoggerFactory.getLogger(ObservationController.class);

    /**
     * V05-708:体格检查列表
     * 显示体格检查列表画面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/physicalExamlist")
    public ModelAndView list(WebRequest request)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/observation/physicalExamList");

        return mav;
    }
}
