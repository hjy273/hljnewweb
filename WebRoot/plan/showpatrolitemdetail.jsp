<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>

<html>

	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
		media="screen, print" />
	<head>
		<title>RFID巡检明细</title>
	</head>
	<body>
		<template:titile value="RFID巡检明细"/>
		<display:table name="sessionScope.QUERYREUSLT" id="currentRowObject"
			pagesize="18">
			<display:column property="patrold_time" title="巡检时间"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column title="巡检地址" property="address" />
			<display:column title="RFID" property="rfid" />
			<display:column title="LACCI" property="lacci" />
		</display:table>
		<div align="center">
			<input type="button" class="button" value="返回"
				onclick="javascript:history.back(-1);">
		</div>
	</body>
</html>
