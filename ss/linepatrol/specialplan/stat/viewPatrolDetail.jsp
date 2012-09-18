<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
	</script>
</head>
<body>
	<template:titile value="Ѳ����ϸ" />
	<display:table name="sessionScope.result" id="row"  pagesize="18" sort="list">
		<display:column media="html" title="Ѳ�������" sortable="true">
			<apptag:assorciateAttr table="pointinfo" label="pointname" key="pointid" keyValue="${row['point_id']}" />
		</display:column>
		<display:column media="html" title="Ѳ�������" sortable="true">
			<apptag:assorciateAttr table="sublineinfo" label="sublinename" key="sublineid" keyValue="${row['subline_id']}" />
		</display:column>
		<display:column media="html" title="Ѳ��SIM����" sortable="true">
			${row['sim_id']}
		</display:column>
		<display:column media="html" title="Ѳ����豸���" sortable="true">
			<apptag:assorciateAttr table="terminalinfo" label="deviceid" key="terminalid" keyValue="${row['terminal_id']}" />
		</display:column>
		<display:column media="html" title="Ѳ������" sortable="true">
			<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${row['patrol_time']}" />
		</display:column>
	</display:table>
</body>