<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>���˱�</title>
		<script type="text/javascript">
	
</script>
	</head>

	<body>
		<template:titile value="${titleMap['title']}" />
		<table width="100%">
			<tr align="center">
				<td>
					���˵�λ��${titleMap['contractorName']}
				</td>
				<td>
					�������ڣ�${titleMap['appraiseMonth']}
				</td>
				<td>
					�����ˣ�${LOGIN_USER.userName}
				</td>
			</tr>
		</table>
		<html:form action="/appraiseMonthAction?method=saveAppraiseRuleResult">
		${html}
		<br/>
		<div align="center">
		<html:submit styleClass="button">ȷ��</html:submit>
		<input class="button" type="button" onclick="history.back()" value="����" />
		</div>
		</html:form>
	</body>
</html>
