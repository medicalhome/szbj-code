/**
 * 阻止事件冒泡
 * @param e
 */
function stopBubble(event) {
    //如果提供了事件对象，则这是一个非IE浏览器
    if (event && event.stopPropagation)
    //因此它支持W3C的stopPropagation()方法
    event.stopPropagation();
    else
    //否则，我们需要使用IE的方式来取消事件冒泡
    event.cancelBubble = true;
}

/**
 * 阻止浏览器的默认行为
 */
function stopDefault(event) {
    //阻止默认浏览器动作(W3C) 
    if (event && event.preventDefault) event.preventDefault();
    //IE中阻止函数器默认动作的方式 
    else event.returnValue = false;
    return false;
}

/**
 * 根据给定的URL、标题和数据显示对话框
 */
function showDocDialog(openUrl, postData, width, height) {
    showDialog(openUrl, "病历文书CDA", postData, width, height);
}
/**
 * emr清除
 */
function emrClear(){
	$("#documentName").val("");
	$("#submitTimeFrom").val("");
	$("#submitTimeTo").val("");
}
/**
 * 通过Ajax的方式提交给定名称的表单
 */
function search(url, formName, id, isAddHistory) {
	var today = new Date().format("yyyy-MM-dd");
    var submitTimeFrom = $("#submitTimeFrom").val();
    var submitTimeTo = $("#submitTimeTo").val();
    if(submitTimeFrom!=""&&submitTimeFrom>today){
    	showMsg("提示","报告日期 开始时间不能>当前时间！");
    	return;
    }
    if (submitTimeTo != "" && submitTimeFrom > submitTimeTo) {
        showMsg("提示", "文档提交时间  开始时间不能>结束时间！");
        return;
    }

    var form = $("[name=" + formName + "]");
    if (form == null || form == '') {
        showMsg("提示", "检索条件表单对象不存在！");
        return;
    }
    //$Author:chang_xuewen
    //$Date:2013/11/25 09:25
    //$[BUG]0039831 ADD BEGIN
    // 增加检索前的时间格式验证
    if(isDateStringCheck(submitTimeFrom) == "true" || isDateStringCheck(submitTimeTo) == "true")
    {
    	return;
    }
    //$[BUG]0039831 ADD END
    parent.loadPanel(form.attr("action"), form.serializeObject(), id, {isAddHistory:isAddHistory});
}

// 打开签章文档（签章服务器请求地址，签章ID）
function previewDoc(signatureUrl, signatureId) {
    stopBubble(event);
    if (signatureId == null || signatureId == '') {
        showMsg("提示", "此条记录没有关联的签章报告！");
        return false;
    }
    try {
        //初始化插件
        var obj = new ActiveXObject("DocSvrClient.CEBXClient");
    } catch(e) {
        showMsg("提示", "未安装签章客户端软件！");
        return false;
    }
    showLoadingScreen({
        loadingScreenId: "loadingScreen",
        loadingMessageId: "loadingMessage",
        modal: true
    });
    //请求服务器端文档
    lRet = obj.previewDoc(signatureUrl, signatureId);
    closeLoadingScreen({
        loadingScreenId: "loadingScreen",
        loadingMessageId: "loadingMessage"
    });
    if (lRet != 0) {
        showMsg("提示", "签章号不存在对应的签章文档！");
    }
}