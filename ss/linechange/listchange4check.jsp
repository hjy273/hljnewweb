<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toAdd(idValue){
    var url = "${ctx}/checkaction.do?method=loadAddForm&changeid=" + idValue;
    self.location.replace(url);
}
</script>
<template:titile value="��д������Ϣ"/>
<body>

<display:table name="sessionScope.queryresult" requestURI="${ctx}/checkaction.do?method=getChangeInfo" pagesize="18" id="currentRowObject">
<%
  BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  String step = null;
  String id =null,changeName="",changeSimpleName="";

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
  <display:column media="html" sortable="true" style="align:center" title="��������" style="width:160px">
  <apptag:checkpower thirdmould="50601" ishead="0">
  <%if (step.equals("E")){   %>
    <a href="javascript:toAdd('<%=id%>')" title="<%=changeName %>"><%=changeSimpleName %></a>
  <%}else{%>
    <span title="<%=changeName %>"><%=changeSimpleName %></span>
  <%}%>
  </apptag:checkpower>
  </display:column>
  <display:column property="changepro" title="��������" style="align:center" maxLength="4"/>
  <display:column property="changeaddr" title="���̵�ַ" style="align:center" maxLength="4"/>
  <display:column property="lineclass" title="��������" style="align:center" maxLength="4"/>
  <display:column property="applytime"  title="��������" style="align:center" maxLength="10"/>
</display:table>
</body>

