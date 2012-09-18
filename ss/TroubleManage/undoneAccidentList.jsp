<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript">
function doUpAccident(idValue){
    var url = "${ctx}/accidentAction.do?method=loadAccident&statusFlag=0&id=" + idValue;
    self.location.replace(url);
}
</script>
<template:titile value="未处理障碍列表"/>
<display:table name="sessionScope.queryresult" pagesize="20" id="currentRowObject">
<display:column property="contractor" title="代维单位" sortable="true" />
  <display:column property="sendtime" title="上报时间" sortable="true"/>
  <display:column property="reason" title="障碍原因" sortable="true" />
  <display:column property="emlevel" title="严重程度" sortable="true" />
  <display:column property="subline" title="巡检段" sortable="true" />
  <display:column property="point" title="巡检点" sortable="true" />


  <display:column media="html" title="处理">

  <%
    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
    String id = (String) object.get("id");
  %>
    <a href="javascript:doUpAccident('<%=id%>')">处理该障碍</a>
  </display:column>

</display:table>
 <html:link action="/accidentAction.do?method=exportUndoneAccident">导出为Excel文件</html:link>
