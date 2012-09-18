<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />

<html>
	<head>
		<title>演练查询列表</title>
		<script type="text/javascript">
			toPerfectDrillTask=function(taskId){
            	window.location.href = "${ctx}/drillTaskAction.do?method=perfectDrillTaskForm&taskId="+taskId;
			}
			toDeleteTempTask=function(taskId){
				if(confirm("您确认要删除该临时演练任务吗？")){
            		window.location.href = "${ctx}/drillTaskAction.do?method=deleteTempTask&taskId="+taskId;
            	}
			}
		</script>
	</head>
	<body>
		<template:titile value="演练查询列表"/>
		<display:table name="sessionScope.list" id="drill" pagesize="18">
			<display:column property="name" title="演练名称" headerClass="subject"  sortable="true"/>
			<display:column property="drill_level" title="演练级别" headerClass="subject"  sortable="true"/>
			<display:column property="task_begin_time" title="建议开始时间" headerClass="subject"  sortable="true"/>
			<display:column property="task_end_time" title="建议结束时间" headerClass="subject"  sortable="true"/>
			<display:column property="locale" title="演练地点" headerClass="subject"  sortable="true"/>
			<display:column property="remark" title="备注" headerClass="subject"  sortable="true" maxLength="15"/>
			<display:column property="task_createtime" title="创建时间" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
				<a href="javascript:toPerfectDrillTask('<bean:write name="drill" property="id"/>')">完善任务</a> | 
				<a href="javascript:toDeleteTempTask('<bean:write name="drill" property="id"/>')">删除</a>
			</display:column>
		</display:table>
	</body>
</html>

