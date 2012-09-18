<%@include file="/common/header.jsp"%>
<%@page import="org.apache.commons.beanutils.BasicDynaBean"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />
<script language="javascript">
function toGetBack(){
        var url = "${ctx}/planstat/showWorkdayInfo.jsp";
        self.location.replace(url);
}
</script>
<body style="width: 95%">
	<template:titile value="计划周期内单个工作日巡检情况" />
	<display:table name="sessionScope.workdaytextinfo"
		id="currentRowObject" pagesize="18">
		<display:column property="patroldate" title="巡检时间" sortable="true" />
		<display:column property="pointname" title="巡检点名称" sortable="true" />
		<display:column property="addressinfo" title="巡检点位置" sortable="true" />
		<display:column property="sublinename" title="所属巡检段" sortable="true" />
	</display:table>
	<p>
	<center>
		<input type="button" class="button_length" onclick="toGetBack()" value="返回工作日列表页面">
	</center>
	<p>
</body>
