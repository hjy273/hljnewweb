<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toAdd(idValue){
    var url = "${ctx}/pageonholeaction.do?method=loadAddForm&id=" + idValue;
    self.location.replace(url);
}
</script>
<template:titile value="填写归档信息"/>
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
  <display:column media="html" sortable="true" style="align:center;width:100px" title="工程名称"  maxLength="10">
  <apptag:checkpower thirdmould="50701" ishead="0">
  <%if (step.equals("F")){   %>
    <a href="javascript:toAdd('<%=id%>')"><%=changeName %></a>
  <%}else
    out.print(changeName);
  %>
  </apptag:checkpower>
  </display:column>
  <display:column property="changepro" title="工程性质" style="align:center" maxLength="4"/>
  <display:column property="changeaddr" title="工程地址" style="align:center"  maxLength="4"/>
  <display:column property="lineclass" title="网络性质" style="align:center"  maxLength="4"/>
  <display:column property="approveresult" title="审定结果" style="align:center"  maxLength="4"/>
  <display:column property="state"  title="工程状态" style="align:center" maxLength="4"/>
  <display:column property="applytime"  title="申请日期" style="align:center"  maxLength="10"/>
</display:table>
</body>

