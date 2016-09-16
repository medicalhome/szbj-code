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

/**
 * 如果报告为已召回仅可查看医嘱跟踪信息
 */
function showConsultationDetailDialog(requestNo,patientSn,patientLid,patientDomain,visitSn,consultationType,orgName,orgCode,urgencyName,orderDeptName,requestDoctorName,orderSn,tr,type,consultationName){
	
	if(orderSn == ""){//当检查仅有报告没有医嘱时,使用reportSn_itemSn唯一确定
		$(tr).attr("id",requestNo);		
	}
	if(type==1){
		//type 为1，是从住院视图调用，为2是从综合视图调用,3是从摘要视图调用
		//showDialog('../exam/detail.html','检查详细', {examReportSn:examReportSn,patientSn:patientSn,itemClass:itemClass,examinationRegion:examinationRegion,examinationItem:examinationItem,itemSn:itemSn,orderSn:orderSn});
		justShow('../exam/detail.html',tr,{examReportSn:examReportSn,patientSn:patientSn,itemClass:itemClass,examinationRegion:examinationRegion,examinationItem:examinationItem,itemSn:itemSn,orderSn:orderSn,withdrawFlag:withdrawFlag,otherName:examinationMethodName,holdOtherName:examinationMethodName,name:examinationItemName,holdName:examinationItemName,labExamID:examReportSn+itemSn});
	}else if(type==2){
		bigShow(tr,'../consultation/detail.html',{requestNo:requestNo,patientSn:patientSn,patientLid:patientLid,patientDomain:patientDomain,visitSn:visitSn,orgName:orgName,orgCode:orgCode,consultationType:consultationType,urgencyName:urgencyName,orderDeptName:orderDeptName,orderSn:orderSn,otherName:consultationName,holdOtherName:consultationName,labExamID:requestNo});
	}else if(type==3){
		showBigPortlet(tr,'../exam/detail.html',{examReportSn:examReportSn,patientSn:patientSn,itemClass:itemClass,examinationRegion:examinationRegion,examinationItem:examinationItem,itemSn:itemSn,orderSn:orderSn,withdrawFlag:withdrawFlag,otherName:examinationMethodName,holdOtherName:examinationMethodName,name:examinationItemName,holdName:examinationItemName,labExamID:examReportSn+itemSn});
	}
}