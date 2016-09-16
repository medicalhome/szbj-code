<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants"/>
<jsp:useBean id="TimerAndInpatientDto" class="com.founder.cdr.dto.TimerAndInpatientDto"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Pragma" content="no-cache" />
	<!-- Prevents caching at the Proxy Server -->
	<meta http-equiv="Expires" content="0" />
    <title>时间轴列表</title>
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
	<script type="text/javascript" src="../scripts/timerInpatient/timerInpatient.js"></script>
	<script type="text/javascript" src="../scripts/exam/examList.js"></script>
	<script type="text/javascript" src="../scripts/lab/labList.js"></script>
	<script type="text/javascript" src="../scripts/doc/docList.js"></script>
</head>
<body style="margin: 0; padding: 0;">
	<table cellpadding="0" cellspacing="0" class="tl" align="left">
		<tr>
			<td>
				<form name="conditionTimerForm" method="post" action="../timer/list_${patientSn}.html">
					<table class="blockTable" cellpadding="0" cellspacing="0" border="0">
						<tr class="condition">
							<td width="100%" align="left" valign="middle" style="border:solid 1px #D8D9DB;border-left:0px;">
								<div id="patientAlerts" class="cell reAller cellEllipsis" style="width:700px;text-align:center;">	
									<img src="../images/pic_gm.gif" style="padding-right: 6px; vertical-align:middle;"/><font
										color="#DB2C33"><b id="aller" onmouseover="createTip($('#aller').html())" onmouseout="removeTip()"></b></font>
								</div>
								<div class="cell" style="width: 58px; text-align: right; float:right; padding-right:1.5%;">
									<input type="button" id="sjzTabPrev"
										<c:choose>
											<c:when test="${visitDate != null && fn:length(visitDate) != 0 ? rowNumEnd >= totalCnt : rowNumStart <= 1}">
												disabled="true" class="but butDisabledBehind timertab_4"
											</c:when>
											<c:otherwise>
												class="but butBehind timertab_4"
											</c:otherwise>
										</c:choose>
										
										onclick="jumpTo('', 4, ${visitDate != null && fn:length(visitDate) != 0 ? rowNumEnd+1 : rowNumStart-visitRowsCnt}, 'conditionTimerForm', '#dyContent',{'visitDate':'${visitDate}','totalCnt':'${totalCnt}','pagingFlag':true},'1');return false;" style="margin-top:2px;cursor:pointer" />
								</div>
								<div class="cell" style="width: 56px; text-align: right; float:right;">
									<input type="button" id="sjzTabNext"
										<c:choose>
											<c:when test="${visitDate != null && fn:length(visitDate) != 0 ? rowNumStart <= 1 : rowNumEnd >= totalCnt}">
												disabled="true" class="but butDisabledFront timertab_3"
											</c:when>
											<c:otherwise>
												class="but butFront timertab_3"
											</c:otherwise>
										</c:choose>
										onclick="jumpTo('', 2, ${visitDate != null && fn:length(visitDate) != 0 ? rowNumStart-visitRowsCnt : rowNumEnd+1}, 'conditionTimerForm', '#dyContent',{'visitDate':'${visitDate}','totalCnt':'${totalCnt}','pagingFlag':true},'1');return false;" style="margin-top:2px;cursor:pointer" />
								</div>
								<!-- $Author :jin_peng
		         					 $Date : 2012/11/05 17:39$
		        					 [BUG]0011016 MODIFY BEGIN -->
								<div class="cell" style="width: 60px; text-align: right; float:right;">
									<input type="button" id="sjzTabGo" class="but butSearch timertab_2" onclick="searchTimer('../timer/list_${patientSn}.html', 'conditionTimerForm', '#dyContent', '${patientSn}', '1');" style="margin-top:2px;cursor:pointer" />
								</div>
								<div class="cell" id="timfirst" style="width: 100px; text-align: right; float:right;">
									<input type="text" id="visitDate" name="visitDate" style="width: 98%; margin-top:4px;"
											onmouseover="this.style.background='#FDE8FE' " onmouseout="this.style.background='#FFFFFF'"
											class="datepicker timertab_1" value="${visitDate}"  />
								</div>
								<div class="cell" style="width: 84px; text-align: right; float:right;">选择就诊日期：</div>
								<!-- [BUG]0011016 MODIFY END -->
							</td>
						</tr>
						<tr style="height:3px"></tr>
                    </table>
				</form>
			</td>
		</tr>

		<tr>
			<td>
				<table cellpadding="0" cellspacing="0" class="tl tab tabLayout" align="left" id="t1">
					<tr class="tabletitleRow"> 
						 <!-- $Author :bin_zhang
			                  $Date : 2012/12/25 10:08$
			                  [BUG]0012694 MODIFY BEGIN -->
						<td id="tabLeft" height="28" class="tabLeft" align="center"><img id="im" src="../images/tree_folder2.gif" class="tabEnter" align="middle" style="padding-right:17px !important;padding-right:12px;" onclick="hideOrShowAllContent('all');"/>
					<input type="hidden" value="1" id="show_all"/></td><!-- [BUG]0012694 MODIFY END -->
						<c:forEach items="${medicalList}" var="timer" varStatus="status" step="1">
							<td height="28" align="center" class="tabRight" style="padding-right:1px;">
								<!-- $Author :jin_peng
									 $Date : 2012/11/06 15:40$
									 [BUG]0011026 MODIFY BEGIN -->
								<!-- $Author :jin_peng
			        				 $Date : 2012/10/15 10:41$
			        				 [BUG]0010405 MODIFY BEGIN -->
								<c:choose>
									<c:when test="${timer.visitTypeCode == inpatientType}">
										<c:choose>
											<c:when test="${timer.visitStatusCode == Constants.EXIT_INPATIENT}">
												<div class="cellHead" style="width:100%; text-align:right;">
													<c:if test="${timer.visitDate == null && timer.dischargeDate == null}">&nbsp;</c:if>
													<fmt:formatDate value="${timer.visitDate}" type="date" pattern="yyyy/MM/dd" /><c:if test="${timer.dischargeDate != null}">-<fmt:formatDate value="${timer.dischargeDate}" type="date" pattern="yyyy/MM/dd" /></c:if>
												</div>
												<div class="cellHead cellHeadGround" style="height:60px ;" >
													<div class="cellHead" style="width:100%">&nbsp;<ref:translate domain="${Constants.DOMAIN_ORG_CODE}" code="${timer.orgCode}"/></div>
													<div class="cellHead" style="width:100%">&nbsp;${timer.visitDeptName}</div>
													<div class="cellHead" style="width:100%">&nbsp;${timer.visitDoctorName}</div>
												</div>
											</c:when>
											<c:otherwise>
												<div class="cellHead" style="width:100%; text-align:right;">
													<c:if test="${timer.visitDate == null && timer.dischargeDate == null}">&nbsp;</c:if>
													<a href="javascript:void(0)" onclick="changeView('#inpatientView','${patientSn}',{dischargeDateAndVisitSn:'<fmt:formatDate value="${timer.dischargeDate}" type="date" pattern="yyyy/MM/dd" />;${timer.visitSn};<fmt:formatDate value="${timer.visitDate}" type="date" pattern="yyyy/MM/dd" />'},undefined,'1')"><fmt:formatDate value="${timer.visitDate}" type="date" pattern="yyyy/MM/dd" /><c:if test="${timer.dischargeDate != null}">-<fmt:formatDate value="${timer.dischargeDate}" type="date" pattern="yyyy/MM/dd" /></c:if></a>
												</div>
												<div class="cellHead cellHeadGround" style="height:60px ;" >
													<div class="cellHead" style="width:100%">&nbsp;<a href="javascript:void(0)" onclick="changeView('#inpatientView','${patientSn}',{dischargeDateAndVisitSn:'<fmt:formatDate value="${timer.dischargeDate}" type="date" pattern="yyyy/MM/dd" />;${timer.visitSn};<fmt:formatDate value="${timer.visitDate}" type="date" pattern="yyyy/MM/dd" />'},undefined,'1')"><ref:translate domain="${Constants.DOMAIN_ORG_CODE}" code="${timer.orgCode}"/></a></div>
													<div class="cellHead" style="width:100%">&nbsp;<a href="javascript:void(0)" onclick="changeView('#inpatientView','${patientSn}',{dischargeDateAndVisitSn:'<fmt:formatDate value="${timer.dischargeDate}" type="date" pattern="yyyy/MM/dd" />;${timer.visitSn};<fmt:formatDate value="${timer.visitDate}" type="date" pattern="yyyy/MM/dd" />'},undefined,'1')">${timer.visitDeptName}</a></div>
													<div class="cellHead" style="width:100%">&nbsp;<a href="javascript:void(0)" onclick="changeView('#inpatientView','${patientSn}',{dischargeDateAndVisitSn:'<fmt:formatDate value="${timer.dischargeDate}" type="date" pattern="yyyy/MM/dd" />;${timer.visitSn};<fmt:formatDate value="${timer.visitDate}" type="date" pattern="yyyy/MM/dd" />'},undefined,'1')">${timer.visitDoctorName}</a></div>
												</div>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<div class="cellHead" style="width:100%; text-align:right">&nbsp;<a href="javascript:void(0)" onclick="changeOutpatientView('../visit/outpatientViewPart.html', '${timer.visitSn}');"><fmt:formatDate value="${timer.visitDate}" type="date" pattern="yyyy-MM-dd" />${timer.visitTypeName}</a></div>
										<div class="cellHead cellHeadGround" style="height:60px ;" >
											<div class="cellHead" style="width:100%;">&nbsp;<a href="javascript:void(0)" onclick="changeOutpatientView('../visit/outpatientViewPart.html', '${timer.visitSn}');"><ref:translate domain="${Constants.DOMAIN_ORG_CODE}" code="${timer.orgCode}"/></a></div>
											<div class="cellHead" style="width:100%;">&nbsp;<a href="javascript:void(0)" onclick="changeOutpatientView('../visit/outpatientViewPart.html', '${timer.visitSn}');">${timer.visitDeptName}</a></div>
											<div class="cellHead" style="width:100%;">&nbsp;<a href="javascript:void(0)" onclick="changeOutpatientView('../visit/outpatientViewPart.html', '${timer.visitSn}');">${timer.visitDoctorName}</a></div>
										</div>
									</c:otherwise>
								</c:choose>
								<!-- [BUG]0010405 MODIFY END -->
								<!-- [BUG]0011026 MODIFY END -->
							</td>
						</c:forEach>	
					</tr>
					<c:set var="count" value="1"/>
					<c:if test="${(useACLFlag && accessControl.clinicalInfoAuth01)||!useACLFlag}"> 
					<c:if test="${viewSettings.showDiagnosis == true}">
					<tr id="diagnosisTitle" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="25" align="center" class="tabBigLeft">
							<div class="cellBodyHeadTitleImage">
								<img src="../images/pic_zd_left.png" width="50%" height="50%"/>
							</div>
							<div class="cellBodyHeadTitleText">
								<img src="../images/tree_folder1.gif" align="middle" class="tabEnter" onclick="hideOrShowContent('diagnosis');"/><a
										href="javascript:void(0)"
										onclick="changeView('#normal', ${patientSn},'','#continueGoto_diagnosis','1');">诊 断</a>
							</div>
						</td>
						<td height="25" colspan="${visitRowsCnt}"><input type="hidden" value="1" id="show_diagnosis"/></td>
					</tr>
					<tr id="diagnosisContentt" class="${count%2==1?'evenRow':'oddRow' }">
						<td height="24">
							<div id="Dignosis" class="cellBodyHead cellBodyHeadGroundDignosis">
								<img src="../images/tree_folder2.gif" align="middle" class="tabEnter" onclick="hideOrShowContent('diagnosis');"/><a
										href="javascript:void(0)"
										onclick="changeView('#normal', ${patientSn},'','#continueGoto_diagnosis','1');">诊 断</a>
							</div>	
						</td>
						<c:forEach items="${diagnosisList}" var="diagnosis" varStatus="status">
							<td height="24" align="center"  class="cellTd tabRight">
								<c:choose>
									<c:when test="${diagnosis != null}">
										<c:forEach items="${diagnosis}" var="diaItem" varStatus="statusItem">
											<c:choose>
												<c:when test="${statusItem.count <= Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT}">
													<div class="cellBody">
														<a href="javascript:void(0)" 
															<c:if test="${diaItem.mainDiagnosisFlag == TimerAndInpatientDto.MAIN_DIAGNOSIS_FLAG}">style="color:blue;" onmouseover="this.style.color='red';" 
															onmouseout="this.style.color='blue'"</c:if> 
															onclick="loadCommonPanel('诊断详细',{'url':'../diagnosis/detail_${diaItem.diagnosisSn}.html','tabsFlag':'true','gotoType':'diagnosis_timer','width':'70%',
																	'patientSn':'${patientSn}','diagnosisSn':'${diaItem.diagnosisSn}','type':'${TimerAndInpatientDto.DIAGNOSIS}','name':'${diaItem.diseaseName}',
																	'visitSn':'${diaItem.visitSn}',trID:'${diaItem.diagnosisSn}','inpatientDate':'<fmt:formatDate value="${diaItem.inpatientDate}" type="date" pattern="yyyy-MM-dd" />'});">
															<c:if test="${diaItem.diseaseName == null}">&nbsp;</c:if> ${diaItem.diagnosisTypeName}-${diaItem.diseaseName} 
														</a>
													</div>
												</c:when>
												<c:otherwise>
													<div class="cellBodyRight">
														<a href="javascript:void(0)" 
															onclick="loadCommonPanel('诊断详细',{'url':'../diagnosis/detail_${diagnosis[0].diagnosisSn}.html','tabsFlag':'true','gotoType':'diagnosis_timer','width':'70%',
																	'patientSn':'${patientSn}','diagnosisSn':'${diagnosis[0].diagnosisSn}','type':'${TimerAndInpatientDto.DIAGNOSIS}','name':'${diagnosis[0].diseaseName}',
																	'visitSn':'${diagnosis[0].visitSn}',trID:'${diagnosis[0].diagnosisSn}','inpatientDate':'<fmt:formatDate value="${diagnosis[0].inpatientDate}" type="date" pattern="yyyy-MM-dd" />'});">
																	更多>>
														</a>
													</div>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>
					<c:set var="count" value="${count+1}"/>
					</c:if>
					</c:if>
					<c:if test="${(useACLFlag && accessControl.clinicalInfoAuth02)||!useACLFlag}"> 
					<c:if test="${viewSettings.showDrug == true}">
					<tr id="drugTitle" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="25" align="center" class="tabBigLeft">
							<div class="cellBodyHeadTitleImage">
								<img src="../images/pic_ywyz_left.png" width="30%" height="30%" align="middle"/>
							</div>
							<div class="cellBodyHeadTitleText">
								<img src="../images/tree_folder1.gif" align="middle" class="tabEnter" onclick="hideOrShowContent('drug');"/><a
										href="javascript:void(0)"
										onclick="changeView('#normal', ${patientSn},'','#continueGoto_drug','1');">药物医嘱</a>
							</div>
						</td>
						<td height="25" colspan="${visitRowsCnt}"><input type="hidden" value="1" id="show_drug"/></td>
					</tr> 
					<tr id="drugContentt" class="${count%2==1?'evenRow':'oddRow' }">
						<td height="24">
							<div id="Drug" class="cellBodyHead cellBodyHeadGroundDrug">
								<img src="../images/tree_folder2.gif" align="middle" class="tabEnter" onclick="hideOrShowContent('drug');"/><a
										href="javascript:void(0)"
										onclick="changeView('#normal', ${patientSn},'','#continueGoto_drug','1');">药物医嘱</a>
							</div>	
						</td>
						<c:forEach items="${drugOrderList}" var="medical" varStatus="status">
							<td height="24" align="center"  class="cellTd tabRight" >
								<c:choose>
									<c:when test="${medical != null}">
										<c:forEach items="${medical}" var="medItem" varStatus="statusItem">
											<c:choose>
												<c:when test="${statusItem.count <= Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT}">
													<div class="cellBody"><a href="javascript:void(0)" 
													onclick="loadCommonPanel('药物医嘱列表',{'url':'../drug/detail_${medItem.orderSn}.html?flag=tabs','tabsFlag':'true','gotoType':'drug_timer','width':'70%',
															'patientSn':'${patientSn}','orderSn':'${medItem.orderSn}','type':'${TimerAndInpatientDto.DRUG_ORDER}','visitSn':'${medItem.visitSn}',
															'name':'${medItem.drugName}',trID:'${medItem.orderSn}','inpatientDate':'<fmt:formatDate value="${medItem.inpatientDate}" type="date" pattern="yyyy-MM-dd" />'});return false;">
														<c:if test="${medItem.drugName == null && medItem.dosage == null && medItem.dosageUnit == null && medItem.medicineFrenquency == null}">&nbsp;</c:if>
														<!-- $Author :jin_peng
															 $Date : 2013/02/19 11:20$
															 [BUG]0013981 MODIFY BEGIN -->
														<c:choose>
															<c:when test="${medItem.orderType==Constants.HERBAL_MEDICINE_CLASS}">草药</c:when>
															<c:otherwise>${medItem.drugName}</c:otherwise>
														</c:choose>
														${medItem.dosage}${medItem.dosageUnit} ${medItem.medicineFrenquency}</a></div>
														<!-- [BUG]0013981 MODIFY END -->
												</c:when>
												<c:otherwise>
													<div class="cellBodyRight"><a href="javascript:void(0)" 
														onclick="loadCommonPanel('药物医嘱列表',{'url':'../drug/detail_${medical[0].orderSn}.html?flag=tabs','tabsFlag':'true','gotoType':'drug_timer','width':'70%','patientSn':'${patientSn}','orderSn':'${medical[0].orderSn}',
															'type':'${TimerAndInpatientDto.DRUG_ORDER}','visitSn':'${medical[0].visitSn}','name':'${medical[0].drugName}',trID:'${medical[0].orderSn}',
															'inpatientDate':'<fmt:formatDate value="${medical[0].inpatientDate}" type="date" pattern="yyyy-MM-dd" />'});return false;">更多>></a></div>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>	
					<c:set var="count" value="${count+1}"/>
					</c:if>
					</c:if>	
					<c:if test="${(useACLFlag && accessControl.clinicalInfoAuth06)||!useACLFlag}">
					<c:if test="${viewSettings.showExam == true}">
					<tr id="examinationTitle" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="25" align="center" class="tabBigLeft">
							<div class="cellBodyHeadTitleImage">
								<img src="../images/pic_jc_right.png" width="48%" height="48%" align="middle"/>
							</div>
							<div class="cellBodyHeadTitleText">
								<img src="../images/tree_folder1.gif" align="middle" class="tabEnter" onclick="hideOrShowContent('examination');"/><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_examination','1');">检 查</a>
							</div>
						</td>
						<td height="25" colspan="${visitRowsCnt}"><input type="hidden" value="1" id="show_examination"/></td>
					</tr> 
					<tr id="examinationContentt" class="${count%2==1?'evenRow':'oddRow' }">
						<td height="24">
							<div id="Examination" class="cellBodyHead cellBodyHeadGroundExamination">
								<img src="../images/tree_folder2.gif" align="middle" class="tabEnter" onclick="hideOrShowContent('examination');"/><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_examination','1');">检 查</a>
							</div>	
						</td>
						<c:forEach items="${examinationList}" var="examination" varStatus="status">
							<td height="24" align="center" class="cellTd tabRight">
								<c:choose>
									<c:when test="${examination != null}">
										<c:forEach items="${examination}" var="examItem" varStatus="statusItem">
										<!-- $Author :chang_xuewen
										     $Date : 2013/11/05 11:00
										     $[BUG]0040313 MODIFY BEGIN -->										
										<!--  <input id="content_${statusItem.count}" value="${examItem.examOrLabTipContent}" style="display: none; "/>-->
										<div style="display:none;" id="content_${statusItem.count}">${examItem.examOrLabTipContent}</div>
											<c:choose>
												<c:when test="${statusItem.count <= Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT}">
													<div class="cellBody">
														<a href="javascript:void(0)" 
														<c:if test="${examItem.examReportSn == null}">
															style="text-decoration:underline;color:black;cursor:pointer;" 
															onmouseover="this.style.color='red';" onmouseout="this.style.color='black';"
														</c:if>
														<c:if test="${examItem.examReportSn != null}">
															style="text-decoration:underline;color:blue;cursor:pointer" 
															onmouseover="this.style.color='red';removeTip();createExamTip('#content_${statusItem.count}');" onmouseout="this.style.color='blue';removeExamTip();"
														</c:if>
														onclick="showExamDetailDialogTabs('${examItem.withdrawFlag}','${examItem.examReportSn}','${patientSn}',
															'${examItem.itemSn}','${examItem.examOrderSn}','${TimerAndInpatientDto.EXAMINATION}',
															'${examItem.visitSn}','true','exam_timer','70%','${examItem.examinationItemName}','');">
															<c:if test="${examItem.examinationMethodName == null && examItem.examinationItemName}">&nbsp;</c:if>
															${examItem.examinationMethodName} ${examItem.examinationItemName}
														</a>
													</div>
												</c:when>
												<c:otherwise>
													<div class="cellBodyRight"><a href="javascript:void(0)" 
														onclick="showExamDetailDialogTabs('${examination[0].withdrawFlag}','${examination[0].examReportSn}','${patientSn}',
															'${examination[0].itemSn}','${examination[0].examOrderSn}','${TimerAndInpatientDto.EXAMINATION}',
															'${examination[0].visitSn}','true','exam_timer','70%','${examination[0].examinationItemName}','');">更多>></a></div>
												</c:otherwise>
											</c:choose>
											<!-- $[BUG]0040313 MODIFY END -->
										</c:forEach>
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>
					<c:set var="count" value="${count+1}"/>
					</c:if>
					</c:if>
					<c:if test="${(useACLFlag && accessControl.clinicalInfoAuth05)||!useACLFlag}"> 	
					<c:if test="${viewSettings.showLab == true}">
					<tr id="labTitle" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="25" align="center" class="tabBigLeft">
							<div class="cellBodyHeadTitleImage">
								<img src="../images/pic_jy_right.png" width="32%" height="32%" align="middle"/>
							</div>
							<div class="cellBodyHeadTitleText">
								<img src="../images/tree_folder1.gif" align="middle" class="tabEnter" onclick="hideOrShowContent('lab');"/><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_lab','1');">检 验</a>
							</div>
						</td>
						<td height="25" colspan="${visitRowsCnt}"><input type="hidden" value="1" id="show_lab"/></td>
					</tr>	
					<tr id="labContentt" class="${count%2==1?'evenRow':'oddRow' }">
						<td height="24">
							<div id="Lab" class="cellBodyHead cellBodyHeadGroundLab">
								<img src="../images/tree_folder2.gif" align="middle" class="tabEnter" onclick="hideOrShowContent('lab');"/><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_lab','1');">检 验</a>
							</div>	
						</td>
						<c:forEach items="${labList}" var="lab" varStatus="status">
							<td height="24" align="center"  class="cellTd tabRight">
								<c:choose>
									<c:when test="${lab != null}">
										<c:forEach items="${lab}" var="labItem" varStatus="statusItem">
											<c:choose>
												<c:when test="${statusItem.count <= Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT}">
													<div class="cellBody">
														<a href="javascript:void(0)" <c:if test="${fn:length(labItem.itemName)>8}"> title="${labItem.itemName}" </c:if>
															<c:choose>
																<c:when test="${labItem.labReportSn == null}">
																	style="text-decoration:underline;color:black;cursor:pointer;"
																	onmouseover="this.style.color='red';" onmouseout="this.style.color='black';"
																</c:when>
																<c:when test="${labItem.exceptionFlag != null && labItem.exceptionFlag == TimerAndInpatientDto.EXCEPTION_EXISTS_FLAG}">
																	style="text-decoration:underline;color:#fa7e8b;" onmouseover="this.style.color='red';removeTip();createLabTip('${labItem.exceptionContent}')" onmouseout="this.style.color='#fa7e8b';removeLabTip()"
																</c:when>
																<c:otherwise>
																	style="text-decoration:underline; color:blue;" onmouseover="this.style.color='red';" onmouseout="this.style.color='blue';"
																</c:otherwise>
															</c:choose>
															onclick="removeTip();showLabDetailDialogTabs('${labItem.withdrawFlag}','${labItem.labReportSn}',
																	'${labItem.compositeItemSn}','${patientSn}','${labItem.itemCode}','${labItem.sourceSystemId}','${labItem.labOrderSn}','${labItem.visitSn}',
																	'<fmt:formatDate value="${labItem.inpatientDate}" type="date" pattern="yyyy-MM-dd" />','${TimerAndInpatientDto.LAB}','lab_timer','true','70%','${labItem.itemName}');return false;">
															<c:if test="${labItem.itemName == null}">&nbsp;</c:if><c:if test="${fn:length(labItem.itemName)>8}">${fn:substring(labItem.itemName,0,6)}...</c:if>
                											<c:if test="${fn:length(labItem.itemName)<=8}">${labItem.itemName}</c:if>
														</a>
													</div>
												</c:when>
												<c:otherwise>
													<div class="cellBodyRight"><a href="javascript:void(0)" 
														onclick="showLabDetailDialogTabs('${lab[0].withdrawFlag}','${lab[0].labReportSn}',
																	'${lab[0].compositeItemSn}','${patientSn}','${lab[0].itemCode}','${lab[0].sourceSystemId}','${lab[0].labOrderSn}','${lab[0].visitSn}',
																	'<fmt:formatDate value="${labItem.inpatientDate}" type="date" pattern="yyyy-MM-dd" />','${TimerAndInpatientDto.LAB}','lab_timer','true','70%','${lab[0].itemName}');return false;">更多>></a></div>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>
					<c:set var="count" value="${count+1}"/>	
					</c:if>
					</c:if>
					<c:if test="${(useACLFlag && accessControl.clinicalInfoAuth04)||!useACLFlag}"> 	
					<c:if test="${viewSettings.showProcedure == true}">
					<tr id="procedureTitle" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="25" align="center" class="tabBigLeft">
							<div class="cellBodyHeadTitleImage">
								<img src="../images/pic_ss_left.png" width="52%" height="52%" align="middle"/>
							</div>
							<div class="cellBodyHeadTitleText">
								<img src="../images/tree_folder1.gif" align="middle" class="tabEnter" onclick="hideOrShowContent('procedure');"/><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_procedure','1');">手 术</a>
							</div>
						</td>
						<td height="25" colspan="${visitRowsCnt}"><input type="hidden" value="1" id="show_procedure"/></td>
					</tr>	
					<tr id="procedureContentt" class="${count%2==1?'evenRow':'oddRow' }">
						<td height="24">
							<div id="Procedure" class="cellBodyHead cellBodyHeadGroundProcedure">
								<img src="../images/tree_folder2.gif" align="middle" class="tabEnter" onclick="hideOrShowContent('procedure');"/><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_procedure','1');">手 术</a>
							</div>	
						</td>
						<c:forEach items="${procedureList}" var="procedure" varStatus="status">
							<td height="24" align="center"  class="cellTd tabRight">
								<c:choose>
									<c:when test="${procedure != null}">
										<c:forEach items="${procedure}" var="proItem" varStatus="statusItem">
											<c:choose>
												<c:when test="${statusItem.count <= Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT}">
													<div class="cellBody">
														<a href="javascript:void(0)" 
															onclick="loadCommonPanel('手术详细', {'url':'../procedure/procedure_${proItem.procedureSn}.html','tabsFlag':'true','gotoType':'procedure_timer',
															'width':'70%','patientSn':'${patientSn}','procedureSn':'${proItem.procedureSn}','type':'${TimerAndInpatientDto.PROCEDURE}',
															'visitSn':'${proItem.visitSn}','name':'${proItem.operationName}',trID:'${proItem.procedureSn}',
															'inpatientDate':'<fmt:formatDate value="${proItem.inpatientDate}" type="date" pattern="yyyy-MM-dd" />'});">
															<c:if test="${proItem.operationName == null}">&nbsp;</c:if>
															${proItem.operationName}
														</a>
													</div>
												</c:when>
												<c:otherwise>
													<div class="cellBodyRight"><a href="javascript:void(0)" 
														onclick="loadCommonPanel('手术详细', {'url':'../procedure/procedure_${procedure[0].procedureSn}.html','tabsFlag':'true','gotoType':'procedure_timer',
															'width':'70%','patientSn':'${patientSn}','procedureSn':'${procedure[0].procedureSn}','type':'${TimerAndInpatientDto.PROCEDURE}',
															'visitSn':'${procedure[0].visitSn}','name':'${procedure[0].operationName}',trID:'${procedure[0].procedureSn}',
															'inpatientDate':'<fmt:formatDate value="${procedure[0].inpatientDate}" type="date" pattern="yyyy-MM-dd" />'});">更多>></a></div>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>
					<c:set var="count" value="${count+1}"/>
					</c:if>
					</c:if>
					<c:if test="${(useACLFlag && accessControl.clinicalInfoAuth07)||!useACLFlag}"> 
					<c:if test="${viewSettings.showDoc == true}">
					<tr id="documentationTitle" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="25" align="center" class="tabBigLeft">
							<div class="cellBodyHeadTitleImage">
								<img src="../images/pic_blws_left.png" width="30%" height="30%" align="middle"/>
							</div>
							<div class="cellBodyHeadTitleText">
								<img src="../images/tree_folder1.gif" align="middle" class="tabEnter" onclick="hideOrShowContent('documentation');"/><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_documentation','1');">病历文书</a>
							</div>
						</td>
						<td height="25" colspan="${visitRowsCnt}"><input type="hidden" value="1" id="show_documentation"/></td>
					</tr>
					<tr id="documentationContentt" class="${count%2==1?'evenRow':'oddRow' }">
						<td height="24">
							<div id="Documentation" class="cellBodyHead cellBodyHeadGroundDocumentation">
								<img src="../images/tree_folder2.gif" align="middle" class="tabEnter" onclick="hideOrShowContent('documentation');"/><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_documentation','1');">病历文书</a>
							</div>	
						</td>
						<c:forEach items="${documenationList}" var="documentation" varStatus="status">
							<td height="24" align="center"  class="cellTd tabRight">
								<c:choose>
									<c:when test="${documentation != null}">
										<c:forEach items="${documentation}" var="docItem" varStatus="statusItem">
											<c:choose>
												<c:when test="${statusItem.count <= Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT}">
													<div class="cellBody">
														<a href="javascript:void(0)" onclick="loadCommonPanel('病历文书列表',{'url':'../doc/detail_${docItem.documentSn}.html',
														'tabsFlag':'true','gotoType':'doc_timer','width':'70%','patientSn':'${patientSn}','documentLid':'${docItem.documentLid}',
														'type':'${TimerAndInpatientDto.DOCUMENTATION}','visitSn':'${docItem.visitSn}','name':'${docItem.documentName}',trID:'${docItem.documentLid}',
															'inpatientDate':'<fmt:formatDate value="${docItem.inpatientDate}" type="date" pattern="yyyy-MM-dd" />'});">
															<c:if test="${docItem.documentName == null}">&nbsp;</c:if>
															${docItem.documentName}
														</a>
													</div>
													<div class="cellBodyRight"><c:if test="${docItem.documentAuthorName == null && docItem.writeTime == null}">&nbsp;</c:if>
													${docItem.documentAuthorName} (<fmt:formatDate value="${docItem.writeTime}" type="time" pattern="MM/dd HH:mm" />)</div>
												</c:when>
												<c:otherwise>
													<div class="cellBodyRight"><a href="javascript:void(0)" 
														onclick="loadCommonPanel('病历文书列表',{'url':'../doc/detail_${documentation[0].documentSn}.html',
														'tabsFlag':'true','gotoType':'doc_timer','width':'70%','patientSn':'${patientSn}','documentLid':'${documentation[0].documentLid}',
														'type':'${TimerAndInpatientDto.DOCUMENTATION}','visitSn':'${documentation[0].visitSn}','name':'${documentation[0].documentName}',trID:'${documentation[0].documentLid}',
															'inpatientDate':'<fmt:formatDate value="${documentation[0].inpatientDate}" type="date" pattern="yyyy-MM-dd" />'});">更多>></a></div>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>
					<c:set var="count" value="${count+1}"/>
					</c:if>
					</c:if>
					<c:if test="${(useACLFlag && accessControl.clinicalInfoAuth03)||!useACLFlag}"> 
					<c:if test="${viewSettings.showOther == true}">
					<tr id="noDrugTitle" class="${count%2==1?'evenRow':'oddRow'}">
						<td height="25" align="center" class="tabBigLeft">
							<div class="cellBodyHeadTitleImage">
								<img src="../images/pic_footer_bj.png" width="30%" height="30%" align="middle"/>
							</div>
							<div class="cellBodyHeadTitleText">
								<img src="../images/tree_folder1.gif" align="middle" class="tabEnter" onclick="hideOrShowContent('noDrug');"/><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_noDrug','1');">非药物医嘱</a>
							</div>
						</td>
						<td height="25" colspan="${visitRowsCnt}"><input type="hidden" value="1" id="show_noDrug"/></td>
					</tr>
					<tr id="noDrugContentt" class="${count%2==1?'evenRow':'oddRow' }">
						<td height="24">
							<div id="NoDrug" class="cellBodyHead cellBodyHeadGroundNoDrug">
								<img src="../images/tree_folder2.gif" align="middle" class="tabEnter" onclick="hideOrShowContent('noDrug');"/><a
									href="javascript:void(0)"
									onclick="changeView('#normal', ${patientSn},'','#continueGoto_noDrug','1');">非药物医嘱</a>
							</div>	
						</td>
						<c:forEach items="${noDrugOrderList}" var="noDrugOrder" varStatus="status">
							<td height="24" align="center"  class="cellTd tabRight">
								<c:choose>
									<c:when test="${noDrugOrder != null}">
										<c:forEach items="${noDrugOrder}" var="ndoItem" varStatus="statusItem">
											<c:choose>
												<c:when test="${statusItem.count <= Constants.TIMER_INPATIENT_DISPLAY_TD_COUNT}">
													<div class="cellBody">
														<a href="javascript:void(0)" onclick="loadCommonPanel('${ndoItem.orderTitle}',{'url':'${ndoItem.orderPath}','gotoType':'nondrug_timer',
															'tabsFlag':'true','width':'70%','name':'${fn:trim(fn:replace(ndoItem.orderName,'\"','&quot;'))}','patientSn':'${patientSn}','orderSn':'${ndoItem.orderSn}',trID:'${ndoItem.orderSn}',
															'type':'${TimerAndInpatientDto.NO_DRUG_ORDER}','visitSn':'${ndoItem.visitSn}'});">
															<c:if test="${ndoItem.orderName == null}">&nbsp;</c:if>
															${ndoItem.orderName}
														</a>
													</div>
												</c:when>
												<c:otherwise>
													<div class="cellBodyRight">
														<a href="javascript:void(0)" onclick="loadCommonPanel('${noDrugOrder[0].orderTitle}',{'url':'${noDrugOrder[0].orderPath}',
															'gotoType':'nondrug_timer','tabsFlag':'true','width':'70%','patientSn':'${patientSn}','orderSn':'${noDrugOrder[0].orderSn}',
															'type':'${TimerAndInpatientDto.NO_DRUG_ORDER}','name':'${fn:trim(fn:replace(noDrugOrder[0].orderName,'\"','&quot;'))}',trID:'${noDrugOrder[0].orderSn}','visitSn':'${noDrugOrder[0].visitSn}'});return false;">
														更多>></a></div>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>
					<c:set var="count" value="${count+1}"/>
					</c:if>
					</c:if>
				</table>
			</td>
		</tr>

		<!-- <tr>
			<td>
				<table class="blockTable" cellpadding="0" cellspacing="0" class="tl" border="0">
					<tr style="height:2px"></tr>
					<tr class="conditionCopyRight">
						<td align="left" valign="middle">
							<div class="cellFoot" style="width: 100%; text-align: center; color:white; height:29px; ">Copyright &copy; 2012 方正国际软件有限公司 版权所有</div>
						</td>
					</tr>
				</table>
			</td>
		</tr> -->
	</table>
		
	<script type="text/javascript">
		$(function () 	
		{
			//初始设置页面布局及尺寸
			setTimerDocumentSize();
			
			//初始设置过敏及危险不良反应值
			var patientAlerts=getPatientAlerts();
			if(patientAlerts==null||patientAlerts==""){
	        	$("#patientAlerts").hide();        		
        	}else{
	        	$("#aller").html(getPatientAlerts());    		
        	}
			
			//调用父js的检索框动态加载刷新页面
			condition();
			
		    //调用父js的时间格式校验
			$(".datepicker").bind("blur",parent.isDateString);
		    
			//初始化页面时隐藏所有栏目
			$("#diagnosisTitle").hide();
		    $("#drugTitle").hide();
		    $("#labTitle").hide();
		    $("#examinationTitle").hide();
		    $("#noDrugTitle").hide();
		    $("#procedureTitle").hide();
		    $("#documentationTitle").hide(); 
		    
		    logical();
		});
		
		$(window).resize(function() 
		{
			if($("#t1").size() > 0)
			{
				//当浏览器可见区域改变时重设页面布局及尺寸
				setTimerDocumentSize();			
			}
		});
		
	</script>
	
	<c:if test="${existsFlag == TimerAndInpatientDto.NO_EXISTS_FLAG && visitDate != null && fn:length(visitDate) != 0}">
		<script type="text/javascript">
			alert("该日期后不存在就诊记录");
		</script>
	</c:if>
	
	<c:if test="${existsFlag == TimerAndInpatientDto.NO_EXISTS_FLAG && (visitDate == null || fn:length(visitDate) == 0)}">
		<script type="text/javascript">
			alert("该患者不存在就诊记录");
		</script>
	</c:if>
</body>
</html>
