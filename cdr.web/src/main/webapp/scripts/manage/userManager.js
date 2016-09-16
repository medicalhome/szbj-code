function singleResetPW(val)
{
		confirmMsg1("重置密码提示", "是否确认对账户{"+val+"}进行密码重置操作？", function(response) {
			if (response ==true) 
			{
				$.post("../iam/resetPW.html", { "userIds": val});
//				alert("重置完毕");
				showMsg("", "重置完毕");
			}
		});
}

function multiResetPw()
{
	var userIds="";
	var userViews=document.getElementsByName("userViews");
	for(var i=0; i<userViews.length; i++){ 
		if(userViews[i].checked && userViews[i].value!="") userIds=userIds+userViews[i].value+','; //如果选中，将value添加到变量authIds中 
		}
	if(userIds==""){
		showMsg("提示", "请选择帐户!");
	}else{
		confirmMsg1("重置密码提示", "是否确认对账户进行密码重置操作？", function(response) {
			if (response ==true) 
			{
				$.post("../iam/resetPW.html", { "userIds": userIds});
//				alert("重置完毕");
				showMsg("", "重置完毕");
			}
			
		});
	}
}


function closeDialog() {
    $("#ajaxDialog").remove();
    $("#dialog").append("<div id='ajaxDialog' style='display:none'></div>");
}

function saveAuth(val){
	var authIds="";
	var patientRangeViews=document.getElementsByName("patientRangeViews");	
	for(var i=0; i<patientRangeViews.length; i++){ 
		if(patientRangeViews[i].checked) authIds+=patientRangeViews[i].value+','; //如果选中，将value添加到变量authIds中 
		} 
	var patientInfoViews=document.getElementsByName("patientInfoViews");	
	for(var i=0; i<patientInfoViews.length; i++){ 
		if(patientInfoViews[i].checked) authIds+=patientInfoViews[i].value+','; //如果选中，将value添加到变量authIds中 
		} 
	var clinicalInfoViews=document.getElementsByName("clinicalInfoViews");	
	for(var i=0; i<clinicalInfoViews.length; i++){ 
		if(clinicalInfoViews[i].checked) authIds+=clinicalInfoViews[i].value+','; //如果选中，将value添加到变量authIds中 
		} 

	confirmMsg1("个人权限设定", "是否确认对账户{"+val+"}进行权限设定操作？", function(response) {
		if (response ==true) 
		{
			$.post("../iam/saveAuth.html", {"userNo": val,"authIds":authIds}, function(){
				closeDialog();
//				alert("权限设定成功");
				showMsg("", "权限设定成功");
			});

		}
	});
	
//	var $form = $("form[name='setPersonAuthForm']");
//
//	$form.attr("action", "../iam/saveAuth.html?userNo="+val);
//
//	confirmMsg1("保存权限提示","是否确认保存设置?", function(response) {
//		if (response ==true) 
//		{
//			$form.submit();
//		}
//	});
}


function userViews_onclick(){
	if($(this).attr("id") == "userViews_ctl")
	{
		var checked = $("#userViews_ctl").is(":checked");

		$("input[name='userViews']").each(function(){
			if($(this).attr("id") != "userViews_ctl")
			{
				if(checked)
					$(this).attr("checked", true);
				else 
					$(this).attr("checked", false);
			}
		});
	}
}
