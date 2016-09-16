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
				<link type="text/css" rel="Stylesheet" href="../css/styles/layout.css" charset="UTF8"/>
				<link type="text/css" rel="Stylesheet" href="../css/styles/tablelist.css" charset="UTF8"/>				
			</head>
			<body>
				<div id="mainContent">
					<div name="selectTabs">
						<div class="responsability">
							<xsl:value-of select="Constants:getResponsabilityClaimingContent()"/>
						</div>
							<xsl:choose>
							<!-- CR检查报告 -->
								<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.112' and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='301'">							
									<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">							
										<tr>
											<td colspan="2" style="border-bottom: solid 1px #B3C4D4;"></td>
											<td colspan="4"  style="border-bottom: solid 1px #B3C4D4;" height="30" align="center">
												<h3 class="reporttitle"><xsl:value-of select="sc:title"/>
												</h3>
											</td>
											<td class="blockHeader" height="30" align="right" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;">
											X线号：</td>
											<td class="reportnumber" height="30" align="left" style="vertical-align:bottom;word-break:break-all;word-wrap:break-word;border-bottom: solid 1px #B3C4D4;">
                                            <xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:id/@extension"/>
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
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='397669002']/sc:value"/>
											</td>
											<td class="doclabel">就诊号：</td>
											<td class="docdataitem">
											<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='01' or sc:componentOf/sc:encompassingEncounter/sc:code/@code='0201'">
												<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112656.1.2.1.12']/@extension"/>
											</xsl:if>
											<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
												<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112656.1.2.1.3']/@extension"/>
											</xsl:if>
											</td>
										</tr>
										<tr class="odd">
											<td class="doclabel">科室：</td>
											<td class="docdataitem">
												<xsl:value-of select="sc:participant/sc:associatedEntity/sc:scopingOrganization/sc:name"/>
											</td>
											<td class="doclabel">病区：</td>
											<td class="docdataitem">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value[@codeSystem='1.2.156.112656.1.1.33']"/>											
											</td>											
											<td class="doclabel">床号：</td>
											<td class="docdataitem">
												<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
											</td>
											<td class="doclabel">住院号：</td>
											<td class="docdataitem">
											<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
												<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112656.1.2.1.12']/@extension"/>
											</xsl:if>
											</td>											
										</tr>
										<tr class="odd">
											<td class="doclabel">片序：</td>
											<td class="docdataitem" colspan="3">
											  <xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code[@codeSystem='1.2.156.112656.1.1.88']/@displayName"/>
											</td>
											<td class="doclabel">摄片日期：</td>
											<td class="docdataitem" colspan="3">
											<xsl:choose>
												<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer/sc:time/@value)>0">
													<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer/sc:time/@value,7,2)"/>日
												</xsl:when>
											</xsl:choose>
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
												检查所见：
											</td>
										</tr>										
										<tr>
											<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
												<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value[@code='01']/sc:originalText"/></pre>
											</td>
										</tr>
										<tr>
											<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
												印象：
											</td>
										</tr>
										<tr>
											<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
												<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value[@code='02']/sc:originalText"/></pre>
											</td>
										</tr>	
										<tr>
											<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
												建议：
											</td>
										</tr>
										<tr>
											<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
												<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:text"/></pre>
											</td>
										</tr>
										<tr>
											<td colspan="8" style="padding:0px;">
												<div>
													<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">																						
														<tr class="odd">
															<td class="doclabel">报告医生:</td>
															<td class="docdataitem">
															<xsl:for-each select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name">
																<xsl:value-of select="."/>
																<xsl:if test="position()!=last()">&#160;</xsl:if>
															</xsl:for-each>									
															</td>
															<td class="doclabel">审核医生:</td>
															<td class="docdataitem">								
															<xsl:for-each select="sc:authenticator/sc:assignedEntity/sc:assignedPerson/sc:name">
																<xsl:value-of select="."/>
																<xsl:if test="position()!=last()">&#160;</xsl:if>
															</xsl:for-each>			
															</td>
															<td class="doclabel">报告日期：</td>
															<td class="docdataitem">
																<xsl:choose>
																	<xsl:when test="string-length(sc:author/sc:time/@value)>0">
																		<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>日
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
								</xsl:when>								
							<!-- CT检查报告 -->
								<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.112' and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='302'">
									<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">							
										<tr>
											<td colspan="2" style="border-bottom: solid 1px #B3C4D4;"></td>
											<td colspan="4" style="border-bottom: solid 1px #B3C4D4;" height="30" align="center">
												<h3 class="reporttitle"><xsl:value-of select="sc:title"/>
												</h3>
											</td>
											<td class="blockHeader" height="30" align="right" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;">
											CT编号：</td>
											<td class="reportnumber" height="30" align="left" style="border-bottom: solid 1px #B3C4D4;vertical-align:bottom;word-break:break-all;word-wrap:break-word;">
											<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:id/@extension"/>
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
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='397669002']/sc:value"/>
											</td>
											<td class="doclabel">就诊号：</td>
											<td class="docdataitem" >
											<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='01' or sc:componentOf/sc:encompassingEncounter/sc:code/@code='0201'">
												<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112656.1.2.1.12']/@extension"/>
											</xsl:if>
											<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
												<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112656.1.2.1.3']/@extension"/>
											</xsl:if>											
											</td>											
										</tr>
										<tr class="odd">
											<td class="doclabel">科室：</td>
											<td class="docdataitem">
												<xsl:value-of select="sc:participant/sc:associatedEntity/sc:scopingOrganization/sc:name"/>
											</td>
											<td class="doclabel">病区：</td>
											<td class="docdataitem">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value[@codeSystem='1.2.156.112656.1.1.33']"/>											
											</td>											
											<td class="doclabel">床号：</td>
											<td class="docdataitem">
												<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
											</td>
											<td class="doclabel">住院号：</td>
											<td class="docdataitem">
											<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
												<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112656.1.2.1.12']/@extension"/>
											</xsl:if>
											</td>											
										</tr>
										<tr class="odd">
											<td class="doclabel">部位/方法：</td>
											<td class="docdataitem" colspan="3">
											  <xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:targetSiteCode/@displayName"/>/<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:methodCode/@displayName"/>
											</td>
											<td class="doclabel">摄片日期：</td>
											<td class="docdataitem" colspan="3">
											<xsl:choose>
												<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer/sc:time/@value)>0">
													<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer/sc:time/@value,7,2)"/>日
												</xsl:when>
											</xsl:choose>
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
												CT所见：
											</td>
										</tr>										
										<tr>
											<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
												<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value[@code='01']/sc:originalText"/></pre>
											</td>
										</tr>
										<tr>
											<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
												印象及建议：
											</td>
										</tr>
										<tr>
											<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
												<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value[@code='02']/sc:originalText"/></pre>
											</td>
										</tr>
										<tr>
											<td style="padding:0px;" colspan="8">
												<div>
													<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">																						
														<tr class="odd">
															<td class="doclabel">报告医生:</td>
															<td class="docdataitem">
															<xsl:for-each select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name">
																<xsl:value-of select="."/>
																<xsl:if test="position()!=last()">&#160;</xsl:if>
															</xsl:for-each>									
															</td>
															<td class="doclabel">审核医生:</td>
															<td class="docdataitem">								
															<xsl:for-each select="sc:authenticator/sc:assignedEntity/sc:assignedPerson/sc:name">
																<xsl:value-of select="."/>
																<xsl:if test="position()!=last()">&#160;</xsl:if>
															</xsl:for-each>			
															</td>
															<td class="doclabel">报告日期：</td>
															<td class="docdataitem">
																<xsl:choose>
																	<xsl:when test="string-length(sc:author/sc:time/@value)>0">
																		<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>日
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
								</xsl:when>
								
							<!-- MR检查报告 -->
								<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.112' and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='303'">
									<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">							
										<tr>
											<td colspan="2" style="border-bottom: solid 1px #B3C4D4;"></td>
											<td colspan="4" height="30" align="center" style="word-break:break-all;word-wrap:break-word;border-bottom: solid 1px #B3C4D4;">
												<h3 class="reporttitle"><xsl:value-of select="sc:title"/>
												</h3>
											</td>
											<td class="blockHeader" height="30" align="right" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;">
											MRI编号：</td>
											<td class="reportnumber" height="30" align="left" style="vertical-align:bottom;word-break:break-all;word-wrap:break-word;border-bottom: solid 1px #B3C4D4;">
											<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:id/@extension"/>
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
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='397669002']/sc:value"/>
											</td>
											<td class="doclabel">就诊号：</td>
											<td class="docdataitem">
											<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='01' or sc:componentOf/sc:encompassingEncounter/sc:code/@code='0201'">
												<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112656.1.2.1.12']/@extension"/>
											</xsl:if>
											<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
												<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112656.1.2.1.3']/@extension"/>
											</xsl:if>											
											</td>
										</tr>
										<tr class="odd">
											<td class="doclabel">科室：</td>
											<td class="docdataitem">
												<xsl:value-of select="sc:participant/sc:associatedEntity/sc:scopingOrganization/sc:name"/>
											</td>
											<td class="doclabel">病区：</td>
											<td class="docdataitem">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value[@codeSystem='1.2.156.112656.1.1.33']"/>											
											</td>											
											<td class="doclabel">床号：</td>
											<td class="docdataitem">
												<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
											</td>
											<td class="doclabel">住院号：</td>
											<td class="docdataitem">
											<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
												<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112656.1.2.1.12']/@extension"/>
											</xsl:if>
											</td>											
										</tr>
										<tr class="odd">
											<td class="doclabel">部位：</td>
											<td class="docdataitem" colspan="3">
											  <xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:targetSiteCode/@displayName"/>
											</td>
											<td class="doclabel">摄片日期：</td>
											<td class="docdataitem" colspan="3">
											<xsl:choose>
												<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer/sc:time/@value)>0">
													<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer/sc:time/@value,7,2)"/>日
												</xsl:when>
											</xsl:choose>
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
												MRI所见：
											</td>
										</tr>										
										<tr>
											<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
												<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value[@code='01']/sc:originalText"/></pre>
											</td>
										</tr>
										<tr>
											<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
												印象及建议：
											</td>
										</tr>
										<tr>
											<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
												<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/value[@code='02']/sc:originalText"/></pre>
											</td>
										</tr>
										<tr>
											<td colspan="8" style="padding:0px;">
												<div>
													<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">																						
														<tr class="odd">
															<td class="doclabel">报告医生:</td>
															<td class="docdataitem">
															<xsl:for-each select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name">
																<xsl:value-of select="."/>
																<xsl:if test="position()!=last()">&#160;</xsl:if>
															</xsl:for-each>									
															</td>
															<td class="doclabel">审核医生:</td>
															<td class="docdataitem">								
															<xsl:for-each select="sc:authenticator/sc:assignedEntity/sc:assignedPerson/sc:name">
																<xsl:value-of select="."/>
																<xsl:if test="position()!=last()">&#160;</xsl:if>
															</xsl:for-each>			
															</td>
															<td class="doclabel">报告日期：</td>
															<td class="docdataitem">
																<xsl:choose>
																	<xsl:when test="string-length(sc:author/sc:time/@value)>0">
																		<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>日
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
								</xsl:when>
							<!-- PET检查报告 -->
								<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.112' and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='304'">
									<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">							
										<tr>
											<td class="blockHeader" height="30" align="right" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;">
											就诊卡号：</td>
											<td class="reportnumber" height="30" align="left" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;word-break:break-all;word-wrap:break-word;">
												<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='01' or sc:componentOf/sc:encompassingEncounter/sc:code/@code='0201'">
													<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112656.1.2.1.12']/@extension"/>
												</xsl:if>
												<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
													<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112656.1.2.1.3']/@extension"/>
												</xsl:if>
											</td>
											<td colspan="2" height="30" align="center" style="border-bottom: solid 1px #B3C4D4;">
												<h3 class="reporttitle"><xsl:value-of select="sc:title"/>
												</h3>
											</td>
											<td class="blockHeader" height="30" align="right" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;">
											PET/CT编号：</td>
											<td class="reportnumber" height="30" align="left" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;word-break:break-all;word-wrap:break-word;">
											<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:id/@extension"/>
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
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='397669002']/sc:value"/>
											</td>
											<!-- <td class="doclabel">就诊卡号：</td>
											<td class="docdataitem">
											<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='01' or sc:componentOf/sc:encompassingEncounter/sc:code/@code='0201'">
												<xsl:value-of select="recordTarget/patientRole/id[@root='1.2.156.112656.1.2.1.12']/@extension"/>
											</xsl:if>
											<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
												<xsl:value-of select="recordTarget/patientRole/id[@root='1.2.156.112656.1.2.1.3']/@extension"/>
											</xsl:if>											
											</td> -->
										</tr>
										<tr class="odd">										
											<td class="doclabel">申请科室：</td>
											<td class="docdataitem">
												<xsl:value-of select="sc:participant/sc:associatedEntity/sc:scopingOrganization/sc:name"/>
											</td>										
											<td class="doclabel">床号：</td>
											<td class="docdataitem">
												<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
											</td>	
											<td class="doclabel">住院号：</td>
											<td class="docdataitem">
											<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
												<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112656.1.2.1.12']/@extension"/>
											</xsl:if>
											</td>																																
										</tr>
										<tr class="odd">
										<td class="doclabel">临床诊断：</td>
										<td class="docdataitem">
										<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:act/sc:entryRelationship/sc:observation/sc:value/@displayName"/>
										</td>
										<td class="doclabel">注射药物：</td>
										<td class="docdataitem">
											<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:substanceAdministration/sc:consumable/sc:manufacturedProduct/sc:manufacturedLabeledDrug/sc:name" />
										</td>										
										<td class="doclabel">药物剂量：</td>
										<td class="docdataitem">
										<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:substanceAdministration/sc:doseQuantity/@value"/>
										<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:substanceAdministration/sc:doseQuantity/@unit"/>
										</td></tr>
										<tr class="odd">
										<td class="doclabel">检查项目：</td>
										<td class="docdataitem" colspan="3">
										<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code[@codeSystem='1.2.156.112656.1.1.88']/@displayName"/>
										</td>
										<td class="doclabel">检查日期：</td>
										<td class="docdataitem">
										<xsl:choose>
											<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer/sc:time/@value)>0">
												<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer/sc:time/@value,7,2)"/>日
											</xsl:when>
										</xsl:choose>
										</td>
										</tr>
										<tr class="odd">
											<td class="doclabel">影像：</td>
											<td colspan="5" style="border-bottom: solid 1px #B3C4D4;">
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
											<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
												检查结果：
											</td>
										</tr>										
										<tr>
											<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;">
												<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value[@code='01']/sc:originalText"/></pre>
											</td>
										</tr>
										<tr>
											<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
												影像诊断：
											</td>
										</tr>
										<tr>
											<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;">
												<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value[@code='02']/sc:originalText"/></pre>
											</td>
										</tr>
										<tr>
											<td colspan="6" style="padding:0px;">
												<div>
													<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">																						
														<tr class="odd">
															<td class="doclabel">报告医生:</td>
															<td class="docdataitem">
															<xsl:for-each select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name">
																<xsl:value-of select="."/>
																<xsl:if test="position()!=last()">&#160;</xsl:if>
															</xsl:for-each>									
															</td>
															<td class="doclabel">审核医生:</td>
															<td class="docdataitem">								
															<xsl:for-each select="sc:authenticator/sc:assignedEntity/sc:assignedPerson/sc:name">
																<xsl:value-of select="."/>
																<xsl:if test="position()!=last()">&#160;</xsl:if>
															</xsl:for-each>			
															</td>
															<td class="doclabel">报告日期：</td>
															<td class="docdataitem">
																<xsl:choose>
																	<xsl:when test="string-length(sc:author/sc:time/@value)>0">
																		<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>日
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
								</xsl:when>	
								<!-- 介入放射诊疗报告单 -->
								<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.112' and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='305'">
									<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">							
										<tr>
											<td colspan="8" height="30" align="center" style="word-break:break-all;word-wrap:break-word;">
												<h3 class="reporttitle"><xsl:value-of select="sc:title"/>
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
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='397669002']/sc:value"/>
											</td>
											<td class="doclabel">住院号：</td>
											<td class="docdataitem">
											<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
												<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112656.1.2.1.12']/@extension"/>
											</xsl:if>											
											</td>
										</tr>
										<tr class="odd">
											<td class="doclabel">科室：</td>
											<td class="docdataitem">
												<xsl:value-of select="sc:participant/sc:associatedEntity/sc:scopingOrganization/sc:name"/>
											</td>
											<td class="doclabel">病区：</td>
											<td class="docdataitem">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value[@codeSystem='1.2.156.112656.1.1.33']"/>											
											</td>											
											<td class="doclabel">床号：</td>
											<td class="docdataitem">
												<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
											</td>
											<td class="doclabel">DSA号：</td>
											<td class="docdataitem">
												<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:id/@extension"/>
											</td>											
										</tr>
										<tr class="odd">
											<td class="doclabel">诊疗项目：</td>
											<td class="docdataitem" colspan="7">
											  <xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code[@codeSystem='1.2.156.112656.1.1.88']/@displayName"/>
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
												检查所见：
											</td>
										</tr>										
										<tr>
											<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
												<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value[@code='01']/sc:originalText"/></pre>
											</td>
										</tr>
										<tr>
											<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
												结果：
											</td>
										</tr>
										<tr>
											<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
												<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value[@code='02']/sc:originalText"/></pre>
											</td>
										</tr>
										<tr>
											<td colspan="8" style="padding:0px;">
												<div>
													<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">																						
														<tr class="odd">
															<td class="doclabel">报告人:</td>
															<td class="docdataitem">
															<xsl:for-each select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name">
																<xsl:value-of select="."/>
																<xsl:if test="position()!=last()">/</xsl:if>
															</xsl:for-each>									
															</td>
															<td class="doclabel">日期：</td>
															<td class="docdataitem">
																<xsl:choose>
																	<xsl:when test="string-length(sc:author/sc:time/@value)>0">
																		<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>日
																</xsl:when>
																</xsl:choose>
															</td>
														</tr>
														<tr>
														<td class="note" colspan="4">注:本次结果只对该标本负责</td>
														</tr>							
													</table>
												</div>
											</td>
										</tr>																															
									</table>						
								</xsl:when>																													
							</xsl:choose>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
