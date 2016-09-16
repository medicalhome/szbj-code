<%@page contentType="text/html; charset=gbk"%>
<%@page import="java.util.List,bios.report.server.treeviewer.*"%>
<jsp:useBean id="getter" scope="page" class="bios.report.server.treeviewer.ReportsGetter" />
<%
  List dataItems = getter.getDataItems(pageContext.getServletContext());
  String root = request.getContextPath();
%>
<html>

	<head>
		<title></title>
		<link href="js/treeview.css" rel="stylesheet" type="text/css" />
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<style type="text/css">		
		</style>
	</head>

	<body leftmargin="20" background="img/leftbg.gif">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="q" align="left">
					<script language="JavaScript" src="js/treeview.js"></script>
					<div id="a">

<script language="JavaScript">
<!--
//新建树菜单
var context = '<%=root%>';
var tree=new TreeView("tree");
tree.add(1, 0, "报表 - 演示", "welcome.htm", "", "mainFrame", "img/project.gif");

tree.add(100, 1, "基本应用", "", "", "", "img/folder.gif");

tree.add(200, 1, "参数报表", "", "", "", "img/folder.gif");
tree.add(201, 200, "URL传递(GET)", context+"/bios_demo/url_params.jsp", "", "mainFrame", "img/report.gif");
tree.add(202, 200, "表单提交(POST)", context+"/bios_demo/form_params.jsp", "", "mainFrame", "img/report.gif");
tree.add(203, 200, "钻取报表", context+"/ReportEmitter?rpt=Demo/参数报表/钻取报表.brt", "", "mainFrame", "img/report.gif");
tree.add(203, 200, "CDR", context+"/ReportEmitter?rpt=Demo/参数报表/CDR.brt&params=sn=2422818", "", "mainFrame", "img/report.gif");

tree.add(300, 1, "分组报表", "", "", "", "img/folder.gif");
tree.add(301, 300, "1普通分组", context+"/ReportEmitter?rpt=Demo/分组报表/1普通分组.brt", "", "mainFrame", "img/report.gif");
tree.add(302, 300, "2过滤分组", context+"/ReportEmitter?rpt=Demo/分组报表/2过滤分组.brt", "", "mainFrame", "img/report.gif");
tree.add(303, 300, "3按段分组", context+"/ReportEmitter?rpt=Demo/分组报表/3按段分组.brt", "", "mainFrame", "img/report.gif");
tree.add(304, 300, "4不完全分组", context+"/ReportEmitter?rpt=Demo/分组报表/4不完全分组.brt", "", "mainFrame", "img/report.gif");
tree.add(305, 300, "5分组汇总", context+"/ReportEmitter?rpt=Demo/分组报表/5分组汇总.brt", "", "mainFrame", "img/report.gif");

tree.add(400, 1, "复杂扩展", "", "", "", "img/folder.gif");
tree.add(401, 400, "1多层交叉", context+"/ReportEmitter?rpt=Demo/复杂扩展/1多层交叉.brt", "", "mainFrame", "img/report.gif");
tree.add(402, 400, "2横向分片", context+"/ReportEmitter?rpt=Demo/复杂扩展/2横向分片.brt", "", "mainFrame", "img/report.gif");
tree.add(403, 400, "3纵向分片", context+"/ReportEmitter?rpt=Demo/复杂扩展/3纵向分片.brt", "", "mainFrame", "img/report.gif");
tree.add(404, 400, "4横向纵向分片", context+"/ReportEmitter?rpt=Demo/复杂扩展/4横向纵向分片.brt", "", "mainFrame", "img/report.gif");
tree.add(405, 400, "5扩展与静态", context+"/ReportEmitter?rpt=Demo/复杂扩展/5扩展与静态.brt", "", "mainFrame", "img/report.gif");
tree.add(406, 400, "6累积报表", context+"/ReportEmitter?rpt=Demo/复杂扩展/6累积报表.brt", "", "mainFrame", "img/report.gif");
tree.add(407, 400, "7占比演示", context+"/ReportEmitter?rpt=Demo/复杂扩展/7占比演示.brt", "", "mainFrame", "img/report.gif");
tree.add(408, 400, "8组内组间排名", context+"/ReportEmitter?rpt=Demo/复杂扩展/8组内组间排名.brt", "", "mainFrame", "img/report.gif");
tree.add(409, 400, "9孤立格运算", context+"/ReportEmitter?rpt=Demo/复杂扩展/9孤立格运算.brt", "", "mainFrame", "img/report.gif");

tree.add(500, 1, "图文展现", "", "", "", "img/folder.gif");
tree.add(501, 500, "动态色彩", context+"/ReportEmitter?rpt=Demo/图文展现/动态色彩.brt", "", "mainFrame", "img/report.gif");
tree.add(502, 500, "符号展示", context+"/ReportEmitter?rpt=Demo/图文展现/符号展示.brt", "", "mainFrame", "img/report.gif");
tree.add(503, 500, "图释功能", context+"/ReportEmitter?rpt=Demo/图文展现/图片与图释.brt", "", "mainFrame", "img/report.gif");
tree.add(504, 500, "大字段支持", context+"/ReportEmitter?rpt=Demo/图文展现/员工信息.brt", "", "mainFrame", "img/report.gif");

tree.add(600, 1, "图表演示", "", "", "", "img/folder.gif");
tree.add(601, 600, "饼状图", context+"/ReportEmitter?rpt=Demo/图表演示/饼状图.brt", "", "mainFrame", "img/report.gif");
tree.add(602, 600, "柱状图", context+"/ReportEmitter?rpt=Demo/图表演示/柱状图.brt", "", "mainFrame", "img/report.gif");
tree.add(603, 600, "线形图", context+"/ReportEmitter?rpt=Demo/图表演示/线形图.brt", "", "mainFrame", "img/report.gif");
tree.add(604, 600, "线性图", context+"/ReportEmitter?rpt=Demo/图表演示/线性图.brt", "", "mainFrame", "img/report.gif");
tree.add(605, 600, "散点图", context+"/ReportEmitter?rpt=Demo/图表演示/散点图.brt", "", "mainFrame", "img/report.gif");
tree.add(606, 600, "时序图", context+"/ReportEmitter?rpt=Demo/图表演示/时序图.brt", "", "mainFrame", "img/report.gif");
tree.add(607, 600, "仪表盘图", context+"/ReportEmitter?rpt=Demo/图表演示/仪表盘图.brt", "", "mainFrame", "img/report.gif");
tree.add(608, 600, "图表综合", context+"/ReportEmitter?rpt=Demo/图表演示/图表综合.brt", "", "mainFrame", "img/report.gif");
tree.add(609, 600, "图表钻取", context+"/ReportEmitter?rpt=Demo/高级特性/图表超链接/column.brt", "", "mainFrame", "img/report.gif");

tree.add(700, 1, "中国式报表", "", "", "", "img/folder.gif");
tree.add(701, 700, "斜线单元格", context+"/ReportEmitter?rpt=Demo/中国式报表/斜线应用.brt", "", "mainFrame", "img/report.gif");
tree.add(702, 700, "表头锁定", context+"/ReportEmitter?rpt=Demo/中国式报表/表头锁定.brt&pagecap=20", "", "mainFrame", "img/report.gif");
tree.add(703, 700, "字体自动调整", context+"/ReportEmitter?rpt=Demo/中国式报表/字体自动调整.brt", "", "mainFrame", "img/report.gif");
tree.add(704, 700, "子报表", context+"/bios_jsp/report_loader.jsp?rpt=Demo/中国式报表/子报表演示.brt&params=path_prefix=Demo/", "", "mainFrame", "img/report.gif");

tree.add(800, 1, "TagLib部署", "", "", "", "img/folder.gif");
tree.add(801, 800, "单页数据", context+"/bios_jsp/common_tag.jsp?rpt=Demo/复杂扩展/4横向纵向分片.brt", "", "mainFrame", "img/report.gif");
tree.add(802, 800, "多页数据", context+"/bios_jsp/common_tag.jsp?rpt=Demo/分组报表/5分组汇总.brt", "", "mainFrame", "img/report.gif");
tree.add(803, 800, "多表标签", context+"/bios_jsp/reportset_tag.jsp", "", "mainFrame", "img/report.gif");

tree.add(880, 1, "环比与同比", "", "", "", "img/folder.gif");
tree.add(881, 880, "环比上期1", context+"/ReportEmitter?rpt=Demo/环比与同比/环比上期1.brt", "", "mainFrame", "img/report.gif");
tree.add(882, 880, "环比上期2", context+"/ReportEmitter?rpt=Demo/环比与同比/环比上期2.brt", "", "mainFrame", "img/report.gif");
tree.add(883, 880, "环比与同比", context+"/ReportEmitter?rpt=Demo/环比与同比/环比与同比.brt", "", "mainFrame", "img/report.gif");

tree.add(900, 1, "高级特性", "", "", "", "img/folder.gif");
tree.add(901, 900, "自定义主格", "", "", "", "img/folder.gif");
tree.add(9011, 901, "条带报表", context+"/ReportEmitter?rpt=Demo/高级特性/自定义主格/条带报表.brt", "", "mainFrame", "img/report.gif");
tree.add(9012, 901, "逆向扩展", context+"/ReportEmitter?rpt=Demo/高级特性/自定义主格/逆向扩展.brt", "", "mainFrame", "img/report.gif");
tree.add(903, 900, "自定义数据集", context+"/ReportEmitter?rpt=Demo/高级特性/自定义数据集.brt", "", "mainFrame", "img/report.gif");
tree.add(904, 900, "套打示例", context+"/ReportEmitter?rpt=Demo/高级特性/套打演示.brt", "", "mainFrame", "img/report.gif");
tree.add(905, 900, "扩展位置", context+"/ReportEmitter?rpt=Demo/高级特性/扩展位置.brt", "", "mainFrame", "img/report.gif");

tree.add(1000, 1, "参数表单演示", "", "", "", "img/folder.gif");
tree.add(1001, 1000, "参数表单", context+"/bios_jsp/param_query.jsp?rpt=Demo/参数报表/时间参数.brt", "", "mainFrame", "img/report.gif");

tree.add(1300, 1, "更多应用", "", "", "", "img/folder.gif");
tree.add(1301, 1300, "动态排序", context+"/ReportEmitter?rpt=Demo/更多应用/动态排序.brt", "", "mainFrame", "img/report.gif");
tree.add(1302, 1300, "动态展开", context+"/ReportEmitter?rpt=Demo/更多应用/动态展开.brt", "", "mainFrame", "img/report.gif");
tree.add(1303, 1300, "分析报表", context+"/bios_jsp/param_query.jsp?rpt=Demo/更多应用/分析报表.brt&fitwidth=true&autoquery=true", "", "mainFrame", "img/report.gif");
tree.add(1305, 1300, "分析报表2", context+"/bios_demo/analysis2.jsp", "", "mainFrame", "img/report.gif");
tree.add(1304, 1300, "动态统计", context+"/bios_jsp/param_query.jsp?rpt=Demo/更多应用/动态统计.brt&fitwidth=true&autoquery=true&params=degree=bios_select_all;dept=bios_select_all", "", "mainFrame", "img/report.gif");

tree.add(1400, 1, "填报展示", "", "", "", "img/folder.gif");
tree.add(1401, 1400, "简单填报", context+"/ReportEmitter?rpt=Demo/填报/填报.brt", "", "mainFrame", "img/report.gif");
tree.add(1402, 1400, "行式填报", context+"/ReportEmitter?rpt=Demo/填报/行式填报.brt&toolbardisplay=top", "", "mainFrame", "img/report.gif");
tree.add(1403, 1400, "多源填报", context+"/ReportEmitter?rpt=Demo/填报/多源填报.brt", "", "mainFrame", "img/report.gif");
tree.add(1404, 1400, "分组填报", context+"/ReportEmitter?rpt=Demo/填报/分组填报.brt", "", "mainFrame", "img/report.gif");

tree.add(1500, 1, "集成应用", "", "", "", "img/folder.gif");
tree.add(1501, 1500, "批量导出", context + "/bios_demo/export.jsp", "", "mainFrame", "img/report.gif");
tree.add(1502, 1500, "批量打印", context + "/bios_jsp/batchPrint.jsp", "", "mainFrame", "img/report.gif");

tree.add(1100, 1, "在线帮助", "http://www.bijetsoft.com", "", "_blank", "img/help.gif");

document.write(tree);

var customTree=new TreeView("customTree");
<%
	for(int i=0;i<dataItems.size();i++){
  		DataItem item = (DataItem)dataItems.get(i);
	  	switch(item.getType()){
	  		case DataItem.PROJECT:{
	  		%>
	  			customTree.add(<%=item.getId()%>, <%=item.getParentId()%>, "<%=item.getName()%>", "", "", "", "img/project.gif");
	  		<%
	  			break;	
	  		}
	  		case DataItem.FOLDER:{
		  	%>
	  			customTree.add(<%=item.getId()%>, <%=item.getParentId()%>, "<%=item.getName()%>");
	  		<%
		  		break;
	  		}
	  		case DataItem.FILE:{
	  		%>
	  			customTree.add(<%=item.getId()%>, <%=item.getParentId()%>, "<%=item.getName()%>", "/report/<%=item.getUrl()%>", "", "mainFrame", "img/report.gif");
	  		<%
	  			break;
	  		}
	  	}
	}
%>
document.write(customTree);

//-->
</script>

					</div>
				</td>
			</tr>
		</table>

	</body>
</html>
