<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">

function toLook(idValue){
    var url = "${ctx}/pageonholeaction.do?method=loadLookForm&id=" + idValue;
    self.location.replace(url);
}
</script>
<template:titile value="��ѯ�鵵��Ϣ"/>
<body>

<display:table name="sessionScope.queryresult" pagesize="18" id="currentRowObject">
<%
  BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  String step = null;
  String id =null,changeName="";
  String budget = "0";
  if (object != null) {
     step = object.get("step").toString();
     id = (String) object.get("id");
     changeName=(String)object.get("changename");
  }
%>
  <display:column media="html" sortable="true" align="center" title="��������"  width="100" maxLength="10">
  	<a href="javascript:toLook('<%=id%>')"><%=changeName %></a>
  </display:column>
  <display:column property="budget" title="����Ԥ�㣨��Ԫ��" align="center"/>
  <display:column property="square" title="���̽��㣨��Ԫ��" align="center"/>
  <display:column property="lineclass" title="��������" align="center" maxLength="4"/>
  <display:column property="ischangedatum" title="�Ƿ����޸�" align="center" maxLength="4"/>
  <display:column property="pageonholedate"  title="��д����" align="center" maxLength="10"/>
</display:table>

<html:link action="/pageonholeaction.do?method=exportPageonholeResult">����ΪExcel�ļ�</html:link>
</body>

