<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/patrolSonAction.do?method=deletePatrolSon&id=" + idValue;
        self.location.replace(url);
    }

}

function toEdit(idValue){
        var url = "${ctx}/patrolSonAction.do?method=loadPatrolSon&id=" + idValue;
        self.location.replace(url);

}


</script>
<template:titile value="查询巡检员信息结果"/>
<display:table name="sessionScope.queryresult" id="currentRowObject"  pagesize="18">

  <display:column property="patrolname" title="姓  名"/>
  <display:column property="sex" title="性  别"/>
  <display:column property="phone" title="固定电话"/>
  <display:column property="mobile" title="移动电话"/>
  <display:column property="parentid" title="代维单位"/>
  <logic:equal value="group" name="PMType">
  	<display:column property="groupid" title="巡检维护组"/>
  </logic:equal>

  <display:column property="jobstate" title="工作状态"/>

  <apptag:checkpower thirdmould="70704" ishead="0">

  <display:column media="html" title="修改">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String patrolid = "";
    if(object != null)
     patrolid = (String) object.get("patrolid");
  %>
    <a href="javascript:toEdit('<%=patrolid%>')">修改</a>
  </display:column>
  </apptag:checkpower>
<apptag:checkpower thirdmould="70705" ishead="0">

  <display:column media="html" title="删除">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String patrolid = (String) object.get("patrolid");
  %>
    <a href="javascript:toDelete('<%=patrolid%>')">删除</a>
  </display:column>
  </apptag:checkpower>


</display:table>
<html:link action="/patrolSonAction.do?method=exportPatrolSonResult">导出为Excel文件</html:link>
