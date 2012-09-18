<%@ include file="/common/header.jsp"%>
<%@page import="org.apache.commons.beanutils.BasicDynaBean"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<br>
<script language="javascript" type="">
function showNonPlan(){
   document.getElementById("stattype").src="${ctx}/planstat/monthlyStatPmCompNoPlan.jsp" 
}
function showFailure(){
   document.getElementById("stattype").src="${ctx}/planstat/monthlyStatPmCompFailure.jsp"  
}
function showSuccess(){
   document.getElementById("stattype").src="${ctx}/planstat/monthlyStatPmCompSuccess.jsp" 
}
</script>
<body onload="showNonPlan()">
     <center>
     <template:titile value="涉及巡检线段详细信息"/>
     <b><template:formTr name="类型选择" ></b>
      
  	   <input  type="radio"  name="comptype" value="1" checked="checked" onclick="showNonPlan()"/> 无巡检计划的巡检线段
       <input  type="radio"  name="comptype" value="2" onclick="showFailure()"/> 不合格的巡检线段
       <input  type="radio"  name="comptype" value="2" onclick="showSuccess()"/> 合格的巡检线段

     </template:formTr>

   </center>

   <div>
       <iframe id="stattype" marginWidth="0" marginHeight="0" src="" frameBorder=0 width="100%" scrolling=auto height="80%"> </iframe>
   </div> 
</body>
