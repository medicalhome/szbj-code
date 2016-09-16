$(document).ready(function(){
    	 
	controlColspan("#controlTr");
	
	var orderSn = getOrderSn();

	var divList = $("#moreTabs").children("div").children("div").children("div");

	$.each(divList,function(i,n){
		if(isNull($(this).attr("id")))
			$(this).attr("id","_"+orderSn)
	});	
	
	closeTab("#moreTabs",orderSn);
 });

//$Author :chang_xuewen
//$Date : 2013/11/06 11:00$
//[BUG]0039004 ADD BEGIN
/**
 * 针对BS357.xsl显示方式修改
 */
function controlColspan(controlId)
{
	if($(controlId).length>0){
		// 取得td个数
		var conspan = $(controlId+"~tr>td").length + 1;
		// 设置表头跨列数
		$(controlId+">td").attr("colspan",conspan);		
		var pre=0;
		var now=0;
		var tem=0;
		$("table[name='ii']").find("td[name='restTd']").each(function(){
			now = $(this).parent("tr").height();
			if(now > pre){
				tem = now;
				pre = now;
			}else
				tem = pre;
		});
		var maxH = tem;
		$("table[name='ii']").find("td[name='restTd']").each(function(){
			$(this).parent("tr").height(maxH);
		});
	}
}
function overChange(tr)
{
	var moveidx = tr.index();
	//$("td[name='restTd']:eq("+moveidx+")").attr("class","mouseover_forBS357");
	
	$("table[name='ii']").find("td[name='restTd']:eq("+moveidx+")").attr("class","mouseover_forBS357");
}
function outChange(tr)
{
	var moveidx = tr.index();
	$("table[name='ii']").find("td[name='restTd']:eq("+moveidx+")").attr("class","docdataitem2");
}
//[BUG]0039004 ADD END