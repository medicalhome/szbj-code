

function closeDialog() {
    $("#ajaxDialog").remove();
    $("#dialog").append("<div id='ajaxDialog' style='display:none'></div>");
}

/**
 * 保存角色
 * */
function saveRole(){
	
	var roleName = (document.getElementsByName("roleName"))[0].value;
	var occupationType = (document.getElementsByName("occupationType"))[0].value;
	var memo = (document.getElementsByName("memo"))[0].value;
	
	if(roleName == null || roleName == ""){
		showMsg("", "角色名称不能为空");
	} else if(occupationType < 0){
		showMsg("", "请选择职业类别");
//		alert(occupationType);
	} else if(memo.length > 200){
		showMsg("", "角色描述最大长度为200，当前长度为" + memo.length);
	} else{
		confirmMsg1("新建角色", "是否保存新建角色？", function(response) {
			if (response == true) 
			{

				$.post("../role/saveNewRole.html", {"roleName" : roleName, "occupationType" : occupationType, "memo" : memo}, function(data){
					closeDialog();
					loadPanel('../role/loadRoleList.html', {}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});

				});
				
			}
		});
	}
}

function userTop(){
//	var top = document.getElementById("userTop");
//	var users = document.getElementsByName("userIds");
//	for(var i = 0; i < users.length; i++){
//		users[i].checked = userTop.checked;
//	}
	$("#userTop").click(function(){
		if($(this).attr("checked")){
			$("input[name='userIds']").attr("checked",true);
		} else {
			$("input[name='userIds']").attr("checked",false);
		}
	    
	});

	
}

function deleteRole(roleId){
	confirmMsg1("删除角色", "是否删除该角色？", function(response) {
		if (response ==true) 
		{
			$.post("../role/deleteRole.html", {"roleId" : roleId}, function(data){
				closeDialog();
				loadPanel('../role/loadRoleList.html', {}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});

			});
		}
	});
}

function save(){
	
	var roleName = (document.getElementsByName("roleName"))[0].value;
	var occupationType = (document.getElementsByName("occupationType"))[0].value;
	var memo = (document.getElementsByName("memo"))[0].value;
	var roleId = (document.getElementsByName("searchRoleId"))[0].value;
	
	if(roleName == null || roleName == ""){
		showMsg("", "角色名称不能为空");
	} else if(occupationType < 0){
		showMsg("", "请选择职业类别");
	} else if(memo.length > 200){
		showMsg("", "角色描述最大长度为200，当前长度为" + memo.length);
	} else{
		confirmMsg1("保存角色信息", "是否保存角色信息？", function(response) {
			if (response ==true) 
			{
				$.post("../role/saveEditedRole.html", {"roleId" : roleId, "roleName" : roleName, "occupationType" : occupationType, "memo" : memo}, 
					function(data){
						closeDialog();
						loadPanel('../role/loadRoleList.html', {}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});
				});
				
			}
		});	
	}

}

function deleteUsers(roleId){
	
	confirmMsg1("删除人员", "是否删除所选的人员？", function(response) {
		if (response ==true) 
		{
			var ids = "";
			var userIds = document.getElementsByName("userIds");
			for(var i = 0; i < userIds.length; i++){
				if(userIds[i].checked){
					ids += userIds[i].value + ',';
				}
			}
			$.post("../role/deleteRoleUsers.html", {"deleteUserIds" : ids, "roleId" : roleId}, function(data){
				loadPanel('../role/editRoleUsers.html', {'searchRoleId': roleId}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});
			});
//			closeDialog();	
//			location.reload();
		}
	});
}

function addUsers(roleId){
	confirmMsg1("添加人员", "是否添加所选的人员？", function(response) {
		if (response ==true) 
		{
			var ids = "";
			var userIds = document.getElementsByName("addUserIds");
			for(var i = 0; i < userIds.length; i++){
				if(userIds[i].checked){
					ids += userIds[i].value + ',';
				}
			}
//			$.post("../role/addUsers.html", {"addUserIds" : ids, "roleId" : roleId});
//			loadPanel('../role/editRoleUsers.html', {'searchRoleId': roleId}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});
			
			$.post("../role/addUsers.html", {"addUserIds" : ids, "roleId" : roleId}, function(data){
				closeDialog();
				loadPanel('../role/editRoleUsers.html', {'searchRoleId': roleId}, '#dynamicContent',{continueGoto:'continueGoto_documentation',isAddHistory:'1', isMainViewMenuPart:'1'});
			} );
			
		}
	});
}

function saveAuth(roleName, roleId){
	confirmMsg1("保存角色", "确认保存角色" + roleName + "的权限？", function(response) {
		if (response ==true) 
		{
			var authIds = "";
			var patientRangeViews = document.getElementsByName("patientRangeViews");
			var patientInfoViews = document.getElementsByName("patientInfoViews");
			var clinicalInfoViews = document.getElementsByName("clinicalInfoViews");
			for(var i = 0; i < patientRangeViews.length; i++) {
				if(patientRangeViews[i].checked){
					authIds += patientRangeViews[i].value + ",";
				}
			}
			for(var i = 0; i < patientInfoViews.length; i++) {
				if(patientInfoViews[i].checked){
					authIds += patientInfoViews[i].value + ",";
				}
			}
			for(var i = 0; i < clinicalInfoViews.length; i++) {
				if(clinicalInfoViews[i].checked){
					authIds += clinicalInfoViews[i].value + ",";
				}
			}	
			$.post("../role/saveRoleAuth.html", {"authIds" : authIds, "roleId" : roleId}, function(data){
				closeDialog();
			});
				

		}
	});
}
