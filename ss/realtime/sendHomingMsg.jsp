<%@include file="/common/header.jsp"%>
<%
String id = request.getParameter("id");
String sim = request.getParameter("sim");
String pw = request.getParameter("pw");
%>
<template:titile value="发送自动巡检命令"/>
<html:form method="Post" action="/smsAction.do?method=sendHomingSMS">
  <template:formTable>
    <template:formTr name="设备手机号码" isOdd="false">
      <html:text property="simid" styleClass="inputtext" style="width:145" value="<%=sim%>"/>
	  <html:hidden property="deviceid" value="<%=id%>"/>
	  <html:hidden property="password" value="<%=pw%>"/>
	  <html:hidden property="mode" value="0"/>
    </template:formTr>
    <template:formTr name="返回消息个数">
		<html:text property="count" styleClass="inputtext" style="width:145" maxlength="3" value="15"/>&nbsp;&nbsp;个
    </template:formTr>
    <template:formTr name="返回时间间隔" isOdd="false">
      <html:text property="seconds" styleClass="inputtext" style="width:145" maxlength="6" value="15"/>&nbsp;&nbsp;秒
    </template:formTr>
    <template:formSubmit>
      <td>
        <html:submit styleClass="button">发送</html:submit>
      </td>
    </template:formSubmit>
  </template:formTable>
</html:form>
