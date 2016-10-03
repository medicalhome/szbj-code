<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
	<script type="text/javascript" src="../scripts/order/nonDrugOrderList.js"></script>
	<script>
		$(function() {
			// $Author:wu_jianfeng
			// $Date : 2012/10/24 14:10
			// $[BUG]0010542 MODIFY BEGIN
			$( "#orderDept").htmlSelectSuggest({width:140, 
				onKeyUp: function(event){
					if (event.keyCode==13) 
			    	{ 
						search('', 'conditionForm');   									
					} 
				}
			});
	
			$( "#execDept").htmlSelectSuggest({width:140, 
				onKeyUp: function(event){
					if (event.keyCode==13) 
			    	{ 
						search('', 'conditionForm');									
					} 
				}
			});
			// $[BUG]0010542 MODIFY END			
			
			/* $Author :chang_xuewen
			 $Date : 2013/07/04 11:00
			 $[BUG]0033461 ADD BEGIN */
			//调用动态表格美化
			beautyTable();
			/* $[BUG]0033461 ADD BEGIN */
			
			
			logical();
		});

	</script>
</head>
<body style="margin: 0; padding: 0;">
	<form id="conditionForm" name="conditionForm" method="post" action="../order/list_${patientSn}.html" <c:if test="${nonDrugOrderPra.visitSn != null}">style="display:none;"</c:if>>
	<table class="blockTable" cellpadding="2" cellspacing="1" border="1px" bordercolor="#BFD9F0">
		<tr style="height: 28px;">
			<td class="blockHeader" colspan="15" height="27" align="left" style="border-top:solid 1px #B3C4D4;">
				<c:if test="${nonDrugOrderPra.orderType != Constants.CONSULTATION_ORDER}">
					<b><img src="../images/pic_fywyz.png" style="padding-left:3px; padding-right: 2px;" width="19" height="19" alt="" align="absmiddle" />其他医嘱</b>
				</c:if>
				<c:if test="${nonDrugOrderPra.orderType == Constants.CONSULTATION_ORDER}">
					<b><img
							src="../images/gm.png"
							style="padding-left: 5px; padding-right: 4px;" width="16"
							height="16" alt="" align="absmiddle" />会诊 </b>
				</c:if>
			</td>
		</tr>
		<tr class="conditionRow">
			<td width="100%" colspan="12" height="36" align="left" valign="middle">
				<div class="cell" style="width: 60px; text-align: right;">开嘱日期</div>
				<div class="cell">  
				<input id="orderStrStartTime" name="orderStrStartTime" style="width: 80px;" class="datepicker" value="${nonDrugOrderPra.orderStrStartTime}" /> <span style="margin: 0 4px 0 4px;">--</span> 
				<input id="orderStrEndTime" name="orderStrEndTime" style="width: 80px;" class="datepicker" value="${nonDrugOrderPra.orderStrEndTime}" /></div>
				<c:choose>
					<c:when test="${nonDrugOrderPra.orderType != Constants.CONSULTATION_ORDER}">
						<div class="cell" style="width: 55px; text-align: right;">医嘱类型</div>
						<div class="cell" style="width: 120px;">
								<select id="orderType" name="orderType" style="width:100%;">
									<option value="${Constants.OPTION_SELECT}">请选择</option>
						           	<html:options domain="${Constants.DOMAIN_ORDER_TYPE}" value="${nonDrugOrderPra.orderType}" />
								</select>
						</div>
					</c:when>
					<c:otherwise>
						<input type="hidden" name="orderType" value="${nonDrugOrderPra.orderType}" />
					</c:otherwise>
				</c:choose>
				<!-- $Author :jin_peng
					 $Date : 2012/11/22 14:00$
					 [BUG]0011026 MODIFY BEGIN -->
				<div class="cell" style="width: 60px; text-align: right;">就诊日期</div>
				<div class="cell">
					<select id="visitDateSn" name="visitDateSn" style="width:110px;">
						<option value="${Constants.OPTION_SELECT}">请选择</option>
						<c:forEach items="${nonDrugOrderPra.visitDateList}" var="vdl">
							<option value="${vdl.visitSn}" <c:if test="${vdl.visitSn == nonDrugOrderPra.visitDateSn}">selected</c:if>>${vdl.visitDate}</option>
						</c:forEach>
					</select>
				</div>
				<!-- [BUG]0011026 MODIFY END -->
				<!-- $Author :chang_xuewen
 					 $Date : 2013/12/16 10:00$
					 [BUG]0040770 MODIFY BEGIN -->
				<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
				<div class="cell" style="width: 80px; text-align: right;">医疗机构</div>
				<div style="float:left;margin-top:4px;width: 140px;">
					<select id="orgCodeFlag" name="orgCodeFlag" style="width:140px;">
						<option value="${Constants.OPTION_SELECT}">请选择</option>
						<html:options domain="${Constants.DOMAIN_ORG_CODE}"
						value='${nonDrugOrderPra.orgName}' />						
					</select>
				</div>
				</c:if>
                <!-- [BUG]0040770 MODIFY END -->
				<div class="cell" style="width: 70px; text-align: right;"><input type="button" onclick="search('', 'conditionForm');" style="color:#464646;border:0px;BACKGROUND-IMAGE:url(../images/5.jpg);width:57px;height:25px;margin-top:3px;cursor: pointer;"  align="absmiddle" /></div>
				<div id="toggleBlock" class="container-on cell">
                        <input type="button" id="buttonBlock" style="color:#464646;border:0px;BACKGROUND-IMAGE:url(../images/6.jpg);width:77px;height:25px;margin-top:3px;cursor: pointer;" onclick="showTr('tr1',this)"   align="absmiddle" />
						<input type="hidden" id="conditionValue" name="conditionValue" value="${conditionValue}"/>
						<input type="hidden" id="senSave" name="senSave" value="${senSave}"/>
						<input type="hidden" name="type" value="${nonDrugOrderPra.type}" />
						<input type="hidden" name="visitSn"
							value="${nonDrugOrderPra.visitSn}" /> <input type="hidden"
							name="inpatientDate" value="${nonDrugOrderPra.inpatientDate}" />
				</div>
			</td>
		</tr>
		<tr id='tr1' class="conditionRow moreCondition off">
			<td width="100%" colspan="11" height="36" align="left" valign="middle">
				<div class="cell" style="width: 60px; text-align: right;">开嘱科室</div>
                <!-- $Author :wu_jianfeng
 					 $Date : 2012/10/24 17:08$
					 [BUG]0010542 MODIFY BEGIN -->
				<div style="float:left;margin-top:4px;width: 140px;">
						<select id="orderDept" name="orderDept" style="width:140px;">
							<option value="${Constants.OPTION_SELECT}">请选择</option>
				           	<html2:pycodeoptions domain="${Constants.DOMAIN_DEPARTMENT}"
								value="${nonDrugOrderPra.orderDept}" />
						</select>
				</div>
                <!-- [BUG]0010542 MODIFY END -->
				<div class="cell" style="width: 50px; text-align: right;">开嘱人</div>
				<div class="cell" style="width: 80px;">
						<input type="text" id="orderPerson" name="orderPerson"  style="width:100%;"
									onmouseover="this.style.background='#FDE8FE';" onmouseout="this.style.background='#FFFFFF'" value="${nonDrugOrderPra.orderPerson}"/>
				</div>
				<div class="cell" style="width: 60px; text-align: right;">执行科室</div>
                <!-- $Author :wu_jianfeng
 					 $Date : 2012/10/24 17:08$
					 [BUG]0010542 MODIFY BEGIN -->
				<div style="float:left;margin-top:4px;width: 140px;">
						<select id="execDept" name="execDept" style="width:140px;">
							<option value="${Constants.OPTION_SELECT}">请选择</option>
				           	<html2:pycodeoptions domain="${Constants.DOMAIN_DEPARTMENT}"
								value="${nonDrugOrderPra.execDept}" />
						</select>
				</div>
                <!-- [BUG]0010542 MODIFY END -->
				<div class="cell" style="width: 60px; text-align: right;">项目名称</div>
				<div class="cell" style="width: 120px;">
						<input type="text" id="orderName" name="orderName" value="${nonDrugOrderPra.orderName}" style="width:100%"
									onmouseover="this.style.background='#FDE8FE';" onmouseout="this.style.background='#FFFFFF'"/>
				</div>
			</td>
		</tr>
		<!-- $Author :chang_xuewen
			$Date : 2013/07/04 11:00
			$[BUG]0033461 MODIFY BEGIN -->
		<tr class="tabletitle">
			<td height="28" align="center" width="5%">就诊类型</td>
			<td height="28" align="center" width="8%">病区</td>
			<td height="28" align="center" width="5%">医嘱类型</td>
			<td height="28" align="center" width="8%">项目名称</td>
			<td height="28" align="center" width="6%">长期或临时</td>
			<td height="28" align="center" width="13%">开嘱科室</td>
			<td height="28" align="center" width="13%">执行科室</td>
			<td height="28" align="center" width="5%">开嘱人</td>
			<!-- $Author :chang_xuewen
     			 $Date : 2013/10/31 11:00
     			 $[BUG]0038735 MODIFY BEGIN -->
			<td height="28" align="center" width="10%">医嘱录入时间</td>
			<!-- $[BUG]0038735 MODIFY END -->
			<!-- <td height="28" align="center" width="6%">确认人</td>
			<td height="28" align="center" width="8%">确认时间</td> -->
			<td height="28" align="center" width="8%">取消人</td>
			<td height="28" align="center" width="10%">取消时间</td>
			<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
			<td height="28" align="center" width="10%">医疗机构</td>
			</c:if>
		</tr>
		<!-- $[BUG]0033461 MODIFY END -->
	</table>
	</form>
	<div id='listcon'>
	<!--[if lt ie 8]><div style='+zoom:1;'><![endif]-->
	<table id="tblid" style="width: 100%;" cellspacing="1" cellpadding="2" border="0" class="table">		
		<c:if test="${fn:length(orderList)==0}">
						<tr>
							<td colspan="10" align="center" class="odd" height="24">没有相关数据！</td>
						</tr>
					</c:if>
		<c:forEach items="${orderList}" var="ord" varStatus="status">
		<!-- $Author :chang_xuewen
			 $Date : 2013/07/04 11:00
			 $[BUG]0033461 MODIFY BEGIN -->
			 <!-- $Author :chang_xuewen
			$Date : 2013/07/04 11:00
			$[BUG]0033461 MODIFY BEGIN -->
			<tr id="${ord.orderSn}" style="cursor:pointer" class="tabEnter"
				<c:if test="${nonDrugOrderPra.visitSn != null}">onclick="showDialog('${ord.orderPath}','${ord.orderTitle}',{},700,500,'#ajaxDialogDetail');"</c:if>
				onclick="bigShow($(this),'${ord.orderPath}',
{'name':'${fn:trim(fn:replace(ord.orderName,'\"','&quot;'))}','holdName':'${fn:trim(fn:replace(ord.orderName,'\"','&quot;'))}','otherName':'','holdOtherName':'','orderSn':'${ord.orderSn}'});">
			<!-- $[BUG]0033461 MODIFY END -->
		<!-- $[BUG]0033461 MODIFY END -->
				<c:if test="${fn:contains(lstOutDomian,ord.patientDomain) && fn:contains(lstOutVisitType,ord.visitTypeCode)}">
					<td height="24" width="5%" align="left" >门诊</td> 
				</c:if>
				<c:if test="${fn:contains(lstInDomian,ord.patientDomain) && fn:contains(lstInVisitType,ord.visitTypeCode)}"> 
					<td height="24" width="5%" align="left" >住院</td>
				</c:if>
				<td height="24" width="8%" align="left" style="padding-left:3px;">${ord.wardName}</td>
				<td height="24" width="5%" align="left">${ord.orderTypeName}</td>
				<td height="24" width="8%" align="left" <c:if test="${fn:length(ord.orderName)>8}"> title="${fn:trim(ord.orderName)}" </c:if>>
				<c:if test="${fn:length(fn:trim(ord.orderName))>8}">${fn:substring(ord.orderName,0,8)}...</c:if>
                <c:if test="${fn:length(fn:trim(ord.orderName))<=8}">${ord.orderName}</c:if></td>
				<td height="24" width="6%" align="center">
					<ref:translate domain="${Constants.DOMAIN_TEMPORARY_FLAG}" code="${ord.temporaryFlag}"/>
				</td>
				<td height="24" width="13%" align="left" <c:if test="${fn:length(ord.orderDeptName)>8}"> title="${ord.orderDeptName}" </c:if>>
				<c:if test="${fn:length(ord.orderDeptName)>8}">${fn:substring(ord.orderDeptName,0,8)}...</c:if>
                <c:if test="${fn:length(ord.orderDeptName)<=8}">${ord.orderDeptName}</c:if></td>
				<td height="24" width="13%" align="left" <c:if test="${fn:length(ord.execDeptName)>8}"> title="${ord.execDeptName}" </c:if>>
				<c:if test="${fn:length(ord.execDeptName)>8}">${fn:substring(ord.execDeptName,0,8)}...</c:if>
                <c:if test="${fn:length(ord.execDeptName)<=8}">${ord.execDeptName}</c:if></td>
				<td height="24" width="5%" align="left" style="padding-left:3px;">${ord.orderPersonName}</td>
				<td height="24" width="10%" align="center"><fmt:formatDate value="${ord.orderTime}" type="date" pattern="yyyy-MM-dd HH:mm" /></td>
				<%-- <td height="24" align="left" style="padding-left:6px;">${ord.confirmPersonName}</td>
				<td height="24" align="center"><fmt:formatDate value="${ord.confirmTime}" type="date" pattern="yyyy-MM-dd" /></td> --%>
				<td height="24" width="8%" align="left" style="padding-left:6px;">${ord.cancelPersonName}</td>
				<td height="24" width="10%" align="center"><fmt:formatDate value="${ord.cancelTime}" type="date" pattern="yyyy-MM-dd" /></td>
				<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
				<td height="24" width="10%" align="center"><ref:translate domain="${Constants.DOMAIN_ORG_CODE}" code="${ord.orgCode}"/></td>
				</c:if>
			</tr>
		</c:forEach>
		<!-- $Author :chang_xuewen
			 $Date : 2013/07/04 11:00
			 $[BUG]0033461 MODIFY BEGIN -->
		<tr class="page_line">
		<!-- $[BUG]0033461 MODIFY END -->
			<td colspan="12" style="height: 27px;">
				<form name="pagingform" method="get" action="../order/list_${patientSn}.html">
                					<div class="pagelinks">
					<div style="float: right; height: 100%;">
						<div class="pagedesc">共${pagingContext.totalRowCnt}条记录！      第${pagingContext.pageNo}页/共${pagingContext.totalPageCnt}页</div>
							<c:if test="${pagingContext.pageNo > 1}">
								<div class="firstPage">
									<a href="javascript:void(0);"
										<c:if test="${nonDrugOrderPra.visitSn != null}">onclick="jumpToPage(1,'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(1);return false;"><img
										src="../images/1.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
								<div class="prevPage">
									<a href="javascript:void(0);"
										<c:if test="${nonDrugOrderPra.visitSn != null}">onclick="jumpToPage(${pagingContext.pageNo-1},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.pageNo-1});return false;"><img
										src="../images/2.gif"
										style="border: 0px; width: 41px; height: 16px;" /></a>
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
												<c:if test="${nonDrugOrderPra.visitSn != null}">onclick="jumpToPage(${i},'#ajaxDialog');return false;"</c:if>
												onclick="jumpToPage(${i}); return false;"><font
												color="#2D56A5">${i}</font></a>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
								<div class="nextPage">
									<a href="javascript:void(0);"
										<c:if test="${nonDrugOrderPra.visitSn != null}">onclick="jumpToPage(${pagingContext.pageNo + 1},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.pageNo + 1});return false;"><img
										src="../images/4.gif"
										style="border: 0px; width: 41px; height: 16px;" /></a>
								</div>
								<div class="lastPage">
									<a href="javascript:void(0);"
										<c:if test="${nonDrugOrderPra.visitSn != null}">onclick="jumpToPage(${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
										onclick="jumpToPage(${pagingContext.totalPageCnt});return false;"><img
										src="../images/3.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
							</c:if>
							<div class="pageNum">
								<input type="text" name="screen" style="display:none;"/>
								<input name="pageNum"
									<c:if test="${nonDrugOrderPra.visitSn != null}">onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
								    onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"
								    style="width: 30px; float: left;" value="" />
							</div>
							<div class="goinput">
								<a href="javascript:void(0);"
									<c:if test="${nonDrugOrderPra.visitSn != null}">onclick="jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt},'#ajaxDialog');return false;"</c:if>
									onclick="jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"><img
									src="../images/go.gif"
									style="border: 0px; width: 21px; height: 15px;" /></a>
							</div>
							<div id="ajaxDialogDetail" style="display: none;">
								<iframe id="dialogFrameDetail" style="width: 100%; height: 100%;" src=""
									frameborder="0"> </iframe>
							</div>
						</div>
				</div>
				</form>
			</td>
		</tr>
	</table>
	<!--[if lt ie 8]></div><![endif]-->
	</div>
</body>
</html>