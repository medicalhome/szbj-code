// $Author :jin_peng
// $Date : 2012/09/21 14:01$
// [BUG]0009561 ADD BEGIN
/**
 * 针对集成登陆提交
 * */
function IntegratedSubmit(userId,pword,patientId,domainId,deptCodeStr,existDeptCodeFlag,hidePatientFlag)
{
	// 用户ID非空校验
	if(userId == null || userId == "")
	{
		showMsg("提示","用户ID不能为空！",undefined,true,"closeExplore()");
		
		return;
	}
	
	// $Author :jin_peng
    // $Date : 2012/10/29 14:50$
    // [BUG]0010795 DELETE BEGIN
	// 患者ID非空校验
	/*if(patientId == null || patientId == "")
	{
		alert("患者ID不能为空");
		return;
	}*/
	// [BUG]0010795 DELETE END
	
	// 域ID非空校验
	if(domainId == null || domainId == "")
	{
		showMsg("提示","域ID不能为空",undefined,true,"closeExplore()");
		
		return;
	}
	
	var deptCode = "";
	
	if(existDeptCodeFlag == "true")
	{
		
		if(deptCodeStr == "")
		{
			deptCode = "; ;";
		}
		else
		{
			deptCode = ";" + deptCodeStr;
		}
	}
	
	// 将用户ID，患者ID，域ID拼接字符串形式传入校验
	var username = userId + ";" + patientId + ";" + domainId + ";" + hidePatientFlag + ";" + existDeptCodeFlag + deptCode;
	
	// 制作登陆表单
	$('<form style="display:none;" name="loginForm" id="loginForm" ' + 
			'action="../cdr/j_spring_security_check" method="post"> ' +
			'<input name="j_username" id="j_username" type="text" value="'+username+'"/> ' +
			'<input name="j_password" id="j_password" type="text" value="'+pword+'"/></form>').appendTo("body");

	// 登陆表单提交
	setTimeout("$('#loginForm').submit()",0 );
}
//$Author :chang_xuewen
// $Date : 2014/01/27 10:00$
// [BUG]0041864 MODIFY BEGIN
function LabReportIntegratedSubmit(userId,pword,reportNo,itemCode,orgCode,reportTypeCode)
{
	// 用户ID非空校验
	if(userId == null || userId == "")
	{
		showMsg("提示","用户ID不能为空！",undefined,true,"closeExplore()");
		
		return;
	}
	else if(reportNo == null || reportNo == "")
	{
		showMsg("提示","检验报告ID不能为空！",undefined,true,"closeExplore()");
		
		return;
	}else if(orgCode == null || orgCode == "")
	{
		showMsg("提示","医疗机构编码不能为空！",undefined,true,"closeExplore()");
		
		return;
	}
	
	// 将用户ID，报告ID，检验大项ID拼接字符串形式传入校验
	var username = "userId=" + userId + "&reportNo=" + reportNo + "&itemCode=" + itemCode + "&orgCode=" + orgCode + "&reportTypeCode=" + reportTypeCode;
	
	// 制作登陆表单
	$('<form style="display:none;" name="loginForm" id="loginForm" ' + 
			'action="../cdr/j_spring_security_check" method="post"> ' +
			'<input name="j_username" id="j_username" type="text" value="'+username+'"/> ' +
			'<input name="j_password" id="j_password" type="text" value="'+pword+'"/></form>').appendTo("body");

	// 登陆表单提交
	setTimeout("$('#loginForm').submit()",0 );
}

function businessSnIntegratedSubmit(userId,pword,businessSn)
{
	// 用户ID非空校验
	if(userId == null || userId == "")
	{
		showMsg("提示","用户ID不能为空！",undefined,true,"closeExplore()");
		
		return;
	}
	else if(businessSn == null || businessSn == "")
	{
		showMsg("提示","业务主键标识不能为空！",undefined,true,"closeExplore()");
		
		return;
	}
	
	// 将用户ID，报告ID，检验大项ID拼接字符串形式传入校验
	var username = "userId=" + userId + "-businessSn=" + businessSn;
	
	// 制作登陆表单
	$('<form style="display:none;" name="loginForm" id="loginForm" ' + 
			'action="../cdr/j_spring_security_check" method="post"> ' +
			'<input name="j_username" id="j_username" type="text" value="'+username+'"/> ' +
			'<input name="j_password" id="j_password" type="text" value="'+pword+'"/></form>').appendTo("body");
	
	// 登陆表单提交
	setTimeout("$('#loginForm').submit()",0 );
}

/**
 * 针对住院视图集成登陆提交
 * */
function InpatientIntegratedSubmit(userId,pword,patientId,domainId,inpatientType,sceneType)
{
// 用户ID非空校验
/*	if(sceneType == null || sceneType == "")
	{
		showMsg("提示","场景类型不能为空!!!",undefined,true,"closeExplore()");
		
		return;
	}*/
	// 用户ID非空校验
	if(userId == null || userId == "")
	{
		showMsg("提示","用户ID不能为空！",undefined,true,"closeExplore()");
		
		return;
	}
	
	// $Author :jin_peng
    // $Date : 2012/10/29 14:50$
    // [BUG]0010795 DELETE BEGIN
	// 患者ID非空校验
	if(patientId == null || patientId == "")
	{
		showMsg("提示","患者ID不能为空！",undefined,true,"closeExplore()");
		return;
	}
	// [BUG]0010795 DELETE END
	
	// 域ID非空校验
	if(domainId == null || domainId == "")
	{
		showMsg("提示","域ID不能为空",undefined,true,"closeExplore()");
		
		return;
	}
/*	var deptCode = "";
	
	if(paramsSize == paramsCount)
	{
		
		if(deptCodeStr == "")
		{
			deptCode = "; ;";
		}
		else
		{
			deptCode = ";" + deptCodeStr;
		}
	}
	*/
	// 住院类型非空校验
	if(inpatientType == null || inpatientType == "")
	{
		showMsg("提示","住院类型不能为空！",undefined,true,"closeExplore()");
		
		return;
	}	
	// 将用户ID，患者ID，域ID,住院类型拼接字符串形式传入校验
		var username = "userId="+userId + "#patientId=" + patientId + "#domainId=" + domainId+"#inpatientType=" + inpatientType+"#sceneType=" + sceneType;
	
	// 制作登陆表单
	$('<form style="display:none;" name="loginForm" id="loginForm" ' + 
			'action="../cdr/j_spring_security_check" method="post"> ' +
			'<input name="j_username" id="j_username" type="text" value="'+username+'"/> ' +
			'<input name="j_password" id="j_password" type="text" value="'+pword+'"/></form>').appendTo("body");

	// 登陆表单提交
	setTimeout("$('#loginForm').submit()",0 );
}
//[BUG]0041864 MODIFY END

/**
 * 针对输液统计，EMR3.0集成登陆提交wang.meng
 * */
function PortalIntegratedSubmit(userId,pword,patientId,domainId,systemId,viewId,visitTimes)
{
	// 用户ID非空校验
	if(userId == null || userId == "")
	{
		showMsg("提示","用户ID不能为空！",undefined,true,"closeExplore()");
		
		return;
	}
	
	// 患者ID非空校验
	if(patientId == null || patientId == "")
	{
		showMsg("提示","患者ID不能为空！",undefined,true,"closeExplore()");
		return;
	}
	// [BUG]0010795 DELETE END
	
	// 域ID非空校验
	if(domainId == null || domainId == "")
	{
		showMsg("提示","域ID不能为空",undefined,true,"closeExplore()");
		
		return;
	}
	// 系统ID非空校验
	if(systemId == null || systemId == "")
	{
		showMsg("提示","系统ID不能为空",undefined,true,"closeExplore()");
		
		return;
	}
	// 视图ID非空校验
	if(viewId == null || viewId == "")
	{
		showMsg("提示","查看视图ID不能为空",undefined,true,"closeExplore()");
		
		return;
	}
	// 将用户ID，患者ID，域ID,住院类型拼接字符串形式传入校验
		var username = "userId="+userId + "%patientId=" + patientId + "%domainId=" + domainId +"%systemId="+ systemId +"%viewId="+ viewId+"%visitTimes="+visitTimes;
	// 制作登陆表单
	$('<form style="display:none;" name="loginForm" id="loginForm" ' + 
			'action="../cdr/j_spring_security_check" method="post"> ' +
			'<input name="j_username" id="j_username" type="text" value="'+username+'"/> ' +
			'<input name="j_password" id="j_password" type="text" value="'+pword+'"/></form>').appendTo("body");

	// 登陆表单提交
	setTimeout("$('#loginForm').submit()",0 );
}
/**
 * 登陆集成成功跳转 
 */
//$Author :chang_xuewen
// $Date : 2014/01/27 10:00$
// [BUG]0041864 MODIFY BEGIN
function successIntegrated(patientSn,patientId,patientDomain,labReportLid,labItemCode,orgCode,inpatientType,sceneType,reportTypeCode,businessSn,systemId,viewId,visitTimes)
{
	if(labReportLid != null && labReportLid != "")
	{
		window.location.href='../lab/labReportForHIS.html?labReportLid='+labReportLid+'&labItemCode='+labItemCode+'&orgCode='+orgCode+'&reportTypeCode='+reportTypeCode;
	}
	else if(businessSn != null && businessSn != "")
	{
		window.location.href='../doc/detail_' + businessSn + '.html?integratedFlag=true';
	}
	//wang.meng
	else if(viewId!= null && viewId!= "")
	{
		if(patientSn == null || patientSn == "")
		{
			var patientMessage = "患者ID：" + patientId + "; 域ID：" + patientDomain + " 对应的患者信息不存在！";
			
			showMsg("提示",patientMessage,"auto",true,"closeExplore()");
			
			return;
		}
		window.location.href='../cdr/portal.html?ps='+patientSn+'&vi='+viewId+'&pi='+patientId+'&pd='+patientDomain+'&vt='+visitTimes;
	}
	else
	{		
		// [BUG]0041864 MODIFY END
		//$Author :jin_peng
		// $Date : 2013/02/22 14:17$
		// [BUG]0014055 MODIFY BEGIN
		// 患者是否存在，不存在则提示相应信息，如果存在跳转到普通视图页面
		if(patientSn == null || patientSn == "")
		{
			var patientMessage = "患者ID：" + patientId + "; 域ID：" + patientDomain + " 对应的患者信息不存在！";
			
			showMsg("提示",patientMessage,"auto",true,"closeExplore()");
			
			return;
		}
		//alert('inpatientType:'+inpatientType);
		// [BUG]0014055 MODIFY END
		
		// 患者存在时跳转到普通视图页面
		window.location.href='../visit/'+patientSn+'.html';
	}
}
// [BUG]0009561 ADD END

//$Author :jin_peng
// $Date : 2013/04/25 13:49$
// [BUG]0031753 ADD BEGIN
function closeExplore()
{
	window.opener = null;
	
	window.close();
}

//[BUG]0031753 ADD END

//$Author :jin_peng
//$Date : 2012/10/31 17:38$
//[BUG]0010836 ADD BEGIN
//是否使用键盘刷新或关闭页面标识
var isKeyUp = false;

//键盘触发关闭刷新浏览器
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

//在页面被卸载前触发浏览器关闭或刷新
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

//关闭浏览器情况退出系统
function closedExitSystem(exitKindsFlag)
{
	// 记录用户退出日志
	window.location.href = "../cdr/j_spring_security_logout?exitKindsName=" + exitKindsFlag;
}
//[BUG]0010836 ADD END