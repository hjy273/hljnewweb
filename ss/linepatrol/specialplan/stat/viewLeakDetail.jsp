<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
	</script>
</head>
<body>
	<template:titile value="©����ϸ" />
	<display:table name="sessionScope.result" id="row"  pagesize="18" sort="list">
		<display:column media="html" title="©�������" sortable="true"> 
			<apptag:assorciateAttr table="pointinfo" label="pointname" key="pointid" keyValue="${row['point_id']}" />
		</display:column>
		<display:column media="html" title="Ѳ�������" sortable="true">
			<apptag:assorciateAttr table="sublineinfo" label="sublinename" key="sublineid" keyValue="${row['subline_id']}" />
		</display:column>
		<display:column media="html" title="�ƻ�Ѳ�����" sortable="true">
			${row["plan_patrol_times"]}
		</display:column>
		<display:column media="html" title="ʵ��Ѳ�����" sortable="true">
			${row["fact_patrol_times"]}
		</display:column>
		<display:column media="html" title="©�����" sortable="true">
			${row["leak_patrol_times"]}
		</display:column>
	</display:table>
</body>