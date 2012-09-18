<%@include file="/common/header.jsp"%>
<%@page import="org.apache.commons.beanutils.BasicDynaBean"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
<script language="javascript" type="">
function toGetForm(idValue,requestIdValue,sublineNameValue,requestTimeValue){
   var url = "${ctx}/SublineRealTimeAction.do?method=showResponsePerSubline&id=" + idValue + "&requestid=" + requestIdValue + "&sublinename=" + sublineNameValue + "&requesttime=" + requestTimeValue;
   self.location.replace(url);
}
</script><br>

<template:titile value="巡检线段今日巡检情况显示"/>
<display:table name="sessionScope.requestSublineList"  id="currentRowObject"  pagesize="18">
  <display:column property="requestid" title="请求序列号"></display:column>
  <display:column property="requesttime" title="请求时间"></display:column> 
  <display:column property="sublinename" title="巡检线段名称"/>
  <display:column media="html" title="状态">
  <%
  		BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  		String state = object.get("state").toString();
  		String sublineid = object.get("sublineid").toString();
  		String requestid = object.get("requestid").toString();
  		String sublinename = object.get("sublinename").toString();
  		String requesttime = object.get("requesttime").toString();
        if ("1".equals(state)){
  %>
           <a href="javascript:toGetForm('<%=sublineid%>','<%=requestid %>','<%=sublinename %>','<%=requesttime %>')">查看结果</a>
     <%}else if ("0".equals(state)){
           %>正在统计中...<%}else {%>统计出错<%}%>
  </display:column>
</display:table>
