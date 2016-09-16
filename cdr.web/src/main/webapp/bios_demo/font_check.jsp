<%@ page contentType="text/html;charset=GBK" %>
<%@ page language="java" pageEncoding="GBK"%>
<jsp:directive.page import="java.awt.GraphicsEnvironment"/>
<%
 GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
 String[] fontNames= ge.getAvailableFontFamilyNames();
%>
<html>
  <head>
  </head>
  <body style="font-size: 14px">
	当前Java环境下支持的字体列表
		<dir>
		<%
		 for(int i=0;i<fontNames.length;i++)
		  out.println("<li>"+fontNames[i]);
		%>
		</dir>
  </body>
</html>