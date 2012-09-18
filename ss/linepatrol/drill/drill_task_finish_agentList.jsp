<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>

<html>
	<head>
		<title>演练查询列表</title>
		<script type="text/javascript">
			toViewDrill=function(taskId,planId,summaryId,conId){
            	window.location.href = "${ctx}/queryStatAction.do?method=viewDrill&taskId="+taskId+"&planId="+planId+"&summaryId="+summaryId+"&conId="+conId;
			}
		</script>
	</head>
	<body>
		<template:titile value="全部已办工作 (<font color='red'>共${num }条已办</font>)"/>
		<!-- <div style="text-align:center;">
			<iframe
				src="${ctx}/drillTaskAction.do?method=viewDrillTaskProcess&&task_name=${param.task_name }"
				frameborder="0" id="processGraphFrame" height="100" scrolling="no"
				width="95%"></iframe>
		</div> -->
		<logic:notEmpty name="list">
		<display:table name="sessionScope.list" id="drill" pagesize="18">
				<bean:define id="sendUserId" name="drill" property="creator" />
				<bean:define id="id" name="drill" property="task_id"></bean:define>
				<bean:define id="applyState" name="drill" property="drill_state"></bean:define>
			<display:column property="task_name" title="演练名称" headerClass="subject" maxLength="18" sortable="true"/>
			<display:column property="drill_level" title="演练级别" headerClass="subject"  sortable="true"/>
			<display:column property="locale" title="演练地点" headerClass="subject"  sortable="true"/>
			<display:column property="username" title="任务创建人" headerClass="subject"  sortable="true"/>
			<display:column property="contractorname" title="代维单位" headerClass="subject"  sortable="true"/>
			<display:column property="task_createtime" title="创建时间" headerClass="subject"  sortable="true"/>
			<display:column property="con_state" title="状态" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
				<a href="javascript:toViewDrill('<bean:write name="drill" property="task_id"/>','<bean:write name="drill" property="plan_id"/>','<bean:write name="drill" property="summary_id"/>','<bean:write name="drill" property="con_id"/>')">查看</a>
			</display:column>
		</display:table>
		</logic:notEmpty>
	</body>
</html>

