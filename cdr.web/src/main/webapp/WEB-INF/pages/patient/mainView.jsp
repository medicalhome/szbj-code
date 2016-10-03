<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Pragma" content="no-cache" />
    <!-- Prevents caching at the Proxy Server -->
    <meta http-equiv="Expires" content="0" />
    <title>患者列表</title>
	<link rel="Stylesheet" type="text/css" href="../styles/patient.css" />
    <script type="text/javascript" src="../scripts/patient/patientList.js"></script>
    <script type="text/javascript" src="../scripts/tabEnter/tabEnter.js"></script>
</head>
<body>
	<div id="alertMessage"></div>
	<div id="pl_tabs">
      <div class="divTab">
		<div onclick="showOrHidden('#pl_tabs1')" name="#pl_tabs1" onmouseover="this.style.color='#2d59b2'" onmouseout="this.style.color='black'" class="divTabBody mouseMove divTabChange pl_tabs1BackGround tabEnterPatient" 
			<c:choose>
				<c:when test="${loginFlag != null && fn:length(loginFlag) != 0}">
		 			style="width:90px;cursor:pointer;"
		 		</c:when>
		 		<c:otherwise>
		 			style="width:78px;cursor:pointer;"
		 		</c:otherwise>
			</c:choose> title="门(急)诊(当天)">&nbsp;</div>
		<div style="width:5px;" class="divTabBody">|</div>
		<div onclick="showOrHidden('#pl_tabs2')" name="#pl_tabs2" onmouseover="this.style.color='#2d59b2'" onmouseout="this.style.color='black'" class="divTabBody mouseMove tabEnterPatient" 
			<c:choose>
				<c:when test="${loginFlag != null && fn:length(loginFlag) != 0}">
		 			style="width:90px;cursor:pointer;"
		 		</c:when>
		 		<c:otherwise>
		 			style="width:78px;cursor:pointer;"
		 		</c:otherwise>
			</c:choose> title="住 院（在院）">住 院（在院）</div>
		<div style="width:5px;" class="divTabBody">|</div>
		<div onclick="showOrHidden('#pl_tabs3')" name="#pl_tabs3" onmouseover="this.style.color='#2d59b2'" onmouseout="this.style.color='black'" class="divTabBody mouseMove tabEnterPatient" 
			<c:choose>
				<c:when test="${loginFlag != null && fn:length(loginFlag) != 0}">
		 			style="width:120px;cursor:pointer;"
		 		</c:when>
		 		<c:otherwise>
		 			style="width:110px;cursor:pointer;"
		 		</c:otherwise>
			</c:choose> title="最 近（7天内）">最 近（7天内）</div>
		<div style="width:5px;" class="divTabBody">|</div>
		<div onclick="showOrHidden('#pl_tabs4','${loginFlag}')" name="#pl_tabs4" onmouseover="this.style.color='#2d59b2'" onmouseout="this.style.color='black'" class="divTabBody mouseMove tabEnterPatient" 
			<c:choose>
				<c:when test="${loginFlag != null && fn:length(loginFlag) != 0}">
		 			style="width:75px;cursor:pointer;"
		 		</c:when>
		 		<c:otherwise>
		 			style="width:68px;cursor:pointer;"
		 		</c:otherwise>
			</c:choose>>检 索</div>
		<div style="width:5px;" class="divTabBody">|</div>
		<div onclick="showOrHidden('#pl_tabs5','${loginFlag}')" name="#pl_tabs5" onmouseover="this.style.color='#2d59b2'" onmouseout="this.style.color='black'" class="divTabBody mouseMove tabEnterPatient" 
			<c:choose>
				<c:when test="${loginFlag != null && fn:length(loginFlag) != 0}">
		 			style="width:90px;cursor:pointer;"
		 		</c:when>
		 		<c:otherwise>
		 			style="width:78px;cursor:pointer;"
		 		</c:otherwise>
			</c:choose> title="我的患者">我的患者</div>
		<div class="divTabLink tabEnterPatient" id="showhidepatient" <c:if test="${loginFlag != null && fn:length(loginFlag) != 0 }">style="display:none"</c:if> onclick="$('body',parent.document).find('#patientList').hide()">
			<!--<a href="javascript:void(0)" class="normalLink" onclick="$('body',parent.document).find('#patientList').hide()" >&nbsp;</a>-->			
		</div>
		<c:if test="${Constants.COMM_INTERFACE == Constants.MIDECAL_VISIT_INTEGRATION}">
			<div style="width:5px;" class="divTabBody">|</div>
				<div onclick="showOrHidden('#pl_tabs6','${loginFlag}')" id="#pl_tabs6" name="#pl_tabs6" onmouseover="this.style.color='#2d59b2'"    onmouseout="this.style.color='black'" class="divTabBody mouseMove tabEnterPatient"
				 	<c:choose>
						<c:when test="${loginFlag != null && fn:length(loginFlag) != 0}">
				 			style="width:110px;cursor:pointer;"
				 		</c:when>
				 		<c:otherwise>
				 			style="width:110px;cursor:pointer;"
				 		</c:otherwise>
					</c:choose> title="我的预约患者">我的预约患者
				</div> 
	  </c:if>
	  </div>
	  <div id="listView">
	    <div id='patientLoadingScreen' class='loadingScreen'><div class='loadingMessage'>数据加载中，请稍候...</div></div>
	  	<div id="patientListView_outpatient" name="patientListView"></div>
	  	<div id="patientListView_inpatient" name="patientListView" style="display:none;"></div>
	  	<div id="patientListView_recent" name="patientListView" style="display:none;"></div>
	  	<div id="patientListView_all" name="patientListView" style="display:none;"></div>
	  	<div id="patientListView_fav" name="patientListView" style="display:none;"></div>
	  	<div id="patientListView_appoint" name="patientListView" style="display:none;"></div>
	  	<div id="confirmMessage" style="display:none;"></div>
	  </div>

	  <div style="height:5px;"></div>
    </div>
    <div id="dialog" title="Basic dialog">
    
	</div>
    <script type="text/javascript">
        $(document).ready(function() {
        	// $Author :jin_peng
            // $Date : 2012/11/09 17:51$
            // [BUG]0010795 MODIFY BEGIN
            // 集成登陆时根据门诊和住院判断显示tab页
        	var patientDomain = '${patientDomain}';
        	var patientDomainConstant = '${Constants.PATIENT_DOMAIN_INPATIENT}';
            
        	// 当域id为住院时页面tab页跳至住院在院页面,否则维持原状
            if(patientDomain == patientDomainConstant)
           	{
            	showOrHidden('#pl_tabs2');
           	}
            else
           	{
            	loadPatientListView("../patient/list_outpatient.html","","#patientListView_outpatient");
           	}
                        
            return false;
            
         	// [BUG]0010795 MODIFY END
        });
    </script>
</body>
</html>
