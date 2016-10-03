<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>检查详细信息</title>
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<script type="text/javascript" src="../scripts/cpoe/raphael.js"></script>
<script type="text/javascript" src="../scripts/cpoe/raphael_common.js"></script>
<script type="text/javascript" src="../scripts/cpoe/jquery.cpoe.js"></script>
<script type="text/javascript" src="../scripts/cpoe/popup.js"></script>
<script type="text/javascript" src="../scripts/cpoe/cpoe.js"></script>
<script>
	
</script>
</head>
<body>
	<div id="dialog">
		<div id="mainContent">
			<div id="tabs-2" class="tabcontainer">

				<c:if test="${fn:length(examDetail)!=0 && withdrawFlag!=constants.REPORT_WITHDRAW_FLAG}">
					<div id="tabs-2" class="tabcontainer">
						<c:choose>
							<c:when test="${examCVIS.dataSourceType == Constants.SOURCE_CVIS_EXAM}">
								<table width="100%" cellpadding="2" cellspacing="1"
									style="border: solid 1px #c0ddea; table-layout: fixed; border-collapse: collapse; border-bottom: 0px;">
									<tr>
										<td class="emrLabel">报告日期:</td>
										<td class="emrDataitem"><fmt:formatDate
												value="${examDetail[0].reportDate}" type="both"
												pattern="yyyy-MM-dd HH:mm" /></td>
										<td class="emrLabel">检查影像:</td>
										<td class="emrDataitem"></td>
									</tr>
								</table>
								<c:if
									test="${examCVIS.itemClass == Constants.EXAM_ITEMCLASS_ECHOCARDIOGRAPHY}">
									<table width="100%" cellpadding="2" cellspacing="1"
										style="border: solid 1px #c0ddea; table-layout: fixed; border-collapse: collapse; border-bottom: 0px;">
										<c:choose>
											<c:when test="${!empty examParentItem}">
												<c:forEach var="parentItem" items="${examParentItem}">
													<tr>
														<td colspan="6"><span class="CVISlabel_1">${parentItem.itemName}:</span><span
															class="CVISdataitem" style="border: 0px;">${parentItem.itemValue}</span></td>
													</tr>
													<c:set var="count" value="0" />
													<c:forEach items="${examChildItem}" var="childItem">
														<c:if test="${parentItem.examinationResultDetailSn == childItem.parentExamResultDetailSn}">
															<c:set var="count" value="${count+1}" />

															<td class="CVISlabel">${childItem.itemName}</td>
															<td class="CVISdataitem">${childItem.itemValue}</td>
															<c:if test="${(count)%3==0}">
																</tr>
																<c:set var="count" value="0" />
															</c:if>
														</c:if>
													</c:forEach>
													<c:if test="${count%3!=0}">
														<td class="CVISdataitem" colspan="${6-count*2}"></td>
													</c:if>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td colspan="6"
														style="word-break: break-all; word-wrap: break-word;"><pre>${!empty examCVIS.imagingFinding?examCVIS.imagingFinding:examCVIS.imagingConclusion}</pre>
													</td>
												</tr>
											</c:otherwise>
										</c:choose>
										<tr>
											<td colspan="6">
												<div class="CVISlabel_1">结论:</div> <span
												class="CVISdataitem"
												style="word-break: break-all; word-wrap: break-word; border: 0px;">
													<pre>${examCVIS.imagingConclusion}</pre>
											</span>
											</td>
										</tr>
										<tr>
											<td class="CVISlabel">判读医师:</td>
											<td class="CVISdataitem">${examCVIS.reviewDoctorName}</td>
											<td class="CVISlabel">执行人:</td>
											<td class="CVISdataitem">${examCVIS.reportDoctorName}</td>
											<td class="CVISlabel">报告日期：</td>
											<td class="CVISdataitem"><fmt:formatDate
													value="${examCVIS.reportDate}" type="date"
													pattern="yyyy-MM-dd HH:mm" /></td>
										</tr>
									</table>
								</c:if>
								<c:if
									test="${examCVIS.itemClass == Constants.EXAM_ITEMCLASS_CORONARY_ANGIOGRAPHY}">
									<table width="100%" cellpadding="2" cellspacing="1"
										style="border: solid 1px #c0ddea; border-collapse: collapse; border-bottom: 0px; table-layout: fixed;">

										<c:choose>
											<c:when test="${!empty examParentItem}">
												<c:forEach items="${examParentItem}" var="parentItem">
													<c:set var="count" value="${count+1}" />
													<tr>
														<td colspan="6">
															<div class="CVISlabel_1">${count}、${parentItem.itemName}:</div>
															<div class="CVISdataitem" style="border: 0px;">${parentItem.itemValue}</div>
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td colspan="6"
														style="word-break: break-all; word-wrap: break-word;"><pre>${!empty examCVIS.imagingFinding?examCVIS.imagingFinding:examCVIS.imagingConclusion}</pre>
													</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</table>
								</c:if>
							</c:when>
							<c:otherwise>
								<table width="100%" style="border: solid 1px #c0ddea">
									<tr>
										<td class="emrLabel">报告日期:</td>
										<td class="emrDataitem"><fmt:formatDate
												value="${examDetail[0].reportDate}" type="both"
												pattern="yyyy-MM-dd HH:mm" /></td>
										<td class="emrLabel">检查影像:</td>
										<td class="emrDataitem"><c:choose>
												<c:when test="${!empty examDetail[0].imageIndexNo}">
													<a href="javascript:void(0);"
														onclick="showImageCenter('${imageCenterUrl}','${examDetail[0].imageIndexNo}');return false;"
														onfocus="this.blur()"><img src="../images/pic_jc.gif"
														align="middle" style="padding-right: 1px; border: 0px" /></a>
												</c:when>
												<c:otherwise>
											无检查影像
										</c:otherwise>
											</c:choose></td>
									</tr>
									<tr class="odd">
										<td class="emrLabel">影像学表现:</td>
										<td class="emrDataitem" colspan="3">${examDetail[0].eiImagingFinding==null?examDetail[0].erImagingFinding:examDetail[0].eiImagingFinding}</td>
									</tr>
									<tr>
										<td class="emrLabel">影像学结论:</td>
										<td class="emrDataitem" colspan="3">${examDetail[0].eiImagingConclusion==null?examDetail[0].erImagingConclusion:examDetail[0].eiImagingConclusion}</td>
									</tr>
								</table>
							</c:otherwise>
						</c:choose>
						<c:if test="${fn:length(examImageSns)!=0}">
							<table width="100%" cellpadding="2" cellspacing="1"
								style="border: solid 1px #c0ddea; border-collapse: collapse; border-bottom: 0px;">
								<tr>
									<td class="blockHeader" colspan="4" height="27" align="left"
										style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">医学影像
									</td>
								</tr>
								<c:forEach items="${examImageSns}" var="examImageSn"
									varStatus="status">
									
									<img
										src="../exam/image_${examImageSn}.html?rand=<%=Math.random() %>"
										alt="医学影像图片" />
									
								</c:forEach>
							</table>
						</c:if>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>
