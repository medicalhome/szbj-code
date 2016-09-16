<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:sc="urn:hl7-org:v3"  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:Constants="xalan://com.founder.cdr.core.Constants">
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
							<tr class="odd">
								<td class="doclabel">姓名：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/></td>
								
								<td class="doclabel">性别：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/></td>
								
								<td class="doclabel">年龄：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:age/@value"/><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:age/@unit"/></td>					
							
							<!-- 	
								<td class="doclabel">联系电话：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:telecom/@value"/></td>
							 -->
							</tr>
							<tr>
								<td class="doclabel">病区：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								
								<td class="doclabel">床位号：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								
								<td class="doclabel">病人科室：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
							</tr>
							<tr class="odd">
								<td class="doclabel">访视医生：</td>
								<td class="docdataitem"><xsl:value-of select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name"/></td>
								
								<td class="doclabel">访视日期：</td>
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
							
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component">
								<tr>
									<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;border-bottom: solid 1px #B3C4D4; font-weight: bold;">
										<xsl:value-of select="sc:section/sc:code/@displayName"/>：	
									</td>
								</tr>
								<xsl:choose>
									<xsl:when test="sc:section/sc:text!=''">
										<tr height="20" class="blws"><td  colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;">
											<pre>
												<xsl:value-of select="sc:section/sc:text"/>
											</pre>
										</td></tr>
									</xsl:when>
								</xsl:choose>
								<tr>
									<xsl:for-each select="sc:section/sc:entry">
										
										<td class="doclabel"><xsl:value-of select="sc:organizer/sc:code/@displayName"/></td>
										
										<td class="docdataitem">
											<xsl:for-each select="sc:organizer/sc:component">
												<xsl:value-of select="sc:observation/sc:code/@displayName"/>:<xsl:value-of select="sc:observation/sc:value/@displayName"/>;
											</xsl:for-each>
										</td>										
										<xsl:if test="position() mod 3=0">
											<tr>
											</tr>
										</xsl:if>
										<xsl:if test="position()=last() and last() mod 3=1">
					                        <td class="docdataitem" colspan="4"></td>				                            				                            
										</xsl:if>
										<xsl:if test="position()=last() and last() mod 3=2">
											<td class="docdataitem" colspan="2"></td>					                            				                            
										</xsl:if>
									
									</xsl:for-each>
							</tr>
							</xsl:for-each>
							
							<td colspan="6" class="blockHeader" height="17" align="center" style="padding-left: 3px; border-top: solid 1px #B3C4D4;border-bottom: solid 1px #B3C4D4;">
										本页在开立手术医嘱时自动生成，请妥善保留在病历中。	
							</td>
							
							<!-- 
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component">
								<tr>
									<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;border-bottom: solid 1px #B3C4D4; font-weight: bold;">
										<xsl:value-of select="sc:section/sc:title"/>：
										<xsl:choose>
											<xsl:when test="sc:section/sc:code/@code='52450-4'"><xsl:value-of select="sc:section/sc:entry/sc:observation/sc:code/@displayName"/></xsl:when>
											<xsl:when test="sc:section/sc:code/@code='18776-5'"><xsl:value-of select="sc:section/sc:entry/sc:act/sc:entryRelationship/sc:act/sc:code/@displayName"/></xsl:when>
										</xsl:choose>
									</td>
								</tr>
								<xsl:choose>
									<xsl:when test="sc:section/sc:code/@code='22029-3'">
										<xsl:for-each select="sc:section/sc:component/sc:section/sc:entry">
											<tr height="20"><td colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;"><pre>
											<xsl:choose>
												<xsl:when test="sc:observation/sc:code/@displayName"><xsl:value-of select="sc:observation/sc:code/@displayName"/>：</xsl:when>
												<xsl:otherwise>其他：</xsl:otherwise>
											</xsl:choose>
											<xsl:choose>
												<xsl:when test="sc:observation/sc:code/@code='413347006'">
													<xsl:value-of select="sc:observation/sc:value/@displayName"/><xsl:if test="sc:observation/sc:priorityCode/@value='1'">（急诊）</xsl:if>
												</xsl:when>
												<xsl:when test="sc:observation/sc:value/@displayName or sc:observation/sc:interpretationCode/@displayName"><xsl:value-of select="sc:observation/sc:value/@displayName"/><xsl:value-of select="sc:observation/sc:interpretationCode/@displayName"/><xsl:if test="sc:observation/sc:text">（<xsl:value-of select="sc:observation/sc:text"/>）</xsl:if>
												</xsl:when>
												
												<xsl:when test="sc:observation/sc:entryRelationship">
													<xsl:value-of select="sc:observation/sc:value"/>：
													<xsl:for-each select="sc:observation/sc:entryRelationship[sc:observation/sc:value/@value='true']">
														<xsl:value-of select="sc:observation/sc:text"/>
														<xsl:if test="position()!=last() and string-length(sc:observation/sc:text)>0">、</xsl:if>
													</xsl:for-each>
												</xsl:when>
												<xsl:otherwise><xsl:value-of select="sc:observation/sc:value"/></xsl:otherwise>
											</xsl:choose>
											</pre></td></tr>
										</xsl:for-each>
									</xsl:when>
									<xsl:when test="sc:section/sc:text">
										<tr height="20" class="blws"><td  colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;">
											<pre>
												<xsl:value-of select="sc:section/sc:text"/>
											</pre>
										</td></tr>
									</xsl:when>
									<xsl:when test="sc:section/sc:entry[sc:observation/sc:value/@value='true']">
										<xsl:choose>
											<xsl:when test="sc:section/sc:entry[sc:observation/sc:value/@value='true']/sc:observation/sc:text">
												<xsl:for-each select="sc:section/sc:entry[sc:observation/sc:value/@value='true']">
													<tr height="20" ><td  colspan="6" class="blws" style="word-break:break-all;word-wrap:break-word;"><pre>
													<xsl:value-of select="sc:observation/sc:code/@displayName"/>：<xsl:value-of select="sc:observation/sc:text"/></pre>
												</td></tr>
												</xsl:for-each>
											</xsl:when>
											<xsl:otherwise>
												<tr height="20" class="odd"><td  colspan="6" class="blws">
													<xsl:for-each select="sc:section/sc:entry[sc:observation/sc:value/@value='true']">
														<xsl:value-of select="sc:observation/sc:code/@displayName"/>
														<xsl:if test="position()!=last() and string-length(sc:observation/sc:code/@displayName)>0">、</xsl:if>
													</xsl:for-each>
												</td></tr>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:when>
									<xsl:when test="sc:section/sc:code/@code='51848-0'">
										<tr height="20" class="odd"><td  colspan="6" class="blws">
											<xsl:value-of select="sc:section/sc:entry/sc:observation/sc:value"/>
										</td></tr>
										<tr height="20" class="odd"><td  colspan="6" class="blws">
											评估医生：<xsl:value-of select="sc:section/sc:entry/sc:observation/sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name"/>
										</td></tr>
									</xsl:when>
								</xsl:choose>
								<xsl:for-each select="sc:section/sc:entry/sc:act/sc:entryRelationship/sc:observation">
									<tr height="20" class="odd"><td  colspan="6" class="blws">
									<xsl:value-of select="sc:code/@displayName"/>：<xsl:value-of select="sc:value/@displayName"/>
									</td></tr>
								</xsl:for-each>
							</xsl:for-each> -->
						</table>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>





