<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="UTF-8" errorPage="/error.jsp"
	trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>患者详细信息</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<link type="text/css" rel="Stylesheet"
		href="../styles/jquery-ui-1.8.18.custom.modify.css" charset="UTF8" />
	<link type="text/css" rel="Stylesheet"
		href="../styles/layout-default-1.3.0rc29.15.css" charset="UTF8" />
	<link type="text/css" rel="Stylesheet"
		href="../styles/layout-cdr-dialog.css" charset="UTF8" />
	<link type="text/css" rel="Stylesheet" href="../styles/tablelist.css"
		charset="UTF8" />
	<script type="text/javascript">
		$(function() {
			$("#tabs").tabs();
		});
	</script>
</head>
<body>
	<div id="dialog">
		<div id="mainContent">
			<div id="tabs">
				<div id="tabs-1" class="tabcontainer">
					<table width="100%" cellpadding="2" cellspacing="1"
						style="border: 0px;border-collapse:collapse;">
						<tr>
							<td style="border: solid 1px #dceef8;"><table width="150px"
									height="100%">
									<tr>
										<td width="150px;" align="center"><img
											src="../images/pic_woman.jpg" align="absmiddle" /></td>
									</tr>
								</table></td>
							<td width="100%"><table width="100%" cellpadding="2" cellspacing="1"
					style="border: solid 1px #c0ddea;border-collapse:collapse;border-bottom:0px;">
									<tr style="height: 1px; text-align: center; border: 0px;">
										<td style="width: 15%"></td>
										<td style="width: 25%"></td>
										<td style="width: 15%"></td>
										<td style="width: 25%"></td>
									</tr>
									<tr class="odd">
										<td class="label">姓名:</td>
										<td class="dataitem">${patient.patientName}</td>
										<td class="label">性别:</td>
										<td class="dataitem">${patient.genderName}</td>
									</tr>
									<tr>
										<td class="label">出生日期:</td>
										<td class="dataitem"><fmt:formatDate type="date"
												value="${patient.birthDate}" pattern="yyyy-MM-dd" /></td>
										<td class="label">婚姻状况:</td>
										<td class="dataitem">${patient.marriageStatusName}</td>
									</tr>
									<tr class="odd">
										<td class="label">民族:</td>
										<td class="dataitem">${patient.nationName}</td>
										<td class="label">出生地:</td>
										<c:choose>
											<c:when test="${accessContorl == false||(accessContorl && !loginUserAclAuths.patientInfoAuth04)}">												
												<td class="dataitem">${patient.birthPlace}</td>
											</c:when>
											<c:otherwise>
												<td class="dataitem" style="color: red">
													${Constants.ACL_NOACCESS_MESSAGE}</td>
											</c:otherwise>
										</c:choose>
									</tr>
									<tr>
										<td class="label">国籍:</td>
										<td class="dataitem">${patient.nationalityName}</td>
										<td class="label">职业:</td>
										<td class="dataitem">${patient.occupationName}</td>
									</tr>
									<tr class="odd">
										<td class="label">文化程度:</td>
										<td class="dataitem">${patient.educationDegreeName}</td>
										<td class="label">工作单位名称:</td>
										<td class="dataitem">${patient.workPlace}</td>
									</tr>
									<tr id="telephoneMobile">
										<td class="label">家庭电话:</td>
										<c:choose>
											<c:when test="${accessContorl == false|| (accessContorl && !loginUserAclAuths.patientInfoAuth05)}">
												
												<td class="dataitem">${patient.telephone}</td>
											</c:when>
											<c:otherwise>
												<td class="dataitem" style="color: red">
													${Constants.ACL_NOACCESS_MESSAGE}</td>
											</c:otherwise>
										</c:choose>
										<td class="label">移动电话:</td>
										<c:choose>
											<c:when
												test="${accessContorl == false|| (accessContorl && !loginUserAclAuths.patientInfoAuth05)}">
												<td class="dataitem">${patient.mobile}</td>
											</c:when>
											<c:otherwise>
												<td class="dataitem" style="color: red">
													${Constants.ACL_NOACCESS_MESSAGE}</td>
											</c:otherwise>
										</c:choose>
									</tr>
									<tr class="odd">
										<td class="label">电子邮件地址:</td>
										<c:choose>
											<c:when
												test="${accessContorl == false|| (accessContorl && !loginUserAclAuths.patientInfoAuth06)}">
												<td class="dataitem">${patient.email}</td>
											</c:when>
											<c:otherwise>
												<td class="dataitem" style="color: red">
													${Constants.ACL_NOACCESS_MESSAGE}</td>
											</c:otherwise>
										</c:choose>
										<td class="label">ABO血型:</td>
										<td class="dataitem">${patient.bloodAboName}</td>
									</tr>
									<tr>
										<td class="label">RH血型:</td>
										<td class="dataitem">${patient.bloodRhName}</td>
										<td class="label"></td>
										<td class="dataitem"></td>
									</tr>
									
								</table></td>
						</tr>
					</table>
					<table id="tblid" style="width: 100%;" cellspacing="1"
						cellpadding="2" class="table">
						<tr>
							<td class="blockHeader" colspan="3" height="27" align="left"
								style="border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold; padding-left: 5px;"><b><img
									src="../images/pic_gm_left.png"
									style="padding-left: 3px; padding-right: 4px;" width="16"
									height="16" alt="" align="absmiddle" />联系地址</b></td>
						</tr>
						<c:choose>
							<c:when test="${accessContorl == false|| (accessContorl && !loginUserAclAuths.patientInfoAuth04)}">
								<tr class="tabletitle">
									<td height="28" align="center" width="20%">地址类型</td>
									<td height="28" align="center" width="60%">地址</td>
									<td height="28" align="center" width="20%">邮编</td>
								</tr>
								<c:if test="${fn:length(patientAddresses)==0}">
									<tr>
										<td colspan="2" align="center" class="odd" height="24">没有相关数据！</td>
									</tr>
								</c:if>
								<c:forEach items="${patientAddresses}" var="patientAddress"
									varStatus="loopStatus">
									<tr class="${loopStatus.index % 2 == 0 ? 'odd' : 'even'}">
										<td height="24">${patientAddress.addressTypeName}</td>
										<c:if test="${fn:length(patientAddress.fullAddress)!=0}">
											<td height="24">${patientAddress.fullAddress}</td>
										</c:if>
										<c:if test="${fn:length(patientAddress.fullAddress)==0}">
											<td height="24">${patientAddress.districtName}
												${patientAddress.subDistrictName}</td>
										</c:if>
										<td height="24">${patientAddress.zipCode}</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<td class="noAccessMessage" align="center" colspan="2" >${Constants.ACL_NOACCESS_MESSAGE}患者的地址信息！</td>
							</c:otherwise>
						</c:choose>
					</table>
					<table style="border: 0px;">
						<tr>
							<td height="10px;"></td>
						</tr>
					</table>
					<table id="tblid" style="width: 100%;" cellspacing="1"
						cellpadding="2" class="table">
						<tr>
							<td class="blockHeader" colspan="8" height="27" align="left"
								style="border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold; padding-left: 5px;"><b><img
									src="../images/pic_gm_left.png"
									style="padding-left: 3px; padding-right: 4px;" width="16"
									height="16" alt="" align="absmiddle" />联系人信息</b></td>
						</tr>
						<tr class="tabletitle">
							<td height="28" align="center" width="10%">关系</td>
							<td height="28" align="center" width="10%">姓名</td>
							<td height="28" align="center" width="10%">性别</td>
							<td height="28" align="center" width="10%">电话</td>
							<td height="28" align="center" width="15%">手机</td>
							<td height="28" align="center" width="20%">住址</td>
							<td height="28" align="center" width="10%">邮编</td>
							<td height="28" align="center" width="15%">电子邮件地址</td>
						</tr>
						<c:if test="${fn:length(patientContacts)==0}">
							<tr>
								<td colspan="8" align="center" class="odd" height="24">没有相关数据！</td>
							</tr>
						</c:if>
						<c:forEach items="${patientContacts}" var="patientContact"
							varStatus="loopStatus">
							<tr class="${loopStatus.index % 2 == 0 ? 'odd' : 'even'}">
								<td height="24" width="10%">${patientContact.relationshipName}</td>
								<td height="24" width="10%">${patientContact.contactName}</td>
								<td height="24" width="10%">${patientContact.contactGenderName}</td>
								<c:choose>
									<c:when
										test="${accessContorl == false || (accessContorl && !loginUserAclAuths.patientInfoAuth05)}">
										<td height="24" width="10%">${patientContact.contactTelephone}</td>
									</c:when>
									<c:otherwise>
										<td class="noAccessMessage">${Constants.ACL_NOACCESS_MESSAGE}</td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when
										test="${accessContorl == false || (accessContorl && !loginUserAclAuths.patientInfoAuth05)}">
										<td height="24" width="15%">${patientContact.contactMobile}</td>
									</c:when>
									<c:otherwise>
										<td class="noAccessMessage">${Constants.ACL_NOACCESS_MESSAGE}</td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when
										test="${accessContorl == false ||(accessContorl && !loginUserAclAuths.patientInfoAuth04)}">
										<td height="24" width="20%">${patientContact.contactAddress}</td>
									</c:when>
									<c:otherwise>
										<td class="noAccessMessage">${Constants.ACL_NOACCESS_MESSAGE}</td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when
										test="${accessContorl == false || (accessContorl && !loginUserAclAuths.patientInfoAuth04)}">
										<td height="24" width="10%">${patientContact.postCode}</td>
									</c:when>
									<c:otherwise>
										<td class="noAccessMessage">${Constants.ACL_NOACCESS_MESSAGE}</td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when
										test="${accessContorl == false || (accessContorl && !loginUserAclAuths.patientInfoAuth06)}">
										<td height="24" width="15%">${patientContact.contactEmail}</td>
									</c:when>
									<c:otherwise>
										<td class="noAccessMessage">${Constants.ACL_NOACCESS_MESSAGE}</td>
									</c:otherwise>
								</c:choose>
							</tr>
						</c:forEach>
					</table>
					<table style="border: 0px;">
						<tr>
							<td height="10px;"></td>
						</tr>
					</table>
					<table id="tblid" style="width: 100%;" cellspacing="1"
						cellpadding="2" class="table">
						<tr>
							<td class="blockHeader" colspan="4" height="27" align="left"
								style="border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold; padding-left: 5px;"><b><img
									src="../images/pic_gm_left.png"
									style="padding-left: 3px; padding-right: 4px;" width="16"
									height="16" alt="" align="absmiddle" />证件信息</b></td>
						</tr>
						<c:choose>
							<c:when test="${accessContorl == false || (accessContorl && !loginUserAclAuths.patientInfoAuth03)}">
								<tr class="tabletitle">
									<td height="28" align="center" width="20%">证件类型</td>
									<td height="28" align="center" width="40%">证件号码</td>
									<td height="28" align="center" width="20%">生效日期</td>
									<td height="28" align="center" width="20%">失效日期</td>
								</tr>
								<c:if test="${fn:length(patientCredentials)==0}">
									<tr>
										<td colspan="4" align="center" class="odd" height="24">没有相关数据！</td>
									</tr>
								</c:if>
								<c:forEach items="${patientCredentials}" var="patientCredential"
									varStatus="loopStatus">
									<tr class="${loopStatus.index % 2 == 0 ? 'odd' : 'even'}">
										<td height="24" width="10%">${patientCredential.credentialTypeName}</td>
										<td height="24" width="10%">${patientCredential.credentialNo}</td>
										<td height="24" align="center" width="10%"><fmt:formatDate
												value="${patientCredential.effectiveDate}" type="date"
												pattern="yyyy-MM-dd HH:mm" /></td>
										<td height="24" align="center" width="10%"><fmt:formatDate
												value="${patientCredential.expirationDate}" type="date"
												pattern="yyyy-MM-dd HH:mm" /></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<td class="noAccessMessage" align="center" colspan="4">${Constants.ACL_NOACCESS_MESSAGE}患者的证件信息！</td>
							</c:otherwise>
						</c:choose>
					</table>
					<table style="border: 0px;">
						<tr>
							<td height="10px;"></td>
						</tr>
					</table>
					<table id="tblid" style="width: 100%;" cellspacing="1"
						cellpadding="2" class="table">
						<tr>
							<td class="blockHeader" colspan="3" height="27" align="left"
								style="border-top: solid 1px #B3C4D4; border-bottom: solid 1px #B3C4D4; font-weight: bold; padding-left: 5px;"><b><img
									src="../images/pic_gm_left.png"
									style="padding-left: 3px; padding-right: 4px;" width="16"
									height="16" alt="" align="absmiddle" />危险性信息</b></td>
						</tr>
						<tr class="tabletitle">
							<td height="28" align="center" width="20%">名称</td>
							<td height="28" align="center" width="40%">失效日期</td>
							<td height="28" align="center" width="20%">信息来源</td>
						</tr>
						<c:if test="${fn:length(riskInformations)==0}">
							<tr>
								<td colspan="3" align="center" class="odd" height="24">没有相关数据！</td>
							</tr>
						</c:if>
						<c:forEach items="${riskInformations}" var="riskInformation"
							varStatus="loopStatus">
							<tr class="${loopStatus.index % 2 == 0 ? 'odd' : 'even'}">
								<td height="24" width="10%">${riskInformation.riskName}</td>
								<td height="24" align="center" width="10%"><fmt:formatDate
										value="${riskInformation.expirationDate}" type="date"
										pattern="yyyy-MM-dd HH:mm" /></td>
								<td height="24" width="10%">${riskInformation.infoSource}</td>
							</tr>
						</c:forEach>
					</table>
					<table style="border: 0px;">
						<tr>
							<td height="10px;"></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
