function closeDialog() {
    $("#ajaxDialog").remove();
    $("#dialog").append("<div id='ajaxDialog' style='display:none'></div>");
}

function changePsw(){
	var oldPassword = document.getElementById("oldPassword").value;
	var newPassword = document.getElementById("newPassword").value;
	var newPasswordRepeat = document.getElementById("newPasswordRepeat").value;

	var wrongOldPassword = document.getElementById("wrongOldPassword");
	if(oldPassword.length == 0 ){
		wrongOldPassword.style.display = "";
		wrongOldPassword.innerHTML = "请输入原密码";
		return ;
	} else {
		wrongOldPassword.style.display = "none";
	}

	var wrongNewPassword = document.getElementById("wrongNewPassword");
	if(newPassword.length < 6){
		wrongNewPassword.style.display = "";
		wrongNewPassword.innerHTML = "密码长度过短";
		return ;
	} else {
		wrongNewPassword.style.display = "none";
	}
	
	var differentNewPsw = document.getElementById("differentNewPsw");
	if(newPassword != newPasswordRepeat){
		differentNewPsw.style.display = "";
		differentNewPsw.innerHTML = "密码不一致";
		return ;
	} else {
		differentNewPsw.style.display = "none";
	}
	
	$.post("../visit/checkPsw.html", {"oldPassword" : oldPassword}, function(data){
		var checkPassword = false; 
		if(data == "true"){
			checkPassword = true;
		}
		else {
			checkPassword = false;
		}
		
		if(checkPassword){
			$.post("../visit/changePswAfterChecked.html", {"newPassword" : newPassword}, function(data){
				if(data == "true"){
					showMsg("", "密码保存成功");
					closeDialog();
				} else {
					showMsg("", "密码保存失败，请联系管理员");
				}
			});
						
		} else {
			wrongOldPassword.style.display = "";
			wrongOldPassword.innerHTML = "原密码不正确";
		}
		
	});

}