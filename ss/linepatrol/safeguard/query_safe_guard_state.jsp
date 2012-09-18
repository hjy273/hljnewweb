<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/linepatrol/css/button.css"
	type="text/css" media="screen, print" />
<html>
	<head>
		<title>query for task state</title>
		<script type="" language="javascript">
		toUrl=function(btn,taskName){
			var parentForm=parent.document.forms[0];
			if(btn.className=="button_not_click"){
				btn.className="button_clicked";
				var inputElement=parent.document.getElementById("taskStates_"+taskName);
				if(typeof(inputElement)=="undefined"||inputElement==null){
					inputElement=parent.document.createElement("input");
				}
				inputElement.id="taskStates_"+taskName;
				inputElement.name="taskStates";
				inputElement.type="hidden";
				inputElement.value=taskName;
				parentForm.appendChild(inputElement);
			}else{
				btn.className="button_not_click";
				var inputElement=parent.document.getElementById("taskStates_"+taskName);
				if(typeof(inputElement)!="undefined"&&inputElement!=null){
					parentForm.removeChild(inputElement);
				}
			}
			parentForm.submit();
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
						class="button_not_click" value="执行保障方案"
						onclick="toUrl(this,'guard_plan_execute_state');" />
				</td>
				<td style="text-align: center">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" id="btnChangeApprove"
						class="button_not_click" style="cursor: hand;" value="终止审核"
						onclick="toUrl(this,'change_guard_plan_approve_task');" />
				</td>
				<td>
				</td>
				<td>
					<input type="button" id="btnEvaluate" class="button_not_click"
						style="cursor: hand;" value="考核评分"
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
					<span style="text-align: center;"> <img
							src="${ctx}/linepatrol/images/arrow_down.gif" /> </span>
				</td>
				<td></td>
				<td>
					<img src="${ctx}/linepatrol/images/arrow_up.gif" />
				</td>
			</tr>
			<tr>
				<td>
					<input type="button" id="btnCreateGuardPlan"
						class="button_not_click" style="cursor: hand;" value="制定保障方案"
						onclick="toUrl(this,'create_guard_proj_task');" />
				</td>
				<td style="text-align: center">
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" id="btnApproveGuradPlan"
						class="button_not_click" style="cursor: hand;" value="审核保障方案"
						onclick="toUrl(this,'approve_guard_proj_task');" />
				</td>
				<td></td>
				<td>
					<input type="button" id="btnCreateGuardSummary"
						class="button_not_click" style="cursor: hand;" value="提交保障总结"
						onclick="toUrl(this,'create_guard_summary_task');" />
				</td>
				<td>
					<img src="${ctx}/linepatrol/images/arrow_right.gif" />
				</td>
				<td>
					<input type="button" id="btnApproveGuardSummary"
						class="button_not_click" style="cursor: hand;" value="审核保障总结"
						onclick="toUrl(this, 'approve_guard_summary_task');" />
				</td>
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
		<logic:notEmpty name="task_names">
			<logic:iterate id="task_name" name="task_names" >
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
			</logic:iterate>
		</logic:notEmpty>
	</body>
</html>
