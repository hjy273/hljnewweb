<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<html>
<head>
<title>session过期拦截</title>

<script language="javascript" type="text/javascript">

function onload(){
	var con = alert("您很久没有操作系统了，致使会话过期,请重新登陆!");
    //window.open("http://${pageContext.request.serverName}:${pageContext.request.serverPort}${ctx}");
    top.location="/";
    //parent.parent.window.opener=null;
	//parent.parent.window.close();
}
</script>
</head>
<body onload="onload()">
</body>
</html>
