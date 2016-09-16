
function loadCommonPanel(title,data)
{
	showTabsDialog('../common/commonTab.html',title,data);
}


/**
 * 点击左边列表，增加多个tabs页
 * @param obj 当前点击的tr
 * @param url 要加载的url
 * @param postData 加载的数组
 * @param id 右边界面最外层的id 用于查找ui下边的a和table下的id
 * @param size 配置文件设定的最多tabs个数
 */
function addMoreTabs(obj, url, postData, id) {
    // 计算tabs的条数
    var liSize = $(id).children("ul").find("li").children("a").size();
    if (changeTabs(obj, typeof(postData.examReportSn)!="undefined"?postData.orderSn+'_'+postData.examReportSn+'_'+postData.itemSn:postData.orderSn, id, liSize)) {
        return;
    } 
    // 设置左边table tr的相关属性
    setLeftTR(obj);
    
    // 动态添加多个tab页
    $(id).tabs("add", //添加add tabs事件
                '#detailViewTab_' + liSize, // tabs的id 以及ul>li>a 对应的href的值
                isNull(postData.name)
                    ? (isNull(postData.otherName) ? "选项卡": postData.otherName)
                    : postData.name); //tabs的name

    // 设置右边tabs页的相关属性
	setRightUL(url, postData, '#detailViewTab_' + liSize, id, liSize);
    
    // 判断UL的宽度决定显示多少个tab页 
    setULWidth(id);
}

/**
 * 设置左边table tr的相关属性
 * @param obj
 */
function setLeftTR(obj){
    $(obj).children().css({
        "backgroundColor" : "#87CEFF",
        "font-weight" : "bold"
    }); // 当前点击的td加粗

    $("#prev_next").find("tr").not(obj).find("td").css({
    	 "backgroundColor" : "",
        "font-weight" : "normal"
    }); // 其他选中的td恢复正常
    
    // $Author :chang_xuewen
	// $Date : 2013/10/24 11:00$
	// [BUG]0038443 ADD BEGIN    
    if($("#upbody").length>0 || $("#downbody").length>0){
    	$("#upbody").find("tr").not(obj).find("td").css({
            "font-weight" : "normal"
        }); // 其他选中的td恢复正常
    	$("#downbody").find("tr").not(obj).find("td").css({
            "font-weight" : "normal"
        }); // 其他选中的td恢复正常
    }
	// [BUG]0038443 ADD END
    
    
    //Author:chang_xuewen
    //Date : 2013/7/31 16:30
    //[BUG]0035345 ADD BEGIN
    if($("#BIGviewFrame").length>0){
    	$("#listcon").find("tr").not(obj).find("td").css({
            "font-weight" : "normal"
        }); // 其他选中的td恢复正常
    }
    //[BUG]0035345 ADD END
}


//added by yang_jianbo@2014-5-9 点击行变色
function emrSetLeftTR(obj){
	// 当前点击的td加粗,上背景色
    $(obj).children().css({
        "backgroundColor" : "#87ceff",
        "font-weight" : "bold"
    }); 
   
    // 其他非选中的行 不设置任何背景色
    $(obj).parent().find("tr").not(obj).each(function(i){
    		$(this).find("td").css({
    			"backgroundColor" : "",
    	        "font-weight" : "normal"
    		})
    })
}

/**
 * 设置右边tabs页的相关属性
 * @param data
 * @param did
 * @param id
 * @param liSize
 */
function setRightUL(url, data, did, id, liSize) {
    // 调整div布局
    $(did).addClass("detailViewTab ui-tabs ui-widget");
	
    $(did).css({"padding":"0"});
    
    $(id).tabs({ // 显示当前点击的医嘱tabs页
    	selected : liSize
    });

    $(id).children("ul").find("li").children("a").addClass("parentli"); 
    // 为a增加title信息
    $(id).children("ul").find("li:last-child").children("a").attr(
            "title",
            data.holdName == null ? data.holdOtherName
                    : data.holdName);

    // 增加tabs页，局部加载右边界面
    loadRightPanel(url, data, did, id, liSize);
}

/**
 * 判断字符串
 * @param string
 * @returns {Boolean}
 */
function isNull(string) {
	if (string == undefined)
		return true;
	if (string == null)
		return true;
	var trimString = $.trim(string);
	if (trimString == "")
		return true;
}

/**
 * 改变左边列表的样式，以及右边tabs页的切换
 * 
 * @param obj
 * @param orderSn
 */
function changeTabs(obj,orderSn,id,liSize)
{
    var flag = false;
    
	var orderSnDiv = $("#moreTabs div[name='selectTabs']");
    
    $.each(orderSnDiv,function(i,n){
    	if($(this).attr("id") == "_"+orderSn){

    		$("#moreTabs").tabs({"selected":i});
    		
    		flag = true;
    	}
    });
    
    return flag;
}

function closeTab(id,orderSn)
{
    $('#_' + orderSn).tabs();
    
    $("#detailViewTab_0").tabs();
    
    $(id).tabs({add:addEventHandler}); 
	 
	 $(id).children("ul").children("li").children("a").css(
				"background-image", "url(../images/title2.png)");
	 
	 detailFocusTab("_" + orderSn);
	 
	 detailFocusTab("detailViewTab_0");
}

/**
 * 
 * @param id
 */
function tabsBind(id,orderSn) {

    $(id).bind('tabsselect',
        function(event, ui) {

    		var $moreTabs = $("#" + event.target.id);
        
            var $moreTabsA = $moreTabs.find("ul:first>li>a");
            
            // 处理当前选中tab页时的高亮
            var href = $moreTabsA[ui.index == $moreTabsA.size() ? (ui.index - 1): ui.index].hash;
            
            var orderSn = $(href).find("div[name='selectTabs']").attr("id");
            
            var drugTrList = $moreTabs.siblings().find("table:last-child tr");

            $.each(drugTrList, function(i, n) {
                
            	if (orderSn == "_" + $(this).attr("id")) {
            		
            		setLeftTR("#" + $(this).attr("id"));
                }
            });
            //Author:chang_xuewen
            //Date : 2013/7/31 16:30
            //[BUG]0035345 ADD BEGIN
            if($("#BIGviewFrame").length>0){
            	 //综合视图
                var list = $moreTabs.parents("#dynamicContent").find("#listcon").find("table:last-child tr");           

                $.each(list, function(i, n) {
                	
                	if (orderSn == "_" + $(this).attr("id")) {
                		
                		setLeftTR("#" + $(this).attr("id"));
                    }
                });
            }
            //[BUG]0035345 ADD END
        });
    $(id).bind("tabsshow",function( event, ui ) {
    	setDialogCss();
    });
}


function addEventHandler(event,ui){  //关闭按钮 

    var a = $(ui.tab).parent(); 
    $('<img style="position:absolute;right:3px;top:10px;cursor: pointer;" src="../images/close.png"/>').appendTo(a).click(
    function(){ //关闭按钮,关闭事件绑定
    	var $moreTabs = $("#"+event.target.id);
    	
    	var orderSn = $(ui.panel).find("div[name='selectTabs']").attr("id");

    	var index = $moreTabs.find('ul:first>li').index(a.get(0));

    	initPanel(orderSn,index,$moreTabs);
    	
    	setDialogCss();
    }); 
}
    
/**
 * 
 * @param event
 * @param orderSn
 * @param index
 * @param $moreTabs
 */
function initPanel(orderSn, index, $moreTabs) {

	$moreTabs.tabs("remove",index); 

	// 格式化最外层TABS的href
    var aList = $moreTabs.find("ul:first>li>a");
    $.each(aList,function(i,n){
    	if(i > 8)
    		$(this).attr("href",$(this).attr("href").substring(0,$(this).attr("href").length-2)+i);
    	else
    		$(this).attr("href",$(this).attr("href").substring(0,$(this).attr("href").length-1)+i);
    });
    
    // 初始化href对应的tab页的id
    var durgDivList = $moreTabs.children("div");
    $.each(durgDivList,function(i,n){
    	if(i > 8)
    		$(this).attr("id",$(this).attr("id").substring(0,$(this).attr("id").length-2)+i);
    	else
    		$(this).attr("id",$(this).attr("id").substring(0,$(this).attr("id").length-1)+i);
    });

    // 控制左边变色
    var drugTrList = $moreTabs.siblings().find("table:last-child tr");
    $.each(drugTrList,function(i,n){
        if(orderSn == "_"+$(this).attr("id"))
        {
            var className = $(this).attr("class");
            $("#" + $(this).attr("id")).children().removeAttr("style").parent().addClass(className);
        }
    });
    
    //Author:chang_xuewen
    //Date : 2013/7/31 16:30
    //[BUG]0035345 ADD BEGIN
    if($("#BIGviewFrame").length>0){
    	//综合视图
        var list = $moreTabs.parents("#dynamicContent").find("#listcon").find("table:last-child tr");           
        $.each(list,function(i,n){
        	
            if(orderSn == "_"+$(this).attr("id"))
            {
            	//当前关闭的tr恢复正常
                $("#" + $(this).attr("id")).find("td").css({
                    "font-weight" : "normal","background-color" : ""
                }); 
                $(this).find("td").css({
                    "font-weight" : "normal","background-color" : ""
                }); 
                //如果是在摘要中进入大图，则需在此判断是否有属性
                if($("#" + $(this).attr("id")).hasClass("selected")){
                	$(this).removeClass("selected");
                }
                if($(this).hasClass("selected")){
                	$(this).removeClass("selected");
                }
            }
        });
        //如果所有tab都被关闭了，就把容器也删掉,并且列表最大化
        var len = $moreTabs.parents("#dynamicContent").find("#BIGviewFrame").find("li").length;
        if(len == 0){
        	$("#mover").remove();
        	$("#slider").remove();
        	$("#BIGviewFrame").remove();
        	$("#listcon").animate({height:"100%"},800);
        	var $con = $("#listcon").parents( ".portlet:first" ).find(".portlet-content");
        	if($con.is(":hidden")){//如果当前的portlet被slideToggle为隐藏，则修改成显示,否则将导致无法显示的bug
    			$con.show();    			
    		}
        	$("#conditionForm").css("width","100%");
        	
        	$("#listcon").show();
        }
    }
    //[BUG]0035345 ADD END

}

/**
 * 向下翻页
 * @param url
 * @param postData
 * @param id
 */
function jumpToNext(url,postData,id)
{
	var pageNo = $("#prev_next").find("table:last").attr("id");  // 翻到的页码
	
	if(isNull(pageNo))
	{
		pageNo = 2;
	}
	else
	{
		pageNo = parseInt(pageNo) + 1;
	}
	if(pageNo == postData.totalPageCnt) // 如果页码已经是最后一页
	{
		$("#img_next").css("display","none");
		$("#prev_next").height($("#prev_next").height()+10);
	}
	postData.pageNo = pageNo;
    loadLeftPanel(url,postData,id);
}


function loadLeftPanel(url, data, id, vid, continueGoto, isAdd, isAddHistory, isMainViewMenuPart)
{
    var $panelContainer = null; 
    $panelContainer = getPanelContainer(id); 
    
    //解决IE浏览器缓存问题
    var a = Math.random();
    if(url.indexOf("?")>0){
        url = url+"&rand="+a;
    }else{
        url = url+"?rand="+a;
    }

    if(vid == undefined || vid != "#normal")
    {
        showLoadingScreen({
            loadingScreenId: "loadingScreen", 
            loadingMessageId: "loadingMessage",
            modal: true
        });
    }
    
    $panelContainer.load(url, data, function(response, status, xhr) {
        //处理Session过期
        var sessionExpried = handleExpiredSession(xhr);
        // 如果session没有过期
        if(!sessionExpried)
        {
            //如果异步加载数据成功
            if (status == "success") {
                var div = $(id);
                
                // 控制左边锚点
                var trList = div.find("table:last").find("tr");
                $.each(trList,function(i,n){
                	if(typeof(data.trID)!="undefined" && data.trID==data.trID.length!=0 && $(this).attr("id") == data.trID){
                	    var pos = $(this).offset().top - $("#prev_next").offset().top - $("#prev_next").height() + $(this).height();
                	    $("#prev_next").animate({scrollTop: pos}, 1000);
                	}
                });
                
                var subId = id.substring(1,id.length);
                //向下翻页
                if(id == "#next_1")
                {
                	div.removeAttr("id");
                    if($(id).children("table").attr("id") == data.totalPageCnt) 
                    {
                        $("#img_next").css("display","none");
                        
                        setDivHeight($("#prev_next").children("div").find("table"),$("#prev_next").find("table").height()+10);
                    }
                    else 
                    {
                        div.after('<div id='+subId+'></div>');
                    }
                }
                else if(id == "#pre_1")
                {
                	div.removeAttr("id");
                    if($(id).children("table").attr("id") == 1) 
                    {
                        $("#img_prev").css("display","none");
                        
                        setDivHeight($("#prev_next").children("div").find("table"),$("#prev_next").find("table").height()+10);
                    }
                    else 
                    {
                        div.before('<div id='+subId+'></div>');
                    }
                }
                
                setLeftTDWidth();
                
                $("#moreTabs").css("float","left");
                
                setupDatePickerSetting();
                
                // 初始化带有datepicker样式的日期输入框
                setupDatePicker();

                afterLoadPanelSuccess();
                
                detailFocusTab();
                
                $("#ui-dialog-title-ajaxDialog").focus();
            }
            
            //如果状态是error或者timeout, 显示错误对话框
            else if(status == "error" || status == "timeout")
            {
            	setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
            }
            
            if(vid == undefined || vid != "#normal")
            {
                // 关闭加载提示
                closeLoadingScreen({
                    loadingScreenId: "loadingScreen", 
                    loadingMessageId: "loadingMessage"
                });
            }
        }
    });
}

/**
 * 点击list中的某个条目时，显示detail信息
 * @param openUrl
 * @param titleName
 * @param postData
 * @param width
 * @param height
 * @param id
 * @param resizable
 */
function showTabsDialog(openUrl, titleName, postData, width, height, id, resizable)
{
    var lid;
    //当跳转页面点击出现缓存标识
    var smFlag;
    
    if(id == undefined)
    {
        lid = "#ajaxDialog";
    }
    else
    {
        lid = id;
    }
    
    var parentDom = $(lid).parent();
    showLoadingScreen({
        loadingScreenId: "loadingScreen", 
        loadingMessageId: "loadingMessage",
        modal: true
    });

    //解决IE浏览器缓存问题
    var a = Math.random();
    if(openUrl.indexOf("?")>0){
        openUrl = openUrl+"&rand="+a;
    }else{
        openUrl = openUrl+"?rand="+a;
    }
    
    jQuery(lid).load(openUrl,postData,function(response, status, xhr) {
        // 关闭加载提示
        closeLoadingScreen({
            loadingScreenId: "loadingScreen", 
            loadingMessageId: "loadingMessage"
        });         

        //处理Session过期
        var sessionExpried = handleExpiredSession(xhr);
        if(!sessionExpried)
        {
            // 如果成功，显示对话框
            if (status == "success") 
            {
                var option = {
                        title: "对话框名", closeText:"关闭", minWidth: 800, minHeight: 565,
                        bgiframe: true ,          //解决下拉框遮盖div的bug
                        modal:true, 
                        close: function(event, ui) {
                            $(this).remove();
                            lid = lid.substring(1);
                            parentDom.append("<div id='" + lid +"' style='display:none;'></div>");
                            showDialogFocus();
                       },
                        open: function (event, ui) {
                            var $dialog = $(this);
                            var atext = $(".ui-dialog-titlebar-close").replaceWith('<div class="ui-xlgwr" style="width:47px;float:right;margin-top:9px;margin-right:5px;cursor:pointer;"><span id="ext" class="ui-icon ui-icon-zoomin tabEnter" style="float:left;margin-right:12px" title="改变窗口大小">extlink</span> <span id="tt_close" style="float:right" class="tabEnter ui-icon ui-icon-closethick">close</span></div>');
                            //绑定最大化
                            $(".ui-xlgwr>span#ext").toggle(function(){
                                if (window.screen) {              //判断浏览器是否支持window.screen判断浏览器是否支持screen
                                    var bigw = $(window).width();   
                                    var bigh = $(document).height();  
                                    $dialog.dialog({
                                        width: bigw * 0.999,
                                        height: bigh * 0.999
                                    });
                                    
                                    $dialog.dialog("option","position","top");
                                    
                                    $("html,body").animate({scrollTop: 0}, 800); // 设置浏览器的滚动条位置
                                    // 设置医嘱跟踪TAB页高度
                                    $("input[value='观看路径动画']").parent().parent().css("height", "500px");
                                    // 调整检验详细标题栏
                                    adjustLabDetailTitles();
                                    
                                } else {
                                    $dialog.dialog({
                                        width: 800,
                                        height: 565
                                    });
                                }
                                $(".ui-xlgwr>span#ext").removeClass("ui-icon-zoomin");
                                $(".ui-xlgwr>span#ext").addClass("ui-icon-zoomout");
                                
                                setLeftTDWidth();
                                
                                setDialogCss();
                            },function(){
                                $dialog.dialog(
                                {
                                    width: 800,
                                    height: 565
                                });
                                $dialog.dialog("option","position","center");
                                $(".ui-xlgwr>span#ext").removeClass("ui-icon-zoomout");
                                $(".ui-xlgwr>span#ext").addClass("ui-icon-zoomin");
                                
                                setLeftTDWidth();
                                
                                setULWidth("#moreTabs");
                                
                                setDialogCss();
                                // 设置医嘱跟踪TAB页高度
                                $("input[value='观看路径动画']").parent().parent().css("height", "440px");
                                // 调整检验详细标题栏
                                adjustLabDetailTitles();
                            });
                            //绑定关闭
                            $(".ui-xlgwr>span.ui-icon-closethick").click(function(){
                                $dialog.dialog("close");
                            });               
                        },
            	     resize: function(event, ui) { setLeftTDWidth(); }
                };
                if (width == undefined) minWidth = 800;
                if (height == undefined) minHeight = 565;
                if (resizable == undefined) resizable = true;
                $(lid).css({"overflow":"hidden"});
                option.title = titleName;
                option.minWidth = minWidth;
                option.minHeight = minHeight;
                option.resizable = resizable;  
                $(lid).dialog(option);
                if(postData.tabsFlag == 'true')
                {
                	loadRightPanel(postData.url,postData,'#detailViewTab_0');
                	if(!isNull(postData.name))
            		{
                		if(postData.name.length > 4){
                			var name = postData.name.substring(0,4);
                			$("#select_moreTabs1").attr("title",postData.name).children("a").text(name+"...");
                		}
                		else{
                			$("#select_moreTabs1").attr("title",postData.name).children("a").text(postData.name);
                		}
            		}
                }
            }
            else if(status == "error" || status == "timeout")
            {
            	setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",270);
            }
        }
    }); 
}

function loadRightPanel(url, data, id, vid, liSize)
{
    var $panelContainer = null; 
    $panelContainer = getPanelContainer(id); 
    
    //解决IE浏览器缓存问题
    var a = Math.random();
    if(url.indexOf("?")>0){
        url = url+"&rand="+a;
    }else{
        url = url+"?rand="+a;
    }

    if(vid == undefined || vid != "#normal")
    {
        showLoadingScreen({
            loadingScreenId: "loadingScreen", 
            loadingMessageId: "loadingMessage",
            modal: true
        });
    }
    
    //$Author :jin_peng
    // $Date : 2013/05/29 15:20$
    // [BUG]0031929 ADD BEGIN
    data = changeIntoJson(data);
    
    if(vid == "#normal")
    {
        g_firstLoad = false;
    }
    
    // [BUG]0031929 ADD END
    
    $panelContainer.load(url, data, function(response, status, xhr) {
        //处理Session过期
        var sessionExpried = handleExpiredSession(xhr);
        // 如果session没有过期
        if(!sessionExpried)
        {
            //如果异步加载数据成功
            if (status == "success") {
            	
            	if(id == "#detailViewTab_6")
            		$("#detailViewTab_5").find("div[name='selectTabs']").css({"border":"0"});
            	else
            		$(id).find("div[name='selectTabs']").css({"border":"0"});
            	
            	if(data.tabsFlag == 'true')
                {
            		loadList(data);
                }
            	// $Author :chang_xuewen
				// $Date : 2013/10/24 11:00$
				// [BUG]0038443 ADD BEGIN            	
            	setDialogCss();
				// [BUG]0038443 ADD END
            	
                afterLoadPanelSuccess();
            }
            
            //如果状态是error或者timeout, 显示错误对话框
            else if(status == "error" || status == "timeout")
            {
            	setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
            }
            
            if(vid == undefined || vid != "#normal")
            {
                // 关闭加载提示
                closeLoadingScreen({
                    loadingScreenId: "loadingScreen", 
                    loadingMessageId: "loadingMessage"
                });
            }
        }
    });
}


function loadList(data){
	if(data.gotoType == 'drug' || data.gotoType == 'drug_timer')
    {
		loadLeftPanel('../drug/list_'+data.patientSn+'.html',data,'#dd_detail');
    }
    else if(data.gotoType == 'lab' || data.gotoType == 'lab_timer')
    {
    	data.sourceSystemId = '';
    	loadLeftPanel('../lab/list_'+data.patientSn+'_.html',data,'#dd_detail');
    }
    else if(data.gotoType == 'diagnosis' || data.gotoType == 'diagnosis_timer')
    {
    	loadLeftPanel('../diagnosis/list_'+data.patientSn+'.html',data,'#dd_detail');
    }
    else if(data.gotoType == 'exam' || data.gotoType == 'exam_timer')
    {
    	loadLeftPanel('../exam/list_'+data.patientSn+'_.html',data,'#dd_detail');
    }
    else if(data.gotoType == 'nondrug' || data.gotoType == 'nondrug_timer')
    {
    	loadLeftPanel('../order/list_'+data.patientSn+'.html',data,'#dd_detail');
    }
    else if(data.gotoType == 'procedure' || data.gotoType == 'procedure_timer')
    {
    	loadLeftPanel('../procedure/list_'+data.patientSn+'.html',data,'#dd_detail');
    }
    else if(data.gotoType == 'doc' || data.gotoType == 'doc_timer')
    {
    	loadLeftPanel('../doc/list_'+data.patientSn+'.html',data,'#dd_detail');
    }
    // $Author :chang_xuewen
	// $Date : 2013/10/24 11:00$
	// [BUG]0038443 ADD BEGIN    
    else if(data.gotoType == 'procedure_doc' || data.gotoType == 'prodoc')
    {
    	
    	loadUpAndDownList('../procedure/getMainList.html','../procedure/inpatientList_'+data.patientSn+'.html','../doc/listprodoc_'+data.patientSn+'.html','#dd_detail',data);
    	/*loadLeftPanel('../procedure/inpatientList_'+data.patientSn+'.html',data,'#dd_detail');*/
    }
    /*else if(data.gotoType == 'prodoc')
    {
    	loadLeftPanel('../doc/listprodoc_'+data.patientSn+'.html',data,'#dd_detail');
    }*/
	
    
}

function loadUpAndDownList(url,urlu,urld,bodyId,data)
{
	var datap = {'tabsFlag':'true','gotoType':'procedure_doc','width':'70%','patientSn':data.patientSn,
			'visitSn':data.visitSn,'inpatientDate':data.inpatientDate};
	var datac = {'tabsFlag':'true','gotoType':'prodoc','width':'70%','patientSn':data.patientSn,
			'visitSn':data.visitSn,'inpatientDate':data.inpatientDate};
	
	if(data.gotoType == 'procedure_doc'){
		$(bodyId).load(url,function(event,status){
			setLeftTDWidth();
			$("#uplist").load(urlu,data,function(){
				setLeftTDWidth();
				$("#downlist").load(urld,datac,function(){});
			});
		});
	}
	else if(data.gotoType == 'prodoc'){
		$(bodyId).load(url,function(event,status){
			setLeftTDWidth();
			$("#downlist").load(urld,data,function(){
				setLeftTDWidth();
				$("#uplist").load(urlu,datap,function(){});
			});
		});
	}
}
// [BUG]0038443 ADD END

/**
 * 设置最上层tab的个数，超过几个后，出现翻页
 * @param did
 * @param id
 */
function setULWidth(id) {

	var ul = $(id).children("ul"); // tabs 的ul
    
    var ulWidth = ul.width(); // ul宽度
    
    var liSize = ul.children("li").size(); // 当前ul li 的个数
    
    var liWidth = ul.children("li").width(); // ul li的宽度
    
    var liTotalWidth = liSize*liWidth; // li总的宽度 
    
    if(ulWidth < liTotalWidth) // 当li的宽度超过ul的宽度
    {
       var ulContent = Math.floor(ulWidth/liWidth); // 当前ul下最多能包含的li的个数
       
       for ( var i = 0; i < liSize - ulContent; i++)
	   {
    	   var divPanel = $(id).children("div");
    	   
		   if (divPanel.eq(0).hasClass("ui-tabs-hide")) {
			   var orderSn = divPanel.eq(0).find("div[name='selectTabs']").attr("id"); // li下panel div的id
			   initPanel(orderSn, 0, $(id));
		   }
		   else{
			   var orderSn = divPanel.eq(1).find("div[name='selectTabs']").attr("id"); // li下panel div的id
			   initPanel(orderSn, 1, $(id));
		   }
	   }
    //Author:chang_xuewen
    //Date : 2013/7/31 16:30
    //[BUG]0035345 ADD BEGIN
    }else if(8 < liSize) // 当li的宽度超过ul的宽度
    {       
    	 var divPanel = $(id).children("div");
		 if (divPanel.eq(0).hasClass("ui-tabs-hide")) {
			 var orderSn = divPanel.eq(0).find("div[name='selectTabs']").attr("id"); // li下panel div的id
			 initPanel(orderSn, 0, $(id));
		 }
		 else{
			 var orderSn = divPanel.eq(1).find("div[name='selectTabs']").attr("id"); // li下panel div的id
			 initPanel(orderSn, 1, $(id));
		}
    }
    //[BUG]0035345 ADD END
}


function setLeftHeight(pageNo,totalPageCnt)
{
	if(pageNo == 1)
	{
		setDivHeight($("#prev_next"),$("#prev_next").height()+10);
	}
	// 如果左边的条数不足，高度补齐
	if(pageNo >= totalPageCnt)
	{
		setDivHeight($("#prev_next"),$("#prev_next").height()+10);
		
		setDivHeight($("#prev_next").children("div").find("table"),$("#prev_next").height());
	}
	
	setLeftTDWidth();
}

function setDivHeight($Div,height){
	$Div.height(height);
}

function setLeftTDWidth(){
	$("#prev_next table :last-child tr :first-child").width($("#tdTitle").width());
	$(".prev_next table :last-child tr :first-child").width($("#tdTitle").width());
}

function setDialogCss(){
	var tr = $("#prev_next table :last-child tr:last-child");
	if(tr.length == 0)
	{
		return ;
	}
	
	var selectTab = $("#moreTabs").children("div");
	$.each(selectTab,function(i,n){
		if(!$(this).hasClass("ui-tabs-hide")) // 当前选中的tab页
		{
			var leftTable ; // 左边列表的高度
			var rightDiv ; // 右边dialog的高度
			var prevNextHeight ; // 左边table的高度
			var leftTitleHeight = $("#prev_next").siblings().height(); // 左边table title的高度
			if($("#ext").hasClass("ui-icon-zoomin")) // 非最大化时 
			{
				leftTable = $("#ajaxDialog").height() - 16 ;
				rightDiv = leftTable - $("#moreTabs ul").height();
			}
			else
			{
				leftTable = $("#prev_next").parent().height();
				rightDiv = $(this).find("#mainContent").height();
				if(leftTable < rightDiv)
				{
					if(rightDiv > $(document).height())
					{
						var ajaxDialog = $("#ajaxDialog").height() - $("#moreTabs").children("ul").height() - 10;
						rightDiv = ajaxDialog;
					}
					var addHeightNum = Math.floor((rightDiv - leftTable)/tr.height());
					for(var i = 0 ; i < addHeightNum ; i ++)
					{
						if(tr.hasClass("odd"))
						{
							$('<tr class="even"><td style="WIDTH: 100px" align=left></td><td align=middle></td></tr>').appendTo(tr.parent());
							$('<tr class="odd"><td style="WIDTH: 100px" align=left></td><td align=middle></td></tr>').appendTo(tr.parent());
						}
						else
						{
							$('<tr class="odd"><td style="WIDTH: 100px" align=left></td><td align=middle></td></tr>').appendTo(tr.parent());
							$('<tr class="even"><td style="WIDTH: 100px" align=left></td><td align=middle></td></tr>').appendTo(tr.parent());
						}
					}
				}
				else
				{
					var minHeight = $("#moreTabs").css("min-height");
					if(rightDiv < minHeight)
					{
						rightDiv = minHeight;					
					}
				}
			}
			$(this).css(
			{
				"max-height" : "",
				"height" : rightDiv
			});
			prevNextHeight = $("#moreTabs").height() - leftTitleHeight;
			if($("#img_next").css("display") != "none" && !isNull($("#img_next").height()))
			{
				prevNextHeight = prevNextHeight - 10 ;
			}
			$("#prev_next").height(prevNextHeight);
			$("#tabs-animate").height($("#moreTabs").height());
			
		}
	});
}
/*
 * 提示信息淡出显示
 * */
function promptFadeIn(id)
{
	//淡入显示tip内容
	setTimeout("if($('"+id+"') != undefined){$('"+id+"').fadeIn(500)}",400 );
}

function removeTabsTip(id)
{
	$(id).remove();
}

