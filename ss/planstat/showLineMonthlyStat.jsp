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
<template:titile value="��·��ͳ�Ʋ�ѯ��ϸ��Ϣ"/>

<%
BasicDynaBean  MonthlyLineStatDynaBean = (BasicDynaBean)request.getAttribute( "linemonthlyallDynaBean" );
String linename = (String)request.getAttribute("linename");
%>
<br>
<table width="95%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <!-- �ƻ���Ϣ -->
   <tr bgcolor="#FFFFFF">
    <td width="20%" class=trcolor>��·����</td>
    <td with="45%"><%=linename %></td>
    <td bgcolor="#FFFFFF" class=trcolor>���·�</td>
    <td bgcolor="#FFFFFF"><%=MonthlyLineStatDynaBean.get("statdate") %></td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td class=trcolor>�߶�����</td>
    <td ><%=MonthlyLineStatDynaBean.get("sublinen") %></td>
    <td bgcolor="#FFFFFF" class=trcolor>��·���(km)</td>
    <td bgcolor="#FFFFFF"><%=MonthlyLineStatDynaBean.get("linekm") %></td>
  </tr>
  <tr class=trcolor>

    <td bgcolor="#FFFFFF" class=trcolor>�ƻ�Ѳ����</td>
    <td bgcolor="#FFFFFF"><%=MonthlyLineStatDynaBean.get("planpoint") %></td>
    <td bgcolor="#FFFFFF" class=trcolor>ʵ��Ѳ����</td>
    <td bgcolor="#FFFFFF"><%=MonthlyLineStatDynaBean.get("factpoint") %></td>
  </tr>
  <tr class=trcolor>
    <td bgcolor="#FFFFFF" class=trcolor>Ѳ����</td>
    <td bgcolor="#FFFFFF"><%=MonthlyLineStatDynaBean.get("patrolp") %>%</td>
    <td bgcolor="#FFFFFF" class=trcolor>ʵ��Ѳ�����(km)</td>
    <td bgcolor="#FFFFFF"><%=MonthlyLineStatDynaBean.get("factkm") %></td>
  </tr>
</table>
<template:formSubmit>
<tr>
    <td>
      <center>
      <html:button property="action" styleClass="button" onclick="addGoBack()"  >����</html:button>
      </center>
    </td>
  </tr>
</template:formSubmit>









