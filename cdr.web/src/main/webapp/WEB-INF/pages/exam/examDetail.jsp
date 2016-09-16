<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>检查详细信息</title>
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<script type="text/javascript" src="../scripts/cpoe/raphael.js"></script>
	<script type="text/javascript" src="../scripts/cpoe/raphael_common.js"></script>
	<script type="text/javascript" src="../scripts/cpoe/jquery.cpoe.js"></script>
	<script type="text/javascript" src="../scripts/cpoe/popup.js"></script>
	<script type="text/javascript" src="../scripts/cpoe/cpoe.js"></script>
	<script>
		/* $(function()
		{
			$("#tabs").tabs();
			$("#tabs").tabs('select', 'tabs-2');
			$("td[name='patientName']").html(getPatientName());
			$("td[name='patientGenderName']").html(getPatientGender());
			$("td[name='patientAge']").html(getPatientAge());
			$("td[name='inpatientNo']").html(getInpatientNo());
		}); */
		
		/*$Author :tong_meng
	     * $Date : 2013/11/22 16:20
	     * $[BUG]0039832 MODIFY BEGIN*/
	    // 如果没有医嘱的报告，用报告和项目sn标识tab页id
		function returnsn(){
			return '${reportSn}${itemSn}';
		}
		// 有医嘱的报告，用报告和项目sn标识tab页id
		function returnorder(){
			return '${examOrderDetail.orderSn}_${reportSn}_${itemSn}';
		}
		// 此方法用于判断检查报告是否有医嘱标识
		function getOrderSn(){
			return '${examOrderDetail.orderSn}';
		}
	    /* $[BUG]0039832 MODIFY END*/
		
		/*$Author :chang_xuewen
	     * $Date : 2013/08/07 16:20
	     * $[BUG]0035740 MODIFY BEGIN*/
		function returnParentName()
		{
			return '${parentDrugOrder.drugName}';
		}
		function returnMutexesName()
		{
			return '${mutexesOrderName}';
		}
		
		$(function(){
			$("#tabs").tabs();
			var orderSn = returnorder();
			var reportSn = returnsn();
			var commonSn ;
			if(getOrderSn() == "")
			{
				commonSn = reportSn;
				closeTab("#moreTabs", reportSn);
			}else
			{
				commonSn = orderSn ;
				closeTab("#moreTabs", orderSn);
			}
			$("#_"+commonSn).tabs('select', 'tabs-2');
			$("#_"+commonSn+" td[name='patientName']").html(getPatientName());
			$("#_"+commonSn+" td[name='patientGenderName']").html(getPatientGender());
			$("#_"+commonSn+" td[name='patientAge']").html(getPatientAge());
			$("#_"+commonSn+" td[name='inpatientNo']").html(getInpatientNo());
			

   			if(${!empty examDetail}){
   				//var examReport = $("#examReport_"+'${reportSn}' + '${itemClass}' + '${itemSn}');
   				//alert("#examReport_"+'${examOrderDetail.orderSn}_'+'${reportSn}_' + '${itemSn}');
   				var examReport = $("#examReport_"+'${examOrderDetail.orderSn}_'+'${reportSn}_' + '${itemSn}');
    			examReport.load("../exam/examReportDetail.html?rand=" + Math.random(),{"documentSn":'${examDetail[0].documentSn}',"dataSourceType":'${examDetail[0].dataSourceType}'},function(data){
					if(data=='reportFormatError'){
						$(this).empty().append("<div style='font-size:20px;font-weight:bold;text-align:center;'>该检查报告没有对应的显示形式。</div>");
					}
				});   
			}
			
		/* $[BUG]0035740 MODIFY END*/	
	    	if(${!empty examOrderDetail && Constants.TEMPORARY_FLAG==examOrderDetail.temporaryFlag }){
			    /* var examCPOEPattern = getExamCPOEPattern(${orderStepDtos},'${patientDomain}','${Constants.PATIENT_DOMAIN_OUTPATIENT}','${Constants.PATIENT_DOMAIN_INPATIENT}');
			    cpoeTabs('#_'+commonSn,'#canvas_'+commonSn,'#animateButton_'+commonSn,examCPOEPattern,'tabs-4',${withdrawFlag==Constants.REPORT_WITHDRAW_FLAG}); */
	    		/* var examCPOEPattern = gd_getExamCPOEPattern(${orderStepDtos},'${visitType}','${Constants.VISIT_TYPE_CODE_OUTPATIENT}','${Constants.VISIT_TYPE_CODE_INPATIENT}',${itemClass},'${Constants.ORDER_EXEC_ULTRASONIC_ALL}','${Constants.ORDER_EXEC_ERADIATION_ALL}','${Constants.ORDER_EXEC_ENDOSCOPE_ALL}',${cancelExam});
			    cpoeTabs('#_'+commonSn,'#canvas_'+commonSn,'#animateButton_'+commonSn,examCPOEPattern,'tabs-4',${withdrawFlag==Constants.REPORT_WITHDRAW_FLAG}); */
	    		var examCPOEPattern = gd_getExamCPOEPattern(${orderStepDtos},'${visitType}','${Constants.VISIT_TYPE_CODE_OUTPATIENT}','${Constants.VISIT_TYPE_CODE_INPATIENT}',${ecgOrPathology},${cancelExam});
			    cpoeTabs('#_'+commonSn,'#canvas_'+commonSn,'#animateButton_'+commonSn,examCPOEPattern,'tabs-4',${withdrawFlag==Constants.REPORT_WITHDRAW_FLAG});
			    
		    }
	    });
	</script>
</head>
<body>
	<div id="dialog">
		<div id="mainContent">
			<div style="word-break:break-all;word-wrap:break-word;border:0px;" 
				<c:choose>
			    	<c:when test="${empty examOrderDetail.orderSn}">
			    		id="_${reportSn}${itemSn}"
			    	</c:when>
			    	<c:otherwise>
			    		 id="_${examOrderDetail.orderSn}_${reportSn}_${itemSn}" 
			    	</c:otherwise>
		    	</c:choose>				
				name="selectTabs">
				<ul>
				    <li><div class="tabseperator">&nbsp;&nbsp;</div></li>
					<c:if test="${!empty examOrderDetail && withdrawFlag!=Constants.REPORT_WITHDRAW_FLAG}">
						<li  class="headtitle"><a href="#tabs-1" class="lj" hidefocus="true">检查医嘱</a></li>
					</c:if>
					<c:if test="${!empty examOrderDetail && fn:length(examDetail)!=0 && withdrawFlag!=Constants.REPORT_WITHDRAW_FLAG}">
						<li><div class="tabseperator"></div></li>
					</c:if>
					<c:if test="${fn:length(examDetail)!=0 && withdrawFlag!=Constants.REPORT_WITHDRAW_FLAG}">
						<li  class="headtitle"><a href="#tabs-2" class="lj">检查报告</a></li>
					</c:if>
					<c:if test="${fn:length(relatedExamDetail)!=0 && withdrawFlag!=Constants.REPORT_WITHDRAW_FLAG}">
						<li><div class="tabseperator"></div></li>
						<li  class="headtitle"><a href="#tabs-3" class="lj">关联检查</a></li>
					</c:if>
					<c:if test="${!empty examOrderDetail && Constants.TEMPORARY_FLAG==examOrderDetail.temporaryFlag }">
						<li><div class="tabseperator">&nbsp;&nbsp;</div></li>
						<li class="headtitle"><a href="#tabs-4" class="lj">医嘱跟踪</a></li>				
					</c:if>
				</ul>
				<c:if test="${!empty examOrderDetail && withdrawFlag!=Constants.REPORT_WITHDRAW_FLAG}">
					<div id="tabs-1" class="tabcontainer">
						<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
						    <tr>
								<td class="label">医嘱号:</td>
								<td class="dataitem">${examOrderDetail.orderLid}</td>
								<td class="label">医嘱状态:</td>
								<td class="dataitem">${examOrderDetail.orderStatusName}</td>
							</tr>
							<tr>
							<!-- 人民 -->
							<!--  
								<td class="label">检查项目:</td>
								<td class="dataitem">${examOrderDetail.itemName}</td>
							-->
							   <td class="label">医嘱描述:</td>
								<td class="dataitem">${examOrderDetail.orderDescribe}</td>
								<td class="label">检查部位:</td>
								<td class="dataitem">${examOrderDetail.regionName}</td>
							</tr>
							<tr>
								<td class="label">检查方法:</td>
								<td class="dataitem">${examOrderDetail.examMethodName}</td>
								<td class="label">医嘱类型:</td>
								<td class="dataitem">${examOrderDetail.orderTypeName}</td>
							</tr>
							<!--$Author : chang_xuewen
								$Date : 2013/8/13 11:00$
		                        [BUG]0035982 ADD BEGIN 
		                    -->
							<tr>
							    <td class="label">医嘱预定开始时间:</td>
								<td class="dataitem"><fmt:formatDate value="${examOrderDetail.orderStartTime}" type="both" pattern="yyyy-MM-dd HH:mm" /></td>
								<td class="label">医嘱停止时间:</td>
								<td class="dataitem"><fmt:formatDate value="${examOrderDetail.orderEndTime}" type="both" pattern="yyyy-MM-dd HH:mm" /></td>
							</tr>
							<!--[BUG]0035982 ADD END -->
							<tr>
							    <td class="label">执行频率:</td>
								<td class="dataitem">${examOrderDetail.execFreqName}</td>
								<td class="label">长期或临时:</td>
								<td class="dataitem"><ref:translate
									domain="${Constants.DOMAIN_TEMPORARY_FLAG}"
									code="${examOrderDetail.temporaryFlag}" /></td>
							</tr>
							<tr>
								<td class="label">下嘱科室:</td>
								<td class="dataitem">${examOrderDetail.orderDeptName}</td>
								<td class="label">执行科室:</td>
								<td class="dataitem">${examOrderDetail.execDeptName}</td>
							</tr>
							<tr>
								<td class="label">下嘱人:</td>
								<td class="dataitem">${examOrderDetail.orderPersonName}</td>
								<td class="label">医嘱录入时间:</td>
								<td class="dataitem"><fmt:formatDate value="${examOrderDetail.orderTime}" type="both" pattern="yyyy-MM-dd HH:mm" /></td>
							</tr>
							<tr>
								<td class="label">确认人:</td>
								<td class="dataitem">${examOrderDetail.confirmPersonName}</td>
								<td class="label">确认时间:</td>
								<td class="dataitem"><fmt:formatDate value="${examOrderDetail.confirmTime}" type="both" pattern="yyyy-MM-dd HH:mm" /></td>
							</tr>
							<tr>
								<td class="label">取消人:</td>
								<td class="dataitem">${examOrderDetail.cancelPersonName}</td>
								<td class="label">取消时间:</td>
								<td class="dataitem"><fmt:formatDate value="${examOrderDetail.cancelTime}" type="both" pattern="yyyy-MM-dd HH:mm" /></td>
							</tr>
							<!--$Author : wu_jianfeng
								$Date : 2013/3/13 18:06$
		                        [BUG]0014531 MODIFY BEGIN 
		                    -->
		                    <!-- 江苏 -->
		                    <tr>
								<td class="label">标本号:</td>
								<td class="dataitem">${examOrderDetail.sampleNo}</td>
								<td class="label">医嘱交费状态:</td>
								<td class="dataitem">${examOrderDetail.chargeStatusName}</td>
<%-- 								<c:choose>
									<c:when test="${examOrderDetail.chargeStatusCode == Constants.CHARGE_STATUS_NOTCHARGE}">
										<td class="dataitem"></td>
									</c:when>
									<c:otherwise>
										<td class="dataitem">${examOrderDetail.chargeStatusName}</td>
									</c:otherwise>
								</c:choose> --%>
								
							</tr>
		                    
		                    <!--人民  -->
		                    <!-- 
							<tr>
								<td class="label">标本号:</td>
								<td class="dataitem">${examOrderDetail.sampleNo}</td>
								<td class="label">互斥医嘱:</td>
								<td class="dataitem">
									<c:choose>
									<c:when test="${mutexesOrderType != ''}">
									<a href="javascript:void(0);" onclick="showMutexesOrder(this,'${mutexesOrderType}','${mutexesOrderSn}','${mutexesOrderName}','${patientSn}','#moreTabs',event)"> 
										${mutexesOrderName}
									</a>
									</c:when>
									</c:choose>
								</td>
							</tr>
							 -->
							<!-- [BUG]0014531 MODIFY END -->
							<!-- 人民 -->
							<!-- 
							<tr>
								<td class="label">医嘱交费状态:</td>
								<c:choose>
									<c:when test="${examOrderDetail.chargeStatusCode == Constants.CHARGE_STATUS_NOTCHARGE}">
										<td class="dataitem"></td>
									</c:when>
									<c:otherwise>
										<td class="dataitem">${examOrderDetail.chargeStatusName}</td>
									</c:otherwise>
								</c:choose>
							 -->
							<!--$Author : chang_xuewen
								$Date : 2013/8/13 11:00$
		                        [BUG]0035982 ADD BEGIN 
		                    -->
		                    <!-- 人民 -->
		                    <!--  
								<td class="label">是否药观:</td>
								<td class="dataitem"><ref:translate
										domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
										code="${examOrderDetail.medViewFlag}" /></td>
							</tr>							
							<tr>
								<td class="label">是否皮试:</td>
								<td class="dataitem"><ref:translate
										domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
										code="${examOrderDetail.skinTestFlag}" /></td>
								<td class="label">是否加急:</td>
								<td class="dataitem"><ref:translate
										domain="${Constants.DOMAIN_BOOLEAN_UNCERTAIN}"
										code="${examOrderDetail.urgentFlag}" /></td>
						    </tr>
						    -->
						    <!--[BUG]0035982 ADD END-->
						   	<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
							    <tr>
							    	<td class="label">医疗机构:</td>
	                                <td class="dataitem"><ref:translate
	                                        domain="${Constants.DOMAIN_ORG_CODE}"
	                                        code="${examOrderDetail.orgCode}" /></td>
							    	
							    	<td class="label"></td>
	                                <td class="dataitem"></td>
							    </tr>
						    </c:if>
						<tr>
						<td colspan="4" height="10"></td>
					    </tr>
						</table>
						<table width="100%" cellpadding="2" cellspacing="1" style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
							<tr>
								<td class="blockHeader" colspan="4" height="27" align="left"
									style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">
									申请单信息</td>
							</tr>
							<tr class="odd">
								<td class="label">申请单编号:</td>
								<td class="dataitem">${examOrderDetail.requestNo}</td>
								<td class="label">申请日期:</td>
								<td class="dataitem"><fmt:formatDate value="${examOrderDetail.requestDate}" type="both" pattern="yyyy-MM-dd HH:mm" /></td>
							</tr>
							<tr>
								<td class="label">诊断:</td>
								<td class="dataitem">${examOrderDetail.diagnosis}</td>
								<td class="label">申请原因:</td>
								<td class="dataitem">${examOrderDetail.requestReason}</td>
							</tr>
							<tr>
								<td class="label">详细信息:</td>
								<td class="dataitem" colspan="3">${examOrderDetail.requestDetails}</td>
							</tr>
						</table>
					</div>
				</c:if>
				<c:if test="${fn:length(examDetail)!=0 && withdrawFlag!=Constants.REPORT_WITHDRAW_FLAG}">
					<div id="tabs-2" class="tabcontainer">
						<div id="examReport_${examOrderDetail.orderSn}_${reportSn}_${itemSn}"> </div>
					</div>
				</c:if>
				<c:if test="${fn:length(relatedExamDetail)!=0 && withdrawFlag!=Constants.REPORT_WITHDRAW_FLAG}">
					<div id="tabs-3" class="tabcontainer">
						<table cellpadding="2" cellspacing="1" class="table" width="100%"
							align="center">
							<tr class="tabletitle">
								<td height="28" align="center">检查项目名</td>
								<td height="28" align="center">检查部位</td>
								<td height="28" align="center">检查医生</td>
								<td height="28" align="center">检查日期</td>
								<td height="28" align="center">报告医生</td>
								<td height="28" align="center">报告日期</td>
								<!-- <td height="28" align="center">审核医生</td>
					<td height="28" align="center">审核日期</td> -->
								<td height="28" align="center">检查科室</td>
								<td height="28" align="center">检查方法</td>
								<td height="28" align="center">检查结果</td>
							</tr>
							<c:if test="${fn:length(relatedExamDetail)==0}">
								<tr>
									<td colspan="9" align="center" class="odd" height="24">没有相关数据！</td>
								</tr>
							</c:if>
							<c:forEach items="${relatedExamDetail}" var="relatedExamDetail"
								varStatus="status">
								<tr height="24"
									<c:choose>
								    	<c:when test="${relatedExamDetail.withdrawFlag==Constants.REPORT_WITHDRAW_FLAG}">class="forbidden"</c:when>
								    	<c:otherwise>
								    		 <c:if test="${status.count%2==1 }">class="odd"</c:if>
								    		 <c:if test="${status.count%2==0 }">class="even"</c:if>
								    	</c:otherwise>
								    </c:choose>>
									<td height="24" align="center">${relatedExamDetail.examinationItemName}</td>
									<td height="24" align="center">${relatedExamDetail.examinationRegionName}</td>
									<td height="24" align="center">${relatedExamDetail.examiningDoctorName}</td>
									<td height="24" align="center"><fmt:formatDate
									value="${relatedExamDetail.examinationDate}" type="both"
									pattern="yyyy-MM-dd HH:mm" /></td>
									<td height="24" align="center">${relatedExamDetail.reportDoctorName}</td>
									<td height="24" align="center"><fmt:formatDate
									value="${relatedExamDetail.reportDate}" type="both"
									pattern="yyyy-MM-dd HH:mm" /></td>
									<%-- 
									<td height="24" align="center">${relatedExamDetail.reviewDoctorName}</td>
									<td height="24" align="center">${fn:substring(relatedExamDetail.reviewDate,0,10)}</td> 
									--%>
									<td height="24" align="center">${relatedExamDetail.examDeptName}</td>
									<td height="24" align="center">${relatedExamDetail.examinationMethodName}</td>
									<%-- 
									<td height="24" align="center">${relatedExamDetail.eiImagingFinding==null?relatedExamDetail.erImagingFinding:relatedExamDetail.eiImagingFinding}</td>
									<td height="24" align="center">${relatedExamDetail.eiImagingConclusion==null?relatedExamDetail.erImagingConclusion:relatedExamDetail.eiImagingConclusion}</td> 
									--%>
									<td height="24" align="left">
										${relatedExamDetail.eiImagingConclusion==null?relatedExamDetail.erImagingConclusion:relatedExamDetail.eiImagingConclusion}
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</c:if>
				<c:choose>
					<c:when test="${!empty examOrderDetail && Constants.TEMPORARY_FLAG==examOrderDetail.temporaryFlag }">
						<div id="tabs-4" class="tabcontainer">
							<div><input type="button" value="观看路径动画" id="animateButton_${examOrderDetail.orderSn}_${reportSn}_${itemSn}" /></div>
							<div id="canvas_${examOrderDetail.orderSn}_${reportSn}_${itemSn}"></div>
						</div>
					</c:when>
					<c:when test="${(empty examOrderDetail || Constants.TEMPORARY_FLAG!=examOrderDetail.temporaryFlag) && Constants.REPORT_WITHDRAW_FLAG==withdrawFlag}">
						<h2 align="center">该医嘱不存在或为长期医嘱，故没有对应的医嘱跟踪信息。</h2>
					</c:when>
				</c:choose>
			</div>
		</div>
	</div>
</body>
</html>
									