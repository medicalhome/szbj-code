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

    var visitDateFrom = $("#visitDateFrom").val();
    var visitDateTo = $("#visitDateTo").val();
    if (visitDateTo != "" && visitDateFrom > visitDateTo) {
        showMsg("提示", "就诊（入院） 日期 开始时间不能>结束时间！");
        return;
    }
    var dischargeDateFrom = $("#dischargeDateFrom").val();
    var dischargeDateTo = $("#dischargeDateTo").val();
    if (dischargeDateTo != "" && dischargeDateFrom > dischargeDateTo) {
        showMsg("提示", "出院日期 结束时间不能>开始时间！");
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
    if(isDateStringCheck(visitDateFrom) == "true" || isDateStringCheck(visitDateTo) == "true" 
    	|| isDateStringCheck(dischargeDateFrom) == "true" || isDateStringCheck(dischargeDateTo) == "true")
    {
    	return;
    }
    //$[BUG]0039831 ADD END
    parent.loadPanel(form.attr("action"), form.serializeObject(), id);
}