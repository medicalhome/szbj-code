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
  	<td colspan='2' style="font-size:14px"><b>��������ʾ��</b></td>
  </tr>
 	<tr>
  	<td colspan='2'><hr></td>
  </tr>
  <form id="paramForm" method="post" action="">
  <input type="hidden" name="rpt" value="Demo/��������/ʱ�����.brt">
  <input type="hidden" name="params" value="">
  <input type="hidden" name="vars" value="">
  <tr>
  	<td>��ѡ���ѯ��ݣ�</td>
  	<td>
  		<SELECT name="year">
				<OPTION value="1997">1997��
				<OPTION value="2006">2006��
				<OPTION value="2007">2007��
			</SELECT>
		</td>
  </tr>
  <tr>
  	<td><input type="button" value="�ύ��Servlet" onclick="chaxun(1)"></td>
  	<td><input type="button" value="�ύ��Taglib" onclick="chaxun(2)"></td>
  </tr>
  </form>
  <tr>
  	<td colspan='2'><hr></td>
  </tr>
</table>
</body>
</html>