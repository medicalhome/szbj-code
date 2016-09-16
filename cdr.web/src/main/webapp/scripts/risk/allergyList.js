$(function() {
    $(".datepicker").bind("blur", parent.isDateString);
    //添加页面文本框的回车检索
    $("#conditionForm input").keyup(function(event) {
        if (event.keyCode == 13) {
            search('', 'conditionForm');
        }
    });
	
	logical();
});

function search(url, formName, id) {
    var allergicStartDate = $("#allergicStartDate").val();
    var allergicStopDate = $("#allergicStopDate").val();
    if (allergicStopDate != "" && allergicStartDate > allergicStopDate) {
        showMsg("提示", "过敏开始日期  开始时间不能>结束时间！");
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
    if(isDateStringCheck(allergicStartDate) == "true" || isDateStringCheck(allergicStopDate) == "true")
    {
    	return;
    }
    //$[BUG]0039831 ADD END
    parent.loadPanel(form.attr("action"), form.serializeObject(), id);
}