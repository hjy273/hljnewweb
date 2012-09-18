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
<template:titile value="线路月统计查询详细信息"/>

<%
BasicDynaBean  MonthlyLineStatDynaBean = (BasicDynaBean)request.getAttribute( "linemonthlyallDynaBean" );
String linename = (String)request.getAttribute("linename");
%>
<br>
<table width="95%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <!-- 计划信息 -->
   <tr bgcolor="#FFFFFF">
    <td width="20%" class=trcolor>线路名称</td>
    <td with="45%"><%=linename %></td>
    <td bgcolor="#FFFFFF" class=trcolor>年月份</td>
    <td bgcolor="#FFFFFF"><%=MonthlyLineStatDynaBean.get("statdate") %></td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td class=trcolor>线段数量</td>
    <td ><%=MonthlyLineStatDynaBean.get("sublinen") %></td>
    <td bgcolor="#FFFFFF" class=trcolor>线路里程(km)</td>
    <td bgcolor="#FFFFFF"><%=MonthlyLineStatDynaBean.get("linekm") %></td>
  </tr>
  <tr class=trcolor>

    <td bgcolor="#FFFFFF" class=trcolor>计划巡检点次</td>
    <td bgcolor="#FFFFFF"><%=MonthlyLineStatDynaBean.get("planpoint") %></td>
    <td bgcolor="#FFFFFF" class=trcolor>实际巡检点次</td>
    <td bgcolor="#FFFFFF"><%=MonthlyLineStatDynaBean.get("factpoint") %></td>
  </tr>
  <tr class=trcolor>
    <td bgcolor="#FFFFFF" class=trcolor>巡检率</td>
    <td bgcolor="#FFFFFF"><%=MonthlyLineStatDynaBean.get("patrolp") %>%</td>
    <td bgcolor="#FFFFFF" class=trcolor>实际巡检里程(km)</td>
    <td bgcolor="#FFFFFF"><%=MonthlyLineStatDynaBean.get("factkm") %></td>
  </tr>
</table>
<template:formSubmit>
<tr>
    <td>
      <center>
      <html:button property="action" styleClass="button" onclick="addGoBack()"  >返回</html:button>
      </center>
    </td>
  </tr>
</template:formSubmit>









