<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<body style="width: 95%">
	<template:titile value="巡检明细表" />
	<display:table name="sessionScope.patrollist" id="currentRowObject"
		pagesize="18">
		<display:column property="patroldate" title="巡检时间" sortable="true" />
		<display:column property="sublinename" title="所属巡检段" sortable="true" />
		<display:column property="pointname" title="巡检点名称" sortable="true" />
		<display:column property="addressinfo" title="巡检点位置" sortable="true" />
		<display:column property="isfocus" title="是否关键点" sortable="true" />
	</display:table>
</body>
