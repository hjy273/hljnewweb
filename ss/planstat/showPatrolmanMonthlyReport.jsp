<%@ include file="/common/header.jsp"%>
<head>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<%@page import="com.cabletech.commons.config.*"%>
<%
  GisConInfo gisServer =GisConInfo.newInstance();
  String PreURL = "http://" + gisServer.getReportsip() + ":" + gisServer.getReportsport() + "${ctx}/";
%>
<script language="javascript" type="">

function toGetBack(){
        window.history.back();
}

</script>
</head>
<br>
<logic:equal value="group" name="PMType">
   <template:titile value="巡检维护组月工作报表"/>
</logic:equal>
<logic:notEqual value="group" name="PMType">
   <template:titile value="巡检人员月工作报表"/>
</logic:notEqual>
<display:table name="sessionScope.patrolmanMonthlyReport"   id="currentRowObject"  pagesize="16">
     <logic:equal value="group" name="PMType">
	    <display:column property="patrolname" title="巡检维护组名称" sortable="true"/>
	 </logic:equal>
	 <logic:notEqual value="group" name="PMType">
	    <display:column property="patrolname" title="巡检员名称" sortable="true"/>
	 </logic:notEqual>
     <display:column media="html" title="PDF格式">
	    <%
		    BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
		    String pdfurl =  "";
		    if(object != null)
		      pdfurl = PreURL + (String) object.get("pdfurl"); 
	    %>
	    <a href= <%= pdfurl %> target=_blank>查看</a>
	  </display:column>
</display:table>

<p>
<center>
<input type="button" class="button" onclick="toGetBack()" value="返回" >
</center>
<p>