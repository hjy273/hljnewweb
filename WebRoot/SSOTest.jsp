<%@ page language="java" contentType="text/html; charset=GBK"%><%@include file="/common/header.jsp"%>
<%
	String userid = (String)request.getParameter("userId");
    String username = (String) request.getParameter("userName");
%>
<script language="javascript" type="">
	function init(){
      	openwin();
		userInfoBean.submit();
        window.opener = null;
        window.close();
    }
    function openwin() {
		newwin=window.open ("", "newwindow", "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
		newwin.moveTo(0,0);
		newwin.resizeTo(screen.width,screen.height);
		newwin.focus();
	}
</script>
<html>
<head>
<title>

</title>
</head>
<body   onload="init()">

	<form name="userInfoBean" method="Post" action="SSOLogin.do?method=SSOLogin" target = "newwindow" >
		<input  type="hidden" name="userId"  id="iId"  value="<%=userid%>" />
        <input  type="hidden" name="userName" id="usernameId" value="<%=username%>"/>
    </form>
</body>
</html>
