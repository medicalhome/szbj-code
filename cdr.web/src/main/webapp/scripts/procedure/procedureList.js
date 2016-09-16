$(function() {
    var availableTags = [{
        label: "李",
        value: "李莉"
    },
    {
        label: "wang",
        value: "wangli"
    }];
    $("#visitDoctorName").autocomplete({
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

function search(url, formName, id) {
    var operationDateFromStr = $("#operationDateFromStr").val();
    var operationDateToStr = $("#operationDateToStr").val();
    if (operationDateToStr != "" && operationDateFromStr > operationDateToStr) {
        showMsg("提示", "手术日期  开始时间不能>结束时间！");
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
    if(isDateStringCheck(operationDateFromStr) == "true" || isDateStringCheck(operationDateToStr) == "true")
    {
    	return;
    }
    //$[BUG]0039831 ADD END
    parent.loadPanel(form.attr("action"), form.serializeObject(), id);
}