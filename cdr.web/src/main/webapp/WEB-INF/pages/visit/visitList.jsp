<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<meta http-equiv=”X-UA-Compatible” content=”IE=edge” /> 
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
	<script type="text/javascript" src="../scripts/visit/visitList.js"></script>
	<script>
		$(function() {
			// $Author:wu_jianfeng
			// $Date : 2012/10/24 14:10
			// $[BUG]0010542 MODIFY BEGIN
	
			$( "#visitDept").htmlSelectSuggest({width:140, 
				onKeyUp: function(event){
					if (event.keyCode==13) 
			    	{ 
						search('../visit/list_${patientSn}.html', 'conditionForm');									
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
		// $[BUG]0010542 MODIFY END
	
		$(function() {
			$( "#tblid tr" ).draggable({
				appendTo: "body",
				helper: "clone"
			});
		
		});
	</script>
</head>
<body style="margin: 0; padding: 0;">
	<form id="conditionForm" name="conditionForm" method="post"
		action="../visit/list_${patientSn}.html">
		<table class="blockTable" cellpadding="2" cellspacing="1" border="1px"
			bordercolor="#BFD9F0">
			<tr style="height: 28px; vertical-align: middle" id="tableheader">
				<td colspan="10"  class="blockHeader" align="left" height="27"
					style="border-top: solid 1px #B3C4D4; vertical-align: middle"><b><img
						src="../images/pic_jz_right.png"
						style="padding-left: 3px; padding-right: 2px;" width="19"
						height="19" alt="" align="absmiddle" />就诊</b></td>
			</tr>
			<tr class="conditionRow">
				<td width="100%" colspan="10"  height="36" align="left" valign="middle">
					<div style="width:260px;" class="cell">
						<div class="cell" style="width: 60px; text-align: right;">就诊日期</div>
						<div class="cell">
							<input id="visitDateFrom" name="visitDateFrom" style="width: 70px;"
								class="datepicker" value="${visitListSearchPra.visitDateFromStr}" />
							<span style="margin: 0 4px 0 4px;">--</span> <input id="visitDateTo"
								name="visitDateTo" style="width: 70px;" class="datepicker"
								value="${visitListSearchPra.visitDateToStr}" />
						</div>
					</div>
					<div style="width:180px;" class="cell">
						<div class="cell" style="width: 60px; text-align: right">就诊类型</div>
						<div class="cell" style="width: 110px;">
							<select name="visitType" style="width: 100%;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html:options domain="${Constants.DOMAIN_VISIT_TYPE}"
								 value="${visitListSearchPra.visitTypeCode}" />
							</select>
						</div>
					</div>
					<div style="width:160px;" class="cell">
						<div class="cell" style="width: 70px; text-align: center;">
							<input type="button"
								style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/5.jpg); width: 57px; height: 25px; margin-top: 3px;cursor: pointer;"
								align="absmiddle"
								onclick="search('../exam/list_${patientSn}.html', 'conditionForm');" />
						</div>
						<div id="toggleBlock" class="container-on cell">
							<input type="button" id="buttonBlock"
								style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/6.jpg); width: 77px; height: 25px; margin-top: 3px;cursor: pointer;"
								onclick="showTr('tr1',this)" align="absmiddle" /> <input
								type="hidden" id="conditionValue" name="conditionValue"
								value="${conditionValue}" /> <input type="hidden" id="senSave"
								name="senSave" value="${senSave}" />
						</div>
					</div>
				</td>
			</tr>
			<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
			<tr class="conditionRow">
				<td width="100%" colspan="10"  height="36" align="left" valign="middle">
					<div class="cell" style="width: 180px;">
						<div class="cell" style="text-align: right; width:60px;">医疗机构</div>
						<div class="cell" style="width: 110px;">
							<select name="orgCode" style="width: 110px;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html:options domain="${Constants.DOMAIN_ORG_CODE}"
									value='${visitListSearchPra.orgCode}' />
							</select>
						</div>
					</div>
				</td>
			</tr>
			</c:if>
			<tr id='tr1' class="conditionRow moreCondition off">
				<td width="100%" colspan="10"  height="36" valign="middle">
					<div style="width:260px;" class="cell">
						<div class="cell" style="width: 60px; text-align: right;">出院日期</div>
						<div class="cell">
							<input id="dischargeDateFrom" name="dischargeDateFrom" style="width: 70px;"
								class="datepicker"
								value="${visitListSearchPra.dischargeDateFromStr}" /> <span
								style="margin: 0 4px 0 4px;">--</span> <input id="dischargeDateTo"
								name="dischargeDateTo" style="width: 70px;" class="datepicker"
								value="${visitListSearchPra.dischargeDateToStr}" />
						</div>
					</div>
					<div style="width:200px;" class="cell">
						<div class="cell ra" style="width: 30px; text-align: right;margin-left: 5px;">科室</div>
	                    <!-- $Author :wu_jianfeng
	     					 $Date : 2012/10/24 17:08$
	    					 [BUG]0010542 MODIFY BEGIN -->
						<div class="cell">
							<select id="visitDept" name="visitDept">
								<option value="${Constants.OPTION_SELECT}">请选择科室</option>
								<html2:pycodeoptions domain="${Constants.DOMAIN_DEPARTMENT}"
									value="${visitListSearchPra.visitDeptId}" />
							</select>
						</div>
					</div>					
					<div style="width:190px;" class="cell">
						<div class="cell ra" style="width: 60px; text-align: right;">就诊医生</div>
						<div class="cell" style="width: 120px;">
							<input type="text" name="visitDoctorName" style="width: 110px;"
								onmouseover="this.style.background='#FDE8FE';"
								onmouseout="this.style.background='#FFFFFF'"
								value="${visitListSearchPra.visitDoctorName}" />
						</div>
					</div>
					<div style="width:170px;" class="cell">
						<div class="cell ra" style="width: 60px; text-align: right">就诊号别</div>
						<div class="cell">
							<select name="registrationClass" style="width: 100px;">
								<option value="${Constants.OPTION_SELECT}">请选择</option>
								<html:options domain="${Constants.DOMAIN_REGISTRATION_CLASS}"
									value="${visitListSearchPra.registrationClassCode}" />
							</select>
						</div>
					</div>
					<!-- [BUG]0010542 MODIFY END -->
				</td>
			</tr>
			<!-- $Author :chang_xuewen
			$Date : 2013/07/04 11:00
			$[BUG]0033461 MODIFY BEGIN -->
			<tr class="tabletitle">
		<!-- $Author :bin_zhang
             $Date : 2012/9/04 17:23$
             [BUG]0009334 ADD BEGIN  -->
			<td height="28" align="center" width="10%">就诊类别</td>
			<td height="28" align="center" width="10%">就诊号别</td>
			<td height="28" align="center" width="10%">就诊次数</td>
			<td height="28" align="center" width="10%">就诊医生</td>
			<td height="28" align="center" width="15%">就诊/入院日期</td>
			<td height="28" align="center" width="15%">就诊/入院科室</td>
			<td height="28" align="center" width="10%">就诊状态</td>
			<td height="28" align="center" width="10%">就诊视图</td>
			<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
				<td height="28" align="center" width="10%">医疗机构</td>
			</c:if>
			<!--[BUG]0009334 ADD END  -->
		</tr>
		<!-- $[BUG]0033461 MODIFY END -->
		</table>
	</form>
	<div id='listcon'>
	<!--[if lt ie 8]><div style='+zoom:1;'><![endif]-->
	<table id="tblid" style="width: 100%;" cellspacing="1" cellpadding="2"
		class="table">
		<c:if test="${fn:length(visitList)==0}">
			<tr>
				<td colspan="9" align="center" class="odd" height="24">没有相关数据！</td>
			</tr>
		</c:if>
		<c:forEach items="${visitList}" var="visitList" varStatus="status">
		<!-- $Author :chang_xuewen
			 $Date : 2013/07/04 11:00
			 $[BUG]0033461 MODIFY BEGIN -->
			<tr id='${visitList.visitSn}' style="cursor: pointer" class="tabEnter"
				onclick="bigShow($(this),'../visit/detail_${visitList.visitSn}.html?flag=tabs',
{'name':'${visitList.visitTypeName}${visitList.visitTimes}次','holdName':'${visitList.visitTypeName}${visitList.visitTimes}次','otherName':'','holdOtherName':'','orderSn':'${visitList.visitSn}'});">
		<!--[BUG]0033461 MODIFY END -->
				<td height="23" width="10%" style="text-align: left;display:none">就诊|../visit/detail_${visitList.visitSn}.html|<fmt:formatDate
											value="${visitList.visitDate}" type="date"
											pattern="yyyy-MM-dd" /><br/>|<c:if test="${fn:length(visitList.visitDeptName)>10}">${fn:substring(visitList.visitDeptName,0,7)}.。。</c:if>
												<c:if test="${fn:length(visitList.visitDeptName)<=10}">${visitList.visitDeptName}</c:if>| </td>
				<td height="23" width="10%" align="center" <c:if test="${fn:length(visitList.visitTypeName)>10}"> title="${visitList.visitTypeName}" </c:if>>
												<c:if test="${fn:length(visitList.visitTypeName)>10}">${fn:substring(visitList.visitTypeName,0,10)}...</c:if>
												<c:if test="${fn:length(visitList.visitTypeName)<=10}">${visitList.visitTypeName}</c:if></td>
												<!-- $Author :bin_zhang
                                                     $Date : 2012/9/04 17:23$
                                                     [BUG]0009334 ADD BEGIN  -->
				<td height="23" width="10%" align="center" <c:if test="${fn:length(visitList.registrationClassName)>10}"> title="${visitList.registrationClassName}" </c:if>>
												<c:if test="${fn:length(visitList.registrationClassName)>10}">${fn:substring(visitList.registrationClassName,0,10)}...</c:if>
												<c:if test="${fn:length(visitList.registrationClassName)<=10}">${visitList.registrationClassName}</c:if></td>
												<!--[BUG]0009334 ADD END  -->
				<td height="23" width="10%" style="text-align: right; padding-right: 5px;">${visitList.visitTimes}</td>
				<td height="23" width="10%" align="center" <c:if test="${fn:length(visitList.visitDoctorName)>10}"> title="${visitList.visitDoctorName}" </c:if>>
												<c:if test="${fn:length(visitList.visitDoctorName)>10}">${fn:substring(visitList.visitDoctorName,0,10)}...</c:if>
												<c:if test="${fn:length(visitList.visitDoctorName)<=10}">${visitList.visitDoctorName}</c:if></td>
				<td height="23" width="15%" align="center"><fmt:formatDate
											value="${visitList.visitDate}" type="date"
											pattern="yyyy-MM-dd" /></td>
				<td height="23" width="15%" align="center" <c:if test="${fn:length(visitList.visitDeptName)>10}"> title="${visitList.visitDeptName}" </c:if>>
												<c:if test="${fn:length(visitList.visitDeptName)>10}">${fn:substring(visitList.visitDeptName,0,10)}...</c:if>
												<c:if test="${fn:length(visitList.visitDeptName)<=10}">${visitList.visitDeptName}</c:if></td>
				<td height="23" width="10%" align="center" <c:if test="${fn:length(visitList.visitStatusName)>10}"> title="${visitList.visitStatusName}" </c:if>>
												<c:if test="${fn:length(visitList.visitStatusName)>10}">${fn:substring(visitList.visitStatusName,0,10)}...</c:if>
												<c:if test="${fn:length(visitList.visitStatusName)<=10}">${visitList.visitStatusName}</c:if></td>
				<td height="23" width="10%" align="center">
					<c:choose>
						<c:when test="${visitList.visitTypeCode==Constants.VISIT_TYPE_CODE_OUTPATIENT||visitList.visitTypeCode==VISIT_TYPE_CODE_PHYSICALEXAM||visitList.visitTypeCode==VISIT_TYPE_CODE_PHYSICALEXAM_BOSS}">
							<a href="javascript:void(0);"onclick="changeOutpatientView('../visit/outpatientViewPart.html', '${visitList.visitSn}');stopBubble(event);return false;"><img src="../images/mzbr.png" align="middle" style="width:23px;height:23px;padding-right: 1px;border:0px"/></a>
						</c:when>
						<c:when test="${visitList.visitTypeCode==Constants.VISIT_TYPE_CODE_EMERGENCY||visitList.visitTypeCode==VISIT_TYPE_CODE_EMERGENCY_ST}">
							<a href="javascript:void(0);"onclick="changeOutpatientView('../visit/outpatientViewPart.html', '${visitList.visitSn}');stopBubble(event);return false;"><img src="../images/jzbr.png" align="middle" style="width:23px;height:23px;padding-right: 1px;border:0px"/></a>
						</c:when>
						<c:when test="${visitList.visitTypeCode==Constants.VISIT_TYPE_CODE_INPATIENT||visitList.visitTypeCode==VISIT_TYPE_CODE_INPATIENT_TR}">
							<a href="javascript:void(0);"onclick="changeView('#inpatientView','${patientSn}',{dischargeDateAndVisitSn:'<fmt:formatDate value="${visitList.dischargeDate}" type="date" pattern="yyyy/MM/dd" />;${visitList.visitSn};<fmt:formatDate value="${visitList.visitDate}" type="date" pattern="yyyy/MM/dd" />'});stopBubble(event);return false;"><img src="../images/zybr.png" align="middle" style="width:23px;height:23px;padding-right: 1px;border:0px"/></a>
						</c:when>
					</c:choose>
				</td>	
				<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
					<td height="23" width="10%" align="center">${visitList.orgName}</td>
				</c:if>
			</tr>
		</c:forEach>
		<!-- $Author :chang_xuewen
			 $Date : 2013/07/04 11:00
			 $[BUG]0033461 MODIFY BEGIN -->
		<tr class="page_line">
		<!--[BUG]0033461 MODIFY END -->
			<td colspan="9" style="height: 27px;">
				<form name="pagingform" method="get"
					action="../visit/list_${patientSn}.html">
					<div class="pagelinks">
						<div style="float: right; height: 100%;">
							<div class="pagedesc">共${pagingContext.totalRowCnt}条记录！
								第${pagingContext.pageNo}页/共${pagingContext.totalPageCnt}页</div>
							<c:if test="${pagingContext.pageNo > 1}">
								<div class="firstPage">
									<a href="javascript:void(0);"
										onclick="jumpToPage(1);return false;"><img
										src="../images/1.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
								<div class="prevPage">
									<a href="javascript:void(0);"
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
												onclick="jumpToPage(${i}); return false;"><font
												color="#2D56A5">${i}</font></a>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
								<div class="nextPage">
									<a href="javascript:void(0);"
										onclick="jumpToPage(${pagingContext.pageNo + 1});return false;"><img
										src="../images/4.gif"
										style="border: 0px; width: 41px; height: 16px;" /></a>
								</div>
								<div class="lastPage">
									<a href="javascript:void(0);"
										onclick="jumpToPage(${pagingContext.totalPageCnt});return false;"><img
										src="../images/3.gif"
										style="border: 0px; width: 21px; height: 16px;" /></a>
								</div>
							</c:if>
							<div class="pageNum">
								<input type="text" name="screen" style="display:none;"/>
								<input name="pageNum"
								    onkeyup="if(event.keyCode!=13){return false;} jumpToPageNo($('#tblid').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"
								    style="width: 30px; float: left;" value="" />
							</div>
							<div class="goinput">
								<a href="javascript:void(0);"
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
	 <!--[if lt ie 8]></div><![endif]-->
	</div>
</body>
</html>
