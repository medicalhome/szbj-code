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
	��ǰJava������֧�ֵ������б�
		<dir>
		<%
		 for(int i=0;i<fontNames.length;i++)
		  out.println("<li>"+fontNames[i]);
		%>
		</dir>
  </body>
</html>