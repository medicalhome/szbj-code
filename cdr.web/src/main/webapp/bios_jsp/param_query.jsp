<%@page contentType="text/html; charset=gbk"%>
<%@ taglib prefix="bios" uri="http://www.bijetsoft.com/BiosReportTags" %>
<%
String encode = "GBK";
request.setCharacterEncoding(encode);

String rpt = request.getParameter("rpt");

String params = request.getParameter("params");
String vars = request.getParameter("vars");

String rfscache = request.getParameter("rfscache");

String rptwidth = request.getParameter("rptwidth");
String rptheight = request.getParameter("rptheight");
String fitwidth = request.getParameter("fitwidth");
String rptskin = request.getParameter("rptskin");
if(rptskin == null)
	rptskin = "";

String toolbardisplay = request.getParameter("toolbardisplay");
String xlsbtn = request.getParameter("xlsbtn");
String xlsbtn1 = request.getParameter("xlsbtn1");
String pdfbtn = request.getParameter("pdfbtn");
String docbtn = request.getParameter("docbtn");
String printbtn = request.getParameter("printbtn");

boolean autoquery = "true".equalsIgnoreCase(request.getParameter("autoquery"));
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk">
		<script language="javascript">
			function setSize() {
				var params_tbl = document.getElementById('params_tbl');
				var rpt_div = document.getElementById('rpt_div');
				rpt_div.style.height = document.body.clientHeight - params_tbl.clientHeight - 20;
			}
			</script>
	</head>
	
	<body onload="setSize();" onresize="setSize();" style="margin:10px;">

		<table id="params_tbl" width="100%" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td style="font:normal bold 10pt 宋体;"><b>&nbsp;请输入查询条件</b></td>
			</tr>
			<tr>
				<td style="border-top: 1px solid gray;border-bottom: 1px solid gray;">
					
					<bios:query rpt="<%=rpt%>" target="rpt_frame" defaultparams="<%=params%>" defaultvars="<%=vars%>" rptskin="<%=rptskin%>" width='100%'>
					<!-- 可指向带进度条的加载页面 action="/report/bios_jsp/report_loader.jsp" -->
						<% if (rfscache != null) { %>
							<input type='hidden' name='rfscache' value="<%=rfscache%>">
						<% } %>
						
						<% if (rptwidth != null) { %>
							<input type='hidden' name='rptwidth' value="<%=rptwidth%>">
						<% } %>
						
						<% if (rptheight != null) { %>
							<input type='hidden' name='rptheight' value="<%=rptheight%>">
						<% } %>
						
						<% if (fitwidth != null) { %>
							<input type='hidden' name='fitwidth' value="<%=fitwidth%>">
						<% } %>
						
						<% if (toolbardisplay != null) { %>
							<input type='hidden' name='toolbardisplay' value="<%=toolbardisplay%>">
						<% } %>
						
						<% if (rptskin != null) { %>
							<input type='hidden' name='rptskin' value="<%=rptskin%>">
						<% } %>
						
						<% if (xlsbtn != null) { %>
							<input type='hidden' name='xlsbtn' value="<%=xlsbtn%>">
						<% } %>
						
						<% if (xlsbtn1 != null) { %>
							<input type='hidden' name='xlsbtn1' value="<%=xlsbtn1%>">
						<% } %>
						
						<% if (pdfbtn != null) { %>
							<input type='hidden' name='pdfbtn' value="<%=pdfbtn%>">
						<% } %>
						
						<% if (docbtn != null) { %>
							<input type='hidden' name='docbtn' value="<%=docbtn%>">
						<% } %>
						
						<% if (printbtn != null) { %>
							<input type='hidden' name='printbtn' value="<%=printbtn%>">
						<% } %>
								
						<input type='hidden' name='rptmargin' value="0">		
					</bios:query>
							
					<!-- 自动进行报表查询 -->
					<%if(autoquery){%>
						<script language='javascript'>
							function test(){
								var targetFrame = document.getElementById("rpt_frame_id");
								if(targetFrame) {
									biosSubmit(); //调用表单提交方法
									clearInterval(interval)
								}
							}
							var interval = setInterval(test, 200);
						</script>
					<%}%>
					
				</td>
			</tr>
		</table>

		<div id="rpt_div" style="width:100%;">
			<iframe src="" name="rpt_frame" id='rpt_frame_id' frameborder="0" style='width:100%;height:100%'>
		</div>
		
	</body>
</html>
