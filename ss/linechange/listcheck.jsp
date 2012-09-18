<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<style type="text/css">
.subject{text-align:center;}
</style>
<script language="javascript" type="">

function toLook(idValue){
    var url = "${ctx}/checkaction.do?method=loadLookForm&id=" + idValue;
    self.location.replace(url);
}

</script>
<template:titile value="查询验收信息"/>
<body>

<display:table name="sessionScope.queryresult" requestURI="${ctx}/checkaction.do?method=getCheckInfo" pagesize="18" id="currentRowObject">
<%
  BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  String id =null,changeName="",changeSimpleName="";

  if (object != null) {
     id = (String) object.get("id");
     changeName=(String)object.get("changename");
     changeSimpleName=changeName;
     if(changeName!=null&&changeName.length()>10){
     	changeSimpleName=changeName.substring(0,10)+"...";
     }
  }
%>
  <display:column media="html" sortable="true" style="align:center" title="工程名称" style="width:160px">
  	<a href="javascript:toLook('<%=id%>')" title="<%=changeName %>"><%=changeSimpleName %></a>
  </display:column>
  <display:column property="checkdate" title="验收日期" style="align:center" maxLength="10"/>
  <display:column property="checkperson" title="验收负责人" style="align:center" maxLength="5"/>
  <display:column property="checkresult" title="验收结果" style="align:center" maxLength="4"/>
  <display:column property="fillintime"  title="填写日期" style="align:center" maxLength="10"/>
</display:table>

<html:link action="/checkaction.do?method=exportCheckResult">导出为Excel文件</html:link>
</body>
