<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<style type="text/css">
.subject{text-align:center;}
</style>
<script language="javascript" type="">
function toAdd(idValue){
    var url = "${ctx}/consignaction.do?method=loadAddForm&type=edit&id=" + idValue;
    self.location.replace(url);
}
function toLook(idValue){
    var url = "${ctx}/consignaction.do?method=loadLookForm&id=" + idValue;
    self.location.replace(url);
}
</script>
<template:titile value="��ѯʩ��ί����Ϣ"/>
<body>

<display:table name="sessionScope.queryresult" requestURI="${ctx}/consignaction.do?method=listConsign" pagesize="18" id="currentRowObject">
<%
  BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  String step = null;
  String id =null,changeName="",changeSimpleName="";
  String budget = "0";
  if (object != null) {
     step = object.get("step").toString();
     id = (String) object.get("id");
     changeName=(String)object.get("changename");
     changeSimpleName=changeName;
     if(changeName!=null&&changeName.length()>10){
     	changeSimpleName=changeName.substring(0,10)+"...";
     }
  }
%>
  <display:column media="html" sortable="true" style="align:center;width:160px" title="��������" >
  	<a href="javascript:toLook('<%=id%>')" title="<%=changeName %>"><%=changeSimpleName %></a>
  </display:column>
  <display:column property="changepro" title="��������" style="align:center" maxLength="4"/>
  <display:column property="changeaddr" title="���̵�ַ" style="align:center" maxLength="4"/>
  <display:column property="lineclass" title="��������" style="align:center" maxLength="4"/>
  <display:column property="entrusttime"  title="��д����" style="align:center" maxLength="10"/>
  <display:column media="html" title="�� ��"  style="align:center;valign:center" headerClass="subject">
  <apptag:checkpower thirdmould="50304" ishead="0">
  <%if (step.equals("D")){   %>
    <a href="javascript:toAdd('<%=id%>')">�޸�</a>
  <%}
  %>
  </apptag:checkpower>
  </display:column>
</display:table>

<html:link action="/consignaction.do?method=exportConsignResult">����ΪExcel�ļ�</html:link>
</body>
