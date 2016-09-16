<%@page contentType="text/html; charset=gbk"%>
<html>
<head>
<script language="javascript">
	function chaxun(type){
		if(type == 1)
			paramForm.action = "<%=request.getContextPath()%>/ReportEmitter";
		else
			paramForm.action = "<%=request.getContextPath()%>/bios_jsp/common_tag.jsp";
		paramForm.params.value = "year=" + paramForm.year.value;
		//paramForm.vars.value = "...";
		paramForm.submit();	
	}	
</script>
<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body leftmargin="20">
<table border="0">
  <tr>
  	<td colspan='2' style="font-size:14px"><b>参数报表示例</b></td>
  </tr>
 	<tr>
  	<td colspan='2'><hr></td>
  </tr>
  <form id="paramForm" method="post" action="">
  <input type="hidden" name="rpt" value="Demo/参数报表/时间参数.brt">
  <input type="hidden" name="params" value="">
  <input type="hidden" name="vars" value="">
  <tr>
  	<td>请选择查询年份：</td>
  	<td>
  		<SELECT name="year">
				<OPTION value="1997">1997年
				<OPTION value="2006">2006年
				<OPTION value="2007">2007年
			</SELECT>
		</td>
  </tr>
  <tr>
  	<td><input type="button" value="提交到Servlet" onclick="chaxun(1)"></td>
  	<td><input type="button" value="提交到Taglib" onclick="chaxun(2)"></td>
  </tr>
  </form>
  <tr>
  	<td colspan='2'><hr></td>
  </tr>
</table>
</body>
</html>