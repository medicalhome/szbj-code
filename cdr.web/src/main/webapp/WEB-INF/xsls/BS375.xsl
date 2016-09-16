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
								<td class="doclabel">婚姻：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:maritalStatusCode/@displayName"/>
								</td>
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
								<td class="doclabel">拟行手术名称：</td>
								<td class="docdataitem" colspan="3">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry[sc:observation/sc:code/@displayName='拟实施手术及操作编码']">
										<xsl:if test="position()>1">、</xsl:if>
										<xsl:value-of select="sc:observation/sc:value/@displayName"/>
									</xsl:for-each>	
								</td>
								
								<td class="doclabel">拟定手术者：</td>
								<td class="docdataitem">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry[sc:observation/sc:code/@displayName='手术者姓名']">
										<xsl:if test="position()>1">、</xsl:if>
										<xsl:value-of select="sc:observation/sc:value/@displayName"/>
									</xsl:for-each>	
								</td>
							</tr>
							
							<tr>
								<td class="doclabel">主治医师：</td>
								<td class="docdataitem"><xsl:value-of select="sc:authenticator/sc:assignedEntity[sc:code/@displayName='主治医师']/sc:assignedPerson/sc:name"/></td>
								<td class="doclabel">住院医师：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:authenticator/sc:assignedEntity[sc:code/@displayName='住院医师']/sc:assignedPerson/sc:name"/>
								</td>
								<td class="doclabel">记录日期：</td>
								<td class="docdataitem">
									<xsl:choose>
									<xsl:when test="string-length(sc:author/sc:time/@value)>8">
										<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>日
										<!-- <xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,9,2)"/>时<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,11,2)"/>分 -->
									</xsl:when>
									<xsl:when test="string-length(sc:author/sc:time/@value)>0">
										<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose>
								</td>
							</tr>
							
							<tr>
								<td class="doclabel">手术审批意见：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='手术审批意见']/sc:text"/>
								</td>
							    <td class="doclabel">审批者：</td>
									<td class="docdataitem">
										<xsl:value-of select="sc:legalAuthenticator/sc:assignedEntity/sc:assignedPerson/sc:name"/>
									</td>
								<td class="doclabel">审批日期：</td>
							    <td class="docdataitem">
									<xsl:choose>
									<xsl:when test="string-length(sc:legalAuthenticator/sc:time/@value)>8">
										<xsl:value-of select="substring(sc:legalAuthenticator/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:legalAuthenticator/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:legalAuthenticator/sc:time/@value,7,2)"/>日
										<!-- <xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,9,2)"/>时<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:asOrganizationPartOf/sc:effectiveTime/@value,11,2)"/>分 -->
									</xsl:when>
									<xsl:when test="string-length(sc:legalAuthenticator/sc:time/@value)>0">
										<xsl:value-of select="substring(sc:legalAuthenticator/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:legalAuthenticator/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:legalAuthenticator/sc:time/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose>
							    </td>
							</tr>
						</table>
						
						<table border="0" align="center" width="100%" cellspacing="2" cellpadding="1" style="border-collapse:collapse;table-layout:fixed;" class="table"> 
							<tr>
								<td class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">
								病历概要
								</td>
							</tr>
							<tr>
							<xsl:choose>
								<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='DE06.00.182.00']/sc:text)=0">
									<td class="blws" height="30" style="text-align:center;height:30px;">没有相关内容！</td>
								</xsl:when>
								<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='DE06.00.182.00']/sc:text)>0">
									<td class="blws" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='DE06.00.182.00']/sc:text"/></pre>
									</td>
								</xsl:when></xsl:choose>
							</tr>
						</table>
						
						<table border="0" align="center" width="100%" cellspacing="2" cellpadding="1" style="border-collapse:collapse;table-layout:fixed;" class="table"> 
							<tr>
								<td class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">
								术前诊断
								</td>
							</tr>
							<tr>
							<xsl:choose>
								<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry[sc:observation/sc:code/@displayName='术前诊断编码'][1]/sc:observation/@classCode)=0">
									<td class="blws" height="30" style="text-align:center;height:30px;">没有相关内容！</td>
								</xsl:when>
								<xsl:otherwise>
									<td class="blws" style="word-break:break-all;word-wrap:break-word;">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry[sc:observation/sc:code/@displayName='术前诊断编码']">
										<xsl:value-of select="position()" />.<xsl:value-of select="sc:observation/sc:value/@displayName"/><br></br>
									</xsl:for-each>
									</td>
								</xsl:otherwise>
							</xsl:choose>
							</tr>
						</table>
						
						<table border="0" align="center" width="100%" cellspacing="2" cellpadding="1" style="border-collapse:collapse;table-layout:fixed;" class="table"> 
							<tr>
								<td class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">
								诊断依据及手术指征
								</td>
							</tr>
							<tr>
							<xsl:choose>
								<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@displayName='诊断依据']/sc:value)=0">
									<xsl:if test="string-length(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:observation[sc:code/@displayName='手术指征']/sc:value)=0">
										<td class="blws" height="30" style="text-align:center;height:30px;">没有相关内容！</td>
									</xsl:if>	
								</xsl:when>
								<xsl:otherwise>
									<td class="blws" style="word-break:break-all;word-wrap:break-word;">
									<pre>
										<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@displayName='诊断依据']/sc:value"/>
									</pre>
									<pre>
										<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:observation[sc:code/@displayName='手术指征']/sc:value"/>
									</pre>
									</td>
								</xsl:otherwise>
							</xsl:choose>
							</tr>
						</table>
						
						<table border="0" align="center" width="100%" cellspacing="2" cellpadding="1" style="border-collapse:collapse;table-layout:fixed;" class="table"> 
							<tr>
								<td class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">
								麻醉科会诊意见及执行麻醉方法
								</td>
							</tr>
							<tr>
								<td class="blws" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='会诊意见']/sc:entry/sc:observation/sc:value"/></pre>
									<pre>
										<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='18776-5']/sc:entry/sc:observation[sc:code/@displayName='拟实施麻醉方法代码']/sc:value/@displayName"/>
									</pre>
								</td>
							</tr>
						</table>
						
						<table border="0" align="center" width="100%" cellspacing="2" cellpadding="1" style="border-collapse:collapse;table-layout:fixed;" class="table"> 
							<tr>
								<td class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">
								术前准备
								</td>
							</tr>
							<tr>
								<td class="blws" style="word-break:break-all;word-wrap:break-word;">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='术前准备']/sc:entry">
										<pre><xsl:value-of select="sc:observation/sc:code/@displayName"/></pre>
										<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component">
											<xsl:value-of select="sc:observation/sc:code/@displayName"/>:<xsl:value-of select="sc:observation/sc:value"/>
												<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component">
													<xsl:value-of select="sc:observation/sc:code/@displayName"/>:<xsl:value-of select="sc:observation/sc:value"/>
													<xsl:text disable-output-escaping="yes">&amp;nbsp;&amp;nbsp;</xsl:text>
												</xsl:for-each>
											<xsl:text disable-output-escaping="yes">&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;</xsl:text>
										</xsl:for-each>
									</xsl:for-each>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
