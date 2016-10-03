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
						return '<xsl:value-of select="sc:setId/@extension"/>'+'<xsl:value-of select="sc:id/@extension"/>' ;
				     }
			    </script>
			    <script type="text/javascript" src="../scripts/doc/docDetail.js"></script>
			</head>
			<body>
				<div id="mainContent">
					<div name="selectTabs">
						<div class="responsability"><xsl:value-of select="Constants:getResponsabilityClaimingContent()"/></div>
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
							<tr>
								<td colspan="6" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;">
								<!--  <h3><xsl:value-of select="sc:custodian/sc:assignedCustodian/sc:representedCustodianOrganization/sc:name"/></h3>-->
								<h3><xsl:value-of select="sc:title"/></h3>
								</td>
							</tr>
							<tr>
								<td class="doclabel">病人姓名：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/></td>
								
								<td class="doclabel">性别：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/></td>
								
								<!--<td class="doclabel">出生日期：</td>
								<td class="docdataitem"><xsl:choose>
									<xsl:when test="string-length(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value)>8">
										<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,7,2)"/>日<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,9,2)"/>时<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value)>0">
										<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose></td>-->
								<td class="doclabel">患者年龄：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:age/@value"/></td>
								
							</tr>
							<tr>
								<!-- //$Author: chang_xuewen	
									 // $Date : 2013/05/27 15:30
									 // $[BUG]0015172 MODIFY BEGIN -->
								<td class="doclabel">病区：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								<!-- // $[BUG]0015172 MODIFY END -->
								<td class="doclabel">床号：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
							
								<td class="doclabel">住院号：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:recordTarget/sc:patientRole/sc:id[@root='1.2.156.112656.1.2.1.12']/@extension"/>
								</td>
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
							</tr> 
							<tr>
								<td class="doclabel">管理机构名称：</td>
								<td class="docdataitem"><xsl:value-of select="sc:custodian/sc:assignedCustodian/sc:representedCustodianOrganization/sc:name"/></td>
								
								<td class="doclabel">修改医生名称：</td>
								<td class="docdataitem"><xsl:value-of select="sc:participant/sc:associatedEntity/sc:associatedPerson/sc:name"/></td>
								
								<td class="doclabel">修改日期：</td>
								<td class="docdataitem"><xsl:choose>
									<xsl:when test="string-length(sc:participant/sc:time/@value)>8">
										<xsl:value-of select="substring(sc:participant/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:participant/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:participant/sc:time/@value,7,2)"/>日<xsl:value-of select="substring(sc:participant/sc:time/@value,9,2)"/>时<xsl:value-of select="substring(sc:participant/sc:time/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:participant/sc:time/@value)>0">
										<xsl:value-of select="substring(sc:participant/sc:time/@value,1,4)"/>年<xsl:value-of select="substring(sc:participant/sc:time/@value,5,2)"/>月<xsl:value-of select="substring(sc:participant/sc:time/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose></td>
							</tr>
							<tr>
								<td class="doclabel">电子签章号：</td>
								<td class="docdataitem"><xsl:value-of select="sc:legalAuthenticator/sc:assignedEntity/sc:id/@extension"/></td>
								
								<td class="doclabel">影像索引号：</td>
								<td class="docdataitem" colspan="3"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry/sc:organizer/sc:component/sc:supply/sc:id/@extension"/></td>
							</tr>-->
							<tr>
							 <td class="doclabel">手术名称：</td>
							    <td class="docdataitem">
								    <xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry[sc:procedure]">
										<xsl:if test="position()>1">、</xsl:if>
										<xsl:value-of select="sc:procedure/sc:code/@displayName"/>
									</xsl:for-each>	
							    </td>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry[sc:procedure][1]">
							    <td class="doclabel">手术开始时间：</td>
									<td class="docdataitem"><xsl:choose>
									<xsl:when test="string-length(sc:procedure/sc:effectiveTime/sc:low/@value)>8">
										<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:low/@value,1,4)"/>年<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:low/@value,5,2)"/>月<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:low/@value,7,2)"/>日<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:low/@value,9,2)"/>时<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:low/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:procedure/sc:effectiveTime/sc:low/@value)>0">
										<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:low/@value,1,4)"/>年<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:low/@value,5,2)"/>月<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:low/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose></td>
								
								<td class="doclabel">手术结束时间：</td>
									<td class="docdataitem"><xsl:choose>
									<xsl:when test="string-length(sc:procedure/sc:effectiveTime/sc:high/@value)>8">
										<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:high/@value,1,4)"/>年<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:high/@value,5,2)"/>月<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:high/@value,7,2)"/>日<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:high/@value,9,2)"/>时<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:high/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:procedure/sc:effectiveTime/sc:high/@value)>0">
										<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:high/@value,1,4)"/>年<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:high/@value,5,2)"/>月<xsl:value-of select="substring(sc:procedure/sc:effectiveTime/sc:high/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose></td>
							</xsl:for-each>	
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='47519-4']/sc:entry[sc:procedure][1]">
								<tr>
									<td class="doclabel">手术者：</td>
									<td class="docdataitem"><xsl:for-each select="sc:procedure/sc:performer">
											<xsl:if test="position()>1">、</xsl:if>
											<xsl:value-of select="sc:assignedEntity/sc:assignedPerson/sc:name"/>
										</xsl:for-each></td>
									
									<td class="doclabel">助手：</td>
									<td class="docdataitem"><xsl:for-each select="sc:procedure/sc:participant[sc:participantRole/sc:code/@displayName='助手']">
											<xsl:if test="position()>1">、</xsl:if>
											<xsl:value-of select="sc:participantRole/sc:playingEntity/sc:name"/>
										</xsl:for-each></td>
										
									<td class="doclabel">护士：</td>
									<td class="docdataitem"><xsl:for-each select="sc:procedure/sc:participant[sc:participantRole/sc:code/@displayName='护士']">
										<xsl:if test="position()>1">、</xsl:if>
										<xsl:value-of select="sc:participantRole/sc:playingEntity/sc:name"/>
									</xsl:for-each></td>
								</tr>
							</xsl:for-each> 
							<tr class="odd">
								<td class="doclabel">麻醉方式：</td>
								<td class="docdataitem"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='10213-7']/sc:entry/sc:observation/sc:value/@displayName"/></td>
								
								<td class="doclabel">麻醉医师：</td>
								<td class="docdataitem"><xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='10213-7']/sc:entry/sc:observation/sc:performer">
										<xsl:if test="position()>1">、</xsl:if>
										<xsl:value-of select="sc:assignedEntity/sc:assignedPerson/sc:name"/>
									</xsl:for-each></td>
								<td class="doclabel">体位：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='8724-7']/sc:entry/sc:observation/sc:entryRelationship/sc:observation/sc:value/@displayName"/>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">皮肤消毒：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='8724-7']/sc:entry/sc:observation/sc:entryRelationship/sc:observation[sc:code/@code='DE08.50.057.00']/sc:value"/>
								</td>
								<td class="doclabel">手术切口描述：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='8724-7']/sc:entry/sc:observation/sc:entryRelationship/sc:observation[sc:code/@code='DE06.00.321.00']/sc:value"/>
								</td>
								<td class="doclabel">送验标本名称：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='8724-7']/sc:entry/sc:observation/sc:entryRelationship/sc:observation[sc:code/@displayName='送验标本名称']/sc:value"/>
								</td>
							</tr>
							<tr class="odd">
								<td class="doclabel">引流材料名称：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11537-8']/sc:entry/sc:observation/sc:entryRelationship/sc:observation[sc:code/@code='DE08.50.044.00']/sc:value"/>
								</td>
								<td class="doclabel">数目：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11537-8']/sc:entry/sc:observation/sc:entryRelationship/sc:observation[sc:code/@code='DE08.50.045.00']/sc:value"/>
								</td>
								<td class="doclabel">放置部位：</td>
								<td class="docdataitem">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11537-8']/sc:entry/sc:observation/sc:entryRelationship/sc:observation[sc:code/@code='DE06.00.341.00']/sc:value"/>
								</td>
							</tr>
							<tr>
							    <td class="doclabel">术中用药(麻醉药品除外)：</td>
							    <td colspan="3" class="docdataitem" >
							    <xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='10160-0']/sc:entry">
								    <xsl:if test="position()>1">、</xsl:if>
							    		<xsl:value-of select="sc:observation/sc:value"/>
							    	</xsl:for-each>
							    </td>
							    <td class="doclabel">输血：</td>
							    <td class="docdataitem" >
							    	<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='56836-0']/sc:entry/sc:observation/sc:value/@value"/>
							    </td>
							</tr>
						</table>
						<table border="0" align="center" width="100%" cellspacing="2" cellpadding="1" style="border-collapse:collapse;table-layout:fixed;" class="table"> 
							<tr>
								<td class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">
								手术经过
								</td>
							</tr>
							<tr>
							<xsl:choose>
								<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='8724-7']/sc:entry/sc:observation/sc:value)=0">
									<td class="blws" height="30" style="text-align:center;height:30px;">没有相关内容！</td>
								</xsl:when>
								<xsl:when test="string-length(sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='8724-7']/sc:entry/sc:observation/sc:value)>0">
									<td class="blws" style="word-break:break-all;word-wrap:break-word;">
									<pre><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='8724-7']/sc:entry/sc:observation/sc:value"/></pre>
									</td>
								</xsl:when></xsl:choose>
							</tr>
						</table>
						<table align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;" class="table">
							<tr>
								<td colspan="2" class="blockHeader" height="27" align="left"
									style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4;font-weight: bold;">
									诊断信息
								</td>
							</tr>
							<!-- <tr height="28" class="tabletitle">
								<td width="20%">诊断类别名称</td>
								<td width="30%">疾病名称</td> 
							</tr>-->
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='10219-4']/sc:entry">
								    <xsl:if test="position() mod 2=0">
								    	<tr class="odd">
											<td><xsl:if test="position()=1">术前诊断：</xsl:if></td>
											<td><xsl:value-of select="sc:observation/sc:value/@displayName"/></td>
									    </tr>
								    </xsl:if>
								    <xsl:if test="position() mod 2=1">
								    	<tr class="even">
											<td><xsl:if test="position()=1">术前诊断：</xsl:if></td>
											<td><xsl:value-of select="sc:observation/sc:value/@displayName"/></td>
									    </tr>
								    </xsl:if>
							</xsl:for-each>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='10218-6']/sc:entry">
								    <xsl:if test="position() mod 2=0">
								    	<tr class="odd">
											<td><xsl:if test="position()=1">术中诊断：</xsl:if></td>
											<td><xsl:value-of select="sc:observation/sc:value/@displayName"/></td>
									    </tr>
								    </xsl:if>
								    <xsl:if test="position() mod 2=1">
								    	<tr class="even">
											<td><xsl:if test="position()=1">术中诊断：</xsl:if></td>
											<td><xsl:value-of select="sc:observation/sc:value/@displayName"/></td>
									    </tr>
								    </xsl:if>
							</xsl:for-each>
						</table>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
