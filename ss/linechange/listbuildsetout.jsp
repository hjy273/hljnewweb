<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<style type="text/css">
.subject{text-align:center;}
</style>
<script language="javascript" type="">
function toAdd(idValue,budget){
    var url = "${ctx}/buildsetoutaction.do?method=loadAddForm&type=edit&id=" + idValue+"&budget="+budget;
    self.location.replace(url);
}
function toLook(idValue,budget){
    var url = "${ctx}/buildsetoutaction.do?method=loadLookForm&id=" + idValue+"&budget="+budget;
    self.location.replace(url);
}
</script>
<template:titile value="查询施工准备信息"/>
<body>

<display:table name="sessionScope.queryresult" requestURI="${ctx}/buildsetoutaction.do?method=listBuildSetout" pagesize="18" id="currentRowObject">
<%
  BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  String step = null;
  String id =null,changeName="",changeSimpleName="";
  String budget = "0";
  if (object != null) {
     step = object.get("step").toString();
     budget = object.get("budget").toString();
     id = (String) object.get("id");
     changeName=(String)object.get("changename");
     changeSimpleName=changeName;
     if(changeName!=null&&changeName.length()>10){
     	changeSimpleName=changeName.substring(0,10)+"...";
     }
  }
%>
  <display:column media="html" sortable="true" style="align:center" title="工程名称" style="width:160px">
  	<a href="javascript:toLook('<%=id%>','<%=budget%>')" title="<%=changeName %>"><%=changeSimpleName %></a>
  </display:column>
  <display:column property="changepro" title="工程性质" style="align:center" maxLength="4"/>
  <display:column property="changeaddr" title="工程地址" style="align:center" maxLength="4"/>
  <display:column property="lineclass" title="网络性质" style="align:center" maxLength="4"/>
  <display:column property="budget" title="工程预算（万元）" style="align:center"/>
  <display:column media="html" title="操 作" style="valign:center" style="align:center" headerClass="subject">
  <apptag:checkpower thirdmould="50304" ishead="0">
  <%if (step.equals("C")){   %>
    <a href="javascript:toAdd('<%=id%>','<%=budget%>')">修改</a>
  <%}
  %>
  </apptag:checkpower>
  </display:column>

</display:table>
<html:link action="/buildsetoutaction.do?method=exportBuildSetoutResult">导出为Excel文件</html:link>

</body>


