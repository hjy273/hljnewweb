<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<%@page import="com.cabletech.commons.config.*"%>
<%
  GisConInfo gisServer = GisConInfo.newInstance();
  String PreURL = "http://" + gisServer.getReportsip() + ":" + gisServer.getReportsport() + "${ctx}/";
  //String PreURL = "http://localhost:7011${ctx}/";
%>
<script language="javascript" type="">
function toGetBack(){
        window.history.back();
}
function showReportContent(urlValue)
{
  var url="${ctx}/MonthlyReportAction.do?method=showReportContent&theurl=" + urlValue;
  self.location.replace(url);

}

</script><br>

<template:titile value="ʡ�ƶ���˾�¹�������"/>
<display:table name="sessionScope.pMobileMonthlyReport"   id="currentRowObject"  pagesize="16">
	<display:column property="regionname" title="����" sortable="true"/>
    <display:column media="html" title="PDF��ʽ">
	    <%
		    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
		    String pdfurl =  "";
		    if(object != null)
		      pdfurl = PreURL + (String) object.get("pdfurl"); 
	    %>
	    <a href= <%= pdfurl %> target=_blank>�鿴</a>
	 </display:column>
</display:table>

<p>
<center>
<input type="button" class="button" onclick="toGetBack()" value="����" >
</center>
<p>