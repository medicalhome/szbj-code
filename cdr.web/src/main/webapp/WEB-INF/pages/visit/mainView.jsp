<%@ page language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.founder.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>CDR临床数据中心</title>
    <link type="text/css" rel="Stylesheet" href="../styles/jquery-ui-1.8.18.custom.modify.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/jquery-ui-autocomplete.custom.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/jquery-ui-combobox.custom.modify.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/layout-default-1.3.0rc29.15.css"
        charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/layout-cdr.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/tablelist.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/timer-shaft.css" charset="utf-8" />
    <link type="text/css" rel="Stylesheet" href="../styles/loadingScreen.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/jquery-suggest.css" charset="UTF8" />
    <link type="text/css" rel="Stylesheet" href="../styles/tabs-menuPart.css" charset="UTF8" />
    <link type="text/css" rel="stylesheet" href="../styles/header.css" charset="UTF8"  />
    <link rel="icon" href="../favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" type="image/x-icon" href="../favicon.ico" />
    <script type="text/javascript" src="../scripts/jquery-1.7.1.js"></script>
    <script type="text/javascript" src="../scripts/jquery-ui-1.8.18.custom.min.js"></script>
    <script type="text/javascript" src="../scripts/jquery.history.js"></script>
    <script type="text/javascript" src="../scripts/forwardOrBack/forwardOrBack.js"></script>
    <script type="text/javascript" src="../scripts/tabEnter/tabEnter.js"></script>
    <script type="text/javascript" src="../scripts/jquery.ui.autocomplete.js"></script>
    <script type="text/javascript" src="../scripts/jquery.ui.combobox.js"></script>
    
    <script type="text/javascript" src="../scripts/jquery.layout-1.3.0rc29.15.js"></script>
    <script type="text/javascript" src="../scripts/jquery.bgiframe.js"></script>
    <script type="text/javascript" src="../scripts/jquery.suggest.js"></script>
    <script type="text/javascript" src="../scripts/htmlSelectSuggest.js"></script>
    <script type="text/javascript" src="../scripts/htmlMultiSelectSuggest.js"></script>
    <script type="text/javascript" src="../scripts/loadingScreen.js"></script>
    <script type="text/javascript" src="../scripts/common.js"></script>
    <script type="text/javascript" src="../scripts/layout.js"></script>
    <script type="text/javascript" src="../scripts/visit/mainView.js"></script>
    <script type="text/javascript" src="../scripts/dialog-tabs.js"></script>
    <script type="text/javascript" src="../scripts/visit/normalViewPart.js"></script>
    <script type="text/javascript" src="../scripts/help/help.js"></script>

    <link rel="Stylesheet" type="text/css" href="../styles/Css.css" />
    <script language="javascript" src="../scripts/ProSlide.modify.js"></script>
    <script type="text/javascript" src="../scripts/timerInpatient/timerInpatient.js"></script>
    
	<script type="text/javascript">
		//$Author :jin_peng
		// $Date : 2013/05/29 15:20$
		// [BUG]0031929 ADD BEGIN
		var countDis = 3;
		// [BUG]0031929 ADD END
		
		var inpatientNo = "${inpatientNo}";
		
		var outpatientNo = "${outpatientNo}";
		
		function getPatientName(){
			return "${patient.patientName}";
		}
		
		function getPatientGender(){
			return "${patient.genderName}";
		}
		
		function getPatientAge(){
			return "${age}";
		}
		
		function getInpatientNo(){
			return "${inpatientNo}";
		}
	
		function getPatientSn(){
			return ${patientSn};
		}
		
		function getPatientAlerts()
		{
			return "${patientAlerts}";
		}

		jQuery.fn.limit = function ()
		{
		    var objString = $(this).text();
		    var objLength = $(this).text().length;

		    if (objLength > 40) 
		    {
		        $(this).attr("title", objString);
		        obj = $(this).text(objString.substring(0, 39) + "...");
		    }
		}
	
		$(function(){
			$("#num").limit();
		})

		$(document).ready(function ()
		{
		    sh();
		    var n = 0;
		    $(window).resize(function () 
		    {
		    	sh();
		    });
		    $("#scrollshow").myMarquee($("#scrollshow"), 3);
		});
		
		//$Author :jin_peng
		// $Date : 2013/05/29 15:20$
		// [BUG]0031929 ADD BEGIN
		var currentContext = null;
		
		var hashKey = {};
		
		function getViewType()
		{
			return "${viewType}";
		}
		
		var viewType = getViewType();
		
		var showOverFirst = null;
		
		// [BUG]0031929 ADD END
		function getSceneType()
		{
			return "${sceneType}";
		}
		
		var sceneType = getSceneType();
    </script>
</head>
<body>
	 <div id="container">
		<div id="header" onmouseover="upArrShow()" onmouseout="upArrHide()">
		<div class="header">
            <div class="header-logo">
                <img src="../${mainBgPic}" width="260" height="56" />
            </div>
            <div class="header-bj">临床数据中心</div>
            <div class="fd-logo">
            	<!-- <img src="../images/top/${mainCpyPic}" /> -->
            </div>
            <div class="bgHeaderNew">
					<!-- $Author :jin_peng
        				 $Date : 2012/11/26 16:40$
       					 [BUG]0011923 MODIFY BEGIN -->
					<table id="headTab" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td><div id="lDiv" style="width: 510px;">&nbsp;</div></td>
							<td>
								<div class="topNav" id="fDiv">						
									<!-- $Author :chang_xuewen
			         					 $Date : 2013/12/20 14:30$
			        					 [BUG]0040878 ADD BEGIN -->			
									<a href="javascript:void(0)" onclick="showHelpDialog('../help/questions.html','常见问题',{},850,500);"
										class="normalLink" style="width: 50px; color: #e2dedd; padding-right: 0px;"
										name="#normal">常见问题</a>&nbsp;
									<!-- [BUG]0040878 ADD END -->
									<img src="../images/xtsz.png" align="middle" style="height: 16px; width: 16px; border: 0px" /> 
									<a href="javascript:void(0)" id="qjfirst" class="normalLink" name="#normal" style="color: #e2dedd"										
										onclick="showDialog('../system/setting.html', '系统设置', {}, 700, 600);">系统设置</a>
									<!-- 如是单点用户,暂时屏蔽"修改密码"功能 -->
									<c:if test="${idm ne 1}">
										<img src="../images/xgmm.png" align="middle" style="height: 16px; width: 16px; border: 0px" />										
										<!-- $Author :jin_peng
				         					 $Date : 2012/09/13 17:49$
				        					 [BUG]0009712 MODIFY BEGIN -->
				        				<!-- $Author: yu_yzh
				        					 $Date: 2015/12/08 09:37
				        					 港大采用单独的更新密码页面，不使用公共服务
				        					 MODIFY BEGIN -->	 
										<%-- <a href="javascript:void(0)" id="idmodify" class="normalLink" style="color: #e2dedd" name="#normal"
											onclick="modifyPw('${userSn}','${Constants.MODIFY_PASSWORD_LINK}');">修改密码</a> --%>
										<a href="javascript:void(0)" id="idmodify" class="normalLink" style="color: #e2dedd" name="#normal"
											onclick="showDialog('../visit/changePsw.html', '修改密码', {}, 370, 208, null, false);">修改密码</a>
										<!-- 港大采用单独的更新密码页面，不使用公共服务 MODIFY END -->	
										<!-- [BUG]0009712 MODIFY END -->
										<!-- $Author :jin_peng
				         					 $Date : 2012/08/31 17:08$
				        					 [BUG]0008759 MODIFY BEGIN -->
										<img src="../images/zx.png" align="middle" style="height: 16px; width: 16px; border: 0px;" /> 
										<a href="javascript:void(0)" onclick="exitSystem('${Constants.EXIT_KINDS_NAME_BUTTON}')"
											class="normalLink" style="width: 50px; color: #e2dedd; padding-right: 0px;"
											name="#normal">注销</a>
									</c:if>
									&nbsp;
									<!-- [BUG]0008759 MODIFY END -->
									<img src="../images/yjfk.png" align="middle" style="height: 16px; width: 16px; border: 0px;" />
									<a href="javascript:void(0)" onclick="changeView('#feedback', '${userSn}','${username}');"
										class="normalLink" style="width: 50px; color: #e2dedd; padding-right: 0px;"
										name="#normal">意见反馈</a>&nbsp;
									<img src="../images/help/help.png" align="middle" style="height: 16px; width: 16px; border: 0px;" /> 
									<a href="javascript:void(0)" onclick="showHelpDialog('../help/accountHelp.html','帮助',{},850,500);"
										class="normalLink" style="width: 50px; color: #e2dedd; padding-right: 0px;"
										name="#normal">帮助</a>&nbsp;
								</div>
								<div class="topNav" style="text-decoration: none; color: #000000; padding-top: 7px;  color: #fff;  padding-right: 0px;">
									<!-- $Author :tong_meng
			         					 $Date : 2013/11/25 15:58$
			        					 [BUG]0039955 MODIFY BEGIN -->
									<%-- 欢迎用户：${username}&nbsp;登录本系统！ --%>
									<!-- $Author :chang_xuewen
			         					 $Date : 2014/02/07 10:36$
			        					 [BUG]0042170 MODIFY BEGIN -->
									 欢迎：${username}&nbsp;<span style="color: red;font-weight: bold;"></span>
			        				<!-- [BUG]0042170 MODIFY END -->
			        				<!-- [BUG]0039955 MODIFY END -->
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
           	<div class="navHeader">
			<div id="top2" style="width:100%;float:left;">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td valign="top" style="float: left;">
						  <div class="linkBar">
						    <input type="button" id="patientRelationship"
						        style="border: 0px; BACKGROUND-IMAGE: url(../images/pic_hzlist.png); padding-left: 6px; padding-right: 2px; width: 85px; height: 23px; cursor: pointer; vertical-align: middle;"
						        onclick="showPatientListView();" />
						    &nbsp;&nbsp;
						      <font color="#2A82CA" style="padding-right: 5px; font-weight: bold; size: portrait;">
								<!-- $Author : wu_jianfeng
		         					 $Date : 2013/02/25 17:49$
		        					 [BUG]0014065 MODIFY BEGIN -->
						        <a href="javascript:void(0)" style="margin-left:7px" class="normalLink" name="#normal"
						            onclick="showDialog('../patient/${patientSn}.html', '患者详细', {});"						            
								  <c:if test="${fn:length(patientFolderMemo)!=0}">
						            onmouseover="createTip($('#scj').html())"
						            onmouseout="removeTip()"
						           </c:if>>						
						          ${patient.patientName}
						        </a>
								<!-- [BUG]0014065 MODIFY END -->
						      </font>
						      <span id="num"><font color="#6E6E6E" style="padding-right: 5px;">${age}&nbsp;${patient.genderName}&nbsp;病人号：${patient.patientEid}<%-- <c:if test="${!empty outpatientNo}">&nbsp;就诊卡号：${outpatientNo}</c:if><c:if test="${!empty inpatientNo}">&nbsp;住院号：${inpatientNo}</c:if> --%></font></span>
						    <font color="#6E6E6E" style="padding-right: 3px;">
						    </font>
						    &nbsp;
						    <input id="addFavFolder" type="button"  				        						        
						        style="border: 0px; BACKGROUND-IMAGE: url(../images/pic_button_tj.png); width: 88px; height: 26px; vertical-align: middle; cursor: pointer;"
						        onclick="addFavFolder(${patient.patientSn});" />
						  </div>
						  <!-- [BUG]0011923 MODIFY END -->
						  <div id="patientList"						      
						      style="display: block; position: absolute; top: 0px; left: 0px; z-index: 1; overflow: hidden; border: 1px solid #0066cc; background: white; display: none;">						
						  </div>
						  <div id="scj" style="display:none">
						    <table style="width:320px;padding:10px;border:1px solid #b4efff;">
						      <tr>
						        <td colspan="2" align="center" style="border:0px;">
						          <font color="#155a81" size="2px">
						            <b>
						              收藏夹相关备注
						            </b>
						          </font>
						        </td>
						      </tr>
						      <c:forEach items="${patientFolderMemo}" var="patientFolderMemo" varStatus="status">
						        <tr>
						          <td>
						            文件夹名称：
						          </td>
						          <td>
						            ${patientFolderMemo.folderName}
						          </td>
						        </tr>
						        <tr>
						          <td>
						            创建时间：
						          </td>
						          <td>
						            <fmt:formatDate value="${patientFolderMemo.createTime}" type="both" pattern="yyyy-MM-dd HH:mm" />
						          </td>
						        </tr>
						        <tr>
						          <td colspan="2">
						            备注：
						            <font color="#4ca3bb">
						              ${patientFolderMemo.memo}
						            </font>
						          </td>
						        </tr>
						      </c:forEach>
						    </table>
						  </div>
						</td>
						
						<td align="right" style="height: 32px; overflow: hidden; display: none" id="a1">
													
							<div class="linkBarRight" style="border: solid 2px ff00; height: 32px; overflow: hidden;">
								<%-- <c:if test="${showPrinter==true}">  --%><c:if test="${showPrinter==true}">
									<img src="../images/print.png" align="middle" style="height: 18px; width: 18px;" /> 
									<a href="javascript:void(0)" class="viewNav" name="#visitIndexView"
										onclick="showDialog('../print/${patientSn}.html', '打印', {}, 640, 223);"><b>打印</b>
									</a>								
								</c:if>							
								<c:if test="${visitIndexViewDisplay==true}"> 
								<img src="../images/syst.png" align="middle" style="height: 18px; width: 18px;" /> 
								<a href="javascript:void(0)" class="viewNav" name="#visitIndexView"
									onclick="changeView('#visitIndexView', ${patientSn}, undefined, undefined, '1')"><b>就诊索引视图</b>
								</a>								
								</c:if>
								<c:if test="${normalViewDisplay==true}">
									<img id="img5" src="../images/zhst.png" align="middle" style="height: 18px; width: 18px;" />										
									<a href="javascript:void(0)" class="viewNav" name="#normal"
										onclick="changeView('#normal', ${patientSn}, undefined, undefined, '1')">综合视图
									</a>
								</c:if>									
								<!--   $Author :bin_zhang
                                       $Date : 2012/12/11
                                       [BUG]12309 ADD BEGIN -->
                                       <!--   $Author :jia_yanqing
                                       $Date : 2012/12/24
                                       [BUG]12701 MODIFY BEGIN -->
								<c:if test="${outpatientViewDisplay==true}"> 
								<c:if test="${out_count!=0}">
								<img id="2" src="../images/mzst.png" align="middle" style="height: 18px; width: 18px;" /> 
								<a href="javascript:void(0)" class="viewNav" name="#outpatientView"									
									onclick="changeView('#outpatientView', ${patientSn}, undefined, undefined, '1')" >门诊视图
								</a>								
								</c:if>
								<c:if test="${out_count==0}">
								<img id="2" src="../images/mzst.png" align="middle" style="height: 18px; width: 18px;" /> 
								<a href="javascript:void(0)" class="viewNav noVisited" style="color:#AAAAAA; cursor:default;" title="该患者没有门诊记录!">门诊视图</a>
								</c:if>
								</c:if>
								<!--  [BUG]12309 ADD END -->
								<c:if test="${inpatientViewDisplay==true}">
								<c:if test="${in_count!=0}">
									<img id="3" src="../images/pic_zyst.gif" align="middle" style="padding-right: 1px;" />
									<a href="javascript:void(0)" class="viewNav" name="#inpatientView"
										onclick="changeView('#inpatientView',${patientSn}, undefined, undefined, '1')" >住院视图
									</a>
							    </c:if>
							    <c:if test="${in_count==0}">
							    <img id="3" src="../images/pic_zyst.gif" align="middle" style="padding-right: 1px;" /> 
							    <a href="javascript:void(0)" class="viewNav noVisited"  style="color:#AAAAAA; cursor:default;" title="该患者没有住院记录!">住院视图</a>
							    </c:if>
							    <!--  [BUG]12701 MODIFY END -->
								</c:if>
								<c:if test="${timerViewDisplay==true}">
									<img id="4" src="../images/pic_sjzst.gif" align="middle" style="padding-right: 1px;" /> 
									<a href="javascript:void(0)" class="viewNav" name="#timer"
										onclick="changeView('#timer',${patientSn}, undefined, undefined, '1')" >时间轴视图
									</a>
								</c:if>
								<%-- <img src="../images/yjfk.png" align="middle" style="height: 18px; width: 18px;" />
								<a href="javascript:void(0)" class="viewNav tiTab" name="#feedback"								
									onclick="changeView('#feedback', '${userSn}','${username}')">意见反馈
								</a> --%>
							</div>
						</td>
						<td align="right" style="height: 32px; overflow: hidden" id="a2">
							<div class="linkBarRight"
								style="border: solid 2px ff00; height: 32px; overflow: hidden;">
								<div class="partcontent">
									<div class="part">
										<div class="part_t" style="width: 25px; display: inline;">
											<div class="rotlink">
												<div style="display: none" class="l _1 xtab tiTab unactive" title="查看更多"></div>
												<div class="r _2 xtab tiTab unactive" title="查看更多"></div>
												<div class="r_unclick" style="display: none" title="查看更多"></div>
											</div>
										</div>
										<div class="part_c">
											<div class="scrollshow" id="scrollshow">
												<div class="scrollshow_con" style="width: 360px;">
													<ul style="list-style-type: none;">
														<c:if test="${showPrinter==true}">
														<li class="banTab">
															<div> 
														<img src="../images/print.png" align="middle" style="height: 18px; width: 18px;" /> 
														<a href="javascript:void(0)" class="viewNav" name="#visitIndexView"
															onclick="showDialog('../print/${patientSn}.html', '打印', {}, 640, 223);"><b>打印</b>
														</a>
															</div>
														</li>								
														</c:if>													
														<c:if test="${visitIndexViewDisplay==true}">
														<li class="banTab">
															<div>
																<img src="../images/syst.png" align="middle"
																	style="height: 18px; width: 18px;" /> <a
																	href="javascript:void(0)" class="viewNav"
																	name="#visitIndexView"
																	onclick="changeView('#visitIndexView', ${patientSn}, undefined, undefined, '1')" >就诊索引视图</a>
															</div>
														</li>
														</c:if>
														<c:if test="${normalViewDisplay==true}">
															<li class="banTab">
																<div>
																	<img id="img5" src="../images/zhst.png" align="middle"
																		style="height: 18px; width: 18px;" /> <a
																		href="javascript:void(0)" class="viewNav"
																		name="#normal"
																		onclick="changeView('#normal', ${patientSn}, undefined, undefined, '1')" >综合视图</a>
																</div>
															</li>
														</c:if>
														<!--   $Author :bin_zhang
								                               $Date : 2012/12/11
								                               [BUG]12309 ADD BEGIN -->
								                               
														<c:if test="${outpatientViewDisplay==true}">
															<c:if test="${out_count!=0}">
																<li class="banTab">
																	<div>
																		 <img id="2" src="../images/mzst.png" align="middle"
																			style="height: 18px; width: 18px;" /> <a
																			href="javascript:void(0)" class="viewNav"
																			name="#outpatientView"
																			onclick="changeView('#outpatientView', ${patientSn}, undefined, undefined, '1')" >门诊视图</a>
																	</div>
																</li>
															</c:if>
															<c:if test="${out_count==0}">
																<li class="banTab">
																	<div>
																		<img id="2" src="../images/mzst.png" align="middle" 
																			style="height: 18px; width: 18px;" /> 
																		<a href="javascript:void(0)" class="viewNav noVisited" style="color:#AAAAAA; cursor:default;" title="该患者没有门诊记录!">门诊视图</a>
																	</div>
																</li>
															</c:if>
														</c:if>
														 <!-- [BUG]12309 ADD END -->
														<c:if test="${inpatientViewDisplay==true}">
															<c:if test="${in_count!=0}">
																<li class="banTab">
																	<div>
																		<img id="3" src="../images/pic_zyst.gif" align="middle"
																			style="height: 18px; width: 18px;padding-right: 1px;" /> <a
																			href="javascript:void(0)" class="viewNav"
																			name="#inpatientView"
																			onclick="changeView('#inpatientView',${patientSn}, undefined, undefined, '1')" >住院视图</a>
																	</div>
																</li>
														    </c:if>
														    <c:if test="${in_count==0}">
															    <li class="banTab">
															    	<div>
											                            <img id="3" src="../images/pic_zyst.gif" align="middle"
													                     style="height: 18px; width: 18px;padding-right: 1px;" /> <a href="javascript:void(0)" class="viewNav noVisited"  style="color:#AAAAAA; cursor:default;" title="该患者没有住院记录!">住院视图</a>
												                    </div>
												                </li>
									                        </c:if>
														</c:if>
														<c:if test="${timerViewDisplay==true}">
															<li class="banTab">
																<div>
																	<img id="4" src="../images/pic_sjzst.gif"
																		align="middle" style="height: 18px; width: 18px;padding-right: 1px;" /> <a
																		href="javascript:void(0)" class="viewNav"
																		name="#timer"
																		onclick="changeView('#timer',${patientSn}, undefined, undefined, '1')" >时间轴视图</a>
																</div>
															</li>
														</c:if>
														<li class="banTab">
															<div>&nbsp;</div>
														</li>
													</ul>
												</div>
												
											</div>
										</div>
									</div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
	<!-- 		<div id="bdqp" style="float:right;">
			<div  class="demo">
					<div id="top3" style="float:right;padding-right:10px;">
<button id="button"  style="border: 0px; BACKGROUND-IMAGE: url(../images/fpxs.png);float:right;width: 84px;margin-top:5px; height: 23px; cursor: pointer;"></button>
</div>
<div class="toggler" style="float:right;margin-top:30px;position:absolute;">
	<div id="effect" class="ui-widget-content ui-corner-all" style="float:right;">
		<h3 class="ui-widget-header ui-corner-all" style="height:30px;"><div id="opener321"  style="height:20px;float:left;"><table><tr><td style="height:20px;width:40px">
	                      <select id="bdys" style="width: 150px;">
							<option value="2">1*2</option>
							<option value="4">2*2</option>
					      </select></td></tr></table></div><input id="button1" type="button" value="最大化" valign="middle"  style="float:right;" /></h3>

	<div>
	<div id="hz2"  style="width:290px;height:auto;float:left;">		
	<div id="cart">
	<div class="ui-widget-content">
		<ol>
			<li class="placeholder"><span style="padding:0px;padding-left:25px;">添加业务信息</span></li>
	       
		</ol>
	</div>
</div>
 <input type="hidden" class="hzoi" id="hzbd" name="hzbd" value="" />
 <input type="hidden" class="j1" id="hzbd" name="hzbd" value="" />
<div id="cart2">
	<div class="ui-widget-content">
		<ol>
			<li class="placeholder"><span style="padding:0px;padding-left:25px;">添加业务信息</span></li>
		</ol>
	</div>
</div>
<input type="hidden"  class="hzoi2" id="hzbd" name="hzbd2" value="" />
<input type="hidden" class="j2" id="hzbd" name="hzbd" value="" />
	</div>
	<div id="hz21" valign="top"  style="display:none;width:100%;height:auto;float:left;">
	<div id="hzxxo" style="width:50%;float:left;border-right:10px solid #a2d7e7;height:100%"></div>
	<div id="hzxxt" style="width:49%;float:left;height:100%" ></div>
	</div>
	<div id="hz4"  style="display:none;width:290px;float:left;">
	<div id="cart3">
	<div class="ui-widget-content">
		<ol>
			<li id="hzbd"  class="placeholder"><span style="padding-left:20px;">添加业务信息</span></li>
		</ol>
	</div>
</div>
	 <input type="hidden" class="hzoi3" id="hzbd" name="hzbd3" value="" />
	 <input type="hidden" class="j3" id="hzbd" name="hzbd" value="" />
<div id="cart4">
	<div class="ui-widget-content">
		<ol>
			<li id="hzbd" class="placeholder"><span style="padding-left:20px;">添加业务信息</span></li>
		</ol>
	</div>
</div>
	<input type="hidden" class="hzoi4" id="hzbd" name="hzbd4" value="" />
	<input type="hidden" class="j4" id="hzbd" name="hzbd" value="" />
<div id="cart5">
	<div class="ui-widget-content">
		<ol>
			<li id="hzbd"  class="placeholder"><span style="padding-left:20px;">添加业务信息</span></li>
		</ol>
	</div>
</div>
<input type="hidden" class="hzoi5" id="hzbd" name="hzbd5" value="" />
<input type="hidden" class="j5" id="hzbd" name="hzbd" value="" />
<div id="cart6">
	<div class="ui-widget-content">
		<ol>
			<li id="hzbd"  class="placeholder"><span style="padding-left:20px;">添加业务信息</span></li>
		</ol>
	</div>
</div>
<input type="hidden" class="hzoi6" id="hzbd" name="hzbd6" value="" />
<input type="hidden" class="j6" id="hzbd" name="hzbd" value="" />
	</div>
	<div id="hz22" valign="top"  style="display:none;width:100%;height:auto;float:left;">
	<div id="hzxxth" style="overflow: auto;position: relative;width:50%;float:left;border-right:10px solid #a2d7e7;height:100%"></div>
	<div id="hzxxf" style="overflow: auto;position: relative;width:49%;float:left;" ></div>
	<div id="hzxxfi" style="overflow: auto;position: relative;width:50%;float:left;border-right:10px solid #a2d7e7;height:100%"></div>
	<div id="hzxxs" style="overflow: auto;position: relative;width:49%;float:left;" ></div>
	</div>
	</div>
</div>
</div></div></div> --></div>
		<div id="upArrShow" style="width:99.5%;text-align:center;height:3px;"><img src="../images/pic_lines_13.jpg" style="width:100%;;height:0px;cursor:pointer;" onclick="logoMoving()"></img></div>
		</div>
	</div>
	
	<div id="downArrShow" style="width:99.5%;text-align:center;"><img src="../images/pic_lines_12.jpg" style="width:100%;;height:0px;cursor:pointer;" onclick="logoMovingDown()"></img></div>
	
	<div id="errorsTipRegion" style="width:99.9%;height:30px;display:none;background-color:#F0CAA2;border:1px solid #fdd;text-align:center;line-height:30px">
		<div id="disFont"><span style="float:left;width:98% !important;width:97%;">抱歉，系统可能发生异常，请您与管理员联系。</span><span id="tt_close_error" style="float:right;cursor:pointer;" onclick="closeErrorTip('#errorsTipRegion');" onmouseover="this.style.backgroundColor='#fdd28a';" onmouseout="this.style.backgroundColor='#F0CAA2';" class="tabEnter ui-icon ui-icon-closethick">close</span></div>
	</div>

	<div id="dyContent" class="container parentTab"></div>

	<table id="normalTab" class="blockTable" cellpadding="0"
		cellspacing="0" style="display: none;" border="0">
		<tr style="height: 2px"></tr>
		<tr class="conditionCopyRight">
			<td align="left" valign="middle">
			<!-- 
				 * $Author :yang_mingjie
				 * $Date : 2014/06/16 10:09$
				 * [BUG]0045328 MODIFY BEGIN 
				 * 版权和版本号
		-->
				<div class="cellFoot"
					style="width: 100%; text-align: center; color: white; height: 29px;"><%-- ${Constants.COPYRIGHT} --%>
					<font style="font-family: Arial">Copyright&nbsp;&copy;&nbsp;2016</font>
					<font style="font-family: 方正黑体简体">北大医疗信息技术有限公司</font>
					<font style="font-family: Arial">All Rights Reserved</font>
					<span style="position: absolute; color:#87CEEB;left:840x;">&nbsp;版本号</span>
					</div>
					<!-- [BUG]0045328 MODIFY End -->
			</td>
		</tr>
	</table>

	<div id="loading">
		<div id="loadingScreen" style="display: none">
			<div id="loadingMessage">数据加载中，请稍候...</div>
		</div>
	</div>
	<div id="alertMessage" style="display:none;"></div>
    <div id="confirmMessage" style="display:none;"></div>

	<div id="error">
		<div id="errorDialog" style="display: none"></div>
	</div>

	<div id="dialog">
		<div id="ajaxDialog" class="ajaxDialog"
			style="display: none; z-index: 10000;">
			<iframe id="dialogFrame" style="width: 100%; height: 100%;" src=""
				frameborder="0"> </iframe>
		</div>
	</div>

	<script type="text/javascript">
		//$Author :jin_peng
		// $Date : 2013/05/29 15:20$
		// [BUG]0031929 ADD BEGIN
		function initLoad()
		{
			var hidePatientFlag = '${hidePatientFlag}';
			
			if(hidePatientFlag == "true")
			{
				hideObj($("#patientRelationship"));
				hideObj($("#addFavFolder"));
			}
//			alert(sceneType);
//			alert(viewType);
//			alert("23123444444"+(viewType && sceneType));
//			alert("23");
			if(viewType && sceneType){
				$("#qjfirst").removeAttr("onclick");
				$("#idmodify").removeAttr("onclick");
 				$("#patientRelationship").hide();
				$("#addFavFolder").hide();
				if(viewType == "normal")
				{
					loadPanel('../visit/menuPart_${patientSn}.html',{'patientSn':'${patientSn}'},'#dyContent',{vid:'#normal', isAdd:"0"}, {beforeLoad: beforeLoadPanel, loadSuccess: loadPanelSuccess});
				}else if(viewType == "outpatientView"){
					if("${out_count!=0}"=="true"){
			            loadPanel('../visit/outpatientViewPart.html',{'patientSn':'${patientSn}'},'#dyContent',{vid:'#outpatientView', isAdd:"0"}, {loadSuccess: loadPanelSuccess});
					}else{
						loadPanel('../visit/visitIndexViewPart_${patientSn}.html',{'patientSn':'${patientSn}'},'#dyContent',{vid:'#visitIndexView', isAdd:"0"}, {loadSuccess: loadPanelSuccess});
					}
				}else if(viewType == "inpatientView"){
					if("${in_count!=0}"=="true"){
						loadPanel('../inpatient/list_${patientSn}.html',{'patientSn':'${patientSn}'},'#dyContent',{vid:'#inpatientView', isAdd:"0"}, {loadSuccess: loadPanelSuccess});
					}else{
						loadPanel('../visit/visitIndexViewPart_${patientSn}.html',{'patientSn':'${patientSn}'},'#dyContent',{vid:'#visitIndexView', isAdd:"0"}, {loadSuccess: loadPanelSuccess});
					}
				}
				else{
					changeView("#"+viewType, '${patientSn}', 'undefined', 'undefined', '0');
				}
			}else{
				if(viewType == "outpatientView")
		        {
		            loadPanel('../visit/outpatientViewPart.html',{'patientSn':'${patientSn}'},'#dyContent',{vid:'#outpatientView', isAdd:"0"}, {loadSuccess: loadPanelSuccess});
		        }
				else if(viewType == "normalView")
				{
					loadPanel('../visit/menuPart_${patientSn}.html',{'patientSn':'${patientSn}'},'#dyContent',{vid:'#normal', isAdd:"0"}, {beforeLoad: beforeLoadPanel, loadSuccess: loadPanelSuccess});
				}
				else if(viewType == "visitIndexView")
				{
					loadPanel('../visit/visitIndexViewPart_${patientSn}.html',{'patientSn':'${patientSn}'},'#dyContent',{vid:'#visitIndexView', isAdd:"0"}, {loadSuccess: loadPanelSuccess});
				}
				else if(viewType == "inpatientView")
				{
					$("#qjfirst").removeAttr("onclick");
					$("#idmodify").removeAttr("onclick");
	 				$("#patientRelationship").hide();
					$("#addFavFolder").hide();
					loadPanel('../inpatient/list_${patientSn}.html',{'patientSn':'${patientSn}'},'#dyContent',{vid:'#inpatientView', isAdd:"0"}, {loadSuccess: loadPanelSuccess});
				}
			}
		}
	
	    function hideObj(obj)
	    {
	    	if(obj != undefined && obj != null)
    		{
				obj.hide();    		
    		}
	    }
		
		// [BUG]0031929 ADD END
	
		//$Author :jin_peng
		// $Date : 2013/05/29 15:20$
		// [BUG]0031929 MODIFY BEGIN
        $(document).ready(function() 
        {
        	$.history.init(loadContent);
        	
        	//$("#upArrShow").children().css("height","1px");
        	
        	//alert($("#visitIndexView").parents(".parentTab").html());
        	
        	// delete by jinpeng begin 2013.05.29
        	/*var viewType = getViewType();
        	
        	if(viewType == "outpatientView")
            {
	            loadPanel('../visit/outpatientViewPart.html',{'visitSn':'${visitSn}'},'#dyContent','#outpatientView',"","0");
            }
   			else if(viewType == "normalView")
   			{
   				loadPanel('../visit/menuPart_${patientSn}.html',{'patientSn':'${patientSn}'},'#dyContent','#normal',"","0");
   			}
   			else if(viewType == "visitIndexView")
   			{
   				loadPanel('../visit/visitIndexViewPart_${patientSn}.html',{'patientSn':'${patientSn}'},'#dyContent','#visitIndexView',"","0");
   			}*/
        	
   			// delete by jinpeng end 2013.05.29
   			
        	// 调整页面布局
        	setPageSize();
        	
            //loadPanel('../patient/index.html', '', '#patientList');            
            /*loadPanel('../timer/list_${patientSn}.html', '', '#timer');
            loadPanel('../inpatient/list_${patientSn}.html', '', '#inpatientView');*/
            return false;
        });
		
     	// [BUG]0031929 MODIFY END
        
        $(window).resize(function() 
  		{
        	// 调整页面布局
        	setPageSize();
        	
        	//alert($("#4").css("display"));
  		});
        
        // [BUG]0011923 MODIFY END
           
      	//$Author :jin_peng
		// $Date : 2012/10/31 17:38$
		// [BUG]0010836 ADD BEGIN
		document.onkeydown = function()
		{
			// 键盘关闭浏览器记录用户退出系统日志
			keyClosed('${Constants.EXIT_KINDS_FLAG_CLOSED}');
		}
	    
		window.onbeforeunload = function()
		{
			// 关闭按钮关闭浏览器记录用户退出系统日志
			buttonClosed('${Constants.EXIT_KINDS_FLAG_CLOSED}');
		}
		// [BUG]0010836 ADD END
    </script>
</body>
</html>
