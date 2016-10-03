<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:sc="urn:hl7-org:v3"  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:Constants="xalan://com.yly.cdr.core.Constants">
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
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="8" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"><h3><xsl:value-of select="sc:title"/></h3></td>
							</tr>
							<tr>
								<td colspan="2" style="padding-left:5px;text-align: right;">卡片编号：</td>
								<td colspan="2" class="docdataitem" style="text-align: left;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='260299005'][@codeSystem='2.16.840.1.113883.6.96']/../sc:value/text()"/></td>
								<td colspan="2" style="text-align: right;">报卡类别：</td>
								<!-- $Author :chang_xuewen
								$Date : 2014/03/04 15:00
								$[BUG]0042690 MODIFY BEGIN -->
								<td colspan="2" class="docdataitem" style="text-align: left;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='308552006']/../sc:value/text()"/></td>
								<!-- $[BUG]0042690 MODIFY END -->
							</tr>
						</table>
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
					            <td colspan="8" class="blockHeader" height="25" align="center" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"></td>
					        </tr>
							<tr>
								<td class="doclabel">患者姓名：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/></td>
								
								<td class="doclabel">患儿家长姓名：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:guardian/sc:guardianPerson/sc:name"/></td>
								
								<td class="doclabel">身份证号：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:id/@extension"/></td>
								
								<td class="doclabel">性别：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/></td>
							</tr>
							<tr>
								<td class="doclabel">出生日期：</td>
								<td class="docdataitem"><xsl:choose>
									<xsl:when test="string-length(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value)>8">
										<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,7,2)"/>日<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,9,2)"/>时<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value)>0">
										<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,7,2)"/>日
									</xsl:when></xsl:choose>
								</td>
								
								<td class="doclabel">工作单位：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='WP']/sc:streetAddressLine"/></td>
								
								<td class="doclabel">联系电话：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:telecom/@value"/></td>
								
								<td class="doclabel">户籍属于：</td>
								<td class="docdataitem"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='307927003'][@codeSystem='2.16.840.1.113883.6.96']/../sc:value/text()"/></td>
							
							</tr>
							<tr>
								<td class="doclabel" colspan="1">现住地址：</td>
								<td class="docdataitem" colspan="3">
								<xsl:if test="string-length(sc:recordTarget/sc:patientRole/sc:addr[@use='HP']/sc:state)>0">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='HP']/sc:state"/>省
								</xsl:if>
								<xsl:if test="string-length(sc:recordTarget/sc:patientRole/sc:addr[@use='HP']/sc:city[1])>0">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[2]/sc:city[1]"/>市
								</xsl:if>
								<xsl:if test="string-length(sc:recordTarget/sc:patientRole/sc:addr[@use='HP']/sc:county)>0">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='HP']/sc:county"/>县(区)
								</xsl:if>
								<xsl:if test="string-length(sc:recordTarget/sc:patientRole/sc:addr[@use='HP']/sc:streetName)>0">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='HP']/sc:streetName"/>街道(乡/镇)
								</xsl:if>
								<xsl:if test="string-length(sc:recordTarget/sc:patientRole/sc:addr[@use='HP']/sc:city[2])>0">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='HP']/sc:city[2]"/>村
								</xsl:if>
								<xsl:if test="string-length(sc:recordTarget/sc:patientRole/sc:addr[@use='HP']/sc:houseNumber)>0">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='HP']/sc:houseNumber"/><br/>
								</xsl:if>
								<xsl:if test="string-length(sc:recordTarget/sc:patientRole/sc:addr[@use='HP']/sc:streetAddressLine)>0">
									详细地址：<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='HP']/sc:streetAddressLine"/>
								</xsl:if>
								</td>
								
								<td class="doclabel" colspan="1">户籍地址：</td>
								<td class="docdataitem" colspan="3">
								<xsl:if test="string-length(sc:recordTarget/sc:patientRole/sc:addr[@use='PUB']/sc:state)>0">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='PUB']/sc:state"/>省
								</xsl:if>
								<xsl:if test="string-length(sc:recordTarget/sc:patientRole/sc:addr[@use='PUB']/sc:city[1])>0">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='PUB']/sc:city[1]"/>市
								</xsl:if>
								<xsl:if test="string-length(sc:recordTarget/sc:patientRole/sc:addr[@use='PUB']/sc:county)>0">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='PUB']/sc:county"/>县(区)
								</xsl:if>
								<xsl:if test="string-length(sc:recordTarget/sc:patientRole/sc:addr[@use='PUB']/sc:streetName)>0">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='PUB']/sc:streetName"/>乡(镇/街道)
								</xsl:if>
								<xsl:if test="string-length(sc:recordTarget/sc:patientRole/sc:addr[@use='PUB']/sc:city[2])>0">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='PUB']/sc:city[2]"/>村
								</xsl:if>
								<xsl:if test="string-length(sc:recordTarget/sc:patientRole/sc:addr[@use='PUB']/sc:houseNumber)>0">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='PUB']/sc:houseNumber"/>(门牌号)<br/>
								</xsl:if>
								<xsl:if test="string-length(sc:recordTarget/sc:patientRole/sc:addr[@use='PUB']/sc:streetAddressLine)>0">
									详细地址：<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='PUB']/sc:streetAddressLine"/>
								</xsl:if>
								</td>
							</tr>
							<tr>
								<td class="doclabel">患者职业：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:occupation/sc:occupationCode/@displayName"/></td>
								
								<td class="doclabel">发病日期：</td>
								<td class="docdataitem">
									<xsl:choose>
									<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='298059007']/../sc:value/@value)>8">
										<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='298059007']/../sc:value/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='298059007']/../sc:value/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='298059007']/../sc:value/@value,7,2)"/>日<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='298059007']/../sc:value/@value,9,2)"/>时<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='298059007']/../sc:value/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='298059007']/../sc:value/@value)>0">
										<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='298059007']/../sc:value/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='298059007']/../sc:value/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='298059007']/../sc:value/@value,7,2)"/>日
									</xsl:when></xsl:choose>
								</td>
								
								<td class="doclabel">诊断日期：</td>
								<td class="docdataitem">
									<xsl:choose>
									<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='432213005']/../sc:value/@value)>8">
										<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='432213005']/../sc:value/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='432213005']/../sc:value/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='432213005']/../sc:value/@value,7,2)"/>日<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='432213005']/../sc:value/@value,9,2)"/>时<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='432213005']/../sc:value/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='432213005']/../sc:value/@value)>0">
										<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='432213005']/../sc:value/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='432213005']/../sc:value/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='432213005']/../sc:value/@value,7,2)"/>日
									</xsl:when></xsl:choose>
								</td>
								
								<td class="doclabel">死亡日期：</td>
								<td class="docdataitem">
									<xsl:choose>
									<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='399753006']/../sc:value/@value)>8">
										<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='399753006']/../sc:value/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='399753006']/../sc:value/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='399753006']/../sc:value/@value,7,2)"/>日<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='399753006']/../sc:value/@value,9,2)"/>时<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='399753006']/../sc:value/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='399753006']/../sc:value/@value)>0">
										<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='399753006']/../sc:value/@value,1,4)"/>年<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='399753006']/../sc:value/@value,5,2)"/>月<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='399753006']/../sc:value/@value,7,2)"/>日
									</xsl:when></xsl:choose>
								</td>
							</tr>
							<tr>
								<td class="doclabel" colspan="1">病例分类：</td>
								<td class="docdataitem" colspan="6">
								(1)<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='278201002']/../sc:entryRelationship/sc:organizer/sc:component[1]/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@displayName"/> , 
								(2)<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='278201002']/../sc:entryRelationship/sc:organizer/sc:component[2]/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@displayName"/>
								</td>
							</tr>
							<tr>
					            <td colspan="8" class="blockHeader" height="25" align="center" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">传染病</td>
					        </tr>
							<tr>
					            <td colspan="1" class="doclabel" style="padding-left:5px;text-align: right;">甲类传染病：</td>
								<td colspan="7" class="docdataitem" style="text-align: left;">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry[1]/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
									<xsl:if test="sc:observation/sc:value/@value != 'false'">
										<xsl:value-of select="sc:observation/sc:code/@displayName"/> , 
									</xsl:if>
								</xsl:for-each>
								</td>
					        </tr>
					        <tr>
					            <td colspan="1" class="doclabel" style="padding-left:5px;text-align: right;">乙类传染病：</td>
								<td colspan="7" class="docdataitem" style="text-align: left;">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry[2]/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
									<xsl:if test="sc:observation/sc:value/@value != 'false'">
										<xsl:value-of select="sc:observation/sc:code/@displayName"/> , 
									</xsl:if>
								</xsl:for-each>
								</td>
					        </tr>
					        <tr>
					            <td colspan="1" class="doclabel" style="padding-left:5px;text-align: right;">丙类传染病：</td>
								<td colspan="7" class="docdataitem" style="text-align: left;">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry[3]/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
									<xsl:if test="sc:observation/sc:value/@value != 'false'">
										<xsl:value-of select="sc:observation/sc:code/@displayName"/> , 
									</xsl:if>
								</xsl:for-each>
								</td>
					        </tr>
					        <tr>
					            <td colspan="1" class="doclabel" style="padding-left:5px;text-align: right;">其他法定管理以及重点<br/>监测传染病：</td>
								<td colspan="7" class="docdataitem" style="text-align: left;">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry[4]/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
									<xsl:if test="sc:observation/sc:value/@value != 'false'">
										<xsl:value-of select="sc:observation/sc:code/@displayName"/>
										<xsl:choose>
											<xsl:when test="sc:observation/sc:text != ''">
												: <xsl:value-of select="sc:observation/sc:text"/>,
											</xsl:when>
											<xsl:otherwise> , </xsl:otherwise>
										</xsl:choose>
									</xsl:if>
								</xsl:for-each>
								</td>
					        </tr>
					  </table>
					  <table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
					  		<tr>
					            <td colspan="8" class="blockHeader" height="25" align="center" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">报告信息</td>
					        </tr>
					        <tr>
								<td class="doclabel">订症病名：</td>
								<td class="docdataitem"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code[@code='40733004']/../sc:value/text()"/></td>
								
								<td class="doclabel">退卡原因：</td>
								<td class="docdataitem"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry[12]/sc:observation/sc:code[@code='419617009']/../sc:value/text()"/></td>
								
								<td class="doclabel">报告单位：</td>
								<td class="docdataitem"><xsl:value-of select="sc:author/sc:assignedAuthor/sc:representedOrganization/sc:name"/></td>
								
								<td class="doclabel">联系电话：</td>
								<td class="docdataitem"><xsl:value-of select="sc:author/sc:assignedAuthor/sc:telecom/@value"/></td>
							</tr>
							<tr>
								<td class="doclabel">报告医生：</td>
								<td class="docdataitem"><xsl:value-of select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name"/></td>
								
								<td class="doclabel">填卡日期：</td>
								<td class="docdataitem">
								<xsl:choose>
									<xsl:when test="string-length(sc:author/sc:time/@value)>8">
										<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>日<xsl:value-of select="substring(sc:author/sc:time/@value,9,2)"/>时<xsl:value-of select="substring(sc:author/sc:time/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:author/sc:time/@value)>0">
										<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>日
									</xsl:when></xsl:choose>
								</td>
								
								<td class="doclabel"></td>
								<td class="docdataitem"></td>
								
								<td class="doclabel"></td>
								<td class="docdataitem"></td>
							</tr>
							<tr>
					            <td colspan="1" class="doclabel" style="padding-left:5px;text-align: right;">备注：</td>
								<td colspan="7" class="docdataitem" style="text-align: left;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component[3]/sc:section/sc:text"/></td>
					        </tr>
						</table>
						<xsl:if test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code[@displayName='艾滋病']/../sc:entryRelationship/@typeCode)>0">
							<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
								<tr>
						            <td colspan="8" class="blockHeader" height="25" align="center" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code[@displayName='艾滋病']/@displayName"/>(附卡)</td>
						        </tr>
						        <xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code[@displayName='艾滋病']/../sc:entryRelationship/sc:organizer/sc:component">
							        <tr>
				
										<xsl:if test="sc:observation/sc:code/@displayName = '接触史'">	
											<td colspan="1" class="doclabel"><xsl:value-of select="sc:observation/sc:code/@displayName"/>:</td>
											<td colspan="7" class="docdataitem" style="text-align: left;">
												 <xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component">									 	
														<xsl:if test="sc:observation/sc:value/@value !='false'">
																<xsl:value-of select="sc:observation/sc:code/@displayName"/> , 
														</xsl:if>									
												 </xsl:for-each>
											 </td>
										</xsl:if>	
										<xsl:if test="sc:observation/sc:code/@displayName != '接触史'">
								        	<xsl:choose>
												<xsl:when test="string-length(sc:observation/sc:value/@value)>0">																					
															<td colspan="1" class="doclabel"><xsl:value-of select="sc:observation/sc:code/@displayName"/>:</td>
															<td colspan="7" class="docdataitem"><xsl:value-of select="sc:observation/sc:value/@value"/><xsl:value-of select="sc:observation/sc:value/@unit"/> </td>											
												</xsl:when>
												<xsl:when test="string-length(sc:observation/sc:value/@value)=0">																						
															<td colspan="1" class="doclabel"><xsl:value-of select="sc:observation/sc:code/@displayName"/>:</td>
															<td colspan="7" class="docdataitem"><xsl:value-of select="sc:observation/sc:value"/><xsl:value-of select="sc:observation/sc:text/@value"/><xsl:value-of select="sc:observation/sc:text"/></td>		
												</xsl:when>	
											</xsl:choose>	
										</xsl:if>      	
							        </tr>
						        </xsl:for-each>					        
						    </table>
						</xsl:if>
						
						<xsl:if test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code[@displayName='乙型病毒性肝炎']/../sc:entryRelationship/@typeCode)>0">
							<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
								<tr>
						            <td colspan="8" class="blockHeader" height="25" align="center" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code[@displayName='乙型病毒性肝炎']/@displayName"/>(附卡)</td>
						        </tr>
						        <xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code[@displayName='乙型病毒性肝炎']/../sc:entryRelationship/sc:organizer/sc:component">
						        <tr>
						        	<xsl:choose>
										<xsl:when test="string-length(sc:observation/sc:value/@value)>0">														
											<td colspan="1" class="doclabel"><xsl:value-of select="sc:observation/sc:code/@displayName"/>:</td>
											<td colspan="7" class="docdataitem"><xsl:value-of select="sc:observation/sc:value/@value"/> <xsl:value-of select="sc:observation/sc:value/@unit"/></td>
										</xsl:when>
										<xsl:when test="string-length(sc:observation/sc:value/@value)=0">
												<td colspan="1" class="doclabel"><xsl:value-of select="sc:observation/sc:code/@displayName"/>:</td>
												<td colspan="7" class="docdataitem"><xsl:value-of select="sc:observation/sc:value"/></td>
										</xsl:when>
									</xsl:choose>					        	
						        </tr>
						        </xsl:for-each>
						    </table>
					    </xsl:if>
					    <xsl:if test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code[@displayName='甲型H1N1流感']/../sc:entryRelationship/@typeCode)>0">
							<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
								<tr>
						            <td colspan="8" class="blockHeader" height="25" align="center" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code[@displayName='甲型H1N1流感']/@displayName"/>(附卡)</td>
						        </tr>
						        <xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code[@displayName='甲型H1N1流感']/../sc:entryRelationship/sc:organizer/sc:component">
						        <tr>
						        	<xsl:choose>
										<xsl:when test="string-length(sc:observation/sc:value/@value)>0">
											<td colspan="1" class="doclabel"><xsl:value-of select="sc:observation/sc:code/@displayName"/>:</td>
											<td colspan="7" class="docdataitem"><xsl:value-of select="sc:observation/sc:value/@value"/> <xsl:value-of select="sc:observation/sc:value/@unit"/></td>
										</xsl:when>
										<xsl:when test="string-length(sc:observation/sc:value/@value)=0">
											<td colspan="1" class="doclabel"><xsl:value-of select="sc:observation/sc:code/@displayName"/>:</td>
											<td colspan="7" class="docdataitem"><xsl:value-of select="sc:observation/sc:value"/></td>
										</xsl:when>
									</xsl:choose>					        	
						        </tr>
						        </xsl:for-each>
						    </table>
					    </xsl:if>
  						<xsl:if test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code[@displayName='手足口病']/../sc:entryRelationship/@typeCode)>0">
							<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
								<tr>
						            <td colspan="8" class="blockHeader" height="25" align="center" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code[@displayName='手足口病']/@displayName"/>(附卡)</td>
						        </tr>
						        <xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code[@displayName='手足口病']/../sc:entryRelationship/sc:organizer/sc:component">
						        <tr>
						        	<xsl:choose>
										<xsl:when test="string-length(sc:observation/sc:value/@value)>0">
											<td colspan="1" class="doclabel"><xsl:value-of select="sc:observation/sc:code/@displayName"/>:</td>
											<td colspan="7" class="docdataitem"><xsl:value-of select="sc:observation/sc:value/@value"/> <xsl:value-of select="sc:observation/sc:value/@unit"/></td>
										</xsl:when>
										<xsl:when test="string-length(sc:observation/sc:value/@value)=0">
											<td colspan="1" class="doclabel"><xsl:value-of select="sc:observation/sc:code/@displayName"/>:</td>
											<td colspan="7" class="docdataitem"><xsl:value-of select="sc:observation/sc:value"/></td>
										</xsl:when>
									</xsl:choose>					        	
						        </tr>
						        </xsl:for-each>
						    </table>
					    </xsl:if>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>