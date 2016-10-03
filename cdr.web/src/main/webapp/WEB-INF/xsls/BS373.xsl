<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:sc="urn:hl7-org:v3"  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:Constants="xalan://com.yly.cdr.core.Constants">
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
								<td class="doclabel">科别：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								
								<td class="doclabel">病区：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								
								<td class="doclabel">床位号：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
							</tr>
							<tr>
								<td class="doclabel">姓名：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/></td>
								
								<td class="doclabel">性别：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/></td>
								
								<td class="doclabel">年龄：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:age/@value"/></td>
							</tr>
							<tr>
								<td class="doclabel">职业：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:occupation/sc:occupationCode/@displayName"/></td>
							
								<td class="doclabel">籍贯：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:nativePlace/sc:place/sc:name"/></td>
							
								<td class="doclabel">民族：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:ethnicGroupCode/@displayName"/></td>
							</tr>
							<tr>
								<td class="doclabel">工作单位：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:employerOrganization/sc:name"/></td>
							
								<td class="doclabel">家庭住址：</td>
								<td class="docdataitem" colspan="3"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:houseNumber"/></td>
							</tr>															
							<tr>
								<td class="doclabel">记录日期：</td>
								<td class="docdataitem"><xsl:choose>
									<xsl:when test="string-length(sc:author/sc:time/@value)>8">
										<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>日<xsl:value-of select="substring(sc:author/sc:time/@value,9,2)"/>时<xsl:value-of select="substring(sc:author/sc:time/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:author/sc:time/@value)>0">
										<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose></td>
								<xsl:choose>
									<xsl:when test="Constants:getTrueOrgCode() = Constants:getDisabledOrgCode()">
										<td class="doclabel">管理机构名称：</td>
										<td class="docdataitem" colspan="3"><xsl:value-of select="sc:custodian/sc:assignedCustodian/sc:representedCustodianOrganization/sc:name"/></td>
									</xsl:when>
									<xsl:otherwise>
										<td class="doclabel"></td>
										<td class="docdataitem" colspan="3"></td>
									</xsl:otherwise>
								</xsl:choose>
							</tr>

							<tr>
							    <td class="doclabel">入院日期：</td>
								<td class="docdataitem"><xsl:choose>
								<xsl:when test="string-length(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:low/@value)>8">
									<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:low/@value,1,4)"/>年<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:low/@value,5,2)"/>月<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:low/@value,7,2)"/>日<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:low/@value,9,2)"/>时<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:low/@value,11,2)"/>分
								</xsl:when>
								<xsl:when test="string-length(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:low/@value)>0">
									<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:low/@value,1,4)"/>年<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:low/@value,5,2)"/>月<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:low/@value,7,2)"/>日
								</xsl:when>
								</xsl:choose></td>
							    <td class="doclabel">死亡日期：</td>
								<td class="docdataitem" colspan="3"><xsl:choose>
								<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='死亡原因']/sc:entry/sc:observation[sc:code/@code='DE02.01.036.00']/sc:value/@value)>8">
									<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='死亡原因']/sc:entry/sc:observation[sc:code/@code='DE02.01.036.00']/sc:value/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='死亡原因']/sc:entry/sc:observation[sc:code/@code='DE02.01.036.00']/sc:value/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='死亡原因']/sc:entry/sc:observation[sc:code/@code='DE02.01.036.00']/sc:value/@value,7,2)"/>日<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='死亡原因']/sc:entry/sc:observation[sc:code/@code='DE02.01.036.00']/sc:value/@value,9,2)"/>时<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='死亡原因']/sc:entry/sc:observation[sc:code/@code='DE02.01.036.00']/sc:value/@value,11,2)"/>分
								</xsl:when>
								<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='死亡原因']/sc:entry/sc:observation[sc:code/@code='DE02.01.036.00']/sc:value/@value)>0">
									<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='死亡原因']/sc:entry/sc:observation[sc:code/@code='DE02.01.036.00']/sc:value/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='死亡原因']/sc:entry/sc:observation[sc:code/@code='DE02.01.036.00']/sc:value/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='死亡原因']/sc:entry/sc:observation[sc:code/@code='DE02.01.036.00']/sc:value/@value,7,2)"/>日
								</xsl:when>
								</xsl:choose></td>								
							</tr>
							<tr>
							    <td class="doclabel" valign="top">死亡诊断：</td>
								<td  class="docdataitem3" colspan="5">
									<ui style="float:left;list-style-type:decimal;list-style-position:inside">
										<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']">
											<li><xsl:value-of select="sc:entry/sc:observation[sc:code/@code='DE05.01.025.00']/sc:value"/></li>
										</xsl:for-each>
									</ui>
								</td>
							</tr>
							<tr>
							    <td class="doclabel" valign="top">死亡原因：</td>
								<td  class="docdataitem" colspan="5">
									<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='死亡原因']/sc:entry/sc:observation[sc:code/@code='DE05.01.025.00']/sc:value"/></pre>
								</td>
							</tr>
							<tr>
							    <td class="doclabel">讨论日期：</td>
								<td class="docdataitem"><xsl:choose>
								<xsl:when test="string-length(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value)>8">
									<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,7,2)"/>日<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,9,2)"/>时<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,11,2)"/>分
								</xsl:when>
								<xsl:when test="string-length(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value)>0">
									<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,7,2)"/>日
								</xsl:when>
								</xsl:choose></td>
							    <td class="doclabel">记录者：</td>
								<td class="docdataitem" colspan="3">
									<xsl:value-of select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name"/>
								</td>								
							</tr>
							<tr>
							    <td class="doclabel" valign="top">参加讨论人员：</td>
								<td  class="docdataitem" colspan="5">
										<xsl:for-each select="sc:participant/sc:associatedEntity[sc:code/@displayName='讨论成员']">
											<xsl:value-of select="sc:associatedPerson/sc:name"/>
											<xsl:if test="position()!=last() and string-length(sc:associatedPerson/sc:name)>0">、</xsl:if>
										</xsl:for-each>
								</td>
							</tr>							
							<tr>
							    <td class="doclabel" valign="top">主持人：</td>
								<td  class="docdataitem" colspan="5">
										<xsl:for-each select="sc:participant/sc:associatedEntity[sc:code/@displayName='主持人']">
											<xsl:value-of select="sc:associatedPerson/sc:name"/>
											<xsl:if test="position()!=last() and string-length(sc:associatedPerson/sc:name)>0">、</xsl:if>
										</xsl:for-each>
								</td>
							</tr>																					
							<tr>
							    <td class="doclabel" valign="top">发言纪要摘要：</td>
								<td class="blws" colspan="5">
									<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='DE06.00.181.00']/sc:text"/></pre>
								</td>
							</tr>							
						</table>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>





