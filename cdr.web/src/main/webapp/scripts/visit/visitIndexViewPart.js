function searchVisitIndex(url, formName, id, isAddHistory)							
{															
    var visitDept= $("#visitDept").val();		
    var visitType=$("#visitType").val();	
    
	var form = $("[name=" + formName + "]");						
	if(form == null|| form == '')						
	{		
		showMsg("提示","检索条件表单对象不存在！");
		return;					
	}						
	
	parent.loadPanel(url, form.serializeObject(), '#dyContent', {vid: '#visitIndexView', isAddHistory:isAddHistory});	
}		

