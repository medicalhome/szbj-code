<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Expires" content="0">
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
	<script type="text/javascript" src="../scripts/visit/mainViewPart.js"></script>
	
	<script type="text/javascript">		
        $(document).ready(function() {
			setMenuSettings();
        	initNormalEffect();
        	var patientAlerts = getPatientAlerts();
        	if(patientAlerts==null||patientAlerts==""){
	        	$("#patientAlerts").hide();        		
        	}else{
	        	$("#aller").html(getPatientAlerts());    		
        	}
        	$("#normalTab").show();
        	
        	banTab($(".banTab"));
        	
            return false;
        });

		function getDisplayMenus()
		{
			return "${displayMenus}";
		}
		
		function getAllMenus()
		{
			return "${allMenus}";
		}
		
		//$Author :jin_peng
		//$Date : 2012/11/27 18:06$
		//[BUG]0011883 MODIFY BEGIN
		// 浏览器大小变化时重置页面显示位置
		$(window).resize
		(
			function()
			{
				try
				{
					leftMenuWidth = new Number(($("#menuW").attr("width")).replace("px",""));
					adjustingPage1();
				}
				catch(e){}
			}
		);
		
		/**外部链接跳转 ，如病案文档
		    * Author:yang_mingjie
		    * Date : 2014/08/22 15:30
		    * [BUG]0047773 ADD BEGIN
		    */
		function linkFromOthers(url)
		{
			$.post("../"+url);
			window.open(url);
		}
		 /**[BUG]0047773 ADD END*/
		
		/**
		 * 调整页面布局
		 */
		function adjustingPage1()
		{
			
			//页面可见区域宽度
			var availWidth = document.body.offsetWidth;
			
			var leftMenuWidth = 0;
			
			try
			{
				leftMenuWidth = new Number(($("#menuW").attr("width")).replace("px",""));
			}
			catch(e){}
			
			//当页面可见区域宽度小于给定值时出现滚动条并保持给定值的宽度
			if (availWidth < 960) 
			{
				availWidth = 960;
			}
			
			if($("#menuW").css("display") == "none")
			{
				$("#dynamicContent").css("width",availWidth - 10 );
			}
			else
			{
				$("#dynamicContent").css("width",availWidth - leftMenuWidth - 10 );
			}
			
		}
		//$[BUG]0011883 MODIFY END
	</script>
</head>
<body style="margin: 0; padding: 0;">
	<div id="normal">
		<div id="content" class="parentTab" >
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<table id="menuNav" cellpadding="0" cellspacing="0" border="0"
							width="100%">
							<tr>
								<td id="menuW" width="160px" style="vertical-align: top;"
									class="menuContent">
									<!--菜单开始-->
									<div class="menuNav">
										<ul id="accordion">
											<li id="menu01"><a href="#category"
												class="heading uncollapsible banTab"
												onclick="loadPanel('../visit/part_${patientSn}.html', '', '#dynamicContent',{isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
													<img src="../images/pic_zyxx.png" border="0" align="center"
													style="padding-left: 4px; padding-right: 4px;" />摘要信息
											</a></li>
											<li id="menu02"><a href="javascript:void(0);"
												onclick="loadPanel('../visit/list_${patientSn}.html', '', '#dynamicContent',{isAddHistory:'1', isMainViewMenuPart:'1'});return false;"
												class="heading uncollapsible banTab"> <img
													src="../images/pic_jzgif.png" border="0" align="center"
													style="padding-left: 4px; padding-right: 4px;" />就诊
											</a></li>
											<li id="menu03"><a href="javascript:void(0);"
												onclick="loadPanel('../risk/allergy_${patientSn}.html', '', '#dynamicContent',{isAddHistory:'1', isMainViewMenuPart:'1'});return false;"
												class="heading uncollapsible banTab"> <img
													src="../images/pic_blfy.png" border="0" align="center"
													style="padding-left: 4px; padding-right: 4px;" />过敏/不良反应
											</a></li>
											<!--$Author :bin_zhang
												$Date : 2012/9/18 16:23$
                                                [BUG]0009791 MODIFY BEGIN 
                                            -->
											<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth01)}">
												<li id="menu04"><a href="javascript:void(0);"
													onclick="loadPanel('../diagnosis/list_${patientSn}.html', '', '#dynamicContent',{isAddHistory:'1', isMainViewMenuPart:'1'});return false;"
													class="heading uncollapsible banTab"> <img
														src="../images/pic_zd.png" border="0" align="center"
														style="padding-left: 4px; padding-right: 4px;" />诊断
												</a></li>
											</c:if>
											<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth02) || (useACL && aclAuths.clinicalInfoAuth03)}">
												<li id="menu05"><a href="javascript:void(0);"
													class="heading banTab"> <img src="../images/pic_yz.png"
														border="0" align="center"
														style="padding-left: 4px; padding-right: 4px;" />医嘱
												</a>
													<ul id="orders">
														<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth02)}">
															<li id="menu0501"><a href="javascript:void(0);" class="banTab"
																onclick="loadPanel('../drug/list_${patientSn}.html', '', '#dynamicContent',{continueGoto:'continueGoto_drug',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																	<div class="headicon">
																		<img src="../images/pic_small.gif" border="0"
																			align="center"
																			style="padding-left: 14px; padding-right: 8px;" />药物医嘱
																	</div>
															</a></li>
														</c:if>
														<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth03)}">
															<li id="menu0502"><a href="javascript:void(0);" class="banTab"
																onclick="loadPanel('../order/list_${patientSn}.html', '', '#dynamicContent', {continueGoto:'continueGoto_noneDrug',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																	<div class="headicon">
																		<img src="../images/pic_small.gif" border="0"
																			align="center"
																			style="padding-left: 14px; padding-right: 8px;" />其他医嘱
																	</div>
															</a></li>
														</c:if>
													</ul></li>
											</c:if>
											<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth06)}">
												<li id="menu06"><a href="javascript:void(0);"
													class="heading banTab"> <img src="../images/pic_jc.png"
														border="0" align="center"
														style="padding-left: 4px; padding-right: 4px;" />检查
												</a>
													<ul id="exams">
														<li id="menu0601"><a href="javascript:void(0);" class="banTab"
															onclick="var examinationTypes=['${Constants.ORDER_EXEC_ERADIATION_ALL}'];loadPanel('../exam/list_${patientSn}_'+examinationTypes+'.html',{menuNum:'1'}, '#dynamicContent',{continueGoto:'continueGoto_examination',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon">
																	<img src="../images/pic_small.gif" align="center"
																		style="padding-left: 14px; padding-right: 8px; border: 0px;" />放射
																</div>
														</a></li>
														<li id="menu0602"><a href="javascript:void(0);" class="banTab"
															onclick="var examinationTypes=['${Constants.ORDER_EXEC_ULTRASONIC_ALL}'];loadPanel('../exam/list_${patientSn}_'+examinationTypes+'.html',{menuNum:'2'}, '#dynamicContent',{continueGoto:'continueGoto_examination',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon">
																	<img src="../images/pic_small.gif" class="banTab"
																		style="padding-left: 14px; padding-right: 8px; border: 0px;"
																		align="center" />超声
																</div>
														</a></li>
														<li id="menu0604"><a href="javascript:void(0);" class="banTab"
															onclick="var examinationTypes=['${Constants.ORDER_EXEC_ECG_ALL}'];loadPanel('../exam/list_${patientSn}_'+examinationTypes+'.html',{menuNum:'3'}, '#dynamicContent',{continueGoto:'continueGoto_examination',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon">
																	<img src="../images/pic_small.gif"
																		style="padding-left: 14px; padding-right: 8px; border: 0px;"
																		align="center" />心电
																</div>
														</a></li>
														<li id="menu0605"><a href="javascript:void(0);" class="banTab"
															onclick="var examinationTypes=['${Constants.ORDER_EXEC_ENDOSCOPE_ALL}'];loadPanel('../exam/list_${patientSn}_'+examinationTypes+'.html',{menuNum:'4'}, '#dynamicContent',{continueGoto:'continueGoto_examination',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon">
																	<img src="../images/pic_small.gif"
																		style="padding-left: 14px; padding-right: 8px; border: 0px;"
																		align="center" />内窥镜
																</div>
														</a></li>
														<%-- <li id="menu0606"><a href="javascript:void(0);" class="banTab"
															onclick="var examinationTypes=['${Constants.ORDER_EXEC_NUCLEAR}','${Constants.ORDER_EXEC_ECG}','${Constants.ORDER_EXEC_CYSTOSCOPE}','${Constants.ORDER_EXEC_HEARTBEAT}'];loadPanel('../exam/list_${patientSn}_'+examinationTypes+'.html',{menuNum:'5'}, '#dynamicContent',{continueGoto:'continueGoto_examination',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon">
																	<img src="../images/pic_small.gif"
																		style="padding-left: 14px; padding-right: 8px; border: 0px;"
																		align="center" />其他
																</div>
														</a></li> --%>
														<li id="menu0699"><a href="javascript:void(0);" class="banTab"
															onclick="loadPanel('../exam/list_${patientSn}_.html', {menuNum:'99'}, '#dynamicContent',{continueGoto:'continueGoto_examination',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon">
																	<img src="../images/pic_small.gif"
																		style="padding-left: 14px; padding-right: 8px; border: 0px;"
																		align="center" />所有
																</div>
														</a></li>
													</ul></li>
											</c:if>
											<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth05)}">
												<li id="menu07"><a href="javascript:void(0);"
													class="heading banTab"> <img src="../images/pic_jy.png"
														style="padding-left: 4px; padding-right: 4px; border: 0px;"
														align="center" />检验
												</a>
													<ul id="labs">
														<li id="menu0704"><a href="javascript:void(0);" class="banTab"
															onclick="var labTypes=['${Constants.ORDER_LAB_BLOOD_ALL}'];loadPanel('../lab/list_${patientSn}_'+labTypes+'.html', {menuNum:'1'}, '#dynamicContent',{continueGoto:'continueGoto_lab',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon">
																	<img src="../images/pic_small.gif"
																		style="padding-left: 14px; padding-right: 8px; border: 0px;"
																		align="center" />血液
																</div>
														</a></li>
														<li id="menu0705"><a href="javascript:void(0);" class="banTab"
															onclick="var labTypes=['${Constants.ORDER_LAB_BIOCHEMISTRY_ALL}'];loadPanel('../lab/list_${patientSn}_'+labTypes+'.html', {menuNum:'2'}, '#dynamicContent',{continueGoto:'continueGoto_lab',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon">
																	<img src="../images/pic_small.gif"
																		style="padding-left: 14px; padding-right: 8px; border: 0px;"
																		align="center" />生化
																</div>
														</a></li>
														<li id="menu0706"><a href="javascript:void(0);" class="banTab"
															onclick="var labTypes=['${Constants.ORDER_LAB_MICRO_ALL}'];loadPanel('../lab/list_${patientSn}_'+labTypes+'.html', {menuNum:'3'}, '#dynamicContent',{continueGoto:'continueGoto_lab',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon">
																	<img src="../images/pic_small.gif"
																		style="padding-left: 14px; padding-right: 8px; border: 0px;"
																		align="center" />微生物
																</div>
														</a></li>
														<li id="menu0706"><a href="javascript:void(0);" class="banTab"
															onclick="var labTypes=['${Constants.ORDER_LAB_PATHOLOGY_ALL}'];loadPanel('../lab/list_${patientSn}_'+labTypes+'.html', {menuNum:'4'}, '#dynamicContent',{continueGoto:'continueGoto_lab',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon">
																	<img src="../images/pic_small.gif"
																		style="padding-left: 14px; padding-right: 8px; border: 0px;"
																		align="center" />组织与细胞
																</div>
														</a></li>
														<li id="menu0799"><a href="javascript:void(0);" class="banTab"
															onclick="loadPanel('../lab/list_${patientSn}_.html', {menuNum:'99'}, '#dynamicContent',{continueGoto:'continueGoto_lab',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon">
																	<img src="../images/pic_small.gif"
																		style="padding-left: 14px; padding-right: 8px; border: 0px;"
																		align="center" />所有
																</div>
														</a></li>
														<!--  <li id="menu0708"><a href="javascript:void(0);" class="banTab"
															onclick="loadPanel('../emrLab/list_${patientSn}_.html', '', '#dynamicContent',{continueGoto:'continueGoto_lab',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon">
																	<img src="../images/pic_small.gif"
																		style="padding-left: 14px; padding-right: 8px; border: 0px;"
																		align="center" />波波电子病历
																</div>
														</a></li>-->
													</ul></li>
											</c:if>
											<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth04)}">
												<li id="menu08"><a href="javascript:void(0);"
													onclick="loadPanel('../procedure/list_${patientSn}.html', '', '#dynamicContent',{isAddHistory:'1', isMainViewMenuPart:'1'});return false;"
													class="heading uncollapsible banTab"> <img
														src="../images/pic_ss.png"
														style="padding-left: 4px; padding-right: 4px; border: 0px;"
														align="center" />手术
												</a></li>
											</c:if>
											<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth07)}">
												<li id="menu09"><a href="javascript:void(0);"
													class="heading banTab"> <img src="../images/pic_blws.png"
														style="padding-left: 4px; padding-right: 4px; border: 0px;"
														align="center" />病历文书
												</a>
													<ul id="docs">
														<li id="menu0901"><a href="javascript:void(0);" class="banTab"
															onclick="loadPanel('../doc/list_${patientSn}.html',{documentTypes:'02,16'}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon" style="margin-top: 5px;">
																	<img src="../images/pic_small.gif" border="0"
																		align="center"
																		style="padding-left: 14px; padding-right: 8px;" />门急诊病历
																</div>
														</a></li>
														<li id="menu0911"><a href="javascript:void(0);" class="banTab"
															onclick="loadPanel('../doc/list_${patientSn}.html',{documentTypes:'05'}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon" style="margin-top: 5px;">
																	<img src="../images/pic_small.gif" border="0"
																		align="center"
																		style="padding-left: 14px; padding-right: 8px;" />治疗处置记录
																</div>
														</a></li>
														<li id="menu0902"><a href="javascript:void(0);" class="banTab"
															onclick="loadPanel('../doc/list_${patientSn}.html',{documentTypes:'18'}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon" style="margin-top: 5px;">
																	<img src="../images/pic_small.gif" border="0"
																		align="center"
																		style="padding-left: 14px; padding-right: 8px;" />手术操作记录
																</div>
														</a></li>
														<li id="menu0903"><a href="javascript:void(0);" class="banTab"
															onclick="loadPanel('../doc/list_${patientSn}.html',{documentTypes:'06'}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon" style="margin-top: 5px;">
																	<img src="../images/pic_small.gif" border="0"
																		align="center"
																		style="padding-left: 14px; padding-right: 8px;" />护理记录
																</div>
														</a></li>
														<li id="menu0904"><a href="javascript:void(0);" class="banTab"
															onclick="loadPanel('../doc/list_${patientSn}.html',{documentTypes:'08'}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon">
																	<img src="../images/pic_small.gif" border="0"
																		align="center"
																		style="padding-left: 14px; padding-right: 8px;" />病案首页
																</div>
														</a></li>
														<li id="menu0905"><a href="javascript:void(0);" class="banTab"
															onclick="loadPanel('../doc/list_${patientSn}.html',{documentTypes:'09'}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon">
																	<img src="../images/pic_small.gif" border="0"
																		align="center"
																		style="padding-left: 14px; padding-right: 8px;" />入院记录
																</div>
														</a></li>
														<li id="menu0906"><a href="javascript:void(0);" class="banTab"
															onclick="loadPanel('../doc/list_${patientSn}.html',{documentTypes:'10'}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon">
																	<img src="../images/pic_small.gif" border="0"
																		align="center"
																		style="padding-left: 14px; padding-right: 8px;" />病程记录
																</div>
														</a></li>
														<li id="menu0907"><a href="javascript:void(0);" class="banTab"
															onclick="loadPanel('../doc/list_${patientSn}.html',{documentTypes:'12'}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon">
																	<img src="../images/pic_small.gif" border="0"
																		align="center"
																		style="padding-left: 14px; padding-right: 8px;" />出院记录
																</div>
														</a></li>
														<li id="menu0908"><a href="javascript:void(0);" class="banTab"
															onclick="loadPanel('../doc/list_${patientSn}.html',{documentTypes:'01,17,99'}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon">
																	<img src="../images/pic_small.gif" border="0"
																		align="center"
																		style="padding-left: 14px; padding-right: 8px;" />其他
																</div>
														</a></li>
														<li id="menu0909"><a href="javascript:void(0);" class="banTab"
															onclick="loadPanel('../doc/list_${patientSn}.html', '', '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon">
																	<img src="../images/pic_small.gif" border="0"
																		align="center"
																		style="padding-left: 14px; padding-right: 8px;" />所有
																</div>
														</a></li>
													</ul></li>
											</c:if>
											<!--  显示会诊
											     $Author:li_shenggen
											     $Date : 2014/07/14 10:52
											     $[BUG]0046276 MODIFY BEGIN -->
											<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth07)}">
												<li id="menu11"><a href="javascript:void(0);"
													onclick="loadPanel('../order/list_${patientSn}.html', {senSave:'false',orderType:'${Constants.CONSULTATION_ORDER}'}, '#dynamicContent',{isAddHistory:'1', isMainViewMenuPart:'1'});return false;"
													class="heading uncollapsible banTab"> <img
														src="../images/pic_blfy.png" border="0" align="center"
														style="padding-left: 4px; padding-right: 4px;" />会诊
												</a></li>
											</c:if>
												<!--  显示系统管理
											     $Author:du_xiaolan
											     $Date : 2015/06/03
											     $ MODIFY BEGIN -->
										<c:if test="${isSystemAdmin && (useACL == false || (useACL && aclAuths.clinicalInfoAuth07))}">
												<li id="menu12"><a href="javascript:void(0);"
													class="heading banTab"> <img src="../images/pic_blws.png"
														style="padding-left: 4px; padding-right: 4px; border: 0px;"
														align="center" />系统管理
												</a>
													<ul id="manage">
														<li id="menu1201"><a href="javascript:void(0);" class="banTab"
															onclick="loadPanel('../iam/searchAccountTable.html',{documentTypes:'02,16'}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon" style="margin-top: 5px;">
																	<img src="../images/pic_small.gif" border="0"
																		align="center"
																		style="padding-left: 14px; padding-right: 8px;" />账户管理
																</div>
														</a></li>
												
														
														
														<li id="menu1202"><a href="javascript:void(0);" class="banTab"
															onclick="loadPanel('../role/loadRoleList.html',{documentTypes:'02,16'}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});return false;">
																<div class="headicon" style="margin-top: 5px;">
																	<img src="../images/pic_small.gif" border="0"
																		align="center"
																		style="padding-left: 14px; padding-right: 8px;" />角色管理
																</div>
														</a></li>
														
														
												
													</ul></li>
											</c:if>
											<!-- MODIFY END -->
											<c:forEach items="${urllist}" var="ulist">
												<c:if test="${ulist.scan==0}">
													<c:if test="${!empty ulist.id}">
													<!-- 
													//病案文档链接 
												    //Author:yang_mingjie
												    //Date : 2014/08/22 15:30
												    //[BUG]0047773 ADD BEGIN
													 -->
														<li id="menu${ulist.urlid}"><a href="javascript:void(0);"	class="heading uncollapsible banTab"
															onclick="
															linkFromOthers('${ulist.url}');
															<%-- window.open('${ulist.url}') --%>">
													<!-- [BUG]0047773 ADD END -->
													 		<img src="../images/pic_blws.png" border="0" align="center"
																style="padding-left: 4px; padding-right: 4px;" />${ulist.urlname }
														</a></li>
													</c:if>
												</c:if>
											</c:forEach>
											<!-- [BUG]0009791 MODIFY END -->
										</ul>
									</div> <!--菜单结束-->
								</td>
								<td style="vertical-align: top; " class="paneSeperator_open"
									onClick="toggleMenu(this);">&nbsp;&nbsp;</td>
								<td style="vertical-align: top;">
									<table cellpadding="0" cellspacing="0" border="0" width="100%">
										<tr id="patientAlerts">
											<td>
												<div class="contentMain">
													<div class="patientInfo">
														<img src="../images/pic_gm.gif" align="absmiddle"
															style="padding-right: 5px;" /><b id="aller"></b>
													</div>
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<div id="dynamicContent" class="parentTab"></div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
