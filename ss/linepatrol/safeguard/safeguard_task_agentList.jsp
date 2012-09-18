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
			toViewSafeguardTask=function(taskId){
            	window.location.href = "${ctx}/safeguardTaskAction.do?method=viewSafeguardTask&taskId="+taskId;
			}
			toAddSafeguardPlan=function(taskId){
            	window.location.href = "${ctx}/safeguardPlanAction.do?method=addSafeguardPlanForm&taskId="+taskId;
			}
			toViewSafeguardPlan=function(planId,flag){
            	window.location.href = "${ctx}/safeguardPlanAction.do?method=viewSafeguardPlan&planId="+planId+"&flag="+flag;
			}
			toReadSafeguardPlan=function(planId,flag,isread){
            	window.location.href = "${ctx}/safeguardPlanAction.do?method=viewSafeguardPlan&planId="+planId+"&flag="+flag+"&isread="+isread;
			}
			toApproveSafeguardPlanForm=function(planId,operator){
            	window.location.href = "${ctx}/safeguardPlanAction.do?method=approveSafeguardPlanForm&planId="+planId+"&operator="+operator;
			}
			toEditSafeguardPlan=function(planId){
            	window.location.href = "${ctx}/safeguardPlanAction.do?method=editSafeguardPlanForm&planId="+planId;
			}
			toAddSafeguardSummary=function(planId){
            	window.location.href = "${ctx}/safeguardSummaryAction.do?method=addSafeguardSummaryForm&planId="+planId;
			}
			toViewSafeguardSummary=function(summaryId){
            	window.location.href = "${ctx}/safeguardSummaryAction.do?method=viewSafeguardSummary&summaryId="+summaryId;
			}
			toReadSafeguardSummary=function(summaryId,isread){
            	window.location.href = "${ctx}/safeguardSummaryAction.do?method=viewSafeguardSummary&summaryId="+summaryId+"&isread="+isread;
			}
			toApproveSafeguardSummaryForm=function(summaryId,operator){
            	window.location.href = "${ctx}/safeguardSummaryAction.do?method=approveSafeguardSummaryForm&summaryId="+summaryId+"&operator="+operator;
			}
			toEditSafeguardSummaryForm=function(summaryId){
            	window.location.href = "${ctx}/safeguardSummaryAction.do?method=editSafeguardSummaryForm&summaryId="+summaryId;
			}
			toExamSafeguard=function(summaryId){
            	window.location.href = "${ctx}/safeguardExamAction.do?method=examSafeguardForm&summaryId="+summaryId;
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
			toReadSafeguardEndPlan=function(businessId,spplanId,endId){
            	window.location.href = "${ctx}/specialEndPlanAction.do?method=specialEndPlanForward&id="+spplanId+"&spEndId="+endId+"&type=view&businessId="+businessId+"&isread=isread";
			}
			toRedoAddSpecialPlan=function(safeguardName,planId,startDate,endDate,endId){
            	window.location.href = "${ctx}/specialplan.do?method=addFrom&ptype=002&isApply=true&redo=redo&pName="+safeguardName+"&id="+planId+"&startDate="+startDate+"&endDate="+endDate+"&endId="+endId;
			}
			//查看特巡计划
			toViewSpecialPlan=function(spid){
            	window.location.href = "specialplan.do?method=loadPlan&ptype=002&isApply=false&id="+spid+"&type=view";
			}
			//查阅特巡计划
			toReadSpecialPlan=function(spid){
            	window.location.href = "specialEndPlanAction.do?method=specialEndPlanForward&ptype=002&isApply=false&id="+spid+"&type=read";
			}
			//审批特巡计划
			toApproveSpecialPlanForm=function(spid,operator){
            	window.location.href = "${ctx}/specialEndPlanAction.do?method=specialEndPlanForward&ptype=002&isApply=false&id="+spid+"&type=approveSplan&operator="+operator;
			}
			//修改特巡计划
			toEditSpecialPlan=function(spid,businessId){
            	window.location.href = "specialplan.do?method=loadPlan&ptype=002&isApply=true&id="+spid+"&type=edit&businessId="+businessId+"&redo=redo";
			}
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
		<template:titile value="全部待办工作 (<font color='red'>共${num }条待办</font>)"/>
		<div style="text-align:center;">
			<iframe
				src="${ctx}/safeguardTaskAction.do?method=viewSafeGuardProcess&&task_name=${param.task_name }"
				frameborder="0" id="processGraphFrame" height="100" scrolling="no"
				width="95%"></iframe>
		</div>
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
				<logic:equal value="制定保障方案" name="safeguard" property="transact_state">
					<a href="javascript:toViewSafeguardTask('<bean:write name="safeguard" property="task_id"/>')" title="查看保障任务">查看</a> | 
					<a href="javascript:toAddSafeguardPlan('<bean:write name="safeguard" property="task_id"/>')" title="制定保障方案及计划">制定方案</a>
				</logic:equal>
				<logic:equal value="保障方案审核" name="safeguard" property="transact_state">
					<logic:equal value="true" name="safeguard" property="isread">
						<a href="javascript:toReadSafeguardPlan('<bean:write name="safeguard" property="plan_id"/>','view','isread')" title="查看保障方案">查阅</a>
					</logic:equal>
					<logic:equal value="false" name="safeguard" property="isread">
						<a href="javascript:toViewSafeguardPlan('<bean:write name="safeguard" property="plan_id"/>','view')" title="查看保障方案">查看</a> | 
						<a href="javascript:toApproveSafeguardPlanForm('<bean:write name="safeguard" property="plan_id"/>','approve')" title="保障方案审核">审核</a> | 
						<a href="javascript:toApproveSafeguardPlanForm('<bean:write name="safeguard" property="plan_id"/>','transfer')" title="保障方案转审">转审</a>
					</logic:equal>
				</logic:equal>
				<logic:equal value="保障方案审核不通过" name="safeguard" property="transact_state">
					<a href="javascript:toViewSafeguardPlan('<bean:write name="safeguard" property="plan_id"/>','view')" title="查看保障方案">查看</a> | 
					<a href="javascript:toEditSafeguardPlan('<bean:write name="safeguard" property="plan_id"/>')" title="修改保障方案">修改</a>
				</logic:equal>
				<logic:equal value="view" name="safeguard" property="page_flag">
					<logic:equal value="方案执行中" name="safeguard" property="transact_state">
						<a href="javascript:toViewSafeguardPlan('<bean:write name="safeguard" property="plan_id"/>','terminate')" title="查看保障方案">查看</a>
					</logic:equal>
				</logic:equal>
				<!--<logic:equal value="endPlanUnapprove" name="safeguard" property="page_flag">
					<logic:equal value="有未审核终止方案" name="safeguard" property="transact_state">
						<a href="javascript:toViewSafeguardPlan('<bean:write name="safeguard" property="plan_id"/>','terminate')" title="查看保障方案">查看</a>
					</logic:equal>
				</logic:equal>-->
				<logic:equal value="operator" name="safeguard" property="page_flag">
					<logic:equal value="制定保障总结" name="safeguard" property="transact_state">
						<a href="javascript:toViewSafeguardPlan('<bean:write name="safeguard" property="plan_id"/>','terminate')" title="查看保障方案">查看</a> | 
						<a href="javascript:toAddSafeguardSummary('<bean:write name="safeguard" property="plan_id"/>')" title="填写保障总结">填写总结</a>
					</logic:equal>
				</logic:equal>
				<logic:equal value="终止方案审核" name="safeguard" property="transact_state">
					<logic:equal value="true" name="safeguard" property="isread">
						<a href="javascript:toReadSafeguardEndPlan('<bean:write name="safeguard" property="plan_id"/>','<bean:write name="safeguard" property="sp_id"/>','<bean:write name="safeguard" property="sp_end_id"/>')" title="查看保障方案">查阅</a>
					</logic:equal>
					<logic:equal value="false" name="safeguard" property="isread">
						<a href="javascript:toViewSafeguardEndPlan('<bean:write name="safeguard" property="plan_id"/>','<bean:write name="safeguard" property="sp_id"/>','<bean:write name="safeguard" property="sp_end_id"/>')" title="查看保障方案">查看</a> | 
						<a href="javascript:toApproveSafeguardEndPlanForm('<bean:write name="safeguard" property="sp_id"/>','<bean:write name="safeguard" property="sp_end_id"/>','approve')" title="保障方案审核">审核</a> | 
						<a href="javascript:toApproveSafeguardEndPlanForm('<bean:write name="safeguard" property="sp_id"/>','<bean:write name="safeguard" property="sp_end_id"/>','transfer')" title="保障方案转审">转审</a>
					</logic:equal>
				</logic:equal>
				
				<logic:equal value="保障总结审核" name="safeguard" property="transact_state">
					<logic:equal value="true" name="safeguard" property="isread">
						<a href="javascript:toReadSafeguardSummary('<bean:write name="safeguard" property="summary_id"/>','isread')" title="查看保障总结">查阅</a>
					</logic:equal>
					<logic:equal value="false" name="safeguard" property="isread">
						<a href="javascript:toViewSafeguardSummary('<bean:write name="safeguard" property="summary_id"/>')" title="查看保障总结">查看</a> | 
						<a href="javascript:toApproveSafeguardSummaryForm('<bean:write name="safeguard" property="summary_id"/>','approve')" title="保障总结审核">审核</a> | 
						<a href="javascript:toApproveSafeguardSummaryForm('<bean:write name="safeguard" property="summary_id"/>','transfer')" title="保障总结转审">转审</a>
					</logic:equal>
				</logic:equal>
				<logic:equal value="保障总结审核不通过" name="safeguard" property="transact_state">
					<a href="javascript:toViewSafeguardSummary('<bean:write name="safeguard" property="summary_id"/>')" title="查看保障总结">查看</a> | 
					<a href="javascript:toEditSafeguardSummaryForm('<bean:write name="safeguard" property="summary_id"/>')" title="修改保障总结">修改</a>
				</logic:equal>
				<logic:equal value="评分" name="safeguard" property="transact_state">
					<a href="javascript:toViewSafeguardSummary('<bean:write name="safeguard" property="summary_id"/>')" title="查看保障总结">查看</a> |
					<a href="javascript:toExamSafeguard('<bean:write name="safeguard" property="summary_id"/>')" title="考核评分">考核</a>
				</logic:equal>
				<logic:equal value="重新制定计划" name="safeguard" property="transact_state">
					<a href="javascript:toViewSafeguardPlan('<bean:write name="safeguard" property="plan_id"/>')" title="查看保障方案">查看</a> | 
					<a href="javascript:toRedoAddSpecialPlan('<bean:write name="safeguard" property="task_name"/>','<bean:write name="safeguard" property="plan_id"/>','<bean:write name="safeguard" property="start_date" format="yyyy/MM/dd HH:mm:ss"/>','<bean:write name="safeguard" property="end_date" format="yyyy/MM/dd HH:mm:ss"/>','<bean:write name="safeguard" property="sp_end_id"/>')" title="制定特巡计划">制定特巡计划</a>
				</logic:equal>
				<logic:equal value="特巡计划审核" name="safeguard" property="transact_state">
					<logic:equal value="true" name="safeguard" property="isread">
						<a href="javascript:toReadSpecialPlan('<bean:write name="safeguard" property="sp_id"/>')" title="查看特巡计划">查阅</a>
					</logic:equal>
					<logic:equal value="false" name="safeguard" property="isread">
						<a href="javascript:toViewSpecialPlan('<bean:write name="safeguard" property="sp_id"/>')" title="查看特巡计划">查看</a> | 
						<a href="javascript:toApproveSpecialPlanForm('<bean:write name="safeguard" property="sp_id"/>','approve')" title="特巡计划审核">审核</a> | 
						<a href="javascript:toApproveSpecialPlanForm('<bean:write name="safeguard" property="sp_id"/>','transfer')" title="特巡计划转审">转审</a>
					</logic:equal>
				</logic:equal>
				<logic:equal value="修改特巡计划" name="safeguard" property="transact_state">
					<a href="javascript:toViewSpecialPlan('<bean:write name="safeguard" property="sp_id"/>')" title="查看特巡计划">查看</a> | 
					<a href="javascript:toEditSpecialPlan('<bean:write name="safeguard" property="sp_id"/>','<bean:write name="safeguard" property="plan_id"/>')" title="修改特巡计划">修改</a>
				</logic:equal>
				
			</display:column>
		</display:table>
	</body>
</html>

