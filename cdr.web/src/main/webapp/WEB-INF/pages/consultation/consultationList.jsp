<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>会诊-列表</title>
<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
<script type="text/javascript" src="../scripts/consultation/consultationList.js"></script>
</head>

<body style="margin: 0; padding: 0;">
	<form id="conditionForm" name="conditionForm" method="post"
		action="../consultation/consultation_${patientSn}.html">
		<table class="blockTable" cellpadding="2" cellspacing="1" border="1px"
			bordercolor="#BFD9F0">
			<tr style="height: 28px;" valign="middle">
				<td class="blockHeader" colspan="7" height="27"
					style="border-top: solid 1px #B3C4D4;" align="left"><b><img
						src="../images/gm.png"
						style="padding-left: 5px; padding-right: 4px;" width="16"
						height="16" alt="" align="absmiddle" />会诊 </b></td>
			</tr>
			<%-- <tr class="conditionRow">
				<td width="100%" height="36" align="left" valign="middle">
					<div class="cell" style="width: 80px; text-align: right;">会诊申请类型</div>
					<div class="cell" style="width: 120px;">
						<select name="seriousness" style="width: 100%;">
							<option value="${Constants.OPTION_SELECT}">请选择</option>
							<html:options domain="${Constants.DOMAIN_SERIOUSNESS}"
								value="${riskListSearchPra.seriousness}" />
						</select>
					</div>				
					<div class="cell" style="width: 80px; text-align: right;">提交日期</div>
					<div class="cell">
						<input id="allergicStartDate" name="allergicStartDate"
							style="width: 80px;" class="datepicker"
							value="${consultationListSearchPra.consultationStartDate}" />
					</div>
					<div class="cell" style="width: 30px; text-align: center;">---</div>
					<div class="cell" style="width: 80px;">
						<input id="allergicStopDate" name="allergicStopDate"
							style="width: 80px;" class="datepicker"
							value="${consultationListSearchPra.consultationStopDate}" />
					</div>
					<div class="cell" style="width: 60px; text-align: right;">申请科室</div>
					<div style="float: left; margin-top: 4px; width: 120px;">
						<select name="labDept" id="labDept" style="width: 110px;">
							<option value="${Constants.OPTION_SELECT}">选择科室类型</option>
							<html2:pycodeoptions domain="${Constants.DOMAIN_DEPARTMENT}"
								value="${consultationListSearchPra.consultationRequestDept}" />
						</select>
					</div>
					<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
					<div class="cell" align="left" style="padding-right: 2px;">医疗机构</div>
					<div class="cell" style="width: 140px;">
						<select id="orgCodeFlag" name="orgCodeFlag" style="width:130px;">
							<option value="${Constants.OPTION_SELECT}">请选择</option>
							<html:options domain="${Constants.DOMAIN_ORG_CODE}"
							value='${orgCode}' />						
						</select>
					</div>
				</c:if>
					<div class="cell" style="width: 70px; text-align: center;">
						<input type="button"
							style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/5.jpg); width: 57px; height: 25px; margin-top: 3px;cursor: pointer;"
							onclick="search('../consultation/consultation_${patientSn}.html', 'conditionForm');" align="absmiddle" />
						<!--<div class="cell"><input type="button" onclick="showTr(tr1,this)" class="button" align="absmiddle" value="高级搜索" /></div>-->
						<input type="hidden" id="senSave" name="senSave"
							value="${senSave}" />
					</div>
				</td>
			</tr> --%>
		</table>
	</form>

	<table id="tblid" style="width: 100%;" cellspacing="1" cellpadding="2"
		border="0" class="table">
		<tr class="tabletitle">
			<td height="28" align="center">会诊单编码</td>
			<td height="28" align="center">申请类型</td>
			<td height="28" align="center">会诊类型</td>
			<td height="28" align="center">提交日期</td>
			<td height="28" align="center">申请科室</td>
			<td height="28" align="center">申请医生</td>
			<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
				<td height="28" align="center">医疗机构</td>
			</c:if>
		</tr>
		<c:if test="${fn:length(consultationList)==0}">
			<tr>
				<td colspan="7" align="center" class="odd" height="24">没有相关数据！</td>
			</tr>
		</c:if>
		<c:forEach items="${consultationList}" var="consultationList" varStatus="status">
			<tr
		    	<c:if test="${status.count%2==1 }">class="odd tabEnter" onmouseout="this.className='odd'"</c:if>
		    	<c:if test="${status.count%2==0 }">class="even tabEnter" onmouseout="this.className='even'"</c:if> 
		    	id="${consultationList.requestNo}}" 
		    	onclick="showConsultationDetailDialog('${consultationList.requestNo}','${patientSn}','${consultationList.patientLid}','${consultationList.patientDomain}',
		    	'${consultationList.visitSn}','${consultationList.consultationType}','${consultationList.orgName}','${consultationList.orgCode}',
		    	'${consultationList.urgencyName}','${consultationList.orderDeptName}',
		    	'${consultationList.requestDoctorName}','${consultationList.orderSn}',$(this),2,'会诊记录');" 
				style="cursor: pointer" onmouseover="this.className='mouseover'">
				<td height="24" align="center">
					${consultationList.requestNo}
                </td>
				<td height="24" align="center" <c:if test="${fn:length(consultationList.consultationTypeName)>10}"> title="${consultationList.consultationTypeName}" </c:if>>
				<c:if test="${fn:length(consultationList.consultationTypeName)>10}">${fn:substring(consultationList.consultationTypeName,0,10)}...</c:if>
                <c:if test="${fn:length(consultationList.consultationTypeName)<=10}">${consultationList.consultationTypeName}</c:if></td>
                
 				<td height="24" align="center" <c:if test="${fn:length(consultationList.urgencyName)>10}"> title="${consultationList.urgencyName}" </c:if>>
				<c:if test="${fn:length(consultationList.urgencyName)>10}">${fn:substring(consultationList.urgencyName,0,10)}...</c:if>
                <c:if test="${fn:length(consultationList.urgencyName)<=10}">${consultationList.urgencyName}</c:if></td>               
				<td height="24" align="center"><fmt:formatDate
											value="${consultationList.requestTime}" type="date"
											pattern="yyyy-MM-dd" /></td>
											
 				<td height="24" align="center" <c:if test="${fn:length(consultationList.orderDeptName)>10}"> title="${consultationList.orderDeptName}" </c:if>>
				<c:if test="${fn:length(consultationList.orderDeptName)>10}">${fn:substring(consultationList.orderDeptName,0,10)}...</c:if>
                <c:if test="${fn:length(consultationList.orderDeptName)<=10}">${consultationList.orderDeptName}</c:if></td> 
                
                 <td height="24" align="center" <c:if test="${fn:length(consultationList.requestDoctorName)>10}"> title="${consultationList.requestDoctorName}" </c:if>>
				<c:if test="${fn:length(consultationList.requestDoctorName)>10}">${fn:substring(consultationList.requestDoctorName,0,10)}...</c:if>
                <c:if test="${fn:length(consultationList.requestDoctorName)<=10}">${consultationList.requestDoctorName}</c:if></td> 											
				<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">							
	                <td height="24" align="center" <c:if test="${fn:length(consultationList.orgName)>10}"> title="${consultationList.orgName}" </c:if>>
					<c:if test="${fn:length(consultationList.orgName)>10}">${fn:substring(consultationList.orgName,0,10)}...</c:if>
	                <c:if test="${fn:length(consultationList.orgName)<=10}">${consultationList.orgName}</c:if></td>
	            </c:if> 											
			</tr>
		</c:forEach>

		<tr>
			<td colspan="7" style="height: 27px;">
				<form name="pagingform" method="get"
					action="../consultation/consultation_${patientSn}.html">
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