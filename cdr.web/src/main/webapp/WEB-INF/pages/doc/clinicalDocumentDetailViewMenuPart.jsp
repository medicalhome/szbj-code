<%@ page language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<jsp:useBean id="TimerAndInpatientDto" class="com.yly.cdr.dto.TimerAndInpatientDto"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<script type="text/javascript" src="../scripts/doc/docMenuPart.js"></script>
<script type="text/javascript" src="../scripts/commonTabs.js"></script>
<script type="text/javascript">
function getPageNo()
{
	return '${pagingContext.pageNo}';
}

function getTotalPageCnt()
{
	return '${pagingContext.totalPageCnt}';
}

$(function(){
	setLeftHeight(getPageNo(),getTotalPageCnt());
});

</script>
</head>
<body style="margin: 0; padding: 0;">
	<div id="dd_detailContent">
			<c:if test="${pagingContext.pageNo>1}">
				<div id="img_prev"
					onclick="jumpToNext('../doc/list_'+'${patientSn}'+'.html?flag=tabs',
					{'gotoType':'part','pageNo':'${pagingContext.pageNo}','totalPageCnt':'${pagingContext.totalPageCnt}','patientSn':'${patientSn}',
					'next':'next'},'#next_${pagingContext.pageNo}');">
					<img style="height:10px;" src="../images/upPage.png"/>
				</div>
			</c:if>
			<table style="width: 100%;" cellspacing="1" cellpadding="2" border="0" class="table">
				<tr class="tabletitle">
					<td id="tdTitle" height="24" align="center" width="50%">文档类别</td>
					<td height="24" align="center">文档名称</td>
				</tr>
			</table>
			<div id="prev_next" style="overflow-y: ${overflow};">
				<!--[if lt ie 8]><div style='+zoom:1;'><![endif]-->
				<div id="pre_${pagingContext.pageNo}"></div>
				<div>
					<%-- <table id="${pagingContext.pageNo}" style="width: 100%;" cellspacing="1" cellpadding="2" border="0" class="table">
						<c:forEach items="${docList}" var="doc" varStatus="status">
							<tr id="${doc.documentLid}"
								<c:if test="${status.count%2==1}">class="odd tabEnter" onmouseout="this.className='odd';removeTabsTip('#tabstip-doc')"</c:if>
								<c:if test="${status.count%2==0}">class="even tabEnter" onmouseout="this.className='even';removeTabsTip('#tabstip-doc')"</c:if> 
								<c:if test="${!empty doc.documentLid}">
								style="cursor:pointer;" 
								onmouseover="this.className='mouseover';showDocument(event,{'documentAuthorName':'${doc.documentAuthorName}',
								'documentModifierName':'${doc.documentModifierName}','submitTime':'<fmt:formatDate value="${doc.submitTime}" type="date" pattern="yyyy-MM-dd"/>'});"
								onclick="addMoreTabs(this,'../doc/detail_'+'${doc.documentSn}'+'.html?flag=tabs',
										{'menuPartFlag':'true','temporaryFlag':'${temporaryFlag}','width':'70%','orderSn':'${doc.documentLid}',
										'otherName':'','holdOtherName':'',
										'name':'<c:choose>
													<c:when test="${fn:length(doc.documentName)>4}">${fn:substring(doc.documentName,0,4)}...</c:when>
													<c:otherwise>${doc.documentName}</c:otherwise>
												</c:choose>','holdName':'${doc.documentName}',
										'patientSn':'${patientSn}'},'#moreTabs');"</c:if>>
								<td height="40" align="center" <c:if test="${doc.documentLid==documentLid && !empty doc.documentLid}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
									${doc.documentTypeName}
								</td>
								<td height="40" align="left" <c:if test="${doc.documentLid==documentLid && !empty doc.documentLid}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
									${doc.documentName}
								</td>
							</tr>
						</c:forEach>
					</table> --%>
					<jsp:include page="clinicalDocumentDetailViewMenuPartChart.jsp" />
				</div>
				<div id="next_${pagingContext.pageNo}"></div>
				<!--[if lt ie 8]></div><![endif]-->
			</div>
			<c:if test="${pagingContext.pageNo < pagingContext.totalPageCnt}">
				<!-- 住院视图用于此连接 -->
				<c:if test="${gotoType == 'doc'}">
					<div id="img_next"
						onclick="jumpToNext('../doc/list_'+'${patientSn}'+'.html?flag=tabs',
						{'gotoType':'part',
						 'pageNo':'${pagingContext.pageNo}',
						 'totalPageCnt':'${pagingContext.totalPageCnt}',
						 'patientSn':'${patientSn}',
						 'next':'next',
						 'type':'${TimerAndInpatientDto.DOCUMENTATION}',
						 'visitSn':'${docListSearchParameters.visitSn}',
						 'inpatientDate':'${docListSearchParameters.inpatientDate}'},
						 '#next_${pagingContext.pageNo}');">
						<img style="height:10px;" src="../images/downPage.png"/>
					</div>
				</c:if>
				<!-- 时间轴视图用于此连接 -->
				<c:if test="${gotoType == 'doc_timer'}">
					<div id="img_next"
						onclick="jumpToNext('../doc/list_'+'${patientSn}'+'.html?flag=tabs',
						{'gotoType':'part',
						 'pageNo':'${pagingContext.pageNo}',
						 'totalPageCnt':'${pagingContext.totalPageCnt}',
						 'patientSn':'${patientSn}',
						 'next':'next',
						 'type':'${TimerAndInpatientDto.DOCUMENTATION}',
						 'visitSn':'${docListSearchParameters.visitSn}'},
						 '#next_${pagingContext.pageNo}');">
						<img style="height:10px;" src="../images/downPage.png"/>
					</div>
				</c:if>
			</c:if>
		</div>
	<!-- </div> -->
	<div id=tabs-animate class="paneSeperator_open">&nbsp;&nbsp;</div>
</body>
</html>