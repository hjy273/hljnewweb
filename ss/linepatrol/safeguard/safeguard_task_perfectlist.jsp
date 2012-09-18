<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />

<html>
	<head>
		<title>保障查询列表</title>
		<script type="text/javascript">
			toPerfectSafeguardTask=function(taskId){
            	window.location.href = "${ctx}/safeguardTaskAction.do?method=perfectSafeguardTaskForm&taskId="+taskId;
			}
			toDeleteTempTask=function(taskId){
				if(confirm("您确认要删除该临时保障任务吗？")){
            		window.location.href = "${ctx}/safeguardTaskAction.do?method=deleteTempTask&taskId="+taskId;
            	}
			}
		</script>
	</head>
	<body>
		<template:titile value="完善保障任务"/>
		<display:table name="sessionScope.list" id="safeguard" pagesize="18">
			<display:column property="task_name" title="保障名称" headerClass="subject" maxLength="15" sortable="true"/>
			<display:column property="safeguard_level" title="保障级别" headerClass="subject"  sortable="true"/>
			<display:column property="region" title="保障地点" headerClass="subject" maxLength="15" sortable="true"/>
			<display:column property="task_createtime" title="创建时间" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
				<a href="javascript:toPerfectSafeguardTask('<bean:write name="safeguard" property="task_id"/>')">完善任务</a> | 
				<a href="javascript:toDeleteTempTask('<bean:write name="safeguard" property="task_id"/>')">删除</a>
			</display:column>
		</display:table>
	</body>
</html>

