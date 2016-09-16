$(function() {
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
    var diagnosisDateFromStr = $("#diagnosisDateFromStr").val();
    var diagnosisDateToStr = $("#diagnosisDateToStr").val();
    if (diagnosisDateToStr != "" && diagnosisDateFromStr > diagnosisDateToStr) {
        showMsg("提示", "诊断日期  开始时间不能>结束时间！");
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
    if(isDateStringCheck(diagnosisDateFromStr) == "true")
    {
    	return;
    }
    if(isDateStringCheck(diagnosisDateToStr) == "true")
    {
    	return;
    }
    //$[BUG]0039831 ADD END
    parent.loadPanel(form.attr("action"), form.serializeObject(), id);
}