<%@include file="/common/header.jsp"%>
<%
String id = request.getParameter("id");
String sim = request.getParameter("sim");
String pw = request.getParameter("pw");
%>
<template:titile value="�����Զ�Ѳ������"/>
<html:form method="Post" action="/smsAction.do?method=sendHomingSMS">
  <template:formTable>
    <template:formTr name="�豸�ֻ�����" isOdd="false">
      <html:text property="simid" styleClass="inputtext" style="width:145" value="<%=sim%>"/>
	  <html:hidden property="deviceid" value="<%=id%>"/>
	  <html:hidden property="password" value="<%=pw%>"/>
	  <html:hidden property="mode" value="0"/>
    </template:formTr>
    <template:formTr name="������Ϣ����">
		<html:text property="count" styleClass="inputtext" style="width:145" maxlength="3" value="15"/>&nbsp;&nbsp;��
    </template:formTr>
    <template:formTr name="����ʱ����" isOdd="false">
      <html:text property="seconds" styleClass="inputtext" style="width:145" maxlength="6" value="15"/>&nbsp;&nbsp;��
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">����</html:submit>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
