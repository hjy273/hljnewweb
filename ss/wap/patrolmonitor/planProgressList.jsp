<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/wap/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=GBK">
		<title>当前巡检计划</title>
		<script type="text/javascript">
	function back() {
		location = "${ctx }/wap/patrolmonitor.do?method=pmindex&&env=wap";
	}
</script>
	</head>
	<body>
		<div>
			<a class="dept1">${LOGIN_USER.userName} 您好！</a><a class="dept2"
				href="${ctx}/wap/navigation.jsp">功能导航</a><a class="dept"
				href="javascript:exitSys();">退出</a><br />
		</div>
		<br>
		<template:titile value="当前巡检计划"/>
		${PLANPROGRESS}
		<br />
		<center>
			<input type="button" value="返回" onclick="back();" />
		</center>
	</body>
</html>