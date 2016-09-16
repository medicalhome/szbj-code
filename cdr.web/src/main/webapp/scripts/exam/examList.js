/**
 * 如果报告为已召回仅可查看医嘱跟踪信息
 */
function showExamDetailDialog(withdrawFlag,examReportSn,patientSn,itemClass,examinationRegion,examinationItem,itemSn,orderSn,tr,type,examinationItemName,examinationMethodName){
	/*$Author :chang_xuewen
	 * $Date : 2013/07/03 16:20
	 * $[BUG]0033461 MODIFY BEGIN*/
	if(orderSn == ""){//当检查仅有报告没有医嘱时,使用reportSn_itemSn唯一确定
		$(tr).attr("id",examReportSn+itemSn);		
	}
	if(type==1){
		//type 为1，是从住院视图调用，为2是从综合视图调用,3是从摘要视图调用
		//showDialog('../exam/detail.html','检查详细', {examReportSn:examReportSn,patientSn:patientSn,itemClass:itemClass,examinationRegion:examinationRegion,examinationItem:examinationItem,itemSn:itemSn,orderSn:orderSn});
		justShow('../exam/detail.html',tr,{examReportSn:examReportSn,patientSn:patientSn,itemClass:itemClass,examinationRegion:examinationRegion,examinationItem:examinationItem,itemSn:itemSn,orderSn:orderSn,withdrawFlag:withdrawFlag,otherName:examinationMethodName,holdOtherName:examinationMethodName,name:examinationItemName,holdName:examinationItemName,labExamID:examReportSn+itemSn});
	}else if(type==2){
		bigShow(tr,'../exam/detail.html',{examReportSn:examReportSn,patientSn:patientSn,itemClass:itemClass,examinationRegion:examinationRegion,examinationItem:examinationItem,itemSn:itemSn,orderSn:orderSn,withdrawFlag:withdrawFlag,otherName:examinationMethodName,holdOtherName:examinationMethodName,name:examinationItemName,holdName:examinationItemName,labExamID:examReportSn+itemSn});
	}else if(type==3){
		showBigPortlet(tr,'../exam/detail.html',{examReportSn:examReportSn,patientSn:patientSn,itemClass:itemClass,examinationRegion:examinationRegion,examinationItem:examinationItem,itemSn:itemSn,orderSn:orderSn,withdrawFlag:withdrawFlag,otherName:examinationMethodName,holdOtherName:examinationMethodName,name:examinationItemName,holdName:examinationItemName,labExamID:examReportSn+itemSn});
	}
	/* $[BUG]0033461 MODIFY END*/
}
//检验所有
function showEmrExamDetailDialog(withdrawFlag,examReportSn,patientSn,itemClass,examinationRegion,examinationItem,itemSn,orderSn,tr,type,examinationItemName,examinationMethodName){
	portalBigShow(tr,'../portalExam/detail.html',{examReportSn:examReportSn,patientSn:patientSn,itemClass:itemClass,examinationRegion:examinationRegion,examinationItem:examinationItem,itemSn:itemSn,orderSn:orderSn,withdrawFlag:withdrawFlag,otherName:examinationMethodName,holdOtherName:examinationMethodName,name:examinationItemName,holdName:examinationItemName,labExamID:examReportSn+itemSn});
}


/**
 * 如果报告为已召回则禁止查看详细信息(时间轴视图或住院视图)
 */
function showExamDetailDialog2(withdrawFlag,examReportSn,patientSn,itemClass,examinationRegion,examinationItem,itemSn,orderSn){
	showDialog('../exam/detail.html','检查详细', {examReportSn:examReportSn,patientSn:patientSn,itemClass:itemClass,examinationRegion:examinationRegion,examinationItem:examinationItem,itemSn:itemSn,orderSn:orderSn,withdrawFlag:withdrawFlag}, 700, 500, '#ajaxDialogDetail');
}


function showExamDetailDialogTabs(withdrawFlag, examReportSn, patientSn, itemSn,
		orderSn, type, visitSn, tabsFlag, gotoType, width, name, inpatientDate, special) {
	loadCommonPanel('检查详细', {
		url : '../exam/detail.html',
		examReportSn : examReportSn,
		patientSn : patientSn,
		itemSn : itemSn,
		orderSn : orderSn,
		trID : orderSn+'_'+examReportSn+'_'+itemSn,
		type : type,
		visitSn : visitSn,
		tabsFlag : tabsFlag,
		gotoType : gotoType,
		width : width,
		name : name,
		inpatientDate : inpatientDate,
		special : special,
		withdrawFlag:withdrawFlag
	}, 800, 565);
}
/**
 * emr清除
 */
function emrClear(){
	$("#examinationTypes").val("-1");
	$("#examinationItem").val("");
	$("#requestDateFrom").val("");
	$("#requestDateTo").val("");
}
/**
 * 通过Ajax的方式提交给定名称的表单
 */
function search(url, formName, id)
{
	var today = new Date().format("yyyy-MM-dd");
    var examinationDateFrom= $("#examinationDateFrom").val();
    var examinationDateTo=$("#examinationDateTo").val();
    if(examinationDateFrom!=""&&examinationDateFrom>today){
    	showMsg("提示","检查日期 开始时间不能>当前日期！");
    	return;
    }
    if(examinationDateTo!=""&&examinationDateFrom>examinationDateTo)
    {
    	showMsg("提示","检查日期 开始时间不能>结束时间！");
    	return;
    }
    
    var requestDateFrom= $("#requestDateFrom").val();
    var requestDateTo=$("#requestDateTo").val();
    if(requestDateFrom!=""&&requestDateFrom>today){
    	showMsg("提示","申请日期 开始时间不能>当前时间！");
    	return;
    }
    if(requestDateTo!=""&&requestDateFrom>requestDateTo)
    {
    	showMsg("提示","申请日期 开始时间不能>结束时间！");
    	return;
    }
    var reportDateFrom= $("#reportDateFrom").val();
    var reportDateTo=$("#reportDateTo").val();
    if(reportDateFrom!=""&&reportDateFrom>today){
    	showMsg("提示","报告日期 开始时间不能>当前时间！");
    	return;
    }
    if(reportDateTo!=""&&reportDateFrom>reportDateTo)
    {
    	showMsg("提示","检查日期 开始时间不能>当前日期！");
    	return;
    }
	
	var form = $("[name=" + formName + "]");
	if(form == null|| form == '')
	{
		showMsg("提示","检索条件表单对象不存在！");
		return;
	}
	//$Author:chang_xuewen
    //$Date:2013/11/25 09:25
    //$[BUG]0039831 ADD BEGIN
    // 增加检索前的时间格式验证
    if(isDateStringCheck(examinationDateFrom) == "true" || isDateStringCheck(examinationDateTo) == "true"
    	|| isDateStringCheck(reportDateFrom) == "true" || isDateStringCheck(reportDateTo) == "true")
    {
    	return;
    }
    //$[BUG]0039831 ADD END
	//console.log(form.serializeArray());
	parent.loadPanel(form.attr("action"), form.serializeObject(), id);
}

/**
 * 打开影像中心链接页面
 */
function showImageCenter(imageCenterUrl,imageIndexNo){
	stopBubble(event);
	if(imageIndexNo==null||imageIndexNo==''){
		showMsg("提示","此条检查记录没有关联的影像信息！");
		return false;
	}
	window.open(imageCenterUrl+imageIndexNo,"影像中心","scrollbars=yes,resizable=yes,status=yes,menubar=yes");
}

// 打开签章文档（签章服务器请求地址，签章ID）
function previewDoc(signatureUrl,signatureId)
{
	stopBubble(event);
	if(signatureId==null||signatureId==''){
		showMsg("提示","此条记录没有关联的签章报告！");
		return false;
	}
	try{
		//初始化插件
		var obj = new ActiveXObject("DocSvrClient.CEBXClient");
	}catch(e){
		showMsg("提示","未安装签章客户端软件！");
		return false;
	}
	showLoadingScreen({
		loadingScreenId: "loadingScreen", 
		loadingMessageId: "loadingMessage",
		modal: true
	});
	//请求服务器端文档
	lRet = obj.previewDoc(signatureUrl,signatureId); 
	closeLoadingScreen({
		loadingScreenId: "loadingScreen", 
		loadingMessageId: "loadingMessage"
	});
	if(lRet!=0){
		showMsg("提示","签章号不存在对应的签章文档！");
	}
}

	