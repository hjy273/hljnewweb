<%@include file="/common/header.jsp"%>
<%@page import="org.apache.commons.beanutils.BasicDynaBean"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toGetForm(idValue,nameValue){
   var url = "${ctx}/PointConfirmAction.do?method=showPointInfo&id=" + idValue + "&sublinename=" + nameValue;
   self.location.replace(url);
}

</script><br>

<template:titile value="Ѳ���߶��б�"/>
  <input  id="conid"  name="conname" type="hidden"/>
<display:table name="sessionScope.sublineForPointConfirm"  id="currentRowObject"  pagesize="18">
  <display:column property="sublinename" title="Ѳ���߶�����"/>
  <display:column media="html" title="����">
  <%
  		BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  		String sublineid = object.get("sublineid").toString();
  		String sublinename = object.get("sublinename").toString();
  %>
  <a href="javascript:toGetForm('<%=sublineid%>','<%=sublinename %>')">�鿴Ѳ���</a>
  </display:column>
</display:table>