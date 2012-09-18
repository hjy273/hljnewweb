<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
	</script>
</head>
<body>
	<template:titile value="����������ͳ��" />
	<display:table name="sessionScope.result" id="row"  pagesize="18" sort="list">
		<display:column media="html" title="��Ѳ�ƻ�" sortable="true">
			<apptag:assorciateAttr table="lp_special_plan" label="plan_name" key="id" keyValue="${row.specPlanId}" />
		</display:column>
		<display:column media="html" title="��������" sortable="true">
			<apptag:assorciateAttr table="lp_watch_polygon" label="polygonname" key="polygonid" keyValue="${row.areaId}" />
		</display:column>
		<display:column media="html" title="Ѳ����" sortable="true">
			<apptag:assorciateAttr table="patrolmaninfo" label="patrolname" key="patrolid" keyValue="${row.patrolGroupId}" />
		</display:column>
		<display:column media="html" title="�ƻ���������" sortable="true">
			${row.planWatchNumber}
		</display:column>
		<display:column media="html" title="ʵ�ʶ�������" sortable="true">
			${row.factWatchNumber}
		</display:column>
		<display:column media="html" title="���������" sortable="true">
			${row.watchRadio}%
		</display:column>
	</display:table>
</body>