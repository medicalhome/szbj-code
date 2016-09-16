<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:sc="urn:hl7-org:v3"  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:Constants="xalan://com.founder.cdr.core.Constants" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="urn:hl7-org:v3 ../coreschemas/CDA.xsd">
	<!-- 
	<xsl:template name="loop">
		<xsl:param name="start">1</xsl:param>
		<xsl:param name="end"/>
		<xsl:param name="content"/>
		
		<xsl:choose>
			<xsl:when test="$content='basicInfo'">
				<xsl:call-template name="basicInfo">
					<xsl:with-param name="start"><xsl:value-of select="$start"/></xsl:with-param>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$content='preDialysis'">
				<xsl:call-template name="preDialysis">
					<xsl:with-param name="start"><xsl:value-of select="$start"/></xsl:with-param>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$content='dialysisFinding'">
				<xsl:call-template name="dialysisFinding">
					<xsl:with-param name="start"><xsl:value-of select="$start"/></xsl:with-param>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$content='dialysisDetail'">
				<xsl:call-template name="dialysisDetail">
					<xsl:with-param name="start"><xsl:value-of select="$start"/></xsl:with-param>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$content='hemodialysisMachine'">
				<xsl:call-template name="hemodialysisMachine">
					<xsl:with-param name="start"><xsl:value-of select="$start"/></xsl:with-param>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$content='usingSubstance'">
				<xsl:call-template name="usingSubstance">
					<xsl:with-param name="start"><xsl:value-of select="$start"/></xsl:with-param>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$content='assessment'">
				<xsl:call-template name="assessment">
					<xsl:with-param name="start"><xsl:value-of select="$start"/></xsl:with-param>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$content='dialysisObservation'">
				<xsl:call-template name="dialysisObservation">
					<xsl:with-param name="start"><xsl:value-of select="$start"/></xsl:with-param>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$content='complicationOfDialysis'">
				<xsl:call-template name="complicationOfDialysis">
					<xsl:with-param name="start"><xsl:value-of select="$start"/></xsl:with-param>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="$content='bloodPressureFinding'">
				<xsl:call-template name="bloodPressureFinding">
					<xsl:with-param name="start"><xsl:value-of select="$start"/></xsl:with-param>
				</xsl:call-template>
			</xsl:when>
		</xsl:choose>
		
		<xsl:if test="$start &lt; $end">
			<xsl:call-template name="loop">
				<xsl:with-param name="start"><xsl:value-of select="$start+1"/></xsl:with-param>
				<xsl:with-param name="end"><xsl:value-of select="$end"/></xsl:with-param>
				<xsl:with-param name="content"><xsl:value-of select="$content"/></xsl:with-param>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>
	
	
	<xsl:template name="basicInfo">
		<xsl:param name="start"/>
		<tr>
			<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='310388008']/sc:entryRelationship/sc:organizer/sc:component[position()-3 &lt;= 4*$start and position()-3 &gt; 4*($start - 1)]">
				<td class="doclabel2"><xsl:value-of select="sc:observation/sc:code/@displayName"/>：</td>
				<td class="docdataitem2"><xsl:call-template name="contentDetail"/></td>
				<xsl:if test="position()=last() and position() &lt; 4">
					<xsl:call-template name="printSpace1">
						<xsl:with-param name="end"><xsl:value-of select="4-position()"/></xsl:with-param>
					</xsl:call-template>
				</xsl:if>
			</xsl:for-each>
		</tr>
	</xsl:template>
	
	<xsl:template name="preDialysis">
		<xsl:param name="start"/>
		<tr>
			<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='115499008']/sc:entryRelationship/sc:organizer/sc:component[not(sc:observation/sc:entryRelationship) and position() &lt;= 4*$start and position() &gt; 4*($start - 1)]">
				<td class="doclabel2"><xsl:value-of select="sc:observation/sc:code/@displayName"/>：</td>
				<td class="docdataitem2"><xsl:call-template name="contentDetail"/></td>
				<xsl:if test="position()=last() and position() &lt; 4">
					<xsl:call-template name="printSpace1">
						<xsl:with-param name="end"><xsl:value-of select="4-position()"/></xsl:with-param>
					</xsl:call-template>
				</xsl:if>
			</xsl:for-each>
		</tr>
	</xsl:template>
	
	<xsl:template name="dialysisFinding">
		<xsl:param name="start"/>
		<tr>
			<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='250171008']/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:code/@displayName!='其他' and position() &lt;= 8*$start and position() &gt; 8*($start - 1)]">
				<td class="lablabel" width="6%"><xsl:value-of select="sc:observation/sc:code/@displayName"/>：</td>
				<td class="labdataitem" width="6.5%"><xsl:call-template name="contentDetail"/></td>
				<xsl:if test="position()=last() and position() &lt; 8">
					<xsl:call-template name="printSpace2">
						<xsl:with-param name="end"><xsl:value-of select="8-position()"/></xsl:with-param>
					</xsl:call-template>
				</xsl:if>
			</xsl:for-each>
		</tr>
	</xsl:template>
	
	<xsl:template name="dialysisDetail">
		<xsl:param name="start"/>
		<tr>
			<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='364671000']/sc:entryRelationship/sc:organizer/sc:component[position() &lt;= 4*$start and position() &gt; 4*($start - 1)]">
				<td class="doclabel2"><xsl:value-of select="sc:observation/sc:code/@displayName"/>：</td>
				<td class="docdataitem2"><xsl:call-template name="contentDetail"/></td>
				<xsl:if test="position()=last() and position() &lt; 4">
					<xsl:call-template name="printSpace1">
						<xsl:with-param name="end"><xsl:value-of select="4-position()"/></xsl:with-param>
					</xsl:call-template>
				</xsl:if>
			</xsl:for-each>
		</tr>
	</xsl:template>
	
	<xsl:template name="hemodialysisMachine">
		<xsl:param name="start"/>
		<tr>
			<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='36965003']/sc:entryRelationship/sc:organizer/sc:component[position() &lt;= 4*$start and position() &gt; 4*($start - 1)]">
				<td class="doclabel2"><xsl:value-of select="sc:observation/sc:code/@displayName"/>：</td>
				<td class="docdataitem2"><xsl:call-template name="contentDetail"/></td>
				<xsl:if test="position()=last() and position() &lt; 4">
					<xsl:call-template name="printSpace1">
						<xsl:with-param name="end"><xsl:value-of select="4-position()"/></xsl:with-param>
					</xsl:call-template>
				</xsl:if>
			</xsl:for-each>
		</tr>
	</xsl:template>
	
	<xsl:template name="usingSubstance">
		<xsl:param name="start"/>
		<tr>
			<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='424361007']/sc:entryRelationship/sc:organizer/sc:component[position() &lt;= 4*$start and position() &gt; 4*($start - 1)]">
				<td class="doclabel2"><xsl:value-of select="sc:observation/sc:code/@displayName"/>：</td>
				<td class="docdataitem2"><xsl:call-template name="contentDetail"/></td>
				<xsl:if test="position()=last() and position() &lt; 4">
					<xsl:call-template name="printSpace1">
						<xsl:with-param name="end"><xsl:value-of select="4-position()"/></xsl:with-param>
					</xsl:call-template>
				</xsl:if>
			</xsl:for-each>
		</tr>
	</xsl:template>
	
	<xsl:template name="assessment">
		<xsl:param name="start"/>
		<tr>
			<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='386053000']/sc:entryRelationship/sc:organizer/sc:component[position() &lt;= 4*$start and position() &gt; 4*($start - 1)]">
				<td class="doclabel2"><xsl:value-of select="sc:observation/sc:code/@displayName"/>：</td>
				<td class="docdataitem2"><xsl:call-template name="contentDetail"/></td>
				<xsl:if test="position()=last() and position() &lt; 4">
					<xsl:call-template name="printSpace1">
						<xsl:with-param name="end"><xsl:value-of select="4-position()"/></xsl:with-param>
					</xsl:call-template>
				</xsl:if>
			</xsl:for-each>
		</tr>
	</xsl:template>
	
	<xsl:template name="dialysisObservation">
		<xsl:param name="start"/>
		<tr>
			<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='251859005']/sc:entryRelationship/sc:organizer/sc:component[position() &lt;= 4*$start and position() &gt; 4*($start - 1)]">
				<td class="doclabel2"><xsl:value-of select="sc:observation/sc:code/@displayName"/>：</td>
				<td class="docdataitem2"><xsl:call-template name="contentDetail"/></td>
				<xsl:if test="position()=last() and position() &lt; 4">
					<xsl:call-template name="printSpace1">
						<xsl:with-param name="end"><xsl:value-of select="4-position()"/></xsl:with-param>
					</xsl:call-template>
				</xsl:if>
			</xsl:for-each>
		</tr>
	</xsl:template>
	
	<xsl:template name="complicationOfDialysis">
		<xsl:param name="start"/>
		<tr>
			<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='19765000']/sc:entryRelationship/sc:organizer/sc:component[not(sc:observation/sc:entryRelationship) and position() &lt;= 4*$start and position() &gt; 4*($start - 1)]">
				<td class="doclabel2"><xsl:value-of select="sc:observation/sc:code/@displayName"/>：</td>
				<td class="docdataitem2"><xsl:call-template name="contentDetail"/></td>
				<xsl:if test="position()=last() and position() &lt; 4">
					<xsl:call-template name="printSpace1">
						<xsl:with-param name="end"><xsl:value-of select="4-position()"/></xsl:with-param>
					</xsl:call-template>
				</xsl:if>
			</xsl:for-each>
		</tr>
	</xsl:template>
	
	<xsl:template name="bloodPressureFinding">
		<xsl:param name="start"/>
		<tr>
			<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='392570002']/sc:entryRelationship/sc:organizer/sc:component[position() &lt;= 4*$start and position() &gt; 4*($start - 1)]">
				<td class="doclabel2"><xsl:value-of select="sc:observation/sc:code/@displayName"/>：</td>
				<td class="docdataitem2"><xsl:call-template name="contentDetail"/></td>
				<xsl:if test="position()=last() and position() &lt; 4">
					<xsl:call-template name="printSpace1">
						<xsl:with-param name="end"><xsl:value-of select="4-position()"/></xsl:with-param>
					</xsl:call-template>
				</xsl:if>
			</xsl:for-each>
		</tr>
	</xsl:template>
	
	<xsl:template name="contentDetail">
		<xsl:value-of select="sc:observation/sc:value"/>
		<xsl:choose>
			<xsl:when test="sc:observation/sc:value/@value='true'">是</xsl:when>
			<xsl:when test="sc:observation/sc:value/@value='false'">否</xsl:when>
			<xsl:otherwise><xsl:value-of select="sc:observation/sc:value/@value"/></xsl:otherwise>
		</xsl:choose>
		<xsl:if test="sc:observation/sc:value/@value"><xsl:value-of select="sc:observation/sc:value/@unit"/></xsl:if>
		<xsl:value-of select="sc:observation/sc:value/@displayName"/>
		<xsl:if test="not(sc:observation/sc:value/@displayName) and sc:observation/sc:value!=''"><xsl:value-of select="sc:observation/sc:value/@code"/></xsl:if>
		<xsl:if test="sc:observation/sc:text">(<xsl:value-of select="sc:observation/sc:text"/>)</xsl:if>
	</xsl:template>
	
	<xsl:template name="printSpace1">
		<xsl:param name="start">1</xsl:param>
		<xsl:param name="end"/>
		<td class="doclabel2"></td><td class="docdataitem2"></td>
		<xsl:if test="$start &lt; $end">
			<xsl:call-template name="printSpace1">
				<xsl:with-param name="start"><xsl:value-of select="$start+1"/></xsl:with-param>
				<xsl:with-param name="end"><xsl:value-of select="$end"/></xsl:with-param>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>
	
	<xsl:template name="printSpace2">
		<xsl:param name="start">1</xsl:param>
		<xsl:param name="end"/>
		<td class="lablabel" width="6%"></td><td class="labdataitem" width="6.5%"></td>
		<xsl:if test="$start &lt; $end">
			<xsl:call-template name="printSpace2">
				<xsl:with-param name="start"><xsl:value-of select="$start+1"/></xsl:with-param>
				<xsl:with-param name="end"><xsl:value-of select="$end"/></xsl:with-param>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>-->
	
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
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="8" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"><h3><xsl:value-of select="sc:title"/></h3></td>
							</tr>
							<tr>
								<td class="doclabel2">姓名：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/></td>
								
								<td class="doclabel2">性别：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/></td>
								
								<td class="doclabel2">年龄：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:age/@value"/></td>
								
								<td class="doclabel2">患者类型：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:code/@displayName"/></td>
							
							</tr>
							<tr>
								<td class="doclabel2">病区：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								
								<td class="doclabel2">床位号：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								
								<td class="doclabel2">病人科室：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
																
								<td class="doclabel2">
									<xsl:if test="Constants:getTrueOrgCode() = Constants:getDisabledOrgCode()">	
										管理机构名称：
									</xsl:if>
								</td>											
								<td class="docdataitem2">
									<xsl:if test="Constants:getTrueOrgCode() = Constants:getDisabledOrgCode()">
										<xsl:value-of select="sc:custodian/sc:assignedCustodian/sc:representedCustodianOrganization/sc:name"/>
									</xsl:if>
								</td>																	
							</tr>
							<tr>
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='34076-0']/sc:section/sc:entry">
										
										<td class="doclabel2"><xsl:value-of select="sc:observation/sc:code/@displayName"/>：</td>
										<td class="docdataitem2">
											<xsl:value-of select="sc:observation/sc:value"/>
										</td>							
								</xsl:for-each>
								<td class="doclabel2"></td>
								<td class="docdataitem2"></td>
							</tr>
							
							
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='29749-9']/sc:section">
								<tr>
									<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;border-bottom: solid 1px #B3C4D4; font-weight: bold;">
										<xsl:value-of select="sc:title"/>
									</td>
								</tr>

								<tr>
									<xsl:for-each select="sc:entry/sc:organizer/sc:component">
										
										<td class="doclabel2">
											<xsl:if test="sc:observation/sc:code/@displayName='透析中置换液总量'">
												血液滤过或血液透析滤过
											</xsl:if>
											<xsl:value-of select="sc:observation/sc:code/@displayName"/>
										</td>
										
										<td class="docdataitem2">
											<xsl:if test="sc:observation/sc:value/@xsi:type='PQ'">
												<xsl:value-of select="sc:observation/sc:value/@value"/><xsl:value-of select="sc:observation/sc:value/@unit"/>
											</xsl:if>
											<xsl:if test="sc:observation/sc:value/@xsi:type='ST'">
												<xsl:if test="sc:observation/sc:code/@displayName='治疗日期'">
													<xsl:choose>
														<xsl:when test="string-length(sc:observation/sc:value)>8">
															<xsl:value-of select="substring(sc:observation/sc:value,1,4)"/>年<xsl:value-of select="substring(sc:observation/sc:value,5,2)"/>月<xsl:value-of select="substring(sc:observation/sc:value,7,2)"/>日<xsl:value-of select="substring(sc:observation/sc:value,9,2)"/>时<xsl:value-of select="substring(sc:observation/sc:value,11,2)"/>分
														</xsl:when>
														<xsl:when test="string-length(sc:observation/sc:value)>0">
															<xsl:value-of select="substring(sc:observation/sc:value,1,4)"/>年<xsl:value-of select="substring(sc:observation/sc:value,5,2)"/>月<xsl:value-of select="substring(sc:observation/sc:value,7,2)"/>日
														</xsl:when>
													</xsl:choose>
												</xsl:if>
												<xsl:if test="sc:observation/sc:code/@displayName!='治疗日期'">
													<xsl:value-of select="sc:observation/sc:value"/>
												</xsl:if>
												
											</xsl:if>
										</td>										
										<xsl:if test="position() mod 4=0">
											<tr>
											</tr>
										</xsl:if>
										<xsl:if test="position()=last() and last() mod 4=1">
					                        <td class="docdataitem2" colspan="6"></td>				                            				                            
										</xsl:if>
										<xsl:if test="position()=last() and last() mod 4=2">
											<td class="docdataitem2" colspan="4"></td>					                            				                            
										</xsl:if>
										<xsl:if test="position()=last() and last() mod 4=3">
											<td class="docdataitem2" colspan="2"></td>					                            				                            
										</xsl:if>
									
									</xsl:for-each>
								</tr>
							</xsl:for-each>
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;border-bottom: solid 1px #B3C4D4; font-weight: bold;">
									参与者信息
								</td>
							</tr>
							
						    <tr>
						    	<xsl:for-each select="sc:authenticator">
						     		<td class="doclabel2"><xsl:value-of select="sc:assignedEntity/sc:code/@displayName"/></td>
										
									<td class="docdataitem2">						
										<xsl:value-of select="sc:assignedEntity/sc:assignedPerson/sc:name"/>
									</td>										
									<xsl:if test="position() mod 4=0"><tr></tr></xsl:if>
									<xsl:if test="position()=last() and last() mod 4=1">
					                        <td class="docdataitem2" colspan="6"></td>				                            				                            
										</xsl:if>
										<xsl:if test="position()=last() and last() mod 4=2">
											<td class="docdataitem2" colspan="4"></td>					                            				                            
										</xsl:if>
										<xsl:if test="position()=last() and last() mod 4=3">
											<td class="docdataitem2"  colspan="2"></td>					                            				                            
										</xsl:if>
									
									</xsl:for-each>
								</tr>
							
						</table>
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="11" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;border-bottom: solid 1px #B3C4D4; font-weight: bold;">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@displayName='透析监测记录']/sc:section/sc:title"/>
								</td>
							</tr>
							<tr>
								<td class="doclabelline3" width="9%">时间</td>
								<td class="doclabelline3" width="9%">静脉压</td>
								<td class="doclabelline3" width="9%">跨膜压</td>
								<td class="doclabelline3" width="9%">电导度</td>
								<td class="doclabelline3" width="9%">血流量</td>
								<td class="doclabelline3" width="9%">透析液流量</td>
								<td class="doclabelline3" width="9%">超滤率</td>									
								<td class="doclabelline3" width="9%">透析液温度</td>
								<td class="doclabelline3" width="9%">血压</td>
								<td class="doclabelline3" width="9%">心率</td>
								<td class="doclabelline3" width="9%">呼吸</td>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@displayName='透析监测记录']/sc:section/sc:entry">
								<tr>
									<xsl:for-each select="sc:organizer/sc:component">										
											<td class="docdataitemline3" width="9%"><xsl:value-of select="sc:observation/sc:value"/></td>										
									</xsl:for-each>	
								</tr>							
							</xsl:for-each>
							
						</table>
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="10" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;border-bottom: solid 1px #B3C4D4; font-weight: bold;">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='19790-5']/sc:section/sc:title"/>
								</td>
							</tr>
							<tr>
								<td class="doclabelline3" width="9%">执行状态</td>
								<td class="doclabelline3" width="9%">执行人</td>
								<td class="doclabelline3" width="9%">给药时间</td>
								<td class="doclabelline3" width="9%">核查人</td>
								<td class="doclabelline3" width="9%">核查时间</td>
								<td class="doclabelline3" width="14%">药品名称</td>
								<td class="doclabelline3" width="12%">用药途径</td>									
								<td class="doclabelline3" width="9%">单次剂量</td>
								<td class="doclabelline3" width="9%">剂量单位</td>
								<td class="doclabelline3" width="9%">类型</td>
				
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='19790-5']/sc:section/sc:entry">
								<tr>
									<td class="docdataitemline3" width="9%">
										<xsl:value-of select="sc:substanceAdministration/sc:entryRelationship/sc:observation/sc:value"/>
									</td>
									<td class="docdataitemline3" width="9%">
										<xsl:value-of select="sc:substanceAdministration/sc:performer/sc:assignedEntity/sc:assignedPerson/sc:name"/>
									</td>
									<td class="docdataitemline3" width="9%">
										<xsl:if test="sc:substanceAdministration/sc:effectiveTime/@value!=''">
											<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/@value,1,2)"/>:<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/@value,3,2)"/>:<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/@value,5,2)"/>
										</xsl:if>
									</td>
									<td class="docdataitemline3" width="9%">
										<xsl:value-of select="sc:substanceAdministration/sc:participant/sc:participantRole/sc:playingEntity/sc:name"/>
									</td>
									<td class="docdataitemline3" width="9%">
										<xsl:if test="sc:substanceAdministration/sc:participant/sc:time/@value!=''">
											<xsl:value-of select="substring(sc:substanceAdministration/sc:participant/sc:time/@value,1,2)"/>:<xsl:value-of select="substring(sc:substanceAdministration/sc:participant/sc:time/@value,3,2)"/>:<xsl:value-of select="substring(sc:substanceAdministration/sc:participant/sc:time/@value,5,2)"/>
										</xsl:if>
									</td>
									<td class="docdataitemline3" width="14%">
										<xsl:value-of select="sc:substanceAdministration/sc:consumable/sc:manufacturedProduct/sc:manufacturedLabeledDrug/sc:name"/>
									</td>
									<td class="docdataitemline3" width="12%">
										<xsl:value-of select="sc:substanceAdministration/sc:routeCode/@displayName"/>
									</td>
									<td class="docdataitemline3" width="9%">
										<xsl:value-of select="sc:substanceAdministration/sc:doseQuantity/@value"/>
									</td>
									<td class="docdataitemline3" width="9%">
										<xsl:value-of select="sc:substanceAdministration/sc:doseQuantity/@unit"/>
									</td>
									<td class="docdataitemline3" width="9%">
										<xsl:value-of select="sc:substanceAdministration/sc:code/@displayName"/>
									</td>
								</tr>							
							</xsl:for-each>							
						</table>
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;border-bottom: solid 1px #B3C4D4; font-weight: bold;">
									本次小结
								</td>
							</tr>
							<tr>
								<td class="docdataitemline4" style="padding-left: 20px;">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='55110-1']/sc:section/sc:text"/>
								</td>
							</tr>
							
						</table>
								
							
							
							
							
							
							<!-- 
							<xsl:call-template name="loop">
								<xsl:with-param name="end"><xsl:value-of select="ceiling(count(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='310388008']/sc:entryRelationship/sc:organizer/sc:component[position() &gt;= 4]) div 4)"/></xsl:with-param>
								<xsl:with-param name="content">basicInfo</xsl:with-param>
							</xsl:call-template>
							-->
						
							
					
						<!-- 
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;">
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">透前信息</td>
							</tr>	
							<xsl:call-template name="loop">
								<xsl:with-param name="end"><xsl:value-of select="ceiling(count(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='115499008']/sc:entryRelationship/sc:organizer/sc:component[not(sc:observation/sc:entryRelationship)]) div 4)"/></xsl:with-param>
								<xsl:with-param name="content">preDialysis</xsl:with-param>
							</xsl:call-template>
							
							
							<tr>
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='115499008']/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:entryRelationship]">
									<td class="doclabel2"><xsl:value-of select="sc:observation/sc:code/@displayName"/>：</td>
									<td colspan="7" class="docdataitem2">
										<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:value/@value]">
											&#160;&#160;<xsl:if test="sc:observation/sc:value/@value='true'">■</xsl:if><xsl:if test="sc:observation/sc:value/@value='false'">□</xsl:if><xsl:value-of select="sc:observation/sc:code/@displayName"/>&#160;
										</xsl:for-each>
									</td>
								</xsl:for-each>
							</tr>
							
							<tr>
								<td class="doclabel2"></td>
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='115499008']/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:entryRelationship]/sc:observation/sc:entryRelationship/sc:organizer/sc:component[not(sc:observation/sc:value/@value)]">
									<td colspan="7" class="docdataitem2">&#160;&#160;<xsl:value-of select="sc:observation/sc:code/@displayName"/>：<xsl:value-of select="sc:observation/sc:value"/></td>
								</xsl:for-each>
							</tr>
						</table>
						
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;">
							<tr>
								<td colspan="16" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">上一次透析后信息</td>
							</tr>	
							<xsl:call-template name="loop">
								<xsl:with-param name="end"><xsl:value-of select="ceiling(count(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='250171008']/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:code/@displayName!='其他']) div 8)"/></xsl:with-param>
								<xsl:with-param name="content">dialysisFinding</xsl:with-param>
							</xsl:call-template>
							<tr>
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='250171008']/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:code/@displayName='其他']">
									<td class="lablabel" width="6%"><xsl:value-of select="sc:observation/sc:code/@displayName"/>：</td>
									<td colspan="15" class="labdataitem"><xsl:value-of select="sc:observation/sc:value"/></td>
								</xsl:for-each>
							</tr>
						</table>
						
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;">
							<tr>
								<td class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">透析检测记录1</td>
							</tr>
						</table>
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;">
							<tr class="tabletitle">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='364672007']/sc:entryRelationship/sc:organizer/sc:component[position()=1]/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
									<td align="center"><xsl:value-of select="sc:observation/sc:code/@displayName"/></td>
								</xsl:for-each>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='364672007']/sc:entryRelationship/sc:organizer/sc:component">
							   <tr>
							       <xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component">
									   <td class="labdataitem" style="text-align:center;"><xsl:value-of select="sc:observation/sc:value/@value"/><xsl:value-of select="sc:observation/sc:value"/></td>
								   </xsl:for-each>
							   </tr>
							</xsl:for-each>
						</table>
						
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;">
							<tr>
								<td class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">医生用药医嘱</td>
							</tr>
						</table>
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;">
							<tr class="tabletitle">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='182833002']/sc:entryRelationship/sc:organizer/sc:component[position()=1]/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
									<td align="center"><xsl:value-of select="sc:observation/sc:code/@displayName"/></td>
								</xsl:for-each>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='182833002']/sc:entryRelationship/sc:organizer/sc:component">
							   <tr>
							       <xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component">
									   <td class="labdataitem" style="text-align:center;"><xsl:value-of select="sc:observation/sc:value/@value"/><xsl:value-of select="sc:observation/sc:value"/></td>
								   </xsl:for-each>
							   </tr>
							</xsl:for-each>
						</table>
						
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;">
							<tr>
								<td class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">透析检测记录2</td>
							</tr>
						</table>
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;">
							<tr class="tabletitle">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='395170001']/sc:entryRelationship/sc:organizer/sc:component[position()=1]/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
									<td align="center"><xsl:value-of select="sc:observation/sc:code/@displayName"/></td>
								</xsl:for-each>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='395170001']/sc:entryRelationship/sc:organizer/sc:component">
							   <tr>
							       <xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component">
									   <td class="labdataitem" style="text-align:center;"><xsl:value-of select="sc:observation/sc:value/@value"/><xsl:value-of select="sc:observation/sc:value"/></td>
								   </xsl:for-each>
							   </tr>
							</xsl:for-each>
						</table>
						
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;">
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">透析中的详细情况及处理，本次透析总结或拟进行的检查，治疗调整信息</td>
							</tr>	
							<xsl:call-template name="loop">
								<xsl:with-param name="end"><xsl:value-of select="ceiling(count(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='364671000']/sc:entryRelationship/sc:organizer/sc:component) div 4)"/></xsl:with-param>
								<xsl:with-param name="content">dialysisDetail</xsl:with-param>
							</xsl:call-template>
						</table>
						
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;">
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">设备情况信息</td>
							</tr>	
							<xsl:call-template name="loop">
								<xsl:with-param name="end"><xsl:value-of select="ceiling(count(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='36965003']/sc:entryRelationship/sc:organizer/sc:component) div 4)"/></xsl:with-param>
								<xsl:with-param name="content">hemodialysisMachine</xsl:with-param>
							</xsl:call-template>
						</table>
						
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;">
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">实际使用项目</td>
							</tr>	
							<xsl:call-template name="loop">
								<xsl:with-param name="end"><xsl:value-of select="ceiling(count(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='424361007']/sc:entryRelationship/sc:organizer/sc:component) div 4)"/></xsl:with-param>
								<xsl:with-param name="content">usingSubstance</xsl:with-param>
							</xsl:call-template>
						</table>
						
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;">
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">透前评估</td>
							</tr>	
							<xsl:call-template name="loop">
								<xsl:with-param name="end"><xsl:value-of select="ceiling(count(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='386053000']/sc:entryRelationship/sc:organizer/sc:component) div 4)"/></xsl:with-param>
								<xsl:with-param name="content">assessment</xsl:with-param>
							</xsl:call-template>
						</table>
						
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;">
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">透析间期信息</td>
							</tr>	
							<xsl:call-template name="loop">
								<xsl:with-param name="end"><xsl:value-of select="ceiling(count(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='251859005']/sc:entryRelationship/sc:organizer/sc:component) div 4)"/></xsl:with-param>
								<xsl:with-param name="content">dialysisObservation</xsl:with-param>
							</xsl:call-template>
						</table>
						
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;">
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">并发症信息</td>
							</tr>	
							<xsl:call-template name="loop">
								<xsl:with-param name="end"><xsl:value-of select="ceiling(count(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='19765000']/sc:entryRelationship/sc:organizer/sc:component[not(sc:observation/sc:entryRelationship)]) div 4)"/></xsl:with-param>
								<xsl:with-param name="content">complicationOfDialysis</xsl:with-param>
							</xsl:call-template>
							<tr>
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='19765000']/sc:entryRelationship/sc:organizer/sc:component[sc:observation/sc:entryRelationship and sc:observation/sc:value/@value='true']">
									<td class="doclabel2"><xsl:value-of select="sc:observation/sc:code/@displayName"/>：</td>
									<td class="docdataitem2" colspan="7">
										<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component">
											<xsl:value-of select="sc:observation/sc:code/@displayName"/>：<xsl:value-of select="sc:observation/sc:value"/><xsl:value-of select="sc:observation/sc:value/@value"/><xsl:value-of select="sc:observation/sc:value/@unit"/><xsl:value-of select="sc:observation/sc:value/@displayName"/>
										</xsl:for-each>
									</td>
								</xsl:for-each>
							</tr>
						</table>
						
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;">
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">血压情况信息</td>
							</tr>
							<xsl:call-template name="loop">
								<xsl:with-param name="end"><xsl:value-of select="ceiling(count(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='392570002']/sc:entryRelationship/sc:organizer/sc:component) div 4)"/></xsl:with-param>
								<xsl:with-param name="content">bloodPressureFinding</xsl:with-param>
							</xsl:call-template>
						</table>
						
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;">
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">负责人签名</td>
							</tr>
							<tr>
								<xsl:for-each select="sc:participant">
									<td class="doclabel2"><xsl:value-of select="sc:functionCode/@displayName"/>：</td>
									<td class="docdataitem2"><xsl:value-of select="sc:associatedEntity/sc:associatedPerson/sc:name"/></td>
								</xsl:for-each>
							</tr>
							<tr>
								<td class="doclabel2">主管护士：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name"/></td>
								
								<td class="doclabel2">查对护士：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:authenticator/sc:assignedEntity/sc:assignedPerson/sc:name"/></td>
							</tr>
						</table>-->
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>





