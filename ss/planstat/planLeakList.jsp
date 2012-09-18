<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/css/justforplanstat.css"
	type="text/css" media="screen, print" />
<body style="width: 95%">
	<template:titile value="漏检明细表" />
	<display:table name="sessionScope.leaklist" id="currentRowObject"
		pagesize="18">
		<display:column property="sublinename" title="所属巡检段" sortable="true" />
		<display:column property="pointname" title="巡检点名称" sortable="true" />
		<display:column property="addressinfo" title="巡检点位置" sortable="true" />
		<display:column property="executetimes" title="计划巡检次数" sortable="true" />
		<display:column property="leaktime" title="漏检次数" sortable="true" />
		<display:column property="isfocus" title="是否关键点" sortable="true" />
	</display:table>
</body>

