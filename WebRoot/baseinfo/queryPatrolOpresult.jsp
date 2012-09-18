<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function toDelete(idValue){
    if(confirm("确定删除该纪录？")){
        var url = "${ctx}/PatrolOpAction.do?method=deletePatrolOp&id=" + idValue;
        self.location.replace(url);
    }
}

function toEdit(idValue){
        var url = "${ctx}/PatrolOpAction.do?method=loadPatrolOp&id=" + idValue;
        self.location.replace(url);

}
</script>

<template:titile value="查询巡检事故码信息结果"/>
<display:table name="sessionScope.queryresult" id="currentRowObject" pagesize="18">


  <display:column property="operationcode" title="事故码" sortable="true"/>
  <display:column property="optype" title="事故类型" sortable="true"/>
  <display:column property="emlevel" title="事故级别" sortable="true"/>
  <display:column property="operationdes" title="事故码说明" />

    <%-- n.b. that this could be done via the autolink attribute, but that rather defeats the purpose    --%>
   <apptag:checkpower thirdmould="71304" ishead="0">

    <display:column media="html" title="修改">
    <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String operationcode = "";
    if(object != null)
     operationcode = (String) object.get("operationcode");
  %>
    <a href="javascript:toEdit('<%=operationcode%>')">修改</a>
  </display:column>
  </apptag:checkpower>
  <apptag:checkpower thirdmould="71305" ishead="0">

    <display:column media="html" title="删除">
     <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String operationcode = (String) object.get("operationcode");
  %>
    <a href="javascript:toDelete('<%=operationcode%>')">删除</a>
  </display:column>
  </apptag:checkpower>
</display:table>
<html:link action="/PatrolOpAction.do?method=exportPatrolOpResult">导出为Excel文件</html:link>
