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
			</head>
			<body>
				<div id="mainContent">
					<div name="selectTabs">
						<div class="responsability">
							<xsl:value-of select="Constants:getResponsabilityClaimingContent()"/>
						</div>
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="8" height="30" align="center" style="border-bottom: solid 1px #B3C4D4;">
									<h3 class="reporttitle">
										<xsl:value-of select="sc:title"/>
									</h3>
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
								<td class="doclabel">科别：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:participant/sc:associatedEntity/sc:scopingOrganization/sc:name"/>								
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
								<td class="doclabel">床号：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
								</td>
								<td class="doclabel">检查号：</td>
								<td class="docdataitem">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:id/@extension"/>
								</td>
								<td class="doclabel" rowspan="2">设备型号：</td>
								<td class="docdataitem" rowspan="2">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:participantRole/sc:playingDevice/sc:manufacturerModelName/@code"/>
								</td>								
							</tr>
							<tr class="odd">
								<td class="doclabel">病理号:</td>
								<td class="docdataitem">
								</td>
								<td class="doclabel">化验：</td>
								<td class="docdataitem">
								</td>
								<td class="doclabel">联系电话：</td>
								<td class="docdataitem">
								<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:telecom/@value"/>
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
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
									检查所见:
								</td>
							</tr>							
							<tr>
								<td colspan="8" class="blws" style="vertical-align:top;word-break:break-all;word-wrap:break-word;">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value">
										<xsl:if test="@code='01'">
											<pre><xsl:value-of select="sc:originalText"/></pre>
										</xsl:if>
									</xsl:for-each>										
								</td>								
							</tr>							
							<xsl:choose>
							<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:observationMedia">
							<tr>
							<td colspan="8">
								<div>
								<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-top: solid 1px #B3C4D4;border-bottom: solid 1px #B3C4D4;bgcolor:white;">		
								<tr>
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:observationMedia">
								<xsl:if test="sc:value !=''">
								<xsl:if test="last() = 1">
								<td colspan="2" align="center">
								<div style="margin:5px;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:value" /></xsl:attribute>
									<xsl:attribute name="width">700px</xsl:attribute> 
									<xsl:attribute name="hspace">4px</xsl:attribute>
									<xsl:attribute name="vspace">10px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
								</xsl:if>
								<xsl:if test="last() > 1">
								<td align="center">
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
								</table>
								</div>
								</td>
							</tr>
							</xsl:when>
							</xsl:choose>
							<xsl:choose>
							<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:act/sc:specimen">
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
									活检:
								</td>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:act/sc:specimen">
							<xsl:if test="sc:specimenRole/sc:specimenPlayingEntity/sc:quantity/@value !=''">
							<tr>
								<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
								[<xsl:value-of select="position()"/>]<xsl:value-of select="sc:specimenRole/sc:specimenPlayingEntity/sc:name"/>:<xsl:value-of select="sc:specimenRole/sc:specimenPlayingEntity/sc:quantity/@value"/><xsl:value-of select="sc:specimenRole/sc:specimenPlayingEntity/sc:quantity/@unit"/>
								</td>
							</tr>
							</xsl:if>								
							</xsl:for-each>							
							</xsl:when>
							</xsl:choose>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation">
							<xsl:if test="sc:code/@codeSystem='1.2.156.112656.1.1.106' and sc:code/@displayName !=''">
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
								<xsl:value-of select="sc:code/@displayName"/>:
								</td>
							</tr>
							<tr>
								<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
								<xsl:value-of select="sc:value"/>
								</td>							
							</tr>
							</xsl:if>
							</xsl:for-each>
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
									诊断:
								</td>
							</tr>							
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value">
								<xsl:if test="@code='02'">
									<tr height="20">
										<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
											<pre>
												<xsl:value-of select="sc:originalText"/>
											</pre>
										</td>
									</tr>
								</xsl:if>
							</xsl:for-each>	
							<tr>
							<td colspan="8" style="padding:0px;">
							<div style="position: relative; bottom: 0px;">
						  	<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr class="odd">
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">报告医师:</td>
								<td class="docdataitem"  style="border-top: solid 1px #B3C4D4;">
								<xsl:for-each select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name">
									<xsl:value-of select="."/>
								  <xsl:if test="position()!=last()">&#160;</xsl:if>
								</xsl:for-each>	
								</td>
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">助手:</td>
								<td class="docdataitem"  style="border-top: solid 1px #B3C4D4;">
								<xsl:for-each select="sc:participant[@typeCode='ATND']/sc:associatedEntity/sc:associatedPerson/sc:name">
									<xsl:value-of select="."/>
									<xsl:if test="position()!=last()">&#160;</xsl:if>
								</xsl:for-each>							
								</td>
								<td class="doclabel"  style="border-top: solid 1px #B3C4D4;">报告日期：</td>
								<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
									<xsl:choose>
										<xsl:when test="string-length(sc:author/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>
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
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>

