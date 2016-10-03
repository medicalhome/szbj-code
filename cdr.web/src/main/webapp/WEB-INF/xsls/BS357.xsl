<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:sc="urn:hl7-org:v3"  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:Constants="xalan://com.yly.cdr.core.Constants" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="urn:hl7-org:v3 ../coreschemas/CDA.xsd">
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
								<td colspan="8" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"><h3><xsl:value-of select="sc:title"/></h3></td>
							</tr>
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">基本信息</td>
							</tr>
							<tr>
								<td class="doclabel2">姓名：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/></td>
								
								<td class="doclabel2">性别：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/></td>
								
								<td class="doclabel">年龄：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:age/@value"/></td>
							
								
								<td class="doclabel2">民族：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:ethnicGroupCode/@displayName"/></td>
							</tr>
							<tr>
								
								<td class="doclabel2">婚姻：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:maritalStatusCode/@displayName"/></td>
								
								<td class="doclabel2">职业：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:occupation/sc:occupationCode/@displayName"/></td>
			
								<td class="doclabel2">入院日期：</td>
								<td class="docdataitem2">
									<xsl:choose>
										<xsl:when test="string-length(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value)>8">
											<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,7,2)"/>日<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,9,2)"/>时<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,11,2)"/>分
										</xsl:when>
										<xsl:when test="string-length(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value)>0">
											<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,7,2)"/>日
										</xsl:when>
									</xsl:choose>
								</td>
							
								<td class="doclabel2">住院号：</td>
								<td class="docdataitem">
								<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
									<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
											<xsl:choose>
												<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
												<xsl:value-of select="@extension"/>
												</xsl:when>
											</xsl:choose>		
									</xsl:for-each></xsl:if>
								</td>
								
							</tr>
							<tr>
								
								<td class="doclabel2">科室：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								
								<td class="doclabel">床号：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								
								<td class="doclabel2">孕产妇信息：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='34076-0']/sc:entry/sc:observation/sc:value"/></td>
								
								<td class="doclabel2">记录日期：</td>
								<td class="docdataitem2">
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
								<td class="doclabel2">出生地：</td>
								<td class="docdataitem2" colspan="7"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:birthplace/sc:place/sc:addr"/></td>
							</tr>
				
						<!-- 	<tr>
								<td class="doclabel2">主治医师：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:participant[@typeCode='ATND']/sc:associatedEntity/sc:associatedPerson/sc:name"/></td>
							
								
								<td class="doclabel2">营养医师：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:participant[@typeCode='CON']/sc:associatedEntity/sc:associatedPerson/sc:name"/></td>
								
								<xsl:choose>
									<xsl:when test="Constants:getTrueOrgCode() = Constants:getDisabledOrgCode()">
										<td class="doclabel2">管理机构名称：</td>
										<td class="docdataitem2"><xsl:value-of select="sc:custodian/sc:assignedCustodian/sc:representedCustodianOrganization/sc:name"/></td>
									</xsl:when>
									<xsl:otherwise>
										<td class="doclabel2"></td>
										<td class="docdataitem2"></td>
									</xsl:otherwise>
								</xsl:choose>
								
								<td class="doclabel2"></td>
								<td class="docdataitem2"></td>
								
							
							</tr> -->
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">入院诊断</td>
							</tr>
							<tr>
								<td class="doclabel2">主诉：</td>
								<td class="docdataitem2" colspan="7" style="word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='10154-3']/sc:entry/sc:observation/sc:value"/></pre></td>
							</tr>
							<tr>
								<td class="doclabel2">现病史：</td>
								<td class="docdataitem2" colspan="7" style="word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='10164-2']/sc:entry/sc:observation/sc:value"/></pre></td>
							</tr>
							<tr>
								<td class="doclabel2">既往史：</td>
								<td class="docdataitem2" colspan="7" style="word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11348-0']/sc:text"/></pre></td>
							</tr>
							<tr>
								<td class="doclabel2">个人/婚育/月经/家族史：</td>
								<td class="docdataitem2" colspan="7" style="word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='29762-2']/sc:entry/sc:observation/sc:value"/></pre></td>
							</tr>
							
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">体格检查</td>
							</tr>
							<!-- 体格检查 -->	
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='8716-3']/sc:section/sc:entry">
									<xsl:choose>
										<xsl:when test="sc:organizer/sc:code/@displayName='血压'">
											<td class="doclabel2"><xsl:value-of select="sc:organizer/sc:code/@displayName"/>：</td>
											<td class="docdataitem2"><xsl:value-of select="sc:organizer/sc:component/sc:observation[sc:code/@code='DE04.10.174.00']/sc:value/@value"/>/<xsl:value-of select="sc:organizer/sc:component/sc:observation[sc:code/@code='DE04.10.176.00']/sc:value/@value"/><xsl:value-of select="sc:organizer/sc:component/sc:observation[sc:code/@code='DE04.10.174.00']/sc:value/@unit"/></td>					
										</xsl:when>
										<xsl:otherwise>
											<td class="doclabel2"><xsl:value-of select="sc:observation/sc:code/@displayName"/>：</td>
											<td class="docdataitem2"><xsl:value-of select="sc:observation/sc:value/@value"/><xsl:value-of select="sc:observation/sc:value/@unit"/></td>
										</xsl:otherwise>
									</xsl:choose>
									<xsl:if test="position() mod 4=0">
										<tr></tr>
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
							<!-- 实验室及器械检查 -->
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">实验室及器械检查</td>
							</tr>
							<td class="docdataitem2" colspan="8">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@displayName='辅助检查']/sc:section/sc:entry/sc:observation/sc:value"/>
							</td>
							<!-- 临床疾病初步诊断 -->
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">临床疾病初步诊断</td>
							</tr>
							<td class="docdataitem2" colspan="8">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='11450-4']/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:observation/sc:value/@displayName"/>
							</td>
							<!-- 营养筛查 -->
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='39200-1']/sc:section/sc:entry">
							<xsl:if test="position()=1">
								<tr>
									<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">首次营养风险筛查</td>
								</tr>
							</xsl:if>
								<xsl:if test="position()=2">
								<tr>
									<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">营养筛查</td>
								</tr>
							</xsl:if>
							<tr>
								<td class="doclabel2">营养筛查时间：</td>
								<td class="docdataitem2">
										<xsl:choose>
											<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='39200-1']/sc:section/sc:entry/sc:observation/sc:author/sc:time/@value)>8">
												<xsl:value-of select="substring(sc:observation/sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:observation/sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:observation/sc:author/sc:time/@value,7,2)"/>日<xsl:value-of select="substring(sc:observation/sc:author/sc:time/@value,9,2)"/>时<xsl:value-of select="substring(sc:observation/sc:author/sc:time/@value,11,2)"/>分
											</xsl:when>
											<xsl:when test="string-length(sc:observation/sc:author/sc:time/@value)>0">
												<xsl:value-of select="substring(sc:observation/sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:observation/sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:observation/sc:author/sc:time/@value,7,2)"/>日
											</xsl:when>
										</xsl:choose>
								</td>
								<td class="doclabel2">营养筛查结果：</td>
								<td class="docdataitem2" colspan="5">
									<xsl:value-of select="sc:observation/sc:value"/>
								</td>
							</tr>
							</xsl:for-each>
							<!-- 病程记录  -->							
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='72225-6']/sc:section/sc:entry">
							<xsl:if test="position()=1">
								<tr>
									<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">首次病程记录</td>
								</tr>
							</xsl:if>
								<xsl:if test="position()=2">
								<tr>
									<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">病程记录</td>
								</tr>
							</xsl:if>
							<tr>	
								<td class="doclabel2">记录时间：</td>
								<td class="docdataitem2">
									
									<xsl:choose>
										<xsl:when test="string-length(sc:observation/sc:author/sc:time/@value)>8">
											<xsl:value-of select="substring(sc:observation/sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:observation/sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:observation/sc:author/sc:time/@value,7,2)"/>日<xsl:value-of select="substring(sc:observation/sc:author/sc:time/@value,9,2)"/>时<xsl:value-of select="substring(sc:observation/sc:author/sc:time/@value,11,2)"/>分
										</xsl:when>
										<xsl:when test="string-length(sc:observation/sc:author/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:observation/sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:observation/sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:observation/sc:author/sc:time/@value,7,2)"/>日
										</xsl:when>
									</xsl:choose>
								</td>
								<td class="doclabel2">记录人：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:observation/sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name"/></td>
								<td class="doclabel2">上级医生：</td>
								<td class="docdataitem2" colspan="3"><xsl:value-of select="sc:observation/sc:participant/sc:participantRole/sc:playingEntity/sc:name"/></td>
								
							</tr>
							<tr><td class="docdataitem2" colspan="8"><xsl:value-of select="sc:observation/sc:value"/></td></tr>		
							</xsl:for-each>
							<!--出院小结-->
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">出院小结</td>
							</tr>
							<tr>	
								<td class="doclabel2">记录时间：</td>
								<td class="docdataitem2">
									
									<xsl:choose>
										<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='28574-2']/sc:section/sc:author/sc:time/@value)>8">
											<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='28574-2']/sc:section/sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='28574-2']/sc:section/sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='28574-2']/sc:section/sc:author/sc:time/@value,7,2)"/>日<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='28574-2']/sc:section/sc:author/sc:time/@value,9,2)"/>时<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='28574-2']/sc:section/sc:author/sc:time/@value,11,2)"/>分
										</xsl:when>
										<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='28574-2']/sc:section/sc:author/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='28574-2']/sc:section/sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='28574-2']/sc:section/sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='28574-2']/sc:section/sc:author/sc:time/@value,7,2)"/>日
										</xsl:when>
									</xsl:choose>
								</td>
								<td class="doclabel2">记录人：</td>
								<td class="docdataitem2" colspan="5"><xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='28574-2']/sc:section/sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name"/></td>
							</tr>
							<tr><td class="docdataitem2" colspan="8"><xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='28574-2']/sc:section/sc:text"/></td></tr>		
				
						<!-- 营养干预 -->
						<!-- 肠内干预 -->
						</table>
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="7" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">肠内干预</td>
							</tr>
								<tr>
								<td class="doclabelline3" width="14%">干预时间</td>
								<td class="doclabelline3" width="14%">类型</td>
								<td class="doclabelline3" width="16%">制剂</td>
								<td class="doclabelline3" width="14%">用量</td>
								<td class="doclabelline3" width="14%">途径</td>
								<td class="doclabelline3" width="14%">用法</td>
								<td class="doclabelline3" width="14%">结束时间</td>									
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='52043-7']/sc:section/sc:entry">
								<tr>										
									<td class="docdataitemline3">
										<xsl:choose>
											<xsl:when test="string-length(sc:substanceAdministration/sc:effectiveTime/sc:low/@value)>8">
												<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:low/@value,1,4)"/>年<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:low/@value,5,2)"/>月<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:low/@value,7,2)"/>日<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:low/@value,9,2)"/>时<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:low/@value,11,2)"/>分
											</xsl:when>
											<xsl:when test="string-length(sc:substanceAdministration/sc:effectiveTime/sc:low/@value)>0">
												<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:low/@value,1,4)"/>年<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:low/@value,5,2)"/>月<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:low/@value,7,2)"/>日
											</xsl:when>
										</xsl:choose>
									</td>
									<td class="docdataitemline3"><xsl:value-of select="sc:substanceAdministration/sc:entryRelationship/sc:observation/sc:value"/></td>
									<td class="docdataitemline3"><xsl:value-of select="sc:substanceAdministration/sc:consumable/sc:manufacturedProduct/sc:manufacturedLabeledDrug/sc:name"/></td>
									<td class="docdataitemline3"><xsl:value-of select="sc:substanceAdministration/sc:doseQuantity/@value"/><xsl:value-of select="sc:substanceAdministration/sc:doseQuantity/@unit"/></td>
									<td class="docdataitemline3"><xsl:value-of select="sc:substanceAdministration/sc:routeCode/@displayName"/></td>
									<td class="docdataitemline3"><xsl:value-of select="sc:substanceAdministration/sc:effectiveTime/sc:event/@displayName"/></td>
									<td class="docdataitemline3">
										<xsl:choose>
											<xsl:when test="string-length(sc:substanceAdministration/sc:effectiveTime/sc:high/@value)>8">
												<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:high/@value,1,4)"/>年<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:high/@value,5,2)"/>月<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:high/@value,7,2)"/>日<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:high/@value,9,2)"/>时<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:high/@value,11,2)"/>分
											</xsl:when>
											<xsl:when test="string-length(sc:substanceAdministration/sc:effectiveTime/sc:high/@value)>0">
												<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:high/@value,1,4)"/>年<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:high/@value,5,2)"/>月<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:high/@value,7,2)"/>日
											</xsl:when>
										</xsl:choose>
									</td>											
								</tr>							
							</xsl:for-each>
						</table>
						<!-- 肠外干预 -->
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="7" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">肠外干预</td>
							</tr>
								<tr>
								<td class="doclabelline3" width="14%">干预时间</td>
								<td class="doclabelline3" width="14%">类型</td>
								<td class="doclabelline3" width="16%">制剂</td>
								<td class="doclabelline3" width="14%">用量</td>
								<td class="doclabelline3" width="14%">途径</td>
								<td class="doclabelline3" width="14%">用法</td>
								<td class="doclabelline3" width="14%">结束时间</td>									
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='56851-9']/sc:section/sc:entry">
								<tr>										
									<td class="docdataitemline3">
										<xsl:choose>
											<xsl:when test="string-length(sc:substanceAdministration/sc:effectiveTime/sc:low/@value)>8">
												<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:low/@value,1,4)"/>年<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:low/@value,5,2)"/>月<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:low/@value,7,2)"/>日<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:low/@value,9,2)"/>时<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:low/@value,11,2)"/>分
											</xsl:when>
											<xsl:when test="string-length(sc:substanceAdministration/sc:effectiveTime/sc:low/@value)>0">
												<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:low/@value,1,4)"/>年<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:low/@value,5,2)"/>月<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:low/@value,7,2)"/>日
											</xsl:when>
										</xsl:choose>
									</td>
									<td class="docdataitemline3"><xsl:value-of select="sc:substanceAdministration/sc:entryRelationship/sc:observation/sc:value"/></td>
									<td class="docdataitemline3"><xsl:value-of select="sc:substanceAdministration/sc:consumable/sc:manufacturedProduct/sc:manufacturedLabeledDrug/sc:name"/></td>
									<td class="docdataitemline3"><xsl:value-of select="sc:substanceAdministration/sc:doseQuantity/@value"/><xsl:value-of select="sc:substanceAdministration/sc:doseQuantity/@unit"/></td>
									<td class="docdataitemline3"><xsl:value-of select="sc:substanceAdministration/sc:routeCode/@displayName"/></td>
									<td class="docdataitemline3"><xsl:value-of select="sc:substanceAdministration/sc:effectiveTime/sc:event/@displayName"/></td>
									<td class="docdataitemline3">
										<xsl:choose>
											<xsl:when test="string-length(sc:substanceAdministration/sc:effectiveTime/sc:high/@value)>8">
												<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:high/@value,1,4)"/>年<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:high/@value,5,2)"/>月<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:high/@value,7,2)"/>日<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:high/@value,9,2)"/>时<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:high/@value,11,2)"/>分
											</xsl:when>
											<xsl:when test="string-length(sc:substanceAdministration/sc:effectiveTime/sc:high/@value)>0">
												<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:high/@value,1,4)"/>年<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:high/@value,5,2)"/>月<xsl:value-of select="substring(sc:substanceAdministration/sc:effectiveTime/sc:high/@value,7,2)"/>日
											</xsl:when>
										</xsl:choose>
									</td>											
								</tr>							
							</xsl:for-each>
						</table>
						<!-- 营养计算 -->
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="14" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">营养计算</td>
							</tr>
							<tr>
								<td class="doclabelline3" colspan="2">时间</td>
								<td class="doclabelline3">总液量ml</td>
								<td class="doclabelline3">能量kcal</td>
								<td class="doclabelline3">蛋白质g</td>
								<td class="doclabelline3">脂肪g</td>
								<td class="doclabelline3">碳水化合物g</td>
								<td class="doclabelline3">钙mg</td>
								<td class="doclabelline3">钾mg</td>
								<td class="doclabelline3">钠mg</td>
								<td class="doclabelline3">磷mg</td>
								<td class="doclabelline3">铁mg</td>
								<td class="doclabelline3">锌mg</td>
								<td class="doclabelline3">碘mg</td>							
							</tr>
							
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='10001']/sc:section/sc:entry">
								<tr>	<xsl:for-each select="sc:organizer/sc:component">
										
																				
												<xsl:if test="sc:observation/sc:code/@displayName='时间'">
													<xsl:choose>
														<xsl:when test="string-length(sc:observation/sc:value)>8">
															<td class="docdataitemline3" colspan="2"><xsl:value-of select="substring(sc:observation/sc:value,1,4)"/>年<xsl:value-of select="substring(sc:observation/sc:value,5,2)"/>月<xsl:value-of select="substring(sc:observation/sc:value,7,2)"/>日<xsl:value-of select="substring(sc:observation/sc:value,9,2)"/>时<xsl:value-of select="substring(sc:observation/sc:value,11,2)"/>分</td>
														</xsl:when>
														<xsl:when test="string-length(sc:observation/sc:value)>0">
															<td class="docdataitemline3" colspan="2"><xsl:value-of select="substring(sc:observation/sc:value,1,4)"/>年<xsl:value-of select="substring(sc:observation/sc:value,5,2)"/>月<xsl:value-of select="substring(sc:observation/sc:value,7,2)"/>日</td>
														</xsl:when>
														<xsl:otherwise>
															<td class="docdataitemline3" colspan="2"></td>
														</xsl:otherwise>
													</xsl:choose>
												</xsl:if>
												<xsl:if test="sc:observation/sc:code/@displayName!='时间'">
													<td class="docdataitemline3"><xsl:value-of select="sc:observation/sc:value/@value"/></td>
												</xsl:if>
										
									</xsl:for-each>
								</tr>
								</xsl:for-each>
							
						</table>
						
						<!-- $[BUG]0039004 MODIFY END -->
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>





