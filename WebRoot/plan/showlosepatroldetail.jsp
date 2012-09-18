<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>
<html>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
		media="screen, print" />
	<head>
		<title>未巡检明细表</title>
			<script type="text/javascript" language="javascript">
</script>
	</head>
	<body>
		<template:titile value="未巡检明细表" />
		<display:table name="sessionScope.QUERYREUSLT" id="currentRowObject"
			pagesize="18">
			<display:column property="rs_name" title="巡检资源" maxLength="18" />
			<display:column title="未巡检项数" property="ritemcount" />
		</display:table>
		<div align="center">
			<input type="button" class="button" value="返回"
				onclick="location.replace('<%=session.getAttribute("previousURL")%>')">
		</div>

	</body>
</html>