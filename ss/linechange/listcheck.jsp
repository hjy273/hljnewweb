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
<template:titile value="��ѯ������Ϣ"/>
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
  <display:column media="html" sortable="true" style="align:center" title="��������" style="width:160px">
  	<a href="javascript:toLook('<%=id%>')" title="<%=changeName %>"><%=changeSimpleName %></a>
  </display:column>
  <display:column property="checkdate" title="��������" style="align:center" maxLength="10"/>
  <display:column property="checkperson" title="���ո�����" style="align:center" maxLength="5"/>
  <display:column property="checkresult" title="���ս��" style="align:center" maxLength="4"/>
  <display:column property="fillintime"  title="��д����" style="align:center" maxLength="10"/>
</display:table>

<html:link action="/checkaction.do?method=exportCheckResult">����ΪExcel�ļ�</html:link>
</body>
