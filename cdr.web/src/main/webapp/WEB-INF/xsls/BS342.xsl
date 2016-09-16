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
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="background-color:#fcffe2;border-collapse:collapse;bgcolor:white;">
							<tr>
								<td colspan="6" height="30" align="center"><h3><xsl:value-of select="sc:title"/></h3></td>
							</tr>
							<tr>
								<td class="blws" >医疗付费方式：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE07.00.007.00']/sc:value/@displayName"/></td>
								<td class="blws">住院号：<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112656.1.2.1.12']/@extension"/></td>
								<xsl:choose>
										<xsl:when test="Constants:getTrueOrgCode() = Constants:getDisabledOrgCode()">
											<td class="blws">医疗机构：<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:name"/>（组织机构代码：<xsl:value-of select="sc:custodian/sc:assignedCustodian/sc:representedCustodianOrganization/sc:id/@extension"/>）</td>
										</xsl:when>
										<xsl:otherwise>
											<td class="blws"></td>
										</xsl:otherwise>
								</xsl:choose>
								
							</tr>
							<tr>
								<td class="blws">健康卡号：<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112656.1.2.1.29']/@extension"/></td>
								<td class="blws">第<xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:id/@extension"/>次住院</td>
								<td class="blws">病案号：<xsl:value-of select="sc:id[@root='2.16.156.10011.1.1']/@extension"/></td>
							</tr>
						</table>
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;">
							<tr style="border-top: solid 1px #B3C4D4;">
								<td class="doclabel2">姓名：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/></td>
								
								<td class="doclabel2">性别：</td>
								<td class="docdataitem2"><xsl:if test="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@code='1'">[1]</xsl:if>
		<xsl:if test="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@code='2'">[2]</xsl:if>						1.男2.女</td>
								
								<td class="doclabel2">出生日期：</td>
								<td class="docdataitem2"><xsl:choose>
									<xsl:when test="string-length(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value)>8">
										<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,7,2)"/>日<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,9,2)"/>时<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value)>0">
										<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose></td>
								
								<td class="doclabel2">国籍：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:nationality/@displayName"/></td>
							</tr>
							<tr height="25" class="even">
								<td class="doclabel2">年龄：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:age/@value"/></td>
								
								<td class="doclabel2">新生儿出生体重：</td>
								<td class="docdataitem2">
								<xsl:if test="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='8716-3']/sc:entry/sc:observation[sc:code/@displayName='新生儿出生体重']/sc:value/@value!=''">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='8716-3']/sc:entry/sc:observation[sc:code/@displayName='新生儿出生体重']/sc:value/@value"/>克</xsl:if></td>								
								<td class="doclabel2">新生儿入院体重：</td>
								<td class="docdataitem2">
								<xsl:if test="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='8716-3']/sc:entry/sc:observation[sc:code/@displayName='新生儿入院体重']/sc:value/@value!=''">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='8716-3']/sc:entry/sc:observation[sc:code/@displayName='新生儿入院体重']/sc:value/@value"/>克</xsl:if></td>
								
								<td class="doclabel2"></td>
								<td class="docdataitem2"></td>
							</tr>
							<tr>
								<td class="doclabel2">出生地：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:birthplace/sc:place/sc:addr"/></td>
								
								<td class="doclabel2">籍贯：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:nativePlace/sc:place/sc:addr/sc:state"/><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:nativePlace/sc:place/sc:addr/sc:city"/></td>
								
								<td class="doclabel2">民族：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:ethnicGroupCode/@displayName"/></td>
								
								<td class="doclabel2"></td>
								<td class="docdataitem2"></td>
							</tr>
							<tr>
								<td class="doclabel2">身份证号：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:id/@extension"/></td>
								
								<td class="doclabel2">职业：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:occupation/sc:occupationCode/@displayName"/></td>
								
								<td class="doclabel2">婚姻：</td>
								<td class="docdataitem2" colspan="3"><xsl:choose><xsl:when test="sc:recordTarget/sc:patientRole/sc:patient/sc:maritalStatusCode/@displayName='未婚'">[1]</xsl:when><xsl:when test="sc:recordTarget/sc:patientRole/sc:patient/sc:maritalStatusCode/@displayName='已婚'">[2]</xsl:when><xsl:when test="sc:recordTarget/sc:patientRole/sc:patient/sc:maritalStatusCode/@displayName='丧偶'">[3]</xsl:when><xsl:when test="sc:recordTarget/sc:patientRole/sc:patient/sc:maritalStatusCode/@displayName='离婚'">[4]</xsl:when><xsl:otherwise >[9]</xsl:otherwise></xsl:choose>1.未婚2.已婚3.丧偶4.离婚9.其他</td>
							</tr>
							<tr>
								<td class="doclabel2">现住址：</td>
								<td class="docdataitem2"  colspan="3"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='HP']/sc:state"/><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='HP']/sc:city"/><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='HP']/sc:county"/><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='HP']/sc:streetAddressLine"/></td>
								
								<td class="doclabel2">电话：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:telecom[@use='H']/@value"/></td>
								
								<td class="doclabel2">邮编：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='HP']/sc:postalCode"/></td>
							</tr>
							<tr>
								<td class="doclabel2">户口地址：</td>
								<td class="docdataitem2"  colspan="3"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='PUB']/sc:state"/><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='PUB']/sc:city"/><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='PUB']/sc:county"/><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='PUB']/sc:streetAddressLine"/></td>
								
								<td class="doclabel2">电话：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:telecom[@use='PUB']/@value"/></td>
								
								<td class="doclabel2">邮编：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='PUB']/sc:postalCode"/></td>
							</tr>
							<tr>
								<td class="doclabel2">工作单位及地址：</td>
								<td class="docdataitem2"  colspan="3"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:employerOrganization/sc:addr/sc:streetAddressLine"/><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:employerOrganization/sc:name"/></td>
								
								<td class="doclabel2">单位电话：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:employerOrganization/sc:telecom/@value"/></td>
								
								<td class="doclabel2">邮编：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:employerOrganization/sc:addr/sc:postalCode"/></td>
							</tr>
							<tr>
								<td class="doclabel2">联系人姓名：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:participant/sc:associatedEntity/sc:associatedPerson/sc:name"/></td>
								
								<td class="doclabel2">关系：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:participant/sc:associatedEntity/sc:code/@displayName"/></td>
								
								<td class="doclabel2">地址：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:participant/sc:associatedEntity/sc:addr"/></td>
								
								<td class="doclabel2">电话：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:participant/sc:associatedEntity/sc:telecom/@value"/></td>
							</tr>
							<tr>
								<td class="doclabel2">入院途径：</td>
								<td class="docdataitem2" colspan="7"><xsl:choose><xsl:when test="sc:componentOf/sc:encompassingEncounter/sc:code/@displayName='急诊'">[1]</xsl:when><xsl:when test="sc:componentOf/sc:encompassingEncounter/sc:code/@displayName='门诊'">[2]</xsl:when><xsl:when test="sc:componentOf/sc:encompassingEncounter/sc:code/@displayName='其他医疗机构转入'">[3]</xsl:when><xsl:otherwise>[9]</xsl:otherwise></xsl:choose>1.急诊2.门诊3.其他医疗机构转入9.其他</td>
							</tr>
							<tr>
								<td class="doclabel2">入院时间：</td>
								<td class="docdataitem2"><xsl:choose>
									<xsl:when test="string-length(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:low/@value)>8">
										<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:low/@value,1,4)"/>年<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:low/@value,5,2)"/>月<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:low/@value,7,2)"/>日<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:low/@value,9,2)"/>时
									</xsl:when>
									<xsl:when test="string-length(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:low/@value)>0">
										<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:low/@value,1,4)"/>年<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:low/@value,5,2)"/>月<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:low/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose></td>
								 
								<td class="doclabel2">入院科别：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								
								<td class="doclabel2">病房：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								
								<td class="doclabel2">转科科别：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:author/sc:assignedAuthor/sc:representedOrganization/sc:name"/></td>
							</tr>
							<tr>
								<td class="doclabel2">出院时间：</td>
								<td class="docdataitem2"><xsl:choose>
									<xsl:when test="string-length(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:high/@value)>8">
										<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:high/@value,1,4)"/>年<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:high/@value,5,2)"/>月<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:high/@value,7,2)"/>日<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:high/@value,9,2)"/>时
									</xsl:when>
									<xsl:when test="string-length(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:high/@value)>0">
										<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:high/@value,1,4)"/>年<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:high/@value,5,2)"/>月<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/sc:high/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose></td>
								 
								<td class="doclabel2">出院科别：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='8648-8']/sc:entry/sc:act/sc:author/sc:assignedAuthor/sc:representedOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								
								<td class="doclabel2">病房：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='8648-8']/sc:entry/sc:act/sc:author/sc:assignedAuthor/sc:representedOrganization/sc:name"/></td>
								
								<td class="doclabel2">实际住院天数：</td>
								<td class="docdataitem2"><xsl:if test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE06.00.310.00']/sc:value/@value)>0"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE06.00.310.00']/sc:value/@value"/>天</xsl:if></td>
							</tr>
							<tr>
								<td class="doclabel2">门（急）诊诊断：</td>
								<td class="docdataitem2" colspan="3"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='29548-5']/sc:entry/sc:observation[sc:code/@displayName='门（急）诊诊断疾病编码']/sc:value/@displayName"/></td>
								
								<td class="doclabel2">疾病编码：</td>
								<td class="docdataitem2"  colspan="3"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='29548-5']/sc:entry/sc:observation[sc:code/@displayName='门（急）诊诊断疾病编码']/sc:value/@code"/></td>
							</tr>					
						</table>
						<table align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;" class="table">
							<tr height="28" class="tabletitle">
								<td>出院诊断</td>
								<td>疾病编码</td>
								<td>入院病情</td>
							</tr>
							<tr class="odd">
								<td>主要诊断：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@displayName='出院诊断-主要诊断-疾病编码']/sc:value/@displayName"/></td>
								<td><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@displayName='出院诊断-主要诊断-疾病编码']/sc:value/@code"/></td>
								<td><xsl:choose>
								<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@displayName='出院诊断-主要诊断-疾病编码']/sc:entryRelationship/sc:observation/sc:value/@displayName='有'">[1]有</xsl:when>
								<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@displayName='出院诊断-主要诊断-疾病编码']/sc:entryRelationship/sc:observation/sc:value/@displayName='临床未确定'">[2]临床未确定</xsl:when>
								<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@displayName='出院诊断-主要诊断-疾病编码']/sc:entryRelationship/sc:observation/sc:value/@displayName='情况不明'">[3]情况不明</xsl:when>
								<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry/sc:observation[sc:code/@displayName='出院诊断-主要诊断-疾病编码']/sc:entryRelationship/sc:observation/sc:value/@displayName='无'">[4]无</xsl:when>						
								</xsl:choose></td>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11535-2']/sc:entry[sc:observation/sc:code/@displayName='出院诊断-其他诊断疾病编码']">
								    <xsl:if test="position() mod 2=0">
								    	<tr class="even">
											<td>其他诊断：<xsl:value-of select="sc:observation/sc:value/@displayName"/></td>
											<td><xsl:value-of select="sc:observation/sc:value/@code"/></td>
											<td><xsl:choose>
											<xsl:when test="sc:observation/sc:entryRelationship/sc:observation/sc:value/@displayName='有'">[1]有</xsl:when>
											<xsl:when test="sc:observation/sc:entryRelationship/sc:observation/sc:value/@displayName='临床未确定'">[2]临床未确定</xsl:when>
											<xsl:when test="sc:observation/sc:entryRelationship/sc:observation/sc:value/@displayName='情况不明'">[3]情况不明</xsl:when>
											<xsl:when test="sc:observation/sc:entryRelationship/sc:observation/sc:value/@displayName='无'">[4]无</xsl:when>						
											</xsl:choose></td>
									    </tr>
								    </xsl:if>
								    <xsl:if test="position() mod 2=1">
								    	<tr class="odd">
											<td>其他诊断：<xsl:value-of select="sc:observation/sc:value/@displayName"/></td>
											<td><xsl:value-of select="sc:observation/sc:value/@code"/></td>
											<td><xsl:choose>
											<xsl:when test="sc:observation/sc:entryRelationship/sc:observation/sc:value/@displayName='有'">[1]有</xsl:when>
											<xsl:when test="sc:observation/sc:entryRelationship/sc:observation/sc:value/@displayName='临床未确定'">[2]临床未确定</xsl:when>
											<xsl:when test="sc:observation/sc:entryRelationship/sc:observation/sc:value/@displayName='情况不明'">[3]情况不明</xsl:when>
											<xsl:when test="sc:observation/sc:entryRelationship/sc:observation/sc:value/@displayName='无'">[4]无</xsl:when>						
											</xsl:choose></td>
									    </tr>
								    </xsl:if>
							</xsl:for-each>
							<tr>
								<td colspan="3" class="blws">入院病情：1.有2.临床未确定3.情况不明4.无</td>
							</tr>
						</table>
						<table align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;">
							<tr>
								<td class="doclabel2">损伤、中毒的外因：</td>
								<td class="docdataitem2" colspan="5">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11450-4']/sc:entry[sc:observation/sc:code/@code='DE05.10.152.00']">
										<xsl:if test="position()>1"><br/></xsl:if>
										<xsl:value-of select="sc:observation/sc:value"/>
									</xsl:for-each>
								</td>
								
								<td class="doclabel2">疾病编码：</td>
								<td class="docdataitem2">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11450-4']/sc:entry[sc:observation/sc:code/@code='DE05.10.152.00']">
										<xsl:if test="position()>1"><br/></xsl:if>
										<xsl:value-of select="sc:observation/sc:entryRelationship/sc:observation/sc:value/@code"/>
									</xsl:for-each>
								</td>
							</tr>
							<tr>
								<td class="doclabel2">病理诊断：</td>
								<td class="docdataitem2" colspan="3">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='29548-5']/sc:entry[sc:observation/sc:code/@displayName='病理诊断-疾病编码']">
										<xsl:if test="position()>1"><br/></xsl:if>
										<xsl:value-of select="sc:observation/sc:value/@displayName"/>
									</xsl:for-each>
								</td>
								
								<td class="doclabel2">疾病编码：</td>
								<td class="docdataitem2">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='29548-5']/sc:entry[sc:observation/sc:code/@displayName='病理诊断-疾病编码']">
										<xsl:if test="position()>1"><br/></xsl:if>
										<xsl:value-of select="sc:observation/sc:value/@code"/>
									</xsl:for-each>
								</td>
								
								<td class="doclabel2"><!--  病理号：--></td>
								<td class="docdataitem2">
									<!-- <xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry[sc:observation/sc:code/@code='DE05.01.025.00']">
										<xsl:if test="position()>1"><br/></xsl:if>
										<xsl:value-of select="sc:observation/sc:id/@extension"/>
									</xsl:for-each>-->
								</td>
							</tr>
							<tr>
								<td class="doclabel2">药物过敏：</td>
								<td class="docdataitem2"><xsl:if test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:act/sc:entryRelationship/sc:observation/sc:value/@value='无'">[1]</xsl:if><xsl:if test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:act/sc:entryRelationship/sc:observation/sc:value/@value='有'">[2]</xsl:if>1.无2.有</td>
								
								<td class="doclabel2">过敏药物：</td>
								<td class="docdataitem2"  colspan="3">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:act/sc:entryRelationship/sc:observation/sc:participant">
										<xsl:if test="position()>1">/</xsl:if>
										<xsl:value-of select="sc:participantRole/sc:playingEntity/sc:desc"/>
									</xsl:for-each>
								</td>
								
								<td class="doclabel2">死亡患者尸检：</td>
								<td class="docdataitem2"><xsl:if test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE09.00.108.00']/sc:value/@value='true'">[1]</xsl:if><xsl:if test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE09.00.108.00']/sc:value/@value='false'">[2]</xsl:if>1.是2.否</td>
							</tr>
							<tr>
								<td class="doclabel2">血型：</td>
								<td class="docdataitem2" colspan="3"><xsl:choose><xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='DE04.50.001.00']/sc:value/@displayName='A'">[1]</xsl:when><xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='DE04.50.001.00']/sc:value/@displayName='B'">[2]</xsl:when><xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='DE04.50.001.00']/sc:value/@displayName='O'">[3]</xsl:when><xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='DE04.50.001.00']/sc:value/@displayName='AB'">[4]</xsl:when><xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='DE04.50.001.00']/sc:value/@displayName='不详'">[5]</xsl:when><xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='DE04.50.001.00']/sc:value/@displayName='未查'">[6]</xsl:when></xsl:choose>1.A 2.B 3.O 4.AB 5.不详 6.未查</td>
								
								<td class="doclabel2">Rh：</td>
								<td class="docdataitem2" colspan="3"><xsl:choose><xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='DE04.50.010.00']/sc:value/@displayName='阴'">[1]</xsl:when><xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='DE04.50.010.00']/sc:value/@displayName='阳'">[2]</xsl:when><xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='DE04.50.010.00']/sc:value/@displayName='不详'">[3]</xsl:when><xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='DE04.50.010.00']/sc:value/@displayName='未查'">[4]</xsl:when></xsl:choose>1.阴性2.阳性3.不详4.未查</td>
							</tr>
							<tr>
								<td class="doclabel2">科主任：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:legalAuthenticator/sc:assignedEntity/sc:assignedPerson/sc:name"/></td>
								
								<td class="doclabel2">主任（副）医师：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:authenticator[sc:assignedEntity/sc:code/@displayName='主任(副主任)医师']/sc:assignedEntity/sc:assignedPerson/sc:name"/></td>
								
								<td class="doclabel2">主治医师：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:authenticator[sc:assignedEntity/sc:code/@displayName='主治医师']/sc:assignedEntity/sc:assignedPerson/sc:name"/></td>
								
								<td class="doclabel2">住院医师：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:authenticator[sc:assignedEntity/sc:code/@displayName='住院医师']/sc:assignedEntity/sc:assignedPerson/sc:name"/></td>
							</tr>
							<tr>
								<td class="doclabel2">责任护士：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:authenticator[sc:assignedEntity/sc:code/@displayName='责任护士']/sc:assignedEntity/sc:assignedPerson/sc:name"/></td>
								
								<td class="doclabel2">进修医师：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:authenticator[sc:assignedEntity/sc:code/@displayName='进修医师']/sc:assignedEntity/sc:assignedPerson/sc:name"/></td>
								
								<td class="doclabel2">实习医师：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:authenticator[sc:assignedEntity/sc:code/@displayName='实习医师']/sc:assignedEntity/sc:assignedPerson/sc:name"/></td>
								
								<td class="doclabel2">编码员：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:authenticator[sc:assignedEntity/sc:code/@displayName='编码员']/sc:assignedEntity/sc:assignedPerson/sc:name"/></td>
							</tr>
							<tr>
								<td class="doclabel2">病案质量：</td>
								<td class="docdataitem2">
								<xsl:choose>
									<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE09.00.103.00']/sc:value/@displayName='甲'">[1]</xsl:when>
									<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE09.00.103.00']/sc:value/@displayName='乙'">[2]</xsl:when>
									<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE09.00.103.00']/sc:value/@displayName='丙'">[3]</xsl:when>						
								</xsl:choose>
								1.甲2.乙3.丙
								</td>
								
								<td class="doclabel2">质控医师：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE09.00.103.00']/sc:author/sc:assignedAuthor[sc:code/@displayName='质控医生']/sc:assignedPerson/sc:name"/></td>
								
								<td class="doclabel2">质控护士：</td>
								<td class="docdataitem2"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE09.00.103.00']/sc:author/sc:assignedAuthor[sc:code/@displayName='质控护士']/sc:assignedPerson/sc:name"/></td>
								
								<td class="doclabel2">质控日期：</td>
								<td class="docdataitem2">
								<xsl:if test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE09.00.103.00']/sc:effectiveTime/@value!=''">
								<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE09.00.103.00']/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE09.00.103.00']/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE09.00.103.00']/sc:effectiveTime/@value,7,2)"/>日</xsl:if></td>
							</tr>
						</table>
						<table align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;" class="table">
							<tr height="20" class="tabletitle">
								<td rowspan="2" width="15%">手术及操作编码</td>
								<td rowspan="2" width="12%">手术及操作日期</td>
								<td rowspan="2" width="8%">手术级别</td>
								<td rowspan="2" width="12%">手术及操作名称</td>
								<td colspan="3" width="23%" align="center">手术及操作医师</td>
								<td rowspan="2" width="10%">切口愈合等级</td>
								<td rowspan="2" width="10%">麻醉方式</td>
								<td rowspan="2" width="10%">麻醉医师</td>
							</tr>
							<tr height="20" class="tabletitle" style="border-top: solid 1px #B3C4D4;">
								<td align="center">术者</td>
								<td align="center">Ⅰ助</td>
								<td align="center">Ⅱ助</td>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry[sc:procedure]">
								    <xsl:if test="position() mod 2=0">
								    	<tr class="even">
											<td><xsl:value-of select="sc:procedure/sc:code/@code"/></td>
											<td><xsl:choose>
												<xsl:when test="string-length(sc:procedure/sc:effectiveTime/@value)>8">
													<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/@value,7,2)"/>日<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/@value,9,2)"/>时<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/@value,11,2)"/>分
												</xsl:when>
												<xsl:when test="string-length(sc:procedure/sc:effectiveTime/@value)>0">
													<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/@value,7,2)"/>日
												</xsl:when>		
											</xsl:choose></td>
											<td><xsl:value-of select="sc:procedure/sc:entryRelationship/sc:observation[sc:code/@code='DE06.00.255.00']/sc:value/@displayName"/></td>
											<td><xsl:value-of select="sc:procedure/sc:code/@displayName"/></td>
											<td><xsl:value-of select="sc:procedure/sc:performer/sc:assignedEntity/sc:assignedPerson/sc:name/text()"/></td>
											<td><xsl:value-of select="sc:procedure/sc:participant/sc:participantRole[sc:code/@displayName='第一助手']/sc:playingEntity/sc:name/text()"/></td>
											<td><xsl:value-of select="sc:procedure/sc:participant/sc:participantRole[sc:code/@displayName='第二助手']/sc:playingEntity/sc:name/text()"/></td>
											<td><xsl:value-of select="sc:procedure/sc:entryRelationship/sc:observation[sc:code/@code='DE05.10.147.00']/sc:value/@displayName"/></td>
											<td><xsl:value-of select="sc:procedure/sc:entryRelationship/sc:observation[sc:code/@code='DE06.00.073.00']/sc:value/@displayName"/></td>
											<td><xsl:value-of select="sc:procedure/sc:entryRelationship/sc:observation[sc:code/@code='DE06.00.073.00']/sc:performer/sc:assignedEntity/sc:assignedPerson/sc:name/text()"/></td>
									    </tr>
								    </xsl:if>
								    <xsl:if test="position() mod 2=1">
								    	<tr class="odd">
											<td><xsl:value-of select="sc:procedure/sc:code/@code"/></td>
											<td><xsl:if test="sc:procedure/sc:effectiveTime/@value!=''"><xsl:value-of select="substring(sc:procedure/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/@value,7,2)"/>日</xsl:if></td>
											<td><xsl:value-of select="sc:procedure/sc:entryRelationship/sc:observation[sc:code/@code='DE06.00.255.00']/sc:value/@displayName"/></td>
											<td><xsl:value-of select="sc:procedure/sc:code/@displayName"/></td>
											<td><xsl:value-of select="sc:procedure/sc:performer/sc:assignedEntity/sc:assignedPerson/sc:name/text()"/></td>
											<td><xsl:value-of select="sc:procedure/sc:participant/sc:participantRole[sc:code/@displayName='第一助手']/sc:playingEntity/sc:name/text()"/></td>
											<td><xsl:value-of select="sc:procedure/sc:participant/sc:participantRole[sc:code/@displayName='第二助手']/sc:playingEntity/sc:name/text()"/></td>
											<td><xsl:value-of select="sc:procedure/sc:entryRelationship/sc:observation[sc:code/@code='DE05.10.147.00']/sc:value/@displayName"/></td>
											<td><xsl:value-of select="sc:procedure/sc:entryRelationship/sc:observation[sc:code/@code='DE06.00.073.00']/sc:value/@displayName"/></td>
											<td><xsl:value-of select="sc:procedure/sc:entryRelationship/sc:observation[sc:code/@code='DE06.00.073.00']/sc:performer/sc:assignedEntity/sc:assignedPerson/sc:name/text()"/></td>
									    </tr>
								    </xsl:if>
							</xsl:for-each>
						</table>
						<table align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;">
							<tr>
								<td class="doclabel2">离院方式：</td>
								<td class="docdataitem2" colspan="3">
									<xsl:choose>
										<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE06.00.223.00']/sc:value/@displayName='医嘱离院'">[1]</xsl:when>
										<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE06.00.223.00']/sc:value/@displayName='医嘱转院'">[2]</xsl:when>
										<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE06.00.223.00']/sc:value/@displayName='医嘱转社区卫生服务机构/乡镇卫生院'">[3]</xsl:when>
										<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE06.00.223.00']/sc:value/@displayName='非医嘱离院'">[4]</xsl:when>
										<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE06.00.223.00']/sc:value/@displayName='死亡'">[5]</xsl:when>
										<xsl:otherwise>[9]</xsl:otherwise>
									</xsl:choose>
									1.医嘱离院2.医嘱转院<xsl:if test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE06.00.223.00']/sc:value/@displayName='医嘱转院'">，拟接收医疗机构名称：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE06.00.223.00']/sc:entryRelationship/sc:observation/sc:value"/></xsl:if>3.医嘱转社区卫生服务机构/乡镇卫生院<xsl:if test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE06.00.223.00']/sc:value/@displayName='医嘱转社区卫生服务机构/乡镇卫生院'">，拟接收医疗机构名称：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE06.00.223.00']/sc:entryRelationship/sc:observation/sc:value"/></xsl:if>4.非医嘱离院5.死亡9.其他
								</td>
							</tr>
							<tr>
							    <td class="doclabel2">是否有出院31天后再住院计划：</td>
							    <td class="docdataitem2"><xsl:if test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE06.00.194.00']/sc:value/@value='true'">[2]</xsl:if><xsl:if test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE06.00.194.00']/sc:value/@value='false'">[1]</xsl:if>1.无2.有</td>
							    
							    <td class="doclabel2">目的：</td>
							    <td class="docdataitem2"><xsl:if test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE06.00.194.00']/sc:value/@value='true'"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE06.00.194.00']/sc:entryRelationship/sc:observation/sc:value"/></xsl:if></td>
							</tr>
							<tr>
								<td class="doclabel2">颅脑损伤患者昏迷时间：</td>
							    <td class="docdataitem2" colspan="3">入院前<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry[sc:organizer/sc:code/@displayName='颅脑损伤患者入院前昏迷时间']/sc:organizer/sc:component/sc:observation[sc:code/@code='DE05.10.138.01']/sc:value/@value"/>天<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry[sc:organizer/sc:code/@displayName='颅脑损伤患者入院前昏迷时间']/sc:organizer/sc:component/sc:observation[sc:code/@code='DE05.10.138.02']/sc:value/@value"/>小时<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry[sc:organizer/sc:code/@displayName='颅脑损伤患者入院前昏迷时间']/sc:organizer/sc:component/sc:observation[sc:code/@code='DE05.10.138.03']/sc:value/@value"/>分 入院后<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry[sc:organizer/sc:code/@displayName='颅脑损伤患者入院后昏迷时间']/sc:organizer/sc:component/sc:observation[sc:code/@code='DE05.10.138.01']/sc:value/@value"/>天<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry[sc:organizer/sc:code/@displayName='颅脑损伤患者入院后昏迷时间']/sc:organizer/sc:component/sc:observation[sc:code/@code='DE05.10.138.02']/sc:value/@value"/>小时<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry[sc:organizer/sc:code/@displayName='颅脑损伤患者入院后昏迷时间']/sc:organizer/sc:component/sc:observation[sc:code/@code='DE05.10.138.03']/sc:value/@value"/>分</td>
							</tr>
						<!-- </table>
						<table align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;"> -->
							<tr style="border-top: solid 2px #B3C4D4; border-bottom: solid 1px #B3C4D4;">
								<td class="doclabel2"><strong>住院费用（元）：</strong></td>
								<td class="docdataitem2" colspan="3">总费用：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry[sc:observation/sc:code/@code='HDSD00.11.142']/sc:observation/sc:value/@value"/>（自付金额：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry[sc:observation/sc:code/@code='HDSD00.11.142']/sc:observation/sc:entryRelationship/sc:observation/sc:value/@value"/>）</td>
							</tr>
							<tr>
								<td class="doclabel2">1.综合医疗服务类：</td>
								<td class="docdataitem2" colspan="3">（1）一般医疗服务费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.147']/sc:value/@value"/>（2）一般治疗操作费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.148']/sc:value/@value"/>（3）护理费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.145']/sc:value/@value"/>（4）其他费用：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.146']/sc:value/@value"/></td>
							</tr>
							
							<tr>
								<td class="doclabel2">2.诊断类：</td>
								<td class="docdataitem2" colspan="3">（5）病理诊断费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.121']/sc:value/@value"/>（6）实验室诊断费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.123']/sc:value/@value"/>（7）影像学诊断费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.124']/sc:value/@value"/>（8）临床诊断项目费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.122']/sc:value/@value"/></td>
							</tr>
							
							<tr>
								<td class="doclabel2">3.治疗类：</td>
								<td class="docdataitem2" colspan="3">（9）非手术治疗项目费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.129']/sc:value/@value"/>（临床物理治疗费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:observation[sc:code/@code='HDSD00.11.130']/sc:value/@value"/>）（10）手术治疗费: <xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.131']/sc:value/@value"/>（麻醉费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:observation[sc:code/@code='HDSD00.11.132']/sc:value/@value"/> 手术费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:observation[sc:code/@code='HDSD00.11.133']/sc:value/@value"/>）</td>
							</tr>
							
							<tr>
								<td class="doclabel2">4.康复类：</td>
								<td class="docdataitem2" colspan="3">（11）康复费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='HDSD00.11.055']/sc:value/@value"/></td>
							</tr>
							
							<tr>
								<td class="doclabel2">5.中医类：</td>
								<td class="docdataitem2" colspan="3">（12）中医治疗费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='HDSD00.11.136']/sc:value/@value"/></td>
							</tr>
							
							<tr>
								<td class="doclabel2">6.西药类：</td>
								<td class="docdataitem2" colspan="3">（13）西药费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='HDSD00.11.098']/sc:value/@value"/>（抗菌药物费用：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry//sc:entryRelationship/sc:observation[sc:code/@code='HDSD00.11.099']/sc:value/@value"/>）</td>
							</tr>
							
							<tr>
								<td class="doclabel2">7.中药类：</td>
								<td class="docdataitem2" colspan="3">（14）中成药费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.135']/sc:value/@value"/>（15）中草药费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.134']/sc:value/@value"/></td>
							</tr>
							
							<tr>
								<td class="doclabel2">8.血液和血液制品类：</td>
								<td class="docdataitem2" colspan="3">（16）血费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.115']/sc:value/@value"/>（17）白蛋白类制品费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.111']/sc:value/@value"/>（18）球蛋白类制品费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.113']/sc:value/@value"/>（19）凝血因子类制品费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.112']/sc:value/@value"/>（20）细胞因子类制品费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.114']/sc:value/@value"/></td>
							</tr>
							
							<tr>
								<td class="doclabel2">9.耗材类：</td>
								<td class="docdataitem2" colspan="3">（21）检查用一次性医用材料费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.038']/sc:value/@value"/>（22）治疗用一次性医用材料费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.040']/sc:value/@value"/>（23）手术用一次性医用材料费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation[sc:code/@code='HDSD00.11.039']/sc:value/@value"/></td>
							</tr>
							
							<tr style="border-bottom: solid 1px #B3C4D4;">
								<td class="doclabel2">10.其他类：</td>
								<td class="docdataitem2" colspan="3">（24）其他费：<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='HSDB05.10.130']/sc:value/@value"/></td>
							</tr>
						</table>
						<table border="0">
							<tr><td class="blws">说明：</td></tr>
							<tr><td class="blws">（一）医疗付费方式 1.城镇职工基本医疗保险 2.城镇居民基本医疗保险 3.新型农村合作医疗 4.贫困救助 5.商业医疗保险 6.全公费 7.全自费 8.其他社会保险 9.其他</td></tr>
							<tr><td class="blws">（二）凡可由医院信息系统提供住院费用清单的，住院病案首页中可不填写“住院费用”。</td></tr>
						</table>
					</div>
				</div>
    	    </body>
		</html>
    </xsl:template>
</xsl:stylesheet>




