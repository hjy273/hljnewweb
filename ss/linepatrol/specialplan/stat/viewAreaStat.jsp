<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
	</script>
</head>
<body>
	<template:titile value="按盯防区域统计" />
	<display:table name="sessionScope.result" id="row"  pagesize="18" sort="list">
		<display:column media="html" title="特巡计划" sortable="true">
			<apptag:assorciateAttr table="lp_special_plan" label="plan_name" key="id" keyValue="${row.specPlanId}" />
		</display:column>
		<display:column media="html" title="盯防区域" sortable="true">
			<apptag:assorciateAttr table="lp_watch_polygon" label="polygonname" key="polygonid" keyValue="${row.areaId}" />
		</display:column>
		<display:column media="html" title="巡检组" sortable="true">
			<apptag:assorciateAttr table="patrolmaninfo" label="patrolname" key="patrolid" keyValue="${row.patrolGroupId}" />
		</display:column>
		<display:column media="html" title="计划盯防数量" sortable="true">
			${row.planWatchNumber}
		</display:column>
		<display:column media="html" title="实际盯防数量" sortable="true">
			${row.factWatchNumber}
		</display:column>
		<display:column media="html" title="盯防完成率" sortable="true">
			${row.watchRadio}%
		</display:column>
	</display:table>
</body>