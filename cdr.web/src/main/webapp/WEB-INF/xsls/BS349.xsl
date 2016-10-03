<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:sc="urn:hl7-org:v3"  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:Constants="xalan://com.yly.cdr.core.Constants">
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
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="6" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"><h3><xsl:value-of select="sc:title"/></h3></td>
							</tr>
							<tr>
								<td class="doclabel">病人姓名：</td>
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
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:houseNumber"/></td>
								
								<td class="doclabel">床位号：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/></td>
								
								<td class="doclabel">病人科室：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:name"/></td>
							</tr>
							<tr>
								<td class="doclabel">记录护士：</td>
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
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:component[sc:section/sc:code/@code='86895']/sc:section/sc:title"/>：<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:component[sc:section/sc:code/@code='86895']/sc:section/sc:entry[sc:observation/sc:value/@value='true']">
								<xsl:value-of select="sc:observation/sc:text"/>
								<xsl:if test="position()!=last() and string-length(sc:observation/sc:text)>0">、</xsl:if>
							</xsl:for-each></td>
							</tr>
							
							
							
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:component[not(sc:section/sc:code/@code='86895')and not(sc:section/sc:code/@code='86857')and not(sc:section/sc:code/@code='86859')and not(sc:section/sc:code/@code='86862-0')and not(sc:section/sc:code/@code='86875')and not(sc:section/sc:code/@code='86881')and not(sc:section/sc:code/@code='86882')]">
								<tr>
									<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
										<xsl:value-of select="sc:section/sc:title"/>：
									<xsl:for-each select="sc:section/sc:entry[sc:observation/sc:value/@value='true']">
										<xsl:value-of select="sc:observation/sc:code/@displayName"/>
										<xsl:if test="sc:observation/sc:text">（<xsl:value-of select="sc:observation/sc:text"/>）</xsl:if>
										<xsl:if test="sc:observation/sc:entryRelationship">：
											<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:value/@value='true']">
												<xsl:value-of select="sc:observation/sc:code/@displayName"/>
												<xsl:if test="sc:observation/sc:text">（<xsl:value-of select="sc:observation/sc:text"/>）</xsl:if>
												<xsl:if test="sc:observation/sc:entryRelationship">：
													<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:value/@value='true']">
														<xsl:value-of select="sc:observation/sc:code/@displayName"/>
													</xsl:for-each>
												</xsl:if>
												<xsl:if test="position()!=last() and string-length(sc:observation/sc:code/@displayName)>0">、</xsl:if>
											</xsl:for-each>
										</xsl:if>
										<xsl:if test="position()!=last() and string-length(sc:observation/sc:code/@displayName)>0">、</xsl:if>
									</xsl:for-each>
									</td>
								</tr>
							</xsl:for-each>
							
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:component[(sc:section/sc:code/@code='86859') or (sc:section/sc:code/@code='86857')]">
								<tr>
									<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
										<xsl:choose>
											<xsl:when test="sc:section/sc:code/@code='86857'">（A）</xsl:when>
											<xsl:otherwise>（B）</xsl:otherwise>
										</xsl:choose>
										<xsl:value-of select="sc:section/sc:title"/>：
										<xsl:for-each select="sc:section/sc:entry[sc:observation/sc:value/@value='true']">
											<tr height="28"><td colspan="6" class="blws" >
												<xsl:value-of select="sc:observation/sc:code/@displayName"/>
												<xsl:if test="sc:observation/sc:text">（<xsl:value-of select="sc:observation/sc:text"/>）</xsl:if>
												<xsl:if test="sc:observation/sc:entryRelationship">：
													<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:value/@value='true']">
														<xsl:value-of select="sc:observation/sc:code/@displayName"/>
														<xsl:if test="sc:observation/sc:text">（<xsl:value-of select="sc:observation/sc:text"/>）</xsl:if>
														<xsl:if test="sc:observation/sc:entryRelationship">
															<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:value/@value='true']">
																（<xsl:value-of select="sc:observation/sc:code/@displayName"/>：<xsl:value-of select="sc:observation/sc:text"/>）
															</xsl:for-each>
														</xsl:if>
														<xsl:if test="position()!=last() and string-length(sc:observation/sc:code/@displayName)>0">、</xsl:if>
													</xsl:for-each>
												</xsl:if>
											</td></tr>
										</xsl:for-each>
									</td>
								</tr>
							</xsl:for-each>
							
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:component[ sc:section/sc:code/@code='86862-0']">
								<tr>
									<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
										（C）<xsl:value-of select="sc:section/sc:title"/>：
									<xsl:for-each select="sc:section/sc:entry[sc:observation/sc:value/@value='true']">
										<tr height="28"><td colspan="6" class="blws">
										<xsl:value-of select="position()"/>.<xsl:value-of select="sc:observation/sc:code/@displayName"/>：
										<xsl:if test="sc:observation/sc:text">（<xsl:value-of select="sc:observation/sc:text"/>）</xsl:if>
										<xsl:if test="sc:observation/sc:entryRelationship">
											<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:value/@value='true']">
												
												<xsl:value-of select="sc:observation/sc:code/@displayName"/>
												<xsl:if test="sc:observation/sc:text">（<xsl:value-of select="sc:observation/sc:text"/>）</xsl:if>
												
												<xsl:if test="position()!=last() and string-length(sc:observation/sc:code/@displayName)>0">、</xsl:if>
											</xsl:for-each>
											
											<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:value/@value='true']">
											<xsl:if test="sc:observation/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:value/@value='true']">
													<tr><td colspan="6" class="blws">
													<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:value/@value='true']">
														<xsl:value-of select="sc:observation/sc:code/@displayName"/>
														<xsl:if test="sc:observation/sc:text">（<xsl:value-of select="sc:observation/sc:text"/>）</xsl:if>
														<xsl:if test="position()!=last() and string-length(sc:observation/sc:code/@displayName)>0">、</xsl:if>
													</xsl:for-each>
													</td></tr>
											</xsl:if>
											</xsl:for-each>
											
										</xsl:if>
										</td></tr>
									</xsl:for-each>
									</td>
								</tr>
							</xsl:for-each>					
							
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:component[ (sc:section/sc:code/@code='86875')or (sc:section/sc:code/@code='86881')]">
								<tr>
									<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
										<xsl:choose>
											<xsl:when test="position()=1">（D）</xsl:when>
											<xsl:when test="position()=2">（E）</xsl:when>
										</xsl:choose>
										<xsl:value-of select="sc:section/sc:title"/>：
									<xsl:for-each select="sc:section/sc:entry[sc:observation/sc:value/@value='true']">
										<xsl:value-of select="sc:observation/sc:code/@displayName"/>
										<xsl:if test="sc:observation/sc:text">（<xsl:value-of select="sc:observation/sc:text"/>）</xsl:if>
										<xsl:if test="sc:observation/sc:entryRelationship">
											<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:value/@value='true']">
												<tr height="28"><td colspan="6" class="blws">
												<xsl:value-of select="sc:observation/sc:code/@displayName"/>
												<xsl:if test="sc:observation/sc:text">（<xsl:value-of select="sc:observation/sc:text"/>）</xsl:if>
												<xsl:if test="sc:observation/sc:entryRelationship">：
													<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:value/@value='true']">
														<xsl:value-of select="sc:observation/sc:code/@displayName"/>
														<xsl:if test="sc:observation/sc:text">（<xsl:value-of select="sc:observation/sc:text"/>）</xsl:if>
														<xsl:if test="position()!=last() and string-length(sc:observation/sc:code/@displayName)>0">、</xsl:if>
													</xsl:for-each>
												</xsl:if>
												</td></tr>
											</xsl:for-each>
										</xsl:if>
										<xsl:if test="position()!=last() and string-length(sc:observation/sc:code/@displayName)>0">、</xsl:if>
									</xsl:for-each>
									</td>
								</tr>
							</xsl:for-each>
							
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">（F）<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:component[sc:section/sc:code/@code='86882']/sc:section/sc:title"/>：</td>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:component[sc:section/sc:code/@code='86882']/sc:section/sc:entry[sc:observation/sc:value/@value='true']">
								<tr>
									<td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:observation/sc:text"/></pre></td>
								</tr>
							</xsl:for-each>
						
						</table>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>





