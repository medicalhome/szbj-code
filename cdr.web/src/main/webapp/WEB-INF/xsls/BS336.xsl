<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:sc="urn:hl7-org:v3"  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:Constants="xalan://com.founder.cdr.core.Constants">
	<xsl:output method="html" indent="yes" version="4.0"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" />
	<xsl:template match="/sc:ClinicalDocument">
		<html>
			<head>
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
						<table border="0" align="center" width="100%" cellspacing="2" cellpadding="1" style="border-collapse:collapse;bgcolor:white;">
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
								<td class="doclabel">病区：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								
								<td class="doclabel">床位号：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								
								<td class="doclabel">所在科室：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								
							</tr>
							<tr>
								<td class="doclabel">记录医生姓名：</td>
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
								<td class="doclabel">上级医生名称：</td>
							 	<td class="docdataitem"><xsl:value-of select="sc:authenticator/sc:assignedEntity/sc:assignedPerson[not(@classCode='PSN')]/sc:name"/></td>			
							</tr>
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
					<!-- 		<tr height="25" class="odd">
								<td class="doclabel">修改医生名称：</td>
								<td class="docdataitem"><xsl:value-of select="sc:participant/sc:associatedEntity/sc:associatedPerson/sc:name"/></td>
								
								<td class="doclabel">修改日期：</td>
								<td class="docdataitem" colspan="3"><xsl:choose>
									<xsl:when test="string-length(sc:participant/sc:time/@value)>8">
										<xsl:value-of select="substring(sc:participant/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:participant/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:participant/sc:time/@value,7,2)"/>日<xsl:value-of select="substring(sc:participant/sc:time/@value,9,2)"/>时<xsl:value-of select="substring(sc:participant/sc:time/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:participant/sc:time/@value)>0">
										<xsl:value-of select="substring(sc:participant/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:participant/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:participant/sc:time/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose></td>
							</tr>
							 -->

							<tr>
								
								<td class="doclabel">电子签章号：</td>
								<td class="docdataitem"><xsl:value-of select="sc:legalAuthenticator/sc:assignedEntity/sc:id/@extension"/></td>
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
						</table>
						<!-- $Author :chang_xuewen
						$Date : 2013/07/24 11:00
						$[BUG]0034785 MODIFY BEGIN -->
						<table border="0" align="center" width="100%" cellspacing="2" cellpadding="1" style="border-collapse:collapse;table-layout:fixed;" class="table"> 
						<!-- $[BUG]0034785 MODIFY END -->
							<tr>
								<td class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">病程记录内容</td>
							</tr>
							<tr>
							<xsl:choose>
								<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value)=0">
									<td class="blws" height="30" style="text-align:center;height:30px;">没有相关内容！</td>
								</xsl:when>
								<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value)>0">
									<!-- $Author :chang_xuewen
									$Date : 2013/07/24 11:00
									$[BUG]0034785 MODIFY BEGIN -->
									<td class="blws" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value"/></pre>
									</td>
									<!-- $[BUG]0034785 MODIFY END -->
								</xsl:when></xsl:choose>
							</tr>
						</table>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>