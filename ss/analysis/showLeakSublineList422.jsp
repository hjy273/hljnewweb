<%@ include file="/common/header.jsp"%>
<%@page import="org.apache.commons.beanutils.BasicDynaBean"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<script language="javascript" type="">
function toGetForm(idValue,nameValue){
   var url = "${ctx}/LeakSublineAction.do?method=showLeakSublineDetail422&id=" + idValue + "&patrolname=" + nameValue; 
   self.location.replace(url);
}

</script><br>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'><%=session.getAttribute("conName422")%>各巡检组本月漏做巡检任务之线段显示结果</div><hr width='100%' size='1'>

<display:table name="sessionScope.leakSublineList422"   id="currentRowObject"  pagesize="18">
     <display:column property="patrolname" title="巡检组" sortable="true"/>
     <display:column property="tnumber" title="应巡检线段数" sortable="true"/>
     <display:column property="pnumber" title="已作计划线段数" sortable="true"/>
     <display:column property="leaknumber" title="漏作计划线段数" sortable="true"/>
  <display:column media="html" title="操作">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("patrolid");
    //String contractorname = (String) object.get("contractorname");
    String patrolname = (String) object.get("patrolname");
  %>
    <a href="javascript:toGetForm('<%=id%>','<%=patrolname%>')">查看</a>
  </display:column>
</display:table>


