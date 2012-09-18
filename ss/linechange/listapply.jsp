<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<style type="text/css">
.subject{text-align:center;}
</style>
<script language="javascript" type="">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/changeapplyaction.do?method=delApplyInfo&changeid=" + idValue;
        self.location.replace(url);
    }
}
function toEdit(idValue){
    var url = "${ctx}/changeapplyaction.do?method=getApplyInfo&type=edit&changeid=" + idValue;
    self.location.replace(url);
}
function toLook(idValue){
    var url = "${ctx}/changeapplyaction.do?method=getApplyInfo&type=look&changeid=" + idValue;
    self.location.replace(url);
}
function warn(){
  alert("该信息已通过审定,不能修改或删除！");
}

</script>
<template:titile value="查询修缮申请信息"/>
<body>

<display:table name="sessionScope.queryresult" requestURI="${ctx}/changeapplyaction.do?method=getApplyInfoList" pagesize="18" id="currentRowObject">
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
  <display:column media="html" sortable="true" style="align:center" title="工程名称" style="width:160px">
  	<a href="javascript:toLook('<%=id%>')" title="<%=changeName %>"><%=changeSimpleName %></a>
  </display:column>
  <display:column property="changepro" title="工程性质" style="align:center" maxLength="4"/>
  <display:column property="changeaddr" title="工程地址" style="align:center"" maxLength="4"/>
  <display:column property="lineclass" title="网络性质" style="align:center" maxLength="4"/>
  <display:column property="applytime"  title="申请日期"style="align:center" maxLength="10"/>
  <display:column media="html" title="操 作" style="align:center" headerClass="subject">
  <apptag:checkpower thirdmould="50104" ishead="0">
  <%if (step.equals("A")){   %>
    <a href="javascript:toEdit('<%=id%>')">修改</a>
  <%}%>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="50105" ishead="0">
  <%if (step.equals("A")){%>|
  <a href="javascript:toDelete('<%=id%>')">删除</a>
  <%}%>
  </apptag:checkpower>
  </display:column>

</display:table>

<html:link action="/changeapplyaction.do?method=exportApplyResult">导出为Excel文件</html:link>
</body>
