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
		<template:titile value="${titleMap['title']}" />
		<table width="100%">
			<tr align="center">
				<td>
					考核单位：${titleMap['contractorName']}
				</td>
				<td>
					考核日期：${titleMap['appraiseMonth']}
				</td>
				<td>
					考核人：${LOGIN_USER.userName}
				</td>
			</tr>
		</table>
		<html:form action="/appraiseMonthAction?method=saveAppraiseRuleResult">
		${html}
		<br/>
		<div align="center">
		<html:submit styleClass="button">确认</html:submit>
		<input class="button" type="button" onclick="history.back()" value="返回" />
		</div>
		</html:form>
	</body>
</html>
