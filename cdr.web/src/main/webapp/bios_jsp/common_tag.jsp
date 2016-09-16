<%@page contentType="text/html; charset=gbk"%>
<%@ taglib prefix="bios" uri="http://www.bijetsoft.com/BiosReportTags" %>
<%
request.setCharacterEncoding("GBK");
String rpt = request.getParameter("rpt");
String params = request.getParameter("params");
String vars = request.getParameter("vars");
%>
<html>

	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<link rel="stylesheet" type="text/css" href="css/style.css" />
	</head>

	<body leftmargin="20" background="img/bg.gif">
		<table width="100%" align="center" cellpadding="0" cellspacing="0" style="table-layout:fixed">
			<tr>
				<td align="center"><b>TagLib应用演示 - <%=rpt%></b></td>
			</tr>
			<tr>
				<td align="center"><hr></td>
			</tr>
			<tr>
				<td style='position:relative'>
					<bios:report rpt="<%=rpt%>" params="<%=params%>" vars="<%=vars%>" rptmargin="0" rptheight="450" />
				</td>
			</tr>
			<tr>
				<td align="center">Copyright@ 2006-2012 BijetSoft</td>
			</tr>
		</table>

	</body>
</html>
