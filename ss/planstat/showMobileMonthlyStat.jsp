<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css" type="text/css" media="screen, print" />
<%@page import="com.cabletech.baseinfo.domainobjects.*" %>
<script language="javascript" type="">
<!--
function addGoBack()
        {
          window.history.go(-1);

        }
//-->
</script>
<br>
<template:titile value="市移动公司月统计查询详细信息"/>

<%
int count = 7;
BasicDynaBean  MonthlyMobileStatDynaBean = (BasicDynaBean)request.getAttribute( "mobilemonthlyallDynaBean" );
String mobilename = (String)request.getAttribute("mobilename");
%>
<br>
<table width="95%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <!-- 计划信息 -->
  <tr bgcolor="#FFFFFF">
    <td width="30%" class=trcolor>移动公司名称</td>
    <td with="45%"><%=mobilename%></td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td bgcolor="#FFFFFF" class=trcolor>年月份</td>
    <td bgcolor="#FFFFFF"><%=MonthlyMobileStatDynaBean.get("statdate") %></td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td bgcolor="#FFFFFF" class=trcolor>各代维公司总巡检率</td>
    <td bgcolor="#FFFFFF"><%=MonthlyMobileStatDynaBean.get("overallpatrolp") %>%</td>
  </tr>
</table>

<br>
<template:titile value="所管辖各市代维公司本月计划列表"/>
<display:table name="sessionScope.mobilemonthlydetail"   id="currentRowObject"  pagesize="18">
  <display:column property="contractorname" title="代维公司名称" sortable="true"/>
  <display:column media="html" title="巡检率">
  <%
  BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  Object patrolp = object.get("patrolp");
  %>
  <%=patrolp.toString()+"%"%>
  </display:column>
  <display:column property="trouble" title="隐患数目" sortable="true"/>
  <display:column property="leakpoint" title="漏检点数目" sortable="true"/>
</display:table>
<template:formSubmit>
<tr>
    <td>
      <center>
      <html:button property="action" styleClass="button" onclick="addGoBack()"  >返回</html:button>
      </center>
    </td>
  </tr>
</template:formSubmit>




