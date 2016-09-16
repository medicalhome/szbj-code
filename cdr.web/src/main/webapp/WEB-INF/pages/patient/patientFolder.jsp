<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Pragma" content="no-cache" />
<!-- Prevents caching at the Proxy Server -->
<meta http-equiv="Expires" content="0" />
<title>增加收藏夹</title>
<link type="text/css" rel="Stylesheet"
	href="../styles/jquery-ui-1.8.18.custom.modify.css" charset="UTF8" />
<link type="text/css" rel="Stylesheet"
	href="../styles/layout-default-1.3.0rc29.15.css" charset="UTF8" />
<link type="text/css" rel="Stylesheet"
	href="../styles/layout-cdr-dialog.css" charset="UTF8" />
<link rel="Stylesheet" type="text/css" href="../styles/tablelist.css" />
<link rel="Stylesheet" type="text/css" href="../styles/patientFavFolder.css" />
<script type="text/javascript" src="../scripts/common.js"></script>
<script type="text/javascript" src="../scripts/layout.js"></script>
<script type="text/javascript"
	src="../scripts/patient/patientFavList.js"></script>
<script>
	function clearDefaultText(el, message) {
		var obj = el;
		if (typeof (el) == "string")
			obj = document.getElementById(id);
		if (obj.value == message) {
			obj.value = "";
		}
		obj.onblur = function() {
			if (obj.value == "") {
				obj.value = message;
			}
		}
	}
	
	$(function()
	{
		$("#patientFolderView").find("input").removeAttr("onfocus");
		setDetailElement($("#patientFolderView").find("input,#loadpatientListView_fav"));
		banTab($("#loadpatientListView_fav"));
    	if($("input[name='folders']:not(:disabled)").size() <= 0)
		{
			window.setTimeout("$('#loadfolderName').focus()", 70);
			$("#loadpatientListView_fav").attr({tabindex : "0", hidefocus : "true"});
			$("#loadpatientListView_fav").attr("class", "tiTab");
		}
    	else
   		{
    		$("input[name='folders']:not(:disabled)").last().attr("class", "tiTab");
   		} 
    	
    	$("#selectableAddPatient .folderFlag").click(function(){
			var parentLi = $(this).parent().parent().parent();
			var childId = '#childAddPatient_'+parentLi.attr("id");
			if($(childId).length>0 && $(childId).is(":hidden"))
			{
				$(childId).show();
				$(this).attr("src","../images/tree_folder2.gif");
			}else if($(childId).length>0){
				$(childId).hide();
				$(this).attr("src","../images/tree_folder1.gif");
			}
		});
    	
    	$("#selectableAddPatient .tabEnterPatient").mousedown(function(e){
			if(e.which==3 && !$(this).hasClass('endLevel')){
				var msg = '<div>创建【'+$.trim($(this).find('div').text())+'】的子文件夹</div><div>名称：<input type="text" id="childFolderName" /></div>';
				createChildFolder('创建患者收藏夹', msg, $(this).attr('id'), function(){},'../patient/list_loadpatientfav_'+${patientSn}+'.html','#loadpatientListView_fav');
				document.oncontextmenu = function (){return false;}
			}
		});
	});
</script>

</head>
<body style="margin: 0; padding: 0;" style="overflow-y:hidden; ">
	<div id="dialog">
		<div id="mainContent">
			<div id="patientFolderView" style="overflow: hidden;">
				<form name="patientFolderForm" method="post"
					action="../patient/patientFolder_${patientSn}.html">
					<input id="patientSn" name="patientSn" type="hidden"
						value="${patientSn}" /> <input id="folderIds" name="folderIds"
						type="hidden" value="" />
					<input id="postbackEvent" name="postbackEvent" type="hidden"
						value="save_folder" />
					<div id="loadpatientListView_fav"
						style="overflow-y: auto;overflow-x: hidden; position: relative; height: 230px; margin: 0px; border: solid 1px #cadeee; background-color: #f8f9fb;">
						
						<div align="left">
							<ol id="selectableAddPatient">
								<c:forEach items="${allPatientFolderDtos}" var="folder" varStatus="status">
									<li id="${folder.folderSn}" class="tabEnterPatient" 
										<c:if test="${empty folder.childrenFolders}">style="padding-left:15px;"</c:if>>
										<div style="float:left;margin-bottom:5px;margin-top:5px;" align="middle"
											<c:if test="${fn:length(folder.folderName)>18}"> title="${folder.folderName}" </c:if>>
											<c:if test="${not empty folder.childrenFolders}"><div style="float:left;"><img class="folderFlag" src="../images/tree_folder1.gif" align="middle"/></div></c:if>
											<input type="checkbox" name="folders" value="${folder.folderSn}" id="folder${folder.folderSn}" onfocus="this.blur()"
												<c:forEach items="${patientFolders}" var="patientFolder">
						               				<c:if test="${patientFolder.folderSn == folder.folderSn}">checked disabled</c:if>
						               			</c:forEach> />
											<image src="../images/fo.png" style="padding-left:1px;" />										
											<c:if test="${fn:length(folder.folderName)>18}">${fn:substring(folder.folderName,0,18)}...</c:if>
											<c:if test="${fn:length(folder.folderName)<=18}">${folder.folderName}</c:if>
										</div>
									</li>
									<c:if test="${not empty folder.childrenFolders}">
										<li style="border-bottom:none;margin-left:20px;float:left;display:none;" id="childAddPatient_${folder.folderSn}">
											<ol id="selectableAddPatient">
												<c:forEach items="${folder.childrenFolders}" var="childfolder1" varStatus="status">
													<li id="${childfolder1.folderSn}" class="tabEnterPatient" <c:if test="${empty childfolder1.childrenFolders}">style="padding-left:15px;"</c:if>>
														<div style="float:left;margin-bottom:5px;margin-top:5px;" align="middle"
															<c:if test="${fn:length(childfolder1.folderName)>15}"> title="${childfolder1.folderName}" </c:if>>
															<c:if test="${not empty childfolder1.childrenFolders}"><div style="float:left;"><img class="folderFlag" src="../images/tree_folder1.gif" align="middle"/></div></c:if>
															<input type="checkbox" name="folders" value="${childfolder1.folderSn}" id="folder${childfolder1.folderSn}" onfocus="this.blur()"
																<c:forEach items="${patientFolders}" var="patientFolder">
										               				<c:if test="${patientFolder.folderSn == childfolder1.folderSn}">checked disabled</c:if>
										               			</c:forEach>/>
															<image src="../images/fo.png" style="padding-left:1px;" />										
															<c:if test="${fn:length(childfolder1.folderName)>15}">${fn:substring(childfolder1.folderName,0,15)}...</c:if>
															<c:if test="${fn:length(childfolder1.folderName)<=15}">${childfolder1.folderName}</c:if>
														</div>
													</li>
													<c:if test="${not empty childfolder1.childrenFolders}">
														<li style="border-bottom:none;padding-left:35px;float:left;display:none;" id="childAddPatient_${childfolder1.folderSn}">
															<ol id="selectableAddPatient">
																<c:forEach items="${childfolder1.childrenFolders}" var="childfolder2" varStatus="status">
																	<li id="${childfolder2.folderSn}" class="tabEnterPatient endLevel" >
																		<div style="float:left;margin-bottom:5px;margin-top:5px;" align="middle"
																			<c:if test="${fn:length(childfolder2.folderName)>13}"> title="${childfolder2.folderName}" </c:if>>
																			<input type="checkbox" name="folders" value="${childfolder2.folderSn}" id="folder${childfolder2.folderSn}" onfocus="this.blur()"
																				<c:forEach items="${patientFolders}" var="patientFolder">
														               				<c:if test="${patientFolder.folderSn == childfolder2.folderSn}">checked disabled</c:if>
														               			</c:forEach>/>
																			<image src="../images/fo.png" style="padding-left:1px;" />										
																			<c:if test="${fn:length(childfolder2.folderName)>13}">${fn:substring(childfolder2.folderName,0,13)}...</c:if>
																			<c:if test="${fn:length(childfolder2.folderName)<=13}">${childfolder2.folderName}</c:if>
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
						
					</div>
					<div style="height: 26px;margin-top: 2px;">
						<input type="button" class="folder_2" id="folderDetail"
							onclick="createNewFolderAndloadDiv('../patient/list_loadpatientfav_${patientSn}.html', '#loadpatientListView_fav');"
							onfocus="this.blur()"
							style="float: right; color: #464646; border: 0px; BACKGROUND-IMAGE: url(../images/patient_create_folder.png); width: 46px; height: 24px; margin: 0; cursor: pointer;" />
						<input type="text" id="loadfolderName" name="folderName" class="folder_1"
							onmouseover="this.style.background='#FDE8FE';"
							onmouseout="this.style.background='#FFFFFF'" value=""
							style="float: right; width: 22%; height: 20px; border: 1px solid #388cb1;" />
						<div style="float: right; height: 24px;">文件夹名称：</div>
						<input type="input" name="hidSubmit" style="display: none;" />
					</div>
					<div
						style="height: 85px; margin: 0px; border: solid 1px #cadeee; background-color: #f8f9fb;">
						<div align="left">
							<table style="width: 100%">
								<tr>
									<td><textarea id="pfmemo" name="pfmemo"
											style="height: 70px; width: 480px; font-family:"
											微软雅黑";text-shadow:1px 1px 3px #fff;" onfocus="clearDefaultText(this,'请输入备注！')">请输入备注！</textarea>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<table id="tb_button" style="width: 100%;" cellspacing="0" cellpadding="0">
						<tr>
							<td style="height: 15px;">&nbsp;</td>
						</tr>
						<tr>
							<td align="right" valign="middle" style="border: 0px;" height="30">
								<input type="button" onfocus="this.blur()" onclick="addPatientFav('', 'patientFolderForm');" align="middle"
									style="border: 0px; BACKGROUND-IMAGE: url(../images/qd.jpg); width: 51px; height: 23px; cursor: pointer;" />
								<input type="button" onfocus="this.blur()" onclick="closeDialog()"
									style="border: 0px; BACKGROUND-IMAGE: url(../images/qx.jpg); width: 51px; height: 23px; margin-right: 10px; cursor: pointer;" align="middle" />
							</td>
						</tr>
						<tr>
							<td style="height: 10px;" />
						</tr>
					</table>
				</form>
			</div>
		</div>
</body>
</html>