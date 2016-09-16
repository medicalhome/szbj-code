<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:sc="urn:hl7-org:v3"  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:Constants="xalan://com.founder.cdr.core.Constants">
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
						return '<xsl:value-of select="sc:id/@extension"/>';
				     }
			    </script>
			    <script type="text/javascript" src="../scripts/doc/docDetail.js"></script>
			</head>
			<body>
				<div id="mainContent">
					<div name="selectTabs">
						<div class="responsability"><xsl:value-of select="Constants:getResponsabilityClaimingContent()"/></div>
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;bgcolor:white;table-layout:fixed;">
							<tr>
								<td colspan="6" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"><h3><xsl:value-of select="sc:title"/></h3></td>
							</tr>
							<tr>
								<td class="doclabel">姓名：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/></td>
								
								<td class="doclabel">性别：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/></td>
								
								<td class="doclabel">年龄：</td>
								<td class="docdataitem"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value"/></td>
							</tr>
							<tr>
								<td class="doclabel">住院号：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112606.1.2.1.12']/@extension"/></td>
								
								<td class="doclabel">病区：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:houseNumber"/></td>
								
								<td class="doclabel">床号：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/></td>
							</tr>
							<tr>
								<td class="doclabel">影像号：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112606.1.2.1.5']/@extension"/></td>
								
								<td class="doclabel">手术间：</td>
								<td class="docdataitem"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:procedure/sc:participant[@typeCode='LOC']/sc:participantRole/sc:playingEntity/sc:name"/></td>
								
								<td class="doclabel">手术日期：</td>
								<td class="docdataitem">
									<xsl:choose>
										<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value)>8">
											<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value,1,4)"/>年
											<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value,5,2)"/>月
											<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value,7,2)"/>日
											<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value,9,2)"/>时
											<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value,11,2)"/>分
										</xsl:when>
										<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value)>0">
											<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value,1,4)"/>年
											<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value,5,2)"/>月
											<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:effectiveTime/sc:low/@value,7,2)"/>日
										</xsl:when>
									</xsl:choose>
								</td>
							</tr>
							<tr>
								<td class="doclabel">术者：</td>
								<td class="docdataitem">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:procedure/sc:performer">
										<xsl:value-of select="sc:assignedEntity/sc:assignedPerson/sc:name/text()"/><xsl:if test="position()!=last()">、</xsl:if>
									</xsl:for-each>
								</td>
								
								<td class="doclabel">助手：</td>
								<xsl:choose>
									<xsl:when test="Constants:getTrueOrgCode() = Constants:getDisabledOrgCode()">
										<td class="docdataitem">
											<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:procedure/sc:participant[@typeCode='ATND']">
												<xsl:value-of select="sc:participantRole/sc:playingEntity/sc:name/text()"/><xsl:if test="position()!=last()">、</xsl:if>
											</xsl:for-each>
										</td>
										<td class="doclabel">管理机构名称：</td>
										<td class="docdataitem"><xsl:value-of select="sc:custodian/sc:assignedCustodian/sc:representedCustodianOrganization/sc:name"/></td>
									</xsl:when>
									<xsl:otherwise>
										<td class="docdataitem" colspan="3">
											<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:procedure/sc:participant[@typeCode='ATND']">
												<xsl:value-of select="sc:participantRole/sc:playingEntity/sc:name/text()"/><xsl:if test="position()!=last()">、</xsl:if>
											</xsl:for-each>
										</td>
									</xsl:otherwise>
								</xsl:choose>
							</tr>
							<tr>
								<td class="doclabel">主要诊断：</td>
								<td class="docdataitem" colspan="5">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:act/sc:entryRelationship">
										<xsl:value-of select="sc:observation/sc:value/@displayName"/><xsl:if test="position()!=last()">、</xsl:if>
									</xsl:for-each>
								</td>
							</tr>
							<tr>
								<td class="docdataitem" colspan="6" height="30" align="left" style="background-color:#fcffe2;word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:procedure/sc:entryRelationship/sc:observation[sc:code/@code='398298007']/sc:text"></xsl:value-of></pre>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
