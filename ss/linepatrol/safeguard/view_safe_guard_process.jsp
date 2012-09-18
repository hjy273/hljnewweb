<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/linepatrol/css/button.css"
	type="text/css" media="screen, print" />
<html>
	<head>
		<title>sendtask</title>
		<script type="" language="javascript">
		toUrl=function(btn,taskName){
			var url="${ctx}/safeguardTaskAction.do?method=getAgentList&&task_name="+taskName;
			parent.location=url;
		}
		</script>
		<style type="text/css">
table td {
	text-align: center;
}
</style>
	</head>
	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<input id="btnStart" type="button" class="button_not_hits"
						disabled="disabled" value="制定保障任务" />
				</td>
				<td style="text-align: center">
					&nbsp;
				</td>

				<td style="text-align: center">
					<input type="button" id="btnExecuteGuardPlan"
						class="button_not_click"
						value="执行保障方案(${wait_guard_plan_execute_num }条)"
						onclick="toUrl(this,'guard_plan_execute_state');" />
				</td>

				<td style="text-align: center">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" id="btnChangeApprove" class="button_not_click"
						style="cursor: hand;" value="终止审核(${wait_guard_plan_approve_num }条)"
						onclick="toUrl(this,'change_guard_plan_approve_task');" />
				</td>
				<td>
					
				</td>
				<td>
					<input type="button" id="btnEvaluate" class="button_not_click"
						style="cursor: hand;" value="考核评分(${wait_evaluate_num }条)"
						onclick="toUrl(this,'evaluate_task');" />
				</td>

			</tr>
			<tr>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_down.gif" />
				</td>
				<td>
				</td>
				<td style="text-align: center;">
					<img src="${ctx}/linepatrol/images/arrow_up.gif" />
				</td>
				<td></td>
				<td>
					<span style="text-align: center;">
						<img src="${ctx}/linepatrol/images/arrow_down.gif" />
					</span>
				</td>
				<td></td>
				<td><img src="${ctx}/linepatrol/images/arrow_up.gif" /></td>

			</tr>
			<tr>
				<td>
					<input type="button" id="btnCreateGuardPlan"
						class="button_not_click" style="cursor: hand;"
						value="制定保障方案(${wait_create_guard_proj_num }条)"
						onclick="toUrl(this,'create_guard_proj_task');" />
				</td>
				<td style="text-align: center">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" id="btnApproveGuradPlan"
						class="button_not_click" style="cursor: hand;"
						value="审核保障方案(${wait_approve_guard_proj_num }条)"
						onclick="toUrl(this,'approve_guard_proj_task');" />
				</td>
				<td></td>
				<td>
					<input type="button" id="btnCreateGuardSummary"
						class="button_not_click" style="cursor: hand;"
						value="提交保障总结(${wait_create_guard_summary_num }条)"
						onclick="toUrl(this,'create_guard_summary_task');" />
				</td>
				<td>
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td><input type="button" id="btnApproveGuardSummary"
						class="button_not_click" style="cursor: hand;"
						value="审核保障总结(${wait_approve_guard_summary_num }条)"
						onclick="toUrl(this, 'approve_guard_summary_task');" /></td>

			</tr>
		</table>
		<logic:notEmpty name="task_name">
			<logic:equal value="create_guard_proj_task" name="task_name">
				<script type="text/javascript">
					document.getElementById('btnCreateGuardPlan').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="approve_guard_proj_task" name="task_name">
				<script type="text/javascript">
					document.getElementById('btnApproveGuradPlan').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="guard_plan_execute_state" name="task_name">
				<script type="text/javascript">
					document.getElementById('btnExecuteGuardPlan').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="change_guard_plan_approve_task" name="task_name">
				<script type="text/javascript">
					document.getElementById('btnChangeApprove').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="create_guard_summary_task" name="task_name">
				<script type="text/javascript">
					document.getElementById('btnCreateGuardSummary').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="approve_guard_summary_task" name="task_name">
				<script type="text/javascript">
					document.getElementById('btnApproveGuardSummary').className = "button_clicked";
				</script>
			</logic:equal>
			<logic:equal value="evaluate_task" name="task_name">
				<script type="text/javascript">
					document.getElementById('btnEvaluate').className = "button_clicked";
				</script>
			</logic:equal>
		</logic:notEmpty>
		<script type="text/javascript">
			<c:if test="${LOGIN_USER.deptype=='2'}">
				document.getElementById('btnStart').className = "button_not_hits";
				document.getElementById('btnStart').disabled = true;
				document.getElementById('btnEvaluate').className = "button_not_hits";
				document.getElementById('btnEvaluate').disabled = true;
				document.getElementById('btnApproveGuradPlan').className = "button_not_hits";
				document.getElementById('btnApproveGuradPlan').disabled = true;
				document.getElementById('btnApproveGuardSummary').className = "button_not_hits";
				document.getElementById('btnApproveGuardSummary').disabled = true;
				document.getElementById('btnChangeApprove').className = "button_not_hits";
				document.getElementById('btnChangeApprove').disabled = true;
			</c:if>
			<c:if test="${LOGIN_USER.deptype=='1'}">
				document.getElementById('btnExecuteGuardPlan').className = "button_not_hits";
				document.getElementById('btnExecuteGuardPlan').disabled = true;
				document.getElementById('btnCreateGuardSummary').className = "button_not_hits";
				document.getElementById('btnCreateGuardSummary').disabled = true;
				document.getElementById('btnCreateGuardPlan').className = "button_not_hits";
				document.getElementById('btnCreateGuardPlan').disabled = true;
			</c:if>
		</script>
	</body>
</html>
