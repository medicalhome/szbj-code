<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:sc="urn:hl7-org:v3"  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:Constants="xalan://com.founder.cdr.core.Constants" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="urn:hl7-org:v3 ../coreschemas/CDA.xsd">
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
								
								<td class="doclabel2">民族：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:ethnicGroupCode/@displayName"/></td>
								
								<td class="doclabel2">出生日期：</td>
								<td class="docdataitem2"><xsl:choose>
									<xsl:when test="string-length(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value)>8">
										<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,7,2)"/>日<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,9,2)"/>时<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value)>0">
										<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,7,2)"/>日
									</xsl:when></xsl:choose>
								</td>
								
							</tr>
							<tr>
								<td class="doclabel2">血型(ABO/RH)：</td>
								<td class="docdataitem2">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='30954-2']/sc:section/sc:entry/sc:organizer/sc:component">						
										<xsl:value-of select="sc:observation/sc:value/@displayName"/>											
									</xsl:for-each>
								
								</td>
								<td class="doclabel2">干体重：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='8716-3']/sc:section/sc:entry/sc:observation/sc:value/@value"/><xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='8716-3']/sc:section/sc:entry/sc:observation/sc:value/@unit"/></td>
								<td class="doclabel2">标准血压：</td>
								<td class="docdataitem2">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='8716-3']/sc:section/sc:entry/sc:organizer/sc:component[sc:observation/sc:code/@code='DE04.10.174.00']/sc:observation/sc:value/@value"/>
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='8716-3']/sc:section/sc:entry/sc:organizer/sc:component[sc:observation/sc:code/@code='DE04.10.174.00']/sc:observation/sc:value/@unit"/>
									/<xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='8716-3']/sc:section/sc:entry/sc:organizer/sc:component[sc:observation/sc:code/@code='DE04.10.176.00']/sc:observation/sc:value/@value"/>
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@code='8716-3']/sc:section/sc:entry/sc:organizer/sc:component[sc:observation/sc:code/@code='DE04.10.176.00']/sc:observation/sc:value/@unit"/>
								</td>	
								<td class="doclabel2">患者类型：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:code/@displayName"/></td>
																											
							</tr>
							<tr>
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@displayName='透析相关信息']/sc:section/sc:entry">						
										<td class="doclabel2"><xsl:value-of select="sc:observation/sc:code/@displayName"/>：</td>
										<td class="docdataitem2"><xsl:value-of select="sc:observation/sc:value"/></td>											
								</xsl:for-each>									
								<td class="doclabel2" >
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
							
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section">
							<xsl:if test="sc:code/@code='70300-9' or sc:code/@code='61146-7' or sc:code/@displayName='血管通路' or sc:code/@displayName='净化器型号' or sc:code/@displayName='穿刺针相关信息' or sc:code/@displayName='透析浓度组成（mmoI/L）' or sc:code/@displayName='预设参数'">
								<tr>
									<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;border-bottom: solid 1px #B3C4D4; font-weight: bold;">
										<xsl:value-of select="sc:title"/>
									</td>
								</tr>

								<tr>
									<xsl:for-each select="sc:entry">
										
										<td class="doclabel2"><xsl:value-of select="sc:observation/sc:code/@displayName"/>：</td>
										
										<td class="docdataitem2">
											<xsl:if test="sc:observation/sc:code/@displayName='处方日期' or sc:observation/sc:code/@displayName='最终失败日/拔管日' or sc:observation/sc:code/@displayName='首次使用' or sc:observation/sc:code/@displayName='首次成功' or sc:observation/sc:code/@displayName='首次失败'">
												<xsl:choose>
													<xsl:when test="string-length(sc:observation/sc:value)>8">
														<xsl:value-of select="substring(sc:observation/sc:value,1,4)"/>年<xsl:value-of select="substring(sc:observation/sc:value,5,2)"/>月<xsl:value-of select="substring(sc:observation/sc:value,7,2)"/>日<xsl:value-of select="substring(sc:observation/sc:value,9,2)"/>时<xsl:value-of select="substring(sc:observation/sc:value,11,2)"/>分
													</xsl:when>
													<xsl:when test="string-length(sc:observation/sc:value)>0">
														<xsl:value-of select="substring(sc:observation/sc:value,1,4)"/>年<xsl:value-of select="substring(sc:observation/sc:value,5,2)"/>月<xsl:value-of select="substring(sc:observation/sc:value,7,2)"/>日
													</xsl:when></xsl:choose>
											</xsl:if>
											<xsl:if test="sc:observation/sc:code/@displayName!='处方日期' and sc:observation/sc:code/@displayName!='最终失败日/拔管日' and sc:observation/sc:code/@displayName!='首次使用' and sc:observation/sc:code/@displayName!='首次成功' and sc:observation/sc:code/@displayName!='首次失败'">
												<xsl:if test="sc:observation/sc:value/@xsi:type='PQ'">
													<xsl:value-of select="sc:observation/sc:value/@value"/><xsl:value-of select="sc:observation/sc:value/@unit"/>
												</xsl:if>
												<xsl:if test="sc:observation/sc:value/@xsi:type='ST'">
													<xsl:value-of select="sc:observation/sc:value"/>
												</xsl:if>
												<xsl:if test="sc:observation/sc:value/@xsi:type='CD'">
													<xsl:value-of select="sc:observation/sc:value/@displayName"/>
												</xsl:if>
											</xsl:if>
											<xsl:if test="sc:observation/sc:code/@displayName='URR'">
												%
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
								
							</xsl:if>
							
							</xsl:for-each>
							
							<!--抗凝治疗 -->
							<tr>
									<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;border-bottom: solid 1px #B3C4D4; font-weight: bold;">
										<xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@displayName='抗凝治疗']/sc:section/sc:title"/>
									</td>
							</tr>
							<tr>		
								<td class="doclabel2"><xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@displayName='抗凝治疗']/sc:section/sc:entry/sc:observation/sc:code/@displayName"/>：</td>
										
								<td class="docdataitem2" colspan="7"><xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@displayName='抗凝治疗']/sc:section/sc:entry/sc:observation/sc:value/@displayName"/></td>								
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@displayName='抗凝治疗']/sc:section/sc:entry/sc:substanceAdministration">
								<tr>
									<td class="doclabel2">
										<xsl:choose>
											<xsl:when test="position()=1">首剂药品：</xsl:when>
											<xsl:otherwise>追加药品：</xsl:otherwise>
										</xsl:choose>
									</td>
									<td class="docdataitem2"><xsl:value-of select="sc:consumable/sc:manufacturedProduct/sc:manufacturedLabeledDrug/sc:name"/></td>
									<xsl:for-each select="sc:entryRelationship">
										<td class="doclabel2"><xsl:value-of select="sc:observation/sc:code/@displayName"/>：</td>
										<td class="docdataitem2">
											<xsl:if test="sc:observation/sc:value/@xsi:type='PQ'">
												<xsl:value-of select="sc:observation/sc:value/@value"/><xsl:value-of select="sc:observation/sc:value/@unit"/>
											</xsl:if>
											<xsl:if test="sc:observation/sc:value/@xsi:type='ST'">
												<xsl:value-of select="sc:observation/sc:value"/>
											</xsl:if>
										</td>
									</xsl:for-each>
									<td class="doclabel2">用药途径：</td>
									<td class="docdataitem2"><xsl:value-of select="sc:routeCode/@displayName"/></td>
								</tr>
							</xsl:for-each>
							
						</table>
						
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>





