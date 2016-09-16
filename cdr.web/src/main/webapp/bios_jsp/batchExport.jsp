<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.servlet.*"%>
<%@ page import="java.net.*"%>

<%@ page import="bios.report.api.manager.*"%>
<%@ page import="org.apache.poi3.hssf.usermodel.HSSFWorkbook"%>

<%
	//rpts参数格式：a.brt|b.brt^params=p1=val1;p2=val2^vars=v1=val3;v2=val4
	//示例：rpt=Demo/分组报表/5分组汇总.brt|rpt=Demo/参数报表/时间参数.brt^params=year=1997
	
	request.setCharacterEncoding("GBK");
	
	String emitter = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/ReportEmitter";
	
	String rpts = request.getParameter("rpts");
	String fileName = request.getParameter("filename"); //导出文件名
	String fileType = request.getParameter("filetype"); //导出格式(xls)
	
	String[] rptArr = rpts.split("\\|");
	
	if("xls".equalsIgnoreCase(fileType)) {
		HSSFWorkbook wb = new HSSFWorkbook();
		for (int i = 0; i < rptArr.length; i++) {
			rptArr[i] = rptArr[i].replace('^', '&');
	
			try {
				URL url = new URL(emitter + "?" + rptArr[i] + "&emitter=toobj");
				HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
				urlc.setRequestMethod("GET");
				urlc.setRequestProperty("Content-type", "application/octet-stream");
	
				InputStream in = urlc.getInputStream();
				ReportBean rptBean = new ReportBean(in);
				rptBean.toExcel(wb);
				wb.setSheetName(i, rptBean.getReportName());
			} catch (Exception e) {
	
			}
		}
		response.resetBuffer();
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1") + ".xls");
		response.setContentType("application/x-msdownload;charset=GBK");
		ServletOutputStream outStream = response.getOutputStream();
		wb.write(outStream);
		outStream.flush();
		outStream.close();
		outStream = null;
		response.flushBuffer();
		out.clear();
		out = pageContext.pushBody();			
	}
	
%>