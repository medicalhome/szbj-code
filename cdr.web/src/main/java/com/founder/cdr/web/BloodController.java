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
 * 用于用血的视图控制类
 * 
 * @author xu_dengfeng
 *
 */
@Controller
@RequestMapping("/blood")
public class BloodController
{
    private static Logger logger = LoggerFactory.getLogger(BloodController.class);

    /**
     * V05-706:用血申请列表
     * 显示用血申请列表信息画面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/requestList")
    public ModelAndView requestList(WebRequest request)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/blood/bloodRequestList");

        return mav;
    }

    /**
     * V05-707:取血单列表
     * 显示取血单列表信息画面
     * 
     * @param reportSn
     * @return
     * @throws Exception
     */
    @RequestMapping("/deliveryRequestList")
    public ModelAndView deliveryRequestList(WebRequest request)
            throws Exception
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/blood/bloodDeliveryRequestList");

        return mav;
    }
}
