//$Author :jin_peng
// $Date : 2013/02/22 14:17$
// [BUG]0014055 MODIFY BEGIN
function showMsg(title, msg, specicalWidth, isClose, func) {
    var w = 330;   

    // 如果调用该提示方法时传入提示框宽度则使用给定的宽度
    if (specicalWidth != undefined) {
        w = specicalWidth;
    }

    $("#alertMessage").html("<div id='msgBody' style='height:80px;line-height:80px;text-align:center;'>" + msg + "</div>").dialog({
        title: title,
        autoOpen: true,
        modal: true,
        width: w,
        bgiframe: true,
        close: function(event, ui) {
            if (isClose) {
                eval(func);
            }
        }
    });
}
//[BUG]0014055 MODIFY END
function confirmMsg(title, msg, callback) {
    $("#confirmMessage").html("<div id='msgBody' style='height:70px;line-height:70px;text-align:center;'>" + msg + "</div>").dialog({
        title: title,
        autoOpen: true,
        modal: true,
        width: 330,
        bgiframe: true,
        buttons: {
            "确认": function() {
                callback.call();
                $(this).dialog("close");
            },
            "取消": function() {
                $(this).dialog("close");
            }
        }
    });
}

function confirmMsg1(title, msg, callback) {
    $("#confirmMessage").html("<div id='msgBody' style='word-break:break-all;word-wrap: break-word;height:70px;line-height:70px;text-align:center;'>" + msg + "</div>").dialog({
        title: title,
        autoOpen: true,
        modal: true,
        width: 350,
        bgiframe: true,
        buttons: {
            "确认": function() {
                callback(true);
                $(this).dialog("close");
            },
            "取消": function() {
                $(this).dialog("close");
            }
        }
    });
}

function showPatientListView() {
    if ($('#patientList').is(":hidden")) {
        if ($("#pl_tabs").length == 0) {
            $('#patientList').show();
            loadPatientPanel('../patient/index.html', '', '#patientList');
        } else $('#patientList').show();
    } else $('#patientList').hide();
}

/**
 * 使用指定的URL和数据局部刷新页面
 */
function loadPatientPanel(url, data, id) {
    var lid;
    if (id == undefined) {
        lid = "#dynamicContent";
    } else {
        lid = id;
    }
    //解决IE浏览器缓存问题
    var a = Math.random();
    if (url.indexOf("?") > 0) {
        url = url + "&rand=" + a;
    } else {
        url = url + "?rand=" + a;
    }

    jQuery(lid).load(url, data,
    function(response, status, xhr) {
        //处理Session过期
        var sessionExpried = handleExpiredSession(xhr);

        // 如果session没有过期
        if (!sessionExpried) {
            if (status == "success") {
                //$Author : wu_jianfeng
                // $Date : 2012/10/31 10:49$
                // [BUG]0010884 MODIFY BEGIN
                $('#patientList').css("width", "650px");
                // [BUG]0010884 MODIFY END
                //$('#patientList').css('height', '480px');
                $('#patientList').css("top", 89);
                $('#patientList').css("left", $(".navHeader").offset().left);
                $('#patientList').bgiframe({
                    opacity: false
                });
            } else if (status == "error" || status == "timeout") {
            	setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
            }
        }
    });
}

//$Author :jin_peng
// $Date : 2012/09/13 17:49$
// [BUG]0009712 ADD BEGIN
/**
 * 修改密码功能与公用服务集成
 * */
function modifyPw(userSn, modifyPasswordUrl) {
    var h = (window.screen.availHeight - 55 - 670) / 2;

    var w = (window.screen.availWidth - 5 - 1024) / 2;

    var test = window.open(modifyPasswordUrl + "?userNo=" + userSn, null, "height=670,width=1024,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,left=" + w + ",top=" + h);
}
//[BUG]0009712 ADD END
//$Author :jin_peng
// $Date : 2012/10/31 17:38$
// [BUG]0010836 ADD BEGIN
/**
 * 退出CDR系统
 * */
function exitSystem(exitKindsName) {
    // 制作退出表单
    $('<form style="display:none;" name="loginForm" id="loginForm" ' + 'action="../login/loginExited.html" method="post"> ' + '<input name="exitKindsName" id="exitKindsName" type="text" value="' + exitKindsName + '"/></form>').appendTo("body");

    // 退出表单提交
    setTimeout("$('#loginForm').submit()", 0);
}

// 是否使用键盘刷新或关闭页面标识
var isKeyUp = false;

// 键盘触发关闭刷新浏览器
function keyClosed(exitKindsFlag) {
    // 当用户使用键盘关闭浏览器时情况
    if ((window.event.altKey && window.event.keyCode == 115)) {
        //alert("closed");
        // 设置键盘关闭浏览器标识为是
        isKeyUp = true;

        // 退出cdr系统并记录用户退出日志
        closedExitSystem(exitKindsFlag);
    }
    // 当用户使用键盘刷新浏览器情况
    else if (window.event.keyCode == 116) {
        // 设置键盘关闭浏览器标识为是
        isKeyUp = true;
    }
}

// 在页面被卸载前触发浏览器关闭或刷新
function buttonClosed(exitKindsFlag) {
    // 当用户点击关闭按钮关闭浏览器情况
    if (((window.event.clientX > document.body.clientWidth - 50) && (window.event.clientY < -93)) && isKeyUp == false) {
        // 退出cdr系统并记录用户退出日志
        closedExitSystem(exitKindsFlag);
    } else {
        // 设置键盘关闭浏览器标识为否
        isKeyUp = false;
    }
}

// 关闭浏览器情况退出系统
function closedExitSystem(exitKindsFlag) {
    // 记录用户退出日志
    window.location.href = "../cdr/j_spring_security_logout?exitKindsName=" + exitKindsFlag;
}
//[BUG]0010836 ADD END
//$Author :jin_peng
//$Date : 2012/11/27 18:06$
//[BUG]0011883 MODIFY BEGIN
//$Author :jin_peng
// $Date : 2012/11/26 16:12$
// [BUG]0011923 ADD BEGIN
var cm;

/**
 * 调整页面布局
 * */
function setPageSize() {
    //页面可见区域宽度
    var availWidth = document.body.offsetWidth - 20;

    if (cm == availWidth) {
        return false;
    } else {
        cm = availWidth;
    }

    var leftWidth = new Number(($("#lDiv").css("width")).replace("px", ""));

    //当页面可见区域宽度小于给定值时出现滚动条并保持给定值的宽度
    if (availWidth < 960) {
        availWidth = 960;
    }

    $("#headTab").css("width", availWidth);

    $("#fDiv").css("width", availWidth - leftWidth);

    $("#dyContent").css("width", availWidth + 20);

    $("#normalTab").css("width", availWidth + 20);

    $("#container").css("width", availWidth + 20);
}
//[BUG]0011923 ADD END
//[BUG]0011883 MODIFY END
//$Author :binzhang
//$Date : 2012/12/4 13:30$
//[BUG]0012098 MODIFY BEGIN
function sh() {
    if ($("#header").width() >= 1256) {
        $("#a1").show();
        $("#a2").hide();
    } else if ($("#header").width() < 1256) {
        if ($("#a2").find(".viewNav").size() < 5) {
            $("#a1").show();
            $("#a2").hide();
        } else {
            $("#a2").show();
            $("#a1").hide();
        }
    }
}

//[BUG]0012098 MODIFY END

function loadDataCompare(url, lid) {
    //解决IE浏览器缓存问题
    var a = Math.random();
    if (url.indexOf("?") > 0) {
        url = url + "&rand=" + a;
    } else {
        url = url + "?rand=" + a;
    }
    jQuery(lid).load(url,
    function(response, status, xhr) {
        //处理Session过期
        var sessionExpried = handleExpiredSession(xhr);
        // 如果session没有过期
        if (!sessionExpried) {
            //如果状态是error或者timeout, 显示错误对话框
            if (status == "error" || status == "timeout") {
            	setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
            }
        }
    });

}

function dra(param1, param2) {
    $("#catalog").accordion();
    $("#catalog li").draggable({
        appendTo: "body",
        helper: "clone"
    });
    $("#" + param1 + " ol").droppable({
        activeClass: "ui-state-default",
        hoverClass: "ui-state-hover",
        accept: ":not(.ui-sortable-helper)",
        drop: function(event, ui) {
            $(this).find(".placeholder").remove();
            var hz = ui.draggable.text().split('|');
            $(this).find("#c").remove();
            $(this).find("#b").remove();
            $(this).find("#d").remove();
            $("<li id='b' style='padding-left:5px;'> </li>").text(hz[0]).appendTo(this);
            $("<li id='c' style='padding-left:5px;'> </li>").text(hz[2]).appendTo(this);
            $("<li id='d' style='padding-left:5px;'> </li>").text(hz[3]).appendTo(this);
            $("." + param2).val(hz[1]);
        }
    }).sortable({
        items: "li:not(.placeholder)",
        sort: function() {
            $(this).removeClass("ui-state-default");
        }
    });
}

$(function() {
    var d = new Array("cart,hzoi", "cart2,hzoi2", "cart3,hzoi3", "cart4,hzoi4", "cart5,hzoi5", "cart6,hzoi6");
    for (var i = 0; i < d.length; i++) {
        dra(d[i].split(',')[0], d[i].split(',')[1]);
    }
});

$(function() {
    $(".toggler").hide();
    function runEffect() {
        $("#effect").hide();
        $("#effect").toggle("blind", 500);
    };
    var n = 0;
    $("#button").click(function() {
        if (n % 2 == 0) {
            $(".toggler").show();
            if ($("#hz21").css("display") != "none") {
                $("#dyContent").hide();
                $("#normalTab").hide();
            }
        } else {
            $(".toggler").hide();
            $("#dyContent").show();
            $("#normalTab").show();
        }
        n++;
        runEffect();
        return false;
    });
});

function searchDataDetail(id, url, id1, id2, id3, id4, id5, id6) {
    $("#hzxxo").hide();
    $("#hzxxt").hide();
    $("#hzxxth").hide();
    $("#hzxxf").hide();
    $("#hzxxfi").hide();
    $("#hzxxs").hide();
    var myh = window.screen.availHeight;
    if (id == "2" && $(".hzoi").val() != "" && $(".hzoi2").val() != "") {
        loadDataCompare($(".hzoi").val() + '?flag=tabs1', id1);
        loadDataCompare($(".hzoi2").val() + '?flag=tabs2', id2);
        setTimeout("$('#hzxxo').fadeIn()", 1000);
        setTimeout("$('#hzxxt').fadeIn()", 1000);
        $("#hzxxo").css("height", (myh - 30) + "px");
        $("#hz22").hide();
    }
    if (id == "4" && $(".hzoi3").val() != "") {
        loadDataCompare($(".hzoi3").val() + '?flag=tabs3', id3);
        loadDataCompare($(".hzoi4").val() + '?flag=tabs4', id4);
        loadDataCompare($(".hzoi5").val() + '?flag=tabs5', id5);
        loadDataCompare($(".hzoi6").val() + '?flag=tabs6', id6);
        setTimeout("$('#hzxxth').fadeIn()", 1000);
        setTimeout("$('#hzxxf').fadeIn()", 1000);
        setTimeout("$('#hzxxfi').fadeIn()", 1000);
        setTimeout("$('#hzxxs').fadeIn()", 1000);
        $("#hzxxth").css("height", (myh / 2 - 15) + "px");
        $("#hzxxfi").css("height", (myh / 2 - 15) + "px");
        $("#hzxxf").css("height", (myh / 2 - 15) + "px");
        $("#hzxxs").css("height", (myh / 2 - 15) + "px");
        $("#hz21").hide();
    }

}

//$Author : wu_jianfeng
//$Date : 2013/3/13 18:06$
//[BUG]0014531 ADD BEGIN
function showMutexesOrder(obj,mutexesOrderType, mutexesOrderSn,mutexesOrderName,patientSn,id,event) {
	var data = {"orderSn":mutexesOrderSn,"name":mutexesOrderName,'holdName':mutexesOrderName,'patientSn':patientSn,'menuPartFlag':'true','width':'70%'};
	if(data.name != undefined && data.name.length > 4){
		data.name = data.name.substring(0,4);
	}
	if($("#BIGviewFrame").length>0){
		if (mutexesOrderType == "drugorder") bigShow(obj,'../drug/detail_'+data.orderSn+'.html?flag=tabs',data);
		else if (mutexesOrderType == "careorder") bigShow(obj,'../order/care_' + data.orderSn + '.html?flag=tabs', data);
	    else if (mutexesOrderType == "generalorder") bigShow(obj,'../order/general_' + data.orderSn + '.html?flag=tabs', data);
	    else if (mutexesOrderType == "treatmentorder") bigShow(obj,'../order/treatment_' + data.orderSn + '.html?flag=tabs', data);
	    else if (mutexesOrderType == "consultationorder") bigShow(obj,'../order/consultation_' + data.orderSn + '.html?flag=tabs', data);
	    else if (mutexesOrderType == "procedureorder") bigShow(obj,'../order/procedure_' + data.orderSn + '.html?flag=tabs', data);
	    else if (mutexesOrderType == "examorder") bigShow(obj,'../exam/detail.html?flag=tabs&orderSn=' + mutexesOrderSn, data);
	    else if (mutexesOrderType == "laborder") bigShow(obj,'../lab/detail.html?flag=tabs&orderSn=' + mutexesOrderSn, data);
	}else if($("#ntr").length>0){
		stopBubble(event);
		if (mutexesOrderType == "drugorder") showTabs(obj,'../drug/detail_'+data.orderSn+'.html?flag=tabs',data);
        else if (mutexesOrderType == "careorder") showTabs(obj,'../order/care_' + data.orderSn + '.html?flag=tabs', data);
        else if (mutexesOrderType == "generalorder") showTabs(obj,'../order/general_' + data.orderSn + '.html?flag=tabs', data);
        else if (mutexesOrderType == "treatmentorder") showTabs(obj,'../order/treatment_' + data.orderSn + '.html?flag=tabs', data);
        else if (mutexesOrderType == "consultationorder") showTabs(obj,'../order/consultation_' + data.orderSn + '.html?flag=tabs', data);
        else if (mutexesOrderType == "procedureorder") showTabs(obj,'../order/procedure_' + data.orderSn + '.html?flag=tabs', data);
        else if (mutexesOrderType == "examorder") showTabs(obj,'../exam/detail.html?flag=tabs&orderSn=' + mutexesOrderSn,data);
        else if (mutexesOrderType == "laborder") showTabs(obj,'../lab/detail.html?flag=tabs&orderSn=' + mutexesOrderSn, data);
	}else{
		if (mutexesOrderType == "drugorder") addMoreTabs(obj,'../drug/detail_' + data.orderSn + '.html?flag=tabs', data,id);
	    else if (mutexesOrderType == "careorder") addMoreTabs(obj,'../order/care_' + data.orderSn + '.html?flag=tabs', data,id);
	    else if (mutexesOrderType == "generalorder") addMoreTabs(obj,'../order/general_' + data.orderSn + '.html?flag=tabs', data,id);
	    else if (mutexesOrderType == "treatmentorder") addMoreTabs(obj,'../order/treatment_' + data.orderSn + '.html?flag=tabs', data,id);
	    else if (mutexesOrderType == "consultationorder") addMoreTabs(obj,'../order/consultation_' + data.orderSn + '.html?flag=tabs', data,id);
	    else if (mutexesOrderType == "procedureorder") addMoreTabs(obj,'../order/procedure_' + data.orderSn + '.html?flag=tabs', data,id);
	    else if (mutexesOrderType == "examorder") addMoreTabs(obj,'../exam/detail.html?flag=tabs&orderSn=' + mutexesOrderSn, data,id);
	    else if (mutexesOrderType == "laborder") addMoreTabs(obj,'../lab/detail.html?flag=tabs&orderSn=' + mutexesOrderSn, data,id);
	}
    
}
//[BUG]0014531 ADD END	 

$(document).ready(function() {
    if ($.browser.version == '6.0') {
        $(".toggler").css("left", "77%")
    }
    var map = {
        "2": "hz2",
        "4": "hz4"
    };
    $("#bdys").bind("change",
    function() {
        var divId = map[this.value];
        $("#" + divId).show().siblings().hide();
    });
    $("#button1").toggle(function() {
        $("#hz2").hide();
        $("#hz21").show();
        $("#hz4").hide();
        $("#hz22").show();
        $("#dyContent").hide();
        $("#normalTab").hide();
        $(".bgHeader1").hide();
        $("#top2").hide();
        $("#top3").hide();
        $(this).val("最小化");
        $("#opener321").hide();
        var myw = window.screen.availWidth;

        $("#effect").animate({
            color: "#000",
            width: myw - 14
        },
        1000);
        searchDataDetail($("#bdys").val(), '', '#hzxxo', '#hzxxt', '#hzxxth', '#hzxxf', '#hzxxfi', '#hzxxs');
        var WshShell = new ActiveXObject('WScript.Shell');
        WshShell.SendKeys('{F11}');
        $(".toggler").css("margin-top", "0px");
        $(".toggler").css("width", "0px");
        $("#blk").css("height", "0px");
        if ($.browser.version == '6.0') {
            $(".toggler").css("left", "0");
        }
    },
    function() {
        if ($("#bdys").val() == 2) {
            $("#hz2").show();
            $("#hz21").hide();
        } else if ($("#bdys").val() == 4) {
            $("#hz4").show();
            $("#hz22").hide();
        }
        $(this).val("最大化");
        $("#opener321").show();

        if ($.browser.version == '6.0') {
            $("#effect").animate({
                color: "#000",
                width: 290
            },
            0);
            $(".toggler").css("left", "77%");
        } else {
            $("#effect").animate({
                color: "#000",
                width: 290
            },
            1000);
        }
        $("#dyContent").show();
        $("#normalTab").show();
        $(".bgHeader1").show();
        $("#top2").show();
        $("#top3").show();
        $(".toggler").css("margin-top", "30px");
        $(".toggler").css("width", "80px");
        var WshShell = new ActiveXObject('WScript.Shell');
        WshShell.SendKeys('{F11}');
    });

});

// $Author:wu_jianfeng
// $Date : 2012/11/21 11:21
// $[BUG]0011156 MODIFY BEGIN
function addFavFolder(patientSn) {
    showDialog('../patient/patientFolder_' + patientSn + '.html', '患者收藏夹', {},
    500, 450, null, false);
}
// $[BUG]0011156 MODIFY END

/**
 * 主页面点击视图转换时加载不同视图
 */

function changeView(vid, patientSn, data, continueGoto, isAddHistory) {
    if (vid == "#visitIndexView") {
        loadPanel('../visit/visitIndexViewPart_' + patientSn + '.html', {'patientSn': patientSn},
        	'#dyContent', {vid: vid, isAddHistory: isAddHistory}, {loadSuccess: loadPanelSuccess});
    }
    if (vid == "#outpatientView") {
        loadPanel('../visit/outpatientViewPart.html', {'patientSn': patientSn},
        	'#dyContent', {vid: vid, isAddHistory: isAddHistory}, {loadSuccess: loadPanelSuccess});
    }
    if (vid == "#normal") {
        loadPanel('../visit/menuPart_' + patientSn +'.html', {'patientSn': patientSn,'inpatientNo': inpatientNo,'outpatientNo': outpatientNo},
        	'#dyContent', {vid: vid, continueGoto: continueGoto, isAddHistory: isAddHistory},  {beforeLoad: beforeLoadPanel, loadSuccess: loadPanelSuccess});
    } else if (vid == "#timer") {
        loadPanel('../timer/list_' + patientSn + '.html', '', '#dyContent', 
        	{vid: vid, isAddHistory: isAddHistory}, {loadSuccess: loadPanelSuccess});
    } else if (vid == "#inpatientView") {
        loadPanel('../inpatient/list_' + patientSn + '.html', data, '#dyContent', 
        	{vid: vid, isAddHistory: isAddHistory}, {loadSuccess: loadPanelSuccess});
    } else if (vid == "#feedback") {
        showDialog('../visit/feedback.html', '意见反馈栏', {
            userId: patientSn,
            userName: data
        },
        800, 500);
    }
}

/**
 * 异步加载门诊视图
 * @param {} url : 请求门诊视图的url地址
 * @param {} visitSn：就诊内部序列号
 * @returns {} 
 */
function changeOutpatientView(url, visitSn) {
	loadPanel(url, {'visitSn': visitSn},'#dyContent',{vid: "#outpatientView", isAddHistory: "1"}, {loadSuccess: loadPanelSuccess});
}

/**  
 * 异步加载成功后，调用此方法
 * @param {} loadArgs 包含url，data，id
 *    url： 请求url地址
 *    data： 文本或者json对象
 *    id： 异步加载的dom元素id
 * @param {} history 历史记录信息
 * @returns {} 
 */
function loadPanelSuccess(loadArgs, history) {
	var data, vid, continueGoto;
	data = loadArgs.data;
	history = history || {};
	vid = history.vid;
	continueGoto = history.continueGoto;
	
	if (vid != undefined) {
	    if (vid == "#normal") {
	        if (continueGoto == undefined) {
	            loadPanel('../visit/part_' + data.patientSn + '.html', undefined, undefined,  {isAdd: "0"});
	        } else if (continueGoto == '#continueGoto_diagnosis') {
	            loadPanel('../diagnosis/list_' + data.patientSn + '.html', undefined, undefined,  {isAdd: "0"});
	        } else if (continueGoto == '#continueGoto_drug') {
	            $("#accordion").accordion("activate", 4);
	            loadPanel('../drug/list_' + data.patientSn + '.html', undefined, undefined,  {isAdd: "0"});
	        } else if (continueGoto == '#continueGoto_standingOrder') {
	            $("#accordion").accordion("activate", 4);
	            loadPanel('../drug/list_' + data.patientSn + '.html', {
	                'temporaryFlag': 1
	            },
	            undefined, undefined,  {vid:"#normal", isAdd: "0"});
	        } else if (continueGoto == '#continueGoto_stardOrder') {
	            $("#accordion").accordion("activate", 4);
	            loadPanel('../drug/list_' + data.patientSn + '.html', {
	                'temporaryFlag': 0
	            },
	            undefined, undefined,  {vid:"#normal", isAdd: "0"});
	        } else if (continueGoto == '#continueGoto_examination') {
	            $("#accordion").accordion("activate", 5);
	            loadPanel('../exam/list_' + data.patientSn + '_.html', undefined, undefined,  {isAdd: "0"});
	        } else if (continueGoto == '#continueGoto_lab') {
	            $("#accordion").accordion("activate", 6);
	            loadPanel('../lab/list_' + data.patientSn + '_.html', undefined, undefined,  {isAdd: "0"});
	        } else if (continueGoto == '#continueGoto_procedure') {
	            loadPanel('../procedure/list_' + data.patientSn + '.html', undefined, undefined,  {isAdd: "0"});
	        } else if (continueGoto == '#continueGoto_documentation') {
	            $("#accordion").accordion("activate", 8);
	            loadPanel('../doc/list_' + data.patientSn + '.html', undefined, undefined,  {isAdd: "0"});
	        } else if (continueGoto == '#continueGoto_noDrug') {
	            $("#accordion").accordion("activate", 4);
	            loadPanel('../order/list_' + data.patientSn + '.html', undefined, undefined,  {isAdd: "0"});
	        }
	    }

		changeViewLabelStatus(vid);
	}
}

/**  
 * 异步加载前，调用此方法
 * @param {} loadArgs 包含url，data，id
 *    url： 请求url地址
 *    data： 文本或者json对象
 *    id： 异步加载的dom元素id
 * @param {} history 历史记录信息
 * @returns {} 
 */
function beforeLoadPanel(loadArgs, history) {
	var data, vid, continueGoto;
	data = loadArgs.data;
	history = history || {};
	vid = history.vid;
	continueGoto = history.continueGoto;
	
	if (vid != undefined) {
	    if (vid == "#normal") {
			g_firstLoad = false;
		}
	}
}

/**
 * 主页面点击视图转换时并加载完成后更新视图转换功能链接状态
 */
function changeViewLabelStatus(vid) {
    $(".viewNav").each(function(i) {
        if (!$(this).hasClass("noVisited")) {
            if ($(this).attr("name") == vid) {
                $(this).html("<font color='#000000'><b>" + $(this).html() + "</b></font>");
            } else {
                $(this).html("<font color='#000000'>" + $(this)[0].innerText + "</font>");
            }
        }
    });

    //$Author :jin_peng
    // $Date : 2013/05/29 15:20$
    // [BUG]0031929 ADD BEGIN
    // 改变历史记录后确定标题的显示
    $("#a2").find("li").each(function(i)
    {
    	if($(this).find("a").find("b").size() > 0)
		{
    		var _no = Math.ceil((i + 1) / parseInt(countDis));
    		
    		var current_res = current; 
	    	
	    	if(_no == 1 && $("._1").css("display") != "none")
    		{
	    		$("#scrollshow").closest('.part_c').prev('.part_t').find('.l').attr('key', 0).click();
    		}
	    	else if(_no == 2 && $("._2").css("display") != "none")
	    	{
	    		$("#scrollshow").closest('.part_c').prev('.part_t').find('.r').attr('key', 0).click();
    		}
	    	
	    	current = current_res;
		}
    });

    // [BUG]0031929 ADD END
}

//只在本页面调用
function changeNav(continueGoto) {

    var active = $('#accordion').accordion('option', 'active');

    if (continueGoto == 'continueGoto_drug') {
        if (active != 4) {
            $("#accordion").accordion("activate", 4);
        }
    } else if (continueGoto == 'continueGoto_noneDrug') {
        if (active != 4) {
            $("#accordion").accordion("activate", 4);
        }
    } else if (continueGoto == 'continueGoto_examination') {
        if (active != 5) {
            $("#accordion").accordion("activate", 5);
        }
    } else if (continueGoto == 'continueGoto_lab') {
        if (active != 6) {
            $("#accordion").accordion("activate", 6);
        }
    } else if (continueGoto == 'continueGoto_documentation') {
        if (active != 8) {
            $("#accordion").accordion("activate", 8);
        }
    }

}


