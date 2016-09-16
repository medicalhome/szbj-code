<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
	$(function()
	{
		$("#loadFolder").find("input").removeAttr("onfocus");
		setDetailElement($("#loadFolder").find("input"));
		$("input[name='folders']").last().attr("class","tiTab");
		var loadDiv = $("#loadpatientListView_fav");
		if(loadDiv.hasClass("tiTab"))
		{
			loadDiv.removeAttr("tabindex");
			loadDiv.removeAttr("hidefocus");
			loadDiv.removeClass("tiTab");
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
<body>
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
</body>
</html>