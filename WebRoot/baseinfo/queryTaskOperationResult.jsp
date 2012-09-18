<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/TaskOperationAction.do?method=deleteTaskOperation&id=" + idValue;
        self.location.replace(url);
    }
}


function toEdit(idValue){
        var url = "${ctx}/TaskOperationAction.do?method=loadTaskOperation&id=" + idValue;
        self.location.replace(url);

}


</script>

<template:titile value="查询任务操作信息结果"/>
<display:table name="sessionScope.queryresult" pagesize="18" id="currentRowObject">

  <display:column property="operationname" title="任务操作名称"/>
  <display:column property="operationdes" title="备  注" maxLength="50"/>

  <apptag:checkpower thirdmould="71404" ishead="0">

  <display:column media="html" title="修改">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = "";
    if(object != null)
     id = (String) object.get("id");
  %>
    <a href="javascript:toEdit('<%=id%>')">修改</a>
  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="71405" ishead="0">

  <display:column media="html" title="删除">
   <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
  %>
    <a href="javascript:toDelete('<%=id%>')">删除</a>
  </display:column>
  </apptag:checkpower>

</display:table>
<html:link action="/TaskOperationAction.do?method=exportTaskOperationResult">导出为Excel文件</html:link>
