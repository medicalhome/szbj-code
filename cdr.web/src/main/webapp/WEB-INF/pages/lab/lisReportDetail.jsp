<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>普通检验详细</title>
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<!-- <script>
		$(function(){
            var s = '${compositeItemList[0].labResultItemList}';
   			if(s != "[]" && s != null && s != "")
   			{ 
   				//$Author :jin_peng
   				//$Date : 2013/01/28 16:38$
   				//[BUG]0013635 MODIFY BEGIN
   				jQuery("#resultLegend").load("../lab/compare.html?rand=" + Math.random(),{"itemCode":"${compositeItemList[0].labResultItemList[0].itemCode}","itemName":"${compositeItemList[0].labResultItemList[0].itemNameCn}","patientSn":"${patientSn}"});
   				//[BUG]0013635 MODIFY END
   			}
		});
    </script> -->
    <script type="text/javascript">
    	$(function()
    	{
    		var accountLogSn = '${accountLogSn}';
    		var labReportLid = '${labReportLid}';
    		var operationTime = '${operationTime}';
    		
    		// 敏感信息审计消息发送
    		$.ajax(
    		{
   			   type: "POST",
   			   url: "../lab/reportAuditSend.html",
   			   data: "accountLogSn=" + accountLogSn + "&labReportLid=" + labReportLid + "&operationTime=" + operationTime
    		});
    	});
    </script>
</head>
<body style="margin: 0; padding: 0;">
	<c:if test="${!empty labResult && labResult.withdrawFlag!=Constants.REPORT_WITHDRAW_FLAG}">
		<div class="responsability">${Constants.RESPONSABILITY_CLAIMING_CONTENT}</div>
		<c:if test="${!isOtherTabFlag }">
			<table width="100%" cellpadding="2" cellspacing="1"
				style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
				<%-- <tr>
					<td class="label">检验医生:</td>
					<td class="dataitem">${reportDetailList[0].testerName}</td>							
					<td class="label">检验日期:</td>
					<td class="dataitem"><fmt:formatDate
							value="${reportDetailList[0].testDate}" type="date"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr> --%>
				<tr >
					<td class="label">报告医生:</td>
					<td class="dataitem">${labResult.reporterName}</td>
					<td class="label">报告日期:</td>
					<td class="dataitem"><fmt:formatDate
							value="${labResult.reportDate}" type="date"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr class="odd">
					<td class="label">审核医生:</td>
					<td class="dataitem">${labResult.reviewerName}</td>
					<td class="label">审核日期:</td>
					<td class="dataitem"><fmt:formatDate
							value="${labResult.reviewDate}" type="date"
							pattern="yyyy-MM-dd HH:mm" /></td>
				</tr>
				<tr >
					<%-- <td class="label">检验方法:</td>
					<td class="dataitem">${reportDetailList[0].testMethod}</td> --%>
					<!-- <td class="dataitem">${reportDetailList[0].sampleType}</td> -->
					<td class="label">标本类型:</td>
					<td class="dataitem">${labResult.sampleTypeName}</td>
					<td class="label">表现现象:</td>
					<td class="dataitem" colspan="3">${labResult.phenomenonPerformance}</td>
				</tr>
				<tr class="odd">
					<td class="label">技术备注:</td>
					<td class="dataitem">${labResult.technicalNote}</td>
					<td class="label">报告备注:</td>
					<td class="dataitem">${labResult.reportMemo}</td>
				</tr>
				<tr class="odd">
					<td class="label">执行科室:</td>
					<td class="dataitem">${labResult.labDeptName}</td>
					<td class="label">方法:</td>
					<td class="dataitem">${labResult.method}</td>
					<%-- <td class="label">检验名称:</td>
					<td class="dataitem" colspan="3">${reportDetailList[0].itemName}</td> --%>
				</tr>
				<tr>
					<td colspan="4" height="10"></td>
				</tr>
			</table>
		</c:if>
		<c:if test="${fn:length(compositeItemList)!=0 }">
			<table cellpadding="2" cellspacing="1" class="table" width="100%"
					align="center">
				<c:forEach items="${compositeItemList}" var="compositeItem" varStatus="status">
					<tr>
						<td class="blockHeader" colspan="11" height="27" align="left"
							style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
							<img src="../images/pic_jiany.png"
							style="padding-left: 3px; padding-right: 1px;" width="19"
							height="20" alt="" align="absmiddle" />${compositeItem.itemName}
						</td>
					</tr>
					<tr height="28" class="tabletitle">
						<td align="center">检验具体项目名称</td>
						<td align="center">检验值</td>
						<td align="center">单位</td>
						<td align="center">正常参考值范围</td>
						<td align="center">警告参考值范围</td>
						<td align="center">备注</td>
					</tr>
					<c:forEach items="${compositeItem.labResultItemList}" var="resultItem"
						varStatus="status">
						<tr <c:if test="${status.count%2==1}">class="odd" onmouseout="this.className='odd'"</c:if>
							<c:if test="${status.count%2==0}">class="even" onmouseout="this.className='even'"</c:if>
							style="cursor: pointer;height:24px;" onmouseover="this.className='mouseover'" title="点击查看结果及图像"
							onclick="loadResultLegend('../lab/compare.html',{'itemCode':'${resultItem.itemCode}','itemName':'${resultItem.itemNameCn}','patientSn':'${patientSn}'},$(this));">
							<td align="left">${resultItem.itemNameCn}</td>
							<td align="right">
								<c:choose>
									<c:when test="${empty resultItem.itemValue}">
										${resultItem.qualitativeResults}
									</c:when>
	            					<c:when test="${resultItem.resultHighLowFlagStr == 'high'}">
	            						<font color="red">${resultItem.itemValue}↑<c:if test="${!empty resultItem.qualitativeResults}">/${resultItem.qualitativeResults}</c:if></font>
	            					</c:when>
	            					<c:when test="${resultItem.resultHighLowFlagStr == 'low'}">
	            						<font color="red">${resultItem.itemValue}↓<c:if test="${!empty resultItem.qualitativeResults}">/${resultItem.qualitativeResults}</c:if></font>
	            					</c:when>
	            					<c:when test="${resultItem.resultHighLowFlagStr == 'normal'}">
	            						${resultItem.itemValue}<c:if test="${!empty resultItem.qualitativeResults}">/${resultItem.qualitativeResults}</c:if>
	            					</c:when>
	            					<c:otherwise>
	            						<font color="red">${resultItem.itemValue}(error)<c:if test="${!empty resultItem.qualitativeResults}">/${resultItem.qualitativeResults}</c:if></font>
	            					</c:otherwise>
            					</c:choose>
							</td>
							<td align="center">${resultItem.itemUnit}</td>
							<td align="center">
								<c:if test="${!empty fn:trim(resultItem.normalRefValueText)}">${resultItem.normalRefValueText}</c:if>
								<c:if test="${empty fn:trim(resultItem.normalRefValueText)}"><fmt:formatNumber value="${resultItem.lowValue}" type="currency" pattern="#.####"/>-<fmt:formatNumber value="${resultItem.highValue}" type="currency" pattern="#.####"/></c:if>
							</td>
							<td align="center"><fmt:formatNumber value="${resultItem.warnLowValue}" type="currency" pattern="#.####"/>-<fmt:formatNumber value="${resultItem.warnHighValue}" type="currency" pattern="#.####"/></td>
							<td align="center">${resultItem.reportMemo}</td>
						</tr>
						<%-- <c:if test="${status.count==1}">
							<tr><td colspan="6" style="border: 4px solid #4fb5e6;"><div id='resultLegend' class='tabs-2_right'></div></td></tr>
						</c:if> --%>
					</c:forEach>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${fn:length(labImageSns)!=0 }">
			<table cellpadding="2" cellspacing="1" class="table" width="100%"
				align="center">
				<tr>
					<td class="blockHeader" colspan="11" height="27" align="left"
						style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
						<img src="../images/pic_jiany.png"
						style="padding-left: 3px; padding-right: 1px;" width="19"
						height="20" alt="" align="absmiddle" />医学影像
					</td>
				</tr>
				<c:forEach items="${labImageSns}"
					var="labImageSn" varStatus="status">
					<c:if test="${status.count%2==1}">
						<tr class="odd"><td>
					</c:if>
					<img src="../exam/image_${labImageSn}.html?rand=<%=Math.random() %>"
						alt="医学影像图片" />
					<c:if test="${status.count%2==0}">
						</td></tr>
					</c:if>
				</c:forEach>
			</table>
		</c:if>
	</c:if>
</body>
</html>