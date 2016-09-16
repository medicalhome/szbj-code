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
						return '<xsl:value-of select="sc:id/@extension"/>';
				     }
			    </script>
			    <script type="text/javascript" src="../scripts/doc/docDetail.js"></script>
			</head>
			<script>
				$('#healthCheckPhoto').attr('src','../doc/image_<xsl:value-of select="sc:id[@root='2.16.840.1.113883.19.4']/@extension"/>_BS347.html');
			</script>
			<body>
				<div id="mainContent">
					<div name="selectTabs">
						<div class="responsability"><xsl:value-of select="Constants:getResponsabilityClaimingContent()"/></div>
					    <table align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;bgcolor:white;">
					        <!-- 体格检查 -->
					        <tr>
					            <td colspan="6" height="35" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"><h3><xsl:value-of select="sc:title"/></h3></td>
					        </tr>
					        <tr>
								<td class="doclabel">姓名：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/></td>
								<td class="doclabel">性别：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/></td>
								<td class="doclabel" rowspan="10" colspan="2">
								<img id="healthCheckPhoto" width="140" height="150"></img>    
								</td>
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
								<td class="doclabel">年龄：</td>
								<td class="docdataitem"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation[sc:code/@code='397669002']/sc:value"/></td>
							</tr>
							<tr>
							    <td class="doclabel">家庭住址：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:addr[@use='H']/sc:streetAddressLine"/></td>
								<td class="doclabel">体检日期：</td>
								<td class="docdataitem"><xsl:choose>
									<xsl:when test="string-length(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value)>8">
										<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,7,2)"/>日<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,9,2)"/>时<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value)>0">
										<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,7,2)"/>日
									</xsl:when></xsl:choose>
								</td>
							</tr>
							<tr>
							    <td class="doclabel">团体编码：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:id/@extension"/></td>
								<td class="doclabel">团体名称：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:providerOrganization/sc:name"/></td>
							</tr>
							<tr>
							    <td class="doclabel">报告医生：</td>
								<td class="docdataitem"><xsl:value-of select="sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name"/></td>
								<td class="doclabel">报告日期：</td>
								<td class="docdataitem"><xsl:value-of select="sc:author/sc:time/@value"/></td>
							</tr>
							<xsl:choose>
								<xsl:when test="Constants:getTrueOrgCode() = Constants:getDisabledOrgCode()">								
									<tr>
									    <td class="doclabel">组织机构编码：</td>
									    <xsl:choose>
									    <xsl:when test="string-length(sc:custodian/sc:assignedCustodian/sc:representedCustodianOrganization/sc:id/@extension)>0">
										<td class="docdataitem"><xsl:value-of select="sc:custodian/sc:assignedCustodian/sc:representedCustodianOrganization/sc:id/@extension"/></td>
										</xsl:when>
										<xsl:when test="string-length(sc:custodian/sc:assignedCustodian/sc:representedCustodianOrganization/sc:id/@extension)=0">
									    <td class="docdataitem">H0001</td>
									    </xsl:when>
									    </xsl:choose>
									    <td class="doclabel">组织机构名称：</td>
									    <xsl:choose>
									    <xsl:when test="string-length(sc:custodian/sc:assignedCustodian/sc:representedCustodianOrganization/sc:name)>0">
										<td class="docdataitem"><xsl:value-of select="sc:custodian/sc:assignedCustodian/sc:representedCustodianOrganization/sc:name"/></td>
										</xsl:when>
										<xsl:when test="string-length(sc:custodian/sc:assignedCustodian/sc:representedCustodianOrganization/sc:name)=0">
									    <td class="docdataitem">北京大学人民医院</td>
									    </xsl:when>
									    </xsl:choose>
									</tr>
								</xsl:when>
							</xsl:choose>
							<tr>
							    <td class="doclabel">备注：</td>
								<td class="docdataitem" colspan="3"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section/sc:entry/sc:observation/sc:value"/></td>
							</tr>
					    </table>
					    <!-- 第一部分 -->
					    <!-- 一般检查 -->
					    <table align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;margin:0;padding:0;" class="table">
					        <tr>
								<td colspan="5" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"><h4><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='22029-3']/sc:title"/></h4></td>
							</tr>
					        <tr>
					            <td colspan="5" class="blockHeader" height="25" align="center" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='22029-3']/sc:component/sc:section[sc:code/@code='248242009']/sc:title"/></td>
					        </tr>
					        <tr class="tabletitle">
							    <td width="18%">检查项目</td>
							    <td width="18%">测量结果</td>
							    <td width="18%">异常描述</td>
							    <td width="28%">正常参考值</td>
							    <td width="18%">单位</td>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='22029-3']/sc:component/sc:section[sc:code/@code='248242009']/sc:entry">
							   <tr class="docdataitem2">
								   <td><xsl:value-of select="sc:observation/sc:code/@displayName"/></td>
								   <td><xsl:value-of select="sc:observation/sc:value/@value"/></td>
								   <td><xsl:value-of select="sc:observation/sc:interpretationCode/@displayName"/></td>
								   <td><xsl:value-of select="sc:observation/sc:referenceRange/sc:observationRange/sc:value/@displayName"/></td>
								   <td><xsl:value-of select="sc:observation/sc:value/@unit"/></td>
							   </tr>
							</xsl:for-each>
					    </table>
					    <!-- 专科检查 -->
					    <table  align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;" class="table">
					        <tr>
					            <td colspan="3" class="blockHeader" height="25" align="center" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='22029-3']/sc:component/sc:section[sc:code/@code='79206001']/sc:title"/></td>
					        </tr>
					        <xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='22029-3']/sc:component/sc:section[sc:code/@code='79206001']/sc:entry">
							    <tr>
								    <td colspan="2" align="left" class="blockHeader" style="border:0;padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"><xsl:value-of select="sc:observation/sc:code/@displayName"/></td>
								    <td align="center" class="blockHeader" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">医师：<xsl:value-of select="sc:observation/sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name"/></td>
							    </tr>
							    <xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer[sc:code/@code='273537009']/sc:component">
							        <tr class="docdataitem2">
									    <td width="30%"><xsl:value-of select="sc:observation/sc:code/@displayName"/></td>
									    <td width="30%"><xsl:value-of select="sc:observation/sc:value/@displayName"/></td>
									    <td width="40%"><xsl:value-of select="sc:observation/sc:value/sc:qualifier/sc:value/sc:originalText"/></td>
								    </tr>
							    </xsl:for-each>
							    <tr class="docdataitem2">
							        <td colspan="3"><font color="red"><strong>小结建议：</strong></font>
							            <xsl:value-of select="sc:observation/sc:value"/>
							        </td>
							    </tr>
							</xsl:for-each>
					    </table>
					    <!-- 医技检查项目 -->
					    <table  align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse" class="table">
					        <tr>
					            <td colspan="3" class="blockHeader" height="25" align="center" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='22029-3']/sc:component/sc:section[sc:code/@code='5880005']/sc:title"/></td>
					        </tr>
					        <xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='22029-3']/sc:component/sc:section[sc:code/@code='5880005']/sc:entry">
							    <tr>
								    <td colspan="2" align="left" class="blockHeader" style="border:0;padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"><xsl:value-of select="sc:observation/sc:code/@displayName"/></td>
								    <td align="center" class="blockHeader" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">医师：<xsl:value-of select="sc:observation/sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name"/></td>
							    </tr>
							    <xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer[sc:code/@code='273537009']/sc:component">
							        <tr class="docdataitem2">
									    <td width="30%"><xsl:value-of select="sc:observation/sc:code/@displayName"/></td>
									    <td width="30%"><xsl:value-of select="sc:observation/sc:value/@displayName"/></td>
									    <td width="40%"><xsl:value-of select="sc:observation/sc:value/sc:qualifier/sc:value/sc:originalText"/></td>
								    </tr>
							    </xsl:for-each>
							    <tr class="docdataitem2">
							        <td colspan="3"><font color="red"><strong>小结建议：</strong></font>
							            <xsl:value-of select="sc:observation/sc:value"/>
									</td>
							    </tr>
							</xsl:for-each>
					    </table>
					    <!-- 实验室检验项目 -->
					    <table  align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;" class="table">
					        <tr>
					            <td colspan="6" class="blockHeader" width="100%" height="25" align="center" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='22029-3']/sc:component/sc:section[sc:code/@code='15220000']/sc:title"/></td>
					        </tr>
					        <xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='22029-3']/sc:component/sc:section[sc:code/@code='15220000']/sc:entry">
					            <tr>
					                <td colspan="4" align="left" class="blockHeader" style="border:0;padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"><xsl:value-of select="sc:observation/sc:entryRelationship/sc:organizer/sc:code/@displayName"/></td>
					                <td colspan="2" align="center" class="blockHeader" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">
					                                                      报告人：<xsl:value-of select="sc:observation/sc:author/sc:assignedAuthor/sc:assignedPerson/sc:name"/>
					                    /审核人：<xsl:value-of select="sc:observation/sc:participant/sc:participantRole/sc:playingEntity/sc:name"/></td>
					            </tr>
					            <tr class="tabletitle">
					                <td width="17%">检验项目</td>
					                <td width="16%">缩写</td>
					                <td width="17%">结果</td>
					                <td width="17%">异常描述</td>
					                <td width="17%">正常参考值</td>
					                <td width="16%">单位</td>
					            </tr>
					            <xsl:for-each select="sc:observation/sc:entryRelationship/sc:organizer/sc:component">
							        <tr class="docdataitem2">
							            <td><xsl:value-of select="sc:observation/sc:code/sc:originalText"/></td>
							            <td><xsl:value-of select="sc:observation/sc:code/@code"/></td>
									    <td><xsl:value-of select="sc:observation/sc:value/@value"/></td>
									    <td><xsl:value-of select="sc:observation/sc:interpretationCode/@displayName"/></td>
									    <td><xsl:value-of select="sc:observation/sc:referenceRange/sc:observationRange/sc:value/@displayName"/></td>
									    <td><xsl:value-of select="sc:observation/sc:value/@unit"/></td>
								    </tr>
							    </xsl:for-each>
							</xsl:for-each>
					    </table>
					    
					    <!-- 第二部分 健康异常提示 -->
					    <!-- 一、体格检查异常部分 -->
					    <table align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;margin:0;padding:0;" class="table">
					        <tr>
					            <td colspan="5" height="25" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"><h4><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11450-4']/sc:title"/></h4></td>
					        </tr>
					        <tr>
					            <td colspan="5" align="left" class="blockHeader" style="border:0;padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11450-4']/sc:component/sc:section[sc:code/@code='71994000']/sc:title"/></td>
					        </tr>
					        <tr class="tabletitle">
							    <td width="18%">检查项目</td>
							    <td width="18%">测量结果</td>
							    <td width="18%">异常描述</td>
							    <td width="28%">正常参考值</td>
							    <td width="18%">单位</td>
							</tr>
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11450-4']/sc:component/sc:section[sc:code/@code='71994000']/sc:component/sc:section[sc:code/@code='248242009']/sc:entry">
							   <tr class="docdataitem2">
								   <td><xsl:value-of select="sc:observation/sc:code/@displayName"/></td>
								   <td><xsl:value-of select="sc:observation/sc:value/@value"/></td>
								   <td><xsl:value-of select="sc:observation/sc:interpretationCode/@displayName"/></td>
								   <td><xsl:value-of select="sc:observation/sc:referenceRange/sc:observationRange/sc:value/@displayName"/></td>
								   <td><xsl:value-of select="sc:observation/sc:value/@unit"/></td>
							   </tr>
							</xsl:for-each>
					    </table>
					    <!-- 专科检查 -->
					    <table  align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;" class="table">
		                    <xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='11450-4']/sc:component/sc:section[sc:code/@code='71994000']/sc:component/sc:section/sc:entry">
							   <tr class="docdataitem2">
								   <td><xsl:if test="position()>1">◆</xsl:if><xsl:value-of select="sc:observation/sc:value"/></td>
							   </tr>
							</xsl:for-each>			    
					    </table>
					    
					    
					    <!-- 第三部分 专家对重点问题的提示 -->
					    <table  align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;" class="table">
					        <tr>
					            <td height="25" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"><h4><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='62385-0']/sc:title"/></h4></td>
					        </tr>
					        <tr>
					            <td><strong><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='62385-0']/sc:component/sc:section[sc:code/@code='311517002']/sc:title"/></strong></td>
					        </tr>
					        <xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='62385-0']/sc:component/sc:section[sc:code/@code='311517002']/sc:entry">
							    <tr>
							    <td>
							        <xsl:value-of select="sc:observation/sc:id/@extension"/>.<xsl:value-of select="sc:observation/sc:value"/>
							    </td>
							    </tr>
							</xsl:for-each>
							<tr height="10"></tr>
							 <tr>
					            <td><strong><xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='62385-0']/sc:component/sc:section[sc:code/@code='370995009']/sc:title"/></strong></td>
					        </tr>
					        <xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='62385-0']/sc:component/sc:section[sc:code/@code='370995009']/sc:entry">
							    <tr>
								    <td align="left"><xsl:value-of select="sc:observation/sc:id/@extension"/>
								    <xsl:value-of select="sc:observation/sc:code/@displayName"/>:
								    <xsl:value-of select="sc:observation/sc:value"/>
							        </td>
							    </tr>
							    <tr height="10"></tr>
							</xsl:for-each>
					    </table>
					 </div>
				 </div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>