$(function() {
    var availableTags = [{
        label: "李",
        value: "李莉"
    },
    {
        label: "wang",
        value: "wangli"
    }];
    $("#orderPersonName").autocomplete({
        source: availableTags
    });
    //调用父js的检索框动态加载刷新页面
    parent.condition();

    //调用父js的时间格式校验
    $(".datepicker").bind("blur", parent.isDateString);

    //添加页面文本框的回车检索
    $("#conditionForm input").keyup(function(event) {
        if (event.keyCode == 13) {
            search('', 'conditionForm');
        }
    });

});

/**
 * 通过Ajax的方式提交给定名称的表单
 */
function search(url, formName, id) {
	var today = new Date().format("yyyy-MM-dd");
    var orderTimeFrom = $("#orderTimeFrom").val();
    var orderTimeTo = $("#orderTimeTo").val();
    if(orderTimeFrom!=""&&orderTimeFrom>today){
    	showMsg("提示","开嘱日期 开始时间不能>当前日期！");
    	return;
    }
    if (orderTimeTo != "" && orderTimeFrom > orderTimeTo) {
        showMsg("提示", "开嘱日期 开始时间不能>结束时间！");
        return;
    }

    var stopTimeFrom = $("#stopTimeFrom").val();
    var stopTimeTo = $("#stopTimeTo").val();
    if (stopTimeTo != "" && stopTimeFrom > stopTimeTo) {
        showMsg("提示", "停嘱时间 结束时间不能>开始时间！");
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
    if(isDateStringCheck(orderTimeFrom) == "true" || isDateStringCheck(orderTimeTo) == "true" 
    	|| isDateStringCheck(stopTimeFrom) == "true" || isDateStringCheck(stopTimeTo) == "true")
    {
    	return;
    }
    //$[BUG]0039831 ADD END
    //console.log(form.serializeArray());
    parent.loadPanel(form.attr("action"), form.serializeObject(), id);
}

//   //点击页码get加载每页数据
//	function jumpToPage(id, pageno)
//	{
//		var form = $("#" + id).find("[name=pagingform]");
//		if(form == null)
//		{
//			alert("页面缺少分页表单");
//			return;
//		}
//		else if(pageno == null|| pageno == '')
//		{
//			alert("页号为空！");
//			return;
//		}
//		
//		var conditionValue= $("#conditionValue").val();
//		$("#senSave").val("true");
//		
//		var href = form.attr("action");
//		href = href + "?pageNo=" + pageno
//		+"&conditionValue="+conditionValue
//		+"&senSave=true";
//		loadPanel(href);
//		
//	}
//	//message
//	function jumpToPageNo(id, pageno,totalPageCnt)
//	{
//		var form = $("#" + id).find("[name=pagingform]");
//		if(form == null)
//		{
//			alert("页面缺少分页表单");
//			return;
//		}
//		else if(pageno == null || pageno == '')
//		{
//			alert("页号为空！");
//			return;
//		}	
//		else if(pageno > totalPageCnt)
//		{
//			alert("输入页号大于页面提供的最大值！");
//			return;
//		}
//
//		var conditionValue= $("#conditionValue").val();
//		$("#senSave").val("true");
//		var senSave=$("#senSave").val();
//		
//		var href = form.attr("action");
//		href = href + "?pageNo=" + pageno
//		+"&conditionValue="+conditionValue
//		+"&senSave=true";
//		loadPanel(href);
//	}
