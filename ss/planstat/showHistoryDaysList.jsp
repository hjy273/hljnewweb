<%@include file="/common/header.jsp"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.cabletech.planstat.beans.RealTimeConditionBean"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>

<%
  RealTimeConditionBean bean = (RealTimeConditionBean)request.getSession().getAttribute("realtimeconditionbean");
  String strYear = bean.getEndYear();
  String strMonth = bean.getEndMonth();
  String strContractorID = bean.getConID();
  String YM = strYear + "年" + strMonth + "月";
  String myContractorName = (String)request.getSession().getAttribute("mycontractorname");

 %>
 
<script language="javascript">
function toLook(conValue,yearValue,monthValue,dayValue){
     var url = "${ctx}/RealTimeAction.do?method=showHistoryPerDay&conid=" + conValue + "&theyear=" + yearValue + "&themonth=" + monthValue + "&theday=" + dayValue;
     self.location.replace(url);
}
</script>
<br>

<template:titile value="月短信发送情况表"/>
<display:table name="sessionScope.dayslist"  id="currentRowObject"  pagesize="18">
  <display:column media="html" title="代维公司名称"><%=myContractorName %></display:column>
  <display:column media="html" title="年月份"><%=YM %></display:column>
  <display:column property="day" title="巡检日期" sortable="true"/>
  <display:column media="html" title="操作">
  <%
    HashMap object = (HashMap)pageContext.findAttribute("currentRowObject");
    String myday = (String) object.get("day");
  %>
  <a href="javascript:toLook('<%=strContractorID%>','<%=strYear%>','<%=strMonth%>','<%=myday %>')">查看</a>
  </display:column>
</display:table>