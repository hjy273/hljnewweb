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
     <template:titile value="�漰Ѳ���߶���ϸ��Ϣ"/>
     <b><template:formTr name="����ѡ��" ></b>
      
  	   <input  type="radio"  name="comptype" value="1" checked="checked" onclick="showNonPlan()"/> ��Ѳ��ƻ���Ѳ���߶�
       <input  type="radio"  name="comptype" value="2" onclick="showFailure()"/> ���ϸ��Ѳ���߶�
       <input  type="radio"  name="comptype" value="2" onclick="showSuccess()"/> �ϸ��Ѳ���߶�

     </template:formTr>

   </center>

   <div>
       <iframe id="stattype" marginWidth="0" marginHeight="0" src="" frameBorder=0 width="100%" scrolling=auto height="80%"> </iframe>
   </div> 
</body>
