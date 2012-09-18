<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css" media="screen, print" />

<html>
	<head>
		<title>演练查询列表</title>
		<script type="text/javascript">
			toViewDrillTask=function(taskId){
            	window.location.href = "${ctx}/drillTaskAction.do?method=viewDrillTask&taskId="+taskId;
			}
			toAddDrillPlan=function(taskId){
            	window.location.href = "${ctx}/drillPlanAction.do?method=addDrillPlanForm&taskId="+taskId;
			}
			toViewDrillPlan=function(planId){
            	window.location.href = "${ctx}/drillPlanAction.do?method=viewDrillPlan&planId="+planId;
			}
			toReadDrillPlan=function(planId,isread){
            	window.location.href = "${ctx}/drillPlanAction.do?method=viewDrillPlan&planId="+planId+"&isread="+isread;
			}
			toApproveDrillPlanForm=function(planId,operator){
            	window.location.href = "${ctx}/drillPlanAction.do?method=approveDrillPlanForm&planId="+planId+"&operator="+operator;
			}
			toEditDrillPlan=function(planId){
            	window.location.href = "${ctx}/drillPlanAction.do?method=editDrillPlanForm&planId="+planId;
			}
			toAddDrillPlanModify=function(planId){
            	window.location.href = "${ctx}/drillPlanModifyAction.do?method=addDrillPlanModifyForm&planId="+planId;
			}
			toApproveModifyList=function(planId){
            	window.location.href = "${ctx}/drillPlanModifyAction.do?method=getApproveModifyList&planId="+planId;
			}
			toViewDrillPlanModify=function(planModifyId){
            	window.location.href = "${ctx}/drillPlanModifyAction.do?method=viewDrillPlanModify&planModifyId="+planModifyId;
			}
			toReadDrillPlanModify=function(planModifyId,isread){
            	window.location.href = "${ctx}/drillPlanModifyAction.do?method=viewDrillPlanModify&planModifyId="+planModifyId+"&isread="+isread;
			}
			toApproveDrillPlanModifyForm=function(planModifyId,operator){
            	window.location.href = "${ctx}/drillPlanModifyAction.do?method=approveDrillPlanModifyForm&planModifyId="+planModifyId+"&operator="+operator;
			}
			toViewDrillPlanModify=function(planModifyId){
            	window.location.href = "${ctx}/drillPlanModifyAction.do?method=viewDrillPlanModify&planModifyId="+planModifyId;
			}
			toApproveDrillPlanModifyForm=function(planModifyId,operator){
            	window.location.href = "${ctx}/drillPlanModifyAction.do?method=approveDrillPlanModifyForm&planModifyId="+planModifyId+"&operator="+operator;
			}
			toAddDrillSummary=function(planId){
            	window.location.href = "${ctx}/drillSummaryAction.do?method=addDrillSummaryForm&planId="+planId;
			}
			toViewDrillSummary=function(summaryId){
            	window.location.href = "${ctx}/drillSummaryAction.do?method=viewDrillSummary&summaryId="+summaryId;
			}
			toReadDrillSummary=function(summaryId,isread){
            	window.location.href = "${ctx}/drillSummaryAction.do?method=viewDrillSummary&summaryId="+summaryId+"&isread="+isread;
			}
			toApproveDrillSummaryForm=function(summaryId,operator){
            	window.location.href = "${ctx}/drillSummaryAction.do?method=approveDrillSummaryForm&summaryId="+summaryId+"&operator="+operator;
			}
			toEditDrillSummaryForm=function(summaryId){
            	window.location.href = "${ctx}/drillSummaryAction.do?method=editDrillSummaryForm&summaryId="+summaryId;
			}
			toExamDrill=function(summaryId){
            	window.location.href = "${ctx}/drillExamAction.do?method=examDrillForm&summaryId="+summaryId;
			}
		</script>
	</head>
	<%	
    	UserInfo userInfo = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );
    	String deptType = userInfo.getDeptype();
    	request.setAttribute("deptType", deptType);
    %>
	<body>
		<template:titile value="全部待办工作 (<font color='red'>共${num }条待办</font>)"/>
		<div style="text-align:center;">
			<iframe
				src="${ctx}/drillTaskAction.do?method=viewDrillTaskProcess&&task_name=${param.task_name }"
				frameborder="0" id="processGraphFrame" height="100" scrolling="no"
				width="95%"></iframe>
		</div>
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
				<logic:equal value="制定方案" name="drill" property="con_state">
					<a href="javascript:toViewDrillTask('<bean:write name="drill" property="task_id"/>')" title="查看演练方案">查看</a> | 
					<a href="javascript:toAddDrillPlan('<bean:write name="drill" property="task_id"/>')">制定方案</a>
				</logic:equal>
				<logic:equal value="方案审核" name="drill" property="con_state">
					<logic:equal value="true" name="drill" property="isread">
						<a href="javascript:toReadDrillPlan('<bean:write name="drill" property="plan_id"/>','isread')" title="查看演练方案">查阅</a>
					</logic:equal>
					<logic:equal value="false" name="drill" property="isread">
						<a href="javascript:toViewDrillPlan('<bean:write name="drill" property="plan_id"/>')" title="查看演练方案">查看</a> | 
						<a href="javascript:toApproveDrillPlanForm('<bean:write name="drill" property="plan_id"/>','approve')" title="审核演练方案">审核</a> | 
						<a href="javascript:toApproveDrillPlanForm('<bean:write name="drill" property="plan_id"/>','transfer')" title="转审演练方案">转审</a>
					</logic:equal>
				</logic:equal>
				<logic:equal value="方案审核不通过" name="drill" property="con_state">
					<a href="javascript:toViewDrillPlan('<bean:write name="drill" property="plan_id"/>')" title="查看演练方案">查看</a> | 
					<a href="javascript:toEditDrillPlan('<bean:write name="drill" property="plan_id"/>')" title="修改演练方案">修改</a>
				</logic:equal>
				<logic:equal value="方案变更审核" name="drill" property="con_state">
					<c:if test="${deptType=='1'}">
						<logic:equal value="false" name="drill" property="isread">
							<a href="javascript:toViewDrillPlanModify('<bean:write name="drill" property="modify_id"/>')" title="查看演练方案变更">查看</a> |
							<a href="javascript:toApproveDrillPlanModifyForm('<bean:write name="drill" property="modify_id"/>','approve')" title="审核演练方案变更">审核</a> | 
							<a href="javascript:toApproveDrillPlanModifyForm('<bean:write name="drill" property="modify_id"/>','transfer')" title="转审演练方案变更">转审</a>
						</logic:equal>
						<logic:equal value="true" name="drill" property="isread">
							<a href="javascript:toReadDrillPlanModify('<bean:write name="drill" property="modify_id"/>','isread')" title="查看演练方案变更">查阅</a>
						</logic:equal>
					</c:if>
				</logic:equal>
				<logic:equal value="方案变更待审核" name="drill" property="con_state">
						<a href="javascript:toViewDrillPlan('<bean:write name="drill" property="plan_id"/>')" title="查看演练方案">查看</a>
				</logic:equal>
				<logic:equal value="制定总结" name="drill" property="con_state">
					<a href="javascript:toViewDrillPlan('<bean:write name="drill" property="plan_id"/>')" title="查看演练方案">查看</a> | 
					<a href="javascript:toAddDrillSummary('<bean:write name="drill" property="plan_id"/>')" title="填写演练总结">填写总结</a> | 
					<a href="javascript:toAddDrillPlanModify('<bean:write name="drill" property="plan_id"/>')" title="填写方案变更">方案变更</a>
				</logic:equal>
				<logic:equal value="总结审核" name="drill" property="con_state">
					<logic:equal value="true" name="drill" property="isread">
						<a href="javascript:toReadDrillSummary('<bean:write name="drill" property="summary_id"/>','isread')" title="查看演练总结">查阅</a>
					</logic:equal>
					<logic:equal value="false" name="drill" property="isread">
						<a href="javascript:toViewDrillSummary('<bean:write name="drill" property="summary_id"/>')" title="查看演练总结">查看</a> |
						<a href="javascript:toApproveDrillSummaryForm('<bean:write name="drill" property="summary_id"/>','approve')" title="审核演练总结">审核</a> | 
						<a href="javascript:toApproveDrillSummaryForm('<bean:write name="drill" property="summary_id"/>','transfer')" title="转审演练总结">转审</a>
					</logic:equal>
				</logic:equal>
				<logic:equal value="总结审核不通过" name="drill" property="con_state">
					<a href="javascript:toViewDrillSummary('<bean:write name="drill" property="summary_id"/>')" title="查看演练总结">查看</a> |
					<a href="javascript:toEditDrillSummaryForm('<bean:write name="drill" property="summary_id"/>')" title="修改演练总结">修改</a>
				</logic:equal>
				<logic:equal value="评分" name="drill" property="con_state">
					<a href="javascript:toViewDrillSummary('<bean:write name="drill" property="summary_id"/>')" title="查看演练总结">查看</a> |
					<a href="javascript:toExamDrill('<bean:write name="drill" property="summary_id"/>')" title="考核评分">评分</a>
				</logic:equal>
			</display:column>
		</display:table>
		</logic:notEmpty>
		<br/><br/>
	</body>
</html>

