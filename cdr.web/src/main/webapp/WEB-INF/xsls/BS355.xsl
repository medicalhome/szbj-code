<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:sc="urn:hl7-org:v3" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:Constants="xalan://com.founder.cdr.core.Constants">
	<xsl:output method="html" indent="yes" version="4.0"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" />
	<xsl:template match="/sc:ClinicalDocument">
		<html>
			<head>
				<meta http-equiv="Pragma" content="no-cache"/>
				<meta http-equiv="Cache-Control" content="no-cache"/>
				<meta http-equiv="Expires" content="0"/>
				<link type="text/css" rel="Stylesheet" href="../../styles/table.css" charset="UTF8" />
				<link type="text/css" rel="Stylesheet" href="../../styles/layout.css" charset="UTF8" />
				<style>
				.responsabilit
				{
					width:100%;
					text-align:center;
					color:red;
				}
				 .blws3{
				  text-align: left;
				  padding-left:15px;
				  background-color: #FFFFFF;
				  line-height:20px;
				  }
				</style>
			</head>
			<body>
				<div id="mainContent">
					<div name="selectTabs">
						<div class="responsabilit">
							<xsl:value-of select="Constants:getResponsabilityClaimingContent()"/>
						</div>
						<xsl:choose>
						<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.117'
							and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@code='084'">
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="2" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"></td>
								<td colspan="6" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;">
									<h3><xsl:value-of select="sc:title"/></h3>
								</td>								
								<td class="blockHeader" align="right" style="background-color:#fcffe2;vertical-align:bottom;border-bottom: solid 1px #B3C4D4;font-weight: bold;">骨髓片号：</td>
								<td class="reportnumber" height="30" align="left" style="background-color:#fcffe2;vertical-align:bottom;border-bottom: solid 1px #B3C4D4;font-weight: bold;">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:id/@extension"/>
								</td>
							</tr>							
							<tr class="odd">
								<td class="doclabel">姓名：</td>
								<td class="docdataitem1">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/>
								</td>
								<td class="doclabel">性别：</td>
								<td class="docdataitem1">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/>
								</td>
								<td class="doclabel">年龄：</td>
								<td class="docdataitem1">
								  <xsl:choose>
									<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code/@code='397669002'">
										<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value"/>
									</xsl:when>
								  </xsl:choose>
								</td>
								<td class="doclabel">科别：</td>
								<td class="docdataitem1">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation">
								<xsl:if test="sc:value/@codeSystem='1.2.156.112656.1.1.33'">
								<xsl:value-of select="sc:value"/>
								</xsl:if>
								</xsl:for-each>	
								</td>
								<td class="doclabel" >就诊卡号：</td>
								<td class="docdataitem1">
								<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='01' or sc:componentOf/sc:encompassingEncounter/sc:code/@code='0201'">
									<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
											<xsl:choose>
												<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
												<xsl:value-of select="@extension"/>
												</xsl:when>
											</xsl:choose>		
									</xsl:for-each></xsl:if>
								</td>								
							</tr>
							<tr class="odd">
								<td class="doclabel">送检医院：</td>
								<td class="docdataitem1">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:participantRole/sc:playingEntity/sc:name"/>
								</td>
								<td class="doclabel">取材部位：</td>
								<td class="docdataitem1" colspan="3">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:targetSiteCode/@displayName"/>
								</td>							
								<td class="doclabel">床位号：</td>
								<td class="docdataitem1">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
								</td>
								<td class="doclabel">住院号：</td>
								<td class="docdataitem1">
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
							<tr class="odd">
								<td class="doclabel">影像：</td>
								<td class="docdataitem1" colspan="9">
								<xsl:choose>
									<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension !=''
									">
									<xsl:element name="a">
										<xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension" />
										</xsl:attribute>
										<xsl:attribute name="target">_blank</xsl:attribute>
										<img src="../images/pic_jc.png" width="22" height="22" border="0" />
									</xsl:element>									
									</xsl:when>
								</xsl:choose>
								</td>
							</tr>
						<tr>
						<td colspan="6" style="padding-top:0px;vertical-align:top;">
						<div style="margin:2px;">
						<table align="left" width="100%" cellspacing="1" cellpadding="2" style="margin-left:5px;border-collapse:collapse;border:1px solid #c0ddea;table-layout:fixed;bgcolor:white;">
						<tr>
							<td class="label" rowspan="2" colspan="3">细胞名称</td>
							<td class="label">血片</td>
							<td class="label" colspan="3">骨髓片</td>
						</tr>
						<tr>
							<td class="label">(%)</td>
							<td class="label">平均值</td>
							<td class="label">标准差</td>
							<td class="label">(%)</td>
						</tr>
						<tr>
							<td class="label" colspan="3">原始血细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='1'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value =0 or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
							</xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='2'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='2'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='2' and (sc:observation/sc:value !=0 or sc:observation/sc:value !='')" >
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
					        </xsl:if>
					        </xsl:for-each>									
							</td>
						</tr>							
						<tr>
							<td class="leftlabel" rowspan="14">粒细胞系统</td>
							<td class="label" colspan="2">原始粒细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='3'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='4'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='4'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='4'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
						</tr>
						<tr>
							<td class="label" colspan="2">早幼粒细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='5'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='6'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='6'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='6'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>							
							</td>								
						</tr>
						<tr>
							<td class="label" rowspan="4">中性</td>
							<td class="label">中幼</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='7'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='8'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='8'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='8'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>						
							</td>							
						</tr>
						<tr>
							<td class="label">晚幼</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='9'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='10'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='10'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>									
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='10'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>							
							</td>							
						</tr>
						<tr>
							<td class="label">杆状核</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='11'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='12'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='12'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='12'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>								
						</tr>
						<tr>
							<td class="label">分叶核</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='13'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='14'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='14'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='14'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>							
						</tr>
						<tr>
							<td class="label" rowspan="4">嗜酸</td>
							<td class="label">中幼</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='15'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='16'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='16'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>									
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='16'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>								
						</tr>
						<tr>
							<td class="label">晚幼</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='17'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='18'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='18'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>									
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='18'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>							
						</tr>
						<tr>
							<td class="label">杆状核</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='19'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='20'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='20'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='20'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>							
						</tr>
						<tr>
							<td class="label">分叶核</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='21'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='22'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='22'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='22'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>							
							</td>							
						</tr>
						<tr>
							<td class="label" rowspan="4">嗜碱</td>
							<td class="label">中幼</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='23'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='24'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='24'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>									
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='24'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>							
						</tr>
						<tr>
							<td class="label">晚幼</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='25'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='26'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='26'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='26'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>								
						</tr>
						<tr>
							<td class="label">杆状核</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='27'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='28'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='28'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='28'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>									
							</td>							
						</tr>
						<tr>
							<td class="label">分叶核</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='29'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='30'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='30'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='30'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>							
						</tr>	
						<tr>
							<td class="leftlabel" rowspan="7">红细胞系统</td>
							<td class="label" colspan="2">原始红细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='31'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='32'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='32'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='32'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>								
						</tr>
						<tr>
							<td class="label" colspan="2">早幼红细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='33'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='34'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='34'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='34'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>								
						</tr>
						<tr>
							<td class="label" colspan="2">中幼红细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='35'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='36'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='36'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='36'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>							
						</tr>
						<tr>
							<td class="label" colspan="2">晚幼红细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='37'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='38'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='38'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='38'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>							
						</tr>
						<tr>
							<td class="label" colspan="2">早巨幼红细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='39'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='40'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='40'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='40'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>							
						</tr>
						<tr>
							<td class="label" colspan="2">中巨幼红细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='41'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='42'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='42'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>									
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='42'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>							
							</td>							
						</tr>
						<tr>
							<td class="label" colspan="2">晚巨幼红细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='43'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='44'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='44'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='44'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>									
							</td>							
						</tr>
						<tr>
							<td class="label" colspan="3">粒系：红系</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='45'">
							<xsl:value-of select="sc:observation/sc:value"/>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='46'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='46'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='46'">
							<xsl:value-of select="sc:observation/sc:value"/>
					        </xsl:if>
					        </xsl:for-each>									
							</td>							
						</tr>
						<tr>
							<td class="leftlabel" rowspan="4">淋巴细胞</td>
							<td class="label" colspan="2">原始淋巴细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='47'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='48'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='48'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='48'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>								
						</tr>
						<tr>
							<td class="label" colspan="2">幼稚淋巴细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='49'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='50'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='50'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='50'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>							
						</tr>
						<tr>
							<td class="label" colspan="2">成熟淋巴细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='51'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='52'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='52'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='52'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>									
							</td>							
						</tr>
						<tr>
							<td class="label" colspan="2">异形淋巴细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='53'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='54'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='54'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='54'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>								
						</tr>
						<tr>
							<td class="leftlabel" rowspan="3">单核</td>
							<td class="label" colspan="2">原始单核细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='55'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='56'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='56'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>									
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='56'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>							
						</tr>
						<tr>
							<td class="label" colspan="2">幼稚单核细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='57'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='58'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='58'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>									
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='58'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>							
						</tr>
						<tr>
							<td class="label" colspan="2">成熟单核细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='59'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='60'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='60'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='60'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>								
						</tr>
						<tr>
							<td class="leftlabel" rowspan="3">浆细胞</td>
							<td class="label" colspan="2">原始浆细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='61'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='62'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='62'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='62'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>							
						</tr>
						<tr>
							<td class="label" colspan="2">幼稚浆细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='63'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='64'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='64'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='64'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>								
						</tr>
						<tr>
							<td class="label" colspan="2">成熟浆细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='65'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='66'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='66'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='66'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>								
						</tr>
						<tr>
							<td class="leftlabel" rowspan="4">其他细胞</td>
							<td class="label" colspan="2">组织细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='67'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='68'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='68'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>									
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='68'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>								
						</tr>
						<tr>
							<td class="label" colspan="2">组织嗜碱细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='69'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='70'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='70'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='70'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>									
							</td>							
						</tr>
						<tr>
							<td class="label" colspan="2">异常细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='71'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='72'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='72'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>									
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='72'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>									
							</td>							
						</tr>
						<tr>
							<td class="label" colspan="2">组织嗜酸细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='73'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='74'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='74'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='74'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>								
						</tr>
						<tr>
							<td class="leftlabel" rowspan="6">巨核细胞</td>
							<td class="label" colspan="2">原始巨核细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='75'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='76'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>						
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='76'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='76'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>									
							</td>							
						</tr>
						<tr>
							<td class="label" colspan="2">幼稚巨核细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='77'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='78'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='78'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='78'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>									
							</td>							
						</tr>
						<tr>
							<td class="label" colspan="2">颗粒巨核细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='79'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='80'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='80'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='80'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>								
						</tr>
						<tr>
							<td class="label" colspan="2">产板巨核细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='81'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='82'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='82'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='82'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>								
						</tr>
						<tr>
							<td class="label" colspan="2">裸核巨核细胞</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='83'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='84'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='84'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='84'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>									
							</td>								
						</tr>
						<tr>
							<td class="label" colspan="2">巨核总数</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='85'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='86'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='86'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>								
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='86'">
							<xsl:choose>
							<xsl:when test="sc:observation/sc:value ='0' or sc:observation/sc:value =''">
							<xsl:text></xsl:text>
							</xsl:when>
							<xsl:otherwise>
							<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
							</xsl:otherwise>
							</xsl:choose>
					        </xsl:if>
					        </xsl:for-each>								
							</td>								
						</tr>
						<tr>
							<td class="label" colspan="3">计数(个)</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='87'">
							<xsl:value-of select="sc:observation/sc:value"/>
					        </xsl:if>
					        </xsl:for-each>
							</td>							
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='88'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A21'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>							
							</td>
							<td class="label">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='88'">
							<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='A22'">
							<xsl:value-of select="sc:value"/>
							</xsl:if>
							</xsl:for-each>
					        </xsl:if>
					        </xsl:for-each>									
							</td>
							<td class="datavalue">
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:sequenceNumber/@value='88'">
							<xsl:value-of select="sc:observation/sc:value"/>
					        </xsl:if>
					        </xsl:for-each>									
							</td>								
						</tr>																																																																																																																																																						
						</table>
						</div>
						</td>
						<td colspan="4" style="padding-top:0px;vertical-align:top;"> 
						<div style="margin:3px;">
						<table align="center" width="100%" cellspacing="1" cellpadding="2" style="border:0px solid #c0ddea;table-layout:fixed;bgcolor:white;word-wrap:break-word;">
							<tr>
							  <td>
								<div>
								<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-top: solid 1px #B3C4D4;border-bottom: solid 1px #B3C4D4;bgcolor:white;">		
								<tr>
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:observationMedia">
								<xsl:if test="sc:value !=''">
								<xsl:if test="last() = 1">
								<td colspan="2" align="center">
								<div style="margin:5px;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:value" /></xsl:attribute>
									<xsl:attribute name="width">140px</xsl:attribute>
									<xsl:attribute name="hight">140px</xsl:attribute> 
									<xsl:attribute name="hspace">4px</xsl:attribute>
									<xsl:attribute name="vspace">20px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
								</xsl:if>
								<xsl:if test="last() > 1">
								<td align="center">
								<div style="margin:5px;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:value" /></xsl:attribute>
									<xsl:attribute name="width">140px</xsl:attribute>
									<xsl:attribute name="hight">140px</xsl:attribute> 
									<xsl:attribute name="hspace">4px</xsl:attribute>
									<xsl:attribute name="vspace">20px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
								</xsl:if>
								<xsl:if test="position() mod 2=0">
								<tr>
								</tr>
								</xsl:if>
								</xsl:if>
								</xsl:for-each>
								</tr>
								</table>
								</div>
								</td>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='72724002'">
							<tr>
								<td class="blockHeader" align="left" style="padding-left: 3px;font-weight: bold;">
									分析
								</td>
							</tr>						
							<tr><td class="blws2" style="margin-top:3px;word-break:break-all;word-wrap:break-word;">
								<pre><xsl:value-of select="sc:value"/></pre>
								</td>
							</tr>
							</xsl:if>
							<xsl:if test="vcode/@code='118246004'">
							<tr>
								<td class="blockHeader" align="left" style="padding-left: 3px;font-weight: bold;">
									意见
								</td>
							</tr>							
							<tr><td class="blws2" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:value"/></pre>
								</td>
							</tr>								
							</xsl:if>
							</xsl:for-each>		
						</table>
						</div>
						</td>
						</tr>
						<tr>
						<td colspan="10" style="padding:0px;">
						<div>
						  <table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">							
							<tr class="odd" valign="bottom">
								<td class="footerlabel">报告人：</td>
								<td class="footerdataitem">
									<xsl:for-each select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name">
										<xsl:value-of select="."/>
										<xsl:if test="position()!=last()">&#160;</xsl:if>
									</xsl:for-each>									
								</td>
								<td class="footerlabel">审查人：</td>
								<td class="footerdataitem">
									<xsl:for-each select="sc:authenticator/sc:assignedEntity/sc:assignedPerson/sc:name">	
										<xsl:value-of select="."/>
										<xsl:if test="position()!=last()">&#160;</xsl:if>
									</xsl:for-each>								
								</td>
								<td class="footerlabel">送检日期：</td>
								<td class="footerdataitem">	
									<xsl:choose>
									<xsl:when test="string-length(sc:participant[@typeCode='DIST']/sc:time/@value)>0">
										<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,7,2)"/>
									</xsl:when>
									</xsl:choose>								
								</td>	
								<td class="footerlabel">审核日期：</td>
								<td class="footerdataitem">	
									<xsl:choose>
									<xsl:when test="string-length(sc:authenticator/sc:time/@value)>0">
										<xsl:value-of select="substring(sc:authenticator/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:authenticator/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:authenticator/sc:time/@value,7,2)"/>
									</xsl:when>
									</xsl:choose>								
								</td>
								<td class="footerlabel">报告日期：</td>
								<td class="footerdataitem">	
									<xsl:choose>
										<xsl:when test="string-length(sc:author/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>																															
							</tr>
							<tr class="odd">
								<td class="note" colspan="10">注:本次结果只对该标本负责</td>
							</tr>							
						  </table>
						</div>
						</td>
						</tr>													
						</table>												
						</xsl:when>
						<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.117'
							and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@code='091'">
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">						
							<xsl:choose>
							<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:observationMedia/sc:value !=''">
							<tr>
								<td colspan="4" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;">
									<h3><xsl:value-of select="sc:title"/></h3>
								</td>
							</tr>								
							<tr class="odd">
								<td class="doclabel">姓名：</td>
								<td class="docdataitem" style="border-right:solid 1px #c0ddea;">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/>
								</td>
								<td class="docdataitem" rowspan="11" colspan="2" align="center">
								<div style="text-align:center;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:observationMedia/sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:observationMedia/sc:value" /></xsl:attribute>
									<xsl:attribute name="width">240px</xsl:attribute>
									<xsl:attribute name="hight">240px</xsl:attribute> 
									<xsl:attribute name="hspace">3px</xsl:attribute>
									<xsl:attribute name="vspace">3px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">性别：</td>
								<td class="docdataitem" style="border-right:solid 1px #c0ddea;">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">年龄：</td>
								<td class="docdataitem" style="border-right:solid 1px #c0ddea;">
								  <xsl:choose>
										<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code/@code='397669002'">
											<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value"/>
										</xsl:when>
								  </xsl:choose>
								</td>
							</tr>						
							<tr class="odd">
								<td class="doclabel">检查号：</td>
								<td class="docdataitem" style="border-right:solid 1px #c0ddea;">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:id/@extension"/>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel" >住院号：</td>
								<td class="docdataitem" style="border-right:solid 1px #c0ddea;">
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
							<tr class="odd">	
								<td class="doclabel">床位：</td>
								<td class="docdataitem" style="border-right:solid 1px #c0ddea;">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
								</td>
							</tr>
							<tr class="odd">	
								<td class="doclabel">病区：</td>
								<td class="docdataitem" style="border-right:solid 1px #c0ddea;">						
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation">
								<xsl:if test="sc:value/@codeSystem='1.2.156.112656.1.1.33'">
								<xsl:value-of select="sc:value"/>
								</xsl:if>
								</xsl:for-each>								 
								</td>	
							</tr>
							<tr class="odd">								
								<td class="doclabel">标本：</td>
								<td class="docdataitem" style="border-right:solid 1px #c0ddea;">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:targetSiteCode/@displayName"/>
								</td>
							</tr>
							<tr class="odd">								
								<td class="doclabel">采集日期：</td>
								<td class="docdataitem" style="border-right:solid 1px #c0ddea;">
									<xsl:choose>
									<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:effectiveTime/@value)>0">
										<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:effectiveTime/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:effectiveTime/@value,5,2)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:effectiveTime/@value,7,2)"/>
									</xsl:when>
									</xsl:choose>								
								</td>
							</tr>	
							<tr class="odd">								
								<td class="doclabel">临床诊断：</td>
								<td class="docdataitem" style="border-right:solid 1px #c0ddea;">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:act/sc:entryRelationship/sc:observation/sc:value/@displayName"/>
								</td>
							</tr>													
							<tr class="odd">
								<td class="doclabel">影像：</td>
								<td class="docdataitem" style="border-right:solid 1px #c0ddea;">
								<xsl:choose>
									<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension !=''
									">
									<xsl:element name="a">
										<xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension" />
										</xsl:attribute>
										<xsl:attribute name="target">_blank</xsl:attribute>
										<img src="../images/pic_jc.png" width="22" height="22" border="0" />
									</xsl:element>									
									</xsl:when>
								</xsl:choose>
								</td>
							</tr>							
							<tr>
								<td colspan="4">
								<div>
								<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-top: solid 1px #B3C4D4;bgcolor:white;">		
								<tr>							
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:observationMedia">
								<xsl:if test="sc:value !=''">
								<xsl:if test="last()=2 and position()=2">
								<td colspan="2" align="center">
								<div style="margin:5px;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:value" /></xsl:attribute>
									<xsl:attribute name="width">700px</xsl:attribute> 
									<xsl:attribute name="hspace">4px</xsl:attribute>
									<xsl:attribute name="vspace">10px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
								</xsl:if>
								<xsl:if test="last() > 2 and position() >2">
								<td align="center">
								<div style="margin:5px;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:value" /></xsl:attribute>
									<xsl:attribute name="width">350px</xsl:attribute> 
									<xsl:attribute name="hspace">4px</xsl:attribute>
									<xsl:attribute name="vspace">10px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
								</xsl:if>
								<xsl:if test="(position() + 1) mod 2=0">
								<tr>
								</tr>
								</xsl:if>
								</xsl:if>
								</xsl:for-each>
								</tr>
								</table>
								</div>
								</td>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">							
							<xsl:if test="sc:code/@code='118246004'">						
							<tr>
								<td colspan="4" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
									结果:
								</td>
							</tr>							
							<tr height="20">
								<td colspan="4" class="blws" style="word-break:break-all;word-wrap:break-word;">
								<pre><xsl:value-of select="sc:value"/></pre>
								</td>
							</tr>
							</xsl:if>
							</xsl:for-each>
							<tr>
							<td colspan="4" style="padding:0px;">	
							<div name="selectTabs" style="position: relative; bottom: 0px;">
						  	<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr class="odd">
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">报告者:</td>
								<td class="docdataitem"  style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name">
										<xsl:value-of select="."/>
										<xsl:if test="position()!=last()">&#160;</xsl:if>
									</xsl:for-each>						
								</td>
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">审核者:</td>
								<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
								<xsl:for-each select="sc:authenticator/sc:assignedEntity/sc:assignedPerson/sc:name">
									<xsl:value-of select="."/>	
									<xsl:if test="position()!=last()">&#160;</xsl:if>									
								</xsl:for-each>
								</td>
								<td class="doclabel"  style="border-top: solid 1px #B3C4D4;">报告日期：</td>
								<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
									<xsl:choose>
										<xsl:when test="string-length(sc:author/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>								
							</tr>
							<tr>
								<td class="note" colspan="6">注:本次结果只对该标本负责</td>
							</tr>						  
						  </table>
						  </div>
						  </td>
						  </tr>																																										
							</xsl:when>
							<xsl:otherwise>
							<tr>
								<td colspan="8" height="30" align="center" style="border-bottom: solid 1px #B3C4D4;">
									<h3 class="reporttitle"><xsl:value-of select="sc:title"/></h3>
								</td>
							</tr>								
							<tr class="odd">
								<td class="doclabel">姓名：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/>
								</td>
								<td class="doclabel">性别：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/>
								</td>
								<td class="doclabel">年龄：</td>
								<td class="docdataitem">
								  <xsl:choose>
										<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code/@code='397669002'">
											<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value"/>
										</xsl:when>
								  </xsl:choose>
								</td>
								<td class="doclabel">检查号：</td>
								<td class="docdataitem">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:id/@extension"/>
								</td>								
							</tr>
							<tr class="odd">	
								<td class="doclabel">病区：</td>
								<td class="docdataitem">						
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation">
								<xsl:if test="sc:value/@codeSystem='1.2.156.112656.1.1.33'">
								<xsl:value-of select="sc:value"/>
								</xsl:if>
								</xsl:for-each>								 
								</td>	
								<td class="doclabel">床位：</td>
								<td class="docdataitem" colspan="3">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
								</td>
								<td class="doclabel" >住院号：</td>
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
								<td class="doclabel">临床诊断：</td>
								<td class="docdataitem">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:act/sc:entryRelationship/sc:observation/sc:value/@displayName"/>
								</td>
								<td class="doclabel">标本：</td>
								<td class="docdataitem" colspan="3">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:targetSiteCode/@displayName"/>
								</td>
								<td class="doclabel">采集日期：</td>	
								<td class="docdataitem">
									<xsl:choose>
									<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:effectiveTime/@value)>0">
										<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:effectiveTime/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:effectiveTime/@value,5,2)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:effectiveTime/@value,7,2)"/>
									</xsl:when>
									</xsl:choose>								
								</td>							
							</tr>
							<tr>
								<td class="doclabel">制备方法：</td>	
								<td class="docdataitem">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure">
									<xsl:if test="sc:code/@code='61594008'">
									<xsl:value-of select="sc:methodCode/@displayName"/>
									</xsl:if>
									</xsl:for-each>
								</td>							
								<td class="doclabel">显带技术：</td>	
								<td class="docdataitem" colspan="5">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure">
									<xsl:if test="sc:code/@code='246366009'">
									<xsl:value-of select="sc:methodCode/@displayName"/>
									</xsl:if>
									</xsl:for-each>
								</td>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='5Z11A'">
							<tr>
								<td class="doclabel"><xsl:value-of select="sc:code/@displayName"/>:</td>
								<td class="docdataitem" colspan="7">
								<xsl:value-of select="sc:value/@value"/>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">影像：</td>
								<td class="docdataitem" colspan="7">
								<xsl:choose>
									<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension !=''
									">
									<xsl:element name="a">
										<xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension" />
										</xsl:attribute>
										<xsl:attribute name="target">_blank</xsl:attribute>
										<img src="../images/pic_jc.png" width="22" height="22" border="0" />
									</xsl:element>									
									</xsl:when>
								</xsl:choose>
								</td>
							</tr>							
							</xsl:if>
							</xsl:for-each>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">							
							<xsl:if test="sc:code/@code='118246004'">						
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
									结果:
								</td>
							</tr>							
							<tr height="20">
								<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
								<pre><xsl:value-of select="sc:value"/></pre>
								</td>
							</tr>
							</xsl:if>
							</xsl:for-each>	
							<tr>
							<td colspan="8" style="padding:0px;">	
							<div name="selectTabs" style="position: relative bottom: 0px;">
						  	<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr class="odd">
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">报告者:</td>
								<td class="docdataitem"  style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name">
										<xsl:value-of select="."/>
										<xsl:if test="position()!=last()">&#160;</xsl:if>
									</xsl:for-each>						
								</td>
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">审核者:</td>
								<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select="sc:authenticator/sc:assignedEntity/sc:assignedPerson/sc:name">
										<xsl:value-of select="."/>
										<xsl:if test="position()!=last()">&#160;</xsl:if>
									</xsl:for-each>										
								</td>
								<td class="doclabel"  style="border-top: solid 1px #B3C4D4;">报告日期：</td>
								<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
									<xsl:choose>
										<xsl:when test="string-length(sc:author/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>								
							</tr>
							<tr>
								<td class="note" colspan="6">注:本次结果只对该标本负责</td>
							</tr>													  
						  </table>
						  </div>
						  </td>
						  </tr>														
							</xsl:otherwise>
							</xsl:choose>										
						</table>						
						</xsl:when>
						<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.117'
							and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@code='801'">
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="2" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"></td>
								<td colspan="4" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;">
									<h3><xsl:value-of select="sc:title"/></h3>
								</td>								
								<td class="blockHeader" height="30" align="right" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;font-weight: bold;">检查号：</td>
								<td class="reportnumber" height="30" align="left" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;font-weight: bold;">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:id/@extension"/>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">姓名：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/>
								</td>
								<td class="doclabel">性别：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/>
								</td>
								<td class="doclabel">年龄：</td>
								<td class="docdataitem">
								  <xsl:choose>
										<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code/@code='397669002'">
											<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value"/>
										</xsl:when>
								  </xsl:choose>
								</td>
								<td class="doclabel" >住院号：</td>
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
							<tr class="odd">	
								<td class="doclabel">地址：</td>
								<td class="docdataitem">						
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation">
								<xsl:if test="sc:value/@codeSystem='1.2.156.112656.1.1.33'">
								<xsl:value-of select="sc:value"/>
								</xsl:if>
								</xsl:for-each>								 
								</td>	
								<td class="doclabel">床位：</td>
								<td class="docdataitem" colspan="5">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
								</td>															
							</tr>
							<tr>
								<td class="doclabel">临床诊断：</td>
								<td class="docdataitem">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:act/sc:entryRelationship/sc:observation/sc:value/@displayName"/>
								</td>
								<td class="doclabel">标本：</td>
								<td class="docdataitem" colspan="3">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:targetSiteCode/@displayName"/>
								</td>
								<td class="doclabel">送检日期：</td>	
								<td class="docdataitem">
									<xsl:choose>
									<xsl:when test="string-length(sc:participant[@typeCode='DIST']/sc:time/@value)>0">
										<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,7,2)"/>
									</xsl:when>
									</xsl:choose>								
								</td>							
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='01'">
							<tr class="odd">
								<td class="doclabel">基因：</td>
								<td class="docdataitem" colspan="7">
								<xsl:value-of select="sc:value"/>
								</td>
							</tr>
							</xsl:if>
							</xsl:for-each>
							<tr class="odd">
							<td class="doclabel">影像：</td>
								<td colspan="7" style="border-bottom: solid 1px #B3C4D4;">
								<xsl:choose>
									<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension !=''
									">
									<xsl:element name="a">
										<xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension" />
										</xsl:attribute>
										<xsl:attribute name="target">_blank</xsl:attribute>
										<img src="../images/pic_jc.png" width="22" height="22" border="0" />
									</xsl:element>									
									</xsl:when>
								</xsl:choose>
								</td>
							</tr>							
							<xsl:choose>
								<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:observationMedia/sc:value!=''">
							<tr>
								<td colspan="8">
								<div>
								<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">		
								<tr>
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:observationMedia">
								<xsl:if test="sc:value !=''">
								<xsl:if test="last() = 1">
								<td colspan="2" align="center">
								<div style="margin:5px;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:value" /></xsl:attribute>
									<xsl:attribute name="width">700px</xsl:attribute>
									<xsl:attribute name="hspace">4px</xsl:attribute>
									<xsl:attribute name="vspace">10px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
								</xsl:if>
								<xsl:if test="last() > 1">
								<td align="center">
								<div style="margin:5px;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:value" /></xsl:attribute>
									<xsl:attribute name="width">350px</xsl:attribute>
									<xsl:attribute name="hspace">4px</xsl:attribute>
									<xsl:attribute name="vspace">10px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
								</xsl:if>
								<xsl:if test="position() mod 2=0">
								<tr>
								</tr>
								</xsl:if>
								</xsl:if>
								</xsl:for-each>
								</tr>
								</table>
								</div>
								</td>
							</tr>
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; font-weight: bold;">
									备注:
								</td>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<tr height="20">
								<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">																	
									<xsl:value-of select="sc:observation/sc:value"/>								
								</td>
							</tr>
							</xsl:for-each>							
							</xsl:when>
							</xsl:choose>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">							
							<xsl:if test="sc:code/@code='118246004'">						
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
									结果:
								</td>
							</tr>							
							<tr>
								<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
								<pre><xsl:value-of select="sc:value"/></pre>
								</td>
							</tr>
							</xsl:if>
							</xsl:for-each>
							<xsl:choose>
							<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:interpretationCode">
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
									注:
								</td>
							</tr>
							<tr>
								<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:text"/></pre>
								</td>
							</tr>
							</xsl:when>
							</xsl:choose>	
							<tr>
							<td colspan="8" style="padding:0px;">
							<div style="position: relative; bottom: 0px;">
						  	<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-top: solid 1px #B3C4D4;bgcolor:white;">
							<tr class="odd">
								<td class="doclabel">报告者:</td>
								<td class="docdataitem">
									<xsl:for-each select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name">
										<xsl:value-of select="."/>
										<xsl:if test="position()!=last()">&#160;</xsl:if>
									</xsl:for-each>						
								</td>
								<td class="doclabel">报告日期：</td>
								<td class="docdataitem">
									<xsl:choose>
										<xsl:when test="string-length(sc:author/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>								
							</tr>
							<tr>
								<td class="note" colspan="4">注:本次结果只对该标本负责</td>
							</tr>						  
						  </table>
						  </div>
						  </td>							
							</tr>											
						</table>							
						</xsl:when>
						<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.117'
							and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@code='803'">
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="2" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"></td>
								<td colspan="4" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;">
									<h3><xsl:value-of select="sc:title"/></h3>
								</td>								
								<td class="blockHeader" height="30" align="right" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;font-weight: bold;">检查号：</td>
								<td class="reportnumber" height="30" align="left" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;font-weight: bold;">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:id/@extension"/>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">姓名：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/>
								</td>
								<td class="doclabel">性别：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/>
								</td>
								<td class="doclabel">年龄：</td>
								<td class="docdataitem">
								  <xsl:choose>
										<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code/@code='397669002'">
											<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value"/>
										</xsl:when>
								  </xsl:choose>
								</td>
								<td class="doclabel" >住院号：</td>
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
							<tr class="odd">	
								<td class="doclabel">地址：</td>
								<td class="docdataitem">						
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation">
								<xsl:if test="sc:value/@codeSystem='1.2.156.112656.1.1.33'">
								<xsl:value-of select="sc:value"/>
								</xsl:if>
								</xsl:for-each>								 
								</td>	
								<td class="doclabel">床位：</td>
								<td class="docdataitem" colspan="5">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
								</td>															
							</tr>
							<tr>
								<td class="doclabel">临床诊断：</td>
								<td class="docdataitem">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:act/sc:entryRelationship/sc:observation/sc:value/@displayName"/>
								</td>
								<td class="doclabel">标本：</td>
								<td class="docdataitem" colspan="3">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:targetSiteCode/@displayName"/>
								</td>
								<td class="doclabel">送检日期：</td>	
								<td class="docdataitem">
									<xsl:choose>
									<xsl:when test="string-length(sc:participant[@typeCode='DIST']/sc:time/@value)>0">
										<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,7,2)"/>
									</xsl:when>
									</xsl:choose>								
								</td>							
							</tr>
							<tr class="odd">
								<td class="doclabel">基因：</td>
								<td class="docdataitem" colspan="3">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation[sc:code/@code='01']/sc:value"/>
								</td>
								
								<td class="doclabel">初始治疗日期：</td>
								<td class="docdataitem" colspan="3">
									<xsl:choose>
									<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:effectiveTime/sc:low/@value)>0">
										<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:effectiveTime/sc:low/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:effectiveTime/sc:low/@value,6,2)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:effectiveTime/sc:low/@value,9,2)"/>
									</xsl:when>
									</xsl:choose>								
								</td>								
							</tr>
							<tr class="odd">
							<td class="doclabel">影像：</td>
								<td colspan="7" style="border-bottom: solid 1px #B3C4D4;">
								<xsl:choose>
									<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension !=''
									">
									<xsl:element name="a">
										<xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension" />
										</xsl:attribute>
										<xsl:attribute name="target">_blank</xsl:attribute>
										<img src="../images/pic_jc.png" width="22" height="22" border="0" />
									</xsl:element>									
									</xsl:when>
								</xsl:choose>
								</td>
							</tr>
							<tr class="odd">
								<td class="docdataitem" style="text-align:center;word-break:break-all;word-wrap:break-word;" colspan="8">
									<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation[sc:code/@codeSystem='2.16.840.1.113883.6.96' and sc:code/@code='72724002']/sc:value"/></pre>
								</td>								
							</tr>														
							<xsl:choose>
							<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:observationMedia/sc:value!=''">
							<tr>
								<td colspan="8">
								<div style="position: relative; bottom: 0px;">
								<table border="0" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 0px #B3C4D4;bgcolor:white;">
									<tr>
										<td style="padding-left:300px;">
										<pre>
										<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:text/sc:content[@ID='a1']"/>
										</pre>										
										</td>
									</tr>
								</table>
								</div>
								<div>
								<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 0px #B3C4D4;bgcolor:white;">		
								<tr>
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:observationMedia">
								<xsl:if test="sc:value !=''">
								<xsl:if test="last() = 1">
								<td colspan="2" align="center">
								<div style="margin:5px;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:value" /></xsl:attribute>
									<xsl:attribute name="width">700px</xsl:attribute>
									<xsl:attribute name="hspace">4px</xsl:attribute>
									<xsl:attribute name="vspace">10px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
								</xsl:if>
								<xsl:if test="last() > 1">
								<td align="center">
								<div style="margin:5px;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:value" /></xsl:attribute>
									<xsl:attribute name="width">350px</xsl:attribute>
									<xsl:attribute name="hspace">4px</xsl:attribute>
									<xsl:attribute name="vspace">10px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
								</xsl:if>
								<xsl:if test="position() mod 2=0">
								<tr>
								</tr>
								</xsl:if>
								</xsl:if>
								</xsl:for-each>
								</tr>
								</table>
								</div>
								<div style="position: relative; bottom: 0px;">
								<table>
									<tr>
										<td style="padding-left:300px;">
										<pre>
										<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:text/sc:content[@ID='a2']"/>
										</pre>										
										</td>
									</tr>
								</table>
								</div>								
								</td>
							</tr>
<!-- 							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; font-weight: bold;">
									备注:
								</td>
							</tr> -->
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<tr height="20">
								<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">																	
									<xsl:value-of select="sc:observation/sc:value"/>								
								</td>
							</tr>
							</xsl:for-each>							
							</xsl:when>
							</xsl:choose>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">							
							<xsl:if test="sc:code/@code='118246004'">						
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
									结果:
								</td>
							</tr>							
							<tr>
								<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
								<pre><xsl:value-of select="sc:value"/></pre>
								</td>
							</tr>
							</xsl:if>
							</xsl:for-each>
							<xsl:choose>
							<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:interpretationCode">
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
									注:
								</td>
							</tr>
							<tr>
								<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:text"/></pre>
								</td>
							</tr>
							</xsl:when>
							</xsl:choose>	
							<tr>
							<td colspan="8" style="padding:0px;">
							<div style="position: relative; bottom: 0px;">
						  	<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-top: solid 1px #B3C4D4;bgcolor:white;">
							<tr class="odd">
								<td class="doclabel">报告者:</td>
								<td class="docdataitem">
									<xsl:for-each select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name">
										<xsl:value-of select="."/>
										<xsl:if test="position()!=last()">&#160;</xsl:if>
									</xsl:for-each>						
								</td>
								<td class="doclabel">报告日期：</td>
								<td class="docdataitem">
									<xsl:choose>
										<xsl:when test="string-length(sc:author/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>								
							</tr>
							<tr>
								<td class="note" colspan="4">注:本次结果只对该标本负责</td>
							</tr>						  
						  </table>
						  </div>
						  </td>							
							</tr>											
						</table>							
						</xsl:when>						
						<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.117'
							and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@code='092'">
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
							<tr>
								<td colspan="8" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;">
									<h3><xsl:value-of select="sc:title"/></h3>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">姓名：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/>
								</td>
								<td class="doclabel">性别：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/>
								</td>
								<td class="doclabel">年龄：</td>
								<td class="docdataitem">
								  <xsl:choose>
										<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code/@code='397669002'">
											<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value"/>
										</xsl:when>
								  </xsl:choose>
								</td>
								<td class="doclabel">检查号：</td>
								<td class="docdataitem">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:id/@extension"/>
								</td>								
							</tr>
							<tr class="odd">	
								<td class="doclabel">病区：</td>
								<td class="docdataitem">						
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation">
								<xsl:if test="sc:value/@codeSystem='1.2.156.112656.1.1.33'">
								<xsl:value-of select="sc:value"/>
								</xsl:if>
								</xsl:for-each>								 
								</td>	
								<td class="doclabel">床位：</td>
								<td class="docdataitem" colspan="3">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
								</td>
								<td class="doclabel" >住院号：</td>
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
								<td class="doclabel">临床诊断：</td>
								<td class="docdataitem">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:act/sc:entryRelationship/sc:observation/sc:value/@displayName"/>
								</td>
								<td class="doclabel">标本：</td>
								<td class="docdataitem" colspan="3">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:targetSiteCode/@displayName"/>
								</td>
								<td class="doclabel">采集日期：</td>	
								<td class="docdataitem">
									<xsl:choose>
									<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:effectiveTime/@value)>0">
										<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:effectiveTime/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:effectiveTime/@value,5,2)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:effectiveTime/@value,7,2)"/>
									</xsl:when>
									</xsl:choose>								
								</td>							
							</tr>
							<tr>
								<td class="doclabel">探针类型：</td>	
								<td class="docdataitem" colspan="7">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure">
									<xsl:if test="sc:code/@code='246366009'">
									<xsl:value-of select="sc:methodCode/@displayName"/>
									</xsl:if>
									</xsl:for-each>
								</td>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='5Z11A'">
							<tr>
								<td class="doclabel"><xsl:value-of select="sc:code/@displayName"/>:</td>
								<td class="docdataitem" colspan="7">
								<xsl:value-of select="sc:value/@value"/>
								</td>
							</tr>
							</xsl:if>
							</xsl:for-each>
							<tr class="odd">
								<td class="doclabel">影像：</td>
								<td class="docdataitem" colspan="7">
								<xsl:choose>
									<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension !=''
									">
									<xsl:element name="a">
										<xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension" />
										</xsl:attribute>
										<xsl:attribute name="target">_blank</xsl:attribute>
										<img src="../images/pic_jc.png" width="22" height="22" border="0" />
									</xsl:element>									
									</xsl:when>
								</xsl:choose>
								</td>
							</tr>							
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">							
							<xsl:if test="sc:code/@code='118246004'">						
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
									结果:
								</td>
							</tr>							
							<tr height="20">
								<td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
								<pre><xsl:value-of select="sc:value"/></pre>
								</td>
							</tr>
							</xsl:if>
							</xsl:for-each>
							<tr>
							<td colspan="8" style="padding:0px;">									
							<div name="selectTabs" style="position: relative; bottom: 0px;">
						  	<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
							<tr class="odd">
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">报告者:</td>
								<td class="docdataitem"  style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name">
										<xsl:value-of select="."/>
										<xsl:if test="position()!=last()">&#160;</xsl:if>
									</xsl:for-each>						
								</td>
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">审核者:</td>
								<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select="sc:authenticator/sc:assignedEntity/sc:assignedPerson/sc:name">
										<xsl:value-of select="."/>
										<xsl:if test="position()!=last()">&#160;</xsl:if>	
									</xsl:for-each>									
								</td>
								<td class="doclabel"  style="border-top: solid 1px #B3C4D4;">报告日期：</td>
								<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
									<xsl:choose>
										<xsl:when test="string-length(sc:author/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>								
							</tr>
							<tr>
								<td class="note" colspan="6">注:本次结果只对该标本负责</td>
							</tr>													  
						  </table>
						  </div>
						  </td>
						</tr>
					</table>							
						</xsl:when>
						<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.117'
							and (sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@code='087'
							or sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@code='088')">
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
							<tr>
								<td colspan="2" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"></td>
								<td colspan="6" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;">
									<h3><xsl:value-of select="sc:title"/></h3>
								</td>								
								<td class="blockHeader" height="30" align="right" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;font-weight: bold;">检查号：</td>
								<td class="reportnumber" height="30" align="left" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;font-weight: bold;">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:id/@extension"/>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">姓名：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/>
								</td>
								<td class="doclabel">性别：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/>
								</td>
								<td class="doclabel">年龄：</td>
								<td class="docdataitem">
								  <xsl:choose>
										<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code/@code='397669002'">
											<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value"/>
										</xsl:when>
								  </xsl:choose>
								</td>
								<td class="doclabel">标本：</td>
								<td class="docdataitem">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:targetSiteCode/@displayName"/>
								</td>
								<td class="doclabel" >就诊卡号：</td>
								<td class="docdataitem">
								<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='01' or sc:componentOf/sc:encompassingEncounter/sc:code/@code='0201'">
									<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
											<xsl:choose>
												<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
												<xsl:value-of select="@extension"/>
												</xsl:when>
											</xsl:choose>		
									</xsl:for-each></xsl:if>
								</td>								
							</tr>
							<tr class="odd">
								<td class="doclabel">科室：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:participant/sc:associatedEntity/sc:scopingOrganization/sc:name"/>								
								</td>	
								<td class="doclabel">病区：</td>
								<td class="docdataitem" colspan="3">						
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation">
								<xsl:if test="sc:value/@codeSystem='1.2.156.112656.1.1.33'">
								<xsl:value-of select="sc:value"/>
								</xsl:if>
								</xsl:for-each>								 
								</td>	
								<td class="doclabel">床位：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
								</td>
								<td class="doclabel">送检日期：</td>
								<td class="docdataitem">
									<xsl:choose>
										<xsl:when test="string-length(sc:participant[@typeCode='DIST']/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>							
							</tr>
							<tr class="odd">
								<td class="doclabel">影像：</td>
								<td class="docdataitem" colspan="9">
								<xsl:choose>
									<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension !=''
									">
									<xsl:element name="a">
										<xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension" />
										</xsl:attribute>
										<xsl:attribute name="target">_blank</xsl:attribute>
										<img src="../images/pic_jc.png" width="22" height="22" border="0" />
									</xsl:element>									
									</xsl:when>
								</xsl:choose>
								</td>
							</tr>							
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:observationMedia">
								<xsl:if test="sc:value !=''">
								<tr>
								<td colspan="10">
								<div>
								<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-top: solid 1px #B3C4D4;border-bottom: solid 1px #B3C4D4;bgcolor:white;">		
								<tr>
								<xsl:if test="last() = 1">
								<td colspan="2" align="center">
								<div style="margin:5px;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:value" /></xsl:attribute>
									<xsl:attribute name="width">700px</xsl:attribute>
									<xsl:attribute name="hspace">4px</xsl:attribute>
									<xsl:attribute name="vspace">10px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
								</xsl:if>
								<xsl:if test="last() > 1">
								<td align="center">
								<div style="margin:5px;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:value" /></xsl:attribute>
									<xsl:attribute name="width">350px</xsl:attribute>
									<xsl:attribute name="hspace">4px</xsl:attribute>
									<xsl:attribute name="vspace">10px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
								</xsl:if>
								<xsl:if test="position() mod 2=0">
								<tr>
								</tr>
								</xsl:if>
								</tr>
								</table>
								</div>
								</td>
							</tr>								
								</xsl:if>
								</xsl:for-each>
							
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">							
							<xsl:if test="sc:code/@code='72724002'">
							<tr>
								<td colspan="10" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
									客观所见:
								</td>
							</tr>							
							<tr>
								<td colspan="10" class="blws" style="word-break:break-all;word-wrap:break-word;">
								<pre><xsl:value-of select="sc:value"/></pre>
								</td>
							</tr>							
							</xsl:if>
							<xsl:if test="sc:code/@code='118246004'">						
							<tr>
								<td colspan="10" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
									主观诊断:
								</td>
							</tr>							
							<tr>
								<td colspan="10" class="blws" style="word-break:break-all;word-wrap:break-word;">
								<pre><xsl:value-of select="sc:value"/></pre>
								</td>
							</tr>
							</xsl:if>
							</xsl:for-each>
							<tr>
							<td colspan="10" style="padding:0px;">
							<div style="position: relative; bottom: 0px;">
						  	<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
							<tr class="odd">
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">诊断者:</td>
								<td class="docdataitem"  style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select="sc:participant[@typeCode='PRF']/sc:associatedEntity/sc:associatedPerson/sc:name">
										<xsl:value-of select="."/>
										<xsl:if test="position()!=last()">&#160;</xsl:if>
									</xsl:for-each>
								</td>
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">审核:</td>
								<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select="sc:authenticator/sc:assignedEntity/sc:assignedPerson/sc:name">
										<xsl:value-of select="."/>
										<xsl:if test="position()!=last()">&#160;</xsl:if>	
									</xsl:for-each>									
								</td>
								<td class="doclabel"  style="border-top: solid 1px #B3C4D4;">报告日期：</td>
								<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
									<xsl:choose>
										<xsl:when test="string-length(sc:author/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>								
							</tr>
							<tr>
								<td class="note" colspan="6">注:本次结果只对该标本负责</td>
							</tr>													  
						  </table>
						  </div>
						  </td>							
							</tr>					
						</table>													
						</xsl:when>
						<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.117'
							and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@code='086'">						
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="8" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;">
									<h3><xsl:value-of select="sc:title"/></h3>
								</td>								
							</tr>
							<tr class="odd">
								<td class="doclabel">姓名：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/>
								</td>
								<td class="doclabel">性别：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/>
								</td>
								<td class="doclabel">年龄：</td>
								<td class="docdataitem">
								  <xsl:choose>
										<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code/@code='397669002'">
											<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value"/>
										</xsl:when>
								  </xsl:choose>
								</td>
								<td class="doclabel">样本号：</td>
								<td class="docdataitem">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:id">
								<xsl:choose>
								<xsl:when test="@root">
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="@extension"/>								
								</xsl:otherwise>
								</xsl:choose>
								</xsl:for-each>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel" >就诊卡号：</td>
								<td class="docdataitem">
								<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='01' or sc:componentOf/sc:encompassingEncounter/sc:code/@code='0201'">
									<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
											<xsl:choose>
												<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
												<xsl:value-of select="@extension"/>
												</xsl:when>
											</xsl:choose>		
									</xsl:for-each></xsl:if>
								</td>
								<td class="doclabel">病区：</td>
								<td class="docdataitem">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation">
								<xsl:if test="sc:value/@codeSystem='1.2.156.112656.1.1.33'">
								<xsl:value-of select="sc:value"/>
								</xsl:if>
								</xsl:for-each>
								</td>	
								<td class="doclabel">床位：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
								</td>
								<td class="doclabel">送检日期：</td>
								<td class="docdataitem">
									<xsl:choose>
										<xsl:when test="string-length(sc:participant[@typeCode='DIST']/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>							
							</tr>
							<tr class="odd">
							<td class="doclabel">影像：</td>
								<td colspan="7" style="border-bottom: solid 1px #B3C4D4;">
								<xsl:choose>
									<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension !=''
									">
									<xsl:element name="a">
										<xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension" />
										</xsl:attribute>
										<xsl:attribute name="target">_blank</xsl:attribute>
										<img src="../images/pic_jc.png" width="22" height="22" border="0" />
									</xsl:element>									
									</xsl:when>
								</xsl:choose>
								</td>
							</tr>
							<tr height="20">
								<td>									
								</td>
							</tr>
							<tr>
							<div style="position:absolute,bottom,140px;margin-bottom:5px;">							
							<table align="center" width="95%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;border:0px solid black;margin-left:5px;table-layout:fixed;bgcolor:white;">
							<tr>
								<th align="center" style="border-bottom:solid 1px #c0ddea;border-top:solid 1px #c0ddea;">项目名称</th>
								<th align="center" style="border-bottom:solid 1px #c0ddea;border-top:solid 1px #c0ddea;">结果(%)</th>
								<th align="center" style="border-bottom:solid 1px #c0ddea;border-top:solid 1px #c0ddea;">项目名称</th>
								<th align="center" style="border-bottom:solid 1px #c0ddea;border-top:solid 1px #c0ddea;">结果(%)</th>
								<th align="center" style="border-bottom:solid 1px #c0ddea;border-top:solid 1px #c0ddea;">项目名称</th>
								<th align="center" style="border-bottom:solid 1px #c0ddea;border-top:solid 1px #c0ddea;">结果(%)</th>
								<th align="center" style="border-bottom:solid 1px #c0ddea;border-top:solid 1px #c0ddea;">项目名称</th>
								<th align="center" style="border-bottom:solid 1px #c0ddea;border-top:solid 1px #c0ddea;">结果(%)</th>								
							</tr>
							<tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.108' and sc:sequenceNumber/@value='1'">
								<td align="center" style="border-bottom:solid 0px #c0ddea;border-top:solid 0px #c0ddea;">
								<xsl:value-of select="sc:observation/sc:code/@displayName"/>
								</td>
								<td align="center" style="border-bottom:solid 0px #c0ddea;border-top:solid 0px #c0ddea;">
									<xsl:choose>
									<xsl:when test="sc:observation/sc:value =0 or sc:observation/sc:value =''">
										<xsl:text></xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
									</xsl:otherwise>
									</xsl:choose>
								</td>
							</xsl:if>
							<xsl:if test="sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.108' and sc:sequenceNumber/@value='5'">
								<td align="center">
								<xsl:value-of select="sc:observation/sc:code/@displayName"/>
								</td>
								<td align="center">
									<xsl:choose>
									<xsl:when test="sc:observation/sc:value =0 or sc:observation/sc:value =''">
										<xsl:text></xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
									</xsl:otherwise>
									</xsl:choose>
								</td>
							</xsl:if>		
							<xsl:if test="sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.108' and sc:sequenceNumber/@value='9'">
								<td align="center">
								<xsl:value-of select="sc:observation/sc:code/@displayName"/>
								</td>
								<td align="center">
									<xsl:choose>
									<xsl:when test="sc:observation/sc:value =0 or sc:observation/sc:value =''">
										<xsl:text></xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
									</xsl:otherwise>
									</xsl:choose>
								</td>
							</xsl:if>		
							<xsl:if test="sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.108' and sc:sequenceNumber/@value='13'">
								<td align="center">
								<xsl:value-of select="sc:observation/sc:code/@displayName"/>
								</td>
								<td align="center">
									<xsl:choose>
									<xsl:when test="sc:observation/sc:value =0 or sc:observation/sc:value =''">
										<xsl:text></xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
									</xsl:otherwise>
									</xsl:choose>
								</td>
							</xsl:if>																							
							</xsl:for-each>
							</tr>
							<tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.108' and sc:sequenceNumber/@value='2'">
								<td align="center">
								<xsl:value-of select="sc:observation/sc:code/@displayName"/>
								</td>
								<td align="center">
									<xsl:choose>
									<xsl:when test="sc:observation/sc:value =0 or sc:observation/sc:value =''">
										<xsl:text></xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
									</xsl:otherwise>
									</xsl:choose>
								</td>
							</xsl:if>
							<xsl:if test="sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.108' and sc:sequenceNumber/@value='6'">
								<td align="center"> 
								<xsl:value-of select="sc:observation/sc:code/@displayName"/>
								</td>
								<td align="center">
									<xsl:choose>
									<xsl:when test="sc:observation/sc:value =0 or observation/sc:value =''">
										<xsl:text></xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
									</xsl:otherwise>
									</xsl:choose>
								</td>
							</xsl:if>		
							<xsl:if test="sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.108' and sc:sequenceNumber/@value='10'">
								<td align="center">
								<xsl:value-of select="sc:observation/sc:code/@displayName"/>
								</td>
								<td align="center">
									<xsl:choose>
									<xsl:when test="sc:observation/sc:value =0 or observation/sc:value =''">
										<xsl:text></xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
									</xsl:otherwise>
									</xsl:choose>
								</td>
							</xsl:if>		
							<xsl:if test="sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.108' and sc:sequenceNumber/@value='14'">
								<td align="center">
								<xsl:value-of select="sc:observation/sc:code/@displayName"/>
								</td>
								<td align="center">
									<xsl:choose>
									<xsl:when test="sc:observation/sc:value =0 or sc:observation/sc:value =''">
										<xsl:text></xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
									</xsl:otherwise>
									</xsl:choose>
								</td>
							</xsl:if>																							
							</xsl:for-each>
							</tr>	
							<tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.108' and sc:sequenceNumber/@value='3'">
								<td align="center">
								<xsl:value-of select="sc:observation/sc:code/@displayName"/>
								</td>
								<td align="center">
									<xsl:choose>
									<xsl:when test="sc:observation/sc:value =0 or sc:observation/sc:value =''">
										<xsl:text></xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
									</xsl:otherwise>
									</xsl:choose>
								</td>
							</xsl:if>
							<xsl:if test="sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.108' and sc:sequenceNumber/@value='7'">
								<td align="center">
								<xsl:value-of select="sc:observation/sc:code/@displayName"/>
								</td>
								<td align="center">
									<xsl:choose>
									<xsl:when test="sc:observation/sc:value =0 or sc:observation/sc:value =''">
										<xsl:text></xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
									</xsl:otherwise>
									</xsl:choose>
								</td>
							</xsl:if>		
							<xsl:if test="sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.108' and sc:sequenceNumber/@value='11'">
								<td align="center">
								<xsl:value-of select="sc:observation/sc:code/@displayName"/>
								</td>
								<td align="center">
									<xsl:choose>
									<xsl:when test="sc:observation/sc:value =0 or sc:observation/sc:value =''">
										<xsl:text></xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
									</xsl:otherwise>
									</xsl:choose>
								</td>
							</xsl:if>		
							<xsl:if test="sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.108' and sc:sequenceNumber/@value='15'">
								<td align="center">
								<xsl:value-of select="sc:observation/sc:code/@displayName"/>
								</td>
								<td align="center">
									<xsl:choose>
									<xsl:when test="sc:observation/sc:value =0 or sc:observation/sc:value =''">
										<xsl:text></xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
									</xsl:otherwise>
									</xsl:choose>
								</td>
							</xsl:if>																							
							</xsl:for-each>
							</tr>
							<tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							<xsl:if test="sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.108' and sc:sequenceNumber/@value='4'">
								<td align="center">
								<xsl:value-of select="sc:observation/sc:code/@displayName"/>
								</td>
								<td align="center">
									<xsl:choose>
									<xsl:when test="sc:observation/sc:value =0 or observation/sc:value =''">
										<xsl:text></xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
									</xsl:otherwise>
									</xsl:choose>
								</td>
							</xsl:if>
							<xsl:if test="sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.108' and sc:sequenceNumber/@value='8'">
								<td align="center">
								<xsl:value-of select="sc:observation/sc:code/@displayName"/>
								</td>
								<td align="center">
									<xsl:choose>
									<xsl:when test="sc:observation/sc:value =0 or sc:observation/sc:value =''">
										<xsl:text></xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
									</xsl:otherwise>
									</xsl:choose>
								</td>
							</xsl:if>		
							<xsl:if test="sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.108' and sc:sequenceNumber/@value='12'">
								<td align="center">
								<xsl:value-of select="sc:observation/sc:code/@displayName"/>
								</td>
								<td align="center">
									<xsl:choose>
									<xsl:when test="sc:observation/sc:value =0 or sc:observation/sc:value =''">
										<xsl:text></xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
									</xsl:otherwise>
									</xsl:choose>
								</td>
							</xsl:if>		
							<xsl:if test="sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.108' and sc:sequenceNumber/@value='16'">
								<td align="center">
								<xsl:value-of select="sc:observation/sc:code/@displayName"/>
								</td>
								<td align="center">
									<xsl:choose>
									<xsl:when test="sc:observation/sc:value =0 or sc:observation/sc:value =''">
										<xsl:text></xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="format-number(sc:observation/sc:value,'#0.0')"/>
									</xsl:otherwise>
									</xsl:choose>
								</td>
							</xsl:if>																							
							</xsl:for-each>
							</tr>	
							</table>
							</div>
							</tr>
							<tr>
							<div>
								<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
									镜下所见:
								</td>
							</tr>							
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='72724002'">														
							<tr height="20"><td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:value"/></pre>
								</td>
							</tr>								
							</xsl:if>
							</xsl:for-each>	
							<tr>
								<td colspan="8">
								<div>
								<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-top: solid 1px #B3C4D4;bgcolor:white;">		
								<tr>
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:observationMedia">
								<xsl:if test="sc:value !=''">
								<xsl:if test="last() = 1">
								<td colspan="2" align="center">
								<div style="margin:5px;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:value" /></xsl:attribute>
									<xsl:attribute name="width">700px</xsl:attribute> 
									<xsl:attribute name="hspace">4px</xsl:attribute>
									<xsl:attribute name="vspace">10px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
								</xsl:if>
								<xsl:if test="last() > 1">
								<td align="center">
								<div style="margin:5px;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:value" /></xsl:attribute>
									<xsl:attribute name="width">350px</xsl:attribute>
									<xsl:attribute name="hspace">4px</xsl:attribute>
									<xsl:attribute name="vspace">10px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
								</xsl:if>
								<xsl:if test="position() mod 2=0">
								<tr>
								</tr>
								</xsl:if>
								</xsl:if>
								</xsl:for-each>
								</tr>
								</table>
								</div>
								</td>
							</tr>
						<tr>
						  <div name="selectTabs" style="position: absolute; bottom:45px;">
						  <table style="border-collapse:collapse;table-layout:fixed;bgcolor:white;" cellpadding="2" cellspacing="1" width="100%" align="center" border="0">
							<tr class="odd">
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">检验者:</td>
								<td class="docdataitem"  style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select="sc:participant[@typeCode='PRF']/sc:associatedEntity/sc:associatedPerson/sc:name">
										<xsl:value-of select="."/>
										<xsl:if test="position()!=last()">&#160;</xsl:if>
									</xsl:for-each>
								</td>
								<td class="doclabel" style="border-top: solid 1px #B3C4D4;">审核:</td>
								<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select="sc:authenticator/sc:assignedEntity/sc:assignedPerson/sc:name">
										<xsl:value-of select="."/>	
										<xsl:if test="position()!=last()">&#160;</xsl:if>
									</xsl:for-each>									
								</td>
								<td class="doclabel"  style="border-top: solid 1px #B3C4D4;">报告日期：</td>
								<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
									<xsl:choose>
										<xsl:when test="string-length(sc:author/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>								
							</tr>
							<tr>
							<td class="note" colspan="6">注:本次结果只对该标本负责</td>
							</tr>						  
						  </table>
						  </div>								
						</tr>							
							</table>						
							</div>
							</tr>	
						</table>
						</xsl:when>
						<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.117'
							and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@code='802'">	
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr>
								<td colspan="2" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"></td>
								<td colspan="4" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;">
									<h3><xsl:value-of select="sc:title"/></h3>
								</td>								
								<td class="blockHeader" height="30" align="right" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;font-weight: bold;">检查号：</td>
								<td class="reportnumber" height="30" align="left" style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;font-weight: bold;">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:id/@extension"/>
								</td>
							</tr>							
							<tr class="odd">
								<td class="doclabel">姓名：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/>
								</td>
								<td class="doclabel">性别：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/>
								</td>
								<td class="doclabel">年龄：</td>
								<td class="docdataitem">
								  <xsl:choose>
									<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code/@code='397669002'">
										<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value"/>
									</xsl:when>
								  </xsl:choose>
								</td>
								<td class="doclabel">科别：</td>
								<td class="docdataitem">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation">
								<xsl:if test="sc:value/@codeSystem='1.2.156.112656.1.1.33'">
								<xsl:value-of select="sc:value"/>
								</xsl:if>
								</xsl:for-each>	
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">床位号：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
								</td>
								<td class="doclabel">住院号：</td>
								<td class="docdataitem" colspan="3">
								<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
									<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
											<xsl:choose>
												<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
												<xsl:value-of select="@extension"/>
												</xsl:when>
											</xsl:choose>		
									</xsl:for-each></xsl:if>
								</td>								
								<td class="doclabel" >就诊卡号：</td>
								<td class="docdataitem">
								<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='01' or sc:componentOf/sc:encompassingEncounter/sc:code/@code='0201'">
									<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
											<xsl:choose>
												<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
												<xsl:value-of select="@extension"/>
												</xsl:when>
											</xsl:choose>		
									</xsl:for-each></xsl:if>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">送检医院：</td>
								<td class="docdataitem" colspan="5">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:participant/sc:participantRole/sc:playingEntity/sc:name"/>
								</td>
								<td class="doclabel">取材部位：</td>
								<td class="docdataitem">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:targetSiteCode/@displayName"/>
								</td>
							</tr>
							<tr class="odd">
							<td class="doclabel">影像：</td>
								<td colspan="7" style="border-bottom: solid 1px #B3C4D4;">
								<xsl:choose>
									<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension !=''
									">
									<xsl:element name="a">
										<xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension" />
										</xsl:attribute>
										<xsl:attribute name="target">_blank</xsl:attribute>
										<img src="../images/pic_jc.png" width="22" height="22" border="0" />
									</xsl:element>									
									</xsl:when>
								</xsl:choose>
								</td>
							</tr>
							<tr>
								<td colspan="8">
								<div>
								<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">		
								<tr>
								<xsl:for-each select="component/structuredBody/component/section/entry/observation/entryRelationship/procedure/entryRelationship/observationMedia">
								<xsl:if test="value !=''">
								<xsl:if test="last() = 1">
								<td colspan="2" align="center">
								<div style="margin:5px;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:value" /></xsl:attribute>
									<xsl:attribute name="width">700px</xsl:attribute> 
									<xsl:attribute name="hspace">4px</xsl:attribute>
									<xsl:attribute name="vspace">10px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
								</xsl:if>
								<xsl:if test="last() > 1">
								<td align="center">
								<div style="margin:5px;">
								<xsl:element name="img">
									<xsl:attribute name="src" >data:<xsl:value-of select="sc:value/@mediaType" /> ;base64,<xsl:value-of select="sc:value" /></xsl:attribute>
									<xsl:attribute name="width">350px</xsl:attribute>
									<xsl:attribute name="hspace">4px</xsl:attribute>
									<xsl:attribute name="vspace">10px</xsl:attribute>
								</xsl:element>
								</div>
								</td>
								</xsl:if>
								<xsl:if test="position() mod 2=0">
								<tr>
								</tr>
								</xsl:if>
								</xsl:if>
								</xsl:for-each>
								</tr>
								</table>
								</div>
								</td>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='72724002'">
							<tr>
								<td colspan="8" class="blockHeader" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
									镜下所见:
								</td>
							</tr>						
							<tr><td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
								<pre><xsl:value-of select="sc:value"/></pre>
								</td>
							</tr>
							</xsl:if>
							<xsl:if test="sc:code/@code='118246004'">
							<tr>
								<td colspan="8" class="blockHeader" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
									诊断提示:
								</td>
							</tr>							
							<tr><td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:value"/></pre>
								</td>
							</tr>								
							</xsl:if>
							</xsl:for-each>		
							<tr>
							<td colspan="8" style="padding:0px;">
							<div name="selectTabs" style="position: relative; bottom: 0px;">
						  	<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
							<tr class="odd">
								<td class="doclabel2" style="border-top: solid 1px #B3C4D4;">诊断医生:</td>
								<td class="docdataitem2"  style="border-top: solid 1px #B3C4D4;">
								<xsl:for-each select="sc:participant[@typeCode='PRF']/sc:associatedEntity/sc:associatedPerson/sc:name">
									<xsl:value-of select="."/>
									<xsl:if test="position()!=last()">&#160;</xsl:if>
								</xsl:for-each>
								</td>
								<td class="doclabel2" style="border-top: solid 1px #B3C4D4;">审查人:</td>
								<td class="docdataitem2" style="border-top: solid 1px #B3C4D4;">
								<xsl:for-each select="sc:authenticator/sc:assignedEntity/sc:assignedPerson/sc:name">
									<xsl:value-of select="."/>
									<xsl:if test="position()!=last()">&#160;</xsl:if>
								</xsl:for-each>										
								</td>
								<td class="doclabel2"  style="border-top: solid 1px #B3C4D4;">送检日期：</td>
								<td class="docdataitem2" style="border-top: solid 1px #B3C4D4;">
									<xsl:choose>
										<xsl:when test="string-length(sc:participant[@typeCode='DIST']/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>								
								<td class="doclabel2"  style="border-top: solid 1px #B3C4D4;">诊断时间：</td>
								<td class="docdataitem2" style="border-top: solid 1px #B3C4D4;">
									<xsl:choose>
										<xsl:when test="string-length(sc:author/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>
							</tr>
							<tr>
								<td class="note" colspan="8">注:本次结果只对该标本负责</td>
							</tr>
							</table>
							</div>
							</td>
							</tr>																										
							</table>							
							</xsl:when>
							<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.117'
							and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation/sc:code/@code='085'">	
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
							<tr>
								<td colspan="8" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;">
									<h3><xsl:value-of select="sc:title"/></h3>
								</td>								
							</tr>							
							<tr class="odd">
								<td class="doclabel">姓名：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/>
								</td>
								<td class="doclabel">性别：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/>
								</td>
								<td class="doclabel">年龄：</td>
								<td class="docdataitem">
								  <xsl:choose>
									<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:code/@code='397669002'">
										<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value"/>
									</xsl:when>
								  </xsl:choose>
								</td>
								<td class="doclabel" >就诊卡号：</td>
								<td class="docdataitem">
								<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='01' or sc:componentOf/sc:encompassingEncounter/sc:code/@code='0201'">
									<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
											<xsl:choose>
												<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
												<xsl:value-of select="@extension"/>
												</xsl:when>
											</xsl:choose>		
									</xsl:for-each></xsl:if>
								</td>																								
							</tr>
							<tr class="odd">
								<td class="doclabel">科室：</td>
								<td class="docdataitem">
								<xsl:choose>
								<xsl:when test="sc:participant/@typeCode='AUT'">
								<xsl:value-of select="sc:participant/sc:associatedEntity/sc:scopingOrganization/sc:name"/>
								</xsl:when>
								</xsl:choose>
								</td>
								<td class="doclabel">病区：</td>
								<td class="docdataitem">
								<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation">
								<xsl:if test="sc:value/@codeSystem='1.2.156.112656.1.1.33'">
								<xsl:value-of select="sc:value"/>
								</xsl:if>
								</xsl:for-each>
								</td>															
								<td class="doclabel">床位：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf"/>
								</td>							
								<td class="doclabel" >送检日期：</td>
								<td class="docdataitem">
									<xsl:choose>
										<xsl:when test="string-length(sc:participant[@typeCode='DIST']/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:participant[@typeCode='DIST']/sc:time/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>								
							</tr>
							<tr class="odd">
								<td class="doclabel">标本：</td>
								<td class="docdataitem">
								<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:targetSiteCode/@displayName"/>
								</td>
								<td class="doclabel">样本号：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:specimen/sc:specimenRole/sc:id/@extension"/>
								</td>
								<td class="doclabel" >方法：</td>
								<td class="docdataitem" colspan="3">
									<xsl:choose>
										<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:code/@code='246366009'">
											<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:entryRelationship/sc:procedure/sc:methodCode/@displayName"/>
										</xsl:when>
									</xsl:choose>
								</td>								
							</tr>
							<tr class="odd">
							<td class="doclabel">影像：</td>
								<td colspan="7" style="border-bottom: solid 1px #B3C4D4;">
								<xsl:choose>
									<xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension !=''
									">
									<xsl:element name="a">
										<xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:supply/sc:id/@extension" />
										</xsl:attribute>
										<xsl:attribute name="target">_blank</xsl:attribute>
										<img src="../images/pic_jc.png" width="22" height="22" border="0" />
									</xsl:element>									
									</xsl:when>
								</xsl:choose>
								</td>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
							<xsl:if test="sc:code/@code='72724002'">
							<tr>
								<td colspan="8" class="blockHeader" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
									镜下所见:
								</td>
							</tr>						
							<tr><td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
								<pre><xsl:value-of select="sc:value"/></pre>
								</td>
							</tr>
							</xsl:if>
							<xsl:if test="sc:code/@code='118246004'">
							<tr>
								<td colspan="8" class="blockHeader" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
									初诊:
								</td>
							</tr>							
							<tr><td colspan="8" class="blws" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:value"/></pre>
								</td>
							</tr>								
							</xsl:if>
							</xsl:for-each>		
							<tr>
							<td colspan="8" style="padding:0px;">
							<div name="selectTabs" style="position: relative; bottom: 0px;">
						  	<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
							<tr class="odd">
								<td class="doclabel2" style="border-top: solid 1px #B3C4D4;">送检医生:</td>
								<td class="docdataitem2"  style="border-top: solid 1px #B3C4D4;">
								<xsl:for-each select="sc:participant[@typeCode='DIST']/sc:associatedEntity/sc:associatedPerson/sc:name">
									<xsl:value-of select="."/>
									<xsl:if test="position()!=last()">&#160;</xsl:if>
								</xsl:for-each>
								</td>							
								<td class="doclabel2" style="border-top: solid 1px #B3C4D4;">诊断医生:</td>
								<td class="docdataitem2"  style="border-top: solid 1px #B3C4D4;">
									<xsl:for-each select="sc:participant[@typeCode='PRF']/sc:associatedEntity/sc:associatedPerson/sc:name">
										<xsl:value-of select="."/>
										<xsl:if test="position()!=last()">&#160;</xsl:if>
									</xsl:for-each>
								</td>
								<td class="doclabel2" style="border-top: solid 1px #B3C4D4;">复核医生:</td>
								<td class="docdataitem2" style="border-top: solid 1px #B3C4D4;">
								<xsl:for-each select="sc:authenticator/sc:assignedEntity/sc:assignedPerson/sc:name">
									<xsl:value-of select="."/>
									<xsl:if test="position()!=last()">&#160;</xsl:if>
								</xsl:for-each>										
								</td>								
								<td class="doclabel2"  style="border-top: solid 1px #B3C4D4;">报告日期：</td>
								<td class="docdataitem2" style="border-top: solid 1px #B3C4D4;">
									<xsl:choose>
										<xsl:when test="string-length(sc:author/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>-<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>
										</xsl:when>
									</xsl:choose>
								</td>
							</tr>
							<tr>
								<td class="note" colspan="8">注:本次结果只对该标本负责</td>
							</tr>
							</table>
							</div>
							</td>
							</tr>																										
							</table>							
							</xsl:when>																																
						</xsl:choose>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
