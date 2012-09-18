<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
	</script>
</head>
<body>
	<template:titile value="��Ч����" />
	<display:table name="sessionScope.result" id="row"  pagesize="18" sort="list">
		<display:column media="html" title="��������" sortable="true">
			<apptag:assorciateAttr table="lp_watch_polygon" label="polygonname" key="polygonid" keyValue="${row['watch_area_id']}" />
		</display:column>
		<display:column media="html" title="Ѳ����" sortable="true">
			<apptag:assorciateAttr table="patrolmaninfo" label="patrolname" key="patrolid" keyValue="${row['deptId']}" />
		</display:column>
		<display:column media="html" title="����ʱ��" sortable="true">
			${row['starttime']}--${row['endtime']}
		</display:column>
		<display:column media="html" title="ʱ����" sortable="true">
			${row['space']}����
		</display:column>
		<display:column media="html" title="���Ͷ�����SIM����" sortable="true">
			${row['sim_id']}
		</display:column>
		<display:column media="html" title="���Ͷ������豸���" sortable="true">
			<apptag:assorciateAttr table="terminalinfo" label="deviceid" key="terminalid" keyValue="${row['terminal_id']}" />
		</display:column>
		<display:column media="html" title="��Ч��������ʱ��" sortable="true">
			<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${row['valid_watch_gps_time']}" />
		</display:column>
	</display:table>
</body>