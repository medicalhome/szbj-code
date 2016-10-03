<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Pragma" content="no-cache" />
    <!-- Prevents caching at the Proxy Server -->
    <meta http-equiv="Expires" content="0" />
    <title>系统设置</title>
	<link type="text/css" rel="Stylesheet" href="../styles/jquery-ui-1.8.18.custom.modify.css" charset="UTF8" />
	<link type="text/css" rel="Stylesheet" href="../styles/layout-default-1.3.0rc29.15.css" charset="UTF8" />
	<link type="text/css" rel="Stylesheet" href="../styles/layout-cdr-dialog.css" charset="UTF8" />
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
    <link type="text/css" rel="Stylesheet" href="../styles/jquery-treeview.css" />
	<script type="text/javascript" src="../scripts/common.js"></script>
	<script type="text/javascript" src="../scripts/layout.js"></script>
    <script type="text/javascript" src="../scripts/system/treeViewItem.js"></script>
    <script type="text/javascript" src="../scripts/system/systemMenu.js"></script>
    <script type="text/javascript" src="../scripts/jquery-treeview.js"></script>
    <script type="text/javascript" src="../scripts/system/systemMenuSetting.js"></script>
    <script type="text/javascript" src="../scripts/system/systemSetting.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#ss_tabs").tabs();
			$("input[name='outpatientViews']").bind("click", outpatientViews_onclick);
			$("input[name='inpatientViews']").bind("click", inpatientViews_onclick);
			$("input[name='timerViews']").bind("click", timerViews_onclick);
			$("input[name='normalViews']").bind("click", normalViews_onclick);
			var perPage = $("#perPage").val();
			$("input[name='rowsPerPage']").each(function(){
				if($(this).val() == perPage){
					$(this).attr("checked",true);
				}
			})
			$("input[name='rowsPerPageLab']").each(function()
			{
				if($(this).val() == $("#perPageLab").val())
				{
					$(this).attr("checked",true);
				}
			})
			initMenuTreeViews();
			//initDrugTreeViews();
			initHideDrugViews();
			$( "#medicineName").htmlSelectSuggest({width:150});
			
			detailFocusTab("ss_tabs");
			setDetailElement($(".hitarea"));
			$("#ss_tabs-2").find("input").removeAttr("onfocus");
			$("#tb_button").find("input").removeAttr("onfocus");
			$("#drug_slow").find("input").removeAttr("onfocus");
			setDetailElement($("#ss_tabs-2").find("input"));
			setDetailElement($("#drug_slow").find("input,select"));
		});
	</script>
	<script language="javascript" for="window" event="onload"> 
	    if(document.readyState=="complete"){			
	    	//initMenuOptionList("possible", "chosen");
			$("#ss_tabs-1").show();
	    } 
	</script>
</head>
<body style="margin: 0; padding: 0;" bgcolor="#FFFFFF">
	<div id="dialog">
	<div id="mainContent">
	<form name="systemSettingForm" method="post"  onsubmit="getSettingValues()" action="../system/save_all.html">
	<div id="ss_tabs">
		<ul>
			<li><div class="tabseperator">&nbsp;&nbsp;</div></li>
			<li class="headtitle"><a href="#ss_tabs-1" class="lj">视图设置</a></li>
			<li><div class="tabseperator"></div></li>
			<li class="headtitle"><a href="#ss_tabs-2" class="lj">药物显示设置</a></li>
		</ul>
		<div id="ss_tabs-1" class="tabcontainer">
             <!--   $Author :wu_jianfeng
                   $Date : 2012/12/11
                   [BUG]12696 ADD BEGIN -->
                <!--
				<table id="tb_visitIndexViews" style="width: 100%;" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="blockHeader" height="27" align="left" style="border-top: solid 1px #B3C4D4; font-weight: bold;">
						<img src="../images/pic_gm_left.png" style="padding-left: 3px; padding-right: 4px;" width="16"
									height="16" alt="" align="absmiddle" />就诊索引视图设置</td>
					</tr>
				    <tr>
						<td width="100%" height="28" style="border-top:1px solid;border-color:#B3C4D4;line-height:28px;">
						    <c:choose>
						       <c:when test="${visitIndexViews.showView==true}">
						          	<input type="checkbox" id="visitIndexViews_1" name="visitIndexViews" checked="true" value="1" onfocus="this.blur()" />
						       </c:when>
						       <c:otherwise>
						          	<input type="checkbox" id="visitIndexViews_1" name="visitIndexViews" value="1" onfocus="this.blur()"/>
						       </c:otherwise>
						    </c:choose>显示视图
					   </td>
					</tr>
				</table>
				-->
				<!--[BUG]12696 ADD END -->
	            <!--   $Author :bin_zhang
	                   $Date : 2012/12/11
	                   [BUG]12309 ADD BEGIN -->
				 <table id="tb_outpatientViews" style="width: 100%;" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="blockHeader" height="27" align="left" style="border-top: solid 1px #B3C4D4; font-weight: bold;" colspan="4">
						<img src="../images/pic_gm_left.png" style="padding-left: 3px; padding-right: 4px;" width="16"
									height="16" alt="" align="absmiddle" />门诊视图设置</td>
					</tr>
				    <tr>
						<td width="100%" height="28" style="border-top:1px solid;border-color:#B3C4D4;line-height:28px;" colspan="4">
						    <c:choose>
						       <c:when test="${outpatientViews.showView==true}">
						          	<input type="checkbox" id="outpatientViews_2" name="outpatientViews" checked="true" value="2" onfocus="this.blur()" />
						       </c:when>
						       <c:otherwise>
						          	<input type="checkbox" id="outpatientViews_2" name="outpatientViews" value="2" onfocus="this.blur()"/>						       		
						       </c:otherwise>
						    </c:choose>显示视图
					   </td>
					</tr>
					<c:if test="${useACL == false || (useACL && (aclAuths.clinicalInfoAuth01 || aclAuths.clinicalInfoAuth02 || aclAuths.clinicalInfoAuth06 || aclAuths.clinicalInfoAuth05 || aclAuths.clinicalInfoAuth07 || aclAuths.clinicalInfoAuth03))}">
					<tr>
						<td height="28" style="border-top:1px solid;border-color:#B3C4D4;line-height:28px;">
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth01)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${outpatientViews.showDiagnosis==true}">
							          	<input type="checkbox" id="outpatientViews_2_02" name="outpatientViews" checked="true" value="2_02" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="outpatientViews_2_02" name="outpatientViews" value="2_02" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示诊断
						    </div>
							</c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth02)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${outpatientViews.showDrug==true}">
							          	<input type="checkbox" id="outpatientViews_2_03" name="outpatientViews" checked="true" value="2_03" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="outpatientViews_2_03" name="outpatientViews" value="2_03" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示药物医嘱
						    </div>
							</c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth06)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${outpatientViews.showExam==true}">
							          	<input type="checkbox" id="outpatientViews_2_06" name="outpatientViews" checked="true" value="2_06" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="outpatientViews_2_06" name="outpatientViews" value="2_06" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示检查
						   	</div>
							</c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth05)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${outpatientViews.showLab==true}">
							          	<input type="checkbox" id="outpatientViews_2_07" name="outpatientViews" checked="true" value="2_07" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="outpatientViews_2_07" name="outpatientViews" value="2_07" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示检验
						   	</div>
							</c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth07)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${outpatientViews.showDoc==true}">
							          	<input type="checkbox" id="outpatientViews_2_09" name="outpatientViews" checked="true" value="2_09" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="outpatientViews2_09" name="outpatientViews" value="2_09" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示病历文书
						    </div>
							</c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth03)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${outpatientViews.showOther==true}">
							          	<input type="checkbox" id="outpatientViews_2_10" name="outpatientViews" checked="true" value="2_10" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="outpatientViews_2_10" name="outpatientViews" value="2_10" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示非药物医嘱
							</div>
							</c:if>
						</td>						    
					</tr>
					</c:if>
				</table>
				<!--[BUG]12309 ADD END -->
				<table id="tb_inpatientViews" style="width: 100%;" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="blockHeader" height="27" align="left" style="border-top:1px solid;border-color:#B3C4D4;font-weight: bold;" colspan="4">
						<img src="../images/pic_gm_left.png" style="padding-left: 3px; padding-right: 4px;" width="16"
									height="16" alt="" align="absmiddle" />住院视图设置</td>
					</tr>
					<tr>
						<td width="100%" height="28" style="border-top:1px solid;border-color:#B3C4D4;line-height:28px;" colspan="4">
						    <c:choose>
						       <c:when test="${inpatientViews.showView==true}">
						          	<input type="checkbox" id="inpatientViews_3" name="inpatientViews" checked="true" value="3" onfocus="this.blur()" />
						       </c:when>
						       <c:otherwise>
						          	<input type="checkbox" id="inpatientViews_3" name="inpatientViews" value="3" onfocus="this.blur()" />
						       </c:otherwise>
						    </c:choose>显示视图
						</td>
					</tr>
					<tr>
						<td height="28" style="border-top:1px solid;border-color:#B3C4D4;line-height:28px;">
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth01)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${inpatientViews.showDiagnosis==true}">
							          	<input type="checkbox" id="inpatientViews_3_02" name="inpatientViews" checked="true" value="3_02" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="inpatientViews_3_02" name="inpatientViews" value="3_02" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示诊断
						   	</div>
							</c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth02)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${inpatientViews.showLongDrug==true}">
							          	<input type="checkbox" id="inpatientViews_3_04" name="inpatientViews" checked="true" value="3_04" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="inpatientViews_3_04" name="inpatientViews" value="3_04" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示长期药物医嘱
						   	</div>
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${inpatientViews.showShortDrug==true}">
							          	<input type="checkbox" id="inpatientViews_3_05" name="inpatientViews" checked="true" value="3_05" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="inpatientViews_3_05" name="inpatientViews" value="3_05" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示临时药物医嘱
						   	</div>
							</c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth06)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${inpatientViews.showExam==true}">
							          	<input type="checkbox" id="inpatientViews_3_06" name="inpatientViews" checked="true" value="3_06" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="inpatientViews_3_06" name="inpatientViews" value="3_06" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示检查
						   	</div>
							</c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth05)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${inpatientViews.showLab==true}">
							          	<input type="checkbox" id="inpatientViews_3_07" name="inpatientViews" checked="true" value="3_07" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="inpatientViews_3_07" name="inpatientViews" value="3_07" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示检验
						   	</div>
							</c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth04)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${inpatientViews.showProcedure==true}">
							          	<input type="checkbox" id="inpatientViews_3_08" name="inpatientViews" checked="true" value="3_08" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="inpatientViews_3_08" name="inpatientViews" value="3_08" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示手术
						   	</div>
							</c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth07)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${inpatientViews.showDoc==true}">
							          	<input type="checkbox" id="inpatientViews_3_09" name="inpatientViews" checked="true" value="3_09" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="inpatientViews_3_09" name="inpatientViews" value="3_09" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示病历文书
						   	</div>
							</c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth03)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${inpatientViews.showOther==true}">
							          	<input type="checkbox" id="inpatientViews_3_10" name="inpatientViews" checked="true" value="3_10" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="inpatientViews_3_10" name="inpatientViews" value="3_10" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示非药物医嘱
						   	</div>
							</c:if>
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${inpatientViews.showTC==true}">
							          	<input type="checkbox" id="inpatientViews_3_11" name="inpatientViews" checked="true" value="3_11" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="inpatientViews_3_11" name="inpatientViews" value="3_11" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示三测单
						    </div>
					   </td>
					</tr>
				</table>	   
				<table id="tb_timerViews" style="width: 100%;" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="blockHeader" height="27" align="left" style="border-top: solid 1px #B3C4D4; font-weight: bold;" colspan="4">
						<img src="../images/pic_gm_left.png" style="padding-left: 3px; padding-right: 4px;" width="16"
									height="16" alt="" align="absmiddle" />时间轴视图设置</td>
					</tr>
					<tr>
						<td width="100%" height="28" style="border-top:1px solid;border-color:#B3C4D4;line-height:28px;" colspan="4">
						    <c:choose>
						       <c:when test="${timerViews.showView==true}">
						          	<input type="checkbox" id="timerViews_4" name="timerViews" checked="true" value="4" onfocus="this.blur()"/>						       		
						       </c:when>
						       <c:otherwise>
									<input type="checkbox" id="timerViews_4" name="timerViews" value="4" onfocus="this.blur()"/>									
						       </c:otherwise>
						    </c:choose>显示视图
						</td>
					</tr>
					<c:if test="${useACL == false || (useACL && (aclAuths.clinicalInfoAuth01 || aclAuths.clinicalInfoAuth02 || aclAuths.clinicalInfoAuth06 || aclAuths.clinicalInfoAuth05 || aclAuths.clinicalInfoAuth04 || aclAuths.clinicalInfoAuth07 || aclAuths.clinicalInfoAuth03))}">
					<tr>
						<td height="28" style="border-top:1px solid;border-color:#B3C4D4;line-height:28px;">
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth01)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${timerViews.showDiagnosis==true}">
							          	<input type="checkbox" id="timerViews_4_02" name="timerViews" checked="true" value="4_02" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="timerViews_4_02" name="timerViews" value="4_02" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示诊断
						    </div>
							</c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth02)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${timerViews.showDrug==true}">
							          	<input type="checkbox" id="timerViews_4_03" name="timerViews" checked="true" value="4_03" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="timerViews_4_03" name="timerViews" value="4_03" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示药物医嘱
						    </div>
							</c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth06)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${timerViews.showExam==true}">
							          	<input type="checkbox" id="timerViews_4_06" name="timerViews" checked="true" value="4_06" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="timerViews_4_06" name="timerViews" value="4_06" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示检查
						    </div>
							</c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth05)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${timerViews.showLab==true}">
							          	<input type="checkbox" id="timerViews_4_07" name="timerViews" checked="true" value="4_07" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="timerViews_4_07" name="timerViews" value="4_07" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示检验
						    </div>
							</c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth04)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${timerViews.showProcedure==true}">
							          	<input type="checkbox" id="timerViews_4_08" name="timerViews" checked="true" value="4_08" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="timerViews_4_08" name="timerViews" value="4_08" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示手术
						    </div>
							</c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth07)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${timerViews.showDoc==true}">
							          	<input type="checkbox" id="timerViews_4_09" name="timerViews" checked="true" value="4_09" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="timerViews_4_09" name="timerViews" value="4_09" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示病历文书
						    </div>
							</c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth03)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${timerViews.showOther==true}">
							          	<input type="checkbox" id="timerViews_4_10" name="timerViews" checked="true" value="4_10" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="timerViews_4_10" name="timerViews" value="4_10" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示非药物医嘱
						    </div>
							</c:if>
					   </td>
					</tr>
					</c:if>
				</table>
				<table id="tb_normalViews" style="width: 100%;" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="blockHeader" height="27" align="left" style="border-top: solid 1px #B3C4D4; font-weight: bold;" colspan="2">
						<img src="../images/pic_gm_left.png" style="padding-left: 3px; padding-right: 4px;" width="16"
									height="16" alt="" align="absmiddle" />综合视图设置</td>
					</tr>
					<tr>
						<td width="25%" height="28" style="border-top:1px solid;border-color:#B3C4D4;line-height:28px;">
						    <c:choose>
						       <c:when test="${normalViews.showView==true}">
						          	<input type="checkbox" id="normalViews_5" name="normalViews" checked="true" value="5" onfocus="this.blur()" />
						       </c:when>
						       <c:otherwise>
						          	<input type="checkbox" id="normalViews_5" name="normalViews" value="5" onfocus="this.blur()"/>
						       </c:otherwise>
						    </c:choose>显示视图&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					   </td>
					   <td height="28" style="border-top:1px solid;border-color:#B3C4D4;line-height:28px;">
					        <input type="hidden" id="perPage" name="perPage" value="${rowsPerPage}"></input>
					                     显示条数：&nbsp;&nbsp;
					        <input type="radio" id="rowsPerPage" name="rowsPerPage" value="10"/>10&nbsp;&nbsp;&nbsp;
                            <input type="radio" id="rowsPerPage" name="rowsPerPage" value="15"/>15&nbsp;&nbsp;&nbsp;
                            <input type="radio" id="rowsPerPage" name="rowsPerPage" value="20"/>20&nbsp;&nbsp;&nbsp;
					   </td>
					</tr>
				</table>
				<table id="tb_normalViews" style="width: 100%;" cellspacing="0" cellpadding="0" border="0">
					<input id="displayMenus" name="displayMenus" type="hidden" value="${displayMenus}" />
					<ul id="allMenus" style="display:none">
			            <c:forEach items="${systemMenus}" var="menu">
					         <li code="${menu.menuCode}">${menu.menuName}</li>
						</c:forEach>
					</ul>
					<tr class="tabletitle">
				        <td height="30" align="center" style="border-right:1px solid #C5D6E0;">可选菜单</td>
				        <td align="center" style="border-right:1px solid #C5D6E0;">操作</td>
				        <td align="center">已选菜单</td>
	   			    </tr>
				    <tr>
				        <td style="border-right:1px solid #C5D6E0;padding:20px;vertical-align: top;width:45%">
							<div id="possible_tip"  style="text-align:center;">从右边选择您的功能菜单</div>
							<div id="possible">
							</div>
				        </td>
				        <td align="center" valign="middle" align="center" style="border-right:1px solid #C5D6E0;padding: 0 10px;">
				            <input type="button" style="width:78px;cursor: pointer;" onclick="copyToList('possible','chosen');setDetailElement($('.hitarea'));" value="添加&nbsp;->" /><br></br>
				            <input type="button" onclick="copyAll('possible','chosen');setDetailElement($('.hitarea'));" value="添加All&nbsp;->" style="width:78px;cursor: pointer;" /><br></br>
				            <input type="button" onclick="copyToList('chosen','possible');setDetailElement($('.hitarea'));" value="<-&nbsp;删除" style="width:78px;cursor: pointer;" /><br></br>
				            <input type="button" onclick="copyAll('chosen','possible');setDetailElement($('.hitarea'));" value="<-&nbsp;删除All" style="width:78px;cursor: pointer;" /><br></br>
				        </td>
				        <td style="padding:20px;vertical-align: top;width:45%">
							<div id="chosen_tip" style="text-align:center;">从左边选择您的功能菜单</div>						
							<div id="chosen">							
							</div>
				        </td>
			    	</tr>
				</table>
				<table id="tb_normalViews" style="width: 100%;" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="blockHeader" height="27" align="left" style="border-top: solid 1px #B3C4D4; font-weight: bold;" colspan="4">
						<img src="../images/pic_gm_left.png" style="padding-left: 3px; padding-right: 4px;" width="16"
									height="16" alt="" align="absmiddle" />综合视图摘要信息设置</td>
					</tr>
					<tr>
						<td height="28" style="border-top:1px solid;border-color:#B3C4D4;line-height:28px;" colspan="2">
							<div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${normalViews.showVisit==true}">
							          	<input type="checkbox" id="normalViews_5_01" name="normalViews" checked="true" value="5_01" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="normalViews_5_01" name="normalViews" value="5_01" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示就诊
						    </div>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth01)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${normalViews.showDiagnosis==true}">
							          	<input type="checkbox" id="normalViews_5_02" name="normalViews" checked="true" value="5_02" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="normalViews_5_02" name="normalViews" value="5_02" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示诊断
						    </div>
						    </c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth02)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${normalViews.showLongDrug==true}">
							          	<input type="checkbox" id="normalViews_5_04" name="normalViews" checked="true" value="5_04" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="normalViews_5_04" name="normalViews" value="5_04" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示长期药物医嘱
						    </div>
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${normalViews.showShortDrug==true}">
							          	<input type="checkbox" id="normalViews_5_05" name="normalViews" checked="true" value="5_05" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="normalViews_5_05" name="normalViews" value="5_05" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示临时药物医嘱
						    </div>
						    </c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth06)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${normalViews.showExam==true}">
							          	<input type="checkbox" id="normalViews_5_06" name="normalViews" checked="true" value="5_06" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="normalViews_5_06" name="normalViews" value="5_06" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示检查
						    </div>
						    </c:if>
							<c:if test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth05)}">
						    <div style="float: left; width: 25%">
							    <c:choose>
							       <c:when test="${normalViews.showLab==true}">
							          	<input type="checkbox" id="normalViews_5_07" name="normalViews" checked="true" value="5_07" onfocus="this.blur()" />
							       </c:when>
							       <c:otherwise>
							          	<input type="checkbox" id="normalViews_5_07" name="normalViews" value="5_07" onfocus="this.blur()"/>
							       </c:otherwise>
							    </c:choose>显示检验
						    </div>
						    </c:if>
					   </td>
					</tr>
				</table>
				<table id="tb_normalViews"
					<c:choose>
						<c:when test="${useACL == false || (useACL && aclAuths.clinicalInfoAuth05)}">
				          	style="width: 100%;"
				       </c:when>
				       <c:otherwise>
				          	style="width: 100%;display: none;"
				       </c:otherwise>
					</c:choose> cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="blockHeader" height="27" align="left" style="border-top: solid 1px #B3C4D4; font-weight: bold;" colspan="2">
						<img src="../images/pic_gm_left.png" style="padding-left: 3px; padding-right: 4px;" width="16"
									height="16" alt="" align="absmiddle" />检验历史曲线对比结果显示条数设置</td>
					</tr>
					<tr>
					   <td height="28" style="border-top:1px solid;border-color:#B3C4D4;line-height:28px;">
					        <input type="hidden" id="perPageLab" name="perPageLab" value="${rowsPerPageLab}"></input>
					                     显示条数：&nbsp;&nbsp;
					        <input type="radio" id="rowsPerPageLab" name="rowsPerPageLab" value="10"/>10&nbsp;&nbsp;&nbsp;
                            <input type="radio" id="rowsPerPageLab" name="rowsPerPageLab" value="15"/>15&nbsp;&nbsp;&nbsp;
                            <input type="radio" id="rowsPerPageLab" name="rowsPerPageLab" value="20"/>20&nbsp;&nbsp;&nbsp;
					   </td>
					</tr>
				</table>
		</div>
		<div id="ss_tabs-2" class="tabcontainer">
			<div id="ss_drug" style="width: 100%;cellspacing:0 ;cellpadding:0;border:0" align="center"
				class="table">
				<div style="height: 30px; align: left; valign: middle; border: solid 1px #D8D9DB; background-color: #F0F8FE;">
					<div class="cell"
						style="margin-left:5px;width: 50px; text-align: left;">药物名称</div>
					<div
						style="margin-top: 4px; float: left;">
						<input type="text" style="display:none;" value="stop" />
						<select id="medicineName" name="medicineName"
							style="width: 250px;">
							<option value="${Constants.OPTION_SELECT}">请选择</option>
							<html2:pycodeoptions
								domain="${Constants.DOMAIN_DRUG_DICTIONARY}"/>
						</select> <input type="button" onclick="addMedicineName()" value="添加" />
					</div>
					<div style="float:right;margin-top:8px; margin-right:3px;">
						<div style="width:10px;height:10px;background-color:#C6F285;float:left; margin-right:3px;"/><div style="float:left;">新增隐藏药物</div>
					</div>
				</div>
			</div>
			<div id="drug_slow" style="overflow-y: auto;max-height:300px;_height:300px;">
				<!--[if lt ie 8]><div style='+zoom:1;'><![endif]-->
				<table id="ss_tabs-3_drug" style="width: 100%" cellspacing="1" cellpadding="2" border="0" class="table">
	 				<tr id="tr2" class="tabletitle">
						<td height="28" align="center">药物名称</td>
						<td height="28" align="center" width="140px">操作时间</td>
						<td height="28" align="center" width="80px">操作</td>
					</tr>
					<c:forEach items="${hideDrug}" var="hideDrug" varStatus="status">
						<tr
							<c:if test="${status.count%2==1}">class="odd""</c:if>
							<c:if test="${status.count%2==0}">class="even""</c:if>
							style="cursor: pointer">
							<td height="24" align="left">&nbsp;${hideDrug.drugName}
								<input type="hidden" name="delete_codeDrug" value="${hideDrug.drugCode}" /></td>
							<td height="24" align="center"><fmt:formatDate
								value="${hideDrug.createTime}" type="date" pattern="yyyy-MM-dd" /></td> 
							<td height="24" align="center">
								<%-- <input type="hidden" name="codeDrugIds" value="${hideDrug.hideDrugSn}"/> --%>
								<input id="medicineId" type="button" onclick="deleteMedicineName(this)"  onfocus="this.blur()" value="删除"/>
							</td>
						</tr>
					</c:forEach>
				</table>
				<!--[if lt ie 8]></div><![endif]-->
				<div id="to"></div>
			</div>
		</div>
	</div>		     
	<div style="width:100%;">
		<table id="tb_button" style="width: 100%;" cellspacing="0" cellpadding="0" border="0">
			<tr><td style="height:15px;"/></tr>
			<tr>
			    <td align="center" valign="middle" style="border:0px;" height="30">
			        <input type="button" value="" style="border:0px;BACKGROUND-IMAGE:url(../images/button_apply.png);width:51px;height:23px;cursor: pointer;"  onfocus="this.blur()" onclick="ok_onclick('apply')" />&nbsp;&nbsp;
			        <input type="button" value="" style="border:0px;BACKGROUND-IMAGE:url(../images/qd.jpg);width:51px;height:23px;cursor: pointer;"  onfocus="this.blur()" onclick="ok_onclick('all')" />&nbsp;&nbsp;
			        <input type="button"  onfocus="this.blur()" onclick ="closeDialog()" style="border:0px;BACKGROUND-IMAGE:url(../images/qx.jpg);width:51px;height:23px;cursor: pointer;"/>
		        </td>
		    </tr>
			<tr><td style="height:5px;"/></tr>
		</table>
	</div>
	</form>
</div>
</div>		     
</body>
</html>