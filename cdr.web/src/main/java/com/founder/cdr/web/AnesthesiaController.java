/**
 * Copyright (c) 2012—2012 Founder International Co.Ltd. All rights reserved.
 */
package com.founder.cdr.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用于麻醉的视图控制类
 * 
 * @author xu_dengfeng
 *
 */
@Controller
@RequestMapping("/anesthesia")
public class AnesthesiaController
{
    private static Logger logger = LoggerFactory.getLogger(AnesthesiaController.class);

    /**
     * V05-705:手术麻醉操作列表
     * 显示手术麻醉操作列表信息画面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/operationList")
    public ModelAndView operationList(WebRequest request)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/anesthesia/anesthesiaOperationList");

        return mav;
    }
}
