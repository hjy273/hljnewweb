<%@include file="/common/header.jsp"%>
<%
String id = request.getParameter("id");
String sim = request.getParameter("sim");
String spid = request.getParameter("spid");
String pw = request.getParameter("pw");
%>
<html>
<head>
<title>设备初始化</title>
</head>
<body onload="SMSBean.submit()">
<html:form method="Post" action="/smsAction.do?method=sendInitialSMS">

	<html:hidden property="simid" value="<%=sim%>"/>
	<html:hidden property="deviceid" value="<%=id%>"/>
	<html:hidden property="password" value="<%=pw%>"/>
	<html:hidden property="spid" value="<%=spid%>"/>

</html:form>
</body>
</html>
