<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />
<script type='text/javascript' src='${ctx}/js/extjs/adapter/ext/ext-base.js'></script>
<script type='text/javascript' src='${ctx}/js/extjs/ext-all.js'></script>
<link rel='stylesheet' type='text/css' href='${ctx}/js/extjs/resources/css/ext-all.css' />
<html>
	<head>
		<title>保障查询列表</title>
		<script type="text/javascript">
			toReadSafeguardEndPlan=function(businessId,spplanId,endId){
            	window.location.href = "${ctx}/specialEndPlanAction.do?method=specialEndPlanForward&id="+spplanId+"&spEndId="+endId+"&type=view&businessId="+businessId+"&isread=isread";
			}
			function toViewSafeguardEndPlan(businessId,spplanId,endId){
				var url = "${ctx}/specialEndPlanAction.do?method=specialEndPlanForward&id="+spplanId+"&spEndId="+endId+"&type=view&businessId="+businessId;
				win = new Ext.Window({
					layout : 'fit',
					width:650,
					height:400,
					resizable:true,
					closeAction : 'close',
					modal:true,
					html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src="'+url+'" />',
					plain: true
				});
				win.show(Ext.getBody());
			}
			toApproveSafeguardEndPlanForm=function(spplanId,endId,operator){
            	window.location.href = "${ctx}/specialEndPlanAction.do?method=specialEndPlanForward&id="+spplanId+"&spEndId="+endId+"&operator="+operator+"&type=approve";
			}
		</script>
	</head>
	<body>
		<template:titile value="全部待办工作 (<font color='red'>共${num }条待办</font>)"/>
		<display:table name="sessionScope.list" id="safeguard" pagesize="18">
			<display:column property="plan_name" title="特巡计划" headerClass="subject"  sortable="true"/>
			<display:column property="end_type" title="终止类型" headerClass="subject"  sortable="true"/>
			<display:column property="prev_end_date" title="终止前截止日期" headerClass="subject" sortable="true"/>
			<display:column property="end_date" title="终止后截止日期" headerClass="subject"  sortable="true"/>
			<display:column property="creater" title="创建人" headerClass="subject"  sortable="true"/>
			<display:column property="create_time" title="创建日期" headerClass="subject"  sortable="true"/>
			<display:column media="html" title="操作" >
				<logic:equal value="change_guard_plan_approve_task" name="safeguard" property="jbpm_task_name">
					<logic:equal value="true" name="safeguard" property="isread">
						<a href="javascript:toReadSafeguardEndPlan('<bean:write name="safeguard" property="scheme_id"/>','<bean:write name="safeguard" property="plan_id"/>','<bean:write name="safeguard" property="sp_end_id"/>')" title="查看保障方案">查看</a>
					</logic:equal>
					<logic:equal value="false" name="safeguard" property="isread">
						<a href="javascript:toViewSafeguardEndPlan('<bean:write name="safeguard" property="scheme_id"/>','<bean:write name="safeguard" property="plan_id"/>','<bean:write name="safeguard" property="sp_end_id"/>')" title="查看保障方案">查看</a> | 
						<a href="javascript:toApproveSafeguardEndPlanForm('<bean:write name="safeguard" property="plan_id"/>','<bean:write name="safeguard" property="sp_end_id"/>','approve')" title="保障方案审核">审核</a> | 
						<a href="javascript:toApproveSafeguardEndPlanForm('<bean:write name="safeguard" property="plan_id"/>','<bean:write name="safeguard" property="sp_end_id"/>','transfer')" title="保障方案转审">转审</a>
					</logic:equal>
				</logic:equal>
			</display:column>
		</display:table>
	</body>
</html>

