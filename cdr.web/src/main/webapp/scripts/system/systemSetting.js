
function closeDialog() {
    $("#ajaxDialog").remove();
    $("#dialog").append("<div id='ajaxDialog' style='display:none'></div>");
}

//这是当用户按下提交按钮时，对列出选择的select对象执行全选工作，让递交至的后台程序能取得相关数据
function getSettingValues() 
{
    var displayMenus = "";
	var chosenSource = $("#chosen").treeView("getSource");
	var r_chosen = new MenuRepository(chosenSource); 

	for(var c_index in chosenSource)
	{
		var menu = chosenSource[c_index];
		if(menu.status == "visible")
		{
			var isTrue = r_chosen.hasSubItems(menu.code, "visible");
			if(isTrue == false) 
			{             
	            if (displayMenus != "") displayMenus += ",";
	            displayMenus += menu.code;
            }
		}
	}	
	
    $("#displayMenus").val(displayMenus);
}

function ok_onclick(action)
{
 	var $tabs = $("#ss_tabs").tabs();
 	var selected = $tabs.tabs('option', 'selected'); 
	var $form = $("form[name='systemSettingForm']");

	if (action == "apply") 
	{
		$form.attr("action", "../system/save_apply.html");
		confirmMsg1("系统提示", "系统保存设置将立即生效，请确认是否继续？", function(response) {
			if (response = true) 
			{
				$form.submit();
			}
		});
	} 
	else 
	{
		confirmMsg1("系统提示", "系统保存设置将在下一次登录生效，请确认是否继续？", function(response) {
			if (response = true) 
			{
				$form.submit();
			}
		});
	}
}

function normalViews_onclick()
{
	if($(this).attr("id") == "normalViews_5")
	{
		var checked = $("#normalViews_5").is(":checked");

		$("input[name='normalViews']").each(function(){
			if($(this).attr("id") != "normalViews_5")
			{
				if(checked)
					$(this).attr("checked", true);
				else 
					$(this).attr("checked", false);
			}
		});
	}
}

function timerViews_onclick()
{
	if($(this).attr("id") == "timerViews_4")
	{
		var checked = $("#timerViews_4").is(":checked");

		$("input[name='timerViews']").each(function(){
			if($(this).attr("id") != "timerViews_4")
			{
				if(checked)
					$(this).attr("checked", true);
				else 
					$(this).attr("checked", false);
			}
		});
	}
}

function inpatientViews_onclick()
{
	if($(this).attr("id") == "inpatientViews_3")
	{
		var checked = $("#inpatientViews_3").is(":checked");

		$("input[name='inpatientViews']").each(function(){
			if($(this).attr("id") != "inpatientViews_3")
			{
				if(checked)
					$(this).attr("checked", true);
				else 
					$(this).attr("checked", false);
			}
		});
	}
}

function outpatientViews_onclick()
{
	if($(this).attr("id") == "outpatientViews_2")
	{
		var checked = $("#outpatientViews_2").is(":checked");

		$("input[name='outpatientViews']").each(function(){
			if($(this).attr("id") != "outpatientViews_2")
			{
				if(checked)
					$(this).attr("checked", true);
				else 
					$(this).attr("checked", false);
			}
		});
	}
}


/*添加药物隐藏信息*/
function addMedicineName()
{
	var medicineId = $("#medicineName").val();
	// 没有选择
	if(medicineId == -1)
	{
		confirmMsg("系统提示", "请选择要添加的药物信息！",function(){});
		return;
	}
	// 选择的元素存在界面中
	if(addExsit(medicineId) == true)
	{
		confirmMsg("系统提示", "数据存在，请勿重复添加！",function(){});
		return;
	}
	var medicineName = $("#medicineName option:selected").text();
	/*var myDate = new Date(); 
	var vYear = myDate.getFullYear();
	var vMon = myDate.getMonth() + 1;
	var vDay = myDate.getDate();*/
	$("#ss_tabs-3_drug").append("<tr class='odd' style='cursor: pointer;'><td height='24' align='left'>&nbsp;" + medicineName 
							+ "<input type='hidden' name='add_codeDrug' value='" + medicineId + "' /></td><td height='24' align='center'>" 
							/*+ vYear + "-" + (vMon<10 ? "0" + vMon : vMon) + "-" + (vDay<10 ? "0" + vDay : vDay)*/
							+ "</td><td height='24' align='center'>" +
									"<input name='codeDrugId' type='button' value='删除' " +
									"onclick='deleteMedicineName(this);' /></td></tr>"); // delete onfocus='this.blur()'
	$("input[name='codeDrugId']").parent().parent().css("backgroundColor","#C6F285");
	// 设置锚点
	/*location.hash="#to";*/
	/*$("#to").scrollTo(0,document.body.scrollHeight);*/
	/*$("#ss_tabs-3").animate({scrollTop:$("#ss_tabs-3_drug").offset().top},1000);*/
	//$("#drug_slow")[0].scroll(0,$("#drug_slow")[0].scrollHeight);
	$("#drug_slow")[0].scrollTop = $("#drug_slow")[0].scrollHeight;
	initHideDrugViews();
}

/**
 * 验证列表中是否存在
 * @param medicineId
 * @returns {Boolean}
 */
function addExsit(medicineId) {
	var isExist = false;
	var $medicineList = $("#ss_tabs-3_drug input:hidden");
	$medicineList.each(function(i)
			{
				var value = $(this).val();
				if(value == medicineId.substring(0,6))
				{
					isExist = true ;
					return isExist;
				}
			});
	return isExist;
}


/*删除药物隐藏信息*/
function deleteMedicineName(obj)
{
	var td = $(obj).parent();
	var tr = td.parent();
	// 删除当前行
	tr.remove();
	// 改变背景颜色
	changeClass();
	// 设置提示信息
	initHideDrugViews();
}

function changeClass()
{
	$("#ss_tabs-3_drug tr:odd").not($("tr[id='tr2']")).attr("class","odd");
	$("#ss_tabs-3_drug tr:even").not($("tr[id='tr2']")).attr("class","even");
}


function initHideDrugViews()
{
	if($("#ss_tabs-3_drug tr").length == 1)
	{
		$("#ss_tabs-3_drug:first-child").append("<tr id='none_message'><td colspan='3' align='center' class='odd' height='26 '>没有相关数据！</td></tr>");
	}
	else
	{
		$("#none_message").remove();
	}
}
