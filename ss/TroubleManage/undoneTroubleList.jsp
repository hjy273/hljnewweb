<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function doUpTrouble(idValue){
    var url = "${ctx}/accidentAction.do?method=loadTrouble&statusFlag=0&id=" + idValue;
    self.location.replace(url);
}
</script>
<template:titile value="未处理隐患列表"/>
<display:table name="sessionScope.queryresult" pagesize="20" id="currentRowObject">
  <display:column property="sendtime" title="上报时间" sortable="true"/>
  <display:column property="reason" title="隐患内容" sortable="true" />
  <display:column property="emlevel" title="严重程度" sortable="true" />
  <display:column property="subline" title="巡检段" sortable="true" />
  <display:column property="point" title="巡检点" sortable="true" />
  <display:column property="contractor" title="代维单位" sortable="true" />
  <display:column media="html" title="处理">
  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
  %>
    <a href="javascript:doUpTrouble('<%=id%>')">处理该隐患</a>
  </display:column>
</display:table>
<logic:empty name="queryresult">
</logic:empty>
<logic:notEmpty name="queryresult">
 <html:link action="/accidentAction.do?method=exportUndoneTrouble">导出为Excel文件</html:link>
 </logic:notEmpty>
