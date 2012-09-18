<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
	</script>
</head>
<body>
	<template:titile value="巡回线段" />
	<display:table name="sessionScope.result" id="row"  pagesize="18" sort="list">
		<display:column media="html" title="特巡计划" sortable="true">
			<apptag:assorciateAttr table="lp_special_plan" label="plan_name" key="id" keyValue="${row.specPlanId}" />
		</display:column>
		<display:column media="html" title="巡检段名称" sortable="true">
			<apptag:assorciateAttr table="sublineinfo" label="sublinename" key="sublineid" keyValue="${row.sublineId}" />
		</display:column>
		<display:column media="html" title="线段级别" sortable="true">
			<apptag:quickLoadList cssClass="input" keyValue="${row.sublineLevel}" name="cabletype" listName="cabletype" type="look"/>
		</display:column>
		<display:column media="html" title="巡检组" sortable="true">
			<apptag:assorciateAttr table="patrolmaninfo" label="patrolname" key="patrolid" keyValue="${row.patrolGroupId}" />
		</display:column>
		<display:column media="html" title="计划巡检点数/点次" sortable="true">
			${row.planPointhNumber}点/${row.planPointhTimes}次
		</display:column>
		<display:column media="html" title="实际巡检点数/点次" sortable="true">
			${row.factPointhNumber}点/${row.factPointhTimes}次
		</display:column>
		<display:column media="html" title="计划/实际巡检里程" sortable="true">
			${row.planPatrolMileage}公里/${row.factPatrolMileage}公里
		</display:column>
		<display:column media="html" title="巡检率" sortable="true">
			${row.patrolRadio}%
		</display:column>
	</display:table>
</body>