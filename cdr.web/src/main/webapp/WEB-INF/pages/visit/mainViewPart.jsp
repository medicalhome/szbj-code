<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="translation.tld" prefix="ref" %>
<%@ taglib uri="html.tld" prefix="html" %>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">
	<link type="text/css" rel="Stylesheet"
		href="../styles/normal-view-portlet.css" charset="UTF8" />
    <script type="text/javascript" src="../scripts/exam/examList.js"></script>
    <script type="text/javascript" src="../scripts/lab/labList.js"></script>
    <script type="text/javascript" src="../scripts/visit/normalViewPart.js"></script>
    <script type="text/javascript" src="../scripts/drug/drugMenuPart.js"></script>
    <script type="text/javascript">
        function getDisplayMenus() {
            return "${displayMenus}";
        }

        function getAllMenus() {
            return "${allMenus}";
        }

        function getPatientSn() {
            return "${patientSn}";
        }

        $(function() {
/*
			var panelWidth = parseInt(($(".portlets").width() - 36) / 2) - 2;

			$(".portlets .portlet").css("margin", 8);
			$(".portlets .portlet").css("display", "inline");
			$(".portlets .portlet").css("width", panelWidth);
*/
            if($(".portlet").length > 1)
            {
	            $(".portlets").sortable({
	                connectWith: ".portlets",
	                containment: "parent",
	                handle: "div.portlet-header",
	                revert: "slow",
	                cursor: "pointer"
	            });
	            $(".portlets").disableSelection();
			}			
			$( ".portlet-header" ).find( ".portlet-header-content" )
					.prepend( "<span class='portlet-header-icon ui-icon ui-icon-zoomin spanAddEnter'></span>")
					.end();

			$( ".portlet-header .portlet-header-icon" ).click(function() {
				var $portlet = $( this ).parents( ".portlet:first" );
				var portletId = $portlet.attr("id");
				if($( this ).hasClass("ui-icon-zoomin"))
				{
					/* $Author :chang_xuewen
					$Date : 2013/07/04 11:00
					$[BUG]0033461 MODIFY BEGIN */
			    	initview(2,portletId);
					toggleView("more");
					setOtherPortletsLess(portletId);
					showPortletMore(portletId);
					/* $[BUG]0033461 MODIFY END */
				}
				else if($( this ).hasClass("ui-icon-newwin"))
				{
					/* $Author :chang_xuewen
					$Date : 2013/07/04 11:00
					$[BUG]0033461 MODIFY BEGIN */
			    	initview(1,portletId);
					toggleView("orignal");
					restorePortlets(portletId);
					integratedRestore();
					/* $[BUG]0033461 MODIFY END */
				}
			});				
			
			integratedInit();
        });		
    </script>
</head>
<body style="margin: 0; padding: 0;">
    <div id="normalView" style="width:100%;">
        <div id="normalView-content" style="padding: 6px; border: solid 1px #1F7BD2;background: url(../images/pic_rightcol_bj.gif) top repeat-x;" >
	        <div class="portlets">
				<c:if test="${viewSettings.showVisit == true}">
		        <div class="portlet" id="portlet_visit">
		            <div class="portlet-header">
		                <div class="portlet-header-content">
		                    <img src="../images/pic_jz_right.png" style="padding-right: 4px;" width="19" height="20"
		                        alt="" align="absmiddle" /><span>就诊</span>
		                </div>
		            </div>
		            <div class="portlet-content">
						<div class="portlet-content-less">
							<table cellpadding="0" cellspacing="0" class="addTab" width="100%" id="t_visit">
								<tr height="28" class="tabletitle">
									<td height="28" align="center" width="15%" nowrap>就诊类型</td>
									<td height="28" align="center" width="15%" nowrap>就诊次数</td>
									<td height="28" align="center" width="20%" nowrap>就诊医生</td>
									<td height="28" align="center" width="25%" nowrap>就诊/入院日期</td>
									<td height="28" align="center" width="25%" style="border-right:0px;" nowrap>就诊/入院科室</td>
								</tr>
								<c:forEach items="${visitList}" var="visit" varStatus="loopVisit">
									<c:choose>
										<c:when test="${visit==null}">
											<tr 
												<c:if test="${loopVisit.count%2==1}">class="odd" onmouseout="this.className='odd'"</c:if>
												<c:if test="${loopVisit.count%2==0}">class="even" onmouseout="this.className='even'"</c:if>>
												<td height="24" align="center"></td>
												<td height="24" align="right"></td>
												<td height="24" align="middle"></td>
												<td height="24" align="center"></td>
												<td height="24" align="left" style="border-right:0px;"></td>
											</tr>
										</c:when>
										<c:otherwise>
											<!-- $Author :chang_xuewen
												$Date : 2013/07/04 11:00
												$[BUG]0033461 MODIFY BEGIN -->
											<tr 
												<c:if test="${loopVisit.count%2==1}">class="odd" onmouseout="this.className='odd'"</c:if>
												<c:if test="${loopVisit.count%2==0}">class="even" onmouseout="this.className='even'"</c:if>
												style="cursor: pointer"
												onmouseover="this.className='mouseover'"
												onclick="showBigPortlet($(this),'../visit/detail_${visit.visitSn}.html?flag=tabs',{'name':'${visit.visitTypeName}${visit.visitTimes}次','holdName':'${visit.visitTypeName}${visit.visitTimes}次','otherName':'','holdOtherName':''});">
												<!-- $[BUG]0033461 MODIFY END -->
												<td height="24" align="center"
													<c:if test="${fn:length(visit.visitTypeName)>6}"> title="${visit.visitTypeName}" </c:if>>
													<c:if test="${fn:length(visit.visitTypeName)>6}">${fn:substring(visit.visitTypeName,0,6)}...</c:if>
													<c:if test="${fn:length(visit.visitTypeName)<=6}">${visit.visitTypeName}</c:if>
												</td>
												<td height="24" align="right">
													${visit.visitTimes}
												</td>
												<td height="24" align="middle"
													<c:if test="${fn:length(fn:trim(visit.visitDoctorName))>6}"> title="${visit.visitDoctorName}" </c:if>>
													<c:if test="${fn:length(fn:trim(visit.visitDoctorName))>6}">${fn:substring(visit.visitDoctorName,0,6)}...</c:if>
													<c:if test="${fn:length(fn:trim(visit.visitDoctorName))<=6}">${visit.visitDoctorName}</c:if>
												</td>
												<td height="24" align="center">
													<fmt:formatDate value="${visit.visitDate}" type="date" pattern="yyyy-MM-dd" /></td>
												<td height="24" align="left" style="border-right:0px;" 
													<c:if test="${fn:length(visit.visitDeptName)>6}"> title="${visit.visitDeptName}" </c:if>>
													<c:if test="${fn:length(visit.visitDeptName)>6}">${fn:substring(visit.visitDeptName,0,6)}...</c:if>
													<c:if test="${fn:length(visit.visitDeptName)<=6}">${visit.visitDeptName}</c:if>
												</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</table>
						</div>
						<div class="portlet-content-more" style="display:none" />
					</div>
				</div>
		        </c:if>
				<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth01)}">
				<c:if test="${viewSettings.showDiagnosis == true}">
		        <div class="portlet" id="portlet_diagnosis">
		            <div class="portlet-header">
		                <div class="portlet-header-content">
		                    <img src="../images/pic_zd_right.gif" style="padding-right: 4px;" width="19" height="16"
		                        alt="" align="absmiddle" /><span>诊断</span>
		                </div>
		            </div>
		            <div class="portlet-content">
			            <div class="portlet-content-less">
							<table cellpadding="0" cellspacing="0" class="addTab" width="100%" id="t_diagnosis">
								<tr class="tabletitle">
									<td height="28" align="center" width="15%" nowrap>诊断类型</td>
									<td height="28" align="center" width="25%" nowrap>疾病名称</td>
									<td height="28" align="center" width="25%" nowrap>科室</td>
									<td height="28" align="center" width="15%" nowrap>医生</td>
									<td height="28" align="center" width="20%" style="border-right:0px;" nowrap>诊断日期</td>
								</tr>
								<c:forEach items="${diagnosisList}" var="diagnosis"
									varStatus="loopDiagnosis">
									<c:choose>
										<c:when test="${diagnosis==null}">
											<tr
												<c:if test="${loopDiagnosis.count%2==1}">class="odd" onmouseout="this.className='odd'"</c:if>
												<c:if test="${loopDiagnosis.count%2==0}">class="even" onmouseout="this.className='even'"</c:if>>
												<td height="24" align="left" width="11%"></td>
												<td height="24" align="left" width="11%"></td>
												<td height="24" align="left"></td>
												<td height="24" align="left"></td>
												<td height="24" align="center" style="border-right:0px;"></td>
											</tr>
										</c:when>
										<c:otherwise>
										<!-- $Author :chang_xuewen
												$Date : 2013/07/04 11:00
												$[BUG]0033461 MODIFY BEGIN -->
											<tr
												<c:if test="${loopDiagnosis.count%2==1}">class="odd" onmouseout="this.className='odd'"</c:if>
												<c:if test="${loopDiagnosis.count%2==0}">class="even" onmouseout="this.className='even'"</c:if>
												onclick="showBigPortlet($(this),'../diagnosis/detail_${diagnosis.diagnosisSn}.html?flag=tabs',{'name':'${diagnosis.diseaseName}','holdName':'${diagnosis.diseaseName}','otherName':'','holdOtherName':''});"
												style="cursor: pointer;"
												onmouseover="this.className='mouseover'">
												<!-- $[BUG]0033461 MODIFY END -->
												<td height="24" align="left" width="11%"
													<c:if test="${fn:length(diagnosis.diagnosisTypeName)>6}"> title="${diagnosis.diagnosisTypeName}" </c:if>>
													<c:if test="${fn:length(diagnosis.diagnosisTypeName)>6}">${fn:substring(diagnosis.diagnosisTypeName,0,6)}...</c:if>
													<c:if test="${fn:length(diagnosis.diagnosisTypeName)<=6}">${diagnosis.diagnosisTypeName}</c:if>												
												</td>
												<td height="24" align="left" width="11%"
													<c:if test="${fn:length(diagnosis.diseaseName)>6}"> title="${diagnosis.diseaseName}" </c:if>>
													<c:if test="${fn:length(diagnosis.diseaseName)>6}">${fn:substring(diagnosis.diseaseName,0,6)}...</c:if>
													<c:if test="${fn:length(diagnosis.diseaseName)<=6}">${diagnosis.diseaseName}</c:if>
												</td>
												<td height="24" align="left"
													<c:if test="${fn:length(diagnosis.diagnosticDeptName)>6}">  title="${diagnosis.diagnosticDeptName}" </c:if>>
													<c:if test="${fn:length(diagnosis.diagnosticDeptName)>6}">${fn:substring(diagnosis.diagnosticDeptName,0,6)}...</c:if>
													<c:if test="${fn:length(diagnosis.diagnosticDeptName)<=6}">${diagnosis.diagnosticDeptName}</c:if>
												</td>
												<td height="24" align="left"
													<c:if test="${fn:length(diagnosis.diagnosisDoctorName)>6}"> title="${diagnosis.diagnosisDoctorName}" </c:if>>
													<c:if test="${fn:length(diagnosis.diagnosisDoctorName)>6}">${fn:substring(diagnosis.diagnosisDoctorName,0,6)}...</c:if>
													<c:if test="${fn:length(diagnosis.diagnosisDoctorName)<=6}">${diagnosis.diagnosisDoctorName}</c:if>
												</td>
												<td height="24" align="center" style="border-right:0px;">
													<fmt:formatDate value="${diagnosis.diagnosisDate}" type="date" pattern="yyyy-MM-dd" />
												</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</table>
						</div>
			            <div class="portlet-content-more" style="display:none" />
		            </div>
		        </div>
				</c:if>
				</c:if>			
				<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth02)}">	
				<c:if test="${viewSettings.showLongDrug == true}">
		        <div class="portlet" id="portlet_longdrug">
		            <div class="portlet-header">
		                <div class="portlet-header-content">
		                    <img src="../images/pic_ywyz_right.png" style="padding-right: 4px;" width="19" height="20"
		                        alt="" align="absmiddle" /><span>长期药物医嘱</span>
		                </div>
		            </div>
		            <div class="portlet-content">
			            <div class="portlet-content-less">
							<table cellpadding="0" cellspacing="0" class="addTab" width="100%" id="t_longdrug">
								<tr class="tabletitle">
									<td height="28" align="center" nowrap>药物商品名</td>
									<td height="28" align="center" nowrap>药物类型</td>
									<td height="28" align="center" nowrap>药物剂型</td>
									<td height="28" align="center" nowrap>用药途径</td>
									<td height="28" align="center" style="border-right:0px;" nowrap>使用频率</td>
								</tr>
								<c:forEach items="${drugLongList}" var="drugLong" varStatus="loopDrug">
									<c:choose>
										<c:when test="${drugLong==null}">
											<tr
												<c:if test="${loopDrug.count%2==1}">class="odd" onmouseout="this.className='odd'"</c:if>
												<c:if test="${loopDrug.count%2==0}">class="even" onmouseout="this.className='even'"</c:if>>
												<td height="24" align="left" style="padding-left: 2px;"></td>
												<td height="24" align="left"></td>
												<td height="24" align="left"></td>
												<td height="24" align="left"></td>
												<td height="24" align="left" style="border-right:0px;"></td>
											</tr>
										</c:when>
										<c:otherwise>
										<!-- $Author :chang_xuewen
												$Date : 2013/07/04 11:00
												$[BUG]0033461 MODIFY BEGIN -->
											<tr id="tr31"
												<c:if test="${loopDrug.count%2==1}">class="odd" onmouseout="this.className='odd'"</c:if>
												<c:if test="${loopDrug.count%2==0}">class="even" onmouseout="this.className='even'"</c:if>
												style="cursor: pointer"
												onmouseover="this.className='mouseover'"
												onclick="showBigPortlet($(this),'../drug/detail_${drugLong.orderSn}.html?flag=tabs',{'otherName':'<c:choose>
																<c:when test="${drugLong.orderType==Constants.HERBAL_MEDICINE_CLASS}">草药&nbsp;</c:when>										
																<c:otherwise>${drugLong.drugName}&nbsp;</c:otherwise>
																</c:choose>','holdOtherName':'${drugLong.drugName}',
																'name':'<c:choose>
															<c:when test="${drugLong.orderType==Constants.HERBAL_MEDICINE_CLASS}">草药&nbsp;</c:when>
															<c:otherwise>${drugLong.drugName}&nbsp;</c:otherwise>
														</c:choose>','holdName':'${drugLong.drugName}'});">
												<!-- $[BUG]0033461 MODIFY END -->
												<td height="24" align="left" style="padding-left: 2px;"
													<c:if test="${fn:length(drugLong.drugName)>6}"> title="${drugLong.drugName}" </c:if>>
													<!-- $Author :jin_peng
														 $Date : 2013/02/19 11:20$
														 [BUG]0013981 MODIFY BEGIN -->
													<c:choose>
														<c:when test="${drugLong.orderType==Constants.HERBAL_MEDICINE_CLASS}"><font color="blue">草药</font></c:when>
														<c:when test="${fn:length(drugLong.drugName)>6}">${fn:substring(drugLong.drugName,0,6)}...</c:when>
														<c:otherwise>${drugLong.drugName}</c:otherwise>
													</c:choose>
													<!-- [BUG]0013981 MODIFY END -->
												</td>
												<td height="24" align="left"
													<c:if test="${fn:length(drugLong.medicineTypeName)>6}"> title="${drugLong.medicineTypeName}" </c:if>>
													<c:if test="${fn:length(drugLong.medicineTypeName)>6}">${fn:substring(drugLong.medicineTypeName,0,6)}...</c:if>
													<c:if test="${fn:length(drugLong.medicineTypeName)<=6}">${drugLong.medicineTypeName}</c:if>
												</td>
												<td height="24" align="left"
													<c:if test="${fn:length(drugLong.medicineForm)>6}"> title="${drugLong.medicineForm}" </c:if>>
													<c:if test="${fn:length(drugLong.medicineForm)>6}">${fn:substring(drugLong.medicineForm,0,6)}...</c:if>
													<c:if test="${fn:length(drugLong.medicineForm)<=6}">${drugLong.medicineForm}</c:if>
												</td>
												<td height="24" align="left"
													<c:if test="${fn:length(drugLong.routeName)>6}"> title="${drugLong.routeName}" </c:if>>
													<c:if test="${fn:length(drugLong.routeName)>6}">${fn:substring(drugLong.routeName,0,6)}...</c:if>
													<c:if test="${fn:length(drugLong.routeName)<=6}">${drugLong.routeName}</c:if>
												</td>
												<td height="24" align="left" style="border-right:0px;"
													<c:if test="${fn:length(drugLong.medicineFreqName)>6}"> title="${drugLong.medicineFreqName}" </c:if>>
													<c:if test="${fn:length(drugLong.medicineFreqName)>6}">${fn:substring(drugLong.medicineFreqName,0,6)}...</c:if>
													<c:if test="${fn:length(drugLong.medicineFreqName)<=6}">${drugLong.medicineFreqName}</c:if>
												</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</table>
						</div>
						<div class="portlet-content-more" style="display:none" />					
		            </div>
		        </div>
				</c:if>
				<c:if test="${viewSettings.showShortDrug == true}">				
		        <div class="portlet" id="portlet_shortdrug">
		            <div class="portlet-header">
		                <div class="portlet-header-content">
		                    <img src="../images/pic_ywyz_right.png" style="padding-right: 4px;" width="19" height="20"
		                        alt="" align="absmiddle" /><span>临时药物医嘱</span>
		                </div>
		            </div>
		            <div class="portlet-content">
			            <div class="portlet-content-less">
							<table cellpadding="0" cellspacing="0" class="addTab" width="100%" id="t_shortdrug">
								<tr class="tabletitle">
									<td height="28" align="center" nowrap>药物商品名</td>
									<td height="28" align="center" nowrap>药物类型</td>
									<td height="28" align="center" nowrap>药物剂型</td>
									<td height="28" align="center" nowrap>用药途径</td>
									<td height="28" align="center" style="border-right:0px;" nowrap>使用频率</td>
								</tr>
								<c:forEach items="${drugShortList}" var="drugShort" varStatus="loopDrug">
									<c:choose>
										<c:when test="${drugShort==null}">
											<tr
												<c:if test="${loopDrug.count%2==1}">class="odd" onmouseout="this.className='odd'"</c:if>
												<c:if test="${loopDrug.count%2==0}">class="even" onmouseout="this.className='even'"</c:if>>
												<td height="24" align="left" style="padding-left: 2px;"></td>
												<td height="24" align="left"></td>
												<td height="24" align="left"></td>
												<td height="24" align="left"></td>
												<td height="24" align="left" style="border-right:0px;"></td>
											</tr>
										</c:when>
										<c:otherwise>
										<!-- $Author :chang_xuewen
												$Date : 2013/07/04 11:00
												$[BUG]0033461 MODIFY BEGIN -->
											<tr id="tr31"
												<c:if test="${loopDrug.count%2==1}">class="odd " onmouseout="this.className='odd'"</c:if>
												<c:if test="${loopDrug.count%2==0}">class="even" onmouseout="this.className='even'"</c:if>
												style="cursor: pointer"
												onmouseover="this.className='mouseover'"
												onclick="showBigPortlet($(this),'../drug/detail_${drugShort.orderSn}.html?flag=tabs',{'otherName':'<c:choose>
																<c:when test="${drugShort.orderType==Constants.HERBAL_MEDICINE_CLASS}">草药&nbsp;</c:when>										
																<c:otherwise>${drugShort.drugName}&nbsp;</c:otherwise>
																</c:choose>','holdOtherName':'${drugShort.drugName}',
																'name':'<c:choose>
															<c:when test="${drugShort.orderType==Constants.HERBAL_MEDICINE_CLASS}">草药&nbsp;</c:when>
															<c:otherwise>${drugShort.drugName}&nbsp;</c:otherwise>
														</c:choose>','holdName':'${drugShort.drugName}'});">
												<!-- $[BUG]0033461 MODIFY END -->
												<td height="24" align="left" style="padding-left: 2px;"
													<c:if test="${fn:length(drugShort.drugName)>6}"> title="${drugShort.drugName}" </c:if>>
													<!-- $Author :jin_peng
														 $Date : 2013/02/19 11:20$
														 [BUG]0013981 MODIFY BEGIN -->
													<c:choose>
														<c:when test="${drugShort.orderType==Constants.HERBAL_MEDICINE_CLASS}"><font color="blue">草药</font></c:when>
														<c:when test="${fn:length(drugShort.drugName)>6}">${fn:substring(drugShort.drugName,0,6)}...</c:when>
														<c:otherwise>${drugShort.drugName}</c:otherwise>
													</c:choose>
													<!-- [BUG]0013981 MODIFY END -->
												</td>
												<td height="24" align="left"
													<c:if test="${fn:length(drugShort.medicineTypeName)>6}"> title="${drugShort.medicineTypeName}" </c:if>>
													<c:if test="${fn:length(drugShort.medicineTypeName)>6}">${fn:substring(drugShort.medicineTypeName,0,6)}...</c:if>
													<c:if test="${fn:length(drugShort.medicineTypeName)<=6}">${drugShort.medicineTypeName}</c:if>
												</td>
												<td height="24" align="left"
													<c:if test="${fn:length(drugShort.medicineForm)>6}"> title="${drugShort.medicineForm}" </c:if>>
													<c:if test="${fn:length(drugShort.medicineForm)>6}">${fn:substring(drugShort.medicineForm,0,6)}...</c:if>
													<c:if test="${fn:length(drugShort.medicineForm)<=6}">${drugShort.medicineForm}</c:if>
												</td>
												<td height="24" align="left"
													<c:if test="${fn:length(drugShort.routeName)>6}"> title="${drugShort.routeName}" </c:if>>
													<c:if test="${fn:length(drugShort.routeName)>6}">${fn:substring(drugShort.routeName,0,6)}...</c:if>
													<c:if test="${fn:length(drugShort.routeName)<=6}">${drugShort.routeName}</c:if>
												</td>
												<td height="24" align="left" style="border-right:0px;"
													<c:if test="${fn:length(drugShort.medicineFreqName)>6}"> title="${drugShort.medicineFreqName}" </c:if>>
													<c:if test="${fn:length(drugShort.medicineFreqName)>6}">${fn:substring(drugShort.medicineFreqName,0,6)}...</c:if>
													<c:if test="${fn:length(drugShort.medicineFreqName)<=6}">${drugShort.medicineFreqName}</c:if>												
												</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</table>
						</div>
						<div class="portlet-content-more" style="display:none" />
		            </div>
		        </div>
				</c:if>
				</c:if>
				<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth06)}">
				<c:if test="${viewSettings.showExam == true}">				
		        <div class="portlet" id="portlet_exam">
		            <div class="portlet-header">
		                <div class="portlet-header-content">
		                    <img src="../images/pic_jc_right.gif" style="padding-right: 4px;" width="19" height="20"
		                        alt="" align="absmiddle" /><span>检查</span>
		                </div>
		            </div>
		            <div class="portlet-content">
			            <div class="portlet-content-less">
							<table cellpadding="0" cellspacing="0" class="addTab" width="100%" id="t_exam">
								<tr class="tabletitle">
									<td height="28" align="center" nowrap>检查项目名</td>
									<td height="28" align="center" nowrap>检查科室</td>
									<td height="28" align="center" nowrap>检查部位</td>
									<td height="28" align="center" nowrap>检查方法</td>
									<td height="28" align="center" style="border-right:0px;" nowrap>开嘱科室</td>
								</tr>
								<c:forEach items="${examList}" var="exam" varStatus="loopExam">
									<c:choose>
										<c:when test="${exam==null}">
											<tr
												<c:if test="${loopExam.count%2==1}">class="odd" onmouseout="this.className='odd'"</c:if>
												<c:if test="${loopExam.count%2==0}">class="even" onmouseout="this.className='even'"</c:if>>
												<td height="24" align="left"></td>
												<td height="24" align="left"></td>
												<td height="24" align="left"></td>
												<td height="24" align="left"></td>
												<td height="24" align="left" style="border-right:0px;"></td>
											</tr>
										</c:when>
										<c:otherwise>
										<!-- $Author :chang_xuewen
												$Date : 2013/07/04 11:00
												$[BUG]0033461 MODIFY BEGIN -->
											<tr
												<c:choose>
											    	<c:when test="${exam.withdrawFlag==Constants.REPORT_WITHDRAW_FLAG}">
											    		class="forbidden" onmouseout="this.className='forbidden'" onmouseover="this.className='forbidden'"
											    	</c:when>
													<c:when test="${empty exam.examReportSn}">
														class="orderOnly" onmouseout="this.className='orderOnly'" 	
													</c:when>
													<c:when test="${empty exam.orderSn}">
														class="reportOnly" onmouseout="this.className='reportOnly'" 
													</c:when>
											    	<c:otherwise>
											    		 <c:if test="${status.count%2==1 }">class="odd" onmouseout="this.className='odd'"</c:if>
											    		 <c:if test="${status.count%2==0 }">class="even" onmouseout="this.className='even'"</c:if>
											    	</c:otherwise>
										        </c:choose>
												style="cursor: pointer"
												onmouseover="this.className='mouseover'"
												onclick="showExamDetailDialog('${exam.withdrawFlag}','${exam.examReportSn}','${exam.patientSn}','${exam.itemClass}','${exam.examinationRegion}','${exam.examinationItem}','${exam.itemSn}','${exam.orderSn}',$(this),3,'${exam.examinationItemName}','${exam.examinationMethodName}');return false;">
												<!-- $[BUG]0033461 MODIFY END -->
												<td height="24" align="left"
													<c:if test="${fn:length(exam.examinationItemName)>6}">  title="${exam.examinationItemName}" </c:if>>
													<c:if test="${fn:length(exam.examinationItemName)>6}">${fn:substring(exam.examinationItemName,0,6)}...</c:if>
													<c:if test="${fn:length(exam.examinationItemName)<=6}">${exam.examinationItemName}</c:if>
												</td>
												<td height="24" align="left"
													<c:if test="${fn:length(exam.examDeptName)>6}"> title="${exam.examDeptName}" </c:if>>
													<c:if test="${fn:length(exam.examDeptName)>6}">${fn:substring(exam.examDeptName,0,6)}...</c:if>
													<c:if test="${fn:length(exam.examDeptName)<=6}">${exam.examDeptName}</c:if>												
												</td>
												<td height="24" align="left"
													<c:if test="${fn:length(exam.examinationRegionName)>6}"> title="${exam.examinationRegionName}" </c:if>>
													<c:if test="${fn:length(exam.examinationRegionName)>6}">${fn:substring(exam.examinationRegionName,0,6)}...</c:if>
													<c:if test="${fn:length(exam.examinationRegionName)<=6}">${exam.examinationRegionName}</c:if>
												</td>
												<td height="24" align="left"
													<c:if test="${fn:length(exam.examinationMethodName)>6}"> title="${exam.examinationMethodName}" </c:if>>
													<c:if test="${fn:length(exam.examinationMethodName)>6}">${fn:substring(exam.examinationMethodName,0,6)}...</c:if>
													<c:if test="${fn:length(exam.examinationMethodName)<=6}">${exam.examinationMethodName}</c:if>
												</td>
												<%-- <td height="24" align="left"><c:if
														test="${exam.withdrawFlag==Constants.REPORT_WITHDRAW_FLAG}">
														<a href="javascript:void(0);"
															onclick="showDialog('../exam/withdraw_${exam.examReportSn}.html?flag=tabs','召回信息', {});stopBubble(event);return false;">已召回</a>
													</c:if> <c:if test="${exam.withdrawFlag==Constants.REPORT_MODIFY_FLAG}">
														<a href="javascript:void(0);"
															onclick="showDialog('../exam/withdraw_${exam.examReportSn}.html?flag=tabs','召回信息', {});stopBubble(event);return false;">已修改</a>
													</c:if></td> --%>
												<td height="24" align="center" style="border-right:0px;"
													<c:if test="${fn:length(exam.orderDeptName)>6}"> title="${exam.orderDeptName}" </c:if>>
													<c:if test="${fn:length(exam.orderDeptName)>6}">${fn:substring(exam.orderDeptName,0,6)}...</c:if>
													<c:if test="${fn:length(exam.orderDeptName)<=6}">${exam.orderDeptName}</c:if>												
												</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</table>
						</div>
			            <div class="portlet-content-more" style="display:none" />
		            </div>
		        </div>
				</c:if>
				</c:if>			
				<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth05)}">
				<c:if test="${viewSettings.showLab == true}">				
		        <div class="portlet" id="portlet_lab">
		            <div class="portlet-header">
		                <div class="portlet-header-content">
		                    <img src="../images/pic_jiany.png" style="padding-right: 4px;" width="19" height="20"
		                        alt="" align="absmiddle" /><span>检验</span>
		                </div>
		            </div>
		            <div class="portlet-content">
			            <div class="portlet-content-less">
							<table cellpadding="0" cellspacing="0" class="addTab" width="100%" id="t_lab">
								<tr class="tabletitle">
									<td height="28" align="center" width="22%" nowrap>执行科室</td>
									<td height="28" align="center" width="15%" nowrap>检验医生</td>
									<td height="28" align="center" width="25%" nowrap>检验名称</td>
									<td height="28" align="center" width="16%" nowrap>检验日期</td>
									<td height="28" align="center" width="22%" style="border-right:0px;" nowrap>开嘱科室</td>
								</tr>
								<c:forEach items="${labList}" var="lab" varStatus="loopLab">
									<c:choose>
										<c:when test="${lab==null}">
											<tr
												<c:if test="${loopLab.count%2==1}">class="odd" onmouseout="this.className='odd'"</c:if>
												<c:if test="${loopLab.count%2==0}">class="even" onmouseout="this.className='even'"</c:if>>
												<td height="24" align="left"></td>
												<td height="24" align="left"></td>
												<td height="24" align="center"></td>
												<td height="24" align="center"></td>
												<td height="24" align="left" style="border-right:0px;"></td>
											</tr>
										</c:when>
										<c:otherwise>
										<!-- $Author :chang_xuewen
												$Date : 2013/07/04 11:00
												$[BUG]0033461 MODIFY BEGIN -->
											<tr
												<c:choose>
											    	<c:when test="${lab.withdrawFlag==Constants.REPORT_WITHDRAW_FLAG}">
											    		class="forbidden" onmouseout="this.className='forbidden'" onmouseover="this.className='forbidden'"
											    	</c:when>
											    	<c:when test="${lab.reviewerId=='000'}">
											    		class="tempReport" onmouseout="this.className='tempReport'"
											    	</c:when>
											    	<c:when test="${empty lab.labReportSn}">
														class="orderOnly" onmouseout="this.className='orderOnly'" 
													</c:when>
													<c:when test="${empty lab.orderSn}">
														class="reportOnly" onmouseout="this.className='reportOnly'" 
													</c:when>
											    	<c:otherwise>
											    		 <c:if test="${status.count%2==1 }">class="odd" onmouseout="this.className='odd'"</c:if>
											    		 <c:if test="${status.count%2==0 }">class="even" onmouseout="this.className='even'"</c:if>
											    	</c:otherwise>
										        </c:choose>
												style="cursor: pointer;"
												onmouseover="this.className='mouseover'"
												onclick="showLabDetailDialog('${lab.withdrawFlag}','${lab.labReportSn}','${lab.compositeItemSn}','${patientSn}','${lab.itemCode}','${lab.sourceSystemId}','${lab.orderSn}',$(this),3,'${lab.itemName}');return false;">
												<!-- $[BUG]0033461 MODIFY END -->
												<td height="24" align="left" 
													<c:if test="${fn:length(lab.labDeptName)>6}"> title="${lab.labDeptName}" </c:if>>
													<c:if test="${fn:length(lab.labDeptName)>6}">${fn:substring(lab.labDeptName,0,6)}...</c:if>
													<c:if test="${fn:length(lab.labDeptName)<=6}">${lab.labDeptName}</c:if></td>
												<td height="24" align="left" 
													<c:if test="${fn:length(lab.testerName)>6}"> title="${lab.testerName}" </c:if>>
													<c:if test="${fn:length(lab.testerName)>6}">${fn:substring(lab.testerName,0,6)}...</c:if>
													<c:if test="${fn:length(lab.testerName)<=6}">${lab.testerName}</c:if>
												</td>
												<td height="24" align="left"
													<c:if test="${fn:length(lab.itemName)>6}"> title="${lab.itemName}" </c:if>>
													<c:if test="${fn:length(lab.itemName)>6}">${fn:substring(lab.itemName,0,6)}...</c:if>
													<c:if test="${fn:length(lab.itemName)<=6}">${lab.itemName}</c:if>
												</td>
												<td height="24" align="center">
													<fmt:formatDate value="${lab.testDate}" type="date" pattern="yyyy-MM-dd" />
												</td>
												<td height="24" align="center" style="border-right:0px;" 
													<c:if test="${fn:length(lab.orderDeptName)>6}"> title="${lab.orderDeptName}" </c:if> >
													<c:if test="${fn:length(lab.orderDeptName)>6}">${fn:substring(lab.orderDeptName,0,6)}...</c:if>
													<c:if test="${fn:length(lab.orderDeptName)<=6}">${lab.orderDeptName}</c:if>
												</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</table>
						</div>
						<div class="portlet-content-more" style="display:none" />
		            </div>
		        </div>
				</c:if>
				</c:if>
			</div>
			<div id="portletViews">	    	
		    	<div class="bigportlets" style="display:none"></div>
				<div class="smallportlets-container" style="display:none;" >
		    		<div class="smallportlets"></div>
		    	</div>
	    	</div>
	    	<div style="clear:both" />
	    </div>
	</div>
</body>
</html>
