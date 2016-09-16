/**
 * 如果报告为已召回则禁止查看详细信息
 */
function showLabDetailDialog(withdrawFlag,labReportSn,compositeItemSn,patientSn,itemCode,sourceSystemId,orderSn,tr,type,itemName){
	if(withdrawFlag!='1'||orderSn!=''){
		/*$Author :chang_xuewen
		 * $Date : 2013/07/03 16:20
		 * $[BUG]0033461 MODIFY BEGIN*/
		if(orderSn == ""){//当检查仅有报告没有医嘱时,使用reportSn_itemSn唯一确定
			$(tr).attr("id",labReportSn+compositeItemSn);		
		}	
		if(type==1){
			//type=1从门诊视图调用，=2从综合视图调用,=3从摘要视图调用
			//showDialog('../lab/detail.html','检验详细', {labReportSn:labReportSn,compositeItemSn:compositeItemSn,patientSn:patientSn,itemCode:itemCode,sourceSystemId:sourceSystemId,orderSn:orderSn}, 800,500);
			justShow('../lab/detail.html',tr,{labReportSn:labReportSn,compositeItemSn:compositeItemSn,patientSn:patientSn,itemCode:itemCode,sourceSystemId:sourceSystemId,orderSn:orderSn,withdrawFlag:withdrawFlag,otherName:'',holdOtherName:'',name:itemName,holdName:itemName,labExamID:labReportSn+compositeItemSn});
		}else if(type==2){
			bigShow(tr,'../lab/detail.html',{labReportSn:labReportSn,compositeItemSn:compositeItemSn,patientSn:patientSn,itemCode:itemCode,sourceSystemId:sourceSystemId,orderSn:orderSn,withdrawFlag:withdrawFlag,otherName:'',holdOtherName:'',name:itemName,holdName:itemName,labExamID:labReportSn+compositeItemSn});
		}
		else if(type==3){
			showBigPortlet(tr,'../lab/detail.html',{labReportSn:labReportSn,compositeItemSn:compositeItemSn,patientSn:patientSn,itemCode:itemCode,sourceSystemId:sourceSystemId,orderSn:orderSn,withdrawFlag:withdrawFlag,otherName:'',holdOtherName:'',name:itemName,holdName:itemName,labExamID:labReportSn+compositeItemSn});
		}
		/* $[BUG]0033461 MODIFY END*/		
	}
}

//检验所有
function showEmrLabDetailDialog(withdrawFlag,labReportSn,compositeItemSn,patientSn,itemCode,sourceSystemId,orderSn,tr,type,itemName){
	portalBigShow(tr,'../portalLab/detail.html',{labReportSn:labReportSn,compositeItemSn:compositeItemSn,patientSn:patientSn,itemCode:itemCode,sourceSystemId:sourceSystemId,orderSn:orderSn,withdrawFlag:withdrawFlag,otherName:'',holdOtherName:'',name:itemName,holdName:itemName,labExamID:labReportSn+compositeItemSn});
}

/**
 * 如果报告为已召回则禁止查看详细信息(时间轴视图或住院视图)
 */
function showLabDetailDialog2(withdrawFlag,labReportSn,compositeItemSn,patientSn,itemCode,sourceSystemId,orderSn){
	if(withdrawFlag!='1'||orderSn!=''){
		showDialog('../lab/detail.html','检验详细', {labReportSn:labReportSn,compositeItemSn:compositeItemSn,patientSn:patientSn,itemCode:itemCode,sourceSystemId:sourceSystemId,orderSn:orderSn,withdrawFlag:withdrawFlag}, 800,500, '#ajaxDialogDetail');		
	}
}


function showLabDetailDialogTabs(withdrawFlag, labReportSn, compositeItemSn,
		patientSn, itemCode, sourceSystemId, orderSn, visitSn, inpatientDate,
		type, gotoType, tabsFlag, width, name, special) {
	loadCommonPanel('检验详细', {
		url : '../lab/detail.html',
		labReportSn : labReportSn,
		compositeItemSn : compositeItemSn,
		patientSn : patientSn,
		itemCode : itemCode,
		sourceSystemId : sourceSystemId,
		orderSn : orderSn,
		trID : orderSn,
		visitSn : visitSn,
		inpatientDate : inpatientDate,
		type : type,
		gotoType : gotoType,
		tabsFlag : tabsFlag,
		width : width,
		name : name,
		special : special,
		withdrawFlag:withdrawFlag
	}, 800, 565);
}

/**
 * 加载生成的图表信息页面
 */
function loadResultLegend(url,data,tr)
{
	stopBubble(event);
	//点击每行项目code
	var itemCode = data.itemCode;
	//项目名称
	var itemName = data.itemName;
	//患者sn
	var patientSn = data.patientSn;
	
	tr.css("backgroundColor" , "#87CEFF");
	tr.siblings().css("backgroundColor" , "");
	$("#resultLegend").closest("tr").remove();
	tr.after("<tr><td colspan='6' style='border: 4px solid #4fb5e6;'><div id='resultLegend' class='tabs-2_right'></div></td></tr>");
	tr.after("<tr><td colspan='6' style='border: 4px solid #4fb5e6;'><div id='resultLegendLoadingScreen' class='loadingScreen'><div class='loadingMessage'>数据加载中，请稍候...</div></div></td></tr>");
	
	$("#resultLegend").closest("tr").on("click", function(event){
	    $(this).prev().css("backgroundColor" , "");
		$(this).fadeOut(900);
		$(this).remove();
	});

	//$Author :jin_peng
	//$Date : 2013/01/28 16:38$
	//[BUG]0013635 ADD BEGIN
	//解决浏览器缓存问题
	var a = Math.random();
	if(url.indexOf("?")>0){
		url = url+"&rand="+a;
	}else{
		url = url+"?rand="+a;
	}
	//[BUG]0013635 ADD END
	
	jQuery("#resultLegend").load(url,{"itemCode":itemCode,"itemName":itemName,"patientSn":patientSn},function(){
		$("#resultLegendLoadingScreen").closest("tr").remove();
		setDialogCss();
	});
}

/**
 * 实现详细页面上检验项目结果的翻页
 * @param pageno
 */
function compareJumpToPage(pageno)
{
	var form = $("[name='comparepagingform']");
	if(form == null)
	{
		showMsg("提示","检索条件表单对象不存在！");
		return;
	}
	
	var href = form.attr("action");
	href = href + "?pageNo=" + pageno;
	loadPanelPage(href, form.serializeObject(),'#resultLegend');
}
/**
 * 跳转到指定的页面
 * @param pageno
 * @param totalPageCnt
 */
function compareJumpToPageNo(pageno,totalPageCnt,isFlipSide)
{
	var form = $("[name='comparepagingform']");
	if(form == null)
	{
		showMsg("提示","检索条件表单对象不存在！");
		return;
	}
	if(pageno == null || pageno == '')
	{
		showMsg("提示","页号为空！");
		return;
	}
	var reg = new RegExp("^[0-9]*$");
	if(!reg.test(pageno)||pageno == "0")
	{
		showMsg("提示","输入内容不正确，请输入大于等于1的整数！");
		return;
	}
	if(pageno > totalPageCnt)
	{
		showMsg("提示","输入页号大于页面提供的最大值！");
		return;
	}
	var href = form.attr("action");
	
	if(isFlipSide == "1")
	{
		pageno = parseInt(totalPageCnt) - parseInt(pageno) + 1; 
	}
	
	href = href + "?pageNo=" + pageno;
	loadPanelPage(href, form.serializeObject(),'#resultLegend');
}
/**
 * emr清除
 */
function emrClear(){
	$("#itemName").val("");
	$("#requestDateFrom").val("");
	$("#requestDateTo").val("");
}
/**
 * 通过Ajax的方式提交给定名称的表单
 */
function search(url, formName, id)
{
	var today = new Date().format("yyyy-MM-dd");
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
	var form = $("[name=" + formName + "]");
	if(form == null|| form == '')
	{
		showMsg("提示","检索条件表单对象不存在！");
		return;
	}
	var testDateFrom= $("#testDateFrom").val();
  var testDateTo=$("#testDateTo").val();
  if(testDateTo!=""&&testDateFrom>testDateTo)
  {
  	showMsg("提示","检验日期 开始时间不能>结束时间！");
  	return;
  }
  
  var reportDateFrom= $("#reportDateFrom").val();
  var reportDateTo=$("#reportDateTo").val();
  if(reportDateTo!=""&&reportDateFrom>reportDateTo)
  {
  	showMsg("提示","报告日期 开始时间不能>结束时间！");
  	return;
  }
	//$Author:chang_xuewen
    //$Date:2013/11/25 09:25
    //$[BUG]0039831 ADD BEGIN
    // 增加检索前的时间格式验证
    if(isDateStringCheck(testDateFrom) == "true" || isDateStringCheck(testDateTo) == "true"
    	|| isDateStringCheck(reportDateFrom) == "true" || isDateStringCheck(reportDateTo) == "true")
    {
    	return;
    }
    //$[BUG]0039831 ADD END
	//console.log(form.serializeArray());
	parent.loadPanel(form.attr("action"), form.serializeObject(), id);
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

