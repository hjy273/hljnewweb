<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>考核表</title>
		<script type="text/javascript">
	
</script>
	</head>

	<body>
		<template:titile value="查看月考核表" />
		<html:form action="/appraiseMonthAction?method=saveAppraiseRuleResult">
		${html}
		<br/>
		<div align="center">
		<input class="button" type="button" onclick="history.back()" value="返回" />
		</div>
		</html:form>
	</body>
</html>
