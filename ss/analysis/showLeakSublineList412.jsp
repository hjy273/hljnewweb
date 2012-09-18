<%@ include file="/common/header.jsp"%>
<%@page import="org.apache.commons.beanutils.BasicDynaBean"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<script language="javascript" type="">
function toGetForm(idValue,nameValue){
   var url = "${ctx}/LeakSublineAction.do?method=showLeakSublineDetail412&id=" + idValue + "&conname=" + nameValue;
   self.location.replace(url);
}

</script><br>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'><%=session.getAttribute("regionName412") %>各代维公司本月漏做巡检任务之线段显示结果</div><hr width='100%' size='1'>

<display:table name="sessionScope.leakSublineList412" requestURI="${ctx}/LeakSublineAction.do?method=showLeakSubline412"  id="currentRowObject"  pagesize="18">
     <display:column property="contractorname" title="代维公司" sortable="true"/>
     <display:column property="tnumber" title="应巡检线段数" sortable="true"/>
     <display:column property="pnumber" title="已作计划线段数" sortable="true"/>
     <display:column property="leaknumber" title="漏作计划线段数" sortable="true"/>
  <display:column media="html" title="操作">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("contractorid");
    String conname = (String) object.get("contractorname");
    //String executorname = (String) object.get("patrolname");
  %>
    <a href="javascript:toGetForm('<%=id%>','<%=conname%>')">查看</a>
  </display:column>
</display:table>


