<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<html>
	<head>
		<title>保障查询列表</title>
		<script type="text/javascript">
			toViewSafeguard=function(taskId,planId,summaryId,conId){
            	window.location.href = "${ctx}/safeguardQueryStatAction.do?method=viewSafeguard&taskId="+taskId+"&planId="+planId+"&summaryId="+summaryId+"&conId="+conId;
			}
		//取消流程
		toCancelForm=function(safeguardTaskId){
			var url;
			if(confirm("确定要取消该保障流程吗？如果取消将结束所有的分发的流程。")){
				url="${ctx}/safeguardTaskAction.do?method=cancelSafeguardTaskForm";
				var queryString="safeguard_task_id="+safeguardTaskId;
				//location=url+"&"+queryString+"&rnd="+Math.random();
				window.open(url+"&"+queryString+"&rnd="+Math.random(),'','width=300,height=200,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no');
			}
		}
		</script>
	</head>
	<body>
		<template:titile value="全部已办工作 (<font color='red'>共${num }条已办</font>)"/>
		<!-- <div style="text-align:center;">
			<iframe
				src="${ctx}/safeguardTaskAction.do?method=viewSafeGuardProcess&&task_name=${param.task_name }"
				frameborder="0" id="processGraphFrame" height="100" scrolling="no"
				width="95%"></iframe>
		</div> -->
		<display:table name="sessionScope.list" id="safeguard" pagesize="18">
			<logic:notEmpty name="safeguard">
				<bean:define id="sendUserId" name="safeguard" property="sender" />
				<bean:define id="id" name="safeguard" property="task_id"></bean:define>
				<bean:define id="applyState" name="safeguard" property="safeguard_state"></bean:define>
			</logic:notEmpty>
			<display:column property="task_name" title="保障名称" headerClass="subject" maxLength="15" sortable="true"/>
			<display:column property="contractorname" title="代维单位" headerClass="subject"  sortable="true"/>
			<display:column property="safeguard_level" title="保障级别" headerClass="subject"  sortable="true"/>
			<display:column property="region" title="保障地点" headerClass="subject" maxLength="15" sortable="true"/>
			<display:column property="username" title="任务创建人" headerClass="subject"  sortable="true"/>
			<display:column property="task_createtime" title="创建时间" headerClass="subject"  sortable="true"/>
			<display:column property="transact_state" title="状态" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
				<a href="javascript:toViewSafeguard('<bean:write name="safeguard" property="task_id"/>','<bean:write name="safeguard" property="plan_id"/>','<bean:write name="safeguard" property="summary_id"/>','<bean:write name="safeguard" property="con_id"/>')">查看</a>
				<c:if test="${sessionScope.LOGIN_USER.deptype=='1'}">
					<c:if test="${applyState=='1'}">
						| <a href="javascript:toCancelForm('<bean:write name="safeguard" property="task_id"/>')">取消</a>
					</c:if>
					<c:if test="${applyState=='2'}">
						| <a href="javascript:toCancelForm('<bean:write name="safeguard" property="task_id"/>')">取消</a>
					</c:if>
					<c:if test="${applyState=='3'}">
						| <a href="javascript:toCancelForm('<bean:write name="safeguard" property="task_id"/>">取消</a>
					</c:if>
				</c:if>
			</display:column>
		</display:table> 
	</body>
</html>

