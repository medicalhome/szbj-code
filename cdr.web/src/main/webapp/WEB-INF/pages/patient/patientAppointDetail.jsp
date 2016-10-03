<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Pragma" content="no-cache" />
    <!-- Prevents caching at the Proxy Server -->
    <meta http-equiv="Expires" content="0" />
    <title>患者列表</title>
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
	<script>
	$(function()
	{
		logicalPatient();
	});	
	
	</script>
</head>
<body style="margin: 0; padding: 0;">
      <div id="pl_tabs1" style="margin-left:1px;margin-right:1px;">
		<table id="tb_patientAppoint" style="width: 100%;" class="table" cellspacing="1" cellpadding="2" border="0">
			<tr class="tabletitle">
					<!-- <td height="28" align="center" width="10%">就诊卡号</td> -->
					<td height="28" align="center" width="10%">病人号</td>
					<td height="28" align="center" width="">姓名</td>
					<td height="28" align="center" width="5%">性别</td>
					<td height="28" align="center" width="10%">年龄</td>
					<td height="28" align="center" width="10%">号别</td>
					<td height="28" align="center" width="15%">号类</td>
					<td height="28" align="center" width="10%">预约医生</td>
					<td height="28" align="center" width="15%">录入日期</td>
					<td height="28" align="center" width="15%">预约日期</td>
					<!-- <td height="28" align="center" width="14%">病区</td> -->
			</tr>
			<c:if test="${fn:length(patientAppointList)==0}">
				<tr>
					<td colspan="9" align="center" class="odd" height="24">没有相关数据！</td>
				</tr>
			</c:if>
			<c:forEach items="${patientAppointList}" var="patient" varStatus="status">
			<tr <c:if test="${status.count%2==1}">class="odd tabEnterPatient" onmouseout="this.className='odd'"</c:if>
				<c:if test="${status.count%2==0}">class="even tabEnterPatient" onmouseout="this.className='even'"</c:if> 
				style="cursor:pointer" onmouseover="this.className='mouseover'"  onclick="visit(${patient.patientSn});">
						<%-- <td height="24" align="left">${patient.outpatientNo}</td> --%>
						<td height="24" align="left">${patient.patientEid}</td>
						<td height="24" align="left">${patient.patientName}</td>
						<td height="24" align="center">${patient.genderName}</td>
						<td height="24" align="left">${patient.age}</td>
						<td height="24" align="left">${patient.clinicType}</td>
						<td height="24" align="left">${patient.reqType}</td>
						<td height="24" align="left">${patient.name}</td>
						<td height="24" align="center"><fmt:formatDate value="${patient.inputDate}"
							type="both" pattern="yyyy-MM-dd" /></td>
						<td height="24" align="center"><fmt:formatDate value="${patient.requestDate}"
						type="both" pattern="yyyy-MM-dd" /></td>
						<%-- <td height="24" align="left">${patient.admissionWardName}</td> --%>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="9" style="height: 27px;">
					<form name="pagingform" method="get" action="../patient/list_patientappoint.html">
						<input type="hidden" name="outpatient_name" value="${patientListSearchPra.patientName}" />
						<input type="hidden" name="requestStartDate" value="${patientListSearchPra.requestStartDate}" />
						<input type="hidden" name="requestEndDate" value="${patientListSearchPra.requestEndDate}" />
						<input type="hidden" name="normalPatient" value="${patientListSearchPra.normalPatient}" />
						<div class="pagelinks">
						<div style="float: right; height: 100%;">
							<div class="pagedesc">共${pagingContext.totalRowCnt}条记录！      第${pagingContext.pageNo}页/共${pagingContext.totalPageCnt}页</div>
						<c:if test="${pagingContext.pageNo > 1}">
							<div class="firstPage">
								<a href="javascript:void(0);" onclick="jumpToListPagePortion(1, 'pagingform', '#patientAppoint_content');return false;"><img src="../images/1.gif" style="border:0px;width:21px;height:16px;" /></a>
							</div>
							<div class="prevPage">
								<a href="javascript:void(0);" onclick="jumpToListPagePortion(${pagingContext.pageNo-1}, 'pagingform', '#patientAppoint_content');return false;"><img src="../images/2.gif" style="border:0px;width:41px;height:16px;" /></a>
							</div>
						</c:if>
				<c:forEach var="i" begin="${pagingContext.pageStartNo}" end="${pagingContext.perPageCnt}" step="1">
					<c:choose>
						<c:when test="${i == pagingContext.pageNo}">
							<div class="currentPage">
								<font color="#2D56A5">${i}</font>
							</div>
						</c:when>
						<c:otherwise>
							<div class="pageno">
								<a href="javascript:void(0);" onclick="jumpToListPagePortion(${i}, 'pagingform', '#patientAppoint_content'); return false;"><font color="#2D56A5">${i}</font></a>
							</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
						<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
							<div class="nextPage">
								<a href="javascript:void(0);" onclick="jumpToListPagePortion(${pagingContext.pageNo + 1}, 'pagingform', '#patientAppoint_content');return false;"><img src="../images/4.gif" style="border:0px;width:41px;height:16px;" /></a>
							</div>
							<div class="lastPage">
								<a href="javascript:void(0);" onclick="jumpToListPagePortion(${pagingContext.totalPageCnt}, 'pagingform', '#patientAppoint_content');return false;"><img src="../images/3.gif" style="border:0px;width:21px;height:16px;" /></a>
							</div>
						</c:if>
							<div class="pageNum">
								<input type="text" name="screen" style="display:none;"/>
								<input name="pageNum"
								    onkeyup="if(event.keyCode!=13){return false;} jumpToListPageNoPortion($('#tb_patientAppoint').find('[name=pageNum]').val(),${pagingContext.totalPageCnt}, 'pagingform', '#patientAppoint_content');return false;"
								    style="width: 30px; float: left;" value="" />
							</div>
							<div class="goinput">
								<a href="javascript:void(0);" onclick="jumpToListPageNoPortion($('#tb_patientAppoint').find('[name=pageNum]').val(),${pagingContext.totalPageCnt}, 'pagingform', '#patientAppoint_content');return false;"><img src="../images/go.gif" style="border:0px;width:21px;height:15px;" /></a>
							</div>
						</div>
					</div>
					</form>
				</td>
			</tr>
		</table>
      </div>
</body>
</html>