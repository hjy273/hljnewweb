<%@ include file="/common/header.jsp"%>
<%@page import="org.apache.commons.beanutils.BasicDynaBean"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<script language="javascript" type="">
function toGetForm(idValue,nameValue){
   var url = "${ctx}/LeakSublineAction.do?method=showLeakSublineDetail422&id=" + idValue + "&patrolname=" + nameValue; 
   self.location.replace(url);
}

</script><br>
<div class='title2' style='font-size:14px; font-weight:600;bottom:1' align='center'><%=session.getAttribute("conName422")%>��Ѳ���鱾��©��Ѳ������֮�߶���ʾ���</div><hr width='100%' size='1'>

<display:table name="sessionScope.leakSublineList422"   id="currentRowObject"  pagesize="18">
     <display:column property="patrolname" title="Ѳ����" sortable="true"/>
     <display:column property="tnumber" title="ӦѲ���߶���" sortable="true"/>
     <display:column property="pnumber" title="�����ƻ��߶���" sortable="true"/>
     <display:column property="leaknumber" title="©���ƻ��߶���" sortable="true"/>
  <display:column media="html" title="����">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("patrolid");
    //String contractorname = (String) object.get("contractorname");
    String patrolname = (String) object.get("patrolname");
  %>
    <a href="javascript:toGetForm('<%=id%>','<%=patrolname%>')">�鿴</a>
  </display:column>
</display:table>


