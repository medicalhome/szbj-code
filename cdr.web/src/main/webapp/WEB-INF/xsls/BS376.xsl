<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:sc="urn:hl7-org:v3"  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:Constants="xalan://com.founder.cdr.core.Constants">
	<xsl:output method="html" indent="yes" version="4.0"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" />
	<xsl:template match="/sc:ClinicalDocument">
		<html>
			<head>
				<link type="text/css" rel="Stylesheet" href="../styles/jquery-ui-1.8.18.custom.modify.css" charset="UTF8" />
				<link type="text/css" rel="Stylesheet" href="../styles/layout-default-1.3.0rc29.15.css" charset="UTF8" />
				<link type="text/css" rel="Stylesheet" href="../styles/layout-cdr-dialog.css" charset="UTF8" />
				<link type="text/css" rel="Stylesheet" href="../styles/tablelist.css" charset="UTF8" />
				<style>
				.doclabel1
				{
				   font-size: 12px;
				   text-align: center;
				   padding-right: 3px;
				   heignt:30px;
				   width:13%;
				   background-color: #D5EAFF;
				   border-bottom:solid 1px #c0ddea;
				   color: #043a8f;
				}
				.doclabel12
				{
				   font-size: 12px;
				   text-align: right;
				   padding-right: 3px;
				   heignt:30px;
				   width:13%;
				   border-bottom:solid 1px #c0ddea;
				   color: #043a8f;
				}				
				.docdataitem12
				{
				   font-size: 12px;
				   font-weight: bold;
				   color: #0000FF;
				   padding-left: 5px;
				   text-align: left;
				   heignt:30px;
				   width:20%;
				   border-bottom:solid 1px #c0ddea;
				}				
				</style>
			   	<script type="text/javascript">
				     function getOrderSn()
				     {
						return '<xsl:value-of select="sc:setId/@extension"/>'+'<xsl:value-of select="sc:id/@extension"/>';
				     }
			    </script>
			    <script type="text/javascript" src="../scripts/doc/docDetail.js"></script>
			</head>
			<body>
				<div id="mainContent">
					<div name="selectTabs">
						<div class="responsability"><xsl:value-of select="Constants:getResponsabilityClaimingContent()"/></div>
						<table border="0" align="center" width="100%" cellspacing="2" cellpadding="1" style="border-collapse:collapse;bgcolor:white;">
							<tr>
								<td colspan="6" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"><h3><xsl:value-of select="sc:title"/></h3></td>
							</tr>
							<tr>
							    <td class="doclabel">科别：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								
								<td class="doclabel">患者姓名：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/></td>

							    <td class="doclabel">性别：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/></td>
							</tr>
							<tr>
								<td class="doclabel">病案号：</td>
								<td class="docdataitem"><!-- <xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/> --></td>

								<td class="doclabel">术者：</td>
								<td class="docdataitem"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:performer/sc:assignedEntity/sc:assignedPerson/sc:name"/></td>
								
								<td class="doclabel">年龄：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:age/@value"/></td>
								
							</tr>
							<tr>
								<td class="doclabel">麻醉方式：</td>
								<td class="docdataitem"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='10213-7']/sc:entry/sc:observation[sc:code/@code='DE06.00.073.00']/sc:value/@displayName"/></td>
								
								<td class="doclabel">手术方式：</td>
								<td class="docdataitem"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:entryRelationship/sc:observation[sc:code/@code='DE06.00.302.00']/sc:value/@displayName"/></td>
					
								<td class="doclabel">手术日期：</td>
								<td class="docdataitem"><xsl:choose>
									<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value)>8">
										<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value,7,2)"/>日<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value,9,2)"/>时<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value)>0">
										<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose></td>								
							</tr>
						</table>
						<table border="0" width="100%" cellspacing="0" cellpadding="0" style="border-collapse:collapse;table-layout:fixed;" class="table"> 
							<tr>
								<td class="blockHeader" height="27" align="center" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">麻醉实施前</td>
								<td class="blockHeader" height="27" align="center" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">手术开始前</td>
								<td class="blockHeader" height="27" align="center" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">患者离开手术室前</td>
							</tr>
							<tr>
								<td valign="top">
									<table border="0" cellpadding="0" cellspacing="0" width="100%" style="border-collapse:collapse;table-layout:fixed;" class="table">
										<tr>
											<td class="doclabel12">患者姓名、性别、年龄正确:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='麻醉实施前']/sc:entry/sc:observation[sc:code/@displayName='患者姓名、性别、年龄正确']/sc:value"/>
											</td>
										</tr>											
										<tr>
											<td class="doclabel12">手术方式确认:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='麻醉实施前']/sc:entry/sc:observation[sc:code/@displayName='手术方式确认']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">手术部位与标识正确:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='麻醉实施前']/sc:entry/sc:observation[sc:code/@displayName='手术部位与标识正确']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">手术知情同意:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='麻醉实施前']/sc:entry/sc:observation[sc:code/@displayName='手术知情同意']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">麻醉知情同意:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='麻醉实施前']/sc:entry/sc:observation[sc:code/@displayName='麻醉知情同意']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">麻醉方式确认:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='麻醉实施前']/sc:entry/sc:observation[sc:code/@displayName='麻醉方式确认']/sc:value"/>
											</td>
										</tr>																																																			
										<tr>
											<td class="doclabel12">麻醉设备安全检查完成:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='麻醉实施前']/sc:entry/sc:observation[sc:code/@displayName='麻醉设备安全检查完成']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">皮肤是否完整:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='麻醉实施前']/sc:entry/sc:observation[sc:code/@displayName='皮肤是否完整']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">术野皮肤准备正确:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='麻醉实施前']/sc:entry/sc:observation[sc:code/@displayName='术野皮肤准备正确']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">静脉通道建立完成:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='麻醉实施前']/sc:entry/sc:observation[sc:code/@displayName='静脉通道建立完成']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">患者是否有过敏史:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='麻醉实施前']/sc:entry/sc:observation[sc:code/@displayName='患者是否有过敏史']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">抗菌药物皮试结果:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='麻醉实施前']/sc:entry/sc:observation[sc:code/@displayName='抗菌药物皮试结果']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">术前备血:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='麻醉实施前']/sc:entry/sc:observation[sc:code/@displayName='术前备血']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">假体:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='麻醉实施前']/sc:entry/sc:observation[sc:code/@displayName='假体']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">体内植入物:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='麻醉实施前']/sc:entry/sc:observation[sc:code/@displayName='体内植入物']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">影像学资料:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='麻醉实施前']/sc:entry/sc:observation[sc:code/@displayName='影像学资料']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">其它:</td>
											<td class="docdataitem12">
												<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='麻醉实施前']/sc:entry/sc:observation[sc:code/@displayName='其它']/sc:value"/></pre>
											</td>
										</tr>																																																												
									</table>
								</td>
								<td valign="top">
									<table  border="0" cellpadding="0" cellspacing="0" style="border-collapse:collapse;table-layout:fixed;" class="table">
										<tr>
											<td class="doclabel12">患者姓名、性别、年龄正确:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='手术开始前']/sc:entry/sc:observation[sc:code/@displayName='患者姓名、性别、年龄正确']/sc:value"/>
											</td>
										</tr>											
										<tr>
											<td class="doclabel12">手术方式确认:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='手术开始前']/sc:entry/sc:observation[sc:code/@displayName='手术方式确认']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">手术部位与标识正确:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='手术开始前']/sc:entry/sc:observation[sc:code/@displayName='手术部位与标识正确']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td colspan="2" style="font-weight: bold;background-color: #D5EAFF;color: #000000;text-align:center;border-bottom:solid 1px #c0ddea;">手术麻醉风险预警:</td>
										</tr>
										<tr>
											<td colspan="2" style="border-bottom:solid 1px #c0ddea;color: #000000;text-align:left">手术医生陈述:</td>
										</tr>										
										<tr>
											<td class="doclabel12">预计手术时间:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='手术开始前']/sc:component/sc:section[sc:code/@displayName='手术医生陈述']/sc:entry/sc:observation[sc:code/@displayName='预计手术时间']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">预计失血量:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='手术开始前']/sc:component/sc:section[sc:code/@displayName='手术医生陈述']/sc:entry/sc:observation[sc:code/@displayName='预计失血量']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">手术关注点:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='手术开始前']/sc:component/sc:section[sc:code/@displayName='手术医生陈述']/sc:entry/sc:observation[sc:code/@displayName='手术关注点']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">其它:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='手术开始前']/sc:component/sc:section[sc:code/@displayName='手术医生陈述']/sc:entry/sc:observation[sc:code/@displayName='其它']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td colspan="2" style="border-bottom:solid 1px #c0ddea;color: #000000;text-align:left">麻醉医生陈述:</td>
										</tr>										
										<tr>
											<td class="doclabel12">麻醉关注点:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='手术开始前']/sc:component/sc:section[sc:code/@displayName='麻醉医生陈述']/sc:entry/sc:observation[sc:code/@displayName='麻醉关注点']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">其它:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='手术开始前']/sc:component/sc:section[sc:code/@displayName='麻醉医生陈述']/sc:entry/sc:observation[sc:code/@displayName='其它']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td colspan="2" style="border-bottom:solid 1px #c0ddea;color: #000000;text-align:left">手术护士陈述:</td>
										</tr>										
										<tr>
											<td class="doclabel12">物品灭菌合格:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='手术开始前']/sc:component/sc:section[sc:code/@displayName='手术护士陈述']/sc:entry/sc:observation[sc:code/@displayName='物品灭菌合格']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">仪器设备:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='手术开始前']/sc:component/sc:section[sc:code/@displayName='手术护士陈述']/sc:entry/sc:observation[sc:code/@displayName='仪器设备']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">术前术中特殊用药情况:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='手术开始前']/sc:component/sc:section[sc:code/@displayName='手术护士陈述']/sc:entry/sc:observation[sc:code/@displayName='术前术中特殊用药情况']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">其它:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='手术开始前']/sc:component/sc:section[sc:code/@displayName='手术护士陈述']/sc:entry/sc:observation[sc:code/@displayName='其它']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">是否需要相关影像资料:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='手术开始前']/sc:component/sc:section[sc:code/@displayName='手术护士陈述']/sc:entry/sc:observation[sc:code/@displayName='是否需要相关影像资料']/sc:value"/>
											</td>
										</tr>																																																																																																																
									</table>								
								</td>
								<td valign="top">
									<table  border="0" cellpadding="0" cellspacing="0" style="border-collapse:collapse;table-layout:fixed;" class="table">
										<tr>
											<td class="doclabel12">患者姓名、性别、年龄正确:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='患者离开手术室前']/sc:entry/sc:observation[sc:code/@displayName='患者姓名、性别、年龄正确']/sc:value"/>
											</td>
										</tr>											
										<tr>
											<td class="doclabel12">实际手术方式确认:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='患者离开手术室前']/sc:entry/sc:observation[sc:code/@displayName='实际手术方式确认']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">手术用药、输血的核查:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='患者离开手术室前']/sc:entry/sc:observation[sc:code/@displayName='手术用药、输血的核查']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">手术用物清点正确:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='患者离开手术室前']/sc:entry/sc:observation[sc:code/@displayName='手术用物清点正确']/sc:value"/>
											</td>
										</tr>											
										<tr>
											<td class="doclabel12">手术标本确认:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='患者离开手术室前']/sc:entry/sc:observation[sc:code/@displayName='手术标本确认']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">皮肤是否完整:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='患者离开手术室前']/sc:entry/sc:observation[sc:code/@displayName='皮肤是否完整']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td colspan="2" style="border-bottom:solid 1px #c0ddea;color: #000000;text-align:left;font-weight:bold;background-color: #D5EAFF;">各种管道:</td>
										</tr>										
										<tr>
											<td class="doclabel12">中心静脉通路:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='患者离开手术室前']/sc:component/sc:section[sc:code/@displayName='各种管道']/sc:entry/sc:observation[sc:code/@displayName='中心静脉通路']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">伤口引流:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='患者离开手术室前']/sc:component/sc:section[sc:code/@displayName='各种管道']/sc:entry/sc:observation[sc:code/@displayName='伤口引流']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">胃管:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='患者离开手术室前']/sc:component/sc:section[sc:code/@displayName='各种管道']/sc:entry/sc:observation[sc:code/@displayName='胃管']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">尿管:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='患者离开手术室前']/sc:component/sc:section[sc:code/@displayName='各种管道']/sc:entry/sc:observation[sc:code/@displayName='尿管']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">动脉通路:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='患者离开手术室前']/sc:component/sc:section[sc:code/@displayName='各种管道']/sc:entry/sc:observation[sc:code/@displayName='动脉通路']/sc:value"/>
											</td>
										</tr>																																																																																																																
										<tr>
											<td class="doclabel12">气管插口:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='患者离开手术室前']/sc:component/sc:section[sc:code/@displayName='各种管道']/sc:entry/sc:observation[sc:code/@displayName='气管插口']/sc:value"/>
											</td>
										</tr>																																																																																																																
<tr>
											<td colspan="2" style="border-bottom:solid 1px #c0ddea;color: #000000;text-align:left;font-weight:bold;background-color: #D5EAFF">患者去向:</td>
										</tr>										
										<tr>
											<td class="doclabel12">恢复室:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='患者离开手术室前']/sc:component/sc:section[sc:code/@displayName='患者去向']/sc:entry/sc:observation[sc:code/@displayName='恢复室']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">PACU:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='患者离开手术室前']/sc:component/sc:section[sc:code/@displayName='患者去向']/sc:entry/sc:observation[sc:code/@displayName='PACU']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">ICU:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='患者离开手术室前']/sc:component/sc:section[sc:code/@displayName='患者去向']/sc:entry/sc:observation[sc:code/@displayName='ICU']/sc:value"/>
											</td>
										</tr>										
										<tr>
											<td class="doclabel12">病房:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='患者离开手术室前']/sc:component/sc:section[sc:code/@displayName='患者去向']/sc:entry/sc:observation[sc:code/@displayName='病房']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">急诊:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='患者离开手术室前']/sc:component/sc:section[sc:code/@displayName='患者去向']/sc:entry/sc:observation[sc:code/@displayName='急诊']/sc:value"/>
											</td>
										</tr>
										<tr>
											<td class="doclabel12">离院:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='患者离开手术室前']/sc:component/sc:section[sc:code/@displayName='患者去向']/sc:entry/sc:observation[sc:code/@displayName='离院']/sc:value"/>
											</td>
										</tr>																																																																																																																
										<tr>
											<td class="doclabel12">病人去向其它:</td>
											<td class="docdataitem12">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='患者离开手术室前']/sc:component/sc:section[sc:code/@displayName='患者去向']/sc:entry/sc:observation[sc:code/@displayName='病人去向其它']/sc:value"/>
											</td>
										</tr>																																																											
									</table>								
								</td>
							</tr>
							<tr>
<!-- 							<xsl:choose>
								<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value)=0">
									<td class="blws" height="30" style="text-align:center;height:30px;">没有相关内容！</td>
								</xsl:when>
								<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value)>0">
									$Author :chang_xuewen
									$Date : 2013/07/24 11:00
									$[BUG]0034785 MODIFY BEGIN
									<td class="blws" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value"/></pre>
									</td>
									$[BUG]0034785 MODIFY END
								</xsl:when></xsl:choose> -->
							</tr>
						</table>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>