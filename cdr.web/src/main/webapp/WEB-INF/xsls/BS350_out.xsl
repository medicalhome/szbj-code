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
						return '<xsl:value-of select="sc:id/@extension"/>';
				     }
			    </script>
			    <script type="text/javascript" src="../scripts/doc/docDetail.js"></script>
			</head>
			<body>
				<div id="mainContent">
					<div name="selectTabs">
						<div class="responsability"><xsl:value-of select="Constants:getResponsabilityClaimingContent()"/></div>
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:component">
								<tr>
									<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">
										<xsl:value-of select="position()"/>.<xsl:value-of select="sc:section/sc:title"/>：
										<xsl:choose>
											<xsl:when test="sc:section/sc:code/@code='87059' or sc:section/sc:code/@code='86970' or sc:section/sc:code/@code='8600'">
												
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
												
											</xsl:when>
											<xsl:when test="sc:section/sc:code/@code='87070-0'">
											
												<xsl:for-each select="sc:section/sc:entry[sc:observation/sc:value/@value='true']">
													<tr height="28"><td colspan="6" class="blws">
													（<xsl:value-of select="position()"/>）<xsl:value-of select="sc:observation/sc:code/@displayName"/>：
													<xsl:if test="sc:observation/sc:entryRelationship">
													
														<xsl:choose>
															<xsl:when test="sc:observation/sc:code/@code='87071'">
																<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:value/@value='true']">
																	<tr><td colspan="6" style="background-color: #FFFFFF;text-align: left;padding-left:40px;">
																	<xsl:value-of select="sc:observation/sc:code/@displayName"/>
																	<xsl:if test="sc:observation/sc:text">（<xsl:value-of select="sc:observation/sc:text"/>）</xsl:if>
																	
																	<xsl:if test="sc:observation/sc:entryRelationship">
																		：<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:value/@value='true']">
																			<xsl:value-of select="sc:observation/sc:code/@displayName"/>
																			<xsl:if test="sc:observation/sc:text">（<xsl:value-of select="sc:observation/sc:text"/>）</xsl:if>
																			<xsl:if test="position()!=last() and string-length(sc:observation/sc:code/@displayName)>0">、</xsl:if>
																		</xsl:for-each>
																	</xsl:if>
																	</td></tr>
																</xsl:for-each>
															</xsl:when>
															<xsl:otherwise>
																<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:value/@value='true']">
															
																	<xsl:value-of select="sc:observation/sc:code/@displayName"/>
																	<xsl:if test="sc:observation/sc:text">（<xsl:value-of select="sc:observation/sc:text"/>）</xsl:if>
																	<xsl:if test="position()!=last() and string-length(sc:observation/sc:code/@displayName)>0">、</xsl:if>
																	
																	<xsl:if test="sc:observation/sc:entryRelationship">
																		<tr><td colspan="6" style="background-color: #FFFFFF;text-align: left;padding-left:40px;">
																		<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:value/@value='true']">
																			<xsl:value-of select="sc:observation/sc:code/@displayName"/>
																			<xsl:if test="sc:observation/sc:text">（<xsl:value-of select="sc:observation/sc:text"/>）</xsl:if>
																			<xsl:if test="position()!=last() and string-length(sc:observation/sc:code/@displayName)>0">、</xsl:if>
																		</xsl:for-each>
																		</td></tr>
																	</xsl:if>
																</xsl:for-each>
															</xsl:otherwise>
														</xsl:choose>
													
													</xsl:if>
													</td></tr>
												</xsl:for-each>
											
											</xsl:when>
											<xsl:when test="sc:section/sc:code/@code='87058'or sc:section/sc:code/@code='265'">
												<xsl:for-each select="sc:section/sc:entry[sc:observation/sc:value/@value='true']">
													<tr>
														<td colspan="6" class="blws"><xsl:value-of select="sc:observation/sc:text"/></td>
													</tr>
												</xsl:for-each>
											</xsl:when>
											<xsl:otherwise>
											
												<xsl:for-each select="sc:section/sc:entry[sc:observation/sc:value/@value='true']">
													<xsl:value-of select="sc:observation/sc:code/@displayName"/>
													<xsl:if test="sc:observation/sc:text">（<xsl:value-of select="sc:observation/sc:text"/>）</xsl:if>
													<xsl:if test="position()!=last() and string-length(sc:observation/sc:code/@displayName)>0">、</xsl:if>
												</xsl:for-each>
												<xsl:if test="sc:section/sc:entry[sc:observation/sc:value/@value='true']/sc:observation/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:value/@value='true']">
													<xsl:choose>
														<xsl:when test="sc:section/sc:code/@code='87045'">
															<xsl:for-each select="sc:section/sc:entry[sc:observation/sc:value/@value='true']/sc:observation/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:value/@value='true']">
																<tr height="28" class="blws"><td colspan="6" class="blws" >
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
														</xsl:when>
														<xsl:otherwise>
															<tr height="28"><td colspan="6" class="blws">
															<xsl:for-each select="sc:section/sc:entry[sc:observation/sc:value/@value='true']/sc:observation/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:value/@value='true']">
																
																<xsl:value-of select="sc:observation/sc:code/@displayName"/>
																<xsl:if test="sc:observation/sc:text">
																	<xsl:choose>
																		<xsl:when test="sc:observation/sc:code/@code='87083-2-3'">
																			（跌倒风险评分：<xsl:value-of select="sc:observation/sc:text"/>）
																		</xsl:when>
																		<xsl:otherwise>
																			（<xsl:value-of select="sc:observation/sc:text"/>）
																		</xsl:otherwise>
																	</xsl:choose>
																</xsl:if>
																<xsl:if test="sc:observation/sc:entryRelationship">：
																	<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:value/@value='true']">
																		<xsl:value-of select="sc:observation/sc:code/@displayName"/>
																		<xsl:if test="sc:observation/sc:text">（<xsl:value-of select="sc:observation/sc:text"/>）</xsl:if>
																		<xsl:if test="position()!=last() and string-length(sc:observation/sc:code/@displayName)>0">、</xsl:if>
																	</xsl:for-each>
																</xsl:if>
																<xsl:if test="position()!=last() and string-length(sc:observation/sc:code/@displayName)>0">、</xsl:if>
															</xsl:for-each>
															</td></tr>
														</xsl:otherwise>
													</xsl:choose>
												</xsl:if>
												
											</xsl:otherwise>
										</xsl:choose>							
									</td>
								</tr>
							</xsl:for-each>
							
						</table>
						<table align="center" width="100%"  border="0" cellspacing="1" cellpadding="2" style="border-collapse:collapse;">
							<tr>
								<td class="blws">评估要求：</td>
							</tr>
							<tr>
								<td class="blws">1、入院当日、术前一日、手术当日及术后3日每日评估10项公共另加该病种专科评估。</td>
							</tr>
							<tr>
								<td class="blws">2、一级护理患者每三日，二、三级至少每七日评估10项公共另加该病种专科评估。</td>
							</tr>
						</table>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>





