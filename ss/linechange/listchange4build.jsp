<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toAdd(idValue){
    var url = "${ctx}/buildaction.do?method=loadAddForm&changeid=" + idValue;
    self.location.replace(url);
}
</script>
<template:titile value="填写施工信息"/>
<body>

<display:table name="sessionScope.queryresult" requestURI="${ctx}/buildaction.do?method=getChangeInfo" pagesize="18" id="currentRowObject">
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
  <display:column media="html"  title="工程名称" >
  <apptag:checkpower thirdmould="50501" ishead="0">
  <%if (step.equals("D")){   %>
    <a href="javascript:toAdd('<%=id%>')" title="<%=changeName %>"><%=changeSimpleName %></a>
  <%}else
    out.print(changeName);
  %>
  </apptag:checkpower>
  </display:column>
  <display:column property="changepro" title="工程性质"style="align:center" maxLength="4"/>
  <display:column property="changeaddr" title="工程地址" style="align:center" maxLength="4"/>
  <display:column property="lineclass" title="网络性质" style="align:center" maxLength="4"/>
  <display:column property="applytime"  title="申请日期" style="align:center" maxLength="10"/>
</display:table>
</body>
