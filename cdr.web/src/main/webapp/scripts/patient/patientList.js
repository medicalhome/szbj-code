function stopBubble(e) {
    if (e && e.stopPropagation)
        e.stopPropagation()
    else
        window.event.cancelBubble = true;
}

// $Author:wu_jianfeng
// $Date : 2012/10/09 13:10
// $[BUG]0010244 ADD BEGIN
//用来控制字体的显示
var patientListHead = 
{
	"pl_tabs1" : "门(急)诊(当天)",
	"pl_tabs2" : "住 院（在院）",
	"pl_tabs3" : "最 近（7天内）",
	"pl_tabs4" : "检 索",
	"pl_tabs5" : "我的患者",
	"pl_tabs6" : "我的预约患者"
}
// $[BUG]0010244 ADD END

/*
 *控制tab页的样式及相关内容的显示
 *@param tid 相应tab页的id
*/
function showOrHidden(tid,lf)
{
	var curSelectedTabName = "";
	//获取tab标题对象
	$(".divTab").find("div[onclick]").each(function(i)
	{		
		//获取现已选中的tab标题对象
		if($(this).hasClass("divTabChange"))
		{
			curSelectedTabName = $(this).attr("name"); 
			//return ;
		}
	}
	);
			
	//获取tab标题对象
	$(".divTab").find("div[onclick]").each(function(i)
	{
		//获取现已选中的tab标题对象
		if($(this).hasClass("divTabChange"))
		{
			//如果当前tab页等于选中的tab页则返回，不进行任何操作
			if($(this).attr("name") == tid)
			{
				return;
			}
			//当前tab也不等于选中tab页情况
			else
			{
				//将当前tab页的样式去掉并隐藏相应tab页内容
				var oldName = $(this).attr("name").substring(1);
				$(this).removeClass("divTabChange").removeClass(oldName + "BackGround").html(eval("patientListHead." + oldName));
				$($(this).attr("name")).hide();

				//更改选中tab页的样式并显示相应tab页内容
				var newName = tid.substring(1);
				$("div[name="+tid+"]").addClass("divTabChange").addClass(newName+"BackGround").html("&nbsp;");
				$(tid).show();
			}
		}
	});

	// $Author:wu_jianfeng
	// $Date : 2012/09/12 14:10
	// $[BUG]0009672 MODIFY BEGIN
	if(curSelectedTabName != tid)
	{
		if(tid == "#pl_tabs1")
			loadPatientListView("../patient/list_outpatient.html", "", "#patientListView_outpatient");            
		else if(tid == "#pl_tabs2")			
			loadPatientListView("../patient/list_inpatient.html", "", "#patientListView_inpatient");            
		else if(tid == "#pl_tabs3")			
			loadPatientListView("../patient/list_recent.html", "", "#patientListView_recent");            
		else if(tid == "#pl_tabs4")		
			loadPatientListView("../patient/list_patient.html?flag=" + lf, "", "#patientListView_all");
		// $Author:wu_jianfeng
		// $Date : 2012/10/09 13:10
		// $[BUG]0010244 ADD BEGIN
		else if(tid == "#pl_tabs5")		
			loadPatientListView("../patient/list_patientfav.html?flag=" + lf, "", "#patientListView_fav");
		// $[BUG]0010244 ADD END
		else if(tid == "#pl_tabs6")		
			loadPatientListView("../patient/list_patientappoint.html?flag=" + lf, "", "#patientListView_appoint");
	}
	// $[BUG]0009672 MODIFY END
}

/**
 * 使用指定的URL和数据局部刷新页面
 */
function loadPatientListView(url, data, lid)
{
    $('#patientLoadingScreen').show();
	$("div[name=patientListView]").hide();

	//解决IE浏览器缓存问题
	var a = Math.random();
	if(url.indexOf("?")>0){
		url = url+"&rand="+a;
	}else{
		url = url+"?rand="+a;
	}
	
	jQuery(lid).load(url, data, function(response, status, xhr) {
		//处理Session过期
		var sessionExpried = handleExpiredSession(xhr);

		// 如果session没有过期
		if(!sessionExpried)
		{
			//如果异步加载数据成功
		    if (status == "success") {
			    $('#patientLoadingScreen').hide();

				// $Author:wu_jianfeng
				// $Date : 2012/09/12 14:10
				// $[BUG]0009672 MODIFY BEGIN
				showPatientListTabView();
				// $[BUG]0009672 MODIFY END

			    setupDatePickerSetting();
			    
			    // 初始化带有datepicker样式的日期输入框
			    setupDatePicker();
		    }

			//如果状态是error或者timeout, 显示错误对话框
			else if(status == "error" || status == "timeout")
			{
				setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
			}
		}
	});

}

// $Author:wu_jianfeng
// $Date : 2012/09/12 14:10
// $[BUG]0009672 ADD BEGIN

function getSelectedListName()
{
	var tid = "";
	//得到当前选中的tab
	$(".divTab").find("div[onclick]").each(function(i)
	{
		//获取现已选中的tab标题对象
		if($(this).hasClass("divTabChange"))
		{
			tid = $(this).attr("name"); 
			return;
		}
	});
	return tid;
}

function getCurrentPatientListView()
{
	var tid = getSelectedListName();
	var patientListView = "";
	if(tid == "#pl_tabs1")
		patientListView = "#patientListView_outpatient";
	else if(tid == "#pl_tabs2")			
		patientListView = "#patientListView_inpatient";
	else if(tid == "#pl_tabs3")			
		patientListView = "#patientListView_recent";
	else if(tid == "#pl_tabs4")		
		patientListView = "#patientListView_all";
	// $Author:wu_jianfeng
	// $Date : 2012/10/09 13:10
	// $[BUG]0010244 ADD BEGIN
	else if(tid == "#pl_tabs5")
		patientListView = "#patientListView_fav";
	// $[BUG]0010244 ADD END
	else if(tid == "#pl_tabs6")
		patientListView = "#patientListView_appoint";
	return patientListView;
}

function showPatientListTabView()
{
	var patientListView = getCurrentPatientListView();				
	$(patientListView).show();
	var tid = getSelectedListName();

	// $Author:wu_jianfeng
	// $Date : 2012/10/09 13:10
	// $[BUG]0010244 ADD BEGIN
	if(tid == "#pl_tabs1")
	{
		$("#patientListView_inpatient").html("");
		$("#patientListView_recent").html("");
		$("#patientListView_all").html("");
		$("#patientListView_fav").html("");
	}
	else if(tid == "#pl_tabs2")			
	{
		$("#patientListView_outpatient").html("");
		$("#patientListView_recent").html("");
		$("#patientListView_all").html("");
		$("#patientListView_fav").html("");
	}
	else if(tid == "#pl_tabs3")			
	{
		$("#patientListView_inpatient").html("");
		$("#patientListView_outpatient").html("");
		$("#patientListView_all").html("");
		$("#patientListView_fav").html("");
	}
	else if(tid == "#pl_tabs4")		
	{
		$("#patientListView_inpatient").html("");
		$("#patientListView_outpatient").html("");
		$("#patientListView_recent").html("");
		$("#patientListView_fav").html("");
	}
	else if(tid == "#pl_tabs5")		
	{
		$("#patientListView_outpatient").html("");
		$("#patientListView_inpatient").html("");
		$("#patientListView_recent").html("");
		$("#patientListView_all").html("");
	}
	// $[BUG]0010244 ADD END
	else if(tid == "#pl_tabs6")		
	{
		$("#patientListView_outpatient").html("");
		$("#patientListView_inpatient").html("");
		$("#patientListView_recent").html("");
		$("#patientListView_all").html("");
		$("#patientListView_fav").html("");
	}
}

// $[BUG]0009672 ADD END

/**
 * 翻页处理
 */
function jumpToListPage(pageno)
{
	var form = $("[name='pagingform']");
	var href = form.attr("action");
	if(href.indexOf("?")>0)
	{
		href = href+"&pageNo=" + pageno;
	}
	else
	{
		href = href+"?pageNo=" + pageno;
	}

	// $Author:wu_jianfeng
	// $Date : 2012/09/12 14:10
	// $[BUG]0009672 MODIFY BEGIN
	var patientListView = getCurrentPatientListView();
	//$Author:chang_xuewen
	// $Date : 2013/06/27 15:10
	// $[BUG]0034184 MODIFY BEGIN
	loadPatientListcommon(href, form.serializeObject(), patientListView);
	//$[BUG]0034184 MODIFY END
	// $[BUG]0009672 MODIFY END
}

/**
 * 处理列表分页的跳转_提示相关信息
 */
function jumpToListPageNo(pageno,totalPageCnt)
{
	var form = $("[name='pagingform']");
	if(form == null)
	{
		showMsg("提示","检索条件表单对象不存在！");
		return;
	}
	if(pageno == null || pageno == '')
	{
		showMsg("提示","页号为空！");
		return;
	}
	var reg = new RegExp("^[0-9]*$");
	if(!reg.test(pageno)||pageno == "0")
	{
		showMsg("提示","输入内容不正确，请输入大于等于1的整数！");
		return;
	}
	if(pageno > totalPageCnt)
	{
		showMsg("提示","输入页号大于页面提供的最大值！");
		return;
	}
	var href = form.attr("action");
	if(href.indexOf("?")>0)
	{
		href = href+"&pageNo=" + pageno;
	}
	else
	{
		href = href+"?pageNo=" + pageno;
	}

	// $Author:wu_jianfeng
	// $Date : 2012/09/12 14:10
	// $[BUG]0009672 MODIFY BEGIN
	var patientListView = getCurrentPatientListView();
	//$Author:chang_xuewen
	// $Date : 2013/06/27 15:10
	// $[BUG]0034184 MODIFY BEGIN
	loadPatientListcommon(href, form.serializeObject(), patientListView);
	// $[BUG]0009672 MODIFY END
}

//$Author:chang_xuewen
// $Date : 2013/06/27 15:10
// $[BUG]0034184 ADD BEGIN
/**
 * @author chang_xuewen
 * 使用指定的URL和数据局部刷新页面
 */
function loadPatientListcommon(url, data, lid)
{
    //解决IE浏览器缓存问题
	var a = Math.random();
	if(url.indexOf("?")>0){
		url = url+"&rand="+a;
	}else{
		url = url+"?rand="+a;
	}	
	//加载中
	$("#patientLoadingScreen").show();
	$(lid).hide();	
	jQuery(lid).load(url, data, function(response, status, xhr) {
		//处理Session过期
		var sessionExpried = handleExpiredSession(xhr);
		
		// 如果session没有过期
		if(!sessionExpried)
		{
			//如果异步加载数据成功
		    if (status == "success") 		   	{
		    	$("#patientLoadingScreen").hide();
		    	$(lid).show();
		    }
			//如果状态是error或者timeout, 显示错误对话框
			else if(status == "error" || status == "timeout")
			{
				setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
			}
		}
	});
}
//$[BUG]0034184 ADD END
var searchTableCache = new Array();

function bzShow(id) {
    var pfp = $("#pf" + id).html();
    $("#bz").html(pfp);
}

function bzHide() {
    $("#bz").html('');
}

function logicalPatient()
{
	var tabEnterClass = ".tabEnterPatient";
	
	var sel = $(tabEnterClass);
	
	sel.each(function(i)
	{
		activeTab($(this));
		
		if(isTable($(this)) || ($(this).attr("name") != undefined 
				&& $(this).attr("name") != null && $(this).attr("name").indexOf("#pl_tabs") >= 0))
		{
			$(this).attr("hidefocus","true");
		}
	});
	
	sel.unbind("focus.patient");
	sel.unbind("blur.patient");
	sel.unbind("keydown.patient");
	
	sel.bind("focus.patient",function(event)
	{
		if($(this).attr("name") != undefined 
				&& $(this).attr("name") != null && $(this).attr("name").indexOf("#pl_tabs") >= 0)
		{
			$(this).trigger("mouseover");
		}
		
		styleTableOver($(this));
	});
	
	sel.bind("blur.patient",function(event)
	{
		if($(this).attr("name") != undefined 
				&& $(this).attr("name") != null && $(this).attr("name").indexOf("#pl_tabs") >= 0)
		{
			$(this).trigger("mouseout");
		}
		
		styleTableOut($(this));
	});
	
	sel.bind("keydown.patient",function(event)
	{
		if (event.keyCode == 13) 
		{
			if($(this).attr("type") == "checkbox")
			{
				if($(this).attr("id") != undefined && $(this).attr("id") == "selectOrNot")
				{
					var size = $('#selectOrNot:checked').size();
					
					if(size <= 0) 
					{
						$("#selectOrNot").attr("checked",true);
						
						$("input[name='patientFavId']").attr("checked",true);
					}
					else 
					{
						$("#selectOrNot").attr("checked",false);
						
						$("input[name='patientFavId']").attr("checked",false);
					}
				}
			}
			else
			{
				if($(this).attr("name") == "controlCheckbox")
				{
					var check = $(this).find("*[type='checkbox']").is(":checked");
					
					$(this).find("*[type='checkbox']").attr("checked",!$(this).find("*[type='checkbox']").is(":checked"));
					
					stopBubble(event);
					
					return false;
				}
				
				if($(this).attr("name") != undefined 
						&& $(this).attr("name") != null && $(this).attr("name").indexOf("#pl_tabs") >= 0)
				{
					showOrHidden($(this).attr("name"));
					
					return false;
				}
				
				if($(this).attr("id") == "showhidepatient")
				{
					$(this).click();
					
					$("#patientRelationship").focus();
					
					return false;
				}
				
				if($(this).parent().attr("id") == "selectable")
				{
					$("#selectable li").removeClass("ui-selected");
					
					$(this).addClass("ui-selected");
					
					searchFolder('', 'conditionFormPatient','#pfcontent');
					
					return false;
				}
				
				enterPress($(this));
			}
		}
	});
}

//$Author:jin_peng
// $Date : 2013/11/07 13:41
// $[BUG]0039035 ADD BEGIN
function outpatientSearch()
{
	var outpatient_no = $("#outpatient_no").val();	
	var outpatient_name = $("#outpatient_name").val();
	var outpatient_dept_name = $("#outpatient_dept_name").val();
	var deptId = $("#deptName_outp").val();
	// $Author:chang_xuewen
	// $Date : 2013/12/18 14:10
	// $[BUG]0040770 ADD BEGIN
	var orgCode = $("#orgCodeFlag").val();
	var patientEId = $("#patientEId").val();
	
	if($.trim(patientEId) == "" && $.trim(outpatient_no) == "" && $.trim(outpatient_name) == "" && $.trim(outpatient_dept_name) == "" && $.trim(deptId) == "-1" && $.trim(orgCode) == "-1")
	{
	// $[BUG]0040770 ADD END
		showMsg("提示","至少有一个查询条件！");
		
    	return false;
	}
	
	loadPatientDetailList("../patient/list_outpatient.html", {"outpatient_no":outpatient_no,"outpatient_name":outpatient_name,"outpatient_dept_name":outpatient_dept_name,"portion":"1","deptId":deptId,"orgCode":orgCode,"patientEId":patientEId}, "#outpatient_content");
}

function inpatientSearch()
{
	var inpatient_no = $("#inpatient_no").val();	
	var inpatient_name = $("#inpatient_name").val();
	var inpatient_bed_no = $("#inpatient_bed_no").val();
	var inpatient_dept_name = $("#inpatient_dept_name").val();
	var deptId = $("#deptName_inp").val();
	var wardId = $("#wardName_inp").val();
	// $Author:chang_xuewen
	// $Date : 2013/12/18 14:10
	// $[BUG]0040770 ADD BEGIN
	var orgCode = $("#orgCodeFlag").val();
	var patientEId = $("#patientEId").val();
	
	if($.trim(patientEId) == "" && $.trim(inpatient_no) == "" && $.trim(inpatient_name) == "" && $.trim(inpatient_bed_no) == "" && $.trim(inpatient_dept_name) == "" && $.trim(deptId) == "-1" && $.trim(wardId) == "-1" && $.trim(orgCode) == "-1")
	{
	// $[BUG]0040770 ADD END	
		showMsg("提示","至少有一个查询条件！");
		
    	return false;
	}
	
	loadPatientDetailList("../patient/list_inpatient.html", {"inpatient_no":inpatient_no,"inpatient_name":inpatient_name,"inpatient_bed_no":inpatient_bed_no,"inpatient_dept_name":inpatient_dept_name,"portion":"1","deptId":deptId,"wardId":wardId,"orgCode":orgCode,"patientEId":patientEId}, "#inpatient_content");
}

function recentpatientSearch()
{
	var recent_visit_no = $("#recent_visit_no").val();	
	var recentpatient_name = $("#recentpatient_name").val();
	var recent_patient_domain = $("#recent_patient_domain").val();
	var recentpatient_dept_name = $("#recentpatient_dept_name").val();
	var deptId = $("#deptName_rec").val();
	var wardId = $("#wardName_rec").val();
	var patientEId = $("#patientEId").val();
	// $Author:chang_xuewen
	// $Date : 2013/12/18 14:10
	// $[BUG]0040770 ADD BEGIN
	var orgCode = $("#orgCodeFlag").val();
	if( $.trim(patientEId) == "" &&  $.trim(recentpatient_name) == "" && $.trim(recentpatient_dept_name) == "" && $.trim(deptId) == "-1" && $.trim(wardId) == "-1" && $.trim(orgCode) == "-1")
	{
	// $[BUG]0040770 ADD END	
		showMsg("提示","至少有一个查询条件！");
		
    	return false;
	}
	
	//选择了就诊号类型，就诊号不能为空
	/*if(recent_patient_domain != "-1" && $.trim(recent_visit_no) == "")
	{
		showMsg("提示","就诊号不能为空！");
		
    	return false;				
	}
	
	//就诊号不为空时，需选择就诊号类型
	if(recent_patient_domain == "-1" && $.trim(recent_visit_no) != "")
	{
		showMsg("提示","请选择就诊号类型！");
		
    	return false;				
	}*/
	
	loadPatientDetailList("../patient/list_recent.html", {"recentpatient_name":recentpatient_name,"recentpatient_dept_name":recentpatient_dept_name,"portion":"1","deptId":deptId,"wardId":wardId,"patientEId":patientEId}, "#recentpatient_content");
}

function patientAppointSearch()
{
	var outpatient_name = $("#outpatient_name").val();
	var requestStartDate = $("#requestStartDate").val();
	var requestEndDate = $("#requestEndDate").val();
	var normalType =  $("#normalPatient")[0];
	if(requestStartDate!=""&&requestEndDate!="" &&requestStartDate>requestEndDate)							
    {			
    	showMsg("提示","预约日期  开始时间不能>结束时间！");
    	return false;						
    }	
	if(requestStartDate =="" )							
    {			
    	showMsg("提示","预约日期  开始时间不能为空！");
    	return false;						
    }	
	// 增加检索前的时间格式验证
    if(isDateStringCheck(requestStartDate) == "true")
    {
    	return false;
    }
    if(isDateStringCheck(requestEndDate) == "true")
    {
    	return false;
    }
	var normalPatient;
	if(normalType.checked){
		normalPatient="1"
		$("#normalPatient").val("1");
	}else{
		normalPatient="0"
		$("#normalPatient").val("0");
	}
	loadPatientDetailList("../patient/list_patientappoint.html", {"outpatient_name":outpatient_name,"requestStartDate":requestStartDate,"portion":"1","requestEndDate":requestEndDate,"normalPatient":normalPatient}, "#patientAppoint_content");
}

/**
 * 判断是否有就诊信息
 */
function visit(patientSn){
	$.ajax({url:'../visit/check.html',
		type:'post',
		dataType:'text',
		data:{patientSn:patientSn},
		success:function(data){
			var result=jQuery.parseJSON(data);
			if(result.visible == '1'){
				location.href='../visit/'+patientSn+'.html';
			}
			else{
				showMsg("提示","此患者无就诊信息！");
			}
		},
		error:function(data){
			
		}
	});
}
$(function() {
	//调用父js的检索框动态加载刷新页面
    parent.condition();
    //调用父js的时间格式校验
	$(".datepicker").bind("blur",isDatePatientString);
});
//日期输入时格式校验共同接口
//示例：$(".datepicker").bind("blur",parent.isDateString)
function isDatePatientString() {
	  var strDate = $(this).val();
	  var check = isDatePatientStringCheck(strDate);
	  if ("true" == check) {
	      //清空错误的时间格式内容
		  $(this).val('');
	  }
}

//日期输入时格式校验
//isDateString调用此方法
function isDatePatientStringCheck(strDate) {
  if (strDate == null || strDate == '') {
      return ("false");
  }
  var strSeparator = "-"; //日期分隔符 
  var strDateArray;
  var intYear;
  var intMonth;
  var intDay;
  var boolLeapYear;
  var ErrorMsg = ""; //出错信息 
  strDateArray = strDate.split(strSeparator);
  //没有判断长度,其实2008-8-8也是合理的//strDate.length != 10 || 判断year的长度必须为4位
  if (strDate.length != 10 || strDateArray.length != 3 || strDateArray[0].length != 4) {
      ErrorMsg += "日期格式必须为: yyyy-mm-dd";
      showMsg("提示", ErrorMsg);
      //alert("dsfdf");
      return ("true");
  }
  intYear = parseInt(strDateArray[0], 10);
  intMonth = parseInt(strDateArray[1], 10);
  intDay = parseInt(strDateArray[2], 10);
  if (isNaN(intYear) || isNaN(intMonth) || isNaN(intDay)) {
      ErrorMsg += "日期格式错误: 年月日必须为纯数字";
      showMsg("提示", ErrorMsg);
      return ("true");
  }
  if (intMonth > 12 || intMonth < 1) {
      ErrorMsg += "日期格式错误: 月份必须介于1和12之间";
      showMsg("提示", ErrorMsg);
      return ("true");
  }
  if ((intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7 || intMonth == 8 || intMonth == 10 || intMonth == 12) && (intDay > 31 || intDay < 1)) {
      ErrorMsg += "日期格式错误: 大月的天数必须介于1到31之间";
      showMsg("提示", ErrorMsg);
      return ("true");
  }
  if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11) && (intDay > 30 || intDay < 1)) {
      ErrorMsg += "日期格式错误: 小月的天数必须介于1到30之间";
      showMsg("提示", ErrorMsg);
      return ("true");
  }
  if (intMonth == 2) {
      if (intDay < 1) {
          ErrorMsg += "日期格式错误: 日期必须大于或等于1";
          showMsg("提示", ErrorMsg);
          return ("true");
      }
      boolLeapYear = false;
      if ((intYear % 100) == 0) {
          if ((intYear % 400) == 0) boolLeapYear = true;
      } else {
          if ((intYear % 4) == 0) boolLeapYear = true;
      }
      if (boolLeapYear) {
          if (intDay > 29) {
              ErrorMsg += "日期格式错误: 闰年的2月份天数不能超过29";
              showMsg("提示", ErrorMsg);
              return ("true");
          }
      } else {
          if (intDay > 28) {
              ErrorMsg += "日期格式错误: 非闰年的2月份天数不能超过28";
              showMsg("提示", ErrorMsg);
              return ("true");
          }
      }
  }
}
/**
 * 翻页处理
 */
function jumpToListPagePortion(pageno, formName, lid)
{
	var form = $("[name='pagingform']");
	
	if(formName != undefined)
	{
		form = $("[name='" + formName + "']");
	}
	
	var href = form.attr("action");
	if(href.indexOf("?")>0)
	{
		href = href+"&portion=1&pageNo=" + pageno;
	}
	else
	{
		href = href+"?portion=1&pageNo=" + pageno;
	}

	loadPatientDetailList(href, form.serializeObject(), lid);
}

/**
 * 处理列表分页的跳转_提示相关信息
 */
function jumpToListPageNoPortion(pageno,totalPageCnt, formName, lid)
{
	var form = $("[name='pagingform']");
	
	if(formName != undefined)
	{
		form = $("[name='" + formName + "']");
	}
	
	if(form == null)
	{
		showMsg("提示","检索条件表单对象不存在！");
		return;
	}
	if(pageno == null || pageno == '')
	{
		showMsg("提示","页号为空！");
		return;
	}
	var reg = new RegExp("^[0-9]*$");
	if(!reg.test(pageno)||pageno == "0")
	{
		showMsg("提示","输入内容不正确，请输入大于等于1的整数！");
		return;
	}
	if(pageno > totalPageCnt)
	{
		showMsg("提示","输入页号大于页面提供的最大值！");
		return;
	}
	var href = form.attr("action");
	if(href.indexOf("?")>0)
	{
		href = href+"&portion=1&pageNo=" + pageno;
	}
	else
	{
		href = href+"?portion=1&pageNo=" + pageno;
	}

	loadPatientDetailList(href, form.serializeObject(), lid);
}

function loadPatientDetailList(url, data, lid)
{
	var loading = "<div id='patientLoading' style='display:block;' class='loadingScreen'><div class='loadingMessage'>数据加载中，请稍候...</div></div>";
	$(lid).html(loading);
	
	//解决IE浏览器缓存问题
	var a = Math.random();
	if(url.indexOf("?")>0)
	{
		url = url+"&rand="+a;
	}else
	{
		url = url+"?rand="+a;
	}
	
	jQuery(lid).load(url, data, function(response, status, xhr) {
		//处理Session过期
		var sessionExpried = handleExpiredSession(xhr);

		// 如果session没有过期
		if(!sessionExpried)
		{
			//如果异步加载数据成功
		    if (status == "success") 
		   	{
			    
		    }

			//如果状态是error或者timeout, 显示错误对话框
			else if(status == "error" || status == "timeout")
			{
				setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
			}
		}
	});
}
function onchangeHeight(id){
	if($(".selectdiv3").length = 0){
		$(".selectdiv input").focus(function(){
			if($("#"+id).height()<250 ){
				$("#"+id).height("380");
			}
		});
		$(".selectdiv input").blur(function(){		
			setTimeout(function(){
				var dis1 = $(".selectdiv .suggest").css("display");
				var dis2 = $(".selectdiv2 .suggest").css("display");
				if((dis1 == "none" || dis1 == undefined) &&
				   (dis2 == "none" || dis2 == undefined)){
					$("#"+id).height("auto");
				}
			},200);
		});
		$(".selectdiv2 input").focus(function(){
			if($("#"+id).height()<250){
				$("#"+id).height("380");
			}
		});
		$(".selectdiv2 input").blur(function(){
			setTimeout(function(){
				var dis1 = $(".selectdiv .suggest").css("display");
				var dis2 = $(".selectdiv2 .suggest").css("display");
				if((dis1 == "none" || dis1 == undefined) &&
				   (dis2 == "none" || dis2 == undefined)){
					$("#"+id).height("auto");
				}
			},200);
		});
	}
	
	if($(".selectdiv3").length > 0){
		$(".selectdiv input").focus(function(){
			if($("#"+id).height()<250 ){
				$("#"+id).height("380");
			}
		});
		$(".selectdiv input").blur(function(){		
			setTimeout(function(){
				var dis1 = $(".selectdiv .suggest").css("display");
				var dis2 = $(".selectdiv2 .suggest").css("display");
				var dis3 = $(".selectdiv3 .suggest").css("display");
				if((dis1 == "none" || dis1 == undefined) &&
				   (dis2 == "none" || dis2 == undefined) &&
				   (dis3 == "none" || dis3 == undefined)){
					$("#"+id).height("auto");
				}
			},200);
		});
		$(".selectdiv2 input").focus(function(){
			if($("#"+id).height()<250){
				$("#"+id).height("380");
			}
		});
		$(".selectdiv2 input").blur(function(){
			setTimeout(function(){
				var dis1 = $(".selectdiv .suggest").css("display");
				var dis2 = $(".selectdiv2 .suggest").css("display");
				var dis3 = $(".selectdiv3 .suggest").css("display");
				if((dis1 == "none" || dis1 == undefined) &&
				   (dis2 == "none" || dis2 == undefined) &&
				   (dis3 == "none" || dis3 == undefined)){
					$("#"+id).height("auto");
				}
			},200);
		});
		$(".selectdiv3 input").focus(function(){
			$("#patientSelectDialog").height("480px");
			if($("#"+id).height()<250 ){
				$("#"+id).height("420");
			}
		});
		$(".selectdiv3 input").blur(function(){					
			setTimeout(function(){
				//$("#patientSelectDialog").height("454px");
				var dis1 = $(".selectdiv .suggest").css("display");
				var dis2 = $(".selectdiv2 .suggest").css("display");
				var dis3 = $(".selectdiv3 .suggest").css("display");
				if((dis1 == "none" || dis1 == undefined) &&
				   (dis2 == "none" || dis2 == undefined) &&
				   (dis3 == "none" || dis3 == undefined)){
					$("#"+id).height("auto");
				}
			},200);
		});
	}
}
//$[BUG]0039035 ADD END