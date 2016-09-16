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
						<table border="0" align="center" width="100%" cellspacing="1" cellpadding="2" style="border-collapse:collapse;table-layout:fixed;bgcolor:white;">
							<tr>
								<td colspan="6" height="30" align="center" style="background-color:#fcffe2;border-bottom: solid 1px #B3C4D4;"><h3><xsl:value-of select="sc:title"/></h3></td>
							</tr>
							<tr>
								<td class="doclabel">病人姓名：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:name"/></td>
								
								<td class="doclabel">性别：</td>
								<td class="docdataitem"><xsl:value-of select="sc:recordTarget/sc:patientRole/sc:patient/sc:administrativeGenderCode/@displayName"/></td>
								
							 	<td class="doclabel">出生日期：</td>
								<td class="docdataitem"><xsl:choose>
									<xsl:when test="string-length(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value)>8">
										<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,7,2)"/>日<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,9,2)"/>时<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,11,2)"/>分
									</xsl:when>
									<xsl:when test="string-length(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value)>0">
										<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:recordTarget/sc:patientRole/sc:patient/sc:birthTime/@value,7,2)"/>日
									</xsl:when>
								</xsl:choose></td>						
							</tr>
							
							<tr>					
								<td class="doclabel">床号：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								
								<td class="doclabel">科别：</td>
								<td class="docdataitem"><xsl:value-of select="sc:componentOf/sc:encompassingEncounter/sc:location/sc:healthCareFacility/sc:serviceProviderOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:asOrganizationPartOf/sc:wholeOrganization/sc:name"/></td>
								
								<td class="doclabel">入院日期：</td>						
								<td class="docdataitem">
									<xsl:choose>
										<xsl:when test="string-length(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value)>8">
											<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,7,2)"/>日<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,9,2)"/>时<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,11,2)"/>分
										</xsl:when>
										<xsl:when test="string-length(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value)>0">
											<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,1,4)"/>年<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,5,2)"/>月<xsl:value-of select="substring(sc:componentOf/sc:encompassingEncounter/sc:effectiveTime/@value,7,2)"/>日
										</xsl:when>
									</xsl:choose>
								</td>
								
							</tr>
							<xsl:choose>
								<xsl:when test="Constants:getTrueOrgCode() = Constants:getDisabledOrgCode()">
									<tr>
										<td class="doclabel">文档保管者：</td>
										<td class="docdataitem" colspan="5"><xsl:value-of select="sc:custodian/sc:assignedCustodian/sc:representedCustodianOrganization/sc:name"/></td>
									</tr>
								</xsl:when>
							</xsl:choose>
							
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">跌倒坠床危险因素</td>
							</tr>
							<tr>
								<td class="doclabel">总分：</td>
								<td class="docdataitem" colspan="5">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='57254-5']/sc:entry/sc:observation/sc:value/@value"/>
								
								</td>					
							</tr>
							
													
							<tr>
								<td class="doclabel">
									项目：
								</td>
								<td class="docdataitem" colspan="5">
									<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='57254-5']/sc:entry">
										<xsl:if test="sc:observation/sc:code/@displayName!='总分' and sc:observation/sc:value/@value='true'">
												<pre><xsl:value-of select="sc:observation/sc:code/@displayName"/></pre>																
										</xsl:if>
									</xsl:for-each>
								</td>
							</tr>	
							
							
							<tr>
								<td colspan="6" class="blockHeader" height="27" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;">压疮危险因素</td>
							</tr>	
							<tr>
								<td class="doclabel">总分：</td>
								<td class="docdataitem" colspan="5">
									<xsl:value-of select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='52573-3']/sc:entry/sc:observation/sc:value/@value"/>
								
								</td>					
							</tr>
							<tr>
								<td colspan="5" class="blockHeader" height="10" align="left" style="padding-left: 3px; border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold;"></td>
							</tr>
							<tr>
								<td class="doclabelline">评估内容</td>
								<td class="doclabelline">1分</td>
								<td class="doclabelline">2分</td>
								<td class="doclabelline">3分</td>
								<td class="doclabelline">4分</td>
							</tr>
						 
							<xsl:for-each select="sc:component/sc:structuredBody/sc:component/sc:section[sc:code/@code='52573-3']/sc:entry">
										<tr>
										<xsl:if test="sc:observation/sc:code/@displayName='感知能力' and sc:observation/sc:value='完全受限'">
												<td class="doclabelline">感知能力</td><td class="docdataitemlinered">√完全受限</td>											
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='感知能力' and sc:observation/sc:value!='完全受限'">
												<td class="doclabelline">感知能力</td><td class="docdataitemline">完全受限</td>
										</xsl:if>
											
										<xsl:if test="sc:observation/sc:code/@displayName='感知能力' and sc:observation/sc:value='大部受限'">
												<td class="docdataitemlinered">√大部受限</td>															
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='感知能力' and sc:observation/sc:value!='大部受限'">
												<td class="docdataitemline">大部受限</td>
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='感知能力' and sc:observation/sc:value='轻度受限'">
												<td class="docdataitemlinered">√轻度受限</td>														
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='感知能力' and sc:observation/sc:value!='轻度受限'">
												<td class="docdataitemline">轻度受限</td>
										</xsl:if>
											<xsl:if test="sc:observation/sc:code/@displayName='感知能力' and sc:observation/sc:value='无损害'">
												<td class="docdataitemlinered">√无损害</td>														
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='感知能力' and sc:observation/sc:value!='无损害'">
												<td class="docdataitemline">无损害</td>
										</xsl:if>
										</tr>
										<tr>
										<xsl:if test="sc:observation/sc:code/@displayName='潮湿程度' and sc:observation/sc:value='持续潮湿'">
												<td class="doclabelline">潮湿程度</td><td class="docdataitemlinered">√持续潮湿</td>											
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='潮湿程度' and sc:observation/sc:value!='持续潮湿'">
												<td class="doclabelline">潮湿程度</td><td class="docdataitemline">持续潮湿</td>
										</xsl:if>
											
										<xsl:if test="sc:observation/sc:code/@displayName='潮湿程度' and sc:observation/sc:value='常常潮湿'">
												<td class="docdataitemlinered">√常常潮湿</td>															
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='潮湿程度' and sc:observation/sc:value!='常常潮湿'">
												<td class="docdataitemline">常常潮湿</td>
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='潮湿程度' and sc:observation/sc:value='偶尔潮湿'">
												<td class="docdataitemlinered">√偶尔潮湿</td>														
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='潮湿程度' and sc:observation/sc:value!='偶尔潮湿'">
												<td class="docdataitemline">偶尔潮湿</td>
										</xsl:if>
											<xsl:if test="sc:observation/sc:code/@displayName='潮湿程度' and sc:observation/sc:value='罕见潮湿'">
												<td class="docdataitemlinered">√罕见潮湿</td>														
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='潮湿程度' and sc:observation/sc:value!='罕见潮湿'">
												<td class="docdataitemline">罕见潮湿</td>
										</xsl:if>
										</tr>
										<tr>
										<xsl:if test="sc:observation/sc:code/@displayName='活动能力' and sc:observation/sc:value='卧床'">
												<td class="doclabelline">活动能力</td><td class="docdataitemlinered">√卧床</td>											
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='活动能力' and sc:observation/sc:value!='卧床'">
												<td class="doclabelline">活动能力</td><td class="docdataitemline">卧床</td>
										</xsl:if>
											
										<xsl:if test="sc:observation/sc:code/@displayName='活动能力' and sc:observation/sc:value='坐椅子'">
												<td class="docdataitemlinered">√坐椅子</td>															
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='活动能力' and sc:observation/sc:value!='坐椅子'">
												<td class="docdataitemline">坐椅子</td>
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='活动能力' and sc:observation/sc:value='偶尔步行'">
												<td class="docdataitemlinered">√偶尔步行</td>														
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='活动能力' and sc:observation/sc:value!='偶尔步行'">
												<td class="docdataitemline">偶尔步行</td>
										</xsl:if>
											<xsl:if test="sc:observation/sc:code/@displayName='活动能力' and sc:observation/sc:value='经常步行'">
												<td class="docdataitemlinered">√经常步行</td>														
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='活动能力' and sc:observation/sc:value!='经常步行'">
												<td class="docdataitemline">经常步行</td>
										</xsl:if>
										</tr>
										<tr>
										<xsl:if test="sc:observation/sc:code/@displayName='移动能力' and sc:observation/sc:value='不能移动'">
												<td class="doclabelline">移动能力</td><td class="docdataitemlinered">√不能移动</td>											
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='移动能力' and sc:observation/sc:value!='不能移动'">
												<td class="doclabelline">移动能力</td><td class="docdataitemline">不能移动</td>
										</xsl:if>
											
										<xsl:if test="sc:observation/sc:code/@displayName='移动能力' and sc:observation/sc:value='非常受限'">
												<td class="docdataitemlinered">√非常受限</td>															
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='移动能力' and sc:observation/sc:value!='非常受限'">
												<td class="docdataitemline">非常受限</td>
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='移动能力' and sc:observation/sc:value='轻微受限'">
												<td class="docdataitemlinered">√轻微受限</td>														
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='移动能力' and sc:observation/sc:value!='轻微受限'">
												<td class="docdataitemline">轻微受限</td>
										</xsl:if>
											<xsl:if test="sc:observation/sc:code/@displayName='移动能力' and sc:observation/sc:value='不受限'">
												<td class="docdataitemlinered">√不受限</td>														
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='移动能力' and sc:observation/sc:value!='不受限'">
												<td class="docdataitemline">不受限</td>
										</xsl:if>
										</tr>
											<tr>
										<xsl:if test="sc:observation/sc:code/@displayName='营养摄取' and sc:observation/sc:value='非常差'">
												<td class="doclabelline">营养摄取</td><td class="docdataitemlinered">√非常差</td>											
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='营养摄取' and sc:observation/sc:value!='非常差'">
												<td class="doclabelline">营养摄取</td><td class="docdataitemline">非常差</td>
										</xsl:if>
											
										<xsl:if test="sc:observation/sc:code/@displayName='营养摄取' and sc:observation/sc:value='可能不足'">
												<td class="docdataitemlinered">√可能不足</td>															
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='营养摄取' and sc:observation/sc:value!='可能不足'">
												<td class="docdataitemline">可能不足</td>
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='营养摄取' and sc:observation/sc:value='充足'">
												<td class="docdataitemlinered">√充足</td>														
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='营养摄取' and sc:observation/sc:value!='充足'">
												<td class="docdataitemline">充足</td>
										</xsl:if>
											<xsl:if test="sc:observation/sc:code/@displayName='营养摄取' and sc:observation/sc:value='丰富'">
												<td class="docdataitemlinered">√丰富</td>														
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='营养摄取' and sc:observation/sc:value!='丰富'">
												<td class="docdataitemline">丰富</td>
										</xsl:if>
										</tr>
												<tr>
										<xsl:if test="sc:observation/sc:code/@displayName='摩擦剪切力' and sc:observation/sc:value='存在问题'">
												<td class="doclabelline">摩擦剪切力</td><td class="docdataitemlinered">√存在问题</td>											
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='摩擦剪切力' and sc:observation/sc:value!='存在问题'">
												<td class="doclabelline">摩擦剪切力</td><td class="docdataitemline">存在问题</td>
										</xsl:if>
											
										<xsl:if test="sc:observation/sc:code/@displayName='摩擦剪切力' and sc:observation/sc:value='潜在问题'">
												<td class="docdataitemlinered">√潜在问题</td>															
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='摩擦剪切力' and sc:observation/sc:value!='潜在问题'">
												<td class="docdataitemline">潜在问题</td>
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='摩擦剪切力' and sc:observation/sc:value='无问题'">
												<td class="docdataitemlinered">√无问题</td><td class="docdataitemline"></td>														
										</xsl:if>
										<xsl:if test="sc:observation/sc:code/@displayName='摩擦剪切力' and sc:observation/sc:value!='无问题'">
												<td class="docdataitemline">无问题</td><td class="docdataitemline"></td>
										</xsl:if>
										
									
										</tr>
							</xsl:for-each>	
							
						
	
					</table>									
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>