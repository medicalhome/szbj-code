<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants"/>
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
      <table id="tb_patient" style="width: 100%;" class="table" cellspacing="1" cellpadding="2" border="0">
		<tr class="tabletitle">
			<!-- <td height="28" align="center" width="26%">就诊卡号/住院号</td> -->
			<td height="28" align="center" width="26%">病人号</td>
			<td height="28" align="center" width="7%">姓名</td>
			<td height="28" align="center" width="5%">性别</td>
			<td height="28" align="center" width="11%">年龄（最近一次就诊）</td>
			<td height="28" align="center" width="18%">科室</td>
			<td height="28" align="center" width="10%">病区</td>
			<td height="28" align="center" width="8%">床号</td>
			<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">             	 
				<td height="28" align="center" width="14%">医疗机构</td>
			</c:if>
		</tr>
		<c:choose>
			<c:when test="${queryFlag==0}">
				<tr>
					<td colspan="8" align="center" class="odd" height="24">请输入检索条件，检索患者信息！</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:if test="${fn:length(patientList)==0}">
					<tr>
						<td colspan="8" align="center" class="odd" height="24">没有相关数据！</td>
					</tr>
				</c:if>
			</c:otherwise>			
		</c:choose>
		<c:forEach items="${patientList}" var="patient" varStatus="status">
		<tr
			<c:if test="${status.count%2==1}">class="odd tabEnterPatient" onmouseout="this.className='odd'"</c:if>
			<c:if test="${status.count%2==0}">class="even tabEnterPatient" onmouseout="this.className='even'"</c:if>
				style="cursor: pointer" onmouseover="this.className='mouseover'" onclick="javascript:location.href='../visit/${patient.patientSn}.html';">
				<%-- <td height="24" align="left" onmouseout="removeTabsTip('#tabstip')"
					<c:if test="${patient.cardFlag=='1'}">				
						onmouseover="showPatientDetailTip(event,'#tabstip',${status.count},${patient.outpatientNoJson},${patient.inpatientNoJson});"
					</c:if>>${patient.outpatientNo}/${patient.inpatientNo}
					<c:if test="${patient.cardFlag=='1'}">		
						<img src="../images/cardsflag.gif" align="middle" style="width:20px;height:20px;padding-right: 1px;border:0px"/>
					</c:if>
				</td> --%>
				<td height="24" align="left">${patient.patientEid}</td>
				<td height="24" align="left">${patient.patientName}</td>
				<td height="24" align="center">${patient.genderName}</td>
				<td height="24" align="left">${patient.age}</td>
				<c:choose>
					<c:when test="${Constants.VISIT_TYPE_CODE_OUTPATIENT == patient.visitTypeCode}">
						<td height="24" align="left">${patient.visitDeptName}</td>
					</c:when>
					<c:when test="${Constants.VISIT_TYPE_CODE_EMERGENCY == patient.visitTypeCode}">
						<td height="24" align="left">${patient.visitDeptName}</td>
					</c:when>
					<c:when test="${Constants.VISIT_TYPE_CODE_EMERGENCY_ST == patient.visitTypeCode}">
						<td height="24" align="left">${patient.visitDeptName}</td>
					</c:when>
					<c:when test="${Constants.VISIT_TYPE_CODE_PHYSICALEXAM == patient.visitTypeCode}">
						<td height="24" align="left">${patient.visitDeptName}</td>
					</c:when>
					<c:when test="${Constants.VISIT_TYPE_CODE_PHYSICALEXAM_BOSS == patient.visitTypeCode}">
						<td height="24" align="left">${patient.visitDeptName}</td>
					</c:when>
					<c:when test="${Constants.VISIT_TYPE_CODE_INPATIENT == patient.visitTypeCode}">
						<td height="24" align="left">${patient.dischargeDeptName}</td>
					</c:when>
					<c:when test="${Constants.VISIT_TYPE_CODE_INPATIENT_TR == patient.visitTypeCode}">
						<td height="24" align="left">${patient.dischargeDeptName}</td>
					</c:when>
					<c:otherwise>
						<td height="24" align="left"></td>
					</c:otherwise>
				</c:choose>				
				<td height="24" align="left">${patient.dischargeWardName}</td>
				<td height="24" align="left">${patient.dischargeBedNo}</td>
				<c:if test="${Constants.TRUE_ORG_CODE == Constants.DISABLE_ORG_CODE}">  
					<td height="24" align="left"><ref:translate domain="${Constants.DOMAIN_ORG_CODE}" code="${patient.orgCode}"/></td>
				</c:if>
			</tr>
			<div id="ajaxDialog${status.count}" class="ajaxDialog"
				style="display: block; z-index: 10000;">
			</div>
		</c:forEach>
		<tr>
			<td colspan="8" style="height: 27px;">
				<form name="pagingform" method="get" action="">
   					<input type="hidden" id="postbackEvent_paging" name="postbackEvent" value="paging"/> 
					<div class="pagelinks">
					<div style="float: right; height: 100%;">
						<div class="pagedesc">共${pagingContext.totalRowCnt}条记录！      第${pagingContext.pageNo}页/共${pagingContext.totalPageCnt}页</div>
					<c:if test="${pagingContext.pageNo > 1}">
						<div class="firstPage">
							<a href="javascript:void(0);" onclick="jumpToPatientListPage(1);return false;"><img src="../images/1.gif" style="border:0px;width:21px;height:16px;" /></a>
						</div>
						<div class="prevPage">
							<a href="javascript:void(0);" onclick="jumpToPatientListPage(${pagingContext.pageNo-1});return false;"><img src="../images/2.gif" style="border:0px;width:41px;height:16px;" /></a>
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
							<a href="javascript:void(0);" onclick="jumpToPatientListPage(${i}); return false;"><font color="#2D56A5">${i}</font></a>
						</div>
					</c:otherwise>
				</c:choose>
			</c:forEach>
					<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
						<div class="nextPage">
							<a href="javascript:void(0);" onclick="jumpToPatientListPage(${pagingContext.pageNo + 1});return false;"><img src="../images/4.gif" style="border:0px;width:41px;height:16px;" /></a>
						</div>
						<div class="lastPage">
							<a href="javascript:void(0);" onclick="jumpToPatientListPage(${pagingContext.totalPageCnt});return false;"><img src="../images/3.gif" style="border:0px;width:21px;height:16px;" /></a>
						</div>
					</c:if>
						<div class="pageNum">
							<input type="text" name="screen" style="display:none;"/>
							<input name="pageNum"
							    onkeyup="if(event.keyCode!=13){return false;} jumpToPatientListPageNo($('#tb_patient').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"
							    style="width: 30px; float: left;" value="" />
						</div>
						<div class="goinput">
							<a href="javascript:void(0);" onclick="jumpToPatientListPageNo($('#tb_patient').find('[name=pageNum]').val(),${pagingContext.totalPageCnt});return false;"><img src="../images/go.gif" style="border:0px;width:21px;height:15px;" /></a>
						</div>
					</div>
				</div>
				</form>
			</td>
		</tr>
	  </table>
	            
</body>
</html>