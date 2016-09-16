$(function(){
	//添加页面文本框的回车检索
	$("#loginForm input").keyup(function(event){
		if (event.keyCode==13) 
	    { 
			loginSubmit();
	    } 
	});
	
	// 初始光标移动至用户名输入框
	moveCursor();
});

function promptMessage(inId,outId)
{
	var inVal = $(inId).val();
	
	if(inVal == null || inVal == "")
	{
		//$(outId).show();
		return false;
	}
	return true;
}

function cancelPrompt(inId,outId)
{
	var inVal = $(inId).val();
	
	if(inVal != null && inVal != "")
	{
		if($(outId).size() > 0)
		{
			$(outId).remove();
		}
	}
}

function loginSubmit()
{
	var userFlag = promptMessage('#j_username','#p_username');
	var passFlag = promptMessage('#j_password','#p_password');
	
	// 添加记录登陆信息操作
	if(userFlag && passFlag)
	{
		var username = $("#j_username").val();
		var pword = $("#j_password").val();
		var saveInfoFlag = $("#saveLoginInfoFlag").attr("checked") == undefined ? "0" : "1";
		
		username = username + "@" + encode(pword) + "@" + saveInfoFlag;
		
		$('<form style="display:none;" name="loginRealForm" id="loginRealForm" ' + 
				'action="../cdr/j_spring_security_check" method="post"> ' +
				'<input name="j_username" id="j_username" type="text" value="'+username+'"/> ' +
				'<input name="j_password" id="j_password" type="text" value="'+pword+'"/></form>').appendTo("body");
		
		setTimeout("$('#loginRealForm').submit()",0 );
	}
	else
	{
		// 初始光标移动至用户名输入框
		moveCursor();
	}
}

function loginClear(userId,passId)
{
	$(userId).val('');
	$(passId).val('');
}

//$Author :jin_peng
// $Date : 2012/10/24 16:37$
// [BUG]0010714 ADD BEGIN
function moveCursor()
{
	var inVal = $("#j_username").val();
	
	// 未填写内容光标移动至此
	if(inVal == null || inVal == "")
	{
		$("#j_username").focus();
	}
	else
	{
		$("#j_password").focus();
	}
}
//[BUG]0010714 ADD END

//$Author :jin_peng
//$Date : 2012/10/31 17:38$
//[BUG]0010836 ADD BEGIN
//是否使用键盘刷新或关闭页面标识
var isKeyUp = false;

// 键盘触发关闭刷新浏览器
function keyClosed(exitKindsFlag)
{
	// 当用户使用键盘关闭浏览器时情况
	if((window.event.altKey && window.event.keyCode == 115))
	{
		// 设置键盘关闭浏览器标识为是
		isKeyUp = true;
		
		// 退出cdr系统并记录用户退出日志
		closedExitSystem(exitKindsFlag);
	}
	// 当用户使用键盘刷新浏览器情况
	else if(window.event.keyCode == 116)
	{
		// 设置键盘关闭浏览器标识为是
		isKeyUp = true;
	}
}

// 在页面被卸载前触发浏览器关闭或刷新
function buttonClosed(exitKindsFlag)
{
	// 当用户点击关闭按钮关闭浏览器情况
	if(((window.event.clientX > document.body.clientWidth - 50) && (window.event.clientY < -93)) && isKeyUp == false)
	{
		// 退出cdr系统并记录用户退出日志
		closedExitSystem(exitKindsFlag);
	}
	else
	{
		// 设置键盘关闭浏览器标识为否
		isKeyUp = false;
	}
}

// 关闭浏览器情况退出系统
function closedExitSystem(exitKindsFlag)
{
	// 记录用户退出日志
	window.location.href = "../cdr/j_spring_security_logout?exitKindsName=" + exitKindsFlag;
}
//[BUG]0010836 ADD END

//$Author: jin_peng
// $Date : 2013/09/12 15:37
// $[BUG]0036478 ADD BEGIN
// 提示信息操作
function confirmMsg(title, msg, callback, widthpara) 
{
	var wid = 330;
	
	if(widthpara != undefined)
	{
		wid = widthpara
	}
	
    $("#confirmMessage").html("<div id='msgBody' style='height:60px;line-height:60px;text-align:center;'>" + msg + "</div>").dialog(
    {
        title: title,
        autoOpen: true,
        modal: true,
        width: wid,
        bgiframe: true,
        buttons: 
        {
            "确认": function() 
            {
            	$("#msgBody").html("正在加载密码修改页面请稍后。。。");
            	
            	$(".ui-button").attr("disabled",true);
            	
                callback.call();
                
                $(this).dialog("close");
            }
        }
    });
}

// 获取密码
function obtainPassword(obtainPasswordUrl)
{
	var h = (window.screen.availHeight - 55 - 670) / 2;

    var w = (window.screen.availWidth - 5 - 1024) / 2;

    var test = window.open(obtainPasswordUrl, null, "height=670,width=1024,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,left=" + w + ",top=" + h);
}

// 页面初始获取设置用户名密码
function obtainInitInfo(initCookieName, error)
{
	if(error != null && error != "")
	{
		return;
	}
	
	var initCookieValue = getCookieValue(initCookieName);
	
	if(initCookieValue != null)
	{
		var initContent = initCookieValue.split(",");
		$("#j_username").val(initContent[0]);
		$("#j_password").val(decode(initContent[1]));
		$("#saveLoginInfoFlag").attr("checked","checked");
	}
}

//通过用户名获取密码
function obtainCookieInfoByName(cookieName)
{
	if(cookieName == null || cookieName == "")
	{
		return;
	}
	
	var cookieValue = getCookieValue(cookieName);
	
	if(cookieValue != null)
	{
		var cookieContent = cookieValue.split(",");
		
		$("#j_password").val(decode(cookieContent[1]));
		$("#saveLoginInfoFlag").attr("checked","checked");
		cancelPrompt('#j_password','.j_passwordformError');
	}
}

// 获取客户端cookie值
function getCookieValue(cookieName)
{
	var cookie = document.cookie;
	var prefix = cookieName + "=";
	var startPos = cookie.indexOf(prefix);
	
	if(startPos == -1)
	{
		return null;
	}
	
	var endPos = cookie.indexOf(";", startPos + prefix.length);
	
	if(endPos == -1)
	{
		endPos = cookie.length;
	}
	
	return cookie.substring(startPos + prefix.length + 1, endPos - 1);
}

// 简单加密内容
function encode(value) 
{
	if(value == null || value == undefined)
	{
		return value;
	}
	
	var numOut = "";
	var strIn = escape(value);
	
	for(i = 0; i < strIn.length; i++) 
	{
		numOut += strIn.charCodeAt(i) - 23;
	}
	
	return numOut;
}

// 解密内容
function decode(value) 
{
	var strOut = "";
	var numOut = value;
	
	for(i = 0; i < numOut.length; i += 2) 
	{
		numIn = parseInt(numOut.substr(i,[2])) + 23;
		numIn = unescape('%' + numIn.toString(16));
		strOut += numIn;
	}
	
	return unescape(strOut);
}

function isIE6()
{
	if($.browser.version == '6.0')
	{
		return true;
	}
	
	return false;
}

//$[BUG]0036478 ADD END