<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:sc="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:Constants="xalan://com.founder.cdr.core.Constants">
	<xsl:output method="html" indent="yes" version="4.0"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" />
	<xsl:template match="/sc:ClinicalDocument">
		<html>
			<head>
				<meta http-equiv="Pragma" content="no-cache"/>
				<meta http-equiv="Cache-Control" content="no-cache"/>
				<meta http-equiv="Expires" content="0"/>
				<link type="text/css" rel="Stylesheet" href="../styles/tablelist.css" charset="UTF8"/>
			</head>
			<body>
				<div id="mainContent">
					<div name="selectTabs">
						<div class="responsability">
							<xsl:value-of select="Constants:getResponsabilityClaimingContent()"/>
						</div>
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="6" height="30" align="center" style="border-bottom: solid 1px #B3C4D4;">
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
							</tr>
							<tr class="odd">
								<td class="doclabel">就诊卡号：</td>
								<td class="docdataitem">
									<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='01' or sc:componentOf/sc:encompassingEncounter/sc:code/@code='0201'">
										<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
											<xsl:choose>
												<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
													<xsl:value-of select="@extension"/>
												</xsl:when>
											</xsl:choose>
										</xsl:for-each>
									</xsl:if>
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
										</xsl:for-each>
									</xsl:if>
								</td>
								<td class="doclabel">送检科室：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:participant/sc:associatedEntity/sc:scopingOrganization/sc:name"/>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">影像：</td>
								<td colspan="5" style="border-bottom: solid 1px #B3C4D4;">
								<xsl:choose>
									<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:supply/sc:id/@extension !=''
									">
									<xsl:element name="a">
										<xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of select="component/structuredBody/component/section/entry/organizer/component/supply/id/@extension" />
										</xsl:attribute>
										<xsl:attribute name="target">_blank</xsl:attribute>
										<img src="../images/pic_jc.png" width="22" height="22" border="0" />
									</xsl:element>									
									</xsl:when>
								</xsl:choose>
								</td>
							</tr>								
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
									镜检所见:
								</td>
							</tr>		
							<tr>
								<td colspan="6" class="blws" style="margin-top:5px;word-break:break-all;word-wrap:break-word;">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value">
										<xsl:if test="@code='01'">
											<pre><xsl:value-of select="sc:originalText"/></pre>
										</xsl:if>
									</xsl:for-each>	
								</td>
							</tr>													
							<tr>
							<td colspan="6">
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
							<xsl:choose>
								<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:act/sc:specimen">
									<tr>
										<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
									活检部位:
										</td>
									</tr>
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:act/sc:specimen">
										<xsl:if test="sc:specimenRole/sc:specimenPlayingEntity/sc:quantity/@value !=''">
										<tr>
											<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;">
								[<xsl:value-of select="position()"/>]<xsl:value-of select="sc:specimenRole/sc:specimenPlayingEntity/sc:name"/>:<xsl:value-of select="sc:specimenRole/sc:specimenPlayingEntity/sc:quantity/@value"/>
												<xsl:value-of select="sc:specimenRole/sc:specimenPlayingEntity/sc:quantity/@unit"/>
											</td>
										</tr>
										</xsl:if>
									</xsl:for-each>
								</xsl:when>
							</xsl:choose>							
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
									诊断:
								</td>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value">
								<xsl:if test="@code='02'">
									<tr height="20">
										<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;">
											<pre>
												<xsl:value-of select="sc:originalText"/>
											</pre>
										</td>
									</tr>
								</xsl:if>
							</xsl:for-each>
							<tr class="odd">
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">报告医师:</td>
								<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name">
										<xsl:value-of select="."/>
										<xsl:if test="position()!=last()">&#160;</xsl:if>
									</xsl:for-each>
								</td>
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">检查日期：</td>
								<td class="docdataitem" colspan="3" style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer">
										<xsl:choose>
											<xsl:when test="position()=1 and string-length(sc:time/@value)>0">
												<xsl:value-of select="substring(sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:time/@value,7,2)"/>
											</xsl:when>
										</xsl:choose>
									</xsl:for-each>
								</td>
							</tr>
							<tr>
							 	<td class="note" colspan="6">注:本次结果只对该标本负责</td>
							</tr>
						</table>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
