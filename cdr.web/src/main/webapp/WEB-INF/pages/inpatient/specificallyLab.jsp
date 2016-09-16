<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<jsp:useBean id="TimerAndInpatientDto" class="com.founder.cdr.dto.TimerAndInpatientDto"/>
<!DOCTYPE html PUBLIC "-//W3C//Ddiv XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/Ddiv/xhtml1-transitional.ddiv">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Pragma" content="no-cache" />
<!-- Prevents caching at the Proxy Server -->
<meta http-equiv="Expires" content="0" />
<style>
.labDropDownDivCSS{float: left; margin-left: 2px; margin-top: 2px; margin-right: 2px; width: 99%;}
</style>
<title>具体检验</title>
<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
</head>
<body style="margin: 0; padding: 0;">
	<div class="leftClass"
		<c:choose>
			<c:when test="${status.count > 2}">
				style="width:${firstDivWidth};margin-top:3px;border-bottom: 0px;" 
			</c:when>
			<c:otherwise>
				 style="width:${firstDivWidth};border-bottom: 0px;"
			</c:otherwise>
		</c:choose>>
		<c:forEach items="${specificallyLabList}" var="specificallyLabList" begin="0" step="2" 
			varStatus="status">
				<c:set var="count" value="${count+1}"/>
				<div class="leftClass" style="color: blue;height:20px;margin-left: 0px;width:100%;float:none;">
					<div
						<c:choose>
							<c:when test="${specificallyLabList.labReportSn == null}">
								style="text-decoration:none;color:black;cursor:pointer;width:100%;"
								onmouseover="this.style.color='red';"  onmouseout="this.style.color='black';"
							</c:when>
							<c:when test="${specificallyLabList.exceptionFlag != null && specificallyLabList.exceptionFlag == TimerAndInpatientDto.EXCEPTION_EXISTS_FLAG}">
								style="color:#fa7e8b;margin-top:3px;cursor:pointer;width:100%;"
								onmouseover="this.style.color='red';"  onmouseout="this.style.color='#fa7e8b';"
							</c:when>
							<c:otherwise>
								style="color:blue;margin-top:3px;cursor:pointer;width:100%;"
								onmouseover="this.style.color='red';"  onmouseout="this.style.color='blue';"
							</c:otherwise>
						</c:choose>
						onclick="showLabDetailDialogTabs('${specificallyLabList.withdrawFlag}','${specificallyLabList.labReportSn}',
								'${specificallyLabList.compositeItemSn}','${patientSn}','${specificallyLabList.itemCode}',
								'${specificallyLabList.sourceSystemId}','${specificallyLabList.labOrderSn}','${visitSn}','${inpatientDate}',
								'${TimerAndInpatientDto.LAB}','lab','true','70%','${specificallyLabList.itemName}','special');return false;"
						>
						<div style="float: left; width: 34%;" 
							<c:if test="${fn:length(specificallyLabList.itemName)>10}">title="${specificallyLabList.itemName}"</c:if>>
							<c:choose>
								<c:when test="${status.count > 2}">
									<c:if test="${fn:length(specificallyLabList.itemName)>10}">${fn:substring(specificallyLabList.itemName,0,10)}...</c:if>
									<c:if test="${fn:length(specificallyLabList.itemName)<=10}">${specificallyLabList.itemName}&nbsp;</c:if>
								</c:when>
								<c:otherwise>
									 ${specificallyLabList.itemName}&nbsp;
								</c:otherwise>
                            </c:choose>
						</div>
						<div style="float: left; width:22%;">
							${specificallyLabList.labDeptName}&nbsp;
						</div>
						<div style="float: left; width:27%;">
							${specificallyLabList.orderStatusName}&nbsp;
						</div>
						<div style="float: left; width:13%;">
							${specificallyLabList.testerName}&nbsp;
						</div>
						<%-- <div name="labDivWithControl4" style="float: left; width 20%;border: 1px solid blue;">
							<fmt:formatDate
								value="${specificallyLabList.testDate}" type="date"
								pattern="yyyy-MM-dd" />&nbsp;
						</div> --%>
						<c:if test="${specificallyLabList.sourceSystemId != Constants.SOURCE_MORPHOLOGY}">
							<div style="float: left;">
								<c:if test="${specificallyLabList.compositeItemSn != null}">
									<a href="javascript:void(0);" onclick="expandedDetail('labdivropdown_${count}');" onfocus="this.blur()"
										style="text-decoration: none">▼</a>
								</c:if>
							</div> 
						</c:if>
					</div>
				</div>
				<c:if test="${specificallyLabList.compositeItemSn != null && specificallyLabList.sourceSystemId != null}">
					<c:if test="${specificallyLabList.sourceSystemId == Constants.SOURCE_LAB}">
						<div id="labdivropdown_${count}" style="float: left;margin-left:0px;width:100%;display: none;">
							<table style="border: 1px solid #0099ff;border-top:0px;width:100%;">
								<tr>
									<td width="23%" align="center">检验具体项目名称</td>
									<td width="10%" align="center">检验值</td>
									<td width="7%" align="center">单位</td>
									<td width="20%" align="center">正常参考值范围</td>
									<td width="20%" align="center">警告参考值范围</td>
									<td width="13%" align="center">检验结果</td>
									<td width="10%" align="center">备注</td>
								</tr>
								<c:forEach items="${timerAndInpatientDtoMap}" var="timerAndInpatientDto">
									<c:if test="${specificallyLabList == timerAndInpatientDto.key}">
										<c:forEach items="${timerAndInpatientDto.value}" var="timerAndInpatient">
											<tr>
												<td align="center">
													${timerAndInpatient.itemNameCn}
													<%-- <c:choose>
														<c:when test="${fn:length(timerAndInpatientDtoList.itemNameCn)>6}">${fn:substring(timerAndInpatientDtoList.itemNameCn,0,6)}...</c:when>
														<c:otherwise></c:otherwise>
													</c:choose> --%>
												</td>
												<td align="center">
													<c:choose>
								    					<c:when test="${timerAndInpatient.resultHighLowFlagStr == 'high'}">
								    						<font color="red">${timerAndInpatient.itemValue}↑</font>
								    					</c:when>
								    					<c:when test="${timerAndInpatient.resultHighLowFlagStr == 'low'}">
								    						<font color="red">${timerAndInpatient.itemValue}↓</font>
								    					</c:when>
								    					<c:when test="${timerAndInpatient.resultHighLowFlagStr == 'normal'}">
								    						${timerAndInpatient.itemValue}
								    					</c:when>
								    					<c:otherwise>
								    						<font color="red">${timerAndInpatient.itemValue}(error)</font>
								    					</c:otherwise>
								   					</c:choose>
												</td>
												<td align="center">
													${timerAndInpatient.itemUnit}
												</td>
												<td align="center">
													<c:if test="${!empty timerAndInpatient.normalRefValueText}">${timerAndInpatient.normalRefValueText}</c:if>
													<c:if test="${empty timerAndInpatient.normalRefValueText}">${timerAndInpatient.lowValue}-${timerAndInpatient.highValue}</c:if>
												</td>
												<td align="center">
													${timerAndInpatient.warnLowValue}-${timerAndInpatient.warnHighValue}
												</td>
												<td align="center">
													${timerAndInpatient.qualitativeResults}
												</td>
												<td align="center">
													${timerAndInpatient.reportMemo}
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</c:forEach>
							</table>
						</div>
					</c:if>
					<c:if test="${specificallyLabList.sourceSystemId == Constants.SOURCE_MICROBE}">
						<c:forEach items="${map}" var="map">
							<div id="labdivropdown_${count}" style="float: left;display:none;border: 1px solid #0099ff;border-top: 0px;">
								<c:if test="${!empty map.key.sendSamplePurposeName}">
									<div class="labDropDownDivCSS">送检目的：${map.key.sendSamplePurposeName}</div>
								</c:if>
								<c:if test="${!empty map.key.testResults}">
									<div class="labDropDownDivCSS">送检结果：${map.key.testResults}</div>
								</c:if>
								<c:if test="${map.value != null}">
									<div class="labDropDownDivCSS">结果：</div>
									<c:forEach items="${map.value}" var="labResultItem">
										<c:if test="${!empty map.value}">
											<div class="labDropDownDivCSS">&nbsp;&nbsp;&nbsp;&nbsp;${labResultItem.qualitativeResults}</div>
										</c:if>
									</c:forEach>
								</c:if>
								<c:if test="${!empty map.key.technicalNote}">
									<div class="labDropDownDivCSS">评语：${map.key.technicalNote}</div>
								</c:if>
								<div class="labDropDownDivCSS">
									<div style="float: left; width: 40%;">${map.key.labDeptName}</div>
									<%-- <div style="float: left; width: 20%;" align="left">${map.key.submittingPersonName}</div> --%>
									<div style="float: left; width: 20%;" align="left">${map.key.testerName}</div>
								<div style="float: right; width: 30%;" align="left">
									<fmt:formatDate value="${map.key.reportDate}" type="date"
										pattern="yyyy-MM-dd hh:ss" />
								</div>
							</div>
							</div>
						</c:forEach>
					</c:if>
				</c:if>
			</c:forEach>
		</div>
		
	<div class="rightClass" style="width: 49%;border-bottom:0px;">
		<c:forEach items="${specificallyLabList}" var="specificallyLabList" begin="1" step="2" 
			varStatus="status">
				<c:set var="count" value="${count+1}"/>
				<div class="leftClass" style="color: blue;height:20px;margin-left: 0px;width:100%;float:none;">
					<div
						<c:choose>
							<c:when test="${specificallyLabList.labReportSn == null}">
								style="text-decoration:none;color:black;cursor:pointer;width:100%;"
								onmouseover="this.style.color='red';"  onmouseout="this.style.color='black';"
							</c:when>
							<c:when test="${specificallyLabList.exceptionFlag != null && specificallyLabList.exceptionFlag == TimerAndInpatientDto.EXCEPTION_EXISTS_FLAG}">
								style="color:#fa7e8b;margin-top:3px;cursor:pointer;width:100%;"
								onmouseover="this.style.color='red';"  onmouseout="this.style.color='#fa7e8b';"
							</c:when>
							<c:otherwise>
								style="color:blue;margin-top:3px;cursor:pointer;width:100%;"
								onmouseover="this.style.color='red';"  onmouseout="this.style.color='blue';"
							</c:otherwise>
						</c:choose>
						onclick="showLabDetailDialogTabs('${specificallyLabList.withdrawFlag}','${specificallyLabList.labReportSn}',
								'${specificallyLabList.compositeItemSn}','${patientSn}','${specificallyLabList.itemCode}',
								'${specificallyLabList.sourceSystemId}','${specificallyLabList.labOrderSn}','${visitSn}','${inpatientDate}',
								'${TimerAndInpatientDto.LAB}','lab','true','70%','${specificallyLabList.itemName}','special');return false;"
						>
						<div style="float: left; width: 34%;" <c:if test="${fn:length(specificallyLabList.itemName)>10}">title="${specificallyLabList.itemName}"</c:if>>
							<c:choose>
                                <c:when test="${fn:length(specificallyLabList.itemName)>10}">${fn:substring(specificallyLabList.itemName,0,10)}...</c:when>
                                <c:otherwise>${specificallyLabList.itemName}&nbsp;</c:otherwise>
                            </c:choose>
						</div>
						<div style="float: left; width:20%;">
							${specificallyLabList.labDeptName}&nbsp;
						</div>
						<div style="float: left; width:27%;">
							${specificallyLabList.orderStatusName}&nbsp;
						</div>
						<div style="float: left; width:13%;">
							${specificallyLabList.testerName}&nbsp;
						</div>
						<%-- <div name="labDivWithControl4" style="float: left; width 20%;border: 1px solid blue;">
							<fmt:formatDate
								value="${specificallyLabList.testDate}" type="date"
								pattern="yyyy-MM-dd" />&nbsp;
						</div> --%>
						<c:if test="${specificallyLabList.sourceSystemId != Constants.SOURCE_MORPHOLOGY}">
							<div style="float: left;">
								<c:if test="${specificallyLabList.compositeItemSn != null}">
									<a href="javascript:void(0);" onclick="expandedDetail('labdivropdown_${count}');" onfocus="this.blur()"
										style="text-decoration: none">▼</a>
								</c:if>
							</div> 
						</c:if>
					</div>
				</div>
				<c:if test="${specificallyLabList.compositeItemSn != null && specificallyLabList.sourceSystemId != null}">
					<c:if test="${specificallyLabList.sourceSystemId == Constants.SOURCE_LAB}">
						<div id="labdivropdown_${count}" style="float: left;margin-left:0px;width:100%;display: none;">
							<table style="border: 1px solid #0099ff;border-top:0px;width:100%;">
								<tr>
									<td width="23%" align="center">检验具体项目名称</td>
									<td width="10%" align="center">检验值</td>
									<td width="7%" align="center">单位</td>
									<td width="20%" align="center">正常参考值范围</td>
									<td width="20%" align="center">警告参考值范围</td>
									<td width="13%" align="center">检验结果</td>
									<td width="10%" align="center">备注</td>
								</tr>
								<c:forEach items="${timerAndInpatientDtoMap}" var="timerAndInpatientDto">
									<c:if test="${specificallyLabList == timerAndInpatientDto.key}">
										<c:forEach items="${timerAndInpatientDto.value}" var="timerAndInpatient">
											<tr>
												<td align="center">
													${timerAndInpatient.itemNameCn}
													<%-- <c:choose>
														<c:when test="${fn:length(timerAndInpatientDtoList.itemNameCn)>6}">${fn:substring(timerAndInpatientDtoList.itemNameCn,0,6)}...</c:when>
														<c:otherwise></c:otherwise>
													</c:choose> --%>
												</td>
												<td align="center">
													<c:choose>
								    					<c:when test="${timerAndInpatient.resultHighLowFlagStr == 'high'}">
								    						<font color="red">${timerAndInpatient.itemValue}↑</font>
								    					</c:when>
								    					<c:when test="${timerAndInpatient.resultHighLowFlagStr == 'low'}">
								    						<font color="red">${timerAndInpatient.itemValue}↓</font>
								    					</c:when>
								    					<c:when test="${timerAndInpatient.resultHighLowFlagStr == 'normal'}">
								    						${timerAndInpatient.itemValue}
								    					</c:when>
								    					<c:otherwise>
								    						<font color="red">${timerAndInpatient.itemValue}(error)</font>
								    					</c:otherwise>
								   					</c:choose>
												</td>
												<td align="center">
													${timerAndInpatient.itemUnit}
												</td>
												<td align="center">
													<c:if test="${!empty timerAndInpatient.normalRefValueText}">${timerAndInpatient.normalRefValueText}</c:if>
													<c:if test="${empty timerAndInpatient.normalRefValueText}">${timerAndInpatient.lowValue}-${timerAndInpatient.highValue}</c:if>
												</td>
												<td align="center">
													${timerAndInpatient.warnLowValue}-${timerAndInpatient.warnHighValue}
												</td>
												<td align="center">
													${timerAndInpatient.qualitativeResults}
												</td>
												<td align="center">
													${timerAndInpatient.reportMemo}
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</c:forEach>
							</table>
						</div>
					</c:if>
					<c:if test="${specificallyLabList.sourceSystemId == Constants.SOURCE_MICROBE}">
						<c:forEach items="${map}" var="map">
							<div id="labdivropdown_${count}" style="float: left;display:none;border: 1px solid #0099ff;border-top: 0px;">
								<c:if test="${!empty map.key.sendSamplePurposeName}">
									<div class="labDropDownDivCSS">送检目的：${map.key.sendSamplePurposeName}</div>
								</c:if>
								<c:if test="${!empty map.key.testResults}">
									<div class="labDropDownDivCSS">送检结果：${map.key.testResults}</div>
								</c:if>
								<c:if test="${map.value != null}">
									<div class="labDropDownDivCSS">结果：</div>
									<c:forEach items="${map.value}" var="labResultItem">
										<c:if test="${!empty map.value}">
											<div class="labDropDownDivCSS">&nbsp;&nbsp;&nbsp;&nbsp;${labResultItem.qualitativeResults}</div>
										</c:if>
									</c:forEach>
								</c:if>
								<c:if test="${!empty map.key.technicalNote}">
									<div class="labDropDownDivCSS">评语：${map.key.technicalNote}</div>
								</c:if>
								<div class="labDropDownDivCSS">
									<div style="float: left; width: 40%;">${map.key.labDeptName}</div>
									<%-- <div style="float: left; width: 20%;" align="left">${map.key.submittingPersonName}</div> --%>
									<div style="float: left; width: 20%;" align="left">${map.key.testerName}</div>
								<div style="float: right; width: 30%;" align="left">
									<fmt:formatDate value="${map.key.reportDate}" type="date"
										pattern="yyyy-MM-dd hh:ss" />
								</div>
							</div>
							</div>
						</c:forEach>
					</c:if>
				</c:if>
			</c:forEach>
		</div>
	<div class="gapBottom">&nbsp;</div>
</body>
</html>
