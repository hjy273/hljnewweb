<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toAdd(idValue){
    var url = "${ctx}/pageonholeaction.do?method=loadAddForm&id=" + idValue;
    self.location.replace(url);
}
</script>
<template:titile value="��д�鵵��Ϣ"/>
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
  <display:column media="html" sortable="true" style="align:center;width:100px" title="��������"  maxLength="10">
  <apptag:checkpower thirdmould="50701" ishead="0">
  <%if (step.equals("F")){   %>
    <a href="javascript:toAdd('<%=id%>')"><%=changeName %></a>
  <%}else
    out.print(changeName);
  %>
  </apptag:checkpower>
  </display:column>
  <display:column property="changepro" title="��������" style="align:center" maxLength="4"/>
  <display:column property="changeaddr" title="���̵�ַ" style="align:center"  maxLength="4"/>
  <display:column property="lineclass" title="��������" style="align:center"  maxLength="4"/>
  <display:column property="approveresult" title="�󶨽��" style="align:center"  maxLength="4"/>
  <display:column property="state"  title="����״̬" style="align:center" maxLength="4"/>
  <display:column property="applytime"  title="��������" style="align:center"  maxLength="10"/>
</display:table>
</body>

