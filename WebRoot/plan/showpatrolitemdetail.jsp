<%@include file="/common/header.jsp"%>
<%@ page contentType="text/html; charset=GBK"%>

<html>

	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
		media="screen, print" />
	<head>
		<title>RFIDѲ����ϸ</title>
	</head>
	<body>
		<template:titile value="RFIDѲ����ϸ"/>
		<display:table name="sessionScope.QUERYREUSLT" id="currentRowObject"
			pagesize="18">
			<display:column property="patrold_time" title="Ѳ��ʱ��"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column title="Ѳ���ַ" property="address" />
			<display:column title="RFID" property="rfid" />
			<display:column title="LACCI" property="lacci" />
		</display:table>
		<div align="center">
			<input type="button" class="button" value="����"
				onclick="javascript:history.back(-1);">
		</div>
	</body>
</html>
