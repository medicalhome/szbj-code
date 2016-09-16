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
				<script type="text/javascript" src="../scripts/doc/docList.js"></script>
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
							<tr>
								<td colspan="6" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"><h3><xsl:value-of select="sc:title"/></h3></td>
							</tr>
							<tr>
								<td class="doclabel">病人姓名：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/></td>
								
								<td class="doclabel">病人科室：</td>
								<td class="docdataitem" colspan="3"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:name"/></td>
							</tr>
							<tr>
								<td class="doclabel">记录医生：</td>
								<td class="docdataitem"><xsl:value-of select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name"/></td>
								
								<td class="doclabel">记录日期：</td>
								<td class="docdataitem">
									<xsl:choose>
										<xsl:when test="string-length(sc:author/sc:time/@value)>8">
											<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>日<xsl:value-of select="substring(sc:author/sc:time/@value,9,2)"/>时<xsl:value-of select="substring(sc:author/sc:time/@value,11,2)"/>分
										</xsl:when>
										<xsl:when test="string-length(sc:author/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:author/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:author/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:author/sc:time/@value,7,2)"/>日
										</xsl:when>
									</xsl:choose>
								</td>
								
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
								<td class="doclabel">审核医生名称：</td>
								<td class="docdataitem"><xsl:for-each select="sc:authenticator">
								<xsl:if test="position()>1">、</xsl:if>
								<xsl:value-of select="sc:assignedEntity/sc:assignedPerson/sc:name"/></xsl:for-each></td>
								
								<td class="doclabel">审核日期：</td>
								<td class="docdataitem" colspan="3"><xsl:for-each select="sc:authenticator">
								<xsl:choose>
									<xsl:when test="string-length(sc:time/@value)>8">
										<xsl:value-of select="substring(sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:time/@value,7,2)"/>日<xsl:value-of select="substring(sc:time/@value,9,2)"/>时<xsl:value-of select="substring(sc:time/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:time/@value)>0">
										<xsl:value-of select="substring(sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:time/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose>
								<xsl:if test="(position()!=last())and(string-length(sc:time/@value)>0)">、</xsl:if>
								</xsl:for-each></td>
							</tr> -->
							<tr>
								<td class="doclabel">修改医生名称：</td>
								<td class="docdataitem"><xsl:value-of select="sc:participant/sc:associatedEntity/sc:associatedPerson/sc:name"/></td>
								
								<td class="doclabel">修改日期：</td>
								<td class="docdataitem" colspan="3">
									<xsl:choose>
										<xsl:when test="string-length(sc:participant/sc:time/@value)>8">
											<xsl:value-of select="substring(sc:participant/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:participant/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:participant/sc:time/@value,7,2)"/>日<xsl:value-of select="substring(sc:participant/sc:time/@value,9,2)"/>时<xsl:value-of select="substring(sc:participant/sc:time/@value,11,2)"/>分
										</xsl:when>
										<xsl:when test="string-length(sc:participant/sc:time/@value)>0">
											<xsl:value-of select="substring(sc:participant/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:participant/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:participant/sc:time/@value,7,2)"/>日
										</xsl:when>
									</xsl:choose>
								</td>
							</tr>
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">知情同意或讨论记录</td>
							</tr>
							<tr>
								<td colspan="6" style="text-align:center;height:30px;" class="blws">
									<xsl:choose>
										<xsl:when test="string-length(sc:legalAuthenticator/sc:assignedEntity/sc:id/@extension)>0">
											<xsl:variable name="signatureId" select="sc:legalAuthenticator/sc:assignedEntity/sc:id/@extension"/>
											<a href='javascript:void(0);' onclick="previewDoc('http://172.16.100.39:7001/DocServer/downloadcebx.do','$signatureId');return false;">查看知情同意内容</a>
										</xsl:when>
										<xsl:when test="string-length(sc:legalAuthenticator/sc:assignedEntity/sc:id/@extension)=0">
											没有知情同意内容以供查看
										</xsl:when>
									</xsl:choose>
								</td>
							</tr> 
						</table>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>





