/** 标识页面是否是第一次加载 */
var g_firstLoad;

/** 跳转视图页面标识 */
var g_jumpViewPageFlag;

$(function() {

});

/**
 * 初始化普通视图效果
 */
function initNormalEffect() {
    // 页面局部刷新触发的事件 
    if (g_firstLoad) return;

    setupDatePickerSetting();

    jQuery('#accordion').accordion();

    //取消无子业务的折叠功能
    $("#accordion").children().children().each(function(i) {
        if ($(this).hasClass("uncollapsible")) {
            $(this).unbind("click");
        }
    });

    initPageLayout();

    initMenuEffect();

    g_firstLoad = true;
}

/**
 * 初始化页面布局
 */
function initPageLayout() {

}

/**
 * 初始化左侧菜单的鼠标进入、推出效果以及注册鼠标点击事件处理逻辑
 */
function initMenuEffect() {
    $("#category > li").each(function() {
        // 一级菜单的鼠标效果
        jQuery(this).hover(function() {
            jQuery(this).find('ul:eq(0)').show();
            jQuery(jQuery(this).find('a .headicon')).addClass('iconmouseover');
        },
        function() {
            jQuery(this).find('ul:eq(0)').hide();
            jQuery(jQuery(this).find('a .headicon')).removeClass('iconmouseover');
        });

        // 注册鼠标点击处理逻辑
        jQuery(this).click(function() {
            loadPanel(jQuery(this).find('a')[0].href);
        });

        // 处理二级子菜单的鼠标效果
        jQuery(this).find('.popMenu li').each(function() {
            jQuery(this).hover(function() {
                jQuery(jQuery(this).find('a .sub-menu-icon')).addClass('sub-menu-over');
            },
            function() {
                jQuery(jQuery(this).find('a .sub-menu-icon')).removeClass('sub-menu-over');
            });
        });
    });
}

/**
 * 设置JQueryUI DatePicker组件的默认选项
 */
function setupDatePickerSetting() {
    $.datepicker.regional['zh-CN'] = {
        closeText: '关闭',
        prevText: '&#x3c;上月',
        nextText: '下月&#x3e;',
        currentText: '今天',
        monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
        monthNamesShort: ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'],
        dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
        dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
        dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
        weekHeader: '周',
        dateFormat: 'yy-mm-dd',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: true,
        yearSuffix: '年'
    };
    $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
}

/**
 * 显示错误对话框
 */
function showErrorDialog(id, xhr, status, dialogOptions) {
	// $Author :jin_peng
    // $Date : 2013/07/29 14:53$
    // [BUG]0035166 DELETE BEGIN
    /**var msg = "Sorry but there was an error: ";
    var dlg = $(id);
    dlg.html(msg + xhr.status + " " + xhr.statusText);
    if (dialogOptions == undefined || dialogOptions == null) {
        dialogOptions = {
            title: "错误",
            minWidth: 400,
            minHeight: 250,
            bgiframe: true,
            //解决下拉框遮盖div的bug
            "buttons": [{
                text: "确定",
                click: function() {
                    $(this).dialog("close");
                }
            }]
        };
    }
    dlg.dialog(dialogOptions);*/
	
	// [BUG]0035166 DELETE END
	
	// $Author :jin_peng
    // $Date : 2013/07/29 14:53$
    // [BUG]0035166 ADD BEGIN
	var tipContent = "抱歉，系统可能发生异常，请您与管理员联系。";
	
	if(status == "timeout")
	{
		tipContent = "连接超时，请重新尝试。";
	}
	
	if($("#ajaxDialog").size() > 0 && $("#ajaxDialog").css("display") != "none")
	{
		var errTip = '<div id="errorsTipRegionDetail" style="width:99.7%;height:30px;display:none;background-color:#F0CAA2;border:1px solid #fdd;text-align:center;line-height:30px"><div><span style="float:left;width:97.5% !important;width:97%;">' + tipContent + '</span><span id="tt_close_error_detail" style="float:right;cursor:pointer;margin-right:3px !important;margin-right:4px;" onclick="closeErrorTip(\'#errorsTipRegionDetail\');" onmouseover="this.style.backgroundColor=\'#fdd28a\';" onmouseout="this.style.backgroundColor=\'#F0CAA2\';" class="tabEnter ui-icon ui-icon-closethick">close</span></div></div>';
		
		if($("#errorsTipRegionDetail").size() <= 0)
		{
			$("#ajaxDialog").prepend(errTip);
		}
		
		errorsTip("errorsTipRegionDetail");
	}
	else
	{
		errorsTip("errorsTipRegion");
	}
	
    return;
}

function closeErrorTip(obj)
{
	$(obj).toggle(500);
}

function errorsTip(obj)
{
	if($("#" + obj).css("display") == "none")
	{
		$("#" + obj).toggle(500,function()
		{
			shake("$('#" + obj + "').css('background','#ffffff')", "$('#" + obj + "').css('background','#F0CAA2')", 2);
		});
	}
	else
	{
		shake("$('#" + obj + "').css('background','#ffffff')", "$('#" + obj + "').css('background','#F0CAA2')", 2);
	}
}

function shake(clsFirst,clsLast,times)
{
	var i = 0, t = false , times = times || 2;
	
	if(t) return;
	
	t = setInterval(function()
	{
		i++;
		
		if(i % 2 == 0)
		{
			eval(clsLast);
		}
		else
		{
			eval(clsFirst);
		}
		
		if(i==2*times)
		{
			clearInterval(t);
			
			eval(clsLast);
		}
		
	},230);
};

//[BUG]0035166 ADD END

// $Author:wu_jianfeng
// $Date : 2013/1/15 11:21
// $[BUG]0012696 MODIFY BEGIN
function getPanelContainer(id) {
    var $panelContainer = null;

    if (id == undefined) {
        if ($("#normalView").length != 0) {
            $panelContainer = $(".dynamicContent");
        } 
        else 
        	$panelContainer = $("#dynamicContent");
    } else {
        $panelContainer = jQuery(id);
    }

    return $panelContainer;
}

/**
 * Ajax加载数据Session过期处理
 */
function handleExpiredSession(xhr) {
    //通过XMLHttpRequest取得响应头，sessionstatus，
    var sessionStatus = xhr.getResponseHeader("sessionStatus");
    if (sessionStatus == "timeout") {
        //如果超时就处理 ，指定要跳转的页面  
        window.location = "../login.html";
        return true;
    }
    return false;
}

/**
 * 使用指定的URL和数据局部刷新页面
 * url: 请求的url
 * data： 请求的文本对象或者字符串
 * id：局部加载的dom元素id
 * events: loadSuccess事件或者beforeLoad事件，如果loadSuccess事件，约定使用loadPanelSuccess作为函数名称，
 * 如果是beforeLoad事件，约定使用beforeLoadPanel作为函数名称
 */
function loadPanel(url, data, id, history, events) {
    var $panelContainer = null;
    $panelContainer = getPanelContainer(id);
    
    events = events || {};
    history = history || {};
    	
    //解决IE浏览器缓存问题
    var a = Math.random();
    if (url.indexOf("?") > 0) {
        url = url + "&rand=" + a;
    } else {
        url = url + "?rand=" + a;
    }

	var loadArgs = {url: url, data: data, id: id};
	if(history.vid == undefined || history.vid != "#normal")
	{
	    showLoadingScreen({
	        loadingScreenId: "loadingScreen",
	        loadingMessageId: "loadingMessage",
	        modal: true
	    });
	}

    if(typeof events.beforeLoad != "undefined") {
    	events.beforeLoad(loadArgs, history);
	}
    $panelContainer.load(url, data,
	    function(response, status, xhr) {
	        //处理Session过期
	        var sessionExpried = handleExpiredSession(xhr);
	        // 如果session没有过期
	        if (!sessionExpried) {
	            //如果异步加载数据成功
	            if (status == "success") {
				    setupDatePickerSetting();
				
				    // 初始化带有datepicker样式的日期输入框
				    setupDatePicker();

				    if(typeof events.loadSuccess != "undefined") {
				    	events.loadSuccess(loadArgs, history);
					}
						
	                if(typeof history != "undefined") {		                						
						addHistory(loadArgs, history, events);
	                }

					afterLoadPanelSuccess();
					
					loadTab();
	            }
	
	            //如果状态是error或者timeout, 显示错误对话框
	            else if(status == "error" || status == "timeout")
	            {
	            	setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
	            }
	
	        	if(history.vid == undefined || history.vid != "#normal")
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

function afterLoadPanelSuccess() {
    if ($("#normalView").length != 0) {
        $("#tableheader").hide();
    }

}


/**
 * 点击页码刷新时是动态加载，不存在IE缓存问题，另起方法名用于翻页调用
 */
function loadPanelPage(url, data, id, isAdd, isAddHistory, vid) {
	stopBubble(event);
    var $panelContainer = null;
    $panelContainer = getPanelContainer(id);
    
    showLoadingScreen({
        loadingScreenId: "loadingScreen",
        loadingMessageId: "loadingMessage",
        modal: true
    });
    if(isAddHistory != undefined)
	{
    	data = changeIntoJson(data);
	}
    $panelContainer.load(url, data,
    function(response, status, xhr) {
    	// 关闭加载提示
        closeLoadingScreen({
            loadingScreenId: "loadingScreen",
            loadingMessageId: "loadingMessage"
        });
        //处理Session过期
        var sessionExpried = handleExpiredSession(xhr);
        if (!sessionExpried) {
            //如果异步加载数据成功
            if (status == "success") {
                setupDatePickerSetting();

                // 初始化带有datepicker样式的日期输入框
                setupDatePicker();

                afterLoadPanelSuccess();
                
                if(vid != undefined)
            	{
                	changeViewLabelStatus(vid);
            	}
                
                //$Author :jin_peng
                // $Date : 2013/05/29 15:20$
                // [BUG]0031929 ADD BEGIN
                if (isAddHistory == "1") 
                {
                    addContextHistory("loadPanelPage('" + url + "','" + changeIntoStringMuti(data) + "'," + emptyToString(id) + ",'0','" + isAddHistory + "','" + vid + "')", isAdd);
                }

                //[BUG]0031929 ADD END
            }
            //如果状态是error或者timeout, 显示错误对话框
            else if (status == "error" || status == "timeout") {
            	setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
            }
        }
    });
}
// $[BUG]0012696 MODIFY END

/**
 * 通过Ajax的方式提交给定名称的表单
 */
function search(url, formName, id, isAddHistory) {
    var form = $("[name=" + formName + "]");
    if (form == null || form == '') {
        showMsg("提示", "检索条件表单对象不存在！");
        return;
    }
    //console.log(form.serializeArray());
    loadPanel(form.attr("action"), form.serializeObject(), id, {isAddHistory: isAddHistory});
}

/**
 * 通过jQueryUI把页面上有datepicker样式的元素转换为日期选择框
 */
function setupDatePicker() {
    $(".datepicker").datepicker({
        showMonthAfterYear: true,
        changeMonth: true,
        changeYear: true,
        buttonImageOnly: true,
        //maxDate: '+0',
        dateFormat: 'yy-mm-dd',
        onSelect: function(dateText, inst) {
            $(this).val(dateText);
        }
    });
}

/**
 * 显示或者隐藏左侧导航菜单条
 */
function toggleMenu(obj) {
    var ps = jQuery(obj);

    var os = jQuery("#dynamicContent");

    if (ps == null) return;

    if (ps.hasClass("paneSeperator_closed")) {
        os.css("width", "100%");
        ps.removeClass("paneSeperator_closed");
        ps.addClass("paneSeperator_open");
        jQuery(".menuContent").removeClass("off");
        
        if($("#graphPie").size() > 0)
    	{
        	if(isIE6())
    		{
        		$(".base").first().css("width","100px");
    		}
        	
        	$("#pieId").css("width","99%");
        	
            drawPieChart();
            
            adjustPromptFont();
    	}
    } else {
        os.css("width", "100%");
        ps.addClass("paneSeperator_closed");
        ps.removeClass("paneSeperator_open");
        jQuery(".menuContent").addClass("off");
        
        if($("#graphPie").size() > 0)
    	{
        	drawPieChart();
            
            adjustPromptFont();
    	}
    }
}

/**
 * 显示或者隐藏高级检索区域
 */
function toggleMore(id, isShow) {
    if (id == null) return;

    var container = $("#" + id);
    if (container == null) return;

    if (isShow) {
        container.addClass("container-off");
        jQuery(jQuery("[name='conditionForm']").find(".moreCondition")).each(function() {
            jQuery(this).show();
        });
        var conditionValue = container.find("[name='conditionValue']");
        conditionValue.val('on');
    } else {
        container.removeClass("container-off");
        jQuery(jQuery("[name='conditionForm']").find(".moreCondition")).each(function() {
            jQuery(this).hide();
        });
        var conditionValue = container.find("[name='conditionValue']");
        conditionValue.val('off');
    }
}

/**
 * 根据给定的URL、标题和数据显示对话框
 * openUrl: url 地址
 * titleName： 标题名称
 * width： 对话框宽度
 * height：对话框高度
 * id：对话框容器id
 * resizable： 是否尺寸可变
 */
function showDialog(openUrl, titleName, postData, width, height, id, resizable) {
    var lid;
    //当跳转页面点击出现缓存标识
    var smFlag;

    if (id == undefined) {
        lid = "#ajaxDialog";
    } else {
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
    if (openUrl.indexOf("?") > 0) {
        openUrl = openUrl + "&rand=" + a;
    } else {
        openUrl = openUrl + "?rand=" + a;
    }

    jQuery(lid).load(openUrl, postData,
	    function(response, status, xhr) {
	        // 关闭加载提示
	        closeLoadingScreen({
	            loadingScreenId: "loadingScreen",
	            loadingMessageId: "loadingMessage"
	        });
	
	        //处理Session过期
	        var sessionExpried = handleExpiredSession(xhr);
	        if (!sessionExpried) {
	            // 如果成功，显示对话框
	            if (status == "success") {
	                var option = {
	                    title: "对话框名",
	                    closeText: "关闭",
	                    width: 800,
	                    height: 500,
	                    bgiframe: true,
	                    //解决下拉框遮盖div的bug
	                    modal: true,
	                    close: function(event, ui) {
	                        $(this).remove();
	                        lid = lid.substring(1);
	                        parentDom.append("<div id='" + lid + "' style='display:none'></div>");
	                        showDialogFocus();
	                    }
	                };
	
	                if (width == undefined) width = 800;
	                if (height == undefined) height = 500;
	                if (resizable == undefined) resizable = true;
	                option.title = titleName;
	                option.width = width;
	                option.height = height;
	                option.resizable = resizable;
	                $(lid).dialog(option);
	                //		    $("#dialogFrame").attr("height", $(lid).attr("height"));
	            } else if (status == "error" || status == "timeout") {
	            	setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",270);
	            }
	        }
	    });
}

/* 翻页处理开始  */
/**
 * 翻页处理
 */
function jumpToPage(pageno, id, isAddHistory, vid) {
    var form = $("[name='conditionForm']");
    if (form == null) {
        showMsg("提示", "检索条件表单对象不存在！");
        return;
    }
    var href = form.attr("action");
    href = href + "?pageNo=" + pageno;
    var senSave = form.find("[name='senSave']");
    senSave.val("true");
  //Author:chang_xuewen
  //Date : 2013/7/9 15:30
  //[BUG]0033461 ADD BEGIN
    if($("#BIGviewFrame").length>0){
    	initview(0);
    }
  //[BUG]0033461 ADD END
    loadPanelPage(href, form.serializeObject(), id, isAddHistory, isAddHistory, vid);
}

/**
 * 处理列表分页的跳转
 */
function jumpTo(id, type, pageno, formName, mainId, data, isAddHistory) {
    if (formName == undefined) {
        var form = $("#" + id).find("[name=pagingform]");
    } else {
        var form = $("[name=" + formName + "]");
    }

    if (form == null) {
        showMsg("提示", "页面缺少分页表单");
        return;
    } else if (pageno == null) {
        showMsg("提示", "页号为空！");
        return;
    }

    var href = form.attr("action");
    href = href + "?pageNo=" + pageno;
    loadPanel(href, data, mainId, {isAddHistory: isAddHistory});
}

/**
 * 处理列表分页的跳转_提示相关信息
 */
function jumpToPageNo(pageno, totalPageCnt, id) {
    var form = $("[name='conditionForm']");
    if (form == null) {
        showMsg("提示", "检索条件表单对象不存在！");
        return;
    }
    if (pageno == null || pageno == '') {
        showMsg("提示", "页号为空！");
        return;
    }
    var reg = new RegExp("^[0-9]*$");
    if (!reg.test(pageno) || pageno == "0") {
        showMsg("提示", "输入内容不正确，请输入1的整数！");
        return;
    }
    if (pageno > totalPageCnt) {
        showMsg("提示", "输入页号大于页面提供的最大值！");
        return;
    }
    var href = form.attr("action");
    href = href + "?pageNo=" + pageno;
    var senSave = form.find("[name='senSave']");
    senSave.val("true");

    //Author:chang_xuewen
    //Date : 2013/7/9 15:30
    //[BUG]0033461 ADD BEGIN
      if($("#BIGviewFrame").length>0){
      	initview(0);
      }
    //[BUG]0033461 ADD END
    loadPanelPage(href, form.serializeObject(), id);
}
/* 翻页处理结束  */

/*
 * 弹出窗用翻页
 * */
function jumpToPageNew(pageno, id, isAddHistory, vid) {
    var form = $("[name='conditionFormNew']");
    if (form == null) {
        showMsg("提示", "检索条件表单对象不存在！");
        return;
    }
    var href = form.attr("action");
    href = href + "?pageNo=" + pageno;
    var senSave = form.find("[name='senSave']");
    senSave.val("true");
    if($("#BIGviewFrame").length>0){
    	initview(0);
    }
    loadPanelPage(href, form.serializeObject(), id, isAddHistory, isAddHistory, vid);
}

function jumpToNew(id, type, pageno, formName, mainId, data, isAddHistory) {
    if (formName == undefined) {
        var form = $("#" + id).find("[name=pagingformNew]");
    } else {
        var form = $("[name=" + formName + "]");
    }

    if (form == null) {
        showMsg("提示", "页面缺少分页表单");
        return;
    } else if (pageno == null) {
        showMsg("提示", "页号为空！");
        return;
    }

    var href = form.attr("action");
    href = href + "?pageNo=" + pageno;
    loadPanel(href, data, mainId, {isAddHistory: isAddHistory});
}

function jumpToPageNoNew(pageno, totalPageCnt, id) {
    var form = $("[name='conditionFormNew']");
    if (form == null) {
        showMsg("提示", "检索条件表单对象不存在！");
        return;
    }
    if (pageno == null || pageno == '') {
        showMsg("提示", "页号为空4！");
        return;
    }
    var reg = new RegExp("^[0-9]*$");
    if (!reg.test(pageno) || pageno == "0") {
        showMsg("提示", "输入内容不正确，请输入1的整数！");
        return;
    }
    if (pageno > totalPageCnt) {
        showMsg("提示", "输入页号大于页面提供的最大值！");
        return;
    }
    var href = form.attr("action");
    href = href + "?pageNo=" + pageno;
    var senSave = form.find("[name='senSave']");
    senSave.val("true");
      if($("#BIGviewFrame").length>0){
      	initview(0);
      }
    loadPanelPage(href, form.serializeObject(), id);
}
/*
 * 弹出窗用翻页结束
 * */

/**
 * 检索框动态加载刷新页面
 */
function condition() {
    var container = $("#toggleBlock");
    if (container == null) return;
    var conditionValue = container.find("[name='conditionValue']").val();
    if ('on' == conditionValue) {
        //    	container.removeClass("container-on");
        //    	container.addClass("container-off");
        //动态加载button属性
        var myTr1 = $("#buttonBlock");             
        myTr1.attr("style", "color:#464646;border:0px;BACKGROUND-IMAGE:url(../images/61.jpg);width:77px;height:25px;margin-top:3px;cursor:pointer;");
        jQuery(jQuery("[name='conditionForm']").find(".moreCondition")).each(function() {
            jQuery(this).show();
        });
    } else {
        //        container.removeClass("container-off");
        //        container.addClass("container-on");
        //动态加载button属性
        var myTr1 = $("#buttonBlock");
        myTr1.attr("style", "color:#464646;border:0px;BACKGROUND-IMAGE:url(../images/6.jpg);width:77px;height:25px;margin-top:3px;cursor:pointer;");
        jQuery(jQuery("[name='conditionForm']").find(".moreCondition")).each(function() {
            jQuery(this).hide();
        });
    }

}

/**
 * 显示或者隐藏高级检索区域IE6IE8兼容版本下的代码
 */
function showTr(myTr, myThis) {
    var container = $("#toggleBlock");
    var mTr = $('#' + myTr);
    var mThis = $(myThis);

    if (container == null) {
        return;
    }

    if (mTr.css('display') == 'none') {
        mTr.show();
        //myThis.innerText='普通搜索';
        mThis.css('background', 'url(../images/61.jpg)');

        //显示或者隐藏高级检索区域标识符
        var conditionValue = container.find("[name='conditionValue']");
        conditionValue.val('on');
    } else {
        mTr.hide();
        //myThis.innerText='高级搜索';
        mThis.css('background', 'url(../images/6.jpg)');

        //显示或者隐藏高级检索区域标识符
        var conditionValue = container.find("[name='conditionValue']");
        conditionValue.val('off');
    }
}

//Author:chang_xuewen
//Date : 2013/6/9 15:30
//[BUG]0012699 ADD BEGIN
/**
* @author chang_xuewen
* 点击list>tr时缩进左边栏
*/
function toggleLeft(){
	var os = $("#dynamicContent");
	var ps = $("#menuW+td");//MENU缩进条
	if(ps.hasClass("paneSeperator_open")){
		ps.addClass("paneSeperator_closed");
	    ps.removeClass("paneSeperator_open");
		os.css("width","100%");
		$(".menuContent").addClass("off");
	}	
}
/**
* @author chang_xuewen
* 右边list向上滑动，形成列表
*/
function goList(){
	//在table外包裹一层div控制大小
	if($("#listcon").length>0){
	}else
		$("#tblid").wrap("<div id='listcon'></div>");
	
	$list = $("#listcon");
	//强制页面显示垂直方向滚动条，避免FF首次展示没有滚动条的BUG
	$list.css("overflow","-moz-scrollbars-vertical");
	//强制页面显示垂直方向滚动条，避免ie首次展示没有滚动条的BUG
	$list.css("overflow-y","auto");
	$list.css("PADDING-LEFT","10px");
	if($list.height()>200){
		$list.animate({height:"220px"},800);
		//调节表头宽度
		$("#conditionForm").css("width","98.7%");
	}else
		$list.animate({height:"100%"},800);
	//获取检索form并使其固定在顶部
	$form = $("#conditionForm>table tr.blockHeader");
	$form.css("position","fixed");
	
	if(isIE6()){
		//禁用dynamicContent的竖向滚动条
		$("#dynamicContent").css("overflow-y","hidden");
	}	
	//延时隐藏条件框	
	$search = $("#conditionForm .conditionRow");
	$search.delay(400).slideUp(300);		
	//鼠标滑过条件框的表头将条件框显示
	$(".blockHeader").mouseover(function(){
		if($list.height()>20){
			$search.slideDown(300);
		}		
	});
	$("#tblid").mouseout(function(){
		$search.slideUp(300);
	});
	//增加拖动条
	var mover = "<div id='mover' class='mover' style='horizontal-align: top; '> </div>";
	if($("#mover").length>0){
	}else $("#dynamicContent").append(mover);	
	//增加slider，点击后隐藏list
	var slider = "<div id='slider' class='updown_up' onclick='toggleList(\"#listcon\");' style='horizontal-align: top; '> </div>";
	if($("#slider").length>0){
	}else $("#dynamicContent").append(slider);	
	//绑定拖动slider后list高度动态改变
	bindResize(document.getElementById('mover'),
			document.getElementById('listcon'));
}


/**
* @author yang_jianbo@2014-5-9
* 右边list向上滑动，形成列表
*/
function emrGoList(){
	//在table外包裹一层div控制大小
	if($("#listcon").length>0){
	}else
		$("#tblid").wrap("<div id='listcon'></div>");
	
	$list = $("#listcon");
	/*//强制页面显示垂直方向滚动条，避免FF首次展示没有滚动条的BUG
	$list.css("overflow","-moz-scrollbars-vertical");
	//强制页面显示垂直方向滚动条，避免ie首次展示没有滚动条的BUG
	$list.css("overflow-y","auto");
	$list.css("PADDING-LEFT","10px");
	if($list.height()>200){
		$list.animate({height:"220px"},800);
		//调节表头宽度
		$("#conditionForm").css("width","98.7%");
	}else
		$list.animate({height:"100%"},800);*/
	//获取检索form并使其固定在顶部
	$form = $("#conditionForm>table tr.blockHeader");
	$form.css("position","fixed");
	
	if(isIE6()){
		//禁用dynamicContent的竖向滚动条
		$("#dynamicContent").css("overflow-y","hidden");
	}	
	//延时隐藏条件框	
	/*$search = $("#conditionForm .conditionRow");
	$search.delay(400).slideUp(300);		
	//鼠标滑过条件框的表头将条件框显示
	$(".blockHeader").mouseover(function(){
		if($list.height()>20){
			$search.slideDown(300);
		}		
	});
	$("#tblid").mouseout(function(){
		$search.slideUp(300);
	});*/
	//增加拖动条
	
}

/**
* @author chang_xuewen
* list折叠toggle方法
*/
function toggleList(id){
	var $slider = $("div#slider");
	$(id).slideToggle(400);
	if($slider.hasClass("updown_up")){
		$slider.removeClass("updown_up")
		.addClass("updown_down");
	}else{
		$slider.removeClass("updown_down")
		.addClass("updown_up");
	}
}
/**
* @author chang_xuewen
* 全屏显示主方法
* @param url
*/
function bigShow(tr,url,data){
	setLeftTR(tr);
	var trid = $(tr).parents("tr:first").attr("id");
	//判断是否进入了portlets视图
	if($("#portletViews").length>0){
//		hideSearch();
		showBigPortlet(tr,url,data);
		
	}else{
		// 1.左边MENU栏缩进，右边DIV全屏
		toggleLeft();
		// 2.list向上滑动，高度缩小, 检索栏鼠标经过时显示
		// 3.增加slider，点击收起list
		goList();		
		// 4.增加tr对应的detail页面
		if($("#BIGviewFrame").length>0){
			var $idcon = $("#BIGviewFrame").find("#moreTabs");
			$idcon.css({"width":"100%","min-height":"100%"});		
			
			var liSize = $idcon.children("ul").find("li").children("a").size();
			
			var changTabID;
			if(typeof(data.examReportSn)!="undefined"){
				if(data.orderSn==''){
					changTabID = data.labExamID;
				}else{
					changTabID = data.orderSn+'_'+data.examReportSn+'_'+data.itemSn;					
				}
			}else{
				changTabID = data.orderSn==''?data.labExamID:data.orderSn;
			}
			
			if (changeTabs(tr, changTabID, "#moreTabs", liSize)) {
		        return;
		    }
			var title = isNull(data.name)
            ? (isNull(data.otherName) ? "选项卡": data.otherName)
                    : data.name;
            if(title.length > 4){
            	title = title.substring(0,4)+"...";
            }
			// 动态添加多个tab页
			$idcon.tabs("add", //添加add tabs事件
		                '#detailViewTab_' + liSize, // tabs的id 以及ul>li>a 对应的href的值
		                title); //tabs的name
			
		    showLoadingScreen({
		        loadingScreenId: "loadingScreen", 
		        loadingMessageId: "loadingMessage",
		        modal: true
		    });
		    $idcon.find('#detailViewTab_' + liSize).load(url,data,function(){
				// 关闭加载提示
                closeLoadingScreen({
                    loadingScreenId: "loadingScreen", 
                    loadingMessageId: "loadingMessage"
                });
                // 显示当前点击的医嘱tabs页
    		    $idcon.tabs({ 
    		    	selected : liSize
    		    });     
    		    
    		    $idcon.children("ul").find("li").children("a").addClass("parentli"); 
    		    // 为a增加title信息
    		    $idcon.children("ul").find("li:last-child").children("a").attr(
    		            "title",
    		            data.holdName == null ? data.holdOtherName
    		                    : data.holdName);
    		    // 判断UL的宽度决定显示多少个tab页 
    		    setULWidth("#moreTabs");
    		    $("#BIGviewFrame").find("#moreTabs>div").css("padding","0");
    		    //手术医嘱时,选择申请单tab或操作记录tab
    		    if($(".procedureTabs").length>0){
    		    	changeTab(trid,data);
    		    }
			});    
		    
		}else{			
			showLoadingScreen({
		        loadingScreenId: "loadingScreen", 
		        loadingMessageId: "loadingMessage",
		        modal: true
		    });
			var viewContainer = "<div id='BIGviewFrame' style='width: 100%; height: 100%;' > </div>";
			$("#dynamicContent").append(viewContainer);
			// 捕获容器
			var $viewContainer = $("#BIGviewFrame");			
			$viewContainer.load('../common/commonTab.html',function(){			
				var $idcon = $viewContainer.find("#moreTabs");
				$idcon.css({"width":"100%","min-height":"100%"});	
				$("#detailViewTab_0").load(url,data,function(){
	                // 显示名称设置
	                if(!isNull(data.name))
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
	    		    //手术医嘱时,选择申请单tab或操作记录tab
	    		    if($(".procedureTabs").length>0){	    	
	    		    	changeTab(trid,data);
	    		    	tr.parents("#listcon>table>tbody>tr:first").children().css({
	    		            "backgroundColor" : "#87CEFF",
	    		            "font-weight" : "bold"
	    		        });
	    		    }
	    		    
	    		    // 关闭加载提示
	                closeLoadingScreen({
	                    loadingScreenId: "loadingScreen", 
	                    loadingMessageId: "loadingMessage"
	                });
				}).css("height","100%");				
			});	   
			
		}
	}    
}


function portalBigShow(tr,url,data){
	emrSetLeftTR(tr);
	var trid = $(tr).parents("tr:first").attr("id");
	//判断是否进入了portlets视图
	if($("#portletViews").length>0){
//		hideSearch();
		showBigPortlet(tr,url,data);
	}else{
		// 1.左边MENU栏缩进，右边DIV全屏
		toggleLeft();
		// 2.list向上滑动，高度缩小, 检索栏鼠标经过时显示
		// 3.增加slider，点击收起list
		emrGoList();		
		// 4.增加tr对应的detail页面
		var $idcon = $("#portalDetail");
	    $idcon.load(url,data,function(){
	    	
	    });  
	}    
}

function changeTab(id,data)
{	
	var tid = "_"+id;
	var $t = $("#moreTabs").find(".procedureTabs:visible ul li:#procedureRequest");
	var $m = $("#moreTabs").find(".procedureTabs:visible ul li:#procedureOperation");
	var reqNum = $t.index()-1;
	var opeNum = $m.index()-1;
	var num = 0;
	if(data.procedureRequest == 'true'){
		num = reqNum/2;
    	$("#"+tid).tabs({ selected: num }); 
    }else if(data.procedureOperation == 'true'){
    	num = opeNum/2;
    	$("#"+tid).tabs({ selected: num });
    }
}
/**
* @author chang_xuewen
* slider动态拖拽改变div大小
* @param el 柄元素
* @param con 被控制的内容元素
*/
function bindResize(el,con){
	//鼠标的 Y 轴坐标
	y = 0;
	$(el).mousedown(function(e){
		y = el.offsetHeight + 180;
		//IE下setCapture捕获所有鼠标事件
		el.setCapture ? (el.setCapture(),
			el.onmousemove = function(ev){
				mouseMove(ev || event);
			},
			el.onmouseup = mouseUp
		) : (
			//绑定事件
			$(document).bind("mousemove",mouseMove).bind("mouseup",mouseUp)
		)
		//防止默认事件发生
		e.preventDefault();
	});
	//移动事件
	function mouseMove(e){
		if(e.clientY<470){
			var h = e.clientY - y;
			$(con).css("height",h);
		}		
	}
	//停止事件
	function mouseUp(){
		el.releaseCapture ? (
			//释放焦点
			el.releaseCapture(),
			//移除事件
			el.onmousemove = el.onmouseup = null
		) : (
			//卸载事件
			$(document).unbind("mousemove", mouseMove).unbind("mouseup", mouseUp)
		)
	}
}

/**
* @author chang_xuewen
*  动态处理表格背景色
*/
function beautyTable(id){
	var flag = "";
	if (id==null || id == undefined){
		var $tr = $('#tblid tbody>tr:not(.tabletitle,.page_line)');//需要手动给下面翻页的tr增加class为page_line
		$('#tblid tbody>tr:odd').addClass("odd");
		$('#tblid tbody>tr:even').addClass("even");
	}else{
		var $tr = $('#'+id+' tbody>tr:not(.tabletitle,.page_line)');//需要手动给下面翻页的tr增加class为page_line
		$('#'+id+' tbody>tr:odd').addClass("odd");
		$('#'+id+' tbody>tr:even').addClass("even");
	}
	
	$tr.mouseover(function(){
		if($(this).hasClass("odd")){
			flag = "odd";
			$(this).removeClass("odd");
			$(this).addClass("mouseover");
		}else{
			flag = "even";
			$(this).removeClass("even");
			$(this).addClass("mouseover");
		}
	});
	$tr.mouseout(function(){
		$(this).removeClass("mouseover");
		if(flag == "odd"){
			$(this).addClass("odd");
		}else{
			$(this).addClass("even");
		}		
	});
//	$tr.click(function(){
//		$(this).removeClass("mouseover odd even");
//		$(this).addClass("selected").siblings().removeClass("selected");
//	});
}

/*$Author :chang_xuewen
 * $Date : 2013/08/07 16:20
 * $[BUG]0035740 ADD BEGIN*/
function showTabs(obj,url,data)
{
	var $idcon = $("#control").find("#moreTabs");
	$idcon.css({"width":"100%","min-height":"100%"});
	var liSize = $idcon.children("ul").find("li").children("a").size();
	if (changeTabs(obj, data.orderSn, "#moreTabs", liSize)) {
        return;
    }
	var title = data.name;
//	alert(title);
    if(title.length > 4){
    	title = title.substring(0,4)+"...";
    }
	// 动态添加多个tab页
	$idcon.tabs("add", //添加add tabs事件
                '#detailViewTab_' + liSize, // tabs的id 以及ul>li>a 对应的href的值
                title); //tabs的name
	showLoadingScreen({
        loadingScreenId: "loadingScreen", 
        loadingMessageId: "loadingMessage",
        modal: true
    });
    $idcon.find('#detailViewTab_' + liSize).load(url,data,function(){
    	$idcon.find("img").remove();
		// 关闭加载提示
        closeLoadingScreen({
            loadingScreenId: "loadingScreen", 
            loadingMessageId: "loadingMessage"
        });
        // 显示当前点击的医嘱tabs页
	    $idcon.tabs({ 
	    	selected : liSize
	    });     	    
	    $idcon.children("ul").find("li").children("a").addClass("parentli"); 
	    // 为a增加title信息
	    $idcon.children("ul").find("li:last-child").children("a").attr(
	            "title",
	            data.holdName == null ? data.holdOtherName
	                    : data.holdName);
	}); 	
}
/* $[BUG]0035740 ADD END*/
//[BUG]0012699 ADD END

function logoMoving()
{
	if(isIE6())
	{
		$("#header").hide();
		
		setTimeout('$("#downArrShow").children().first().animate({"height":"5px"},230);',150);
	}
	else
	{
		$("#header").toggle(400,function()
		{
			$("#downArrShow").children().first().animate({"height":"5px"},240);
		});
	}
}

function logoMovingDown()
{
	if(isIE6())
	{
		$("#downArrShow").children().first().animate({"height":"0px"},230,function()
		{
			setTimeout('$("#header").show()',100);
		});
	}
	else
	{
		$("#downArrShow").children().first().animate({"height":"0px"},240,function()
		{
			$("#header").toggle(400);
		});
	}
}

function upArrShow()
{
	if(isSafari())
	{
		if(showOverFirst != null)
		{
			$("#upArrShow").children().stop(true);
			
			$("#upArrShow").children().animate({"height":"3px"},100);
		}
		else
		{
			showOverFirst = 0;
		}
	}
	else
	{
		$("#upArrShow").children().stop(true);
		
		$("#upArrShow").children().animate({"height":"3px"},100);
	}
}

function upArrHide()
{
	$("#upArrShow").children().stop(true);
	
	$("#upArrShow").children().animate({"height":"0px"},100);
}

function isSafari()
{
	if($.browser.safari)
	{
		return true;
	}
	
	return false;
}
/**
 * emr3.0集成显示或者隐藏高级检索区域IE6IE8兼容版本下的代码
 */
/* $Author :liu_hongjie
 $Date : 2014/8/19 10:26$
[BUG]0047658 MODIFY BEGIN */
function showTr1(myTr, myThis) {
    var container = $("#toggleBlock");
    var mTr = $('#' + myTr);
    var mThis = $(myThis);

    if (container == null) {
        return;
    }

    if (mTr.css('display') == 'none') {
        mTr.show();
        //myThis.innerText='普通搜索';
        mThis.css('background', 'url(../images/177.png)');
        //显示或者隐藏高级检索区域标识符
        var conditionValue = container.find("[name='conditionValue']");
        conditionValue.val('on');
    } else {
        mTr.hide();
        //myThis.innerText='高级搜索';
        mThis.css('background', 'url(../images/17.png)');
        //显示或者隐藏高级检索区域标识符
        var conditionValue = container.find("[name='conditionValue']");
        conditionValue.val('off');
    }
}
//[BUG] 0047658 MODIFY END



/*
 * Author: yu_yzh
 * ggfw迁移 BEGIN
 * Date： 2015/7/14
 * */
/**
 * tr的class设置selectable，点击该行，最前端的checkbox进行选择/取消选择操作
 * @param topId 表头checkbox的id
 * @param checkboxsName 要实现全选操作的checkbox的name
 * @param flag true表示最后一个td点击时也会改变该行checkbox的选中状态
 * */
function selectable(topId, checkboxsName, flag){
//	$("tr.selectable").each(function(){
//		var p = this;
//		$(this).children().first().nextAll().click(function(){
//			var cells = p.cells;
//			var td = cells[0];
//			var inputs = td.getElementsByTagName("input");
//			var input = inputs[0];
//			if(input.type == "checkbox"){
//				var value = input.checked;
//				input.checked = !value;
//			}
//			checkboxUpdate(topId, checkboxsName);
//		});
//	});
	$("tr.selectable").each(function(){
		var p = this;
		var checkTd = p.cells[0];
		var inputs = checkTd.getElementsByTagName("input");
		var input = inputs[0];
		var length = p.cells.length;
		if(!flag){
			length = p.cells.length - 1;
		} 
		for(var i = 0; i < length; i++){
			
			var td = p.cells[i];
			if(i == 0){
				td.onclick = function(){
					checkboxUpdate(topId, checkboxsName);
				};
			} else {
				td.onclick = function(){
					if( input.type == "checkbox"){
						var value = input.checked;
						input.checked = !value;
					}
					checkboxUpdate(topId, checkboxsName);
				};
			}
		}
	});
	
	checkboxSelectAll(topId, checkboxsName);
}
/**
 * 点击行选择取消，最后一个td点击不响应
 * 已经废弃，统一使用selectable()方法
 * */
function selectableWithoutLast(topId, checkboxsName){
	$("tr.selectable").each(function(){
		var p = this;
		var checkTd = p.cells[0];
		var inputs = checkTd.getElementsByTagName("input");
		var input = inputs[0];
		for(var i = 0; i < p.cells.length - 1; i++){
			var td = p.cells[i];
//			alert(td.tagName);
			if(i == 0){
				td.onclick = function(){
					checkboxUpdate(topId, checkboxsName);
				};
			} else {
				td.onclick = function(){
					if( input.type == "checkbox"){
						var value = input.checked;
						input.checked = !value;
					}
					checkboxUpdate(topId, checkboxsName);
				};
			}
		}
	});
	checkboxSelectAll(topId, checkboxsName);
}

/**
 * checkbox全选/取消全选
 * @param topId 表头checkbox的id
 * @param checkboxsName 要实现全选操作的checkbox的name
 * */
function checkboxSelectAll(topId, checkboxsName){
	$("#" + topId).click(function(){
		if($(this).attr("checked")){
			$("input[name='" + checkboxsName + "']").attr("checked",true);
		} else {
			$("input[name='" + checkboxsName + "']").attr("checked",false);
		}
	    
	});
}

/**
 * checkBox所有项全选择自动把表头的也选择，反之有取消将表头的选择取消
 * @param topId 表头checkbox的id
 * @param checkboxsName 表中checkbox的name
 * */
function checkboxUpdate(topId, checkboxsName){
	var inputTop = document.getElementById(topId);
	var inputCheckboxs = document.getElementsByName(checkboxsName);
	var sum = 0;
	for(var i = 0; i < inputCheckboxs.length; i++){
		var checkbox = inputCheckboxs[i];
		if(checkbox.checked){
			sum++;
		}
	}
	if(sum == inputCheckboxs.length){
		inputTop.checked = true;
	} else {
		inputTop.checked = false;
	}
	

/*	$("tr.selectable").each(function(){
		var p = this;
		for(var i = 0; i < p.cells.length; i++){
			var td = p.cells[i];
			td.onclick = function(){
				var inputTop = document.getElementById(topId);
				var inputCheckboxs = document.getElementsByName(checkboxsName);
				var sum = 0;
				for(var j = 0; j < inputCheckboxs.length; j++){
					var checkbox = inputCheckboxs[j];
					if(checkbox.checked){
						sum++;
					}
					if(sum == inputCheckboxs.length){
						inputTop.checked = true;
					} else {
						inputTop.checked = false;
					}
//					alert("11");
				}
			};
		}
	});*/
	
}

/*
 * ggfw迁移 END 
 */