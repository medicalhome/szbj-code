package com.yly.cdr.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/help")
public class HelpController
{
    @RequestMapping("/accountHelp")
    public ModelAndView help(WebRequest request, HttpSession session)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/help/accountHelp");
        return mav;
    }
    // $Author:chang_xuewen
    // $Date : 2013/12/19 14:10
    // $[BUG]0040878 ADD BEGIN
    @RequestMapping("/questions")
    public ModelAndView questions(WebRequest request, HttpSession session)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/help/questions");
        return mav;
    }
    // $[BUG]0040878 ADD END
}
