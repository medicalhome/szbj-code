<%@ page language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Pragma" content="no-cache" />
	<!-- Prevents caching at the Proxy Server -->
	<meta http-equiv="Expires" content="0" />
	<title>患者列表</title>
	<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
	<link rel="Stylesheet" type="text/css" href="../styles/patientFavFolder.css" />
	<script type="text/javascript" src="../scripts/jquery.ui.mouse.js"></script>
	<script type="text/javascript" src="../scripts/jquery.ui.selectable.js"></script>
	<script type="text/javascript" src="../scripts/patient/patientFavList.js"></script>
	<script type="text/javascript" src="../scripts/timerInpatient/timerInpatient.js"></script>	
	<script type="text/javascript" src="../scripts/forwardOrBack/forwardOrBack.js"></script>
	<script>
		$(function () {
			//添加页面文本框的回车检索
			$("#conditionFormPatient #folderName").keyup(function (event) {
				if(event.keyCode==13)
				{
					createNewFolder('','conditionFormPatient','#patientListView_fav');
				}
			});
			
			// $Author:wei_peng
			// $Date:2013/12/19 15:13
			// $[BUG]0040534 ADD BEGIN
			/* $("#selectable").selectable({
				stop:function () {
					$(".ui-selected",this).each(
					function (i,selected) {
						 searchFolder('', 'conditionFormPatient','#pfcontent');	
					});
				}
			}); */
			
			
			$("#selectable .tabEnterPatient").click(function(){
				$("#selectable .tabEnterPatient").removeClass("ui-selected");
				$(this).addClass("ui-selected");
				
				searchFolder('', 'conditionFormPatient','#pfcontent');	
				
			});
			
			$("#selectable .folderFlag").click(function(){
				var parentLi = $(this).parent().parent().parent();
				var childId = '#child_'+parentLi.attr("id");
				if($(childId).length>0 && $(childId).is(":hidden"))
				{
					$(childId).show();
					$(this).attr("src","../images/tree_folder2.gif");
				}else if($(childId).length>0){
					$(childId).hide();
					$(this).attr("src","../images/tree_folder1.gif");
				}
			});
			
			$("#selectable .tabEnterPatient").mousedown(function(e){
				if(e.which==3 && !$(this).hasClass('endLevel')){
					var msg = '<div>创建【'+$.trim($(this).find('div').text())+'】的子文件夹</div><div>名称：<input type="text" id="childFolderName" name="'+$(this).attr("id")+'" /></div>';
					createChildFolder('创建患者收藏夹', msg, $(this).attr('id'), function(){},'','#patientListView_fav');
					document.oncontextmenu = function (){return false;}
				}
			});
			
			
			/* $("#childFolderName").live('keyup',function (event) {
				if(event.keyCode==13)
				{
					createNewChildFolder('conditionFormPatient','#patientListView_fav',$(this).attr('name'));
					$("#confirmMessage").dialog("close");
				}
			}); */
			// $[BUG]0040534 ADD END
			
			
			
			
			$("input[type='button']").removeAttr("onfocus");
			
			logicalPatient();
		});
		
		function getLoginFlag()
		{
			return "${loginFlag}";
		}
		
	    $(document).ready(function() {
			loadFavPatientList("../patient/list_pfpatientlist_.html?flag=${loginFlag}","","#pfcontent");		    
		});

		// 全选/全不选
		function selectAllOrNo(checkboxid,nameid) {
			var size=$('#'+checkboxid+':checked').size();
			if(size) {
				$("input[name='"+nameid+"']").attr("checked",true);
			}else {
				$("input[name='"+nameid+"']").attr("checked",false);
			}
		}
		$(function(){
			if(!isIE6())
			{
				$("#pfcontent").css("width","79%");
			}
			else
			{
				if($("#selectable").find("li").size()*$("#selectable").find("li").height()>$("#content").height()-10)
				{
					$("#controlImageWidth").css("width","111%");
				}
			}
		});
	</script>
</head>
<body style="margin: 0; padding: 0;">
	<c:choose>
		<c:when test="${loginFlag != null && fn:length(loginFlag) != 0}">
			<div style="width:100%">
				<div  style="float:left;width: 20%;word-wrap: break-word; word-break: break-all;background-color: #eaf3fa">
					<div id="pl_tabs5" style="width:100%;">
						<div id="folderView" style="width:100%;border: solid 1px #388cb1;">
							<div id="controlImageWidth" style="width:100%;height:26px;text-align:center; background-image: url(../images/hzscn.png);background-repeat:repeat;align="middle"></div>
							<div id="content" style="overflow-y: auto; height: 320px; position: relative;padding-left:5px;">
							
								<ol id="selectable">
									<c:forEach items="${patientFavFolders}" var="folder" varStatus="status">
										<li id="${folder.folderSn}" class="tabEnterPatient">
											<div style="float:left;margin-bottom:5px;margin-top:5px;" align="middle"
												<c:if test="${fn:length(folder.folderName)>8}"> title="${folder.folderName}" </c:if>>
												<c:if test="${not empty folder.childrenFolders}"><div style="float:left;"><img class="folderFlag" src="../images/tree_folder1.gif" align="middle"/></div></c:if>
												<image src="../images/fo.png" style="padding-left:1px;" />										
												<c:if test="${fn:length(folder.folderName)>8}">${fn:substring(folder.folderName,0,10)}...</c:if>
												<c:if test="${fn:length(folder.folderName)<=8}">${folder.folderName}</c:if>
											</div>
										</li>
										<c:if test="${not empty folder.childrenFolders}">
											<li style="border-bottom:none;width:85%;float:right;display:none;" id="child_${folder.folderSn}">
												<ol id="selectable">
													<c:forEach items="${folder.childrenFolders}" var="childfolder1" varStatus="status">
														<li id="${childfolder1.folderSn}" class="tabEnterPatient">
															<div style="float:left;margin-bottom:5px;margin-top:5px;" align="middle"
																<c:if test="${fn:length(childfolder1.folderName)>5}"> title="${childfolder1.folderName}" </c:if>>
																<c:if test="${not empty childfolder1.childrenFolders}"><div style="float:left;"><img class="folderFlag" src="../images/tree_folder1.gif" align="middle"/></div></c:if>
																<image src="../images/fo.png" style="padding-left:1px;" />										
																<c:if test="${fn:length(childfolder1.folderName)>5}">${fn:substring(childfolder1.folderName,0,5)}...</c:if>
																<c:if test="${fn:length(childfolder1.folderName)<=5}">${childfolder1.folderName}</c:if>
															</div>
														</li>
														<c:if test="${not empty childfolder1.childrenFolders}">
															<li style="border-bottom:none;width:82%;float:right;display:none;" id="child_${childfolder1.folderSn}">
																<ol id="selectable">
																	<c:forEach items="${childfolder1.childrenFolders}" var="childfolder2" varStatus="status">
																		<li id="${childfolder2.folderSn}" class="tabEnterPatient endLevel">
																			<div style="float:left;margin-bottom:5px;margin-top:5px;" align="middle"
																				<c:if test="${fn:length(childfolder2.folderName)>4}"> title="${childfolder2.folderName}" </c:if>>
																				<image src="../images/fo.png" style="padding-left:1px;" />										
																				<c:if test="${fn:length(childfolder2.folderName)>4}">${fn:substring(childfolder2.folderName,0,4)}...</c:if>
																				<c:if test="${fn:length(childfolder2.folderName)<=4}">${childfolder2.folderName}</c:if>
																			</div>
																		</li>
																	</c:forEach>
																</ol>
															</li>
														</c:if>
													</c:forEach>
												</ol>
											</li>
										</c:if>
									</c:forEach>
								</ol>
								
							</div>
							<form id="conditionFormPatient" name="conditionFormPatient" method="post" action="../patient/list_patientfav.html?flag=${loginFlag}">
								<input type="hidden" id="selectedFolder" name="selectedFolder" value="${folderId}" /> 
								<input type="hidden" id="postbackEvent" name="postbackEvent" value="" /> 
								<div style="border-top:1px solid #388cb1;background-image: url(../images/addFolder.png);">
									<table style="width: 100%; height: 50px" cellpadding="2"
										cellspacing="0" border="0">
										<tr height="40">
											<!-- <td valign="middle" align="right" width="40%"><font color="#175b88">文件夹名称：</font></td> -->
											<td align="left" valign="middle" colspan="2">
												<input type="text" id="folderName" name="folderName" onmouseover="this.style.background='#FDE8FE';"
													onmouseout="this.style.background='#FFFFFF'" value=""   style="margin-left: 5px;width:120px;margin-bottom:5px;border:1px solid #388cb1;" /> 
												<input type="input" name="hidSubmit" style="display: none;" />
											</td></tr>
											<tr>
											<td width="46px">
												<input type="button" onclick="createNewFolder('', 'conditionFormPatient','#patientListView_fav');"
													onfocus="this.blur()"
													style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/patient_create_folder.png); width: 46px; height: 24px; margin-left: 20px;margin-bottom:5px; cursor: pointer;"
													align="absmiddle" />
											</td>
											
											<td width="46px">
												<input type="button" onclick="deleteFolder('', 'conditionFormPatient','#patientListView_fav');"										
													onfocus="this.blur()"
													style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/patient_delete_folder.png); width: 46px; height: 24px;margin-bottom:5px; cursor: pointer;"
													align="absmiddle" />
											</td>
										</tr>
									</table>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div id="pfcontent"  style="float:left;margin-left:2px;border:1px solid #388cb1;height:415px;BACKGROUND-color:#ffffff"></div>
			</div>
		</c:when>
		<c:otherwise>
	
				<div style="width:100%"><div  style="float:left;width: 20%;word-wrap: break-word; word-break: break-all;background-color: #eaf3fa"><div id="pl_tabs5">
				<div id="folderView" style="border: solid 1px #388cb1; ">
				<div id="controlImageWidth" style="height:26px;text-align:center;background-image: url(../images/hzscn.png);background-repeat:repeat;"></div>
					<div id="content" style="overflow-y: auto; height: 320px; position: relative;padding-left:5px;">
						
						<ol id="selectable">
							<c:forEach items="${patientFavFolders}" var="folder" varStatus="status">
								<li id="${folder.folderSn}" class="tabEnterPatient">
									<div style="float:left;margin-bottom:5px;margin-top:5px;" align="middle"
										<c:if test="${fn:length(folder.folderName)>8}"> title="${folder.folderName}" </c:if>>
										<c:if test="${not empty folder.childrenFolders}"><div style="float:left;"><img class="folderFlag" src="../images/tree_folder1.gif" align="middle"/></div></c:if>
										<image src="../images/fo.png" style="padding-left:1px;" />										
										<c:if test="${fn:length(folder.folderName)>8}">${fn:substring(folder.folderName,0,8)}...</c:if>
										<c:if test="${fn:length(folder.folderName)<=8}">${folder.folderName}</c:if>
									</div>
								</li>
								<c:if test="${not empty folder.childrenFolders}">
									<li style="border-bottom:none;width:85%;float:right;display:none;" id="child_${folder.folderSn}">
										<ol id="selectable">
											<c:forEach items="${folder.childrenFolders}" var="childfolder1" varStatus="status">
												<li id="${childfolder1.folderSn}" class="tabEnterPatient">
													<div style="float:left;margin-bottom:5px;margin-top:5px;" align="middle"
														<c:if test="${fn:length(childfolder1.folderName)>6}"> title="${childfolder1.folderName}" </c:if>>
														<c:if test="${not empty childfolder1.childrenFolders}"><div style="float:left;"><img class="folderFlag" src="../images/tree_folder1.gif" align="middle"/></div></c:if>
														<image src="../images/fo.png" style="padding-left:1px;" />										
														<c:if test="${fn:length(childfolder1.folderName)>6}">${fn:substring(childfolder1.folderName,0,6)}...</c:if>
														<c:if test="${fn:length(childfolder1.folderName)<=6}">${childfolder1.folderName}</c:if>
													</div>
												</li>
												<c:if test="${not empty childfolder1.childrenFolders}">
													<li style="border-bottom:none;width:82%;float:right;display:none;" id="child_${childfolder1.folderSn}">
														<ol id="selectable">
															<c:forEach items="${childfolder1.childrenFolders}" var="childfolder2" varStatus="status">
																<li id="${childfolder2.folderSn}" class="tabEnterPatient endLevel">
																	<div style="float:left;margin-bottom:5px;margin-top:5px;" align="middle"
																		<c:if test="${fn:length(childfolder2.folderName)>5}"> title="${childfolder2.folderName}" </c:if>>
																		<image src="../images/fo.png" style="padding-left:1px;" />										
																		<c:if test="${fn:length(childfolder2.folderName)>5}">${fn:substring(childfolder2.folderName,0,5)}...</c:if>
																		<c:if test="${fn:length(childfolder2.folderName)<=5}">${childfolder2.folderName}</c:if>
																	</div>
																</li>
															</c:forEach>
														</ol>
													</li>
												</c:if>
											</c:forEach>
										</ol>
									</li>
								</c:if>
							</c:forEach>
						</ol>
						
					</div>
						<form id="conditionFormPatient" name="conditionFormPatient" method="post" action="../patient/list_patientfav.html?flag=${loginFlag}">
					<input type="hidden" id="selectedFolder" name="selectedFolder" value="${folderId}" /> 
					<input type="hidden" id="postbackEvent" name="postbackEvent" value="" /> 
					<div style="border-top:1px solid #388cb1;background-image: url(../images/addFolder.png);">
						<table style="width: 100%; height: 50px" cellpadding="2"
							cellspacing="0" border="0">
							<tr height="40">
								<!-- <td valign="middle" align="right" width="40%"><font color="#175b88">文件夹名称：</font></td> -->
								<td align="left" valign="middle" colspan="2">
									<input type="text" id="folderName" name="folderName"
										maxlength="60" onmouseover="this.style.background='#FDE8FE';"
										onmouseout="this.style.background='#FFFFFF'" value=""   style="margin-left: 5px;margin-bottom:5px;border:1px solid #388cb1;width:110px;" /> 
									<input type="input" name="hidSubmit" style="display: none;" />
									
								</td></tr>
								
								<tr>
								<td width="36px">
									<input type="button" onclick="createNewFolder('', 'conditionFormPatient','#patientListView_fav');"
										onfocus="this.blur()"
										style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/patient_create_folder.png); width: 46px; height: 24px; margin-left: 10px;margin-bottom:5px; cursor: pointer;"
										align="absmiddle" />
								</td>
								<td width="36px">
									<input type="button" onclick="deleteFolder('', 'conditionFormPatient','#patientListView_fav');"										
										onfocus="this.blur()"
										style="color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/patient_delete_folder.png); width: 46px; height: 24px;margin-bottom:5px; cursor: pointer;"
										align="absmiddle" />
								</td>
							</tr>
						</table>
					</div>
				</form>
				</div></div></div><div id="pfcontent"  style="float:left;margin-left:2px;border:1px solid #388cb1;border-right:0px;height:415px;BACKGROUND-color:#ffffff"></div>
				
			</div>
		</c:otherwise>
	</c:choose>
	
	
</body>
</html>