<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
	</script>
</head>
<body>
	<template:titile value="漏检明细" />
	<display:table name="sessionScope.result" id="row"  pagesize="18" sort="list">
		<display:column media="html" title="漏检点名称" sortable="true"> 
			<apptag:assorciateAttr table="pointinfo" label="pointname" key="pointid" keyValue="${row['point_id']}" />
		</display:column>
		<display:column media="html" title="巡检段名称" sortable="true">
			<apptag:assorciateAttr table="sublineinfo" label="sublinename" key="sublineid" keyValue="${row['subline_id']}" />
		</display:column>
		<display:column media="html" title="计划巡检次数" sortable="true">
			${row["plan_patrol_times"]}
		</display:column>
		<display:column media="html" title="实际巡检次数" sortable="true">
			${row["fact_patrol_times"]}
		</display:column>
		<display:column media="html" title="漏检次数" sortable="true">
			${row["leak_patrol_times"]}
		</display:column>
	</display:table>
</body>