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
<template:titile value="���ƶ���˾��ͳ�Ʋ�ѯ��ϸ��Ϣ"/>

<%
int count = 7;
BasicDynaBean  MonthlyMobileStatDynaBean = (BasicDynaBean)request.getAttribute( "mobilemonthlyallDynaBean" );
String mobilename = (String)request.getAttribute("mobilename");
%>
<br>
<table width="95%" border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#999999">
  <!-- �ƻ���Ϣ -->
  <tr bgcolor="#FFFFFF">
    <td width="30%" class=trcolor>�ƶ���˾����</td>
    <td with="45%"><%=mobilename%></td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td bgcolor="#FFFFFF" class=trcolor>���·�</td>
    <td bgcolor="#FFFFFF"><%=MonthlyMobileStatDynaBean.get("statdate") %></td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td bgcolor="#FFFFFF" class=trcolor>����ά��˾��Ѳ����</td>
    <td bgcolor="#FFFFFF"><%=MonthlyMobileStatDynaBean.get("overallpatrolp") %>%</td>
  </tr>
</table>

<br>
<template:titile value="����Ͻ���д�ά��˾���¼ƻ��б�"/>
<display:table name="sessionScope.mobilemonthlydetail"   id="currentRowObject"  pagesize="18">
  <display:column property="contractorname" title="��ά��˾����" sortable="true"/>
  <display:column media="html" title="Ѳ����">
  <%
  BasicDynaBean object = (BasicDynaBean) pageContext.findAttribute("currentRowObject");
  Object patrolp = object.get("patrolp");
  %>
  <%=patrolp.toString()+"%"%>
  </display:column>
  <display:column property="trouble" title="������Ŀ" sortable="true"/>
  <display:column property="leakpoint" title="©�����Ŀ" sortable="true"/>
</display:table>
<template:formSubmit>
<tr>
    <td>
      <center>
      <html:button property="action" styleClass="button" onclick="addGoBack()"  >����</html:button>
      </center>
    </td>
  </tr>
</template:formSubmit>




