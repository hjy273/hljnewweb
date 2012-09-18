<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toAdd(idValue){
    var url = "${ctx}/consignaction.do?method=loadAddForm&type=add&id=" + idValue;
    self.location.replace(url);
}
</script>
<template:titile value="填写施工委托信息"/>
<body>

<display:table name="sessionScope.queryresult" requestURI="${ctx}/consignaction.do?method=listChangeInfo" pagesize="18" id="currentRowObject">
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
  <display:column media="html" sortable="true" style="align:center" title="工程名称" style="width:160px">
  <apptag:checkpower thirdmould="50401" ishead="0">
    <a href="javascript:toAdd('<%=id%>')" title="<%=changeName %>"><%=changeSimpleName %></a>
  </apptag:checkpower>
  </display:column>
  <display:column property="changepro" title="工程性质" style="align:center" maxLength="4"/>
  <display:column property="changeaddr" title="工程地址" style="align:center" maxLength="4"/>
  <display:column property="lineclass" title="网络性质"style="align:center" maxLength="4"/>
  <display:column property="applytime"  title="申请日期" style="align:center" maxLength="10"/>
</display:table>
</body>
