<%@include file="/common/header.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print"/>
	<title></title>
	<script type="text/javascript">
		function viewDetail(id, type){
			var url = "${ctx}/linepatrol/specialplan/stat/stat.jsp?type="+type+"&id="+id;
			parent.location.href = url;
		}
	</script>
</head>
<body>
	<template:titile value="计划统计列表" />
	<display:table name="sessionScope.result" id="row"  pagesize="18" sort="list">
		<display:column property="specPlanName" title="计划名称" sortable="true" />
		<display:column media="html" title="计划类型" sortable="true">
			<c:if test="${row.specPlanType eq '001'}">
				盯防计划
			</c:if>
			<c:if test="${row.specPlanType eq '002'}">
				保障计划
			</c:if>
		</display:column>
		<display:column media="html" title="巡检统计状态" sortable="true">
			<c:choose>
				<c:when test="${row.patrolStatState eq '1'}">
					计划执行中
				</c:when>
				<c:otherwise>
					计划执行完毕
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:column media="html" title="巡检率" sortable="true">
			${row.patrolRatio}%
		</display:column>
		<display:column media="html" title="盯防完成率" sortable="true">
			${row.watchRatio}%
		</display:column>
		<display:column media="html" title="操作" sortable="true">
			<a href="javascript:viewDetail('${row.specPlanId}','${row.specPlanType}')">详细</a>
		</display:column>
	</display:table>
</body>