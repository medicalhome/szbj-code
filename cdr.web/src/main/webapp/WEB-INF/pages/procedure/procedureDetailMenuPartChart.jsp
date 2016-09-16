<%@ page language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- <script>
	var pageNo = '${pageNo}';
	$(function(){
		setLeftTDWidth();
	});
</script> -->
</head>
<body style="margin: 0; padding: 0;">
<!-- $Author :chang_xuewen
     $Date : 2013/10/24 11:00
     $[BUG]0038443 MODIFY BEGIN -->
	<c:if test="${viewFlag && !empty Procedure}">
		<table id="uptitle" style="width: 100%;" cellspacing="1"
			cellpadding="2" border="0" class="table">
			<tr class="tabletitle">
				<td id="tdTitle" height="24" align="center" width="50%">手术医嘱日期
				</td>
				<td height="24" align="center">手术名称</td>
			</tr>
		</table>
	</c:if>
	<div class="prev_next">
		<table id="${pagingContext.pageNo}" style="width: 100%;" cellspacing="1" cellpadding="2" border="0" class="table">
			<c:forEach items="${Procedure}" var="Procedure" varStatus="status">
				<tr id="${Procedure.orderSn}"
					<c:if test="${status.count%2==1}">class="odd tabEnter" onmouseout="this.className='odd';removeTabsTip('#tabstip-procedure')"</c:if>
					<c:if test="${status.count%2==0}">class="even tabEnter" onmouseout="this.className='even';removeTabsTip('#tabstip-procedure')"</c:if> 
					<c:if test="${!empty Procedure.orderSn}">
					style="cursor:pointer;" 
					onmouseover="this.className='mouseover';
					showDetailTip(event,'#tabstip-procedure',
					{'执行科室':'${Procedure.execDept}',
					'申请科室':'${Procedure.orderDept}',		
					'手术性质':'${Procedure.operationPropertyName}',
					'麻醉方式':'${Procedure.anesthesiaName}',
					'手术医生':'${Procedure.operatorName}'});"
					onclick="addMoreTabs(this,'../procedure/procedure_${Procedure.orderSn}.html?flag=tabs',
					{'menuPartFlag':'true','width':'70%','orderSn':'${Procedure.orderSn}','otherName':'','holdOtherName':'',
					'name':'<c:choose>
								<c:when test="${fn:length(Procedure.operationName)>4}">${fn:substring(Procedure.operationName,0,4)}...</c:when>
								<c:otherwise>${Procedure.operationName}</c:otherwise>
							</c:choose>','holdName':'${Procedure.operationName}',
					'patientSn':'${patientSn}'},'#moreTabs');"</c:if>>
					<td height= "34" align="center" <c:if test="${Procedure.orderSn==orderSn && !empty Procedure.orderSn}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
						<fmt:formatDate value="${Procedure.planExecTime}" type="date" pattern="yyyy-MM-dd" />
					</td>
					<td height= "34" align="left" <c:if test="${Procedure.orderSn==orderSn && !empty Procedure.orderSn}">style="background-color: #87CEFF;font-weight: bolder;"</c:if>>
						${Procedure.operationName}
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<!--$[BUG]0038443 MODIFY END -->
	
</body>
</html>