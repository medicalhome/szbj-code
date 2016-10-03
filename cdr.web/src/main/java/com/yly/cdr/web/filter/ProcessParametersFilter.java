package com.yly.cdr.web.filter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yly.cdr.dto.CommonParameters;
import com.yly.cdr.dto.TimerAndInpatientDto;
import com.yly.cdr.dto.diagnosis.DiagnosisListSearchPra;
import com.yly.cdr.dto.doc.DocListSearchParameters;
import com.yly.cdr.dto.drug.DrugListSearchPra;
import com.yly.cdr.dto.exam.ExamListSearchParameters;
import com.yly.cdr.dto.lab.LabListSearchParameters;
import com.yly.cdr.dto.order.NonDrugOrderListSearchPra;
import com.yly.cdr.dto.procedure.ProcedureListSearchPra;
import com.yly.cdr.entity.SystemAccountLog;
import com.yly.cdr.entity.SystemImpressionLog;
import com.yly.cdr.service.CommonService;
import com.yly.cdr.util.DateUtils;
import com.yly.cdr.util.StringUtils;
import com.yly.cdr.web.loginValidation.LoginUserDetails;
import com.yly.cdr.web.util.LoginUtil;
import com.yly.cdr.web.util.StatisticalDataHelper;

/**
 * 当访问的为时间轴视图或住院视图时向所需各业务参数对象中设置相应参数
 * @author jin_peng
 * @version 1.0, 2012/06/11
 */
public class ProcessParametersFilter implements Filter
{
    private static List<String> patternNoFilterList;

    @Autowired
    private StatisticalDataHelper statisticalDataHelper;

    @Autowired
    private CommonService commonService;

    static
    {
        // 添加不需过滤处理链接
        patternNoFilterList = new ArrayList<String>();

        patternNoFilterList.add("/scripts/**/*");
        patternNoFilterList.add("/styles/**/*");
        patternNoFilterList.add("/images/**/*");
        patternNoFilterList.add("/**/favicon.ico");
        patternNoFilterList.add("/**/login.html");
        patternNoFilterList.add("/**/loginunauthorized.html");
        patternNoFilterList.add("/**/loginIntegrated.html");
        patternNoFilterList.add("/**/loginLabReportIntegrated.html");
        patternNoFilterList.add("/**/loginInpatientIntegrated.html");
        patternNoFilterList.add("/**/loginBusinessSnIntegrated.html");
        patternNoFilterList.add("/**/loginPortalIntegrated.html");
        patternNoFilterList.add("/**/loginExited.html");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException
    {
        long beginTimeMillis = StatisticalDataHelper.getSystemCurrentTime();

        // 时间轴视图或住院视图传递的各业务标识
        String type = request.getParameter("type");
        // 就诊内部序列号
        String visitSn = request.getParameter("visitSn");
        // 住院日期
        String inpatientDate = request.getParameter("inpatientDate");

        // $Author :jin_peng
        // $Date : 2012/10/10 18:28$
        // [BUG]0010239 ADD BEGIN
        // 药物医嘱长期临时标识
        String longTempFlag = request.getParameter("longTempFlag");

        // 药物医嘱撤销标识
        String cancelOrderStatus = request.getParameter("cancelOrderStatus");

        // 长期药物医嘱住院日期
        String inpatientLongTermDate = request.getParameter("inpatientLongTermDate");

        // 时间轴视图或住院视图所需向各业务传递参数对象，已被所需各业务继承
        CommonParameters commonParameters = null;

        // 当由时间轴视图或住院视图访问时，向各业务参数对象设置相应参数
        if (!StringUtils.isEmpty(type))
        {
            // 诊断
            if (TimerAndInpatientDto.DIAGNOSIS.equals(type))
            {
                commonParameters = new DiagnosisListSearchPra();
            }

            // 用药医嘱
            if (TimerAndInpatientDto.DRUG_ORDER.equals(type))
            {
                commonParameters = new DrugListSearchPra();
            }

            // 检查
            if (TimerAndInpatientDto.EXAMINATION.equals(type))
            {
                commonParameters = new ExamListSearchParameters();
            }

            // 检验
            if (TimerAndInpatientDto.LAB.equals(type))
            {
                commonParameters = new LabListSearchParameters();
            }

            // 手术
            if (TimerAndInpatientDto.PROCEDURE.equals(type))
            {
                commonParameters = new ProcedureListSearchPra();
            }

            // 病例文书
            if (TimerAndInpatientDto.DOCUMENTATION.equals(type))
            {
                commonParameters = new DocListSearchParameters();
            }

            // 非用药医嘱
            if (TimerAndInpatientDto.NO_DRUG_ORDER.equals(type))
            {
                commonParameters = new NonDrugOrderListSearchPra();
            }

            // 实例相应对象完成后进行参数设置
            commonParameters.setVisitSn(visitSn);
            commonParameters.setInpatientDate(inpatientDate);
            commonParameters.setType(type);
            commonParameters.setLongTempFlag(longTempFlag);
            commonParameters.setCancelOrderStatus(cancelOrderStatus);
            commonParameters.setInpatientLongTermDate(inpatientLongTermDate);

            // [BUG]0010239 ADD END

            // 将设置完成的相应业务对象加入request域
            request.setAttribute("commonParameters", commonParameters);
        }

        // 开始执行各业务流程
        chain.doFilter(request, response);

        // Putting the contents related to response into table
        // systemImpressionLog
        processImpressionPlusIn(request, beginTimeMillis);
    }

    /**
     * 存储请求响应数据
     * @param request 请求对象
     * @param beginTimeMillis 请求到达服务器进入filter开始时间
     */
    private void processImpressionPlusIn(ServletRequest request,
            long beginTimeMillis)
    {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String requestUrl = statisticalDataHelper.getRequestUrl(httpRequest);

        // 需记录请求响应内容链接相关操作
        if (statisticalDataHelper.isFiltratedOrNot(requestUrl,
                patternNoFilterList))
        {
            String requestParams = statisticalDataHelper.getRequestParams(httpRequest);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null)
            {
                return;
            }

            LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();
            BigDecimal accountLogSn = userDetails.getSequenceNo();

            Map<String ,Object> condition = new HashMap<String ,Object>();
            condition.put("accountLogSn", accountLogSn);
            List list = commonService.selectByCondition(SystemAccountLog.class, condition);
            if(list.size()<1){
            	/**
            	 *  单点用户
            	 */
            	httpRequest.getSession().setAttribute("idm", 1);
	            // 该标识验证用户为从登陆页面或集成登陆进入
	            userDetails.setLogin(false);
	            SystemAccountLog systemAccountLog = LoginUtil.getSystemAccountLog(httpRequest,
	                    userDetails, userDetails.getDomainId());
	
	            // 记录用户登陆日志
	            commonService.save(systemAccountLog);
            }
            Date responseSystemTime = DateUtils.getSystemTime();
            long endTimeMillis = responseSystemTime.getTime();

            BigDecimal responseTimeMillis = statisticalDataHelper.getResponseTimeMillis(
                    beginTimeMillis, endTimeMillis);

            SystemImpressionLog systemImpressionLog = statisticalDataHelper.getSystemImpressionLog(
                    accountLogSn, requestUrl, requestParams,
                    responseTimeMillis, responseSystemTime);

            commonService.save(systemImpressionLog);
        }
    }

    @Override
    public void destroy()
    {
        // TODO Auto-generated method stub
    }

}
