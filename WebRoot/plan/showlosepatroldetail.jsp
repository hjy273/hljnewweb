<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
		media="screen, print" />
	<head>
		<title>δѲ����ϸ��</title>
			<script type="text/javascript" language="javascript">
</script>
	</head>
	<body>
		<template:titile value="δѲ����ϸ��" />
		<display:table name="sessionScope.QUERYREUSLT" id="currentRowObject"
			pagesize="18">
			<display:column property="rs_name" title="Ѳ����Դ" maxLength="18" />
			<display:column title="δѲ������" property="ritemcount" />
		</display:table>
		<div align="center">
			<input type="button" class="button" value="����"
				onclick="location.replace('<%=session.getAttribute("previousURL")%>')">
		</div>

	</body>
</html>