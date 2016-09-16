<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title></title>
<link type="text/css" rel="Stylesheet" href="../styles/tablelist.css"
	charset="UTF8" />
	<script type="text/javascript" src="../scripts/exam/examList.js"></script>
</head>

<body style="margin: 0; padding: 0;">
	<c:if test="${fn:length(examList)!=0 and viewSettings.showExam==true}">
			<table width="100%" class="ctab">
				<c:choose>
					<c:when
						test="${accessContorl == false|| (accessContorl && loginUserAclAuths.clinicalInfoAuth06)}">
						<tr>
							<td style="vertical-align: top;">
								<table width="100%" class="table">
									<tr style="height: 28px;">
										<td class="blockHeader" width="100px" colspan="10"
											style="border-right: 0px"><b> <img
												src="../images/pic_jc_right.gif"
												style="padding-left: 3px; padding-right: 2px;" width="20"
												height="16" alt="" align="absmiddle" />检查
										</b></td>
										<td class="blockHeader" align="right"
											style="padding-right: 5px;"><input
											style="margin-right: 2px" type="hidden" value="1"
											id="show_exam" /> <a href="javascript:void(0);"
											id="show_exam_text" onclick="showBlock('exam');"
											onfocus="this.blur()" style="text-decoration: none">▼</a></td>
									</tr>
								</table>
								<table style="width: 100%" id="examInfo" class="table">
									<tr>
										<td>
											<table style="width: 100%;" id="examt">
												<tr class="tabletitle">
													<td height="28" align="center">检查项目名</td>
													<td height="28" align="center">检查部位</td>
													<td height="28" align="center">医嘱状态</td>
													<td height="28" align="center">申请日期</td>
													<td height="28" align="center">检查方法</td>
													<td height="28" align="center">检查医生</td>
													<td height="28" align="center">检查日期</td>
													<td height="28" align="center">检查科室</td>
													<td height="28" align="center">检查结果</td>
													<td height="28" align="center">召回状态</td>
													<td height="28" align="center">影像</td>
													<td height="28" align="center">签章报告</td>
												</tr>
												<%-- <c:if test="${fn:length(examList)==0}">
													<tr>
														<td colspan="12" align="center" class="odd" height="24">没有相关数据！</td>
													</tr>
												</c:if> --%>
												<c:forEach items="${examList}" var="exam" varStatus="status">
												<!-- $Author :chang_xuewen
													$Date : 2013/07/04 11:00
											 		$[BUG]0033461 MODIFY BEGIN -->
													<!-- $Author :bin_zhang
							                                $Date : 2012/10/30 14:16$
							                                [BUG]0010922 MODIFY BEGIN -->
													<tr
														<c:choose>
													    	<c:when test="${exam.withdrawFlag==Constants.REPORT_WITHDRAW_FLAG}">class="forbidden tabEnter" onmouseout="this.className='forbidden'" onmouseover="this.className='forbidden'"</c:when>
													    	<c:otherwise>
													    		 <c:if test="${status.count%2==1 }">class="odd tabEnter" onmouseout="this.className='odd'"</c:if>
													    		 <c:if test="${status.count%2==0 }">class="even tabEnter" onmouseout="this.className='even'"</c:if>
													    	</c:otherwise>
												        </c:choose>
														style="cursor: pointer"
														onmouseover="this.className='mouseover'"
														onclick="showExamDetailDialog('${exam.withdrawFlag}','${exam.examReportSn}','${exam.patientSn}','${exam.itemClass}','${exam.examinationRegion}','${exam.examinationItem}','${exam.itemSn}','${exam.orderSn}',$(this),1,'${exam.examinationItemName}','${exam.examinationMethodName}');">
														<!--[BUG]0010922 MODIFY END -->
														<!-- $[BUG]0033461 MODIFY END -->
														<td height="24" align="center"
															<c:if test="${fn:length(exam.examinationItemName)>10}"> title="${exam.examinationItemName}" </c:if>>
															<c:if test="${fn:length(exam.examinationItemName)>10}">${fn:substring(exam.examinationItemName,0,10)}...</c:if>
															<c:if test="${fn:length(exam.examinationItemName)<=10}">${exam.examinationItemName}</c:if>
														</td>
														<td height="24" align="center">${exam.examinationRegionName}</td>
														<td height="24" align="center">${exam.orderStatusName}</td>
														<td height="24" align="center"><fmt:formatDate
																value="${exam.requestDate}" type="date"
																pattern="yyyy-MM-dd" /></td>
														<td height="24" align="center"
															<c:if test="${fn:length(exam.examinationMethodName)>10}"> title="${exam.examinationMethodName}" </c:if>>
															<c:if test="${fn:length(exam.examinationMethodName)>10}">${fn:substring(exam.examinationMethodName,0,10)}...</c:if>
															<c:if test="${fn:length(exam.examinationMethodName)<=10}">${exam.examinationMethodName}</c:if>
														</td>
														<td height="24" align="center">${exam.examiningDoctorName}</td>
														<td height="24" align="center"><fmt:formatDate
																value="${exam.examinationDate}" type="date"
																pattern="yyyy-MM-dd" /></td>
														<td height="24" align="center">${exam.examDeptName}</td>
														<td height="24" align="left">
															<c:choose>
														    	<c:when test="${exam.withdrawFlag==Constants.REPORT_WITHDRAW_FLAG}">
														    		${Constants.REPORT_WITHDRAW_CONTENT}
														    	</c:when>
														    	<c:otherwise>
														    		 ${exam.eiImagingConclusion==null?exam.erImagingConclusion:exam.eiImagingConclusion}
														    	</c:otherwise>
												        	</c:choose>
														</td>
														<td height="24" align="center"><c:if
																test="${exam.withdrawFlag==Constants.REPORT_WITHDRAW_FLAG}">
																<a href="javascript:void(0);"
																	onclick="showDialog('../exam/withdraw_${exam.examReportSn}.html?flag=tabs','召回信息', {});stopBubble(event);return false;"><img
																	src="../images/jczh.png" align="middle"
																	style="padding-right: 1px; border: 0px" /></a>
															</c:if> <c:if
																test="${exam.withdrawFlag==Constants.REPORT_MODIFY_FLAG}">
																<a href="javascript:void(0);"
																	onclick="showDialog('../exam/withdraw_${exam.examReportSn}.html?flag=tabs','召回信息', {});stopBubble(event);return false;"><img
																	src="../images/yxg.png" align="middle"
																	style="padding-right: 1px; border: 0px" /></a>
															</c:if></td>
														<td height="24" align="center"
															<c:if test="${empty exam.imageIndexNo}"> onclick="stopBubble(event);return false;"</c:if>>
															<c:if test="${!empty exam.imageIndexNo}">
																<a href="javascript:void(0);"
																	onclick="showImageCenter('${imageCenterUrl}','${exam.imageIndexNo}');return false;"
																	onfocus="this.blur()"><img
																	src="../images/pic_jc.gif" align="middle"
																	style="padding-right: 1px; border: 0px" /></a>
															</c:if>
														</td>
														<td height="24" align="center"
															<c:if test="${empty exam.signatureId}"> onclick="stopBubble(event);return false;"</c:if>>
															<c:if test="${!empty exam.signatureId}">
																<a href="javascript:void(0);"
																	onclick="previewDoc('${signatureUrl}','${exam.signatureId}');return false;"
																	onfocus="this.blur()"><img src="../images/qzbg.png"
																	align="middle" style="padding-right: 1px; border: 0px" /></a>
															</c:if>
														</td>
													</tr>
												</c:forEach>
												<!-- $Author :chang_xuewen
													 $Date : 2013/07/01 16:40
													 $[BUG]0032417 MODIFY BEGIN  -->
												<c:if test="${pagingContext_exam.totalRowCnt > Constants.ROWCNT_PER_PAGE_YW}">
												<tr>
													<td colspan="12" style="height: 27px;">
														<form name="pageform_exam" method="get"
															action="../visit/examPart.html">
															<div class="pagelinks">
																<div style="float: right; height: 100%;">
																	<div class="pagedesc">共${pagingContext_exam.totalRowCnt}条记录！
																		第${pagingContext_exam.pageNo}页/共${pagingContext_exam.totalPageCnt}页</div>
																	<c:if test="${pagingContext_exam.pageNo > 1}">
																		<div class="firstPage">
																			<a href="javascript:void(0);"																				
																				onclick="turnPage(1,'#examdiv','exam');return false;"><img
																				src="../images/1.gif"
																				style="border: 0px; width: 21px; height: 16px;" /></a>
																		</div>
																		<div class="prevPage">
																			<a href="javascript:void(0);"
																				onclick="turnPage(${pagingContext_exam.pageNo-1},'#examdiv','exam');return false;"><img
																				src="../images/2.gif"
																				style="border: 0px; width: 41px; height: 16px;" /></a>
																		</div>
																	</c:if>
																	<c:forEach var="i" begin="${pagingContext_exam.pageStartNo}"
																		end="${pagingContext_exam.perPageCnt}" step="1">
																		<c:choose>
																			<c:when test="${i == pagingContext_exam.pageNo}">
																				<div class="currentPage">
																					<font color="#2D56A5">${i}</font>
																				</div>
																			</c:when>
																			<c:otherwise>
																				<div class="pageno">
																					<a href="javascript:void(0);"
																						onclick="turnPage(${i},'#examdiv','exam'); return false;"><font
																						color="#2D56A5">${i}</font></a>
																				</div>
																			</c:otherwise>
																		</c:choose>
																	</c:forEach>
																	<c:if
																		test="${pagingContext_exam.pageNo < pagingContext_exam.totalPageCnt}">
																		<div class="nextPage">
																			<a href="javascript:void(0);"
																				onclick="turnPage(${pagingContext_exam.pageNo + 1},'#examdiv','exam');return false;"><img
																				src="../images/4.gif"
																				style="border: 0px; width: 41px; height: 16px;" /></a>
																		</div>
																		<div class="lastPage">
																			<a href="javascript:void(0);"
																				onclick="turnPage(${pagingContext_exam.totalPageCnt},'#examdiv','exam');return false;"><img
																				src="../images/3.gif"
																				style="border: 0px; width: 21px; height: 16px;" /></a>
																		</div>
																	</c:if>
																	<div class="pageNum">
																		<input type="text" name="screen"
																			style="display: none;" /> <input name="pageNum"
																			onkeyup="if(event.keyCode!=13){return false;} turnToPageNo($('#examt').find('[name=pageNum]').val(),${pagingContext_exam.totalPageCnt},'#examdiv','exam');return false;"
																			style="width: 30px; float: left;" value="" />
																	</div>
																	<div class="goinput">
																		<a href="javascript:void(0);"
																			onclick="turnToPageNo($('#examt').find('[name=pageNum]').val(),${pagingContext_exam.totalPageCnt},'#examdiv','exam');return false;"><img
																			src="../images/go.gif"
																			style="border: 0px; width: 21px; height: 15px;" /></a>
																	</div>
																</div>
															</div>															
														</form>
													</td>
												</tr>
											  </c:if>
											  <!-- $[BUG]0032417 MODIFY END  -->
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td class="noAccessMessage" align="center"></td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
			<table class="ctab">
				<tr>
					<td height="10"></td>
				</tr>
			</table>
		</c:if>
</body>
</html>