<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>过敏/不良反应-列表</title>
<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
<script type="text/javascript" src="../scripts/risk/allergyList.js"></script>
</head>

<body style="margin: 0; padding: 0;">
	<form id="conditionForm" name="conditionForm" method="post"
		action="../risk/allergy_${patientSn}.html">
		<table class="blockTable" cellpadding="2" cellspacing="1" border="1px"
			bordercolor="#BFD9F0">
			<tr style="height: 28px;" valign="middle">
				<td class="blockHeader" colspan="7" height="27"
					style="border-top: solid 1px #B3C4D4;" align="left"><b><img
						src="../images/gm.png"
						style="padding-left: 5px; padding-right: 4px;" width="16"
						height="16" alt="" align="absmiddle" />过敏/不良反应 </b></td>
			</tr>
			<tr class="conditionRow">
				<td width="100%" height="36" align="left" valign="middle">
					<div class="cell" style="width: 80px; text-align: right;">过敏开始日期</div>
					<div class="cell">
						<input id="allergicStartDate" name="allergicStartDate"
							style="width: 80px;" class="datepicker"
							value="${riskListSearchPra.allergicStartDate}" />
					</div>
					<div class="cell" style="width: 30px; text-align: center;">---</div>
					<div class="cell" style="width: 80px;">
						<input id="allergicStopDate" name="allergicStopDate"
							style="width: 80px;" class="datepicker"
							value="${riskListSearchPra.allergicStopDate}" />
					</div>
					<div class="cell" style="width: 70px; text-align: right;">过敏严重性</div>
					<div class="cell" style="width: 120px;">
						<select name="seriousness" style="width: 100%;">
							<option value="${Constants.OPTION_SELECT}">请选择</option>
							<html:options domain="${Constants.DOMAIN_SERIOUSNESS}"
								value="${riskListSearchPra.seriousness}" />
						</select>
					</div>
					<div class="cell" style="width: 70px; text-align: center;">
						<input type="button"
							style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/5.jpg); width: 57px; height: 25px; margin-top: 3px;cursor: pointer;"
							onclick="search('../risk/allergy_${patientSn}.html', 'conditionForm');" align="absmiddle" />
						<!--<div class="cell"><input type="button" onclick="showTr(tr1,this)" class="button" align="absmiddle" value="高级搜索" /></div>-->
						<input type="hidden" id="senSave" name="senSave"
							value="${senSave}" />
					</div>
				</td>
			</tr>
		</table>
	</form>


	<table id="tblid" style="width: 100%;" cellspacing="1" cellpadding="2"
		border="0" class="table">
		<tr class="tabletitle">
			<td height="28" align="center">过敏源</td>
			<!-- <td height="28" align="center">过敏症状</td>
			<td height="28" align="center">过敏病情</td> -->
			<td height="28" align="center">过敏类型</td>
			<td height="28" align="center">过敏严重性</td>
			<td height="28" align="center">过敏开始日期</td>
		</tr>
		<c:if test="${fn:length(riskList)==0}">
			<tr>
				<td colspan="5" align="center" class="odd" height="24">没有相关数据！</td>
			</tr>
		</c:if>
		<c:forEach items="${riskList}" var="riskList" varStatus="status">
			<tr
				<c:choose>
		    	<c:when test="${examList.withdrawFlag==Constants.REPORT_WITHDRAW_FLAG}">class="forbidden tabEnter" onmouseout="this.className='forbidden'" onmouseover="this.className='forbidden'"</c:when>
		    	<c:otherwise>
		    		 <c:if test="${status.count%2==1 }">class="odd tabEnter" onmouseout="this.className='odd'"</c:if>
		    		 <c:if test="${status.count%2==0 }">class="even tabEnter" onmouseout="this.className='even'"</c:if>
		    	</c:otherwise>
		        </c:choose>
				style="cursor: pointer" onmouseover="this.className='mouseover'">
				<td height="24" align="center" <c:if test="${fn:length(riskList.allergicSource)>10}">  title="${riskList.allergicSource}" </c:if>>
				<c:if test="${fn:length(riskList.allergicSource)>10}">${fn:substring(riskList.allergicSource,0,10)}...</c:if>
                <c:if test="${fn:length(riskList.allergicSource)<=10}">${riskList.allergicSource}</c:if></td>
				<%-- <td height="24" align="center" <c:if test="${fn:length(riskList.allergicSymptom)>10}"> title="${riskList.allergicSymptom}" </c:if>>
				<c:if test="${fn:length(riskList.allergicSymptom)>10}">${fn:substring(riskList.allergicSymptom,0,10)}...</c:if>
                <c:if test="${fn:length(riskList.allergicSymptom)<=10}">${riskList.allergicSymptom}</c:if></td>
				<td height="24" align="center">${riskList.allergicConditionName}</td> --%>
				
					 <!-- $Author :bin_zhang
				     	  $Date : 2012/10/26 12:08$
				    	  [BUG]0010757 MODIFY BEGIN -->
				<td height="24" align="center" <c:if test="${fn:length(riskList.allergicTypeName)>10}"> title="${riskList.allergicTypeName}" </c:if>>
                <c:if test="${fn:length(riskList.allergicTypeName)>10}">${fn:substring(riskList.allergicTypeName,0,10)}...</c:if>
                <c:if test="${fn:length(riskList.allergicTypeName)<=10}">${riskList.allergicTypeName}</c:if></td>
                <!-- [BUG]0010757 MODIFY BEGIN -->
                
				<td height="24" align="center" <c:if test="${fn:length(riskList.seriousnessName)>10}"> title="${riskList.seriousnessName}" </c:if>>
                <c:if test="${fn:length(riskList.seriousnessName)>10}">${fn:substring(riskList.seriousnessName,0,10)}...</c:if>
                <c:if test="${fn:length(riskList.seriousnessName)<=10}">${riskList.seriousnessName}</c:if></td>
				<td height="24" align="center"><fmt:formatDate
											value="${riskList.allergicStartDate}" type="date"
											pattern="yyyy-MM-dd" /></td>
			</tr>
		</c:forEach>

		<tr>
			<td colspan="5" style="height: 27px;">
				<form name="pagingform" method="get"
					action="../risk/allergy_${patientSn}.html">
					<div class="pagelinks">
						<div style="float: right; height: 100%;">
							<div class="pagedesc">共${pagingContext.totalRowCnt}条记录！
								第${pagingContext.pageNo}页/共${pagingContext.totalPageCnt}页</div>
							<c:if test="${pagingContext.pageNo>1}">
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
										onclick="jumpToPage(${pagingContext.pageNo+1});return false;"><img
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
</body>
</html>