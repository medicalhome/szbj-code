<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:sc="urn:hl7-org:v3"  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="urn:hl7-org:v3 ../coreschemas/CDA.xsd" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:Constants="xalan://com.yly.cdr.core.Constants">
	<xsl:output method="html" indent="yes" version="4.0"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" />
    <xsl:template match="/sc:ClinicalDocument">
        <html>
			<head>
				<meta http-equiv="Pragma" content="no-cache" />
				<meta http-equiv="Cache-Control" content="no-cache" />
				<meta http-equiv="Expires" content="0" />
				<link type="text/css" rel="Stylesheet" href="../../styles/jquery-ui-1.8.18.custom.modify.css" charset="UTF8" />
				<link type="text/css" rel="Stylesheet" href="../../styles/layout-default-1.3.0rc29.15.css" charset="UTF8" />
				<link type="text/css" rel="Stylesheet" href="../../styles/layout-cdr-dialog.css" charset="UTF8" />
				<link type="text/css" rel="Stylesheet" href="../../styles/tablelist.css" charset="UTF8" />
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
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="6" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"><h3><xsl:value-of select="sc:title"/></h3></td>
							</tr>
							<tr>
								<td class="doclabel">姓名：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/></td>
								
								<td class="doclabel">性别：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/></td>
								
								<td class="doclabel">出生日期：</td>
								<td class="docdataitem"><xsl:choose>
									<xsl:when test="string-length(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value)>8">
										<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,7,2)"/>日<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,9,2)"/>时<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value)>0">
										<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose></td>
							</tr>
							<tr>
								<td class="doclabel">年龄：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:age/@value"/></td>
								
								<td class="doclabel">病历医生名称：</td>
								<td class="docdataitem"><xsl:value-of select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name"/></td>
								
								<td class="doclabel">病历日期：</td>
								<td class="docdataitem"><xsl:choose>
									<xsl:when test="string-length(sc:author/sc:time/@value)>8">
										<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>日<xsl:value-of select="substring(sc:author/sc:time/@value,9,2)"/>时<xsl:value-of select="substring(sc:author/sc:time/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:author/sc:time/@value)>0">
										<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose></td>
							</tr>
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">主诉</td>
							</tr>
							<tr>
								<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='10154-3']/sc:entry/sc:observation/sc:value"/></pre></td>
							</tr>
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">现病史</td>
							</tr>
							<tr>
								<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='10164-2']/sc:entry/sc:observation/sc:value"/></pre></td>
							</tr>							 
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">既往史</td>
							</tr>
							<tr>
								<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11348-0']/sc:entry/sc:observation/sc:value"/></pre></td>
							</tr>
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">过敏史</td>
							</tr>
							<tr>
								<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='48765-2']/sc:entry/sc:observation/sc:entryRelationship/sc:observation/sc:value"/></pre></td>
							</tr> 							
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">体格检查</td>
							</tr>
							<tr>
								<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='29545-1']/sc:entry/sc:observation/sc:value"/></pre></td>
							</tr> 
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">辅助检查</td>
							</tr>
							<tr>
								<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='30954-2']/sc:entry/sc:observation/sc:entryRelationship/sc:observation/sc:value"/></pre></td>
							</tr>
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">诊断</td>
							</tr>							
							<tr>
								<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;">
									<ui style="float:left;list-style-type:decimal;list-style-position:inside">
										<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='29548-5']/sc:entry/sc:observation[sc:code/@code='DE05.01.024.00']">
											<li><pre><xsl:value-of select="sc:value/@displayName"/></pre></li>
										</xsl:for-each>
									</ui>								
								</td>
							</tr>
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">处理</td>
							</tr>							
							<tr>
								<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='46209-3']/sc:text"/></pre></td>
							</tr>														
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">嘱托</td>
							</tr>
							<tr>
								<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='48767-8']/sc:text"/></pre></td>
							</tr> 
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="right" style="padding-right: 30px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"><pre>医生签名：<xsl:value-of select="sc:legalAuthenticator/sc:assignedEntity/sc:assignedPerson/sc:name"/></pre></td>
							</tr> 														 														 																					
						</table>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>





