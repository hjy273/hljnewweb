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
<template:titile value="�߶���ͳ�Ʋ�ѯ��ϸ��Ϣ"/>

<%
BasicDynaBean  MonthlySublineStatDynaBean = (BasicDynaBean)request.getAttribute( "sublinemonthlyallDynaBean" );
String sublinename = (String)request.getAttribute("sublinename");
String linename = (String)request.getAttribute("linename");
%>
<br>
<table width="95%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <!-- �ƻ���Ϣ -->
   <tr bgcolor="#FFFFFF">
    <td width="20%" class=trcolor>��·����</td>
    <td with="45%"><%=linename %></td>
    <td bgcolor="#FFFFFF" class=trcolor>�߶�����</td>
    <td bgcolor="#FFFFFF"><%=sublinename %></td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td class=trcolor>��ά��λ</td>
    <td ><%=MonthlySublineStatDynaBean.get("contractorname") %></td>
    <td bgcolor="#FFFFFF" class=trcolor>Ѳ����</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("patrolname") %></td>
  </tr>
  <tr class=trcolor>
    <td bgcolor="#FFFFFF" class=trcolor>���·�</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("statdate") %></td>
    <td bgcolor="#FFFFFF" class=trcolor>Ѳ����</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("patrolp") %>%</td>
  </tr>
  <tr class=trcolor>
    <td bgcolor="#FFFFFF" class=trcolor>ʵ��Ѳ����</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("factpoint") %></td>
    <td bgcolor="#FFFFFF" class=trcolor>�߶γ���</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("sublinekm") %></td>
  </tr>
  <tr class=trcolor>
    <td bgcolor="#FFFFFF" class=trcolor>�ƻ�Ѳ�����</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("planpoint") %></td>
    <td bgcolor="#FFFFFF" class=trcolor>ʵ��Ѳ�����(km)</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("factkm") %></td>
  </tr>
  <tr class=trcolor>
    <td bgcolor="#FFFFFF" class=trcolor>�ؼ���</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("keypoint") %></td>
    <td bgcolor="#FFFFFF" class=trcolor>©���</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("leakpoint") %></td>
  </tr>
  <tr class=trcolor>
    <td bgcolor="#FFFFFF" class=trcolor>©��ؼ���</td>
    <td bgcolor="#FFFFFF"><%=MonthlySublineStatDynaBean.get("leakkeypoint") %></td>
    <td bgcolor="#FFFFFF" class=trcolor></td>
    <td bgcolor="#FFFFFF"></td>
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








