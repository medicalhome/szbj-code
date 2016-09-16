/**
 * Copyright (c) 2012—2012 Founder International Co.Ltd. All rights reserved.
 */
package com.founder.cdr.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.founder.cdr.cache.DictionaryCache;
import com.founder.cdr.core.Constants;
import com.founder.cdr.dto.AccessControlDto;
import com.founder.cdr.dto.DictionaryDto;
import com.founder.cdr.dto.SystemSettingDto;
import com.founder.cdr.dto.codeValue.HideDrugDto;
import com.founder.cdr.entity.HideDrug;
import com.founder.cdr.entity.SystemMenu;
import com.founder.cdr.service.SystemService;
import com.founder.cdr.util.DateUtils;
import com.founder.cdr.util.StringUtils;
import com.founder.cdr.web.loginValidation.AccessControl;
import com.founder.cdr.web.loginValidation.LoginUserDetails;
import com.founder.cdr.web.userSettings.InpatientViewSettings;
import com.founder.cdr.web.userSettings.NormalViewSettings;
import com.founder.cdr.web.userSettings.OutpatientViewSettings;
import com.founder.cdr.web.userSettings.TimerViewSettings;
import com.founder.cdr.web.userSettings.UserSettings;
import com.founder.fasf.core.util.daohelper.entity.EntityDao;

/**
 * 处理系统设置相关的显示控制类
 * 
 * @author xu_dengfeng
 *
 */
@Controller
@RequestMapping("/system")
public class SystemController
{
    @Autowired
    private SystemService systemService;

    /**
     * V01-001:系统设置
     * 系统设置画面，可自定义菜单项
     * @param request
     * @return
     * @throws Exception
     * method "get"
     */
    @RequestMapping(value = "/setting")
    public ModelAndView setting(WebRequest request, HttpSession session)
            throws Exception
    {
        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userSn = userDetails.getUsername();

        // 根据患者姓名，查出隐藏药物的信息
        List<HideDrugDto> hideDrug = systemService.selectHideDrugByUserName(userSn);
        
        ModelAndView mav = new ModelAndView();
        mav.addObject("userSn", userSn);
        mav.addObject("hideDrug", hideDrug);

        // $Author:wu_jianfeng
        // $Date : 2012/12/21 14:10
        // $[BUG]0012967 MODIFY BEGIN
        UserSettings userSettings = userDetails.getUserSettings();

        // Author:jia_yanqing
        // Date : 2013/1/11 15:30
        // [BUG]0012699 ADD BEGIN
        String rowsPerPage = userSettings.getRowsPerPage();
        // [BUG]0012699 ADD END
        
        // $Author :jin_peng
        // $Date : 2013/12/19 09:51$
        // [BUG]0040598 ADD BEGIN
        String rowsPerPageLab = userSettings.getRowsPerPageLab();
        
        // [BUG]0040598 ADD END

        // 根据患者内部序列号，读取系统设置表
        // $Author:wu_jianfeng
        // $Date : 2012/09/12 14:10
        // $[BUG]0009852 MODIFY BEGIN
        // 访问控制实现
        // 得到系统所有的菜单
        List<SystemMenu> allSystemMenus = systemService.getAllSystemMenu();
        // 得到系统设置中已设置要显示的菜单
        String displayMenusStr = userSettings.getDisplayMenus();
        if (displayMenusStr == null)
        {
            displayMenusStr = "";
            for (SystemMenu menu : allSystemMenus)
            {
                if (displayMenusStr != "")
                    displayMenusStr += ",";
                displayMenusStr += menu.getMenuCode();
            }
        }
        else if (displayMenusStr == "-1")
            displayMenusStr = "";

        AccessControlDto aclAuths = null;
        List<SystemMenu> systemMenus = new ArrayList<SystemMenu>();
        List<SystemMenu> displayMenus = new ArrayList<SystemMenu>();

        if (AccessControl.useACL())
        {
            AccessControl acl = userDetails.getAccessControl();
            aclAuths = acl.getAccessControlAuths();
            // 根据访问控制权限，得到能够访问的所有功能菜单
            systemMenus = systemService.getSystemMenusByACL(allSystemMenus,
                    aclAuths);

            // 根据访问控制权限，从显示的功能菜单中筛选
            if (displayMenusStr != null && !displayMenusStr.isEmpty())
            {
                List<String> menuCodes = new ArrayList<String>();
                for (String menuCode : displayMenusStr.split(","))
                    menuCodes.add(menuCode.trim());
                if (menuCodes.size() != 0)
                    displayMenus = systemService.getSystemMenusByACL(
                            systemService.getSystemMenus(menuCodes), aclAuths);
            }
            displayMenusStr = "";
            for (SystemMenu menu : displayMenus)
            {
                if (displayMenusStr != "")
                    displayMenusStr += ",";
                displayMenusStr += menu.getMenuCode().trim();
            }
        }

        // $Author :jin_peng
        // $Date : 2013/12/19 09:51$
        // [BUG]0040598 ADD BEGIN
        mav.addObject("rowsPerPageLab", rowsPerPageLab);
        
        // [BUG]0040598 ADD END
        
        // Author:jia_yanqing
        // Date : 2013/1/11 15:30
        // [BUG]0012699 ADD BEGIN
        mav.addObject("rowsPerPage", rowsPerPage);
        // [BUG]0012699 ADD END
        if (AccessControl.useACL())
            mav.addObject("systemMenus", systemMenus);
        else
            mav.addObject("systemMenus", allSystemMenus);
        mav.addObject("displayMenus", displayMenusStr);

        mav.addObject("visitIndexViews",
                userSettings.getVisitIndexViewSettings());
        mav.addObject("outpatientViews",
                userSettings.getOutpatientViewSettings());
        mav.addObject("inpatientViews", userSettings.getInpatientViewSettings());
        mav.addObject("timerViews", userSettings.getTimerViewSettings());
        mav.addObject("normalViews", userSettings.getNormalViewSettings());

        // $[BUG]0009852 MODIFY END
        // $[BUG]0012967 MODIFY END

        // Author:jia_yanqing
        // Date : 2013/1/30 15:30
        // [BUG]0013677 ADD BEGIN
        mav.addObject("aclAuths", aclAuths);
        mav.addObject("useACL", AccessControl.useACL());
        // [BUG]0013677 ADD END

        mav.setViewName("/system/systemSetting");

        return mav;
    }

    @RequestMapping(value = "/save_{action}")
    public ModelAndView saveSystemSetting(WebRequest request,
            @PathVariable("action") String action, HttpSession session)
            throws Exception
    {
        LoginUserDetails userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userSn = userDetails.getUsername();
        String patientSn = session.getAttribute("patientSn").toString();

        // $Author:wu_jianfeng
        // $Date : 2012/12/21 14:10
        // $[BUG]0012967 MODIFY BEGIN
        SystemSettingDto systemSettingDto = new SystemSettingDto();
        systemSettingDto.setUserSn(userSn);

        String displayMenus = request.getParameter("displayMenus");
        if (StringUtils.isEmpty(displayMenus))
            displayMenus = "-1";
        systemSettingDto.setDisplayMenus(displayMenus);

        /*
         * String[] visitIndexViews =
         * request.getParameterValues("visitIndexViews"); String
         * visitIndexViewStr = ""; if (visitIndexViews != null &&
         * visitIndexViews.length > 0) { for (String str : visitIndexViews) { if
         * (visitIndexViewStr != "") visitIndexViewStr += ","; visitIndexViewStr
         * += str; } }
         */

        String[] outpatientViews = request.getParameterValues("outpatientViews");
        String outpatientViewStr = "";
        if (outpatientViews != null && outpatientViews.length > 0)
        {
            for (String str : outpatientViews)
            {
                if (outpatientViewStr != "")
                    outpatientViewStr += ",";
                outpatientViewStr += str;
            }
        }

        String[] inpatientViews = request.getParameterValues("inpatientViews");
        String inpatientViewStr = "";
        if (inpatientViews != null && inpatientViews.length > 0)
        {
            for (String str : inpatientViews)
            {
                if (inpatientViewStr != "")
                    inpatientViewStr += ",";
                inpatientViewStr += str;
            }
        }

        String[] timerViews = request.getParameterValues("timerViews");
        String timerViewStr = "";
        if (timerViews != null && timerViews.length > 0)
        {
            for (String str : timerViews)
            {
                if (timerViewStr != "")
                    timerViewStr += ",";
                timerViewStr += str;
            }
        }

        String[] normalViews = request.getParameterValues("normalViews");
        String normalViewStr = "";
        if (normalViews != null && normalViews.length > 0)
        {
            for (String str : normalViews)
            {
                if (normalViewStr != "")
                    normalViewStr += ",";
                normalViewStr += str;
            }
        }

        // Author:jia_yanqing
        // Date : 2013/1/11 10:30
        // [BUG]0012699 ADD BEGIN
        String rowsPerPage = request.getParameter("rowsPerPage");
        // [BUG]0012699 ADD END
        
        // $Author :jin_peng
        // $Date : 2013/12/19 09:51$
        // [BUG]0040598 ADD BEGIN
        String rowsPerPageLab = request.getParameter("rowsPerPageLab");
        
        // [BUG]0040598 ADD END

        // if(visitIndexViewStr == "") visitIndexViewStr = "-1";
        if (outpatientViewStr == "")
            outpatientViewStr = "-1";
        if (inpatientViewStr == "")
            inpatientViewStr = "-1";
        if (timerViewStr == "")
            timerViewStr = "-1";
        if (normalViewStr == "")
            normalViewStr = "-1";

        // systemSettingDto.setVisitIndexViews(visitIndexViewStr);
        systemSettingDto.setOutpatientViews(outpatientViewStr);
        systemSettingDto.setInpatientViews(inpatientViewStr);
        systemSettingDto.setTimerViews(timerViewStr);
        systemSettingDto.setNormalViews(normalViewStr);
        // Author:jia_yanqing
        // Date : 2013/1/11 10:30
        // [BUG]0012699 ADD BEGIN
        systemSettingDto.setRowsPerPage(StringUtils.strToBigDecimal(
                rowsPerPage, "每页显示条数"));
        // [BUG]0012699 ADD END
        
        // $Author :jin_peng
        // $Date : 2013/12/19 09:51$
        // [BUG]0040598 ADD BEGIN
        systemSettingDto.setRowsPerPageLab(StringUtils.strToBigDecimal(
                rowsPerPageLab, "检验历史曲线对比结果每页显示条数"));
        
        // [BUG]0040598 ADD END

        // 保存系统设置
        systemService.saveSystemSetting("all", systemSettingDto);

        if (action.equals("apply"))
        {
            UserSettings userSettings = userDetails.getUserSettings();
            userSettings.setDisplayMenus(systemSettingDto.getDisplayMenus());
            // userSettings.setVisitIndexViewSettings(new
            // VisitIndexViewSettings(systemSettingDto.getVisitIndexViews()));
            userSettings.setOutpatientViewSettings(new OutpatientViewSettings(
                    systemSettingDto.getOutpatientViews()));
            userSettings.setInpatientViewSettings(new InpatientViewSettings(
                    systemSettingDto.getInpatientViews()));
            userSettings.setTimerViewSettings(new TimerViewSettings(
                    systemSettingDto.getTimerViews()));
            userSettings.setNormalViewSettings(new NormalViewSettings(
                    systemSettingDto.getNormalViews()));
            // Author:jia_yanqing
            // Date : 2013/1/11 10:30
            // [BUG]0012699 ADD BEGIN
            userSettings.setRowsPerPage(rowsPerPage);
            // [BUG]0012699 ADD END
            
            // $Author :jin_peng
            // $Date : 2013/12/19 09:51$
            // [BUG]0040598 ADD BEGIN
            userSettings.setRowsPerPageLab(rowsPerPageLab);
            
            // [BUG]0040598 ADD END
        }
        // $[BUG]0012967 MODIFY END

        
        String[] addCodeDrugIds = request.getParameterValues("add_codeDrug");
        String[] deleteCodeDrugIds = request.getParameterValues("delete_codeDrug");
        
        systemService.deleteHideDrug(userSn,deleteCodeDrugIds);
        
        // 设置增加hideDrug
        if(addCodeDrugIds != null && addCodeDrugIds.length != 0)
        {
            systemService.saveHideDrug(addCodeDrugIds,userSn);
        }
        
        ModelAndView mav = new ModelAndView();
        mav.addObject("patientSn", patientSn);
        mav.setViewName("system/afterSaved");
        return mav;
    }

    /**
     * V02-002:权限设定
     * 系统管理员设定用户权限的界面
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/privilegeAssign")
    public ModelAndView privilegeAssign(WebRequest request) throws Exception
    {
        ModelAndView mav = new ModelAndView();

        return mav;
    }
    
}
