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
   <template:titile value="Ѳ��ά�����¹�������"/>
</logic:equal>
<logic:notEqual value="group" name="PMType">
   <template:titile value="Ѳ����Ա�¹�������"/>
</logic:notEqual>
<display:table name="sessionScope.patrolmanMonthlyReport"   id="currentRowObject"  pagesize="16">
     <logic:equal value="group" name="PMType">
	    <display:column property="patrolname" title="Ѳ��ά��������" sortable="true"/>
	 </logic:equal>
	 <logic:notEqual value="group" name="PMType">
	    <display:column property="patrolname" title="Ѳ��Ա����" sortable="true"/>
	 </logic:notEqual>
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