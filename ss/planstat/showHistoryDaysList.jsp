<%@include file="/common/header.jsp"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.cabletech.planstat.beans.RealTimeConditionBean"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>

<%
  RealTimeConditionBean bean = (RealTimeConditionBean)request.getSession().getAttribute("realtimeconditionbean");
  String strYear = bean.getEndYear();
  String strMonth = bean.getEndMonth();
  String strContractorID = bean.getConID();
  String YM = strYear + "��" + strMonth + "��";
  String myContractorName = (String)request.getSession().getAttribute("mycontractorname");

 %>
 
<script language="javascript">
function toLook(conValue,yearValue,monthValue,dayValue){
     var url = "${ctx}/RealTimeAction.do?method=showHistoryPerDay&conid=" + conValue + "&theyear=" + yearValue + "&themonth=" + monthValue + "&theday=" + dayValue;
     self.location.replace(url);
}
</script>
<br>

<template:titile value="�¶��ŷ��������"/>
<display:table name="sessionScope.dayslist"  id="currentRowObject"  pagesize="18">
  <display:column media="html" title="��ά��˾����"><%=myContractorName %></display:column>
  <display:column media="html" title="���·�"><%=YM %></display:column>
  <display:column property="day" title="Ѳ������" sortable="true"/>
  <display:column media="html" title="����">
  <%
    HashMap object = (HashMap)pageContext.findAttribute("currentRowObject");
    String myday = (String) object.get("day");
  %>
  <a href="javascript:toLook('<%=strContractorID%>','<%=strYear%>','<%=strMonth%>','<%=myday %>')">�鿴</a>
  </display:column>
</display:table>