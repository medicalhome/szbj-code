<%@page contentType="text/html; charset=gbk"%>
<%
  String root = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<title>皕杰报表演示中心</title>
	</head>
	
	<frameset rows="51,*" cols="*" framespacing="0" border="0">
		<frame src="<%=root%>/bios_demo/top.htm" name="topFrame" scrolling="NO" noresize>
		<frameset rows="*" cols="203,*" framespacing="1" frameborder="0" border="0" bordercolor="#333366">
			<frame src="<%=root%>/bios_demo/left.jsp" name="leftFrame" noresize>
			<frame src="<%=root%>/bios_demo/welcome.htm" name="mainFrame">
		</frameset>
	</frameset>
	
	<noframes>
		<body>

		</body>
	</noframes>
	
</html>
