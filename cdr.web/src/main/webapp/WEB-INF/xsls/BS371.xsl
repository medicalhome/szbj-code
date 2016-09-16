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
								<td class="doclabel">婚姻：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:maritalStatusCode/@displayName"/></td>
								
								<td class="doclabel">职业：</td>
								<td class="docdataitem" colspan="3"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:occupation/sc:occupationCode/@displayName"/></td>
							</tr>							
							<tr>
								<td class="doclabel">记录医生：</td>
								<td class="docdataitem"><xsl:value-of select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name"/></td>
								
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
										<td class="docdataitem"><xsl:value-of select="sc:custodian/sc:assignedCustodian/sc:representedCustodianOrganization/sc:name"/></td>
									</xsl:when>
									<xsl:otherwise>
										<td class="doclabel"></td>
										<td class="docdataitem"></td>
									</xsl:otherwise>
								</xsl:choose>
								
							</tr>
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">诊断</td>
							</tr>
							<tr>
							    <td class="doclabel">入院日期：</td>
								<td class="docdataitem" colspan="5"><xsl:choose>
								<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.092.00']/sc:value/@value)>8">
									<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.092.00']/sc:value/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.092.00']/sc:value/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.092.00']/sc:value/@value,7,2)"/>日<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.092.00']/sc:value/@value,9,2)"/>时<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.092.00']/sc:value/@value,11,2)"/>分
								</xsl:when>
								<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.092.00']/sc:value/@value)>0">
									<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.092.00']/sc:value/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.092.00']/sc:value/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.092.00']/sc:value/@value,7,2)"/>日
								</xsl:when>
								</xsl:choose></td>
							</tr>
							<tr>
							    <td class="doclabel" valign="top">入院诊断：</td>
								<td  class="docdataitem3" colspan="5">
									<ui style="float:left;list-style-type:decimal;list-style-position:inside">
										<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@displayName='入院诊断编码']">
											<li><xsl:value-of select="sc:value/@displayName"/></li>
										</xsl:for-each>
									</ui>
								</td>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry">
							<tr>
							    <td class="doclabel">手术日期：</td>
								<td class="docdataitem"><xsl:choose>
								<xsl:when test="string-length(sc:procedure/sc:effectiveTime/sc:low/@value)>8">
									<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:low/@value,1,4)"/>年<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:low/@value,5,2)"/>月<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:low/@value,7,2)"/>日<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:low/@value,9,2)"/>时<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:low/@value,11,2)"/>分
								</xsl:when>
								<xsl:when test="string-length(sc:procedure/sc:effectiveTime/sc:low/@value)>0">
									<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:low/@value,1,4)"/>年<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:low/@value,5,2)"/>月<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:low/@value,7,2)"/>日
								</xsl:when>
								</xsl:choose></td>
								
							    <td class="doclabel">手术名称：</td>
								<td class="docdataitem" colspan="3">
									<xsl:value-of select="sc:procedure/sc:code/@displayName"/>
								</td>
							</tr>
							</xsl:for-each>
							<tr>
							    <td class="doclabel">出院日期：</td>
								<td class="docdataitem"  colspan="5"><xsl:choose>
								sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code="11535-2"]/sc:entry/sc:observation[sc:code/@code="DE06.00.017.00"]/sc:value
								<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.017.00']/sc:value/@value)>8">
									<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.017.00']/sc:value/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.017.00']/sc:value/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.017.00']/sc:value/@value,7,2)"/>日<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.017.00']/sc:value/@value,9,2)"/>时<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.017.00']/sc:value/@value,11,2)"/>分
								</xsl:when>
								<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.017.00']/sc:value/@value)>0">
									<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.017.00']/sc:value/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.017.00']/sc:value/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.017.00']/sc:value/@value,7,2)"/>日
								</xsl:when>
								</xsl:choose></td>
							</tr>
							<tr>
							    <td class="doclabel" valign="top">出院诊断：</td>
								<td class="docdataitem3" colspan="5">
									<ui style="float:left;list-style-type:decimal;list-style-position:inside">
										<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@displayName='出院诊断-西医诊断编码']">
											<li><xsl:value-of select="sc:value/@displayName"/></li>
										</xsl:for-each>
									</ui>
								</td>
							</tr>
							<tr>
							    <td class="doclabel" valign="top">入院时情况：</td>
								<td class="docdataitem3" colspan="5">
									<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11450-4']/sc:entry/sc:observation[sc:code/@code='DE05.10.148.00']/sc:value"/></pre>
								</td>
							</tr>
							<tr>
							    <td class="doclabel" valign="top">出院时情况：</td>
								<td class="docdataitem3" colspan="5">
									<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11450-4']/sc:entry/sc:observation[sc:code/@code='DE05.10.148.00']/sc:value"/></pre>
								</td>
							</tr>
							<tr>
							    <td class="doclabel" valign="top">出院医嘱：</td>
								<td class="docdataitem3" colspan="5">
									<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@code='DE06.00.287.00']/sc:value"/></pre>
								</td>
							</tr>														
							<tr>
							    <td class="doclabel">X线片号：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='关联检查']/sc:entry/sc:observation[sc:code/@displayName='X线号']/sc:value"/>
								</td>
															
							    <td class="doclabel">CT号：</td>
								<td class="docdataitem" colspan="3">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='关联检查']/sc:entry/sc:observation[sc:code/@displayName='CT号']/sc:value"/>
								</td>
							</tr>
							<tr>
							    <td class="doclabel">MRI号：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='关联检查']/sc:entry/sc:observation[sc:code/@displayName='MRI号']/sc:value"/>
								</td>
								
							    <td class="doclabel">病理检验号：</td>
								<td class="docdataitem" colspan="3">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='关联检查']/sc:entry/sc:observation[sc:code/@displayName='病理检验号']/sc:value"/>
								</td>
							</tr>																																																	
							<tr>
								<td class="doclabel">主治医师：</td>
								<td class="docdataitem"><xsl:value-of select="sc:authenticator/sc:assignedEntity[sc:code/@displayName='主治医师']/sc:assignedPerson/sc:name"/></td>
								
								<td class="doclabel">医师：</td>
								<td class="docdataitem" colspan="3"><xsl:value-of select="sc:authenticator/sc:assignedEntity[sc:code/@displayName='住院医师']/sc:assignedPerson/sc:name"/></td>
							</tr>							
						</table>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>





