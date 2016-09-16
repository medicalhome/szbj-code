function showHelpDialog(openUrl, titleName, postData, width, height, id, resizable) {
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
	                    width: 850,
	                    height: 500,
	                    bgiframe: true,
	                    //解决下拉框遮盖div的bug
	                    modal: true,
	                    close: function(event, ui) {
	                        $(this).remove();
	                        lid = lid.substring(1);
	                        parentDom.append("<div id='" + lid + "' style='display:none'></div>");
	                        showDialogFocus();
	                    },
		                open: function (event, ui) {
		                    var $dialog = $(this);
		                    var atext = $(".ui-dialog-titlebar-close").replaceWith('<div class="ui-xlgwr" style="width:47px;float:right;margin-top:9px;margin-right:5px;cursor:pointer;"><span id="ext" class="ui-icon ui-icon-zoomin tabEnter" style="float:left;margin-right:12px" title="改变窗口大小">extlink</span> <span id="tt_close" style="float:right" class="tabEnter ui-icon ui-icon-closethick">close</span></div>');
		                    //绑定最大化
		                    $(".ui-xlgwr>span#ext").toggle(function(){
		                    	//判断浏览器是否支持window.screen判断浏览器是否支持screen
		                        if (window.screen) {
		                            var bigw = $(window).width();   
		                            var bigh = $(window).height();  
		                            $dialog.dialog({
		                                width: bigw * 1.01,
		                                height: bigh
		                            });
		                            
		                            $("html,body").animate({scrollTop: 0}, 800); // 设置浏览器的滚动条位置
		                            
		                            // 禁用浏览器滚动条
		                            $("html,body").css("overflow","hidden");
		                            
		                            $dialog.dialog("option","position","top");
		                            
		                            // 左边高度自适应
		                            $("#accordionHelp").height(bigh * 0.94);

		                            // 左边高度自适应
		                            $dialog.css("overflow","hidden");
		                            
		                            // 右边高度自适应
		                            $("#help_slow").height(bigh * 0.94);
		                        } else {
		                            $dialog.dialog({
		                                width: 850,
		                                height: 500
		                            });
		                        }
		                        $(".ui-xlgwr>span#ext").removeClass("ui-icon-zoomin");
		                        $(".ui-xlgwr>span#ext").addClass("ui-icon-zoomout");
		                        
		                        // 放大时，调整图片宽度
		                        adjustPic($dialog.width());
		                        
		                    },function(){
		                        $dialog.dialog(
		                        {
		                            width: 850,
		                            height: 500
		                        });
		                        // 开启浏览器滚动条
	                            $("html,body").css("overflow","auto");
	                            
	                            // 左边高度自适应
	                            $("#accordionHelp").height("465px");
	                            
	                            // 右边高度自适应
	                            $("#help_slow").height("465px");
		                        
		                        $dialog.dialog("option","position","center");
		                        $(".ui-xlgwr>span#ext").removeClass("ui-icon-zoomout");
		                        $(".ui-xlgwr>span#ext").addClass("ui-icon-zoomin");
		                        
		                        // 缩小时，调整图片宽度
		                        if(isIE6()){
			                        adjustPic("850");
		                        }
		                        else{
		                        	adjustPic($dialog.width());
		                        }
		                    });
		                    //绑定关闭
		                    $(".ui-xlgwr>span.ui-icon-closethick").click(function(){
		                        $dialog.dialog("close");
		                    });    
		                    
		                },
		    	     resize: function(event, ui) { var $dialog = $(this); adjustPic($dialog.width()); }

	                };
	
	                if (width == undefined) width = 850;
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

// 调整图片大小
function adjustPic($dialogWidth){
	$("#help_slow td>img").css("width",$dialogWidth-$("#menuWHelp").width()-40);
}

//$Author:chang_xuewen
//$Date : 2013/12/20 15:00
//$[BUG]0040878 ADD BEGIN
function cutName(obj,limitLength){
	var len = limitLength;
	$(obj).each(function(){
		var tpTxt = $(this).text();
		if(tpTxt.length > len && len != undefined){
			$(this).attr("title",tpTxt);
			tpTxt = tpTxt.substring(0,limitLength)+"...";
			$(this).text(tpTxt);
		}
	});
}
//$[BUG]0040878 ADD END