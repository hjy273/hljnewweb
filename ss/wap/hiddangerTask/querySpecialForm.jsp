<%@ page language="java" pageEncoding="GBK"%>
<%@include file="/wap/header.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="test/html; charset=GBK">
		<title>盯防查询</title>
		<script type="text/javascript">
	function back() {
		location = "${ctx}/wap/hiddangerAction.do?method=index&&env=wap";
	}
</script>
	</head>
	<body>
		<div>
			<a class="dept1">${LOGIN_USER.userName} 您好！</a><a class="dept2"
				href="${ctx}/wap/navigation.jsp">功能导航</a><a class="dept"
				href="javascript:exitSys();">退出</a><br/><br />
		</div>
		<div>
			<template:titile value="盯防查询"/>
		</div>
		<form action="${ctx}/wap/hiddangerAction.do?method=queryHiddanger"
			name="form" method="post">
			${contractorHtml }
			<center>
				<input type="submit" name="query" value="查询">
				<input type="button" value="返回" onclick="back();" />
			</center>
		</form>
	</body>
</html>
