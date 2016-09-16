
//增加一个收藏夹
function createNewFolder(url, formName, id)
{
	var folderName = $("#folderName").val();	
	if($.trim(folderName) == "")
	{
		showMsg("新建文件夹","请输入文件夹名称！");
		$("#folderName").focus();
		return false;
	}

  	//$("#selectable").append("<li><div/><div>" + folderName + "</div></li>");

  	$("#postbackEvent").val("create_folder");		
	var form = $("[name=" + formName + "]");						
	if(form == null|| form == '')						
	{		
		showMsg("提示","检索条件表单对象不存在！");
		return;					
	}
	parent.loadPanel(form.attr("action"), form.serializeObject(), id);
}

// 收藏中新建文件夹
function createNewFolderAndloadDiv(url,id)
{
	var folderName = $("#loadfolderName").val();	
	if($.trim(folderName) == "")
	{
		showMsg("新建文件夹","请输入文件夹名称！");
		$("#folderName").focus();
		return false;
	}
	loadPanel(url, {'folderName':folderName,'postbackEvent':'create_folder'},id);
	$("#loadfolderName").val('');
}

//得到选择的收藏夹ID
function getSelectedFolderId()
{
    var selected_id = -1;
    
    $("#selectable li").each(function() { 
		 if($(this).hasClass("ui-selected"))
		 {
		 	selected_id = this.id;
		 }
    });

	return selected_id;
}

//是否有选中的收藏夹
function hasSelectedFolder()
{
	var selectedFolderId = getSelectedFolderId();
	if(selectedFolderId == -1)
		return false;
	else
		return true;
}

//删除收藏夹
function deleteFolder(url, formName, id)
{
	if(!hasSelectedFolder())
	{
		showMsg("删除文件夹","请选择要删除的文件夹！");
		return false;
	}
	confirmMsg("删除提示","确定要删除该文件夹吗？",function(){
		$("#postbackEvent").val("delete_folder");
		$("#selectedFolder").val(getSelectedFolderId());
		var form = $("[name=" + formName + "]");						
		parent.loadPanel(form.attr("action"), form.serializeObject(), id);
	});
}

//检索收藏夹
function searchFolder(url, formName, id)
{	
	if(!hasSelectedFolder())
	{
		showMsg("提示","请先选择患者的文件夹！");
		return false;
	}
	
	$("#selectedFolder").val(getSelectedFolderId());
  	$("#postbackEvent").val("search_folder");		
	var form = $("[name=" + formName + "]");						

	loadFavPatientList("../patient/list_pfpatientlist_" + $("#selectedFolder").val() + ".html?flag=" + getLoginFlag(),form.serializeObject(),id);
}


/**
 * 使用指定的URL和数据局部刷新页面
 */
function loadFavPatientList(url, data, lid)
{
   
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
			//如果状态是error或者timeout, 显示错误对话框
			if(status == "error" || status == "timeout")
			{
				setTimeout("showErrorDialog('#errorDialog', '" + xhr + "','" + status + "')",70);
			}
		}
	});

}

function closeDialog() {
    $("#ajaxDialog").remove();
    $("#dialog").append("<div id='ajaxDialog' style='display:none'></div>");
}

function searchFavPatient(url, formName, id)							
{		
/*
	var patientName = $("#patientName").val();
	var gender = $("#gender").val();
	if($.trim(patientName) == ""
		&& gender == "-1" ) 
	{
		alert("至少有一个查询条件！");
    	$("#patientName").focus();										
    	return;								
	}
*/	      						

	if(!hasSelectedFolder())
	{
		showMsg("提示","请先选择患者的文件夹！");
		return false;
	}
	  	
	var form = $("[name=" + formName + "]");						
	if(form == null|| form == '')						
	{						
		showMsg("提示","请先选择患者的文件夹！");			
		return;					
	}										

  	$("#pl_PostbackEvent").val("search_folder");
	loadFavPatientList("../patient/list_pfpatientlist_" + $("#selectedFolder").val() + ".html?flag=" + getLoginFlag(),form.serializeObject(),id);
}		



// $Author:bin_zhang
// $Date : 2012/10/11 15:10
// $[BUG]0010244 ADD BEGIN
function deleteFavPatient(url, formName, id)							
{		
	$("#pl_PostbackEvent").val("delete_patients");	
	var patientFavIds = "";	
    var len = $("input:checkbox[name=patientFavId]:checked").length;
    if(len == 0)
    {
    	showMsg("提示","请选择患者！");
	   return false;
    }

	$("input:checkbox[name=patientFavId]:checked").each(function() { 
		 if(patientFavIds != "")
		 	patientFavIds += ",";
		 patientFavIds += $(this).val();
    });
    
   
	var form = $("[name=" + formName + "]");						
	if(form == null|| form == '')						
	{				
		showMsg("提示","检索条件表单对象不存在！");
		return;					
	}	
	
	confirmMsg("删除收藏的患者提示","确定要删除收藏的患者吗？",function(){
		$("#pl_PatientFavIds").val(patientFavIds);
		loadFavPatientList("../patient/list_pfpatientlist_" + $("#selectedFolder").val() + ".html?flag=" + getLoginFlag(),form.serializeObject(),id);
	});

}	
// $[BUG]0010244 ADD END	

/**
 * 翻页处理
 */
function jumpToPatientFavListPage(pageno)
{
	var form = $("[name='conditionFormPatientList']");
	var href = form.attr("action");
	if(href.indexOf("?")>0)
	{
		href = href+"&pageNo=" + pageno;
	}
	else
	{
		href = href+"?pageNo=" + pageno;
	}

  	$("#pl_PostbackEvent").val("search_folder");		

	loadFavPatientList(href,form.serializeObject(),"#pfcontent");
}


/**
 * 处理列表分页的跳转_提示相关信息
 */
function jumpToPatientFavListPageNo(pageno,totalPageCnt)
{
	var form = $("[name='conditionFormPatientList']");
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

  	$("#pl_PostbackEvent").val("search_folder");		

	loadFavPatientList(href,form.serializeObject(),"#pfcontent");
}

function addPatientFav(url, formName)                           
{       
    var folderIds = ""; 
    var pf=$("#pfmemo").val();
    
    $("input:checkbox[name=folders]:checked").each(function() { 
		 if($(this).attr("disabled") != "disabled")
		 { 
	         if(folderIds != "")
	            folderIds += ",";
	         folderIds += $(this).val();
         }
    });

    if(folderIds == "")
    {
       showMsg("提示","请选择患者收藏夹！");
       return false;
    }
    if(pf=="请输入备注！")
    {
      $("#pfmemo").html('');
    }
    
    var form = $("[name=" + formName + "]");                        
    if(form == null|| form == '')                       
    {        
    	showMsg("提示","检索条件表单对象不存在！");
        return;                 
    }   
    
    confirmMsg("添加患者提示","确定要添加患者到选中的收藏夹吗？",function(){
    	$("#folderIds").val(folderIds);
        form.submit();
        closeDialog();
	});
}   


function createChildFolder(title, msg, parentFolderSn, callback,url,id) {
    $("#confirmMessage").html("<div id='msgBody' style='line-height:40px;text-align:center;'>" + msg + "</div>").dialog({
        title: title,
        autoOpen: true,
        modal: true,
        width: 400,
        height:200,
        bgiframe: true,
        buttons: {
            "创建": function() {
            	createNewChildFolder('conditionFormPatient',id, parentFolderSn,url);
                $(this).dialog("close");
            },
            "取消": function() {
                $(this).dialog("close");
            }
        }
    });
}

//增加一个子收藏夹
function createNewChildFolder(formName,id,parentFolderSn,url)
{
	var childFolderName = $("#childFolderName").val();	
	if($.trim(childFolderName) == "")
	{
		showMsg("新建子文件夹","请输入子文件夹名称！");
		$("#childFolderName").focus();
		return false;
	}

	if(typeof(url)==undefined||url==''){
		var form = $("[name=" + formName + "]");						
		if(form == null|| form == '')						
		{		
			showMsg("提示","检索条件表单对象不存在！");
			return;					
		}
		parent.loadPanel(form.attr("action"),{'postbackEvent':'create_child_folder','folderName':childFolderName,'parentFolderSn':parentFolderSn}, id);		
	}else{
		parent.loadPanel(url,{'postbackEvent':'create_child_folder','folderName':childFolderName,'parentFolderSn':parentFolderSn}, id);
	}
}

