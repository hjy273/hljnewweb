<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
	</script>
</head>
<body>
	<template:titile value="无效盯防" />
	<display:table name="sessionScope.result" id="row"  pagesize="18" sort="list">
		<display:column media="html" title="盯防区域" sortable="true">
			<apptag:assorciateAttr table="lp_watch_polygon" label="polygonname" key="polygonid" keyValue="${row['watch_area_id']}" />
		</display:column>
		<display:column media="html" title="巡检组" sortable="true">
			<apptag:assorciateAttr table="patrolmaninfo" label="patrolname" key="patrolid" keyValue="${row['deptId']}" />
		</display:column>
		<display:column media="html" title="盯防时段" sortable="true">
			${row['starttime']}--${row['endtime']}
		</display:column>
		<display:column media="html" title="时间间隔" sortable="true">
			${row['space']}分钟
		</display:column>
		<display:column media="html" title="发送盯防的SIM卡号" sortable="true">
			${row['sim_id']}
		</display:column>
		<display:column media="html" title="发送盯防的设备编号" sortable="true">
			<apptag:assorciateAttr table="terminalinfo" label="deviceid" key="terminalid" keyValue="${row['terminal_id']}" />
		</display:column>
		<display:column media="html" title="无效盯防发送时间" sortable="true">
			<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${row['invalid_watch_gps_time']}" />
		</display:column>
		<display:column media="html" title="无效盯防类型" sortable="true">
			<c:if test="${row['invalid_watch_type'] == '0'}">
				未巡检
			</c:if>
			<c:if test="${row['invalid_watch_type'] == '1'}">
				不在有效区域
			</c:if>
		</display:column>
	</display:table>
</body>