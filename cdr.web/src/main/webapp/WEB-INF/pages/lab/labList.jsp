﻿﻿<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
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
<link type="text/css" rel="Stylesheet" href="../styles/layout.css" charset="UTF8" />
<link type="text/css" rel="Stylesheet" href="../styles/table.css" charset="UTF8" />
<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
<script type="text/javascript" src="../scripts/lab/labList.js"></script>
<script>
	$(function() {
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
	<form id="conditionForm" name="conditionForm" method="post"
		action="../lab/list_${patientSn}_${labListSearchParameters.orderExecId}.html"
		<c:if test="${labListSearchParameters.visitSn != null}">style="display:none;"</c:if>>
		<table class="blockTable" cellpadding="2" cellspacing="1" border="1px"
			bordercolor="#BFD9F0">
			<tr style="height: 28px;"  id="tableheader">
				<td colspan="12" class="blockHeader" height="27" align="left"
					style="border-top: solid 1px #B3C4D4;"><b><img
						src="../images/pic_jiany.png"
						style="padding-left: 3px; padding-right: 1px;" width="19"
						height="20" alt="" align="absmiddle" /> <%-- <c:choose>
							<c:when test="${isDeptNevFlag==0}">检验</c:when>
							<c:when test="${isDeptNevFlag==1&&labListSearchParameters.labDept!=null}"><ref:translate domain="${Constants.DOMAIN_DEPARTMENT}" code="${labListSearchParameters.labDept}"/></c:when>
							<c:otherwise>其他</c:otherwise>
						</c:choose> --%>${leftTopTitle}</b>
				</td>
			</tr>
			<tr class="conditionRow">
				<td width="100%"  colspan="12" height="36" align="left" valign="middle">
				
					<div class="cell" style="width: 60px; text-align: right;">执行科室</div>
	                <!-- $Author :wu_jianfeng
	 					 $Date : 2012/10/24 17:08$
						 [BUG]0010542 MODIFY BEGIN -->
					<div style="float: left; margin-top: 4px; width: 120px;">
						<select name="labDept" id="labDept" style="width: 110px;">
							<option value="${Constants.OPTION_SELECT}">选择科室类型</option>
							<html2:pycodeoptions domain="${Constants.DOMAIN_DEPARTMENT}"
								value="${labListSearchParameters.labDept}" />
						</select>
					</div> <!-- [BUG]0010542 MODIFY END -->
					<div class="cell" style="width: 60px; text-align: right;">检验名称</div>
					<div class="cell" style="width: 120px;">
						<input type='text' onmouseover="this.style.background='#FDE8FE';"
							style="width: 110px;"
							onmouseout="this.style.background='#FFFFFF'" name="itemName"
							value="${labListSearchParameters.itemName}" />
					</div>
				<!-- $Author :chang_xuewen
 					 $Date : 2013/12/16 10:00$
					 [BUG]0040770 MODIFY BEGIN -->
				<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
					<div class="cell" align="left" style="padding-right: 2px;">医疗机构</div>
					<div class="cell" style=" text-align: left; left;margin-top:4px;width: 140px;">
						<select id="orgCodeFlag" name="orgCodeFlag" style="width:130px;">
							<option value="${Constants.OPTION_SELECT}">请选择</option>
							<html:options domain="${Constants.DOMAIN_ORG_CODE}"
							value='${orgCode}' />						
						</select>
					</div>
				</c:if>
				<!-- 	<div class="cell" style="width: 60px; text-align: right;">检验日期</div>
					<div class="cell">
						<input id="testDateFrom" name="testDateFrom" style="width: 90px;"
							class="datepicker"
							value="${labListSearchParameters.testDateFrom}" /> <span
							style="margin: 0 4px 0 4px;">--</span> <input id="testDateTo"
							name="testDateTo" style="width: 90px;" class="datepicker"
							value="${labListSearchParameters.testDateTo}" />
					</div> -->
					<div class="cell" style="width: 70px; text-align: center;">
						<input type="button"
							style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/5.jpg); width: 57px; height: 25px; margin-top: 3px; cursor: pointer;"
							onclick="search('','conditionForm');" align="absmiddle" />
					</div>
					<div id="toggleBlock" class="container-on cell">
						<input type="button" id="buttonBlock"
							style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/6.jpg); width: 77px; height: 25px; margin-top: 3px; cursor: pointer;"
							onclick="showTr('tr1',this)" align="absmiddle" /> <input
							type="hidden" id="conditionValue" name="conditionValue"
							value="${conditionValue}" /> <input type="hidden" id="senSave"
							name="senSave" value="${senSave}" /> <input type="hidden"
							name="type" value="${labListSearchParameters.type}" /> <input
							type="hidden" name="visitSn"
							value="${labListSearchParameters.visitSn}" /> <input
							type="hidden" name="inpatientDate"
							value="${labListSearchParameters.inpatientDate}" /><input
							type="hidden" name="leftTopTitle"
							value="${leftTopTitle}" />
					</div></td>
			<!--</tr>
			<tr class="conditionRow">
				<td width="100%"  colspan="12" height="36" align="left" valign="middle">
					
				</td>
			</tr>  -->
			<tr id="tr1" class="conditionRow moreCondition off">
				<td width="100%" colspan="12" height="36" align="left" valign="middle">
					<div class="cell" style="width: 60px; text-align: right;">检验医生</div>
					<div class="cell" style="width: 120px;">
						<input type='text' onmouseover="this.style.background='#FDE8FE';"
							style="width: 110px;"
							onmouseout="this.style.background='#FFFFFF'" name="testerName"
							value="${labListSearchParameters.testerName}" />
					</div>
					<div class="cell" style="width: 60px; text-align: right;">报告医生</div>
					<div class="cell" style="width: 100px;">
						<input type='text' onmouseover="this.style.background='#FDE8FE';"
							style="width: 90px;" onmouseout="this.style.background='#FFFFFF'"
							name="reporterName"
							value="${labListSearchParameters.reporterName}" />
					</div>
					<div class="cell" style="width: 60px; text-align: right;">报告日期</div>
					<div class="cell">
						<input id="reportDateFrom" name="reportDateFrom"
							style="width: 80px;" class="datepicker"
							value="${labListSearchParameters.reportDateFrom}" /> <span
							style="margin: 0 4px 0 4px;">--</span> <input id="reportDateTo"
							name="reportDateTo" style="width: 80px;" class="datepicker"
							value="${labListSearchParameters.reportDateTo }" />
					</div>
					<div class="cell" style="width: 180px;">
						<div class="cell" style="width: 55px; text-align: right">开医嘱人</div>
						<div class="cell" style="width: 110px;">
							<input type="text" id="orderPersonName" name="orderPersonName"
								style="width:100px;"
								onmouseover="this.style.background='#FDE8FE';"
								onmouseout="this.style.background='#FFFFFF'"
								value="${labListSearchParameters.orderPersonName}" />
						</div>
					</div>
					<div class="cell" style="width: 230px;">
						<div class="cell" style="width: 65px; text-align: right">医嘱开立科室</div>
						<div class="cell" style="width: 150px;">
							<select id="orderDept" name="orderDept" style="width: 140px;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html2:pycodeoptions domain="${Constants.DOMAIN_DEPARTMENT}"
									value="${labListSearchParameters.orderDept}" />
							</select>
						</div>
					</div>
				</td>
			</tr>
			<tr class="tabletitle">
			<td height="28" align="center" width="15%">检验名称</td>
			<td height="28" align="center" width="10%">医嘱状态</td>
			<td height="28" align="center" width="10%">申请日期</td>
			<td height="28" align="center" width="11%">执行科室</td>
			<td height="28" align="center" width="10%">检验医生</td>
		<!-- 	<td height="28" align="center" width="10%">检验日期</td> -->
			<td height="28" align="center" width="10%">报告医生</td>
			<td height="28" align="center" width="10%">报告日期</td>
			<td height="28" align="center" width="10%">召回状态</td>
			<c:if test="${Constants.COMM_INTERFACE == Constants.ELECTRONIC_SIGNATURE}">
				<td height="28" align="center" width="8%">签章报告</td>
			</c:if>
			<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
				<td height="28" align="center" width="9%">医疗机构</td>
			</c:if>
			<!-- 
			  $Author :yang_mingjie
        	  $Date : 2014/07/15 15:05$
        	  [BUG]0045623 MODIFY BEGIN 
			 -->
			<td height="28" align="center" width="14%">标本类型</td>
			<!-- [BUG]0045623 MODIFY END -->
		</tr>
		</table>
	</form>
	<div id='listcon'>
	<!--[if lt ie 8]><div style='+zoom:1;'><![endif]-->
	<table id="tblid" style="width: 100%;" cellspacing="1" cellpadding="2"
		border="0" class="table">
		<c:if test="${fn:length(labList)==0}">
			<tr>
				<td colspan="12" align="center" class="odd" height="24">没有相关数据！</td>
			</tr>
		</c:if>
		<c:forEach items="${labList}" var="lab" varStatus="status">
			<!-- $Author :chang_xuewen
			$Date : 2013/07/04 11:00
			$[BUG]0033461 MODIFY BEGIN -->
				<tr
					<c:choose>
			    	<c:when test="${empty lab.orderSn && lab.withdrawFlag==Constants.REPORT_WITHDRAW_FLAG}">
			    		class="forbidden tabEnter" onmouseout="this.className='forbidden'" onmouseover="this.className='forbidden'"
			    	</c:when>
			    	<c:when test="${empty lab.orderSn && lab.reviewerId=='000'}">
			    		class="tempReport tabEnter" onmouseout="this.className='tempReport'"
			    	</c:when>
			    	<c:when test="${empty lab.labReportSn}">
						class="orderOnly tabEnter" onmouseout="this.className='orderOnly'" 
					</c:when>
					<c:when test="${empty lab.orderSn}">
						class="reportOnly tabEnter" onmouseout="this.className='reportOnly'" 
					</c:when>
			    	<c:otherwise>
			    		 <c:if test="${status.count%2==1 }">class="odd tabEnter" onmouseout="this.className='odd'"</c:if>
			    		 <c:if test="${status.count%2==0 }">class="even tabEnter" onmouseout="this.className='even'"</c:if>
			    	</c:otherwise>
		        </c:choose>
				id='${lab.orderSn}' style="cursor: pointer" onmouseover="this.className='mouseover'"
				<c:if test="${labListSearchParameters.visitSn != null}">onclick="showLabDetailDialog2('${lab.withdrawFlag}','${lab.labReportSn}','${lab.compositeItemSn}','${patientSn}','${lab.itemCode}','${lab.sourceSystemId}','${lab.orderSn}');"</c:if>
				onclick="showLabDetailDialog('${lab.withdrawFlag}','${lab.labReportSn}','${lab.compositeItemSn}','${patientSn}','${lab.itemCode}','${lab.sourceSystemId}','${lab.orderSn}',$(this),2,'${lab.itemName}');" >
				<!--$[BUG]0033461 MODIFY END -->
				<td height="24" width="15%" align="center" <c:if test="${fn:length(lab.itemName)>10}"> title="${lab.itemName}" </c:if>>
				<c:if test="${fn:length(lab.itemName)>10}">${fn:substring(lab.itemName,0,10)}...</c:if>
                <c:if test="${fn:length(lab.itemName)<=10}">${lab.itemName}</c:if></td>
				<td height="24" width="10%" align="center">${lab.orderStatusName}</td>
				<td height="24" width="10%" align="center"><fmt:formatDate
						value="${lab.requestDate}" type="date" pattern="yyyy-MM-dd" /></td>
				<td height="24" width="11%" align="center">${lab.labDeptName}</td>
				<td height="24" width="10%" align="center">${lab.testerName}</td>
		<!-- 	<td height="24" width="10%" align="center"><fmt:formatDate
						value="${lab.testDate}" type="date" pattern="yyyy-MM-dd" /></td> -->
				<td height="24" width="10%" align="center">${lab.reporterName}</td>
				<td height="24" width="10%" align="center"><fmt:formatDate
						value="${lab.reportDate}" type="date" pattern="yyyy-MM-dd" /></td>
				<td height="24" width="10%" align="center">
					<a href="javascript:void(0);"
						<c:if test="${labListSearchParameters.visitSn != null}">onclick="showDialog('../exam/withdraw_${lab.labReportSn}.html','召回信息', {}, 700, 500, '#ajaxDialogDetail');stopBubble(event);return false;"</c:if>
						onclick="showDialog('../exam/withdraw_${lab.labReportSn}.html','召回信息', {});stopBubble(event);return false;">
							<c:if test="${lab.withdrawFlag==Constants.REPORT_WITHDRAW_FLAG}">
								<img src="../images/jczh.png" align="middle"
									style="padding-right: 1px; border: 0px" />
							</c:if> <c:if test="${lab.withdrawFlag==Constants.REPORT_MODIFY_FLAG}">
								<img src="../images/yxg.png" align="middle"
									style="padding-right: 1px; border: 0px" />
							</c:if> </a></td>
					<c:if test="${Constants.COMM_INTERFACE == Constants.ELECTRONIC_SIGNATURE}">
						<td height="24" width="8%" align="center"
							<c:if test="${empty lab.signatureId}"> onclick="stopBubble(event);return false;"</c:if>>
							<c:if test="${!empty lab.signatureId}">
								<a href="javascript:void(0);"
									onclick="previewDoc('${signatureUrl}','${lab.signatureId}');return false;"><img
									src="../images/qzbg.png" align="middle"
									style="padding-right: 1px; border: 0px" /> </a>
							</c:if></td>
						</c:if>
						<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
							<td height="24" width=9%" align="center"><ref:translate domain="${Constants.DOMAIN_ORG_CODE}" code="${lab.orgCode}"/></td>
						</c:if>
			           <!--  $Author :yang_mingjie
        	            $Date : 2014/07/15 15:05$
        	            [BUG]0045623 MODIFY BEGIN --> 
						<td height="24" width=14%" align="center">${lab.sampleTypeName}</td>
				         <!-- [BUG]0045623 MODIFY END  -->
				</tr>
			</c:forEach>
			<tr>
				<td colspan="12" style="height: 27px;">
					<form name="pagingform" method="get"
						action="../lab/list_${patientSn}_.html">
						<div class="pagelinks">
							<div style="float: left; margin-top: 8px;">
								<div
									style="width: 10px; height: 10px; background-color: #D3F8F8; float: left; margin-right: 3px;" />
								<div style="float: left;">仅有医嘱信息</div>
								<div
									style="width: 10px; height: 10px; background-color: #E9D3F8; float: left; margin-right: 3px;" />
								<div style="float: left;">仅有报告信息</div>
								<div
									style="width: 10px; height: 10px; background-color: #FCE6C9; float: left; margin-right: 3px;" />
								<div style="float: left;">已召回报告</div>
								<div
									style="width: 10px; height: 10px; background-color: #D1E097; float: left; margin-right: 3px;" />
								<div style="float: left;">临时报告</div>
							</div>
							<!--[if lt ie 8]></div><div class="pagelinks"><![endif]-->
							<div style="float: right; height: 100%;">
								<div class="pagedesc">共${pagingContext.totalRowCnt}条记录！
									第${pagingContext.pageNo}页/共${pagingContext.totalPageCnt}页</div>
								<c:if test="${pagingContext.pageNo > 1}">
									<div class="firstPage">
										<a href="javascript:void(0);"
											<c:if test="${labListSearchParameters.visitSn != null}">onclick="jumpToPage(1,'#ajaxDialog');return false;"</c:if>
											onclick="jumpToPage(1);return false;"><img
											src="../images/1.gif"
											style="border: 0px; width: 21px; height: 16px;" /> </a>
									</div>
									<div class="prevPage">
										<a href="javascript:void(0);"
											<c:if test="${labListSearchParameters.visitSn != null}">onclick="jumpToPage(${pagingContext.pageNo-1},'#ajaxDialog');return false;"</c:if>
											onclick="jumpToPage(${pagingContext.pageNo-1});return false;"><img
											src="../images/2.gif"
											style="border: 0px; width: 41px; height: 16px;" /> </a>
									</div>
								</c:if>
								<c:forEach var="i" begin="${pagingContext.pageStartNo}"
									end="${pagingContext.perPageCnt}" step="1">
									<c:choose>
										<c:when test="${i == pagingContext.pageNo}">
											<div class="currentPage">
												<font color="#2D56A5">${i}</font>
											</div>
										</c:when>
										<c:otherwise>
											<div class="pageno">
												<a href="javascript:void(0);"
													<c:if test="${labListSearchParameters.visitSn != null}">onclick="jumpToPage(${i},'#ajaxDialog');return false;"</c:if>
													onclick="jumpToPage(${i}); return false;"><font
													color="#2D56A5">${i}</font> </a>
											</div>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<c:if
									test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
									<div class="nextPage">
										<a href="javascript:void(0);"
											<c:if test="${labListSearchParameters.visitSn != null}">onclick="jumpToPage(${pagingContext.pageNo + 1},'#ajaxDialog');return false;"</c:if>
											onclick="jumpToPage(${pagingContext.pageNo + 1});return false;"><img
											src="../images/4.gif"
											style="border: 0px; width: 41px; height: 16px;" /> </a>
									</div>
									<div class="lastPage">
										<a href="javascript:void(0);"
											<c:if test="${labListSearchParameters.visitSn != null}">onclick="jumpToPage(${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
											onclick="jumpToPage(${pagingContext.totalPageCnt});return false;"><img
											src="../images/3.gif"
											style="border: 0px; width: 21px; height: 16px;" /> </a>
									</div>
								</c:if>
								<div class="pageNum">
									<input type="text" name="screen" style="display: none;" /> <input
										name="pageNum"
										<c:if test="${labListSearchParameters.visitSn != null}">onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
										onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"
										style="width: 30px; float: left;" value="" />
								</div>
								<div class="goinput">
									<a href="javascript:void(0);"
										<c:if test="${labListSearchParameters.visitSn != null}">onclick="jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"><img
										src="../images/go.gif"
										style="border: 0px; width: 21px; height: 15px;" /> </a>
								</div>
							</div>
						</div>
						<div id="ajaxDialogDetail" style="display: none;">
							<iframe id="dialogFrameDetail" style="width: 100%; height: 100%;"
								src="" frameborder="0"> </iframe>
						</div>
					</form></td>
			</tr>
		</table>
		<!--[if lt ie 8]></div><![endif]-->
	</div>
</body>
</html>
