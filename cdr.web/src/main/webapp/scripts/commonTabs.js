$(document).ready(function() {
	$("#tabs-animate").toggle(function() {
		$("#dd_detailContent").hide(1000);
		$("#tabs-animate").animate({
			"margin-left" : "2px"
		}, 1, function() {
			$("#moreTabs").animate({
				"width" : "97%",
				"margin-left" : "2px"
			}, 1000,function(){
				// 调整检验详细标题栏
				adjustLabDetailTitles();
			});
			$("#tabs-animate").addClass("paneSeperator_closed");
			$("#tabs-animate").removeClass("paneSeperator_open");
		});
	}, function() {
		$("#moreTabs").animate({
			"width" : "69.8%"
		}, 800,function(){
			// 调整检验详细标题栏
			adjustLabDetailTitles();
		});
		$("#dd_detailContent").show(1000,function(){setDialogCss();setLeftTDWidth();});
		$("#tabs-animate").animate({
			"margin-left" : "2px"
		}, function() {
			$("#tabs-animate").removeClass("paneSeperator_closed");
			$("#tabs-animate").addClass("paneSeperator_open");
		});
	});
});
//调整检验详细标题栏，TAB页过多时出现滚动条
function adjustLabDetailTitles(){
	$("div[name='labInnerTitle']").each(function(){
    	var width=0;
    	$(this).find("ul li").each(function(){
    		width += $(this).width();
    	});
    	if(width>$("#moreTabs").width()){
    		$(this).width(width);     	    		
    	}else{
    		$(this).width("100%");
    	}
    });
}

