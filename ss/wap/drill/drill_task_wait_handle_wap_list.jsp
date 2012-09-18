<%@include file="/common/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"%>
<link rel="stylesheet" href="${ctx}/css/screen.css" type="text/css"
	media="screen, print" />

<html>
	<head>
		<title>演练查询列表</title>
		<script type="text/javascript">
		goBack=function(){
			var url="${ctx}/wap/login.do?method=index&&env=wap";
			location=url+"&&env=wap&&rnd="+Math.random();
		}
	toReadDrillPlan = function(planId, isread) {
		location.href = "${ctx}/wap/drillPlanAction.do?method=viewDrillPlan&&env=wap&planId="
				+ planId + "&isread=" + isread;
	}
	toApproveDrillPlanForm = function(planId, operator) {
		location.href = "${ctx}/wap/drillPlanAction.do?method=approveDrillPlanForm&&env=wap&planId="
				+ planId + "&operator=" + operator;
	}
	toTransferApproveDrillPlanForm = function(planId, operator) {
		var actionUrl = "${ctx}/wap/drillPlanAction.do?method=approveDrillPlanForm&&env=wap";
		var queryString = "planId=" + planId + "&operator=" + operator;
		var form1 = document.forms["toApproverUrlForm"];
		form1.action_url.value = actionUrl + "&" + queryString + "&rnd="
				+ Math.random();
		form1.object_id.value = planId;
		form1.object_type.value = "LP_DRILLPLAN";
		form1.submit();
	}
	toReadDrillPlanModify = function(planModifyId, isread) {
		location.href = "${ctx}/wap/drillPlanModifyAction.do?method=viewDrillPlanModify&&env=wap&planModifyId="
				+ planModifyId + "&isread=" + isread;
	}
	toApproveDrillPlanModifyForm = function(planModifyId, operator) {
		location.href = "${ctx}/wap/drillPlanModifyAction.do?method=approveDrillPlanModifyForm&&env=wap&planModifyId="
				+ planModifyId + "&operator=" + operator;
	}
	toTransferApproveDrillPlanModifyForm = function(planModifyId, operator) {
		var actionUrl = "${ctx}/wap/drillPlanModifyAction.do?method=approveDrillPlanModifyForm&&env=wap";
		var queryString = "planModifyId=" + planModifyId + "&operator="
				+ operator;
		var form1 = document.forms["toApproverUrlForm"];
		form1.action_url.value = actionUrl + "&" + queryString + "&rnd="
				+ Math.random();
		form1.approver_input_name.value="approvers,mobiles";
		form1.object_id.value = planModifyId;
		form1.object_type.value = "LP_DRILLPLAN_MODIFY";
		form1.submit();
	}
	toReadDrillSummary = function(summaryId, isread) {
		location.href = "${ctx}/wap/drillSummaryAction.do?method=viewDrillSummary&&env=wap&summaryId="
				+ summaryId + "&isread=" + isread;
	}
	toApproveDrillSummaryForm = function(summaryId, operator) {
		location.href = "${ctx}/wap/drillSummaryAction.do?method=approveDrillSummaryForm&&env=wap&&env=wap&summaryId="
				+ summaryId + "&operator=" + operator;
	}
	toTransferApproveDrillSummaryForm = function(summaryId, operator) {
		var actionUrl = "${ctx}/wap/drillSummaryAction.do?method=approveDrillSummaryForm&&env=wap";
		var queryString = "summaryId=" + summaryId + "&operator=" + operator;
		var form1 = document.forms["toApproverUrlForm"];
		form1.action_url.value = actionUrl + "&" + queryString + "&rnd="
				+ Math.random();
		form1.approver_input_name.value="approvers,mobiles";
		form1.object_id.value = summaryId;
		form1.object_type.value = "LP_DRILLSUMMARY";
		form1.submit();
	}
</script>
	</head>
	<body>
		<template:titile value="全部待办工作 (<font color='red'>共${num }条待办</font>)" />
		<p>
			<input type="button" value="返回首页" onclick="goBack();">
		</p>
		<logic:notEmpty name="list">
		<%
			List list = (List) session.getAttribute("list");
			DynaBean bean;
			for (int i = 0; list != null && i < list.size(); i++) {
				bean = (DynaBean) list.get(i);
		%>
		<p>
			<%=(String) bean.get("task_name")%><br />
			<%=(String) bean.get("drill_level")%>&nbsp;&nbsp;
			<%=(String) bean.get("contractorname")%><br />
			<%=(String) bean.get("con_state")%><br />
			<%
				if ("方案审核".equals(bean.get("con_state"))) {
						if ("true".equals(bean.get("isread"))) {
			%>
			<a
				href="javascript:toReadDrillPlan('<%=(String) bean.get("plan_id")%>','isread')"
				title="查看演练方案">查阅</a>
			<%
				} else {
			%>
			<a
				href="javascript:toApproveDrillPlanForm('<%=(String) bean.get("plan_id")%>','approve')"
				title="审核演练方案">审核</a> |
			<a
				href="javascript:toTransferApproveDrillPlanForm('<%=(String) bean.get("plan_id")%>','transfer')"
				title="转审演练方案">转审</a>
			<%
				}
					}
					if ("方案变更审核".equals(bean.get("con_state"))) {
						if ("true".equals(bean.get("isread"))) {
			%>
			<a
				href="javascript:toReadDrillPlanModify('<%=(String) bean.get("modify_id")%>','isread')"
				title="查看演练方案变更">查阅</a>
			<%
				} else {
			%>
			<a
				href="javascript:toApproveDrillPlanModifyForm('<%=(String) bean.get("modify_id")%>','approve')"
				title="审核演练方案变更">审核</a> |
			<a
				href="javascript:toTransferApproveDrillPlanModifyForm('<%=(String) bean.get("modify_id")%>','transfer')"
				title="转审演练方案变更">转审</a>
			<%
				}
					}
					if ("总结审核".equals(bean.get("con_state"))) {
						if ("true".equals(bean.get("isread"))) {
			%>
			<a
				href="javascript:toReadDrillSummary('<%=(String) bean.get("summary_id")%>','isread')"
				title="查看演练总结">查阅</a>
			<%
				} else {
			%>
			<a
				href="javascript:toApproveDrillSummaryForm('<%=(String) bean.get("summary_id")%>','approve')"
				title="审核演练总结">审核</a> |
			<a
				href="javascript:toTransferApproveDrillSummaryForm('<%=(String) bean.get("summary_id")%>','transfer')"
				title="转审演练总结">转审</a>
			<%
				}
					}
			%>
		</p>
		<%
			}
		%>
		</logic:notEmpty>
		<p>
			<input type="button" value="返回首页" onclick="goBack();">
		</p>
		<div style="display: none;">
			<form id="toApproverUrlForm" method="post"
				action="${ctx}/wap/load_approvers.do?method=loadWapApprovers">
				<input name="display_type" value="mobile" type="hidden" />
				<input name="exist_value" value="" type="hidden" />
				<input name="object_id" value="" type="hidden" />
				<input name="object_type" value="" type="hidden" />
				<input name="except_self" value="true" type="hidden" />
				<input name="action_url" value="" type="hidden" />
				<input name="depart_id" value="" type="hidden" />
				<input name="approver_type" value="transfer" type="hidden" />
				<input name="approver_input_name" value="approvers,mobile"
					type="hidden" />
				<input name="reader_input_name" value="" type="hidden" />
			</form>
		</div>
	</body>
</html>

