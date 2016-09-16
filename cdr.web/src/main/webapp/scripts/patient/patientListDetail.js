var searchTable = new SearchTable("tbPatientListAS", getBusinessFields(), "#new");
var patientAdvanceListUrl = "../patient/list_patient_ad_detail.html";
var patientNormalListUrl = "../patient/list_patient_detail.html";
var patientEmptyListUrl = "../patient/list_patient_empty_detail.html";
var normalSearchType = "normal";
var advanceSearchType = "advance";

$(function () {
	if(searchTableCache != null && searchTableCache.length != 0)
	{
		for(var index in searchTableCache)
		{
			var searchRow = searchTable.addRow(searchTableCache[index].name, searchTableCache[index].values);
			if(searchTableCache[index].name == "baseAge")
				searchRow.getCustomSql = getBaseAgeCustomSql;
		}
	}		    	
});


function getBusinessFields()
{
	var businessFields = new CustomBusinessFields();
	var tables = new Array();
	var fields = new Array();
	
	var patientTable = new CustomTable("患者", "patient", "p");	
	var empiTable = new CustomTable("empi", "pci", "pci");
	var visitTable = new CustomTable("就诊", "visit", "mv");
	var diagnosisTable = new CustomTable("诊断", "diagnosis", "dia");
	var labItemTable = new CustomTable("检验项目", "lab_result_item", "lri");
	
	tables[tables.length] = patientTable;
	tables[tables.length] = empiTable;
	tables[tables.length] = visitTable;
	tables[tables.length] = diagnosisTable;
	tables[tables.length] = labItemTable;
	businessFields.tables = tables;

	fields[fields.length] = new CustomBusinessField("patientGender", "患者", new CustomTableField("gender_code", "患者性别", "list", "plas_gender", patientTable));
	fields[fields.length] = new CustomBusinessField("patientName", "患者", new CustomTableField("patient_name", "患者姓名", "string", "", patientTable));
	
	var visitNoTableField = new CustomTableField("visit_no", "就诊号", "string", "", empiTable);
	fields[fields.length] = new CustomBusinessFieldPair("visitNo", 'empi', 
			new CustomBusinessField("patientDomain", 'empi', new CustomTableField("patient_domain", "就诊号类型", "list", "plas_patientDomain", empiTable)),
			new CustomBusinessField("visitNo", 'empi', visitNoTableField)
		);
	visitNoTableField.getName = getVisitNoTableFieldName;
	
	fields[fields.length] = new CustomBusinessField("visitType", "就诊", new CustomTableField("visit_type_code", "就诊类型", "list", "plas_visitType", visitTable));
	fields[fields.length] = new CustomBusinessField("visitDate", '就诊', new CustomTableField("visit_date", "就诊日期", "date", "", visitTable));
	fields[fields.length] = new CustomBusinessField("baseAge", '就诊', new CustomTableField("base_age", "年龄段", "list", "plas_baseAge", visitTable));
	fields[fields.length] = new CustomBusinessField("diseaseName", '诊断', new CustomTableField("disease_name", "疾病名称", "string", "", diagnosisTable));

	var labItemField = new CustomBusinessField("labItem", '检验项目', new CustomTableField("item_code", "检验项目", "list", "plas_labSubItem", labItemTable));
	labItemField.suggest = true;
	fields[fields.length] = new CustomBusinessFieldPair("labItemResult", '检验项目', 
			labItemField,
			new CustomBusinessField("labItemResult", '检验项目', new CustomTableField("item_value", "检验项目结果", "number", "", labItemTable))
		); 

/*
	var visitDeptField = new CustomBusinessField("visitDept", '就诊', new CustomTableField("visit_dept_id", "就诊科室", "list", "plas_visitDept", visitTable));
	visitDeptField.suggest = true;
	fields[fields.length] = visitDeptField;
*/
	businessFields.fields = fields;
	
	return businessFields;
}

function getVisitNoTableFieldName(paras)
{
	if(paras.field == "门诊号")
		return "outpatient_no";
	else if(paras.field == "住院号")
		return "inpatient_no";
	else if(paras.field == "影像号")
		return "imaging_no";	
}

function addSearchTableRow()
{
	var fieldText = $("#t_field").val();
	if(fieldText == "-1")
	{
		showMsg("提示","请选择字段名称！");
		$("#t_field").focus();
		return false;
	}
	else
	{
		var searchRow = searchTable.addRow(fieldText);
		if(fieldText == "baseAge")
			searchRow.getCustomSql = getBaseAgeCustomSql;
		return true;
	}
}

function getBaseAgeCustomSql()
{
	var inputValue = this.row.find("select.selectValue").val();
	var optValue = this.row.find("select.opt").val();
	if(optValue == "equal")
	{
		if(inputValue == "20")
		    return "(mv.visit_date-p.birth_date)/365 >= 100";	
		else
	        return "(mv.visit_date-p.birth_date)/365 between to_number(''"  + inputValue + "'')*5 and (to_number(''" + inputValue + "'')*5+5)";
	}
	else if(optValue == "notEqual")
	{
		if(inputValue == "20")
		    return "(mv.visit_date-p.birth_date)/365 < 100";
		else
		{
			var lowAge = parseInt(inputValue) * 5;
			var highAge = parseInt(inputValue) * 5 + 5;
	        return "(((mv.visit_date-p.birth_date)/365 < " + lowAge + ") or ((mv.visit_date-p.birth_date)/365 > " + highAge + "))";
		}	
	}
}

function deleteSearchTableRow(object)
{
	var rowIndex = $(object).attr("id").substring(9);
	var row = $("#" + searchTable.tableId + " tr[id=\'row"+rowIndex+"\']");
	searchTable.deleteRow(row);		
}
			
function btnSearchType_onclick()
{
    var searchType = $("#searchType").val();
    changeSearchType(searchType);
}

function changeSearchType(searchType)
{
    if(searchType == normalSearchType)
    {    
    	$("#searchType").val("advance");
    	$("#btnSearchType").css({'background-image':'url(../images/61.jpg)'})
    	$("#tb_patientList").hide();
    	$("#tbPatientListAS").show();
		$("#patientNormalList").hide();
		$("#patientAdvanceList").show();
    }
    else if(searchType == advanceSearchType)
    {
    	$("#searchType").val("normal");
    	$("#btnSearchType").css({'background-image':'url(../images/6.jpg)'})
    	$("#tb_patientList").show();
    	$("#tbPatientListAS").hide();
		$("#patientNormalList").show();
		$("#patientAdvanceList").hide();
    }
}

function btnSearch_onclick()
{
	//$Author:chang_xuewen
    //$Date:2013/11/20 17:25
    //$[BUG]39470 ADD BEGIN
	var patientDomainStr = $("#patientDomainStr").val();
	var visitNo = $("#visitNo").val();	
	var patientName = $("#patientName").val();	
	var gender = $("#gender").val();
	var visitStartDate = $("#visitStartDate").val();
	var visitTypeStr = $("#visitTypeStr").val();
	var visitEndDate = $("#visitEndDate").val();
	var baseAge = $("#baseAge").val();
	var bedNo = $("#bedNo").val();
	var deptName = $("#deptName").val();
	var wardName = $("#wardName").val();
	var orgCode = $("#orgCodeFlag").val();
	var patientEId = $("#patientEId").val();
	var idNumber = $("#idNumber").val();
	if(patientEId == ""  && $.trim(patientName) == "" && $.trim(gender) == "-1" && $.trim(visitStartDate) == "" && $.trim(visitEndDate) == "" && $.trim(visitTypeStr) == "-1" && $.trim(baseAge) == "-1" && $.trim(bedNo) == "" && $.trim(deptName) == "-1" && $.trim(wardName) == "-1" && $.trim(idNumber) == "")
	{
		showMsg("提示","至少有一个查询条件！");
		
    	return false;
	}	
	
	//选择了就诊号类型，就诊号不能为空
	/*if(patientDomainStr != "-1" && $.trim(visitNo) == "")
	{
		showMsg("提示","就诊号不能为空！");
		
    	return false;				
	}
	
	//就诊号不为空时，需选择就诊号类型
	if(patientDomainStr == "-1" && $.trim(visitNo) != "")
	{
		showMsg("提示","请选择就诊号类型！");
		
    	return false;				
	}
	//$[BUG]39470 ADD END
	//$Author:chang_xuewen
    //$Date:2013/11/22 09:25
    //$[BUG]0039831 ADD BEGIN
	var today = new Date().format("yyyy-MM-dd");
    if(visitStartDate!=""&&visitStartDate>today){
    	showMsg("提示","检查日期 开始时间不能>当前日期！");
    	return;
    }*/
    //$[BUG]0039831 ADD END
    var searchType = $("#searchType").val();
	searchPatients(searchType, false);
}

function searchPatients(searchType, flag)							
{		
	var formName = "conditionFormPatient";
	var form = $("[name='" + formName + "']");
	
	if(searchType == normalSearchType)
    {    	
    	//flag=true，直接加载空列表，否则加载有条件的列表
    	if(flag)
    	{
    		loadPatientList(patientEmptyListUrl, "", "#patientNormalListContent", searchType);
    	}
    	else
    	{	
			if(validateNormalSearch())
    			loadPatientList(patientNormalListUrl, form.serializeObject(), "#patientNormalListContent", searchType);						
		} 		
	}		
	else if(searchType == advanceSearchType)
	{
		if(flag)
		{	
			if(searchTable.rows.length == 0)
				loadPatientList(patientEmptyListUrl, "", "#patientAdvanceListContent", searchType);
			else
				advanceSearchPatients(patientAdvanceListUrl, formName, "#patientAdvanceListContent");
		}
		else	
		{
			if(validateAdvanceSearch())
				advanceSearchPatients(patientAdvanceListUrl, formName, "#patientAdvanceListContent");
		}
	}
}		

function isEmptyNormalSearch()
{
	var visitNoType = $("#patientDomainStr").val();	
	var visitNo = $("#visitNo").val();
	var patientName = $("#patientName").val();
	var gender = $("#gender").val();
    var visitStartDate = $("#visitStartDate").val();		
    var visitEndDate = $("#visitEndDate").val();							
	var visitTypeStr = $("#visitTypeStr").val();
	//var diseaseName = $("#diseaseName").val();
	var baseAge = $("#baseAge").val();
	var deptName = $("#deptName").val();
	var wardName = $("#wardName").val();
	var bedNo = $("#bedNo").val();
	var orgCode = $("#orgCodeFlag").val();
	
	if(visitNoType == "-1" 
		&& $.trim(visitNo) == "" 
		&& $.trim(patientName) == "" 
		&& gender == "-1" 
		&& visitStartDate == "" 
		&& visitEndDate == "" 
		&& visitTypeStr == "-1"
		//&& $.trim(diseaseName) == ""
		&& baseAge == "-1"
		&& $.trim(deptName) == "-1"
		&& $.trim(wardName) == "-1"
		&& $.trim(bedNo) == ""
		&& $.trim(orgCode) == "-1")
		return true;
	else
		return false;
}
//$Author:chang_xuewen
//$Date:2013/11/25 09:25
//$[BUG]0039831 ADD BEGIN
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
//$[BUG]0039831 ADD END
function validateNormalSearch()
{
	//$Author:chang_xuewen
    //$Date:2013/11/26 10:42
    //$[BUG]0039987 MODIFY BEGIN
	/*if(isEmptyNormalSearch())
	{
		showMsg("提示","至少有一个查询条件！");
    	$("#patientDomainStr").focus();										
    	return false;								
	}*/
	
	//$Author:wu_jianfeng
    //$Date:2012/9/5 10:42
    //$[BUG]0009335 ADD BEGIN
    // 检索时加限制，至少输入一个检索条件 
	var visitNoType = $("#patientDomainStr").val();	
	var visitNo = $("#visitNo").val();	
	var patientName = $("#patientName").val();
    var visitStartDate = $("#visitStartDate").val();		
    var visitEndDate = $("#visitEndDate").val();		
	var deptName = $("#deptName").val();
	var patientEId = $("#patientEId").val();

    if($.trim(patientEId) == "" && $.trim(patientName) == "" && $.trim(visitStartDate) == "" && $.trim(visitEndDate) == "" && $.trim(deptName) == "-1")
	{
		showMsg("提示","姓名、科室、就诊日期3个检索条件至少填入一项!&nbsp;&nbsp;&nbsp;(建议就诊日期范围在1个月内)",550);
		
    	return false;
	}
    //$[BUG]0039987 ADD END
	//选择了就诊号类型，就诊号不能为空
	/*if(visitNoType != "-1" && $.trim(visitNo) == "")
	{
		showMsg("提示","就诊号不能为空！");
    	$("#visitNo").focus();								
    	return false;						
	}

	//就诊号不为空，必须选择就诊号类型
	if(visitNoType == "-1" && $.trim(visitNo) != "")
	{
		showMsg("提示","必须选择就诊号类型！");
    	$("#patientDomainStr").focus();										
    	return false;						
	}*/
    //$[BUG]0009335 ADD END

    if(visitEndDate!=""&&visitStartDate>visitEndDate)							
    {			
    	showMsg("提示","就诊日期  开始时间不能>结束时间！");
    	return false;						
    }	
    //$Author:chang_xuewen
    //$Date:2013/11/25 09:25
    //$[BUG]0039831 ADD BEGIN
    // 增加检索前的时间格式验证
    if(isDateStringCheck(visitStartDate) == "true")
    {
    	return false;
    }
    if(isDateStringCheck(visitEndDate) == "true")
    {
    	return false;
    }
    //$[BUG]0039831 ADD END
    /*if($.trim($("#deptName").val()) == "-1")
	{
    	showMsg("提示","科室不能为空！");
    	return false;
	}*/
    
	return true;										
}

function validateAdvanceSearch()
{
	if(searchTable.rows.length == 0)
	{
		showMsg("提示","至少有一个查询条件！");
		return false;		
	}	

	return searchTable.validate();
}

function advanceSearchPatients(url, formName, id)
{
	searchTableCache = searchTable.saveCache();
	var tableSqls = searchTable.getSql();
/*		
	for(var index in tableSqls)
	{
		var tableSql = tableSqls[index];
		if(tableSql.getSql() != "")
		{
			alert(tableSql.getSql());
			alert(tableSql.getSqlText());
		}
	}
*/
	var postdata = "";
	var jsondata = "";
	
	for(var index in tableSqls)
	{
		var tableSql = tableSqls[index];
		if(tableSql.getSql() != "")
		{
			if(postdata != "")
				postdata += ",";
			postdata += tableSql.table;
			postdata += ":";
			postdata += "\"" + tableSql.getSql() + "\""; 
		} 
	}
	
	if(postdata != "")
		jsondata = "{advanceSearch:true, postbackEvent:\"search\", textTip:\"" + "\", " + postdata + "}";
	else
		jsondata = "{advanceSearch:true, postbackEvent:\"search\"}";

	loadPatientList(patientAdvanceListUrl, eval("(" + jsondata + ")"), id, advanceSearchType);						
}

/**
 * 使用指定的URL和数据局部刷新页面
 */
function loadPatientList(url, data, lid, searchType)
{
    if(searchType == normalSearchType)
	{
		$("#LS_patientNormalList").show();
		$("#patientNormalListContent").hide();
	}
	else if(searchType == advanceSearchType)
	{
		$("#LS_patientAdvanceList").show();
		$("#patientAdvanceListContent").hide();
	}

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
		    if (status == "success") 
		   	{
			    if(searchType == normalSearchType)
				{
					$("#LS_patientNormalList").hide();
					$("#patientNormalListContent").show();
				}
				else if(searchType == advanceSearchType)
				{
					$("#LS_patientAdvanceList").hide();
					$("#patientAdvanceListContent").show();
				}
		    }

			//如果状态是error或者timeout, 显示错误对话框
			else if(status == "error" || status == "timeout")
			{
				setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
			}
		}
	});
}

/**
 * 翻页处理
 */
function jumpToPatientListPage(pageno)
{
	var form = $("[name='conditionFormPatient']");
    var searchType = $("#searchType").val();
	var href = "", lid="";

    if(searchType == normalSearchType)
	{
		href = patientNormalListUrl;
		lid = "#patientNormalListContent";
	}
	else if(searchType == advanceSearchType)
	{
		href = patientAdvanceListUrl;
		lid = "#patientAdvanceListContent";
	} 

	if(href.indexOf("?")>0)
	{
		href = href+"&postbackEvent=paging&pageNo=" + pageno;
	}
	else
	{
		href = href+"?postbackEvent=paging&pageNo=" + pageno;
	}

	loadPatientList(href, form.serializeObject(), lid, searchType);
}

/**
 * 处理列表分页的跳转_提示相关信息
 */
function jumpToPatientListPageNo(pageno,totalPageCnt)
{
	var form = $("[name='conditionFormPatient']");
    var searchType = $("#searchType").val();
	var href = "", lid="";

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

    if(searchType == normalSearchType)
	{
		href = patientNormalListUrl;
		lid = "#patientNormalListContent";
	}
	else if(searchType == advanceSearchType)
	{
		href = patientAdvanceListUrl;
		lid = "#patientAdvanceListContent";
	} 

	if(href.indexOf("?")>0)
	{
		href = href+"&postbackEvent=paging&pageNo=" + pageno;
	}
	else
	{
		href = href+"?postbackEvent=paging&pageNo=" + pageno;
	}

	loadPatientList(href, form.serializeObject(),  lid, searchType);
}
