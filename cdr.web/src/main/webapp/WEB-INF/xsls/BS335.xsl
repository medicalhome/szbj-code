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
								<td class="doclabel">病人姓名：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/></td>
								
								<td class="doclabel">性别：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/></td>
								
								<td class="doclabel">年龄：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:age/@value"/></td>
							</tr>
							<tr>
								<td class="doclabel">民族：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:ethnicGroupCode/@displayName"/></td>
							
								<td class="doclabel">出生地：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:birthplace/sc:place/sc:addr"/></td>
								
								<td class="doclabel">职业状况：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:occupation/sc:occupationCode/@displayName"/></td>
							</tr>
							<tr>
								<td class="doclabel">婚姻：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:maritalStatusCode/@displayName"/></td>
							
								<td class="doclabel">工作单位：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:employerOrganization/sc:name"/></td>
								
								<td class="doclabel">病人科室：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
							</tr>							
							<tr>
								<td class="doclabel">病区：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								
								<td class="doclabel">床位号：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								
								<xsl:choose>
									<xsl:when test="Constants:getTrueOrgCode() = Constants:getDisabledOrgCode()">
										<td class="doclabel">管理机构名称：</td>
										<td class="docdataitem"><xsl:value-of select="sc:custodian/sc:assignedCustodian/sc:representedCustodianOrganization/sc:name"/></td>
									</xsl:when>
									<xsl:otherwise>
										<td class="doclabel"></td>
										<td class="docdataitem"></td>
									</xsl:otherwise>
								</xsl:choose></tr>
							<!-- <tr>
								<td class="doclabel">审核医生名称：</td>
								<td class="docdataitem"><xsl:for-each select="sc:authenticator">
								<xsl:if test="position()>1">、</xsl:if>
								<xsl:value-of select="sc:assignedEntity/sc:assignedPerson/sc:name"/></xsl:for-each></td>
								
								<td class="doclabel">审核日期：</td>
								<td class="docdataitem" colspan="3"><xsl:for-each select="sc:authenticator">
								<xsl:choose>
									<xsl:when test="string-length(sc:time/@value)>8">
										<xsl:value-of select="substring(sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:time/@value,7,2)"/>日<xsl:value-of select="substring(sc:time/@value,9,2)"/>时<xsl:value-of select="substring(sc:time/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:time/@value)>0">
										<xsl:value-of select="substring(sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:time/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose>
								<xsl:if test="(position()!=last())and(string-length(sc:time/@value)>0)">、</xsl:if>
								</xsl:for-each></td>
							</tr> -->
							<tr>
								<td class="doclabel">入院日期：</td>
								<td class="docdataitem">
								<xsl:choose>
									<xsl:when test="string-length(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value)>8">
										<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,7,2)"/>日<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,9,2)"/>时<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value/@value)>0">
										<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose>								
								</td>
								
								<td class="doclabel">记录日期：</td>
								<td class="docdataitem" colspan="3">
								<xsl:choose>
									<xsl:when test="string-length(sc:author/sc:time/@value)>8">
										<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>日<xsl:value-of select="substring(sc:author/sc:time/@value,9,2)"/>时<xsl:value-of select="substring(sc:author/sc:time/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:author/sc:time/@value)>0">
										<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose>
								</td>
							</tr>
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='10154-3']/sc:entry/sc:observation/sc:code/@displayName"/></td>
							</tr>
							<tr>
								<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='10154-3']/sc:entry/sc:observation/sc:value"/></pre></td>
							</tr>
							
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='10164-2']/sc:entry/sc:observation/sc:code/@displayName"/></td>
							</tr>
							<tr>
								<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='10164-2']/sc:entry/sc:observation/sc:value"/></pre></td>
							</tr>
							
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"><!-- <xsl:value-of select="既往史"/> -->既往史</td>
							</tr>
							<tr>
								<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11348-0']/sc:text"/></pre></td>
							</tr>
							
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='10157-6']/sc:entry/sc:observation/sc:code/@displayName"/></td>
							</tr>
							<tr>
								<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='10157-6']/sc:entry/sc:observation/sc:value"/></pre></td>
							</tr>
														 							 							 							
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='29762-2']/sc:entry/sc:observation/sc:code/@displayName"/></td>
							</tr>
							<tr>
								<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='29762-2']/sc:entry/sc:observation/sc:value"/></pre></td>
							</tr>
														 							 							 							
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11348-0']/sc:entry/sc:observation[sc:code/@code='DE02.10.098.00']/sc:code/@displayName"/></td>
							</tr>
							<tr>
								<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11348-0']/sc:entry/sc:observation[sc:code/@code='DE02.10.098.00']/sc:value"/></pre></td>
							</tr>

							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">体格检查</td>
							</tr>
							<tr>
							<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;">
							<div align="center">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='8716-3']/sc:section/sc:entry[sc:observation/@classCode='OBS']">
									<xsl:value-of select="sc:observation/sc:code/@displayName"/>:
									<xsl:value-of select="sc:observation/sc:value/@value"/>
									<xsl:text disable-output-escaping="yes">&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;</xsl:text>
								</xsl:for-each>
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='8716-3']/sc:section/sc:entry[sc:organizer/@classCode='BATTERY']/sc:organizer/sc:code/@displayName"/>:
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='8716-3']/sc:section/sc:entry[sc:organizer/@classCode='BATTERY']/sc:organizer/sc:component[sc:observation/@classCode='OBS']">
									<xsl:value-of select="sc:observation/sc:value/@value"/>
									<xsl:if test="(position()!=last())and(string-length(sc:observation/sc:value/@value)>0)">/</xsl:if>
								</xsl:for-each>								
							</div>
							<div align="left">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='29545-1']/sc:entry/sc:observation[sc:code/@code='DE04.10.219.00']/sc:value"/>
							</div>	
							</td>
							</tr>
							 
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">专科情况</td>
							</tr>
							<tr>
							<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;">
							<div align="left">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='29545-1']/sc:entry/sc:observation[sc:code/@code='DE08.10.061.00']/sc:value"/>
							</div>	
							</td>
							</tr>
							
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">辅助检查</td>
							</tr>
							<tr>
							<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;">
							<div align="left">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE04.30.009.00']/sc:value"/>
							</div>	
							</td>
							</tr>
							
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">初步诊断</td>
							</tr>
							<tr>
							<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;">
								 	<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11450-4']">
										<xsl:value-of select="sc:entry/sc:observation/sc:entryRelationship/sc:observation[sc:code/@code='DE05.01.024.00']/sc:value/@displayName"/><br/>
									</xsl:for-each>	
							</td>
							</tr>
						</table>
<!-- 						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;">
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">诊断</td>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='10154-3']/sc:section/sc:entry/sc:act/sc:entryRelationship">
								<tr class="odd">
									<td class="doclabel">诊断类别：</td>
									<td class="docdataitem"><xsl:value-of select="sc:observation/sc:code/@displayName"/></td>
									<td class="doclabel">疾病名称：</td>
									<td class="docdataitem"><xsl:value-of select="sc:observation/sc:value/@displayName"/></td>
									<td class="doclabel">诊断日期：</td>
									<td class="docdataitem"><xsl:choose>
									<xsl:when test="string-length(sc:observation/sc:effectiveTime/@value)>8">
										<xsl:value-of select="substring(sc:observation/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:observation/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:observation/sc:effectiveTime/@value,7,2)"/>日<xsl:value-of select="substring(sc:observation/sc:effectiveTime/@value,9,2)"/>时<xsl:value-of select="substring(sc:observation/sc:effectiveTime/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:observation/sc:effectiveTime/@value)>0">
										<xsl:value-of select="substring(sc:observation/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:observation/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:observation/sc:effectiveTime/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose></td>
								</tr>
								<tr>
									<td class="doclabel">诊断描述：</td>
									<td colspan="5" style="height:30px;word-break:break-all;word-wrap:break-word;" class="docdataitem"><pre><xsl:value-of select="sc:observation/sc:text"/></pre></td>
								</tr> 
							</xsl:for-each>
						</table> -->
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>





