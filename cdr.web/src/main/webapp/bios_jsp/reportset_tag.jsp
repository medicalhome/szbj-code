<%@page contentType="text/html; charset=gbk"%>
<%@ taglib prefix="bios" uri="http://www.bijetsoft.com/BiosReportTags" %>
<%
request.setCharacterEncoding("GBK");
String rpt1 = "Demo/�й�ʽ����/��ͷ����.brt";
String rpt2 = "Demo/���鱨��/5�������.brt";
String rpt3 = "Demo/������չ/1��㽻��.brt";
%>
<html>

	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<link type="text/css" rel="stylesheet" href="/report/bios_web_res/component/tab/tabs.css" />
		<script src="/report/bios_web_res/component/tab/bios_tabs.js"></script>
	</head>

	<body leftmargin="20">
		<bios:reportSet>
			<bios:report name="��1" rpt="<%=rpt1%>" rptwidth="100%" rptheight="100%" />
			<bios:report name="��2" rpt="<%=rpt2%>" fitwidth="true" rptwidth="100%" rptheight="100%" toolbardisplay="top"/>
			<bios:report name="��3" rpt="<%=rpt3%>" rptwidth="100%" rptheight="100%" />
			<bios:reportItem name="��4" url="/report/bios_demo/welcome.htm" />	
		</bios:reportSet>
	</body>
</html>
