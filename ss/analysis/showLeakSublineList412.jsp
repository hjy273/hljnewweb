<%@ include file="/common/header.jsp"%>
<%@page import="org.apache.commons.beanutils.BasicDynaBean"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<script language="javascript" type="">
function toGetForm(idValue,nameValue){
   var url = "${ctx}/LeakSublineAction.do?method=showLeakSublineDetail412&id=" + idValue + "&conname=" + nameValue;
   self.location.replace(url);
}

</script><br>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'><%=session.getAttribute("regionName412") %>����ά��˾����©��Ѳ������֮�߶���ʾ���</div><hr width='100%' size='1'>

<display:table name="sessionScope.leakSublineList412" requestURI="${ctx}/LeakSublineAction.do?method=showLeakSubline412"  id="currentRowObject"  pagesize="18">
     <display:column property="contractorname" title="��ά��˾" sortable="true"/>
     <display:column property="tnumber" title="ӦѲ���߶���" sortable="true"/>
     <display:column property="pnumber" title="�����ƻ��߶���" sortable="true"/>
     <display:column property="leaknumber" title="©���ƻ��߶���" sortable="true"/>
  <display:column media="html" title="����">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("contractorid");
    String conname = (String) object.get("contractorname");
    //String executorname = (String) object.get("patrolname");
  %>
    <a href="javascript:toGetForm('<%=id%>','<%=conname%>')">�鿴</a>
  </display:column>
</display:table>


