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
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
							<tr>
								<td colspan="6" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;">
								<!--  <h3><xsl:value-of select="sc:custodian/sc:assignedCustodian/sc:representedCustodianOrganization/sc:name"/></h3>-->
								<h3><xsl:value-of select="sc:title"/></h3>
								</td>
							</tr>
							<tr>
								<td class="doclabel">科别：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								<td class="doclabel">病区：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								<td class="doclabel">床号：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
							</tr>
							<tr>
								<td class="doclabel">住院号：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112656.1.2.1.12']/@extension"/>
								</td>
								<td class="doclabel">病人姓名：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/></td>
								<td class="doclabel">性别：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/></td>
							</tr>
							<tr>
								<td class="doclabel">患者年龄：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:age/@value"/></td>
								<td class="doclabel">职业：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:occupation/sc:occupationCode/@displayName"/>
								</td>
								<td class="doclabel">籍贯：</td>
								<td class="docdataitem">
								<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:nativePlace/sc:place/sc:addr/sc:state"/>
								<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:nativePlace/sc:place/sc:addr/sc:city"/>
								</td>
							</tr>
							<tr>
								<td class="doclabel">入院日期：</td>
								<td class="docdataitem">
								<xsl:choose>
									<xsl:when test="string-length(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value)>8">
										<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,7,2)"/>日
										<!-- <xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,9,2)"/>时<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,11,2)"/>分 -->
									</xsl:when>
									<xsl:when test="string-length(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value)>0">
										<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose>
								</td>
							    <td class="doclabel">讨论时间：</td>
									<td class="docdataitem"><xsl:choose>
									<xsl:when test="string-length(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value)>8">
										<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,7,2)"/>日<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,9,2)"/>时<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value)>0">
										<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose></td>
								<td class="doclabel">讨论地点：</td>
							    <td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:addr"/>
							    </td>
							</tr>
							<tr>
								<td class="doclabel">参加者：</td>
								<td colspan="5" class="docdataitem">
									<xsl:for-each select="sc:participant[sc:associatedEntity/@classCode='ASSIGNED']">
										<xsl:if test="position()>1">、</xsl:if>
										<xsl:value-of select="sc:associatedEntity/sc:associatedPerson/sc:name"/>
									</xsl:for-each>
								</td>
							</tr>
							<tr>
								<td class="doclabel">主持者：</td>
								<td class="docdataitem">
									<xsl:for-each select="sc:authenticator[sc:assignedEntity/sc:code/@displayName='主持人']">
										<xsl:if test="position()>1">、</xsl:if>
										<xsl:value-of select="sc:assignedEntity/sc:assignedPerson/sc:name"/>
									</xsl:for-each>
								</td>
								<td class="doclabel">病情报告者：</td>
								<td class="docdataitem"><xsl:value-of select="sc:informant/sc:assignedEntity/sc:assignedPerson/sc:name"/></td>
								<td class="doclabel">记录者：</td>
								<td class="docdataitem"><xsl:value-of select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name"/></td>
							</tr>
						</table>
						<table border="0" align="center" width="100%" cellspacing="2" cellpadding="1" style="border-collapse:collapse;table-layout:fixed;" class="table"> 
							<tr>
								<td class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">
								发言纪要(重点记录发言者对病情的分析、对诊断、治疗、抢救的意见)
								</td>
							</tr>
							<tr>
							<xsl:choose>
								<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE06.00.018.00']/sc:value)=0">
									<td class="blws" height="30" style="text-align:center;height:30px;">没有相关内容！</td>
								</xsl:when>
								<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE06.00.018.00']/sc:value)>0">
									<td class="blws" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE06.00.018.00']/sc:value"/></pre>
									</td>
								</xsl:when></xsl:choose>
							</tr>
						</table>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
