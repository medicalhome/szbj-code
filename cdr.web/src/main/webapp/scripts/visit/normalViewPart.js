var portletOrders = new Array();

function getPortletOrders() {
    var portlets = $(".portlets").sortable({
        connectWith: ".portlets"
    }).toArray();
    var orders = new Array();

    $(portlets).find(".portlet").each(function() {
        orders[orders.length] = $(this).attr("id");
    });
    return orders;
}
//Author:chang_xuewen
//Date : 2013/7/9 15:30
//[BUG]0033461 ADD BEGIN
/**
 * 重写综合视图bigshow
 * @author chang_xuewen
 */
//全局临时flag，判定是否第一次进入大图
var f = 1;
//全局临时flag，判定是否第一次点击某业务的tr
var clicktimes = 1;
function showBigPortlet(tr,url,data){
	// portlet的head中放大图标
	var $icon = $( ".portlet-header .portlet-header-icon" );
	var $portlet = $(tr).parents( ".portlet:first" );
	var portletId = $portlet.attr("id");
	
	//传入tr索引
	var trind = $(tr).index()-1;
	//如果有放大图标，则放大该portlet，缩小其他
	if($icon.hasClass("ui-icon-zoomin"))
	{
		toggleView("more");
		setOtherPortletsLess(portletId);
		showPortletMore(portletId,trind);
	}					
	
	if(isIE6()){
		//禁用dynamicContent的竖向滚动条
		$("#dynamicContent").css("overflow-y","hidden");
	}	
	// 4.增加tr对应的detail页面
	if($("#BIGviewFrame").length>0){
		var $idcon = $("#BIGviewFrame").find("#moreTabs");
		$idcon.css({"width":"100%","min-height":"100%"});
		
		var liSize = $idcon.children("ul").find("li").children("a").size();
		var changTabID;
		if(typeof(data.examReportSn)!="undefined"){
			changTabID = data.orderSn+'_'+data.examReportSn+'_'+data.itemSn;
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
		    //调用隐藏搜索框
			hideSearch(portletId);
		    //绑定mover，滑动list
			setlist(clicktimes);
			clicktimes++;
			
			// 判断UL的宽度决定显示多少个tab页 
		    setULWidth("#moreTabs");
		    $("#BIGviewFrame").find("#moreTabs>div").css("padding","0");
		});
	}else{
		showLoadingScreen({
	        loadingScreenId: "loadingScreen", 
	        loadingMessageId: "loadingMessage",
	        modal: true
	    });		
		//首次点击tr后清理
		initview(2,portletId);
		//增加拖动条
		var mover = "<div id='mover' class='mover' style='horizontal-align: top; '> </div>";
		$portlet.append(mover);
		
		// 2.list向上滑动，高度缩小, 检索栏鼠标经过时显示
		// 3.增加slider，点击收起list
		var slider = "<div id='slider' class='updown_up' onclick='toggleList(\".bigportlets .portlet-content\");' style='horizontal-align: top; '> </div>";
		$portlet.append(slider);	
		//绑定mover，滑动list
		setlist(1);	
		//调用隐藏搜索框
		hideSearch(portletId);
		//展示详细页
		// 1.左边MENU栏缩进，右边DIV全屏
		parent.toggleLeft();
		
		var viewContainer = "<div id='BIGviewFrame' style='width: 100%; height: 100%;' > </div>";
		$portlet.append(viewContainer);	
	
		// 捕获容器
	    var $viewContainer = $("#BIGviewFrame");
	    $viewContainer.find("#moreTabs").css({
	    	"margin":"30px 0 0 0px","overflow-y":"hidden","overflow-x":"auto"});
	    $viewContainer.load('../common/commonTab.html',function(){
	    	var $idcon = $viewContainer.find("#moreTabs");
	    	$idcon.css({"width":"100%","min-height":"100%"});				
			$("#detailViewTab_0").load(url,data,function(){
				//do something    	首次进入大图页面时向下滚动到列表
		    	if(f==1){
		    		$("html,body").animate({scrollTop:180},1000);
		    	}
		    	f++;
		    	clicktimes=1;
		    	if(f==100){
		    		f=2;
		    	}    					
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
			    // 关闭加载提示
	            closeLoadingScreen({
	                loadingScreenId: "loadingScreen", 
	                loadingMessageId: "loadingMessage"
	            });
			}).css("height","100%");				
	    });
	}
}
function setlist(type){
	var list = document.getElementById("listcon");
	var $list = $(list);
	//强制页面显示垂直方向滚动条，避免FF首次展示没有滚动条的BUG
	$list.css("overflow","-moz-scrollbars-vertical");
	//强制页面显示垂直方向滚动条，避免ie首次展示没有滚动条的BUG
	$list.css("overflow-y","auto");
	if($list.height()>200 && type == 1){
		$list.animate({height:"220px"},800);
		//调节表头宽度
		$("#conditionForm").css("width","98.7%");
	}
	
	//绑定拖动slider后list高度动态改变
	parent.bindResize(document.getElementById('mover'),
			document.getElementById('listcon'));
}
function hideSearch(portletId){
	//获取检索form并使其固定在顶部
	$form = $("#listId#conditionForm>table tr.blockHeader");
	$form.css("position","fixed");
	//延时隐藏条件框
	$search = $("#"+portletId).find("#conditionForm .conditionRow");
	$search.delay(400).slideUp(300);		
	//鼠标滑过条件框的表头将条件框显示
	$("#"+portletId).find(".portlet-header-content").on("mouseover",function(event){
		if($("#listcon").height()>20){
			$search.slideDown(200);
		}		
	});
	$("#tblid").on("mouseout",function(){
		$search.slideUp(300);
	});
}

/**
 * 初始话portlet高度，清理页面上不需要显示的内容
 * @author chang_xuewen
 */
function initview(type,pid){
	var $con = $("#"+pid).find(".portlet-content");
	var $head = $("#"+pid).find(".portlet-header-content");
	var $mover = $("#mover");
	var $slider = $("#slider");
	var $viewFrame = $("#BIGviewFrame");
	$mover.remove();
	$slider.remove();	
	if(type == '0'){
		$mover.remove();
		$slider.remove();
		$viewFrame.remove();
	}
	if(type == '1'){//页面掉转到摘要视图，显示均匀的220px
		if($con.is(":hidden")){//如果当前的portlet被slideToggle为隐藏，则修改成显示,否则将导致无法显示的bug
			$con.show();
		}
		f=1;
		$con.height(220);
		$(".portlet").find(".portlet-content").height(220);
		$viewFrame.remove();
	}else if(type == '2'){//摘要视图中，直接点击放大按钮；或者列表视图中点击其他列表的小portlet，再次点击本视图的小portlet，初始化高度
		$(".portlet").find(".portlet-content").height(28);
		$con.height("100%");
		$(".smallportlet.portlet-content").height(28);
		$(".smallportlet.portlet-content").css("overflow-y","hidden");
		$(".portlet-header-content").unbind("mouseover");
	}else if(type == '3'){//3.列表视图中点击其他列表的小portlet，缩小当前视图的portlet
		if($con.is(":hidden")){//如果当前的portlet被slideToggle为隐藏，则修改成显示,否则将导致无法显示的bug
			$con.show();
		}		
		$con.height(28);
		$con.css("overflow-y","hidden");
		$(".portlet-header-content").unbind("mouseover");
		$viewFrame.remove();
	}else if(type == '4'){//列表视图中点击其他列表的小portlet，再次点击本视图的小portlet，初始化高度
		$(".portlet").find(".portlet-content").height(28);
		$con.height("100%");
		$(".smallportlet.portlet-content").height(28);
		$(".smallportlet.portlet-content").css("overflow-y","hidden");
		$(".portlet-header-content").unbind("mouseover");
		$viewFrame.remove();
	}	
}

//[BUG]0033461 ADD END


function toggleView(viewType) {
    if (viewType == "orignal") {
        $(".portlets").show();
        $(".smallportlets-container").hide();
        $(".bigportlets").hide();
    } else {
        portletOrders = getPortletOrders();
        $(".portlets").hide();
        if ($(".portlet").length == 1) {
            $(".smallportlets-container").hide();
            $(".bigportlets").css("width", "100%").show();
        } else {
            $(".bigportlets").show();
            $(".smallportlets-container").show();
        }
    }
}
//Author:chang_xuewen
//Date : 2013/7/9 15:30
//[BUG]0033461 MODIFY BEGIN
function showPortletMore(portletId,trind) {
//[BUG]0033461 MODIFY END
    var $portlet = $("#" + portletId);
    var $bigContainer = $(".bigportlets");

    $portlet.find(".portlet-content .portlet-content-less").hide();
    $portlet.find(".portlet-content .portlet-content-more").addClass("dynamicContent").show();

    $portlet.find(".portlet-header .portlet-header-icon").removeClass("ui-icon-zoomin").addClass("ui-icon").addClass("ui-icon-newwin");

    $portlet.removeClass("smallportlet").removeAttr("style").addClass("bigportlet").appendTo($bigContainer).hide();
    //Author:chang_xuewen
    //Date : 2013/7/9 15:30
    //[BUG]0033461 MODIFY BEGIN
    loadPortletMore(portletId, "drop", "showLine",trind);
  //[BUG]0033461 MODIFY END
}

function setOtherPortletsLess(portletId) {
    var duration = 300;
    integratedSmall();
    
    $(".portlet").each(function() {
        var target = $(this).attr("id");
        if (target != portletId) {
            var $smallContainer = $(".smallportlets");

            $(this).find(".portlet-content .portlet-content-less").show();
            $(this).find(".portlet-content .portlet-content-more").hide();

            $(this).find(".portlet-header .portlet-header-icon").removeClass("ui-icon").removeClass("ui-icon-zoomin");

            duration += 150;

            $(this).animate({
                width: 140,
                height: 56
            },
            duration,
            function() {
                $(this).removeClass("bigportlet").removeAttr("style").addClass("smallportlet").appendTo($smallContainer);
                //Author:chang_xuewen
	            //Date : 2013/7/9 15:30
	            //[BUG]0033461 MODIFY BEGIN
                $(this).find(".portlet-header").bind("click",{pid:portletId}, function(e){ 
						//add by chang_xuewen
						//大portlet初始化为小portlet，删除不显示元素并且更改高度
						var id = e.data.pid;
						initview(3,id);
						setPortletMoreToLess();
						var $portlet = $( this ).parents( ".portlet:first" );
						var portletId = $portlet.attr("id");
						setPortletLessToMore(portletId); 
				});
                //[BUG]0033461 MODIFY END
            });
        }
    });
}

function setPortletMoreToLess() {
    var $bigportlet = $(".bigportlet");
    var $smallContainer = $(".smallportlets");
    
    integratedSmallClick($bigportlet);

    $bigportlet.find(".portlet-header .portlet-header-icon").removeClass("ui-icon").removeClass("ui-icon-newwin");

    $bigportlet.find(".portlet-content .portlet-content-less").show();
    $bigportlet.find(".portlet-content .portlet-content-more").hide();
    $bigportlet.find(".portlet-content .portlet-content-more").removeClass("dynamicContent").html("");

    $bigportlet.removeClass("bigportlet").removeAttr("style").addClass("smallportlet");

    $bigportlet.find(".portlet-header").bind("click",
    function() {
        setPortletMoreToLess();
        var $portlet = $(this).parents(".portlet:first");
        var portletId = $portlet.attr("id");
        setPortletLessToMore(portletId);
    });

    var sourcePortletId = $bigportlet.attr("id");
    var sourceIndex = -1;

    for (var index in portletOrders) {
        if (sourcePortletId == portletOrders[index]) {
            sourceIndex = index;
            break;
        }
    }

    if (sourceIndex == 0) {
        $bigportlet.insertBefore($(".smallportlets .smallportlet").eq(0));
    } else {
        $bigportlet.insertAfter($(".smallportlets .smallportlet").eq(sourceIndex - 1));
    }
    
    //Author:chang_xuewen
    //Date : 2013/7/9 15:30
    //[BUG]0033461 ADD BEGIN
    //每次点击其他小图标是，判定自身视图是否被slideToggle隐藏
    if($(".portlet-content").is(":hidden")){
		$(".portlet-content").show();
	}
    //[BUG]0033461 ADD END		
}

function setPortletLessToMore(portletId) {
	//Author:chang_xuewen
    //Date : 2013/7/9 15:30
    //[BUG]0033461 ADD BEGIN
	//added by chang_xuewen
	//初始化小的port到大的port
	initview(4,portletId);
	//[BUG]0033461 ADD END
    var $portlet = $("#" + portletId);
    var $bigContainer = $(".bigportlets");

    $portlet.find(".portlet-content .portlet-content-less").hide();
    $portlet.find(".portlet-content .portlet-content-more").addClass("dynamicContent").show();

    $portlet.find(".portlet-header .portlet-header-icon").removeClass("ui-icon-zoomin").addClass("ui-icon").addClass("ui-icon-newwin");

    $portlet.removeClass("smallportlet").removeAttr("style").addClass("bigportlet").appendTo($bigContainer);

    $portlet.find(".portlet-header").unbind("click");

    $portlet.hide();

    loadPortletMore(portletId, "blind");
}

function restorePortlets(portletId) {
    for (var index in portletOrders) {
        var target = portletOrders[index];
        $(".portlet").each(function() {
            if ($(this).attr("id") == target) {
                if (target == portletId) {
                    $(this).find(".portlet-header .portlet-header-icon").removeClass("ui-icon-newwin").addClass("ui-icon-zoomin");

                    $(this).find(".portlet-content .portlet-content-more").removeClass("dynamicContent");
                } else {
                    $(this).find(".portlet-header .portlet-header-icon").addClass("ui-icon").addClass("ui-icon-zoomin");
                }

                $(this).find(".portlet-header").unbind("click");

                $(this).removeClass("smallportlet").removeClass("bigportlet").removeAttr("style").appendTo($(".portlets"));

                return false;
            }
        });
    }

    $(".smallportlets").html("");
    $(".bigportlets").html("");

    $(".portlet-content-less").show();
    $(".portlet-content-more").hide();
    $(".portlet-content-more").html("");
}

/**
* 使用指定的URL和数据局部刷新页面
*/
//Author:chang_xuewen
//Date : 2013/7/9 15:30
//[BUG]0033461 MODIFY BEGIN
function loadPortletMore(portletId, effect, style,trind) {
//[BUG]0033461 MODIFY END
    var url = "";
    var data = {};
    var id = "#" + portletId + " .portlet-content .portlet-content-more";

    if (portletId == "portlet_visit") {
        url = "../visit/list_" + getPatientSn() + ".html";
    } else if (portletId == "portlet_diagnosis") {
        url = "../diagnosis/list_" + getPatientSn() + ".html";
    } else if (portletId == "portlet_longdrug") {
        url = "../drug/list_" + getPatientSn() + ".html";
        data = {
            'temporaryFlag': 1
        };
    } else if (portletId == "portlet_shortdrug") {
        url = "../drug/list_" + getPatientSn() + ".html";
        data = {
            'temporaryFlag': 0
        };
    } else if (portletId == "portlet_exam") {
        url = "../exam/list_" + getPatientSn() + "_" + ".html";
    } else if (portletId == "portlet_lab") {
        url = "../lab/list_" + getPatientSn() + "_" + ".html";
    }

    //解决IE浏览器缓存问题
    var a = Math.random();
    if (url.indexOf("?") > 0) {
        url = url + "&rand=" + a;
    } else {
        url = url + "?rand=" + a;
    }

    showLoadingScreen({
        loadingScreenId: "loadingScreen",
        loadingMessageId: "loadingMessage",
        modal: true
    });

    jQuery(id).load(url, data,
	    function(response, status, xhr) {
	        //如果异步加载数据成功
	        if (status == "success") {
	            $("#tableheader").hide();
	
	            var $bigPortlet = $(".bigportlet");
	            $bigPortlet.show();
	
	            setupDatePickerSetting();
	            // 初始化带有datepicker样式的日期输入框
	            setupDatePicker();
	            
	            integratedSmallLoad();

	            //Author:chang_xuewen
	            //Date : 2013/7/9 15:30   Date : 2013/11/27 10:30
	            //[BUG]0033461 ADD BEGIN   [BUG]0040070 MODIFY BEGIN 
	            //传递selected样式的tr
	            if(style=="showLine" && trind != -1 && trind != undefined){
	            	$("#tblid tbody>tr:eq("+trind+")").removeAttr("class").children().css({
		            	"backgroundColor" : "#87CEFF",
		            	"font-weight" : "bold"
		            }); // 当前点击的td加粗
	            }	            
	            //[BUG]0033461 ADD END    [BUG]0040070 MODIFY END 

	        }
	        //如果状态是error或者timeout, 显示错误对话框
	        else if (status == "error" || status == "timeout") {
	        	setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
	        }
	        // 关闭加载提示
	        closeLoadingScreen({
	            loadingScreenId: "loadingScreen",
	            loadingMessageId: "loadingMessage"
	        });
	    });
}