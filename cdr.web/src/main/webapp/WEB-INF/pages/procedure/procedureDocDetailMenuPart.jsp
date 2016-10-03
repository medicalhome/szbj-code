<%@ page language="java" pageEncoding="UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="translation.tld" prefix="ref"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="html2.tld" prefix="html2"%>
<%@ taglib uri="html.tld" prefix="html"%>
<jsp:useBean id="Constants" class="com.yly.cdr.core.Constants" />
<jsp:useBean id="TimerAndInpatientDto" class="com.yly.cdr.dto.TimerAndInpatientDto"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<script type="text/javascript" src="../scripts/order/nonDrugOrderMenuPart.js"></script>
<script type="text/javascript" src="../scripts/procedure/procedureOrderMenuPart.js"></script>
<script>
function getPageNo()
{
	return '${pagingContext.pageNo}';
}

function getTotalPageCnt()
{
	return '${pagingContext.totalPageCnt}';
}

$(function(){
	setLeftHeight(getPageNo(),getTotalPageCnt());
});

</script>
</head>
<body style="margin: 0; padding: 0;">
<!-- $Author :chang_xuewen
     $Date : 2013/10/24 11:00
     $[BUG]0038443 MODIFY BEGIN -->
  <div id= "dd_detailContent" >         
             
             <div id = "upbody" style= "overflow-y: ${overflow} ;" >
                   <div id = "uplist">
                        
                   </div >
             </div >
             
             <div id = "downbody" style= "overflow-y: ${overflow} ;" >
                   <div id = "downlist">
                        
                   </div >
             </div >
   </div >
   <div id = "tabs-animate" class= "paneSeperator_open" >&nbsp;&nbsp; </div >
   <!-- $[BUG]0038443 MODIFY END -->
   
</body>
</html>