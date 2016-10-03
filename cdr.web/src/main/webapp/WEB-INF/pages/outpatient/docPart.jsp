<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
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
	<script type="text/javascript" src="../scripts/doc/docList.js"></script>
</head>

<body style="margin: 0; padding: 0;">
	<c:if test="${fn:length(docList)!=0 and viewSettings.showDoc==true}">
			<table width="100%" class="ctab">
				<c:choose>
					<c:when
						test="${accessContorl == false|| (accessContorl && loginUserAclAuths.clinicalInfoAuth07)}">
						<tr>
							<td style="vertical-align: top;">
								<table width="100%" class="table">
									<tr style="height: 28px;">
										<td class="blockHeader" width="100px" colspan="6"
											style="border-right: 0px"><b> <img
												src="../images/pic_mzbl.png"
												style="padding-left: 3px; padding-right: 2px;" width="19"
												height="19" alt="" align="absmiddle" />病历
										</b></td>
										<td class="blockHeader" align="right"
											style="padding-right: 5px;"><input type="hidden"
											value="1" id="show_doc" /> <a href="javascript:void(0);"
											id="show_doc_text" onclick="showBlock('doc');"
											onfocus="this.blur()" style="text-decoration: none">▼</a></td>
									</tr>
								</table>
								<table style="width: 100%" id="docInfo" class="table">
									<tr>
										<td>
											<table style="width: 100%;" id="doct">
												<tr class="tabletitle">
													<td height="28" align="center" width="13%">文档类别</td>
													<td height="28" align="center" width="13%">文档名称</td>
													<td height="28" align="center" width="13%">文档作者</td>
													<td height="28" align="center" width="13%">文档修改者</td>
													<td height="28" align="center" width="13%">提交时间</td>
													<td height="28" align="center" width="14%">审核人</td>
													<td height="28" align="center" width="15%">签章报告</td>
												</tr>
												<c:forEach items="${docList}" var="doc" varStatus="status">
													<tr
														<c:if test="${status.count%2==1 }">class="odd tabEnter" onmouseout="this.className='odd'"</c:if>
														<c:if test="${status.count%2==0 }">class="even tabEnter" onmouseout="this.className='even'"</c:if>
														style="cursor: pointer"
														onmouseover="this.className='mouseover'"
														onclick="justShow('../doc/detail_${doc.documentSn}.html',$(this),
														{'otherName':'','holdOtherName':'','name':'${doc.documentName}','holdName':'${doc.documentName}','isDoc':true});">
														<td height="24" align="left" width="16%">${doc.documentTypeName}</td>
														<td height="24" align="left" width="16%"
															<c:if test="${fn:length(doc.documentName)>10}"> title="${doc.documentName}" </c:if>>
															<c:if test="${fn:length(doc.documentName)>10}">${fn:substring(doc.documentName,0,10)}...</c:if>
															<c:if test="${fn:length(doc.documentName)<=10}">${doc.documentName}</c:if>
														</td>
														<td height="24" align="left">${doc.documentAuthorName}</td>
														<td height="24" align="left">${doc.documentModifierName}</td>
														<td height="24" align="center"><fmt:formatDate
																value="${doc.submitTime}" type="date"
																pattern="yyyy-MM-dd" /></td>
														<td height="24" align="left">${doc.reviewPersonName}</td>
														<td height="24" align="center"
															<c:if test="${empty doc.signatureId}"> onclick="stopBubble(event);return false;"</c:if>>
															<c:if test="${!empty doc.signatureId}">
																<a href="javascript:void(0);"
																	onclick="previewDoc('${signatureUrl}','${doc.signatureId}');return false;"><img
																	src="../images/qzbg.png" align="middle"
																	style="padding-right: 1px; border: 0px" /></a>
															</c:if>
														</td>
													</tr>
												</c:forEach>
												<!-- $Author :chang_xuewen
													 $Date : 2013/07/01 16:40
													 $[BUG]0032417 MODIFY BEGIN  -->
												<c:if test="${pagingContext_doc.totalRowCnt > Constants.ROWCNT_PER_PAGE_YW}">
												<tr>
													<td colspan="10" style="height: 27px;">
														<form name="pageform_doc" method="get"
															action="../visit/docPart.html">
															<div class="pagelinks">
																<div style="float: right; height: 100%;">
																	<div class="pagedesc">共${pagingContext_doc.totalRowCnt}条记录！
																		第${pagingContext_doc.pageNo}页/共${pagingContext_doc.totalPageCnt}页</div>
																	<c:if test="${pagingContext_doc.pageNo > 1}">
																		<div class="firstPage">
																			<a href="javascript:void(0);"																				
																				onclick="turnPage(1,'#docdiv','doc');return false;"><img
																				src="../images/1.gif"
																				style="border: 0px; width: 21px; height: 16px;" /></a>
																		</div>
																		<div class="prevPage">
																			<a href="javascript:void(0);"
																				onclick="turnPage(${pagingContext_doc.pageNo-1},'#docdiv','doc');return false;"><img
																				src="../images/2.gif"
																				style="border: 0px; width: 41px; height: 16px;" /></a>
																		</div>
																	</c:if>
																	<c:forEach var="i" begin="${pagingContext_doc.pageStartNo}"
																		end="${pagingContext_doc.perPageCnt}" step="1">
																		<c:choose>
																			<c:when test="${i == pagingContext_doc.pageNo}">
																				<div class="currentPage">
																					<font color="#2D56A5">${i}</font>
																				</div>
																			</c:when>
																			<c:otherwise>
																				<div class="pageno">
																					<a href="javascript:void(0);"
																						onclick="turnPage(${i},'#docdiv','doc'); return false;"><font
																						color="#2D56A5">${i}</font></a>
																				</div>
																			</c:otherwise>
																		</c:choose>
																	</c:forEach>
																	<c:if
																		test="${pagingContext_doc.pageNo < pagingContext_doc.totalPageCnt}">
																		<div class="nextPage">
																			<a href="javascript:void(0);"
																				onclick="turnPage(${pagingContext_doc.pageNo + 1},'#docdiv','doc');return false;"><img
																				src="../images/4.gif"
																				style="border: 0px; width: 41px; height: 16px;" /></a>
																		</div>
																		<div class="lastPage">
																			<a href="javascript:void(0);"
																				onclick="turnPage(${pagingContext_doc.totalPageCnt},'#docdiv','doc');return false;"><img
																				src="../images/3.gif"
																				style="border: 0px; width: 21px; height: 16px;" /></a>
																		</div>
																	</c:if>
																	<div class="pageNum">
																		<input type="text" name="screen"
																			style="display: none;" /> <input name="pageNum"
																			onkeyup="if(event.keyCode!=13){return false;} turnToPageNo($('#doct').find('[name=pageNum]').val(),${pagingContext_doc.totalPageCnt},'#docdiv','doc');return false;"
																			style="width: 30px; float: left;" value="" />
																	</div>
																	<div class="goinput">
																		<a href="javascript:void(0);"
																			onclick="turnToPageNo($('#doct').find('[name=pageNum]').val(),${pagingContext_doc.totalPageCnt},'#docdiv','doc');return false;"><img
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