<%@page contentType="text/html; charset=gbk"%>
<html>
<head>
<script language="javascript">
	function getchkval(pre){
		var val = "";
		for(var i=1;;i++){
			var chk = document.all[pre + i];
			if(chk){
				if(chk.checked){
					if(val != "")
						val += "|";
					val += chk.value;
				}
			}else{
				break;
			}
		}
		return val;
	}
	function doExport(){
		paramForm.rpts.value = getchkval('rpt');
		paramForm.submit();	
	}	
</script>
</head>
<body leftmargin="20">
<table style="font-size:13px" cellspacing=0>
  <tr>
  	<td colspan='2'><b>批量导出</b></td>
  </tr>
  <form id="paramForm" method="post"  action='/report/bios_jsp/batchExport.jsp' method='post'>
  <input type="hidden" name="rpts" value="">
  <tr>
  	<td style='border-top: 1px solid gray;' valign='top'>选择报表：</td>
  	<td style='border-top: 1px solid gray;'>
  		<input type='checkbox' name='rpt1' value="rpt=Demo/参数报表/时间参数.brt^params=year=1997" checked>销售额统计年报_1997年 &nbsp;
  		<input type='checkbox' name='rpt2' value="rpt=Demo/参数报表/时间参数.brt^params=year=1998" checked>销售额统计年报_1998年 &nbsp;
  		<input type='checkbox' name='rpt3' value="rpt=Demo/分组报表/5分组汇总.brt" checked>员工信息分组汇总统计 &nbsp;
		</td>
  </tr>
  <tr>
  	<td>导出格式：</td>
  	<td>
  		<SELECT name="filetype">
				<OPTION value="xls">EXCEL格式
			</SELECT>
		</td>
  </tr>
  <tr>
  	<td>文件名称：</td>
  	<td>
  		<input type="text" name='filename'>
		</td>
  </tr>  
  <tr>
  	<td style='border-top: 1px solid gray;' colspan='2' align='right'><input type="button" value=" 导 出 " onclick="doExport()"></td>
  </tr>
  </form>
</table>
</body>
</html>