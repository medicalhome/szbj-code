﻿<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>检验列表</title>
<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
<link type="text/css" rel="Stylesheet" href="../styles/jquery-ui-1.8.18.custom.modify.css"
        charset="UTF8" />
     <link type="text/css" rel="Stylesheet" href="../styles/jquery-ui-autocomplete.custom.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/jquery-ui-combobox.custom.modify.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/layout-default-1.3.0rc29.15.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/layout-cdr.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/tablelist.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/timer-shaft.css" charset="utf-8" />
    <link type="text/css" rel="Stylesheet" href="../styles/loadingScreen.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/jquery-suggest.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/tabs-menuPart.css" charset="UTF8" />
    <link type="text/css" rel="stylesheet" href="../styles/header.css" charset="UTF8"  />
	<script type="text/javascript" src="../scripts/jquery-1.7.1.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui-1.8.18.custom.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery.history.js"></script>
    <script type="text/javascript" src="../scripts/forwardOrBack/forwardOrBack.js"></script>
    <script type="text/javascript" src="../scripts/tabEnter/tabEnter.js"></script>
    <script type="text/javascript" src="../scripts/jquery.ui.autocomplete.js"></script>
    <script type="text/javascript" src="../scripts/jquery.ui.combobox.js"></script>
    
    <script type="text/javascript" src="../scripts/jquery.layout-1.3.0rc29.15.js"></script>
    <script type="text/javascript" src="../scripts/jquery.bgiframe.js"></script>
    <script type="text/javascript" src="../scripts/jquery.suggest.js"></script>
    <script type="text/javascript" src="../scripts/htmlSelectSuggest.js"></script>
    <script type="text/javascript" src="../scripts/htmlMultiSelectSuggest.js"></script>
    <script type="text/javascript" src="../scripts/loadingScreen.js"></script>
    <script type="text/javascript" src="../scripts/common.js"></script>
    <script type="text/javascript" src="../scripts/layout.js"></script>
    <script type="text/javascript" src="../scripts/visit/mainView.js"></script>
    <script type="text/javascript" src="../scripts/dialog-tabs.js"></script>
    <script type="text/javascript" src="../scripts/visit/normalViewPart.js"></script>
    <script type="text/javascript" src="../scripts/help/help.js"></script>

    <link rel="Stylesheet" type="text/css" href="../styles/Css.css" />
    <script language="javascript" src="../scripts/ProSlide.modify.js"></script>
    <script type="text/javascript" src="../scripts/timerInpatient/timerInpatient.js"></script>
<script type="text/javascript" src="../scripts/lab/labList.js"></script>
<script>
	$(function() {
		
		// 初始化带有datepicker样式的日期输入框
		setupDatePicker();
		setupDatePickerSetting();
		
		parent.condition();
		$(".datepicker").bind("blur",parent.isDateString);
		//添加页面文本框的回车检索
		$("#conditionForm input").keyup(function(event){
			if (event.keyCode==13) 
		    { 
				search('','conditionForm');
		    } 
		});

		// $Author:wu_jianfeng
		// $Date : 2012/10/24 14:10
		// $[BUG]0010542 MODIFY BEGIN
		$( "#labDept").htmlSelectSuggest({width:120, 
			onKeyUp: function(event){
				if (event.keyCode==13) 
		    	{ 
					search('','conditionForm');   									
				} 
			}
		});
		// $[BUG]0010542 MODIFY END
		
		// Author:jin_peng
	    // Date : 2014/1/2 15:58
	    // [BUG]0041392 ADD BEGIN
		$( "#orderDept").htmlSelectSuggest({width:140, 
			onKeyUp: function(event)
			{
				if (event.keyCode==13) 
		    	{ 
					search('', 'conditionForm');    									
				} 
			}
		});
		
		// [BUG]0041392 ADD END
		
		logical();
	});
</script>
</head>
<body style="margin: 0; padding: 0;">
<div id="dynamicContent">
<div id="alertMessage" style="display:none;"></div>
	<form id="conditionForm" name="conditionForm" method="post"
		action="../portalLab/list_${patientSn}_.html"
		<c:if test="${labListSearchParameters.visitSn != null}">style="display:none;"</c:if>>
		<table class="blockTable" cellpadding="2" cellspacing="1" >
			<tr style="height: 28px;" id="tableheader">
				<td  class="blockHeader" style="width: 60px; text-align: center;">姓名:
				</td>
				<td style="width: 60px; text-align: left;">${patient.patientName}</td>
				<td  class="blockHeader" style="width: 70px; text-align: center;">性别:
				</td>
				<td style="width: 60px; text-align: left;">${patient.genderName}</td>
				<td class="blockHeader" style="width: 70px; text-align: center;">年龄:</td>
				<td style="width: 60px; text-align: left;">${age}</td>
					<td class="blockHeader" style="width: 70px; text-align: center;">病历号:
				</td>
				<td style="width: 60px; text-align: left;">${patientCrossIndex.inpatientNo}</td>
				<td width="55%" ></td>
			</tr>
			<tr class="conditionRow">
				<td width="100%"  colspan="11" height="36" align="left" valign="middle">
				<input type="hidden" id="visitTimes" name ="visitTimes" value ="${labListSearchParameters.visitTimes }"/>
				<input type="hidden" id="patientId" name ="patientId" value ="${patientCrossIndex.patientLid }"/>
				<input type="hidden" id="patientDomain" name ="patientDomain" value ="${patientCrossIndex.patientDomain }"/>
				<input type="hidden" id="viewId" name ="viewId" value ="${labListSearchParameters.viewId }"/>
				<input type="hidden" id="sourceSystemId" name ="sourceSystemId" value ="${labListSearchParameters.sourceSystemId }"/>
				
				<div class="cell" style="width: 60px; text-align: right;">申请日期</div>
					<div class="cell">
						<input id="requestDateFrom" name="requestDateFrom"
							style="width: 80px;" class="datepicker"
							value="${labListSearchParameters.requestDateFrom}" /> <span
							style="margin: 0 4px 0 4px;">--</span> <input id="requestDateTo"
							name="requestDateTo" style="width: 80px;" class="datepicker"
							value="${labListSearchParameters.requestDateTo }" />
					</div>
					<div class="cell" style="width: 60px; text-align: right;">检验名称</div>
					<div class="cell" style="width: 120px;">
						<input type='text' id="itemName"name="itemName" style="width: 110px;"
						    onmouseover="this.style.background='#FDE8FE';"							
							onmouseout="this.style.background='#FFFFFF'"
							value="${labListSearchParameters.itemName}" />
					</div>
					
					
					<div class="cell" style="width: 70px; text-align: center;">
						<input type="button"
							style="color: #464646; border: 0px; width: 57px; height: 24px; margin-top: 3px; cursor: pointer;"
							onclick="search('','conditionForm');" value="搜 索" />
						
					</div>
					<div id="toggleBlock" class="container-on cell">
						<input type="button"
							style="color: #464646; border: 0px; width: 57px; height: 24px; margin-top: 3px; cursor: pointer;"
							onclick="emrClear('','conditionForm');" value="清除" />
					</div>
					</td>
			</tr>						
		</table>
	</form>		
	<div style="width:45%;float:left;">
		<!-- $Author :wang_yanbo
			 $Date : 2014/08/28 16:00
			 $[BUG]0048052 MODIFY BEGIN -->
		<table  style="border: solid 1px #dbe7f1;" id="tblid" style="width: 100%;" cellspacing="1" cellpadding="2" class="blockTable">
			<!-- $[BUG]0048052 MODIFY END -->
				<tr class="blockHeader" >
					<td height="28" width="12%" align="center">报告来源</td>
					<td height="28" align="center" width="12%">检验名称</td>
					<td height="28" width="20%" align="center">检查科室</td>
					<td height="28" width="12%" align="center">申请日期</td>
				</tr>
				<c:if test="${fn:length(labList)==0}">
					<tr>
						<td colspan="2" align="center" class="odd" height="24">没有相关数据！</td>
					</tr>
				</c:if>
				<c:forEach items="${labList}" var="lab" varStatus="status">
					<!-- $Author :chang_xuewen
					$Date : 2013/07/04 11:00
					$[BUG]0033461 MODIFY BEGIN -->
						<tr style="border:1px solid #dbe7f1;"
						id='${lab.orderSn}' style="cursor: pointer" onmouseover="this.className='mouseover'"
							onmouseout="this.className='mouseout'"
						<c:if test="${labListSearchParameters.visitSn != null}">onclick="showLabDetailDialog2('${lab.withdrawFlag}','${lab.labReportSn}','${lab.compositeItemSn}','${patientSn}','${lab.itemCode}','${lab.sourceSystemId}','${lab.orderSn}');"</c:if>
						onclick="showEmrLabDetailDialog('${lab.withdrawFlag}','${lab.labReportSn}','${lab.compositeItemSn}','${patientSn}','${lab.itemCode}','${lab.sourceSystemId}','${lab.orderSn}',$(this),2,'${lab.itemName}');" >
						<!--$[BUG]0033461 MODIFY END -->
						<!-- $Author :wang_yanbo
							 $Date : 2014/08/28 16:00
							 $[BUG]0048052 MODIFY BEGIN -->
						<c:if test="${lab.patientDomain=='01' }">
							<td style="border:1px solid #dbe7f1;" height="24" width="14%" align="left">门诊</td> 
						</c:if>
						<c:if test="${lab.patientDomain=='02' }"> 
							<td style="border:1px solid #dbe7f1;" height="24" width="14%" align="left">住院</td>
						</c:if>
						<c:if test="${lab.patientDomain==null }"> 
							<td style="border:1px solid #dbe7f1;" height="24" width="14%" align="left"></td>
						</c:if>
							<!-- $[BUG]0048052 MODIFY END -->
						<td style="border:1px solid #dbe7f1;" height="24" width="14%" align="left" <c:if test="${fn:length(lab.itemName)>10}"> title="${lab.itemName}" </c:if>>
						<c:if test="${fn:length(lab.itemName)>10}">${fn:substring(lab.itemName,0,10)}...</c:if>
		                <c:if test="${fn:length(lab.itemName)<=10}">${lab.itemName}</c:if></td>
		                <td  style="border:1px solid #dbe7f1;" height="24" width="8%" align="left">${lab.labDeptName}</td>
						<td  style="border:1px solid #dbe7f1;" height="24" width="8%" align="left"><fmt:formatDate value="${lab.requestDate}" type="date" pattern="yyyy-MM-dd"/></td>
					</tr>
				</c:forEach>
		<tr>
			<td colspan="4" style="height: 27px;">
				<form name="pagingform" method="get"
					action="../portalExam/list_${patientSn}_.html">
					<div class="pagelinks">
						<div style="float: left; height: 100%;">
							<div class="pagedesc">共${pagingContext.totalRowCnt}条记录！
								第${pagingContext.pageNo}页/共${pagingContext.totalPageCnt}页</div>
							<c:if test="${pagingContext.pageNo > 1}">
								<div class="firstPage">
									<a href="javascript:void(0);"
										<c:if test="${ExamListSearchParameters.visitSn != null}">onclick="jumpToPage(1,'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(1);return false;"><img
										src="../images/1.gif"
										style="border: 0px; width: 22px; height: 16px;" /></a>
								</div>
								<div class="prevPage">
									<a href="javascript:void(0);"
										<c:if test="${ExamListSearchParameters.visitSn != null}">onclick="jumpToPage(${pagingContext.pageNo-1},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.pageNo-1});return false;"><img
										src="../images/2.gif"
										style="border: 0px; width: 22px; height: 16px;" /></a>
								</div>
							</c:if>
							<c:forEach var="i" begin="${pagingContext.pageStartNo}" end="${pagingContext.perPageCnt}"
								step="1">
								<c:choose>
									<c:when test="${i == pagingContext.pageNo}">
										<div class="currentPage">
											<font color="#2D56A5">${i}</font>
										</div>
									</c:when>
									<c:otherwise>
										<div class="pageno">
											<a href="javascript:void(0);"
												<c:if test="${ExamListSearchParameters.visitSn != null}">onclick="jumpToPage(${i},'#ajaxDialog');return false;"</c:if>
												onclick="jumpToPage(${i}); return false;"><font
												color="#2D56A5">${i}</font></a>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
								<div class="nextPage">
									<a href="javascript:void(0);"
										<c:if test="${ExamListSearchParameters.visitSn != null}">onclick="jumpToPage(${pagingContext.pageNo + 1},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.pageNo + 1});return false;"><img
										src="../images/4.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
								<div class="lastPage">
									<a href="javascript:void(0);"
										<c:if test="${ExamListSearchParameters.visitSn != null}">onclick="jumpToPage(${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.totalPageCnt});return false;"><img
										src="../images/3.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
							</c:if>
							<div class="pageNum">
								<input type="text" name="screen" style="display:none;"/>
								<input name="pageNum"
									<c:if test="${ExamListSearchParameters.visitSn != null}">onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
								    onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"
								    style="width: 30px; float: left;" value="" />
							</div>
							<div class="goinput">
								<a href="javascript:void(0);"
									<c:if test="${ExamListSearchParameters.visitSn != null}">onclick="jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
									onclick="jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"><img
									src="../images/go.gif"
									style="border: 0px; width: 21px; height: 15px;" /></a>
							</div>
						</div>
					</div>
				</form>
			</td>
		</tr>
	</table>
	</div> 
	<div style="width:55%;height:50%;float:right;background-color:#fff;" id="portalDetail">
		<table style="border:1px solid #dbe7f1;width:100%">
			<tr class="" style="height:24px;">
				<td>&nbsp;</td>
			</tr>
		</table>
	</div> 
	</div>
</body>
</html>
