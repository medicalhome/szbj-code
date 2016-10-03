/**
 * Copyright (c) 2012—2012 Founder International Co.Ltd. All rights reserved.
 */
package com.yly.cdr.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 处方的视图控制类
 * 
 * @author xu_dengfeng
 *
 */
@Controller
@RequestMapping("/prescription")
public class PrescriptionController
{
    private static Logger logger = LoggerFactory.getLogger(PrescriptionController.class);

    /**
     * V05-302:西医处方详细
     * 西医处方详细画面
     * V05-303:中医处方详细
     * 中医处方详细画面
     * 
     * @param visitSn
     * @return
     * @throws Exception
     */
    @RequestMapping("/{prescriptionSn}")
    public ModelAndView detail(
            @PathVariable("prescriptionSn") String prescriptionSn)
            throws Exception
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/prescription/prescriptionDetail");
        mav.setViewName("/prescription/herbPrescriptionDetail");

        return mav;
    }
}
