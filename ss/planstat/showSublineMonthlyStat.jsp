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
<template:titile value="线段月统计查询详细信息"/>

<%
BasicDynaBean  MonthlySublineStatDynaBean = (BasicDynaBean)request.getAttribute( "sublinemonthlyallDynaBean" );
String sublinename = (String)request.getAttribute("sublinename");
String linename = (String)request.getAttribute("linename");
%>
<br>
<table width="95%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <!-- 计划信息 -->
   <tr bgcolor="#FFFFFF">
    <td width="20%" class=trcolor>线路名称</td>
    <td with="45%"><%=linename %></td>
    <td bgcolor="#FFFFFF" class=trcolor>线段名称</td>
    <td bgcolor="#FFFFFF"><%=sublinename %></td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td class=trcolor>代维单位</td>
    <td ><%=MonthlySublineStatDynaBean.get("contractorname") %></td>
    <td bgcolor="#FFFFFF" class=trcolor>巡检人</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("patrolname") %></td>
  </tr>
  <tr class=trcolor>
    <td bgcolor="#FFFFFF" class=trcolor>年月份</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("statdate") %></td>
    <td bgcolor="#FFFFFF" class=trcolor>巡检率</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("patrolp") %>%</td>
  </tr>
  <tr class=trcolor>
    <td bgcolor="#FFFFFF" class=trcolor>实际巡检点次</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("factpoint") %></td>
    <td bgcolor="#FFFFFF" class=trcolor>线段长度</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("sublinekm") %></td>
  </tr>
  <tr class=trcolor>
    <td bgcolor="#FFFFFF" class=trcolor>计划巡检次数</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("planpoint") %></td>
    <td bgcolor="#FFFFFF" class=trcolor>实际巡检里程(km)</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("factkm") %></td>
  </tr>
  <tr class=trcolor>
    <td bgcolor="#FFFFFF" class=trcolor>关键点</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("keypoint") %></td>
    <td bgcolor="#FFFFFF" class=trcolor>漏检点</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("leakpoint") %></td>
  </tr>
  <tr class=trcolor>
    <td bgcolor="#FFFFFF" class=trcolor>漏检关键点</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("leakkeypoint") %></td>
    <td bgcolor="#FFFFFF" class=trcolor></td>
    <td bgcolor="#FFFFFF"></td>
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








