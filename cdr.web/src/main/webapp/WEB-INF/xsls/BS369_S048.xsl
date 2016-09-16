<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:sc="urn:hl7-org:v3"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="urn:hl7-org:v3 ../coreschemas/CDA.xsd" xmlns:Constants="xalan://com.founder.cdr.core.Constants">
	<xsl:output method="html" indent="yes" version="4.0"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" />
	<xsl:template match="/sc:ClinicalDocument">
		<html>
			<head>
				<meta http-equiv="Pragma" content="no-cache" />
				<meta http-equiv="Cache-Control" content="no-cache" />
				<meta http-equiv="Expires" content="0" />
				<link type="text/css" rel="Stylesheet" href="../../styles/tablelist.css"
					charset="UTF8" />
				<script type="text/javascript">
					$(function() {
						var reportType = '<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code[@codeSystem='1.2.156.112656.1.1.112']/@code"/>';
						if(reportType=='104'){
							getEndDateTime();
						}
					});
					function getEndDateTime()
					{
						<!--开始时间-->
						var beginTime='<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value"/>';
						var y = parseInt(beginTime.substring(0,4));
						var m = parseInt(beginTime.substring(4,6));
						var d = parseInt(beginTime.substring(6,8));
						var h = parseInt(beginTime.substring(8,10));
						var mm= parseInt(beginTime.substring(10,12));
						var s = parseInt(beginTime.substring(12,14));
						<!-- alert("y:"+y+" m:"+m+" d:"+d+" h:"+h+" mm:"+mm+" s:"+s); -->
						<!--总时间-->
						var totleTime='<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:width/@value" />';
						var h2=parseInt(totleTime.substring(0,2));
						var mm2=parseInt(totleTime.substring(3,5));
						var s2=parseInt(totleTime.substring(6,8));
						
						<!--计算结束时间-->						
						  var test=new Date(y,m,d,h,mm,s);
						  test.setHours(test.getHours()+h2);
						  test.setMinutes(test.getMinutes()+mm2);
						  test.setSeconds(test.getSeconds()+s2);
						  var m3=test.getMonth();
						  var d3=test.getDate();
						  var h3=test.getHours();
						  var mm3=test.getMinutes();
						  var s3=test.getSeconds();
						  if(m3&#60;10)
						  {m3="0"+m3;}
						  if(d3&#60;10)
						  {d3="0"+d3;}
						  if(h3&#60;10)
						  {h3="0"+h3;}
						  if(mm3&#60;10)
						  {mm3="0"+mm3;}						  
						  if(s3&#60;10)
						  {s3="0"+s3;}						  
						  var str=test.getFullYear()+"-"+m3+"-"+d3+" "+h3+":"+mm3+":"+s3;
						 return document.getElementById("endTime1").innerHTML=str;
					}
				</script>	
			</head>
			<body>
				<div id="mainContent">
					<div name="selectTabs">
						<div class="responsability">
							<xsl:value-of select="Constants:getResponsabilityClaimingContent()"/>
						</div>
						<xsl:choose>
							<xsl:when
								test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.112' and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='101'">
								<table border="0" align="center" width="100%" cellspacing="1"
									cellpadding="2"
									style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
									<tr>
										<td colspan="2" style="border-bottom: solid 1px #B3C4D4;"></td>
										<td colspan="4" height="30" style="border-bottom: solid 1px #B3C4D4;" align="center">
											<h3 class="reporttitle">
												<xsl:value-of select="sc:title" />
											</h3>
										</td>
										<td class="blockHeader" height="30" align="right"
											style="vertical-align:bottom;font-weight: bold;border-bottom: solid 1px #B3C4D4;">医嘱号：
										</td>
										<td class="reportnumber" height="30" align="left"
											style="vertical-align:bottom;border-bottom: solid 1px #B3C4D4;font-weight: bold;">
											<xsl:value-of select="sc:inFulfillmentOf/sc:order/sc:id[position()=1]/@extension"/>
										</td>
									</tr>
									<tr class="odd">
										<td class="doclabel">姓名：</td>
										<td class="docdataitem">
											<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name" />
										</td>
										<td class="doclabel">性别：</td>
										<td class="docdataitem">
											<xsl:value-of
												select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName" />
										</td>
										<td class="doclabel">年龄：</td>
										<td class="docdataitem">
											<xsl:for-each
												select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
												<xsl:choose>
													<xsl:when test="sc:observation/sc:code/@code='397669002'">
														<xsl:value-of select="sc:observation/sc:value" />
													</xsl:when>
												</xsl:choose>
											</xsl:for-each>
										</td>
										<td class="doclabel"></td>
										<td class="docdataitem" style="word-wrap:break-word;overflow:auto;">
										</td>
									</tr>
									<tr class="odd">
										<td class="doclabel">门诊号：</td>
										<td class="docdataitem">
											<xsl:if
												test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='01' or sc:componentOf/sc:encompassingEncounter/sc:code/@code='0201'">
												<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
													<xsl:choose>
														<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
															<xsl:value-of select="@extension" />
														</xsl:when>
													</xsl:choose>
												</xsl:for-each>
											</xsl:if>
										</td>
										<td class="doclabel">住院号：</td>
										<td class="docdataitem">
											<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
												<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
													<xsl:choose>
														<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
															<xsl:value-of select="@extension" />
														</xsl:when>
													</xsl:choose>
												</xsl:for-each>
											</xsl:if>
										</td>
										<td class="doclabel">病区：</td>
										<td class="docdataitem">
											<xsl:for-each
												select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
												<xsl:choose>
													<xsl:when
														test="sc:observation/sc:value/@codeSystem='1.2.156.112656.1.1.33'">
														<xsl:value-of select="sc:observation/sc:value" />
													</xsl:when>
												</xsl:choose>
											</xsl:for-each>
										</td>
										<td class="doclabel">床号：</td>
										<td class="docdataitem">
											<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf" />
										</td>
									</tr>
									<tr class="odd">
										<xsl:for-each
											select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
											<td class="doclabel">
												<xsl:value-of select="sc:observation/sc:code/@displayName" />
											</td>
											<td class="docdataitem">
												<xsl:choose>
													<xsl:when test="sc:observation/sc:value/@value">
														<xsl:value-of select="sc:observation/sc:value/@value" />
														<xsl:text>  </xsl:text>
														<xsl:value-of select="sc:observation/sc:value/@unit" />
													</xsl:when>
													<xsl:otherwise>
														<xsl:value-of select="sc:observation/sc:value" />
													</xsl:otherwise>
												</xsl:choose>
											</td>
											<xsl:if test="position() mod 4=0">
												<tr>
												</tr>
											</xsl:if>
											<xsl:if test="position()=last() and last() mod 4=1">
				                            <td class="docdataitem" colspan="6"></td>				                            				                            
											</xsl:if>
											<xsl:if test="position()=last() and last() mod 4=2">
											<td class="docdataitem" colspan="4"></td>					                            				                            
											</xsl:if>
											<xsl:if test="position()=last() and last() mod 4=3">
											<td class="docdataitem" colspan="2"></td>				                            				                            
											</xsl:if>																						
										</xsl:for-each>
									</tr>
									<tr class="odd">
										<td class="doclabel">影像：</td>
										<td colspan="7" style="border-bottom: solid 1px #B3C4D4;">
											<xsl:choose>
												<xsl:when
													test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:supply/sc:id/@extension !=''
													">
													<xsl:element name="a">
														<xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of
															select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:supply/sc:id/@extension" />
														</xsl:attribute>
														<xsl:attribute name="target">_blank</xsl:attribute>
														<img src="../images/pic_jc.png" width="22"
															height="22" border="0" />
													</xsl:element>
												</xsl:when>
											</xsl:choose>
										</td>
									</tr>
									<tr>
										<td colspan="8" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4;font-weight: bold;">
											心电图诊断:
										</td>
									</tr>
									<tr>
										<td colspan="8" class="childname"
											style="word-break:break-all;word-wrap:break-word;">
											<xsl:for-each
												select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value">
												<xsl:if test="@code='02'">
													<pre>
														<xsl:value-of select="sc:originalText" />
													</pre>
												</xsl:if>
											</xsl:for-each>
										</td>
									</tr>									
									<xsl:choose>
										<xsl:when
											test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:observationMedia">
											<tr>
												<xsl:for-each
													select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:observationMedia">
													<xsl:if test="sc:value !=''">
														<xsl:if test="last() = 1">
															<td colspan="6" align="center"
																style="border-bottom: solid 1px #B3C4D4;">
																<div style="margin:5px;">
																	<xsl:element name="img">
																		<xsl:attribute name="src">data:<xsl:value-of
																			select="sc:value/@mediaType" /> ;base64,<xsl:value-of
																			select="sc:value" /></xsl:attribute>
																		<xsl:attribute name="width">700px</xsl:attribute>
																		<xsl:attribute name="hspace">4px</xsl:attribute>
																		<xsl:attribute name="vspace">10px</xsl:attribute>
																	</xsl:element>
																</div>
															</td>
														</xsl:if>
														<xsl:if test="last() > 1">
															<td colspan="3" align="center"
																style="border-bottom: solid 1px #B3C4D4;">
																<div style="margin:5px;">
																	<xsl:element name="img">
																		<xsl:attribute name="src">data:<xsl:value-of
																			select="sc:value/@mediaType" /> ;base64,<xsl:value-of
																			select="sc:value" /></xsl:attribute>
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
											<tr>
												<td colspan="8" style="padding:0px;">
													<div name="selectTabs" style="position: relative; bottom: 0px;">
														<table border="0" align="center" width="100%"
															cellspacing="1" cellpadding="2"
															style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
															<tr class="odd" valign="bottom">
																<td class="doclabel">报告日期：</td>
																<td class="docdataitem">
																	<xsl:choose>
																		<xsl:when test="string-length(sc:author/sc:time/@value)>0">
																			<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)" />-<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)" />-<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)" />
																		</xsl:when>
																	</xsl:choose>
																</td>
																<td class="doclabel">报告医生:</td>
																<td class="docdataitem">
																	<xsl:for-each
																		select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name">
																		<xsl:value-of select="." />
																		<xsl:if test="position()!=last()">
																			&#160;
																		</xsl:if>
																	</xsl:for-each>
																</td>
																<td class="doclabel">审核医生：</td>
																<td class="docdataitem">
																	<xsl:for-each
																		select="sc:authenticator/sc:assignedEntity/sc:assignedPerson/sc:name">
																		<xsl:value-of select="." />
																		<xsl:if test="position()!=last()">
																			&#160;
																		</xsl:if>
																	</xsl:for-each>																
																</td>
															</tr>
															<tr class="odd">
																<td class="note" colspan="6">注:本次结果只对该标本负责</td>
															</tr>
														</table>
													</div>
												</td>
											</tr>
										</xsl:when>
									</xsl:choose>
								</table>
							</xsl:when>
							<xsl:when
								test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.112' and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='102'">
								<table border="0" align="center" width="100%" cellspacing="1"
									cellpadding="2"
									style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
									<tr>
										<td colspan="2" style="border-bottom: solid 1px #B3C4D4;"></td>
										<td colspan="2" style="border-bottom: solid 1px #B3C4D4;" height="30" align="center">
											<h3 class="reporttitle">
												<xsl:value-of select="sc:title" />
											</h3>
										</td>
										<td class="blockHeader" height="30" align="right"
											style="vertical-align:bottom;font-weight: bold;border-bottom: solid 1px #B3C4D4;">编号：
										</td>
										<td class="reportnumber" height="30" align="left"
											style="vertical-align:bottom;font-weight: bold;">
											<xsl:value-of select="sc:inFulfillmentOf/sc:order/sc:id[position()=1]/@extension" />
										</td>
									</tr>
									<tr class="odd">
										<td class="doclabel">姓名：</td>
										<td class="docdataitem">
											<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name" />
										</td>
										<td class="doclabel">病区：</td>
										<td class="docdataitem">
											<xsl:for-each
												select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
												<xsl:choose>
													<xsl:when
														test="sc:observation/sc:value/@codeSystem='1.2.156.112656.1.1.33'">
														<xsl:value-of select="sc:observation/sc:value" />
													</xsl:when>
												</xsl:choose>
											</xsl:for-each>
										</td>
										<td class="doclabel">检查日期：</td>
										<td class="docdataitem">
											<xsl:for-each
												select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:performer">
												<xsl:if test="position()=1">
													<xsl:choose>
														<xsl:when test="string-length(sc:time/@value)>0">
															<xsl:value-of select="substring(sc:time/@value,1,4)" />-<xsl:value-of select="substring(sc:time/@value,5,2)" />-<xsl:value-of select="substring(sc:time/@value,7,2)" />
														</xsl:when>
													</xsl:choose>
												</xsl:if>
											</xsl:for-each>
										</td>
									</tr>
									<tr class="odd">
										<td class="doclabel">性别：</td>
										<td class="docdataitem">
											<xsl:value-of
												select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName" />
										</td>
										<td class="doclabel">床号：</td>
										<td class="docdataitem">
											<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf" />
										</td>
										<td class="doclabel">记录开始时间：</td>
										<td class="docdataitem">
<!-- 											<xsl:value-of
												select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value" /> -->
										<xsl:choose>
											<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value)>12">
												<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,5,2)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,7,2)"/><xsl:text> </xsl:text><xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,9,2)"/>:<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,11,2)"/>:<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,13,2)"/>
											</xsl:when>									
											<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value)>8">
												<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,5,2)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,7,2)"/><xsl:text> </xsl:text><xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,9,2)"/>:<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,11,2)"/>
											</xsl:when>
											<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value)>0">
												<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,5,2)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,7,2)"/>
											</xsl:when>
										</xsl:choose>												
										</td>
									</tr>
									<tr class="odd">
										<td class="doclabel">年龄：</td>
										<td class="docdataitem">
											<xsl:for-each
												select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
												<xsl:choose>
													<xsl:when test="sc:observation/sc:code/@code='397669002'">
														<xsl:value-of select="sc:observation/sc:value" />
													</xsl:when>
												</xsl:choose>
											</xsl:for-each>
										</td>
										<td class="doclabel">住院号：</td>
										<td class="docdataitem">
											<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
												<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
													<xsl:choose>
														<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
															<xsl:value-of select="@extension" />
														</xsl:when>
													</xsl:choose>
												</xsl:for-each>
											</xsl:if>
										</td>
										<td class="doclabel">记录总时间：</td>
										<td class="docdataitem">
											<xsl:value-of
												select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:width/@value" />
										</td>
									</tr>
									<tr class="odd">
										<td class="doclabel">影像：</td>
										<td colspan="5" style="border-bottom: solid 1px #B3C4D4;">
											<xsl:choose>
												<xsl:when
													test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:supply/sc:id/@extension !=''
									">
													<xsl:element name="a">
														<xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of
															select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:supply/sc:id/@extension" />
														</xsl:attribute>
														<xsl:attribute name="target">_blank</xsl:attribute>
														<img src="../images/pic_jc.png" width="22"
															height="22" border="0" />
													</xsl:element>
												</xsl:when>
											</xsl:choose>
										</td>
									</tr>
									<tr>
										<td colspan="6">
											<div>
												<table border="0" align="center" width="100%"
													cellspacing="1" cellpadding="2"
													style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
													<xsl:for-each
														select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
														<xsl:if test="sc:observation/sc:entryRelationship">
															<tr>
																<td align="center" style="border-bottom: solid 1px #B3C4D4;">
																	<div>
																		<table width="100%" style="word-wrap:break-word;">
																			<tr>
																				<td class="projectname">
																					<xsl:value-of select="sc:observation/sc:code/@displayName" />
																				</td>
																			</tr>
																			<xsl:for-each
																				select="sc:observation/sc:entryRelationship/sc:organizer/sc:component">
																				<tr>
																					<td>
																						<div>
																							<table border="0" align="center" width="100%"
																								cellspacing="1" cellpadding="2">
																								<tr>
																									<td width="25%" class="childname"
																										style="text-indent:2em;padding:0px;">
																										<xsl:value-of select="sc:observation/sc:code/@displayName" />
																									</td>
																									<td width="35%" class="childname"
																										style="text-indent:2em;padding:0px;">
																										<xsl:if test="sc:observation/sc:value/@xsi:type='PQ'">
																											<xsl:value-of select="sc:observation/sc:value/@value" />
																										</xsl:if>
																										<xsl:if test="sc:observation/sc:value/@xsi:type='SC'">
																											<xsl:value-of select="sc:observation/sc:value" />
																										</xsl:if>
																									</td>
																									<td width="40%" class="childname"
																										style="text-indent:2em;padding:0px;">
																										<xsl:value-of select="sc:observation/sc:value/@unit" />
																										<xsl:value-of select="sc:observation/sc:text" />
																									</td>
																								</tr>
																							</table>
																						</div>
																					</td>
																				</tr>
																			</xsl:for-each>
																		</table>
																	</div>
																</td>
															</tr>
														</xsl:if>
													</xsl:for-each>
												</table>
											</div>
										</td>
									</tr>
									<tr>
										<td colspan="6" class="projectname" align="left"
											style="padding-left: 3px;font-weight: bold;">
											说明:
										</td>
									</tr>
									<tr>
										<td colspan="6" class="childname"
											style="word-break:break-all;word-wrap:break-word;">
											<pre>
												<xsl:value-of
													select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value[@code='01']/sc:originalText" />
											</pre>
										</td>
									</tr>
									<tr>
										<td colspan="6" class="projectname" align="left"
											style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
											动态心电图诊断:
										</td>
									</tr>
									<tr>
										<td colspan="6" class="childname"
											style="word-break:break-all;word-wrap:break-word;">
											<pre>
												<xsl:value-of
													select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value[@code='02']/sc:originalText" />
											</pre>
										</td>
									</tr>
									<tr>
										<td colspan="6" style="padding:0px;">
											<div>
												<table border="0" align="center" width="100%"
													cellspacing="1" cellpadding="2"
													style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
													<tr class="odd" valign="bottom">
														<td class="doclabel" style="border-top: solid 1px #B3C4D4;">报告日期：</td>
														<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
															<xsl:choose>
																<xsl:when test="string-length(sc:author/sc:time/@value)>0">
																	<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)" />-<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)" />-<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)" />
																</xsl:when>
															</xsl:choose>
														</td>
														<td class="doclabel" style="border-top: solid 1px #B3C4D4;">报告医生:</td>
														<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
															<xsl:for-each select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name">
																<xsl:value-of select="." />
																<xsl:if test="position()!=last()">
																	&#160;
																</xsl:if>
															</xsl:for-each>
														</td>
														<td class="doclabel" style="border-top: solid 1px #B3C4D4;">审核医生：</td>
														<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
															<xsl:for-each
																select="sc:authenticator/sc:assignedEntity/sc:assignedPerson/sc:name">
																<xsl:value-of select="." />
																<xsl:if test="position()!=last()">
																	&#160;
																</xsl:if>
															</xsl:for-each>															
														</td>
													</tr>
													<tr class="odd">
														<td class="note" colspan="6">注:本次结果只对该标本负责</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</xsl:when>
							<!-- 动态血压检查报告 -->
							<xsl:when
								test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.112' and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='104'">
								<table border="0" align="center" width="100%" cellspacing="1"
									cellpadding="2"
									style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
									<tr>
										<td height="30" style="border-bottom: solid 1px #B3C4D4;" align="center">
											<h3 class="reporttitle">
												<xsl:value-of select="sc:title" />
											</h3>
										</td>
									</tr>
									<tr>
										<td style="padding:0px;">
											<div>
												<table border="0" align="center" width="100%"
													cellspacing="1" cellpadding="2"
													style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
													<tr class="odd">
														<td class="doclabel">姓名：</td>
														<td class="docdataitem">
															<xsl:value-of
																select="sc:recordTarget/sc:patientRole/sc:patient/sc:name" />
														</td>
														<td class="doclabel">病区：</td>
														<td class="docdataitem">
															<xsl:for-each
																select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
																<xsl:choose>
																	<xsl:when
																		test="sc:observation/sc:value/@codeSystem='1.2.156.112656.1.1.33'">
																		<xsl:value-of select="sc:observation/sc:value" />
																	</xsl:when>
																</xsl:choose>
															</xsl:for-each>
														</td>
														<td class="doclabel">记录开始时间：</td>
														<td class="docdataitem"> 
															 <xsl:choose>
																<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value)>12">
																	<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,5,2)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,7,2)"/>&#160;<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,9,2)"/>:<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,11,2)"/>:<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,13,2)"/>
																</xsl:when>
																<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value)>8">
																	<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,5,2)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,7,2)"/>&#160;<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,9,2)"/>:<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,11,2)"/>
															</xsl:when>
																<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value)>0">
																	<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,1,4)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,5,2)"/>-<xsl:value-of select="substring(sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,7,2)"/>
															</xsl:when>
															</xsl:choose> 
														</td>
													</tr>
													<tr class="odd">
														<td class="doclabel">性别：</td>
														<td class="docdataitem">
															<xsl:value-of
																select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName" />
														</td>
														<td class="doclabel">床号：</td>
														<td class="docdataitem">
															<xsl:value-of
																select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf" />
														</td>
														<td class="doclabel">记录结束时间：</td>
														<td id="endTime1" class="docdataitem">
															<!-- $("#endTime").val(getEndDateTime());-->
									<!-- 						<xsl:value-of select="dateTime:addDate($dateChange,sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:low/@value,sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:width/@value)"/>											
									 -->					</td>
													</tr>
													<tr class="odd">
														<td class="doclabel">年龄：</td>
														<td class="docdataitem">
															<xsl:for-each
																select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
																<xsl:choose>
																	<xsl:when test="sc:observation/sc:code/@code='397669002'">
																		<xsl:value-of select="sc:observation/sc:value" />
																	</xsl:when>
																</xsl:choose>
															</xsl:for-each>
														</td>
														<td class="doclabel">总测量次数：</td>
														<td class="docdataitem">
															<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:repeatNumber/@value"/>
														</td>
														<td class="doclabel">总测量时间：</td>
														<td class="docdataitem">
															<xsl:value-of
																select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:participant/sc:time/sc:width/@value" />
														</td>
													</tr>
													<tr class="odd">
														<td class="doclabel">影像：</td>
														<td colspan="5" style="border-bottom: solid 1px #B3C4D4;">
															<xsl:choose> <xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:supply/sc:id/@extension 
																!=''"> 
																<xsl:element name="a"> <xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of 
																select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:supply/sc:id/@extension" 
																/> </xsl:attribute> <xsl:attribute name="target">_blank</xsl:attribute> <img 
																src="../images/pic_jc.png" width="22" height="22" border="0" /> </xsl:element> 
																</xsl:when> </xsl:choose>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
									<xsl:for-each
										select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation">
										<xsl:choose>
											<xsl:when test="sc:code/@code">
											</xsl:when>
											<xsl:otherwise>
												<tr>
													<td class="projectname" align="left"
														style="padding-left: 3px; font-weight: bold;">
														<xsl:value-of select="sc:code/@displayName" />
													</td>
												</tr>
												<xsl:choose>
													<xsl:when
														test="sc:entryRelationship/sc:organizer/sc:component/sc:observation">
														<xsl:for-each
															select="sc:entryRelationship/sc:organizer/sc:component/sc:observation">
															<tr>
																<td class="childname"
																	style="word-break:break-all;word-wrap:break-word;">
																	<xsl:value-of select="sc:code/@displayName" />
																	:
																	<xsl:value-of select="sc:text" />
																</td>
															</tr>
														</xsl:for-each>
													</xsl:when>
												</xsl:choose>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:for-each>
									<tr>
									<td style="padding:0px;">
									<div>
									<table width="100%" style="word-wrap:break-word;border-top: solid 1px #B3C4D4;">
									<tr>
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation[sc:code/@code]">
									<xsl:choose>
									<xsl:when test="count(sc:entryRelationship)=0">
									<td class="projectname" width="30%">
									<xsl:value-of select="sc:code/@displayName"/>
									</td>
									<xsl:if test="sc:value/@xsi:type='PQ'">
									<td class="childname" width="10%">
										<xsl:value-of select="sc:value/@value"/>
									</td>
									<td class="childname" width="10%">
										<xsl:value-of select="sc:value/@unit"/>
										<xsl:value-of select="sc:text" />
									</td>
									</xsl:if>
									<xsl:if test="sc:value/@xsi:type='SC'">
									<td class="childname" width="10%">
										<xsl:value-of select="sc:value" />
									</td>
									<td class="childname" width="10%">
										<xsl:value-of select="sc:text" />
									</td>																			
									</xsl:if>
									<xsl:if test="position() mod 2=0">
									<tr></tr>
									</xsl:if>
									</xsl:when>
									</xsl:choose>
									</xsl:for-each>
									</tr>
									</table>
									</div>
									</td>
									</tr>
									<xsl:for-each
										select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
										<xsl:choose>
											<xsl:when test="sc:observation/sc:entryRelationship">
													<tr>
														<td class="projectname" align="left" style="border-top: solid 1px #B3C4D4;">
															<xsl:value-of select="sc:observation/sc:code/@displayName" />
														</td>
													</tr>
													<tr>
														<td style="padding:0px;">
															<div>
																<table width="100%" style="word-wrap:break-word;">
																	<tr>
																		<xsl:for-each
																			select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
																			<td class="childname" width="15%">
																				<xsl:value-of select="sc:code/@displayName" />
																			</td>
																			<xsl:if test="sc:value/@xsi:type='PQ'">
																			<td class="childname" width="10%">
																				<xsl:value-of select="sc:value/@value" />
																			</td>
																			<td class="childname" width="25%">
																				<xsl:value-of select="sc:value/@unit" />
																				<xsl:value-of select="sc:text" />
																			</td>
																			</xsl:if>
																			<xsl:if test="sc:value/@xsi:type='SC'">
																			<td class="childname" width="10%">
																				<xsl:value-of select="sc:value" />
																			</td>
																			<td class="childname" width="25%">
																				<xsl:value-of select="sc:text" />
																			</td>																			
																			</xsl:if>
																			<xsl:if test="position() mod 2=0">
																			<tr></tr>
																			</xsl:if>
																		</xsl:for-each>
																	</tr>
																</table>
															</div>
														</td>
													</tr>
											</xsl:when>
										</xsl:choose>
									</xsl:for-each>
									<tr>
										<td class="projectname" align="left"
											style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
											结论:
										</td>
									</tr>
									<tr>
										<td class="childname"
											style="word-break:break-all;word-wrap:break-word;">
											<pre>
												<xsl:value-of
													select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value[@code='02']/sc:originalText" />
											</pre>
										</td>
									</tr>
									<tr>
										<td style="padding:0px;">
											<div>
												<table border="0" align="center" width="100%"
													cellspacing="1" cellpadding="2"
													style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
													<tr class="odd" valign="bottom">
														<td class="doclabel" style="border-top: solid 1px #B3C4D4;">医师签名:</td>
														<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
															<xsl:for-each
																select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name">
																<xsl:value-of select="." />
																<xsl:if test="position()!=last()">
																	&#160;
																</xsl:if>
															</xsl:for-each>
														</td>
														<td class="doclabel" style="border-top: solid 1px #B3C4D4;">报告日期：</td>
														<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
															<xsl:choose>
																<xsl:when test="string-length(sc:author/sc:time/@value)>0">
																	<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)" />-<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)" />-<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)" />
																</xsl:when>
															</xsl:choose>
														</td>
													</tr>
													<tr class="odd">
														<td class="note" colspan="4">注:本次结果只对该标本负责</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</xsl:when>
							<!-- 老年科运动平板检查报告 -->
							<xsl:when
								test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.112' and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='103'">
								<table border="0" align="center" width="100%" cellspacing="1"
									cellpadding="2"
									style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
									<tr>
										<td height="30" style="border-bottom: solid 1px #B3C4D4;" align="center">
											<h3 class="reporttitle">
												<xsl:value-of select="sc:title" />
											</h3>
										</td>
									</tr>
									<tr>
										<td style="padding:0px;">
											<div>
												<table border="0" align="center" width="100%"
													cellspacing="1" cellpadding="2"
													style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
													<tr class="odd">
														<td class="doclabel" style="width:8%;">姓名：</td>
														<td class="docdataitem" style="width:12%;">
															<xsl:value-of
																select="sc:recordTarget/sc:patientRole/sc:patient/sc:name" />
														</td>
														<td class="doclabel" style="width:8%;">性别：</td>
														<td class="docdataitem" style="width:12%;">
															<xsl:value-of
																select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName" />
														</td>
														<td class="doclabel" style="width:8%;">年龄：</td>
														<td class="docdataitem" style="width:12%;">
															<xsl:for-each
																select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
																<xsl:choose>
																	<xsl:when test="sc:observation/sc:code/@code='397669002'">
																		<xsl:value-of select="sc:observation/sc:value" />
																	</xsl:when>
																</xsl:choose>
															</xsl:for-each>
														</td>
														<td class="doclabel" style="width:8%;">身高：</td>
														<td class="docdataitem" style="width:12%;">
															<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE04.10.167.00']/sc:value/@value"/>
															<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE04.10.167.00']/sc:value/@unit"/>
														</td>
														<td class="doclabel" style="width:8%;">体重：</td>
														<td class="docdataitem" style="width:12%;">
															<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE04.10.188.00']/sc:value/@value"/>														
															<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='DE04.10.188.00']/sc:value/@unit"/>
														</td>
													</tr>
													<tr class="odd">
														<td class="doclabel" style="width:8%;">门诊号：</td>
														<td class="docdataitem" style="width:12%;">
															<xsl:if
																test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='01' or sc:componentOf/sc:encompassingEncounter/sc:code/@code='0201'">
																<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
																	<xsl:choose>
																		<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
																			<xsl:value-of select="@extension" />
																		</xsl:when>
																	</xsl:choose>
																</xsl:for-each>
															</xsl:if>
														</td>
														<td class="doclabel" style="width:8%;">住院号：</td>
														<td class="docdataitem" style="width:12%;">
															<xsl:if
																test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
																<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
																	<xsl:choose>
																		<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
																			<xsl:value-of select="@extension" />
																		</xsl:when>
																	</xsl:choose>
																</xsl:for-each>
															</xsl:if>
														</td>
														<td class="doclabel" style="width:8%;">病区：</td>
														<td class="docdataitem" style="width:12%;">
															<xsl:for-each
																select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
																<xsl:choose>
																	<xsl:when
																		test="sc:observation/sc:value/@codeSystem='1.2.156.112656.1.1.33'">
																		<xsl:value-of select="sc:observation/sc:value" />
																	</xsl:when>
																</xsl:choose>
															</xsl:for-each>
														</td>
														<td class="doclabel" style="width:8%;">床号：</td>
														<td class="docdataitem" style="width:12%;">
															<xsl:value-of
																select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf" />
														</td>
														<td class="doclabel" style="width:8%;">编号：</td>
														<td class="docdataitem" style="width:12%;">
															<xsl:value-of select="sc:inFulfillmentOf/sc:order/sc:id[position()=1]/@extension" />
														</td>
													</tr>
													<tr class="odd">
														<td class="doclabel" style="width:8%;">影像：</td>
														<td colspan="9" style="border-bottom: solid 1px #B3C4D4;width:92%;">
															<xsl:choose> <xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:supply/sc:id/@extension 
																!=''"> 
																<xsl:element name="a"> <xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of 
																select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:supply/sc:id/@extension" 
																/> </xsl:attribute> <xsl:attribute name="target">_blank</xsl:attribute> <img 
																src="../images/pic_jc.png" width="22" height="22" border="0" /> </xsl:element> 
																</xsl:when> </xsl:choose>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
									<xsl:for-each
										select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation">
										<xsl:choose>
											<xsl:when test="sc:code/@code">
											</xsl:when>
											<xsl:otherwise>
												<tr>
													<td class="blockHeader" align="left"
														style="padding-left: 3px;font-weight: bold;">
														<xsl:value-of select="sc:code/@displayName" />
													</td>
												</tr>
												<xsl:choose>
													<xsl:when
														test="sc:entryRelationship/sc:organizer/sc:component/sc:observation">
														<xsl:for-each
															select="sc:entryRelationship/sc:organizer/sc:component/sc:observation">
															<tr>
																<td class="blws"
																	style="border-bottom: solid 1px #B3C4D4; word-break:break-all;word-wrap:break-word;">
																	<xsl:value-of select="sc:code/@displayName" />
																	:
																	<xsl:value-of select="sc:value/@value" />
																</td>
															</tr>
														</xsl:for-each>
													</xsl:when>
													<xsl:otherwise>
														<tr>
															<td class="blws"
																style="border-bottom: solid 1px #B3C4D4; word-break:break-all;word-wrap:break-word;">
																<xsl:value-of select="sc:value" />
															</td>
														</tr>
													</xsl:otherwise>
												</xsl:choose>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:for-each>
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
										<xsl:if test="sc:observation/sc:entryRelationship">
											<tr>
												<td class="blockHeader" align="left"
											style="padding-left: 3px;font-weight: bold;">
													<xsl:value-of select="sc:observation/sc:code/@displayName" />
												</td>
											</tr>	
											<tr>
												<td style="padding:0px;">
													<div>
														<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;border-bottom: solid 1px #B3C4D4; ">
															<tr>
															<xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
																<td width="20%" class="childname" align="left"
																	style="text-indent:2em;padding:0px;">
																	<xsl:value-of select="sc:code/@displayName" />
																</td>
																<td width="10%" class="childname" align="left"
																	style="padding:0px;">
																	<xsl:if test="sc:value/@xsi:type='PQ'">
																		<xsl:value-of select="sc:value/@value" />
																	</xsl:if>
																	<xsl:if test="sc:value/@xsi:type='SC'">
																		<xsl:value-of select="sc:value" />
																	</xsl:if>
																	<xsl:value-of select="sc:value/@unit" />
																	<xsl:value-of select="sc:text" />																	
																</td>
																<xsl:if test="position() mod 3=0">
																<tr></tr>
																</xsl:if>																
																</xsl:for-each>
															</tr>
														</table>
													</div>
												</td>
											</tr>
										</xsl:if>
									</xsl:for-each>	
									<tr>
										<td class="blockHeader" align="left"
											style="padding-left: 3px;font-weight: bold;">
											观察
										</td>
									</tr>
									<tr>
										<td class="blws"
											style="border-bottom: solid 1px #B3C4D4; word-break:break-all;word-wrap:break-word;">
											<pre>
												<xsl:value-of
													select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value[@code='01']/sc:originalText" />
											</pre>
										</td>
									</tr>
									<tr>
										<td class="blockHeader" align="left"
											style="padding-left: 3px;font-weight: bold;">
											结论
										</td>
									</tr>
									<tr>
										<td class="blws"
											style="word-break:break-all;word-wrap:break-word;">
											<pre>
												<xsl:value-of
													select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value[@code='02']/sc:originalText" />
											</pre>
										</td>
									</tr>
									<tr>
										<td style="padding:0px;">
											<div>
												<table border="0" align="center" width="100%"
													cellspacing="1" cellpadding="2"
													style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
													<tr class="odd" valign="bottom">
														<td class="doclabel">报告日期：</td>
														<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
															<xsl:choose>
																<xsl:when test="string-length(sc:author/sc:time/@value)>0">
																	<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)" />-<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)" />-<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)" />
																</xsl:when>
															</xsl:choose>
														</td>
														<td class="doclabel">报告医生:</td>
														<td class="docdataitem">
															<xsl:for-each
																select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name">
																<xsl:value-of select="." />
																<xsl:if test="position()!=last()">
																	&#160;
																</xsl:if>
															</xsl:for-each>
														</td>
													</tr>
													<tr class="odd">
														<td class="note" colspan="4">注:本次结果只对该标本负责</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</xsl:when>
							<!-- 左心功能图文报告检查报告 -->
							<xsl:when
								test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.112' and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='105'">
								<table border="0" align="center" width="100%" cellspacing="1"
									cellpadding="2"
									style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
									<tr>
										<td height="30" style="border-bottom: solid 1px #B3C4D4;" align="center">
											<h3 class="reporttitle">
												<xsl:value-of select="sc:title" />
											</h3>
										</td>
									</tr>
									<tr>
										<td style="padding:0px;">
											<div>
												<table border="0" align="center" width="100%"
													style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
													<tr class="odd">
														<td class="doclabel">姓名：</td>
														<td class="docdataitem">
															<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name" />
														</td>
														<td class="doclabel">性别：</td>
														<td class="docdataitem">
															<xsl:value-of
																select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName" />
														</td>
														<td class="doclabel">年龄：</td>
														<td class="docdataitem">
															<xsl:for-each
																select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
																<xsl:choose>
																	<xsl:when test="sc:observation/sc:code/@code='397669002'">
																		<xsl:value-of select="sc:observation/sc:value" />
																	</xsl:when>
																</xsl:choose>
															</xsl:for-each>
														</td>
														<td class="doclabel">住院号：</td>
														<td class="docdataitem">
															<xsl:if
																test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
																<xsl:for-each select="sc:recordTarget/sc:patientRole/sc:id">
																	<xsl:choose>
																		<xsl:when test="@root='1.2.156.112656.1.2.1.12'">
																			<xsl:value-of select="@extension" />
																		</xsl:when>
																	</xsl:choose>
																</xsl:for-each>
															</xsl:if>
														</td>
													</tr>
													<tr class="odd">
														<td class="doclabel">病区：</td>
														<td class="docdataitem" colspan="3">
															<xsl:for-each
																select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
																<xsl:choose>
																	<xsl:when
																		test="sc:observation/sc:value/@codeSystem='1.2.156.112656.1.1.33'">
																		<xsl:value-of select="sc:observation/sc:value" />
																	</xsl:when>
																</xsl:choose>
															</xsl:for-each>
														</td>
														<td class="doclabel">床号：</td>
														<td class="docdataitem">
															<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf" />
														</td>
														<td class="doclabel">就诊卡号：</td>
														<td class="docdataitem">
															<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='01' or sc:componentOf/sc:encompassingEncounter/sc:code/@code='0201'">
																<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112656.1.2.1.12']/@extension"/>
															</xsl:if>
															<xsl:if test="sc:componentOf/sc:encompassingEncounter/sc:code/@code='03'">
																<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112656.1.2.1.3']/@extension"/>
															</xsl:if>
														</td>
													</tr>
													<tr class="odd">
														<td class="doclabel">影像：</td>
														<td colspan="7" style="border-bottom: solid 1px #B3C4D4;">
															<xsl:choose>
															 <xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:supply/sc:id/@extension 
																"> 
																<xsl:element name="a"> 
																<xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of 
																select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:supply/sc:id/@extension"/> 
																</xsl:attribute> 
																<xsl:attribute name="target">_blank</xsl:attribute>
																<img src="../images/pic_jc.png" width="22" height="22" border="0" />
																</xsl:element> 
																</xsl:when>
															</xsl:choose>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
									<tr>
										<td style="padding:0px;">
											<div align="center" style="margin-top:10px;margin-bottom:10px;">
												<table border="0" align="center" width="50%"
													cellspacing="1" cellpadding="2"
													style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
													<tr>
													<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation[sc:code/@code]">
														<xsl:if test="count(sc:entryRelationship)=0">
															
															<td class="childname" style="padding:0px;width:20%;">
																<xsl:value-of select="sc:code/@displayName" />
															</td>
															<td class="childname" style="padding:0px;width:10%;">
																<xsl:if test="sc:value/@xsi:type='PQ'">
																	<xsl:value-of select="sc:value/@value" />
																</xsl:if>
																<xsl:if test="sc:value/@xsi:type='SC'">
																	<xsl:value-of select="sc:value" />
																</xsl:if>
															</td>
															<td class="childname" style="padding:0px;width:20%;">
																<xsl:value-of select="sc:value/@unit" />
															</td>
															<xsl:if test="position() mod 2=0">
															<tr></tr>
															</xsl:if>
														</xsl:if>
													</xsl:for-each>
													</tr>
												</table>
											</div>
										</td>
									</tr>
									<tr>
										<td style="padding:0px;">
											<div>
												<table border="0" align="center" width="100%"
													cellspacing="1" cellpadding="2"
													style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
													<tr class="odd" valign="bottom">
														<td class="doclabel" style="border-top: solid 1px #B3C4D4;">报告医生:</td>
														<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
															<xsl:for-each select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name">
																<xsl:value-of select="." />
																<xsl:if test="position()!=last()">
																	&#160;
																</xsl:if>
															</xsl:for-each>
														</td>
														<td class="doclabel" style="border-top: solid 1px #B3C4D4;">报告日期：</td>
														<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
															<xsl:choose>
																<xsl:when test="string-length(sc:author/sc:time/@value)>0">
																	<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)" />-<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)" />-<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)" />
																</xsl:when>
															</xsl:choose>
														</td>
													</tr>
													<tr class="odd">
														<td class="note" colspan="4">注:本次结果只对该标本负责</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</xsl:when>	
							<!-- 老年科动态心电检查报告 -->
							<xsl:when
								test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@codeSystem='1.2.156.112656.1.1.112' and sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:code/@code='106'">
								<table border="0" align="center" width="100%" cellspacing="1"
									cellpadding="2"
									style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
									<tr>
										<td height="30" style="border-bottom: solid 1px #B3C4D4;" align="center">
											<h3 class="reporttitle">
												<xsl:value-of select="sc:title" />
											</h3>
										</td>
									</tr>
									<tr>
										<td style="padding:0px;">
											<div>
												<table border="0" align="center" width="100%"
													cellspacing="1" cellpadding="2"
													style="border-collapse:collapse;table-layout:fixed;border-bottom: solid 1px #B3C4D4;bgcolor:white;">
													<tr class="odd">
														<td class="doclabel">姓名：</td>
														<td class="docdataitem">
															<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name" />
														</td>
														<td class="doclabel">性别：</td>
														<td class="docdataitem">
															<xsl:value-of
																select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName" />
														</td>
														<td class="doclabel">年龄：</td>
														<td class="docdataitem">
															<xsl:for-each
																select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
																<xsl:choose>
																	<xsl:when test="sc:observation/sc:code/@code='397669002'">
																		<xsl:value-of select="sc:observation/sc:value" />
																	</xsl:when>
																</xsl:choose>
															</xsl:for-each>
														</td>
														<td class="doclabel">ID：</td>
														<td class="docdataitem">
															<xsl:value-of select="sc:inFulfillmentOf/sc:order/sc:id[position()=1]/@extension" />
														</td>
													</tr>
													<tr class="odd">
														<td class="doclabel">病区：</td>
														<td class="docdataitem" colspan="3">
															<xsl:for-each
																select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry">
																<xsl:choose>
																	<xsl:when
																		test="sc:observation/sc:value/@codeSystem='1.2.156.112656.1.1.33'">
																		<xsl:value-of select="sc:observation/sc:value" />
																	</xsl:when>
																</xsl:choose>
															</xsl:for-each>
														</td>
														<td class="doclabel">床号：</td>
														<td class="docdataitem" colspan="3">
															<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr/sc:careOf" />
														</td>
													</tr>
													<tr class="odd">
														<td class="doclabel">影像：</td>
														<td colspan="7" style="border-bottom: solid 1px #B3C4D4;">
															<xsl:choose> <xsl:when test="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:supply/sc:id/@extension 
																!=''"> 
																<xsl:element name="a"> <xsl:attribute name="href"><xsl:value-of select="Constants:getImageCenterUrl()" /><xsl:value-of 
																select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:supply/sc:id/@extension" 
																/> </xsl:attribute> <xsl:attribute name="target">_blank</xsl:attribute> <img 
																src="../images/pic_jc.png" width="22" height="22" border="0" /> </xsl:element> 
																</xsl:when> </xsl:choose>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
									<tr>
										<td class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
											摘要报告
										</td>
									</tr>							
									<tr>
										<td class="blws" style="word-break:break-all;word-wrap:break-word;padding:0px;">
										<div>
											<table border="0" align="center" width="100%"
													cellspacing="1" cellpadding="2"
													style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
											<tr>
												<xsl:for-each
														select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:organizer/sc:component/sc:observation">
														<xsl:choose>
														<xsl:when test="sc:entryRelationship and position()=1"> 
														<xsl:for-each select="sc:entryRelationship/sc:organizer/sc:component/sc:observation">														
															<td style="padding:0px;">
																<xsl:value-of select="sc:code/@displayName" />
															</td>
															<td style="padding:0px;">
																<xsl:if test="sc:value/@xsi:type='SC'">
																<xsl:value-of select="sc:value"/>
																</xsl:if>
																<xsl:if test="sc:value/@xsi:type='PQ'">
																<xsl:value-of select="sc:value/@value"/><xsl:value-of select="sc:value/@unit"/>
																</xsl:if>
															</td>
														<xsl:if test="position() mod 3=0">
														<tr></tr>
														</xsl:if>
														</xsl:for-each>
														</xsl:when>
														</xsl:choose>
												</xsl:for-each>											
											</tr>
											</table>
										</div>
										</td>
									</tr>														
									<tr>
										<td style="padding:0px;">
											<div>
												<table border="0" align="center" width="100%"
													cellspacing="1" cellpadding="2"
													style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
													<xsl:for-each
														select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:entryRelationship/sc:organizer/sc:component">
														<xsl:if test="sc:observation/sc:entryRelationship and position()>1">
															<tr>
																<td align="center">
																	<div>
																		<table width="100%" style="word-wrap:break-word;">
																			<tr>
																				<td class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
																					<xsl:value-of select="sc:observation/sc:code/@displayName" />
																				</td>
																			</tr>
																			<xsl:for-each
																				select="sc:observation/sc:entryRelationship/sc:organizer/sc:component">
																				<tr>
																					<td>
																						<div>
																							<table border="0" align="center" width="100%"
																								cellspacing="1" cellpadding="2">
																								<tr>
																									<td width="25%" class="childname"
																										style="text-indent:2em;padding:0px;">
																										<xsl:value-of select="sc:observation/sc:code/@displayName" />
																									</td>
																									<td width="35%" class="childname"
																										style="text-indent:2em;padding:0px;">
																										<xsl:if test="sc:observation/sc:value/@xsi:type='PQ'">
																											<xsl:value-of select="sc:observation/sc:value/@value" />
																										</xsl:if>
																										<xsl:if test="sc:observation/sc:value/@xsi:type='SC'">
																											<xsl:value-of select="sc:observation/sc:value" />
																										</xsl:if>
																									</td>
																									<td width="40%" class="childname"
																										style="text-indent:2em;padding:0px;">
																										<xsl:value-of select="sc:observation/sc:value/@unit" />
																										<xsl:value-of select="sc:observation/sc:text" />
																									</td>
																								</tr>
																							</table>
																						</div>
																					</td>
																				</tr>
																			</xsl:for-each>
																		</table>
																	</div>
																</td>
															</tr>
														</xsl:if>
													</xsl:for-each>
												</table>
											</div>
										</td>
									</tr>
									<tr>
										<td class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; font-weight: bold;">
											诊断报告:
										</td>
									</tr>							
									<tr>
										<td class="blws" style="word-break:break-all;word-wrap:break-word;">
										<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:organizer/sc:component/sc:observation/sc:value[@code='02']/sc:originalText"/></pre>
										</td>
									</tr>		
									<tr>
										<td style="padding:0px;">
											<div>
												<table border="0" align="center" width="100%"
													cellspacing="1" cellpadding="2"
													style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
													<tr class="odd" valign="bottom">
														<td class="doclabel" style="border-top: solid 1px #B3C4D4;">签字:</td>
														<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
															<xsl:for-each select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name">
																<xsl:value-of select="." />
																<xsl:if test="position()!=last()">
																	&#160;
																</xsl:if>
															</xsl:for-each>
														</td>
														<td class="doclabel" style="border-top: solid 1px #B3C4D4;">日期：</td>
														<td class="docdataitem" style="border-top: solid 1px #B3C4D4;">
															<xsl:choose>
																<xsl:when test="string-length(sc:author/sc:time/@value)>0">
																	<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)" />-<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)" />-<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)" />
																</xsl:when>
															</xsl:choose>
														</td>
													</tr>
													<tr class="odd">
														<td class="note" colspan="4">注:本次结果只对该标本负责</td>
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
