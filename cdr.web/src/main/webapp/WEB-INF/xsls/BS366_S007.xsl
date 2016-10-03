<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:sc="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:Constants="xalan://com.yly.cdr.core.Constants">
	<xsl:output method="html" indent="yes" version="4.0"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" />
	<xsl:template match="/sc:ClinicalDocument">
		<html>
			<head>
				<meta http-equiv="Pragma" content="no-cache"/>
				<meta http-equiv="Cache-Control" content="no-cache"/>
				<meta http-equiv="Expires" content="0"/>
				<link type="text/css" rel="Stylesheet" href="../../styles/tablelist.css" charset="UTF8"/>
			</head>
			<body>
				<div id="mainContent">
					<div name="selectTabs">
						<div class="responsability">
							<xsl:value-of select="Constants:getResponsabilityClaimingContent()"/>
						</div>
						<xsl:choose>
						<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.112'
							and (sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='201'
							or sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='202'
							or sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='204'
							or sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='205')">						
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="2" style="border-bottom: solid 1px #B3C4D4;"></td>
								<td colspan="4" height="30" align="center" style="border-bottom: solid 1px #B3C4D4;">
									<h3 class="reporttitle"><xsl:value-of select="sc:title"/>
									</h3>
								</td>
								<td class="blockHeader" height="30" align="right" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;font-weight: bold;">
								<xsl:choose>
								<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:code/@code='310388008'">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code">
								<xsl:if test="@code='201' or @code='202'"><xsl:text>病理号：</xsl:text></xsl:if>				
								<xsl:if test="@code='204' or @code='205'"><xsl:text>检测号：</xsl:text></xsl:if>
								</xsl:for-each>
								</xsl:when>
								</xsl:choose>
								</td>
								<td class="docdataitem" height="30" align="left" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;font-weight: bold;">
								<xsl:value-of select="sc:id/@extension"/>
								</td>
							</tr>
							<xsl:choose>
							<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:code/@code='310388008'
							and (sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='201'
							or sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='202'
							or sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='204'
							or sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='205')">										
							<tr class="odd">
								<td class="doclabel">姓名：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/>
								</td>
								<td class="doclabel">性别：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/>
								</td>
								<td class="doclabel">年龄：</td>
								<td class="docdataitem">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
								  <xsl:choose>
									<xsl:when test="sc:observation/sc:code/@code='397669002'">
										<xsl:value-of select="sc:observation/sc:value"/>
									</xsl:when>
								  </xsl:choose>
								</xsl:for-each>
								</td>
								<td class="doclabel">送检医院：</td>
								<td class="docdataitem">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:performer/sc:assignedEntity/sc:representedOrganization/sc:name"/>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">住院号：</td>
								<td class="docdataitem">
								<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
									<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
											<xsl:choose>
												<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
												<xsl:value-of select="@extension"/>
												</xsl:when>
											</xsl:choose>		
									</xsl:for-each></xsl:if>
								</td>								
								<td class="doclabel" >就诊卡号：</td>
								<td class="docdataitem" colspan="3">
								<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='01' or sc:componentOf/sc:encompassingEncounter/sc:code/@code='0201'">
									<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
											<xsl:choose>
												<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
												<xsl:value-of select="@extension"/>
												</xsl:when>
											</xsl:choose>		
									</xsl:for-each></xsl:if>
								</td>
								<td class="doclabel" >送检日期：</td>
								<td class="docdataitem">
								<xsl:choose>
									<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value)>12">
										<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(author[1]/time/@value,7,2)"/><xsl:text> </xsl:text><xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,9,2)"/>:<xsl:value-of select="substring(author[1]/time/@value,11,2)"/>:<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,13,2)"/>
									</xsl:when>									
									<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value)>8">
										<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,7,2)"/><xsl:text> </xsl:text><xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,9,2)"/>:<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,11,2)"/>
									</xsl:when>
									<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value)>0">
										<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,7,2)"/>
									</xsl:when>
								</xsl:choose>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">病区：</td>
								<td class="docdataitem" colspan="5">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
								  <xsl:choose>
									<xsl:when test="sc:observation/sc:value/@codeSystem='1.2.156.112656.1.1.33'">
										<xsl:value-of select="sc:observation/sc:value"/>
									</xsl:when>
								  </xsl:choose>
								</xsl:for-each>
								</td>
								<td class="doclabel">床位号：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
								</td>
							</tr>
							</xsl:when>
						
							</xsl:choose>								
							<tr class="odd">
							<td class="doclabel">影像：</td>
								<td colspan="7" style="border-bottom: solid 1px #B3C4D4;">
									<xsl:element name="a">
										<xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:supply/sc:id/@extension" />
										</xsl:attribute>
										<xsl:attribute name="target">_blank</xsl:attribute>
										<img src="../images/pic_jc.png" width="22" height="22" border="0" />
									</xsl:element>
								</td>
							</tr>
							<xsl:choose>
							<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:code/@code='310388008'">
							<xsl:if test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='201'
							 or sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='202'">
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
									镜下所见:
								</td>
							</tr>
							<tr>
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:observationMedia">
								<xsl:if test="sc:value !=''">
								<xsl:if test="last() = 1">
								<td colspan="8" align="center" style="border-bottom: solid 1px #B3C4D4;">
								<div style="margin:5px;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:value" /></xsl:attribute>
									<xsl:attribute name="width">700px</xsl:attribute>
									<xsl:attribute name="hight">700px</xsl:attribute> 
									<xsl:attribute name="hspace">4px</xsl:attribute>
									<xsl:attribute name="vspace">10px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
								</xsl:if>
								<xsl:if test="last() > 1">
								<td colspan="4" align="center" style="border-bottom: solid 1px #B3C4D4;">
								<div style="margin:5px;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:value" /></xsl:attribute>
									<xsl:attribute name="width">350px</xsl:attribute>
									<xsl:attribute name="hspace">4px</xsl:attribute>
									<xsl:attribute name="vspace">10px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
								</xsl:if>
								<xsl:if test="position() mod 2=0">
								<tr>
								</tr>
								</xsl:if>
								</xsl:if>
								</xsl:for-each>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value">
							<xsl:choose>
							<xsl:when test="@code='02'">
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
											病理诊断:
								</td>
							</tr>							
							<tr height="20"><td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:originalText"/></pre>
								</td>
							</tr>								
							</xsl:when>								
							</xsl:choose>
							</xsl:for-each>								
							<tr class="odd">
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">诊断医生:</td>
								<td class="docdataitem"  style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer">								
									<xsl:value-of select="sc:assignedEntity/sc:assignedPerson/sc:name"/>
								  	<xsl:if test="position()!=last()">&#160;</xsl:if>
									</xsl:for-each>	
								</td>
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">复诊医生:</td>
								<td class="docdataitem" colspan="3" style="border-top: solid 1px #B3C4D4;">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:participantRole/sc:playingEntity/sc:name">
									<xsl:value-of select="."/>	
									<xsl:if test="position()!=last()">&#160;</xsl:if>							
								</xsl:for-each>										
								</td>
								<td class="doclabel"  style="border-top: solid 1px #B3C4D4;">报告日期：</td>
								<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select=".">
									<xsl:if test="count(sc:author)= 1">
									<xsl:choose>
										<xsl:when test="string-length(sc:author[1]/sc:time/@value)>12">
											<xsl:value-of select="substring(sc:author[1]/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author[1]/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author[1]/sc:time/@value,7,2)"/><xsl:text> </xsl:text><xsl:value-of select="substring(sc:author[1]/sc:time/@value,9,2)"/>:<xsl:value-of select="substring(sc:author[1]/sc:time/@value,11,2)"/>:<xsl:value-of select="substring(sc:author[1]/sc:time/@value,13,2)"/>
										</xsl:when>									
										<xsl:when test="string-length(sc:author[1]/sc:time/@value)>8">
											<xsl:value-of select="substring(sc:author[1]/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author[1]/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author[1]/sc:time/@value,7,2)"/><xsl:text> </xsl:text><xsl:value-of select="substring(sc:author[1]/sc:time/@value,9,2)"/>:<xsl:value-of select="substring(sc:author[1]/sc:time/@value,11,2)"/>
										</xsl:when>
										<xsl:when test="string-length(sc:author[1]/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:author[1]/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author[1]/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author[1]/sc:time/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
									</xsl:if>
									<xsl:if test="count(sc:author)>= 2">
									<xsl:choose>
										<xsl:when test="string-length(sc:author[2]/sc:time/@value)>12">
											<xsl:value-of select="substring(sc:author[2]/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author[2]/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(author[2]/sc:time/@value,7,2)"/><xsl:text> </xsl:text><xsl:value-of select="substring(sc:author[2]/sc:time/@value,9,2)"/>:<xsl:value-of select="substring(sc:author[2]/sc:time/@value,11,2)"/>:<xsl:value-of select="substring(sc:author[2]/sc:time/@value,13,2)"/>
										</xsl:when>									
										<xsl:when test="string-length(sc:author[2]/sc:time/@value)>8">
											<xsl:value-of select="substring(sc:author[2]/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author[2]/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author[2]/sc:time/@value,7,2)"/><xsl:text> </xsl:text><xsl:value-of select="substring(sc:author[2]/sc:time/@value,9,2)"/>:<xsl:value-of select="substring(sc:author[2]/sc:time/@value,11,2)"/>
										</xsl:when>
										<xsl:when test="string-length(sc:author[2]/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:author[2]/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author[2]/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author[2]/sc:time/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
									</xsl:if>
									</xsl:for-each>	
								</td>
							</tr>	
							</xsl:if>
							<xsl:if test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='204'">
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
									病理号及临床诊断：
								</td>
							</tr>
							<tr height="20"><td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
									<xsl:text>原病理号[</xsl:text><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:observation/sc:id/@extension"/><xsl:text>]的诊断</xsl:text>									
								</td>
							</tr>							
							<tr height="20"><td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value/sc:originalText"/></pre>
								</td>
							</tr>
							<tr height="40">
								<td colspan="8" class="blws" style="border-bottom: solid 1px #B3C4D4;word-break:break-all;word-wrap:break-word;">									
								</td>
							</tr>
							<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td class="blockHeader" height="27" width="80" align="left" style="padding-left: 3px;font-weight: bold;">
									标本类型：
								</td>
								<td class="blws" align="left" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:participantRole/sc:code/@displayName"/></pre>
								</td>								
							</tr>
							<tr>
								<td class="blockHeader" height="27" align="left" style="padding-left: 3px;font-weight: bold;">
									检测方法：
								</td>
								<td class="blws" align="left" style="word-break:break-all;word-wrap:break-word;">
								<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:methodCode/@displayName"/></pre>
								</td>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value">
							<xsl:choose>
							<xsl:when test="@code='01'">						
							<tr>
								<td class="blockHeader" height="27" align="left" style="padding-left: 3px;font-weight: bold;">
									检测结果：
								</td>
							</tr>	
							<tr>
								<td colspan="2" class="blws" style="word-break:break-all;word-wrap:break-word;">
								<pre><xsl:value-of select="sc:originalText"/></pre>
								</td>
							</tr>
							</xsl:when>							
							</xsl:choose>
							</xsl:for-each>	
							<tr height="40">
								<td class="blws" colspan="2" style="border-bottom: solid 1px #B3C4D4;word-break:break-all;word-wrap:break-word;">									
								</td>
							</tr>							
							</table>
							<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value">
							<xsl:choose>
							<xsl:when test="@code='02'">
							<tr>
								<td class="blockHeader" height="27" align="left" style="padding-left: 3px;font-weight: bold;">
									病理学诊断:
								</td>
							</tr>							
							<tr><td class="blws" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:originalText"/></pre>
								</td>
							</tr>								
							</xsl:when>								
							</xsl:choose>
							</xsl:for-each>								
							</table>																			
							</xsl:if>
							<xsl:if test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='205'">
							<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td class="blockHeader" height="27" width="80" align="left" style="padding-left: 3px;font-weight: bold;">
									标本类型：
								</td>
								<td class="blws" align="left" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:participantRole/sc:code/@displayName"/></pre>
								</td>								
							</tr>
							<tr>
								<td colspan="2" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
									临床诊断：
								</td>
							</tr>
							<tr height="20"><td colspan="2" class="blws" style="word-break:break-all;word-wrap:break-word;">
									<xsl:text>原病理号[</xsl:text><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:observation/sc:id/@extension"/><xsl:text>]的诊断</xsl:text>									
								</td>
							</tr>							
							<tr height="20"><td colspan="2" class="blws" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value/sc:originalText"/></pre>
								</td>
							</tr>											
							</table>
							<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td class="blockHeader" height="27" align="left" style="padding-left: 3px;font-weight: bold;">
									检测项目内容：
								</td>
							</tr>
							<tr height="20">
								<td colspan="2" class="blws" style="word-break:break-all;word-wrap:break-word;">
									<xsl:text>1、检测内容:</xsl:text>
									<xsl:choose>
									<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:code/@code !='310388008'">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@displayName"/>
									</xsl:when>
									</xsl:choose>
								</td>
							</tr>
							<tr height="20">
								<td colspan="2" class="blws" style="word-break:break-all;word-wrap:break-word;">
									<xsl:text>2、检测方法:</xsl:text>
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:methodCode/@displayName"/>
								</td>
							</tr>
							<tr height="20">
								<td colspan="2" class="blws" style="word-break:break-all;word-wrap:break-word;">
									<xsl:text>3、DNA抽取方法:</xsl:text>
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:methodCode/@displayName"/>
								</td>
							</tr>	
							<tr height="20">
								<td colspan="2" class="blws" style="word-break:break-all;word-wrap:break-word;">
									<xsl:text>4、主要设备:</xsl:text>
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:participantRole/sc:playingDevice/sc:manufacturerModelName/@code"/>
								</td>
							</tr>																											
							</table>
							<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
							<tr>
								<td class="blockHeader" height="27" align="left" style="padding-left: 3px;font-weight: bold;">
									肿瘤相关基因突变：
								</td>
							</tr>
							<tr height="20">
								<td>									
								</td>
							</tr>	
							<table border="border:1px solid black;" align="center" width="95%" cellspacing="1" cellpadding="2" style="margin-left:5px;border-collapse:collapse;table-layout:fixed;bgcolor:white;">
							<tr>
								<td align="center">检测项目</td>
								<td align="center">拼接外显子</td>
								<td align="center">突变类型</td>
								<td align="center">检测结果</td>
							</tr>
							<tr>
								<td align="center" rowspan="3">融合基因检测</td>
								<td align="center" style="word-break:break-all;word-wrap:break-word;">Exon-13,14</td>
								<td align="center" style="word-break:break-all;word-wrap:break-word;">Exon-13,14Exon-13,14Exon-13,14</td>
							</tr>
							<tr>
								<td align="center" style="word-break:break-all;word-wrap:break-word;">Exon-13,14</td>
								<td align="center" style="word-break:break-all;word-wrap:break-word;">Exon-13,14Exon-13,14Exon-13,14Exon-13,14</td>
								<td align="center" style="word-break:break-all;word-wrap:break-word;">Exon-13,14</td>
							</tr>
							<tr>
								<td align="center" style="word-break:break-all;word-wrap:break-word;">Exon-13,14</td>
								<td align="center" style="word-break:break-all;word-wrap:break-word;">Exon-13,14Exon-13,14Exon-13,14Exon-13,14</td>
								<td align="center" style="word-break:break-all;word-wrap:break-word;">Exon-13,14</td>
							</tr>
							</table>							
							</table>
							<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
							<tr>
								<td class="blockHeader" height="27" align="left" style="padding-left: 3px;font-weight: bold;">
									检测结果附图：
								</td>
							</tr>
							<tr>
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:observationMedia">
								<xsl:if test="sc:value !=''">
								<xsl:if test="last() = 1">
								<td colspan="8" align="center" style="border-bottom: solid 1px #B3C4D4;">
								<div style="margin:5px;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:value" /></xsl:attribute>
									<xsl:attribute name="width">280px</xsl:attribute>
									<xsl:attribute name="hight">280px</xsl:attribute> 
									<xsl:attribute name="hspace">4px</xsl:attribute>
									<xsl:attribute name="vspace">20px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
								</xsl:if>
								<xsl:if test="last() > 1">
								<td colspan="4" align="center" style="border-bottom: solid 1px #B3C4D4;">
								<div style="margin:5px;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:value" /></xsl:attribute>
									<xsl:attribute name="width">280px</xsl:attribute>
									<xsl:attribute name="hight">280px</xsl:attribute> 
									<xsl:attribute name="hspace">4px</xsl:attribute>
									<xsl:attribute name="vspace">20px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
								</xsl:if>
								<xsl:if test="position() mod 2=0">
								<tr>
								</tr>
								</xsl:if>
								</xsl:if>
								</xsl:for-each>
							</tr>														
							</table>																										
							</xsl:if>							
							</xsl:when>
							</xsl:choose>															
						</table>
						</xsl:when>
						<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:code/@code='310388008'
							and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='203'">
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="2" style="border-bottom: solid 1px #B3C4D4;"></td>
								<td colspan="4" height="50" align="center" style="border-bottom: solid 1px #B3C4D4;">
									<h3 class="reporttitle"><xsl:value-of select="sc:title"/>
									</h3>
								</td>
								<td class="blockHeader" height="30" align="right" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;font-weight: bold;">
								病理编号：
								</td>
								<td class="docdataitem" height="30" align="left" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;font-weight: bold;">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:observation/sc:id/@extension"/>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">病人姓名：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/>
								</td>
								<td class="doclabel">性别：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/>
								</td>
								<td class="doclabel">年龄：</td>
								<td class="docdataitem">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
								  <xsl:choose>
									<xsl:when test="sc:observation/sc:code/@code='397669002'">
										<xsl:value-of select="sc:observation/sc:value"/>
									</xsl:when>
								  </xsl:choose>
								</xsl:for-each>
								</td>
								<td class="doclabel">送检医院：</td>
								<td class="docdataitem">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:performer/sc:assignedEntity/sc:representedOrganization/sc:name"/>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">住院号：</td>
								<td class="docdataitem" colspan="3">
								<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
									<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
											<xsl:choose>
												<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
												<xsl:value-of select="@extension"/>
												</xsl:when>
											</xsl:choose>		
									</xsl:for-each></xsl:if>
								</td>								
								<td class="doclabel" >就诊号：</td>
								<td class="docdataitem" colspan="3">
								<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='01' or sc:componentOf/sc:encompassingEncounter/sc:code/@code='0201'">
									<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
											<xsl:choose>
												<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
												<xsl:value-of select="@extension"/>
												</xsl:when>
											</xsl:choose>		
									</xsl:for-each></xsl:if>
								</td>		
							</tr>		
							<tr class="odd">
								<td class="doclabel">病区号：</td>
								<td class="docdataitem" colspan="3">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
								  <xsl:choose>
									<xsl:when test="sc:observation/sc:value/@codeSystem='1.2.156.112656.1.1.33'">
										<xsl:value-of select="sc:observation/sc:value"/>
									</xsl:when>
								  </xsl:choose>
								</xsl:for-each>
								</td>
								<td class="doclabel">床号：</td>
								<td class="docdataitem" colspan="3">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
								</td>												
							</tr>					
							<tr class="odd">
								<td class="doclabel">影像：</td>
								<td colspan="7" style="border-bottom: solid 1px #B3C4D4;">
									<xsl:element name="a">
										<xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:supply/sc:id/@extension" />
										</xsl:attribute>
										<xsl:attribute name="target">_blank</xsl:attribute>
										<img src="../images/pic_jc.png" width="22" height="22" border="0" />
									</xsl:element>
								</td>
							</tr>
							<tr class="odd">
							<td class="blockHeader" colspan="8" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
							诊断：
							</td>	
							</tr>
							<tr class="odd">
							<td colspan="8" style="border-bottom: solid 1px #B3C4D4;">
							</td>
							</tr>
							<tr class="odd">
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">诊断医师:</td>
								<td class="docdataitem"  style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer">
									<xsl:if test="last()= 1">
									<xsl:value-of select="sc:assignedEntity/sc:assignedPerson/sc:name"/>
									</xsl:if>
									<xsl:if test="last()>= 2 and position()=2">
									<xsl:value-of select="sc:assignedEntity/sc:assignedPerson/sc:name"/>
									</xsl:if>
									</xsl:for-each>	
								</td>
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">复核医师:</td>
								<td class="docdataitem" colspan="3" style="border-top: solid 1px #B3C4D4;">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:participantRole/sc:playingEntity/sc:name"/>										
								</td>
								<td class="doclabel"  style="border-top: solid 1px #B3C4D4;">打印日期：</td>
								<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
									<xsl:choose>
										<xsl:when test="string-length(sc:effectiveTime/@value)>12">
											<xsl:value-of select="substring(sc:effectiveTime/@value,1,4)"/>-<xsl:value-of select="substring(sc:effectiveTime/@value,5,2)"/>-<xsl:value-of select="substring(sc:effectiveTime/@value,7,2)"/><xsl:text> </xsl:text><xsl:value-of select="substring(sc:effectiveTime/@value,9,2)"/>:<xsl:value-of select="substring(sc:effectiveTime/@value,11,2)"/>:<xsl:value-of select="substring(sc:effectiveTime/@value,13,2)"/>
										</xsl:when>									
										<xsl:when test="string-length(sc:effectiveTime/@value)>8">
											<xsl:value-of select="substring(sc:effectiveTime/@value,1,4)"/>-<xsl:value-of select="substring(sc:effectiveTime/@value,5,2)"/>-<xsl:value-of select="substring(sc:effectiveTime/@value,7,2)"/><xsl:text> </xsl:text><xsl:value-of select="substring(sc:effectiveTime/@value,9,2)"/>:<xsl:value-of select="substring(sc:effectiveTime/@value,11,2)"/>
										</xsl:when>
										<xsl:when test="string-length(sc:effectiveTime/@value)>0">
											<xsl:value-of select="substring(sc:effectiveTime/@value,1,4)"/>-<xsl:value-of select="substring(sc:effectiveTime/@value,5,2)"/>-<xsl:value-of select="substring(sc:effectiveTime/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>
							</tr>																			
						</table>		
						</xsl:when>
						<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:code/@code='310388008'
							and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='207'">
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="2" style="border-bottom: solid 1px #B3C4D4;"></td>
								<td colspan="2" height="50" align="center" style="border-bottom: solid 1px #B3C4D4;">
									<h3 class="reporttitle"><xsl:value-of select="sc:title"/>
									</h3>
								</td>
								<td class="blockHeader" height="30" align="right" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;font-weight: bold;">
								<xsl:text>编号：</xsl:text>
								</td>
								<td class="docdataitem" height="30" align="left" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;font-weight: bold;">
								<xsl:value-of select="sc:id/@extension"/>
								</td>
							</tr>							
							<tr class="odd">
								<td class="doclabel">姓名：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/>
								</td>
								<td class="doclabel">年龄：</td>
								<td class="docdataitem">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
								  <xsl:choose>
									<xsl:when test="sc:observation/sc:code/@code='397669002'">
										<xsl:value-of select="sc:observation/sc:value"/>
									</xsl:when>
								  </xsl:choose>
								</xsl:for-each>
								</td>
								<td class="doclabel">取样日期：</td>
								<td class="docdataitem">
									<xsl:choose>
										<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:effectiveTime/@value)>0">
											<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:effectiveTime/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:effectiveTime/@value,5,2)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:effectiveTime/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">末次月经时间：</td>
								<td class="docdataitem">
									<xsl:choose>
										<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:component/sc:section/sc:entry/sc:observation/sc:value/@type='TS' and string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:component/sc:section/sc:entry/sc:observation/sc:value/@value)>0">
											<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:component/sc:section/sc:entry/sc:observation/sc:value/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:component/sc:section/sc:entry/sc:observation/sc:value/@value,5,2)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:component/sc:section/sc:entry/sc:observation/sc:value/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>
								<td class="doclabel">绝经：</td>
								<td class="docdataitem">
									<xsl:choose>
										<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:component/sc:section/sc:entry/sc:observation/sc:value/@type='ST' and string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:component/sc:section/sc:entry/sc:observation/sc:value/@value)>0">
											<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:component/sc:section/sc:entry/sc:observation/sc:value/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:component/sc:section/sc:entry/sc:observation/sc:value/@value,5,2)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:component/sc:section/sc:entry/sc:observation/sc:value/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>								
								<td class="doclabel">收到日期：</td>
								<td class="docdataitem">
									<xsl:choose>
										<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">病区：</td>
								<td class="docdataitem">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
								  <xsl:choose>
									<xsl:when test="sc:observation/sc:value/@codeSystem='1.2.156.112656.1.1.33'">
										<xsl:value-of select="sc:observation/sc:value"/>
									</xsl:when>
								  </xsl:choose>
								</xsl:for-each>
								</td>
								<td class="doclabel">床位号：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
								</td>
								<td class="doclabel">住院号：</td>
								<td class="docdataitem">
								<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
									<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
											<xsl:choose>
												<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
												<xsl:value-of select="@extension"/>
												</xsl:when>
											</xsl:choose>		
									</xsl:for-each></xsl:if>
								</td>								
							</tr>
							<tr>
								<td class="doclabel">联系方式：</td>
								<td class="docdataitem">
								</td>
								<td class="doclabel" >就诊卡号：</td>
								<td class="docdataitem">
								<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='01' or sc:componentOf/sc:encompassingEncounter/sc:code/@code='0201'">
									<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
											<xsl:choose>
												<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
												<xsl:value-of select="@extension"/>
												</xsl:when>
											</xsl:choose>		
									</xsl:for-each></xsl:if>
								</td>
								<td class="doclabel">取样医生：</td>
								<td class="docdataitem">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:performer/sc:assignedEntity/sc:assignedPerson/sc:name"/>
								</td>							
							</tr>
							<tr class="odd">
							<td class="doclabel">影像：</td>
							<td colspan="5" style="border-bottom: solid 1px #B3C4D4;">
								<xsl:element name="a">
									<xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:supply/sc:id/@extension" />
									</xsl:attribute>
									<xsl:attribute name="target">_blank</xsl:attribute>
									<img src="../images/pic_jc.png" width="22" height="22" border="0" />
								</xsl:element>
							</td>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value">
							<xsl:choose>
							<xsl:when test="@code='01'">						
							<tr height="20"><td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;">
								<pre><xsl:value-of select="sc:originalText"/></pre>
								</td>
							</tr>
							</xsl:when>
							<xsl:when test="@code='02'">
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
									诊断:
								</td>
							</tr>							
							<tr height="20"><td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:originalText"/></pre>
								</td>
							</tr>								
							</xsl:when>								
							</xsl:choose>
							</xsl:for-each>	
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
									补充意见:
								</td>
							</tr>														
							<tr>
							<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:text"/>	
							</td>
							</tr>
							<tr class="odd">
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">报告医师:</td>
								<td class="docdataitem"  style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select=".">
									<xsl:if test="count(sc:author)= 1">
									<xsl:value-of select="sc:author[1]/sc:assignedAuthor/sc:assignedPerson/sc:name"/>
									</xsl:if>
									<xsl:if test="count(sc:author)>= 2">
									<xsl:value-of select="sc:author[2]/sc:assignedAuthor/sc:assignedPerson/sc:name"/>
									</xsl:if>
									</xsl:for-each>	
								</td>
								<td colspan="2"></td>
								<td class="doclabel"  style="border-top: solid 1px #B3C4D4;">报告日期：</td>
								<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select=".">
									<xsl:if test="count(sc:author)= 1">
										<xsl:choose>
										<xsl:when test="string-length(sc:author/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>
										</xsl:when>
										</xsl:choose>
									</xsl:if>
									<xsl:if test="count(sc:author)>= 2">
										<xsl:choose>
										<xsl:when test="string-length(sc:author[2]/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:author[2]/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author[2]/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author[2]/sc:time/@value,7,2)"/>
										</xsl:when>
										</xsl:choose>
									</xsl:if>
									</xsl:for-each>	
								</td>	
							</tr>																											
							</table>							
							</xsl:when>	
							<xsl:otherwise>
							<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="2" style="border-bottom: solid 1px #B3C4D4;"></td>
								<td colspan="4" height="50" align="center" style="border-bottom: solid 1px #B3C4D4;">
									<h3 class="reporttitle"><xsl:value-of select="sc:title"/>
									</h3>
								</td>
								<td class="blockHeader" height="30" align="right" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;font-weight: bold;">
								病理号：
								</td>
								<td class="reportnumber" height="30" align="left" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;font-weight: bold;">
								<xsl:value-of select="sc:id/@extension"/>
								</td>
							</tr>								
							<tr class="odd">
								<td class="doclabel">姓名：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/>
								</td>
								<td class="doclabel">性别：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/>
								</td>
								<td class="doclabel">年龄：</td>
								<td class="docdataitem">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
								  <xsl:choose>
									<xsl:when test="sc:observation/sc:code/@code='397669002'">
										<xsl:value-of select="sc:observation/sc:value"/>
									</xsl:when>
								  </xsl:choose>
								</xsl:for-each>
								</td>
								<td class="doclabel">送检医院：</td>
								<td class="docdataitem">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:performer/sc:assignedEntity/sc:representedOrganization/sc:name"/>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">住院号：</td>
								<td class="docdataitem">
								<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
									<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
											<xsl:choose>
												<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
												<xsl:value-of select="@extension"/>
												</xsl:when>
											</xsl:choose>		
									</xsl:for-each></xsl:if>
								</td>								
								<td class="doclabel" >就诊卡号：</td>
								<td class="docdataitem" colspan="3">
								<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='01' or sc:componentOf/sc:encompassingEncounter/sc:code/@code='0201'">
									<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
											<xsl:choose>
												<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
												<xsl:value-of select="@extension"/>
												</xsl:when>
											</xsl:choose>		
									</xsl:for-each></xsl:if>
								</td>
								<td class="doclabel" >送检日期：</td>
								<td class="docdataitem">
								<xsl:choose>
									<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value)>12">
										<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(author[1]/time/@value,7,2)"/><xsl:text> </xsl:text><xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,9,2)"/>:<xsl:value-of select="substring(author[1]/time/@value,11,2)"/>:<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,13,2)"/>
									</xsl:when>									
									<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value)>8">
										<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,7,2)"/><xsl:text> </xsl:text><xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,9,2)"/>:<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,11,2)"/>
									</xsl:when>
									<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value)>0">
										<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:time/@value,7,2)"/>
									</xsl:when>
								</xsl:choose>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">病区：</td>
								<td class="docdataitem">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
								  <xsl:choose>
									<xsl:when test="sc:observation/sc:value/@codeSystem='1.2.156.112656.1.1.33'">
										<xsl:value-of select="sc:observation/sc:value"/>
									</xsl:when>
								  </xsl:choose>
								</xsl:for-each>
								</td>
								<td class="doclabel">床位号：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
								</td>
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">报告医师:</td>
								<td class="docdataitem"  style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select=".">
									<xsl:if test="count(sc:author)= 1">
									<xsl:value-of select="sc:author[1]/sc:assignedAuthor/sc:assignedPerson/sc:name"/>
									</xsl:if>
									<xsl:if test="count(sc:author)>= 2">
									<xsl:value-of select="sc:author[2]/sc:assignedAuthor/sc:assignedPerson/sc:name"/>
									</xsl:if>
									</xsl:for-each>	
								</td>
								<td class="doclabel"  style="border-top: solid 1px #B3C4D4;">报告日期：</td>
								<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select=".">
									<xsl:if test="count(sc:author)= 1">
									<xsl:choose>
										<xsl:when test="string-length(sc:author[1]/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:author[1]/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author[1]/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author[1]/sc:time/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
									</xsl:if>
									<xsl:if test="count(sc:author)>= 2 and position()=2">
									<xsl:choose>
										<xsl:when test="string-length(sc:author[position()]/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:author[position()]/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author[2]/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author[2]/sc:time/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
									</xsl:if>
									</xsl:for-each>	
								</td>																
							</tr>								
							<tr class="odd">
							<td class="doclabel">影像：</td>
								<td colspan="7" style="border-bottom: solid 1px #B3C4D4;">
								<xsl:choose>
									<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:supply/sc:id/@extension !=''
									">
									<xsl:element name="a">
										<xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:supply/sc:id/@extension" />
										</xsl:attribute>
										<xsl:attribute name="target">_blank</xsl:attribute>
										<img src="../images/pic_jc.png" width="22" height="22" border="0" />
									</xsl:element>									
									</xsl:when>
								</xsl:choose>
								</td>
							</tr>
						   <xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value">
							<xsl:choose>
							<!-- <xsl:when test="@code='01'">
							<tr>
								<td colspan="8" class="blockHeader" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
									所见:
								</td>
							</tr>													
							<tr height="20">
								<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
								<pre><xsl:value-of select="sc:originalText"/></pre>
								</td>
							</tr>
							</xsl:when> -->
							<xsl:when test="@code='02'">
							<tr>
								<td colspan="8" class="blockHeader" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
									病理诊断:
								</td>
							</tr>							
							<tr>
								<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:originalText"/></pre>
								</td>
							</tr>								
							</xsl:when>								
							</xsl:choose>
							</xsl:for-each>	
							<tr>
							<td colspan="8" style="padding:0px;">
						<div style="position:relative; bottom:0px;">
						  <table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr class="odd">
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">诊断医生:</td>
								<td class="docdataitem"  style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer/sc:assignedEntity/sc:assignedPerson/sc:name">								
										<xsl:value-of select="."/>
									  	<xsl:if test="position()!=last()">&#160;</xsl:if>
									</xsl:for-each>	
								</td>
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">复诊医生:</td>
								<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:participantRole/sc:playingEntity/sc:name">
									<xsl:value-of select="."/>	
									<xsl:if test="position()!=last()">&#160;</xsl:if>							
								</xsl:for-each>									
								</td>
								<td class="doclabel"  style="border-top: solid 1px #B3C4D4;">打印日期：</td>
								<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
									<xsl:choose>
										<xsl:when test="string-length(sc:effectiveTime/@value)>12">
											<xsl:value-of select="substring(sc:effectiveTime/@value,1,4)"/>-<xsl:value-of select="substring(sc:effectiveTime/@value,5,2)"/>-<xsl:value-of select="substring(sc:effectiveTime/@value,7,2)"/><xsl:text> </xsl:text><xsl:value-of select="substring(sc:effectiveTime/@value,9,2)"/>:<xsl:value-of select="substring(sc:effectiveTime/@value,11,2)"/>:<xsl:value-of select="substring(sc:effectiveTime/@value,13,2)"/>
										</xsl:when>									
										<xsl:when test="string-length(sc:effectiveTime/@value)>8">
											<xsl:value-of select="substring(sc:effectiveTime/@value,1,4)"/>-<xsl:value-of select="substring(sc:effectiveTime/@value,5,2)"/>-<xsl:value-of select="substring(sc:effectiveTime/@value,7,2)"/><xsl:text> </xsl:text><xsl:value-of select="substring(sc:effectiveTime/@value,9,2)"/>:<xsl:value-of select="substring(sc:effectiveTime/@value,11,2)"/>
										</xsl:when>
										<xsl:when test="string-length(sc:effectiveTime/@value)>0">
											<xsl:value-of select="substring(sc:effectiveTime/@value,1,4)"/>-<xsl:value-of select="substring(sc:effectiveTime/@value,5,2)"/>-<xsl:value-of select="substring(sc:effectiveTime/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>								
							</tr>
							<tr>
								<td class="note" colspan="6">注:本次结果只对该标本负责</td>
							</tr>													  
						  </table>
						  </div>
						  </td>
						  </tr>						
						  </table>							
						</xsl:otherwise>
						</xsl:choose>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>

