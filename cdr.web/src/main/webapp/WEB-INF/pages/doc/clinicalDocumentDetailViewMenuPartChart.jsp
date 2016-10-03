<%@ page language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- <script type="text/javascript">
$(function(){
	setLeftTDWidth();
});
</script> -->
</head>
<body style="margin: 0; padding: 0;">
<!-- $Author :chang_xuewen
     $Date : 2013/10/24 11:00
     $[BUG]0038443 ADD BEGIN -->
	<c:if test="${viewFlag && !empty docList}">
			 <table id="downtitle" style ="width : 100%;" cellspacing= "1" cellpadding= "2" border= "0" class = "table">
                    <tr class = "tabletitle">
                          <td id = "tdTitle" height= "24" align= "center" width = "50%"> 文档类别</td>
                          <td height = "24" align= "center"> 文档名称 </td >
                    </tr >
             </table >
    </c:if>
    <!-- $[BUG]0038443 ADD END -->
    <div class="prev_next">
		<table id="${pagingContext.pageNo}" style="width: 100%;" cellspacing="1" cellpadding="2" border="0" class="table">
			<c:forEach items="${docList}" var="doc" varStatus="status">
				<tr id="${doc.documentLid}"
					<c:if test="${status.count%2==1}">class="odd tabEnter" onmouseout="this.className='odd';removeTabsTip('#tabstip-doc')"</c:if>
					<c:if test="${status.count%2==0}">class="even tabEnter" onmouseout="this.className='even';removeTabsTip('#tabstip-doc')"</c:if> 
					<c:if test="${!empty doc.documentLid}">
					style="cursor:pointer;" 
					onmouseover="this.className='mouseover';
					showDetailTip(event,'#tabstip-doc',
						{'文档作者':'${doc.documentAuthorName}',
						 '文档修改者':'${doc.documentModifierName}',
						 '提交时间':'<fmt:formatDate value="${doc.submitTime}" type="date" pattern="yyyy-MM-dd"/>'});"
					onclick="addMoreTabs(this,'../doc/detail_'+'${doc.documentSn}'+'.html?flag=tabs',
							{'menuPartFlag':'true','temporaryFlag':'${temporaryFlag}','width':'70%','orderSn':'${doc.serviceId}${doc.documentLid}',
							'otherName':'','holdOtherName':'',
							'name':'<c:choose>
										<c:when test="${fn:length(doc.documentName)>4}">${fn:substring(doc.documentName,0,4)}...</c:when>
										<c:otherwise>${doc.documentName}</c:otherwise>
									</c:choose>','holdName':'${doc.documentName}',
							'patientSn':'${patientSn}'},'#moreTabs');"</c:if>>
					<td height="34" align="left" <c:if test="${doc.documentLid==documentLid && !empty doc.documentLid}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
						${doc.documentTypeName}
					</td>
					<td height="34" align="left" <c:if test="${doc.documentLid==documentLid && !empty doc.documentLid}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
						${doc.documentName}
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>