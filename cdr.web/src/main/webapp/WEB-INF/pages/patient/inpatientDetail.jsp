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
	<script type="text/javascript" src="../scripts/dialog-tabs.js"></script>
	<script>
		$(function()
		{
			logicalPatient();
		});	
	</script>
</head>
<body style="margin: 0; padding: 0;">
      <div id="pl_tabs2" style="margin-left:1px;margin-right:1px">
          <table id="tb_inpatient" style="width: 100%;" class="table" cellspacing="1" cellpadding="2" border="0">
			<tr class="tabletitle">
					<!-- <td height="28" align="center" width="16%">住院号</td> -->
					<td height="28" align="center" width="16%">病人号</td>
					<td height="28" align="center" width="9%">姓名</td>
					<td height="28" align="center" width="5%">性别</td>
					<td height="28" align="center" width="5%">年龄</td>
					<td height="28" align="center" width="13%">入院时间</td> 
					<td height="28" align="center" width="20%">科室</td>
					<td height="28" align="center" width="8%">病区</td>			
					<td height="28" align="center" width="7%">床号</td>
					<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
						<td height="28" align="center" width="14%">医疗机构</td>
					</c:if>	
			</tr>
			<c:if test="${fn:length(inpatientList)==0}">
				<tr>
					<td colspan="9" align="center" class="odd" height="24">没有相关数据！</td>
				</tr>
			</c:if>
			<c:forEach items="${inpatientList}" var="patient" varStatus="status">
				<tr
					<c:if test="${status.count%2==1}">class="odd tabEnterPatient" onmouseout="this.className='odd'"</c:if>
					<c:if test="${status.count%2==0}">class="even tabEnterPatient" onmouseout="this.className='even'"</c:if>
					style="cursor: pointer" onmouseover="this.className='mouseover'" onclick="javascript:location.href='../visit/${patient.patientSn}.html';">
					<%-- <td height="24" align="left" onmouseout="removeTabsTip('#tabstip')"
					<c:if test="${patient.cardFlag=='1'}">				
					onmouseover="showPatientDetailTip(event,'#tabstip',${status.count},{},${patient.inpatientNoJson});"
					</c:if>>${patient.inpatientNo}
						<c:if test="${patient.cardFlag=='1'}">		
							<img src="../images/cardsflag.gif" align="middle" style="width:20px;height:20px;padding-right: 1px;border:0px"/>
						</c:if>
					</td> --%>
					<td height="24" align="left">${patient.patientEid}</td>
					<td height="24" align="left">${patient.patientName}</td>
					<td height="24" align="center">${patient.genderName}</td>
					<td height="24" align="left">${patient.age}</td>
					<td height="24" align="center"><fmt:formatDate value="${patient.visitDate}"
							type="both" pattern="yyyy-MM-dd hh:mm" />
					</td>
					<td height="24" align="left">${patient.dischargeDeptName}</td>
					<td height="24" align="left">${patient.dischargeWardName}</td>
					<td height="24" align="left">${patient.dischargeBedNo}</td>
					<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">
						<td height="24" align="left"><ref:translate domain="${Constants.DOMAIN_ORG_CODE}" code="${patient.orgCode}"/></td>
					</c:if>
				</tr>
				<div id="ajaxDialog${status.count}" class="ajaxDialog" style="display: block; z-index: 10000;"></div>
			</c:forEach>
			<tr>
				<td colspan="9" style="height: 27px;">
					<form name="pagingform" method="get" action="../patient/list_inpatient.html">
						<input type="hidden" name="inpatient_no" value="${inpatientListSearchPra.inpatientNo}" />
						<input type="hidden" name="inpatient_name" value="${inpatientListSearchPra.patientName}" />
						<input type="hidden" name="inpatient_bed_no" value="${inpatientListSearchPra.dischargeBedNo}" />
						<input type="hidden" name="inpatient_dept_name" value="${inpatientListSearchPra.deptName}" />
						<input type="hidden" name="deptId" value="${inpatientListSearchPra.deptId}" />
						<input type="hidden" name="wardId" value="${inpatientListSearchPra.dischargeWardId}" />
						<input type="hidden" name="orgCode" value="${inpatientListSearchPra.orgCode}" />
						<div class="pagelinks">
						<div style="float: right; height: 100%;">
							<div class="pagedesc">共${pagingContext.totalRowCnt}条记录！      第${pagingContext.pageNo}页/共${pagingContext.totalPageCnt}页</div>
						<c:if test="${pagingContext.pageNo > 1}">
							<div class="firstPage">
								<a href="javascript:void(0);" onclick="jumpToListPagePortion(1, 'pagingform', '#inpatient_content');return false;"><img src="../images/1.gif" style="border:0px;width:21px;height:16px;" /></a>
							</div>
							<div class="prevPage">
								<a href="javascript:void(0);" onclick="jumpToListPagePortion(${pagingContext.pageNo-1}, 'pagingform', '#inpatient_content');return false;"><img src="../images/2.gif" style="border:0px;width:41px;height:16px;" /></a>
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
								<a href="javascript:void(0);" onclick="jumpToListPagePortion(${i}, 'pagingform', '#inpatient_content'); return false;"><font color="#2D56A5">${i}</font></a>
							</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
						<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
							<div class="nextPage">
								<a href="javascript:void(0);" onclick="jumpToListPagePortion(${pagingContext.pageNo + 1}, 'pagingform', '#inpatient_content');return false;"><img src="../images/4.gif" style="border:0px;width:41px;height:16px;" /></a>
							</div>
							<div class="lastPage">
								<a href="javascript:void(0);" onclick="jumpToListPagePortion(${pagingContext.totalPageCnt}, 'pagingform', '#inpatient_content');return false;"><img src="../images/3.gif" style="border:0px;width:21px;height:16px;" /></a>
							</div>
						</c:if>
							<div class="pageNum">
								<input type="text" name="screen" style="display:none;"/>
								<input name="pageNum"
								    onkeyup="if(event.keyCode!=13){return false;} jumpToListPageNoPortion($('#tb_inpatient').find('[name=pageNum]').val(),${pagingContext.totalPageCnt}, 'pagingform', '#inpatient_content');return false;"
								    style="width: 30px; float: left;" value="" />
							</div>
							<div class="goinput">
								<a href="javascript:void(0);" onclick="jumpToListPageNoPortion($('#tb_inpatient').find('[name=pageNum]').val(),${pagingContext.totalPageCnt}, 'pagingform', '#inpatient_content');return false;"><img src="../images/go.gif" style="border:0px;width:21px;height:15px;" /></a>
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