var visitOptions = null;

$(function() {
	$("#normalTab").show();	
	var currentVisitSn = $("#date").val();
	var currentIndex = -1;
	var visitList = document.getElementById("date");
	
	$("#prev").attr("class","but butFront");
	$("#next").attr("class","but butBehind");
	
	// $Author:jin_peng
	// $Date : 2012/11/1 10:30
	// $[BUG]0010908 ADD BEGIN
	$("#prev").css("cursor","pointer");
	$("#next").css("cursor","pointer");
	
	// $[BUG]0010908 ADD END
	
	//visitOptions = visitList.options.cloneNode(true);
	//处理IE和google、safari浏览器兼容问题
	visitOptions = visitList;
	
	if(visitList.options.length == 0||visitList.options.length == 1)
	{
		$("#prev").attr("disabled",true);
		$("#prev").attr("class","but butDisabledFront");
		$("#next").attr("disabled",true);
		$("#next").attr("class","but butDisabledBehind");
	}
	else
	{
		for (var index = 0; index < visitList.options.length; index++) {
	    	var visit = visitList.options[index];
	    	if(currentVisitSn == visit.value) 
	    	{
	    		currentIndex = index;
	    		break;
	    	}
	    }
		
		if(currentIndex == 0)
		{
			$("#next").attr("disabled",true);
			$("#next").attr("class","but butDisabledBehind");
		}
		else if(currentIndex == visitList.options.length - 1)
		{
			$("#prev").attr("disabled",true);
			$("#prev").attr("class","but butDisabledFront");
		}
	}
	
	// $Author:jin_peng
	// $Date : 2012/11/1 10:30
	// $[BUG]0010908 ADD BEGIN
	//初始设置过敏及危险不良反应值
	var patientAlerts = getPatientAlerts();
	
	if(patientAlerts == null || patientAlerts == "")
	{
    	$("#patientAlerts").hide();        		
    	$("#patientAlertsOut").show();
	}
	else
	{
    	$("#aller").html(patientAlerts);
    	$("#patientAlertsOut").hide();
	}
	
	adjustingPage();
	// $[BUG]0010908 ADD END
	
	doinit();
});

//$Author:jin_peng
//$Date : 2012/11/27 14:16
//$[BUG]0011883 MODIFY BEGIN
//$Author:jin_peng
// $Date : 2012/11/1 10:30
// $[BUG]0010908 ADD BEGIN
// 浏览器大小变化时重置页面显示位置
$(window).resize
(
	function()
	{
		if($("#outpatientView").size() > 0)
		{
			adjustingPage();
		}
	}
);
// $[BUG]0010908 ADD END

/**
 * 调整页面布局
 */
function adjustingPage()
{
	var wid = 0;
	
	// 获取出过敏危险信息外的对象宽度并相加
	$(".allerRightout").each
	(
		function(i)
		{
			wid = wid + parseNumber(replaceContent($(this).css("width"),"px",""));
		}
	);
	
	//页面可见区域宽度
	var availWidth = document.body.offsetWidth;
	
	//当页面可见区域宽度小于给定值时出现滚动条并保持给定值的宽度
	if (availWidth < 960) 
	{
		availWidth = 960;
	}
	
	var orgWidth = 3;
	
	//设置页面最上部分警告栏宽度
	$(".reAller").css("width",availWidth - wid - 60);
	
	$("#patientAlertsOut").css("width",availWidth - wid - 53 - orgWidth);
}
//$[BUG]0011883 MODIFY END

function showBlock(blockName)
{
	var showFlag = $("#show_"+ blockName).val();
	if(showFlag == "1")
	{
		$("#show_" + blockName).val("0");
		$("#show_" + blockName + "_text").html("▲");
		$("#" + blockName + "Info").fadeOut();
	}	
	else
	{
		$("#show_" + blockName).val("1");
		$("#show_" + blockName + "_text").html("▼");
		$("#" + blockName + "Info").fadeIn();
	}						
}

//$Author:bin_zhang
// $Date : 2012/12/25 10:30
// $[BUG]0012694 ADD BEGIN

//$Author:wu_jianfeng
// $Date : 2013/04/02 10:30
// $[BUG]0014854 MODIFY BEGIN

function showBlockAll(){
	var show_blocks = [{ show_value_id: "show_drug", show_text_id:"show_drug_text", show_table_id:"drugInfo"},
	 				   { show_value_id: "show_exam", show_text_id:"show_exam_text", show_table_id:"examInfo"},
	 				   { show_value_id: "show_lab", show_text_id:"show_lab_text", show_table_id:"labInfo"},
	 				   { show_value_id: "show_doc", show_text_id:"show_doc_text", show_table_id:"docInfo"},
	 				   { show_value_id: "show_other", show_text_id:"show_other_text", show_table_id:"otherInfo"}];	
	var $outpatientView = $("#outpatientView");
	var $show_all = $outpatientView.find("input#show_all");
	var $show_all_text = $outpatientView.find("a#show_all_text");
    var showFlag = $show_all.val();

	if(showFlag == "1")
	{
		for(var index in show_blocks){
			var block = show_blocks[index];
			$outpatientView.find("a#" + block.show_text_id).html("▲");	
			$outpatientView.find("input#" + block.show_value_id).val("0");
			$outpatientView.find("table#" + block.show_table_id).fadeOut();				
		}
		$show_all.val("0");
		$show_all_text.html("▲");
	}
	else{
		for(var index in show_blocks){
			var block = show_blocks[index];
			$outpatientView.find("a#" + block.show_text_id).html("▼");	
			$outpatientView.find("input#" + block.show_value_id).val("1");
			$outpatientView.find("table#" + block.show_table_id).fadeIn();				
		}
		$show_all.val("1");
		$show_all_text.html("▼");
	}
}
// $[BUG]0012694 ADD END
// $[BUG]0014854 MODIFY END

function obtainVisitDate(orgCode,patientSn)
{
	$("#date").attr("disabled",true);
	
	$("#relatedItem").load("../visit/obtainVisitDateCondition.html", {"orgCode":orgCode,"patientSn":patientSn},
		function(response, status, xhr) 
	    {
			if(status == "success")
			{
				if(orgCode != null && orgCode.length != 0 && orgCode != "-1")
				{
					$("#relatedItem").css("width","120px");
				}
				else
				{
					$("#relatedItem").css("width","200px");
				}
				
				adjustingPage();
			}
		
	        //如果状态是error或者timeout, 显示错误对话框
			else if(status == "error" || status == "timeout")
	        {
	        	setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
	        }
	    });
}

function go(isAddHistory)
{
	var attrDisabled = $("#date").attr("disabled");
	if(attrDisabled == true)
	{
		showMsg("提示","正在加载就诊日期选择列表，请稍后。。。");
		return false;
	}
	
	var visitSn = $("#date").val();
	if($.trim(visitSn) == "")
	{
		showMsg("提示","请选择门诊就诊（日期）记录");
		return false;
	}
	
	var orgCode = "";
	
	if($("#orgCode").size() > 0)
	{
		orgCode = $("#orgCode").val();
	}
	
    loadPanel('../visit/outpatientViewPart.html', {'visitSn': visitSn,'orgCode':orgCode},'#dyContent', {vid: '#outpatientView', isAddHistory:isAddHistory});
}

function prev(isAddHistory, currentVisitSn, orgCode)
{
	//var currentVisitSn = $("#date").val();
	var currentIndex = -1;
	//var visitList = document.getElementById("date");
	
	for (var index = 0; index < visitOptions.length; index++) 
	{
    	var visit = visitOptions[index];
    	if(currentVisitSn == visit.value) 
    	{
    		currentIndex = index;
    		break;
    	}
    }
	
    var prevIndex = currentIndex + 1;
    if(prevIndex >= 0)
	{
		var visitSn = visitOptions[prevIndex].value;
	    loadPanel('../visit/outpatientViewPart.html', {'visitSn': visitSn, 'orgCode': orgCode},'#dyContent', {vid: '#outpatientView', isAddHistory:isAddHistory});
	}         	
}
	
function next(isAddHistory, currentVisitSn, orgCode)
{
	//alert(currentVisitSn+"  " +orgCode);
	//var currentVisitSn = $("#date").val();
	var currentIndex = -1;
	//var visitList = document.getElementById("date");
	
	for (var index = 0; index < visitOptions.length; index++) 
	{
    	var visit = visitOptions[index];
    	if(currentVisitSn == visit.value) 
    	{
    		currentIndex = index;
    		break;
    	}
    }
	
    var nextIndex = currentIndex - 1 ;
    
    if(nextIndex < visitOptions.length)
	{
		var visitSn = visitOptions[nextIndex].value;
	    loadPanel('../visit/outpatientViewPart.html', {'visitSn': visitSn, 'orgCode':orgCode},'#dyContent',{vid: '#outpatientView', isAddHistory:isAddHistory});
	}         	
}
//$Author:chang_xuewen
//$Date : 2013/06/28 16:20
//$[BUG]0033461 ADD BEGIN
/**
 * 门诊视图独立翻页实现
 * @param pageno
 * @param id
 * @param name
 * @returns
 */
function turnPage(pageno,id,name){
	var form = null;
	if(name=="drug"){
		form = $("[name='pageform_drug']");
	}
	else if(name=="exam"){
		form = $("[name='pageform_exam']");
	}
	else if(name=="lab"){
		form = $("[name='pageform_lab']");
	}	
	else if(name=="doc"){
		form = $("[name='pageform_doc']");
	}
	else if(name=="other"){
		form = $("[name='pageform_other']");
	}
	else{
		form = null;
	}
	if(form == null)
	{
		showMsg("提示","检索条件表单对象不存在！");
		return;
	}
	var href = form.attr("action");
	href = href + "?pageNo=" + pageno;
	$(id).load(href,function()
	{
		$("*").removeAttr("onfocus");
		logical();
	});
}
/**
 *  门诊视图独立翻页实现
 * @param pageno
 * @param totalPageCnt
 * @param id
 * @returns
 */
function turnToPageNo(pageno,totalPageCnt,id,name)
{
	var form = null;
	if(name=="drug"){
		form = $("[name='pageform_drug']");
	}
	else if(name=="exam"){
		form = $("[name='pageform_exam']");
	}
	else if(name=="lab"){
		form = $("[name='pageform_lab']");
	}	
	else if(name=="doc"){
		form = $("[name='pageform_doc']");
	}
	else if(name=="other"){
		form = $("[name='pageform_other']");
	}
	else{
		form = null;
	}
	if(form == null)
	{
		showMsg("提示","检索条件表单对象不存在！");
		return;
	}
	var reg = new RegExp("^[0-9]*$");
	if(!reg.test(pageno)||pageno == "0")
	{
		showMsg("提示","输入内容不正确，请输入1的整数！");
		return;
	}
	if(pageno > totalPageCnt)
	{
		showMsg("提示","输入页号大于页面提供的最大值！");
		return;
	}
	var href = form.attr("action");
	href = href + "?pageNo=" + pageno;
	
	$(id).load(href,function()
	{
		$("*").removeAttr("onfocus");
		logical();
	});
}
function justShow(url,tr,data){
	// 1.在当前点击的tr下增加一个tr
	// 2.在增加的tr中增加一个div，将详细页面load进去
	// 3.siblings去除掉其他tr下增加的tr
	tr.css("backgroundColor" , "#87CEFF");
	tr.siblings().css("backgroundColor" , "");
	var ntr = "<tr id='ntr'><td id='ntd' colspan='12' style='border: 4px solid #4fb5e6;'><div id='control'></div></td></tr>";
	if($("#ntr").length>0){
		$("#ntr").fadeOut(900);
		$("#ntr").remove();
	}else{
		//do nothing for now
	}
	//$Author:chang_xuewen
	//$Date : 2013/07/22 10:20
	//$[BUG]0035067 MODIFY BEGIN
//	var panel = "<div id='outpatientLoadingScreen' class='loadingScreen'><div class='loadingMessage'>数据加载中，请稍候...</div></div>";
	tr.after(ntr);
	var $control = $("#control");	
	$("#ntr").fadeIn(3000);
//	$control.html(panel);	
	showLoadingScreen({
        loadingScreenId: "loadingScreen", 
        loadingMessageId: "loadingMessage",
        modal: true
    });
	//load内容
	$control.load(url,data,function(){
		/*$Author :chang_xuewen
		 * $Date : 2013/08/07 16:20
		 * $[BUG]0035740 MODIFY BEGIN*/
		//do something
		if((typeof(data.isDoc)== "undefined" || !data.isDoc)&& (returnParentName() != "" || returnMutexesName() != "")){
			$("#control").load('../common/commonTab.html',function(){
				var $idcon = $("#control").find("#moreTabs");
				$idcon.css({"width":"100%","min-height":"100%"});
				if($.browser.mozilla) {
					 $idcon.find("li").css("margin-top","0");
				}
				$("#detailViewTab_0").load(url,data,function(){					
					$idcon.find("img").remove();
					// 显示名称设置
	                if(data.name != "")
            		{
                		if(data.name.length > 4){
                			var name = data.name.substring(0,4);
                			$("#select_moreTabs1").attr("title",data.name).children("a").text(name+"...");
                		}
                		else{
                			$("#select_moreTabs1").attr("title",data.name).children("a").text(data.name);
                		}
            		}else{
            			if(data.otherName.length > 4){
                			var name = data.otherName.substring(0,4);
                			$("#select_moreTabs1").attr("title",data.otherName).children("a").text(name+"...");
                		}
                		else{
                			$("#select_moreTabs1").attr("title",data.otherName).children("a").text(data.otherName);
                		}
            		}                
	                // 为a增加title信息
	    		    $idcon.children("ul").find("li:last-child").children("a").attr(
	    		            "title",
	    		            data.holdName == null ? data.holdOtherName
	    		                    : data.holdName);					
					// 关闭加载提示
	                closeLoadingScreen({
	                    loadingScreenId: "loadingScreen", 
	                    loadingMessageId: "loadingMessage"
	                });
				}).css("height","100%");
			});
		}
		// 关闭加载提示
        closeLoadingScreen({
            loadingScreenId: "loadingScreen", 
            loadingMessageId: "loadingMessage"
        });
        /*$[BUG]0035740 MODIFY END*/
	});
	//
	$("#ntr").on("click", function(event){
		$("#ntr").prev().css("backgroundColor" , "");
		$("#ntr").fadeOut(900);
		$("#ntr").remove();
	});
	//$[BUG]0035067 MODIFY END
}
/**
 * 处理ie缓存
 */
function catchMover(url){
	var a = Math.random();
    if (url.indexOf("?") > 0) {
    	url = url + "&rand=" + a;
    	return url;
    } else {
    	url = url + "?rand=" + a;
    	return url;
    }
}
/**
* 加载五个业务数据页面
*/
function doinit(){	
	//解决IE浏览器缓存问题
	var drughref=catchMover("../visit/drugPart.html");
	var examhref=catchMover("../visit/examPart.html");
	var labhref=catchMover("../visit/labPart.html");
	var dochref=catchMover("../visit/docPart.html");
	var otherhref=catchMover("../visit/otherPart.html");
	$.ajaxSetup ({
		cache: false
	});
	
	$("#drugdiv").load(drughref,function(){
		$("#examdiv").load(examhref,function(){
			$("#labdiv").load(labhref,function(){
				$("#docdiv").load(dochref,function(){
					$("#otherdiv").load(otherhref,function()
					{
						//$Author:wu_jianfeng
						// $Date : 2013/04/02 10:30
						// $[BUG]0014854 MODIFY BEGIN
						var $outpatientView = $("#outpatientView");
						var show_blocks = ["#show_other_text", "#show_doc_text", "#show_lab_text", "#show_exam_text", "#show_drug_text"];
						var show_all = false;
						for(var b_index in show_blocks) {		
							if($outpatientView.find("a" + show_blocks[b_index]).length) {
								show_all = true;	 
								break;
							}
						}
						if(show_all == false)
							$("#ov_show_all").hide();
						// $[BUG]0014854 MODIFY END
						$("*").removeAttr("onfocus");
						logical();
					});
				});
			});
		});
	});
}

//$[BUG]0033461 ADD END