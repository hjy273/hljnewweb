<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<style type="text/css">
.subject{text-align:center;}
</style>
<script language="javascript" type="">

function toLook(idValue){
    var url = "${ctx}/buildaction.do?method=loadLookForm&id=" + idValue;
    self.location.replace(url);
}
function toEdit(idValue){
    var url = "${ctx}/buildaction.do?method=loadEditForm&id=" + idValue;
    self.location.replace(url);
}

</script>
<template:titile value="查询施工信息"/>
<body>

<display:table name="sessionScope.queryresult" requestURI="${ctx}/buildaction.do?method=getBuildInfo" pagesize="18" id="currentRowObject">
<%
  BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  String id =null,changeName="",changeSimpleName="";
  String step = null;

  if (object != null) {
     id = (String) object.get("id");
     step = (String) object.get("step");
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
  <display:column property="starttime" title="施工开始日期" style="align:center" maxLength="10"/>
  <display:column property="endtime" title="施工结束日期" style="align:center" maxLength="10"/>
  <display:column property="changepro" title="工程性质" style="align:center" maxLength="5"/>
  <display:column property="buildunit" title="施工单位名称" style="align:center" maxLength="5"/>
  <display:column property="fillindate"  title="填写日期" style="align:center" maxLength="10"/>
  <display:column media="html" title="操 作" style="align:center" headerClass="subject">
  <apptag:checkpower thirdmould="50504" ishead="0">
  <%if (step.equals("E")){   %>
    <a href="javascript:toEdit('<%=id%>')">修改</a>
  <%}
  %>
  </apptag:checkpower>
  </display:column>
</display:table>
<html:link action="/buildaction.do?method=exportBuildResult">导出为Excel文件</html:link>
</body>
