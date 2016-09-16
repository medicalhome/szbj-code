<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:sc="urn:hl7-org:v3"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:Constants="xalan://com.founder.cdr.core.Constants">
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
					{return '<xsl:value-of select="sc:setId/@extension"/>'+'<xsl:value-of select="sc:id/@extension"/>';
				</script>
				<script type="text/javascript" src="../scripts/doc/docDetail.js"></script>
			</head>
			<body>
				<div id="mainContent">
					<div name="selectTabs">
						<div class="responsability">
							<xsl:value-of select="Constants:getResponsabilityClaimingContent()" />
						</div>
						<table border="0" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="6" height="30" align="center"
									style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;">
									<h3>
										<xsl:value-of select="sc:title" />
									</h3>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">姓名：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name" />
								</td>

								<td class="doclabel">病区：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name" />
								</td>

								<td class="doclabel">床号：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name" />
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">科别：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name" />
								</td>

								<td class="doclabel">诊断：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='11450-4']/sc:section/sc:entry/sc:observation/sc:value/@displayName" />
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
								<td colspan="6" class="blockHeader" height="10" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"></td>
							</tr>
							<tr>
								<td class="doclabelline2">序号</td>
								<td class="doclabelline2">医嘱</td>
								<td class="doclabelline2">交叉配血采血时间/签名</td>
								<td class="doclabelline2">入室核对时间/签名</td>
								<td class="doclabelline2"><pre>执行時間/签名</pre><pre>输血速度/巡视结果</pre></td>
								<td class="doclabelline2">签名</td>
							</tr>

							<xsl:for-each select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='56836-0']/sc:section/sc:component">
								<tr>
									<td class="docdataitemline2" align="center">
										<xsl:value-of select="sc:section/sc:id/@extension" />
									</td>
									<td class="docdataitemline2" align="left">

										<xsl:choose>
											<xsl:when test="string-length(sc:section/sc:component[sc:section/sc:code/@displayName='医嘱']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value)>8">
												<xsl:value-of select="substring(sc:section/sc:component[sc:section/sc:code/@displayName='医嘱']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,1,4)" />年<xsl:value-of select="substring(sc:section/sc:component[sc:section/sc:code/@displayName='医嘱']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,5,2)" />月<xsl:value-of select="substring(sc:section/sc:component[sc:section/sc:code/@displayName='医嘱']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,7,2)" />日<xsl:value-of select="substring(sc:section/sc:component[sc:section/sc:code/@displayName='医嘱']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,9,2)" />时<xsl:value-of select="substring(sc:section/sc:component[sc:section/sc:code/@displayName='医嘱']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,11,2)" />分（开立时间）
											</xsl:when>
											<xsl:when test="string-length(sc:section/sc:component[sc:section/sc:code/@displayName='医嘱']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value)>0">
												<xsl:value-of select="substring(sc:section/sc:component[sc:section/sc:code/@displayName='医嘱']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,1,4)" />年<xsl:value-of select="substring(sc:section/sc:component[sc:section/sc:code/@displayName='医嘱']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,5,2)" />月<xsl:value-of select="substring(sc:section/sc:component[sc:section/sc:code/@displayName='医嘱']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,7,2)" />日（开立时间）
											</xsl:when>
										</xsl:choose>

										<pre><xsl:value-of select="sc:section/sc:component[sc:section/sc:code/@displayName='医嘱']/sc:section/sc:entry/sc:observation/sc:value/@value" /><xsl:value-of select="sc:section/sc:component[sc:section/sc:code/@displayName='医嘱']/sc:section/sc:entry/sc:observation/sc:value/@unit" /></pre>
										<xsl:for-each select="sc:section/sc:component[sc:section/sc:code/@displayName='医嘱']/sc:section/sc:entry/sc:observation/sc:entryRelationship">
											<pre><xsl:value-of select="sc:observation/sc:value/@displayName" /></pre>
										</xsl:for-each>

									</td>

									<td class="docdataitemline2" align="left">

										<xsl:choose>
											<xsl:when test="string-length(sc:section/sc:component[sc:section/sc:code/@displayName='交叉配血采血时间/签名']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value)>8">
												<xsl:value-of select="substring(sc:section/sc:component[sc:section/sc:code/@displayName='交叉配血采血时间/签名']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,1,4)" />年<xsl:value-of select="substring(sc:section/sc:component[sc:section/sc:code/@displayName='交叉配血采血时间/签名']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,5,2)" />月<xsl:value-of select="substring(sc:section/sc:component[sc:section/sc:code/@displayName='交叉配血采血时间/签名']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,7,2)" />日<xsl:value-of select="substring(sc:section/sc:component[sc:section/sc:code/@displayName='交叉配血采血时间/签名']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,9,2)" />时<xsl:value-of select="substring(sc:section/sc:component[sc:section/sc:code/@displayName='交叉配血采血时间/签名']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,11,2)" />分
											</xsl:when>
											<xsl:when test="string-length(sc:section/sc:component[sc:section/sc:code/@displayName='交叉配血采血时间/签名']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value)>0">
												<xsl:value-of select="substring(sc:section/sc:component[sc:section/sc:code/@displayName='交叉配血采血时间/签名']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,1,4)" />年<xsl:value-of select="substring(sc:section/sc:component[sc:section/sc:code/@displayName='交叉配血采血时间/签名']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,5,2)" />	月<xsl:value-of 	select="substring(sc:section/sc:component[sc:section/sc:code/@displayName='交叉配血采血时间/签名']/sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,7,2)" />日
											</xsl:when>
										</xsl:choose>
									</td>

									<td class="docdataitemline2" align="left">
										<xsl:for-each select="sc:section/sc:component[sc:section/sc:code/@displayName='入室核对时间/签名']">
											<pre><xsl:value-of select="sc:section/sc:entry/sc:observation/sc:code/@code" /></pre>
											<xsl:choose>
												<xsl:when test="string-length(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value)>8">
													<xsl:value-of select="substring(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,1,4)" />年<xsl:value-of select="substring(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,5,2)" />月<xsl:value-of select="substring(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,7,2)" />	日<xsl:value-of select="substring(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,9,2)" />时<xsl:value-of select="substring(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,11,2)" />分
												</xsl:when>
												<xsl:when test="string-length(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value)>0">
													<xsl:value-of select="substring(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,1,4)" />年<xsl:value-of select="substring(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,5,2)" />	月<xsl:value-of select="substring(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,7,2)" />日
												</xsl:when>
											</xsl:choose>
											<pre><xsl:value-of select="sc:section/sc:entry/sc:observation/sc:performer/sc:assignedEntity/sc:assignedPerson/sc:name" /></pre>
										</xsl:for-each>
									</td>
									<td align="left" class="cellTd tabRight" style="border-top:solid 1px #c0ddea;border-right:1px solid #C5D6E0;border-bottom:solid 1px #c0ddea;">
										<xsl:for-each select="sc:section/sc:component[sc:section/sc:code/@displayName='执行時間 / 签名/输血速度 / 巡视结果']">
											<xsl:if test="string-length(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value)>0 or string-length(sc:section/sc:entry/sc:observation/sc:entryRelationship[sc:observation/sc:code/@code='输血状态']/sc:observation/sc:value/@displayName)>0 or string-length(sc:section/sc:entry/sc:observation/sc:code/@code)>0 or string-length(sc:section/sc:entry/sc:observation/sc:value/@value)>0 or string-length(sc:section/sc:entry/sc:observation/sc:entryRelationship[sc:observation/sc:code/@code='输血反应']/sc:observation/sc:value/@displayName)>0 or string-length(sc:section/sc:entry/sc:observation/sc:entryRelationship[sc:observation/sc:code/@displayName='处理措施']/sc:observation/sc:value/@displayName)>0 or string-length(sc:section/sc:entry/sc:observation/sc:entryRelationship[sc:observation/sc:code/@displayName='处理措施']/sc:observation/sc:performer/sc:assignedEntity/sc:assignedPerson/sc:name)>0">
												<div style="border-bottom:solid 1px #c0ddea;color: #043a8f">

													<xsl:choose>
														<xsl:when test="string-length(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value)>8">
															<xsl:value-of select="substring(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,1,4)" />年<xsl:value-of select="substring(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,5,2)" />月<xsl:value-of select="substring(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,7,2)" />日<xsl:value-of select="substring(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,9,2)" />时<xsl:value-of select="substring(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,11,2)" />分
														</xsl:when>
														<xsl:when test="string-length(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value)>0">
															<xsl:value-of select="substring(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,1,4)" />年<xsl:value-of select="substring(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,5,2)" />月<xsl:value-of select="substring(sc:section/sc:entry/sc:observation/sc:effectiveTime/@value,7,2)" />日
														</xsl:when>
													</xsl:choose>
													<pre><xsl:value-of select="sc:section/sc:entry/sc:observation/sc:entryRelationship[sc:observation/sc:code/@code='输血状态']/sc:observation/sc:value/@displayName" /></pre>
													<pre><xsl:value-of select="sc:section/sc:entry/sc:observation/sc:code/@code" /></pre>
													<pre><xsl:value-of select="sc:section/sc:entry/sc:observation/sc:value/@value" /><xsl:value-of select="sc:section/sc:entry/sc:observation/sc:value/@unit" /></pre>
													<pre><xsl:value-of select="sc:section/sc:entry/sc:observation/sc:entryRelationship[sc:observation/sc:code/@code='输血反应']/sc:observation/sc:value/@displayName" /></pre>

													<xsl:if test="string-length(sc:section/sc:entry/sc:observation/sc:entryRelationship[sc:observation/sc:code/@code='输血反应类型']/sc:observation/sc:value/@displayName)>0">
														<pre>输血反应：<xsl:value-of select="sc:section/sc:entry/sc:observation/sc:entryRelationship[sc:observation/sc:code/@code='输血反应类型']/sc:observation/sc:value/@displayName" /></pre>
													</xsl:if>
													<xsl:if test="string-length(sc:section/sc:entry/sc:observation/sc:entryRelationship[sc:observation/sc:code/@displayName='处理措施']/sc:observation/sc:value/@displayName)>0 or string-length(sc:section/sc:entry/sc:observation/sc:entryRelationship[sc:observation/sc:code/@displayName='处理措施']/sc:observation/sc:performer/sc:assignedEntity/sc:assignedPerson/sc:name)>0">
														<pre>处理措施：<xsl:value-of select="sc:section/sc:entry/sc:observation/sc:entryRelationship[sc:observation/sc:code/@displayName='处理措施']/sc:observation/sc:value/@displayName" />;<xsl:value-of select="sc:section/sc:entry/sc:observation/sc:entryRelationship[sc:observation/sc:code/@displayName='处理措施']/sc:observation/sc:performer/sc:assignedEntity/sc:assignedPerson/sc:name" /></pre>
													</xsl:if>
												</div>
											</xsl:if>
										</xsl:for-each>
									</td>

									<td class="docdataitemline2" align="center">
										<xsl:value-of select="sc:section/sc:component[sc:section/sc:code/@displayName='执行時間 / 签名/输血速度 / 巡视结果']/sc:section/sc:entry/sc:observation/sc:performer/sc:assignedEntity/sc:assignedPerson/sc:name" />
									</td>
								</tr>
							</xsl:for-each>



						</table>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>