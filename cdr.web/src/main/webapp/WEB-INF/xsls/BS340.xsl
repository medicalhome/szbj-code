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
				     function showDetail(obj) {
				     
				     var tr = $(obj).parent().parent().parent().siblings()[1];
				     var dis = $(obj).parent().parent().parent().siblings()[1].style.display;
				     if(dis == "none"){
				     	obj.src="../images/top.gif";
				     }else{
				     	obj.src="../images/bottom.gif";
				     }
				     $(tr).slideToggle('show');
					}

				     
			    </script>
			   <script type="text/javascript" src="../scripts/doc/docDetail.js"></script>
			</head>
			<body>
				<div name="mainContent">
					<div name="selectTabs">
						<div class="responsability"><xsl:value-of select="Constants:getResponsabilityClaimingContent()"/></div>
							<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
								<tr>
									<td colspan="6" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"><h3><xsl:value-of select="sc:title"/></h3></td>
								</tr>
								<tr>
									<td style="text-align: right;"></td>					
									<td></td>					
									<td style="text-align: right;"></td>					
									<td></td>	
									<td style="text-align: right;">会诊紧急程度：</td>
									<xsl:choose>
									<xsl:when test="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@displayName='会诊原因']/sc:section/sc:entry[sc:observation/sc:code/@displayName='会诊紧急程度']/sc:observation/sc:value='急'">			
										<td class="docdataitemlinered" style="text-align: left;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@displayName='会诊原因']/sc:section/sc:entry[sc:observation/sc:code/@displayName='会诊紧急程度']/sc:observation/sc:value"/></td>					
									</xsl:when>	
									<xsl:otherwise>
										<td class="docdataitem" style="text-align: left;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component[sc:section/sc:code/@displayName='会诊原因']/sc:section/sc:entry[sc:observation/sc:code/@displayName='会诊紧急程度']/sc:observation/sc:value"/></td>
									</xsl:otherwise>
									</xsl:choose>
								</tr>
							</table>
							<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
								<tr>
					         	   <td colspan="6" class="blockHeader" height="0" align="center" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"></td>
					        	</tr>
								<tr>
									<td class="doclabel">病人姓名：</td>
									<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/></td>
									
									<td class="doclabel">性别：</td>
									<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/></td>
									
								 	<td class="doclabel">年龄：</td>
									<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:age/@value"/><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:age/@unit"/></td>											
								</tr>
								<tr>
									<td class="doclabel">婚姻：</td>
									<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:maritalStatusCode/@displayName"/></td>
									
									<td class="doclabel">职业：</td>
									<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:occupation/sc:occupationCode/@displayName"/></td>
							
									<td class="doclabel">病区：</td>
									<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								</tr>
								<tr>
									<td class="doclabel">床位号：</td>
									<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
									
									<td class="doclabel">病人科室：</td>
									<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
									
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
							
							
							
							
					   <!-- <tr>
								<td colspan="9" align="left"
									style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"></td>
							</tr>							
							<tr>
								<td class="doclabel">
									<div align="left">
										<img name="button_detail" src="../images/bottom.gif"
											onclick="showDetail(this)" style="cursor: hand;"/>
									</div>
								</td>
								<td class="doclabel">所属医院：</td>
								<td class="docdataitem"><xsl:value-of select="sc:custodian/sc:assignedCustodian/sc:representedCustodianOrganization/sc:name"/></td>

								<td class="doclabel">科室：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:name"/></td>
							
								<td class="doclabel">记录医生：</td>
								<td class="docdataitem"><xsl:value-of select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name"/></td>
								
								<td class="doclabel">记录日期：</td>
								<td class="docdataitem"><xsl:choose>
									<xsl:when test="string-length(sc:author/sc:time/@value)>8">
										<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>日<xsl:value-of select="substring(sc:author/sc:time/@value,9,2)"/>时<xsl:value-of select="substring(sc:author/sc:time/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:author/sc:time/@value)>0">
										<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose></td>
							</tr>
				 		<tr name="recordText">
							<xsl:choose>
									<xsl:when test="string-length(sc:component/sc:nonXMLBody/sc:text)>0">
										<td colspan="9" height="30" class="blws" style="height:30px;word-break:break-all;word-wrap:break-word;"><pre><xsl:value-of select="sc:component/sc:nonXMLBody/sc:text"/></pre></td>
									</xsl:when>
									<xsl:when test="string-length(sc:component/sc:nonXMLBody/sc:text)=0">
										<td colspan="9" style="text-align:center;height:30px;" class="blws">没有相关内容！</td>
									</xsl:when>
								</xsl:choose>
								
							</tr> -->
						</table>
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
							<tr>
					        	<td colspan="6" class="blockHeader" height="25" align="center" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"></td>
					        </tr>
							<tr>
								<td class="doclabel">申请会诊医师：</td>
								<td class="docdataitem"><xsl:value-of select="sc:authenticator[sc:assignedEntity/sc:code/@displayName='会诊申请医师']/sc:assignedEntity/sc:assignedPerson/sc:name"/></td>
									
								<td class="doclabel">申请日期：</td>
								<td class="docdataitem">
									<xsl:choose>
										<xsl:when test="string-length(sc:authenticator[sc:assignedEntity/sc:code/@displayName='会诊申请医师']/sc:time/@value)>8">
											<xsl:value-of select="substring(sc:authenticator[sc:assignedEntity/sc:code/@displayName='会诊申请医师']/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:authenticator[sc:assignedEntity/sc:code/@displayName='会诊申请医师']/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:authenticator[sc:assignedEntity/sc:code/@displayName='会诊申请医师']/sc:time/@value,7,2)"/>日<xsl:value-of select="substring(sc:authenticator[sc:assignedEntity/sc:code/@displayName='会诊申请医师']/sc:time/@value,9,2)"/>时<xsl:value-of select="substring(sc:authenticator[sc:assignedEntity/sc:code/@displayName='会诊申请医师']/sc:time/@value,11,2)"/>分
										</xsl:when>
										<xsl:when test="string-length(sc:authenticator[sc:assignedEntity/sc:code/@displayName='会诊申请医师']/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:authenticator[sc:assignedEntity/sc:code/@displayName='会诊申请医师']/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:authenticator[sc:assignedEntity/sc:code/@displayName='会诊申请医师']/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:authenticator[sc:assignedEntity/sc:code/@displayName='会诊申请医师']/sc:time/@value,7,2)"/>日
										</xsl:when>
									</xsl:choose>
								</td>
									
								<td class="doclabel">会诊类型：</td>
								<td class="docdataitem"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='会诊原因']/sc:entry/sc:observation[sc:code/@code='DE06.00.319.00']/sc:value"/></td>											
							</tr>
							<tr>
								<td class="doclabel">病例摘要：</td>
								<td class="docdataitem" colspan="5"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='51848-0']/sc:entry/sc:observation[sc:code/@code='DE06.00.182.00']/sc:value"/></td>
							</tr>
							<tr>		
								<td class="doclabel">申请会诊目的和要求：</td>
								<td class="docdataitem" colspan="5"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='18776-5']/sc:entry/sc:observation[sc:code/@code='DE06.00.214.00']/sc:value"/></td>
							</tr>
							
													
						</table>
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
							<tr>
					        	<td colspan="6" class="blockHeader" height="25" align="center" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"></td>
					        </tr>
							<xsl:for-each select="sc:authenticator[sc:assignedEntity/sc:code/@displayName='会诊医师']">
								<tr>
									<td class="doclabel">会诊医师：</td>
									<td class="docdataitem"><xsl:value-of select="sc:assignedEntity/sc:assignedPerson/sc:name"/></td>
									<td class="doclabel">会诊日期：</td>
									<td class="docdataitem">
										<xsl:choose>
											<xsl:when test="string-length(sc:time/@value)>8">
												<xsl:value-of select="substring(sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:time/@value,7,2)"/>日<xsl:value-of select="substring(sc:time/@value,9,2)"/>时<xsl:value-of select="substring(sc:time/@value,11,2)"/>分
											</xsl:when>
											<xsl:when test="string-length(sc:time/@value)>0">
												<xsl:value-of select="substring(sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:time/@value,7,2)"/>日
											</xsl:when>
										</xsl:choose>
									</td>
									<td class="doclabel">会诊医师科室：</td>
									<td class="docdataitem"><xsl:value-of select="sc:assignedEntity/sc:representedOrganization/sc:name"/></td>								
								</tr>
							</xsl:for-each>
							<tr>									
								<td class="doclabel">会诊医师意见：</td>
								<td class="docdataitem" colspan="5"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@displayName='会诊意见']/sc:entry/sc:observation[sc:code/@code='DE06.00.018.00']/sc:value"/></td>											
							</tr>
									
						</table>
					</div>
					
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>





