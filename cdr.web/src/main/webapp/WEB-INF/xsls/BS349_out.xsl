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





